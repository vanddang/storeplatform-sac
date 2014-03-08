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
import com.skplanet.storeplatform.purchase.client.order.sci.PurchaseOrderSearchSCI;
import com.skplanet.storeplatform.purchase.client.order.vo.SearchShoppingSpecialCountScReq;
import com.skplanet.storeplatform.purchase.client.order.vo.SearchShoppingSpecialCountScRes;
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
import com.skplanet.storeplatform.sac.purchase.order.vo.FixrateProduct;
import com.skplanet.storeplatform.sac.purchase.order.vo.PurchaseOrderInfo;
import com.skplanet.storeplatform.sac.purchase.order.vo.PurchaseProduct;
import com.skplanet.storeplatform.sac.purchase.order.vo.PurchaseUserDevice;
import com.skplanet.storeplatform.sac.purchase.shopping.repository.ShoppingRepository;
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
	private ExistenceSCI existenceSCI;
	@Autowired
	private PurchaseOrderSearchSCI purchaseOrderSearchSCI;
	@Autowired
	private PurchaseOrderPolicyService purchaseOrderPolicyService;
	@Autowired
	private PurchaseMemberRepository purchaseMemberRepository;
	@Autowired
	private PurchaseDisplayRepository purchaseDisplayRepository;
	@Autowired
	private ShoppingRepository shoppingRepository;

	private final List<String> freeChargeReqCdList;

	private final PurchaseMemberPartService memberPartService = new PurchaseMemberPartServiceImpl();
	private final PurchaseDisplayPartService displayPartService = new PurchaseDisplayPartServiceImpl();

	public PurchaseOrderValidationServiceImpl() {
		// TAKTODO:: 비과금 요청 허용 코드 목록 관리
		this.freeChargeReqCdList = new ArrayList<String>();
		this.freeChargeReqCdList.add(PurchaseConstants.PRCHS_REQ_PATH_IAP_COMMERCIAL_CONVERTED); // OR000408-정식판전환
		this.freeChargeReqCdList.add(PurchaseConstants.PRCHS_REQ_PATH_B2B_BALANCE); // OR000421-B2B Gateway(정산)
		this.freeChargeReqCdList.add(PurchaseConstants.PRCHS_REQ_PATH_B2B_NON_BALANCE); // OR000422-B2B Gateway(비정산)
		this.freeChargeReqCdList.add(PurchaseConstants.PRCHS_REQ_PATH_T_FREEMIUM); // OR000420-T freemium(DRM)
		this.freeChargeReqCdList.add(PurchaseConstants.PRCHS_REQ_PATH_T_BENEFIT_EVENT); // OR000413-T혜택 이벤트
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
		if (StringUtils.equals(userInfo.getUserStatusCd(), PurchaseConstants.USER_STATUS_NORMAL) == false) {
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
			if (StringUtils.equals(recvUserInfo.getUserStatusCd(), PurchaseConstants.USER_STATUS_NORMAL) == false) {
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
		if (StringUtils.equals(purchaseUserDevice.getUserMainStatus(), PurchaseConstants.USER_STATUS_NORMAL) == false) {
			throw new StorePlatformException("SAC_PUR_4102");
		}

		if (purchaseUserDevice.isRealName() == false) {
			throw new StorePlatformException("SAC_PUR_4105");
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
			if (StringUtils.equals(receiveUserDevice.getUserMainStatus(), PurchaseConstants.USER_STATUS_NORMAL) == false) {
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
			productInfo.setProdAmt(reqProduct.getProdAmt()); // DUMMY용 코드
			// 상품 가격 체크
			if (reqProduct.getProdAmt() != productInfo.getProdAmt()) {
				throw new StorePlatformException("SAC_PUR_5105");
			}

			productInfo.setProdQty(reqProduct.getProdQty());
			totAmt += (reqProduct.getProdAmt() * reqProduct.getProdQty());

			productInfo.setResvCol01(reqProduct.getResvCol01());
			productInfo.setResvCol02(reqProduct.getResvCol02());
			productInfo.setResvCol03(reqProduct.getResvCol03());
			productInfo.setResvCol04(reqProduct.getResvCol04());
			productInfo.setResvCol05(reqProduct.getResvCol05());

			// 비과금 구매요청 경우, 이용종료일시 세팅
			if (purchaseOrderInfo.isFreeChargeReq()) {
				productInfo.setUseExprDt(reqProduct.getUseExprDt());
			}

			productInfoList.add(productInfo);
		}

		// 결제 총 금액 & 상품 가격 총합 체크 : // 비과금 요청 경우, 결제금액 체크 생략
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
		double totAmt = 0.0, nowPurchaseProdAmt = 0.0;
		// 상품 정보 조회
		PurchaseProduct purchaseProduct = null;
		for (CreatePurchaseSacReqProduct reqProduct : purchaseOrderInfo.getCreatePurchaseReq().getProductList()) {
			purchaseProduct = purchaseProductMap.get(reqProduct.getProdId());

			// 상품정보 조회 실패
			if (purchaseProduct == null) {
				throw new StorePlatformException("SAC_PUR_5101");
			}
			// 상품 판매상태 체크
			if (StringUtils.equals(purchaseProduct.getProdStatusCd(), PurchaseConstants.PRODUCT_STATUS_SALE) == false) {
				throw new StorePlatformException("SAC_PUR_5102");
			}
			// 상품 지원 여부 체크
			if (StringUtils.equals(purchaseProduct.getProdSprtYn(), PurchaseConstants.USE_Y) == false) {
				if (StringUtils.equals(purchaseOrderInfo.getPrchsCaseCd(), PurchaseConstants.PRCHS_CASE_GIFT_CD)) {
					throw new StorePlatformException("SAC_PUR_5104");
				} else {
					throw new StorePlatformException("SAC_PUR_5103");
				}
			}
			// 상품 가격 체크
			nowPurchaseProdAmt = StringUtils.isBlank(purchaseProduct.getSpecialSaleCouponId()) ? purchaseProduct
					.getProdAmt() : purchaseProduct.getSpecialSaleAmt();
			if (reqProduct.getProdAmt() != nowPurchaseProdAmt) {
				throw new StorePlatformException("SAC_PUR_5105");
			}

			purchaseProduct.setProdQty(reqProduct.getProdQty());
			totAmt += (nowPurchaseProdAmt * reqProduct.getProdQty());

			purchaseProduct.setResvCol01(reqProduct.getResvCol01());
			purchaseProduct.setResvCol02(reqProduct.getResvCol02());
			purchaseProduct.setResvCol03(reqProduct.getResvCol03());
			purchaseProduct.setResvCol04(reqProduct.getResvCol04());
			purchaseProduct.setResvCol05(reqProduct.getResvCol05());

			// 비과금 구매요청 경우, 이용종료일시 세팅
			if (purchaseOrderInfo.isFreeChargeReq()) {
				purchaseProduct.setUseExprDt(reqProduct.getUseExprDt());
			}

			purchaseProductList.add(purchaseProduct);
		}

		// 결제 총 금액 & 상품 가격 총합 체크 : // 비과금 요청 경우, 결제금액 체크 생략
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
		if (purchaseOrderInfo.isBlockPayment()) { // 구매차단
			throw new StorePlatformException("SAC_PUR_6103");
		}

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
			if (StringUtils.equals(product.getProdGrdCd(), PurchaseConstants.PRODUCT_GRADE_19)
					&& useUserInfo.getAge() < 20) {
				throw new StorePlatformException("SAC_PUR_5110");
			}

			// TAKTODO:: 쇼핑상품 경우, 발급 가능 여부 확인
			if (StringUtils.startsWith(purchaseOrderInfo.getTenantProdGrpCd(),
					PurchaseConstants.TENANT_PRODUCT_GROUP_SHOPPING)) {
				this.checkAvailableCouponPublish(product.getCouponCode(), product.getItemCode(), product.getProdQty(),
						purchaseOrderInfo.getPurchaseMember().getDeviceId()); // 결제자 MDN 기준

			}

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
		// 구매차단 체크
		if (purchaseOrderInfo.isBlockPayment()) {
			throw new StorePlatformException("SAC_PUR_6103");
		}

		// 이용자(구매자 + 선물수신자) 조회
		PurchaseUserDevice useUser = null;
		if (StringUtils.equals(purchaseOrderInfo.getPrchsCaseCd(), PurchaseConstants.PRCHS_CASE_GIFT_CD)) {
			useUser = purchaseOrderInfo.getReceiveUser();
		} else {
			useUser = purchaseOrderInfo.getPurchaseUser();
		}

		// 디바이스(mdn) 기반 상품 여부 조회
		boolean bDeviceBased = false;
		bDeviceBased = this.purchaseOrderPolicyService.isDeviceBasedPurchaseHistory(useUser.getTenantId(),
				purchaseOrderInfo.getTenantProdGrpCd());

		List<ExistenceItemSc> existenceItemScList = new ArrayList<ExistenceItemSc>();
		ExistenceItemSc existenceItemSc = null;

		for (PurchaseProduct product : purchaseOrderInfo.getPurchaseProductList()) {
			// 연령 체크
			if (StringUtils.equals(product.getProdGrdCd(), PurchaseConstants.PRODUCT_GRADE_19) && useUser.getAge() < 20) {
				throw new StorePlatformException("SAC_PUR_5110");
			}

			// (정액권) 배타 상품 체크
			if (product.getExclusiveFixrateProdIdList().size() > 0) {
				// TAKTODO:: 기구매체크 일단 때려박음, 공통처리로 빼야 함
				List<ExistenceItemSc> exclusiveExistenceItemScList = new ArrayList<ExistenceItemSc>();
				for (String exclusiveProdId : product.getExclusiveFixrateProdIdList()) {
					existenceItemSc = new ExistenceItemSc();
					existenceItemSc.setProdId(exclusiveProdId);
					exclusiveExistenceItemScList.add(existenceItemSc);
				}
				ExistenceScReq existenceScReq = new ExistenceScReq();
				existenceScReq.setTenantId(useUser.getTenantId());
				existenceScReq.setUserKey(useUser.getUserKey());
				if (bDeviceBased) {
					existenceScReq.setDeviceKey(useUser.getDeviceKey()); // 디바이스(mdn) 기반
				}
				existenceScReq.setProductList(exclusiveExistenceItemScList);

				List<ExistenceScRes> checkPurchaseResultList = this.existenceSCI.searchExistenceList(existenceScReq);
				for (ExistenceScRes checkRes : checkPurchaseResultList) {
					if (StringUtils.equals(checkRes.getStatusCd(), PurchaseConstants.PRCHS_STATUS_COMPT)) {
						throw new StorePlatformException("SAC_PUR_6109");
					}
				}
			}

			// 이용 가능한 정액권 기구매 확인 처리
			if (product.getAvailableFixrateProdIdList().size() > 0) {
				// TAKTODO:: 기구매체크 일단 때려박음, 공통처리로 빼야 함
				List<ExistenceItemSc> fixrateExistenceItemScList = new ArrayList<ExistenceItemSc>();
				for (String fixrateProdId : product.getAvailableFixrateProdIdList()) {
					existenceItemSc = new ExistenceItemSc();
					existenceItemSc.setProdId(fixrateProdId);
					fixrateExistenceItemScList.add(existenceItemSc);
				}
				ExistenceScReq existenceScReq = new ExistenceScReq();
				existenceScReq.setTenantId(useUser.getTenantId());
				existenceScReq.setUserKey(useUser.getUserKey());
				if (bDeviceBased) {
					existenceScReq.setDeviceKey(useUser.getDeviceKey()); // 디바이스(mdn) 기반
				}
				existenceScReq.setProductList(fixrateExistenceItemScList);

				List<ExistenceScRes> checkPurchaseResultList = this.existenceSCI.searchExistenceList(existenceScReq);
				ExistenceScRes useExistenceScRes = null;
				for (ExistenceScRes checkRes : checkPurchaseResultList) {
					if (StringUtils.equals(checkRes.getStatusCd(), PurchaseConstants.PRCHS_STATUS_COMPT)) {
						if (useExistenceScRes == null) {
							useExistenceScRes = checkRes;
							continue;
						}
						// TAKTODO:: 구매일시 비교로 변경
						if (checkRes.getPrchsId().compareTo(useExistenceScRes.getPrchsId()) > 0) {
							useExistenceScRes = checkRes;
						}
					}
				}
				if (useExistenceScRes != null) {
					// 정액권 DRM 정보 조회
					FixrateProduct fixrateProduct = this.purchaseDisplayRepository.searchFreePassDrmInfo(
							useUser.getTenantId(), purchaseOrderInfo.getLangCd(), useExistenceScRes.getProdId(),
							product.getProdId());
					product.setUsePeriodUnitCd(fixrateProduct.getUsePeriodUnitCd());
					product.setUsePeriod(Integer.parseInt(fixrateProduct.getUsePeriod()));
					product.setDrmYn(fixrateProduct.getDrmYn());
					product.setUseFixrateProdId(fixrateProduct.getFixrateProdId()); // 사용할 정액권ID 세팅

					// 무료구매 처리 데이터 세팅
					purchaseOrderInfo.setRealTotAmt(0.0);
					String mtdCd = null;
					switch (Integer.parseInt(fixrateProduct.getCmpxProdClsfCd().substring(2))) {
					// OR004301-정액권, OR004302-시리즈패스, OR004303-전권소장, OR004304-전권대여
					case 4301:
						mtdCd = PurchaseConstants.PAYMENT_METHOD_FIXRATE; // OR000612-정액권
						break;
					case 4302:
						mtdCd = PurchaseConstants.PAYMENT_METHOD_SERIESPASS; // OR000613-시리즈패스권
						break;
					case 4303:
						mtdCd = PurchaseConstants.PAYMENT_METHOD_EBOOKCOMIC_OWN; // OR000617-이북/코믹 전권 소장
						break;
					case 4304:
						mtdCd = PurchaseConstants.PAYMENT_METHOD_EBOOKCOMIC_LOAN; // OR000618-이북/코믹 전권 대여
						break;
					}
					purchaseOrderInfo.setFreePaymentMtdCd(mtdCd);
				}
			}

			// 쇼핑 상품 추가 체크
			if (StringUtils.startsWith(purchaseOrderInfo.getTenantProdGrpCd(),
					PurchaseConstants.TENANT_PRODUCT_GROUP_SHOPPING)) {
				// TAKTODO:: 특가상품 구매 건수 체크
				if (StringUtils.isNotBlank(product.getSpecialSaleCouponId())) {
					// product.getSpecialSaleCouponId();
					// product.getSpecialSaleDayLimit();
					// product.getSpecialSaleDayLimitPerson();
					// product.getSpecialSaleMonthLimit();
					// product.getSpecialSaleMonthLimitPerson();
					// product.getSpecialSaleStartDt();
					// product.getSpecialSaleEndDt();
					// product.getSpecialSaleAmt();

					SearchShoppingSpecialCountScReq specialReq = new SearchShoppingSpecialCountScReq();
					specialReq.setTenantId(purchaseOrderInfo.getTenantId());
					specialReq.setUserKey(purchaseOrderInfo.getPurchaseUser().getUserKey());
					specialReq.setDeviceKey(purchaseOrderInfo.getPurchaseUser().getDeviceKey());
					specialReq.setSpecialCouponId(product.getSpecialSaleCouponId());
					specialReq.setSpecialAmt(product.getProdAmt() - product.getSpecialSaleAmt());

					SearchShoppingSpecialCountScRes specialRes = this.purchaseOrderSearchSCI
							.SearchShoppingSpecialCount(specialReq);
					if (specialRes.getDayCount() + product.getProdQty() > product.getSpecialSaleDayLimit()) {
						throw new StorePlatformException("SAC_PUR_6104");
					}
					if (specialRes.getDayUserCount() + product.getProdQty() > product.getSpecialSaleDayLimitPerson()) {
						throw new StorePlatformException("SAC_PUR_6106");
					}
					if (specialRes.getMonthCount() + product.getProdQty() > product.getSpecialSaleMonthLimit()) {
						throw new StorePlatformException("SAC_PUR_6105");
					}
					if (specialRes.getMonthUserCount() + product.getProdQty() > product
							.getSpecialSaleMonthLimitPerson()) {
						throw new StorePlatformException("SAC_PUR_6107");
					}

					purchaseOrderInfo.setSpecialCouponId(product.getSpecialSaleCouponId());
					purchaseOrderInfo.setSpecialCouponAmt((product.getProdAmt() - product.getSpecialSaleAmt())
							* product.getProdQty());
				}

				// TAKTODO:: 발급 가능 여부 확인
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
		if (existenceItemScList.size() > 0) {

			ExistenceScReq existenceScReq = new ExistenceScReq();
			existenceScReq.setTenantId(useUser.getTenantId());
			existenceScReq.setUserKey(useUser.getUserKey());
			if (bDeviceBased) {
				existenceScReq.setDeviceKey(useUser.getDeviceKey()); // 디바이스(mdn) 기반
			}
			existenceScReq.setProductList(existenceItemScList);

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
			shoppingRes = this.shoppingRepository.getCouponPublishAvailable(shoppingReq);
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
