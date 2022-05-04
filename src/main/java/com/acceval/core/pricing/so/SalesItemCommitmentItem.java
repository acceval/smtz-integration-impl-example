package com.acceval.core.pricing.so;

import java.io.Serializable;
import java.time.LocalDateTime;

public class SalesItemCommitmentItem implements Serializable {
	private static final long serialVersionUID = 1L;

	private double quantity;
	private String period;
	private double invoicePrice;
	private int orderNum;
	private LocalDateTime validFrom;
	private LocalDateTime validTo;
	private double maximumQuantity;

	private double previousSalesQuantity;
	private double previousMaximumQuantity;
	private Long migrationCommitmentItemID;
		
	public double getPreviousSalesQuantity() {
		return previousSalesQuantity;
	}

	public void setPreviousSalesQuantity(double previousSalesQuantity) {
		this.previousSalesQuantity = previousSalesQuantity;
	}

	public double getPreviousMaximumQuantity() {
		return previousMaximumQuantity;
	}

	public void setPreviousMaximumQuantity(double previousMaximumQuantity) {
		this.previousMaximumQuantity = previousMaximumQuantity;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public double getInvoicePrice() {
		return invoicePrice;
	}

	public void setInvoicePrice(double invoicePrice) {
		this.invoicePrice = invoicePrice;
	}

	public int getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}

	public LocalDateTime getValidFrom() {
		return validFrom;
	}

	public void setValidFrom(LocalDateTime validFrom) {
		this.validFrom = validFrom;
	}

	public LocalDateTime getValidTo() {
		return validTo;
	}

	public void setValidTo(LocalDateTime validTo) {
		this.validTo = validTo;
	}

	public double getMaximumQuantity() {
		return maximumQuantity;
	}

	public void setMaximumQuantity(double maximumQuantity) {
		this.maximumQuantity = maximumQuantity;
	}

	public Long getMigrationCommitmentItemID() {
		return migrationCommitmentItemID;
	}

	public void setMigrationCommitmentItemID(Long migrationCommitmentItemID) {
		this.migrationCommitmentItemID = migrationCommitmentItemID;
	}

}
