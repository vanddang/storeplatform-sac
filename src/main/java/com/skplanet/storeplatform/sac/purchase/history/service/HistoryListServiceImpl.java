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

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.purchase.client.history.sci.HistorySCI;
import com.skplanet.storeplatform.purchase.client.history.vo.HistoryList;
import com.skplanet.storeplatform.purchase.client.history.vo.HistoryListRequest;
import com.skplanet.storeplatform.purchase.client.history.vo.HistoryListResponse;
import com.skplanet.storeplatform.sac.client.purchase.history.vo.History;
import com.skplanet.storeplatform.sac.client.purchase.history.vo.HistoryCountRes;
import com.skplanet.storeplatform.sac.client.purchase.history.vo.HistoryListReq;
import com.skplanet.storeplatform.sac.client.purchase.history.vo.HistoryListRes;

/**
 * 구매내역 Implements
 * 
 * Updated on : 2014-01-10 Updated by : 양주원, 엔텔스.
 */
@Service
@Transactional
public class HistoryListServiceImpl implements HistoryListService {

	private static final Logger logger = LoggerFactory.getLogger(HistoryListServiceImpl.class);

	@Autowired
	private HistorySCI historySci;

	/**
	 * 구매내역조회
	 * 
	 * @param request
	 * @return
	 */
	@Override
	public HistoryListRes list(HistoryListReq request) {
		// logger.debug("list : {}", historyListReq);

		// SC request/response VO
		HistoryListRequest scRequest = new HistoryListRequest();
		HistoryListResponse scResponse = new HistoryListResponse();

		// SC Request Set
		scRequest.setTenantId(request.getTenantId());
		scRequest.setInsdUsermbrNo(request.getInsdUsermbrNo());
		scRequest.setStartDt(request.getStartDt());
		scRequest.setEndDt(request.getEndDt());
		scRequest.setPrchsProdType(request.getPrchsProdType());
		scRequest.setPrchsStatusCd(request.getPrchsStatusCd());
		scRequest.setProdId(request.getProdId());
		scRequest.setHidingYn(request.getHidingYn());
		scRequest.setTenantProdGrpCd(request.getTenantProdGrpCd());

		scRequest.setOffset(request.getOffset());
		scRequest.setCount(request.getCount());

		// SC Call
		scResponse = this.historySci.listHistory(scRequest);

		// SAC Response VO
		HistoryListRes response = new HistoryListRes();
		List<History> sacHistoryList = new ArrayList<History>();
		History history = new History();

		// SC객체를 SAC객체로 맵핑작업
		List<HistoryList> scHistoryList = scResponse.getHistoryList();
		for (HistoryList obj : scHistoryList) {

			history = new History();

			history.setTenantId(obj.getTenantId());
			history.setSystemId(obj.getSystemId());
			history.setPrchsId(obj.getPrchsId());
			history.setPrchsDtlId(obj.getPrchsDtlId());
			history.setUseTenantId(obj.getUseTenantId());
			history.setUseInsdUsermbrNo(obj.getUseInsdUsermbrNo());
			history.setUseInsdDeviceId(obj.getUseInsdDeviceId());
			history.setPrchsDt(obj.getPrchsDt());
			history.setTotAmt(obj.getTotAmt());
			history.setSendInsdUsermbrNo(obj.getSendInsdUsermbrNo());
			history.setSendInsdDeviceId(obj.getSendInsdDeviceId());
			history.setRecvDt(obj.getRecvDt());
			history.setProdId(obj.getProdId());
			history.setProdAmt(obj.getProdAmt());
			history.setProdQty(obj.getProdQty());
			history.setTenantProdGrpCd(obj.getTenantProdGrpCd());
			history.setStatusCd(obj.getStatusCd());
			history.setUseStartDt(obj.getUseStartDt());
			history.setUseExprDt(obj.getUseExprDt());
			history.setHidingYn(obj.getHidingYn());
			history.setCancelReqPathCd(obj.getCancelReqPathCd());
			history.setCancelDt(obj.getCancelDt());
			history.setCpnPublishCd(obj.getCpnPublishCd());
			history.setCpnDlvUrl(obj.getCpnDlvUrl());
			history.setPrchsCaseCd(obj.getPrchsCaseCd());
			history.setRePrchsPmtYn(obj.getRePrchsPmtYn());
			history.setDwldStartDt(obj.getDwldStartDt());
			history.setDwldExprDt(obj.getDwldExprDt());

			sacHistoryList.add(history);
		}

		response.setHistoryList(sacHistoryList);
		response.setTotalCnt(sacHistoryList.size());

		// logger.debug("list : {}", historyListRes);
		return response;
	}

	/**
	 * 구매건수조회
	 * 
	 * @param request
	 * @return
	 */
	@Override
	public HistoryCountRes count(HistoryListReq request) {
		// logger.debug("list : {}", historyListReq);

		// SC request/response VO
		HistoryListRequest scRequest = new HistoryListRequest();
		int scResponse = 0;

		// SC Request Set
		scRequest.setTenantId(request.getTenantId());
		scRequest.setInsdUsermbrNo(request.getInsdUsermbrNo());
		scRequest.setStartDt(request.getStartDt());
		scRequest.setEndDt(request.getEndDt());
		scRequest.setPrchsProdType(request.getPrchsProdType());
		scRequest.setPrchsStatusCd(request.getPrchsStatusCd());
		scRequest.setProdId(request.getProdId());
		scRequest.setHidingYn(request.getHidingYn());
		scRequest.setTenantProdGrpCd(request.getTenantProdGrpCd());

		// SC Call
		scResponse = this.historySci.getHistoryCount(scRequest);

		HistoryCountRes response = new HistoryCountRes();
		response.setTotalCnt(scResponse);

		// logger.debug("list : {}", historyListRes);
		return response;
	}
}
