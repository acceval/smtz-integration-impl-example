package com.acceval.core.util;

public class CostOfCreditCalculator {
	public static double calc(double day, double rateInPercentage, double price) {
		return day * (rateInPercentage / 100) / 365 * price;
	}
}
