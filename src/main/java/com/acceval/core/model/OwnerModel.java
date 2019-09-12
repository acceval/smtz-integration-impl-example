package com.acceval.core.model;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import com.fasterxml.jackson.annotation.JsonIgnore;

@MappedSuperclass
@EntityListeners(OwnerModelListener.class)
public abstract class OwnerModel extends BaseModel implements OwnerIF {
    
	private static final long serialVersionUID = 1L;

	@JsonIgnore
    @Column(nullable = false, updatable = true)
    private String recordOwner;
    
	public String getRecordOwner() {
		return recordOwner;
	}
	public void setRecordOwner(String recordOwner) {		
		this.recordOwner = recordOwner;
	}    
}
