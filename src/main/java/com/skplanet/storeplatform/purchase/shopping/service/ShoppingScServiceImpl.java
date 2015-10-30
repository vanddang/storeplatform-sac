/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 * 
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.purchase.shopping.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.purchase.client.shopping.vo.ShoppingScReq;

/**
 * CMS Implements
 * 
 * Updated on : 2014-01-10 Updated by : ntels_yjw, nTels.
 */
@Service
public class ShoppingScServiceImpl implements ShoppingScService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("scPurchase")
	private CommonDAO commonDAO;

	/**
	 * 쿠폰사용여부 업데이트.
	 * (쿠폰발급비동기응답, 쿠폰사용여부 초기화 API)
	 *
	 * @param request
	 *            쿠폰정보
	 * @return int
	 */
	@Override
	public int updatePrchsDtl(ShoppingScReq request) {

		return this.commonDAO.update("Shopping.updatePrchsDtl", request);

	}

}
