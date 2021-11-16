package com.acceval.core.cache.model;

import com.acceval.core.model.VariableContext;
import org.springframework.util.MultiValueMap;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;

public class ConditionEvaluationResult {
	
	private ConditionRecord conditionRecord;
	private Currency targetCurrency;
	private Uom targetUom;
	private Double numericValue;
	private Double quantity;
	private String firstValue;
	private boolean isSuccess;	
	private VariableContext variableContext;
	private StringBuffer message;
	private String notFoundMessage;
    private boolean isFatal;
    private LocalDateTime validFrom;
    private LocalDateTime validTo;
	
	public ConditionEvaluationResult() {
		this.isSuccess = true;
		this.message = new StringBuffer();
	}
	
	public void logNotFound(ConditionRecordConfig recordConfig, MultiValueMap<String, String> mapParam) {
		isSuccess = false;
		StringBuilder builder = new StringBuilder();
		builder.append("No record found for CT [" + recordConfig.getCode() + "]");
		
		builder.append(", [VALIDFROM] value [" + mapParam.getFirst("VALID_FROM") + "]");
		builder.append(", [VALIDTO] value [" + mapParam.getFirst("VALID_TO") + "]");
		
		for (ConditionFieldWrapper wrapper: recordConfig.getConditionFieldWrappers()) {
			String code = wrapper.getConditionFieldConfig().getCode();
			if (mapParam.containsKey(code)) {				
				builder.append(", [" + wrapper.getConditionFieldConfig().getCode() + "] value [" 
				+ mapParam.getFirst(code) + "]");
			} else {
				builder.append(", [" + code + "] not found in context");
			}
		}
		
		this.notFoundMessage = builder.toString();
	}

    public boolean isFatal() {
        return isFatal;
    }

    public void setFatal(boolean isFatal) {
        this.isFatal = isFatal;
    }
	
	public void logInfo(String info) {
		message.append(info);
		message.append("\n");
	}
	
	public void logError(Throwable ex) {

	    StringWriter writer = new StringWriter();
		PrintWriter pw = new PrintWriter(writer);
		ex.printStackTrace(pw);
		this.isSuccess = false;
		message.append(writer.toString());
	}
	
	public String getMessage() {
		return message.toString();
	}

	public void setMessage(String message) {		
	}

	public String getNotFoundMessage() {
		return notFoundMessage;
	}

	public void setNotFoundMessage(String notFoundMessage) {
		this.notFoundMessage = notFoundMessage;
	}

	public ConditionRecord getConditionRecord() {
		return conditionRecord;
	}
	public void setConditionRecord(ConditionRecord conditionRecord) {
		this.conditionRecord = conditionRecord;
	}
	public Currency getTargetCurrency() {
		return targetCurrency;
	}
	public void setTargetCurrency(Currency targetCurrency) {
		this.targetCurrency = targetCurrency;
	}
	public Uom getTargetUom() {
		return targetUom;
	}
	public void setTargetUom(Uom targetUom) {
		this.targetUom = targetUom;
	}
	public Double getNumericValue() {
		return numericValue;
	}
	public void setNumericValue(Double numericValue) {
		this.numericValue = numericValue;
	}	
	public String getFirstValue() {
		return firstValue;
	}
	public void setFirstValue(String firstValue) {
		this.firstValue = firstValue;
	}
	public boolean isSuccess() {
		return isSuccess;
	}
	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
		
	public VariableContext getVariableContext() {
		return variableContext;
	}
	public void setVariableContext(VariableContext variableContext) {
		this.variableContext = variableContext;
	}
	public Double getQuantity() {
		return quantity;
	}
	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public LocalDateTime getValidFrom() {
		return validFrom;
	}

	public void setValidFrom(LocalDateTime validFrom) {
		this.validFrom = validFrom;
	}

	public LocalDateTime getValidTo() {
		return validTo;
	}

	public void setValidTo(LocalDateTime validTo) {
		this.validTo = validTo;
	}
	
}
