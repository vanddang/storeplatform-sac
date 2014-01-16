/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.prototype.service;

import com.skplanet.storeplatform.sac.client.purchase.vo.prototype.CheckPurchaseReq;
import com.skplanet.storeplatform.sac.client.purchase.vo.prototype.CheckPurchaseRes;
import com.skplanet.storeplatform.sac.client.purchase.vo.prototype.MyPagePurchaseHistoryReq;
import com.skplanet.storeplatform.sac.client.purchase.vo.prototype.MyPagePurchaseHistoryRes;

/**
 * [Prototype] 구매내역 서비스 인터페이스
 * 
 * Updated on : 2013. 12. 10. Updated by : 이승택, nTels.
 */
public interface PurchasePrototypeService {

	/**
	 * <pre>
	 * MyPage 구매내역 조회.
	 * </pre>
	 * 
	 * @param paramVO
	 *            구매내역 조회조건
	 * @return MyPage 구매내역
	 */
	public MyPagePurchaseHistoryRes searchPurchaseList(MyPagePurchaseHistoryReq paramVO);

	/**
	 * <pre>
	 * 기구매 체크.
	 * </pre>
	 * 
	 * @param paramVO
	 *            구매내역 조회조건
	 * @return MyPage 구매내역
	 */
	public CheckPurchaseRes checkPurchase(CheckPurchaseReq paramVO);
}
