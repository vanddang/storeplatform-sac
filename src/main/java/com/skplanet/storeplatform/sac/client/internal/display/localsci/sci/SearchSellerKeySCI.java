/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.internal.display.localsci.sci;

import com.skplanet.storeplatform.framework.core.proxy.SCI;

/**
 * AID를 이용하여 SellerKey 조회
 * 
 * Updated on : 2014. 02. 13. Updated by : 이석희
 */
@SCI
public interface SearchSellerKeySCI {

	/**
	 * 입력받은 Aid로 SellerKey를 조회한다.
	 * 
	 * @param aid
	 *            aid.
	 */
	public String searchSellerKeyForAid(String aid);

}
