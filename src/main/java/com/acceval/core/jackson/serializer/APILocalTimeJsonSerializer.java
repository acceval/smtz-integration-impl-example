package com.acceval.core.jackson.serializer;

import java.io.IOException;
import java.time.LocalTime;

import com.acceval.core.jackson.Fields;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class APILocalTimeJsonSerializer extends JsonSerializer<LocalTime> {
	public static APILocalTimeJsonSerializer INSTANCE = new APILocalTimeJsonSerializer();

	private APILocalTimeJsonSerializer() {
		super();
	}

	@Override
	public void serialize(LocalTime value, JsonGenerator gen, SerializerProvider serializers)
			throws IOException, JsonProcessingException {
		if (value == null) {
			gen.writeNull();
			return;
		}

		gen.writeStartObject();
		gen.writeNumberField(Fields.HOUR, value.getHour());
		gen.writeNumberField(Fields.MINUTE, value.getMinute());
		gen.writeNumberField(Fields.SECOND, value.getSecond());
		gen.writeEndObject();
	}
}
