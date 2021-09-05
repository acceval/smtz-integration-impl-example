package com.acceval.core.cache.processor;

import com.acceval.core.cache.impl.MasterDataCache;
import com.acceval.core.cache.model.ConditionEvaluationResult;
import com.acceval.core.cache.model.GlobalUomConversion;
import com.acceval.core.cache.model.SkuProductAltUom;
import com.acceval.core.cache.model.Uom;
import com.acceval.core.microservice.ObjectNotFoundException;
import com.acceval.core.model.VariableContext;
import com.acceval.core.repository.Criteria;
import com.acceval.core.repository.Criterion;
import com.acceval.core.repository.Criterion.RestrictionType;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("cacheUomConversionUtil")
@ConditionalOnProperty(name = "microservice.cache", havingValue = "true")
public class UomConversionUtil {

    @Autowired
    private MasterDataCache masterDataCache;

    @Autowired
    private ConditionRecordCacheProcessor conditionRecordCacheProcessor;

//	@Autowired
//	private ProductService productService;
//	@Autowired
//	private UomService uomService;
//	@Autowired
//	private SkuProductAltUomService skuProductAltUomService;
//	@Autowired
//	private GlobalUomConversionService globalUomConversionService;
//	@Autowired
//    @Lazy
//	private ConditionRecordService conditionRecordService;
	
	private static Logger log = LoggerFactory.getLogger(UomConversionUtil.class);

	public double getConvertedQuantity(long productId, long fromUomId, long toUomId, double quantity, VariableContext context) throws ObjectNotFoundException {
		Collection<Uom> fullUomList = this.masterDataCache.getAllUoms();
		double convertedQuantity = 0d;
		Collection result;
		double conversionFactor = 0;
		if (fromUomId == toUomId)
			return quantity; // no conversion
		else {
			if (!isUomIdValid(fromUomId)) { // validate fromUomId
				log.debug("Search SKU Base Uom");
				fromUomId = getSkuBaseUomId(productId);
				log.debug("base uom is:" + fromUomId);
			}
			if (fromUomId == -1L) {
				log.info("UomConversionUtil", "FromUOM is not valid. Also no BASEUOM found from TB_PRODUCT");
				return convertedQuantity;
			} else {
				log.debug("Search Sku Product Alternate UOM");
				log.info("UomConversionUtil", "Search Sku Product Alternate UOM");
				conversionFactor = recursiveConverter(fullUomList, productId, fromUomId, toUomId, context);
				if (conversionFactor != 0) {
					convertedQuantity = quantity * conversionFactor;

				}
			}
		}

		return convertedQuantity;
	}

	private double getConvertedQuantity(long productId, String fromUomCode, String toUomCode, double quantity, VariableContext context) throws ObjectNotFoundException {
		long fromUomID = this.masterDataCache.getUomByCode(fromUomCode).getUomID();
		long toUomID = this.masterDataCache.getUomByCode(toUomCode).getUomID();

		return getConvertedQuantity(productId, fromUomID, toUomID, quantity, context);
	}

	/**
	 * @deprecated use this method only if you don't have product, this method
	 *             will only get Global UOM Convertion rate
	 */
	@Deprecated
	public double getConvertedQuantity(long fromUomId, long toUomId, double quantity, VariableContext context) {
		double convertedQuantity = 0d;
		Collection<GlobalUomConversion> result;

		if (fromUomId == toUomId) {
			// they're the same uom, so just return the same value
			return quantity;
		}

		result = getGlobalUomConversion(fromUomId, toUomId, context);
		if (result.isEmpty() == false) {
			log.info("UomConversionUtil", "Multiplication Logic");
			log.debug("Global UOM Found.");
            GlobalUomConversion globalUomConversionObj = result.iterator().next();
			convertedQuantity = quantity * globalUomConversionObj.getConversionFactor().doubleValue();
		} else {
			log.info("UomConversionUtil", "Division Logic");
			log.info("UomConversionUtil", "Reversal Search");
			result = getGlobalUomConversion(toUomId, fromUomId, context);
			if (result.isEmpty() == false) {
				log.debug("Global Reverse UOM Logic Found.");
                GlobalUomConversion globalUomConversionObj = result.iterator().next();
				convertedQuantity = quantity / globalUomConversionObj.getConversionFactor().doubleValue();
			} else {
				log.debug("No available sku specific and global conversion found.");
			}
		}

		return convertedQuantity;
	}

