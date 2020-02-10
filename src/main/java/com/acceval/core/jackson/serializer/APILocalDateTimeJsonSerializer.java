package com.acceval.core.jackson.serializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;

import com.acceval.core.jackson.Fields;
import com.acceval.core.security.PrincipalUtil;
import com.acceval.core.service.TimezoneService;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.github.jknack.handlebars.internal.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

public class APILocalDateTimeJsonSerializer extends JsonSerializer<LocalDateTime> {
	public static final APILocalDateTimeJsonSerializer INSTANCE = new APILocalDateTimeJsonSerializer();

	private APILocalDateTimeJsonSerializer() {
		super();
	}

	@Autowired
	TimezoneService timezoneService;

	@Override
	public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider serializerProvider)
			throws IOException, JsonProcessingException {
		if (value == null) {
			gen.writeNull();
			return;
		}

		String timeZone = PrincipalUtil.getTimeZone();
		if (StringUtils.isNotBlank(timeZone)) {
			//String customTimeZone = timezoneService.convertToUTCTimeZoneId(timeZone);
			value = value.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of(timeZone)).toLocalDateTime();
		}

		gen.writeStartObject();
		gen.writeNumberField(Fields.YEAR, value.getYear());
		gen.writeNumberField(Fields.MONTH, value.getMonthValue());
		gen.writeNumberField(Fields.DAY, value.getDayOfMonth());
		gen.writeNumberField(Fields.HOUR, value.getHour());
		gen.writeNumberField(Fields.MINUTE, value.getMinute());
		gen.writeNumberField(Fields.SECOND, value.getSecond());
		gen.writeEndObject();
	}
}
