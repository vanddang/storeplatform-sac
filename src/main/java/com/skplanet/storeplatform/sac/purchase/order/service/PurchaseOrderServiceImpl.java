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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.skplanet.pdp.sentinel.shuttle.TLogSentinelShuttle;
import com.skplanet.storeplatform.external.client.shopping.vo.CouponPublishEcRes;
import com.skplanet.storeplatform.external.client.shopping.vo.CouponPublishItemDetailEcRes;
import com.skplanet.storeplatform.external.client.tstore.vo.TStoreCashChargeReserveDetailEcRes;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.exception.vo.ErrorInfo;
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

	@Autowired
	private MessageSourceAccessor messageSourceAccessor;
	@Autowired
	@Qualifier("scPurchase")
	private MessageSourceAccessor scMessageSourceAccessor;

	@Autowired
	private PurchaseOrderSCI purchaseOrderSCI;
	@Autowired
	private PurchaseOrderSearchSCI purchaseOrderSearchSCI;
	@Autowired
	private PurchaseOrderAssistService purchaseOrderAssistService;
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
	public int freePurchase(PurchaseOrderInfo purchaseOrderInfo) {
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
			purchaseOrderInfo.setPrchsId(this.purchaseOrderAssistService.makePrchsId(
					searchPurchaseSequenceAndDateRes.getNextSequence(), searchPurchaseSequenceAndDateRes.getNowDate()));
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
			throw (this.purchaseOrderAssistService.isDuplicateKeyException(e) ? new StorePlatformException(
					"SAC_PUR_6110") : e); // 중복된 구매요청 체크

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
				final String use_end_time = this.purchaseOrderAssistService.calculateUseDate(prchsInfo.getPrchsDt(),
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
	public void reservePurchase(PurchaseOrderInfo purchaseOrderInfo) {
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
			purchaseOrderInfo.setPrchsId(this.purchaseOrderAssistService.makePrchsId(
					searchPurchaseSequenceAndDateRes.getNextSequence(), searchPurchaseSequenceAndDateRes.getNowDate()));
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

		StorePlatformException checkException = null;

		try {
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

		} catch (StorePlatformException e) {
			checkException = e;
			throw e;

		} finally {
			// 구매예약 TLog 로깅
			ErrorInfo errorInfo = (checkException != null ? checkException.getErrorInfo() : null);

			final String result_code = (errorInfo != null ? errorInfo.getCode() : "SUCC");
			final String result_message = (errorInfo != null ? this.messageSourceAccessor.getMessage(result_code) : "");
			final String exception_log = (errorInfo != null ? (errorInfo.getCause() == null ? "" : errorInfo.getCause()
					.toString()) : "");

			final String mbr_id = purchaseOrderInfo.getPurchaseUser().getUserId();
			final String device_id = purchaseOrderInfo.getPurchaseUser().getDeviceId();
			final String purchase_id = purchaseOrderInfo.getPrchsId();

			final List<String> product_id_list = new ArrayList<String>();
			final List<String> product_name_list = new ArrayList<String>();
			final List<Long> product_price_list = new ArrayList<Long>();
			final Long product_qty = (long) purchaseOrderInfo.getPurchaseProductList().size();

			for (PurchaseProduct product : purchaseOrderInfo.getPurchaseProductList()) {
				product_id_list.add(product.getProdId());
				product_name_list.add(product.getProdNm());
				product_price_list.add(product.getProdAmt().longValue());
			}

			new TLogUtil().log(new ShuttleSetter() {
				@Override
				public void customize(TLogSentinelShuttle shuttle) {
					shuttle.log_id(PurchaseConstants.TLOG_ID_PURCHASE_ORDER_RESERVE).mbr_id(mbr_id)
							.device_id(device_id).purchase_id(purchase_id).product_id(product_id_list)
							.product_name(product_name_list).product_price(product_price_list).product_qty(product_qty)
							.result_code(result_code);
					if (StringUtils.equals(result_code, "SUCC") == false) {
						shuttle.result_message(result_message).exception_log(exception_log);
					}
				}
			});
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

		// res.setFlgBlockPayment(this.purchaseOrderPolicyService.isBlockPayment(prchsDtlMore.getTenantId(),
		// reservedDataMap.get("deviceId"), prchsDtlMore.getTenantProdGrpCd()) ?
		// PurchaseConstants.VERIFYORDER_BLOCK_PAYMENT : PurchaseConstants.VERIFYORDER_ALLOW_PAYMENT);

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
				verifyOrderInfo.getSystemId(), prchsDtlMore.getTenantProdGrpCd(), reservedDataMap.get("prodCaseCd"),
				prchsDtlMore.getTotAmt().doubleValue()));

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
		if (StringUtils.equals(prchsDtlMore.getTenantProdGrpCd().substring(8, 12), "DP01")
				&& StringUtils.endsWith(prchsDtlMore.getTenantProdGrpCd(),
						PurchaseConstants.TENANT_PRODUCT_GROUP_SUFFIX_UNIT)) {
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
				prchsDtlMore.getTenantProdGrpCd(), reservedDataMap.get("cmpxProdClsfCd"), prchsDtlMoreList.size()));

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
		// 자동결제 상품 - 다음 자동 결제일
		if (StringUtils.equals(reservedDataMap.get("autoPrchsYn"), PurchaseConstants.USE_Y)) {
			String afterAutoPayDt = this.purchaseOrderAssistService.calculateUseDate(prchsDtlMore.getUseStartDt(),
					reservedDataMap.get("autoPrchsPeriodUnitCd"),
					StringUtils.defaultString(reservedDataMap.get("autoPrchsPeriodValue"), "0"));
			res.setAfterAutoPayDt(afterAutoPayDt.substring(0, 8) + "000000"); // 다음 자동 결제일
		}
		// VOD 정액제 이용권 (ex, 30일 이용권) - 만료예정일
		if (StringUtils.equals(reservedDataMap.get("cmpxProdClsfCd"), PurchaseConstants.FIXRATE_PROD_TYPE_VOD_FIXRATE)
				&& (StringUtils.equals(reservedDataMap.get("autoPrchsYn"), PurchaseConstants.USE_Y) == false)) {
			res.setUseExprDt(prchsDtlMore.getUseExprDt());
		}
		// 시리즈 전회차 이용권 - 이용시작일, 이용종료일
		if (StringUtils.equals(reservedDataMap.get("cmpxProdClsfCd"),
				PurchaseConstants.FIXRATE_PROD_TYPE_VOD_SERIESPASS)) {
			res.setUseStartDt(prchsDtlMore.getUseStartDt());
			res.setUseExprDt(prchsDtlMore.getUseExprDt());
		}
		// 게임캐쉬
		if (StringUtils.startsWith(prchsDtlMore.getTenantProdGrpCd(),
				PurchaseConstants.TENANT_PRODUCT_GROUP_DTL_GAMECASH_FIXRATE)) {
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

			// 쇼핑상품 종류
			res.setProdKind(reservedDataMap.get("prodCaseCd"));
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
	public List<PrchsDtlMore> confirmPurchase(NotifyPaymentSacReq notifyPaymentReq, String tenantId) {
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
		// final String insdUsermbrNo = StringUtils.equals(prchsDtlMore.getPrchsCaseCd(),
		// PurchaseConstants.PRCHS_CASE_PURCHASE_CD) ? prchsDtlMore.getUseInsdUsermbrNo() : prchsDtlMore
		// .getSendInsdUsermbrNo();
		// final String insdDeviceId = StringUtils.equals(prchsDtlMore.getPrchsCaseCd(),
		// PurchaseConstants.PRCHS_CASE_PURCHASE_CD) ? prchsDtlMore.getUseInsdDeviceId() : prchsDtlMore
		// .getSendInsdDeviceId();
		// final String mbrId = reservedDataMap.get("userId");
		// final String deviceId = reservedDataMap.get("deviceId");
		// final String imei = reservedDataMap.get("imei");
		// final String mno_type = StringUtils.equals(reservedDataMap.get("telecom"), PurchaseConstants.TELECOM_SKT) ?
		// "SKT" : (StringUtils
		// .equals(reservedDataMap.get("telecom"), PurchaseConstants.TELECOM_KT) ? "KT" : (StringUtils.equals(
		// reservedDataMap.get("telecom"), PurchaseConstants.TELECOM_UPLUS) ? "U+" : "")); // SKT, KT, U+
		// final String usermbr_no = reservedDataMap.get("userKey");
		// final String system_id = prchsDtlMore.getSystemId();
		// final String purchase_channel = prchsDtlMore.getPrchsReqPathCd();
		// final String purchase_inflow_channel = StringUtils.equals(prchsDtlMore.getPrchsCaseCd(),
		// PurchaseConstants.PRCHS_CASE_PURCHASE_CD) ? "FDS00201" : "FDS00202";
		// final String purchase_id_recv = StringUtils.equals(prchsDtlMore.getPrchsCaseCd(),
		// PurchaseConstants.PRCHS_CASE_GIFT_CD) ? prchsDtlMore.getPrchsId() : "";
		// final String coupon_code = reservedDataMap.get("couponCode");
		// final String coupon_item_code = reservedDataMap.get("itemCode");
		// final String auto_payment_yn = StringUtils.defaultIfBlank(reservedDataMap.get("autoPrchsYn"), "N");
		// final List<String> prodIdTempList = new ArrayList<String>();
		// for (PrchsDtlMore tempPrchsDtlMore : prchsDtlMoreList) {
		// prodIdTempList.add(tempPrchsDtlMore.getProdId());
		// }
		//
		// new TLogUtil().set(new ShuttleSetter() {
		// @Override
		// public void customize(TLogSentinelShuttle shuttle) {
		// shuttle.insd_usermbr_no(insdUsermbrNo).insd_device_id(insdDeviceId).mbr_id(mbrId).device_id(deviceId)
		// .product_id(prodIdTempList).imei(imei).mno_type(mno_type).usermbr_no(usermbr_no)
		// .system_id(system_id).purchase_channel(purchase_channel)
		// .purchase_inflow_channel(purchase_inflow_channel).purchase_id_recv(purchase_id_recv)
		// .coupon_code(coupon_code).coupon_item_code(coupon_item_code).auto_payment_yn(auto_payment_yn);
		// }
		// });

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
				bonusPointUseExprDt = this.purchaseOrderAssistService.calculateUseDate(prchsDtlMore.getUseStartDt(),
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
			prchsProdCntList = this.purchaseOrderMakeDataService.makePrchsProdCntList(prchsDtlMoreList,
					PurchaseConstants.PRCHS_STATUS_COMPT);
		}

		// 자동구매 생성 요청 데이터
		this.logger.info("PRCHS,ORDER,SAC,CONFIRM,CHECKAUTO,{},{}", prchsDtlMore.getPrchsProdType(),
				reservedDataMap.get("autoPrchsYn"));
		List<AutoPrchs> autoPrchsList = null;
		if (StringUtils.equals(reservedDataMap.get("autoPrchsYn"), PurchaseConstants.USE_Y)) {
			autoPrchsList = this.purchaseOrderMakeDataService.makeAutoPrchsList(prchsDtlMore,
					reservedDataMap.get("deviceModelCd"));
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

		StorePlatformException checkException = null;
		try {

			ConfirmPurchaseScRes confirmPurchaseScRes = this.purchaseOrderSCI.confirmPurchase(confirmPurchaseScReq);
			this.logger.info("PRCHS,ORDER,SAC,CONFIRM,CNT,{}", confirmPurchaseScRes.getCount());

		} catch (StorePlatformException e) {
			// 중복된 구매요청 체크
			checkException = (this.purchaseOrderAssistService.isDuplicateKeyException(e) ? new StorePlatformException(
					"SAC_PUR_6110") : e);
			throw checkException;

		} catch (DuplicateKeyException e) {
			checkException = new StorePlatformException("SAC_PUR_6110");
			throw checkException;

		} catch (Exception e) {
			checkException = new StorePlatformException("SAC_PUR_7202", e);
			throw checkException;

		} finally {
			// 쇼핑쿠폰발급 취소, 게임캐쉬 충전 취소 등
			if (checkException != null) {
				this.revertToPreConfirm(prchsDtlMoreList, cashReserveResList);
			}

			// 구매완료 TLog 상품 별 로깅
			ErrorInfo errorInfo = (checkException != null ? checkException.getErrorInfo() : null);

			String msg = null;
			if (errorInfo != null) {
				try {
					if (StringUtils.startsWith(errorInfo.getCode(), "SC_")) {
						msg = this.scMessageSourceAccessor.getMessage(errorInfo.getCode());
					} else {
						msg = this.messageSourceAccessor.getMessage(errorInfo.getCode());
					}
				} catch (NoSuchMessageException e) {
					msg = "";
				}
			}

			final String result_code = (errorInfo != null ? errorInfo.getCode() : "SUCC");
			final String result_message = msg;
			final String exception_log = (errorInfo != null ? (errorInfo.getCause() == null ? "" : errorInfo.getCause()
					.toString()) : "");

			final String purchase_id = prchsDtlMore.getPrchsId();
			final String insd_usermbr_no = StringUtils.equals(prchsDtlMore.getPrchsCaseCd(),
					PurchaseConstants.PRCHS_CASE_PURCHASE_CD) ? prchsDtlMore.getUseInsdUsermbrNo() : prchsDtlMore
					.getSendInsdUsermbrNo();
			final String insd_device_id = StringUtils.equals(prchsDtlMore.getPrchsCaseCd(),
					PurchaseConstants.PRCHS_CASE_PURCHASE_CD) ? prchsDtlMore.getUseInsdDeviceId() : prchsDtlMore
					.getSendInsdDeviceId();
			final String mbr_id = reservedDataMap.get("userId");
			final String device_id = reservedDataMap.get("deviceId");
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
						shuttle.log_id(PurchaseConstants.TLOG_ID_PURCHASE_ORDER_RESULT).purchase_id(purchase_id)
								.insd_usermbr_no(insd_usermbr_no).insd_device_id(insd_device_id).mbr_id(mbr_id)
								.device_id(device_id).imei(imei).mno_type(mno_type).usermbr_no(usermbr_no)
								.system_id(system_id).purchase_channel(purchase_channel)
								.purchase_inflow_channel(purchase_inflow_channel).purchase_id_recv(purchase_id_recv)
								.coupon_code(coupon_code).coupon_item_code(coupon_item_code)
								.auto_payment_yn(auto_payment_yn).product_id(prodIdList)
								.purchase_prod_num(purchase_prod_num).purchase_prod_num_recv(purchase_prod_num_recv)
								.tid(tid).tx_id(tx_id).use_start_time(use_start_time).use_end_time(use_end_time)
								.download_expired_time(download_expired_time).product_qty(product_qty)
								.coupon_publish_code(coupon_publish_code).result_code(result_code);
						if (StringUtils.equals(result_code, "SUCC") == false) {
							shuttle.result_message(result_message).exception_log(exception_log);
						}
					}
				});
			}
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
		this.logger.info("PRCHS,ORDER,SAC,REVERT");

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
				sbOcbAccum.append(sktTestOrSkpCorp ? "11:0.0;" : "11:1.0;"); // 시험폰, SKP법인폰 결제 제외
			} else {
				sbOcbAccum.append("12:1.0;"); // 다날
			}

			sbOcbAccum.append("13:1.0;14:1.0;25:1.0"); // 신용카드, PayPin, T store Cash

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
	 * @param cmpxProdClsfCd 정액상품 구분 코드
	 * 
	 * @param prchsProdCnt 구매하는 상품 갯수
	 * 
	 * @return 결제Page 템플릿 코드
	 */
	private String adjustPaymentPageTemplate(String prchsCaseCd, String tenantProdGrpCd, String cmpxProdClsfCd,
			int prchsProdCnt) {
		if (StringUtils.equals(prchsCaseCd, PurchaseConstants.PRCHS_CASE_GIFT_CD)) {
			return PurchaseConstants.PAYMENT_PAGE_TEMPLATE_GIFT; // 선물: TC06

		} else {
			if (StringUtils.startsWith(tenantProdGrpCd, PurchaseConstants.TENANT_PRODUCT_GROUP_SHOPPING)) {
				return PurchaseConstants.PAYMENT_PAGE_TEMPLATE_SHOPPING; // 쇼핑: TC05

			} else if (StringUtils.startsWith(tenantProdGrpCd, PurchaseConstants.TENANT_PRODUCT_GROUP_VOD)
					&& StringUtils.endsWith(tenantProdGrpCd, PurchaseConstants.TENANT_PRODUCT_GROUP_SUFFIX_FIXRATE)
					&& (StringUtils.equals(cmpxProdClsfCd, PurchaseConstants.FIXRATE_PROD_TYPE_VOD_SERIESPASS) == false)) {
				return PurchaseConstants.PAYMENT_PAGE_TEMPLATE_AUTOPAY; // 자동결제: TC04

				// } else if (StringUtils
				// .startsWith(tenantProdGrpCd, PurchaseConstants.TENANT_PRODUCT_GROUP_DTL_MOVIE_FIXRATE)
				// || StringUtils.startsWith(tenantProdGrpCd, PurchaseConstants.TENANT_PRODUCT_GROUP_DTL_TV_FIXRATE)) {
				// if (StringUtils.equals(cmpxProdClsfCd, PurchaseConstants.FIXRATE_PROD_TYPE_VOD_SERIESPASS) == false)
				// {
				// return PurchaseConstants.PAYMENT_PAGE_TEMPLATE_AUTOPAY; // 자동결제: TC04
				// }

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
	 * @param systemId 시스템ID
	 * 
	 * @param tenantProdGrpCd 테넌트 상품 그룹 코드
	 * 
	 * @param prodCaseCd 쇼핑 상품 종류 코드
	 * 
	 * @param payAmt 결제할 금액
	 * 
	 * @return 재정의 된 결제 수단 정보
	 */
	private String adjustPaymethod(String sktPaymethodInfo, String tenantId, String systemId, String tenantProdGrpCd,
			String prodCaseCd, double payAmt) {
		// 결제수단 별 가능 거래금액/비율 조정 정보
		String paymentAdjustInfo = this.purchaseOrderPolicyService.getAvailablePaymethodAdjustInfo(tenantId,
				tenantProdGrpCd);
		if (paymentAdjustInfo == null) {
			throw new StorePlatformException("SAC_PUR_7103");
		}

		StringBuffer sbPaymethodInfo = new StringBuffer(64);

		if (StringUtils.isNotBlank(sktPaymethodInfo)) {
			sbPaymethodInfo.append(sktPaymethodInfo).append(";12:0:0;");
		} else {
			sbPaymethodInfo.append("11:0:0;");
		}

		// PP용 : 쇼핑상품권 경우, 신용카드, 페이핀 결제수단 제외
		if (StringUtils.startsWith(systemId, "S00")
				&& StringUtils.equals(prodCaseCd, PurchaseConstants.SHOPPING_TYPE_GIFT)) {
			sbPaymethodInfo.append("13:0:0;14:0:0;");
		}

		sbPaymethodInfo.append(paymentAdjustInfo.replaceAll("MAXAMT", String.valueOf(payAmt)));

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

}
