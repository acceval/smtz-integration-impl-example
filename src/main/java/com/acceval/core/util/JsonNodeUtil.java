package com.acceval.core.util;

import java.util.Iterator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class JsonNodeUtil {

	public static String getAsString(JsonNode node, String property) {
		JsonNode jNode = recursiveProperty(node, property);
		return jNode == null ? null : jNode.asText();
	}

	public static Long getAsLong(JsonNode node, String property) {
		JsonNode jNode = recursiveProperty(node, property);
		return jNode == null ? null : jNode.asLong();
	}

	public static Double getAsDouble(JsonNode node, String property) {
		JsonNode jNode = recursiveProperty(node, property);
		return jNode == null ? null : jNode.asDouble();
	}

	public static Integer getAsInteger(JsonNode node, String property) {
		JsonNode jNode = recursiveProperty(node, property);
		return jNode == null ? null : jNode.asInt();
	}

	public static Boolean getAsBoolean(JsonNode node, String property) {
		JsonNode jNode = recursiveProperty(node, property);
		return jNode == null ? null : jNode.asBoolean();
	}

	public static JsonNode get(JsonNode node, String property) {
		JsonNode jNode = recursiveProperty(node, property);
		return jNode;
	}

	public static Iterator<JsonNode> getAsCollection(JsonNode node, String property) {
		JsonNode jNode = recursiveProperty(node, property);
		if (jNode != null && jNode instanceof ArrayNode) {
			return ((ArrayNode) jNode).elements();
		}
		return null;
	}
	
	public static Object convertJsonNode(JsonNode node, Class<?> clz) {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());				
		try {
			return objectMapper.treeToValue(node, clz);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Object getAsObject(JsonNode node, String property, Class<?> clz) {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		JsonNode nodeProp = get(node, property);
		if (nodeProp == null) return null;
		try {
			return objectMapper.treeToValue(nodeProp, clz);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static JsonNode recursiveProperty(JsonNode node, String property) {
		String[] split = property.split("[.]");
		for (String key : split) {
			if (node.has(key)) {
				node = node.get(key);
			} else {
				return null;
			}
		}

		return node;
	}
}
