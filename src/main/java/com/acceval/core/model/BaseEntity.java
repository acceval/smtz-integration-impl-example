package com.acceval.core.model;

import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;

import com.fasterxml.jackson.annotation.JsonIgnore;

@MappedSuperclass
@EntityListeners(BaseEntityListener.class)
public abstract class BaseEntity extends BaseModel {

	private static final long serialVersionUID = 1L;

	public enum STATUS {
		ACTIVE, ARCHIVE;
	}

	@JsonIgnore
	@Enumerated(EnumType.STRING)
	private STATUS recordStatus = STATUS.ACTIVE;

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
