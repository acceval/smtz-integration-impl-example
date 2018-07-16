package com.acceval.core.jackson.deserializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import com.acceval.core.jackson.Fields;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class APILocalDateTimeJsonDeserializer extends JsonDeserializer<LocalDateTime> {
	public static final APILocalDateTimeJsonDeserializer INSTANCE = new APILocalDateTimeJsonDeserializer();

	private APILocalDateTimeJsonDeserializer() {
		super();
	}

	@Override
	public LocalDateTime deserialize(JsonParser p, DeserializationContext ctx)
			throws IOException, JsonProcessingException {
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

		field = p.nextFieldName();
		val = p.nextIntValue(-1);
		mapVal.put(field, val);

		field = p.nextFieldName();
		val = p.nextIntValue(-1);
		mapVal.put(field, val);

		field = p.nextFieldName();
		val = p.nextIntValue(-1);
		mapVal.put(field, val);

		if (p.nextToken() != JsonToken.END_OBJECT) {
			ctx.reportWrongTokenException(p, JsonToken.END_OBJECT, "Expected end object for date");
		}

		return LocalDateTime.of(
				mapVal.getOrDefault(Fields.YEAR, -1),
				mapVal.getOrDefault(Fields.MONTH, -1),
				mapVal.getOrDefault(Fields.DAY, -1),
				mapVal.getOrDefault(Fields.HOUR, -1),
				mapVal.getOrDefault(Fields.MINUTE, -1),
				mapVal.getOrDefault(Fields.SECOND, -1)
		);
	}
}