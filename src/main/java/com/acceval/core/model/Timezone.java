package com.acceval.core.model;

import java.util.List;

public class Timezone {

	private String value;
	private String abbr;
	private Long offset;
	private Boolean isdst;
	private String text;
	private String utcId;
	private List<String> utc;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getAbbr() {
		return abbr;
	}

	public void setAbbr(String abbr) {
		this.abbr = abbr;
	}

	public Long getOffset() {
		return offset;
	}

	public void setOffset(Long offset) {
		this.offset = offset;
	}

	public Boolean getIsdst() {
		return isdst;
	}

	public void setIsdst(Boolean isdst) {
		this.isdst = isdst;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<String> getUtc() {
		return utc;
	}

	public void setUtc(List<String> utc) {
		this.utc = utc;
	}

	public String getUtcId() {
		return utcId;
	}

	public void setUtcId(String utcId) {
		this.utcId = utcId;
	}
}
