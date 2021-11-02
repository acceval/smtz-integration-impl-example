package com.acceval.core.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.acceval.core.repository.Criterion;
import com.acceval.core.util.TimeZoneUtil;

public class VariableContext implements Serializable, Cloneable {

	private static final long serialVersionUID = 3457134321135281269L;

	public static final String ESTIMATED_BILING_DATE = "ESTIMATED_BILING_DATE";
	public static final String BILING_DATE = "BILING_DATE";
	public static final String DELIVERY_DATE = "DELIVERY_DATE";

	public static final String CURRENCY_CODE = "CURRENCY_CODE";
	public static final String UOM_CODE = "UOM_CODE";
	public static final String QUANTITY = "QUANTITY";
	public static final String PRODUCT = "PRODUCT";
	public static final String GRADE = "GRADE";
	public static final String PERCENTAGE_APPLY_INPUT_PARAM = "PERCENTAGE_APPLY_INPUT_PARAM";
	public static final String VALID_FROM = "VALID_FROM";
	public static final String VALID_TO = "VALID_TO";
	public static final String CURRENCY_EXCHANGE_RATE_TYPE = "CURRENCY_EXCHANGE_RATE_TYPE";
	public static final String CURRENCY_EXCHANGE_RATE_TYPE_CTS = "CURRENCY_EXCHANGE_RATE_TYPE_CTS";
	public static final String COMPANY_ID = "COMPANY_ID";
	public static final String COMPANY_CODE = "COMPANY_CODE";
	public static final String DEFAULT_DECIMAL = "DEFAULT_DECIMAL";
	public static final String SALES_HEADER_TOTAL_VOLUME = "SALESHEADERTOTALVOLUME";
	public final static String REGION = "REGION";
	public final static String NEGOTIATION_COCKPIT_TYPE = "NEGOTIATIONCOCKPITTYPE";
	public final static String PLANT = "PLANT";
	public final static String BOM = "BOM";
	public final static String FORMULA = "FORMULA";
	public final static String RULE_FORMULA = "RULE_FORMULA";
	public final static String RULE_FORMULA_2 = "RULE_FORMULA_2";
	public final static String DOCUMENT_CATEGORY = "DOCUMENTCATEGORY";
	public final static String PACKAGING = "PACKAGING";
	public final static String PAYMENT_TERM = "PAYMENTTERM";
	public final static String INCOTERM = "INCOTERM";
	public static final String CONTEXTKEY_PALLETIZE = "PALLETIZE";
	public static final String CONTEXTKEY_NORMALASING = "NORMALASING";
	public final static String BYPASS_INCOTERM_DEPENDENCY = "BYPASSINCOTERMDEPENDENCY";
	public static final String SALES_DIVISION = "SALES_DIVISION";
	public static final String REFERENCE_DOC_NUMBER = "REFERENCE_DOC_NUMBER";
	public static final String SALES_ITEM_NUMBER = "SALES_ITEM_NUMBER";
	public static final String FINAL_ALPHA = "FINAL_ALPHA";
	public static final String ALPHA_ADJUSTMENT = "ALPHA_ADJUSTMENT";
	public static final String ALPHA_2 = "ALPHA_2";
	public static final String SALES_OFFICE = "SALES_OFFICE";
	public static final String MANDATE_ALPHA = "ALPHA";
	public static final String PRICING_TECHNIQUE_CODE = "PRICING_TECHNIQUE_CODE";
	public static final String PRICE_MODEL_CODE = "PRICE_MODEL_CODE";
	public static final String MARGIN_MODEL_CODE = "MARGIN_MODEL_CODE";
	public static final String OVERWRITE_ALPHA_FLAG = "OVERWRITE_ALPHA_FLAG";
	public static final String OVERWRITE_EXECUTION_ALPHA_FLAG = "OVERWRITE_EXECUTION_ALPHA_FLAG";
	public static final String OVERWRITE_EXCHANGE_RATE = "OVERWRITE_EXCHANGE_RATE";
	public static final String OVERWRITE_EXCHANGE_RATE_CTS = "OVERWRITE_EXCHANGE_RATE_CTS";
	public static final String OVERWRITE_CURRENCY_FROM = "OVERWRITE_CURRENCY_FROM";
	public static final String OVERWRITE_CURRENCY_TO = "OVERWRITE_CURRENCY_TO";

