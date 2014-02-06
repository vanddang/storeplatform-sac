package com.skplanet.storeplatform.sac.api.v1.member;

import java.util.regex.Pattern;

import org.codehaus.jackson.map.ObjectMapper;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;

/**
 * ConvertMapper Object To JSON
 * 
 * Updated on : 2014. 1. 23. Updated by : 김경복, 부르칸.
 */
public class ConvertMapperUtils {

	/**
	 * <pre>
	 * Object To JSON.
	 * </pre>
	 * 
	 * @param obj
	 * @return
	 */
	public static String convertObjectToJson(Object obj) {
		String returnStr = "";
		try {
			ObjectMapper mapper = new ObjectMapper();
			if (obj != null)
				returnStr = mapper.writeValueAsString(obj);
		} catch (Exception e) {
			throw new StorePlatformException("", e);
		}
		return returnStr;
	}

	public static void main(String[] args) {
		boolean flag = false;
		String id = "email";
		String email = "kim.rejoice@gmail";
		String patternRegex = "^id|^email";

		flag = Pattern.matches(patternRegex, id);
		System.out.println(flag);
		flag = Pattern.matches(patternRegex, email);
	}
}
