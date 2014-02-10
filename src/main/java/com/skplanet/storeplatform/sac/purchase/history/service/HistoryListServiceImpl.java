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

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.purchase.client.history.sci.HistorySCI;
import com.skplanet.storeplatform.purchase.client.history.vo.HistoryCountScReq;
import com.skplanet.storeplatform.purchase.client.history.vo.HistoryCountScRes;
import com.skplanet.storeplatform.purchase.client.history.vo.HistoryListScReq;
import com.skplanet.storeplatform.purchase.client.history.vo.HistoryListScRes;
import com.skplanet.storeplatform.purchase.client.history.vo.HistorySc;
import com.skplanet.storeplatform.purchase.client.history.vo.ProductCountSc;
import com.skplanet.storeplatform.purchase.constant.PurchaseConstants;
import com.skplanet.storeplatform.sac.client.purchase.history.vo.HistoryCountSacReq;
import com.skplanet.storeplatform.sac.client.purchase.history.vo.HistoryCountSacRes;
import com.skplanet.storeplatform.sac.client.purchase.history.vo.HistoryListSacReq;
import com.skplanet.storeplatform.sac.client.purchase.history.vo.HistoryListSacRes;
import com.skplanet.storeplatform.sac.client.purchase.history.vo.HistorySac;
import com.skplanet.storeplatform.sac.client.purchase.history.vo.ProductCountSac;
import com.skplanet.storeplatform.sac.client.purchase.history.vo.ProductListSac;
import com.skplanet.storeplatform.sac.display.category.service.CategorySpecificProductService;

/**
 * 구매내역 Implements
 * 
 * Updated on : 2014-01-10 Updated by : 양주원, 엔텔스.
 */
@Service
public class HistoryListServiceImpl implements HistoryListService {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

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
	 * @return HistoryListSacRes
	 */
	@Override
	public HistoryListSacRes searchHistoryList(HistoryListSacReq request) {
		this.LOGGER.debug("HistoryListSacRes : {}", request);

		// SC request/response VO
		HistoryListScReq scRequest = new HistoryListScReq();
		HistoryListScRes scResponse = new HistoryListScRes();

		// SAC Response VO
		HistoryListSacRes response = new HistoryListSacRes();
		List<HistorySac> sacHistoryList = new ArrayList<HistorySac>();
		HistorySac historySac = new HistorySac();

		String prodArray = "";

		// SC Request Set
		scRequest.setTenantId(request.getTenantId());
		scRequest.setUserKey(request.getUserKey());
		scRequest.setDeviceKey(request.getDeviceKey());
		scRequest.setStartDt(request.getStartDt());
		scRequest.setEndDt(request.getEndDt());
		scRequest.setPrchsProdType(request.getPrchsProdType());
		scRequest.setPrchsStatusCd(request.getPrchsStatusCd());
		scRequest.setPrchsCaseCd(request.getPrchsCaseCd());
		scRequest.setTenantProdGrpCd(request.getTenantProdGrpCd());

		List<String> prodList = new ArrayList<String>();
		if (request.getProductList() != null && request.getProductList().size() > 0) {
			for (ProductListSac obj : request.getProductList()) {
				if (!StringUtils.isEmpty(obj.getProdId())) {
					prodList.add(obj.getProdId());
				}
			}
		}

		// 보유상품 조회일 때만 해당값이 조회 조건으로 사용된다.
		if (PurchaseConstants.PRCHS_PROD_TYPE_OWN.equals(request.getPrchsProdType())) {
			scRequest.setUseFixrateProdId(request.getUseFixrateProdId());
		}

		scRequest.setProductList(prodList);
		scRequest.setHidingYn(request.getHidingYn());
		scRequest.setOffset(request.getOffset());
		scRequest.setCount(request.getCount());

		try {
			// SC Call
			scResponse = this.historySci.searchHistoryList(scRequest);
		} catch (Exception ex) {
			// TODO : 추후 메세지 추가후 처리함
			throw new StorePlatformException("구매SC 호출중 오류발생", ex);
		}

		// SC객체를 SAC객체로 맵핑작업
		for (HistorySc obj : scResponse.getHistoryList()) {

			historySac = new HistorySac();

			// 구매정보 set
			historySac.setTenantId(obj.getTenantId());
			historySac.setSystemId(obj.getSystemId());
			historySac.setPrchsId(obj.getPrchsId());
			historySac.setPrchsDtlId(obj.getPrchsDtlId());
			historySac.setUseTenantId(obj.getUseTenantId());
			historySac.setUseUserKey(obj.getUseUserKey());
			historySac.setUseDeviceKey(obj.getUseDeviceKey());

			scRequest.setUserKey(request.getUserKey());
			scRequest.setDeviceKey(request.getDeviceKey());

			historySac.setPrchsDt(obj.getPrchsDt());
			historySac.setTotAmt(obj.getTotAmt());
			historySac.setSendUserKey(obj.getSendUserKey());
			historySac.setSendDeviceKey(obj.getSendDeviceKey());
			historySac.setRecvDt(obj.getRecvDt());
			historySac.setProdId(obj.getProdId());
			historySac.setProdAmt(obj.getProdAmt());
			historySac.setProdQty(obj.getProdQty());
			historySac.setTenantProdGrpCd(obj.getTenantProdGrpCd());
			historySac.setStatusCd(obj.getStatusCd());
			historySac.setUseStartDt(obj.getUseStartDt());
			historySac.setUseExprDt(obj.getUseExprDt());
			historySac.setHidingYn(obj.getHidingYn());
			historySac.setCancelReqPathCd(obj.getCancelReqPathCd());
			historySac.setCancelDt(obj.getCancelDt());
			historySac.setCpnPublishCd(obj.getCpnPublishCd());
			historySac.setCpnDlvUrl(obj.getCpnDlvUrl());
			historySac.setPrchsCaseCd(obj.getPrchsCaseCd());
			historySac.setRePrchsPmtYn(obj.getRePrchsPmtYn());
			historySac.setDwldStartDt(obj.getDwldStartDt());
			historySac.setDwldExprDt(obj.getDwldExprDt());
			historySac.setPrchsProdType(obj.getPrchsProdType());
			historySac.setUseFixrateProdId(obj.getUseFixrateProdId());
			historySac.setResvCol01(obj.getResvCol01());
			historySac.setResvCol02(obj.getResvCol02());
			historySac.setResvCol03(obj.getResvCol03());
			historySac.setResvCol04(obj.getResvCol04());
			historySac.setResvCol05(obj.getResvCol05());

			// 수신자 정보 set
			historySac.setRecvTenantId(obj.getRecvTenantId());
			historySac.setRecvUserKey(obj.getRecvUserKey());
			historySac.setRecvDeviceKey(obj.getRecvDeviceKey());

			// 정액제 정보 set
			historySac.setPaymentStartDt(obj.getPaymentStartDt());
			historySac.setPaymentEndDt(obj.getPaymentEndDt());
			historySac.setAfterPaymentDt(obj.getAfterPaymentDt());
			historySac.setPrchsTme(obj.getPrchsTme());
			historySac.setClosedCd(obj.getClosedCd());
			historySac.setClosedDt(obj.getClosedDt());
			historySac.setClosedReasonCd(obj.getClosedReasonCd());
			historySac.setClosedReqPathCd(obj.getClosedReqPathCd());

			sacHistoryList.add(historySac);

			// 상품정보 조회를 위한 상품ID 셋팅
			prodArray = prodArray + historySac.getProdId() + "+";
		}

		// CategorySpecificReq productReq = new CategorySpecificReq();
		// this.logger.debug("#######################################" + prodArray);
		// productReq.setList(prodArray);
		//
		// // TODO : 테스트용 데이터 받기
		// // productReq.setDummy("dummy");
		//
		// // 상품정보조회
		// CategorySpecificRes productRes = this.productService.getSpecificProductList(productReq, requestHeader);
		//
		// List<Product> resProdList = productRes.getProductList();
		// this.logger.debug("prodList ==== " + resProdList.toString());
		// for (History obj : sacHistoryList) {
		//
		// for (Product product : resProdList) {
		//
		// if (obj.getProdId().equals(product.getIdentifierList().get(0).getText())) {
		// // obj.setProduct(product); //
		// obj.setProdNm(product.getTitle().getText());
		// obj.setGrade(product.getRights().getGrade());
		// break;
		// }
		// }
		// }

		response.setHistoryList(sacHistoryList);
		response.setTotalCnt(scResponse.getTotalCnt());

		// logger.debug("list : {}", historyListRes);
		return response;
	}