	public static final String DEFAULT_DATE_FORMAT = Criterion.DEFAULT_DATE_FORMAT;
	public static final String DEFAULT_DATE_TIME_FORMAT = Criterion.DEFAULT_DATE_TIME_FORMAT;

	public static final String TRIGGER_FROM_CPL = "TRIGGER_FROM_CPL"; // indicate sys flow from CPL calculation

	public static final String CONTEXTKEY_EXCHANGE_RATE_OVERRIDE = "EXCHANGERATEOVERRIDE";
	public static final String CONTEXTKEY_EXCHANGE_RATE_FROM_OVERRIDE = "EXCHANGERATEFROMOVERRIDE";
	public static final String CONTEXTKEY_EXCHANGE_RATE_TO_OVERRIDE = "EXCHANGERATETOOVERRIDE";
	public final static String CONTEXTKEY_MARKET_STRUCTURE_REGION = "MARKET_STRUCTURE_REGION";
	public static final String CONTEXTCONFIGCODE_PRICESTATUS = "PRICESTATUS";

	// normalisation
	public static final String NORMALISED_PACKAGING = "NORMALISED_PACKAGING";
	public static final String NORMALISED_PAYMENT_TERM = "NORMALISED_PAYMENT_TERM";
	public static final String NORMALISED_INCOTERM = "NORMALISED_INCOTERM";
	public static final String MARKET_STRUCTURE_REGION = "MARKET_STRUCTURE_REGION";
	public static final String REFERENCE_MARKET_PRICE = "REFERENCE_MARKET_PRICE";

	// formula exchange rate
	public static final String FORMULA_EXCHANGE_RATE = "formulaExchangeRate";
	public static final String FORMULA_EXCHANGE_RATE_TO_MYR = "formulaExchangeRateToMYR";
	public static final String FORMULA_EXCHANGE_RATE_TO_THB = "formulaExchangeRateToTHB";
	public static final String FORMULA_EXCHANGE_RATE_FOR_FINALALPHA = "formulaExchangeRateForFinalAlpha";
	public static final String FORMULA_EXCHANGE_RATE_FOR_ALL_CURRENCIES = "formulaExchangeRateForAllCurrencies";

	// Pricing Technique Condition Table Code
	// public static final String PRICETECH_DECIDER = "PRICING_TECHNIQUE_DECIDER";

	// Various Decider
	public static final String FLOOR_PRICE_TECHNIQUE_DECIDER = "FLOOR_PRICE_TECHNIQUE_DECIDER";
	public static final String NORMALISATION_TECHNIQUE_DECIDER = "NORMALISATION_TECHNIQUE_DECIDER";
	public static final String CEV_TECHNIQUE_DECIDER = "CEV_TECHNIQUE_DECIDER";

	// Various Model
	public static final String PREFCHEM_FLOOR_PRICE_SETTING_MODEL = "PREFCHEM_FLOOR_PRICE_SETTING_MODEL";

	// Condition Table Code
	public static final String CT_PRODUCT_BASE_PRICE = "PRODUCT_BASE_PRICE";
	public static final String CT_STD_VALUE_CONDITION = "STD_VALUE_CONDITION";
	public static final String CT_PRODUCT_LIST_PRICE_BY_COUNTRY = "GRADE_LIST_PRICE_BY_COUNTRY";
	public static final String CT_APPLICATION_ADJUSTMENT = "APPLICATION_ADJUSTMENT";
	public static final String CT_NORMALISED_PRICE = "STANDARD_FLOOR_PRICE";
	public static final String CT_FLOOR_PRICE_BASELINE = "FLOOR_PRICE_BASELINE";
	public static final String CT_NORMALISED_ALPHA = "STANDARD_FLOOR_ALPHA";
	public static final String CT_RULECONTRIBUTIONMARGIN = "CONTRIBUTION_MARGIN_RULES";
	public static final String CT_NORMALISED_PRICE_FOB = "NORMALISED_PRICE_FOB";
	public static final String CT_NEGOTIATED_DISCOUNT_CONTROL = "NEGOTIATED_DISCOUNT_CONTROL";
	public static final String CT_EXCHANGE_RATE_DECIDER = "EXCHANGE_RATE_DECIDER";
	public static final String CT_SWAPINVOICE = "SWAP_INVOICE";
	public static final String CT_COSTOFCREDITTERM = "CREDIT_TERM_RATE";
	public static final String CT_HIST_REBATE = "HIST_REBATE_COIM";
	public static final String CT_PACKAGINGCOST = "PACKAGING_COST";
	public static final String CT_COUNTRYSTANDARDDEEMEDFREIGHT = "STANDARD_DEEMED_FREIGHT";
	public static final String CT_SPOT_LOA_ASSIGNMENT = "SPOT_LOA_ASSIGNMENT";
	public static final String CT_PRICING_MANDATE = "LONG_TERM_DEAL_PRICING_MANDATE";
	public static final String CT_APPROVER_DELEGATION = "APPROVER_DELEGATION";
	public static final String CT_TENDER_PRICE_FORMULA = "TENDER_PRICE_FORMULA";
	public static final String CT_TENDER_PRICE_FIXED_FIRM = "TENDER_PRICE_FIXED_FIRM";
	
	

