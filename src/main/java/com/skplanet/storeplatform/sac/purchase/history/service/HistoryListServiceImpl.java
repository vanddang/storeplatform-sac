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
import com.skplanet.storeplatform.sac.client.display.vo.category.CategorySpecificReq;
import com.skplanet.storeplatform.sac.client.display.vo.category.CategorySpecificRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.client.purchase.history.vo.History;
import com.skplanet.storeplatform.sac.client.purchase.history.vo.HistoryCountReq;
import com.skplanet.storeplatform.sac.client.purchase.history.vo.HistoryCountRes;
import com.skplanet.storeplatform.sac.client.purchase.history.vo.HistoryListReq;
import com.skplanet.storeplatform.sac.client.purchase.history.vo.HistoryListRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.category.service.CategorySpecificProductService;
import com.skplanet.storeplatform.sac.purchase.common.PurchaseConstants;

/**
 * 구매내역 Implements
 * 
 * Updated on : 2014-01-10 Updated by : 양주원, 엔텔스.
 */
@Service
@Transactional
public class HistoryListServiceImpl implements HistoryListService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private HistorySCI historySci;

	@Autowired
	private CategorySpecificProductService productService;

	/**
	 * 구매내역 조회 기능을 제공한다.
	 * 
	 * @param request
	 *            구매내역요청
	 * @param requestHeader
	 *            공통헤더정보
	 * @return HistoryListResponse
	 */
	@Override
	public HistoryListRes list(HistoryListReq request, SacRequestHeader requestHeader) {
		// logger.debug("list : {}", historyListReq);

		// SC request/response VO
		HistoryListRequest scRequest = new HistoryListRequest();
		HistoryListResponse scResponse = new HistoryListResponse();

		// SAC Response VO
		HistoryListRes response = new HistoryListRes();
		List<History> sacHistoryList = new ArrayList<History>();
		History history = new History();

		String prodArray = "";

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

		if (PurchaseConstants.PRCHS_PROD_TYPE_OWN.equals(request.getPrchsProdType())) {
			this.logger.debug("##### 보유상품조회");
			// SC Call
			scResponse = this.historySci.listHistory(scRequest);

		} else if (PurchaseConstants.PRCHS_PROD_TYPE_SEND.equals(request.getPrchsProdType())) {
			this.logger.debug("##### 미보유상품조회");
			// SC Call
			scResponse = this.historySci.sendListHistory(scRequest);

		} else if (PurchaseConstants.PRCHS_PROD_TYPE_FIX.equals(request.getPrchsProdType())) {
			this.logger.debug("##### 구매권한상품조회");
			// SC Call
			scResponse = this.historySci.authListHistory(scRequest);
		} else {
			// 오류처리... 잘못된 요청
			return response;
		}

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

			history.setPaymentStartDt(obj.getPaymentStartDt());
			history.setPaymentEndDt(obj.getPaymentEndDt());
			history.setAfterPaymentDt(obj.getAfterPaymentDt());

			history.setClosedCd(obj.getClosedCd());
			history.setClosedDt(obj.getClosedDt());
			history.setClosedReasonCd(obj.getClosedReasonCd());
			history.setClosedReqPathCd(obj.getClosedReqPathCd());

			sacHistoryList.add(history);

			// 상품정보 조회를 위한 상품ID 셋팅
			prodArray = prodArray + history.getProdId() + "+";
		}

		CategorySpecificReq productReq = new CategorySpecificReq();
		productReq.setList(prodArray);

		// TODO : 테스트용 데이터 받기
		productReq.setDummy("dummy");

		// 상품정보조회
		CategorySpecificRes productRes = this.productService.getSpecificProductList(productReq, requestHeader);

		List<Product> resProdList = productRes.getProductList();

		for (History obj : sacHistoryList) {

			for (Product product : resProdList) {

				if (obj.getProdId().equals(product.getIdentifier().getText())) {
					// obj.setProduct(product); //
					obj.setProdNm(product.getTitle().getText());
					obj.setGrade(product.getRights().getGrade());
					break;
				}
			}
		}

		this.logger.debug("prodList ==== " + resProdList.toString());

		response.setHistoryList(sacHistoryList);
		response.setTotalCnt(sacHistoryList.size());

		// logger.debug("list : {}", historyListRes);
		return response;
	}

	/**
	 * 구매내역건수 조회 기능을 제공한다.
	 * 
	 * @param request
	 *            구매내역요청
	 * @return HistoryListResponse
	 */
	@Override
	public HistoryCountRes count(HistoryCountReq request) {
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
