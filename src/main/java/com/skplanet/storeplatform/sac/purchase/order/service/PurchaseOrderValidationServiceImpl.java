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
import java.util.Map;

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
import com.skplanet.storeplatform.sac.purchase.order.repository.PurchaseDisplayRepository;
import com.skplanet.storeplatform.sac.purchase.order.repository.PurchaseMemberRepository;
import com.skplanet.storeplatform.sac.purchase.order.vo.PurchaseOrderInfo;
import com.skplanet.storeplatform.sac.purchase.order.vo.PurchaseProduct;
import com.skplanet.storeplatform.sac.purchase.order.vo.PurchaseUserDevice;
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

	@Autowired
	private PurchaseOrderPolicyService purchaseOrderPolicyService;
	@Autowired
	private PurchaseMemberRepository purchaseMemberRepository;
	@Autowired
	private PurchaseDisplayRepository purchaseDisplayRepository;
	@Autowired
	private ExistenceSCI existenceSCI;
	@Autowired
	private ShoppingService shoppingService;

	private final List<String> freeChargeReqCdList;

	private final PurchaseMemberPartService memberPartService = new PurchaseMemberPartServiceImpl();
	private final PurchaseDisplayPartService displayPartService = new PurchaseDisplayPartServiceImpl();

	public PurchaseOrderValidationServiceImpl() {
		// TAKTODO:: 비과금 요청 허용 코드 목록 관리
		this.freeChargeReqCdList = new ArrayList<String>();
		this.freeChargeReqCdList.add("OR000408"); // 정식판전환
		this.freeChargeReqCdList.add("OR000421"); // B2B Gateway(정산)
		this.freeChargeReqCdList.add("OR000422"); // B2B Gateway(비정산)
		this.freeChargeReqCdList.add("OR000413"); // T혜택 이벤트
		this.freeChargeReqCdList.add("OR000420"); // T freemium(DRM)
	}

	/**
	 * 
	 * <pre>
	 * 비과금 구매요청 적합성 체크.
	 * </pre>
	 * 
	 * @param purchaseOrderInfo
	 *            구매 주문 정보
	 */
	@Override
	public void validateFreeCharge(PurchaseOrderInfo purchaseOrderInfo) {
		if (this.freeChargeReqCdList.contains(purchaseOrderInfo.getPrchsReqPathCd()) == false) {
			throw new StorePlatformException("SAC_PUR_5201");
		}

		if (purchaseOrderInfo.getTotAmt() != 0) {
			throw new StorePlatformException("SAC_PUR_5105");
		}

		purchaseOrderInfo.setFreeChargeReq(true); // 비과금 요청
	}

	/**
	 * 
	 * <pre>
	 * [DUMMY] 회원 적합성 체크.
	 * </pre>
	 * 
	 * @param purchaseOrderInfo
	 *            구매 주문 정보
	 */
	@Override
	public void validateMemberDummy(PurchaseOrderInfo purchaseOrderInfo) {
		// ----------------------------------------------------------------------------------------------
		// 구매(선물발신) 회원

		// 회원 정보 조회
		DummyMember userInfo = this.memberPartService.searchDummyUserDetail(purchaseOrderInfo.getTenantId(),
				purchaseOrderInfo.getSystemId(), purchaseOrderInfo.getUserKey(), purchaseOrderInfo.getDeviceKey());

		// 회원정보 조회 실패
		if (userInfo == null) {
			throw new StorePlatformException("SAC_PUR_4101");
		}
		// 회원상태 체크
		if (StringUtils.equals(userInfo.getUserStatusCd(), "US010701") == false) {
			throw new StorePlatformException("SAC_PUR_4102");
		}

		purchaseOrderInfo.setPurchaseMember(userInfo);

		// ----------------------------------------------------------------------------------------------
		// 선물 수신 회원

		if (StringUtils.equals(purchaseOrderInfo.getPrchsCaseCd(), PurchaseConstants.PRCHS_CASE_GIFT_CD)) {
			// 회원 정보 조회
			DummyMember recvUserInfo = this.memberPartService.searchDummyUserDetail(
					purchaseOrderInfo.getRecvTenantId(), purchaseOrderInfo.getSystemId(),
					purchaseOrderInfo.getRecvUserKey(), purchaseOrderInfo.getRecvDeviceKey());

			// 회원정보 조회 실패
			if (recvUserInfo == null) {
				throw new StorePlatformException("SAC_PUR_4103");
			}
			// 회원상태 체크
			if (StringUtils.equals(recvUserInfo.getUserStatusCd(), "US010701") == false) {
				throw new StorePlatformException("SAC_PUR_4104");
			}

			purchaseOrderInfo.setRecvMember(recvUserInfo);
		}
	}

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
		// --------------------------------------------------------------------------------------
		// 구매(선물발신) 회원/기기

		// 조회
		PurchaseUserDevice purchaseUserDevice = this.purchaseMemberRepository.searchUserDeviceByKey(
				purchaseOrderInfo.getTenantId(), purchaseOrderInfo.getUserKey(), purchaseOrderInfo.getDeviceKey());

		// 조회 실패
		if (purchaseUserDevice == null) {
			throw new StorePlatformException("SAC_PUR_4101");
		}

		// 회원상태 체크
		if (StringUtils.equals(purchaseUserDevice.getUserMainStatus(), "US010201") == false) {
			throw new StorePlatformException("SAC_PUR_4102");
		}

		purchaseOrderInfo.setPurchaseUser(purchaseUserDevice);

		// ----------------------------------------------------------------------------------------------
		// 선물 수신 회원/기기

		if (StringUtils.equals(purchaseOrderInfo.getPrchsCaseCd(), PurchaseConstants.PRCHS_CASE_GIFT_CD)) {

			// 조회
			PurchaseUserDevice receiveUserDevice = this.purchaseMemberRepository.searchUserDeviceByKey(
					purchaseOrderInfo.getRecvTenantId(), purchaseOrderInfo.getRecvUserKey(),
					purchaseOrderInfo.getRecvDeviceKey());

			// 조회 실패
			if (receiveUserDevice == null) {
				throw new StorePlatformException("SAC_PUR_4103");
			}

			// 회원상태 체크
			if (StringUtils.equals(receiveUserDevice.getUserMainStatus(), "US010201") == false) {
				throw new StorePlatformException("SAC_PUR_4104");
			}

			purchaseOrderInfo.setReceiveUser(receiveUserDevice);
		}
	}

	/**
	 * 
	 * <pre>
	 * [DUMMY] 상품 적합성 체크.
	 * </pre>
	 * 
	 * @param purchaseOrderInfo
	 *            구매 주문 정보
	 */
	@Override
	public void validateProductDummy(PurchaseOrderInfo purchaseOrderInfo) {

		String tenantId = purchaseOrderInfo.getTenantId();
		String systemId = purchaseOrderInfo.getSystemId();
		String useDeviceModelCd = null;
		if (StringUtils.equals(purchaseOrderInfo.getPrchsCaseCd(), PurchaseConstants.PRCHS_CASE_GIFT_CD)) {
			useDeviceModelCd = purchaseOrderInfo.getRecvMember().getDeviceModelCd();
		} else {
			useDeviceModelCd = purchaseOrderInfo.getPurchaseMember().getDeviceModelCd();
		}
		List<DummyProduct> productInfoList = purchaseOrderInfo.getProductList();

		double totAmt = 0.0;
		// 상품 정보 조회
		DummyProduct productInfo = null;
		for (CreatePurchaseSacReqProduct reqProduct : purchaseOrderInfo.getCreatePurchaseReq().getProductList()) {
			productInfo = this.displayPartService.searchDummyProductDetail(tenantId, systemId, reqProduct.getProdId(),
					useDeviceModelCd);

			// 상품정보 조회 실패
			if (productInfo == null) {
				throw new StorePlatformException("SAC_PUR_5101");
			}
			// 상품 판매상태 체크
			if (productInfo.getbSell() == false) {
				throw new StorePlatformException("SAC_PUR_5102");
			}
			// 상품 지원 여부 체크
			if (productInfo.getbSupport() == false) {
				if (StringUtils.equals(purchaseOrderInfo.getPrchsCaseCd(), PurchaseConstants.PRCHS_CASE_GIFT_CD)) {
					throw new StorePlatformException("SAC_PUR_5104");
				} else {
					throw new StorePlatformException("SAC_PUR_5103");
				}
			}
			productInfo.setProdAmt(reqProduct.getProdAmt());

			// 상품 가격 체크: 비과금 요청 경우, 상품가격 비교 생략
			if (purchaseOrderInfo.isFreeChargeReq()) {
				productInfo.setUseExprDt(reqProduct.getUseExprDt());
			} else {
				if (reqProduct.getProdAmt() != productInfo.getProdAmt()) {
					throw new StorePlatformException("SAC_PUR_5105");
				}
			}

			productInfo.setProdQty(reqProduct.getProdQty());
			totAmt += (reqProduct.getProdAmt() * reqProduct.getProdQty());

			productInfo.setResvCol01(reqProduct.getResvCol01());
			productInfo.setResvCol02(reqProduct.getResvCol02());
			productInfo.setResvCol03(reqProduct.getResvCol03());
			productInfo.setResvCol04(reqProduct.getResvCol04());
			productInfo.setResvCol05(reqProduct.getResvCol05());

			productInfoList.add(productInfo);
		}

		// 결제 총 금액 & 상품 가격 총합 체크 : // 비과금 요청 경우, 상품가격 비교 생략
		if (purchaseOrderInfo.isFreeChargeReq()) {
			purchaseOrderInfo.setRealTotAmt(0.0);
		} else {
			if (totAmt != purchaseOrderInfo.getCreatePurchaseReq().getTotAmt()) {
				throw new StorePlatformException("SAC_PUR_5106");
			}
			purchaseOrderInfo.setRealTotAmt(totAmt);
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
		// String systemId = purchaseOrderInfo.getSystemId();
		String langCd = purchaseOrderInfo.getLangCd();
		String useDeviceModelCd = null;
		if (StringUtils.equals(purchaseOrderInfo.getPrchsCaseCd(), PurchaseConstants.PRCHS_CASE_GIFT_CD)) {
			useDeviceModelCd = purchaseOrderInfo.getReceiveUser().getDeviceModelCd();
		} else {
			useDeviceModelCd = purchaseOrderInfo.getPurchaseUser().getDeviceModelCd();
		}

		List<String> prodIdList = new ArrayList<String>();
		for (CreatePurchaseSacReqProduct reqProduct : purchaseOrderInfo.getCreatePurchaseReq().getProductList()) {
			prodIdList.add(reqProduct.getProdId());
		}

		Map<String, PurchaseProduct> purchaseProductMap = this.purchaseDisplayRepository.searchPurchaseProductList(
				tenantId, langCd, useDeviceModelCd, prodIdList);
		if (purchaseProductMap == null || purchaseProductMap.size() < 1) {
			throw new StorePlatformException("SAC_PUR_5101");
		}

		List<PurchaseProduct> purchaseProductList = purchaseOrderInfo.getPurchaseProductList();
		double totAmt = 0.0;
		// 상품 정보 조회
		PurchaseProduct purchaseProduct = null;
		for (CreatePurchaseSacReqProduct reqProduct : purchaseOrderInfo.getCreatePurchaseReq().getProductList()) {
			purchaseProduct = purchaseProductMap.get(reqProduct.getProdId());

			// 상품정보 조회 실패
			if (purchaseProduct == null) {
				throw new StorePlatformException("SAC_PUR_5101");
			}
			// 상품 판매상태 체크
			if (StringUtils.equals(purchaseProduct.getProdStatusCd(), "PD000403") == false) {
				throw new StorePlatformException("SAC_PUR_5102");
			}
			// 상품 지원 여부 체크
			if (StringUtils.equals(purchaseProduct.getProdSprtYn(), "Y") == false) {
				if (StringUtils.equals(purchaseOrderInfo.getPrchsCaseCd(), PurchaseConstants.PRCHS_CASE_GIFT_CD)) {
					throw new StorePlatformException("SAC_PUR_5104");
				} else {
					throw new StorePlatformException("SAC_PUR_5103");
				}
			}
			// 상품 가격 체크: 비과금 요청 경우, 상품가격 비교 생략
			if (purchaseOrderInfo.isFreeChargeReq()) {
				purchaseProduct.setUseExprDt(reqProduct.getUseExprDt());
			} else {
				if (reqProduct.getProdAmt() != purchaseProduct.getProdAmt()) {
					throw new StorePlatformException("SAC_PUR_5105");
				}
			}

			purchaseProduct.setProdAmt((int) reqProduct.getProdAmt());
			purchaseProduct.setProdQty(reqProduct.getProdQty());
			totAmt += (purchaseProduct.getProdAmt() * reqProduct.getProdQty());

			purchaseProduct.setResvCol01(reqProduct.getResvCol01());
			purchaseProduct.setResvCol02(reqProduct.getResvCol02());
			purchaseProduct.setResvCol03(reqProduct.getResvCol03());
			purchaseProduct.setResvCol04(reqProduct.getResvCol04());
			purchaseProduct.setResvCol05(reqProduct.getResvCol05());

			purchaseProductList.add(purchaseProduct);
		}

		// 결제 총 금액 & 상품 가격 총합 체크 : // 비과금 요청 경우, 상품가격 비교 생략
		if (purchaseOrderInfo.isFreeChargeReq()) {
			purchaseOrderInfo.setRealTotAmt(0.0);
		} else {
			if (totAmt != purchaseOrderInfo.getCreatePurchaseReq().getTotAmt()) {
				throw new StorePlatformException("SAC_PUR_5106");
			}
			purchaseOrderInfo.setRealTotAmt(totAmt);
		}
	}

	/**
	 * 
	 * <pre>
	 * [DUMMY] 구매 적합성(&가능여부) 체크: 상품&회원 결합 체크, 기구매체크, 쇼핑쿠폰 발급 가능여부 체크 등.
	 * </pre>
	 * 
	 * @param purchaseOrderInfo
	 *            구매 주문 정보
	 */
	@Override
	public void validatePurchaseDummy(PurchaseOrderInfo purchaseOrderInfo) {
		DummyMember useUserInfo = null;
		String useTenantId = null;
		String useUserKey = null;

		if (StringUtils.equals(purchaseOrderInfo.getPrchsCaseCd(), PurchaseConstants.PRCHS_CASE_GIFT_CD)) {
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
			if (StringUtils.equals(product.getProdGrdCd(), "PD004404") && useUserInfo.getAge() < 20) {
				throw new StorePlatformException("SAC_PUR_5110");
			}

			// TAKTODO:: 쇼핑상품 경우, 발급 가능 여부 확인
			if (StringUtils.startsWith(purchaseOrderInfo.getTenantProdGrpCd(), "OR006205")) {
				this.checkAvailableCouponPublish(product.getCouponCode(), product.getItemCode(), product.getProdQty(),
						purchaseOrderInfo.getPurchaseMember().getDeviceId()); // 결제자 MDN 기준

			}

			// TAKTODO:: 구매 가능 건수 체크 (쇼핑 특가상품, 정액권 등 한정수량 상품)

			// (동일상품 중복구매 불가 상품) 기구매 체크 대상 ADD
			if (product.getbDupleProd() == false) {
				existenceItemSc = new ExistenceItemSc();
				existenceItemSc.setProdId(product.getProdId());
				existenceItemScList.add(existenceItemSc);
			}
		}

		// 기구매 체크
		if (existenceItemScList.size() > 10000) { // TAKTODO:: 기구매 체크 제외 (테스트용)

			ExistenceScReq existenceScReq = new ExistenceScReq();
			existenceScReq.setTenantId(useTenantId);
			existenceScReq.setProductList(existenceItemScList);

			if (this.purchaseOrderPolicyService.isDeviceBasedPurchaseHistory(useTenantId,
					purchaseOrderInfo.getTenantProdGrpCd())) {
				existenceScReq.setUserKey(useUserKey); // 디바이스(mdn) 기반
			}

			List<ExistenceScRes> checkPurchaseResultList = this.existenceSCI.searchExistenceList(existenceScReq);
			for (ExistenceScRes checkRes : checkPurchaseResultList) {
				if (StringUtils.equals(checkRes.getStatusCd(), PurchaseConstants.PRCHS_STATUS_COMPT)) {
					throw new StorePlatformException("SAC_PUR_6101");
				}

				// TAKTODO:: 예약 상태 경우 해당 구매ID 사용... 복수 구매 시 일부 예약상태일 때 처리 방안?
			}
		}

	}

	/**
	 * 
	 * <pre>
	 * 구매 적합성(&가능여부) 체크: 상품&회원 결합 체크, 기구매체크, 쇼핑쿠폰 발급 가능여부 체크 등.
	 * </pre>
	 * 
	 * @param purchaseOrderInfo
	 *            구매 주문 정보
	 */
	@Override
	public void validatePurchase(PurchaseOrderInfo purchaseOrderInfo) {
		PurchaseUserDevice useUser = null;

		if (StringUtils.equals(purchaseOrderInfo.getPrchsCaseCd(), PurchaseConstants.PRCHS_CASE_GIFT_CD)) {
			useUser = purchaseOrderInfo.getReceiveUser();
		} else {
			useUser = purchaseOrderInfo.getPurchaseUser();
		}

		List<ExistenceItemSc> existenceItemScList = new ArrayList<ExistenceItemSc>();
		ExistenceItemSc existenceItemSc = null;

		for (PurchaseProduct product : purchaseOrderInfo.getPurchaseProductList()) {
			// 연령 체크
			if (StringUtils.equals(product.getProdGrdCd(), "PD004404") && useUser.getAge() < 20) {
				throw new StorePlatformException("SAC_PUR_5110");
			}

			// TAKTODO:: 쇼핑 특가상품 가능 여부 체크 (건수 체크)

			if (StringUtils.isNotBlank(product.getSpecialSaleCouponId())) {
				product.getSpecialSaleCouponId();
				product.getSpecialSaleDayLimit();
				product.getSpecialSaleDayLimitPerson();
				product.getSpecialSaleMonthLimit();
				product.getSpecialSaleMonthLimitPerson();
				product.getSpecialSaleStartDt();
				product.getSpecialSaleEndDt();
				product.getSpecialSaleAmt();
			}

			// TAKTODO:: 쇼핑상품 경우, 발급 가능 여부 확인
			if (StringUtils.startsWith(purchaseOrderInfo.getTenantProdGrpCd(), "OR006205")) {
				this.checkAvailableCouponPublish(product.getCouponCode(), product.getItemCode(), product.getProdQty(),
						purchaseOrderInfo.getPurchaseUser().getDeviceId()); // 결제자 MDN 기준

			} else {
				// 기구매 체크 대상 추가: 쇼핑상품은 동일상품 중복구매 허용
				existenceItemSc = new ExistenceItemSc();
				existenceItemSc.setProdId(product.getProdId());
				existenceItemScList.add(existenceItemSc);
			}
		}

		// 기구매 체크
		if (existenceItemScList.size() > 10000) { // TAKTODO:: 기구매 체크 제외 (테스트용)

			ExistenceScReq existenceScReq = new ExistenceScReq();
			existenceScReq.setTenantId(useUser.getTenantId());
			existenceScReq.setUserKey(useUser.getUserKey()); // 디바이스(mdn) 기반
			existenceScReq.setProductList(existenceItemScList);

			if (this.purchaseOrderPolicyService.isDeviceBasedPurchaseHistory(useUser.getTenantId(),
					purchaseOrderInfo.getTenantProdGrpCd())) {
				existenceScReq.setDeviceKey(useUser.getDeviceKey()); // 디바이스(mdn) 기반
			}

			List<ExistenceScRes> checkPurchaseResultList = this.existenceSCI.searchExistenceList(existenceScReq);
			for (ExistenceScRes checkRes : checkPurchaseResultList) {
				if (StringUtils.equals(checkRes.getStatusCd(), PurchaseConstants.PRCHS_STATUS_COMPT)) {
					throw new StorePlatformException("SAC_PUR_6101");
				}

				// TAKTODO:: 예약 상태 경우 해당 구매ID 사용... 복수 구매 시 일부 예약상태일 때 처리 방안?
			}
		}

	}

	/*
	 * <pre> 쇼핑 쿠폰 발급 가능여부 확인. </pre>
	 * 
	 * @param couponCode 쿠폰상품코드
	 * 
	 * @param itemCode 쿠폰단품코드
	 * 
	 * @param itemCount 쿠폰구매수량
	 * 
	 * @param deviceId MDN
	 */
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
			throw new StorePlatformException("SAC_PUR_7205");
		}

		if (availCd == null) {
			throw new StorePlatformException("SAC_PUR_7205");
		}

		this.logger
				.debug("PRCHS,SAC,ORDER,VALID,SHOPPING,{},{}", shoppingRes.getStatusCd(), shoppingRes.getStatusMsg());

		switch (Integer.parseInt(availCd)) {
		case 0: // 정상
			break;
		case 3301:
			throw new StorePlatformException("SAC_PUR_6104");
		case 3302:
			throw new StorePlatformException("SAC_PUR_6105");
		case 3303:
			throw new StorePlatformException("SAC_PUR_6106");
		case 3304:
			throw new StorePlatformException("SAC_PUR_6107");
		default:
			throw new StorePlatformException("SAC_PUR_7205");
		}
	}

}
