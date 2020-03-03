package com.acceval.core.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.apache.commons.lang3.StringUtils;

import com.acceval.core.security.PrincipalUtil;
import com.acceval.core.service.TimezoneServiceImpl;

public class TimeZoneUtil {
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
}
