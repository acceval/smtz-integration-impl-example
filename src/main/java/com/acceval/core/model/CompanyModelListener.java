package com.acceval.core.model;

import javax.persistence.PrePersist;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.acceval.core.model.company.BaseCompanyModel;
import com.acceval.core.security.PrincipalUtil;

@Service
public class CompanyModelListener {

	@PrePersist
	public void setCompanyId(Object baseModel) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Long companyId = PrincipalUtil.getCompanyID();

		if (auth != null) {
			Object principal = auth.getPrincipal();

			if (principal != null && principal instanceof AuthUser && baseModel instanceof BaseCompanyModel) {
				AuthUser authUser = (AuthUser) principal;

				//if (baseModel.getCompanyId() == null && authUser.getCompanyId() != null) {// before BaseCompanyModel
				if (((BaseCompanyModel)baseModel).getCompanyId() == null && authUser.getCompanyId() != null) {
					
					((BaseCompanyModel) baseModel).setCompanyId(authUser.getCompanyId());
				}
			}
		} else if (companyId != null && baseModel instanceof BaseCompanyModel) {
			// backup plan for Auth not found
			((BaseCompanyModel) baseModel).setCompanyId(companyId);
			((BaseCompanyModel) baseModel).setCreatedBy(PrincipalUtil.getUsername());
			((BaseCompanyModel) baseModel).setModifiedBy(PrincipalUtil.getUsername());
		}
	}
}
