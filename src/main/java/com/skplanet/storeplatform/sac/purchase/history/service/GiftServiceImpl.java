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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.purchase.client.history.sci.GiftSCI;
import com.skplanet.storeplatform.purchase.client.history.vo.GiftConfirmRequest;
import com.skplanet.storeplatform.purchase.client.history.vo.GiftConfirmResponse;
import com.skplanet.storeplatform.purchase.client.history.vo.GiftReceiveRequest;
import com.skplanet.storeplatform.purchase.client.history.vo.GiftReceiveResponse;

/**
 * 선물확인 SAC Service 인터페이스 구현체
 * 
 * Updated on : 2013-09-01 Updated by :조용진, 엔텔스.
 */
@Service
public class GiftServiceImpl implements GiftService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private GiftSCI giftSCI;

	/**
	 * 선물수신확인 체크.
	 * 
	 * @param GiftReceiveRequest
	 *            선물수신확인 체크
	 * @return GiftReceiveResponse
	 */
	@Override
	public GiftReceiveResponse searchGiftReceive(GiftReceiveRequest giftReceiveRequest) {

		GiftReceiveResponse giftReceiveResponse = new GiftReceiveResponse();
		giftReceiveResponse = this.giftSCI.searchGiftReceive(giftReceiveRequest);

		return giftReceiveResponse;
	}

	/**
	 * 선물수신.
	 * 
	 * @param GiftConfirmRequest
	 *            선물수신
	 * @return GiftConfirmResponse
	 */
	@Override
	public GiftConfirmResponse modifyGiftConfirm(GiftConfirmRequest giftConfirmRequest) {
		GiftConfirmResponse giftConfirmResponse = new GiftConfirmResponse();
		giftConfirmResponse = this.giftSCI.modifyGiftConfirm(giftConfirmRequest);

		return giftConfirmResponse;
	}

}