	// Condition Table Fields / context key
	public static final String CONDFIELDCODE_NORMALISE_FOR_FLOOR_PRICE = "NORMALISE_FOR_FLOOR_PRICE";
	public static final String CONDFIELDCODE_INVOICE_DOCUMENT = "INVOICE_DOCUMENT";
	public static final String CONDFIELDCODE_SWAP_INVOICE = "SWAP_INVOICE";
	public static final String CONDFIELDCODE_CEV_FOR_FLOOR_PRICE = "CEV_FOR_FLOOR_PRICE";
	public final static String CONDFIELDCODE_DOCUMENT_ID = "DOCUMENT_ID";
	public final static String CONDFIELDCODE_SOLD_TO_CUSTOMER = "SOLD_TO_CUSTOMER";
	public final static String CONDFIELDCODE_GRADE = "GRADE";
	public static final String CONDFIELDCODE_DOMESTIC = "DOMESTIC";
	public static final String CONDFIELDCODE_ACCUMULATIVE_AMOUNT_IN_RM = "ACCUMULATIVE_AMOUNT_IN_RM";
	public static final String CONDFIELDCODE_TASK_NAME = "TASK_NAME";
	public static final String CONDFIELDCODE_USER_ACCOUNT = "USER_ACCOUNT";

	// Condition Table Values / context key
	public static final String CONDVALUECODE_PRODUCT_BASE_PRICE_INCO = "INCOTERM";
	public static final String CONDVALUECODE_PRODUCT_BASE_PACKAGING = "PACKAGING";
	public static final String CONDVALUECODE_PRODUCT_BASE_REGION = "REGION";
	public static final String CONDVALUECODE_PRODUCT_BASE_PAYMENT_TERM = "PAYMENT_TERM";
	public static final String CONDVALUECODE_REFERENCEAPPLICATION = "REFERENCE_APPLICATION";
	public static final String CONDVALUECODE_ACCOUNT = "ACCOUNT";
	public static final String CONDVALUECODE_DESIGNATION = "DESIGNATION";
	public static final String CONDVALUECODE_NORMALISED_PRICE = "FLOOR_PRICE";
	public static final String CONDVALUECODE_NORMALISED_ALPHA = "FLOOR_ALPHA";
	public static final String CONDVALUECODE_LIMIT_AMOUNT = "LIMIT_AMOUNT";
	public static final String CONDVALUECODE_FROM_CURRENCY = "FROM_CURRENCY";
	public static final String CONDVALUECODE_TO_CURRENCY = "TO_CURRENCY";
	public static final String CONDVALUECODE_DGCWC_DG = "DG";
	public static final String CONDVALUECODE_MARGIN = "MARGIN";

	// Deal Mgt Object
	public static final String OBJ_SALES_DOC = "salesDoc";
	public static final String OBJ_SALES_ITEM = "salesItem";
	public static final String OBJ_SALES_SCENARIO = "salesScenario";
	public static final String REBATE_CONFIG = "REBATE_CONFIG";
	public static final String COMMITMENT = "COMMITMENT";

	// Object unique keys
	public static final String CFR_INCOTERM = "CFR";
	public final static String EXW_INCOTERM = "EXW";
	public final static String FOB_INCOTERM = "FOB";
	public final static String CIF_INCOTERM = "CIF";
	public final static String CIP_INCOTERM = "CIP";
	public final static String DAP_INCOTERM = "DAP";
	public final static String DDP_INCOTERM = "DDP";
	public final static String FCA_INCOTERM = "FCA";
	public final static String DAT_INCOTERM = "DAT";

