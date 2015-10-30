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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.purchase.client.history.vo.PurchaseDrmInfoSc;
import com.skplanet.storeplatform.purchase.client.history.vo.PurchaseDrmInfoScReq;
import com.skplanet.storeplatform.purchase.client.history.vo.PurchaseDrmInfoScRes;

/**
 * 구매DRM정보 Implements
 * 
 * Updated on : 2014-01-10 Updated by : 양주원, 엔텔스.
 */
@Service
public class PurchaseDrmInfoScServiceImpl implements PurchaseDrmInfoScService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("scPurchase")
	private CommonDAO commonDAO;

	/**
	 * 다운로드 여부에 따른 DRM 정보 수정.
	 * 
	 * @param purchaseDrmInfoScReq
	 *            DRM정보
	 * @return PurchaseDrmInfoScRes
	 */
	@Override
	public PurchaseDrmInfoScRes updatePrchaseDrm(PurchaseDrmInfoScReq purchaseDrmInfoScReq) {

		this.logger.info("####PurchaseDrmInfoScServiceImpl.purchaseDrmInfoScReq {}", purchaseDrmInfoScReq);

		int cnt = this.commonDAO.update("History.updatePrchaseDrm", purchaseDrmInfoScReq);
		PurchaseDrmInfoScRes result = new PurchaseDrmInfoScRes();

		if (cnt > 0) {
			result = (PurchaseDrmInfoScRes) this.commonDAO.queryForObject("History.retrieveUpdatedDtl",
					purchaseDrmInfoScReq);
			result.setResultYn("Y");
		} else {
			result.setResultYn("N");
		}

		this.logger.info("####PurchaseDrmInfoScServiceImpl.result {}", result);
		this.logger.info("####PurchaseDrmInfoScServiceImpl.cnt {}", cnt);

		return result;

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

		this.logger.info("####PurchaseDrmInfoScServiceImpl.selectPrchsDtl.REQ {}", request);

		PurchaseDrmInfoSc response = this.commonDAO.queryForObject("History.selectPrchsDtl", request,
				PurchaseDrmInfoSc.class);

		this.logger.info("####PurchaseDrmInfoScServiceImpl.selectPrchsDtl.RES {}", response);

		return response;
	}

}
