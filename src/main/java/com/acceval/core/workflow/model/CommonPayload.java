package com.acceval.core.workflow.model;

import java.io.Serializable;

public class CommonPayload extends TaskPayload implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long entityID;
	private Object entity;
	private Long idToDelete;

	public Long getEntityID() {
		return entityID;
	}

	public void setEntityID(Long entityID) {
		this.entityID = entityID;
	}

	public Object getEntity() {
		return entity;
	}

	public void setEntity(Object entity) {
		this.entity = entity;
	}

	public Long getIdToDelete() {
		return idToDelete;
	}

	public void setIdToDelete(Long idToDelete) {
		this.idToDelete = idToDelete;
	}

}
