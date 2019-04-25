package com.acceval.core.repository;

import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.acceval.core.model.AuthUser;
import com.acceval.core.model.OwnerModel;

public class RecordOwnerMongoEventListener extends AbstractMongoEventListener<OwnerModel> {
	
	@Override
	public void onBeforeConvert(BeforeConvertEvent<OwnerModel> event) {
		
		OwnerModel ownerModel = event.getSource();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		if (auth != null) {
			Object principal = auth.getPrincipal();
			
			if (principal != null && principal instanceof AuthUser) {
				AuthUser authUser = (AuthUser) principal; 
				
				if (ownerModel.getRecordOwner() == null && authUser.getUsername() != null) {					
					ownerModel.setRecordOwner(authUser.getUsername());	
				}				
			}
		}
	}
}
