package com.acceval.core.model;

import javax.persistence.PrePersist;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CompanyModelListener {

	@PrePersist
	public void setCompanyId(BaseModel baseModel) {
		
		AuthUser authUser = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		if (authUser != null) {
			if (baseModel.getCompanyId() == null && authUser.getCompanyId() != null) {
				
				baseModel.setCompanyId(authUser.getCompanyId());				
			}		
		}
	}
}
