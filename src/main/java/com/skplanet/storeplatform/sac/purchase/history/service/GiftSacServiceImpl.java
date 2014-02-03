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
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.purchase.client.history.sci.GiftSCI;
import com.skplanet.storeplatform.purchase.client.history.vo.GiftConfirmScRequest;
import com.skplanet.storeplatform.purchase.client.history.vo.GiftConfirmScResponse;
import com.skplanet.storeplatform.purchase.client.history.vo.GiftReceiveScRequest;
import com.skplanet.storeplatform.purchase.client.history.vo.GiftReceiveScResponse;

/**
 * 선물확인 SAC Service 인터페이스 구현체
 * 
 * Updated on : 2013-09-01 Updated by :조용진, 엔텔스.
 */
@Service
@Transactional
public class GiftSacServiceImpl implements GiftSacService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private GiftSCI giftSCI;

	/**
	 * 선물수신확인 체크.
	 * 
	 * @param giftReceiveScRequest
	 *            요청정보
	 * @return GiftReceiveScResponse
	 */
	@Override
	public GiftReceiveScResponse searchGiftReceive(GiftReceiveScRequest giftReceiveScRequest) {

		GiftReceiveScResponse giftReceiveScResponse = new GiftReceiveScResponse();
		giftReceiveScResponse = this.giftSCI.searchGiftReceive(giftReceiveScRequest);

		return giftReceiveScResponse;
	}

	/**
	 * 선물수신 저장.
	 * 
	 * @param giftConfirmScRequest
	 *            요청정보
	 * @return GiftConfirmScResponse
	 */
	@Override
	public GiftConfirmScResponse updateGiftConfirm(GiftConfirmScRequest giftConfirmScRequest) {
		GiftConfirmScResponse giftConfirmScResponse = new GiftConfirmScResponse();
		giftConfirmScResponse = this.giftSCI.updateGiftConfirm(giftConfirmScRequest);

		return giftConfirmScResponse;
	}

}
