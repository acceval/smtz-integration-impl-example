package com.acceval.core.jackson.serializer;

import java.io.IOException;
import java.time.LocalDateTime;

import com.acceval.core.jackson.Fields;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class APILocalDateTimeJsonSerializer extends JsonSerializer<LocalDateTime> {
	public static final APILocalDateTimeJsonSerializer INSTANCE =  new APILocalDateTimeJsonSerializer();

	private APILocalDateTimeJsonSerializer() {
		super();
	}

	@Override
	public void serialize(LocalDateTime value, JsonGenerator gen,
			SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
		if (value == null) {
			gen.writeNull();
			return;
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
