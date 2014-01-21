package com.skplanet.storeplatform.sac.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Convert Mapper Util
 * 
 * Updated on : 2014. 1. 21. Updated by : 김경복, 부르칸.
 */
public class ConvertMapperUtil {

	/**
	 * <pre>
	 * Convert Object : Object의 "null" 파라미터를 null 객체로 반환.
	 * </pre>
	 * 
	 * @param obj
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
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		}
		return returnObj;
	}
}
