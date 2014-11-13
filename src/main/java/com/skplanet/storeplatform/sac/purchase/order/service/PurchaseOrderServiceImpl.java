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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
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
import com.skplanet.storeplatform.external.client.shopping.vo.CouponPublishV2EcRes;
import com.skplanet.storeplatform.external.client.shopping.vo.CouponPublishV2ItemDetailEcRes;
import com.skplanet.storeplatform.external.client.tstore.vo.TStoreCashChargeReserveDetailEcRes;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.exception.vo.ErrorInfo;
import com.skplanet.storeplatform.framework.core.util.log.TLogUtil;
import com.skplanet.storeplatform.framework.core.util.log.TLogUtil.ShuttleSetter;
import com.skplanet.storeplatform.purchase.client.common.vo.AutoPrchs;
import com.skplanet.storeplatform.purchase.client.common.vo.MembershipReserve;
import com.skplanet.storeplatform.purchase.client.common.vo.Payment;
import com.skplanet.storeplatform.purchase.client.common.vo.PrchsProdCnt;
import com.skplanet.storeplatform.purchase.client.common.vo.UniqueTid;
import com.skplanet.storeplatform.purchase.client.order.sci.PurchaseOrderSCI;
import com.skplanet.storeplatform.purchase.client.order.sci.PurchaseOrderSearchSCI;
import com.skplanet.storeplatform.purchase.client.order.vo.ConfirmPurchaseScReq;
import com.skplanet.storeplatform.purchase.client.order.vo.ConfirmPurchaseScRes;
import com.skplanet.storeplatform.purchase.client.order.vo.CreateCompletePurchaseScReq;
import com.skplanet.storeplatform.purchase.client.order.vo.MakeFreePurchaseScReq;
import com.skplanet.storeplatform.purchase.client.order.vo.MakeFreePurchaseScRes;
import com.skplanet.storeplatform.purchase.client.order.vo.PrchsDtlMore;
import com.skplanet.storeplatform.purchase.client.order.vo.ReservePurchaseScReq;
import com.skplanet.storeplatform.purchase.client.order.vo.ReservePurchaseScRes;
import com.skplanet.storeplatform.purchase.client.order.vo.SearchPurchaseListByStatusScReq;
import com.skplanet.storeplatform.purchase.client.order.vo.SearchPurchaseListByStatusScRes;
import com.skplanet.storeplatform.purchase.client.order.vo.SearchPurchaseSequenceAndDateRes;
import com.skplanet.storeplatform.purchase.client.order.vo.ShoppingCouponPublishInfo;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.EpisodeInfoRes;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.IapProductInfoRes;
import com.skplanet.storeplatform.sac.client.internal.member.seller.vo.SellerMbrSac;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchOrderUserByDeviceIdSacRes;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchUserPayplanetSacRes;
import com.skplanet.storeplatform.sac.client.purchase.vo.order.CreateCompletePurchaseInfoSac;
import com.skplanet.storeplatform.sac.client.purchase.vo.order.CreateCompletePurchaseSacReq;
import com.skplanet.storeplatform.sac.client.purchase.vo.order.NotifyPaymentSacReq;
import com.skplanet.storeplatform.sac.client.purchase.vo.order.PaymentInfo;
import com.skplanet.storeplatform.sac.client.purchase.vo.order.VerifyOrderIapInfoSac;
import com.skplanet.storeplatform.sac.client.purchase.vo.order.VerifyOrderSacRes;
import com.skplanet.storeplatform.sac.purchase.common.service.MembershipReserveService;
import com.skplanet.storeplatform.sac.purchase.common.service.PayPlanetShopService;
import com.skplanet.storeplatform.sac.purchase.common.service.PurchaseTenantPolicyService;
import com.skplanet.storeplatform.sac.purchase.common.vo.PayPlanetShop;
import com.skplanet.storeplatform.sac.purchase.constant.PurchaseConstants;
import com.skplanet.storeplatform.sac.purchase.order.PaymethodUtil;
import com.skplanet.storeplatform.sac.purchase.order.repository.PurchaseDisplayRepository;
import com.skplanet.storeplatform.sac.purchase.order.repository.PurchaseMemberRepository;
import com.skplanet.storeplatform.sac.purchase.order.repository.PurchaseShoppingOrderRepository;
import com.skplanet.storeplatform.sac.purchase.order.vo.MileageSubInfo;
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
	private PurchaseOrderPaymentPageService orderPaymentPageService;
	@Autowired
	private PurchaseOrderPolicyService purchaseOrderPolicyService;
	@Autowired
	private PurchaseOrderTstoreService purchaseOrderTstoreService;
	@Autowired
	private PurchaseTenantPolicyService purchaseTenantPolicyService;
	@Autowired
	private PurchaseOrderValidationService purchaseOrderValidationService;
	@Autowired
	private PayPlanetShopService payPlanetShopService;
	@Autowired
	private MembershipReserveService membershipReserveService;
	@Autowired
	private PurchaseMemberRepository purchaseMemberRepository;
	@Autowired
	private PurchaseDisplayRepository purchaseDisplayRepository;
	@Autowired
	private PurchaseShoppingOrderRepository purchaseShoppingOrderRepository;

	/**
	 * 
	 * <pre>
	 * 비과금 구매 처리.
	 * </pre>
	 * 
	 * @param purchaseOrderInfo
	 *            구매요청 정보
	 * @return 생성된 구매이력 건수
	 */
	@Override
	public int processFreeChargePurchase(PurchaseOrderInfo purchaseOrderInfo) {
		this.purchaseOrderValidationService.validateFreeChargeAuth(purchaseOrderInfo.getPrchsReqPathCd()); // 비과금 구매요청
																										   // 권한 체크

		return this.processPurchase(purchaseOrderInfo);
	}

	/**
	 * 
	 * <pre>
	 * Biz 쿠폰 발급 요청 처리.
	 * </pre>
	 * 
	 * @param purchaseOrderInfo
	 *            구매요청 정보
	 * @return 생성된 구매이력 건수
	 */
	@Override
	public int processBizPurchase(PurchaseOrderInfo purchaseOrderInfo) {
		this.purchaseOrderValidationService.validateBizAuth(purchaseOrderInfo.getPrchsReqPathCd()); // Biz 구매요청 권한 체크

		return this.processPurchase(purchaseOrderInfo);
	}

	/**
	 * 
	 * <pre>
	 * 상품 구매요청 처리.
	 * </pre>
	 * 
	 * @param purchaseOrderInfo
	 *            구매요청 정보
	 * @return 생성된 구매이력 건수
	 */
	@Override
	public int processPurchase(PurchaseOrderInfo purchaseOrderInfo) {
		// 구매 정합성 체크
		this.validatePurchaseOrder(purchaseOrderInfo);

		// 구매 진행 체크
		if (purchaseOrderInfo.getRealTotAmt() <= 0) {
			// CLINK 예외 처리: 무료 구매 건에 대해서만.
			boolean bAtLeastOne = false;
			for (PurchaseProduct product : purchaseOrderInfo.getPurchaseProductList()) {
				if (StringUtils.isBlank(product.getResultCd())) {
					bAtLeastOne = true;
					break;
				}
			}

			if (bAtLeastOne == false) {
				purchaseOrderInfo.setResultType(PurchaseConstants.CREATE_PURCHASE_RESULT_FREE);
				purchaseOrderInfo.setPaymentPageUrlParam(this.makeClinkResProductResult(purchaseOrderInfo
						.getPurchaseProductList()));

				return 0;
			}

		} else {
			// 구매(결제)차단 여부 체크
			if (this.purchaseOrderPolicyService.isBlockPayment(purchaseOrderInfo.getTenantId(), purchaseOrderInfo
					.getPurchaseUser().getDeviceId(), purchaseOrderInfo.getTenantProdGrpCd())) {
				throw new StorePlatformException("SAC_PUR_6103");
			}
		}

		// 구매ID, 구매일시 세팅
		SearchPurchaseSequenceAndDateRes searchPurchaseSequenceAndDateRes = this.purchaseOrderSearchSCI
				.searchPurchaseSequenceAndDate();

		if (StringUtils.isBlank(purchaseOrderInfo.getPrchsId())) {
			purchaseOrderInfo.setPrchsId(this.purchaseOrderAssistService.makePrchsId(
					searchPurchaseSequenceAndDateRes.getNextSequence(), searchPurchaseSequenceAndDateRes.getNowDate()));
		}

		purchaseOrderInfo.setPrchsDt(searchPurchaseSequenceAndDateRes.getNowDate());

		// --

		int cnt = 0;

		if (purchaseOrderInfo.getRealTotAmt() <= 0) {
			cnt = this.freePurchase(purchaseOrderInfo);

			purchaseOrderInfo.setResultType(PurchaseConstants.CREATE_PURCHASE_RESULT_FREE);
			if (purchaseOrderInfo.isClink()) { // CLINK 예외처리
				purchaseOrderInfo.setPaymentPageUrlParam(this.makeClinkResProductResult(purchaseOrderInfo
						.getPurchaseProductList()));
			}

		} else {
			cnt = this.reservePurchase(purchaseOrderInfo); // 구매예약

			purchaseOrderInfo.setResultType(PurchaseConstants.CREATE_PURCHASE_RESULT_PAYMENT);
			this.orderPaymentPageService.buildPaymentPageUrlParam(purchaseOrderInfo); // 결제Page 정보 세팅
		}

		return cnt;
	}

	/*
	 * 
	 * <pre> 무료구매 처리. </pre>
	 * 
	 * @param purchaseOrderInfo 구매요청 정보
	 * 
	 * @return 생성된 구매이력 건수
	 */
	private int freePurchase(PurchaseOrderInfo purchaseOrderInfo) {

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

		// -------------------------------------------------------------------------------------------
		// 이북/코믹 전권 소장/대여 에피소드 상품 목록 조회 (T프리미엄 경우)

		PrchsDtlMore prchsDtlMore = prchsDtlMoreList.get(0);

		PurchaseUserDevice useUser = purchaseOrderInfo.isGift() ? purchaseOrderInfo.getReceiveUserList().get(0) : purchaseOrderInfo
				.getPurchaseUser();

		List<EpisodeInfoRes> episodeList = null;

		if (StringUtils.startsWith(prchsDtlMore.getTenantProdGrpCd(),
				PurchaseConstants.TENANT_PRODUCT_GROUP_DTL_EBOOK_FIXRATE)
				|| StringUtils.startsWith(prchsDtlMore.getTenantProdGrpCd(),
						PurchaseConstants.TENANT_PRODUCT_GROUP_DTL_COMIC_FIXRATE)) {
			episodeList = this.purchaseDisplayRepository.searchEbookComicEpisodeList(prchsDtlMore.getTenantId(),
					prchsDtlMore.getCurrencyCd(), useUser.getDeviceModelCd(), prchsDtlMore.getProdId(),
					purchaseOrderInfo.getPurchaseProductList().get(0).getCmpxProdClsfCd());
		}

		// 이북/코믹 전권 소장/대여 에피소드 상품 - 구매이력 생성 요청 데이터
		List<PrchsDtlMore> ebookComicEpisodeList = null;
		if (CollectionUtils.isNotEmpty(episodeList)) {
			ebookComicEpisodeList = this.purchaseOrderMakeDataService.makeEbookComicEpisodeList(prchsDtlMore,
					episodeList, purchaseOrderInfo.getPurchaseProductList().get(0).getCmpxProdClsfCd());
		}

		// -----------------------------------------------------------------------------
		// 무료구매 완료 요청

		MakeFreePurchaseScReq makeFreePurchaseScReq = new MakeFreePurchaseScReq();
		makeFreePurchaseScReq.setPrchsDtlMoreList(prchsDtlMoreList);
		makeFreePurchaseScReq.setPrchsProdCntList(prchsProdCntList);
		makeFreePurchaseScReq.setPaymentList(paymentList);
		makeFreePurchaseScReq.setEbookComicEpisodeList(ebookComicEpisodeList);

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
		// 구매완료 TLog : Biz 쿠폰 발급 경우는 T Log 제외

		if (purchaseOrderInfo.isBizShopping() == false) {

			final String imei = purchaseOrderInfo.getImei();
			final String mno_type = purchaseOrderInfo.getPurchaseUser().getTelecom();
			final String usermbr_no = purchaseOrderInfo.getPurchaseUser().getUserKey();
			final String system_id = purchaseOrderInfo.getSystemId();
			final String purchase_channel = purchaseOrderInfo.getPrchsReqPathCd();
			final String purchase_inflow_channel = purchaseOrderInfo.getPrchsCaseCd();
			final String purchase_id = purchaseOrderInfo.getPrchsId();
			final String purchase_id_recv = purchaseOrderInfo.isGift() ? purchaseOrderInfo.getPrchsId() : "";

			final List<String> topCatCodeList = new ArrayList<String>();
			topCatCodeList.add(prchsDtlMore.getTenantProdGrpCd().substring(8, 12));

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
								.top_cat_code(topCatCodeList).result_code("SUCC");
					}
				});
			}
		}

		return count;
	}

	/*
	 * 
	 * <pre> 유료구매 - 구매예약. </pre>
	 * 
	 * @param purchaseOrderInfo 구매요청 정보
	 */
	private int reservePurchase(PurchaseOrderInfo purchaseOrderInfo) {
		// -----------------------------------------------------------------------------
		// PayPlanet 가맹점 정보 조회

		PayPlanetShop payPlanetShop = this.payPlanetShopService.getPayPlanetShopInfo(purchaseOrderInfo.getTenantId());
		purchaseOrderInfo.setMid(payPlanetShop.getMid());
		purchaseOrderInfo.setAuthKey(payPlanetShop.getAuthKey());
		purchaseOrderInfo.setEncKey(payPlanetShop.getEncKey());
		purchaseOrderInfo.setPaymentPageUrl(payPlanetShop.getPaymentUrl());

		// -----------------------------------------------------------------------------
		// 구매생성 요청 데이터 생성

		List<PrchsDtlMore> prchsDtlMoreList = this.purchaseOrderMakeDataService.makePrchsDtlMoreList(purchaseOrderInfo);
		this.purchaseOrderMakeDataService.buildReservedData(purchaseOrderInfo, prchsDtlMoreList); // 예약 정보 세팅

		// -----------------------------------------------------------------------------
		// 구매예약 생성 요청

		StorePlatformException checkException = null;
		int reserveCount = 0;

		try {
			ReservePurchaseScRes reservePurchaseScRes = this.purchaseOrderSCI.reservePurchase(new ReservePurchaseScReq(
					prchsDtlMoreList));

			reserveCount = reservePurchaseScRes.getCount();

			if (CollectionUtils.isNotEmpty(purchaseOrderInfo.getReceiveUserList())) {
				if (reserveCount != purchaseOrderInfo.getReceiveUserList().size()
						* purchaseOrderInfo.getPurchaseProductList().size()) {
					throw new StorePlatformException("SAC_PUR_7201");
				}
			} else {
				if (reserveCount != prchsDtlMoreList.size()) {
					throw new StorePlatformException("SAC_PUR_7201");
				}
			}

		} catch (StorePlatformException e) {
			checkException = e;
			throw e;

		} finally {
			// 구매예약 TLog 로깅
			ErrorInfo errorInfo = (checkException != null ? checkException.getErrorInfo() : null);

			String msg = "";
			if (errorInfo != null) {
				try {
					msg = this.messageSourceAccessor.getMessage(errorInfo.getCode());
				} catch (NoSuchMessageException e) {
					msg = "";
				}
			}

			final String result_code = (errorInfo != null ? errorInfo.getCode() : "SUCC");
			final String result_message = msg;
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

		return reserveCount;
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

		String payUserKey = null;
		String payDeviceKey = null;

		if (StringUtils.equals(prchsDtlMore.getPrchsCaseCd(), PurchaseConstants.PRCHS_CASE_PURCHASE_CD)) {
			payUserKey = prchsDtlMore.getUseInsdUsermbrNo();
			payDeviceKey = prchsDtlMore.getUseInsdDeviceId();
		} else {
			payUserKey = prchsDtlMore.getSendInsdUsermbrNo();
			payDeviceKey = prchsDtlMore.getSendInsdDeviceId();
		}

		// ------------------------------------------------------------------------------------------------
		// 구매인증 응답 데이터 처리

		VerifyOrderSacRes res = new VerifyOrderSacRes();

		// 예약 저장해둔 데이터 추출
		Map<String, String> reservedDataMap = this.purchaseOrderMakeDataService.parseReservedData(prchsDtlMore
				.getPrchsResvDesc());

		// 회원 - 결제 관련 정보 조회
		SearchUserPayplanetSacRes userPayRes = null;
		try {
			userPayRes = this.purchaseMemberRepository.searchUserPayplanet(payUserKey, payDeviceKey);
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
				reservedDataMap.get("cmpxProdClsfCd"), prchsDtlMore.getTotAmt().doubleValue()));

		// ------------------------------------------------------------------------------------------------
		// 결제수단 정렬 재조정

		res.setCdPriority("");

		// ------------------------------------------------------------------------------------------------
		// T store 쿠폰 조회

		List<String> prodIdList = new ArrayList<String>();
		for (PrchsDtlMore productInfo : prchsDtlMoreList) {
			prodIdList.add(productInfo.getProdId());
		}
		res.setNoCouponList(this.purchaseOrderTstoreService.searchTstoreCouponList(payUserKey,
				reservedDataMap.get("deviceId"), prodIdList));

		// ------------------------------------------------------------------------------------------------
		// 캐쉬/포인트 잔액 통합 정보 : 게임 경우 통합조회 규격, 그 외는 T store Cash 단일 조회

		String cashIntgAmtInf = "";

		if (StringUtils.equals(prchsDtlMore.getTenantProdGrpCd().substring(8, 12), "DP01")
				&& StringUtils.endsWith(prchsDtlMore.getTenantProdGrpCd(),
						PurchaseConstants.TENANT_PRODUCT_GROUP_SUFFIX_UNIT)) {
			cashIntgAmtInf = this.purchaseOrderTstoreService.searchTstoreCashIntegrationAmt(payUserKey);

		} else {
			// T store Cash 조회
			double tstoreCashAmt = this.purchaseOrderTstoreService.searchTstoreCashAmt(payUserKey);

			// 캐쉬/포인트 잔액 통합 정보
			StringBuffer sbCashPoint = new StringBuffer();
			sbCashPoint.append(PurchaseConstants.PAYPLANET_PAYMENT_METHOD_TSTORE_CASH).append(":")
					.append(tstoreCashAmt).append(";").append(PurchaseConstants.PAYPLANET_PAYMENT_METHOD_GAMECASH)
					.append(":").append(0).append(";")
					.append(PurchaseConstants.PAYPLANET_PAYMENT_METHOD_TGAMEPASS_POINT).append(":").append(0);

			cashIntgAmtInf = sbCashPoint.toString();
		}

		res.setCashPointList(cashIntgAmtInf);

		// ------------------------------------------------------------------------------------------------
		// T마일리지 적립 정보

		// 회원등급
		res.setUserGrade(this.purchaseMemberRepository.searchUserGrade(payUserKey));

		// 상품 적립률
		res.settMileageSaveRate(reservedDataMap.get("tMileageRateInfo"));

		// 적립 가능 결제수단 코드
		res.settMileageAvailMtd(this.purchaseOrderPolicyService.searchtMileageSavePaymentMethod(
				prchsDtlMore.getTenantId(), prchsDtlMore.getTenantProdGrpCd()));

		// T마일리지 적립한도 금액
		res.settMileageLimitAmt(this.purchaseOrderPolicyService.searchtMileageSaveLimit(prchsDtlMore.getTenantId(),
				prchsDtlMore.getTenantProdGrpCd()));

		// (이번회) T마일리지 적립예정 금액
		String targetDt = "20" + prchsDtlMore.getPrchsId().substring(0, 12);

		// TAKTODO::오타때문에 2개 항목 세팅
		int reserveAmt = this.membershipReserveService.searchSaveExpectTotalAmt(prchsDtlMore.getTenantId(), payUserKey,
				targetDt, null);
		res.settMileageReseveAmt(reserveAmt);
		res.settMileageReserveAmt(reserveAmt);

		// ------------------------------------------------------------------------------------------------
		// OCB 적립율

		// 시험폰, SKP법인폰 여부
		boolean sktTestOrSkpCorp = (policyResult != null ? (policyResult.isSktTestMdn() || policyResult
				.isSkpCorporation()) : false);

		res.setCdOcbSaveInfo(this.adjustOcbSaveInfo(reservedDataMap.get("telecom"), prchsDtlMore.getTenantProdGrpCd(),
				sktTestOrSkpCorp));

		// ------------------------------------------------------------------------------------------------
		// 결제Page 템플릿

		boolean bIapAutoPrchs = false;
		if (StringUtils.startsWith(prchsDtlMore.getTenantProdGrpCd(), PurchaseConstants.TENANT_PRODUCT_GROUP_IAP)
				&& StringUtils.equals(reservedDataMap.get("autoPrchsYn"), "Y")) {
			bIapAutoPrchs = true;
		}

		res.setCdPaymentTemplate(this.adjustPaymentPageTemplate(prchsDtlMore.getPrchsCaseCd(),
				prchsDtlMore.getTenantProdGrpCd(), reservedDataMap.get("cmpxProdClsfCd"), bIapAutoPrchs,
				StringUtils.equals(reservedDataMap.get("s2sAutoYn"), "Y"), prchsDtlMoreList.size()));

		// ------------------------------------------------------------------------------------------------
		// (다날) 컨텐츠 종류: 실물 / 디지털 : 쇼핑상품만 실물로 처리

		if (StringUtils.startsWith(prchsDtlMore.getTenantProdGrpCd(), PurchaseConstants.TENANT_PRODUCT_GROUP_SHOPPING)) {
			res.setTypeDanalContent(PurchaseConstants.DANAL_CONTENT_TYPE_REAL);
		} else {
			res.setTypeDanalContent(PurchaseConstants.DANAL_CONTENT_TYPE_DIGITAL);
		}

		// ------------------------------------------------------------------------------------------------
		// 기타 응답 값

		res.setTenantId(prchsDtlMore.getTenantId());
		res.setMdn(reservedDataMap.get("deviceId")); // 결제 MDN
		res.setOneId(reservedDataMap.get("oneId")); // ONE ID
		res.setFlgMbrStatus(PurchaseConstants.VERIFYORDER_USER_STATUS_NORMAL); // [fix] 회원상태: 1-정상
		res.setFlgProductStatus(PurchaseConstants.VERIFYORDER_PRODUCT_STATUS_NORMAL); // [fix] 상품상태: 1-정상
		res.setPrchsCaseCd(prchsDtlMore.getPrchsCaseCd()); // 구매/선물 구분 코드
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
		res.setDwldAvailableDayCnt(reservedDataMap.get("dwldAvailableDayCnt")); // 다운로드 가능기간(일)
		res.setUsePeriodCnt(StringUtils.defaultIfBlank(reservedDataMap.get("usePeriodCnt"), "")); // 이용기간(일)

		res.setProdKind(reservedDataMap.get("prodCaseCd")); // 쇼핑상품 종류

		// IAP P/P 정보 세팅
		if (StringUtils.equals("Y", reservedDataMap.get("iapYn"))) {
			VerifyOrderIapInfoSac iapProdInfo = new VerifyOrderIapInfoSac();
			iapProdInfo.setPostbackUrl(reservedDataMap.get("iapPostbackUrl"));
			iapProdInfo.setProdKind(reservedDataMap.get("iapProdKind"));
			iapProdInfo.setProdCase(reservedDataMap.get("iapProdCase"));
			if (StringUtils.isNotBlank(reservedDataMap.get("iapUsePeriod"))) {
				iapProdInfo.setUsePeriod(Integer.parseInt(reservedDataMap.get("iapUsePeriod")));
			}

			res.setIapProdInfo(iapProdInfo);
		}

		// 프로모션 정보
		// TAKTEST:: PP만
		// if (StringUtils.startsWith(verifyOrderInfo.getSystemId(), "S00")) {
		// res.setPromotionList(this.searchPromotionListTemp(prchsDtlMore));
		// }

		// 판매자 정보 세팅
		SellerMbrSac sellerInfo = this.searchSellerInfo(reservedDataMap.get("sellerMbrNo"),
				prchsDtlMore.getTenantProdGrpCd());
		res.setNmSellerCompany(sellerInfo.getSellerCompany()); // 회사명
		res.setNmSeller(sellerInfo.getSellerNickName()); // 쇼핑 노출명
		res.setEmailSeller(sellerInfo.getSellerEmail()); // 판매자 이메일 주소
		res.setNoTelSeller(sellerInfo.getRepPhone()); // 대표전화번호

		// 선물 수신자 정보
		if (StringUtils.equals(prchsDtlMore.getPrchsCaseCd(), PurchaseConstants.PRCHS_CASE_GIFT_CD)) {
			res.setNmDelivery(this.concatResvDesc(prchsDtlMoreList, "receiveName", ";")); // 선물수신자 성명
			res.setNoMdnDelivery(this.concatResvDesc(prchsDtlMoreList, "useDeviceId", ";")); // 선물수신자 MDN
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

		String payUserKey = null;

		if (StringUtils.equals(prchsDtlMore.getPrchsCaseCd(), PurchaseConstants.PRCHS_CASE_PURCHASE_CD)) {
			payUserKey = prchsDtlMore.getUseInsdUsermbrNo();
		} else {
			payUserKey = prchsDtlMore.getSendInsdUsermbrNo();
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

			// 구매요청 API 버전
			int apiVer = Integer.parseInt(StringUtils.defaultString(reservedDataMap.get("apiVer"), "1"));

			// if (StringUtils.equals(giftYn, "Y") && prchsDtlMoreList.size() > 1 && prchsDtlMore.getProdQty() == 1)
			// {1:N선물구분

			if (apiVer > 1) { // 구매요청 버전 V2 부터는 신규 쿠폰발급요청 규격 이용 (1:N 선물 지원)

				List<String> useMdnList = this.concatResvDescByList(prchsDtlMoreList, "useDeviceId");

				CouponPublishV2EcRes couponPublishV2EcRes = this.purchaseShoppingOrderRepository.createCouponPublishV2(
						prchsDtlMore.getPrchsId(), reservedDataMap.get("couponCode"), reservedDataMap.get("itemCode"),
						reservedDataMap.get("deviceId"), useMdnList,
						StringUtils.equals(prchsDtlMore.getPrchsCaseCd(), PurchaseConstants.PRCHS_CASE_GIFT_CD));

				String availStartDt = couponPublishV2EcRes.getAvailStartdate();
				String availEndDt = couponPublishV2EcRes.getAvailEnddate();

				shoppingCouponList = new ArrayList<ShoppingCouponPublishInfo>();
				ShoppingCouponPublishInfo shoppingCouponPublishInfo = null;
				int prchsDtlId = 1;
				for (CouponPublishV2ItemDetailEcRes couponInfo : couponPublishV2EcRes.getItems()) {
					shoppingCouponPublishInfo = new ShoppingCouponPublishInfo();

					shoppingCouponPublishInfo.setPrchsDtlId(prchsDtlId++); // 구매상세ID 처리
					shoppingCouponPublishInfo.setAvailStartDt(availStartDt);
					shoppingCouponPublishInfo.setAvailEndDt(availEndDt);
					shoppingCouponPublishInfo.setPublishCode(couponInfo.getPublishCode());
					shoppingCouponPublishInfo.setShippingUrl(couponInfo.getShippingUrl());
					shoppingCouponPublishInfo.setAddInfo(couponInfo.getExtraData());

					shoppingCouponList.add(shoppingCouponPublishInfo);
				}

			} else {

				CouponPublishEcRes couponPublishEcRes = this.purchaseShoppingOrderRepository.createCouponPublish(
						prchsDtlMore.getPrchsId(), reservedDataMap.get("useDeviceId"), reservedDataMap.get("deviceId"),
						reservedDataMap.get("couponCode"), reservedDataMap.get("itemCode"), prchsDtlMore.getProdQty());

				if (StringUtils.equals(couponPublishEcRes.getPublishType(),
						PurchaseConstants.SHOPPING_COUPON_PUBLISH_ASYNC) == false
						&& CollectionUtils.isNotEmpty(couponPublishEcRes.getItems())) { // 0-즉시발급
					String availStartDt = couponPublishEcRes.getAvailStartdate();
					String availEndDt = couponPublishEcRes.getAvailEnddate();

					shoppingCouponList = new ArrayList<ShoppingCouponPublishInfo>();
					ShoppingCouponPublishInfo shoppingCouponPublishInfo = null;
					int prchsDtlId = 1;
					for (CouponPublishItemDetailEcRes couponInfo : couponPublishEcRes.getItems()) {
						shoppingCouponPublishInfo = new ShoppingCouponPublishInfo();

						shoppingCouponPublishInfo.setPrchsDtlId(prchsDtlId++); // 구매상세ID 처리
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
		}

		prchsDtlMore.setPrchsResvDesc(prchsDtlMore.getPrchsResvDesc() + "&tstoreNotiPublishType="
				+ tstoreNotiPublishType); // 후처리 시 사용 : -> 구매완료Noti

		// -------------------------------------------------------------------------------------------
		// 게임캐쉬 충전 예약 및 확정

		List<TStoreCashChargeReserveDetailEcRes> cashReserveResList = null;

		if (StringUtils.startsWith(prchsDtlMore.getTenantProdGrpCd(),
				PurchaseConstants.TENANT_PRODUCT_GROUP_DTL_GAMECASH_FIXRATE)) {
			// 충전 예약
			cashReserveResList = this.purchaseOrderTstoreService.reserveGameCashCharge(
					prchsDtlMore.getUseInsdUsermbrNo(), prchsDtlMore.getProdAmt().doubleValue(),
					prchsDtlMore.getUseStartDt(), Double.parseDouble(reservedDataMap.get("bonusPoint")),
					reservedDataMap.get("bonusPointUsePeriodUnitCd"), reservedDataMap.get("bonusPointUsePeriod"));

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

		boolean bSktTest = false; // 시험폰 결제 여부
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
		confirmPurchaseScReq.setMediaId(reservedDataMap.get("mediaId")); // CPS 매체ID 세팅

		// TAKTODO::T멤버쉽

		String tMileageRateInfo = reservedDataMap.get("tMileageRateInfo"); // 상품 적립률
		Map<String, Integer> rateMap = new HashMap<String, Integer>();

		if (StringUtils.isBlank(tMileageRateInfo)) {
			rateMap.put(PurchaseConstants.USER_GRADE_PLATINUM, 0);
			rateMap.put(PurchaseConstants.USER_GRADE_GOLD, 0);
			rateMap.put(PurchaseConstants.USER_GRADE_SILVER, 0);

		} else {
			String[] arRates = tMileageRateInfo.split(";");
			String[] arRate = null;
			for (String rate : arRates) {
				arRate = rate.split(":");
				rateMap.put(arRate[0], Integer.parseInt(StringUtils.defaultIfBlank(arRate[1], "0")));
			}
		}

		String userGrade = this.purchaseMemberRepository.searchUserGrade(payUserKey); // 등급

		MileageSubInfo mileageSubInfo = new MileageSubInfo();
		mileageSubInfo.setTypeCd(PurchaseConstants.MEMBERSHIP_TYPE_TMEMBERSHIP);
		mileageSubInfo.setUserGrdCd(userGrade);
		mileageSubInfo.setProdSaveRate(rateMap.get(userGrade));
		mileageSubInfo.setProcStatusCd(PurchaseConstants.MEMBERSHIP_PROC_STATUS_RESERVE);
		mileageSubInfo.setProdNm(prchsDtlMore.getPartChrgProdNm()); // IAP 부분상품명
		// if (StringUtils.isNotBlank(reservedDataMap.get("prodNm"))) { // 상품명
		// try {
		// mileageSubInfo.setProdNm(URLDecoder.decode(reservedDataMap.get("prodNm"),
		// PurchaseConstants.DEFAULT_ENCODING));
		// } catch (UnsupportedEncodingException e1) {
		// ;
		// }
		// }

		// 시험폰 경우, 후불결제 금액 제외: 시험폰 적립 WhiteList로 변경 예정
		boolean bSktSaveMileage = true;
		if (bSktTest) {
			bSktSaveMileage = this.purchaseOrderPolicyService.isMileageSaveSktTestDevice(prchsDtlMore.getTenantId(),
					reservedDataMap.get("deviceId"), prchsDtlMore.getTenantProdGrpCd());
		}

		if (StringUtils.isNotBlank(notifyPaymentReq.getProcSubStatusCd()) && bSktSaveMileage) { // T멤버쉽 정보 받은 경우

			this.logger.info("PRCHS,ORDER,SAC,CONFIRM,MILEAGE,CHECK,BYREQ,({},{}),{},{},{}", bSktTest, bSktSaveMileage,
					prchsDtlMore.getPrchsId(), userGrade, tMileageRateInfo);

			mileageSubInfo.setPrchsReqPathCd(prchsDtlMore.getPrchsReqPathCd());

			mileageSubInfo.setTargetPaymentAmt(notifyPaymentReq.getTargetPaymentAmt());
			mileageSubInfo.setSaveExpectAmt(notifyPaymentReq.getSaveExpectAmt());
			mileageSubInfo.setSaveResultAmt(notifyPaymentReq.getSaveResultAmt());
			mileageSubInfo.setSaveTypeCd(notifyPaymentReq.getProcSubStatusCd());

		} else {

			mileageSubInfo.setPrchsReqPathCd(null);

			// 적립 가능 결제수단 금액
			String availPayMtd = this.purchaseOrderPolicyService.searchtMileageSavePaymentMethod(
					prchsDtlMore.getTenantId(), prchsDtlMore.getTenantProdGrpCd());
			if (bSktSaveMileage == false) {
				availPayMtd = availPayMtd.replaceAll("11;", "");
			}

			this.logger.info("PRCHS,ORDER,SAC,CONFIRM,MILEAGE,CHECK,BYSERVER,({},{}),{},{},{},{}", bSktTest,
					bSktSaveMileage, prchsDtlMore.getPrchsId(), userGrade, tMileageRateInfo, availPayMtd);

			double availPayAmt = 0.0;
			for (PaymentInfo paymentInfo : notifyPaymentReq.getPaymentInfoList()) {
				if (StringUtils.contains(availPayMtd,
						PaymethodUtil.convert2PayPlanetCodeWithoutPointCode(paymentInfo.getPaymentMtdCd()))) {
					availPayAmt += paymentInfo.getPaymentAmt();
				}
			}
			mileageSubInfo.setTargetPaymentAmt(availPayAmt);

			if (availPayAmt > 0.0 && rateMap.get(userGrade) != null && rateMap.get(userGrade) > 0) {

				// 적립예정 금액: 1원 미만 버림
				int expectAmt = (int) (availPayAmt * rateMap.get(userGrade) * 0.01);
				mileageSubInfo.setSaveExpectAmt(expectAmt);

				// 적립예정 이력 총 금액
				String targetDt = "20" + prchsDtlMore.getPrchsId().substring(0, 12);
				int preReserveAmt = this.membershipReserveService.searchSaveExpectTotalAmt(prchsDtlMore.getTenantId(),
						payUserKey, targetDt, null);

				int limitAmt = this.purchaseOrderPolicyService.searchtMileageSaveLimit(prchsDtlMore.getTenantId(),
						prchsDtlMore.getTenantProdGrpCd());

				if (preReserveAmt >= limitAmt) { // 한도초과
					mileageSubInfo.setSaveResultAmt(0);
					mileageSubInfo.setSaveTypeCd(PurchaseConstants.MEMBERSHIP_SAVE_TYPE_OVER);

				} else if ((preReserveAmt + expectAmt) <= limitAmt) { // 전체적립
					mileageSubInfo.setSaveResultAmt(expectAmt);
					mileageSubInfo.setSaveTypeCd(PurchaseConstants.MEMBERSHIP_SAVE_TYPE_ALL);

				} else { // 부분적립
					mileageSubInfo.setSaveResultAmt(limitAmt - preReserveAmt);
					mileageSubInfo.setSaveTypeCd(PurchaseConstants.MEMBERSHIP_SAVE_TYPE_PART);
				}

			}
		}

		this.logger.info("PRCHS,ORDER,SAC,CONFIRM,MILEAGE,CHECK,PAY,{},{}", prchsDtlMore.getPrchsId(),
				ReflectionToStringBuilder.toString(mileageSubInfo, ToStringStyle.SHORT_PREFIX_STYLE));

		List<MembershipReserve> membershipReserveList = null;
		if (mileageSubInfo.getTargetPaymentAmt() > 0 && mileageSubInfo.getProdSaveRate() > 0) {
			membershipReserveList = this.purchaseOrderMakeDataService.makeMembershipReserveList(prchsDtlMoreList,
					mileageSubInfo);
		}

		// -------------------------------------------------------------------------------------------
		// 구매확정 요청

		confirmPurchaseScReq.setPrchsProdCntList(prchsProdCntList); // 건수집계
		confirmPurchaseScReq.setPaymentList(paymentList); // 결제
		confirmPurchaseScReq.setAutoPrchsList(autoPrchsList); // 자동구매
		confirmPurchaseScReq.setShoppingCouponList(shoppingCouponList); // 쇼핑발급 목록
		confirmPurchaseScReq.setEbookComicEpisodeList(ebookComicEpisodeList);
		confirmPurchaseScReq.setMembershipReserveList(membershipReserveList);

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

			String msg = "";
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
			final String mbr_id = reservedDataMap.get("userId");
			final String device_id = reservedDataMap.get("deviceId");
			final String device_id_recv = StringUtils.equals(prchsDtlMore.getPrchsCaseCd(),
					PurchaseConstants.PRCHS_CASE_PURCHASE_CD) ? "" : reservedDataMap.get("useDeviceId");
			final String imei = reservedDataMap.get("imei");
			final String mno_type = reservedDataMap.get("telecom");
			final String usermbr_no = payUserKey;
			final String system_id = prchsDtlMore.getSystemId();
			final String purchase_channel = prchsDtlMore.getPrchsReqPathCd();
			final String purchase_inflow_channel = prchsDtlMore.getPrchsCaseCd();
			final String purchase_id_recv = StringUtils.equals(prchsDtlMore.getPrchsCaseCd(),
					PurchaseConstants.PRCHS_CASE_GIFT_CD) ? prchsDtlMore.getPrchsId() : "";
			final String coupon_code = reservedDataMap.get("couponCode");
			final String coupon_item_code = reservedDataMap.get("itemCode");
			final String auto_payment_yn = StringUtils.defaultIfBlank(reservedDataMap.get("autoPrchsYn"), "N");

			final List<String> topCatCodeList = new ArrayList<String>();
			topCatCodeList.add(prchsDtlMore.getTenantProdGrpCd().substring(8, 12));

			for (PrchsDtlMore prchsInfo : prchsDtlMoreList) {
				final String insd_usermbr_no = StringUtils.equals(prchsDtlMore.getPrchsCaseCd(),
						PurchaseConstants.PRCHS_CASE_PURCHASE_CD) ? prchsDtlMore.getUseInsdUsermbrNo() : prchsDtlMore
						.getSendInsdUsermbrNo();
				final String insd_device_id = StringUtils.equals(prchsDtlMore.getPrchsCaseCd(),
						PurchaseConstants.PRCHS_CASE_PURCHASE_CD) ? prchsDtlMore.getUseInsdDeviceId() : prchsDtlMore
						.getSendInsdDeviceId();
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
								.coupon_publish_code(coupon_publish_code).device_id_recv(device_id_recv)
								.top_cat_code(topCatCodeList).result_code(result_code);
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

	/**
	 * 
	 * <pre>
	 * IAP 구매/결제 통합 구매이력 생성 요청.
	 * </pre>
	 * 
	 * @param req
	 *            구매/결제 통합 구매이력 생성 요청 VO
	 * @return 생성된 구매ID
	 */
	@Override
	public String completeIapPurchase(CreateCompletePurchaseSacReq req) {
		CreateCompletePurchaseInfoSac purchase = req.getPurchase();

		// 상품 존재 여부 체크 및 카테고리 세팅
		List<String> prodIdList = new ArrayList<String>();
		prodIdList.add(purchase.getProdId());
		Map<String, PurchaseProduct> purchaseProductMap = this.purchaseDisplayRepository.searchPurchaseProductList(
				purchase.getTenantId(), purchase.getCurrencyCd(), null, prodIdList, false);
		if (purchaseProductMap == null || purchaseProductMap.size() < 1) {
			throw new StorePlatformException("SAC_PUR_5101", purchase.getProdId());
		}

		PurchaseProduct purchaseProduct = purchaseProductMap.get(purchase.getProdId());

		// IAP 여부 체크
		// if (StringUtils.equals(purchaseProduct.getInAppYn(), PurchaseConstants.USE_Y) == false) {
		// throw new StorePlatformException("SAC_PUR_5111");
		// }

		// IAP상품 정보 조회
		IapProductInfoRes iapInfo = this.purchaseDisplayRepository.searchIapProductInfo(purchase.getProdId());
		if (iapInfo == null) {
			throw new StorePlatformException("SAC_PUR_5101", purchase.getProdId());
		}

		purchase.setTenantProdGrpCd(purchase.getTenantProdGrpCd().replaceAll("DP00",
				iapInfo.getMenuId().substring(0, 4)));

		// 회원정보(userKey, deviceKey) 조회
		String userKey = "Z";
		String deviceKey = "Z";

		String orderDt = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		SearchOrderUserByDeviceIdSacRes searchOrderUserByDeviceIdSacRes = this.purchaseMemberRepository
				.searchOrderUserByDeviceId(purchase.getDeviceId(), orderDt);

		if (searchOrderUserByDeviceIdSacRes != null) {
			userKey = StringUtils.defaultIfBlank(searchOrderUserByDeviceIdSacRes.getUserKey(), "Z");
			deviceKey = StringUtils.defaultIfBlank(searchOrderUserByDeviceIdSacRes.getDeviceKey(), "Z");
		}

		// 구매ID 생성용: 구매 시퀀스
		SearchPurchaseSequenceAndDateRes searchPurchaseSequenceAndDateRes = this.purchaseOrderSearchSCI
				.searchPurchaseSequenceAndDate();
		String prchsId = this.purchaseOrderAssistService.makePrchsId(
				searchPurchaseSequenceAndDateRes.getNextSequence(), searchPurchaseSequenceAndDateRes.getNowDate());

		// 구매정보 세팅
		PrchsDtlMore prchsDtlMore = new PrchsDtlMore();
		prchsDtlMore.setNetworkTypeCd(purchase.getNetworkTypeCd());
		prchsDtlMore.setTenantId(purchase.getTenantId());
		prchsDtlMore.setSystemId(purchase.getRegId());
		prchsDtlMore.setPrchsId(prchsId);
		prchsDtlMore.setPrchsDtlId(1);
		prchsDtlMore.setUseTenantId(purchase.getTenantId());
		prchsDtlMore.setUseInsdUsermbrNo(userKey);
		prchsDtlMore.setUseInsdDeviceId(deviceKey);
		prchsDtlMore.setInsdUsermbrNo(userKey);
		prchsDtlMore.setInsdDeviceId(deviceKey);
		prchsDtlMore.setSvcMangNo(purchase.getSvcMangNo());
		prchsDtlMore.setPrchsDt(purchase.getPrchsDt());
		prchsDtlMore.setTotAmt(purchase.getTotAmt());
		prchsDtlMore.setCurrencyCd(purchase.getCurrencyCd());
		prchsDtlMore.setPrchsReqPathCd(purchase.getPrchsReqPathCd());
		prchsDtlMore.setClientIp(purchase.getClientIp());
		prchsDtlMore.setProdId(purchase.getProdId());
		prchsDtlMore.setProdAmt(purchase.getProdAmt());
		prchsDtlMore.setProdQty(1);
		prchsDtlMore.setTenantProdGrpCd(purchase.getTenantProdGrpCd());
		prchsDtlMore.setStatusCd(PurchaseConstants.PRCHS_STATUS_COMPT);
		prchsDtlMore.setPrchsCaseCd(PurchaseConstants.PRCHS_CASE_PURCHASE_CD);
		prchsDtlMore.setDrmYn(purchase.getDrmYn());
		prchsDtlMore.setUseStartDt(purchase.getUseStartDt());
		prchsDtlMore.setUseExprDt(purchase.getUseExprDt());
		prchsDtlMore.setDwldStartDt(purchase.getDwldStartDt());
		prchsDtlMore.setDwldExprDt(purchase.getDwldExprDt());
		prchsDtlMore.setPrchsProdType(PurchaseConstants.PRCHS_PROD_TYPE_UNIT);
		prchsDtlMore.setUseHidingYn(PurchaseConstants.USE_N);
		prchsDtlMore.setSendHidingYn(PurchaseConstants.USE_N);
		prchsDtlMore.setTid(purchase.getTid());
		prchsDtlMore.setTxId(purchase.getTxId());
		prchsDtlMore.setParentProdId(purchase.getParentProdId());
		prchsDtlMore.setPartChrgVer(purchase.getPartChrgVer());
		prchsDtlMore.setPartChrgProdNm(purchase.getPartChrgProdNm());
		prchsDtlMore.setRegId(purchase.getRegId());
		prchsDtlMore.setUpdId(purchase.getUpdId());
		prchsDtlMore.setAlarmYn(PurchaseConstants.USE_N);
		prchsDtlMore.setContentsType(iapInfo.getProdKind());

		List<PrchsDtlMore> prchsDtlMoreList = new ArrayList<PrchsDtlMore>();
		prchsDtlMoreList.add(prchsDtlMore);

		// 결제정보 생성
		List<Payment> paymentList = this.purchaseOrderMakeDataService.makePaymentList(prchsDtlMore,
				req.getPaymentList(), prchsDtlMore.getStatusCd());

		boolean bSktTest = false; // 시험폰 결제 여부
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
					prchsDtlMore.getStatusCd());
		}

		// TB_PR_UNIQUE_TID : 중복요청 체크 정보 생성
		UniqueTid uniqueTid = new UniqueTid();
		uniqueTid.setTid(req.getPaymentList().get(0).getTid());
		uniqueTid.setPrchsId(prchsId);

		// TAKTODO::T멤버쉽

		Map<String, Integer> rateMap = purchaseProduct.getMileageRateMap(); // 상품 적립률
		if (rateMap == null) {
			rateMap = new HashMap<String, Integer>();
			rateMap.put(PurchaseConstants.USER_GRADE_PLATINUM, 0);
			rateMap.put(PurchaseConstants.USER_GRADE_GOLD, 0);
			rateMap.put(PurchaseConstants.USER_GRADE_SILVER, 0);
		}
		StringBuffer sbtMileageRateInfo = new StringBuffer();
		for (String key : rateMap.keySet()) {
			sbtMileageRateInfo.append(key).append(":").append(rateMap.get(key)).append(";");
		}
		sbtMileageRateInfo.setLength(sbtMileageRateInfo.length() - 1);

		String userGrade = null; // 등급
		try {
			userGrade = this.purchaseMemberRepository.searchUserGrade(userKey);
		} catch (StorePlatformException e) {
			if (StringUtils.equals(e.getCode(), PurchaseConstants.SACINNER_MEMBER_RESULT_NOTEXIST_KEY)) {
				;
			} else {
				throw e;
			}
		}

		MileageSubInfo mileageSubInfo = new MileageSubInfo();
		mileageSubInfo.setTypeCd(PurchaseConstants.MEMBERSHIP_TYPE_TMEMBERSHIP);
		mileageSubInfo.setUserGrdCd(userGrade);
		mileageSubInfo
				.setProdSaveRate((rateMap == null || rateMap.get(userGrade) == null) ? 0 : rateMap.get(userGrade));
		mileageSubInfo.setProcStatusCd(PurchaseConstants.MEMBERSHIP_PROC_STATUS_RESERVE);
		mileageSubInfo.setProdNm(prchsDtlMore.getPartChrgProdNm()); // IAP 부분상품명

		// 시험폰 경우, 후불결제 금액 제외: 시험폰 적립 WhiteList로 변경 예정
		boolean bSktSaveMileage = true;
		if (bSktTest) {
			bSktSaveMileage = this.purchaseOrderPolicyService.isMileageSaveSktTestDevice(prchsDtlMore.getTenantId(),
					purchase.getDeviceId(), prchsDtlMore.getTenantProdGrpCd());
		}

		if (StringUtils.isNotBlank(req.getProcSubStatusCd()) && bSktSaveMileage) { // T멤버쉽 정보 받은 경우

			this.logger.info("PRCHS,ORDER,SAC,COMPLETE,MILEAGE,CHECK,BYREQ,({},{}),{},{},{}", bSktTest,
					bSktSaveMileage, prchsDtlMore.getPrchsId(), userGrade, sbtMileageRateInfo.toString());

			mileageSubInfo.setPrchsReqPathCd(prchsDtlMore.getPrchsReqPathCd());

			mileageSubInfo.setTargetPaymentAmt(req.getTargetPaymentAmt());
			mileageSubInfo.setSaveExpectAmt(req.getSaveExpectAmt());
			mileageSubInfo.setSaveResultAmt(req.getSaveResultAmt());
			mileageSubInfo.setSaveTypeCd(req.getProcSubStatusCd());

		} else {
			mileageSubInfo.setPrchsReqPathCd(null);

			// 적립 가능 결제수단 금액
			String availPayMtd = this.purchaseOrderPolicyService.searchtMileageSavePaymentMethod(
					prchsDtlMore.getTenantId(), prchsDtlMore.getTenantProdGrpCd());
			if (bSktSaveMileage == false) {
				availPayMtd = availPayMtd.replaceAll("11;", "");
			}

			this.logger.info("PRCHS,ORDER,SAC,COMPLETE,MILEAGE,CHECK,BYSERVER,({},{}),{},{},{},{}", bSktTest,
					bSktSaveMileage, prchsDtlMore.getPrchsId(), userGrade, sbtMileageRateInfo.toString(), availPayMtd);

			double availPayAmt = 0.0;
			for (Payment paymentInfo : paymentList) {
				if (StringUtils.contains(availPayMtd,
						PaymethodUtil.convert2PayPlanetCodeWithoutPointCode(paymentInfo.getPaymentMtdCd()))) {
					availPayAmt += paymentInfo.getPaymentAmt();
				}
			}
			mileageSubInfo.setTargetPaymentAmt(availPayAmt);

			if (availPayAmt > 0.0 && rateMap.get(userGrade) != null && rateMap.get(userGrade) > 0) {

				// 적립예정 금액: 10원 미만 버림
				int expectAmt = (int) (availPayAmt * rateMap.get(userGrade) * 0.01);

				// 적립예정 이력 총 금액
				String targetDt = "20" + prchsDtlMore.getPrchsId().substring(0, 12);
				int preReserveAmt = this.membershipReserveService.searchSaveExpectTotalAmt(prchsDtlMore.getTenantId(),
						userKey, targetDt, null);

				int limitAmt = this.purchaseOrderPolicyService.searchtMileageSaveLimit(prchsDtlMore.getTenantId(),
						prchsDtlMore.getTenantProdGrpCd());

				if (preReserveAmt >= limitAmt) { // 한도초과
					mileageSubInfo.setSaveResultAmt(0);
					mileageSubInfo.setSaveTypeCd(PurchaseConstants.MEMBERSHIP_SAVE_TYPE_OVER);

				} else if ((preReserveAmt + expectAmt) <= limitAmt) { // 전체적립
					mileageSubInfo.setSaveResultAmt(expectAmt);
					mileageSubInfo.setSaveTypeCd(PurchaseConstants.MEMBERSHIP_SAVE_TYPE_ALL);

				} else { // 부분적립
					mileageSubInfo.setSaveResultAmt(limitAmt - preReserveAmt);
					mileageSubInfo.setSaveTypeCd(PurchaseConstants.MEMBERSHIP_SAVE_TYPE_PART);
				}

				mileageSubInfo.setSaveExpectAmt(expectAmt);
			}
		}

		this.logger.info("PRCHS,ORDER,SAC,COMPLETE,MILEAGE,CHECK,PAY,{},{}", prchsDtlMore.getPrchsId(),
				ReflectionToStringBuilder.toString(mileageSubInfo, ToStringStyle.SHORT_PREFIX_STYLE));

		List<MembershipReserve> membershipReserveList = null;
		if (mileageSubInfo.getTargetPaymentAmt() > 0 && mileageSubInfo.getProdSaveRate() > 0) {
			membershipReserveList = this.purchaseOrderMakeDataService.makeMembershipReserveList(prchsDtlMoreList,
					mileageSubInfo);
		}

		// 이력생성
		CreateCompletePurchaseScReq createCompletePurchaseScReq = new CreateCompletePurchaseScReq();
		createCompletePurchaseScReq.setPrchsDtlMoreList(prchsDtlMoreList);
		createCompletePurchaseScReq.setPaymentList(paymentList);
		createCompletePurchaseScReq.setPrchsProdCntList(prchsProdCntList);
		createCompletePurchaseScReq.setUniqueTid(uniqueTid);
		createCompletePurchaseScReq.setMembershipReserveList(membershipReserveList);

		try {
			this.purchaseOrderSCI.completePurchase(createCompletePurchaseScReq);

		} catch (StorePlatformException e) {
			throw (this.purchaseOrderAssistService.isDuplicateKeyException(e) ? new StorePlatformException(
					"SAC_PUR_6110") : e); // 중복된 구매요청 체크

		} catch (DuplicateKeyException e) {
			throw new StorePlatformException("SAC_PUR_6110");

		} catch (Exception e) {
			throw new StorePlatformException("SAC_PUR_7202", e);
		}

		return prchsId;
	}

	// ======================================================================================================================

	/*
	 * <pre> 구매 전처리 - 구매 정합성 체크. </pre>
	 * 
	 * @param purchaseOrderInfo 구매진행 정보
	 */
	private void validatePurchaseOrder(PurchaseOrderInfo purchaseOrderInfo) {

		ErrorInfo errorInfo = null;

		try {
			this.purchaseOrderValidationService.validateMember(purchaseOrderInfo); // 회원 적합성 체크
			this.purchaseOrderValidationService.validateProduct(purchaseOrderInfo); // 상품 적합성 체크
			this.purchaseOrderPolicyService.checkUserPolicy(purchaseOrderInfo); // 회원정책 체크 : TestMDN
			this.purchaseOrderValidationService.validatePurchase(purchaseOrderInfo); // 구매 적합성(&가능여부) 체크

		} catch (StorePlatformException e) {
			errorInfo = e.getErrorInfo();
			throw e;

		} finally {

			// T Log SET
			if (purchaseOrderInfo.getPurchaseUser() != null) {
				final String mbrId = purchaseOrderInfo.getPurchaseUser().getUserId();
				final String deviceId = purchaseOrderInfo.getPurchaseUser().getDeviceId();
				new TLogUtil().set(new ShuttleSetter() {
					@Override
					public void customize(TLogSentinelShuttle shuttle) {
						shuttle.mbr_id(mbrId).device_id(deviceId);
					}
				});
			}

			// 구매 선결조건 체크 결과 LOGGING
			if (errorInfo == null) {
				new TLogUtil().log(new ShuttleSetter() {
					@Override
					public void customize(TLogSentinelShuttle shuttle) {
						shuttle.log_id(PurchaseConstants.TLOG_ID_PURCHASE_ORDER_PRECHECK).result_code("SUCC");
					}
				});

			} else {
				String msg = null;
				try {
					msg = this.messageSourceAccessor.getMessage(errorInfo.getCode());
				} catch (NoSuchMessageException e) {
					msg = "";
				}

				final String result_code = errorInfo.getCode();
				final String result_message = msg;
				final String exception_log = errorInfo.getCause() == null ? "" : errorInfo.getCause().toString();

				new TLogUtil().log(new ShuttleSetter() {
					@Override
					public void customize(TLogSentinelShuttle shuttle) {
						shuttle.log_id(PurchaseConstants.TLOG_ID_PURCHASE_ORDER_PRECHECK).result_code(result_code)
								.result_message(result_message).exception_log(exception_log);
					}
				});
			}
		}

	}

	/*
	 * 
	 * <pre> CLINK 상품 별 구매 결과 응답 값 생성. </pre>
	 * 
	 * @param productList 구매요청 상품 목록
	 * 
	 * @return 상품 별 구매 결과
	 */
	private String makeClinkResProductResult(List<PurchaseProduct> productList) {
		StringBuffer sbResult = new StringBuffer();

		for (PurchaseProduct product : productList) {
			sbResult.append(product.getProdId()).append(":")
					.append(StringUtils.defaultIfBlank(product.getResultCd(), "0000")).append(",");
		}

		if (sbResult.length() > 0) {
			sbResult.setLength(sbResult.length() - 1);
		}

		return sbResult.toString();
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
		SearchPurchaseListByStatusScReq reqSearch = new SearchPurchaseListByStatusScReq();
		reqSearch.setTenantId(tenantId);
		reqSearch.setPrchsId(prchsId);
		reqSearch.setStatusCd(null);

		SearchPurchaseListByStatusScRes searchPurchaseListRes = this.purchaseOrderSearchSCI
				.searchPurchaseListByStatus(reqSearch);
		List<PrchsDtlMore> prchsDtlMoreList = searchPurchaseListRes.getPrchsDtlMoreList();
		if (prchsDtlMoreList.size() < 1) {
			throw new StorePlatformException("SAC_PUR_7101");
		} else {
			if (StringUtils.equals(prchsDtlMoreList.get(0).getStatusCd(), PurchaseConstants.PRCHS_STATUS_COMPT)) {
				throw new StorePlatformException("SAC_PUR_7105");
			} else if (StringUtils.equals(prchsDtlMoreList.get(0).getStatusCd(),
					PurchaseConstants.PRCHS_STATUS_RESERVATION) == false) {
				throw new StorePlatformException("SAC_PUR_7101");
			}
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
				sbOcbAccum.append(sktTestOrSkpCorp ? "11:0.0;" : "11:0.1;"); // 시험폰, SKP법인폰 결제 제외
			} else {
				sbOcbAccum.append("12:0.1;"); // 다날
			}

			sbOcbAccum.append("13:0.1;14:0.1;25:0.1"); // 신용카드, PayPin, T store Cash

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
	 * @param bIapAutoPrchs IAP 월자동결제 상품 여부
	 * 
	 * @param bS2sAutoPrchs IAP S2S 월자동결제 상품 여부
	 * 
	 * @param prchsProdCnt 구매하는 상품 갯수
	 * 
	 * @return 결제Page 템플릿 코드
	 */
	private String adjustPaymentPageTemplate(String prchsCaseCd, String tenantProdGrpCd, String cmpxProdClsfCd,
			boolean bIapAutoPrchs, boolean bS2sAutoPrchs, int prchsProdCnt) {
		if (StringUtils.equals(prchsCaseCd, PurchaseConstants.PRCHS_CASE_GIFT_CD)) {
			return PurchaseConstants.PAYMENT_PAGE_TEMPLATE_GIFT; // 선물: TC06

		} else {
			if (bS2sAutoPrchs) { // IAP S2S 월자동결제 상품
				return PurchaseConstants.PAYMENT_PAGE_TEMPLATE_IAP_S2S_AUTOPAY; // S2S 자동결제: TC08

			} else if (bIapAutoPrchs) { // IAP 월자동결제 상품
				return PurchaseConstants.PAYMENT_PAGE_TEMPLATE_IAP_AUTOPAY; // IAP 자동결제: TC07

			} else if (StringUtils.startsWith(tenantProdGrpCd, PurchaseConstants.TENANT_PRODUCT_GROUP_SHOPPING)) {
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
				// return PurchaseConstants.PAYMENT_PAGE_TEMPLATE_LOAN_OWN; // 대여/소장: TC03
				// 대여/소장 TAB 제거 : 2014/08/27 적용
				return PurchaseConstants.PAYMENT_PAGE_TEMPLATE_NORMAL; // 일반: TC01

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
			if (e instanceof StorePlatformException) {
				throw (StorePlatformException) e;
			} else {
				throw new StorePlatformException("SAC_PUR_7209", e);
			}
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
	 * @param cmpxProdClsfCd 정액상품 타입 코드
	 * 
	 * @param payAmt 결제할 금액
	 * 
	 * @return 재정의 된 결제 수단 정보
	 */
	private String adjustPaymethod(String sktPaymethodInfo, String tenantId, String systemId, String tenantProdGrpCd,
			String prodCaseCd, String cmpxProdClsfCd, double payAmt) {
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

		// 시리즈 패스
		if (StringUtils.equals(cmpxProdClsfCd, PurchaseConstants.FIXRATE_PROD_TYPE_VOD_SERIESPASS)) {
			return sbPaymethodInfo.toString().replaceAll("14:0:0;", "");
		} else {
			return sbPaymethodInfo.toString();
		}
	}

	/*
	 * 
	 * <pre> 판매자 회원 정보 조회 - 쇼핑 상품인 경우에만 조회. </pre>
	 * 
	 * @param sellerKey 판매자 내부 회원 번호
	 * 
	 * @param tenantProdGrpCd 테넌트 상품 분류 코드
	 * 
	 * @return 판매자 정보
	 */
	private SellerMbrSac searchSellerInfo(String sellerKey, String tenantProdGrpCd) {
		SellerMbrSac sellerMbrSac = null;

		if (StringUtils.isNotBlank(sellerKey)) {
			sellerMbrSac = this.purchaseMemberRepository.searchSellerInfo(sellerKey);
		}

		if (sellerMbrSac == null) {
			sellerMbrSac = new SellerMbrSac();

			// 쇼핑상품 경우, 디폴트 값 정의
			if (StringUtils.startsWith(tenantProdGrpCd, PurchaseConstants.TENANT_PRODUCT_GROUP_SHOPPING)) {
				sellerMbrSac.setSellerNickName(PurchaseConstants.SHOPPING_SELLER_DEFAULT_NAME); // 판매자명
				sellerMbrSac.setSellerEmail(PurchaseConstants.SHOPPING_SELLER_DEFAULT_EMAIL); // 판매자 이메일 주소
				sellerMbrSac.setRepPhone(PurchaseConstants.SHOPPING_SELLER_DEFAULT_TEL); // 판매자 전화번호
			}
		}

		return sellerMbrSac;
	}

	/*
	 * 
	 * <pre> 구매 예약 정보의 특정 값을 구분자로 연결. </pre>
	 * 
	 * @param prchsDtlMoreList 구매예약정보 목록
	 * 
	 * @param fieldName 원하는 값
	 * 
	 * @param separator 구분자
	 * 
	 * @return 연결된 값
	 */
	private String concatResvDesc(List<PrchsDtlMore> prchsDtlMoreList, String fieldName, String separator) {
		StringBuffer sbConcat = new StringBuffer();
		String resvDesc = null;
		int tmpIdx = 0;
		int cnt = 0;

		for (PrchsDtlMore resvPrchsDtlMore : prchsDtlMoreList) {

			if (cnt > 0) {
				sbConcat.append(separator);
			}
			cnt++;

			resvDesc = resvPrchsDtlMore.getPrchsResvDesc();

			tmpIdx = resvDesc.indexOf(fieldName + "=");
			sbConcat.append(resvDesc.substring(tmpIdx + fieldName.length() + 1,
					resvDesc.indexOf("&", tmpIdx + fieldName.length() + 1)));
		}

		return sbConcat.toString();
	}

	/*
	 * 
	 * <pre> 구매 예약 정보의 특정 값을 리스트로 반환. </pre>
	 * 
	 * @param prchsDtlMoreList 구매예약정보 목록
	 * 
	 * @param fieldName 원하는 값
	 * 
	 * @return 해당 값의 리스트
	 */
	private List<String> concatResvDescByList(List<PrchsDtlMore> prchsDtlMoreList, String fieldName) {
		List<String> valList = new ArrayList<String>();
		String resvDesc = null;
		int tmpIdx = 0;

		for (PrchsDtlMore resvPrchsDtlMore : prchsDtlMoreList) {

			resvDesc = resvPrchsDtlMore.getPrchsResvDesc();

			tmpIdx = resvDesc.indexOf(fieldName + "=");
			valList.add(resvDesc.substring(tmpIdx + fieldName.length() + 1,
					resvDesc.indexOf("&", tmpIdx + fieldName.length() + 1)));
		}

		return valList;
	}

	/*
	 * 
	 * <pre> 프로모션 정보 조회. </pre>
	 * 
	 * @return
	 */
	// private List<VerifyOrderPromotionInfoSac> searchPromotionListTemp(PrchsDtlMore prchsDtlMore) {
	// // TAKTODO:: 프로모션/배너 관리 이전까지 하드코딩
	//
	// // 쇼핑 상품 경우는 프로모션 없는 테스트
	// if (StringUtils.startsWith(prchsDtlMore.getTenantProdGrpCd(), PurchaseConstants.TENANT_PRODUCT_GROUP_SHOPPING)) {
	// return null;
	// }
	//
	// List<VerifyOrderPromotionInfoSac> promotionList = new ArrayList<VerifyOrderPromotionInfoSac>();
	//
	// VerifyOrderPromotionInfoSac promotion = new VerifyOrderPromotionInfoSac();
	// promotion.setPaymentMtdCd(PurchaseConstants.PAYPLANET_PAYMENT_METHOD_CREDIT_CARD);
	// promotion.setTitle("신용카드 2014년 11월 1차 TEST 프로모션");
	// promotion.setImagePath("/data/img/banner/sc/shopping/B_20141106103300066.jpg");
	// promotion.setLinkUrl("m.nate.com");
	// promotion.setBackColorCd("#D5C8EB");
	// promotionList.add(promotion);
	//
	// promotion = new VerifyOrderPromotionInfoSac();
	// promotion.setPaymentMtdCd(PurchaseConstants.PAYPLANET_PAYMENT_METHOD_TMEMBERSHIP);
	// promotion.setTitle("T멤버쉽 2014년 11월 1차 TEST 프로모션");
	// promotion.setImagePath("/data/img/banner/sc/shopping/B_20141106103300066.jpg");
	// promotion.setLinkUrl("m.google.com");
	// promotion.setBackColorCd("#D5C8EB");
	// promotionList.add(promotion);
	//
	// return promotionList;
	// }
}
