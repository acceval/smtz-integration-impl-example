package com.acceval.core.cache.processor;

import com.acceval.core.cache.CacheException;
import com.acceval.core.cache.impl.ConditionRecordCache;
import com.acceval.core.cache.impl.ExchangeRateCache;
import com.acceval.core.cache.impl.MasterDataCache;
import com.acceval.core.cache.model.ConditionEvaluationResult;
import com.acceval.core.cache.model.ConditionFieldConfig;
import com.acceval.core.cache.model.ConditionFieldWrapper;
import com.acceval.core.cache.model.ConditionRecord;
import com.acceval.core.cache.model.ConditionRecordConfig;
import com.acceval.core.cache.model.ConditionTableInputParameter;
import com.acceval.core.cache.model.ConditionValue;
import com.acceval.core.cache.model.ConditionValueConfig;
import com.acceval.core.cache.model.Currency;
import com.acceval.core.cache.model.ExchangeRateType;
import com.acceval.core.cache.model.Uom;
import com.acceval.core.microservice.ObjectNotFoundException;
import com.acceval.core.model.VariableContext;
import com.acceval.core.security.PrincipalUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@ConditionalOnProperty(name = "microservice.cache", havingValue = "true")
public class ConditionRecordCacheProcessor {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ConditionRecordCache conditionRecordCache;

    @Autowired
    private MasterDataCache masterDataCache;

    @Autowired
    private ExchangeRateCache exchangeRateCache;

    @Autowired
    @Qualifier("cacheUomConversionUtil")
    private UomConversionUtil uomConversionUtil;

    @Autowired
    @Qualifier("cacheCurrencyConversionUtil")
    private CurrencyConversionUtil currencyConversionUtil;

    @Autowired
    private ConditionRecordRetriever conditionRecordRetriever;

    public ConditionEvaluationResult evaluate(String conditionRecordCode, MultiValueMap<String, String> mapParam) {
        conditionRecordCode = conditionRecordCode.replaceAll("-", "_");


        LinkedMultiValueMap<String, String> cloneMapParam = new LinkedMultiValueMap<>();
        cloneMapParam.addAll(mapParam);
        mapParam = cloneMapParam.deepCopy();

        // clean mapParam
        List<String> removeKeys = new ArrayList();
        for (Iterator<String> itr = mapParam.keySet().iterator(); itr.hasNext();) {
            String key = itr.next();
            if (mapParam.getFirst(key) == null || mapParam.getFirst(key).length() == 0) {
                removeKeys.add(key);
            }
        }

        for (Iterator<String> itr = removeKeys.iterator(); itr.hasNext();) {
            mapParam.remove(itr.next());
        }

        // pre resolve the context value for property based context key in the master
        // so already assume that the context passed into this method already resolve
        // TODO implement later at the
        //  1. ms-pricing (by calling masterDataClient)
        //  2. ms-masterdata (by calling existing method)
//        this.resolveEntityValueInContext(conditionRecordCode, mapParam);

        ConditionRecord conditionRecord = this.singleResultQuery(conditionRecordCode, mapParam);

        ConditionEvaluationResult evaluationResult = new ConditionEvaluationResult();

        if (conditionRecord != null) {

            evaluationResult.setConditionRecord(conditionRecord);

            VariableContext context = new VariableContext();
            context.setVariableMap(this.convertMultivaluedMapToMap(mapParam));

            if (!conditionRecord.getConditionValues().isEmpty()) {
                ConditionValue firstValue = conditionRecord.getConditionValues().get(0);
                evaluationResult.setFirstValue(firstValue.getValue());
            }
            evaluationResult.setVariableContext(context);

            ConditionValue firstNumericValue = null;
            for (ConditionValue value : conditionRecord.getConditionValues()) {
                if (value.getConfig().isNumericValue()) {
                    if (firstNumericValue == null) {
                        firstNumericValue = value;
                        Double calculatedValue = this.calculateNumericValue(firstNumericValue, context,
                                evaluationResult, true);
                        evaluationResult.setNumericValue(calculatedValue);
                    } else {
                        Double calculatedValue = this.calculateNumericValue(value, context, evaluationResult, false);
                        value.setValue(calculatedValue != null ? calculatedValue.toString() : null);
                    }
                }
            }

            this.copyEvaluationResultToContext(evaluationResult, context);
            return evaluationResult;

        } else {

            ConditionRecordConfig recordConfig = conditionRecordCache.getConditionRecordConfig(conditionRecordCode.toUpperCase());

            if (recordConfig != null) {
                evaluationResult.logNotFound(recordConfig, mapParam);
            }

            return evaluationResult;
        }
    }


