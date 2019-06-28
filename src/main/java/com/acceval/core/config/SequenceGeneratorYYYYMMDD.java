package com.acceval.core.config;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

public class SequenceGeneratorYYYYMMDD extends SequenceStyleGenerator {

	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
		long seqNum = 0L;
		LocalDate localDate = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		String result = localDate.format(formatter);

		try {
			seqNum = (long) super.generate(session, object);
			return Long.valueOf(result + StringUtils.leftPad(String.valueOf(seqNum), 7, '0'));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Long.valueOf(result + StringUtils.leftPad(String.valueOf(seqNum), 7, '0'));
	}

}