	public static final String THB_CURRENCY = "THB";
	public static final String MYR_CURRENCY = "MYR";
	public static final String USD_CURRENCY = "USD";

	public static final String PACK_CODE_BULK = "PC01";
	public static final String MARINE_ISO = "MI";
	public static final String MARINE_PACK = "MP";

	// Workflow Violation
	public static final String WFL_VIO_NORFLOORPRICE = "WFLVIONORFLOORPRICE";
	public static final String WFL_VIO_CM = "WFLVIOCM";
	public static final String WFL_VIO_EXCHANGE_RATE = "WFLEXCHANGERATE";
	public static final String WFL_VIO_REPRICING = "[Repricing]";
	public static final String NO_WFL_VIO = "NOVIOLATION";
	public static final String IS_QUOTATION = "IS_QUOTATION";
	public static final String IS_PENDING_STATE = "IS_PENDING_STATE";
	public static final String WFL_VIO_NOR_FLOOR_ALPHA = "WFLVIONORFLOORALPHA";
	public static final String WFL_NO_APPROVAL = "NO_APPROVAL";
	public static final String WFL_VIO_LOA_SM_VERIFY = "WFLLOASMVERIFY";
	public static final String IS_NQ_CONTRACT_EXECUTION = "IS_NQ_CONTRACT_EXECUTION";

	// Workflow Context
	public static final String FORMULAPRICINGADJ = "FORMULAPRICINGADJ";

	// Negotiation Cockpit Component Code
	public static final String COMPONENT_CM_MARKET = "CM_MARKET";
	public static final String COMPONENT_NEGOTIATION_DISCOUNT = "NEGOTIATED_DISCOUNT";
	public final static String COMPONENT_FORMULA = "FORMULA";
	public final static String COMPONENT_FORMULA_DESCRIPTION = "FORMULA_DESCRIPTION";
	public final static String COMPONENT_PAYMENT_TERM = "PAYMENT_TERM";
	public static final String COMPONENT_SHIPPINGCONDITION = "SHIPPING_CONDITION";
	public final static String COMPONENT_ORIGIN_PORT = "ORIGIN_PORT";	
	public final static String COMPONENT_INCOTERM = "INCOTERM";
	public static final String COMPONENT_COMPOSITEOCEANFREIGHT = "COMPOSITE_OCEAN_FREIGHT";
	public static final String COMPONENT_CUSTOMDUTIES = "CUSTOM_DUTIES";
	public static final String COMPONENT_EXWTOFOBADJUSTMENT = "EXW_TO_FOB_ADJUSTMENT";
	public static final String COMPONENT_INLANDHAULAGECOST = "INLAND_HAULAGE_COST";
	public static final String COMPONENT_LANDINSURANCE = "LAND_INSURANCE";
	public static final String COMPONENT_MARINEINSURANCE = "MARINE_INSURANCE";
	public static final String COMPONENT_OCEANFREIGHTCHARGE = "OCEAN_FREIGHT_CHARGE";
	public static final String COMPONENT_TRUCKINGCOSTW2C = "TRUCKING_COST_W2C";
	public static final String MARGICOMP_OCEANFREIGHTCOST_ACTUAL = "OCEAN_FREIGHT_COST_ACTUAL";
	public static final String MARGICOMP_TRUCKINGCOST_ACTUAL = "TRUCKING_COST_ACTUAL";
	public static final String COMPONENT_PREFCHEM_STANDARD_FLOOR_PRICE = "PREFCHEM_STANDARD_FLOOR_PRICE";
	public static final String COMPONENT_FINAL_EXECUTION_ALPHA = "FINAL_EXECUTION_ALPHA";
	public static final String COMPONENT_ALPHA_AS_PER_TERM_CONTRACT_AGREEMENT = "ALPHA_AS_PER_TERM_CONTRACT_AGREEMENT";
	public static final String BYPASS_MARKET_MISSINGDATE_DEPENDENCY = "BYPASS_MARKET_MISSINGDATE_DEPENDENCY";
	public static final String COMPONENT_CM_INTEGRATED = "CM_INTEGRATED";
	
