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
import com.skplanet.storeplatform.purchase.client.history.vo.GiftConfirmScReq;
import com.skplanet.storeplatform.purchase.client.history.vo.GiftConfirmScRes;
import com.skplanet.storeplatform.purchase.client.history.vo.GiftReceiveScReq;
import com.skplanet.storeplatform.purchase.client.history.vo.GiftReceiveScRes;

/**
 * 선물확인 SAC Service 인터페이스 구현체
 * 
 * Updated on : 2013-09-01 Updated by :조용진, 엔텔스.
 */
@Service
public class GiftSacServiceImpl implements GiftSacService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private GiftSCI giftSCI;

	/**
	 * 선물수신확인 체크.
	 * 
	 * @param giftReceiveScReq
	 *            요청정보
	 * @return GiftReceiveScRes
	 */
	@Override
	public GiftReceiveScRes searchGiftReceive(GiftReceiveScReq giftReceiveScReq) {

		GiftReceiveScRes giftReceiveScRes = new GiftReceiveScRes();
		giftReceiveScRes = this.giftSCI.searchGiftReceive(giftReceiveScReq);

		return giftReceiveScRes;
	}

	/**
	 * 선물수신 저장.
	 * 
	 * @param giftConfirmScReq
	 *            요청정보
	 * @return GiftConfirmScRes
	 */
	@Override
	public GiftConfirmScRes updateGiftConfirm(GiftConfirmScReq giftConfirmScReq) {
		GiftConfirmScRes giftConfirmScRes = new GiftConfirmScRes();
		giftConfirmScRes = this.giftSCI.updateGiftConfirm(giftConfirmScReq);

		return giftConfirmScRes;
	}

}