    private ConditionRecord singleResultQuery(String conditionRecordCode, MultiValueMap<String, String> mapParam) {

        ConditionRecordConfig recordConfig = null;
        if (!mapParam.containsKey("COMPANY_ID")) {
            recordConfig = this.conditionRecordCache.getConditionRecordConfig(conditionRecordCode);
        } else {
            Long companyId = Long.valueOf(mapParam.getFirst("COMPANY_ID"));
            recordConfig =  this.conditionRecordCache.getConditionRecordConfig(String.valueOf(companyId), conditionRecordCode);
        }

        if (recordConfig != null && CollectionUtils.isNotEmpty(recordConfig.getConditionTableInputParameters())) {
            if (mapParam.get(VariableContext.PERCENTAGE_APPLY_INPUT_PARAM) == null) {
                for (ConditionTableInputParameter inputParam : recordConfig.getConditionTableInputParameters()) {
                    if (ConditionTableInputParameter.InputParamType.DIRECT.equals(inputParam.getType())
                            && StringUtils.isNotBlank(inputParam.getParameterCode())
                            && mapParam.containsKey(inputParam.getParameterCode())) {
                        mapParam.put(VariableContext.PERCENTAGE_APPLY_INPUT_PARAM,
                                Arrays.asList(new String[] { inputParam.getParameterCode() }));
                    }
                }
            }
        }

        // this.preformatProductLevel(recordConfig, mapParam);

        // don't need to implement the remote data display cause we only do the evaluation
//        Map<String, List<LabelValue>> remoteData = this.retriveRemoteData(recordConfig);

        ConditionRecord record = conditionRecordRetriever.singleResultConditionRecord(recordConfig, mapParam);

        return record;

    }

    public ConditionEvaluationResult evaluateConditionTable(String conditionTableCode, VariableContext context) {
        Set<String> lstFldCode = this.getRequiredCondFieldCode(conditionTableCode);

        MultiValueMap<String, String> mapParam = new LinkedMultiValueMap<>();

        if (context.getCompanyId() == null || context.getCompanyId() == 0l) {
            mapParam.add(VariableContext.COMPANY_ID, String.valueOf(PrincipalUtil.getCompanyID()));
            mapParam.add(VariableContext.COMPANY_CODE, PrincipalUtil.getCompanyCode());
        } else {
            mapParam.add(VariableContext.COMPANY_ID, context.getCompanyId().toString());
            mapParam.add(VariableContext.COMPANY_CODE, context.getCompanyCode());
        }

        if (lstFldCode != null) {
            for (String fldCode : lstFldCode) {
                Object objValue = context.getVariable(fldCode);
                if (objValue instanceof Collection) {
                    for (Object obj : (Collection<?>) objValue) {
                        mapParam.add(fldCode, String.valueOf(obj));
                    }
                } else {
                    mapParam.add(fldCode, context.getVariableAsString(fldCode));
                }
            }
        }

        return evaluate(conditionTableCode, mapParam);
    }