	private Collection<SkuProductAltUom> getSkuProductAltUom(long productId, long fromUomId, long toUomId) {
		return masterDataCache.getSKUAlternateUom(productId, fromUomId, toUomId);
	}

    private Collection<GlobalUomConversion> getGlobalUomConversion(long fromUomId, long toUomId, VariableContext context) {
		/** find from condition record*/
		VariableContext cloneContext = context.clone();
		cloneContext.setVariable("FROM_UOM", fromUomId);
		cloneContext.setVariable("TO_UOM", toUomId);
		
		Long divisionId = null;
		if (context != null && context.getVariableMap() != null) {
			divisionId = context.getVariableAsLong(VariableContext.SALES_DIVISION);
			
			ConditionEvaluationResult result = conditionRecordCacheProcessor.evaluateConditionTable("GLOBAL_UOM_CONVERSION", cloneContext);
				
			if (result != null && result.getConditionRecord() != null) {
                GlobalUomConversion uomConversion = new GlobalUomConversion();
                uomConversion.setConversionFactor(result.getNumericValue());

				List<GlobalUomConversion> lstUom = new ArrayList<>();
				lstUom.add(uomConversion);
				return lstUom;
			}
		}
		
		List<GlobalUomConversion> res = null;

		if (divisionId != null && divisionId != 0l) {
            res = masterDataCache.getGlobalUomConversion(fromUomId, toUomId, divisionId);
		}
		if (res == null || CollectionUtils.isEmpty(res)) {
            res = masterDataCache.getGlobalUomConversion(fromUomId, toUomId);
		}
		if (res != null && CollectionUtils.isNotEmpty(res)) {
			return res;
		}
		return Collections.EMPTY_LIST;
	}

    private boolean isUomIdValid(long uomId) {
		Collection lst = masterDataCache.getAllUoms();
		for (Object obj : lst) {
			Uom uom = (Uom) obj;
			if (uom.getUomID() == uomId) {
				return true;
			}
		}

		return false;
	}

    private long getSkuBaseUomId(long productID) throws ObjectNotFoundException {
	    Uom uom = masterDataCache.getProductBaseUom(productID);

		if (uom != null) return uom.getUomID();
		return 0;
	}

