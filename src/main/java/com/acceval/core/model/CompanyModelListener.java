package com.acceval.core.model;

import javax.persistence.PrePersist;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.acceval.core.security.PrincipalUtil;

@Service
public class CompanyModelListener {

	@PrePersist
	public void setCompanyId(CompanyIF companyIF) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Long companyId = PrincipalUtil.getCompanyID();

		if (auth != null) {
			Object principal = auth.getPrincipal();

			if (principal != null && principal instanceof AuthUser) {
				AuthUser authUser = (AuthUser) principal;

				if (companyIF.getCompanyId() == null && authUser.getCompanyId() != null) {
					companyIF.setCompanyId(authUser.getCompanyId());
				}
			}
		} else if (companyId != null) {
			// backup plan for Auth not found
			companyIF.setCompanyId(companyId);
			if (companyIF instanceof BaseModel) {
				((BaseModel) companyIF).setCreatedBy(PrincipalUtil.getUsername());
				((BaseModel) companyIF).setModifiedBy(PrincipalUtil.getUsername());
			}
		}
	}
}
