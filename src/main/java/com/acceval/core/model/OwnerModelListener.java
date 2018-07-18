package com.acceval.core.model;

import javax.persistence.PrePersist;

import org.springframework.stereotype.Service;

@Service
public class OwnerModelListener {

	@PrePersist
	public void setRecordOwner(OwnerModel ownerModel) {
		if (ownerModel.getRecordOwner() == null && ownerModel.getCreatedBy() != null) {
			
			ownerModel.setRecordOwner(ownerModel.getCreatedBy());
		}		
	}
}
