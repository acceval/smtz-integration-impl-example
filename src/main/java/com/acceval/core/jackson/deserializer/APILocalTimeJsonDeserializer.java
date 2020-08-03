package com.acceval.core.jackson.deserializer;

import java.io.IOException;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

import com.acceval.core.jackson.Fields;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class APILocalTimeJsonDeserializer extends JsonDeserializer<LocalTime> {
	public static APILocalTimeJsonDeserializer INSTANCE = new APILocalTimeJsonDeserializer();

	private APILocalTimeJsonDeserializer() {
		super();
	}

	@Override
	public LocalTime deserialize(JsonParser p, DeserializationContext ctx) throws IOException, JsonProcessingException {
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

		if (p.nextToken() != JsonToken.END_OBJECT) {
			ctx.reportWrongTokenException(p, JsonToken.END_OBJECT, "Expected end object for time");
		}

		return LocalTime.of(mapVal.getOrDefault(Fields.HOUR, -1), mapVal.getOrDefault(Fields.MINUTE, -1),
				mapVal.getOrDefault(Fields.SECOND, -1));
	}
}
