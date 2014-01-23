package com.skplanet.storeplatform.sac.api.v1.member;

import java.io.IOException;
import java.util.regex.Pattern;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * ConvertMapper Object To JSON
 * 
 * Updated on : 2014. 1. 23. Updated by : 김경복, 부르칸.
 */
public class ConvertMapperUtil {

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
			returnStr = mapper.writeValueAsString(obj);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return returnStr;
	}

	public static void main(String[] args) {
		boolean flag = false;
		String id = "id";
		String email = "email";

		String patternRegex = "^id|^email";

		flag = Pattern.matches(patternRegex, id);
		System.out.println(flag);
		flag = Pattern.matches(patternRegex, email);
		System.out.println(flag);

	}
}
