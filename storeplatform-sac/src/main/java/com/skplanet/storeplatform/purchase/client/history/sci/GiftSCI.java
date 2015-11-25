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

import com.skplanet.storeplatform.framework.core.proxy.SCI;
import com.skplanet.storeplatform.purchase.client.history.vo.GiftConfirmScReq;
import com.skplanet.storeplatform.purchase.client.history.vo.GiftConfirmScRes;
import com.skplanet.storeplatform.purchase.client.history.vo.GiftReceiveScReq;
import com.skplanet.storeplatform.purchase.client.history.vo.GiftReceiveScRes;

/**
 * 구매 컴포넌트 인터페이스
 * 
 * Updated on : 2013-12-10 Updated by : 조용진, 엔텔스.
 */
@SCI
public interface GiftSCI {

	/**
	 * 선물수신확인 체크.
	 * 
	 * @param giftReceiveRequest
	 *            선물수신확인 체크
	 * @return GiftReceiveResponse
	 */
	public GiftReceiveScRes searchGiftReceive(GiftReceiveScReq giftReceiveRequest);

	/**
	 * 선물수신 저장.
	 * 
	 * @param giftConfirmRequest
	 *            선물수신 저장
	 * @return GiftConfirmResponse
	 */
	public GiftConfirmScRes updateGiftConfirm(GiftConfirmScReq giftConfirmRequest);
}
