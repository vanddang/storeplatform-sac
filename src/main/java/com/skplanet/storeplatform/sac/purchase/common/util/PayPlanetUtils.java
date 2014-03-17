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

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.sac.purchase.order.MD5Util;

/**
 * Class 설명
 * 
 * Updated on : 2014. 3. 14. Updated by : nTels_cswoo81, nTels.
 */
public class PayPlanetUtils {

	public static String makeToken(String authKey, String orderId, String amtPurchase, String mid) {

		String token;

		try {

			StringBuffer sb = new StringBuffer(128);
			sb.append(authKey).append(orderId).append(amtPurchase).append(mid);

			token = sb.toString();

			token = MD5Util.digestInHexFormat(token);

		} catch (Exception e) {
			throw new StorePlatformException("SAC_PUR_8133");
		}

		return token;

	}

}