    public Set<String> getRequiredCondFieldCode(String conditionTableCode) {

        Set<String> lstCondFieldCode = new LinkedHashSet<>();

        // common keys
        ConditionRecordConfig config = this.conditionRecordCache.getConditionRecordConfig(conditionTableCode);

        if (config == null) {
            logger.error("Condition Table not found for [" + conditionTableCode + "]");
            return null;
        }
        lstCondFieldCode.add(VariableContext.VALID_FROM);
        lstCondFieldCode.add(VariableContext.VALID_TO);
        lstCondFieldCode.add(VariableContext.CURRENCY_CODE);
        lstCondFieldCode.add(VariableContext.CURRENCY_EXCHANGE_RATE_TYPE);
        lstCondFieldCode.add(VariableContext.UOM_CODE);
        lstCondFieldCode.add(VariableContext.SALES_DIVISION);// use for new UOM search
        lstCondFieldCode.add(VariableContext.CONTEXTKEY_PALLETIZE);// use for new UOM search
        lstCondFieldCode.add(VariableContext.PERCENTAGE_APPLY_INPUT_PARAM);
        lstCondFieldCode.add(VariableContext.PRODUCT);
        lstCondFieldCode.add(VariableContext.QUANTITY);
        lstCondFieldCode.add(VariableContext.COMPANY_CODE);
        lstCondFieldCode.add(VariableContext.COMPANY_ID);
        lstCondFieldCode.add(VariableContext.OVERWRITE_EXCHANGE_RATE);
        lstCondFieldCode.add(VariableContext.OVERWRITE_CURRENCY_FROM);
        lstCondFieldCode.add(VariableContext.OVERWRITE_CURRENCY_TO);

        if (config != null && CollectionUtils.isNotEmpty(config.getConditionTableInputParameters())) {
            for (ConditionTableInputParameter inputParam : config.getConditionTableInputParameters()) {
                if (ConditionTableInputParameter.InputParamType.DIRECT.equals(inputParam.getType()) && StringUtils.isNotBlank(inputParam.getParameterCode())) {
                    String targetPct = inputParam.getParameterCode();
                    if (StringUtils.isNotBlank(targetPct)) {
                        lstCondFieldCode.add(targetPct);
                    }
                }
            }
        }

        // Condition Field keys
        lstCondFieldCode.addAll(config.getConditionFieldWrappers().stream()
                .map(cf -> cf.getConditionFieldConfig().getCode()).collect(Collectors.toList()));

        if (!lstCondFieldCode.contains("COUNTRY")) {
            for (ConditionFieldWrapper wrapper: config.getConditionFieldWrappers()) {
                if ((wrapper.getConditionFieldConfig().getType() == ConditionFieldConfig.ConditionFieldType.ENTITY
                        && "com.acceval.masterdata.model.Region".indexOf(wrapper.getConditionFieldConfig().getEntityClass()) != -1)
                        || wrapper.getConditionFieldConfig().getType() == ConditionFieldConfig.ConditionFieldType.REGIONCOUNTRY) {
                    lstCondFieldCode.add("COUNTRY");
                    break;
                }
            }
        }

        return lstCondFieldCode;
    }

    private void copyEvaluationResultToContext(ConditionEvaluationResult evaluationResult, VariableContext context) {

        if (evaluationResult.getConditionRecord() != null) {

            for (ConditionValue conditionValue : evaluationResult.getConditionRecord().getConditionValues()) {

                context.setVariable(conditionValue.getConfig().getColumnName(), conditionValue.getValue());
            }
        }
    }

    private Double calculateNumericValue(ConditionValue numericConditionValue, VariableContext context,
                                         ConditionEvaluationResult evaluationResult, boolean primary) {

        String targetCurrencyCode = (String) context.getVariable(VariableContext.CURRENCY_CODE);
        String targetUomCode = (String) context.getVariable(VariableContext.UOM_CODE);

        Currency targetCurrency = null;
        Uom targetUom = null;

        try {
            targetCurrency = masterDataCache.getCurrencyByCode(targetCurrencyCode);
        } catch (ObjectNotFoundException e) {
            evaluationResult.logError(e);
            e.printStackTrace();
        }
        try {
            targetUom = masterDataCache.getUomByCode(targetUomCode);
        } catch (ObjectNotFoundException e) {
            evaluationResult.logError(e);
            e.printStackTrace();
        }

        if (primary) {
            evaluationResult.setTargetCurrency(targetCurrency);
            evaluationResult.setTargetUom(targetUom);
        }

        Double numericValue = 0d;
        if (numericConditionValue != null && StringUtils.isNotBlank(numericConditionValue.getValue())) {
            numericValue = Double.valueOf(numericConditionValue.getValue());
        }

        LocalDateTime effectiveDate = context.getVariableAsDateTime(VariableContext.VALID_FROM);
        if (numericConditionValue.getConfig().isContainsCurrency() && targetCurrency != null) {
            Currency valueCurrency = numericConditionValue.getCurrency();
            if (!valueCurrency.getCode().equals(targetCurrency.getCode())) {

                Long exchangeRateTypeId = context.getVariableAsLong(VariableContext.CURRENCY_EXCHANGE_RATE_TYPE);
                if (exchangeRateTypeId == null) {
                    String defaultType = exchangeRateCache.getDefaultExchangeRateType();
                    ExchangeRateType exchangeRateType = null;
                    try {
                        exchangeRateType = exchangeRateCache.getExchangeRateTypeByCode(defaultType);
                        exchangeRateTypeId = Long.valueOf(exchangeRateType.getExchangeRateTypeID());

                    } catch (Throwable e) {
                        e.printStackTrace();
                    }

                }
                
                numericValue = currencyConversionUtil.getConvertedAmount(
                        Double.valueOf(numericConditionValue.getValue()).doubleValue(),
                        numericConditionValue.getCurrencyId().longValue(), targetCurrency.getCurrencyID(),
                        effectiveDate, exchangeRateTypeId, context);
            }
        }

        if (numericConditionValue.getConfig().getType() == ConditionValueConfig.ConditionValueType.UNIT_AMOUNT) {
            Double quantity = context.getVariableAsDouble(VariableContext.QUANTITY);
            if (primary) {
                evaluationResult.setQuantity(quantity);
            }
            numericValue = numericValue * quantity;
        } else if (numericConditionValue.getConfig().getType() == ConditionValueConfig.ConditionValueType.PERCENTAGE) {
            String percentageApplyInputParam = context
                    .getVariableAsString(VariableContext.PERCENTAGE_APPLY_INPUT_PARAM);
            Double applyValue = context.getVariableAsDouble(percentageApplyInputParam);
            numericValue = numericValue * applyValue / 100;
        }

        if (numericConditionValue.getConfig().isContainsUom() && targetUom != null) {

            Long productId = context.getVariableAsLong(VariableContext.PRODUCT);
            Uom valueUom = numericConditionValue.getUom();
            double conversionRate = 0;

            if (productId != null) {
                try {
                    conversionRate = uomConversionUtil.getConvertedQuantity(productId, valueUom.getUomID(),
                            targetUom.getUomID(), 1, context);
                } catch (ObjectNotFoundException e) {
                    evaluationResult.logError(e);
                    e.printStackTrace();
                }
            } else {
                conversionRate = uomConversionUtil.getConvertedQuantity(valueUom.getUomID(), targetUom.getUomID(), 1,
                        context);
            }

            if (numericConditionValue.getConfig().getType() == ConditionValueConfig.ConditionValueType.UNIT_AMOUNT) {
                if (conversionRate != 0) {
                    numericValue = numericValue / conversionRate;
                }
            } else if (numericConditionValue.getConfig().getType() == ConditionValueConfig.ConditionValueType.QUANTITY) {
                if (conversionRate != 0) {
                    numericValue = numericValue * conversionRate;
                }
            }
        }

        return numericValue;
    }

