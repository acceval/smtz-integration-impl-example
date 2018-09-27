package com.acceval.core.model;

import javax.persistence.PrePersist;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CompanyModelListener {

	@PrePersist
	public void setCompanyId(BaseModel baseModel) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		if (auth != null) {
			Object principal = auth.getPrincipal();
			
			if (principal != null && principal instanceof AuthUser) {
				AuthUser authUser = (AuthUser) principal; 
				
				if (baseModel.getCompanyId() == null && authUser.getCompanyId() != null) {					
					baseModel.setCompanyId(authUser.getCompanyId());				
				}					
			}
		}
	}
}
