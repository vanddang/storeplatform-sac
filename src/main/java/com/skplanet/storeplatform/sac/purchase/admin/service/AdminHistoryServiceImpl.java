/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.admin.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.purchase.client.admin.sci.AdminHistorySCI;
import com.skplanet.storeplatform.purchase.client.admin.vo.AdminHistorySc;
import com.skplanet.storeplatform.purchase.client.admin.vo.AdminHistoryScReq;
import com.skplanet.storeplatform.purchase.client.admin.vo.AdminHistoryScRes;
import com.skplanet.storeplatform.sac.client.purchase.admin.vo.AdminHistorySac;
import com.skplanet.storeplatform.sac.client.purchase.admin.vo.AdminHistorySacReq;
import com.skplanet.storeplatform.sac.client.purchase.admin.vo.AdminHistorySacRes;

/**
 * 상품별 구매내역 Implements
 * 
 * Updated on : 2014-01-10 Updated by : 양주원, 엔텔스.
 */
@Service
public class AdminHistoryServiceImpl implements AdminHistoryService {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AdminHistorySCI adminHistorySCI;

	/**
	 * 상품별 구매내역 조회 기능을 제공한다.
	 * 
	 * @param request
	 *            상품별 구매내역요청
	 * @return AdminHistorySacRes
	 */
	@Override
	public AdminHistorySacRes searchAdminHistoryList(AdminHistorySacReq request) {
		this.LOGGER.debug("HistoryListSacRes : {}", request);

		// SC request/response VO
		AdminHistoryScReq scRequest = new AdminHistoryScReq();
		AdminHistoryScRes scResponse = new AdminHistoryScRes();

		// SAC Response VO
		AdminHistorySacRes response = new AdminHistorySacRes();
		List<AdminHistorySac> sacHistoryList = new ArrayList<AdminHistorySac>();
		AdminHistorySac historySac = new AdminHistorySac();

		/*************************************************
		 * SC Request Setting Start
		 *************************************************/
		scRequest.setTenantId(request.getTenantId());
		scRequest.setStartDt(request.getStartDt());
		scRequest.setEndDt(request.getEndDt());
		scRequest.setProdId(request.getProdId());
		scRequest.setTenantProdGrpCd(request.getTenantProdGrpCd());
		// scRequest.setOffset(request.getOffset());
		// scRequest.setCount(request.getCount());
		// pageInfo set
		scRequest.getPage().setNo(request.getOffset());
		scRequest.getPage().setRows(request.getCount());

		/*************************************************
		 * SC Request Setting End
		 *************************************************/

		// try {
		// SC Call
		scResponse = this.adminHistorySCI.searchAdminHistoryList(scRequest);
		// } catch (Exception ex) {
		// TODO : 추후 메세지 추가후 처리함
		// throw new StorePlatformException("구매SC 호출중 오류발생", ex);
		// }

		// SC객체를 SAC객체로 맵핑작업

		/*************************************************
		 * SC -> SAC Response Setting Start
		 *************************************************/
		for (AdminHistorySc obj : scResponse.getHistoryList()) {

			historySac = new AdminHistorySac();

			// 구매정보 set
			historySac.setTenantId(obj.getTenantId());
			// historySac.setSystemId(obj.getSystemId());
			historySac.setPrchsId(obj.getPrchsId());
			historySac.setPrchsDtlId(obj.getPrchsDtlId());
			historySac.setUseTenantId(obj.getUseTenantId());
			historySac.setUseUserKey(obj.getUseUserKey());
			historySac.setUseDeviceKey(obj.getUseDeviceKey());
			historySac.setPrchsDt(obj.getPrchsDt());
			historySac.setTotAmt(obj.getTotAmt());
			historySac.setSendUserKey(obj.getSendUserKey());
			historySac.setSendDeviceKey(obj.getSendDeviceKey());
			historySac.setRecvDt(obj.getRecvDt());
			historySac.setRecvConfPathCd(obj.getRecvConfPathCd());
			historySac.setTenantProdGrpCd(obj.getTenantProdGrpCd());
			historySac.setProdId(obj.getProdId());
			historySac.setProdAmt(obj.getProdAmt());
			historySac.setProdQty(obj.getProdQty());
			historySac.setStatusCd(obj.getStatusCd());
			historySac.setUseStartDt(obj.getUseStartDt());
			historySac.setUseExprDt(obj.getUseExprDt());
			historySac.setPrchsReqPathCd(obj.getPrchsReqPathCd());
			historySac.setHidingYn(obj.getHidingYn());
			historySac.setCancelReqPathCd(obj.getCancelReqPathCd());
			historySac.setCancelDt(obj.getCancelDt());
			historySac.setPrchsCaseCd(obj.getPrchsCaseCd());
			historySac.setRePrchsPmtYn(obj.getRePrchsPmtYn());
			historySac.setDwldStartDt(obj.getDwldStartDt());
			historySac.setDwldExprDt(obj.getDwldExprDt());
			historySac.setCpnPublishCd(obj.getCpnPublishCd());
			historySac.setCpnDlvUrl(obj.getCpnDlvUrl());
			historySac.setEtcSeq(obj.getEtcSeq());
			historySac.setUseFixrateProdId(obj.getUseFixrateProdId());
			historySac.setPrchsProdType(obj.getPrchsProdType());
			historySac.setResvCol01(obj.getResvCol01());
			historySac.setResvCol02(obj.getResvCol02());
			historySac.setResvCol03(obj.getResvCol03());
			historySac.setResvCol04(obj.getResvCol04());
			historySac.setResvCol05(obj.getResvCol05());

			sacHistoryList.add(historySac);
		}

		/*************************************************
		 * SC -> SAC Response Setting End
		 *************************************************/

		response.setHistoryList(sacHistoryList);
		response.setTotalCnt(scResponse.getTotalCnt());

		return response;
	}

}
