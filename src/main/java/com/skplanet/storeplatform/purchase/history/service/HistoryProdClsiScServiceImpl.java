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
import com.skplanet.storeplatform.framework.core.persistence.dao.page.PageInfo;
import com.skplanet.storeplatform.framework.core.persistence.dao.page.PageStatementBuilder;
import com.skplanet.storeplatform.purchase.client.history.vo.HistoryProdClsiSc;
import com.skplanet.storeplatform.purchase.client.history.vo.HistoryProdClsiScReq;
import com.skplanet.storeplatform.purchase.client.history.vo.HistoryProdClsiScRes;

/**
 * 상품별 구매내역 Implements
 * 
 * Updated on : 2014-01-10 Updated by : 양주원, 엔텔스.
 */
@Service
public class HistoryProdClsiScServiceImpl implements HistoryProdClsiScService {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("scPurchase")
	private CommonDAO commonDAO;

	/**
	 * 상품별 구매내역 조회 기능을 제공한다.
	 * 
	 * @param request
	 *            구매내역요청
	 * @return HistoryProdClsiScRes
	 */
	@Override
	public HistoryProdClsiScRes searchHistoryProdClsiList(HistoryProdClsiScReq request) {

		this.LOGGER.debug(">>>> >>> HistoryProdClsiScReq: {}", request);
		PageInfo<HistoryProdClsiSc> pageInfo = this.commonDAO.queryForPagenatedList(
				PageStatementBuilder.create().totalCountStatementId("HistoryProdClsi.searchHistoryProdClsiCount")
						.dataStatementId("HistoryProdClsi.searchHistoryProdClsiList").build(), request);

		// Response Set
		HistoryProdClsiScRes response = new HistoryProdClsiScRes();
		response.setHistoryList(pageInfo.getData());
		response.setTotalCnt(pageInfo.getPage().getTotalCount());

		return response;
	}

}
