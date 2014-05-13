package com.skplanet.storeplatform.sac.member.common.util;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;

/**
 * ConvertMapper Object To JSON
 * 
 * Updated on : 2014. 1. 23. Updated by : 김경복, 부르칸.
 */
public class TestConvertMapperUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(TestConvertMapperUtils.class);

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
				LOGGER.debug(returnStr);
			}
		} catch (Exception e) {
			throw new StorePlatformException("", e);
		}
		return returnStr;
	}

}