    private Map<String, Object> convertMultivaluedMapToMap(MultiValueMap<String, String> mapParam) {

        Map<String, Object> map = new HashMap<String, Object>();

        if (mapParam == null) {
            return map;
        }

        for (Map.Entry<String, List<String>> entry : mapParam.entrySet()) {

            StringBuilder stringBuilder = new StringBuilder();

            for (String value : entry.getValue()) {

                if (stringBuilder.length() > 0) {
                    stringBuilder.append(',');
                }
                stringBuilder.append(value);
            }
            map.put(entry.getKey(), stringBuilder.toString());
        }

        return map;
    }

    public String test(String conditionRecordCode, MultiValueMap<String, String> mapParam) {
        StringBuffer buffer = new StringBuffer();

        ConditionEvaluationResult result = this.evaluate(conditionRecordCode, mapParam);

        buffer.append("Condition Record Evaluation : " + (result.isSuccess() ? "Success" : "Fail"));
        buffer.append("\n");

        if (result.isSuccess()) {
            buffer.append("Condition Record Evaluation : Condition Record Found " + result.getConditionRecord().getId() + " with value " + printValue(result.getConditionRecord()) + ".");
            buffer.append("\n");
            buffer.append("Condition Record Evaluation : Numeric : " + result.getNumericValue());
            buffer.append("\n");
        } else {
        }

        buffer.append("Condition Record Evaluation : Message : " + result.getMessage());
        buffer.append("\n");

        return buffer.toString();
    }

    private String printValue(ConditionRecord cr) {
        StringBuffer buffer = new StringBuffer();

        String sep = "";
        for (ConditionValue cv : cr.getConditionValues()) {
            buffer.append(sep).append(cv.getValue());

            if (cv.getConfig().getType() == ConditionValueConfig.ConditionValueType.UNIT_AMOUNT) {
                buffer.append(" ").append(cv.getCurrency().getCode()).append("/").append(cv.getUom().getCode());
            } else if (cv.getConfig().getType() == ConditionValueConfig.ConditionValueType.AMOUNT) {
                buffer.append(" ").append(cv.getCurrency().getCode());
            } else if (cv.getConfig().getType() == ConditionValueConfig.ConditionValueType.QUANTITY) {
                buffer.append(" ").append(cv.getUom().getCode());
            }
            sep = ", ";
        }

        return buffer.toString();
    }
}
