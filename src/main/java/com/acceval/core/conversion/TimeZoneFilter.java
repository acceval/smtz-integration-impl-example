package com.acceval.core.conversion;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.acceval.core.service.TimezoneService;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.acceval.core.security.CurrentUser;
import com.acceval.core.security.PrincipalUtil;
import com.github.jknack.handlebars.internal.lang3.StringUtils;

@Component
public class TimeZoneFilter implements Filter {
	private static String[] STD_DATEFORMAT = new String[] { "yyyy-MM-dd", "dd-MM-yyyy", "dd/MM/yyyy", "yyyy/MM/dd" };
	private static String[] STD_DATETIMEFORMAT = new String[] { "yyyy-MM-dd HH:mm:ss", "dd-MM-yyyy HH:mm:ss",
			"dd/MM/yyyy HH:mm:ss", "yyyy/MM/dd HH:mm:ss" };

	@Autowired
	TimezoneService timezoneService;

	@Override
	public void init(FilterConfig config) throws ServletException {
		//
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws ServletException, IOException {
		HttpServletRequest request = (HttpServletRequest) req;
		String timeZone = "";
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			Object principal = auth.getPrincipal();
			if (principal != null && principal instanceof CurrentUser) {
				CurrentUser currentUser = (CurrentUser) principal;
				timeZone = currentUser.getTimeZone();
				
			}
		}

		// check for feign client session without login user
//		if (StringUtils.isBlank(timeZone)) {
//			Enumeration<String> headerNames = request.getHeaderNames();
//			if (headerNames != null) {
//				timeZone = request.getHeader(PrincipalUtil.HDRKEY_TIMEZONEID);
//			}
//		}

		
		if (StringUtils.isNotBlank(timeZone)) {
			timeZone = timezoneService.convertToUTCTimeZoneId(timeZone);
			Map<String, String[]> reqParam = request.getParameterMap();
			Map<String, String[]> convertedDateTime = new HashMap<String, String[]>();
			DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

			for (String key : reqParam.keySet()) {
				String[] values = reqParam.get(key);

				for (String value : values) {
					if (isValidDate(value)) {
						LocalDate localDate = LocalDate.parse(value, dateFormatter);
						LocalDateTime localDateTime = localDate.atStartOfDay().atZone(ZoneId.of(timeZone))
								.withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
						convertedDateTime.put(key, new String[] { localDateTime.format(dateTimeFormatter) });
					} else if (isValidDateTime(value)) {
						LocalDateTime localDateTime = LocalDateTime.parse(value, dateTimeFormatter);
						localDateTime = localDateTime.atZone(ZoneId.of(timeZone))
								.withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
						convertedDateTime.put(key, new String[] { localDateTime.format(dateTimeFormatter) });
					}

				}
			}

			HttpServletRequest wrappedRequest = new TimeZoneWrappedRequest(request, convertedDateTime);
			chain.doFilter(wrappedRequest, res);
		} else {
			chain.doFilter(request, res);
		}

	}

	@Override
	public void destroy() {
		//
	}

	public boolean isValidDate(String dateString) {
		try {
			DateUtils.parseDateStrictly(dateString, STD_DATEFORMAT);
			return true;
		} catch (ParseException e) {
			return false;
		}
	}

	public boolean isValidDateTime(String dateString) {
		try {
			DateUtils.parseDateStrictly(dateString, STD_DATETIMEFORMAT);
			return true;
		} catch (ParseException e) {
			return false;
		}
	}
}