	// Market Price Type
	public static final String HISTORICAL = "HISTORICAL";
	public static final String PROJECTION = "PROJECTION";

	// Business Unit
	public static final String METHANOL = "Methanol";
	public static final String ANF = "AnF";
	public static final String OGD = "OGD";
	public static final String POLYMER = "Polymer";

	public final static double DEFAULT_NOT_FOUND_NOR_FLOOR_PRICE_VALUE = 1000000;

	public static enum WFL_VIOLATE {
		CHANGED_PAYMENT_TERM("Violated Payment Term Credit Day"), DEAL_AMOUNT("Below Targeted Amount [?]"),
		WFL_NORMALISED_PRICE(
				"Violated Normalised Price Rule. Product: [?], Country: [?], Normalised Price [?], Targeted Normalised Floor Price: [?]"),
		WFL_NORMALISED_ALPHA(
				"Violated Alpha Rule. Line Item ?, Product: [?], Country: [?], Alpha [?], Targeted Alpha: [?]"),
		WFL_CONTRIBUTION_MARGIN(
				"Violated Contribution Margin (Legal Book) Rule. Product [?], Contribution Margin (Legal Book): [?], Targeted Contribution Margin: [?]"),
		FORMULA_PRICING_ITEM("Violated Contract LOA"), REVISED_CASE("Revised Case"),
		WFL_NEGOTIATED_DISCOUNT(
				"Violated Negotiated Discount Control. Product: [?], Negotiated Discount: [?], Target Negotiated Discount Control: [?]"),
		SPOT_LOA("Violated Spot LOA."), CONTRACT_LOA("Violated Contract LOA."),
		OVERRIDE_EXCHANGE_RATE("Overwrite Exchange Rate"),
		WFL_PRICING_MANDATE(
				"Violated Pricing Mandate Formula Rule. Line Item ?, Product: [?], Country: [?], Formula [?], Targeted Formula: [?]"),
		WFL_PRICING_MANDATE_FORMULA_2(" / Targeted Formula 2: [?]"), WFL_PRICING_MANDATE_VOLUME(
				"Violated Pricing Mandate [?] Rule, Line Item ? ? Exceeded. Requested Volume: [?], ? Remaining: [?]");

		private final String msg;

		public static List<String> getDealLvlViolateCodes() {
			List<String> lst = new ArrayList<String>();

			lst.add(FORMULA_PRICING_ITEM.toString());
			lst.add(WFL_NORMALISED_PRICE.toString());
			lst.add(WFL_CONTRIBUTION_MARGIN.toString());

			return lst;
		}

		WFL_VIOLATE(String msg) {
			this.msg = msg;
		}

		public String getCode() {
			return this.name().toString();
		}

		public String getMsg() {
			return msg;
		}

		public String getMsg(String[] args) {
			String returnMsg = new String(msg);
			if (args != null && args.length > 0) {
				for (String arg : args) {
					if (returnMsg.indexOf("?") > -1) {
						returnMsg = returnMsg.replaceFirst("[?]", arg);
					}
				}
			}
			return returnMsg;
		}
	};

	private Map<String, Object> variableMap = Collections.synchronizedMap(new HashMap<>());
	private Long companyId;
	private String companyCode;

	@Override
	public VariableContext clone() {
		try {
			VariableContext context = (VariableContext) super.clone();

			Map<String, Object> nMap = Collections.synchronizedMap(new HashMap<>());

			for (Iterator<String> itr = variableMap.keySet().iterator(); itr.hasNext();) {
				String key = itr.next();

				nMap.put(key, variableMap.get(key));
			}
			context.variableMap = nMap;

			return context;
		} catch (CloneNotSupportedException cnse) {
			throw new RuntimeException(cnse);
		}
	}

	public void setContext(VariableContext context) {
		if (context != null) {
			this.variableMap.putAll(context.getVariableMap());
		}
	}

