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
import com.skplanet.storeplatform.purchase.client.history.sci.MileageSaveSCI;
import com.skplanet.storeplatform.purchase.client.history.vo.MileageSaveGetScReq;
import com.skplanet.storeplatform.purchase.client.history.vo.MileageSaveGetScRes;
import com.skplanet.storeplatform.purchase.client.history.vo.MileageSaveScReq;
import com.skplanet.storeplatform.purchase.client.history.vo.MileageSaveScRes;
import com.skplanet.storeplatform.purchase.history.service.MileageSaveScService;

/**
 * 마일리지 적립정보 조회 Controller
 * 
 * Updated on : 2013. 12. 10. Updated by : wisestone_mikepark
 */
@LocalSCI
public class MileageSaveSCIController implements MileageSaveSCI {

	@Autowired
	private MileageSaveScService service;

	/**
	 * 마일리지 적립정보 조회 기능을 제공한다.
	 * 
	 * @param request
	 *            마일리지 적립정보 요청
	 * @return MileageSaveScRes
	 */
	@Override
	public MileageSaveScRes searchMileageSave(MileageSaveScReq request) {
		return this.service.searchMileageSave(request);

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
		return this.service.getMileageSave(request);

	}

}
