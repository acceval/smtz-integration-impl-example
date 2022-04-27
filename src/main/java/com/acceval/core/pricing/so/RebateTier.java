package com.acceval.core.pricing.so;

public class RebateTier implements Comparable<RebateTier> {

	private double tier;
	private double value;
	private String tierDisplay;
	private String valueDisplay;

	public RebateTier() {
		super();
	}

	public RebateTier(double tier, double value) {
		this.tier = tier;
		this.value = value;
	}

	public double getTier() {
		return tier;
	}

	public void setTier(double tier) {
		this.tier = tier;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public String getTierDisplay() {
		return tierDisplay;
	}

	public void setTierDisplay(String tierDisplay) {
		this.tierDisplay = tierDisplay;
	}

	public String getValueDisplay() {
		return valueDisplay;
	}

	public void setValueDisplay(String valueDisplay) {
		this.valueDisplay = valueDisplay;
	}

	@Override
	public int compareTo(RebateTier o) {
		if (tier == tier) return 0;
		return tier > o.tier ? 1 : -1;
	}
}
