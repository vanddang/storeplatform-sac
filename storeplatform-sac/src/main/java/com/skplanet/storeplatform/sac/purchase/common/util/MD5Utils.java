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

import java.security.MessageDigest;

/**
 * 
 * MD5 Digest Util
 * 
 * Updated on : 2014. 3. 14. Updated by : 이승택, nTels.
 */
public class MD5Utils {

	/**
	 * 
	 * <pre>
	 * hex형태로 Digest.
	 * </pre>
	 * 
	 * @param src
	 * @return
	 * @throws Exception
	 */
	public static String digestInHexFormat(String src) throws Exception {
		StringBuffer sb = new StringBuffer();

		byte[] mdbytes = MessageDigest.getInstance("MD5").digest(src.getBytes());

		String hex = null;
		for (int i = 0; i < mdbytes.length; i++) {
			hex = Integer.toHexString(0xFF & mdbytes[i]);
			// if (hex.length() < 2) {
			// sb.append('0');
			// }
			sb.append(hex);
		}

		return sb.toString();
	}
}
