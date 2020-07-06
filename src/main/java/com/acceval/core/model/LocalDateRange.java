package com.acceval.core.model;

import java.time.LocalDate;

import com.acceval.core.util.DateUtil;

/**
 * TODO: Document this
 *
 * @author Julian
 */
public class LocalDateRange {
	private LocalDate from;
	private LocalDate to;

	public LocalDate getFrom() {
		return from;
	}

	public void setFrom(LocalDate from) {
		this.from = from;
	}

	public LocalDate getTo() {
		return to;
	}

	public void setTo(LocalDate to) {
		this.to = to;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj != null && obj instanceof LocalDateRange) {
			LocalDateRange toCompare = (LocalDateRange) obj;
			return DateUtil.LocalDateEquals(getFrom(), toCompare.getFrom()) && DateUtil.LocalDateEquals(getTo(), toCompare.getTo());
		}
		return super.equals(obj);
	}
}
