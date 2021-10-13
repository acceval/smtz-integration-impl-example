package com.acceval.core.cache.model;

import java.io.Serializable;

public class PaymentTerm implements Serializable {
	private static final long serialVersionUID = 1L;

	private long paymentTermID;
	private String code;
	private String name;
	private int creditDay;
	private String description;
	private Long paymentMethodID;
	private int additionMonths;
	private int fixedDay;
	private int rank;
	private String type;

	public long getPaymentTermID() {
		return paymentTermID;
	}

	public void setPaymentTermID(long paymentTermID) {
		this.paymentTermID = paymentTermID;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCreditDay() {
		return creditDay;
	}

	public void setCreditDay(int creditDay) {
		this.creditDay = creditDay;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getPaymentMethodID() {
		return paymentMethodID;
	}

	public void setPaymentMethodID(Long paymentMethodID) {
		this.paymentMethodID = paymentMethodID;
	}

	public int getAdditionMonths() {
		return additionMonths;
	}

	public void setAdditionMonths(int additionMonths) {
		this.additionMonths = additionMonths;
	}

	public int getFixedDay() {
		return fixedDay;
	}

	public void setFixedDay(int fixedDay) {
		this.fixedDay = fixedDay;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
