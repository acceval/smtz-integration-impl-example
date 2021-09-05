package com.acceval.core.cache.processor;

import com.acceval.core.cache.CacheException;
import com.acceval.core.cache.impl.ConditionRecordCache;
import com.acceval.core.cache.impl.MasterDataCache;
import com.acceval.core.cache.model.ConditionField;
import com.acceval.core.cache.model.ConditionFieldConfig;
import com.acceval.core.cache.model.ConditionFieldWrapper;
import com.acceval.core.cache.model.ConditionRecord;
import com.acceval.core.cache.model.ConditionRecordCacheHelper;
import com.acceval.core.cache.model.ConditionRecordCacheHolder;
import com.acceval.core.cache.model.ConditionRecordConfig;
import com.acceval.core.cache.model.ConditionValue;
import com.acceval.core.cache.model.ConditionValueConfig;
import com.acceval.core.cache.model.Range;
import com.acceval.core.cache.model.RangeGroup;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
@ConditionalOnProperty(name = "microservice.cache", havingValue = "true")
public class ConditionRecordRetriever {

    @Autowired
    private ConditionRecordCache conditionRecordCache;

    @Autowired
    private MasterDataCache masterDataCache;

    public ConditionRecord singleResultConditionRecord(ConditionRecordConfig config,
                                                MultiValueMap<String, String> masterMapParam) {

        LinkedMultiValueMap<String, String> mapParam = new LinkedMultiValueMap<>();

        mapParam.addAll(masterMapParam);

        ConditionRecordCacheHolder holder = conditionRecordCache.getConditionRecordCacheHolder(config.getCode());

        if (holder.getRecords() == null || holder.getRecords().isEmpty()) return null;

        List<ConditionRecordCacheHelper> records = holder.getRecords();
        // construct multi value map with preQueryProcess
        // parent product, region/country
        this.preQueryProcess(config, mapParam);

        // check whether has mandatory param
        if (!hasMandatoryParams(config, mapParam)) return null;

        // filter by date first
        if (config.isHasValidDate()) {

            String refDateString = mapParam.getFirst("VALID_FROM");
            if (refDateString == null) return null;

            LocalDateTime refDate = getLocalDateTime(refDateString);

            records = records.stream().filter(r -> {
                ConditionRecord cr = r.getConditionRecord();

                LocalDateTime from = cr.getValidFrom();
                LocalDateTime to = cr.getValidTo();

                return !(refDate.isBefore(from) || refDate.isAfter(to));
            }).collect(Collectors.toList());
        }

        if (records == null || records.isEmpty()) return null;

        // search record with score
        List<TemporalResult> temporalResults = new ArrayList<>();

        for (ConditionRecordCacheHelper helper : records) {
            int score = 0;
            try {
                for (ConditionFieldWrapper field : holder.getSortedFields()) {
                    score += getScore(field, helper, mapParam);
                }
            } catch (CacheException e) {
                continue;
            }

            if (score == 0) return cloneRecord(helper.getConditionRecord());
            TemporalResult temp = new TemporalResult();
            temp.score = score;
            temp.helper = helper;

            temporalResults.add(temp);
        }

        if (temporalResults.isEmpty()) {
            return null;
        }

        records = temporalResults.stream().sorted(Comparator.comparingInt(o -> o.score)).map(t -> t.helper).collect(Collectors.toList());

        if (records != null && records.size() > 0) {
            return cloneRecord(records.get(0).getConditionRecord());
        }
        return null;
    }

    private ConditionRecord cloneRecord(ConditionRecord record) {
        ConditionRecord clone = new ConditionRecord();

        BeanUtils.copyProperties(record, clone);

        clone.setConditionFields(record.getConditionFields().stream().map(conditionField -> {
            ConditionField c = new ConditionField();
            BeanUtils.copyProperties(conditionField, c);
            return c;
        }).collect(Collectors.toList()));

        clone.setConditionValues(record.getConditionValues().stream().map(conditionValue -> {
            ConditionValue c = new ConditionValue();
            BeanUtils.copyProperties(conditionValue, c);
            return c;
        }).collect(Collectors.toList()));

        return clone;
    }

//    public String generateMapKey(ConditionRecordCacheHolder holder,
//                                        ConditionRecordConfig config,
//                                        MultiValueMap<String, String> mapParam) {
//
//        String mapKey = "";
//
//        for (ConditionFieldWrapper w : holder.getSortedFields()) {
//
//        }
//    }

