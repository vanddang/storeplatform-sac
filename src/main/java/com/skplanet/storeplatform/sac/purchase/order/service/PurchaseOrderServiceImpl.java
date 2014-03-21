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
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.external.client.shopping.sci.ShoppingSCI;
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
import com.skplanet.storeplatform.purchase.client.order.sci.PurchaseOrderSCI;
import com.skplanet.storeplatform.purchase.client.order.sci.PurchaseOrderSearchSCI;
import com.skplanet.storeplatform.purchase.client.order.vo.ConfirmPurchaseScReq;
import com.skplanet.storeplatform.purchase.client.order.vo.ConfirmPurchaseScRes;
import com.skplanet.storeplatform.purchase.client.order.vo.CreateAutoPurchaseScReq;
import com.skplanet.storeplatform.purchase.client.order.vo.CreateAutoPurchaseScRes;
import com.skplanet.storeplatform.purchase.client.order.vo.CreatePaymentScReq;
import com.skplanet.storeplatform.purchase.client.order.vo.CreatePaymentScRes;
import com.skplanet.storeplatform.purchase.client.order.vo.CreatePurchaseSc;
import com.skplanet.storeplatform.purchase.client.order.vo.CreatePurchaseScReq;
import com.skplanet.storeplatform.purchase.client.order.vo.CreatePurchaseScRes;
import com.skplanet.storeplatform.purchase.client.order.vo.CreatePurchaseUserInfo;
import com.skplanet.storeplatform.purchase.client.order.vo.SearchReservedPurchaseListScReq;
import com.skplanet.storeplatform.purchase.client.order.vo.SearchReservedPurchaseListScRes;
import com.skplanet.storeplatform.purchase.client.order.vo.ShoppingCouponPublishInfo;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchUserPayplanetSacRes;
import com.skplanet.storeplatform.sac.client.purchase.vo.order.NotifyPaymentSacReq;
import com.skplanet.storeplatform.sac.client.purchase.vo.order.PaymentInfo;
import com.skplanet.storeplatform.sac.client.purchase.vo.order.VerifyOrderSacRes;
import com.skplanet.storeplatform.sac.purchase.common.service.PurchaseTenantPolicyService;
import com.skplanet.storeplatform.sac.purchase.constant.PurchaseConstants;
import com.skplanet.storeplatform.sac.purchase.interworking.service.InterworkingSacService;
import com.skplanet.storeplatform.sac.purchase.interworking.vo.Interworking;
import com.skplanet.storeplatform.sac.purchase.interworking.vo.InterworkingSacReq;
import com.skplanet.storeplatform.sac.purchase.order.MD5Util;
import com.skplanet.storeplatform.sac.purchase.order.PaymethodUtil;
import com.skplanet.storeplatform.sac.purchase.order.Seed128Util;
import com.skplanet.storeplatform.sac.purchase.order.repository.PurchaseMemberRepository;
import com.skplanet.storeplatform.sac.purchase.order.vo.CreatePaymentSacInfo;
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

	@Value("#{propertiesForSac['purchase.payplanet.paymentPage.url']}")
	private String payplanetPaymentPageUrl;
	@Value("#{propertiesForSac['purchase.payplanet.encrypt.key']}")
	private String payplanetEncryptKey;

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
	private PurchaseTenantPolicyService purchaseTenantPolicyService;
	@Autowired
	private PurchaseOrderPolicyService purchaseOrderPolicyService;
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
		// 구매 내역 저장
		int count = this.createPurchaseByType(purchaseOrderInfo, PurchaseConstants.CREATE_PURCHASE_TYPE_COMPLETED);

		// 결제 내역 저장
		if (StringUtils.isNotBlank(purchaseOrderInfo.getFreePaymentMtdCd())) {
			CreatePaymentSacInfo createPaymentSacInfo = new CreatePaymentSacInfo();
			createPaymentSacInfo.setTenantId(purchaseOrderInfo.getTenantId());
			createPaymentSacInfo.setSystemId(purchaseOrderInfo.getSystemId());
			createPaymentSacInfo.setPayUserKey(purchaseOrderInfo.getUserKey());
			createPaymentSacInfo.setPayDeviceKey(purchaseOrderInfo.getDeviceKey());
			createPaymentSacInfo.setPrchsId(purchaseOrderInfo.getPrchsId());
			createPaymentSacInfo.setPrchsDt(purchaseOrderInfo.getPrchsDt());
			createPaymentSacInfo.setStatusCd(PurchaseConstants.PRCHS_STATUS_COMPT);
			createPaymentSacInfo.setTotAmt(0.0);

			List<PaymentInfo> paymentInfoList = new ArrayList<PaymentInfo>();
			PaymentInfo paymentInfo = new PaymentInfo();
			paymentInfo.setTid(purchaseOrderInfo.getPrchsId());
			paymentInfo.setPaymentMtdCd(purchaseOrderInfo.getFreePaymentMtdCd());
			paymentInfo.setPaymentAmt(0.0);
			paymentInfo.setPaymentDt(purchaseOrderInfo.getPrchsDt());
			// paymentInfo.setApplNum(null);
			// paymentInfo.setMoid(null);
			// paymentInfo.setBillKey(null);
			paymentInfoList.add(paymentInfo);

			createPaymentSacInfo.setPaymentInfoList(paymentInfoList);

			int createCnt = this.createPayment(createPaymentSacInfo);
			if (createCnt != createPaymentSacInfo.getPaymentInfoList().size()) {
				throw new StorePlatformException("SAC_PUR_7203");
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
		this.createPurchaseByType(purchaseOrderInfo, PurchaseConstants.CREATE_PURCHASE_TYPE_RESERVED);
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
		List<CreatePurchaseSc> createPurchaseScList = searchPurchaseListRes.getCreatePurchaseScList();
		if (createPurchaseScList.size() < 1) {
			throw new StorePlatformException("SAC_PUR_7101");
		}
		CreatePurchaseSc createPurchaseSc = createPurchaseScList.get(0);

		// ------------------------------------------------------------------------------------------------
		// 구매인증 응답 데이터 처리

		// 예약 저장해둔 데이터 추출
		Map<String, String> reservedDataMap = this.parseReservedData(createPurchaseSc.getPrchsResvDesc());

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
			policyCheckParam.setPaymentTotAmt(createPurchaseSc.getTotAmt());
			policyCheckParam.setTenantProdGrpCd(createPurchaseSc.getTenantProdGrpCd());
			if (StringUtils.equals(createPurchaseSc.getPrchsCaseCd(), PurchaseConstants.PRCHS_CASE_GIFT_CD)) {
				policyCheckParam.setTenantId(createPurchaseSc.getTenantId());
				policyCheckParam.setUserKey(createPurchaseSc.getSendInsdUsermbrNo());
				policyCheckParam.setDeviceKey(createPurchaseSc.getSendInsdDeviceId());
				policyCheckParam.setRecvTenantId(createPurchaseSc.getUseTenantId());
				policyCheckParam.setRecvUserKey(createPurchaseSc.getUseInsdUsermbrNo());
				policyCheckParam.setRecvDeviceKey(createPurchaseSc.getUseInsdDeviceId());
				policyCheckParam.setRecvDeviceId(reservedDataMap.get("useDeviceId"));
			} else {
				policyCheckParam.setTenantId(createPurchaseSc.getTenantId());
				policyCheckParam.setUserKey(createPurchaseSc.getUseInsdUsermbrNo());
				policyCheckParam.setDeviceKey(createPurchaseSc.getUseInsdDeviceId());
			}

			try {
				policyResult = this.purchaseOrderPolicyService.checkSktPaymentPolicy(policyCheckParam);
			} catch (Exception e) {
				throw new StorePlatformException("SAC_PUR_7209", e);
			}

			// SKT 결제 처리 타입
			if (policyResult.isSktTestMdn()) {
				testMdnType = policyResult.isSktTestMdnWhiteList() ? PurchaseConstants.SKT_PAYMENT_TYPE_TESTDEVICE : PurchaseConstants.SKT_PAYMENT_TYPE_ETCSERVICE;
			} else if (policyResult.isCorporation() || policyResult.isMvno()) {
				testMdnType = PurchaseConstants.SKT_PAYMENT_TYPE_ETCSERVICE;
			}

			// SKT 결제 금액
			sktAvailableAmt = policyResult.getSktRestAmt();
			if (sktAvailableAmt > createPurchaseSc.getTotAmt()) {
				sktAvailableAmt = createPurchaseSc.getTotAmt();
			}

			// SKT 후불 재조정
			if (StringUtils.equals(testMdnType, PurchaseConstants.SKT_PAYMENT_TYPE_ETCSERVICE)) {
				sbPaymentMtdAmtRate.append("11:0:0");
			} else {
				sbPaymentMtdAmtRate.append("11:").append(sktAvailableAmt).append(":100");
			}

		} else {
			sbPaymentMtdAmtRate.append("11:0:0"); // SKT 외 통신사
		}

		res.setTypeTestMdn(testMdnType); // 법인 및 일반 시험폰 처리 타입 (T01, T02, T03)

		// 결제수단 별 가능 거래금액/비율 조정 정보
		String paymentAdjustInfo = this.purchaseOrderPolicyService.getAvailablePaymethodAdjustInfo(
				createPurchaseSc.getTenantId(), createPurchaseSc.getTenantProdGrpCd());
		if (paymentAdjustInfo != null) {
			sbPaymentMtdAmtRate.append(";").append(
					paymentAdjustInfo.replaceAll("MAX", String.valueOf(createPurchaseSc.getTotAmt())));
		}
		res.setCdMaxAmtRate(sbPaymentMtdAmtRate.toString());

		// ------------------------------------------------------------------------------------------------
		// 결제수단 정렬 재조정

		res.setCdPriority("");

		// ------------------------------------------------------------------------------------------------
		// T store 쿠폰 조회

		Set<String> prodIdSet = new HashSet<String>();
		for (CreatePurchaseSc productInfo : createPurchaseScList) {
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
			// TAKTEST:: 방화벽 문제로 일단 Pass
			// tstoreCouponListEcRes = this.tStoreCouponSCI.getUserCouponList(userCouponListEcReq);
			tstoreCouponListEcRes = new UserCouponListEcRes();
			tstoreCouponListEcRes.setResultCd("0000");
		} catch (Exception e) {
			throw new StorePlatformException("SAC_PUR_7210", e);
		}

		if (StringUtils.equals(tstoreCouponListEcRes.getResultCd(), PurchaseConstants.TSTORE_COUPON_RESULT_CD_SUCCESS) == false) {
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
						.append(coupon.getCouponAmt());
			}

			res.setNoCouponList(sbTstoreCoupon.toString());

		} else {
			res.setNoCouponList("NULL");
		}

		// ------------------------------------------------------------------------------------------------
		// T store Cash 조회

		TStoreCashEcReq tStoreCashEcReq = new TStoreCashEcReq();
		tStoreCashEcReq.setUserKey(reservedDataMap.get("userKey"));
		tStoreCashEcReq.setType(PurchaseConstants.TSTORE_CASH_SVC_TYPE_INQUIRY); // 서비스 타입 : 조회
		tStoreCashEcReq.setDetailType(PurchaseConstants.TSTORE_CASH_SVC_DETAIL_TYPE_INQUIRY); // 서비스 상세 타입 : 조회
		tStoreCashEcReq.setChannel(PurchaseConstants.TSTORE_CASH_SVC_CHANNEL_SAC); // 서비스 채널 : SAC
		tStoreCashEcReq.setProductGroup(PurchaseConstants.TSTORE_CASH_PRODUCT_GROUP_ALL); // 상품군 : 전체

		TStoreCashEcRes tStoreCashEcRes = null;
		try {
			// TAKTEST:: 방화벽 문제로 일단 Pass
			// tStoreCashEcRes = this.tStoreCashSCI.getBalance(tStoreCashEcReq);

			tStoreCashEcRes = new TStoreCashEcRes();
			tStoreCashEcRes.setResultCd("0000");
			tStoreCashEcRes.setCashList(new ArrayList<Cash>());
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

		// ------------------------------------------------------------------------------------------------
		// OCB 적립율

		StringBuffer sbOcbAccum = new StringBuffer(64);

		// 쇼핑상품, VOD정액제 상품 제외
		if (StringUtils.equals(createPurchaseSc.getTenantProdGrpCd(), PurchaseConstants.TENANT_PRODUCT_GROUP_SHOPPING) == false
				&& StringUtils.equals(createPurchaseSc.getTenantProdGrpCd(),
						PurchaseConstants.TENANT_PRODUCT_GROUP_SHOPPING) == false) {
			if (policyResult != null) {
				// 시험폰, SKP법인폰 결제 제외
				if (policyResult.isSktTestMdn() == false && policyResult.isSkpCorporation() == false) {
					sbOcbAccum.append("11:4.0;");
				}
			}
			sbOcbAccum.append("12:4.0;13:4.0;14:4.0;25:4.0"); // 다날, 신용카드, PayPin, T store Cash
		}

		res.setCdOcbSaveInfo(sbOcbAccum.toString());

		// ------------------------------------------------------------------------------------------------
		// 결제Page 템플릿

		String tenantProdGrpCd = createPurchaseSc.getTenantProdGrpCd();
		if (StringUtils.equals(createPurchaseSc.getPrchsCaseCd(), PurchaseConstants.PRCHS_CASE_GIFT_CD)) {
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
	public List<CreatePurchaseSc> executeConfirmPurchase(NotifyPaymentSacReq notifyPaymentReq, String tenantId) {
		this.logger.info("PRCHS,ORDER,SAC,CONFIRM,START");

		// ------------------------------------------------------------------------------
		// 구매 예약 건 조회

		SearchReservedPurchaseListScReq reqSearch = new SearchReservedPurchaseListScReq();
		reqSearch.setTenantId(tenantId);
		reqSearch.setPrchsId(notifyPaymentReq.getPrchsId());

		SearchReservedPurchaseListScRes res = this.purchaseOrderSearchSCI.searchReservedPurchaseList(reqSearch);
		List<CreatePurchaseSc> createPurchaseScList = res.getCreatePurchaseScList();
		if (createPurchaseScList.size() < 1) {
			throw new StorePlatformException("SAC_PUR_7101");
		}

		CreatePurchaseSc createPurchaseSc = createPurchaseScList.get(0);

		// ------------------------------------------------------------------------------
		// 결제 금액 체크

		double checkAmt = 0.0;
		for (PaymentInfo paymentInfo : notifyPaymentReq.getPaymentInfoList()) {
			checkAmt += paymentInfo.getPaymentAmt();
		}
		if (checkAmt != createPurchaseSc.getTotAmt().doubleValue()
				|| createPurchaseSc.getTotAmt().doubleValue() != notifyPaymentReq.getTotAmt()) {
			throw new StorePlatformException("SAC_PUR_5106");
		}

		// ------------------------------------------------------------------------------
		// 구매예약 시, 추가 저장해 두었던 데이터 추출

		Map<String, String> reservedDataMap = this.parseReservedData(createPurchaseSc.getPrchsResvDesc());

		// 공통 작업용 세팅
		for (CreatePurchaseSc createPurchaseInfo : createPurchaseScList) {
			createPurchaseInfo.setSystemId(reservedDataMap.get("systemId"));
			createPurchaseInfo.setCurrencyCd(reservedDataMap.get("currencyCd"));
			createPurchaseInfo.setNetworkTypeCd(reservedDataMap.get("networkTypeCd"));

			createPurchaseInfo.setStatusCd(PurchaseConstants.PRCHS_STATUS_COMPT); // 구매확정
			if (StringUtils.equals(createPurchaseInfo.getPrchsCaseCd(), PurchaseConstants.PRCHS_CASE_GIFT_CD)) {
				createPurchaseInfo.setInsdUsermbrNo(createPurchaseInfo.getSendInsdUsermbrNo());
				createPurchaseInfo.setInsdDeviceId(createPurchaseInfo.getSendInsdDeviceId());
			} else {
				createPurchaseInfo.setInsdUsermbrNo(createPurchaseInfo.getUseInsdUsermbrNo());
				createPurchaseInfo.setInsdDeviceId(createPurchaseInfo.getUseInsdDeviceId());
			}
		}
		createPurchaseSc = createPurchaseScList.get(0);

		// -------------------------------------------------------------------------------------------
		// 쇼핑상품 쿠폰 발급요청

		String tstoreNotiPublishType = PurchaseConstants.TSTORE_NOTI_PUBLISH_TYPE_SYNC;

		List<ShoppingCouponPublishInfo> shoppingCouponList = null;

		if (createPurchaseSc.getTenantProdGrpCd().startsWith(PurchaseConstants.TENANT_PRODUCT_GROUP_SHOPPING)) {
			CouponPublishEcReq couponPublishEcReq = new CouponPublishEcReq();
			couponPublishEcReq.setPrchsId(createPurchaseSc.getPrchsId());
			couponPublishEcReq.setUseMdn(reservedDataMap.get("useDeviceId"));
			couponPublishEcReq.setBuyMdn(reservedDataMap.get("deviceId"));
			couponPublishEcReq.setCouponCode(reservedDataMap.get("couponCode"));
			couponPublishEcReq.setItemCode(reservedDataMap.get("itemCode"));
			couponPublishEcReq.setItemCount(createPurchaseSc.getProdQty());
			CouponPublishEcRes couponPublishEcRes = this.shoppingSCI.createCouponPublish(couponPublishEcReq);
			this.logger.info("PRCHS,ORDER,SAC,CONFIRM,SHOPPING,{}", couponPublishEcRes);

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

		createPurchaseSc.setPrchsResvDesc(createPurchaseSc.getPrchsResvDesc() + "&tstoreNotiPublishType="
				+ tstoreNotiPublishType);

		// -------------------------------------------------------------------------------------------
		// 구매확정

		// 구매이력 확정 처리
		ConfirmPurchaseScReq confirmPurchaseScReq = new ConfirmPurchaseScReq();
		confirmPurchaseScReq.setTenantId(createPurchaseSc.getTenantId());
		confirmPurchaseScReq.setSystemId(createPurchaseSc.getSystemId());
		confirmPurchaseScReq.setUseUserKey(createPurchaseSc.getUseInsdUsermbrNo());
		confirmPurchaseScReq.setPrchsId(createPurchaseSc.getPrchsId());
		confirmPurchaseScReq.setCurrencyCd(createPurchaseSc.getCurrencyCd());
		confirmPurchaseScReq.setNetworkTypeCd(createPurchaseSc.getNetworkTypeCd());
		confirmPurchaseScReq.setShoppingCouponList(shoppingCouponList); // 쇼핑발급 목록
		ConfirmPurchaseScRes resConfirm = this.purchaseOrderSCI.executeConfirmPurchase(confirmPurchaseScReq);
		this.logger.info("PRCHS,ORDER,SAC,CONFIRM,PRCHS,{}", resConfirm.getCount());
		if (resConfirm.getCount() < 1) {
			throw new StorePlatformException("SAC_PUR_7202");
		}

		// 결제 내역 저장
		if (StringUtils.isNotBlank(reservedDataMap.get("specialCouponId"))) { // 쇼핑 특가상품 쿠폰 정보 입력
			PaymentInfo paymentInfo = new PaymentInfo();
			paymentInfo.setPaymentAmt(Double.parseDouble(reservedDataMap.get("specialCouponAmt"))
					* createPurchaseSc.getProdQty());
			paymentInfo.setPaymentMtdCd(PurchaseConstants.PAYMENT_METHOD_SHOPPING_SPECIAL_COUPON); // TAKTODO:: 특가상품 쿠폰
																								   // 수단 추가 여부 확인
			paymentInfo.setTid(createPurchaseSc.getPrchsId());
			paymentInfo.setPaymentDt(createPurchaseSc.getPrchsDt());
			paymentInfo.setCpnId(reservedDataMap.get("specialCouponId"));

			notifyPaymentReq.getPaymentInfoList().add(paymentInfo);
		}
		CreatePaymentSacInfo createPaymentSacInfo = new CreatePaymentSacInfo();
		createPaymentSacInfo.setTenantId(createPurchaseSc.getTenantId());
		createPaymentSacInfo.setSystemId(createPurchaseSc.getSystemId());
		createPaymentSacInfo.setPayUserKey(createPurchaseSc.getInsdUsermbrNo());
		createPaymentSacInfo.setPayDeviceKey(createPurchaseSc.getInsdDeviceId());
		createPaymentSacInfo.setPrchsId(createPurchaseSc.getPrchsId());
		createPaymentSacInfo.setPrchsDt(createPurchaseSc.getPrchsDt());
		createPaymentSacInfo.setStatusCd(createPurchaseSc.getStatusCd());
		createPaymentSacInfo.setTotAmt(createPurchaseSc.getTotAmt());
		createPaymentSacInfo.setPaymentInfoList(notifyPaymentReq.getPaymentInfoList());
		int createCnt = this.createPayment(createPaymentSacInfo);
		this.logger.info("PRCHS,ORDER,SAC,CONFIRM,PAYMENT,{}/{}", createCnt, createPaymentSacInfo.getPaymentInfoList()
				.size());
		if (createCnt != createPaymentSacInfo.getPaymentInfoList().size()) {
			throw new StorePlatformException("SAC_PUR_7203");
		}

		// 자동구매 신규이력 저장
		// if (StringUtils.equals(createPurchaseSc.getPrchsProdType(), PurchaseConstants.PRCHS_PROD_TYPE_AUTH)) {
		// for (PaymentInfo paymentInfo : notifyPaymentReq.getPaymentInfoList()) {
		// if (StringUtils.isNotBlank(paymentInfo.getBillKey())) {
		// createCnt = this.createAutoPurchase(createPurchaseSc);
		// if (createCnt != 1) {
		// throw new StorePlatformException("SAC_PUR_7204");
		// }
		// break;
		// }
		// }
		// }
		this.logger.info("PRCHS,ORDER,SAC,CONFIRM,CHECKAUTO,{},{}", createPurchaseSc.getPrchsProdType(),
				reservedDataMap.get("autoPrchsYn"));
		if (StringUtils.equals(createPurchaseSc.getPrchsProdType(), PurchaseConstants.PRCHS_PROD_TYPE_AUTH)
				&& StringUtils.equals(reservedDataMap.get("autoPrchsYn"), PurchaseConstants.USE_Y)) {
			createCnt = this.createAutoPurchase(createPurchaseSc);
			this.logger.info("PRCHS,ORDER,SAC,CONFIRM,AUTOPRCHS,{}", createCnt);
			if (createCnt != 1) {
				throw new StorePlatformException("SAC_PUR_7204");
			}
		}

		this.logger.info("PRCHS,ORDER,SAC,CONFIRM,END");
		return createPurchaseScList;
	}

	/**
	 * 
	 * <pre>
	 * 구매 후처리( 인터파크/씨네21, 구매건수 증가).
	 * </pre>
	 */
	@Override
	public void postPurchase(List<CreatePurchaseSc> createPurchaseScList) {
		this.logger.info("PRCHS,ORDER,SAC,POST,START");

		// ------------------------------------------------------------------------------------
		// 인터파크 / 씨네21

		List<Interworking> interworkingList = new ArrayList<Interworking>();
		Interworking interworking = null;
		Map<String, String> reservedDataMap = null;

		for (CreatePurchaseSc createPurchaseSc : createPurchaseScList) {
			reservedDataMap = this.parseReservedData(createPurchaseSc.getPrchsResvDesc());

			interworking = new Interworking();
			interworking.setProdId(createPurchaseSc.getProdId());
			interworking.setProdAmt(createPurchaseSc.getProdAmt());
			interworking.setSellermbrNo(reservedDataMap.get("sellerMbrNo"));
			interworking.setMallCd(reservedDataMap.get("mallCd"));
			interworking.setCompCid(reservedDataMap.get("outsdContentsId"));

			interworkingList.add(interworking);
		}

		CreatePurchaseSc createPurchaseSc = createPurchaseScList.get(0);
		InterworkingSacReq interworkingSacReq = new InterworkingSacReq();
		interworkingSacReq.setTenantId(createPurchaseSc.getTenantId());
		interworkingSacReq.setSystemId(createPurchaseSc.getSystemId());
		interworkingSacReq.setUserKey(createPurchaseSc.getUseInsdUsermbrNo());
		interworkingSacReq.setDeviceKey(createPurchaseSc.getUseInsdDeviceId());
		interworkingSacReq.setPrchsId(createPurchaseSc.getPrchsId());
		interworkingSacReq.setPrchsDt(createPurchaseSc.getPrchsDt());
		interworkingSacReq.setInterworkingList(interworkingList);

		try {
			this.interworkingSacService.createInterworking(interworkingSacReq);
		} catch (Exception e) {
			// 예외 throw 차단
			this.logger.info("PRCHS,ORDER,SAC,POST,INTER,ERROR,{}", e.getMessage());
		}

		// ------------------------------------------------------------------------------------
		// Tstore 측으로 알림: 이메일 발송, SMS / MMS 등등 처리

		createPurchaseSc = createPurchaseScList.get(0);
		reservedDataMap = this.parseReservedData(createPurchaseSc.getPrchsResvDesc());
		String tstoreNotiPublishType = reservedDataMap.get("tstoreNotiPublishType");

		TStoreNotiEcReq tStoreNotiEcReq = new TStoreNotiEcReq();
		tStoreNotiEcReq.setPrchsId(createPurchaseSc.getPrchsId());
		tStoreNotiEcReq.setPrchsDt(createPurchaseSc.getPrchsDt());
		tStoreNotiEcReq.setUserKey(createPurchaseSc.getUseInsdUsermbrNo());
		tStoreNotiEcReq.setDeviceKey(createPurchaseSc.getUseInsdDeviceId());
		tStoreNotiEcReq.setPublishType(tstoreNotiPublishType);
		tStoreNotiEcReq.setType(PurchaseConstants.TSTORE_NOTI_TYPE_NORMALPAY);

		try {
			TStoreNotiEcRes tStoreNotiEcRes = this.tStoreNotiSCI.postTStoreNoti(tStoreNotiEcReq);
			this.logger.debug("PRCHS,ORDER,SAC,POST,TSTORENOTI,{},{}", tStoreNotiEcRes.getCode(),
					tStoreNotiEcRes.getMessage());
		} catch (Exception e) {
			// 예외 throw 차단
			this.logger.info("PRCHS,ORDER,SAC,POST,TSTORENOTI,ERROR,{}", e.getMessage());
		}

		// ------------------------------------------------------------------------------------
		// 전시Part 상품 구매건수 증가

		// 건수 증가 처리 변경으로 주석
		// Map<String, Integer> prchsCntMap = new HashMap<String, Integer>();
		// for (CreatePurchaseSc prchsInfo : createPurchaseScList) {
		// // 전시Part
		// if (prchsCntMap.containsKey(prchsInfo.getProdId())) {
		// prchsCntMap.put(prchsInfo.getProdId(), prchsCntMap.get(prchsInfo.getProdId()) + 1);
		// } else {
		// prchsCntMap.put(prchsInfo.getProdId(), 1);
		// }
		// }
		// String tenantId = createPurchaseSc.getTenantId();
		// List<UpdatePurchaseCountSacReq> updCntList = new ArrayList<UpdatePurchaseCountSacReq>();
		// UpdatePurchaseCountSacReq updCntReq = null;
		// for (String key : prchsCntMap.keySet()) {
		// updCntReq = new UpdatePurchaseCountSacReq();
		// updCntReq.setTenantId(tenantId);
		// updCntReq.setProductId(key);
		// updCntReq.setPurchaseCount(prchsCntMap.get(key));
		// updCntList.add(updCntReq);
		// }
		// this.updatePurchaseCountSCI.updatePurchaseCount(updCntList);

		this.logger.info("PRCHS,ORDER,SAC,POST,END");
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
		this.logger.info("PRCHS,ORDER,SAC,PAYPAGE,START");

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
		paymentPageParam.setMctSpareParam(purchaseOrderInfo.getTenantId());
		paymentPageParam.setMdn(purchaseOrderInfo.getPurchaseUser().getDeviceId());
		paymentPageParam.setNmDevice(purchaseOrderInfo.getDeviceModelCd());
		paymentPageParam.setImei(purchaseOrderInfo.getImei());
		paymentPageParam.setUacd(purchaseOrderInfo.getUacd());
		if (StringUtils.equals(purchaseOrderInfo.getNetworkTypeCd(), PurchaseConstants.NETWORK_TYPE_3G)) {
			paymentPageParam.setTypeNetwork("1"); // 3G, LTE
		} else if (StringUtils.equals(purchaseOrderInfo.getNetworkTypeCd(), PurchaseConstants.NETWORK_TYPE_WIFI)) {
			paymentPageParam.setTypeNetwork("2"); // WIFI
		} else {
			paymentPageParam.setTypeNetwork("3");
		}
		if (StringUtils.equals(purchaseOrderInfo.getPurchaseUser().getTelecom(), PurchaseConstants.TELECOM_SKT)) {
			paymentPageParam.setCarrier("1"); // SKT
		} else if (StringUtils
				.equals(purchaseOrderInfo.getPurchaseUser().getTelecom(), PurchaseConstants.TELECOM_UPLUS)) {
			paymentPageParam.setCarrier("2"); // LGT
		} else if (StringUtils.equals(purchaseOrderInfo.getPurchaseUser().getTelecom(), PurchaseConstants.TELECOM_KT)) {
			paymentPageParam.setCarrier("3"); // KT
		} else {
			paymentPageParam.setCarrier("4"); // UKNOWN
		}
		paymentPageParam.setNoSim(purchaseOrderInfo.getSimNo());
		paymentPageParam.setFlgSim(purchaseOrderInfo.getSimYn());

		// 암호화
		String eData = paymentPageParam.makeEncDataFormat();
		this.logger.info("PRCHS,ORDER,SAC,PAYPAGE,EDATA,SRC,{}", eData);
		try {
			paymentPageParam.setEData(Seed128Util.encrypt(eData, this.payplanetEncryptKey));
		} catch (Exception e) {
			throw new StorePlatformException("SAC_PUR_7201", e);
		}
		this.logger.info("PRCHS,ORDER,SAC,PAYPAGE,EDATA,ENC,{}", paymentPageParam.getEData());

		// Token
		String token = paymentPageParam.makeTokenFormat();
		this.logger.info("PRCHS,ORDER,SAC,PAYPAGE,TOKEN,SRC,{}", token);
		try {
			paymentPageParam.setToken(MD5Util.digestInHexFormat(token));
		} catch (Exception e) {
			throw new StorePlatformException("SAC_PUR_7201", e);
		}
		this.logger.info("PRCHS,ORDER,SAC,PAYPAGE,TOKEN,ENC,{}", paymentPageParam.getToken());

		// 버전
		paymentPageParam.setVersion("1.0");

		// 결제Page 요청 URL
		purchaseOrderInfo.setPaymentPageUrl(this.payplanetPaymentPageUrl);

		purchaseOrderInfo.setPaymentPageParam(paymentPageParam);

		this.logger.info("PRCHS,ORDER,SAC,PAYPAGE,END");
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
		// sbPrchsId.append("TAK").append("1")
		// .append(DateFormatUtils.format(Calendar.getInstance().getTimeInMillis(), "yyMMddHHmmss"))
		// .append(prchsIdSeq);
		return sbPrchsId.toString();
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
	 * <pre> 구매생성을 위한 데이터 목록 생성. </pre>
	 * 
	 * @param purchaseOrderInfo 구매요청 VO
	 * 
	 * @return 구매생성을 위한 데이터 목록
	 */
	private List<CreatePurchaseSc> makeCreatePurchaseScListForRequest(PurchaseOrderInfo purchaseOrderInfo) {
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
					.append(purchaseOrderInfo.getCurrencyCd()).append("&specialCouponId=")
					.append(StringUtils.defaultString(purchaseOrderInfo.getSpecialCouponId()))
					.append("&specialCouponAmt=").append(purchaseOrderInfo.getSpecialCouponAmt());
			if (StringUtils.equals(purchaseOrderInfo.getPrchsCaseCd(), PurchaseConstants.PRCHS_CASE_GIFT_CD)) {
				sbReserveData.append("&receiveNames=").append(StringUtils.defaultString(useUser.getUserName()))
						.append("&receiveMdns=").append(useUser.getDeviceId()); // 선물수신자 성명, 선물수신자 MDN
			}
		}

		int commonReserveDataLen = sbReserveData.length();

		// 구매생성 요청 데이터 세팅
		List<CreatePurchaseSc> createPurchaseList = new ArrayList<CreatePurchaseSc>();
		CreatePurchaseSc createPurchase = null;

		int prchsDtlCnt = 1, i = 0;
		for (PurchaseProduct product : purchaseOrderInfo.getPurchaseProductList()) {
			for (i = 0; i < product.getProdQty(); i++) {
				createPurchase = new CreatePurchaseSc();

				createPurchase.setPrchsDtlId(prchsDtlCnt++);

				createPurchase.setTenantId(purchaseOrderInfo.getTenantId());
				createPurchase.setPrchsId(purchaseOrderInfo.getPrchsId());
				createPurchase.setPrchsDt(purchaseOrderInfo.getPrchsDt());
				if (StringUtils.equals(purchaseOrderInfo.getPrchsCaseCd(), PurchaseConstants.PRCHS_CASE_GIFT_CD)) {
					createPurchase.setSendInsdUsermbrNo(purchaseOrderInfo.getUserKey());
					createPurchase.setSendInsdDeviceId(purchaseOrderInfo.getDeviceKey());
					createPurchase.setUseTenantId(purchaseOrderInfo.getTenantId());

					if (CollectionUtils.isNotEmpty(purchaseOrderInfo.getReceiveUserList())) {
						List<CreatePurchaseUserInfo> receiverList = new ArrayList<CreatePurchaseUserInfo>();
						CreatePurchaseUserInfo receiver = null;
						for (PurchaseUserDevice receiveUser : purchaseOrderInfo.getReceiveUserList()) {
							receiver = new CreatePurchaseUserInfo();
							receiver.setUserKey(receiveUser.getUserKey());
							receiver.setDeviceKey(receiveUser.getDeviceKey());
							receiverList.add(receiver);
						}
						createPurchase.setReceiverList(receiverList);
					} else {
						createPurchase.setUseInsdUsermbrNo(purchaseOrderInfo.getRecvUserKey());
						createPurchase.setUseInsdDeviceId(purchaseOrderInfo.getRecvDeviceKey());
					}
				} else {
					createPurchase.setUseTenantId(purchaseOrderInfo.getTenantId());
					createPurchase.setUseInsdUsermbrNo(purchaseOrderInfo.getUserKey());
					createPurchase.setUseInsdDeviceId(purchaseOrderInfo.getDeviceKey());
				}
				createPurchase.setTotAmt(purchaseOrderInfo.getRealTotAmt());
				createPurchase.setPrchsReqPathCd(purchaseOrderInfo.getPrchsReqPathCd());
				createPurchase.setClientIp(purchaseOrderInfo.getClientIp());
				createPurchase.setUseHidingYn(PurchaseConstants.USE_N);
				createPurchase.setSendHidingYn(PurchaseConstants.USE_N); // NotNull 로 되어 있어서 일단 세팅 ;;
				createPurchase.setRegId(purchaseOrderInfo.getSystemId());
				createPurchase.setUpdId(purchaseOrderInfo.getSystemId());
				createPurchase.setPrchsCaseCd(purchaseOrderInfo.getPrchsCaseCd());
				createPurchase.setTenantProdGrpCd(purchaseOrderInfo.getTenantProdGrpCd());
				createPurchase.setCurrencyCd(purchaseOrderInfo.getCurrencyCd()); // PRCHS
				createPurchase.setNetworkTypeCd(purchaseOrderInfo.getNetworkTypeCd()); // PRCHS

				if (purchaseOrderInfo.getTenantProdGrpCd().endsWith(
						PurchaseConstants.TENANT_PRODUCT_GROUP_SUFFIX_FIXRATE)) {
					createPurchase.setPrchsProdType(PurchaseConstants.PRCHS_PROD_TYPE_AUTH); // 권한 상품
				} else {
					createPurchase.setPrchsProdType(PurchaseConstants.PRCHS_PROD_TYPE_UNIT); // 단위 상품
				}
				createPurchase.setProdId(product.getProdId());
				createPurchase.setProdAmt(product.getProdAmt());
				createPurchase.setProdQty(product.getProdQty());
				createPurchase.setResvCol01(product.getResvCol01());
				createPurchase.setResvCol02(product.getResvCol02());
				createPurchase.setResvCol03(product.getResvCol03());
				createPurchase.setResvCol04(product.getResvCol04());
				createPurchase.setResvCol05(product.getResvCol05());
				createPurchase.setUsePeriodUnitCd(product.getUsePeriodUnitCd());
				createPurchase.setUsePeriod(product.getUsePeriod());
				// 비과금 구매요청 시, 이용종료일시 세팅
				if (purchaseOrderInfo.isFreeChargeReq() && StringUtils.isNotBlank(product.getUseExprDt())) {
					createPurchase
							.setUseExprDt(product.getUseExprDt().length() == 14 ? product.getUseExprDt() : product
									.getUseExprDt() + "235959");
					createPurchase.setDwldExprDt(createPurchase.getUseExprDt());
				}
				createPurchase.setUseFixrateProdId(product.getUseFixrateProdId());
				createPurchase.setDrmYn(product.getDrmYn());
				createPurchase.setAlarmYn(PurchaseConstants.USE_Y);
				createPurchase.setCurrencyCd(purchaseOrderInfo.getCurrencyCd());

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
							.append(StringUtils.defaultString(product.getAutoPrchsYN()));
					createPurchase.setPrchsResvDesc(sbReserveData.toString());
				}

				createPurchaseList.add(createPurchase);
			}
		}

		return createPurchaseList;
	}

	/*
	 * <pre> 구매이력 생성. </pre>
	 * 
	 * @param purchaseOrderInfo 구매요청 정보 VO
	 * 
	 * @param type 구매이력 생성 타입: 1-구매완료, 2-구매예약
	 * 
	 * @return 생성된 구매이력 건수
	 */
	private int createPurchaseByType(PurchaseOrderInfo purchaseOrderInfo, int type) {
		this.logger.info("PRCHS,ORDER,SAC,CREATE,START,{},{}", type);

		// 구매ID 생성
		if (StringUtils.isBlank(purchaseOrderInfo.getPrchsId())) {
			purchaseOrderInfo.setPrchsId(this.makePrchsId());
		}

		// 구매시간 세팅
		purchaseOrderInfo
				.setPrchsDt(DateFormatUtils.format(Calendar.getInstance().getTimeInMillis(), "yyyyMMddHHmmss"));

		// 구매생성 요청 데이터 생성
		List<CreatePurchaseSc> createPurchaseList = this.makeCreatePurchaseScListForRequest(purchaseOrderInfo);

		// 구매생성 요청 (default: 는 진입 가능성 zero)
		CreatePurchaseScRes res = null;
		switch (type) {
		case PurchaseConstants.CREATE_PURCHASE_TYPE_COMPLETED: // 구매완료
			res = this.purchaseOrderSCI.createCompletedPurchase(new CreatePurchaseScReq(createPurchaseList));
			break;
		case PurchaseConstants.CREATE_PURCHASE_TYPE_RESERVED: // 구매예약
			res = this.purchaseOrderSCI.createReservedPurchase(new CreatePurchaseScReq(createPurchaseList));
			break;
		}

		this.logger.info("PRCHS,ORDER,SAC,CREATE,END,{},{}", type, res.getCount());

		if (CollectionUtils.isNotEmpty(purchaseOrderInfo.getReceiveUserList())) {
			if (res.getCount() < 1 || res.getCount() != purchaseOrderInfo.getReceiveUserList().size()) {
				throw new StorePlatformException("SAC_PUR_7201");
			}
		} else {
			if (res.getCount() < 1 || res.getCount() != createPurchaseList.size()) {
				throw new StorePlatformException("SAC_PUR_7201");
			}
		}

		return res.getCount();
	}

	/*
	 * 
	 * <pre> 결제 내역 생성. </pre>
	 * 
	 * @param createPaymentSacInfo 결제이력 생성 정보
	 * 
	 * @return 생성된 결제정보 건수
	 */
	private int createPayment(CreatePaymentSacInfo createPaymentSacInfo) {
		this.logger.info("PRCHS,ORDER,SAC,CREATE_PAY,START,{}", createPaymentSacInfo);

		String tenantId = createPaymentSacInfo.getTenantId();
		String systemId = createPaymentSacInfo.getSystemId();
		String prchsId = createPaymentSacInfo.getPrchsId();
		String payUserKey = createPaymentSacInfo.getPayUserKey();
		String payDeviceKey = createPaymentSacInfo.getPayDeviceKey();
		String prchsDt = createPaymentSacInfo.getPrchsDt();
		String statusCd = createPaymentSacInfo.getStatusCd();
		Double totAmt = createPaymentSacInfo.getTotAmt();

		List<Payment> paymentList = new ArrayList<Payment>();
		Payment payment = null;
		int dtlIdCnt = 1;

		for (PaymentInfo paymentInfo : createPaymentSacInfo.getPaymentInfoList()) {
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

		// 결제 내역 생성 요청
		CreatePaymentScReq reqPayment = new CreatePaymentScReq();
		reqPayment.setPaymentList(paymentList);

		CreatePaymentScRes res = this.purchaseOrderSCI.createPayment(reqPayment);
		this.logger.info("PRCHS,ORDER,SAC,CREATE_PAY,END,{},{}", createPaymentSacInfo.getPrchsId(), res.getCount());
		return res.getCount();
	}

	/*
	 * 
	 * <pre> 자동구매 생성. </pre>
	 * 
	 * @param createPurchaseSc 구매생성 정보
	 * 
	 * @return 생성된 건수
	 */
	private int createAutoPurchase(CreatePurchaseSc createPurchaseSc) {
		this.logger.info("PRCHS,ORDER,SAC,CREATE_AUTO,START,{}", createPurchaseSc.getPrchsId());
		List<AutoPrchs> autoPrchsList = new ArrayList<AutoPrchs>();

		AutoPrchs autoPrchs = new AutoPrchs();
		autoPrchs.setTenantId(createPurchaseSc.getTenantId());
		autoPrchs.setFstPrchsId(createPurchaseSc.getPrchsId());
		autoPrchs.setFstPrchsDtlId(1);
		autoPrchs.setInsdUsermbrNo(createPurchaseSc.getInsdUsermbrNo());
		autoPrchs.setInsdDeviceId(createPurchaseSc.getInsdDeviceId());
		autoPrchs.setProdId(createPurchaseSc.getProdId());
		autoPrchs.setPaymentStartDt(createPurchaseSc.getPrchsDt());
		autoPrchs.setPaymentEndDt("99991231235959"); // TAKTODO
		autoPrchs.setAfterPaymentDt(createPurchaseSc.getUseExprDt().substring(0, 8) + "000000"); // TAKTODO
		autoPrchs.setReqPathCd(createPurchaseSc.getPrchsReqPathCd());
		autoPrchs.setClientIp(createPurchaseSc.getClientIp());
		autoPrchs.setPrchsTme(0);
		autoPrchs.setLastPrchsId(createPurchaseSc.getPrchsId());
		autoPrchs.setLastPrchsDtlId(1);
		autoPrchs.setRegId(createPurchaseSc.getSystemId());
		autoPrchs.setUpdId(createPurchaseSc.getSystemId());
		autoPrchs.setResvCol01(createPurchaseSc.getResvCol01());
		autoPrchs.setResvCol02(createPurchaseSc.getResvCol02());
		autoPrchs.setResvCol03(createPurchaseSc.getResvCol03());
		autoPrchs.setResvCol04(createPurchaseSc.getResvCol04());
		autoPrchs.setResvCol05(createPurchaseSc.getResvCol05());
		autoPrchs.setAutoPaymentStatusCd(PurchaseConstants.AUTO_PRCHS_STATUS_AUTO);
		autoPrchsList.add(autoPrchs);

		CreateAutoPurchaseScReq autoReq = new CreateAutoPurchaseScReq();
		autoReq.setAutoPrchsList(autoPrchsList);

		CreateAutoPurchaseScRes autoRes = this.purchaseOrderSCI.createNewAutoPurchase(autoReq);
		this.logger.info("PRCHS,ORDER,SAC,CREATE_AUTO,END,{}", createPurchaseSc.getPrchsId(), autoRes.getCount());
		return autoRes.getCount();
	}
}
