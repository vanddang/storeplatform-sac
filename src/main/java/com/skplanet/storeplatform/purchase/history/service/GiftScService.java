package com.skplanet.storeplatform.purchase.history.service;

import com.skplanet.storeplatform.purchase.client.history.vo.GiftConfirmScReq;
import com.skplanet.storeplatform.purchase.client.history.vo.GiftConfirmScRes;
import com.skplanet.storeplatform.purchase.client.history.vo.GiftReceiveScReq;
import com.skplanet.storeplatform.purchase.client.history.vo.GiftReceiveScRes;

/**
 * 구매 서비스 인터페이스
 * 
 * Updated on : 2013-12-06 Updated by : 조용진, 엔텔스.
 */
public interface GiftScService {

	/**
	 * 선물수신확인 체크.
	 * 
	 * @param giftReceiveScReq
	 *            요청정보
	 * @return GiftReceiveScRes
	 */
	public GiftReceiveScRes searchGiftReceive(GiftReceiveScReq giftReceiveScReq);

	/**
	 * 선물수신 저장.
	 * 
	 * @param giftConfirmScReq
	 *            요청정보
	 * @return GiftConfirmScRes
	 */
	public GiftConfirmScRes updateGiftConfirm(GiftConfirmScReq giftConfirmScReq);
}