	public void setVariable(String key, Object value) {
		
		if (variableMap == null) {
			variableMap = Collections.synchronizedMap(new HashMap<>());
		}
		if (value instanceof LocalDate) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT);
			variableMap.put(key, ((LocalDate) value).format(formatter));
		} else if (value instanceof LocalDateTime) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT);
			variableMap.put(key, ((LocalDateTime) value).format(formatter));
		} else {
			variableMap.put(key, value);
		}
	}

	public void setVariable(String key, double value) {
		setVariable(key, new Double(value));
	}

	public void setVariable(String key, long value) {
		setVariable(key, new Long(value));
	}

	public void removeVariable(String key) {
		if (variableMap.containsKey(key)) {
			variableMap.remove(key);
		}
	}
	
	public boolean containsVariable(String key) {
		if (this.variableMap != null && this.variableMap.containsKey(key)) {
			return true;
		}
		return false;
	}

	public <T> T getVariable(String key) {
		if (this.variableMap == null) {
			this.variableMap = Collections.synchronizedMap(new HashMap<>());
		}
		T val = (T) variableMap.get(key);
		if (val instanceof String && "null".equals((String) val)) {
			return null;
		}
		return val;
	}

	public String getVariableAsString(String key) {
		Object value = getVariable(key);
		if (value instanceof String) {
			return (String) value;
		} else if (value != null) {
			return String.valueOf(value);
		}
		return null;
	}

	public LocalDate getVariableAsDate(String key) {
		Object data = getVariable(key);

		LocalDate retVal = null;

		if (!(data instanceof LocalDate)) {
			if (data instanceof String) {
				String date = (String) data;

				if (date.length() == 10) {
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT);
					retVal = LocalDate.parse((String) date, formatter);
				} else {
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT);
					retVal = LocalDateTime.parse((String) date, formatter).toLocalDate();
				}
			}
		} else {
			retVal = (LocalDate) data;
		}

		return retVal;
	}

	public LocalDateTime getVariableAsDateTime(String key) {
		Object data = getVariable(key);

		LocalDateTime retVal = null;

		if (!(data instanceof LocalDateTime)) {
			if (data instanceof String) {
				String strDate = (String) data;
				if (strDate.length() == 10) {
					return TimeZoneUtil.returnTimeZone(strDate);
				}
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT);
				retVal = LocalDateTime.parse((String) data, formatter);
			}
		} else {
			retVal = (LocalDateTime) data;
		}

		return retVal;
	}

	public double getVariableAsDouble(String key) {
		Object value = getVariable(key);
		if (value instanceof Double) {
			return (Double) value;
		}
		if (value == null) {
			return 0;
		}
		if (StringUtils.isBlank(String.valueOf(value)) || "null".equals(value)) {
			return 0;
		}
		Double retVal = Double.parseDouble(String.valueOf(value));
		return retVal == null ? 0 : retVal;
	}

	public int getVariableAsInteger(String key) {
		Object value = getVariable(key);
		if (value instanceof Integer) {
			return (Integer) value;
		}

		if (value == null) {
			return 0;
		}
		if (StringUtils.isBlank(String.valueOf(value))) {
			return 0;
		}
		Integer retVal = Integer.parseInt(String.valueOf(value));
		return retVal == null ? 0 : retVal;
	}

	public Long getVariableAsLong(String key) {

		Object value = getVariable(key);
		if (value == null || "null".equals(value)) {
			return null;
		}
		if (value instanceof Long) {
			return (Long) value;
		}

		if (StringUtils.isNotBlank(String.valueOf(value))) {
			return Long.parseLong(String.valueOf(value).trim());
		}
		return null;
	}

	public boolean getVariableAsBoolean(String key) {
		Object value = getVariable(key);
		if (value == null) {
			return false;
		}
		if (value instanceof Boolean) {
			return (Boolean) value;
		}

		return false;
	}

	public Map<String, Object> getVariableMap() {
		return variableMap;
	}

	public void setVariableMap(Map<String, Object> variableMap) {
		this.variableMap = variableMap;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		setVariable(COMPANY_ID, companyId);
		this.companyId = companyId;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		setVariable(COMPANY_CODE, companyCode);
		this.companyCode = companyCode;
	}

	public Map<String, Object> getNonBaseModelMap() {
		Map<String, Object> map = new HashMap<>();

		if (this.getVariableMap() == null) {
			return map;
		}
		for (String key : getVariableMap().keySet()) {
			
			if (!(getVariableMap().get(key) instanceof BaseModel)
					&& !key.equals(COMMITMENT)
					&& !key.equals(REBATE_CONFIG)) {
				map.put(key, getVariableMap().get(key));
			}
		}
		return map;
	}
}
