package com.acceval.core.util;

import java.util.Iterator;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

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

	public static Iterator<JsonNode> getAsCollection(JsonNode node, String property) {
		JsonNode jNode = recursiveProperty(node, property);
		if (jNode != null && jNode instanceof ArrayNode) {
			return ((ArrayNode) jNode).elements();
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