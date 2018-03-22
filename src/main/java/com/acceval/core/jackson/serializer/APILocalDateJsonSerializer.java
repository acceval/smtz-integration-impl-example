package com.acceval.core.jackson.serializer;

import java.io.IOException;
import java.time.LocalDate;

import com.acceval.core.jackson.Fields;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class APILocalDateJsonSerializer extends JsonSerializer<LocalDate> {
	public static APILocalDateJsonSerializer INSTANCE = new APILocalDateJsonSerializer();

	private APILocalDateJsonSerializer() {
		super();
	}

	@Override
	public void serialize(LocalDate value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
		if (value == null) {
			gen.writeNull();
			return;
		}

		gen.writeStartObject();
		gen.writeNumberField(Fields.YEAR, value.getYear());
		gen.writeNumberField(Fields.MONTH, value.getMonthValue());
		gen.writeNumberField(Fields.DAY, value.getDayOfMonth());
		gen.writeEndObject();
	}
}
