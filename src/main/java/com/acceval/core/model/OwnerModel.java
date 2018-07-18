package com.acceval.core.model;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@EntityListeners(OwnerModelListener.class)
public abstract class OwnerModel extends BaseModel {
    
    @Column(nullable = false, updatable = true)
    private String recordOwner;
    
	public String getRecordOwner() {
		return recordOwner;
	}
	public void setRecordOwner(String recordOwner) {		
		this.recordOwner = recordOwner;
	}    
}
