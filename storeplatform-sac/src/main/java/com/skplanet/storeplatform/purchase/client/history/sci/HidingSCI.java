/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.purchase.client.history.sci;

import java.util.List;

import com.skplanet.storeplatform.framework.core.proxy.SCI;
import com.skplanet.storeplatform.purchase.client.history.vo.HidingScReq;
import com.skplanet.storeplatform.purchase.client.history.vo.HidingScRes;

/**
 * 구매 컴포넌트 인터페이스
 * 
 * Updated on : 2013-12-10 Updated by : 조용진, 엔텔스.
 */
@SCI
public interface HidingSCI {

	/**
	 * 구매내역 숨김처리.
	 * 
	 * @param hidingScRequest
	 *            요청정보
	 * @return List<HidingScResponse>
	 */
	public List<HidingScRes> updateHiding(HidingScReq hidingScRequest);

}
