package com.acceval.core.filehandler.common;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.acceval.core.microservice.model.LabelValue;

public class ColumnDef {

	private String label;
	private List<LabelValue> datasource;
	private boolean mandate = false;
	private String dateFormat;

	public ColumnDef() {
		super();
	}

	public ColumnDef(String label) {
		super();
		this.label = label;
	}

	public ColumnDef(String label, List<LabelValue> datasource) {
		super();
		this.label = label;
		this.datasource = datasource;
	}


	public ColumnDef(String label, boolean mandate, String dateFormat) {
		super();
		this.label = label;
		this.mandate = mandate;
		this.dateFormat = dateFormat;
	}

	public String findValueFromDatasource(String text) {
		if (this.datasource != null && StringUtils.isNotBlank(text)) {
			for (LabelValue lv : this.datasource) {
				if (text.trim().toLowerCase().equals(lv.getLabel().toLowerCase())) {
					return lv.getValue();
				} else if (text.trim().equals(lv.getValue())) {
					return lv.getValue();
				}
			}
		}
		return null;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public List<LabelValue> getDatasource() {
		return datasource;
	}

	public void setDatasource(List<LabelValue> datasource) {
		this.datasource = datasource;
	}

	public boolean isMandate() {
		return mandate;
	}

	public void setMandate(boolean mandate) {
		this.mandate = mandate;
	}

	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

}