    private Map<String, Double> searchValues(long productID, long fromUomID, long toUomID, VariableContext context) {
		Map<String, Double> conversionRates = new HashMap<String, Double>();
		double conversionFactor = 0;
		String operator = "";
		Collection<SkuProductAltUom> result = getSkuProductAltUom(productID, fromUomID, toUomID);
		if (!result.isEmpty()) {
            SkuProductAltUom skuProductAltUom = result.iterator().next();
			conversionFactor = skuProductAltUom.getConversionFactor().doubleValue();
			operator = "*";
		} else {
			result = getSkuProductAltUom(productID, toUomID, fromUomID);
			if (!result.isEmpty()) {
                SkuProductAltUom skuProductAltUom = result.iterator().next();
				conversionFactor = skuProductAltUom.getConversionFactor().doubleValue();
				operator = "/";
			} else {
                Collection<GlobalUomConversion> globalUomConversions = getGlobalUomConversion(fromUomID, toUomID, context);

				if (!globalUomConversions.isEmpty()) {
                    GlobalUomConversion globalUomConversionObj = globalUomConversions.iterator().next();

					conversionFactor = globalUomConversionObj.getConversionFactor().doubleValue();
					operator = "*";

//					if (StringUtils.isNotBlank(globalUomConversionObj.getConverterClass())) {
//						String className = globalUomConversionObj.getConverterClass();
//						// UOMConverterRequest rq = new UOMConverterRequest();
//						//
//						// rq.setContext(null);
//						// rq.setGlobalUomConversion(globalUomConversionObj);
//						// rq.setValue(conversionFactor);
//						//
//						// Object invoker = ClassUtil.getClassObject(className);
//						//
//						// try {
//						// UOMConverterRespond respond = (UOMConverterRespond)
//						// ClassUtil.invokeMethod(invoker, "convert",
//						// new Class[] { UOMConverterRequest.class }, new
//						// Object[] { rq });
//						//
//						// conversionFactor = respond.getValue();
//						//
//						// } catch (ApplicationException e) {
//						// e.printStackTrace();
//						// }
//
//					}
				} else {
                    globalUomConversions = getGlobalUomConversion(toUomID, fromUomID, context);

                    if (!result.isEmpty()) {
                        GlobalUomConversion globalUomConversionObj = globalUomConversions.iterator().next();

						conversionFactor = globalUomConversionObj.getConversionFactor().doubleValue();
						operator = "/";

//						if (StringUtils.isNotBlank(globalUomConversionObj.getConverterClass())) {
//							String className = globalUomConversionObj.getConverterClass();
//							// UOMConverterRequest rq = new
//							// UOMConverterRequest();
//							//
//							// rq.setContext(null);
//							// rq.setGlobalUomConversion(globalUomConversionObj);
//							// rq.setValue(conversionFactor);
//							//
//							// Object invoker =
//							// ClassUtil.getClassObject(className);
//							//
//							// try {
//							// UOMConverterRespond respond =
//							// (UOMConverterRespond)
//							// ClassUtil.invokeMethod(invoker, "convert",
//							// new Class[] { UOMConverterRequest.class }, new
//							// Object[] { rq });
//							//
//							// conversionFactor = respond.getValue();
//							//
//							// } catch (ApplicationException e) {
//							// e.printStackTrace();
//							// }
//
//						}
					}
				}
			}
		}

		conversionRates.put(operator, conversionFactor);
		return conversionRates;
	}

    private double recursiveConverter(Collection<Uom> fullUomList, long productID, long fromUomID, long toUomID, VariableContext context) {
		Map<String, Double> conversionRates = searchValues(productID, fromUomID, toUomID, context);
		double accumulativeConversionFactor = 0;

		for (String string : conversionRates.keySet()) {
			if ("/".equals(string))
				accumulativeConversionFactor = 1 / conversionRates.get(string);
			else if ("*".equals(string)) accumulativeConversionFactor = conversionRates.get(string);

			if (accumulativeConversionFactor != 0) return accumulativeConversionFactor;
		}

		List<Uom> removeLoopedUom = new ArrayList<Uom>();
		removeLoopedUom.addAll(fullUomList);

		for (Uom uom : fullUomList) {
			removeLoopedUom.remove(uom);
			double found = 0;
			conversionRates = searchValues(productID, fromUomID, uom.getUomID(), context);

			for (String string : conversionRates.keySet()) {
				if ("/".equals(string))
					found = 1 / conversionRates.get(string);
				else if ("*".equals(string)) found = conversionRates.get(string);
				break;
			}

			if (found != 0) {
				accumulativeConversionFactor = found;

				double finalValue = 0;

				conversionRates = searchValues(productID, uom.getUomID(), toUomID, context);

				for (String string : conversionRates.keySet()) {
					if ("/".equals(string))
						finalValue = 1 / conversionRates.get(string);
					else if ("*".equals(string)) finalValue = conversionRates.get(string);
					break;
				}

				if (finalValue != 0) {
					accumulativeConversionFactor = accumulativeConversionFactor * finalValue;
					break;
				}
			}

			accumulativeConversionFactor = 0;
		}

		return accumulativeConversionFactor;

	}

}
