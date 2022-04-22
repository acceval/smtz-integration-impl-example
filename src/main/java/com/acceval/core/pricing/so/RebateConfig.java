package com.acceval.core.pricing.so;

import java.time.LocalDate;
import java.util.List;

public class RebateConfig {

	public enum RebateTierType {
		ACCUMULATIVE_AMOUNT, AMOUNT, ACCUMULATIVE_QUANTITY, QUANTITY
	}

	public enum CalculationType {
		POINT, RANGE
	}

	public enum AdjustmentType {
		UNIT, AMOUNT, PERCENTAGE
	}

	public enum AdjustmentValueType {
		ACTUAL, INCREMENTAL
	}

	public enum PayoutPeriod {
		END_MONTH, END_QUARTER, END_YEAR, END_OF_CONTRACT, SPECIFIC_DATE
	}
	
	private Boolean activated;
	private RebateTierType rebateTierType;
	private CalculationType calculationType;
	private AdjustmentType adjustmentType;
	private AdjustmentValueType adjustmentValueType;
	private PayoutPeriod payoutPeriod;
	private List<RebateTier> rebateTiers;	
	private List<LocalDate> payoutSchedules;
	
	public RebateTierType getRebateTierType() {
		return rebateTierType;
	}

	public void setRebateTierType(RebateTierType rebateTierType) {
		this.rebateTierType = rebateTierType;
	}

	public CalculationType getCalculationType() {
		return calculationType;
	}

	public void setCalculationType(CalculationType calculationType) {
		this.calculationType = calculationType;
	}

	public AdjustmentType getAdjustmentType() {
		return adjustmentType;
	}

	public void setAdjustmentType(AdjustmentType adjustmentType) {
		this.adjustmentType = adjustmentType;
	}

	public AdjustmentValueType getAdjustmentValueType() {
		return adjustmentValueType;
	}

	public void setAdjustmentValueType(AdjustmentValueType adjustmentValueType) {
		this.adjustmentValueType = adjustmentValueType;
	}

	public PayoutPeriod getPayoutPeriod() {
		return payoutPeriod;
	}

	public void setPayoutPeriod(PayoutPeriod payoutPeriod) {
		this.payoutPeriod = payoutPeriod;
	}

	public List<RebateTier> getRebateTiers() {
		return rebateTiers;
	}

	public void setRebateTiers(List<RebateTier> rebateTiers) {
		this.rebateTiers = rebateTiers;
	}

	public Boolean getActivated() {
		return activated;
	}

	public void setActivated(Boolean activated) {
		this.activated = activated;
	}

	public List<LocalDate> getPayoutSchedules() {
		return payoutSchedules;
	}

	public void setPayoutSchedules(List<LocalDate> payoutSchedules) {
		this.payoutSchedules = payoutSchedules;
	}

}
