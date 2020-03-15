package com.acceval.core.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import org.apache.commons.lang3.StringUtils;

import com.acceval.core.security.PrincipalUtil;
import com.acceval.core.service.TimezoneServiceImpl;

public class TimeZoneUtil {
	private static final int MINUTES_PER_HOUR = 60;
	private static final int SECONDS_PER_MINUTE = 60;
	private static final int SECONDS_PER_HOUR = SECONDS_PER_MINUTE * MINUTES_PER_HOUR;

	public static String convertTimeZone(String localDateString) {
		String timeZone = PrincipalUtil.getTimeZone();

		if (StringUtils.isBlank(timeZone)) {
			timeZone = PrincipalUtil.getSystemUser() != null ? PrincipalUtil.getSystemUser().getTimeZone() : "";
		}

		timeZone = new TimezoneServiceImpl().convertToUTCTimeZoneId(timeZone);
		if (StringUtils.isNotBlank(timeZone)) {
			DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
			if (localDateString.length() == 10) {
				return LocalDate.parse(localDateString, dateFormatter).atStartOfDay().atZone(ZoneId.of(timeZone))
						.withZoneSameInstant(ZoneId.systemDefault()).format(dateTimeFormatter);
			} else {
				return LocalDateTime.parse(localDateString, dateTimeFormatter).atZone(ZoneId.of(timeZone))
						.withZoneSameInstant(ZoneId.systemDefault()).format(dateTimeFormatter);
			}
		}
		return localDateString;
	}

	public static LocalDateTime returnTimeZone(String localDateString) {
		if (StringUtils.isBlank(localDateString)) return null;

		String timeZone = PrincipalUtil.getTimeZone();

		if (StringUtils.isBlank(timeZone)) {
			timeZone = PrincipalUtil.getSystemUser() != null ? PrincipalUtil.getSystemUser().getTimeZone() : "";
		}

		timeZone = new TimezoneServiceImpl().convertToUTCTimeZoneId(timeZone);
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		if (StringUtils.isNotBlank(timeZone)) {
			if (localDateString.length() == 10) {
				return LocalDate.parse(localDateString, dateFormatter).atStartOfDay().atZone(ZoneId.of(timeZone))
						.withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
			} else {
				return LocalDateTime.parse(localDateString, dateTimeFormatter).atZone(ZoneId.of(timeZone))
						.withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
			}
		} else {
			if (localDateString.length() == 10) {
				return LocalDate.parse(localDateString, dateFormatter).atStartOfDay();
			} else {
				return LocalDateTime.parse(localDateString, dateTimeFormatter);
			}
		}
	}

	public static String getTimeDifference(LocalDateTime dateTime) {
		String difference = "";

		if (dateTime != null) {
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime tempDateTime = LocalDateTime.from(dateTime);

		long years = tempDateTime.until(now, ChronoUnit.YEARS);
		tempDateTime = tempDateTime.plusYears(years);

		long months = tempDateTime.until(now, ChronoUnit.MONTHS);
		tempDateTime = tempDateTime.plusMonths(months);

		long days = tempDateTime.until(now, ChronoUnit.DAYS);
		tempDateTime = tempDateTime.plusDays(days);

		long hours = tempDateTime.until(now, ChronoUnit.HOURS);
		tempDateTime = tempDateTime.plusHours(hours);

		long minutes = tempDateTime.until(now, ChronoUnit.MINUTES);
		tempDateTime = tempDateTime.plusMinutes(minutes);

		long seconds = tempDateTime.until(now, ChronoUnit.SECONDS);
		
		if (years > 0) {
			difference = difference.concat(years + " year(s) ");
		}

		if (months > 0) {
			difference = difference.concat(months + " month(s) ");
		}

		if (days > 0) {
			difference = difference.concat(days + " day(s) " + hours + " hour(s)");
		} else {
			difference = difference.concat(hours + " hour(s) " + minutes + " minute(s)");
			}
		}

		return difference;
	}
}