    private int getScore(ConditionFieldWrapper field, ConditionRecordCacheHelper helper, MultiValueMap<String, String> mapParam) throws CacheException {

        // assign score
        // 1. exact match : 0
        // 2. condition value is blank : 10
        // 3. level hierarchy map value like product/region : according to position of value matched
        // 4. if not match one (like condition value != map value), then should disqualify the condition record

        String key = field.getConditionFieldConfig().getCode();
        String value = helper.getValue(key);

        String mapParamValue = mapParam.getFirst(key);

        if (StringUtils.isBlank(value)) return 10;

        if (StringUtils.isNotBlank(value) && StringUtils.isNotBlank(mapParamValue)) {
            if (field.getConditionFieldConfig().getType() == ConditionFieldConfig.ConditionFieldType.RANGE) {

                try {
                    Double mapParamValueDouble = Double.parseDouble(mapParamValue);

                    RangeGroup rg = this.masterDataCache.getRangeGroup(field.getConditionFieldConfig().getRangeGroup().getRangeGroupId());

                    if (rg != null) {
                        List<String> matchedRangeIDs = new ArrayList<>();
                        for (Range r : rg.getRanges()) {
                            if (mapParamValueDouble <= r.getMaxValue() && mapParamValueDouble>= r.getMinValue()) {
                                matchedRangeIDs.add(r.getRangeId().toString());
                            }
                        }

                        if (matchedRangeIDs.contains(value)) {
                            return 0;
                        }
                    }
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            } else {

                if (value.equals(mapParamValue)) return 0;

                // check hierarchy thingy here
                List<String> mapParamMultiValues = mapParam.get(key);

                if (mapParamMultiValues.contains(value)) {
                    return mapParamMultiValues.indexOf(value);
                }
            }
        }

        throw new CacheException("Not matched");
    }

    private void preQueryProcess(ConditionRecordConfig config, MultiValueMap<String, String> mapParam) {

        for (ConditionFieldWrapper wrapper : config.getConditionFieldWrappers()) {

            if (wrapper.getConditionFieldConfig().getType() == ConditionFieldConfig.ConditionFieldType.ENTITY
                    && "com.acceval.masterdata.model.Product".indexOf(wrapper.getConditionFieldConfig().getEntityClass()) != -1) {
                if (mapParam.getFirst(wrapper.getConditionFieldConfig().getCode()) != null) {

                    Long productID = Long.valueOf(mapParam.getFirst(wrapper.getConditionFieldConfig().getCode()));
                    List<String> parentIDs = masterDataCache.getProductParentToTop(productID);

                    mapParam.addAll(wrapper.getConditionFieldConfig().getCode(), parentIDs);

//                    while (true) {
//                        try {
//                            Product parentProduct = this.productService.findById(parentId);
//                            if (parentProduct.getLevelNumber() == 1) {
//                                break;
//                            }
//                            parentId = parentProduct.getParentProductId();
//                            mapParam.add(wrapper.getConditionFieldConfig().getCode(), parentId.toString());
//                        } catch (ObjectNotFoundException e) {
//                            e.printStackTrace();
//                        }
//                    }
                }
            }
            if (wrapper.getConditionFieldConfig().getType() == ConditionFieldConfig.ConditionFieldType.ENTITY
                    && "com.acceval.masterdata.model.Region".indexOf(wrapper.getConditionFieldConfig().getEntityClass()) != -1
                    && mapParam.getFirst(wrapper.getConditionFieldConfig().getCode()) == null
                    && mapParam.getFirst("COUNTRY") != null) {
                Long countryId = Long.valueOf(mapParam.getFirst("COUNTRY"));
                List<String> regions = masterDataCache.getRegionsByCountry(countryId);
                mapParam.addAll(wrapper.getConditionFieldConfig().getCode(), regions);

//                List<Region> regions = this.regionService.getRegionsByCountryID(countryId);
//                for (Region region : regions) {
//                    mapParam.add(wrapper.getConditionFieldConfig().getCode(),
//                            String.valueOf(region.getLocationID()));
//                }

            }
            if (wrapper.getConditionFieldConfig().getType() == ConditionFieldConfig.ConditionFieldType.REGIONCOUNTRY
                    && mapParam.getFirst("COUNTRY") != null) {

                Long countryId = Long.valueOf(mapParam.getFirst("COUNTRY"));
                mapParam.add(wrapper.getConditionFieldConfig().getCode(),
                        String.valueOf(countryId));
                List<String> regions = masterDataCache.getRegionsByCountry(countryId);
                mapParam.addAll(wrapper.getConditionFieldConfig().getCode(), regions);

//                List<Region> regions = this.regionService.getRegionsByCountryID(countryId);
//                for (Region region : regions) {
//                    mapParam.add(wrapper.getConditionFieldConfig().getCode(),
//                            String.valueOf(region.getLocationID()));
//                }

            }
        }
    }


    private boolean hasMandatoryParams(ConditionRecordConfig recordConfig, MultiValueMap<String, String> mapParam) {
        for (ConditionFieldWrapper wrapper : recordConfig.getConditionFieldWrappers()) {
            if (wrapper.isMandatory()) {
                ConditionFieldConfig fieldConfig = wrapper.getConditionFieldConfig();
                if (!mapParam.containsKey(fieldConfig.getCode())) {
                    return false;
                }
            }
        }
        return true;
    }
    private LocalDateTime getLocalDateTime(String date) {
        if (StringUtils.length(date) > 0 && StringUtils.length(date) <= 10) {
            return LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy")).atStartOfDay();
        } else if (StringUtils.length(date) > 10) {
            return LocalDateTime.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
        }

        return null;
    }

    class TemporalResult {
        int score;
        ConditionRecordCacheHelper helper;
    }

    public String test(String conditionRecordCode, MultiValueMap<String, String> mapParam) {

        StringBuffer buffer = new StringBuffer();
        ConditionRecordConfig config = conditionRecordCache.getConditionRecordConfig(conditionRecordCode);

        ConditionRecord cr = singleResultConditionRecord(config, mapParam);

        if (cr == null) {
            buffer.append("Condition Record Retriever : No condition record found.");
            buffer.append("\n");
        } else {
            buffer.append("Condition Record Retriever : Found " + cr.getId() + " with value " + printValue(cr) + ".");
            buffer.append("\n");
        }

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
