package com.acceval.core.model;

import javax.persistence.PrePersist;

import org.springframework.stereotype.Service;

@Service
public class OwnerEntityListener {

	@PrePersist
	public void setRecordOwner(OwnerEntity ownerEntity) {
		if (ownerEntity.getRecordOwner() == null && ownerEntity.getCreatedBy() != null) {
			
	    		ownerEntity.setRecordOwner(ownerEntity.getCreatedBy());
		}		
	}
}
