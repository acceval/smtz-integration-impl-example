package com.acceval.core.pricing;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acceval.core.MicroServiceUtilException;
import com.acceval.core.model.VariableContext;
import com.acceval.core.pricing.so.BaseVCMapping;
import com.acceval.core.pricing.so.ModelCompResult;
import com.acceval.core.pricing.so.RebateConfig;
import com.acceval.core.pricing.so.SalesItemCommitment;
import com.acceval.core.util.ClassUtil;
import com.acceval.core.util.JsonNodeUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class VariableContextUtil {
	private static final Logger logger = LoggerFactory.getLogger(VariableContextUtil.class);

	public static void objectToContext(Object object, DefaultContextMapping contextMapping,
			VariableContext variableContext) {
		List<BaseVCMapping> lstMapping = contextMapping.getDefaultContextMapping();
		for (BaseVCMapping vcmapping : lstMapping) {
			objectToContext(object, vcmapping, variableContext);
		}
	}

	public static void objectToContext(Object object, BaseVCMapping mapping, VariableContext variableContext) {
		try {
			Object value = null;
			if (StringUtils.isNotBlank(mapping.getPropertyName())) {
				// base on String property name
				value = ClassUtil.getProperty(object, mapping.getPropertyName());
				if (value == null && mapping.getDefaultValue() != null) {
					variableContext.setVariable(mapping.getKey(), mapping.getDefaultValue());
				} else if (value != null) {
					variableContext.setVariable(mapping.getKey(), value);
				}
			} else if (mapping.getCustomProperty() != null) {
				// custom property
				try {
					value = mapping.getCustomProperty().invoke(object);
					if (value != null) {
						variableContext.setVariable(mapping.getKey(), value);
					}
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					logger.error(e.getMessage(), e);
				}
			}

		} catch (MicroServiceUtilException e) {
			logger.error(e.getMessage(), e);
		}
	}

	public static void populateValueToObj(Object targetObj, DefaultContextMapping contextMapping,
			VariableContext variableContext) {
		List<BaseVCMapping> lstMapping = contextMapping.getDefaultContextMapping();
		for (BaseVCMapping vcmapping : lstMapping) {
			populateValueToObj(targetObj, vcmapping, variableContext);
		}
	}

	public static void populateValueToObj(Object targetObj, BaseVCMapping mapping, VariableContext variableContext) {

		if (!mapping.isContextToObj()) {
			return;
		}

		try {
			// not support nested property populate yet
			if (StringUtils.isBlank(mapping.getPropertyName()) || mapping.getPropertyName().indexOf(".") > -1) {
				return;
			}

			Object value = variableContext.getVariable(mapping.getKey());
			if (value == null && !variableContext.containsVariable(mapping.getKey())) {
				return;
			}

			String clzName = targetObj.getClass().getName();
			boolean isLocalDate = false;
			try {
				isLocalDate = LocalDate.class
						.isAssignableFrom(ClassUtil.getPropertyClass(clzName, mapping.getPropertyName()));
			} catch (SecurityException | NoSuchMethodException e) {
				logger.error(e.getMessage(), e);
			}
			if (ClassUtil.isDateProperty(clzName, mapping.getPropertyName()) || isLocalDate) {
				value = variableContext.getVariableAsDate(mapping.getKey());
			} else if (ClassUtil.isIntegerProperty(clzName, mapping.getPropertyName())) {
				value = variableContext.getVariableAsInteger(mapping.getKey());
			} else if (ClassUtil.isDoubleProperty(clzName, mapping.getPropertyName())) {
				value = variableContext.getVariableAsDouble(mapping.getKey());
			} else if (ClassUtil.isLongProperty(clzName, mapping.getPropertyName())) {
				value = variableContext.getVariableAsLong(mapping.getKey());
			} else if (ClassUtil.isStringProperty(clzName, mapping.getPropertyName())) {
				value = variableContext.getVariableAsString(mapping.getKey());
			}

			ClassUtil.setProperty(targetObj, mapping.getPropertyName(), value);
		} catch (MicroServiceUtilException e) {
			logger.error(e.getMessage());
		}
	}

	/**
	 * waterfall result to Object
	 */
	public static void modelComponentResultToObject(List<BaseVCMapping> lstDCM, Object target,
			List<ModelCompResult> modelCompResults) {
		for (ModelCompResult result : modelCompResults) {
			for (BaseVCMapping vcm : lstDCM) {
				if (result.getComponent() != null && StringUtils.equals(vcm.getKey(), result.getComponent().getCode())
						&& vcm.isContextToObj()) {
					try {
						ClassUtil.setProperty(target, vcm.getPropertyName(), result.getModelComponentValue());
					} catch (MicroServiceUtilException e) {
						logger.error(e.getMessage(), e);
					}
				}
			}
		}
	}

	public static void convertInputterObject(VariableContext variableContext) {
		if (variableContext.getVariableMap().containsKey(VariableContext.REBATE_CONFIG)
				&& variableContext.getVariable(VariableContext.REBATE_CONFIG) != null) {
			Object value = variableContext.getVariable(VariableContext.REBATE_CONFIG);
			RebateConfig rebateConfig = null;
			if (value instanceof String) {
				JsonNode rebateNode = getJsonNodeByCode(variableContext, VariableContext.REBATE_CONFIG);
				if (rebateNode != null) {
					rebateConfig = (RebateConfig) JsonNodeUtil.convertJsonNode(rebateNode, RebateConfig.class);
				}
			} else {
				rebateConfig = (RebateConfig) value;
			}
			variableContext.setVariable(VariableContext.REBATE_CONFIG, rebateConfig);
		}

		if (variableContext.getVariableMap().containsKey(VariableContext.COMMITMENT)
				&& variableContext.getVariable(VariableContext.COMMITMENT) != null) {
			Object value = variableContext.getVariable(VariableContext.COMMITMENT);
			SalesItemCommitment commitment = null;
			if (value instanceof String) {
				JsonNode commitmentNode = getJsonNodeByCode(variableContext, VariableContext.COMMITMENT);
				if (commitmentNode != null) {
					commitment = (SalesItemCommitment) JsonNodeUtil.convertJsonNode(commitmentNode,
							SalesItemCommitment.class);
				}
			} else {
				commitment = (SalesItemCommitment) value;
			}
			variableContext.setVariable(VariableContext.COMMITMENT, commitment);
			variableContext.setVariable(VariableContext.QUANTITY, commitment.getTotalQuantity());
		}
	}

	public static JsonNode getJsonNodeByCode(VariableContext context, String code) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.registerModule(new JavaTimeModule());
			return mapper.readTree(context.getVariableAsString(code));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
