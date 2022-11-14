package com.acceval.core.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;

import org.apache.commons.lang3.StringUtils;

public class NumberUtil {

    private static final String NUMBER_FORMAT = "#,###,###,##0.00";

	public static void testCase() {
		System.out.println("=========================================");
		double weird1 = -3.655 - 0.01;
		double weird1p = -3.655 + 0.01;
		System.out.println("DecimalFormat(\"#.00\").format(" + weird1 + "): " + new DecimalFormat("#.00").format(weird1));
		System.out.println("BigDecimal(" + String.valueOf(weird1) + ").setScale(2, RoundingMode.HALF_UP): "
				+ new BigDecimal(weird1).setScale(2, RoundingMode.HALF_UP));
		System.out.println(weird1 + " vs " + NumberUtil.round(weird1, 2));
		System.out.println(weird1p + " vs " + NumberUtil.round(weird1p, 2));
		System.out.println((3.655 - 0.01) + " vs " + NumberUtil.round((3.655 - 0.01), 2));
		System.out.println((3.655 + 0.01) + " vs " + NumberUtil.round((3.655 + 0.01), 2));
		System.out.println("NumberUtil.round(3.6545, 2): " + NumberUtil.round(3.6545, 2));
		System.out.println("NumberUtil.round(-3.6545, 2): " + NumberUtil.round(-3.6545, 2));
		System.out.println(NumberUtil.round(3.00001, 21));
		double value1 = 1.0848;
		double value2 = 1.2345;
		System.out.println(String.valueOf(value1) + " :" + NumberUtil.round(value1, 2));
		System.out.println(String.valueOf(value2) + " :" + NumberUtil.round(value2, 2));
		double value3 = 123456789012345.0848;
		double value4 = 123456789012345.2345;
		System.out.println(String.valueOf(value3) + " :" + NumberUtil.round(value3, 2));
		System.out.println(String.valueOf(value4) + " :" + NumberUtil.round(value4, 2));
		double weird10 = -1234567.655 - 0.01 - 0.01;
		System.out.println(weird10 + " vs " + NumberUtil.round(weird10, 2));
		System.out.println("=========================================");
		weird1 = -123456.655;
		for (int i = 1; i <= 1000; i++) {
			weird1 -= 0.01;
			double newV = NumberUtil.round(weird1, 4);
			System.out.println("(" + i + " post)" + weird1 + " vs " + newV);
//			weird1 = newV;
		}
		System.out.println("=========================================");
	}

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
		return round(number, decimalPoint, RoundingMode.HALF_UP);
	}

	public static double round(double number, int decimalPoint, RoundingMode roundingMode) {
		String strNumber = String.valueOf(number);
		if (Double.isInfinite(number) || Double.isNaN(number) || "NaN".equals(strNumber.trim())) {
			return number;
		}

		try {
			if (strNumber.indexOf("E") == -1 && strNumber.indexOf(".") > -1) {
				int firstRoundDecimal = strNumber.length() - strNumber.indexOf(".") - 5;
				if (firstRoundDecimal >= (decimalPoint * 3)) {
//					System.out.println("\t" + strNumber.length() + " - " + strNumber.indexOf(".") + " - 5 = " + firstRoundDecimal);
					double firstRounding = new BigDecimal(strNumber).setScale(firstRoundDecimal, RoundingMode.HALF_UP).doubleValue();
					double newV = new BigDecimal(Double.toString(firstRounding)).setScale(decimalPoint, roundingMode).doubleValue();
//					System.out.println("\t" + number + " => " + firstRounding + " (" + firstRoundDecimal + ") => " + newV);
					return newV;
				}
			}
			return new BigDecimal(strNumber).setScale(decimalPoint, roundingMode).doubleValue();
		} catch (NumberFormatException e) {
			throw new RuntimeException("Original Number [" + number + "] New Number [" + strNumber + "]" + e.getMessage(), e);

		}
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
		df.setRoundingMode(RoundingMode.HALF_UP);
        return df.parse(value).doubleValue();
    }

	public static String formatNumber(double num, String fmt) throws NumberFormatException {
		DecimalFormat df = new DecimalFormat(fmt);
		df.setRoundingMode(RoundingMode.HALF_UP);
		return df.format(num);
	}
}
