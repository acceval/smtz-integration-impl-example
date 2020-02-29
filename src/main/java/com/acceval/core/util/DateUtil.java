package com.acceval.core.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {
	public static String[] STD_DATEFORMAT = new String[] { "yyyy-MM-dd", "dd-MM-yyyy", "dd/MM/yyyy", "yyyy/MM/dd", "yyyy-MM-dd HH:mm:ss",
			"dd-MM-yyyy HH:mm:ss", "dd/MM/yyyy HH:mm:ss", "yyyy/MM/dd HH:mm:ss" };

	public static LocalDateTime parseToLocalDateTime(String value) {
		LocalDateTime dateTime = null;

		for (String strDateFormat : STD_DATEFORMAT) {
			try {
				dateTime = LocalDateTime.parse(value, DateTimeFormatter.ofPattern(strDateFormat));
			} catch (Exception ignore) {
			}
			if (dateTime != null) break;
		}

		return dateTime;
	}
}
