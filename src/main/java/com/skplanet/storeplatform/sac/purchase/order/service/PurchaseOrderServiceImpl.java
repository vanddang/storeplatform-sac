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

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.skplanet.pdp.sentinel.shuttle.TLogSentinelShuttle;
import com.skplanet.storeplatform.external.client.shopping.vo.CouponPublishEcRes;
import com.skplanet.storeplatform.external.client.shopping.vo.CouponPublishItemDetailEcRes;
import com.skplanet.storeplatform.external.client.tstore.vo.TStoreCashChargeReserveDetailEcRes;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.util.DateUtils;
import com.skplanet.storeplatform.framework.core.util.log.TLogUtil;
import com.skplanet.storeplatform.framework.core.util.log.TLogUtil.ShuttleSetter;
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
import com.skplanet.storeplatform.purchase.client.order.vo.ReservePurchaseScReq;
import com.skplanet.storeplatform.purchase.client.order.vo.ReservePurchaseScRes;
import com.skplanet.storeplatform.purchase.client.order.vo.SearchPurchaseSequenceAndDateRes;
import com.skplanet.storeplatform.purchase.client.order.vo.SearchReservedPurchaseListScReq;
import com.skplanet.storeplatform.purchase.client.order.vo.SearchReservedPurchaseListScRes;
import com.skplanet.storeplatform.purchase.client.order.vo.ShoppingCouponPublishInfo;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.EpisodeInfoRes;
import com.skplanet.storeplatform.sac.client.internal.member.seller.vo.SellerMbrSac;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchUserPayplanetSacRes;
import com.skplanet.storeplatform.sac.client.purchase.vo.order.NotifyPaymentSacReq;
import com.skplanet.storeplatform.sac.client.purchase.vo.order.PaymentInfo;
import com.skplanet.storeplatform.sac.client.purchase.vo.order.VerifyOrderSacRes;
import com.skplanet.storeplatform.sac.purchase.common.service.PayPlanetShopService;
import com.skplanet.storeplatform.sac.purchase.common.service.PurchaseTenantPolicyService;
import com.skplanet.storeplatform.sac.purchase.common.vo.PayPlanetShop;
import com.skplanet.storeplatform.sac.purchase.constant.PurchaseConstants;
import com.skplanet.storeplatform.sac.purchase.order.repository.PurchaseDisplayRepository;
import com.skplanet.storeplatform.sac.purchase.order.repository.PurchaseMemberRepository;
import com.skplanet.storeplatform.sac.purchase.order.repository.PurchaseShoppingOrderRepository;
import com.skplanet.storeplatform.sac.purchase.order.vo.PurchaseOrderInfo;
import com.skplanet.storeplatform.sac.purchase.order.vo.PurchaseProduct;
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
	@Value("#{systemProperties['env.servername']}")
	private String instanceName;
	private String hostNum;
	private String instanceNum;

	@Autowired
	private PurchaseOrderSCI purchaseOrderSCI;
	@Autowired
	private PurchaseOrderSearchSCI purchaseOrderSearchSCI;
	@Autowired
	private PurchaseOrderMakeDataService purchaseOrderMakeDataService;
	@Autowired
	private PurchaseOrderPolicyService purchaseOrderPolicyService;
	@Autowired
	private PurchaseOrderTstoreService purchaseOrderTstoreService;
	@Autowired
	private PurchaseTenantPolicyService purchaseTenantPolicyService;
	@Autowired
	private PayPlanetShopService payPlanetShopService;
	@Autowired
	private PurchaseMemberRepository purchaseMemberRepository;
	@Autowired
	private PurchaseDisplayRepository purchaseDisplayRepository;
	@Autowired
	private PurchaseShoppingOrderRepository purchaseShoppingOrderRepository;

	@PostConstruct
	public void initHostInstanceNum() {
		String hostName = null;
		try {
			hostName = InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			hostName = "01";
		}
		Pattern pattern = Pattern.compile("\\d");

		this.hostNum = "";
		Matcher matcher = pattern.matcher(hostName);
		while (matcher.find()) {
			this.hostNum += matcher.group(0);
		}
		if (StringUtils.isBlank(this.hostNum)) {
			this.hostNum = "01";
		}
		this.hostNum = StringUtils.leftPad(this.hostNum, 2, "0");

		this.instanceNum = "";
		if (StringUtils.isNotBlank(this.instanceName)) {
			matcher = pattern.matcher(this.instanceName);
			while (matcher.find()) {
				this.instanceNum += matcher.group(0);
			}
		}
		if (StringUtils.isBlank(this.instanceNum)) {
			this.instanceNum = "01";
		}
		this.instanceNum = StringUtils.leftPad(this.instanceNum, 2, "0");
	}

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
		// CLINK 예외 처리
		boolean bAtLeastOne = false;
		for (PurchaseProduct product : purchaseOrderInfo.getPurchaseProductList()) {
			if (StringUtils.isBlank(product.getResultCd())) {
				bAtLeastOne = true;
				break;
			}
		}

		if (bAtLeastOne == false) {
			return 0;
		}

		// -----------------------------------------------------------------------------
		// 구매ID, 구매일시 세팅

		SearchPurchaseSequenceAndDateRes searchPurchaseSequenceAndDateRes = this.purchaseOrderSearchSCI
				.searchPurchaseSequenceAndDate();

		if (StringUtils.isBlank(purchaseOrderInfo.getPrchsId())) {
			purchaseOrderInfo.setPrchsId(this.makePrchsId(searchPurchaseSequenceAndDateRes.getNextSequence(),
					searchPurchaseSequenceAndDateRes.getNowDate()));
		}

		purchaseOrderInfo.setPrchsDt(searchPurchaseSequenceAndDateRes.getNowDate());

		// -----------------------------------------------------------------------------
		// 무료구매 완료 요청 데이터 생성

		// 구매생성 요청 데이터
		List<PrchsDtlMore> prchsDtlMoreList = this.purchaseOrderMakeDataService.makePrchsDtlMoreList(purchaseOrderInfo);

		// 구매집계 요청 데이터: 테스트폰 / Biz 쿠폰 발급 요청 경우는 제외
		List<PrchsProdCnt> prchsProdCntList = null;
		if ((purchaseOrderInfo.isTestMdn() == false) && (purchaseOrderInfo.isBizShopping() == false)) {
			prchsProdCntList = this.purchaseOrderMakeDataService.makePrchsProdCntList(prchsDtlMoreList,
					PurchaseConstants.PRCHS_STATUS_COMPT);
		}

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

			paymentList = this.purchaseOrderMakeDataService.makePaymentList(prchsDtlMoreList.get(0), paymentInfoList,
					PurchaseConstants.PRCHS_STATUS_COMPT);
		}

		// -----------------------------------------------------------------------------
		// Biz 쿠폰 발급 요청

		if (purchaseOrderInfo.isBizShopping()) {
			try {
				this.purchaseShoppingOrderRepository.createBizCouponPublish(purchaseOrderInfo.getPrchsId(),
						purchaseOrderInfo.getPurchaseUser().getUserId(), purchaseOrderInfo.getPurchaseUser()
								.getDeviceId(), purchaseOrderInfo.getPurchaseProductList().get(0).getCouponCode(),
						purchaseOrderInfo.getReceiveUserList());
			} catch (Exception e) {
				throw new StorePlatformException("SAC_PUR_7212", e);
			}
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
			throw (this.isDuplicateKeyException(e) ? new StorePlatformException("SAC_PUR_6110") : e); // 중복된 구매요청 체크

		} catch (DuplicateKeyException e) {
			throw new StorePlatformException("SAC_PUR_6110");

		} catch (Exception e) {
			throw new StorePlatformException("SAC_PUR_7218", e);
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

		// -------------------------------------------------------------------------------------------
		// 구매완료 TLog
		// TAKTODO:: 일단은 Biz 쿠폰 발급 경우는 T Log 제외

		if (purchaseOrderInfo.isBizShopping() == false) {

			final String imei = purchaseOrderInfo.getImei();
			String telecom = purchaseOrderInfo.getPurchaseUser().getTelecom();
			final String mno_type = StringUtils.equals(telecom, PurchaseConstants.TELECOM_SKT) ? "SKT" : (StringUtils
					.equals(telecom, PurchaseConstants.TELECOM_KT) ? "KT" : (StringUtils.equals(telecom,
					PurchaseConstants.TELECOM_UPLUS) ? "U+" : "")); // SKT, KT, U+
			final String usermbr_no = purchaseOrderInfo.getPurchaseUser().getUserKey();
			final String system_id = purchaseOrderInfo.getSystemId();
			final String purchase_channel = purchaseOrderInfo.getPrchsReqPathCd();
			final String purchase_inflow_channel = StringUtils.equals(purchaseOrderInfo.getPrchsCaseCd(),
					PurchaseConstants.PRCHS_CASE_PURCHASE_CD) ? "FDS00201" : "FDS00202";
			final String purchase_id = purchaseOrderInfo.getPrchsId();
			final String purchase_id_recv = purchaseOrderInfo.isGift() ? purchaseOrderInfo.getPrchsId() : "";

			for (PrchsDtlMore prchsInfo : prchsDtlMoreList) { // 상품 수 만큼 로깅
				final List<String> prodIdList = new ArrayList<String>();
				prodIdList.add(prchsInfo.getProdId());
				final String purchase_prod_num = String.valueOf(prchsInfo.getPrchsDtlId());
				final String purchase_prod_num_recv = purchaseOrderInfo.isGift() ? String.valueOf(prchsInfo
						.getPrchsDtlId()) : "";
				final String tid = prchsInfo.getTid();
				final String tx_id = prchsInfo.getTxId();
				final String use_start_time = prchsInfo.getPrchsDt();
				final String use_end_time = this.calculateUseDate(prchsInfo.getPrchsDt(),
						prchsInfo.getUsePeriodUnitCd(), prchsInfo.getUsePeriod());
				final String download_expired_time = use_end_time;
				final Long product_qty = (long) prchsInfo.getProdQty();
				final String coupon_publish_code = "";
				final String coupon_code = "";
				final String coupon_item_code = "";
				final String auto_payment_yn = "N";

				new TLogUtil().log(new ShuttleSetter() {
					@Override
					public void customize(TLogSentinelShuttle shuttle) {
						shuttle.log_id(PurchaseConstants.TLOG_ID_PURCHASE_ORDER_RESULT).imei(imei).mno_type(mno_type)
								.usermbr_no(usermbr_no).system_id(system_id).purchase_channel(purchase_channel)
								.purchase_inflow_channel(purchase_inflow_channel).purchase_id(purchase_id)
								.purchase_id_recv(purchase_id_recv).product_id(prodIdList)
								.purchase_prod_num(purchase_prod_num).purchase_prod_num_recv(purchase_prod_num_recv)
								.tid(tid).tx_id(tx_id).use_start_time(use_start_time).use_end_time(use_end_time)
								.download_expired_time(download_expired_time).product_qty(product_qty)
								.coupon_publish_code(coupon_publish_code).coupon_code(coupon_code)
								.coupon_item_code(coupon_item_code).auto_payment_yn(auto_payment_yn)
								.result_code("SUCC");
					}
				});
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

		SearchPurchaseSequenceAndDateRes searchPurchaseSequenceAndDateRes = this.purchaseOrderSearchSCI
				.searchPurchaseSequenceAndDate();

		if (StringUtils.isBlank(purchaseOrderInfo.getPrchsId())) {
			purchaseOrderInfo.setPrchsId(this.makePrchsId(searchPurchaseSequenceAndDateRes.getNextSequence(),
					searchPurchaseSequenceAndDateRes.getNowDate()));
		}

		// -----------------------------------------------------------------------------
		// 구매시간 세팅

		purchaseOrderInfo.setPrchsDt(searchPurchaseSequenceAndDateRes.getNowDate());

		// -----------------------------------------------------------------------------
		// 구매생성 요청 데이터 생성

		List<PrchsDtlMore> prchsDtlMoreList = this.purchaseOrderMakeDataService.makePrchsDtlMoreList(purchaseOrderInfo);
		this.purchaseOrderMakeDataService.buildReservedData(purchaseOrderInfo, prchsDtlMoreList); // 예약 정보 세팅

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

		List<PrchsDtlMore> prchsDtlMoreList = this.searchReservedPurchaseList(verifyOrderInfo.getTenantId(),
				verifyOrderInfo.getPrchsId());

		PrchsDtlMore prchsDtlMore = prchsDtlMoreList.get(0);

		// ------------------------------------------------------------------------------------------------
		// 구매인증 응답 데이터 처리

		VerifyOrderSacRes res = new VerifyOrderSacRes();

		// 예약 저장해둔 데이터 추출
		Map<String, String> reservedDataMap = this.purchaseOrderMakeDataService.parseReservedData(prchsDtlMore
				.getPrchsResvDesc());

		// 회원 - 결제 관련 정보 조회
		SearchUserPayplanetSacRes userPayRes = null;
		try {
			userPayRes = this.purchaseMemberRepository.searchUserPayplanet(reservedDataMap.get("userKey"),
					reservedDataMap.get("deviceKey"));
		} catch (Exception e) {
			throw new StorePlatformException("SAC_PUR_7208", e);
		}
		// 통신과금 동의여부
		res.setFlgTeleBillingAgree(StringUtils.equals(userPayRes.getSkpAgreementYn(), PurchaseConstants.USE_Y) ? "1" : "0");
		// OCB 이용약관 동의여부
		res.setFlgOcbUseAgree(StringUtils.equals(userPayRes.getOcbAgreementYn(), PurchaseConstants.USE_Y) ? "1" : "0");
		// OCB 카드번호
		res.setNoOcbCard(userPayRes.getOcbCardNumber());
		// OCB 인증수단 코드
		res.setOcbAuthMtdCd(userPayRes.getOcbAuthMethodCode());

		// ------------------------------------------------------------------------------------------------
		// 구매(결제) 차단 체크

		res.setFlgBlockPayment(this.purchaseOrderPolicyService.isBlockPayment(prchsDtlMore.getTenantId(),
				reservedDataMap.get("deviceId"), prchsDtlMore.getTenantProdGrpCd()) ? PurchaseConstants.VERIFYORDER_BLOCK_PAYMENT : PurchaseConstants.VERIFYORDER_ALLOW_PAYMENT);

		// ------------------------------------------------------------------------------------------------
		// SKT 후불 관련 정책 체크: SKT시험폰, MVNO, 법인폰, 한도금액 조회

		SktPaymentPolicyCheckResult policyResult = this.checkSktPaymentPolicy(prchsDtlMore,
				reservedDataMap.get("telecom"), reservedDataMap.get("deviceId"), reservedDataMap.get("useDeviceId"));

		String testMdnType = PurchaseConstants.SKT_PAYMENT_TYPE_NORMAL;
		String sktPaymethodInfo = null;

		if (policyResult != null) {

			// SKT후불 결제정보 재정의 원인
			res.setTypeSktLimit(policyResult.getSktLimitType());

			// SKT 결제 가능 금액
			double sktAvailableAmt = policyResult.getSktRestAmt();

			// SKT 결제 처리 타입
			if (policyResult.isSktTestMdn()) {
				if (policyResult.isSktTestMdnWhiteList()) {
					testMdnType = PurchaseConstants.SKT_PAYMENT_TYPE_TESTDEVICE;
					sktAvailableAmt = prchsDtlMore.getTotAmt().doubleValue();
				} else {
					testMdnType = PurchaseConstants.SKT_PAYMENT_TYPE_ETCSERVICE;
				}
			} else if (policyResult.isCorporation() || policyResult.isMvno()) {
				testMdnType = PurchaseConstants.SKT_PAYMENT_TYPE_ETCSERVICE;
			}

			// SKT 후불 재조정
			if (StringUtils.equals(testMdnType, PurchaseConstants.SKT_PAYMENT_TYPE_ETCSERVICE)) {
				sktPaymethodInfo = "11:0:0";
			} else {
				if (sktAvailableAmt > 0.0) {
					sktPaymethodInfo = "11:" + sktAvailableAmt + ":100";
				} else {
					sktPaymethodInfo = "11:0:0";
				}
			}

			// SKT 후불 SYSTEM_DIVISION
			if (StringUtils.startsWith(prchsDtlMore.getTenantProdGrpCd(),
					PurchaseConstants.TENANT_PRODUCT_GROUP_DTL_MOVIE_FIXRATE)
					|| StringUtils.startsWith(prchsDtlMore.getTenantProdGrpCd(),
							PurchaseConstants.TENANT_PRODUCT_GROUP_DTL_TV_FIXRATE)) {
				res.setApprovalSd(PurchaseConstants.SKT_SYSTEM_DIVISION_VOD_FIXRATE_APPROVAL);
				res.setCancelSd(PurchaseConstants.SKT_SYSTEM_DIVISION_VOD_FIXRATE_CANCEL);
			} else {
				res.setApprovalSd(PurchaseConstants.SKT_SYSTEM_DIVISION_NORMAL_APPROVAL);
				res.setCancelSd(PurchaseConstants.SKT_SYSTEM_DIVISION_NORMAL_CANCEL);
			}
		}

		// 법인 및 일반 시험폰 처리 타입 (T01, T02, T03)
		res.setTypeTestMdn(testMdnType);

		// 결제수단 별 가능 거래금액/비율 조정 정보
		res.setCdMaxAmtRate(this.adjustPaymethod(sktPaymethodInfo, prchsDtlMore.getTenantId(),
				prchsDtlMore.getTenantProdGrpCd(), prchsDtlMore.getTotAmt().doubleValue()));

		// ------------------------------------------------------------------------------------------------
		// 결제수단 정렬 재조정

		res.setCdPriority("");

		// ------------------------------------------------------------------------------------------------
		// T store 쿠폰 조회

		List<String> prodIdList = new ArrayList<String>();
		for (PrchsDtlMore productInfo : prchsDtlMoreList) {
			prodIdList.add(productInfo.getProdId());
		}
		res.setNoCouponList(this.purchaseOrderTstoreService.searchTstoreCouponList(reservedDataMap.get("userKey"),
				reservedDataMap.get("deviceId"), prodIdList));

		// ------------------------------------------------------------------------------------------------
		// T store Cash 조회

		res.setTstoreCashAmt(this.purchaseOrderTstoreService.searchTstoreCashAmt(reservedDataMap.get("userKey")));

		// ------------------------------------------------------------------------------------------------
		// 게임캐쉬 조회

		// if (StringUtils.startsWith(prchsDtlMore.getTenantProdGrpCd(),
		// PurchaseConstants.TENANT_PRODUCT_GROUP_DTL_GAME)) {
		if (StringUtils.equals(prchsDtlMore.getTenantProdGrpCd().substring(8, 12), "DP01")) {
			res.setGameCashAmt(this.purchaseOrderTstoreService.searchGameCashAmt(reservedDataMap.get("userKey")));
		} else {
			res.setGameCashAmt(0.0);
		}

		// ------------------------------------------------------------------------------------------------
		// OCB 적립율

		// 시험폰, SKP법인폰 여부
		boolean sktTestOrSkpCorp = (policyResult != null ? (policyResult.isSktTestMdn() || policyResult
				.isSkpCorporation()) : false);

		res.setCdOcbSaveInfo(this.adjustOcbSaveInfo(reservedDataMap.get("telecom"), prchsDtlMore.getTenantProdGrpCd(),
				sktTestOrSkpCorp));

		// ------------------------------------------------------------------------------------------------
		// 결제Page 템플릿

		res.setCdPaymentTemplate(this.adjustPaymentPageTemplate(prchsDtlMore.getPrchsCaseCd(),
				prchsDtlMore.getTenantProdGrpCd(), prchsDtlMoreList.size()));

		// ------------------------------------------------------------------------------------------------
		// (다날) 컨텐츠 종류: 실물 / 디지털 : 쇼핑상품만 실물로 처리

		if (StringUtils.startsWith(prchsDtlMore.getTenantProdGrpCd(), PurchaseConstants.TENANT_PRODUCT_GROUP_SHOPPING)) {
			res.setTypeDanalContent(PurchaseConstants.DANAL_CONTENT_TYPE_REAL);
		} else {
			res.setTypeDanalContent(PurchaseConstants.DANAL_CONTENT_TYPE_DIGITAL);
		}

		// ------------------------------------------------------------------------------------------------
		// 기타 응답 값

		res.setMdn(reservedDataMap.get("deviceId")); // 결제 MDN
		res.setOneId(reservedDataMap.get("oneId")); // ONE ID
		res.setFlgMbrStatus(PurchaseConstants.VERIFYORDER_USER_STATUS_NORMAL); // [fix] 회원상태: 1-정상
		res.setFlgProductStatus(PurchaseConstants.VERIFYORDER_PRODUCT_STATUS_NORMAL); // [fix] 상품상태: 1-정상
		res.setTopMenuId(prchsDtlMore.getTenantProdGrpCd().substring(8, 12)); // 상품 TOP 메뉴 ID
		// 게임캐쉬
		if (StringUtils.startsWith(prchsDtlMore.getTenantProdGrpCd(),
				PurchaseConstants.TENANT_PRODUCT_GROUP_DTL_GAMECASH_FIXRATE)) {
			String afterAutoPayDt = this.calculateUseDate(prchsDtlMore.getUseStartDt(),
					reservedDataMap.get("autoPrchsPeriodUnitCd"),
					StringUtils.defaultString(reservedDataMap.get("autoPrchsPeriodValue"), "0"));
			res.setAfterAutoPayDt(afterAutoPayDt.substring(0, 8) + "000000"); // 다음 자동 결제일
			res.setBonusCashPoint(reservedDataMap.get("bonusPoint")); // 보너스 캐쉬 지급 Point
			res.setBonusCashUsableDayCnt(reservedDataMap.get("bonusPointUsableDayCnt")); // 보너스 캐쉬 유효기간(일)
		}
		// 대여/소장
		if (StringUtils.isNotBlank(reservedDataMap.get("loanPid"))
				&& StringUtils
						.startsWith(prchsDtlMore.getTenantProdGrpCd(), PurchaseConstants.TENANT_PRODUCT_GROUP_VOD)) {
			res.setBasePid(reservedDataMap.get("loanPid"));
		} else {
			res.setBasePid(prchsDtlMore.getProdId());
		}
		res.setDwldAvailableDayCnt(reservedDataMap.get("dwldAvailableDayCnt")); // 다운로드 가능기간(일)
		res.setUsePeriodCnt(reservedDataMap.get("usePeriodCnt")); // 이용기간(일)
		res.setLoanPid(reservedDataMap.get("loanPid")); // 대여하기 상품 ID
		if (StringUtils.isNotBlank(reservedDataMap.get("loanAmt"))) {
			res.setLoanAmt(Double.parseDouble(StringUtils.defaultString(reservedDataMap.get("loanAmt"), "0"))); // 대여하기
																												// 상품 금액
		}
		res.setOwnPid(reservedDataMap.get("ownPid")); // 소장하기 상품 ID
		if (StringUtils.isNotBlank(reservedDataMap.get("ownAmt"))) {
			res.setOwnAmt(Double.parseDouble(StringUtils.defaultString(reservedDataMap.get("ownAmt"), "0"))); // 소장하기 상품
																											  // 금액
		}

		// 판매자 정보 조회 - 쇼핑상품 경우만 조회
		if (StringUtils.startsWith(prchsDtlMore.getTenantProdGrpCd(), PurchaseConstants.TENANT_PRODUCT_GROUP_SHOPPING)) {
			SellerMbrSac sellerInfo = this.searchSellerInfo(reservedDataMap.get("sellerMbrNo"));

			res.setNmSeller(sellerInfo.getSellerNickName()); // 쇼핑 노출명
			res.setEmailSeller(sellerInfo.getSellerEmail()); // 판매자 이메일 주소
			res.setNoTelSeller(sellerInfo.getRepPhone()); // 대표전화번호
		}

		// 선물 수신자
		res.setNmDelivery(reservedDataMap.get("receiveNames")); // 선물수신자 성명
		res.setNoMdnDelivery(reservedDataMap.get("receiveMdns")); // 선물수신자 MDN

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
		this.logger.info("PRCHS,ORDER,SAC,CONFIRM,START");

		// ------------------------------------------------------------------------------
		// 구매 예약 건 조회

		List<PrchsDtlMore> prchsDtlMoreList = this.searchReservedPurchaseList(tenantId, notifyPaymentReq.getPrchsId());

		// ------------------------------------------------------------------------------
		// 구매예약 시, 추가 저장해 두었던 데이터 추출

		Map<String, String> reservedDataMap = this.purchaseOrderMakeDataService.parseReservedData(prchsDtlMoreList.get(
				0).getPrchsResvDesc());

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
		PrchsDtlMore prchsDtlMore = prchsDtlMoreList.get(0);

		// 구매완료 TLog 중간 세팅
		final String insdUsermbrNo = StringUtils.equals(prchsDtlMore.getPrchsCaseCd(),
				PurchaseConstants.PRCHS_CASE_PURCHASE_CD) ? prchsDtlMore.getUseInsdUsermbrNo() : prchsDtlMore
				.getSendInsdUsermbrNo();
		final String insdDeviceId = StringUtils.equals(prchsDtlMore.getPrchsCaseCd(),
				PurchaseConstants.PRCHS_CASE_PURCHASE_CD) ? prchsDtlMore.getUseInsdDeviceId() : prchsDtlMore
				.getSendInsdDeviceId();
		final String mbrId = reservedDataMap.get("userId");
		final String deviceId = reservedDataMap.get("deviceId");
		final String imei = reservedDataMap.get("imei");
		final String mno_type = StringUtils.equals(reservedDataMap.get("telecom"), PurchaseConstants.TELECOM_SKT) ? "SKT" : (StringUtils
				.equals(reservedDataMap.get("telecom"), PurchaseConstants.TELECOM_KT) ? "KT" : (StringUtils.equals(
				reservedDataMap.get("telecom"), PurchaseConstants.TELECOM_UPLUS) ? "U+" : "")); // SKT, KT, U+
		final String usermbr_no = reservedDataMap.get("userKey");
		final String system_id = prchsDtlMore.getSystemId();
		final String purchase_channel = prchsDtlMore.getPrchsReqPathCd();
		final String purchase_inflow_channel = StringUtils.equals(prchsDtlMore.getPrchsCaseCd(),
				PurchaseConstants.PRCHS_CASE_PURCHASE_CD) ? "FDS00201" : "FDS00202";
		final String purchase_id_recv = StringUtils.equals(prchsDtlMore.getPrchsCaseCd(),
				PurchaseConstants.PRCHS_CASE_GIFT_CD) ? prchsDtlMore.getPrchsId() : "";
		final String coupon_code = reservedDataMap.get("couponCode");
		final String coupon_item_code = reservedDataMap.get("itemCode");
		final String auto_payment_yn = StringUtils.defaultIfBlank(reservedDataMap.get("autoPrchsYn"), "N");
		final List<String> prodIdTempList = new ArrayList<String>();
		for (PrchsDtlMore tempPrchsDtlMore : prchsDtlMoreList) {
			prodIdTempList.add(tempPrchsDtlMore.getProdId());
		}

		new TLogUtil().set(new ShuttleSetter() {
			@Override
			public void customize(TLogSentinelShuttle shuttle) {
				shuttle.insd_usermbr_no(insdUsermbrNo).insd_device_id(insdDeviceId).mbr_id(mbrId).device_id(deviceId)
						.product_id(prodIdTempList).imei(imei).mno_type(mno_type).usermbr_no(usermbr_no)
						.system_id(system_id).purchase_channel(purchase_channel)
						.purchase_inflow_channel(purchase_inflow_channel).purchase_id_recv(purchase_id_recv)
						.coupon_code(coupon_code).coupon_item_code(coupon_item_code).auto_payment_yn(auto_payment_yn);
			}
		});

		// 소장/대여 TAB으로 구매요청이 아닌 상품 구매 시
		if (StringUtils.isNotBlank(notifyPaymentReq.getProdId())
				&& StringUtils.isNotBlank(reservedDataMap.get("ownPid"))
				&& StringUtils.isNotBlank(reservedDataMap.get("loanPid"))
				&& (StringUtils.equals(notifyPaymentReq.getProdId(), prchsDtlMore.getProdId()) == false)) {

			if (StringUtils.equals(notifyPaymentReq.getProdId(), reservedDataMap.get("ownPid")) == false
					&& StringUtils.equals(notifyPaymentReq.getProdId(), reservedDataMap.get("loanPid")) == false) {
				throw new StorePlatformException("SAC_PUR_7101");
			}
			prchsDtlMore.setUsePeriodUnitCd(reservedDataMap.get("usePeriodUnitCd"));
			prchsDtlMore.setUsePeriod(StringUtils.defaultString(reservedDataMap.get("usePeriod"), "0"));
			prchsDtlMore.setProdId(notifyPaymentReq.getProdId());
			prchsDtlMore.setProdAmt(notifyPaymentReq.getTotAmt());
			prchsDtlMore.setTotAmt(notifyPaymentReq.getTotAmt());
		}

		// ------------------------------------------------------------------------------
		// 결제 금액 체크

		double checkAmt = 0.0;
		for (PaymentInfo paymentInfo : notifyPaymentReq.getPaymentInfoList()) {
			checkAmt += paymentInfo.getPaymentAmt();
		}
		if (checkAmt != prchsDtlMore.getTotAmt().doubleValue()) {
			throw new StorePlatformException("SAC_PUR_5106", checkAmt, prchsDtlMore.getTotAmt().doubleValue());
		} else if (prchsDtlMore.getTotAmt().doubleValue() != notifyPaymentReq.getTotAmt()) {
			throw new StorePlatformException("SAC_PUR_5106", notifyPaymentReq.getTotAmt(), prchsDtlMore.getTotAmt()
					.doubleValue());
		}

		// ------------------------------------------------------------------------------
		// 오퍼링 ID 체크

		String offeringId = null;

		if (StringUtils.isNotBlank(prchsDtlMore.getResvCol01())) { // 오퍼링 제공으로 예약된 경우
			if (StringUtils.equals(notifyPaymentReq.getOfferingYn(), PurchaseConstants.USE_N) == false) {
				offeringId = prchsDtlMore.getResvCol01();
			}
		}

		// -------------------------------------------------------------------------------------------
		// 쇼핑상품 쿠폰 발급요청

		String tstoreNotiPublishType = PurchaseConstants.TSTORE_NOTI_PUBLISH_TYPE_SYNC;

		List<ShoppingCouponPublishInfo> shoppingCouponList = null;

		if (prchsDtlMore.getTenantProdGrpCd().startsWith(PurchaseConstants.TENANT_PRODUCT_GROUP_SHOPPING)) {
			CouponPublishEcRes couponPublishEcRes = this.purchaseShoppingOrderRepository.createCouponPublish(
					prchsDtlMore.getPrchsId(), reservedDataMap.get("useDeviceId"), reservedDataMap.get("deviceId"),
					reservedDataMap.get("couponCode"), reservedDataMap.get("itemCode"), prchsDtlMore.getProdQty());
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

		prchsDtlMore.setPrchsResvDesc(prchsDtlMore.getPrchsResvDesc() + "&tstoreNotiPublishType="
				+ tstoreNotiPublishType); // 후처리 시 사용 : -> 구매완료Noti

		// -------------------------------------------------------------------------------------------
		// 게임캐쉬 충전 예약 및 확정

		List<TStoreCashChargeReserveDetailEcRes> cashReserveResList = null;

		if (StringUtils.startsWith(prchsDtlMore.getTenantProdGrpCd(),
				PurchaseConstants.TENANT_PRODUCT_GROUP_DTL_GAMECASH_FIXRATE)) {
			// 보너스 Point 정보 계산
			double bonusPointAmt = 0.0;
			String bonusPointUseExprDt = null;
			if (Integer.parseInt(reservedDataMap.get("bonusPoint")) > 0) { // 보너스 Point
				bonusPointAmt = Double.parseDouble(reservedDataMap.get("bonusPoint"));
				bonusPointUseExprDt = this.calculateUseDate(prchsDtlMore.getUseStartDt(),
						reservedDataMap.get("bonusPointUsePeriodUnitCd"), reservedDataMap.get("bonusPointUsePeriod"));
			}

			// 충전 예약
			cashReserveResList = this.purchaseOrderTstoreService.reserveGameCashCharge(
					prchsDtlMore.getUseInsdUsermbrNo(), prchsDtlMore.getProdAmt().doubleValue(),
					prchsDtlMore.getUseExprDt(), bonusPointAmt, bonusPointUseExprDt);

			// 충전 확정
			if (CollectionUtils.isNotEmpty(cashReserveResList)) {
				this.purchaseOrderTstoreService.confirmGameCashCharge(prchsDtlMore.getUseInsdUsermbrNo(),
						prchsDtlMore.getPrchsId(), cashReserveResList);
			}
		}

		// -------------------------------------------------------------------------------------------
		// 이북/코믹 전권 소장/대여 에피소드 상품 목록 조회

		List<EpisodeInfoRes> episodeList = null;

		if (StringUtils.startsWith(prchsDtlMore.getTenantProdGrpCd(),
				PurchaseConstants.TENANT_PRODUCT_GROUP_DTL_EBOOK_FIXRATE)
				|| StringUtils.startsWith(prchsDtlMore.getTenantProdGrpCd(),
						PurchaseConstants.TENANT_PRODUCT_GROUP_DTL_COMIC_FIXRATE)) {
			episodeList = this.purchaseDisplayRepository.searchEbookComicEpisodeList(tenantId,
					prchsDtlMore.getCurrencyCd(), reservedDataMap.get("useDeviceModelCd"), prchsDtlMore.getProdId(),
					reservedDataMap.get("cmpxProdClsfCd"));
		}

		// -------------------------------------------------------------------------------------------
		// 구매확정 요청 데이터 생성

		// 결제생성 요청 데이터
		List<Payment> paymentList = this.purchaseOrderMakeDataService.makePaymentList(prchsDtlMore,
				notifyPaymentReq.getPaymentInfoList(), PurchaseConstants.PRCHS_STATUS_COMPT);

		boolean bSktTest = false;
		for (Payment payment : paymentList) {
			if (StringUtils.equals(payment.getPaymentMtdCd(), PurchaseConstants.PAYMENT_METHOD_SKT_TEST_DEVICE)) {
				bSktTest = true;
				break;
			}
		}

		// 구매집계 요청 데이터 : 시험폰 결제 경우 제외
		List<PrchsProdCnt> prchsProdCntList = null;
		if (bSktTest == false) {
			this.purchaseOrderMakeDataService.makePrchsProdCntList(prchsDtlMoreList,
					PurchaseConstants.PRCHS_STATUS_COMPT);
		}

		// 자동구매 생성 요청 데이터
		this.logger.info("PRCHS,ORDER,SAC,CONFIRM,CHECKAUTO,{},{}", prchsDtlMore.getPrchsProdType(),
				reservedDataMap.get("autoPrchsYn"));
		List<AutoPrchs> autoPrchsList = null;
		if (StringUtils.equals(reservedDataMap.get("autoPrchsYn"), PurchaseConstants.USE_Y)) {
			autoPrchsList = this.purchaseOrderMakeDataService.makeAutoPrchsList(prchsDtlMore);
		}

		// 이북/코믹 전권 소장/대여 에피소드 상품 - 구매이력 생성 요청 데이터
		List<PrchsDtlMore> ebookComicEpisodeList = null;
		if (CollectionUtils.isNotEmpty(episodeList)) {
			ebookComicEpisodeList = this.purchaseOrderMakeDataService.makeEbookComicEpisodeList(prchsDtlMore,
					episodeList, reservedDataMap.get("cmpxProdClsfCd"));
		}

		// 구매확정 데이터
		ConfirmPurchaseScReq confirmPurchaseScReq = new ConfirmPurchaseScReq();
		confirmPurchaseScReq.setTenantId(prchsDtlMore.getTenantId());
		confirmPurchaseScReq.setSystemId(prchsDtlMore.getSystemId());
		confirmPurchaseScReq.setUseUserKey(prchsDtlMore.getUseInsdUsermbrNo());
		confirmPurchaseScReq.setPrchsId(prchsDtlMore.getPrchsId());
		confirmPurchaseScReq.setNetworkTypeCd(prchsDtlMore.getNetworkTypeCd());
		confirmPurchaseScReq.setOfferingId(offeringId);
		if (CollectionUtils.isNotEmpty(cashReserveResList)) {
			for (TStoreCashChargeReserveDetailEcRes chargeInfo : cashReserveResList) {
				if (StringUtils.equals(chargeInfo.getCashCls(), "02")) { // T store Cash 충전형 - 01 : Point, 02 : Cash
					confirmPurchaseScReq.setGameCashCashId(chargeInfo.getIdentifier());
				} else if (StringUtils.equals(chargeInfo.getCashCls(), "01")) {
					confirmPurchaseScReq.setGameCashPointId(chargeInfo.getIdentifier());
				}
			}
		}

		// 소장/대여 TAB 상품
		confirmPurchaseScReq.setProdId(prchsDtlMore.getProdId()); // 상품ID
		confirmPurchaseScReq.setProdAmt(prchsDtlMore.getProdAmt()); // 상품 가격
		confirmPurchaseScReq.setTotAmt(prchsDtlMore.getTotAmt()); // 총 결제 금액
		confirmPurchaseScReq.setUsePeriodUnitCd(prchsDtlMore.getUsePeriodUnitCd()); // 사용기간 단위
		confirmPurchaseScReq.setUsePeriod(prchsDtlMore.getUsePeriod()); // 사용기간 값

		confirmPurchaseScReq.setPrchsProdCntList(prchsProdCntList); // 건수집계
		confirmPurchaseScReq.setPaymentList(paymentList); // 결제
		confirmPurchaseScReq.setAutoPrchsList(autoPrchsList); // 자동구매
		confirmPurchaseScReq.setShoppingCouponList(shoppingCouponList); // 쇼핑발급 목록
		confirmPurchaseScReq.setEbookComicEpisodeList(ebookComicEpisodeList);

		// -------------------------------------------------------------------------------------------
		// 구매확정 요청

		Exception checkException = null;
		try {

			ConfirmPurchaseScRes confirmPurchaseScRes = this.purchaseOrderSCI.confirmPurchase(confirmPurchaseScReq);
			this.logger.info("PRCHS,ORDER,SAC,CONFIRM,CNT,{}", confirmPurchaseScRes.getCount());

		} catch (StorePlatformException e) {
			checkException = e;
			throw (this.isDuplicateKeyException(e) ? new StorePlatformException("SAC_PUR_6110") : e); // 중복된 구매요청 체크

		} catch (DuplicateKeyException e) {
			checkException = e;
			throw new StorePlatformException("SAC_PUR_6110");

		} catch (Exception e) {
			checkException = e;
			throw new StorePlatformException("SAC_PUR_7202", e);

		} finally {
			// 쇼핑쿠폰발급 취소, 게임캐쉬 충전 취소 등
			if (checkException != null) {
				this.revertToPreConfirm(prchsDtlMoreList, cashReserveResList);
			}
		}

		// -------------------------------------------------------------------------------------------
		// 구매완료 TLog 상품 별 로깅

		for (PrchsDtlMore prchsInfo : prchsDtlMoreList) {
			final List<String> prodIdList = new ArrayList<String>();
			prodIdList.add(prchsInfo.getProdId());
			final String purchase_prod_num = String.valueOf(prchsInfo.getPrchsDtlId());
			final String purchase_prod_num_recv = StringUtils.equals(prchsInfo.getPrchsCaseCd(),
					PurchaseConstants.PRCHS_CASE_GIFT_CD) ? String.valueOf(prchsInfo.getPrchsDtlId()) : "";
			final String tid = prchsInfo.getTid();
			final String tx_id = prchsInfo.getTxId();
			final String use_start_time = prchsInfo.getUseStartDt();
			final String use_end_time = prchsInfo.getUseExprDt();
			final String download_expired_time = prchsInfo.getDwldExprDt();
			final Long product_qty = (long) prchsInfo.getProdQty();
			final String coupon_publish_code = prchsInfo.getCpnPublishCd();

			new TLogUtil().log(new ShuttleSetter() {
				@Override
				public void customize(TLogSentinelShuttle shuttle) {
					shuttle.product_id(prodIdList).purchase_prod_num(purchase_prod_num)
							.purchase_prod_num_recv(purchase_prod_num_recv).tid(tid).tx_id(tx_id)
							.use_start_time(use_start_time).use_end_time(use_end_time)
							.download_expired_time(download_expired_time).product_qty(product_qty)
							.coupon_publish_code(coupon_publish_code).result_code("SUCC");
				}
			});
		}

		this.logger.info("PRCHS,ORDER,SAC,CONFIRM,END");
		return prchsDtlMoreList;
	}

	/*
	 * 
	 * <pre> 구매 확정 취소 작업. </pre>
	 * 
	 * @param prchsDtlMoreList 구매 정보
	 * 
	 * @param cashReserveResList 게임캐쉬 충전예약 결과 정보 목록
	 */
	private void revertToPreConfirm(List<PrchsDtlMore> prchsDtlMoreList,
			List<TStoreCashChargeReserveDetailEcRes> cashReserveResList) {

		PrchsDtlMore prchsDtlMore = prchsDtlMoreList.get(0);

		// -------------------------------------------------------------------------------------
		// 쇼핑 쿠폰 발급 취소

		if (StringUtils.startsWith(prchsDtlMore.getTenantProdGrpCd(), PurchaseConstants.TENANT_PRODUCT_GROUP_SHOPPING)) {
			try {
				this.purchaseShoppingOrderRepository.cancelCouponPublish(prchsDtlMore.getPrchsId());
			} catch (Exception e) {
				// 이 때 발생하는 예외는 로깅만.
				this.logger.info("PRCHS,ORDER,SAC,REVERT,COUPON,ERROR,{}", e.getMessage());
			}
		}

		// -------------------------------------------------------------------------------------
		// 게임캐쉬 충전 취소

		if (CollectionUtils.isNotEmpty(cashReserveResList)) {
			try {
				this.purchaseOrderTstoreService.cancelGameCashCharge(prchsDtlMore.getUseInsdUsermbrNo(),
						prchsDtlMore.getPrchsId(), cashReserveResList);
			} catch (Exception e) {
				// 이 때 발생하는 예외는 로깅만.
				this.logger.info("PRCHS,ORDER,SAC,REVERT,GAMECASH,ERROR,{}", e.getMessage());
			}
		}

	}

	/*
	 * <pre> 새로운 구매ID 생성. </pre>
	 * 
	 * @return 새로 생성된 구매ID
	 */
	private String makePrchsId(String sequence, String date) {
		// yyMMddhhmmss(12) + 서버ID(2) + 인스턴스ID(2) + 구매시퀀스(4)
		StringBuffer sbPrchsId = new StringBuffer(20);
		sbPrchsId.append(date.substring(2)).append(this.hostNum).append(this.instanceNum)
				.append(StringUtils.leftPad(sequence, 4, "0"));
		return sbPrchsId.toString();
	}

	// =========================================================================================================================================================================
	// RE~

	/*
	 * 
	 * <pre> 구매 예약 정보 목록 조회. </pre>
	 * 
	 * @param tenantId 테넌트 ID
	 * 
	 * @param prchsId 조회할 구매 ID
	 * 
	 * @return 구매 예약 정보 목록
	 */
	private List<PrchsDtlMore> searchReservedPurchaseList(String tenantId, String prchsId) {
		SearchReservedPurchaseListScReq reqSearch = new SearchReservedPurchaseListScReq();
		reqSearch.setTenantId(tenantId);
		reqSearch.setPrchsId(prchsId);

		SearchReservedPurchaseListScRes searchPurchaseListRes = this.purchaseOrderSearchSCI
				.searchReservedPurchaseList(reqSearch);
		List<PrchsDtlMore> prchsDtlMoreList = searchPurchaseListRes.getPrchsDtlMoreList();
		if (prchsDtlMoreList.size() < 1) {
			throw new StorePlatformException("SAC_PUR_7101");
		}

		return prchsDtlMoreList;
	}

	/*
	 * 
	 * <pre> 결제수단 별 OCB 적립율 산정. </pre>
	 * 
	 * @param telecom 통신사
	 * 
	 * @param tenantProdGrpCd 테넌트 상품 그룹 코드
	 * 
	 * @param sktTestOrSkpCorp 시험폰 또는 SKP법인폰 여부
	 * 
	 * @return 결제수단 별 OCB 적립율
	 */
	private String adjustOcbSaveInfo(String telecom, String tenantProdGrpCd, boolean sktTestOrSkpCorp) {

		// 쇼핑상품, VOD정액제 상품, 게임캐쉬 정액 상품 제외
		if (StringUtils.startsWith(tenantProdGrpCd, PurchaseConstants.TENANT_PRODUCT_GROUP_SHOPPING)
				|| StringUtils.startsWith(tenantProdGrpCd, PurchaseConstants.TENANT_PRODUCT_GROUP_DTL_MOVIE_FIXRATE)
				|| StringUtils.startsWith(tenantProdGrpCd, PurchaseConstants.TENANT_PRODUCT_GROUP_DTL_TV_FIXRATE)
				|| StringUtils.startsWith(tenantProdGrpCd, PurchaseConstants.TENANT_PRODUCT_GROUP_DTL_GAMECASH_FIXRATE)) {
			return "";

		} else {
			StringBuffer sbOcbAccum = new StringBuffer(64);

			if (StringUtils.equals(telecom, PurchaseConstants.TELECOM_SKT)) {
				sbOcbAccum.append(sktTestOrSkpCorp ? "11:0.0;" : "11:4.0;"); // 시험폰, SKP법인폰 결제 제외
			} else {
				sbOcbAccum.append("12:4.0;"); // 다날
			}

			sbOcbAccum.append("13:4.0;14:4.0;25:4.0"); // 신용카드, PayPin, T store Cash

			return sbOcbAccum.toString();
		}
	}

	/*
	 * 
	 * <pre> 결제Page 템플릿 코드 정의. </pre>
	 * 
	 * @param prchsCaseCd 구매/선물 구분 코드
	 * 
	 * @param tenantProdGrpCd 테넌트 상품 그룹 코드
	 * 
	 * @param prchsProdCnt 구매하는 상품 갯수
	 * 
	 * @return 결제Page 템플릿 코드
	 */
	private String adjustPaymentPageTemplate(String prchsCaseCd, String tenantProdGrpCd, int prchsProdCnt) {
		if (StringUtils.equals(prchsCaseCd, PurchaseConstants.PRCHS_CASE_GIFT_CD)) {
			return PurchaseConstants.PAYMENT_PAGE_TEMPLATE_GIFT; // 선물: TC06

		} else {
			if (StringUtils.startsWith(tenantProdGrpCd, PurchaseConstants.TENANT_PRODUCT_GROUP_SHOPPING)) {
				return PurchaseConstants.PAYMENT_PAGE_TEMPLATE_SHOPPING; // 쇼핑: TC05

			} else if (StringUtils
					.startsWith(tenantProdGrpCd, PurchaseConstants.TENANT_PRODUCT_GROUP_DTL_MOVIE_FIXRATE)
					|| StringUtils.startsWith(tenantProdGrpCd, PurchaseConstants.TENANT_PRODUCT_GROUP_DTL_TV_FIXRATE)) {
				return PurchaseConstants.PAYMENT_PAGE_TEMPLATE_AUTOPAY; // 자동결제: TC04

			} else if (prchsProdCnt == 1
					&& (StringUtils.startsWith(tenantProdGrpCd, PurchaseConstants.TENANT_PRODUCT_GROUP_VOD) || StringUtils
							.startsWith(tenantProdGrpCd, PurchaseConstants.TENANT_PRODUCT_GROUP_EBOOKCOMIC))
					&& StringUtils.endsWith(tenantProdGrpCd, PurchaseConstants.TENANT_PRODUCT_GROUP_SUFFIX_FIXRATE) == false) {
				return PurchaseConstants.PAYMENT_PAGE_TEMPLATE_LOAN_OWN; // 대여/소장: TC03

			} else if (StringUtils.startsWith(tenantProdGrpCd,
					PurchaseConstants.TENANT_PRODUCT_GROUP_DTL_GAMECASH_FIXRATE)) {
				return PurchaseConstants.PAYMENT_PAGE_TEMPLATE_GAMECASH_FIXRATE; // 정액제(게임캐쉬): TC02

			} else {
				return PurchaseConstants.PAYMENT_PAGE_TEMPLATE_NORMAL; // 일반: TC01
			}
		}
	}

	/*
	 * 
	 * <pre> SKT 결제 정책 체크. </pre>
	 * 
	 * @param prchsDtlMore 구매 정보
	 * 
	 * @param telecom 통신사
	 * 
	 * @param payDeviceId 결제 MDN
	 * 
	 * @param useDeviceId 이용 MDN
	 * 
	 * @return 결제 정책 체크 결과
	 */
	private SktPaymentPolicyCheckResult checkSktPaymentPolicy(PrchsDtlMore prchsDtlMore, String telecom,
			String payDeviceId, String useDeviceId) {
		if (StringUtils.equals(telecom, PurchaseConstants.TELECOM_SKT) == false) {
			return null;
		}

		SktPaymentPolicyCheckParam policyCheckParam = new SktPaymentPolicyCheckParam();
		policyCheckParam.setDeviceId(payDeviceId);
		policyCheckParam.setPaymentTotAmt(prchsDtlMore.getTotAmt());
		policyCheckParam.setTenantProdGrpCd(prchsDtlMore.getTenantProdGrpCd());
		if (StringUtils.equals(prchsDtlMore.getPrchsCaseCd(), PurchaseConstants.PRCHS_CASE_GIFT_CD)) {
			policyCheckParam.setTenantId(prchsDtlMore.getTenantId());
			policyCheckParam.setUserKey(prchsDtlMore.getSendInsdUsermbrNo());
			policyCheckParam.setDeviceKey(prchsDtlMore.getSendInsdDeviceId());
			policyCheckParam.setRecvTenantId(prchsDtlMore.getUseTenantId());
			policyCheckParam.setRecvUserKey(prchsDtlMore.getUseInsdUsermbrNo());
			policyCheckParam.setRecvDeviceKey(prchsDtlMore.getUseInsdDeviceId());
			policyCheckParam.setRecvDeviceId(useDeviceId);
		} else {
			policyCheckParam.setTenantId(prchsDtlMore.getTenantId());
			policyCheckParam.setUserKey(prchsDtlMore.getUseInsdUsermbrNo());
			policyCheckParam.setDeviceKey(prchsDtlMore.getUseInsdDeviceId());
		}

		try {
			return this.purchaseOrderPolicyService.checkSktPaymentPolicy(policyCheckParam);
		} catch (Exception e) {
			throw new StorePlatformException("SAC_PUR_7209", e);
		}
	}

	/*
	 * 
	 * <pre> 결제 수단 재정의. </pre>
	 * 
	 * @param sktPaymethodInfo SKT결제 재정의 정보
	 * 
	 * @param tenantId 테넌트ID
	 * 
	 * @param tenantProdGrpCd 테넌트 상품 그룹 코드
	 * 
	 * @param payAmt 결제할 금액
	 * 
	 * @return 재정의 된 결제 수단 정보
	 */
	private String adjustPaymethod(String sktPaymethodInfo, String tenantId, String tenantProdGrpCd, double payAmt) {
		// 결제수단 별 가능 거래금액/비율 조정 정보
		String paymentAdjustInfo = this.purchaseOrderPolicyService.getAvailablePaymethodAdjustInfo(tenantId,
				tenantProdGrpCd);
		if (paymentAdjustInfo == null) {
			throw new StorePlatformException("SAC_PUR_7103");
		}

		StringBuffer sbPaymethodInfo = new StringBuffer(64);

		if (StringUtils.isNotBlank(sktPaymethodInfo)) {
			sbPaymethodInfo.append(sktPaymethodInfo).append(";12:0:0");
		} else {
			sbPaymethodInfo.append("11:0:0");
		}
		sbPaymethodInfo.append(";").append(paymentAdjustInfo.replaceAll("MAXAMT", String.valueOf(payAmt)));

		return sbPaymethodInfo.toString();
	}

	/*
	 * 
	 * <pre> 판매자 회원 정보 조회 - 쇼핑 상품인 경우에만 조회. </pre>
	 * 
	 * @param sellerKey 판매자 내부 회원 번호
	 * 
	 * @return 판매자 정보
	 */
	private SellerMbrSac searchSellerInfo(String sellerKey) {
		SellerMbrSac sellerMbrSac = null;

		if (StringUtils.isNotBlank(sellerKey)) {
			sellerMbrSac = this.purchaseMemberRepository.searchSellerInfo(sellerKey);
		}

		if (sellerMbrSac == null) {
			sellerMbrSac = new SellerMbrSac();
			sellerMbrSac.setSellerNickName(PurchaseConstants.SHOPPING_SELLER_DEFAULT_NAME); // 판매자명
			sellerMbrSac.setSellerEmail(PurchaseConstants.SHOPPING_SELLER_DEFAULT_EMAIL); // 판매자 이메일 주소
			sellerMbrSac.setRepPhone(PurchaseConstants.SHOPPING_SELLER_DEFAULT_TEL); // 판매자 전화번호
		}

		return sellerMbrSac;
	}

	/*
	 * 
	 * <pre> 기준일로부터 이용 일자 계산. </pre>
	 * 
	 * @param startDt 기준일(시작일)
	 * 
	 * @param periodUnitCd 이용기간 단위 코드
	 * 
	 * @param periodVal 이용기간 값
	 * 
	 * @return 계산된 이용 일자
	 */
	private String calculateUseDate(String startDt, String periodUnitCd, String periodVal) {

		if (StringUtils.equals(periodUnitCd, PurchaseConstants.PRODUCT_USE_PERIOD_UNIT_UNLIMITED)) { // 무제한
			return "99991231235959";
		} else if (StringUtils.equals(periodUnitCd, PurchaseConstants.PRODUCT_USE_PERIOD_UNIT_SELECT)) { // 기간선택
			return periodVal;
		} else if (StringUtils.equals(periodUnitCd, PurchaseConstants.PRODUCT_USE_PERIOD_UNIT_CURR_DATE)) { // 당일
			return startDt.substring(0, 8) + "235959";
		} else if (StringUtils.equals(periodUnitCd, PurchaseConstants.PRODUCT_USE_PERIOD_UNIT_CURR_YEAR)) { // 당년
			return startDt.substring(0, 4) + "1231235959";
		} else {
			Date checkDate = null;
			try {
				checkDate = DateUtils.parseDate(startDt, "yyyyMMddHHmmss");
			} catch (ParseException e) {
				throw new StorePlatformException("SAC_PUR_7216", startDt);
			}

			if (StringUtils.equals(periodUnitCd, PurchaseConstants.PRODUCT_USE_PERIOD_UNIT_DATE)) { // 일
				checkDate = DateUtils.addSeconds(DateUtils.addDays(checkDate, Integer.parseInt(periodVal)), -1);
			} else if (StringUtils.equals(periodUnitCd, PurchaseConstants.PRODUCT_USE_PERIOD_UNIT_HOUR)) { // 시간
				checkDate = DateUtils.addSeconds(DateUtils.addHours(checkDate, Integer.parseInt(periodVal)), -1);
			} else if (StringUtils.equals(periodUnitCd, PurchaseConstants.PRODUCT_USE_PERIOD_UNIT_MONTH)) { // 월
				checkDate = DateUtils.addSeconds(DateUtils.addMonths(checkDate, Integer.parseInt(periodVal)), -1);
			} else if (StringUtils.equals(periodUnitCd, PurchaseConstants.PRODUCT_USE_PERIOD_UNIT_YEAR)) { // 년
				checkDate = DateUtils.addSeconds(DateUtils.addYears(checkDate, Integer.parseInt(periodVal)), -1);
			} else if (StringUtils.equals(periodUnitCd, PurchaseConstants.PRODUCT_USE_PERIOD_UNIT_CURR_MONTH)) { // 당월
				checkDate = DateUtils.addSeconds(DateUtils.ceiling(checkDate, Calendar.MONTH), -1);
			} else {
				throw new StorePlatformException("SAC_PUR_7215", periodUnitCd);
			}

			return DateFormatUtils.format(checkDate, "yyyyMMddHHmmss");
		}
	}

	/*
	 * 
	 * <pre> DB PK 오류 여부 체크. </pre>
	 * 
	 * @param e 발생한 Exception 개체
	 * 
	 * @return DB PK 오류 여부
	 */
	private boolean isDuplicateKeyException(Exception e) {
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
