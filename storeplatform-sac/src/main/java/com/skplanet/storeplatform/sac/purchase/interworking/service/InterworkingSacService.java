/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.interworking.service;

import com.skplanet.storeplatform.sac.purchase.interworking.vo.InterworkingSacReq;

/**
 * 구매후처리 (인터파크,씨네21).
 * 
 * Updated on : 2014. 2. 5. Updated by : 조용진, NTELS.
 */

public interface InterworkingSacService {

	/**
	 * 구매후처리 (인터파크,씨네21).
	 * 
	 * @param interworkingSacReq
	 *            요청정보
	 * @return
	 */
	public void createInterworking(InterworkingSacReq interworkingSacReq);
}
