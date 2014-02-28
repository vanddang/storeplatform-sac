/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 * 
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.history.sci;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.skplanet.storeplatform.framework.integration.bean.LocalSCI;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.sci.HistoryInternalSCI;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.vo.HistoryCountSacInReq;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.vo.HistoryCountSacInRes;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.vo.HistoryListSacInReq;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.vo.HistoryListSacInRes;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.vo.HistorySacIn;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.vo.ProductCountSacIn;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.vo.ProductListSacIn;
import com.skplanet.storeplatform.sac.client.purchase.history.vo.HistoryCountSacReq;
import com.skplanet.storeplatform.sac.client.purchase.history.vo.HistoryCountSacRes;
import com.skplanet.storeplatform.sac.client.purchase.history.vo.HistoryListSacReq;
import com.skplanet.storeplatform.sac.client.purchase.history.vo.HistoryListSacRes;
import com.skplanet.storeplatform.sac.client.purchase.history.vo.HistorySac;
import com.skplanet.storeplatform.sac.client.purchase.history.vo.ProductCountSac;
import com.skplanet.storeplatform.sac.client.purchase.history.vo.ProductListSac;
import com.skplanet.storeplatform.sac.purchase.history.service.HistoryListService;

/**
 * 구매내역 Controller
 * 
 * Updated on : 2013. 12. 10. Updated by : wisestone_mikepark
 */
@LocalSCI
public class HistoryInternalSCIController implements HistoryInternalSCI {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private HistoryListService service;

	/**
	 * 구매내역 조회 기능을 제공한다.
	 * 
	 * @param request
	 *            구매내역요청
	 * @return HistoryListSacInRes
	 */
	@Override
	public HistoryListSacInRes searchHistoryList(HistoryListSacInReq request) {
		this.LOGGER.debug(">>>> >>> HistoryListSacInRes: {}", request);

		HistoryListSacReq sacReq = new HistoryListSacReq();
		HistoryListSacRes sacRes = new HistoryListSacRes();

		// Request Header Info
		sacReq.setTenantId(request.getTenantId());

		// Request Parameter Info
		sacReq.setUserKey(request.getUserKey());
		sacReq.setDeviceKey(request.getDeviceKey());
		sacReq.setStartDt(request.getStartDt());
		sacReq.setEndDt(request.getEndDt());
		sacReq.setPrchsProdType(request.getPrchsProdType());
		sacReq.setPrchsStatusCd(request.getPrchsStatusCd());
		sacReq.setPrchsCaseCd(request.getPrchsCaseCd());
		sacReq.setPrchsProdHaveYn(request.getPrchsProdHaveYn());
		sacReq.setPrchsReqPathCd(request.getPrchsReqPathCd());
		sacReq.setTenantProdGrpCd(request.getTenantProdGrpCd());

		ProductListSac product = new ProductListSac();
		List<ProductListSac> productList = new ArrayList<ProductListSac>();

		if (request.getProductList() != null && request.getProductList().size() > 0) {
			for (ProductListSacIn obj : request.getProductList()) {
				product = new ProductListSac();
				product.setProdId(obj.getProdId());
				productList.add(product);
			}
		}
		sacReq.setUseFixrateProdId(request.getUseFixrateProdId());
		sacReq.setProductList(productList);
		sacReq.setHidingYn(request.getHidingYn());
		sacReq.setGiftRecvConfYn(request.getGiftRecvConfYn());
		sacReq.setOffset(request.getOffset());
		sacReq.setCount(request.getCount());

		// SAC내부호출여부
		sacReq.setInternalYn("Y");

		// SAC Service Call
		sacRes = this.service.searchHistoryList(sacReq);

		/********************************************
		 * Response Mapping Start
		 ********************************************/
		HistoryListSacInRes response = new HistoryListSacInRes();
		List<HistorySacIn> historySacInList = new ArrayList<HistorySacIn>();
		HistorySacIn historySacIn = new HistorySacIn();

		for (HistorySac obj : sacRes.getHistoryList()) {

			historySacIn = new HistorySacIn();

			// 구매정보 set
			historySacIn.setTenantId(obj.getTenantId());
			historySacIn.setPrchsId(obj.getPrchsId());
			historySacIn.setPrchsDtlId(obj.getPrchsDtlId());
			historySacIn.setUseTenantId(obj.getUseTenantId());
			historySacIn.setUseUserKey(obj.getUseUserKey());
			historySacIn.setUseDeviceKey(obj.getUseDeviceKey());
			historySacIn.setPrchsDt(obj.getPrchsDt());
			historySacIn.setTotAmt(obj.getTotAmt());
			historySacIn.setSendUserKey(obj.getSendUserKey());
			historySacIn.setSendDeviceKey(obj.getSendDeviceKey());
			historySacIn.setPrchsReqPathCd(obj.getPrchsReqPathCd());
			historySacIn.setRecvDt(obj.getRecvDt());
			historySacIn.setProdId(obj.getProdId());
			historySacIn.setProdAmt(obj.getProdAmt());
			historySacIn.setProdQty(obj.getProdQty());
			historySacIn.setTenantProdGrpCd(obj.getTenantProdGrpCd());
			historySacIn.setStatusCd(obj.getStatusCd());
			historySacIn.setUseStartDt(obj.getUseStartDt());
			historySacIn.setUseExprDt(obj.getUseExprDt());
			historySacIn.setHidingYn(obj.getHidingYn());
			historySacIn.setCancelReqPathCd(obj.getCancelReqPathCd());
			historySacIn.setCancelDt(obj.getCancelDt());
			historySacIn.setCpnPublishCd(obj.getCpnPublishCd());
			historySacIn.setCpnDlvUrl(obj.getCpnDlvUrl());
			historySacIn.setPrchsCaseCd(obj.getPrchsCaseCd());
			historySacIn.setDwldStartDt(obj.getDwldStartDt());
			historySacIn.setDwldExprDt(obj.getDwldExprDt());
			historySacIn.setPrchsProdType(obj.getPrchsProdType());
			historySacIn.setUseFixrateProdId(obj.getUseFixrateProdId());
			historySacIn.setResvCol01(obj.getResvCol01());
			historySacIn.setResvCol02(obj.getResvCol02());
			historySacIn.setResvCol03(obj.getResvCol03());
			historySacIn.setResvCol04(obj.getResvCol04());
			historySacIn.setResvCol05(obj.getResvCol05());
			historySacIn.setDrmYn(obj.getDrmYn());
			historySacIn.setAlarmYn(obj.getAlarmYn());
			historySacIn.setPermitDeviceYn(obj.getPermitDeviceYn());

			// 수신자 정보 set
			historySacIn.setRecvTenantId(obj.getRecvTenantId());
			historySacIn.setRecvUserKey(obj.getRecvUserKey());
			historySacIn.setRecvDeviceKey(obj.getRecvDeviceKey());

			// 정액제 정보 set
			historySacIn.setPaymentStartDt(obj.getPaymentStartDt());
			historySacIn.setPaymentEndDt(obj.getPaymentEndDt());
			historySacIn.setAfterPaymentDt(obj.getAfterPaymentDt());
			historySacIn.setPrchsTme(obj.getPrchsTme());
			historySacIn.setClosedCd(obj.getClosedCd());
			historySacIn.setClosedDt(obj.getClosedDt());
			historySacIn.setClosedReasonCd(obj.getClosedReasonCd());
			historySacIn.setClosedReqPathCd(obj.getClosedReqPathCd());

			historySacInList.add(historySacIn);

		}

		response.setHistoryList(historySacInList);
		response.setTotalCnt(sacRes.getTotalCnt());
		/********************************************
		 * Response Mapping End
		 ********************************************/

		return response;

	}

