package com.acceval.core.cache.model;

import com.acceval.core.model.BaseEntity;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Transient;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ConditionRecord implements Cloneable, Serializable {
    private Long id;
    private ConditionRecordConfig config;
    private List<ConditionField> conditionFields;
    private List<ConditionValue> conditionValues;
    private LocalDateTime validFrom;
    private LocalDateTime validTo;
    private String createdBy;
    private LocalDateTime created;
    private String modifiedBy;
    private LocalDateTime modified;
    private String recordOwner;
    @Enumerated(EnumType.STRING)
    private BaseEntity.STATUS recordStatus;

    public ConditionRecord() {
        this.conditionFields = new ArrayList<ConditionField>();
        this.conditionValues = new ArrayList<ConditionValue>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRecordOwner() {
        return recordOwner;
    }

    public void setRecordOwner(String recordOwner) {
        this.recordOwner = recordOwner;
    }

    public ConditionRecordConfig getConfig() {
        return config;
    }

    public void setConfig(ConditionRecordConfig config) {
        this.config = config;
    }

    public List<ConditionField> getConditionFields() {
        return conditionFields;
    }

    public void setConditionFields(List<ConditionField> conditionFields) {
        this.conditionFields = conditionFields;
    }

    public List<ConditionValue> getConditionValues() {
        return conditionValues;
    }

    public void setConditionValues(List<ConditionValue> conditionValues) {
        this.conditionValues = conditionValues;
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

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public LocalDateTime getModified() {
        return modified;
    }

    public void setModified(LocalDateTime modified) {
        this.modified = modified;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public BaseEntity.STATUS getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(BaseEntity.STATUS recordStatus) {
        this.recordStatus = recordStatus;
    }

}
