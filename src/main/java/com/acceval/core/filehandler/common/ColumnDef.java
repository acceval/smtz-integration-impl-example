package com.acceval.core.filehandler.common;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.acceval.core.microservice.model.LabelValue;

public class ColumnDef {

	private String label;
	private List<LabelValue> datasource;

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
		return text;
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

}
