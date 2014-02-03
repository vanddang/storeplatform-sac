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

import com.skplanet.storeplatform.purchase.client.history.vo.GiftConfirmScRequest;
import com.skplanet.storeplatform.purchase.client.history.vo.GiftConfirmScResponse;
import com.skplanet.storeplatform.purchase.client.history.vo.GiftReceiveScRequest;
import com.skplanet.storeplatform.purchase.client.history.vo.GiftReceiveScResponse;

/**
 * 선물확인 SAC Service 인터페이스
 * 
 * Updated on : 2013-09-01 Updated by :조용진, 엔텔스.
 */
public interface GiftSacService {

	/**
	 * 선물수신확인 체크.
	 * 
	 * @param giftReceiveScRequest
	 *            요청정보
	 * @return GiftReceiveScResponse
	 */
	public GiftReceiveScResponse searchGiftReceive(GiftReceiveScRequest giftReceiveScRequest);

	/**
	 * 선물수신 저장.
	 * 
	 * @param giftConfirmScRequest
	 *            요청정보
	 * @return GiftConfirmScResponse
	 */
	public GiftConfirmScResponse updateGiftConfirm(GiftConfirmScRequest giftConfirmScRequest);

}
