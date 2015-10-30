/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 * 
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.internal.purchase.history.sci;

import com.skplanet.storeplatform.framework.core.proxy.SCI;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.vo.GiftConfirmSacInReq;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.vo.GiftConfirmSacInRes;

/**
 * 구매내역 회원정보 변경 Interface
 * 
 * Updated on : 2013. 12. 13. Updated by : ntels_yjw
 */
@SCI
public interface GiftConfirmInternalSCI {

	/**
	 * 선물수신 처리.
	 * 
	 * @param GiftConfirmSacInReq
	 *            요청정보
	 * @param requestHeader
	 *            헤더정보
	 * @return GiftConfirmSacInRes
	 */
	public GiftConfirmSacInRes modifyGiftConfirm(GiftConfirmSacInReq GiftConfirmSacInReq);

}