	/**
	 * 구매내역건수 조회 기능을 제공한다.
	 * 
	 * @param request
	 *            구매내역요청
	 * @return HistoryCountSacRes
	 */
	@Override
	public HistoryCountSacRes searchHistoryCount(HistoryCountSacReq request) {
		// logger.debug("list : {}", historyListReq);

		// SC request/response VO
		HistoryCountScReq scRequest = new HistoryCountScReq();
		HistoryCountScRes scResponse = new HistoryCountScRes();

		// SAC Response VO
		HistoryCountSacRes response = new HistoryCountSacRes();
		List<ProductCountSac> sacProdList = new ArrayList<ProductCountSac>();
		ProductCountSac productCountSac = new ProductCountSac();

		// SC Request Set
		scRequest.setTenantId(request.getTenantId());
		scRequest.setUserKey(request.getUserKey());
		scRequest.setDeviceKey(request.getDeviceKey());
		scRequest.setStartDt(request.getStartDt());
		scRequest.setEndDt(request.getEndDt());
		scRequest.setPrchsProdType(request.getPrchsProdType());
		scRequest.setPrchsStatusCd(request.getPrchsStatusCd());
		scRequest.setPrchsCaseCd(request.getPrchsCaseCd());
		scRequest.setTenantProdGrpCd(request.getTenantProdGrpCd());

		List<String> prodList = new ArrayList<String>();
		if (request.getProductList() != null && request.getProductList().size() > 0) {
			for (ProductListSac obj : request.getProductList()) {
				if (!StringUtils.isEmpty(obj.getProdId())) {
					prodList.add(obj.getProdId());
				}
			}
		}
		scRequest.setUseFixrateProdId(request.getUseFixrateProdId());
		scRequest.setProductList(prodList);
		scRequest.setHidingYn(request.getHidingYn());

		// SC Call
		scResponse = this.historySci.searchHistoryCount(scRequest);

		if (scResponse.getCntList() != null && scResponse.getCntList().size() > 0) {
			for (ProductCountSc obj : scResponse.getCntList()) {
				productCountSac = new ProductCountSac();
				productCountSac.setProdId(obj.getProdId());
				productCountSac.setProdCount(obj.getProdCount());

				sacProdList.add(productCountSac);
			}
		}

		response.setTotalCnt(scResponse.getTotalCnt());
		response.setCntList(sacProdList);

		// logger.debug("list : {}", historyListRes);
		return response;
	}
}
