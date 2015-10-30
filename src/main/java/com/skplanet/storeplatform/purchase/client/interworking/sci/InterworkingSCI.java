/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.purchase.client.interworking.sci;

import com.skplanet.storeplatform.framework.core.proxy.SCI;
import com.skplanet.storeplatform.purchase.client.interworking.vo.InterworkingScReq;

/**
 * 인터파크 및 씨네21 저장.
 * 
 * Updated on : 2014. 2. 5. Updated by : 조용진, NTELS.
 */
@SCI
public interface InterworkingSCI {

	/**
	 * 구매후처리 (인터파크,씨네21).
	 * 
	 * @param interworkingReq
	 *            요청정보
	 * @return int
	 */
	public int createInterworking(InterworkingScReq interworkingReq);
}
