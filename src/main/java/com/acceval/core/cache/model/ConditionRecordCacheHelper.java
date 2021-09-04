package com.acceval.core.cache.model;

import java.io.Serializable;
import java.util.Map;

public class ConditionRecordCacheHelper implements Serializable {
    private ConditionRecord conditionRecord;
    private Map<String, String> fieldKeyValues;

    public ConditionRecord getConditionRecord() {
        return conditionRecord;
    }

    public void setConditionRecord(ConditionRecord conditionRecord) {
        this.conditionRecord = conditionRecord;
    }

    public Map<String, String> getFieldKeyValues() {
        return fieldKeyValues;
    }

    public void setFieldKeyValues(Map<String, String> fieldKeyValues) {
        this.fieldKeyValues = fieldKeyValues;
    }

    public String getValue(String fieldKey) {
        return this.fieldKeyValues.get(fieldKey);
    }

    public String getConditionFieldValue(String key) {
        String value = null;
        for (ConditionField cf : conditionRecord.getConditionFields()) {
            if (key.equals(cf.getConfig().getCode())) {
                value = cf.getValue();
                break;
            }
        }

//        if (StringUtils.isBlank(value)) {
//            return "$EMPTY$";
//        }

        return value;
    }
}
