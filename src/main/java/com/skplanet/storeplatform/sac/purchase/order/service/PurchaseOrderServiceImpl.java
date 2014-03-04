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

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.external.client.shopping.sci.ShoppingSCI;
import com.skplanet.storeplatform.external.client.shopping.vo.CouponPublishEcReq;
import com.skplanet.storeplatform.external.client.shopping.vo.CouponPublishEcRes;
import com.skplanet.storeplatform.external.client.shopping.vo.CouponPublishItemDetailEcRes;
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
import com.skplanet.storeplatform.purchase.client.order.vo.SearchReservedPurchaseListScReq;
import com.skplanet.storeplatform.purchase.client.order.vo.SearchReservedPurchaseListScRes;
import com.skplanet.storeplatform.purchase.client.order.vo.ShoppingCouponPublishInfo;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.sci.UpdatePurchaseCountSCI;
import com.skplanet.storeplatform.sac.client.purchase.vo.order.NotifyPaymentSacReq;
import com.skplanet.storeplatform.sac.client.purchase.vo.order.PaymentInfo;
import com.skplanet.storeplatform.sac.client.purchase.vo.order.VerifyOrderSacRes;
import com.skplanet.storeplatform.sac.purchase.constant.PurchaseConstants;
import com.skplanet.storeplatform.sac.purchase.interworking.service.InterworkingSacService;
import com.skplanet.storeplatform.sac.purchase.order.Seed128Util;
import com.skplanet.storeplatform.sac.purchase.order.dummy.vo.DummyMember;
import com.skplanet.storeplatform.sac.purchase.order.dummy.vo.DummyProduct;
import com.skplanet.storeplatform.sac.purchase.order.vo.CreatePaymentSacInfo;
import com.skplanet.storeplatform.sac.purchase.order.vo.PaymentPageParam;
import com.skplanet.storeplatform.sac.purchase.order.vo.PurchaseOrderInfo;
import com.skplanet.storeplatform.sac.purchase.order.vo.PurchaseUserDevice;
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

	private static final int CREATE_PURCHASE_TYPE_COMPLETED = 1;
	private static final int CREATE_PURCHASE_TYPE_RESERVED = 2;

	public static final String DUMMY_PP_PAYMENT_PAGE_URL = "http://121.165.99.39:8080/payplanet/paymentPage";
	public static final String DUMMY_SAC_PUR_PAYMENT_NOTIFY_URL = "http://121.165.99.39:8010/sp_sac/purchase/order/notifyPayment/v1";

	@Autowired
	private PurchaseOrderSCI purchaseOrderSCI;
	@Autowired
	private PurchaseOrderSearchSCI purchaseOrderSearchSCI;
	@Autowired
	private ShoppingSCI shoppingSCI;
	@Autowired
	private UpdatePurchaseCountSCI updatePurchaseCountSCI;
	@Autowired
	private InterworkingSacService interworkingSacService;

	/**
	 * 
	 * <pre>
	 * 무료구매 처리.
	 * </pre>
	 * 
	 * @param purchaseOrderInfo
	 *            구매요청 정보
	 */
	@Override
	public void createFreePurchase(PurchaseOrderInfo purchaseOrderInfo) {
		// 구매 내역 저장
		this.createPurchaseByType(purchaseOrderInfo, CREATE_PURCHASE_TYPE_COMPLETED);

		// 결제 내역 저장
		CreatePaymentSacInfo createPaymentSacInfo = new CreatePaymentSacInfo();
		createPaymentSacInfo.setTenantId(purchaseOrderInfo.getTenantId());
		createPaymentSacInfo.setSystemId(purchaseOrderInfo.getSystemId());
		createPaymentSacInfo.setPayUserKey(purchaseOrderInfo.getUserKey());
		createPaymentSacInfo.setPayDeviceKey(purchaseOrderInfo.getDeviceKey());
		createPaymentSacInfo.setPrchsId(purchaseOrderInfo.getPrchsId());
		createPaymentSacInfo.setPrchsDt(purchaseOrderInfo.getPrchsDt());
		createPaymentSacInfo.setStatusCd(PurchaseConstants.PRCHS_STATUS_COMPT);
		createPaymentSacInfo.setTotAmt(purchaseOrderInfo.getRealTotAmt());

		List<PaymentInfo> paymentInfoList = new ArrayList<PaymentInfo>();
		PaymentInfo paymentInfo = new PaymentInfo();
		paymentInfo.setTid(purchaseOrderInfo.getPrchsId());
		paymentInfo.setPaymentMtdCd("OR000697");
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
		this.createPurchaseByType(purchaseOrderInfo, CREATE_PURCHASE_TYPE_RESERVED);
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
			throw new StorePlatformException("SAC_PUR_7101");// TAKTODO:: PP측으로 응답 정리
		}
		CreatePurchaseSc createPurchaseSc = createPurchaseScList.get(0);

		// ------------------------------------------------------------------------------------------------
		// 구매인증 응답 데이터 처리

		// 예약 저장해둔 데이터 추출
		Map<String, String> reservedData = this.parseReservedData(createPurchaseSc.getResvCol05());

		VerifyOrderSacRes res = new VerifyOrderSacRes();
		res.setMdn(reservedData.get("deviceId")); // 결제 MDN
		res.setFlgMbrStatus("1"); // [fix] 회원상태: 1-정상
		res.setFlgProductStatus("1"); // [fix] 상품상태: 1-정상
		res.setBonusCashPoint(reservedData.get("bonusCashPoint")); // 보너스 캐쉬 지급 Point
		res.setBonusCashUsableDayCnt(reservedData.get("bonusCashUsableDayCnt")); // 보너스 캐쉬 유효기간(일)
		res.setAfterAutoPayDt(reservedData.get("afterAutoPayDt")); // 다음 자동 결제일
		res.setDwldAvailableDayCnt(reservedData.get("dwldAvailableDayCnt")); // 다운로드 가능기간(일)
		res.setUsePeriodCnt(reservedData.get("usePeriodCnt")); // 이용기간(일)
		res.setLoanPid(reservedData.get("loanPid")); // 대여하기 상품 ID
		res.setLoanAmt(reservedData.get("loanAmt")); // 대여하기 상품 금액
		res.setOwnPid(reservedData.get("ownPid")); // 소장하기 상품 ID
		res.setOwnAmt(reservedData.get("ownAmt")); // 소장하기 상품 금액
		res.setNmSeller(reservedData.get("sellerNm")); // 판매자명
		res.setEmailSeller(reservedData.get("sellerEmail")); // 판매자 이메일 주소
		res.setNoTelSeller(reservedData.get("sellerTelno")); // 판매자 전화번호
		res.setNmDelivery(reservedData.get("receiveNames")); // 선물수신자 성명
		res.setNoMdnDelivery(reservedData.get("receiveMdns")); // 선물수신자 MDN
		/* TAKTODO:: 조회/처리 필요 항목 */
		res.setFlgTeleBillingAgree("Y"); // [회원Part] 통신과금 동의여부 조회
		res.setFlgOcbUseAgree("Y"); // [회원Part] OCB 이용약관 동의여부 조회
		res.setCdMaxAmtRate(""); // 결제수단 별 가능 거래금액/비율 조정 정보
		res.setCdOcbSaveInfo(""); // OCB 적립코드
		res.setNoOcbCard(""); // [회원Part] OCB 카드번호
		res.setNoCouponList(""); // [Tenant] 쿠폰 List
		res.setTypeTestMdn("T03"); // 법인 및 일반 시험폰 처리 타입 (T01, T02, T03)
		/* END - 조회/처리 필요 항목 */

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
	public void executeConfirmPurchase(NotifyPaymentSacReq notifyPaymentReq) {
		this.logger.debug("PRCHS,ORDER,CONFIRM,START");

		// ------------------------------------------------------------------------------
		// 구매 예약 건 조회

		SearchReservedPurchaseListScReq reqSearch = new SearchReservedPurchaseListScReq();
		reqSearch.setTenantId("S01");
		reqSearch.setPrchsId(notifyPaymentReq.getPrchsId());

		SearchReservedPurchaseListScRes res = this.purchaseOrderSearchSCI.searchReservedPurchaseList(reqSearch);
		List<CreatePurchaseSc> createPurchaseScList = res.getCreatePurchaseScList();
		if (createPurchaseScList.size() < 1) {
			throw new StorePlatformException("SAC_PUR_7101"); // TAKTODO:: PP측으로 응답 정리
		}

		CreatePurchaseSc createPurchaseSc = createPurchaseScList.get(0);

		// ------------------------------------------------------------------------------
		// 금액 체크

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

		Map<String, String> reservedData = this.parseReservedData(createPurchaseSc.getResvCol05());

		// 공통 작업용 세팅
		for (CreatePurchaseSc createPurchaseInfo : createPurchaseScList) {
			createPurchaseInfo.setSystemId(reservedData.get("systemId"));
			createPurchaseInfo.setCurrencyCd(reservedData.get("currencyCd"));
			createPurchaseInfo.setNetworkTypeCd(reservedData.get("networkTypeCd"));

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
		// TAKTODO:: 보유자MDN/구매자MDN 조회, COUPONCODE/ITEMCODE 조회, 응답 처리

		List<ShoppingCouponPublishInfo> shoppingCouponList = null;

		if (createPurchaseSc.getTenantProdGrpCd().startsWith("OR006205")) {
			CouponPublishEcReq couponPublishEcReq = new CouponPublishEcReq();
			couponPublishEcReq.setPrchsId(createPurchaseSc.getPrchsId());
			couponPublishEcReq.setUseMdn(reservedData.get("useDeviceId"));
			couponPublishEcReq.setBuyMdn(reservedData.get("deviceId"));
			couponPublishEcReq.setCouponCode(reservedData.get("couponCode"));
			couponPublishEcReq.setItemCode(reservedData.get("itemCode"));
			couponPublishEcReq.setItemCount(createPurchaseSc.getProdQty());
			CouponPublishEcRes couponPublishEcRes = this.shoppingSCI.createCouponPublish(couponPublishEcReq);

			if (StringUtils.equals(couponPublishEcRes.getPublishType(), "0")) { // 0-즉시발급, 1-비동기발급
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
					shoppingCouponPublishInfo.setExtraData(couponInfo.getExtraData());

					shoppingCouponList.add(shoppingCouponPublishInfo);
				}
			}
		}

		// -------------------------------------------------------------------------------------------
		// 구매확정

		createPurchaseSc.setResvCol05(null); // 예약저장해둔 데이터 제거

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
		this.logger.debug("PRCHS,ORDER,CONFIRM,END,{}", resConfirm.getCount());
		if (resConfirm.getCount() < 1) {
			throw new StorePlatformException("SAC_PUR_7202");
		}

		// 결제 내역 저장
		CreatePaymentSacInfo createPaymentSacInfo = new CreatePaymentSacInfo();
		createPaymentSacInfo.setTenantId(createPurchaseSc.getTenantId());
		createPaymentSacInfo.setSystemId(createPurchaseSc.getSystemId());
		createPaymentSacInfo.setPayUserKey(createPurchaseSc.getInsdUsermbrNo());
		createPaymentSacInfo.setPayDeviceKey(createPurchaseSc.getInsdDeviceId());
		createPaymentSacInfo.setPrchsId(createPurchaseSc.getPrchsId());
		createPaymentSacInfo.setPrchsDt(createPurchaseSc.getPrchsDt());
		createPaymentSacInfo.setStatusCd(createPurchaseSc.getStatusCd());
		createPaymentSacInfo.setPaymentInfoList(notifyPaymentReq.getPaymentInfoList());
		int createCnt = this.createPayment(createPaymentSacInfo);
		if (createCnt != createPaymentSacInfo.getPaymentInfoList().size()) {
			throw new StorePlatformException("SAC_PUR_7203");
		}

		// 자동구매 신규이력 저장
		boolean bAuto = false;
		for (PaymentInfo paymentInfo : notifyPaymentReq.getPaymentInfoList()) {
			if (StringUtils.isNotBlank(paymentInfo.getBillKey())) {
				bAuto = true;
			}
		}
		if (bAuto) {
			createCnt = this.createAutoPurchase(createPurchaseSc);
			if (createCnt != 1) {
				throw new StorePlatformException("SAC_PUR_7204");
			}
		}

		// -------------------------------------------------------------------------------------------
		// 구매 후처리

		// 씨네21, 인터파크
		// TAKTODO:: 판매자 회원번호 조회, COMP_CONTS_ID, MALL_CD 조회

		// InterworkingSacReq interworkingSacReq = new InterworkingSacReq();
		// interworkingSacReq.setTenantId(createPurchaseSc.getTenantId());
		// interworkingSacReq.setSystemId(createPurchaseSc.getSystemId());
		// interworkingSacReq.setPrchsId(createPurchaseSc.getPrchsId());
		// interworkingSacReq.setUserKey(createPurchaseSc.getUseInsdUsermbrNo());
		// interworkingSacReq.setPrchsDt(createPurchaseSc.getPrchsDt());
		// interworkingSacReq.setProdId(createPurchaseSc.getProdId());
		// interworkingSacReq.setSellermbrNo("");
		// interworkingSacReq.setCompContentsId("");
		// TAKTODO:임시주석 this.interworkingSacService.createInterworking(interworkingSacReq);

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
		this.logger.debug("PRCHS,ORDER,SET_PAYPAGE,START,{}", purchaseOrderInfo);

		// eData(암호화 데이터)
		DummyProduct product = purchaseOrderInfo.getProductList().get(0);

		PaymentPageParam paymentPageParam = new PaymentPageParam();
		paymentPageParam.setMid(purchaseOrderInfo.getMid());
		paymentPageParam.setAuthKey(purchaseOrderInfo.getAuthKey());
		paymentPageParam.setOrderId(purchaseOrderInfo.getPrchsId());
		paymentPageParam.setMctTrDate(purchaseOrderInfo.getPrchsDt());
		paymentPageParam.setAmtPurchase(String.valueOf(purchaseOrderInfo.getRealTotAmt()));
		paymentPageParam.setPid(product.getProdId());
		paymentPageParam.setpName(product.getProdNm());
		paymentPageParam.setpDescription(product.getProdId()); // TAKTODO
		paymentPageParam.setpType(product.getProdId()); // TAKTODO
		paymentPageParam.setAid(product.getAid());
		paymentPageParam.setReturnFormat(PaymentPageParam.PP_RETURN_FORMAT_JSON);
		paymentPageParam.setReturnPath(purchaseOrderInfo.getReturnUrl());
		paymentPageParam.setResultPath(DUMMY_SAC_PUR_PAYMENT_NOTIFY_URL);
		paymentPageParam.setMdn(purchaseOrderInfo.getPurchaseMember().getDeviceId());
		paymentPageParam.setNmDevice(purchaseOrderInfo.getDeviceModelCd());
		paymentPageParam.setImei(purchaseOrderInfo.getImei());
		paymentPageParam.setUacd(purchaseOrderInfo.getUacd());
		paymentPageParam.setTypeNetwork(purchaseOrderInfo.getNetworkTypeCd().substring(
				purchaseOrderInfo.getNetworkTypeCd().length() - 1));
		paymentPageParam.setCarrier(purchaseOrderInfo.getPurchaseMember().getTelecom());
		paymentPageParam.setNoSim(purchaseOrderInfo.getSimNo());
		paymentPageParam.setFlgSim(purchaseOrderInfo.getSimYn());

		// 암호화
		String eData = paymentPageParam.makeEncDataFormat();
		try {
			eData = Seed128Util.encrypt(eData, "qnqnsdbfyghk0001");
		} catch (Exception e) {
			throw new StorePlatformException("SAC_PUR_7201");
		}
		paymentPageParam.seteData(eData);

		// Token
		String token = paymentPageParam.makeTokenFormat();
		try {
			// token = Base64.encodeBase64String(MessageDigest.getInstance("MD5").digest(token.getBytes()));
			token = new String(MessageDigest.getInstance("MD5").digest(token.getBytes()));
		} catch (NoSuchAlgorithmException e) {
			throw new StorePlatformException("SAC_PUR_7201");
		}
		paymentPageParam.setToken(token);

		// 버전
		paymentPageParam.setVersion("1.0");

		// 결제Page 요청 URL
		purchaseOrderInfo.setPaymentPageUrl(DUMMY_PP_PAYMENT_PAGE_URL);

		purchaseOrderInfo.setPaymentPageParam(paymentPageParam);

		this.logger.debug("PRCHS,ORDER,SET_PAYPAGE,END,{}", purchaseOrderInfo);
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
		// sbPrchsId.append("01").append("01").append(DateFormatUtils.format(Calendar.getInstance().getTimeInMillis(),
		// "yyMMddHHmmss")).append(prchsIdSeq);
		sbPrchsId.append("TAK").append("1")
				.append(DateFormatUtils.format(Calendar.getInstance().getTimeInMillis(), "yyMMddHHmmss"))
				.append(prchsIdSeq);
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
				this.logger.debug("[" + param + "]");
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
		// deviceId(결제자), useUserKey, useDeviceKey, useDeviceModelCd, useTelecom,
		PurchaseUserDevice useUser = null;
		if (StringUtils.equals(purchaseOrderInfo.getPrchsCaseCd(), PurchaseConstants.PRCHS_CASE_GIFT_CD)) {
			useUser = purchaseOrderInfo.getReceiveUser();
		} else {
			useUser = purchaseOrderInfo.getPurchaseUser();
		}
		StringBuffer sbReserveData = new StringBuffer(1024);
		sbReserveData.append("tenantId=").append(useUser.getTenantId()).append("&systemId=")
				.append(purchaseOrderInfo.getSystemId()).append("&deviceId=")
				.append(purchaseOrderInfo.getPurchaseUser().getDeviceId()).append("&useDeviceId=")
				.append(useUser.getDeviceId()).append("&useUserKey=").append(useUser.getUserKey())
				.append("&useDeviceKey=").append(useUser.getDeviceKey()).append("&useDeviceModelCd=")
				.append(useUser.getDeviceModelCd()).append("&useTelecom=").append(useUser.getTelecom())
				.append("&networkTypeCd=").append(purchaseOrderInfo.getNetworkTypeCd()).append("&currencyCd=")
				.append(purchaseOrderInfo.getCurrencyCd());
		// receiveNames; // 선물수신자 성명
		// receiveMdns; // 선물수신자 MDN

		int commonReserveDataLen = sbReserveData.length();

		// 구매생성 요청 데이터 세팅
		List<CreatePurchaseSc> createPurchaseList = new ArrayList<CreatePurchaseSc>();
		CreatePurchaseSc createPurchase = null;

		int prchsDtlCnt = 1, i = 0;
		for (DummyProduct product : purchaseOrderInfo.getProductList()) {
			for (i = 0; i < product.getProdQty(); i++) {
				createPurchase = new CreatePurchaseSc();

				createPurchase.setPrchsDtlId(prchsDtlCnt++);

				createPurchase.setTenantId(purchaseOrderInfo.getTenantId());
				createPurchase.setPrchsId(purchaseOrderInfo.getPrchsId());
				createPurchase.setPrchsDt(purchaseOrderInfo.getPrchsDt());
				if (StringUtils.equals(purchaseOrderInfo.getPrchsCaseCd(), PurchaseConstants.PRCHS_CASE_GIFT_CD)) {
					createPurchase.setUseTenantId(purchaseOrderInfo.getRecvTenantId());
					createPurchase.setUseInsdUsermbrNo(purchaseOrderInfo.getRecvUserKey());
					createPurchase.setUseInsdDeviceId(purchaseOrderInfo.getRecvDeviceKey());
					createPurchase.setSendInsdUsermbrNo(purchaseOrderInfo.getUserKey());
					createPurchase.setSendInsdDeviceId(purchaseOrderInfo.getDeviceKey());
				} else {
					createPurchase.setUseTenantId(purchaseOrderInfo.getTenantId());
					createPurchase.setUseInsdUsermbrNo(purchaseOrderInfo.getUserKey());
					createPurchase.setUseInsdDeviceId(purchaseOrderInfo.getDeviceKey());
				}
				createPurchase.setTotAmt(purchaseOrderInfo.getRealTotAmt());
				createPurchase.setPrchsReqPathCd(purchaseOrderInfo.getPrchsReqPathCd());
				createPurchase.setClientIp(purchaseOrderInfo.getClientIp());
				createPurchase.setHidingYn(PurchaseConstants.USE_N);
				createPurchase.setRegId(purchaseOrderInfo.getSystemId());
				createPurchase.setUpdId(purchaseOrderInfo.getSystemId());
				createPurchase.setPrchsCaseCd(purchaseOrderInfo.getPrchsCaseCd());
				createPurchase.setTenantProdGrpCd(purchaseOrderInfo.getTenantProdGrpCd());
				createPurchase.setCurrencyCd(purchaseOrderInfo.getCurrencyCd()); // PRCHS
				createPurchase.setNetworkTypeCd(purchaseOrderInfo.getNetworkTypeCd()); // PRCHS

				if (product.isbFlat()) { // TAKTODO:: 권한상품 판단
					createPurchase.setPrchsProdType(PurchaseConstants.PRCHS_PROD_TYPE_FIX);
				} else {
					createPurchase.setPrchsProdType(PurchaseConstants.PRCHS_PROD_TYPE_OWN);
				}
				createPurchase.setProdId(product.getProdId());
				createPurchase.setProdAmt(product.getProdAmt());
				createPurchase.setProdQty(product.getProdQty());
				createPurchase.setRePrchsPmtYn(product.getbDupleProd() ? "Y" : "N");
				createPurchase.setResvCol01(product.getResvCol01());
				createPurchase.setResvCol02(product.getResvCol02());
				createPurchase.setResvCol03(product.getResvCol03());
				createPurchase.setResvCol04(product.getResvCol04());
				createPurchase.setUsePeriodUnitCd(product.getUsePeriodUnitCd());
				createPurchase.setUsePeriod(product.getUsePeriod());

				// 구매예약 시 저장할 데이터 (상품별)
				sbReserveData.setLength(commonReserveDataLen);
				sbReserveData.append("&aid=").append(product.getAid()).append("&couponCode=")
						.append(product.getCouponCode()).append("&itemCode=").append(product.getItemCode())
						.append("&bonusCashPoint=").append("").append("&bonusCashUsableDayCnt=").append("")
						.append("&afterAutoPayDt=").append("").append("&dwldAvailableDayCnt=").append("")
						.append("&usePeriodCnt=").append("").append("&loanPid=").append("").append("&loanAmt=")
						.append("").append("&ownPid=").append("").append("&ownAmt=").append("").append("&sellerNm=")
						.append("").append("&sellerEmail=").append("").append("&sellerTelno=").append("")
						.append("&sellerMbrNo=").append("").append("&mallCd=").append("").append("&outsdContentsId=")
						.append("");
				createPurchase.setResvCol05(sbReserveData.toString());

				createPurchaseList.add(createPurchase);
			}
		}

		return createPurchaseList;
	}

	/*
	 * <pre> [DUMMY] 구매생성을 위한 데이터 목록 생성. </pre>
	 * 
	 * @param purchaseOrderInfo 구매요청 VO
	 * 
	 * @return 구매생성을 위한 데이터 목록
	 */
	private List<CreatePurchaseSc> makeCreatePurchaseScListForRequestDummy(PurchaseOrderInfo purchaseOrderInfo) {
		// 구매예약 시 저장할 데이터 (공통)
		// tenantId, systemId, networkTypeCd, currencyCd
		// deviceId(결제자), useUserKey, useDeviceKey, useDeviceModelCd, useTelecom,
		DummyMember useUser = null;
		if (StringUtils.equals(purchaseOrderInfo.getPrchsCaseCd(), PurchaseConstants.PRCHS_CASE_GIFT_CD)) {
			useUser = purchaseOrderInfo.getRecvMember();
		} else {
			useUser = purchaseOrderInfo.getPurchaseMember();
		}
		StringBuffer sbReserveData = new StringBuffer(1024);
		sbReserveData.append("tenantId=").append(useUser.getTenantId()).append("&systemId=")
				.append(purchaseOrderInfo.getSystemId()).append("&deviceId=")
				.append(purchaseOrderInfo.getPurchaseMember().getDeviceId()).append("&useDeviceId=")
				.append(useUser.getDeviceId()).append("&useUserKey=").append(useUser.getUserKey())
				.append("&useDeviceKey=").append(useUser.getDeviceKey()).append("&useDeviceModelCd=")
				.append(useUser.getDeviceModelCd()).append("&useTelecom=").append(useUser.getTelecom())
				.append("&networkTypeCd=").append(purchaseOrderInfo.getNetworkTypeCd()).append("&currencyCd=")
				.append(purchaseOrderInfo.getCurrencyCd());
		// receiveNames; // 선물수신자 성명
		// receiveMdns; // 선물수신자 MDN

		int commonReserveDataLen = sbReserveData.length();

		// 구매생성 요청 데이터 세팅
		List<CreatePurchaseSc> createPurchaseList = new ArrayList<CreatePurchaseSc>();
		CreatePurchaseSc createPurchase = null;

		int prchsDtlCnt = 1, i = 0;
		for (DummyProduct product : purchaseOrderInfo.getProductList()) {
			for (i = 0; i < product.getProdQty(); i++) {
				createPurchase = new CreatePurchaseSc();

				createPurchase.setPrchsDtlId(prchsDtlCnt++);

				createPurchase.setTenantId(purchaseOrderInfo.getTenantId());
				createPurchase.setPrchsId(purchaseOrderInfo.getPrchsId());
				createPurchase.setPrchsDt(purchaseOrderInfo.getPrchsDt());
				if (StringUtils.equals(purchaseOrderInfo.getPrchsCaseCd(), PurchaseConstants.PRCHS_CASE_GIFT_CD)) {
					createPurchase.setUseTenantId(purchaseOrderInfo.getRecvTenantId());
					createPurchase.setUseInsdUsermbrNo(purchaseOrderInfo.getRecvUserKey());
					createPurchase.setUseInsdDeviceId(purchaseOrderInfo.getRecvDeviceKey());
					createPurchase.setSendInsdUsermbrNo(purchaseOrderInfo.getUserKey());
					createPurchase.setSendInsdDeviceId(purchaseOrderInfo.getDeviceKey());
				} else {
					createPurchase.setUseTenantId(purchaseOrderInfo.getTenantId());
					createPurchase.setUseInsdUsermbrNo(purchaseOrderInfo.getUserKey());
					createPurchase.setUseInsdDeviceId(purchaseOrderInfo.getDeviceKey());
				}
				createPurchase.setTotAmt(purchaseOrderInfo.getRealTotAmt());
				createPurchase.setPrchsReqPathCd(purchaseOrderInfo.getPrchsReqPathCd());
				createPurchase.setClientIp(purchaseOrderInfo.getClientIp());
				createPurchase.setHidingYn(PurchaseConstants.USE_N);
				createPurchase.setRegId(purchaseOrderInfo.getSystemId());
				createPurchase.setUpdId(purchaseOrderInfo.getSystemId());
				createPurchase.setPrchsCaseCd(purchaseOrderInfo.getPrchsCaseCd());
				createPurchase.setTenantProdGrpCd(purchaseOrderInfo.getTenantProdGrpCd());
				createPurchase.setCurrencyCd(purchaseOrderInfo.getCurrencyCd()); // PRCHS
				createPurchase.setNetworkTypeCd(purchaseOrderInfo.getNetworkTypeCd()); // PRCHS

				if (product.isbFlat()) { // TAKTODO:: 권한상품 판단
					createPurchase.setPrchsProdType(PurchaseConstants.PRCHS_PROD_TYPE_FIX);
				} else {
					createPurchase.setPrchsProdType(PurchaseConstants.PRCHS_PROD_TYPE_OWN);
				}
				createPurchase.setProdId(product.getProdId());
				createPurchase.setProdAmt(product.getProdAmt());
				createPurchase.setProdQty(product.getProdQty());
				createPurchase.setRePrchsPmtYn(product.getbDupleProd() ? "Y" : "N");
				createPurchase.setResvCol01(product.getResvCol01());
				createPurchase.setResvCol02(product.getResvCol02());
				createPurchase.setResvCol03(product.getResvCol03());
				createPurchase.setResvCol04(product.getResvCol04());
				createPurchase.setUsePeriodUnitCd(product.getUsePeriodUnitCd());
				createPurchase.setUsePeriod(product.getUsePeriod());
				// 비과금 구매요청 시, 이용종료일시 세팅
				if (purchaseOrderInfo.isFreeChargeReq() && StringUtils.isNotBlank(product.getUseExprDt())) {
					if (product.getUseExprDt().length() == 14) {
						createPurchase.setUseExprDt(product.getUseExprDt());
						createPurchase.setDwldExprDt(product.getUseExprDt());
					} else if (product.getUseExprDt().length() == 8) {
						createPurchase.setUseExprDt(product.getUseExprDt() + "235959");
						createPurchase.setDwldExprDt(product.getUseExprDt() + "235959");
					}
				}

				// 구매예약 시 저장할 데이터 (상품별)
				if (purchaseOrderInfo.getRealTotAmt() > 0.0) {
					sbReserveData.setLength(commonReserveDataLen);
					sbReserveData.append("&aid=").append(product.getAid()).append("&couponCode=")
							.append(product.getCouponCode()).append("&itemCode=").append(product.getItemCode())
							.append("&bonusCashPoint=").append("").append("&bonusCashUsableDayCnt=").append("")
							.append("&afterAutoPayDt=").append("").append("&dwldAvailableDayCnt=").append("")
							.append("&usePeriodCnt=").append("").append("&loanPid=").append("").append("&loanAmt=")
							.append("").append("&ownPid=").append("").append("&ownAmt=").append("")
							.append("&sellerNm=").append("").append("&sellerEmail=").append("").append("&sellerTelno=")
							.append("").append("&sellerMbrNo=").append("").append("&mallCd=").append("")
							.append("&outsdContentsId=").append("");
					createPurchase.setResvCol05(sbReserveData.toString());
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
	 */
	private void createPurchaseByType(PurchaseOrderInfo purchaseOrderInfo, int type) {
		this.logger.debug("PRCHS,ORDER,CREATE,START,{},{}", type, purchaseOrderInfo);

		// 구매ID 생성
		if (StringUtils.isBlank(purchaseOrderInfo.getPrchsId())) {
			purchaseOrderInfo.setPrchsId(this.makePrchsId());
		}

		// 구매시간 세팅
		purchaseOrderInfo
				.setPrchsDt(DateFormatUtils.format(Calendar.getInstance().getTimeInMillis(), "yyyyMMddHHmmss"));

		// 구매생성 요청 데이터 생성
		List<CreatePurchaseSc> createPurchaseList = this.makeCreatePurchaseScListForRequestDummy(purchaseOrderInfo);

		// 구매생성 요청 (default: 는 진입 가능성 zero)
		CreatePurchaseScRes res = null;
		switch (type) {
		case CREATE_PURCHASE_TYPE_COMPLETED: // 구매완료
			res = this.purchaseOrderSCI.createCompletedPurchase(new CreatePurchaseScReq(createPurchaseList));
			break;
		case CREATE_PURCHASE_TYPE_RESERVED: // 구매예약
			res = this.purchaseOrderSCI.createReservedPurchase(new CreatePurchaseScReq(createPurchaseList));
			break;
		}

		this.logger.debug("PRCHS,ORDER,CREATE,END,{},{}", type, res.getCount());
		if (res.getCount() < 1 || res.getCount() != createPurchaseList.size()) {
			throw new StorePlatformException("SAC_PUR_7201");
		}
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
		this.logger.debug("PRCHS,ORDER,CREATE_PAY,START,{}", createPaymentSacInfo);

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

			payment.setPaymentMtdCd(paymentInfo.getPaymentMtdCd());
			payment.setPaymentAmt(paymentInfo.getPaymentAmt());
			payment.setPaymentDt(paymentInfo.getPaymentDt());
			payment.setPaymentStatusCd(statusCd);

			payment.setTid(paymentInfo.getTid());

			payment.setRegId(systemId);
			payment.setUpdId(systemId);

			paymentList.add(payment);
		}

		// 결제 내역 생성 요청
		CreatePaymentScReq reqPayment = new CreatePaymentScReq();
		reqPayment.setPaymentList(paymentList);

		CreatePaymentScRes res = this.purchaseOrderSCI.createPayment(reqPayment);
		this.logger.debug("PRCHS,ORDER,CREATE_PAY,END,{},{}", createPaymentSacInfo.getPrchsId(), res.getCount());
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
		this.logger.debug("PRCHS,ORDER,CREATE_AUTO,START,{}", createPurchaseSc.getPrchsId());
		List<AutoPrchs> autoPrchsList = new ArrayList<AutoPrchs>();

		AutoPrchs autoPrchs = new AutoPrchs();
		autoPrchs.setTenantId(createPurchaseSc.getTenantId());
		autoPrchs.setFstPrchsId(createPurchaseSc.getPrchsId());
		autoPrchs.setFstPrchsDtlId(1); // TAKTODO
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
		autoPrchsList.add(autoPrchs);

		CreateAutoPurchaseScReq autoReq = new CreateAutoPurchaseScReq();
		autoReq.setAutoPrchsList(autoPrchsList);

		CreateAutoPurchaseScRes autoRes = this.purchaseOrderSCI.createNewAutoPurchase(autoReq);
		this.logger.debug("PRCHS,ORDER,CREATE_AUTO,END,{}", createPurchaseSc.getPrchsId(), autoRes.getCount());
		return autoRes.getCount();
	}
}
