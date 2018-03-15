package com.acceval.core.jackson.deserializer;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import com.acceval.core.jackson.Fields;

public class APILocalDateJsonDeserializer extends JsonDeserializer<LocalDate> {
	public static APILocalDateJsonDeserializer INSTANCE = new APILocalDateJsonDeserializer();

	private APILocalDateJsonDeserializer() {
		super();
	}

	@Override
	public LocalDate deserialize(JsonParser p, DeserializationContext ctx) throws IOException, JsonProcessingException {
		if (p.currentToken() == JsonToken.VALUE_NULL) {
			return null;
		}

		if (!p.isExpectedStartObjectToken()) {
			ctx.reportWrongTokenException(p, JsonToken.START_OBJECT, "Expected start object");
		}

		if (p.nextToken() == JsonToken.END_OBJECT) {
			return null;
		}

		Map<String, Integer> mapVal = new HashMap<>(3);

		String field = p.getCurrentName();
		int val = p.nextIntValue(-1);
		mapVal.put(field, val);

		field = p.nextFieldName();
		val = p.nextIntValue(-1);
		mapVal.put(field, val);

		field = p.nextFieldName();
		val = p.nextIntValue(-1);
		mapVal.put(field, val);

		return LocalDate.of(
				mapVal.getOrDefault(Fields.YEAR, -1),
				mapVal.getOrDefault(Fields.MONTH, -1),
				mapVal.getOrDefault(Fields.DAY, -1)
		);
	}
}
