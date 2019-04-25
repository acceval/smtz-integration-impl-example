package com.acceval.core.repository;

import java.time.LocalDateTime;

import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.acceval.core.model.AuthUser;
import com.acceval.core.model.BaseModel;

public class AuditMongoEventListener extends AbstractMongoEventListener<BaseModel> {
	
	@Override
	public void onBeforeConvert(BeforeConvertEvent<BaseModel> event) {
		
		BaseModel baseModel = event.getSource();
		
		if (baseModel.getCreated() == null) {
			baseModel.setCreated(LocalDateTime.now());
		}
		baseModel.setModified(LocalDateTime.now());
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		if (auth != null) {
			Object principal = auth.getPrincipal();
			
			if (principal != null && principal instanceof AuthUser) {
				AuthUser authUser = (AuthUser) principal; 
								
				if (baseModel.getCreatedBy() == null && authUser.getUsername() != null) {					
					baseModel.setCreatedBy(authUser.getUsername());	
				}
				if (authUser.getUsername() != null) {
					baseModel.setModifiedBy(authUser.getUsername());
				}
			}
		} 
		
		if (baseModel.getCreatedBy() == null) {
			baseModel.setCreatedBy("anonymousUser");			
		}
		if (baseModel.getModifiedBy() == null) {
			baseModel.setModifiedBy("anonymousUser");
		}
	}
}
