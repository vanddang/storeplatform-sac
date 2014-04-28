package com.skplanet.storeplatform.sac.member.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;

/**
 * Convert Mapper Util
 * 
 * Updated on : 2014. 1. 21. Updated by : 김경복, 부르칸.
 */
public class ConvertMapperUtils {

	private static final String[] MASKING_PARAMS = { "userPw", "oldPassword", "newPassword", "sellerPw", "subSellerPw",
			"oldPw", "newPw" };
	private static final String MASKING_VALUE = "*";

	/**
	 * <pre>
	 * Convert Object : Object의 "null" 파라미터를 null 객체로 반환.
	 * </pre>
	 * 
	 * @param obj
	 *            Object
	 * @return Object
	 */
	public static Object convertObject(Object obj) {

		String setMethodString = "set";
		String methodString = null;
		String keyAttribute = null;
		Object returnObj = null;
		try {
			returnObj = obj.getClass().newInstance();
			Field[] fields = obj.getClass().getDeclaredFields();
			for (int i = 0; i <= fields.length - 1; i++) {
				fields[i].setAccessible(true);
				keyAttribute = fields[i].getName();
				Method[] methods = returnObj.getClass().getMethods();
				methodString = setMethodString + keyAttribute.substring(0, 1).toUpperCase() + keyAttribute.substring(1);
				for (int k = 0; k <= methods.length - 1; k++) {
					if (methodString.equals(methods[k].getName())) {
						if (fields[i].get(obj) != null && !((String) fields[i].get(obj)).toLowerCase().equals("null"))
							methods[k].invoke(returnObj, fields[i].get(obj));
					}
				}
			}
		} catch (Exception e) {
			throw new StorePlatformException("SAC_MEM_0098", e);
		}
		return returnObj;
	}

	/**
	 * <pre>
	 * Object To JSON.
	 * MASKING_PARAMS : 마스킹이 필요한 필드에 MASKING_VALUE 로 치환
	 * </pre>
	 * 
	 * @param obj
	 *            Object
	 * @return String
	 */
	public static String convertObjectToJson(Object obj) {
		String returnStr = "";
		String setMethodPrefix = "set";
		String methodString = null;
		String keyAttribute = null;
		// return value
		StringBuffer rv = null;
		List<String> reflectionFields = Arrays.asList(MASKING_PARAMS);
		Object returnObj = null;
		try {
			returnObj = obj.getClass().newInstance();
			// Fields
			Field[] fields = obj.getClass().getDeclaredFields();
			// Methods
			Method[] methods = obj.getClass().getMethods();

			// Fields [for - Start]
			for (int i = 0; i < fields.length; i++) {
				// 접근 허용
				fields[i].setAccessible(true);
				// Field Name
				keyAttribute = fields[i].getName();
				// SetMethod Name 추출
				methodString = setMethodPrefix + keyAttribute.substring(0, 1).toUpperCase() + keyAttribute.substring(1);

				// Methods [for - Start]
				for (int j = 0; j < methods.length; j++) {
					// 접근 허용
					methods[j].setAccessible(true);
					// Set Method
					if (StringUtils.equals(methodString, methods[j].getName())) {

						// ReflectionFields [for - Start]
						for (String reflectionField : reflectionFields) {

							// reflectionField
							if (StringUtils.equals(keyAttribute, reflectionField)) {
								// masking value
								if (fields[i].get(obj) instanceof String) {
									rv = new StringBuffer();
									String value = (String) fields[i].get(obj);
									for (int k = 0; k < value.length(); k++) {
										rv.append(MASKING_VALUE);
									}
								}
								// MASKING_VALUE => invoke
								obj.getClass().getMethod(methodString, String.class).invoke(returnObj, rv.toString());
								break;
							} else {
								methods[j].invoke(returnObj, fields[i].get(obj));
							}

						}// ReflectionFields [for - End]
					}

				}// Methods [for - End]

			}// Fields [for - End]

			ObjectMapper mapper = new ObjectMapper();
			// JSon String
			returnStr = mapper.writeValueAsString(returnObj);

		} catch (Exception e) {
			throw new StorePlatformException("SAC_MEM_0099", e);
		}
		return returnStr;
	}
}
