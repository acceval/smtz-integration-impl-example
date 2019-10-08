package com.acceval.core.model.company;

import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;

import com.acceval.core.model.BaseEntityListener;
import com.acceval.core.model.BaseEntity.STATUS;
import com.fasterxml.jackson.annotation.JsonIgnore;

@MappedSuperclass
@EntityListeners(BaseEntityListener.class)
public abstract class BaseCompanyEntity extends BaseCompanyModel {

	private static final long serialVersionUID = 1L;

	@JsonIgnore
	@Enumerated(EnumType.STRING)
	private STATUS recordStatus = STATUS.ACTIVE;
	
	private String dateArchived = "";

	public String getDateArchived() {
		return dateArchived;
	}

	public void setDateArchived(String dateArchived) {
		this.dateArchived = dateArchived;
	}

	public STATUS getRecordStatus() {
		return recordStatus;
	}

	public void setRecordStatus(STATUS recordStatus) {
		this.recordStatus = recordStatus;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
