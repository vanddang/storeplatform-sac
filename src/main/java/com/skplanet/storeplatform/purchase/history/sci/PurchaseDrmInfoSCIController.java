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

import org.springframework.beans.factory.annotation.Autowired;

import com.skplanet.storeplatform.framework.integration.bean.LocalSCI;
import com.skplanet.storeplatform.purchase.client.history.sci.PurchaseDrmInfoSCI;
import com.skplanet.storeplatform.purchase.client.history.vo.PurchaseDrmInfoSc;
import com.skplanet.storeplatform.purchase.client.history.vo.PurchaseDrmInfoScReq;
import com.skplanet.storeplatform.purchase.client.history.vo.PurchaseDrmInfoScRes;
import com.skplanet.storeplatform.purchase.history.service.PurchaseDrmInfoScService;

/**
 * 구매DRM정보 Controller
 * 
 * Updated on : 2013. 12. 10. Updated by : wisestone_mikepark
 */
@LocalSCI
public class PurchaseDrmInfoSCIController implements PurchaseDrmInfoSCI {

	@Autowired
	private PurchaseDrmInfoScService service;

	/**
	 * 다운로드 여부에 따른 DRM 정보 수정.
	 * 
	 * @param request
	 *            DRM정보
	 * @return PurchaseDrmInfoScRes
	 */
	@Override
	public PurchaseDrmInfoScRes updatePrchaseDrm(PurchaseDrmInfoScReq request) {
		return this.service.updatePrchaseDrm(request);

	}

	/**
	 * 구매상세내역조회.
	 * 
	 * @param request
	 *            DRM정보
	 * @return PurchaseDrmInfoScRes
	 */
	@Override
	public PurchaseDrmInfoSc selectPrchsDtl(PurchaseDrmInfoScReq request) {
		return this.service.selectPrchsDtl(request);
	}

}
