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

import com.skplanet.storeplatform.purchase.client.history.sci.PurchaseDrmInfoSCI;
import com.skplanet.storeplatform.purchase.client.history.vo.PurchaseDrmInfoScReq;
import com.skplanet.storeplatform.purchase.client.history.vo.PurchaseDrmInfoScRes;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.vo.PurchaseDrmInfoSacInReq;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.vo.PurchaseDrmInfoSacInRes;

/**
 * 구매DRM정보 Implements
 * 
 * Updated on : 2014-01-10 Updated by : 양주원, 엔텔스.
 */
@Service
public class PurchaseDrmInfoServiceImpl implements PurchaseDrmInfoService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private PurchaseDrmInfoSCI purchaseDrmInfoSCI;

	/**
	 * 다운로드 여부에 따른 DRM 정보 수정.
	 * 
	 * @param request
	 *            DRM정보
	 * @return PurchaseDrmInfoSacInRes
	 */
	@Override
	public PurchaseDrmInfoSacInRes updatePrchaseDrm(PurchaseDrmInfoSacInReq request) {

		// SC request/response VO
		PurchaseDrmInfoScReq scRequest = new PurchaseDrmInfoScReq();

		// SAC Response VO
		PurchaseDrmInfoSacInRes response = new PurchaseDrmInfoSacInRes();

		/*************************************************
		 * SC Request Setting Start
		 *************************************************/
		scRequest.setTenantId(request.getTenantId());
		scRequest.setSystemId(request.getSystemId());
		scRequest.setPrchsId(request.getPrchsId());
		scRequest.setProdId(request.getProdId());
		scRequest.setUserKey(request.getUserKey());
		/*************************************************
		 * SC Request Setting End
		 *************************************************/

		/** SC Call */
		PurchaseDrmInfoScRes scResponse = this.purchaseDrmInfoSCI.updatePrchaseDrm(scRequest);

		/*************************************************
		 * SC -> SAC Response Setting Start
		 *************************************************/
		response.setPrchsId(request.getPrchsId());
		response.setProdId(request.getProdId());
		response.setResultYn(scResponse.getResultYn());
		if (scResponse != null) {
			response.setUseStartDt(scResponse.getUseStartDt());
			response.setUseExprDt(scResponse.getUseExprDt());
			response.setDwldStartDt(scResponse.getDwldStartDt());
			response.setDwldExprDt(scResponse.getDwldExprDt());
		}
		/*************************************************
		 * SC -> SAC Response Setting End
		 *************************************************/

		this.logger.info("####PurchaseDrmInfoServiceImpl.response {}", response);

		return response;
	}

}
