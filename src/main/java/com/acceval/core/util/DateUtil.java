package com.acceval.core.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.jknack.handlebars.internal.lang3.StringUtils;

public class DateUtil {

	private static Logger Logger = LoggerFactory.getLogger(DateUtil.class);

	public static String[] STD_DATEFORMAT = new String[] { "yyyy-MM-dd", "dd-MM-yyyy", "dd/MM/yyyy", "yyyy/MM/dd", "yyyy-MM-dd HH:mm:ss",
			"dd-MM-yyyy HH:mm:ss", "dd/MM/yyyy HH:mm:ss", "yyyy/MM/dd HH:mm:ss" };

	public static LocalDateTime parseToLocalDateTime(String value) {
		LocalDateTime dateTime = null;

		if (StringUtils.isNotBlank(value)) {
			value = value.trim();
			for (String strDateFormat : STD_DATEFORMAT) {
				try {
					if (value.length() == 10) {
						dateTime = LocalDateTime.parse(value + " 00:00:00", DateTimeFormatter.ofPattern(strDateFormat));
					} else {
						dateTime = LocalDateTime.parse(value, DateTimeFormatter.ofPattern(strDateFormat));
					}
				} catch (Exception ignore) {
				}
				if (dateTime != null) break;
			}
		}

		if (dateTime == null) {
			Logger.error("Failed to convert [" + value + "]");
		}

		return dateTime;
	}
}
