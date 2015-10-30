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

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.purchase.client.shopping.vo.ShoppingAsyncItemSc;
import com.skplanet.storeplatform.purchase.client.shopping.vo.ShoppingAsyncListSc;
import com.skplanet.storeplatform.purchase.client.shopping.vo.ShoppingAsyncReq;

/**
 * 쿠폰발급비동기응답 Implements
 * 
 * Updated on : 2014-01-10 Updated by : ntels_yjw, nTels.
 */
@Service
public class ShoppingAsyncScServiceImpl implements ShoppingAsyncScService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("scPurchase")
	private CommonDAO commonDAO;

	/**
	 * 쿠폰발급비동기응답.
	 * 
	 * @param request
	 *            쿠폰정보
	 * @return int
	 */
	@Override
	public int updateShoppingAsync(ShoppingAsyncReq request) {

		int cnt = 0;
		int prchsDtlId = 0;

		for (ShoppingAsyncListSc prchs : request.getPublish()) {

			for (ShoppingAsyncItemSc item : prchs.getItems()) {
				prchsDtlId++;
				item.setPrchsId(prchs.getPrchsId());
				item.setPrchsDtlId(prchsDtlId);
				item.setAvailStartdate(prchs.getAvail_startdate());
				item.setAvailEnddate(prchs.getAvail_enddate());

				this.logger.debug("### update info {} : " + item.toString());
				this.commonDAO.update("Shopping.updateShoppingAsync", item);
				cnt++;
			}
			prchsDtlId = 0;
		}

		return cnt;

	}

	/**
	 * 쿠폰발급비동기응답.
	 * 
	 * @param request
	 *            쿠폰정보
	 * @return int
	 */
	@Override
	public int updateShoppingAsyncItem(ShoppingAsyncItemSc request) {
		return this.commonDAO.update("Shopping.updateShoppingAsync", request);

	}

	/**
	 * 구매상태변경.
	 * 
	 * @param request
	 *            쿠폰정보
	 * @return int
	 */
	@Override
	public int updatePrchsStatus(ShoppingAsyncItemSc request) {

		this.commonDAO.update("Shopping.updatePrchsStatus", request);
		int cnt = this.commonDAO.update("Shopping.updatePrchsDtlStatus", request);

		return cnt;
	}

	@Override
	public int searchPrchsIdCnt(Map<String, Object> request) {
		return this.commonDAO.queryForInt("Shopping.searchPrchsIdCnt", request);
	}

}
