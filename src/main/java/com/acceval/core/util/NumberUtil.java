package com.acceval.core.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;

import org.apache.commons.lang3.StringUtils;

public class NumberUtil {

    private static final String NUMBER_FORMAT = "#,###,###,##0.00";

	public static double stringToDouble(String str) {
		if (StringUtils.isEmpty(str)) return 0;
		return new Double(str).doubleValue();
	}

	public static Double zeroIfNulll(Object number) {
		if (number == null) return new Double(0);
		if (number instanceof BigDecimal) return zeroIfNulll((BigDecimal) number).doubleValue();
		if (number instanceof String) return stringToDouble((String) number);
		return number instanceof Integer ? (Integer) number : (Double) number;
	}

	public static BigDecimal zeroIfNulll(BigDecimal number) {
		if (number == null) return new BigDecimal(0);
		return number;
	}

	public static Double zeroIfNullorNaN(Double number) {
		String newNumber = String.valueOf(number);
		if (newNumber == null || "NaN".equals(newNumber.trim()) || number.isInfinite()) return new Double(0);
		return new Double(Double.parseDouble(newNumber));
	}

	public static double round(double number, int decimalPoint) {
		return round(number, decimalPoint, RoundingMode.CEILING);
	}

	public static double round(double number, int decimalPoint, RoundingMode roundingMode) {
		if (Double.isInfinite(number) || Double.isNaN(number)) {
			return number;
		}
		StringBuffer format = new StringBuffer("###########0");
		if (decimalPoint > 0) {
			format.append(".");
			for (int i = 0; i < decimalPoint; i++) {
				format.append("#");
			}
		}
		String newNumberStr = formatNumber(number, format.toString(), roundingMode);
		double test = new BigDecimal(number).setScale(decimalPoint, BigDecimal.ROUND_HALF_UP).doubleValue();

		try {
			return Double.parseDouble(newNumberStr);
		} catch (NumberFormatException e) {
			throw new RuntimeException("Original Number [" + number + "] New Number [" + newNumberStr + "]" + e.getMessage(), e);

		}
	}

	public static String formatNumber(double num, String fmt, RoundingMode roundingMode) throws NumberFormatException {
		DecimalFormat df = new DecimalFormat(fmt);
		df.setRoundingMode(roundingMode);
		return df.format(num);
	}

	public static double round(double number, int decimalPoint, int roundingMode) {
		if (Double.isNaN(number)) {
			number = 0;
		}
		return new BigDecimal(number).setScale(decimalPoint, roundingMode).doubleValue();
	}

	public static String formatNumber(double num, int decimalPoint) {
		StringBuffer format = new StringBuffer("#,###,###,##0");
		if (decimalPoint > 0) {
			format.append(".");
			for (int i = 0; i < decimalPoint; i++) {
				format.append("0");
			}
		}
		return formatNumber(num, format.toString());
	}

    public static double parse(String value) throws ParseException {
        if (StringUtils.isBlank(value)) {
            return 0;
        }

        DecimalFormat df = new DecimalFormat(NUMBER_FORMAT);
		df.setRoundingMode(RoundingMode.CEILING);
        return df.parse(value).doubleValue();
    }

	public static String formatNumber(double num, String fmt) throws NumberFormatException {
		DecimalFormat df = new DecimalFormat(fmt);
		df.setRoundingMode(RoundingMode.CEILING);
		return df.format(num);
	}
}
