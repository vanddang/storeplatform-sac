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

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.purchase.client.history.vo.*;
import com.skplanet.storeplatform.purchase.constant.PurchaseCDConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 구매내역 Implements
 * 
 * Updated on : 2014-01-10 Updated by : 양주원, 엔텔스.
 */
@Service
public class HistoryListScServiceImpl implements HistoryListScService {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("scPurchase")
	private CommonDAO commonDAO;

	/**
	 * 구매내역 조회 기능을 제공한다.
	 * 
	 * @param request
	 *            구매내역요청
	 * @return HistoryListScRes
	 */
	@Override
	public HistoryListScRes searchHistoryList(HistoryListScReq request) {

		this.LOGGER.debug(">>>> >>> HistoryListScRes: {}", request);

		HistoryListScRes response = new HistoryListScRes();
		List<HistorySc> resuleList = null;

		if (PurchaseCDConstants.USE_Y.equals(request.getPrchsProdHaveYn())) {// 보유상품
			resuleList = this.commonDAO.queryForList("History.searchHistoryList", request, HistorySc.class);
		} else { // 미보유상품
			resuleList = this.commonDAO.queryForList("History.searchHistorySendList", request, HistorySc.class);
		}

		// Response Set
		response.setHistoryList(resuleList);

		if (resuleList != null && resuleList.size() > 0) {
			response.setTotalCnt(resuleList.get(0).getTotcnt());
		} else {
			response.setTotalCnt(0);
		}

		return response;
	}

	/**
	 * 구매내역 조회 기능을 제공한다.
	 * 
	 * @param request
	 *            구매내역요청
	 * @return HistoryListScRes
	 */
	@Override
	public HistoryListScRes searchHistoryListV2(HistoryListScReq request) {

		this.LOGGER.debug(">>>> >>> HistoryListScRes: {}", request);

		HistoryListScRes response = new HistoryListScRes();
		List<HistorySc> resuleList = null;

		if (PurchaseCDConstants.USE_Y.equals(request.getPrchsProdHaveYn())) {// 보유상품
			resuleList = this.commonDAO.queryForList("History.searchHistoryListV2", request, HistorySc.class);
		} else { // 미보유상품
			resuleList = this.commonDAO.queryForList("History.searchHistorySendListV2", request, HistorySc.class);
		}

		// Response Set
		response.setHistoryList(resuleList);

		if (resuleList != null && resuleList.size() > 0) {
			response.setTotalCnt(resuleList.get(0).getTotcnt());
		} else {
			response.setTotalCnt(0);
		}

		return response;
	}

	/**
	 * 구매건수 조회 기능을 제공한다.
	 * 
	 * @param request
	 *            구매내역요청
	 * @return HistoryCountScRes
	 */
	@Override
	public HistoryCountScRes searchHistoryCount(HistoryCountScReq request) {

		HistoryCountScRes response = new HistoryCountScRes();
		List<ProductCountSc> cntList;

		int totalCnt = 0;

		if (PurchaseCDConstants.USE_Y.equals(request.getPrchsProdHaveYn())) {// 보유상품

			this.LOGGER.debug("##### 보유상품건수조회");
			totalCnt = this.commonDAO.queryForInt("History.searchHistoryCount", request);

			if (request.getProductList() != null && request.getProductList().size() > 0) {
				cntList = this.commonDAO.queryForList("History.searchHistoryProdCount", request, ProductCountSc.class);
				response.setCntList(cntList);
			}

		} else {

			this.LOGGER.debug("##### 미보유상품건수조회");
			totalCnt = this.commonDAO.queryForInt("History.searchHistorySendCount", request);

			if (request.getProductList() != null && request.getProductList().size() > 0) {
				cntList = this.commonDAO.queryForList("History.searchHistorySendProdCount", request,
						ProductCountSc.class);
				response.setCntList(cntList);
			}

		}

		response.setTotalCnt(totalCnt);

		return response;
	}

}
