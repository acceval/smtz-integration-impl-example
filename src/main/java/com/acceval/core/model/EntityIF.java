package com.acceval.core.model;

import com.acceval.core.model.BaseEntity.STATUS;

public interface EntityIF {
	public String getDateArchived();

	public void setDateArchived(String dateArchived);

	public STATUS getRecordStatus();

	public void setRecordStatus(STATUS recordStatus);

}
