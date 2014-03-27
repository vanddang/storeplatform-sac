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

import org.apache.commons.collections.CollectionUtils;
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
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.FreePassInfo;
import com.skplanet.storeplatform.sac.client.purchase.vo.order.CreatePurchaseSacReq;
import com.skplanet.storeplatform.sac.client.purchase.vo.order.CreatePurchaseSacReqProduct;
import com.skplanet.storeplatform.sac.purchase.constant.PurchaseConstants;
import com.skplanet.storeplatform.sac.purchase.history.service.ExistenceSacService;
import com.skplanet.storeplatform.sac.purchase.order.repository.PurchaseDisplayRepository;
import com.skplanet.storeplatform.sac.purchase.order.repository.PurchaseMemberRepository;
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
	private ExistenceSacService existenceSacService;
	@Autowired
	private PurchaseMemberRepository purchaseMemberRepository;
	@Autowired
	private PurchaseDisplayRepository purchaseDisplayRepository;
	@Autowired
	private ShoppingRepository shoppingRepository;

	private final List<String> freeChargeReqCdList;

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
	 * 구매요청 파라미터 적합성 체크.
	 * </pre>
	 * 
	 * @param req
	 *            구매요청 VO
	 */
	@Override
	public void validatePurchaseRequestParameter(CreatePurchaseSacReq req) {

		// 선물 요청: 수신자 정보 필수
		if (StringUtils.equals(req.getPrchsCaseCd(), PurchaseConstants.PRCHS_CASE_GIFT_CD)
				&& (StringUtils.isBlank(req.getRecvUserKey()) || StringUtils.isBlank(req.getRecvDeviceKey()))) {
			throw new StorePlatformException("SAC_PUR_5100");
		}

		// 부분유료화 정보
		if (StringUtils.startsWith(req.getTenantProdGrpCd(), PurchaseConstants.TENANT_PRODUCT_GROUP_IAP)) {
			for (CreatePurchaseSacReqProduct product : req.getProductList()) {
				if (StringUtils.isBlank(product.getTid()) || StringUtils.isBlank(product.getTxId())
						|| StringUtils.isBlank(product.getPartChrgVer())
						|| StringUtils.isBlank(product.getPartChrgProdNm())) {
					throw new StorePlatformException("SAC_PUR_5100");
				}
			}
		}

		// TAKTODO:: 링&벨
		// if( StringUtils.startsWith(req.getTenantProdGrpCd(), PurchaseConstants.TENANT_PRODUCT_GROUP_RINGBELL)) {
		// for(CreatePurchaseSacReqProduct product : req.getProductList()) {
		// if( StringUtils.isBlank(product.getTimbreClsf()) ) {
		// throw new StorePlatformException("SAC_PUR_5100");
		// }
		// }
		// }
	}

	/**
	 * 
	 * <pre>
	 * 비과금 구매요청 권한 체크.
	 * </pre>
	 * 
	 * @param prchsReqPathCd
	 *            구매 요청 경로 코드
	 */
	@Override
	public void validateFreeChargeAuth(String prchsReqPathCd) {
		if (this.freeChargeReqCdList.contains(prchsReqPathCd) == false) {
			throw new StorePlatformException("SAC_PUR_5201");
		}
	}

	/**
	 * 
	 * <pre>
	 * Biz 쿠폰 발급 요청 권한 체크.
	 * </pre>
	 * 
	 * @param prchsReqPathCd
	 *            구매 요청 경로 코드
	 */
	@Override
	public void validateBizAuth(String prchsReqPathCd) {
		if (StringUtils.equals(prchsReqPathCd, PurchaseConstants.PRCHS_REQ_PATH_BIZ_COUPON) == false) { // OR000432-Biz쿠폰
			throw new StorePlatformException("SAC_PUR_5201");
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
		if (purchaseUserDevice == null) {
			throw new StorePlatformException("SAC_PUR_4101");
		}

		// 회원상태 체크
		if (StringUtils.equals(purchaseUserDevice.getUserMainStatus(), PurchaseConstants.USER_STATUS_NORMAL) == false) {
			throw new StorePlatformException("SAC_PUR_4102");
		}

		// 디바이스 모델 코드, 통신사 코드 : 요청 값이 있는 경우에는 요청 값으로 사용
		if (StringUtils.isNotBlank(purchaseOrderInfo.getDeviceModelCd())) {
			purchaseUserDevice.setDeviceModelCd(purchaseOrderInfo.getDeviceModelCd());
		}
		if (StringUtils.isNotBlank(purchaseOrderInfo.getTelecomCd())) {
			purchaseUserDevice.setTelecom(purchaseOrderInfo.getTelecomCd());
		}

		purchaseOrderInfo.setPurchaseUser(purchaseUserDevice);

		// Biz 쿠폰 경우 수신자 체크 Skip
		if (StringUtils.equals(purchaseOrderInfo.getPrchsReqPathCd(), PurchaseConstants.PRCHS_REQ_PATH_BIZ_COUPON)) {
			return;
		}

		// ----------------------------------------------------------------------------------------------
		// 선물 수신 회원/기기

		if (StringUtils.equals(purchaseOrderInfo.getPrchsCaseCd(), PurchaseConstants.PRCHS_CASE_GIFT_CD)) {

			// 조회
			PurchaseUserDevice receiveUserDevice = this.purchaseMemberRepository.searchUserDeviceByKey(
					purchaseOrderInfo.getRecvTenantId(), purchaseOrderInfo.getRecvUserKey(),
					purchaseOrderInfo.getRecvDeviceKey());
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
		if (StringUtils.startsWith(purchaseOrderInfo.getTenantProdGrpCd(),
				PurchaseConstants.TENANT_PRODUCT_GROUP_SHOPPING)) {
			useDeviceModelCd = purchaseOrderInfo.getPurchaseUser().getDeviceModelCd();
		} else {
			useDeviceModelCd = (StringUtils.equals(purchaseOrderInfo.getPrchsCaseCd(),
					PurchaseConstants.PRCHS_CASE_GIFT_CD) ? purchaseOrderInfo.getReceiveUser().getDeviceModelCd() : purchaseOrderInfo
					.getPurchaseUser().getDeviceModelCd());
		}

		// 상품 정보 조회

		List<CreatePurchaseSacReqProduct> reqProdList = null;
		List<String> prodIdList = new ArrayList<String>();

		if (CollectionUtils.isNotEmpty(purchaseOrderInfo.getCreatePurchaseReq().getProductList())) {
			for (CreatePurchaseSacReqProduct reqProduct : purchaseOrderInfo.getCreatePurchaseReq().getProductList()) {
				prodIdList.add(reqProduct.getProdId());
			}

			reqProdList = purchaseOrderInfo.getCreatePurchaseReq().getProductList();

		} else {
			prodIdList.add(purchaseOrderInfo.getCreatePurchaseReq().getProdId());

			CreatePurchaseSacReqProduct reqProduct = new CreatePurchaseSacReqProduct();
			reqProduct.setProdId(purchaseOrderInfo.getCreatePurchaseReq().getProdId());
			reqProdList = new ArrayList<CreatePurchaseSacReqProduct>();
			reqProdList.add(reqProduct);
		}

		Map<String, PurchaseProduct> purchaseProductMap = this.purchaseDisplayRepository.searchPurchaseProductList(
				tenantId, langCd, useDeviceModelCd, prodIdList);
		if (purchaseProductMap == null || purchaseProductMap.size() < 1) {
			throw new StorePlatformException("SAC_PUR_5101");
		}

		// 상품 체크

		List<PurchaseProduct> purchaseProductList = purchaseOrderInfo.getPurchaseProductList();
		double totAmt = 0.0, nowPurchaseProdAmt = 0.0;
		PurchaseProduct purchaseProduct = null;
		for (CreatePurchaseSacReqProduct reqProduct : reqProdList) {
			purchaseProduct = purchaseProductMap.get(reqProduct.getProdId());

			// 상품정보 조회 실패
			if (purchaseProduct == null) {
				throw new StorePlatformException("SAC_PUR_5101");
			}
			// 상품 판매상태 체크
			if (StringUtils.equals(purchaseProduct.getProdStatusCd(), PurchaseConstants.PRODUCT_STATUS_SALE) == false
					&& StringUtils.equals(purchaseProduct.getProdStatusCd(),
							PurchaseConstants.PRODUCT_STATUS_FIXRATE_SALE) == false) {
				throw new StorePlatformException("SAC_PUR_5102");
			}

			// Biz 쿠폰 경우 이하 상품 체크 Skip
			if (StringUtils.equals(purchaseOrderInfo.getPrchsReqPathCd(), PurchaseConstants.PRCHS_REQ_PATH_BIZ_COUPON)) {
				purchaseProduct.setProdQty(1);
				purchaseProductList.add(purchaseProduct);
				continue;
			}

			// 상품 지원 여부 체크
			// TAKTEST:: 테스트 위해 주석
			// if (StringUtils.equals(purchaseProduct.getProdSprtYn(), PurchaseConstants.USE_Y) == false) {
			// if (StringUtils.equals(purchaseOrderInfo.getPrchsCaseCd(), PurchaseConstants.PRCHS_CASE_GIFT_CD)) {
			// throw new StorePlatformException("SAC_PUR_5104");
			// } else {
			// throw new StorePlatformException("SAC_PUR_5103");
			// }
			// }

			// TAKTODO:: 링&벨은 일단 상품 가격을 요청가격으로
			if (StringUtils.startsWith(purchaseOrderInfo.getTenantProdGrpCd(),
					PurchaseConstants.TENANT_PRODUCT_GROUP_RINGBELL)) {
				purchaseProduct.setProdAmt(reqProduct.getProdAmt());
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

			// 요청 시 받은 상품 정보 세팅
			// rnBillCd // RN_과금_코드
			// cid // 컨텐츠ID
			// contentsClsf // 컨텐츠_구분
			// contentsType // 컨텐츠_타입
			// prchsType // 구매_타입
			// menuId // 메뉴_ID
			// genreClsfCd // 장르_구분_코드
			/* IAP */
			purchaseProduct.setTid(reqProduct.getTid()); // 부분유료화 개발사 구매Key
			purchaseProduct.setTxId(reqProduct.getTxId()); // 부분유료화 전자영수증 번호
			purchaseProduct.setPartChrgVer(reqProduct.getPartChrgVer()); // 부분_유료_버전
			purchaseProduct.setPartChrgProdNm(reqProduct.getPartChrgProdNm()); // 부분_유료_상품_명
			/* Ring & Bell */
			purchaseProduct.setInfoUseFee(reqProduct.getInfoUseFee()); // 정보_이용_요금 (ISU_AMT_ADD)
			purchaseProduct.setTimbreClsf(reqProduct.getTimbreClsf()); // 음질_구분
			purchaseProduct.setTimbreSctn(reqProduct.getTimbreSctn()); // 음질_구간

			// 비과금 구매요청 경우, 이용종료일시 세팅
			if (purchaseOrderInfo.isFreeChargeReq()) {
				purchaseProduct.setUseExprDt(reqProduct.getUseExprDt());
			}

			// 쇼핑특가 쿠폰 정보 저장
			if (StringUtils.isNotBlank(purchaseProduct.getSpecialSaleCouponId())) {
				purchaseOrderInfo.setSpecialCouponId(purchaseProduct.getSpecialSaleCouponId());
				purchaseOrderInfo.setSpecialCouponAmt(purchaseProduct.getProdAmt()
						- purchaseProduct.getSpecialSaleAmt());
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

		if (purchaseOrderInfo.getPurchaseProductList().size() <= 0) {
			throw new StorePlatformException("SAC_PUR_9999");
		}

		// Biz 쿠폰 경우 이하 체크 Skip
		if (StringUtils.equals(purchaseOrderInfo.getPrchsReqPathCd(), PurchaseConstants.PRCHS_REQ_PATH_BIZ_COUPON)) {
			return;
		}

		// 이용자(구매자 + 선물수신자) 조회
		PurchaseUserDevice useUser = null;
		if (StringUtils.equals(purchaseOrderInfo.getPrchsCaseCd(), PurchaseConstants.PRCHS_CASE_GIFT_CD)) {
			useUser = purchaseOrderInfo.getReceiveUser();
		} else {
			useUser = purchaseOrderInfo.getPurchaseUser();
		}

		// 디바이스(mdn) 기반 상품 여부 조회
		boolean bDeviceBased = this.purchaseOrderPolicyService.isDeviceBasedPurchaseHistory(useUser.getTenantId(),
				purchaseOrderInfo.getTenantProdGrpCd());

		// 기구매 체크 처리 용 변수
		List<String> existenceProdIdList = new ArrayList<String>();
		List<String> tempExistenceProdIdList = null;

		String existTenantId = useUser.getTenantId();
		String existUserKey = useUser.getUserKey();
		String existDeviceKey = bDeviceBased ? useUser.getDeviceKey() : null;

		for (PurchaseProduct product : purchaseOrderInfo.getPurchaseProductList()) {
			// 연령체크 안함: 생년월일도 * 문자 포함으로 확인불가
			// 연령 체크
			// if (StringUtils.equals(product.getProdGrdCd(), PurchaseConstants.PRODUCT_GRADE_19) && useUser.getAge() <
			// 20) {
			// throw new StorePlatformException("SAC_PUR_5110");
			// }

			// 실명인증 체크
			if (StringUtils.equals(product.getProdGrdCd(), PurchaseConstants.PRODUCT_GRADE_19)
					&& purchaseOrderInfo.getPurchaseUser().isRealName() == false) {
				throw new StorePlatformException("SAC_PUR_4105");
			}

			// (정액권) 배타 상품 체크
			if (CollectionUtils.isNotEmpty(product.getExclusiveFixrateProdIdList())) {

				tempExistenceProdIdList = new ArrayList<String>();
				for (String exclusiveProdId : product.getExclusiveFixrateProdIdList()) {
					tempExistenceProdIdList.add(exclusiveProdId);
				}

				List<ExistenceScRes> checkPurchaseResultList = this.searchExistence(existTenantId, existUserKey,
						existDeviceKey, tempExistenceProdIdList);

				for (ExistenceScRes checkRes : checkPurchaseResultList) {
					if (StringUtils.equals(checkRes.getStatusCd(), PurchaseConstants.PRCHS_STATUS_COMPT)) {
						throw new StorePlatformException("SAC_PUR_6109");
					}
				}
			}

			// 이용 가능한 정액권 기구매 확인 처리
			if (CollectionUtils.isNotEmpty(product.getAvailableFixrateProdIdList())) {

				tempExistenceProdIdList = new ArrayList<String>();
				for (String fixrateProdId : product.getAvailableFixrateProdIdList()) {
					tempExistenceProdIdList.add(fixrateProdId);
				}

				List<ExistenceScRes> checkPurchaseResultList = this.searchExistence(existTenantId, existUserKey,
						existDeviceKey, tempExistenceProdIdList);

				ExistenceScRes useExistenceScRes = null;
				for (ExistenceScRes checkRes : checkPurchaseResultList) {
					if (StringUtils.equals(checkRes.getStatusCd(), PurchaseConstants.PRCHS_STATUS_COMPT) == false) {
						continue;
					}

					if (useExistenceScRes == null) {
						useExistenceScRes = checkRes;

						// TAKTODO:: 구매일시 비교로 변경
					} else if (checkRes.getPrchsId().compareTo(useExistenceScRes.getPrchsId()) > 0) {
						useExistenceScRes = checkRes;
					}
				}

				if (useExistenceScRes != null) {

					// 정액권 DRM 정보 조회 및 반영
					FreePassInfo freepassInfo = this.purchaseDisplayRepository.searchFreePassDrmInfo(
							useUser.getTenantId(), purchaseOrderInfo.getLangCd(), useExistenceScRes.getProdId(),
							product.getProdId());
					if (freepassInfo != null) {
						product.setUsePeriodUnitCd(freepassInfo.getUsePeriodUnitCd());
						product.setUsePeriod(Integer.parseInt(freepassInfo.getUsePeriod()));
						product.setDrmYn(freepassInfo.getDrmYn());
						product.setUseFixrateProdId(useExistenceScRes.getProdId()); // 사용할 정액권ID 세팅

						// 무료구매 처리 데이터 세팅
						purchaseOrderInfo.setRealTotAmt(0.0);

						if (StringUtils.equals(freepassInfo.getCmpxProdClsfCd(),
								PurchaseConstants.FIXRATE_PROD_TYPE_VOD_FIXRATE)) {
							// OR000612-정액권
							purchaseOrderInfo.setFreePaymentMtdCd(PurchaseConstants.PAYMENT_METHOD_FIXRATE);

						} else if (StringUtils.equals(freepassInfo.getCmpxProdClsfCd(),
								PurchaseConstants.FIXRATE_PROD_TYPE_VOD_SERIESPASS)) {
							// OR000613-시리즈패스권
							purchaseOrderInfo.setFreePaymentMtdCd(PurchaseConstants.PAYMENT_METHOD_SERIESPASS);

						} else if (StringUtils.equals(freepassInfo.getCmpxProdClsfCd(),
								PurchaseConstants.FIXRATE_PROD_TYPE_VOD_FIXRATE)) {
							// OR000617-이북/코믹 전권 소장
							purchaseOrderInfo.setFreePaymentMtdCd(PurchaseConstants.PAYMENT_METHOD_EBOOKCOMIC_OWN);

						} else if (StringUtils.equals(freepassInfo.getCmpxProdClsfCd(),
								PurchaseConstants.FIXRATE_PROD_TYPE_VOD_FIXRATE)) {
							// OR000618-이북/코믹 전권 대여
							purchaseOrderInfo.setFreePaymentMtdCd(PurchaseConstants.PAYMENT_METHOD_EBOOKCOMIC_LOAN);
						}
					}
				}
			}

			// 쇼핑 상품 추가 체크
			if (StringUtils.startsWith(purchaseOrderInfo.getTenantProdGrpCd(),
					PurchaseConstants.TENANT_PRODUCT_GROUP_SHOPPING)) {
				if (StringUtils.isNotBlank(product.getSpecialSaleCouponId())) { // TAKTODO:: 특가상품 구매 건수 체크
					SearchShoppingSpecialCountScReq specialReq = new SearchShoppingSpecialCountScReq();
					specialReq.setTenantId(purchaseOrderInfo.getTenantId());
					specialReq.setUserKey(purchaseOrderInfo.getPurchaseUser().getUserKey());
					specialReq.setDeviceKey(purchaseOrderInfo.getPurchaseUser().getDeviceKey());
					specialReq.setSpecialCouponId(product.getSpecialSaleCouponId());
					specialReq.setSpecialCouponAmt(product.getProdAmt() - product.getSpecialSaleAmt());

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

				// 발급 가능 여부 확인: 결제자 MDN 기준
				this.checkAvailableCouponPublish(product.getCouponCode(), product.getItemCode(), product.getProdQty(),
						purchaseOrderInfo.getPurchaseUser().getDeviceId());

			} else { // 기구매 체크 대상 추가: 쇼핑상품은 동일상품 중복구매 허용

				existenceProdIdList.add(product.getProdId());
			}
		}

		// 기구매 체크
		if (existenceProdIdList.size() > 100) {

			List<ExistenceScRes> checkPurchaseResultList = this.searchExistence(existTenantId, existUserKey,
					existDeviceKey, existenceProdIdList);

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
			throw new StorePlatformException("SAC_PUR_7205", e);
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

	/*
	 * 
	 * <pre> 기구매 체크. </pre>
	 * 
	 * @param tenantId 테넌트 ID
	 * 
	 * @param userKey 내부 회원 NO
	 * 
	 * @param deviceKey 내부 디바이스 ID
	 * 
	 * @param prodIdList 기구매 체크 대상 상품ID 목록
	 * 
	 * @return 기구매 체크 결과
	 */
	public List<ExistenceScRes> searchExistence(String tenantId, String userKey, String deviceKey,
			List<String> prodIdList) {

		List<ExistenceItemSc> existenceItemScList = new ArrayList<ExistenceItemSc>();
		ExistenceItemSc existenceItemSc = null;
		for (String prodId : prodIdList) {
			existenceItemSc = new ExistenceItemSc();
			existenceItemSc.setProdId(prodId);
			existenceItemScList.add(existenceItemSc);
		}

		ExistenceScReq existenceScReq = new ExistenceScReq();
		existenceScReq.setTenantId(tenantId);
		existenceScReq.setUserKey(userKey);
		if (deviceKey != null) {
			existenceScReq.setCheckValue(true);
			existenceScReq.setDeviceKey(deviceKey); // 디바이스(mdn) 기반
		}
		existenceScReq.setProductList(existenceItemScList);

		return this.existenceSCI.searchExistenceList(existenceScReq);
	}
}