	/**
	 * 구매건수 조회 기능을 제공한다.
	 * 
	 * @param request
	 *            구매내역요청
	 * @return HistoryCountSacInRes
	 */
	@Override
	public HistoryCountSacInRes searchHistoryCount(HistoryCountSacInReq request) {
		// logger.debug("### historyListRequest : {}", request);

		HistoryCountSacReq sacReq = new HistoryCountSacReq();
		HistoryCountSacRes sacRes = new HistoryCountSacRes();

		// Request Header Info
		sacReq.setTenantId(request.getTenantId());

		// Request Parameter Info
		sacReq.setUserKey(request.getUserKey());
		sacReq.setDeviceKey(request.getDeviceKey());
		sacReq.setStartDt(request.getStartDt());
		sacReq.setEndDt(request.getEndDt());
		sacReq.setPrchsProdType(request.getPrchsProdType());
		sacReq.setPrchsStatusCd(request.getPrchsStatusCd());
		sacReq.setPrchsCaseCd(request.getPrchsCaseCd());
		sacReq.setPrchsProdHaveYn(request.getPrchsProdHaveYn());
		sacReq.setPrchsReqPathCd(request.getPrchsReqPathCd());
		sacReq.setTenantProdGrpCd(request.getTenantProdGrpCd());

		ProductListSac product = new ProductListSac();
		List<ProductListSac> productList = new ArrayList<ProductListSac>();

		if (request.getProductList() != null && request.getProductList().size() > 0) {
			for (ProductListSacIn obj : request.getProductList()) {
				product = new ProductListSac();
				product.setProdId(obj.getProdId());
				productList.add(product);
			}
		}
		sacReq.setUseFixrateProdId(request.getUseFixrateProdId());
		sacReq.setProductList(productList);
		sacReq.setHidingYn(request.getHidingYn());
		sacReq.setGiftRecvConfYn(request.getGiftRecvConfYn());

		// SAC Service Call
		sacRes = this.service.searchHistoryCount(sacReq);

		/********************************************
		 * Response Mapping Start
		 ********************************************/
		ProductCountSacIn productCountSacIn = new ProductCountSacIn();
		List<ProductCountSacIn> sacInProdList = new ArrayList<ProductCountSacIn>();
		HistoryCountSacInRes response = new HistoryCountSacInRes();

		if (sacRes.getCntList() != null && sacRes.getCntList().size() > 0) {
			for (ProductCountSac obj : sacRes.getCntList()) {
				productCountSacIn = new ProductCountSacIn();
				productCountSacIn.setProdId(obj.getProdId());
				productCountSacIn.setProdCount(obj.getProdCount());

				sacInProdList.add(productCountSacIn);
			}
		}

		response.setTotalCnt(sacRes.getTotalCnt());
		response.setCntList(sacInProdList);
		/********************************************
		 * Response Mapping End
		 ********************************************/

		return response;
	}
}
