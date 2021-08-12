package com.acceval.core.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateUtil {

	private static Logger Logger = LoggerFactory.getLogger(DateUtil.class);

	public static String[] STD_DATEFORMAT = new String[] { "dd-MM-yyyy HH:mm:ss", "yyyy-MM-dd", "dd-MM-yyyy", "dd/MM/yyyy", "yyyy/MM/dd",
			"yyyy-MM-dd HH:mm:ss", "dd/MM/yyyy HH:mm:ss", "yyyy/MM/dd HH:mm:ss" };

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

	public static LocalDate mapToLocalDate(Map<String, Object> mapData) {
		if (mapData != null) {
			Object objYear = mapData.get("year") != null ? mapData.get("year") : null;
			Object objMonth = mapData.get("month") != null ? mapData.get("month") : null;
			Object objDay = mapData.get("day") != null ? mapData.get("day") : null;
			if (objYear != null && objMonth != null && objDay != null) {
				Integer year = objYear instanceof String ? Integer.parseInt((String) objYear) : (Integer) objYear;
				Integer month = objMonth instanceof String ? Integer.parseInt((String) objMonth) : (Integer) objMonth;
				Integer day = objDay instanceof String ? Integer.parseInt((String) objDay) : (Integer) objDay;
				LocalDate localDate = LocalDate.of(year, month, day);
				return localDate;
			}
		}
		return null;
	}

	public static LocalDateTime mapToLocalDateTime(Map<String, Object> mapData) {
		if (mapData != null) {
			Object objYear = mapData.get("year") != null ? mapData.get("year") : null;
			Object objMonth = mapData.get("month") != null ? mapData.get("month") : null;
			Object objDay = mapData.get("day") != null ? mapData.get("day") : null;
			Object objHour = mapData.get("hour") != null ? mapData.get("hour") : null;
			Object objMinute = mapData.get("minute") != null ? mapData.get("minute") : null;
			Object objSecond = mapData.get("second") != null ? mapData.get("second") : null;
			if (objYear != null && objMonth != null && objDay != null && objHour != null && objMinute != null && objSecond != null) {
				Integer year = objYear instanceof String ? Integer.parseInt((String) objYear) : (Integer) objYear;
				Integer month = objMonth instanceof String ? Integer.parseInt((String) objMonth) : (Integer) objMonth;
				Integer day = objDay instanceof String ? Integer.parseInt((String) objDay) : (Integer) objDay;
				Integer hour = objHour instanceof String ? Integer.parseInt((String) objHour) : (Integer) objHour;
				Integer minute = objMinute instanceof String ? Integer.parseInt((String) objMinute) : (Integer) objMinute;
				Integer second = objSecond instanceof String ? Integer.parseInt((String) objSecond) : (Integer) objSecond;
				LocalDateTime localDateTime = LocalDateTime.of(year, month, day, hour, minute, second);
				return localDateTime;
			}
		}
		return null;
	}

	public static boolean LocalDateEquals(LocalDate ld1, LocalDate ld2) {
		if (ld1 == null && ld2 == null) {
			return true;
		} else if (ld1 == null && ld2 != null) {
			return false;
		} else if (ld1 != null && ld2 == null) {
			return false;
		}
		return ld1.compareTo(ld2) == 0;
	}

	public static LocalDate getFirstDateOfMonth(LocalDate localDate) {
		return LocalDate.of(localDate.getYear(), localDate.getMonth(), 1);
	}

	public static LocalDate getLastDateOfMonth(LocalDate localDate) {
		return getFirstDateOfMonth(localDate).minusDays(1);
	}
}
