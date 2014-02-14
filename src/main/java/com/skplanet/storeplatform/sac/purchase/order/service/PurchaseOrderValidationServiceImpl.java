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

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.purchase.client.history.sci.ExistenceSCI;
import com.skplanet.storeplatform.purchase.client.history.vo.ExistenceItemSc;
import com.skplanet.storeplatform.purchase.client.history.vo.ExistenceScReq;
import com.skplanet.storeplatform.purchase.client.history.vo.ExistenceScRes;
import com.skplanet.storeplatform.sac.client.purchase.vo.order.CreatePurchaseSacReqProduct;
import com.skplanet.storeplatform.sac.purchase.common.service.PurchaseDisplayPartService;
import com.skplanet.storeplatform.sac.purchase.common.service.PurchaseDisplayPartServiceImpl;
import com.skplanet.storeplatform.sac.purchase.common.service.PurchaseMemberPartService;
import com.skplanet.storeplatform.sac.purchase.common.service.PurchaseMemberPartServiceImpl;
import com.skplanet.storeplatform.sac.purchase.constant.PurchaseConstants;
import com.skplanet.storeplatform.sac.purchase.order.dummy.vo.DummyMember;
import com.skplanet.storeplatform.sac.purchase.order.dummy.vo.DummyProduct;
import com.skplanet.storeplatform.sac.purchase.order.vo.PurchaseOrderInfo;
import com.skplanet.storeplatform.sac.purchase.shopping.service.ShoppingService;
import com.skplanet.storeplatform.sac.purchase.shopping.vo.CouponPublishAvailableSacParam;
import com.skplanet.storeplatform.sac.purchase.shopping.vo.CouponPublishAvailableSacResult;

/**
 * 
 * 구매 적합성 체크 서비스 구현체
 * 
 * Updated on : 2014. 1. 22. Updated by : 이승택, nTels.
 */
