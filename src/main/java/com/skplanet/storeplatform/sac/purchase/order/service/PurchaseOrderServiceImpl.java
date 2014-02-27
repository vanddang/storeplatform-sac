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

import org.apache.commons.codec.binary.Base64;
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
import com.skplanet.storeplatform.sac.client.purchase.vo.order.VerifyOrderSacRes;
import com.skplanet.storeplatform.sac.common.util.CryptUtils;
import com.skplanet.storeplatform.sac.purchase.constant.PurchaseConstants;
import com.skplanet.storeplatform.sac.purchase.interworking.service.InterworkingSacService;
import com.skplanet.storeplatform.sac.purchase.interworking.vo.InterworkingSacReq;
import com.skplanet.storeplatform.sac.purchase.order.dummy.vo.DummyProduct;
import com.skplanet.storeplatform.sac.purchase.order.vo.PaymentPageParam;
import com.skplanet.storeplatform.sac.purchase.order.vo.PurchaseOrderInfo;
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

	/*
	 * <pre> 새로운 구매ID 생성. </pre>
	 * 
	 * @param tempPrchsReqPathCd (임시파라미터) 구매요청경로코드
	 * 
	 * @param tempProdId (임시파라미터) 상품ID
	 * 
	 * @return 새로 생성된 구매ID
	 */
	private String makePrchsId(String tempPrchsReqPathCd, String tempProdId) {
		// TAKTODO:: 구매ID 생성
		String formattedNow = DateFormatUtils.format(Calendar.getInstance().getTimeInMillis(), "ddmmss");
		return "TAK" + formattedNow + tempPrchsReqPathCd.substring(8) + (int) (Math.random() * 1000)
				+ tempProdId.substring(7) + (int) (Math.random() * 1000);
	}

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
		this.createPurchaseByType(purchaseOrderInfo, CREATE_PURCHASE_TYPE_COMPLETED);
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

	private static final String USER_STATUS_ABNORMAL = "0";
	private static final String USER_STATUS_NORMAL = "1";
	private static final String PRODUCT_STATUS_ABNORMAL = "0";
	private static final String PRODUCT_STATUS_NORMAL = "1";

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
			throw new StorePlatformException("SAC_PUR_0001", "조회된 구매건이 없습니다.");// TAKTODO:: PP측으로 응답 정리
		}
		CreatePurchaseSc createPurchaseSc = createPurchaseScList.get(0);

		// ------------------------------------------------------------------------------------------------
		// 구매인증 응답 데이터 처리
		// TAKTODO:: 아래 항목들 모두 처리 필요

		VerifyOrderSacRes res = new VerifyOrderSacRes();
		if (StringUtils.equals(createPurchaseSc.getPrchsCaseCd(), PurchaseConstants.PRCHS_CASE_GIFT_CD)) {
			res.setUserKey(createPurchaseSc.getSendInsdUsermbrNo());
		} else {
			res.setUserKey(createPurchaseSc.getUseInsdUsermbrNo());
		}
		res.setMdn(res.getUserKey()); // [회원Part] MDN 조회
		res.setFlgMbrStatus(USER_STATUS_NORMAL); // [회원Part] 회원 (상태) 조회
		res.setFlgProductStatus(PRODUCT_STATUS_NORMAL); // [전시Part] 상품 조회
		res.setFlgTeleBillingAgree("Y"); // [회원Part] 통신과금 동의여부 조회
		res.setFlgOcbUseAgree("Y"); // [회원Part] OCB 이용약관 동의여부 조회
		res.setCdResetPaymethod(""); // 결제수단 별 가능 거래금액/비율 조정 정보
		res.setCdMaxAmtRate(""); // 결제수단 별 가능 거래금액/비율 조정 정보
		res.setCdOcbSaveInfo(""); // OCB 적립코드
		res.setNoOcbCard(""); // [회원Part] OCB 카드번호
		res.setNoCouponList(""); // [Tenant] 쿠폰 List
		res.setTypeTestMdn("T03"); // 법인 및 일반 시험폰 처리 타입 (T01, T02, T03)
		res.setBonusCashPoint("0"); // [전시Part] 보너스 캐쉬 지급 Point
		res.setBonusCashUsableDayCnt("0"); // [전시Part] 보너스 캐쉬 유효기간(일)
		res.setAfterAutoPayDt(""); // [전시Part] 다음 자동 결제일
		res.setDwldAvailableDayCnt("0"); // [전시Part] 다운로드 가능기간(일)
		res.setUsePeriodCnt("0"); // [전시Part] 이용기간(일)
		res.setLoanPid(""); // [전시Part] 대여하기 상품 ID
		res.setLoanAmt("0"); // [전시Part] 대여하기 상품 금액
		res.setOwnPid(""); // [전시Part] 소장하기 상품 ID
		res.setOwnAmt("0"); // [전시Part] 소장하기 상품 금액
		res.setNmSeller("SKP Store"); // [전시Part] 판매자명
		res.setEmailSeller("testhong@test.com"); // [전시Part] 판매자 이메일 주소
		res.setNoTelSeller("090-1212-3434"); // [전시Part] 판매자 전화번호
		res.setNmDelivery(""); // 선물수신자 성명
		res.setNoMdnDelivery(""); // 선물수신자 MDN

		return res;
	}

	/**
	 * 
	 * <pre>
	 * 유료구매 - 구매확정: 구매상세 내역 상태변경 & 구매 내역 저장 & (선물 경우)발송 상세 내역 저장, 결제내역 저장.
	 * </pre>
	 * 
	 * @param createPurchaseSc
	 *            구매상세 정보
	 * @param notifyPaymentReq
	 *            결제결과 정보
	 */
	@Override
	public void updateConfirmPurchase(NotifyPaymentSacReq notifyPaymentReq) {
		this.logger.debug("PRCHS,ORDER,CONFIRM,START");

		// ------------------------------------------------------------------------------
		// 구매 예약 건 조회

		Map<String, String> spareParamMap = this.parsePaySpareParameter(notifyPaymentReq.getMctSpareParam()); // 가맹점용
																											  // 파라미터

		SearchReservedPurchaseListScReq reqSearch = new SearchReservedPurchaseListScReq();
		reqSearch.setTenantId(spareParamMap.get("tenantId"));
		reqSearch.setPrchsId(notifyPaymentReq.getOrderId());

		SearchReservedPurchaseListScRes res = this.purchaseOrderSearchSCI.searchReservedPurchaseList(reqSearch);
		List<CreatePurchaseSc> createPurchaseScList = res.getCreatePurchaseScList();
		if (createPurchaseScList.size() < 1) {
			throw new StorePlatformException("SAC_PUR_0001", "조회된 구매건이 없습니다.");// TAKTODO:: PP측으로 응답 정리
		}

		// 공통 작업용 세팅
		for (CreatePurchaseSc createPurchaseSc : createPurchaseScList) {
			createPurchaseSc.setSystemId(spareParamMap.get("systemId"));
			createPurchaseSc.setCurrencyCd(spareParamMap.get("currencyCd"));
			createPurchaseSc.setNetworkTypeCd(spareParamMap.get("networkTypeCd"));

			createPurchaseSc.setStatusCd(PurchaseConstants.PRCHS_STATUS_COMPT); // 구매확정
			if (StringUtils.equals(createPurchaseSc.getPrchsCaseCd(), PurchaseConstants.PRCHS_CASE_GIFT_CD)) {
				createPurchaseSc.setInsdUsermbrNo(createPurchaseSc.getSendInsdUsermbrNo());
				createPurchaseSc.setInsdDeviceId(createPurchaseSc.getSendInsdDeviceId());
			} else {
				createPurchaseSc.setInsdUsermbrNo(createPurchaseSc.getUseInsdUsermbrNo());
				createPurchaseSc.setInsdDeviceId(createPurchaseSc.getUseInsdDeviceId());
			}
		}
		CreatePurchaseSc createPurchaseSc = createPurchaseScList.get(0);

		// -------------------------------------------------------------------------------------------
		// 쇼핑상품 쿠폰 발급요청
		// TAKTODO:: 보유자MDN/구매자MDN 조회, COUPONCODE/ITEMCODE 조회, 응답 처리

		List<ShoppingCouponPublishInfo> shoppingCouponList = null;

		if (createPurchaseSc.getTenantProdGrpCd().startsWith("OR006205")) {
			CouponPublishEcReq couponPublishEcReq = new CouponPublishEcReq();
			couponPublishEcReq.setPrchsId(createPurchaseSc.getPrchsId());
			couponPublishEcReq.setUseMdn("");
			couponPublishEcReq.setBuyMdn("");
			couponPublishEcReq.setCouponCode("");
			couponPublishEcReq.setItemCode("");
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

		// 구매이력 확정 처리
		ConfirmPurchaseScReq reqConfirm = new ConfirmPurchaseScReq();
		reqConfirm.setTenantId(createPurchaseSc.getTenantId());
		reqConfirm.setSystemId(createPurchaseSc.getSystemId());
		reqConfirm.setUseUserKey(createPurchaseSc.getUseInsdUsermbrNo());
		reqConfirm.setPrchsId(createPurchaseSc.getPrchsId());
		reqConfirm.setCurrencyCd(createPurchaseSc.getCurrencyCd());
		reqConfirm.setNetworkTypeCd(createPurchaseSc.getNetworkTypeCd());
		reqConfirm.setShoppingCouponList(shoppingCouponList); // 쇼핑발급 목록

		ConfirmPurchaseScRes resConfirm = this.purchaseOrderSCI.confirmPurchase(reqConfirm);
		this.logger.debug("PRCHS,ORDER,CONFIRM,END,{}", resConfirm.getCount());
		if (resConfirm.getCount() < 1) {
			throw new StorePlatformException("SAC_PUR_0001", "구매확정 처리 중 에러 발생");
		}

		// 결제 내역 저장
		this.createPayment(createPurchaseSc, notifyPaymentReq);

		// 자동구매 신규이력 저장
		if (StringUtils.isNotBlank(notifyPaymentReq.getGwBillkey())) {
			this.createAutoPurchase(createPurchaseSc);
		}

		// -------------------------------------------------------------------------------------------
		// 구매 후처리

		// 씨네21, 인터파크
		// TAKTODO:: 판매자 회원번호 조회, COMP_CONTS_ID, MALL_CD 조회

		InterworkingSacReq interworkingSacReq = new InterworkingSacReq();
		interworkingSacReq.setTenantId(createPurchaseSc.getTenantId());
		interworkingSacReq.setSystemId(createPurchaseSc.getSystemId());
		interworkingSacReq.setPrchsId(createPurchaseSc.getPrchsId());
		interworkingSacReq.setUserKey(createPurchaseSc.getUseInsdUsermbrNo());
		interworkingSacReq.setPrchsDt(createPurchaseSc.getPrchsDt());
		interworkingSacReq.setProdId(createPurchaseSc.getProdId());
		interworkingSacReq.setSellermbrNo("");
		interworkingSacReq.setCompContentsId("");
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

		purchaseOrderInfo.setPaymentPageUrl(DUMMY_PP_PAYMENT_PAGE_URL);
		purchaseOrderInfo.setPaymentPageParam(this.makeEncryptedPaymentParameter(purchaseOrderInfo));

		this.logger.debug("PRCHS,ORDER,SET_PAYPAGE,END,{}", purchaseOrderInfo);
	}

	/*
	 * <pre> 결제Page 측으로부터 전달받은 가맹점용 파라미터 분리. </pre>
	 * 
	 * @param mctSpareParam 최초 결제Page로 넘겼던 파라미터
	 * 
	 * @return 분리된 파라미터 Map
	 */
	private Map<String, String> parsePaySpareParameter(String mctSpareParam) {
		Map<String, String> spareParamMap = new HashMap<String, String>();
		String[] arSpareParam = null;
		String[] arParamKeyValue = null;

		if (StringUtils.isNotEmpty(mctSpareParam)) {
			arSpareParam = mctSpareParam.split("&");

			for (String param : arSpareParam) {
				arParamKeyValue = param.split(":");
				spareParamMap.put(arParamKeyValue[0], arParamKeyValue[1]);
			}
		}

		return spareParamMap;
	}

	/*
	 * <pre> 구매생성을 위한 데이터 목록 생성. </pre>
	 * 
	 * @param purchaseOrderInfo 구매요청 VO
	 * 
	 * @return 구매생성을 위한 데이터 목록
	 */
	private List<CreatePurchaseSc> makeCreatePurchaseScListForRequest(PurchaseOrderInfo purchaseOrderInfo) {

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
				createPurchase.setResvCol05(product.getResvCol05());
				createPurchase.setUsePeriodUnitCd(product.getUsePeriodUnitCd());
				createPurchase.setUsePeriod(product.getUsePeriod());

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
			String prchsId = this.makePrchsId(purchaseOrderInfo.getPrchsReqPathCd(), purchaseOrderInfo.getProductList()
					.get(0).getProdId());
			purchaseOrderInfo.setPrchsId(prchsId);
		}

		// 구매시간 세팅
		purchaseOrderInfo
				.setPrchsDt(DateFormatUtils.format(Calendar.getInstance().getTimeInMillis(), "yyyyMMddHHmmss"));

		// 구매생성 요청 데이터 생성
		List<CreatePurchaseSc> createPurchaseList = this.makeCreatePurchaseScListForRequest(purchaseOrderInfo);

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
			throw new StorePlatformException("SAC_PUR_0001", "구매요청 처리 중 에러 발생");
		}
	}

	/*
	 * 
	 * <pre> 결제 내역 생성. </pre>
	 * 
	 * @param createPurchaseSc 구매정보
	 * 
	 * @param notifyParam 결제정보
	 */
	private void createPayment(CreatePurchaseSc createPurchaseSc, NotifyPaymentSacReq notifyParam) {
		this.logger.debug("PRCHS,ORDER,NOTIPAY,START,{}", notifyParam);

		String tenantId = createPurchaseSc.getTenantId();
		String systemId = createPurchaseSc.getSystemId();
		String prchsId = createPurchaseSc.getPrchsId();
		String payUserKey = createPurchaseSc.getInsdUsermbrNo();
		String payDeviceKey = createPurchaseSc.getInsdDeviceId();
		String prchsDt = createPurchaseSc.getPrchsDt();
		Double totAmt = createPurchaseSc.getTotAmt();

		String tid = notifyParam.getTid();

		List<Payment> paymentList = new ArrayList<Payment>();
		Payment payment = null;

		String[] arPaymethod = notifyParam.getPaymethodInfo().split(";"); // 수단코드:결제금액;...
		String[] arPayInfo = null;

		int dtlIdCnt = 1;
		for (String pay : arPaymethod) {
			arPayInfo = pay.split(":");

			payment = new Payment();
			payment.setTenantId(tenantId);
			payment.setPrchsId(prchsId);
			payment.setPaymentDtlId(dtlIdCnt++);
			payment.setInsdUsermbrNo(payUserKey);
			payment.setInsdDeviceId(payDeviceKey);
			payment.setPrchsDt(prchsDt);
			payment.setTotAmt(totAmt);

			payment.setPaymentMtdCd("OR0006" + arPayInfo[0]);
			payment.setPaymentAmt(Double.valueOf(arPayInfo[1]));
			payment.setPaymentDt(prchsDt);
			payment.setPaymentStatusCd(createPurchaseSc.getStatusCd());

			payment.setTid(tid);

			payment.setRegId(systemId);
			payment.setUpdId(systemId);

			paymentList.add(payment);
		}

		// 결제 내역 생성 요청
		CreatePaymentScReq reqPayment = new CreatePaymentScReq();
		reqPayment.setPaymentList(paymentList);

		CreatePaymentScRes res = this.purchaseOrderSCI.createPayment(reqPayment);
		this.logger.debug("PRCHS,ORDER,NOTIPAY,END,{}", notifyParam);
		if (res.getCount() < 1 || res.getCount() != paymentList.size()) {
			throw new StorePlatformException("SAC_PUR_0001", "결제내역 저장 처리 중 에러 발생");
		}
	}

	/*
	 * 
	 * <pre> 자동구매 생성. </pre>
	 * 
	 * @param createPurchaseSc 구매생성 정보
	 */
	private void createAutoPurchase(CreatePurchaseSc createPurchaseSc) {
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

		this.purchaseOrderSCI.createNewAutoPurchase(autoReq);
	}

	/*
	 * <pre> 결제Page 요청시 전달할 (암호화된) 파라미터 생성. </pre>
	 * 
	 * @param purchaseOrderInfo 구매요청 정보 VO
	 * 
	 * @return 결제Page로 전달할 파라미터의 암호화된 문자열
	 */
	private String makeEncryptedPaymentParameter(PurchaseOrderInfo purchaseOrderInfo) {

		DummyProduct product = purchaseOrderInfo.getProductList().get(0);

		PaymentPageParam param = new PaymentPageParam();
		param.setMid(purchaseOrderInfo.getMid());
		param.setAuthKey(purchaseOrderInfo.getAuthKey());
		param.setOrderId(purchaseOrderInfo.getPrchsId());
		param.setMctTrDate(purchaseOrderInfo.getPrchsDt());
		param.setAmtPurchase(String.valueOf(purchaseOrderInfo.getRealTotAmt()));
		param.setPid(product.getProdId());
		param.setpName(product.getProdNm());
		param.setpDescription(product.getProdId()); // TAKTODO
		param.setpType(product.getProdId()); // TAKTODO
		param.setAid(product.getAid());
		param.setReturnFormat(PaymentPageParam.PP_RETURN_FORMAT_JSON);
		param.setReturnPath(purchaseOrderInfo.getReturnUrl());
		param.setResultPath(DUMMY_SAC_PUR_PAYMENT_NOTIFY_URL);
		param.setMdn(purchaseOrderInfo.getPurchaseMember().getDeviceId());
		param.setNmDevice(purchaseOrderInfo.getDeviceModelCd());
		param.setImei(purchaseOrderInfo.getImei());
		param.setUacd(purchaseOrderInfo.getUacd());
		param.setTypeNetwork(purchaseOrderInfo.getNetworkTypeCd().substring(
				purchaseOrderInfo.getNetworkTypeCd().length() - 1));
		param.setCarrier(purchaseOrderInfo.getPurchaseMember().getTelecom());
		param.setNoSim(purchaseOrderInfo.getSimNo());
		param.setFlgSim(purchaseOrderInfo.getSimYn());

		// 가맹점 필요 파라미터
		String useUserKey = null;
		if (StringUtils.equals(purchaseOrderInfo.getPrchsCaseCd(), PurchaseConstants.PRCHS_CASE_GIFT_CD)) {
			useUserKey = purchaseOrderInfo.getRecvUserKey();
		} else {
			useUserKey = purchaseOrderInfo.getUserKey();
		}
		StringBuffer sbSpare = new StringBuffer(1024);
		sbSpare.append("tenantId:").append(purchaseOrderInfo.getTenantId()).append("&systemId:")
				.append(purchaseOrderInfo.getSystemId()).append("&useUserKey:").append(useUserKey)
				.append("&networkTypeCd:").append(purchaseOrderInfo.getNetworkTypeCd()).append("&currencyCd:")
				.append(purchaseOrderInfo.getCurrencyCd());

		param.setMctSpareParam(sbSpare.toString());

		// 암호화
		String eData = param.makeEncDataFormat();
		eData = CryptUtils.encrypt(CryptUtils.AES128_CTR, "5", eData);
		eData = Base64.encodeBase64String(eData.getBytes());
		param.seteData(eData);

		// Token
		String token = param.makeTokenFormat();
		try {
			token = Base64.encodeBase64String(MessageDigest.getInstance("MD5").digest(token.getBytes()));
		} catch (NoSuchAlgorithmException e) {
			throw new StorePlatformException("SAC_PUR_0001", "구매요청 처리 중 에러 발생");
		}
		param.setToken(token);

		this.logger.debug("TAKTEST,{},{}", eData, token);

		return eData;
	}
}
