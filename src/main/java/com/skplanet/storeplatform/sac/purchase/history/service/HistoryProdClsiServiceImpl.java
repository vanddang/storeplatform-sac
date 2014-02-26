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

import com.skplanet.storeplatform.purchase.client.history.sci.HistoryProdClsiSCI;
import com.skplanet.storeplatform.purchase.client.history.vo.HistoryProdClsiSc;
import com.skplanet.storeplatform.purchase.client.history.vo.HistoryProdClsiScReq;
import com.skplanet.storeplatform.purchase.client.history.vo.HistoryProdClsiScRes;
import com.skplanet.storeplatform.sac.client.purchase.history.vo.HistoryProdClsiSac;
import com.skplanet.storeplatform.sac.client.purchase.history.vo.HistoryProdClsiSacReq;
import com.skplanet.storeplatform.sac.client.purchase.history.vo.HistoryProdClsiSacRes;

/**
 * 상품별 구매내역 Implements
 * 
 * Updated on : 2014-01-10 Updated by : 양주원, 엔텔스.
 */
@Service
public class HistoryProdClsiServiceImpl implements HistoryProdClsiService {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private HistoryProdClsiSCI historyProdClsiSCI;

	/**
	 * 상품별 구매내역 조회 기능을 제공한다.
	 * 
	 * @param request
	 *            상품별 구매내역요청
	 * @return HistoryProdClsiSacRes
	 */
	@Override
	public HistoryProdClsiSacRes searchHistoryProdClsiList(HistoryProdClsiSacReq request) {
		this.LOGGER.debug("HistoryListSacRes : {}", request);

		// SC request/response VO
		HistoryProdClsiScReq scRequest = new HistoryProdClsiScReq();
		HistoryProdClsiScRes scResponse = new HistoryProdClsiScRes();

		// SAC Response VO
		HistoryProdClsiSacRes response = new HistoryProdClsiSacRes();
		List<HistoryProdClsiSac> sacHistoryList = new ArrayList<HistoryProdClsiSac>();
		HistoryProdClsiSac historySac = new HistoryProdClsiSac();

		/*************************************************
		 * SC Request Setting Start
		 *************************************************/
		scRequest.setTenantId(request.getTenantId());
		scRequest.setStartDt(request.getStartDt());
		scRequest.setEndDt(request.getEndDt());
		scRequest.setProdId(request.getProdId());
		scRequest.setTenantProdGrpCd(request.getTenantProdGrpCd());
		scRequest.setOffset(request.getOffset());
		scRequest.setCount(request.getCount());
		// pageInfo set
		scRequest.getPage().setNo(request.getOffset());
		scRequest.getPage().setRows(request.getCount());

		/*************************************************
		 * SC Request Setting End
		 *************************************************/

		// try {
		// SC Call
		scResponse = this.historyProdClsiSCI.searchHistoryProdClsiList(scRequest);
		// } catch (Exception ex) {
		// TODO : 추후 메세지 추가후 처리함
		// throw new StorePlatformException("구매SC 호출중 오류발생", ex);
		// }

		// SC객체를 SAC객체로 맵핑작업

		/*************************************************
		 * SC -> SAC Response Setting Start
		 *************************************************/
		for (HistoryProdClsiSc obj : scResponse.getHistoryList()) {

			historySac = new HistoryProdClsiSac();

			// 구매정보 set
			historySac.setTenantId(obj.getTenantId());
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
			historySac.setDrmYn(obj.getDrmYn());
			historySac.setAlarmYn(obj.getAlarmYn());

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
