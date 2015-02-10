package com.skplanet.storeplatform.sac.other.common.util;

import org.codehaus.jackson.map.ObjectMapper;

/**
 * Convert Object Mepper Utils.
 * 
 * Updated on : 2015. 2. 6. Updated by : 심대진, 다모아 솔루션.
 */
public class ObjectMapperUtils {

	/**
	 * <pre>
	 * Object를 Json String으로 변환.
	 * </pre>
	 * 
	 * @param obj
	 * @return
	 */
	public static String convertObjectToJson(Object obj) {
		String returnStr = "";
		try {
			ObjectMapper mapper = new ObjectMapper();
			returnStr = mapper.writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return returnStr;
	}

}
