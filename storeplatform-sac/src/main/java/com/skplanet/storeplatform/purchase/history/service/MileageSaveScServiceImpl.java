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

import java.text.SimpleDateFormat;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.purchase.client.history.vo.MileageSaveGetScReq;
import com.skplanet.storeplatform.purchase.client.history.vo.MileageSaveGetScRes;
import com.skplanet.storeplatform.purchase.client.history.vo.MileageSaveSc;
import com.skplanet.storeplatform.purchase.client.history.vo.MileageSaveScReq;
import com.skplanet.storeplatform.purchase.client.history.vo.MileageSaveScRes;

/**
 * 마일리지 적립정보 조회 Implements
 * 
 * Updated on : 2014-01-10 Updated by : 양주원, 엔텔스.
 */
@Service
public class MileageSaveScServiceImpl implements MileageSaveScService {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("scPurchase")
	private CommonDAO commonDAO;

	/**
	 * 마일리지 적립정보 조회 기능을 제공한다.
	 * 
	 * @param request
	 *            마일리지 적립정보 요청
	 * @return MileageSaveScRes
	 */
	@Override
	public MileageSaveScRes searchMileageSave(MileageSaveScReq request) {

		this.LOGGER.debug(">>>> >>> MileageSaveScReq: {}", request);

		List<MileageSaveSc> resuleList = this.commonDAO.queryForList("MileageSave.searchMileageSave", request,
				MileageSaveSc.class);

		MileageSaveSc mileageSaveSc = new MileageSaveSc();
		if (resuleList.size() < 1) {
			mileageSaveSc.setSaveAmt(0);
			mileageSaveSc
					.setSaveDt(StringUtils.isNotBlank(request.getStandardDt()) ? request.getStandardDt() : new SimpleDateFormat(
							"yyyyMMdd").format(new java.util.Date()));
			resuleList.add(mileageSaveSc);
		}

		MileageSaveScRes response = new MileageSaveScRes();
		response.settMileageReseveList(resuleList);

		return response;
	}

	/**
	 * 마일리지 적립정보 조회 기능을 제공한다.
	 * 
	 * @param request
	 *            마일리지 적립정보 요청
	 * @return MileageSaveScRes
	 */
	@Override
	public MileageSaveGetScRes getMileageSave(MileageSaveGetScReq request) {
		return this.commonDAO.queryForObject("MileageSave.getMileageSave", request, MileageSaveGetScRes.class);
	}

}
