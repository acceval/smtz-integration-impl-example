package com.acceval.core.jackson.module;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.acceval.core.jackson.deserializer.APILocalDateJsonDeserializer;
import com.acceval.core.jackson.deserializer.APILocalDateTimeJsonDeserializer;
import com.acceval.core.jackson.serializer.APILocalDateJsonSerializer;
import com.acceval.core.jackson.serializer.APILocalDateTimeJsonSerializer;
import com.fasterxml.jackson.databind.module.SimpleModule;

public class APIJavaTimeModule extends SimpleModule {
	public APIJavaTimeModule() {
		super();

		addSerializer(LocalDate.class, APILocalDateJsonSerializer.INSTANCE);
		addDeserializer(LocalDate.class, APILocalDateJsonDeserializer.INSTANCE);

		addSerializer(LocalDateTime.class, APILocalDateTimeJsonSerializer.INSTANCE);
		addDeserializer(LocalDateTime.class, APILocalDateTimeJsonDeserializer.INSTANCE);
	}
}
