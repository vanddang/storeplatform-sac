package com.skplanet.storeplatform.sac.member.common.util;

import org.codehaus.jackson.map.ObjectMapper;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;

/**
 * ConvertMapper Object To JSON
 * 
 * Updated on : 2014. 1. 23. Updated by : 김경복, 부르칸.
 */
public class TestConvertMapperUtils {

	/**
	 * <pre>
	 * Object To JSON.
	 * </pre>
	 * 
	 * @param obj
	 *            Object
	 * @return String
	 */
	public static String convertObjectToJson(Object obj) {
		String returnStr = "";
		try {
			ObjectMapper mapper = new ObjectMapper();
			if (obj != null) {
				returnStr = mapper.writeValueAsString(obj);
				System.out.println(returnStr);
			}
		} catch (Exception e) {
			throw new StorePlatformException("", e);
		}
		return returnStr;
	}

}
