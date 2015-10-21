/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */

package com.skplanet.storeplatform.sac.purchase.common.util;

import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.sac.purchase.constant.PurchaseConstants;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * 값을 조정하는 각종 기능을 한다.
 *
 * Updated on : 15. 9. 9.
 * Updated by : 황민규, SK 플래닛.
 */
public class AdjustValueUtil {

	public static void main(String []args)
	{
		String org = "11:0;12:0;13:0";

		System.out.println(adjuestValue(org, "12:4000;11:300"));
	}


	/**
	 * Extends value.
	 *
	 * @param orgContents the orgContents
	 * @param replacementContent the replacementContent
	 * @param separator the separator
	 * @param delimiter the delimiter
	 * @param sortKey the sort key
	 * @return the string
	 */
	public static String adjuestValue(String orgContents, String replacementContent, String separator, String delimiter, boolean sortKey){
		if(replacementContent ==null)
			return orgContents;

		String result;
		Map<String, String> resultMap;
		Set<String> keySet;

		// classify orgContents sample data
		resultMap = classifyValuesToMap(orgContents, separator, delimiter);
		resultMap.putAll(classifyValuesToMap(replacementContent, separator, delimiter));

		if(sortKey) {
			keySet = new TreeSet<String>();
			keySet.addAll(resultMap.keySet());
			result = concatStr(keySet, resultMap, separator, delimiter);
		} else {
			result = concatStr(resultMap, separator, delimiter);
		}
		return StringUtils.defaultString(result);
	}

	public static String adjuestValue(String orgContents, String replacementContent, String separator){
		return adjuestValue(orgContents, replacementContent, separator, PurchaseConstants.DELIMITER, false);
	}

	public static String adjuestValue(String orgContents, String replacementContent){
		return adjuestValue(orgContents, replacementContent, PurchaseConstants.SEPARATOR, PurchaseConstants.DELIMITER, false);
	}

	public static String adjuestValue(String orgContents, String replacementContent, boolean sortKey){
		return adjuestValue(orgContents, replacementContent, PurchaseConstants.SEPARATOR, PurchaseConstants.DELIMITER, sortKey);
	}

	/**
	 * Classify values to map.
	 *
	 * @param str the str
	 * @param separator the separator
	 * @param delimiter the delimiter
	 * @return the map
	 */
	public static Map<String, String> classifyValuesToMap(String str, String separator, String delimiter)
	{
		Map<String, String> resultMap = new LinkedHashMap<String, String>();

		for(String sample : StringUtils.splitPreserveAllTokens(StringUtils.defaultString(str), separator)) {
			String key = StringUtils.substringBefore(sample, delimiter);
			if(StringUtils.isEmpty(key)) continue;
			String value = StringUtils.substringAfter(sample, delimiter);
			resultMap.put(key,value);
		}
		return resultMap;
	}

	private static String concatStr(Map<String,String> dataMap, String separator, String delimiter)
	{
		return concatStr(dataMap.keySet(), dataMap, separator, delimiter);
	}

	private static String concatStr(Set<String> keySet, Map<String,String> dataMap, String separator, String delimiter)
	{
		StringBuffer sb = new StringBuffer();

		for(String key : keySet)
		{
			sb.append(key);
			sb.append(delimiter);
			sb.append(dataMap.get(key));
			sb.append(separator);
		}
		if(sb.length()>0) sb.deleteCharAt(sb.length()-1);
		return sb.toString();
	}
}
