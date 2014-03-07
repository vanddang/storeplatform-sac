package com.skplanet.storeplatform.sac.client.rest.util;

import org.codehaus.jackson.map.ObjectMapper;

public class SacRestConvertingUtils {

	private static ObjectMapper mapper = new ObjectMapper();

	public static String convertToJson(Object req) {
		try {
			return mapper.writeValueAsString(req);
		} catch (Exception e) {
			return "{}";
		}
	}

	public static <T> T convertToData(String json, Class<T> responseType) {
		T data;
		try {
			data = mapper.readValue(json, responseType);
		} catch (Exception e) {
			throw new RuntimeException("Failed to convert the json string to a data object.", e);
		}
		return data;
	}

}
