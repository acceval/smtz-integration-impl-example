package com.acceval.core.cache.model;

import java.io.Serializable;
import java.util.List;

public class ConditionRecordCacheHolder implements Serializable {
//    private String code;
    private ConditionRecordConfig config;
    private List<ConditionRecord> records;

//    public String getCode() {
//        return code;
//    }
//
//    public void setCode(String code) {
//        this.code = code;
//    }

    public ConditionRecordConfig getConfig() {
        return config;
    }

    public void setConfig(ConditionRecordConfig config) {
        this.config = config;
    }

    public List<ConditionRecord> getRecords() {
        return records;
    }

    public void setRecords(List<ConditionRecord> records) {
        this.records = records;
    }

    @Override
    public String toString() {
        return "ConditionRecordCacheHolder{" +
//                "code=" + code +
                "config=" + (config == null ? "null" : config.getCode()) +
                ", records=" + (records == null ? "empty" : records.size()) +
                '}';
    }
}
