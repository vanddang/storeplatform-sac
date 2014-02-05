/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.runtime.acl.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
*
* ACL 관련 유틸리티
*
* Updated on : 2014. 2. 5.
* Updated by : 서대영, SK 플래닛
*/
public class AclUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(AclUtils.class);

	private static final int TIMEOUT = 10; // 10 sec (Temporary)

	/**
	 * <pre>
	 * 요청 시간 초과 여부
	 * </pre>
	 * @param requestTimestamp
	 * @return
	 */
	public static boolean isTimeOut(String requestTimestamp) {
		long requestTimeInt;
		try {
			requestTimeInt = Long.parseLong(requestTimestamp);
		} catch (NumberFormatException e) {
			return false;
		}

		long currentTimeInt = getTimestamp();
		long timeDifference = currentTimeInt - requestTimeInt;

		LOGGER.debug("RequestTime : {}, CurrentTime : {}, TimeDifference : {},", requestTimeInt, currentTimeInt, timeDifference);

		if (timeDifference >= 0 && timeDifference <= TIMEOUT) {
			return true;
		} else {
			return false;
		}
	}

	public static long getTimestamp() {
		return System.currentTimeMillis() / 1000;
	}

}
