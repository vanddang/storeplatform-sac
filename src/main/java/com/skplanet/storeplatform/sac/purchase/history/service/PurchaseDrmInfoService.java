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

import com.skplanet.storeplatform.sac.client.internal.purchase.history.vo.PurchaseDrmInfoSacInReq;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.vo.PurchaseDrmInfoSacInRes;

/**
 * 구매DRM정보 Interface
 * 
 * Updated on : 2014-01-10 Updated by : 양주원, 엔텔스.
 */
public interface PurchaseDrmInfoService {

	/**
	 * 다운로드 여부에 따른 DRM 정보 수정.
	 * 
	 * @param request
	 *            DRM정보
	 * @return PurchaseDrmInfoSacInRes
	 */
	public PurchaseDrmInfoSacInRes updatePrchaseDrm(PurchaseDrmInfoSacInReq request);

}
