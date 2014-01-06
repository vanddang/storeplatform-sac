/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.external.idp.util;

/**
 * CommonUtil Class
 * 
 * Updated on : 2014. 1. 6. Updated by : Jeon.ByungYoul, SK planet.
 */
public class IdpCommonUtil {

	/**
	 * 보안 점검을 위해 <,>,",',&,%,!,-- 값 막기"
	 * 
	 * @param param
	 * @return
	 */
	public static String removeSpecial(String param) {
		if (param == null)
			param = "";
		else {
			param = param.replaceAll("<", "");
			param = param.replaceAll(">", "");
			param = param.replaceAll("\"", "");
			param = param.replaceAll("'", "");
			param = param.replaceAll("&", "");
			param = param.replaceAll("%", "");
			param = param.replaceAll("!", "");
			param = param.replaceAll("--", "");
			param = param.replaceAll("#", "");
			param = param.replaceAll("[/*]", "");
			param = param.replaceAll("[*/]", "");
			param = param.replace(Character.toString(Character.toChars(49824)[0]), " ");
		}

		return param;
	}
}
