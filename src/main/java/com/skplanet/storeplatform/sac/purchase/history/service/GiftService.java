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

import com.skplanet.storeplatform.purchase.client.history.vo.GiftConfirmRequest;
import com.skplanet.storeplatform.purchase.client.history.vo.GiftConfirmResponse;
import com.skplanet.storeplatform.purchase.client.history.vo.GiftReceiveRequest;
import com.skplanet.storeplatform.purchase.client.history.vo.GiftReceiveResponse;

/**
 * 선물확인 SAC Service 인터페이스
 * 
 * Updated on : 2013-09-01 Updated by :조용진, 엔텔스.
 */
public interface GiftService {

	/**
	 * 선물수신확인 체크.
	 * 
	 * @param giftReceiveRequest
	 *            선물수신확인 체크
	 * @return GiftReceiveResponse
	 */
	public GiftReceiveResponse searchGiftReceive(GiftReceiveRequest giftReceiveRequest);

	/**
	 * 선물수신.
	 * 
	 * @param giftConfirmRequest
	 *            선물수신
	 * @return GiftConfirmResponse
	 */
	public GiftConfirmResponse modifyGiftConfirm(GiftConfirmRequest giftConfirmRequest);

}
