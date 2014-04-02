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
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.external.client.shopping.sci.ShoppingSCI;
import com.skplanet.storeplatform.external.client.shopping.vo.BizCouponPublishDetailEcReq;
import com.skplanet.storeplatform.external.client.shopping.vo.BizCouponPublishEcReq;
import com.skplanet.storeplatform.external.client.shopping.vo.CouponPublishCancelEcReq;
import com.skplanet.storeplatform.external.client.shopping.vo.CouponPublishEcReq;
import com.skplanet.storeplatform.external.client.shopping.vo.CouponPublishEcRes;
import com.skplanet.storeplatform.external.client.shopping.vo.CouponPublishItemDetailEcRes;
import com.skplanet.storeplatform.external.client.tstore.sci.TStoreCashSCI;
import com.skplanet.storeplatform.external.client.tstore.sci.TStoreCouponSCI;
import com.skplanet.storeplatform.external.client.tstore.sci.TStoreNotiSCI;
import com.skplanet.storeplatform.external.client.tstore.vo.Cash;
import com.skplanet.storeplatform.external.client.tstore.vo.Coupon;
import com.skplanet.storeplatform.external.client.tstore.vo.ProdId;
import com.skplanet.storeplatform.external.client.tstore.vo.TStoreCashEcReq;
import com.skplanet.storeplatform.external.client.tstore.vo.TStoreCashEcRes;
import com.skplanet.storeplatform.external.client.tstore.vo.TStoreNotiEcReq;
import com.skplanet.storeplatform.external.client.tstore.vo.TStoreNotiEcRes;
import com.skplanet.storeplatform.external.client.tstore.vo.UserCouponListEcReq;
import com.skplanet.storeplatform.external.client.tstore.vo.UserCouponListEcRes;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.purchase.client.common.vo.AutoPrchs;
import com.skplanet.storeplatform.purchase.client.common.vo.Payment;
import com.skplanet.storeplatform.purchase.client.common.vo.PrchsProdCnt;
import com.skplanet.storeplatform.purchase.client.order.sci.PurchaseOrderSCI;
import com.skplanet.storeplatform.purchase.client.order.sci.PurchaseOrderSearchSCI;
import com.skplanet.storeplatform.purchase.client.order.vo.ConfirmPurchaseScReq;
import com.skplanet.storeplatform.purchase.client.order.vo.ConfirmPurchaseScRes;
import com.skplanet.storeplatform.purchase.client.order.vo.MakeFreePurchaseScReq;
import com.skplanet.storeplatform.purchase.client.order.vo.MakeFreePurchaseScRes;
import com.skplanet.storeplatform.purchase.client.order.vo.PrchsDtlMore;
import com.skplanet.storeplatform.purchase.client.order.vo.PurchaseUserInfo;
import com.skplanet.storeplatform.purchase.client.order.vo.ReservePurchaseScReq;
import com.skplanet.storeplatform.purchase.client.order.vo.ReservePurchaseScRes;
import com.skplanet.storeplatform.purchase.client.order.vo.SearchReservedPurchaseListScReq;
import com.skplanet.storeplatform.purchase.client.order.vo.SearchReservedPurchaseListScRes;
import com.skplanet.storeplatform.purchase.client.order.vo.ShoppingCouponPublishInfo;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchUserPayplanetSacRes;
import com.skplanet.storeplatform.sac.client.purchase.vo.order.NotifyPaymentSacReq;
import com.skplanet.storeplatform.sac.client.purchase.vo.order.PaymentInfo;
import com.skplanet.storeplatform.sac.client.purchase.vo.order.VerifyOrderSacRes;
import com.skplanet.storeplatform.sac.purchase.common.service.PayPlanetShopService;
import com.skplanet.storeplatform.sac.purchase.common.service.PurchaseTenantPolicyService;
import com.skplanet.storeplatform.sac.purchase.common.util.MD5Utils;
import com.skplanet.storeplatform.sac.purchase.common.util.PayPlanetUtils;
import com.skplanet.storeplatform.sac.purchase.common.vo.PayPlanetShop;
import com.skplanet.storeplatform.sac.purchase.constant.PurchaseConstants;
import com.skplanet.storeplatform.sac.purchase.interworking.service.InterworkingSacService;
import com.skplanet.storeplatform.sac.purchase.interworking.vo.Interworking;
import com.skplanet.storeplatform.sac.purchase.interworking.vo.InterworkingSacReq;
import com.skplanet.storeplatform.sac.purchase.order.PaymethodUtil;
import com.skplanet.storeplatform.sac.purchase.order.repository.PurchaseMemberRepository;
import com.skplanet.storeplatform.sac.purchase.order.vo.PaymentPageParam;
import com.skplanet.storeplatform.sac.purchase.order.vo.PurchaseOrderInfo;
import com.skplanet.storeplatform.sac.purchase.order.vo.PurchaseProduct;
import com.skplanet.storeplatform.sac.purchase.order.vo.PurchaseUserDevice;
import com.skplanet.storeplatform.sac.purchase.order.vo.SktPaymentPolicyCheckParam;
import com.skplanet.storeplatform.sac.purchase.order.vo.SktPaymentPolicyCheckResult;
import com.skplanet.storeplatform.sac.purchase.order.vo.VerifyOrderInfo;

/**
 * 
 * 구매 서비스 구현
 * 
 * Updated on : 2014. 1. 3. Updated by : 이승택, nTels.
 */