@Service
public class PurchaseOrderValidationServiceImpl implements PurchaseOrderValidationService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private final PurchaseMemberPartService memberPartService = new PurchaseMemberPartServiceImpl();
	private final PurchaseDisplayPartService displayPartService = new PurchaseDisplayPartServiceImpl();

	@Autowired
	private ExistenceSCI existenceSCI;

	@Autowired
	private ShoppingService shoppingService;

	/**
	 * 
	 * <pre>
	 * 회원 적합성 체크.
	 * </pre>
	 * 
	 * @param purchaseOrderInfo
	 *            구매 주문 정보
	 */
	@Override
	public void validateMember(PurchaseOrderInfo purchaseOrderInfo) {
		// ----------------------------------------------------------------------------------------------
		// 구매(선물발신) 회원

		// 회원 정보 조회
		DummyMember userInfo = this.memberPartService.searchDummyUserDetail(purchaseOrderInfo.getTenantId(),
				purchaseOrderInfo.getSystemId(), purchaseOrderInfo.getUserKey(), purchaseOrderInfo.getDeviceKey());

		// 회원정보 조회 실패
		if (userInfo == null) {
			throw new StorePlatformException("SAC_PUR_0001", "회원정보를 조회할 수 없습니다: " + purchaseOrderInfo.getUserKey());
		}
		// 회원상태 체크
		if ("US010701".equals(userInfo.getUserStatusCd()) == false) {
			throw new StorePlatformException("SAC_PUR_0001", "정상회원이 아닙니다: " + userInfo.getUserStatusCd());
		}

		purchaseOrderInfo.setPurchaseMember(userInfo);

		// ----------------------------------------------------------------------------------------------
		// 선물 수신 회원

		if (PurchaseConstants.PRCHS_CASE_GIFT_CD.equals(purchaseOrderInfo.getPrchsCaseCd())) {
			// 회원 정보 조회
			DummyMember recvUserInfo = this.memberPartService.searchDummyUserDetail(
					purchaseOrderInfo.getRecvTenantId(), purchaseOrderInfo.getSystemId(),
					purchaseOrderInfo.getRecvUserKey(), purchaseOrderInfo.getRecvDeviceKey());

			// 회원정보 조회 실패
			if (recvUserInfo == null) {
				throw new StorePlatformException("SAC_PUR_0001", "선물 수신 회원정보를 조회할 수 없습니다: "
						+ purchaseOrderInfo.getRecvUserKey());
			}
			// 회원상태 체크
			if ("US010701".equals(recvUserInfo.getUserStatusCd()) == false) {
				throw new StorePlatformException("SAC_PUR_0001", "선물 수신회원이 정상회원이 아닙니다: "
						+ recvUserInfo.getUserStatusCd());
			}

			purchaseOrderInfo.setRecvMember(recvUserInfo);
		}
	}

	/**
	 * 
	 * <pre>
	 * 상품 적합성 체크.
	 * </pre>
	 * 
	 * @param purchaseOrderInfo
	 *            구매 주문 정보
	 */
	@Override
	public void validateProduct(PurchaseOrderInfo purchaseOrderInfo) {

		String tenantId = purchaseOrderInfo.getTenantId();
		String systemId = purchaseOrderInfo.getSystemId();
		String useDeviceModelCd = null;
		if (StringUtils.equals(PurchaseConstants.PRCHS_CASE_GIFT_CD, purchaseOrderInfo.getPrchsCaseCd())) {
			useDeviceModelCd = purchaseOrderInfo.getRecvMember().getDeviceModelCd();
		} else {
			useDeviceModelCd = purchaseOrderInfo.getPurchaseMember().getDeviceModelCd();
		}
		List<DummyProduct> productInfoList = purchaseOrderInfo.getProductList();

		Double totAmt = 0.0;
		// 상품 정보 조회
		DummyProduct productInfo = null;
		for (CreatePurchaseSacReqProduct reqProduct : purchaseOrderInfo.getCreatePurchaseReq().getProductList()) {
			productInfo = this.displayPartService.searchDummyProductDetail(tenantId, systemId, reqProduct.getProdId(),
					useDeviceModelCd);

			// 상품정보 조회 실패
			if (productInfo == null) {
				throw new StorePlatformException("SAC_PUR_0001", "상품정보를 조회할 수 없습니다: " + reqProduct.getProdId());
			}
			// 상품 판매상태 체크
			if (productInfo.getbSell() == false) {
				throw new StorePlatformException("SAC_PUR_0001", "판매중인 상품이 아닙니다.");
			}
			// 상품 지원 여부 체크
			if (productInfo.getbSupport() == false) {
				if (StringUtils.equals(PurchaseConstants.PRCHS_CASE_GIFT_CD, purchaseOrderInfo.getPrchsCaseCd())) {
					throw new StorePlatformException("SAC_PUR_0001", "선물 수신 단말을 상품이 지원하지 않습니다.");
				} else {
					throw new StorePlatformException("SAC_PUR_0001", "해당 단말을 상품이 지원하지 않습니다.");
				}
			}

			productInfo.setProdAmt(reqProduct.getProdAmt()); // 임시적. TAKTODO
			productInfo.setProdQty(reqProduct.getProdQty());
			productInfo.setTenantProdGrpCd(reqProduct.getTenantProdGrpCd());
			totAmt += reqProduct.getProdAmt();

			// 상품 가격 체크
			if (reqProduct.getProdAmt() != productInfo.getProdAmt()) {
				throw new StorePlatformException("SAC_PUR_0001", "상품가격이 맞지 않습니다.");
			}

			productInfoList.add(productInfo);
		}

		// 결제 총 금액 & 상품 가격 총합 체크
		if (totAmt != purchaseOrderInfo.getCreatePurchaseReq().getTotAmt()) {
			throw new StorePlatformException("SAC_PUR_0001", "구매요청 금액이 정상적이지 않습니다.(" + totAmt + ":"
					+ purchaseOrderInfo.getCreatePurchaseReq().getTotAmt() + ")");
		}

		purchaseOrderInfo.setRealTotAmt(totAmt);
	}

	/**
	 * 
	 * <pre>
	 * 구매 적합성 체크: 상품&회원 결합 체크, 기구매체크, 쇼핑쿠폰 발급 가능여부 체크 등.
	 * </pre>
	 * 
	 * @param purchaseOrderInfo
	 *            구매 주문 정보
	 */
	@Override
	public void validatePurchase(PurchaseOrderInfo purchaseOrderInfo) {
		DummyMember useUserInfo = null;
		String useTenantId = null;
		String useUserKey = null;

		if (PurchaseConstants.PRCHS_CASE_GIFT_CD.equals(purchaseOrderInfo.getPrchsCaseCd())) {
			useUserInfo = purchaseOrderInfo.getRecvMember();
			useTenantId = purchaseOrderInfo.getRecvTenantId();
			useUserKey = purchaseOrderInfo.getRecvUserKey();
		} else {
			useUserInfo = purchaseOrderInfo.getPurchaseMember();
			useTenantId = purchaseOrderInfo.getTenantId();
			useUserKey = purchaseOrderInfo.getUserKey();
		}

		List<ExistenceItemSc> existenceItemScList = new ArrayList<ExistenceItemSc>();
		ExistenceItemSc existenceItemSc = null;

		for (DummyProduct product : purchaseOrderInfo.getProductList()) {
			// 연령 체크
			if ("PD004404".equals(product) && useUserInfo.getAge() < 20) {
				throw new StorePlatformException("SAC_PUR_0001", "연령제한으로 이용할 수 없는 상품입니다: " + useUserInfo.getAge());
			}

			// TAKTODO:: 쇼핑상품 경우, 발급 가능 여부 확인
			if (StringUtils.equals("SHOPPING", product.getSvcGrpCd())) {
				this.checkAvailableCouponPublish(product.getCouponCode(), product.getItemCode(), purchaseOrderInfo
						.getProductList().get(0).getProdQty(), purchaseOrderInfo.getPurchaseMember().getDeviceId());
			}

			// (동일상품 중복구매 불가 상품) 기구매 체크 대상 ADD
			if (product.getbDupleProd() == false) {
				existenceItemSc = new ExistenceItemSc();
				existenceItemSc.setProdId(product.getProdId());
				existenceItemSc.setTenantProdGrpCd(product.getTenantProdGrpCd());
				existenceItemScList.add(existenceItemSc);
			}
		}

		// 기구매 체크
		if (existenceItemScList.size() > 0) {

			ExistenceScReq existenceScReq = new ExistenceScReq();
			existenceScReq.setTenantId(useTenantId);
			existenceScReq.setUserKey(useUserKey);
			existenceScReq.setProductList(existenceItemScList);

			List<ExistenceScRes> checkPurchaseResultList = this.existenceSCI.searchExistenceList(existenceScReq);
			for (ExistenceScRes checkRes : checkPurchaseResultList) {
				if (PurchaseConstants.PRCHS_STATUS_COMPT.equals(checkRes.getStatusCd())) {
					throw new StorePlatformException("SAC_PUR_0001", "이미 보유한 상품입니다: " + checkRes.getPrchsId());
				}

				// TAKTODO:: 예약 상태 경우 해당 구매ID 사용... 복수 구매 시 일부 예약상태일 때 처리 방안?
			}
		}

	}

	// 쇼핑 쿠폰 발급 가능여부 확인
	private void checkAvailableCouponPublish(String couponCode, String itemCode, int itemCount, String deviceId) {
		CouponPublishAvailableSacResult shoppingRes = null;
		String availCd = null;

		CouponPublishAvailableSacParam shoppingReq = new CouponPublishAvailableSacParam();
		shoppingReq.setCouponCode(couponCode);
		shoppingReq.setItemCode(itemCode);
		shoppingReq.setItemCount(itemCount);
		shoppingReq.setMdn(deviceId);

		try {
			shoppingRes = this.shoppingService.getCouponPublishAvailable(shoppingReq);
			availCd = shoppingRes.getStatusCd();
		} catch (Exception e) {
			throw new StorePlatformException("SAC_PUR_0001", "쇼핑 쿠폰 발급 가능여부 확인 중 에러가 발생하였습니다.");
		}

		if (availCd == null) {
			throw new StorePlatformException("SAC_PUR_0001", "쇼핑 쿠폰 발급 가능여부 확인 중 에러가 발생하였습니다.");
		}

		this.logger.debug("PRCHS,SAC,ORDER,VALID,SHOPPING,,{},{}", shoppingRes.getStatusCd(),
				shoppingRes.getStatusMsg());

		switch (Integer.parseInt(availCd)) {
		case 3301:
			throw new StorePlatformException("SAC_PUR_0001", "오늘 판매 가능 수량이 모두 소진되었습니다.");
		case 3302:
			throw new StorePlatformException("SAC_PUR_0001", "당월 판매 가능 수량이 모두 소진되었습니다.");
		case 3303:
			throw new StorePlatformException("SAC_PUR_0001", "1인 일 최대 구매 가능 수량을 초과하였습니다.");
		case 3304:
			throw new StorePlatformException("SAC_PUR_0001", "1인 월 최대 구매 가능 수량을 초과하였습니다.");
		default:
			if (StringUtils.equals(availCd, "0000") == false) {
				throw new StorePlatformException("SAC_PUR_0001", "쇼핑 쿠폰 발급 가능여부 확인 중 에러가 발생하였습니다.", availCd);
			}
		}
	}

}
