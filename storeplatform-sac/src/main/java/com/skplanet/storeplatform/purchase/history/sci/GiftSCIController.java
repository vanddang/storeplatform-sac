/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.purchase.history.sci;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.skplanet.storeplatform.framework.integration.bean.LocalSCI;
import com.skplanet.storeplatform.purchase.client.history.sci.GiftSCI;
import com.skplanet.storeplatform.purchase.client.history.vo.GiftConfirmScReq;
import com.skplanet.storeplatform.purchase.client.history.vo.GiftConfirmScRes;
import com.skplanet.storeplatform.purchase.client.history.vo.GiftReceiveScReq;
import com.skplanet.storeplatform.purchase.client.history.vo.GiftReceiveScRes;
import com.skplanet.storeplatform.purchase.history.service.GiftScService;

/**
 * 구매 컴포넌트 인터페이스 컨트롤러
 * 
 * Updated on : 2013-12-20 Updated by : 조용진, 엔텔스.
 */
@LocalSCI
public class GiftSCIController implements GiftSCI {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private GiftScService giftScService;

	/**
	 * 선물수신 저장.
	 * 
	 * @param giftConfirmScReq
	 *            요청정보
	 * @return GiftConfirmScRes
	 */
	@Override
	public GiftConfirmScRes updateGiftConfirm(GiftConfirmScReq giftConfirmScReq) {
		this.logger.debug("PRCHS,GiftSCIController,SC,REQ,{}", giftConfirmScReq);
		return this.giftScService.updateGiftConfirm(giftConfirmScReq);
	}

	/**
	 * 선물수신확인 체크.
	 * 
	 * @param giftReceiveScReq
	 *            요청정보
	 * @return GiftReceiveScRes
	 */
	@Override
	public GiftReceiveScRes searchGiftReceive(GiftReceiveScReq giftReceiveScReq) {
		this.logger.debug("PRCHS,GiftSCIController,SC,REQ,{}", giftReceiveScReq);
		return this.giftScService.searchGiftReceive(giftReceiveScReq);
	}

}
