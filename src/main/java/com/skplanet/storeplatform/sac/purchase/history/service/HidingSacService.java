/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.history.service;

import java.util.List;

import com.skplanet.storeplatform.purchase.client.history.vo.HidingRequest;
import com.skplanet.storeplatform.purchase.client.history.vo.HidingResponse;

/**
 * 구매 서비스 인터페이스
 * 
 * Updated on : 2013-12-06 Updated by : 조용진, 엔텔스.
 */
public interface HidingSacService {

	/**
	 * 구매내역 숨김처리.
	 * 
	 * @param hidingRequest
	 *            요청정보
	 * @return HidingResponse
	 */
	public List<HidingResponse> modifyHiding(HidingRequest hidingRequest);

}
