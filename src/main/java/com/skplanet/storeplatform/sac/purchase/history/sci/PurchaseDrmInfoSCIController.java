/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 * 
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.history.sci;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.skplanet.storeplatform.framework.integration.bean.LocalSCI;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.sci.PurchaseDrmInfoInternalSCI;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.vo.PurchaseDrmInfoSacInReq;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.vo.PurchaseDrmInfoSacInRes;
import com.skplanet.storeplatform.sac.purchase.history.service.PurchaseDrmInfoService;

/**
 * 구매DRM정보 Controller
 * 
 * Updated on : 2013. 12. 10. Updated by : wisestone_mikepark
 */
@LocalSCI
public class PurchaseDrmInfoSCIController implements PurchaseDrmInfoInternalSCI {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private PurchaseDrmInfoService service;

	/**
	 * 다운로드 여부에 따른 DRM 정보 수정.
	 * 
	 * @param request
	 *            DRM요청
	 * @return PurchaseDrmInfoSacInRes
	 */
	@Override
	public PurchaseDrmInfoSacInRes updatePrchaseDrm(PurchaseDrmInfoSacInReq request) {
		return this.service.updatePrchaseDrm(request);
	}

}
