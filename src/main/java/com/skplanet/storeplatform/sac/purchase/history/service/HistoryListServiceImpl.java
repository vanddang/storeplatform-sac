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

import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.purchase.client.history.sci.HistorySCI;
import com.skplanet.storeplatform.purchase.client.history.vo.HistoryCountScReq;
import com.skplanet.storeplatform.purchase.client.history.vo.HistoryCountScRes;
import com.skplanet.storeplatform.purchase.client.history.vo.HistoryListScReq;
import com.skplanet.storeplatform.purchase.client.history.vo.HistoryListScRes;
import com.skplanet.storeplatform.purchase.client.history.vo.HistorySc;
import com.skplanet.storeplatform.purchase.client.history.vo.ProductCountSc;
import com.skplanet.storeplatform.sac.client.purchase.history.vo.HistoryCountSacReq;
import com.skplanet.storeplatform.sac.client.purchase.history.vo.HistoryCountSacRes;
import com.skplanet.storeplatform.sac.client.purchase.history.vo.HistoryListSacReq;
import com.skplanet.storeplatform.sac.client.purchase.history.vo.HistoryListSacRes;
import com.skplanet.storeplatform.sac.client.purchase.history.vo.HistorySac;
import com.skplanet.storeplatform.sac.client.purchase.history.vo.ProductCountSac;
import com.skplanet.storeplatform.sac.client.purchase.history.vo.ProductListSac;
import com.skplanet.storeplatform.sac.display.category.service.CategorySpecificProductService;
import com.skplanet.storeplatform.sac.purchase.common.service.PurchaseTenantPolicyService;
import com.skplanet.storeplatform.sac.purchase.common.vo.PurchaseTenantPolicy;
import com.skplanet.storeplatform.sac.purchase.constant.PurchaseConstants;

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

	@Autowired
	private PurchaseTenantPolicyService purchaseTenantPolicyService;

	/**
	 * 구매내역 조회 기능을 제공한다.
	 * 
	 * @param request
	 *            구매내역요청
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

		/*************************************************
		 * SC Request Setting Start
		 *************************************************/
		scRequest.setTenantId(request.getTenantId());
		scRequest.setUserKey(request.getUserKey());

		// TenantProdGrpCd가 요청값으로 전달되면 구매 정책을 확인한다. (Device기반 구매내역관리)
		// TenantProdGrpCd가 Device기반 정책이면 device_key를 세팅하고 아니면 공백처리하여 쿼리 조건으로 사용되지 않게 처리됨
		if (StringUtils.isNotBlank(request.getTenantProdGrpCd())) {
			List<PurchaseTenantPolicy> purchaseTenantPolicyList = this.purchaseTenantPolicyService
					.searchPurchaseTenantPolicyList(request.getTenantId(), request.getTenantProdGrpCd(),
							PurchaseConstants.POLICY_ID_008);

			if (purchaseTenantPolicyList.size() > 0) {
				scRequest.setDeviceKey(request.getDeviceKey());
			}
		}

		scRequest.setStartDt(request.getStartDt());
		scRequest.setEndDt(request.getEndDt());
		scRequest.setTenantProdGrpCd(request.getTenantProdGrpCd());

		List<String> prodList = new ArrayList<String>();
		if (request.getProductList() != null && request.getProductList().size() > 0) {
			for (ProductListSac obj : request.getProductList()) {
				if (!StringUtils.isEmpty(obj.getProdId())) {
					prodList.add(obj.getProdId());
				}
			}
		}
		scRequest.setProductList(prodList);
		scRequest.setPrchsProdHaveYn(request.getPrchsProdHaveYn());
		scRequest.setPrchsProdType(request.getPrchsProdType());
		scRequest.setPrchsCaseCd(request.getPrchsCaseCd());
		scRequest.setPrchsReqPathCd(request.getPrchsReqPathCd());
		scRequest.setHidingYn(request.getHidingYn());
		scRequest.setPrchsStatusCd(request.getPrchsStatusCd());

		// 보유상품 조회일 때만 해당값이 조회 조건으로 사용된다.
		if (PurchaseConstants.USE_Y.equals(request.getPrchsProdHaveYn())) {
			scRequest.setUseFixrateProdId(request.getUseFixrateProdId());
		}
		scRequest.setGiftRecvConfYn(request.getGiftRecvConfYn());

		// pageInfo set
		scRequest.getPage().setNo(request.getOffset());
		scRequest.getPage().setRows(request.getCount());
		/*************************************************
		 * SC Request Setting End
		 *************************************************/

		// try {
		// SC Call
		scResponse = this.historySci.searchHistoryList(scRequest);
		// } catch (Exception ex) {
		// TODO : 추후 메세지 추가후 처리함
		// throw new StorePlatformException("구매SC 호출중 오류발생", ex);
		// }

		// SC객체를 SAC객체로 맵핑작업

		/*************************************************
		 * SC -> SAC Response Setting Start
		 *************************************************/
		for (HistorySc obj : scResponse.getHistoryList()) {

			historySac = new HistorySac();

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
			// 수신자 정보 set
			historySac.setRecvTenantId(obj.getRecvTenantId());
			historySac.setRecvUserKey(obj.getRecvUserKey());
			historySac.setRecvDeviceKey(obj.getRecvDeviceKey());
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

			// 정액제 정보 set
			historySac.setPaymentStartDt(obj.getPaymentStartDt());
			historySac.setPaymentEndDt(obj.getPaymentEndDt());
			historySac.setAfterPaymentDt(obj.getAfterPaymentDt());
			historySac.setPrchsTme(obj.getPrchsTme());
			historySac.setClosedCd(obj.getClosedCd());
			historySac.setClosedDt(obj.getClosedDt());
			historySac.setClosedReasonCd(obj.getClosedReasonCd());
			historySac.setClosedReqPathCd(obj.getClosedReqPathCd());

			historySac.setPrchsProdType(obj.getPrchsProdType());

			historySac.setResvCol01(obj.getResvCol01());
			historySac.setResvCol02(obj.getResvCol02());
			historySac.setResvCol03(obj.getResvCol03());
			historySac.setResvCol04(obj.getResvCol04());
			historySac.setResvCol05(obj.getResvCol05());
			historySac.setDrmYn(obj.getDrmYn());
			historySac.setAlarmYn(obj.getAlarmYn());

			sacHistoryList.add(historySac);

			// 상품정보 조회를 위한 상품ID 셋팅
			prodArray = prodArray + historySac.getProdId() + "+";
		}

		/*************************************************
		 * SC -> SAC Response Setting Start
		 *************************************************/

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
	 * 구매건수 조회 기능을 제공한다.
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

		/*************************************************
		 * SC Request Setting Start
		 *************************************************/
		scRequest.setTenantId(request.getTenantId());
		scRequest.setUserKey(request.getUserKey());
		// scRequest.setDeviceKey(request.getDeviceKey());
		scRequest.setStartDt(request.getStartDt());
		scRequest.setEndDt(request.getEndDt());
		scRequest.setPrchsProdType(request.getPrchsProdType());
		scRequest.setPrchsStatusCd(request.getPrchsStatusCd());
		scRequest.setPrchsProdHaveYn(request.getPrchsProdHaveYn());
		scRequest.setPrchsReqPathCd(request.getPrchsReqPathCd());
		scRequest.setPrchsCaseCd(request.getPrchsCaseCd());
		scRequest.setTenantProdGrpCd(request.getTenantProdGrpCd());
		scRequest.setUseFixrateProdId(request.getUseFixrateProdId());

		// TenantProdGrpCd가 요청값으로 전달되면 구매 정책을 확인한다. (Device기반 구매내역관리)
		// TenantProdGrpCd가 Device기반 정책이면 device_key를 세팅하고 아니면 공백처리하여 쿼리 조건으로 사용되지 않게 처리됨
		if (StringUtils.isNotBlank(request.getTenantProdGrpCd())) {
			List<PurchaseTenantPolicy> purchaseTenantPolicyList = this.purchaseTenantPolicyService
					.searchPurchaseTenantPolicyList(request.getTenantId(), request.getTenantProdGrpCd(),
							PurchaseConstants.POLICY_ID_008);

			if (purchaseTenantPolicyList.size() > 0) {
				scRequest.setDeviceKey(request.getDeviceKey());
			}
		}

		List<String> prodList = new ArrayList<String>();
		if (request.getProductList() != null && request.getProductList().size() > 0) {
			for (ProductListSac obj : request.getProductList()) {
				if (!StringUtils.isEmpty(obj.getProdId())) {
					prodList.add(obj.getProdId());
				}
			}
		}
		scRequest.setProductList(prodList);
		scRequest.setHidingYn(request.getHidingYn());
		scRequest.setGiftRecvConfYn(request.getGiftRecvConfYn());

		/*************************************************
		 * SC Request Setting End
		 *************************************************/

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
