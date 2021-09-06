package com.acceval.core.cache.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ConditionRecordCacheHolder implements Serializable {
    private ConditionRecordConfig config;
    private List<ConditionRecordCacheHelper> records;

    private List<ConditionFieldWrapper> sortedFields;

    public ConditionRecordConfig getConfig() {
        return config;
    }

    public void setConfig(ConditionRecordConfig config) {
        this.config = config;
        this.sortedFields = null;
    }

    public List<ConditionRecordCacheHelper> getRecords() {
        return records;
    }

    public void setRecords(List<ConditionRecordCacheHelper> records) {
        this.records = records;
    }

    public ConditionRecordCacheHelper toHelper(ConditionRecord conditionRecord) {
//        String recordKey = "";
        Map<String, String> fieldKeyValues = new HashMap<>();

        ConditionRecordCacheHelper helper = new ConditionRecordCacheHelper();
        helper.setConditionRecord(conditionRecord);

        for (ConditionFieldWrapper w : getSortedFields()) {
            String fieldKey = w.getConditionFieldConfig().getCode();
//            recordKey += fieldKey + "=" + helper.getConditionFieldValue(fieldKey);

            fieldKeyValues.put(fieldKey, helper.getConditionFieldValue(fieldKey));
        }

//        helper.setRecordKey(recordKey);
        helper.setFieldKeyValues(fieldKeyValues);

        return helper;
    }

    public List<ConditionFieldWrapper> getSortedFields() {
        if (sortedFields != null) return sortedFields;

        sortedFields = config.getConditionFieldWrappers().stream().sorted(
                (o1, o2) -> o1.getDisplayOrder() - o2.getDisplayOrder()).collect(Collectors.toList());

        return sortedFields;
    }
}
