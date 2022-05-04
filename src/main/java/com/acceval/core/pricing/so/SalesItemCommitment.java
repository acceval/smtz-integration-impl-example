package com.acceval.core.pricing.so;

import java.io.Serializable;
import java.util.List;

public class SalesItemCommitment implements Serializable {
	private static final long serialVersionUID = 1L;

	public enum PeriodType {
		WEEK, MONTH, QUARTER, YEAR, CONTRACT_DURATION
	}

	private Double totalQuantity;
	private Double totalSalesQuantity;
	private PeriodType periodType;
	private List<SalesItemCommitmentItem> items;
	private Integer priceIncreaseDelayPeriod;
	private String formula;
	private Double periodQuantity;
	private Double periodSalesQuantity;

	private Long migrationCommitmentID;

	public Double getTotalQuantity() {
		return totalQuantity;
	}

	public void setTotalQuantity(Double totalQuantity) {
		this.totalQuantity = totalQuantity;
	}

	public PeriodType getPeriodType() {
		return periodType;
	}

	public void setPeriodType(PeriodType periodType) {
		this.periodType = periodType;
	}

	public List<SalesItemCommitmentItem> getItems() {
		return items;
	}

	public void setItems(List<SalesItemCommitmentItem> items) {
		this.items = items;
	}

	public Integer getPriceIncreaseDelayPeriod() {
		return priceIncreaseDelayPeriod;
	}

	public void setPriceIncreaseDelayPeriod(Integer priceIncreaseDelayPeriod) {
		this.priceIncreaseDelayPeriod = priceIncreaseDelayPeriod;
	}

	public String getFormula() {
		return formula;
	}

	public void setFormula(String formula) {
		this.formula = formula;
	}

	public Double getPeriodQuantity() {
		return periodQuantity;
	}

	public void setPeriodQuantity(Double periodQuantity) {
		this.periodQuantity = periodQuantity;
	}

	public Long getMigrationCommitmentID() {
		return migrationCommitmentID;
	}

	public void setMigrationCommitmentID(Long migrationCommitmentID) {
		this.migrationCommitmentID = migrationCommitmentID;
	}

	public Double getTotalSalesQuantity() {
		return totalSalesQuantity;
	}

	public void setTotalSalesQuantity(Double totalSalesQuantity) {
		this.totalSalesQuantity = totalSalesQuantity;
	}

	public Double getPeriodSalesQuantity() {
		return periodSalesQuantity;
	}

	public void setPeriodSalesQuantity(Double periodSalesQuantity) {
		this.periodSalesQuantity = periodSalesQuantity;
	}

	
}
