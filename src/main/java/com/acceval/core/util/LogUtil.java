package com.acceval.core.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import ch.qos.logback.core.rolling.FixedWindowRollingPolicy;

public class LogUtil extends FixedWindowRollingPolicy {
	@Override
	public void setFileNamePattern(String fnp) {
		String fnp1 = fnp.substring(0, 10);
		String fnp2 = fnp.substring(11, fnp.length());
		super.setFileNamePattern(fnp1 + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + fnp2);
	}
}