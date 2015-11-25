/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 * 
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.purchase.history.service;

import com.skplanet.storeplatform.purchase.client.history.vo.PurchaseDrmInfoSc;
import com.skplanet.storeplatform.purchase.client.history.vo.PurchaseDrmInfoScReq;
import com.skplanet.storeplatform.purchase.client.history.vo.PurchaseDrmInfoScRes;

/**
 * 구매DRM정보 Interface
 * 
 * Updated on : 2014-01-10 Updated by : 양주원, 엔텔스.
 */
public interface PurchaseDrmInfoScService {

	/**
	 * 다운로드 여부에 따른 DRM 정보 수정.
	 * 
	 * @param request
	 *            DRM정보
	 * @return PurchaseDrmInfoScRes
	 */
	public PurchaseDrmInfoScRes updatePrchaseDrm(PurchaseDrmInfoScReq request);

	/**
	 * 구매상세내역조회.
	 * 
	 * @param request
	 *            DRM정보
	 * @return PurchaseDrmInfoScRes
	 */
	public PurchaseDrmInfoSc selectPrchsDtl(PurchaseDrmInfoScReq request);
}
