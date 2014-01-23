/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.order.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.sac.client.purchase.vo.order.CreatePurchaseReqProduct;
import com.skplanet.storeplatform.sac.purchase.common.service.PurchaseDisplayPartService;
import com.skplanet.storeplatform.sac.purchase.common.service.PurchaseDisplayPartServiceImpl;
import com.skplanet.storeplatform.sac.purchase.common.service.PurchaseMemberPartService;
import com.skplanet.storeplatform.sac.purchase.common.service.PurchaseMemberPartServiceImpl;
import com.skplanet.storeplatform.sac.purchase.constant.PurchaseConstants;
import com.skplanet.storeplatform.sac.purchase.order.dummy.vo.DummyMember;
import com.skplanet.storeplatform.sac.purchase.order.dummy.vo.DummyProduct;
import com.skplanet.storeplatform.sac.purchase.order.vo.PurchaseOrder;
import com.skplanet.storeplatform.sac.purchase.order.vo.PurchaseOrderResult;

/**
 * 
 * 구매 적합성 체크 서비스 구현체
 * 
 * Updated on : 2014. 1. 22. Updated by : 이승택, nTels.
 */
@Service
@Transactional
public class PurchaseOrderValidationServiceImpl implements PurchaseOrderValidationService {

	private final PurchaseMemberPartService memberPartService = new PurchaseMemberPartServiceImpl();
	private final PurchaseDisplayPartService displayPartService = new PurchaseDisplayPartServiceImpl();

	/**
	 * 
	 * <pre>
	 * 회원 적합성 체크.
	 * </pre>
	 * 
	 * @param purchaseOrderInfo
	 *            구매 주문 정보
	 * @return 회원 적합성 체크 결과 정보: null-정상, not null-체크 오류 결과 정보
	 */
	@Override
	public PurchaseOrderResult validateMember(PurchaseOrder purchaseOrderInfo) {
		// ----------------------------------------------------------------------------------------------
		// 구매(선물발신) 회원

		// 회원 정보 조회
		DummyMember userInfo = this.memberPartService.searchDummyUserDetail(purchaseOrderInfo.getTenantId(),
				purchaseOrderInfo.getSystemId(), purchaseOrderInfo.getUserKey(), purchaseOrderInfo.getDeviceKey());

		// 회원 체크
		if (userInfo == null) { // 회원 정보 조회 실패
			return new PurchaseOrderResult("SAC_PUR_00001", "not found user: " + purchaseOrderInfo.getUserKey());
		}
		if ("US010701".equals(userInfo.getUserStatusCd()) == false) { // 회원상태 정상 아님
			return new PurchaseOrderResult("SAC_PUR_00002", "not available user status: " + userInfo.getUserStatusCd());
		}

		purchaseOrderInfo.setPurchaseMember(userInfo);

		// ----------------------------------------------------------------------------------------------
		// 선물 수신 회원

		if (PurchaseConstants.PRCHS_CASE_GIFT_CD.equals(purchaseOrderInfo.getPrchsCaseCd())) {
			// 회원 정보 조회
			DummyMember recvUserInfo = this.memberPartService.searchDummyUserDetail(
					purchaseOrderInfo.getRecvTenantId(), purchaseOrderInfo.getSystemId(),
					purchaseOrderInfo.getRecvUserKey(), purchaseOrderInfo.getRecvDeviceKey());

			// 회원 체크
			if (recvUserInfo == null) { // 회원 정보 조회 실패
				return new PurchaseOrderResult("SAC_PUR_00003", "not found recv user: "
						+ purchaseOrderInfo.getRecvUserKey());
			}
			if ("US010701".equals(recvUserInfo.getUserStatusCd()) == false) { // 회원상태 정상 아님
				return new PurchaseOrderResult("SAC_PUR_00004", "not available recvuser status: "
						+ recvUserInfo.getUserStatusCd());
			}

			purchaseOrderInfo.setRecvMember(recvUserInfo);
		}

		return null;
	}

	/**
	 * 
	 * <pre>
	 * 상품 적합성 체크.
	 * </pre>
	 * 
	 * @param purchaseOrderInfo
	 *            구매 주문 정보
	 * @return 상품 적합성 체크 결과 정보: null-정상, not null-체크 오류 결과 정보
	 */
	@Override
	public PurchaseOrderResult validateProduct(PurchaseOrder purchaseOrderInfo) {

		String tenantId = purchaseOrderInfo.getTenantId();
		String systemId = purchaseOrderInfo.getSystemId();
		String deviceModelCd = purchaseOrderInfo.getDeviceModelCd();
		List<DummyProduct> productInfoList = purchaseOrderInfo.getProductList();

		// 상품 정보 조회
		DummyProduct productInfo = null;
		for (CreatePurchaseReqProduct reqProduct : purchaseOrderInfo.getCreatePurchaseReq().getProductList()) {
			productInfo = this.displayPartService.searchDummyProductDetail(tenantId, systemId, reqProduct.getProdId(),
					deviceModelCd);

			if (productInfo == null) {
				return new PurchaseOrderResult("SAC_PUR_00011", "not found product: " + reqProduct.getProdId());
			}
			if (productInfo.getbSell() == false) {
				return new PurchaseOrderResult("SAC_PUR_00012", "not selling product");
			}
			if (productInfo.getbSupport() == false) {
				return new PurchaseOrderResult("SAC_PUR_00013", "not support product");
			}

			productInfoList.add(productInfo);
		}

		return null;
	}

	/**
	 * 
	 * <pre>
	 * 구매 적합성 체크.
	 * </pre>
	 * 
	 * @param purchaseOrderInfo
	 *            구매 주문 정보
	 * @return 구매 적합성 체크 결과 정보: null-정상, not null-체크 오류 결과 정보
	 */
	@Override
	public PurchaseOrderResult validatePurchase(PurchaseOrder purchaseOrderInfo) {
		DummyMember userInfo = PurchaseConstants.PRCHS_CASE_GIFT_CD.equals(purchaseOrderInfo.getPrchsCaseCd()) ? purchaseOrderInfo
				.getPurchaseMember() : purchaseOrderInfo.getRecvMember();

		for (DummyProduct product : purchaseOrderInfo.getProductList()) {
			if ("PD004404".equals(product) && userInfo.getAge() < 20) {
				return new PurchaseOrderResult("SAC_PUR_00022", "not allow age: " + userInfo.getAge());
			}

			// 쇼핑상품 경우, 발급 가능 여부 확인
		}

		return null;
	}

}
