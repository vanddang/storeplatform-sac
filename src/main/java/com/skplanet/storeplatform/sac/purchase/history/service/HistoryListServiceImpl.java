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
import com.skplanet.storeplatform.sac.client.purchase.history.vo.HistoryListReq;
import com.skplanet.storeplatform.sac.client.purchase.history.vo.HistoryListRes;

@Service
@Transactional
public class HistoryListServiceImpl implements HistoryListService {

	private static final Logger logger = LoggerFactory.getLogger(HistoryListServiceImpl.class);

	@Autowired
	private HistorySCI historySci;

	@Override
	public HistoryListRes list(HistoryListReq historyListReq) {
		// logger.debug("list : {}", historyListReq);

		// SC request/response VO
		HistoryListRequest scRequest = new HistoryListRequest();
		HistoryListResponse scResponse = new HistoryListResponse();

		// SC Request Set
		scRequest.setTenantId(historyListReq.getTenantId());
		scRequest.setInsdUsermbrNo(historyListReq.getInsdUsermbrNo());
		scRequest.setStartDt(historyListReq.getStartDt());
		scRequest.setEndDt(historyListReq.getEndDt());
		scRequest.setPrchsProdType(historyListReq.getPrchsProdType());
		scRequest.setPrchsStatusCd(historyListReq.getPrchsStatusCd());
		scRequest.setProdId(historyListReq.getProdId());
		scRequest.setHidingYn(historyListReq.getHidingYn());
		scRequest.setTenantProdGrpCd(historyListReq.getTenantProdGrpCd());

		// SC Call
		scResponse = this.historySci.listHistory(scRequest);

		// SAC Response VO
		HistoryListRes historyListRes = new HistoryListRes();
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

		historyListRes.setHistoryList(sacHistoryList);
		historyListRes.setTotalCnt(sacHistoryList.size());

		// logger.debug("list : {}", historyListRes);
		return historyListRes;
	}
}
