package com.acceval.core.model;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@EntityListeners({ CompanyModelListener.class })
public abstract class BaseCompanyModel extends BaseModel implements CompanyIF {

	private static final long serialVersionUID = 1L;

	private Long companyId;

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
}
