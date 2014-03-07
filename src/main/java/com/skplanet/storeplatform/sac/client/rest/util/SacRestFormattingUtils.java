package com.skplanet.storeplatform.sac.client.rest.util;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;

public class SacRestFormattingUtils {

	public static String formatUrl(String scheme, String host, String path, Object param) {
		String query = formatQuery(param);
		return formatUrl(scheme, host, path, query);
	}

	public static String formatUrl(String scheme, String host, String path, String query) {
		return scheme + "://" + host + path + "?" + query;
	}

	public static String formatUrl(String scheme, String host, String path) {
		return scheme + "://" + host + path;
	}

	@SuppressWarnings("unchecked")
	public static String formatQuery(Object obj) {
		ObjectMapper mapper = new ObjectMapper();

		Map<String, String> map;
		if (obj instanceof Map) {
			map = (Map<String, String>) obj;
		} else {
			map = mapper.convertValue(obj, Map.class);
		}

		String queryString = "";
		for (Map.Entry<String, String> entry : map.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			if (StringUtils.isNotEmpty(value)) {
				queryString += key + "=" + value + "&";
			}
		}

		return StringUtils.substringBeforeLast(queryString, "&");
	}

}
