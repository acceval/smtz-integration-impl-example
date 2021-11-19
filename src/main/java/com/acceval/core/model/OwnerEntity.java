package com.acceval.core.model;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.javers.core.metamodel.annotation.DiffIgnore;

import com.fasterxml.jackson.annotation.JsonIgnore;

@MappedSuperclass
@EntityListeners(OwnerEntityListener.class)
public abstract class OwnerEntity extends BaseEntity {
    
	@DiffIgnore
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
