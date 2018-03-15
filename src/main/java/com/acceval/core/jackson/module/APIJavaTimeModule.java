package com.acceval.core.jackson.module;

import java.time.LocalDate;

import com.fasterxml.jackson.databind.module.SimpleModule;

import com.acceval.core.jackson.deserializer.APILocalDateJsonDeserializer;
import com.acceval.core.jackson.serializer.APILocalDateJsonSerializer;

public class APIJavaTimeModule extends SimpleModule {
	public APIJavaTimeModule() {
		super();

		addSerializer(LocalDate.class, APILocalDateJsonSerializer.INSTANCE);
		addDeserializer(LocalDate.class, APILocalDateJsonDeserializer.INSTANCE);
	}
}