@Service
public class PurchaseOrderServiceImpl implements PurchaseOrderService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Value("#{systemProperties['spring.profiles.active']}")
	private String envServerLevel;

	@Autowired
	private PurchaseOrderSCI purchaseOrderSCI;
	@Autowired
	private PurchaseOrderSearchSCI purchaseOrderSearchSCI;
	@Autowired
	private ShoppingSCI shoppingSCI;
	@Autowired
	private TStoreCouponSCI tStoreCouponSCI;
	@Autowired
	private TStoreCashSCI tStoreCashSCI;
	@Autowired
	private TStoreNotiSCI tStoreNotiSCI;
	@Autowired
	private InterworkingSacService interworkingSacService;
	@Autowired
	private PayPlanetShopService payPlanetShopService;
	@Autowired
	private PurchaseOrderPolicyService purchaseOrderPolicyService;
	@Autowired
	private PurchaseTenantPolicyService purchaseTenantPolicyService;
	@Autowired
	private PurchaseMemberRepository purchaseMemberRepository;

	/**
	 * 
	 * <pre>
	 * 무료구매 처리.
	 * </pre>
	 * 
	 * @param purchaseOrderInfo
	 *            구매요청 정보
	 * @return 생성된 구매이력 건수
	 */
	@Override
	public int createFreePurchase(PurchaseOrderInfo purchaseOrderInfo) {
		// -----------------------------------------------------------------------------
		// 구매ID, 구매일시 세팅

		if (StringUtils.isBlank(purchaseOrderInfo.getPrchsId())) {
			purchaseOrderInfo.setPrchsId(this.makePrchsId());
		}

		purchaseOrderInfo
				.setPrchsDt(DateFormatUtils.format(Calendar.getInstance().getTimeInMillis(), "yyyyMMddHHmmss"));

		// -----------------------------------------------------------------------------
		// 무료구매 완료 요청 데이터 생성

		// 구매생성 요청 데이터
		List<PrchsDtlMore> prchsDtlMoreList = this.makePrchsDtlMoreList(purchaseOrderInfo);

		// 구매집계 요청 데이터
		List<PrchsProdCnt> prchsProdCntList = this.makePrchsProdCntList(prchsDtlMoreList,
				PurchaseConstants.PRCHS_STATUS_COMPT);

		// 결제생성 요청 데이터
		List<Payment> paymentList = null;
		if (StringUtils.isNotBlank(purchaseOrderInfo.getFreePaymentMtdCd())) {
			List<PaymentInfo> paymentInfoList = new ArrayList<PaymentInfo>();
			PaymentInfo paymentInfo = new PaymentInfo();
			paymentInfo.setTid(purchaseOrderInfo.getPrchsId());
			paymentInfo.setPaymentMtdCd(purchaseOrderInfo.getFreePaymentMtdCd());
			paymentInfo.setPaymentAmt(0.0);
			paymentInfo.setPaymentDt(purchaseOrderInfo.getPrchsDt());
			paymentInfoList.add(paymentInfo);

			paymentList = this.makePaymentList(prchsDtlMoreList.get(0), paymentInfoList);
		}

		// -----------------------------------------------------------------------------
		// 무료구매 완료 요청

		MakeFreePurchaseScReq makeFreePurchaseScReq = new MakeFreePurchaseScReq();
		makeFreePurchaseScReq.setPrchsDtlMoreList(prchsDtlMoreList);
		makeFreePurchaseScReq.setPrchsProdCntList(prchsProdCntList);
		makeFreePurchaseScReq.setPaymentList(paymentList);

		MakeFreePurchaseScRes makeFreePurchaseScRes = null;

		try {
			makeFreePurchaseScRes = this.purchaseOrderSCI.makeFreePurchase(makeFreePurchaseScReq);

		} catch (StorePlatformException e) {
			// 중복된 구매요청 체크
			throw (this.isDuplicateKeyException(e) ? new StorePlatformException("SAC_PUR_6110") : e);
		}

		int count = makeFreePurchaseScRes.getCount();

		if (CollectionUtils.isNotEmpty(purchaseOrderInfo.getReceiveUserList())) {
			if (count != purchaseOrderInfo.getReceiveUserList().size()) {
				throw new StorePlatformException("SAC_PUR_7201");
			}
		} else {
			if (count != prchsDtlMoreList.size()) {
				throw new StorePlatformException("SAC_PUR_7201");
			}
		}

		// -----------------------------------------------------------------------------
		// Biz 쿠폰 발급 요청

		if (StringUtils.equals(purchaseOrderInfo.getPrchsReqPathCd(), PurchaseConstants.PRCHS_REQ_PATH_BIZ_COUPON)) {
			List<BizCouponPublishDetailEcReq> bizCouponPublishDetailEcList = new ArrayList<BizCouponPublishDetailEcReq>();

			BizCouponPublishDetailEcReq bizCouponPublishDetailEcReq = null;
			String prchsId = purchaseOrderInfo.getPrchsId();
			for (PurchaseUserDevice receiver : purchaseOrderInfo.getReceiveUserList()) {
				bizCouponPublishDetailEcReq = new BizCouponPublishDetailEcReq();
				bizCouponPublishDetailEcReq.setPrchsId(prchsId);
				bizCouponPublishDetailEcReq.setMdn(receiver.getDeviceId());
				bizCouponPublishDetailEcList.add(bizCouponPublishDetailEcReq);
			}

			BizCouponPublishEcReq bizCouponPublishEcReq = new BizCouponPublishEcReq();
			bizCouponPublishEcReq.setAdminId(purchaseOrderInfo.getPurchaseUser().getUserId());
			bizCouponPublishEcReq.setMdn(purchaseOrderInfo.getPurchaseUser().getDeviceId());
			bizCouponPublishEcReq.setCouponCode(purchaseOrderInfo.getPurchaseProductList().get(0).getCouponCode());
			bizCouponPublishEcReq.setBizCouponPublishDetailList(bizCouponPublishDetailEcList);

			this.logger.debug("PRCHS,ORDER,SAC,CREATEBIZ,PUBLISH,REQ,{}", bizCouponPublishEcReq);

			try {
				this.shoppingSCI.createBizCouponPublish(bizCouponPublishEcReq);
			} catch (Exception e) {
				throw new StorePlatformException("SAC_PUR_7212", e);
			}
		}

		return count;
	}

	/**
	 * 
	 * <pre>
	 * 유료구매 - 구매예약.
	 * </pre>
	 * 
	 * @param purchaseOrderInfo
	 *            구매요청 정보
	 */
	@Override
	public void createReservedPurchase(PurchaseOrderInfo purchaseOrderInfo) {
		// -----------------------------------------------------------------------------
		// PayPlanet 가맹점 정보 조회

		PayPlanetShop payPlanetShop = this.payPlanetShopService.getPayPlanetShopInfo(purchaseOrderInfo.getTenantId());
		purchaseOrderInfo.setMid(payPlanetShop.getMid());
		purchaseOrderInfo.setAuthKey(payPlanetShop.getAuthKey());
		purchaseOrderInfo.setEncKey(payPlanetShop.getEncKey());
		purchaseOrderInfo.setPaymentPageUrl(payPlanetShop.getPaymentUrl());

		// -----------------------------------------------------------------------------
		// 구매ID 생성

		if (StringUtils.isBlank(purchaseOrderInfo.getPrchsId())) {
			purchaseOrderInfo.setPrchsId(this.makePrchsId());
		}

		// -----------------------------------------------------------------------------
		// 구매시간 세팅

		purchaseOrderInfo
				.setPrchsDt(DateFormatUtils.format(Calendar.getInstance().getTimeInMillis(), "yyyyMMddHHmmss"));

		// -----------------------------------------------------------------------------
		// 구매생성 요청 데이터 생성

		List<PrchsDtlMore> prchsDtlMoreList = this.makePrchsDtlMoreList(purchaseOrderInfo);

		// -----------------------------------------------------------------------------
		// 구매예약 생성 요청

		ReservePurchaseScRes reservePurchaseScRes = this.purchaseOrderSCI.reservePurchase(new ReservePurchaseScReq(
				prchsDtlMoreList));

		int count = reservePurchaseScRes.getCount();

		if (CollectionUtils.isNotEmpty(purchaseOrderInfo.getReceiveUserList())) {
			if (count != purchaseOrderInfo.getReceiveUserList().size()) {
				throw new StorePlatformException("SAC_PUR_7201");
			}
		} else {
			if (count != prchsDtlMoreList.size()) {
				throw new StorePlatformException("SAC_PUR_7201");
			}
		}
	}

	/**
	 * 
	 * <pre>
	 * 구매인증.
	 * </pre>
	 * 
	 * @param verifyOrderInfo
	 *            구매인증 요청 정보
	 */
	@Override
	public VerifyOrderSacRes verifyPurchaseOrder(VerifyOrderInfo verifyOrderInfo) {
		// ------------------------------------------------------------------------------------------------
		// 예약된 구매정보 조회

		SearchReservedPurchaseListScReq reqSearch = new SearchReservedPurchaseListScReq();
		reqSearch.setTenantId(verifyOrderInfo.getTenantId());
		reqSearch.setPrchsId(verifyOrderInfo.getPrchsId());

		SearchReservedPurchaseListScRes searchPurchaseListRes = this.purchaseOrderSearchSCI
				.searchReservedPurchaseList(reqSearch);
		List<PrchsDtlMore> prchsDtlMoreList = searchPurchaseListRes.getPrchsDtlMoreList();
		if (prchsDtlMoreList.size() < 1) {
			throw new StorePlatformException("SAC_PUR_7101");
		}
		PrchsDtlMore prchsDtlMore = prchsDtlMoreList.get(0);

		// ------------------------------------------------------------------------------------------------
		// 구매인증 응답 데이터 처리

		// 예약 저장해둔 데이터 추출
		Map<String, String> reservedDataMap = this.parseReservedData(prchsDtlMore.getPrchsResvDesc());

		VerifyOrderSacRes res = new VerifyOrderSacRes();
		res.setMdn(reservedDataMap.get("deviceId")); // 결제 MDN
		res.setFlgMbrStatus(PurchaseConstants.VERIFYORDER_USER_STATUS_NORMAL); // [fix] 회원상태: 1-정상
		res.setFlgProductStatus(PurchaseConstants.VERIFYORDER_PRODUCT_STATUS_NORMAL); // [fix] 상품상태: 1-정상
		res.setBonusCashPoint(reservedDataMap.get("bonusCashPoint")); // 보너스 캐쉬 지급 Point
		res.setBonusCashUsableDayCnt(reservedDataMap.get("bonusCashUsableDayCnt")); // 보너스 캐쉬 유효기간(일)
		res.setAfterAutoPayDt(reservedDataMap.get("afterAutoPayDt")); // 다음 자동 결제일
		res.setDwldAvailableDayCnt(reservedDataMap.get("dwldAvailableDayCnt")); // 다운로드 가능기간(일)
		res.setUsePeriodCnt(reservedDataMap.get("usePeriodCnt")); // 이용기간(일)
		res.setLoanPid(reservedDataMap.get("loanPid")); // 대여하기 상품 ID
		if (StringUtils.isNotBlank(reservedDataMap.get("loanAmt"))) {
			res.setLoanAmt(Double.parseDouble(reservedDataMap.get("loanAmt"))); // 대여하기 상품 금액
		}
		res.setOwnPid(reservedDataMap.get("ownPid")); // 소장하기 상품 ID
		if (StringUtils.isNotBlank(reservedDataMap.get("ownAmt"))) {
			res.setOwnAmt(Double.parseDouble(reservedDataMap.get("ownAmt"))); // 소장하기 상품 금액
		}
		res.setNmSeller(reservedDataMap.get("sellerNm")); // 판매자명
		res.setEmailSeller(reservedDataMap.get("sellerEmail")); // 판매자 이메일 주소
		res.setNoTelSeller(reservedDataMap.get("sellerTelno")); // 판매자 전화번호
		res.setNmDelivery(reservedDataMap.get("receiveNames")); // 선물수신자 성명
		res.setNoMdnDelivery(reservedDataMap.get("receiveMdns")); // 선물수신자 MDN

		// 회원 - 결제 관련 정보 조회
		SearchUserPayplanetSacRes userPayRes = null;
		try {
			userPayRes = this.purchaseMemberRepository.searchUserPayplanet(reservedDataMap.get("userKey"),
					reservedDataMap.get("deviceKey"));
		} catch (Exception e) {
			throw new StorePlatformException("SAC_PUR_7208", e);
		}
		res.setFlgTeleBillingAgree(userPayRes.getSkpAgreementYn()); // 통신과금 동의여부
		res.setFlgOcbUseAgree(userPayRes.getOcbAgreementYn()); // OCB 이용약관 동의여부
		res.setNoOcbCard(userPayRes.getOcbCardNumber()); // OCB 카드번호

		// ------------------------------------------------------------------------------------------------
		// SKT 후불 관련 정책 체크: SKT시험폰, MVNO, 법인폰, 한도금액 조회

		SktPaymentPolicyCheckResult policyResult = null;

		StringBuffer sbPaymentMtdAmtRate = new StringBuffer(64); // 결제수단 재정의
		String testMdnType = PurchaseConstants.SKT_PAYMENT_TYPE_NORMAL;
		double sktAvailableAmt = 0.0;
		if (StringUtils.equals(reservedDataMap.get("telecom"), PurchaseConstants.TELECOM_SKT)) {
			SktPaymentPolicyCheckParam policyCheckParam = new SktPaymentPolicyCheckParam();
			policyCheckParam.setDeviceId(reservedDataMap.get("deviceId"));
			policyCheckParam.setPaymentTotAmt(prchsDtlMore.getTotAmt());
			policyCheckParam.setTenantProdGrpCd(prchsDtlMore.getTenantProdGrpCd());
			if (StringUtils.equals(prchsDtlMore.getPrchsCaseCd(), PurchaseConstants.PRCHS_CASE_GIFT_CD)) {
				policyCheckParam.setTenantId(prchsDtlMore.getTenantId());
				policyCheckParam.setUserKey(prchsDtlMore.getSendInsdUsermbrNo());
				policyCheckParam.setDeviceKey(prchsDtlMore.getSendInsdDeviceId());
				policyCheckParam.setRecvTenantId(prchsDtlMore.getUseTenantId());
				policyCheckParam.setRecvUserKey(prchsDtlMore.getUseInsdUsermbrNo());
				policyCheckParam.setRecvDeviceKey(prchsDtlMore.getUseInsdDeviceId());
				policyCheckParam.setRecvDeviceId(reservedDataMap.get("useDeviceId"));
			} else {
				policyCheckParam.setTenantId(prchsDtlMore.getTenantId());
				policyCheckParam.setUserKey(prchsDtlMore.getUseInsdUsermbrNo());
				policyCheckParam.setDeviceKey(prchsDtlMore.getUseInsdDeviceId());
			}

			try {
				policyResult = this.purchaseOrderPolicyService.checkSktPaymentPolicy(policyCheckParam);
			} catch (Exception e) {
				throw new StorePlatformException("SAC_PUR_7209", e);
			}

			// SKT후불 결제정보 재정의 원인
			res.setTypeSktLimit(policyResult.getSktLimitType());

			// SKT 결제 처리 타입
			if (policyResult.isSktTestMdn()) {
				testMdnType = policyResult.isSktTestMdnWhiteList() ? PurchaseConstants.SKT_PAYMENT_TYPE_TESTDEVICE : PurchaseConstants.SKT_PAYMENT_TYPE_ETCSERVICE;
			} else if (policyResult.isCorporation() || policyResult.isMvno()) {
				testMdnType = PurchaseConstants.SKT_PAYMENT_TYPE_ETCSERVICE;
			}

			// SKT 결제 금액
			sktAvailableAmt = policyResult.getSktRestAmt();

			// SKT 후불 재조정
			if (StringUtils.equals(testMdnType, PurchaseConstants.SKT_PAYMENT_TYPE_ETCSERVICE)) {
			} else {
				if (sktAvailableAmt > 0.0) {
					sbPaymentMtdAmtRate.append("11:").append(sktAvailableAmt).append(":100");
				} else {
					sbPaymentMtdAmtRate.append("11:0:0");
				}
			}

		} else {
			sbPaymentMtdAmtRate.append("11:0:0"); // SKT 외 통신사
		}

		res.setTypeTestMdn(testMdnType); // 법인 및 일반 시험폰 처리 타입 (T01, T02, T03)

		// 결제수단 별 가능 거래금액/비율 조정 정보
		String paymentAdjustInfo = this.purchaseOrderPolicyService.getAvailablePaymethodAdjustInfo(
				prchsDtlMore.getTenantId(), prchsDtlMore.getTenantProdGrpCd());
		if (paymentAdjustInfo != null) {
			sbPaymentMtdAmtRate.append(";").append(
					paymentAdjustInfo.replaceAll("MAXAMT", String.valueOf(prchsDtlMore.getTotAmt())));
		}
		res.setCdMaxAmtRate(sbPaymentMtdAmtRate.toString());

		// ------------------------------------------------------------------------------------------------
		// 결제수단 정렬 재조정

		res.setCdPriority("");

		// ------------------------------------------------------------------------------------------------
		// T store 쿠폰 조회

		// TAKTEST:: 상용 -> BMS 연동 불가로 Skip
		if (StringUtils.equalsIgnoreCase(this.envServerLevel, PurchaseConstants.ENV_SERVER_LEVEL_REAL)) {
			res.setNoCouponList("NULL");

		} else {

			Set<String> prodIdSet = new HashSet<String>();
			for (PrchsDtlMore productInfo : prchsDtlMoreList) {
				prodIdSet.add(productInfo.getProdId());
			}
			List<ProdId> prodIdList = new ArrayList<ProdId>();
			ProdId prodIdObj = null;
			for (String prodId : prodIdSet) {
				prodIdObj = new ProdId();
				prodIdObj.setProdId(prodId);
				prodIdList.add(prodIdObj);
			}
			UserCouponListEcReq userCouponListEcReq = new UserCouponListEcReq();
			userCouponListEcReq.setUserKey(reservedDataMap.get("userKey"));
			userCouponListEcReq.setMdn(reservedDataMap.get("deviceId"));
			// userCouponListEcReq.setCouponType("");
			userCouponListEcReq.setProdIdList(prodIdList);

			UserCouponListEcRes tstoreCouponListEcRes = null;
			try {
				tstoreCouponListEcRes = this.tStoreCouponSCI.getUserCouponList(userCouponListEcReq);
			} catch (Exception e) {
				throw new StorePlatformException("SAC_PUR_7210", e);
			}

			if (StringUtils.equals(tstoreCouponListEcRes.getResultCd(),
					PurchaseConstants.TSTORE_COUPON_RESULT_CD_SUCCESS) == false) {
				throw new StorePlatformException("SAC_PUR_7206", tstoreCouponListEcRes.getResultCd(),
						tstoreCouponListEcRes.getResultMsg());
			}

			if (CollectionUtils.isNotEmpty(tstoreCouponListEcRes.getCouponList())) {
				StringBuffer sbTstoreCoupon = new StringBuffer(256);

				for (Coupon coupon : tstoreCouponListEcRes.getCouponList()) {
					if (sbTstoreCoupon.length() > 0) {
						sbTstoreCoupon.append(";");
					}
					sbTstoreCoupon.append(coupon.getCouponId()).append(":").append(coupon.getCouponName()).append(":")
							.append(coupon.getCouponAmt()).append(":").append(coupon.getMakeHost()).append(":")
							.append(coupon.getCouponType());
				}

				res.setNoCouponList(sbTstoreCoupon.toString());

			} else {
				res.setNoCouponList("NULL");
			}
		}

		// ------------------------------------------------------------------------------------------------
		// T store Cash 조회

		// TAKTEST:: 상용 -> BMS 연동 불가로 Skip
		if (StringUtils.equalsIgnoreCase(this.envServerLevel, PurchaseConstants.ENV_SERVER_LEVEL_REAL)) {
			res.setTstoreCashAmt(0.0);

		} else {

			TStoreCashEcReq tStoreCashEcReq = new TStoreCashEcReq();
			tStoreCashEcReq.setUserKey(reservedDataMap.get("userKey"));
			tStoreCashEcReq.setType(PurchaseConstants.TSTORE_CASH_SVC_TYPE_INQUIRY); // 서비스 타입 : 조회
			tStoreCashEcReq.setDetailType(PurchaseConstants.TSTORE_CASH_SVC_DETAIL_TYPE_INQUIRY); // 서비스 상세 타입 : 조회
			tStoreCashEcReq.setChannel(PurchaseConstants.TSTORE_CASH_SVC_CHANNEL_SAC); // 서비스 채널 : SAC
			tStoreCashEcReq.setProductGroup(PurchaseConstants.TSTORE_CASH_PRODUCT_GROUP_ALL); // 상품군 : 전체

			TStoreCashEcRes tStoreCashEcRes = null;
			try {
				tStoreCashEcRes = this.tStoreCashSCI.getBalance(tStoreCashEcReq);
			} catch (Exception e) {
				throw new StorePlatformException("SAC_PUR_7211", e);
			}

			if (StringUtils.equals(tStoreCashEcRes.getResultCd(), PurchaseConstants.TSTORE_CASH_RESULT_CD_SUCCESS) == false) {
				throw new StorePlatformException("SAC_PUR_7207", tStoreCashEcRes.getResultCd(),
						tStoreCashEcRes.getResultMsg());
			}
			List<Cash> tstoreCashList = tStoreCashEcRes.getCashList();
			double cashAmt = 0.0;
			for (Cash cash : tstoreCashList) {
				cashAmt += Double.parseDouble(cash.getAmt());
			}
			res.setTstoreCashAmt(cashAmt);
		}

		// ------------------------------------------------------------------------------------------------
		// OCB 적립율

		StringBuffer sbOcbAccum = new StringBuffer(64);

		// 쇼핑상품, VOD정액제 상품 제외
		if (StringUtils.equals(prchsDtlMore.getTenantProdGrpCd(), PurchaseConstants.TENANT_PRODUCT_GROUP_SHOPPING) == false
				&& StringUtils.equals(prchsDtlMore.getTenantProdGrpCd(),
						PurchaseConstants.TENANT_PRODUCT_GROUP_SHOPPING) == false) {
			if (policyResult != null) {
				// 시험폰, SKP법인폰 결제 제외
				if (policyResult.isSktTestMdn() || policyResult.isSkpCorporation()) {
					sbOcbAccum.append("11:0.0;");
				} else {
					sbOcbAccum.append("11:4.0;");
				}
			}
			sbOcbAccum.append("12:4.0;13:4.0;14:4.0;25:4.0"); // 다날, 신용카드, PayPin, T store Cash
		}

		res.setCdOcbSaveInfo(sbOcbAccum.toString());

		// ------------------------------------------------------------------------------------------------
		// 결제Page 템플릿

		String tenantProdGrpCd = prchsDtlMore.getTenantProdGrpCd();
		if (StringUtils.equals(prchsDtlMore.getPrchsCaseCd(), PurchaseConstants.PRCHS_CASE_GIFT_CD)) {
			res.setCdPaymentTemplate(PurchaseConstants.PAYMENT_PAGE_TEMPLATE_GIFT); // 선물: TC06

		} else {
			if (StringUtils.startsWith(tenantProdGrpCd, PurchaseConstants.TENANT_PRODUCT_GROUP_SHOPPING)) {
				res.setCdPaymentTemplate(PurchaseConstants.PAYMENT_PAGE_TEMPLATE_SHOPPING); // 쇼핑: TC05

			} else if (StringUtils
					.startsWith(tenantProdGrpCd, PurchaseConstants.TENANT_PRODUCT_GROUP_DTL_MOVIE_FIXRATE)
					|| StringUtils.startsWith(tenantProdGrpCd, PurchaseConstants.TENANT_PRODUCT_GROUP_DTL_TV_FIXRATE)) {
				res.setCdPaymentTemplate(PurchaseConstants.PAYMENT_PAGE_TEMPLATE_AUTOPAY); // 자동결제: TC04

			} else if (StringUtils.startsWith(tenantProdGrpCd, PurchaseConstants.TENANT_PRODUCT_GROUP_VOD)
					|| StringUtils.startsWith(tenantProdGrpCd, PurchaseConstants.TENANT_PRODUCT_GROUP_EBOOKCOMIC)) {
				res.setCdPaymentTemplate(PurchaseConstants.PAYMENT_PAGE_TEMPLATE_LOAN_OWN); // 대여/소장: TC03

			} else if (StringUtils.startsWith(tenantProdGrpCd,
					PurchaseConstants.TENANT_PRODUCT_GROUP_DTL_GAMECASH_FIXRATE)) {
				res.setCdPaymentTemplate(PurchaseConstants.PAYMENT_PAGE_TEMPLATE_GAMECASH_FIXRATE); // 정액제(게임캐쉬): TC02

			} else {
				res.setCdPaymentTemplate(PurchaseConstants.PAYMENT_PAGE_TEMPLATE_NORMAL); // 일반: TC01
			}
		}

		// ------------------------------------------------------------------------------------------------
		// (다날) 컨텐츠 종류: 실물 / 디지털 : 쇼핑상품만 실물로 처리

		if (StringUtils.startsWith(tenantProdGrpCd, PurchaseConstants.TENANT_PRODUCT_GROUP_SHOPPING)) {
			res.setTypeDanalContent(PurchaseConstants.DANAL_CONTENT_TYPE_REAL);
		} else {
			res.setTypeDanalContent(PurchaseConstants.DANAL_CONTENT_TYPE_DIGITAL);
		}

		return res;
	}

	/**
	 * 
	 * <pre>
	 * 유료구매 - 구매확정: 구매상세 내역 상태변경 & 구매 내역 저장 & (선물 경우)발송 상세 내역 저장, 결제내역 저장.
	 * </pre>
	 * 
	 * @param notifyPaymentReq
	 *            결제결과 정보
	 */
	@Override
	public List<PrchsDtlMore> executeConfirmPurchase(NotifyPaymentSacReq notifyPaymentReq, String tenantId) {
		this.logger.debug("PRCHS,ORDER,SAC,CONFIRM,START");

		// ------------------------------------------------------------------------------
		// 구매 예약 건 조회

		SearchReservedPurchaseListScReq reqSearch = new SearchReservedPurchaseListScReq();
		reqSearch.setTenantId(tenantId);
		reqSearch.setPrchsId(notifyPaymentReq.getPrchsId());

		SearchReservedPurchaseListScRes res = this.purchaseOrderSearchSCI.searchReservedPurchaseList(reqSearch);
		List<PrchsDtlMore> prchsDtlMoreList = res.getPrchsDtlMoreList();
		if (prchsDtlMoreList.size() < 1) {
			throw new StorePlatformException("SAC_PUR_7101");
		}

		PrchsDtlMore prchsDtlMore = prchsDtlMoreList.get(0);

		// ------------------------------------------------------------------------------
		// 결제 금액 체크

		double checkAmt = 0.0;
		for (PaymentInfo paymentInfo : notifyPaymentReq.getPaymentInfoList()) {
			checkAmt += paymentInfo.getPaymentAmt();
		}
		if (checkAmt != prchsDtlMore.getTotAmt().doubleValue()
				|| prchsDtlMore.getTotAmt().doubleValue() != notifyPaymentReq.getTotAmt()) {
			throw new StorePlatformException("SAC_PUR_5106");
		}

		// ------------------------------------------------------------------------------
		// 구매예약 시, 추가 저장해 두었던 데이터 추출

		Map<String, String> reservedDataMap = this.parseReservedData(prchsDtlMore.getPrchsResvDesc());

		// 특가 상품 여부
		String sprcProdYn = StringUtils.isNotBlank(reservedDataMap.get("specialCouponId")) ? PurchaseConstants.USE_Y : PurchaseConstants.USE_N;

		// 공통 작업용 세팅
		for (PrchsDtlMore createPurchaseInfo : prchsDtlMoreList) {
			createPurchaseInfo.setSystemId(reservedDataMap.get("systemId"));
			createPurchaseInfo.setCurrencyCd(reservedDataMap.get("currencyCd"));
			createPurchaseInfo.setNetworkTypeCd(reservedDataMap.get("networkTypeCd"));

			createPurchaseInfo.setStatusCd(PurchaseConstants.PRCHS_STATUS_COMPT); // 구매확정
			createPurchaseInfo.setSprcProdYn(sprcProdYn);

			if (StringUtils.equals(createPurchaseInfo.getPrchsCaseCd(), PurchaseConstants.PRCHS_CASE_GIFT_CD)) {
				createPurchaseInfo.setInsdUsermbrNo(createPurchaseInfo.getSendInsdUsermbrNo());
				createPurchaseInfo.setInsdDeviceId(createPurchaseInfo.getSendInsdDeviceId());
			} else {
				createPurchaseInfo.setInsdUsermbrNo(createPurchaseInfo.getUseInsdUsermbrNo());
				createPurchaseInfo.setInsdDeviceId(createPurchaseInfo.getUseInsdDeviceId());
			}
		}
		prchsDtlMore = prchsDtlMoreList.get(0);

		// -------------------------------------------------------------------------------------------
		// 쇼핑상품 쿠폰 발급요청

		String tstoreNotiPublishType = PurchaseConstants.TSTORE_NOTI_PUBLISH_TYPE_SYNC;

		List<ShoppingCouponPublishInfo> shoppingCouponList = null;

		if (prchsDtlMore.getTenantProdGrpCd().startsWith(PurchaseConstants.TENANT_PRODUCT_GROUP_SHOPPING)) {
			CouponPublishEcReq couponPublishEcReq = new CouponPublishEcReq();
			couponPublishEcReq.setPrchsId(prchsDtlMore.getPrchsId());
			couponPublishEcReq.setUseMdn(reservedDataMap.get("useDeviceId"));
			couponPublishEcReq.setBuyMdn(reservedDataMap.get("deviceId"));
			couponPublishEcReq.setCouponCode(reservedDataMap.get("couponCode"));
			couponPublishEcReq.setItemCode(reservedDataMap.get("itemCode"));
			couponPublishEcReq.setItemCount(prchsDtlMore.getProdQty());
			CouponPublishEcRes couponPublishEcRes = this.shoppingSCI.createCouponPublish(couponPublishEcReq);
			this.logger.debug("PRCHS,ORDER,SAC,CONFIRM,SHOPPING,{}", couponPublishEcRes);

			if (StringUtils
					.equals(couponPublishEcRes.getPublishType(), PurchaseConstants.SHOPPING_COUPON_PUBLISH_ASYNC) == false
					&& CollectionUtils.isNotEmpty(couponPublishEcRes.getItems())) { // 0-즉시발급
				String availStartDt = couponPublishEcRes.getAvailStartdate();
				String availEndDt = couponPublishEcRes.getAvailEnddate();

				shoppingCouponList = new ArrayList<ShoppingCouponPublishInfo>();
				ShoppingCouponPublishInfo shoppingCouponPublishInfo = null;
				for (CouponPublishItemDetailEcRes couponInfo : couponPublishEcRes.getItems()) {
					shoppingCouponPublishInfo = new ShoppingCouponPublishInfo();
					shoppingCouponPublishInfo.setAvailStartDt(availStartDt);
					shoppingCouponPublishInfo.setAvailEndDt(availEndDt);
					shoppingCouponPublishInfo.setPublishCode(couponInfo.getPublishCode());
					shoppingCouponPublishInfo.setShippingUrl(couponInfo.getShippingUrl());
					shoppingCouponPublishInfo.setAddInfo(couponInfo.getExtraData());

					shoppingCouponList.add(shoppingCouponPublishInfo);
				}
			} else {
				tstoreNotiPublishType = PurchaseConstants.TSTORE_NOTI_PUBLISH_TYPE_ASYNC;
			}
		}

		prchsDtlMore.setPrchsResvDesc(prchsDtlMore.getPrchsResvDesc() + "&tstoreNotiPublishType="
				+ tstoreNotiPublishType);

		// -------------------------------------------------------------------------------------------
		// 구매확정 요청 데이터 생성

		// 구매집계 요청 데이터
		List<PrchsProdCnt> prchsProdCntList = this.makePrchsProdCntList(prchsDtlMoreList,
				PurchaseConstants.PRCHS_STATUS_COMPT);

		// 결제생성 요청 데이터
		List<Payment> paymentList = this.makePaymentList(prchsDtlMore, notifyPaymentReq.getPaymentInfoList());

		// 자동구매 생성 요청 데이터
		this.logger.debug("PRCHS,ORDER,SAC,CONFIRM,CHECKAUTO,{},{}", prchsDtlMore.getPrchsProdType(),
				reservedDataMap.get("autoPrchsYn"));
		List<AutoPrchs> autoPrchsList = null;
		if (StringUtils.equals(reservedDataMap.get("autoPrchsYn"), PurchaseConstants.USE_Y)) {
			autoPrchsList = this.makeAutoPrchsList(prchsDtlMore);
		}

		// 구매확정 데이터
		ConfirmPurchaseScReq confirmPurchaseScReq = new ConfirmPurchaseScReq();
		confirmPurchaseScReq.setTenantId(prchsDtlMore.getTenantId());
		confirmPurchaseScReq.setSystemId(prchsDtlMore.getSystemId());
		confirmPurchaseScReq.setUseUserKey(prchsDtlMore.getUseInsdUsermbrNo());
		confirmPurchaseScReq.setPrchsId(prchsDtlMore.getPrchsId());
		confirmPurchaseScReq.setCurrencyCd(prchsDtlMore.getCurrencyCd());
		confirmPurchaseScReq.setNetworkTypeCd(prchsDtlMore.getNetworkTypeCd());

		confirmPurchaseScReq.setPrchsProdCntList(prchsProdCntList); // 건수집계
		confirmPurchaseScReq.setPaymentList(paymentList); // 결제
		confirmPurchaseScReq.setAutoPrchsList(autoPrchsList); // 자동구매
		confirmPurchaseScReq.setShoppingCouponList(shoppingCouponList); // 쇼핑발급 목록

		// -------------------------------------------------------------------------------------------
		// 구매확정 요청

		try {

			ConfirmPurchaseScRes confirmPurchaseScRes = this.purchaseOrderSCI.confirmPurchase(confirmPurchaseScReq);
			this.logger.debug("PRCHS,ORDER,SAC,CONFIRM,CNT,{}", confirmPurchaseScRes.getCount());
			if (confirmPurchaseScRes.getCount() < 1) {
				throw new StorePlatformException("SAC_PUR_7202");
			}

		} catch (StorePlatformException e) {
			// 쇼핑쿠폰발급 취소 등
			this.revertToPreConfirm(prchsDtlMoreList);

			// 중복된 구매요청 체크
			throw (this.isDuplicateKeyException(e) ? new StorePlatformException("SAC_PUR_6110") : e);
		}

		this.logger.debug("PRCHS,ORDER,SAC,CONFIRM,END");
		return prchsDtlMoreList;
	}

	/**
	 * 
	 * <pre>
	 * 구매 후처리( 인터파크/씨네21, 구매건수 증가).
	 * </pre>
	 */
	@Override
	public void postPurchase(List<PrchsDtlMore> prchsDtlMoreList) {
		this.logger.debug("PRCHS,ORDER,SAC,POST,START,{}", prchsDtlMoreList.get(0).getPrchsId());

		// ------------------------------------------------------------------------------------
		// 인터파크 / 씨네21

		List<Interworking> interworkingList = new ArrayList<Interworking>();
		Interworking interworking = null;
		Map<String, String> reservedDataMap = null;

		for (PrchsDtlMore prchsDtlMore : prchsDtlMoreList) {
			reservedDataMap = this.parseReservedData(prchsDtlMore.getPrchsResvDesc());

			interworking = new Interworking();
			interworking.setProdId(prchsDtlMore.getProdId());
			interworking.setProdAmt(prchsDtlMore.getProdAmt());
			interworking.setSellermbrNo(reservedDataMap.get("sellerMbrNo"));
			interworking.setMallCd(reservedDataMap.get("mallCd"));
			interworking.setCompCid(reservedDataMap.get("outsdContentsId"));

			interworkingList.add(interworking);
		}

		PrchsDtlMore prchsDtlMore = prchsDtlMoreList.get(0);
		InterworkingSacReq interworkingSacReq = new InterworkingSacReq();
		interworkingSacReq.setTenantId(prchsDtlMore.getTenantId());
		interworkingSacReq.setSystemId(prchsDtlMore.getSystemId());
		interworkingSacReq.setUserKey(prchsDtlMore.getUseInsdUsermbrNo());
		interworkingSacReq.setDeviceKey(prchsDtlMore.getUseInsdDeviceId());
		interworkingSacReq.setPrchsId(prchsDtlMore.getPrchsId());
		interworkingSacReq.setPrchsDt(prchsDtlMore.getPrchsDt());
		interworkingSacReq.setInterworkingList(interworkingList);

		try {
			this.interworkingSacService.createInterworking(interworkingSacReq);
		} catch (Exception e) {
			// 예외 throw 차단
			this.logger.debug("PRCHS,ORDER,SAC,POST,INTER,ERROR,{},{}", prchsDtlMoreList.get(0).getPrchsId(),
					e.getMessage());
		}

		// ------------------------------------------------------------------------------------
		// Tstore 측으로 알림: 이메일 발송, SMS / MMS 등등 처리

		// TAKTEST:: 상용 -> BMS 연동 불가로 Skip
		if (StringUtils.equalsIgnoreCase(this.envServerLevel, PurchaseConstants.ENV_SERVER_LEVEL_REAL) == false) {

			prchsDtlMore = prchsDtlMoreList.get(0);
			reservedDataMap = this.parseReservedData(prchsDtlMore.getPrchsResvDesc());
			String tstoreNotiPublishType = reservedDataMap.get("tstoreNotiPublishType");

			TStoreNotiEcReq tStoreNotiEcReq = new TStoreNotiEcReq();
			tStoreNotiEcReq.setPrchsId(prchsDtlMore.getPrchsId());
			tStoreNotiEcReq.setPrchsDt(prchsDtlMore.getPrchsDt());
			tStoreNotiEcReq.setUserKey(prchsDtlMore.getUseInsdUsermbrNo());
			tStoreNotiEcReq.setDeviceKey(prchsDtlMore.getUseInsdDeviceId());
			tStoreNotiEcReq.setPublishType(tstoreNotiPublishType);
			tStoreNotiEcReq.setType(PurchaseConstants.TSTORE_NOTI_TYPE_NORMALPAY);

			try {
				TStoreNotiEcRes tStoreNotiEcRes = this.tStoreNotiSCI.postTStoreNoti(tStoreNotiEcReq);
				this.logger.debug("PRCHS,ORDER,SAC,POST,TSTORENOTI,{},{},{}", prchsDtlMoreList.get(0).getPrchsId(),
						tStoreNotiEcRes.getCode(), tStoreNotiEcRes.getMessage());
			} catch (Exception e) {
				// 예외 throw 차단
				this.logger.debug("PRCHS,ORDER,SAC,POST,TSTORENOTI,ERROR,{},{}", prchsDtlMoreList.get(0).getPrchsId(),
						e.getMessage());
			}
		}

		this.logger.debug("PRCHS,ORDER,SAC,POST,END,{}", prchsDtlMoreList.get(0).getPrchsId());
	}

	/**
	 * 
	 * <pre>
	 * 유료구매 - 결제Page 준비작업.
	 * </pre>
	 * 
	 * @param purchaseOrderInfo
	 *            구매요청 정보
	 */
	@Override
	public void setPaymentPageInfo(PurchaseOrderInfo purchaseOrderInfo) {
		this.logger.debug("PRCHS,ORDER,SAC,PAYPAGE,START,{}", purchaseOrderInfo.getPrchsId());

		// eData(암호화 데이터)
		PurchaseProduct product = purchaseOrderInfo.getPurchaseProductList().get(0);

		PaymentPageParam paymentPageParam = new PaymentPageParam();
		paymentPageParam.setMid(purchaseOrderInfo.getMid());
		paymentPageParam.setAuthKey(purchaseOrderInfo.getAuthKey());
		paymentPageParam.setOrderId(purchaseOrderInfo.getPrchsId());
		paymentPageParam.setMctTrDate(purchaseOrderInfo.getPrchsDt());
		paymentPageParam.setAmtPurchase(String.valueOf(purchaseOrderInfo.getRealTotAmt()));
		paymentPageParam.setPid(product.getProdId());
		if (purchaseOrderInfo.getPurchaseProductList().size() > 1) {
			paymentPageParam.setpName(product.getProdNm() + " 외 "
					+ (purchaseOrderInfo.getPurchaseProductList().size() - 1) + "개");
		} else {
			paymentPageParam.setpName(product.getProdNm());
		}
		// paymentPageParam.setpDescription("remove");
		// paymentPageParam.setpType("remove");
		paymentPageParam.setAid(product.getAid());
		paymentPageParam.setReturnFormat(PaymentPageParam.PP_RETURN_FORMAT_JSON);
		paymentPageParam.setFlgMchtAuth(PurchaseConstants.USE_Y);
		paymentPageParam.setMctSpareParam(purchaseOrderInfo.getTenantId());
		paymentPageParam.setMdn(purchaseOrderInfo.getPurchaseUser().getDeviceId());
		paymentPageParam.setNmDevice(purchaseOrderInfo.getPurchaseUser().getDeviceModelCd());
		paymentPageParam.setImei(purchaseOrderInfo.getImei());
		paymentPageParam.setUacd(purchaseOrderInfo.getUacd());
		if (StringUtils.equals(purchaseOrderInfo.getNetworkTypeCd(), PurchaseConstants.NETWORK_TYPE_3G)) {
			paymentPageParam.setTypeNetwork(PurchaseConstants.PAYPLANET_NETWORK_TYPE_3GLTE); // 3G, LTE
		} else if (StringUtils.equals(purchaseOrderInfo.getNetworkTypeCd(), PurchaseConstants.NETWORK_TYPE_WIFI)) {
			paymentPageParam.setTypeNetwork(PurchaseConstants.PAYPLANET_NETWORK_TYPE_WIFI); // WIFI
		} else {
			paymentPageParam.setTypeNetwork(PurchaseConstants.PAYPLANET_NETWORK_TYPE_UNKNOWN);
		}
		if (StringUtils.equals(purchaseOrderInfo.getPurchaseUser().getTelecom(), PurchaseConstants.TELECOM_SKT)) {
			paymentPageParam.setCarrier(PurchaseConstants.PAYPLANET_TELECOM_SKT); // SKT
		} else if (StringUtils
				.equals(purchaseOrderInfo.getPurchaseUser().getTelecom(), PurchaseConstants.TELECOM_UPLUS)) {
			paymentPageParam.setCarrier(PurchaseConstants.PAYPLANET_TELECOM_LGT); // LGT
		} else if (StringUtils.equals(purchaseOrderInfo.getPurchaseUser().getTelecom(), PurchaseConstants.TELECOM_KT)) {
			paymentPageParam.setCarrier(PurchaseConstants.PAYPLANET_TELECOM_KT); // KT
		} else {
			paymentPageParam.setCarrier(PurchaseConstants.PAYPLANET_TELECOM_UNKNOWN); // UKNOWN
		}
		paymentPageParam.setNoSim(purchaseOrderInfo.getSimNo());
		paymentPageParam.setFlgSim(purchaseOrderInfo.getSimYn());

		// 암호화
		String eData = paymentPageParam.makeEncDataFormat();
		this.logger.debug("PRCHS,ORDER,SAC,PAYPAGE,EDATA,SRC,{}", eData);
		try {
			paymentPageParam.setEData(PayPlanetUtils.encrypt(eData, purchaseOrderInfo.getEncKey()));
		} catch (Exception e) {
			throw new StorePlatformException("SAC_PUR_7201", e);
		}
		this.logger.debug("PRCHS,ORDER,SAC,PAYPAGE,EDATA,ENC,{}", paymentPageParam.getEData());

		// Token
		String token = paymentPageParam.makeTokenFormat();
		this.logger.debug("PRCHS,ORDER,SAC,PAYPAGE,TOKEN,SRC,{}", token);
		try {
			paymentPageParam.setToken(MD5Utils.digestInHexFormat(token));
		} catch (Exception e) {
			throw new StorePlatformException("SAC_PUR_7201", e);
		}
		this.logger.debug("PRCHS,ORDER,SAC,PAYPAGE,TOKEN,ENC,{}", paymentPageParam.getToken());

		// 버전
		paymentPageParam.setVersion("1.0");

		// 결제Page 요청 URL
		purchaseOrderInfo.setPaymentPageUrl(purchaseOrderInfo.getPaymentPageUrl());

		purchaseOrderInfo.setPaymentPageParam(paymentPageParam);

		this.logger.debug("PRCHS,ORDER,SAC,PAYPAGE,END,{}", purchaseOrderInfo.getPrchsId());
	}

	/*
	 * 
	 * <pre> 구매 확정 취소 작업. </pre>
	 * 
	 * @param prchsDtlMoreList 구매 정보
	 */
	private void revertToPreConfirm(List<PrchsDtlMore> prchsDtlMoreList) {

		PrchsDtlMore prchsDtlMore = prchsDtlMoreList.get(0);

		// -------------------------------------------------------------------------------------
		// 쇼핑 쿠폰 발급 취소

		if (StringUtils.startsWith(prchsDtlMore.getTenantProdGrpCd(), PurchaseConstants.TENANT_PRODUCT_GROUP_SHOPPING)) {
			CouponPublishCancelEcReq couponPublishCancelEcReq = new CouponPublishCancelEcReq();
			couponPublishCancelEcReq.setPrchsId(prchsDtlMore.getPrchsId());
			try {
				this.shoppingSCI.cancelCouponPublish(couponPublishCancelEcReq);
			} catch (Exception e) {
				// TAKTODO:: 이 때 발생하는 예외처리는 어떻게? 로깅만?
				this.logger.debug("PRCHS,ORDER,SAC,REVERT,COUPON,ERROR,{}", e.getMessage());
			}
		}

	}

	/*
	 * <pre> 새로운 구매ID 생성. </pre>
	 * 
	 * @return 새로 생성된 구매ID
	 */
	private String makePrchsId() {
		// TAKTODO:: 서버ID(2), 인스턴스ID(2) 적용 방안 확인
		String prchsIdSeq = this.purchaseOrderSearchSCI.searchNextPurchaseIdSequence();
		StringBuffer sbPrchsId = new StringBuffer(20);
		sbPrchsId.append("01").append("01")
				.append(DateFormatUtils.format(Calendar.getInstance().getTimeInMillis(), "yyMMddHHmmss"))
				.append(prchsIdSeq);
		return sbPrchsId.toString();
	}

	/*
	 * <pre> 구매생성을 위한 데이터 목록 생성. </pre>
	 * 
	 * @param purchaseOrderInfo 구매요청 VO
	 * 
	 * @return 구매생성을 위한 데이터 목록
	 */
	private List<PrchsDtlMore> makePrchsDtlMoreList(PurchaseOrderInfo purchaseOrderInfo) {
		// 구매예약 시 저장할 데이터 (공통)
		// tenantId, systemId, networkTypeCd, currencyCd
		// 결제자: userKey, deviceKey, deviceId, telecom
		// 보유자: useUserKey, useDeviceKey, useDeviceId, useDeviceModelCd
		PurchaseUserDevice useUser = null;
		if (StringUtils.equals(purchaseOrderInfo.getPrchsCaseCd(), PurchaseConstants.PRCHS_CASE_GIFT_CD)) {
			useUser = purchaseOrderInfo.getReceiveUser();
		} else {
			useUser = purchaseOrderInfo.getPurchaseUser();
		}

		boolean bCharge = purchaseOrderInfo.getRealTotAmt() > 0.0;

		StringBuffer sbReserveData = new StringBuffer(1024);
		if (bCharge) {
			sbReserveData.append("tenantId=").append(useUser.getTenantId()).append("&systemId=")
					.append(purchaseOrderInfo.getSystemId()).append("&userKey=").append(purchaseOrderInfo.getUserKey())
					.append("&deviceId=").append(purchaseOrderInfo.getPurchaseUser().getDeviceId()).append("&telecom=")
					.append(purchaseOrderInfo.getPurchaseUser().getTelecom()).append("&useUserKey=")
					.append(useUser.getUserKey()).append("&useDeviceKey=").append(useUser.getDeviceKey())
					.append("&useDeviceId=").append(useUser.getDeviceId()).append("&useDeviceModelCd=")
					.append(useUser.getDeviceModelCd()).append("&networkTypeCd=")
					.append(purchaseOrderInfo.getNetworkTypeCd()).append("&currencyCd=")
					.append(purchaseOrderInfo.getCurrencyCd());
			if (StringUtils.equals(purchaseOrderInfo.getPrchsCaseCd(), PurchaseConstants.PRCHS_CASE_GIFT_CD)) {
				sbReserveData.append("&receiveNames=").append(StringUtils.defaultString(useUser.getUserName()))
						.append("&receiveMdns=").append(useUser.getDeviceId()); // 선물수신자 성명, 선물수신자 MDN
			}
		}

		int commonReserveDataLen = sbReserveData.length();

		// 구매생성 요청 데이터 세팅
		List<PrchsDtlMore> prchsDtlMoreList = new ArrayList<PrchsDtlMore>();
		PrchsDtlMore prchsDtlMore = null;

		int prchsDtlCnt = 1, i = 0;
		for (PurchaseProduct product : purchaseOrderInfo.getPurchaseProductList()) {
			for (i = 0; i < product.getProdQty(); i++) {
				prchsDtlMore = new PrchsDtlMore();

				prchsDtlMore.setPrchsDtlId(prchsDtlCnt++);

				prchsDtlMore.setTenantId(purchaseOrderInfo.getTenantId());
				prchsDtlMore.setSystemId(purchaseOrderInfo.getSystemId());
				prchsDtlMore.setPrchsId(purchaseOrderInfo.getPrchsId());
				prchsDtlMore.setPrchsDt(purchaseOrderInfo.getPrchsDt());
				if (StringUtils.equals(purchaseOrderInfo.getPrchsCaseCd(), PurchaseConstants.PRCHS_CASE_GIFT_CD)) {
					prchsDtlMore.setSendInsdUsermbrNo(purchaseOrderInfo.getUserKey());
					prchsDtlMore.setSendInsdDeviceId(purchaseOrderInfo.getDeviceKey());
					prchsDtlMore.setUseTenantId(purchaseOrderInfo.getTenantId());

					if (CollectionUtils.isNotEmpty(purchaseOrderInfo.getReceiveUserList())) {
						List<PurchaseUserInfo> receiverList = new ArrayList<PurchaseUserInfo>();
						PurchaseUserInfo receiver = null;
						for (PurchaseUserDevice receiveUser : purchaseOrderInfo.getReceiveUserList()) {
							receiver = new PurchaseUserInfo();
							receiver.setUserKey(receiveUser.getUserKey());
							receiver.setDeviceKey(receiveUser.getDeviceKey());
							receiverList.add(receiver);
						}
						prchsDtlMore.setReceiverList(receiverList);
					} else {
						prchsDtlMore.setUseInsdUsermbrNo(purchaseOrderInfo.getRecvUserKey());
						prchsDtlMore.setUseInsdDeviceId(purchaseOrderInfo.getRecvDeviceKey());
					}
				} else {
					prchsDtlMore.setUseTenantId(purchaseOrderInfo.getTenantId());
					prchsDtlMore.setUseInsdUsermbrNo(purchaseOrderInfo.getUserKey());
					prchsDtlMore.setUseInsdDeviceId(purchaseOrderInfo.getDeviceKey());
				}
				prchsDtlMore.setTotAmt(purchaseOrderInfo.getRealTotAmt());
				prchsDtlMore.setPrchsReqPathCd(purchaseOrderInfo.getPrchsReqPathCd());
				prchsDtlMore.setClientIp(purchaseOrderInfo.getClientIp());
				prchsDtlMore.setUseHidingYn(PurchaseConstants.USE_N);
				prchsDtlMore.setSendHidingYn(PurchaseConstants.USE_N);
				prchsDtlMore.setRegId(purchaseOrderInfo.getSystemId());
				prchsDtlMore.setUpdId(purchaseOrderInfo.getSystemId());
				prchsDtlMore.setPrchsCaseCd(purchaseOrderInfo.getPrchsCaseCd());
				prchsDtlMore.setTenantProdGrpCd(purchaseOrderInfo.getTenantProdGrpCd());
				prchsDtlMore.setCurrencyCd(purchaseOrderInfo.getCurrencyCd()); // PRCHS
				prchsDtlMore.setNetworkTypeCd(purchaseOrderInfo.getNetworkTypeCd()); // PRCHS

				if (purchaseOrderInfo.getTenantProdGrpCd().endsWith(
						PurchaseConstants.TENANT_PRODUCT_GROUP_SUFFIX_FIXRATE)) {
					prchsDtlMore.setPrchsProdType(PurchaseConstants.PRCHS_PROD_TYPE_AUTH); // 권한 상품
				} else {
					prchsDtlMore.setPrchsProdType(PurchaseConstants.PRCHS_PROD_TYPE_UNIT); // 단위 상품
				}
				prchsDtlMore.setProdId(product.getProdId());
				prchsDtlMore.setProdAmt(product.getProdAmt());
				prchsDtlMore.setProdQty(product.getProdQty());
				prchsDtlMore.setResvCol01(product.getResvCol01());
				prchsDtlMore.setResvCol02(product.getResvCol02());
				prchsDtlMore.setResvCol03(product.getResvCol03());
				prchsDtlMore.setResvCol04(product.getResvCol04());
				prchsDtlMore.setResvCol05(product.getResvCol05());
				prchsDtlMore.setUsePeriodUnitCd(product.getUsePeriodUnitCd());
				prchsDtlMore.setUsePeriod(product.getUsePeriod());
				// 비과금 구매요청 시, 이용종료일시 세팅
				if (purchaseOrderInfo.isFreeChargeReq() && StringUtils.isNotBlank(product.getUseExprDt())) {
					prchsDtlMore.setUseExprDt(product.getUseExprDt().length() == 14 ? product.getUseExprDt() : product
							.getUseExprDt() + "235959");
					prchsDtlMore.setDwldExprDt(prchsDtlMore.getUseExprDt());
				}
				prchsDtlMore.setUseFixrateProdId(product.getUseFixrateProdId());
				prchsDtlMore.setDrmYn(product.getDrmYn());
				prchsDtlMore.setAlarmYn(PurchaseConstants.USE_Y);
				prchsDtlMore.setCurrencyCd(purchaseOrderInfo.getCurrencyCd());
				/* IAP */
				prchsDtlMore.setTid(product.getTid()); // 부분유료화 개발사 구매Key
				prchsDtlMore.setTxId(product.getTxId()); // 부분유료화 전자영수증 번호
				prchsDtlMore.setParentProdId(product.getParentProdId()); // 부모_상품_ID
				prchsDtlMore.setPartChrgVer(product.getPartChrgVer()); // 부분_유료_버전
				prchsDtlMore.setPartChrgProdNm(product.getPartChrgProdNm()); // 부분_유료_상품_명
				/* Ring & Bell */
				prchsDtlMore.setRnBillCd(product.getRnBillCd()); // RN_과금_코드
				prchsDtlMore.setInfoUseFee(product.getInfoUseFee()); // 정보_이용_요금 (ISU_AMT_ADD)
				prchsDtlMore.setCid(product.getCid()); // 컨텐츠ID
				prchsDtlMore.setContentsClsf(product.getContentsClsf()); // 컨텐츠_구분
				prchsDtlMore.setContentsType(product.getContentsType()); // 컨텐츠_타입
				prchsDtlMore.setPrchsType(product.getPrchsType()); // 구매_타입
				prchsDtlMore.setTimbreClsf(product.getTimbreClsf()); // 음질_구분
				prchsDtlMore.setTimbreSctn(product.getTimbreSctn()); // 음질_구간
				prchsDtlMore.setMenuId(product.getMenuId()); // 메뉴_ID
				prchsDtlMore.setGenreClsfCd(product.getGenreClsfCd()); // 장르_구분_코드

				// 구매예약 시 저장할 데이터 (상품별)
				if (bCharge) {
					sbReserveData.setLength(commonReserveDataLen);
					sbReserveData.append("&aid=").append(StringUtils.defaultString(product.getAid()))
							.append("&couponCode=").append(StringUtils.defaultString(product.getCouponCode()))
							.append("&itemCode=").append(StringUtils.defaultString(product.getItemCode()))
							.append("&bonusCashPoint=").append(StringUtils.defaultString(product.getBonusCashPoint()))
							.append("&bonusCashUsableDayCnt=")
							.append(StringUtils.defaultString(product.getBonusCashUsableDayCnt()))
							.append("&afterAutoPayDt=").append(StringUtils.defaultString(product.getAfterAutoPayDt()))
							.append("&dwldAvailableDayCnt=")
							.append(StringUtils.defaultString(product.getDwldAvailableDayCnt()))
							.append("&usePeriodCnt=").append(StringUtils.defaultString(product.getUsePeriodCnt()))
							.append("&loanPid=").append(StringUtils.defaultString(product.getLoanPid()))
							.append("&loanAmt=").append(StringUtils.defaultString(product.getLoanAmt()))
							.append("&ownPid=").append(StringUtils.defaultString(product.getOwnPid()))
							.append("&ownAmt=").append(StringUtils.defaultString(product.getOwnAmt()))
							.append("&sellerNm=").append(StringUtils.defaultString(product.getSellerNm()))
							.append("&sellerEmail=").append(StringUtils.defaultString(product.getSellerEmail()))
							.append("&sellerTelno=").append(StringUtils.defaultString(product.getSellerTelno()))
							.append("&sellerMbrNo=").append(StringUtils.defaultString(product.getSellerMbrNo()))
							.append("&mallCd=").append(StringUtils.defaultString(product.getMallCd()))
							.append("&outsdContentsId=")
							.append(StringUtils.defaultString(product.getOutsdContentsId())).append("&autoPrchsYn=")
							.append(StringUtils.defaultString(product.getAutoPrchsYN())).append("&specialCouponId=")
							.append(StringUtils.defaultString(product.getSpecialSaleCouponId()))
							.append("&specialCouponAmt=").append(product.getSpecialCouponAmt());
					prchsDtlMore.setPrchsResvDesc(sbReserveData.toString());
				}

				prchsDtlMoreList.add(prchsDtlMore);
			}
		}

		return prchsDtlMoreList;
	}

	/*
	 * 
	 * <pre> 결제내역 생성 목록 생성. </pre>
	 * 
	 * @param prchsDtlMore 구매정보
	 * 
	 * @param paymentInfoList 결제이력 생성 정보
	 * 
	 * @return 결제내역 생성 목록
	 */
	private List<Payment> makePaymentList(PrchsDtlMore prchsDtlMore, List<PaymentInfo> paymentInfoList) {

		String tenantId = prchsDtlMore.getTenantId();
		String systemId = prchsDtlMore.getSystemId();
		String prchsId = prchsDtlMore.getPrchsId();
		String prchsDt = prchsDtlMore.getPrchsDt();
		String statusCd = prchsDtlMore.getStatusCd();
		Double totAmt = prchsDtlMore.getTotAmt();
		String payUserKey = null;
		String payDeviceKey = null;
		if (StringUtils.equals(prchsDtlMore.getPrchsCaseCd(), PurchaseConstants.PRCHS_CASE_GIFT_CD)) {
			payUserKey = prchsDtlMore.getSendInsdUsermbrNo();
			payDeviceKey = prchsDtlMore.getSendInsdDeviceId();
		} else {
			payUserKey = prchsDtlMore.getUseInsdUsermbrNo();
			payDeviceKey = prchsDtlMore.getUseInsdDeviceId();
		}

		List<Payment> paymentList = new ArrayList<Payment>();
		Payment payment = null;
		int dtlIdCnt = 1;

		for (PaymentInfo paymentInfo : paymentInfoList) {
			payment = new Payment();
			payment.setTenantId(tenantId);
			payment.setPrchsId(prchsId);
			payment.setPaymentDtlId(dtlIdCnt++);
			payment.setInsdUsermbrNo(payUserKey);
			payment.setInsdDeviceId(payDeviceKey);
			payment.setPrchsDt(prchsDt);
			payment.setTotAmt(totAmt);
			payment.setApprNo(paymentInfo.getApprNo());
			payment.setBillKey(paymentInfo.getBillKey());
			payment.setCpnId(paymentInfo.getCpnId());
			payment.setCpnMakeHost(paymentInfo.getCpnMakeHost());
			payment.setCpnType(paymentInfo.getCpnType());
			payment.setMoid(paymentInfo.getMoid());

			payment.setPaymentMtdCd(PaymethodUtil.convert2StoreCode(paymentInfo.getPaymentMtdCd()));
			payment.setPaymentAmt(paymentInfo.getPaymentAmt());
			payment.setPaymentDt(paymentInfo.getPaymentDt());
			payment.setStatusCd(statusCd);

			payment.setTid(paymentInfo.getTid());

			payment.setRegId(systemId);
			payment.setUpdId(systemId);

			paymentList.add(payment);
		}

		return paymentList;
	}

	/*
	 * 
	 * <pre> 상품 건수 저장을 위한 목록 생성. </pre>
	 * 
	 * @param prchsDtlMoreList 구매 정보 목록
	 * 
	 * @param prchsStatusCd 구매상태
	 * 
	 * @return 상품 건수 저장을 위한 목록
	 */
	private List<PrchsProdCnt> makePrchsProdCntList(List<PrchsDtlMore> prchsDtlMoreList, String prchsStatusCd) {
		List<PrchsProdCnt> prchsProdCntList = new ArrayList<PrchsProdCnt>();
		PrchsProdCnt prchsProdCnt = null;

		List<String> procProdIdList = new ArrayList<String>();
		String tenantProdGrpCd = null;

		for (PrchsDtlMore prchsDtlMore : prchsDtlMoreList) {
			if (procProdIdList.contains(prchsDtlMore.getProdId())) {
				continue;
			}
			procProdIdList.add(prchsDtlMore.getProdId());

			prchsProdCnt = new PrchsProdCnt();

			prchsProdCnt.setTenantId(prchsDtlMore.getTenantId());
			prchsProdCnt.setUseUserKey(prchsDtlMore.getUseInsdUsermbrNo());
			prchsProdCnt.setUseDeviceKey(prchsDtlMore.getUseInsdDeviceId());
			prchsProdCnt.setRegId(prchsDtlMore.getSystemId());
			prchsProdCnt.setUpdId(prchsDtlMore.getSystemId());

			prchsProdCnt.setPrchsId(prchsDtlMore.getPrchsId());
			prchsProdCnt.setPrchsDt(prchsDtlMore.getPrchsDt());
			prchsProdCnt.setPrchsClas(prchsDtlMore.getPrchsReqPathCd());
			prchsProdCnt.setStatusCd(prchsStatusCd);

			prchsProdCnt.setProdId(prchsDtlMore.getProdId());
			prchsProdCnt.setProdAmt(prchsDtlMore.getProdAmt());
			prchsProdCnt.setProdQty(prchsDtlMore.getProdQty());
			prchsProdCnt
					.setSprcProdYn(StringUtils.defaultString(prchsDtlMore.getSprcProdYn(), PurchaseConstants.USE_N));

			// 중복 구매 가능한 쇼핑상품 / 부분유료화 상품 처리
			tenantProdGrpCd = prchsDtlMore.getTenantProdGrpCd();
			if (StringUtils.startsWith(tenantProdGrpCd, PurchaseConstants.TENANT_PRODUCT_GROUP_IAP)
					|| StringUtils.startsWith(tenantProdGrpCd, PurchaseConstants.TENANT_PRODUCT_GROUP_SHOPPING)) {
				prchsProdCnt.setProdGrpCd(prchsDtlMore.getTenantProdGrpCd().substring(0, 12)
						+ prchsDtlMore.getPrchsId());
			} else {
				prchsProdCnt.setProdGrpCd(prchsDtlMore.getTenantProdGrpCd().substring(0, 12));
			}

			prchsProdCnt.setCntProcStatus(PurchaseConstants.USE_N);

			prchsProdCntList.add(prchsProdCnt);
		}

		return prchsProdCntList;
	}

	/*
	 * 
	 * <pre> 자동구매 생성을 위한 목록 생성. </pre>
	 * 
	 * @param prchsDtlMore 구매생성 정보
	 * 
	 * @return 자동구매 생성을 위한 목록
	 */
	private List<AutoPrchs> makeAutoPrchsList(PrchsDtlMore prchsDtlMore) {
		List<AutoPrchs> autoPrchsList = new ArrayList<AutoPrchs>();

		AutoPrchs autoPrchs = new AutoPrchs();
		autoPrchs.setTenantId(prchsDtlMore.getTenantId());
		autoPrchs.setFstPrchsId(prchsDtlMore.getPrchsId());
		autoPrchs.setFstPrchsDtlId(1);
		autoPrchs.setInsdUsermbrNo(prchsDtlMore.getInsdUsermbrNo());
		autoPrchs.setInsdDeviceId(prchsDtlMore.getInsdDeviceId());
		autoPrchs.setProdId(prchsDtlMore.getProdId());
		autoPrchs.setPaymentStartDt(prchsDtlMore.getPrchsDt());
		autoPrchs.setPaymentEndDt("99991231235959"); // TAKTODO
		autoPrchs.setAfterPaymentDt(prchsDtlMore.getUseExprDt().substring(0, 8) + "000000"); // TAKTODO
		autoPrchs.setReqPathCd(prchsDtlMore.getPrchsReqPathCd());
		autoPrchs.setClientIp(prchsDtlMore.getClientIp());
		autoPrchs.setPrchsTme(0);
		autoPrchs.setLastPrchsId(prchsDtlMore.getPrchsId());
		autoPrchs.setLastPrchsDtlId(1);
		autoPrchs.setRegId(prchsDtlMore.getSystemId());
		autoPrchs.setUpdId(prchsDtlMore.getSystemId());
		autoPrchs.setResvCol01(prchsDtlMore.getResvCol01());
		autoPrchs.setResvCol02(prchsDtlMore.getResvCol02());
		autoPrchs.setResvCol03(prchsDtlMore.getResvCol03());
		autoPrchs.setResvCol04(prchsDtlMore.getResvCol04());
		autoPrchs.setResvCol05(prchsDtlMore.getResvCol05());
		autoPrchs.setAutoPaymentStatusCd(PurchaseConstants.AUTO_PRCHS_STATUS_AUTO);
		autoPrchsList.add(autoPrchs);

		return autoPrchsList;
	}

	/*
	 * <pre> 구매예약시 예약컬럼에 저장해뒀던 추가 데이터들. </pre>
	 * 
	 * @param reservedData 구매예약시 저장해뒀던 추가 데이터들
	 * 
	 * @return 분리된 파라미터 Map
	 */
	private Map<String, String> parseReservedData(String reservedData) {
		Map<String, String> reservedDataMap = new HashMap<String, String>();
		String[] arReservedData = null;
		String[] arParamKeyValue = null;

		if (StringUtils.isNotEmpty(reservedData)) {
			arReservedData = reservedData.split("&");

			for (String param : arReservedData) {
				arParamKeyValue = param.split("=", 2);
				reservedDataMap.put(arParamKeyValue[0], arParamKeyValue[1]);
			}
		}

		return reservedDataMap;
	}

	/*
	 * 
	 * <pre> DB PK 오류 여부 체크. </pre>
	 * 
	 * @param e 발생한 StorePlatformException 개체
	 * 
	 * @return DB PK 오류 여부
	 */
	private boolean isDuplicateKeyException(StorePlatformException e) {
		Throwable exception = e;
		while (exception != null) {
			if (exception instanceof DuplicateKeyException) {
				return true;
			}
			exception = exception.getCause();
		}

		return false;
	}
}
