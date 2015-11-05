/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.order.controller;

import com.skplanet.pdp.sentinel.shuttle.TLogSentinelShuttle;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.exception.vo.ErrorInfo;
import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.framework.core.util.log.TLogUtil;
import com.skplanet.storeplatform.framework.core.util.log.TLogUtil.ShuttleSetter;
import com.skplanet.storeplatform.purchase.client.order.vo.PrchsDtlMore;
import com.skplanet.storeplatform.sac.client.purchase.vo.order.*;
import com.skplanet.storeplatform.sac.client.purchase.vo.order.CreatePurchaseSacReq.GroupCreateBizPurchase;
import com.skplanet.storeplatform.sac.client.purchase.vo.order.CreatePurchaseSacReq.GroupCreateFreePurchase;
import com.skplanet.storeplatform.sac.client.purchase.vo.order.CreatePurchaseSacReq.GroupCreatePurchase;
import com.skplanet.storeplatform.sac.client.purchase.vo.order.CreatePurchaseSacReq.GroupCreatePurchaseV2;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.purchase.common.service.PayPlanetShopService;
import com.skplanet.storeplatform.sac.purchase.constant.PurchaseConstants;
import com.skplanet.storeplatform.sac.purchase.order.service.*;
import com.skplanet.storeplatform.sac.purchase.order.vo.PurchaseOrderInfo;
import com.skplanet.storeplatform.sac.purchase.order.vo.PurchaseUserDevice;
import com.skplanet.storeplatform.sac.purchase.order.vo.VerifyOrderInfo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 구매 처리 컨트롤러
 *
 * Updated on : 2014. 1. 3. Updated by : 이승택, nTels.
 */
@Controller
@RequestMapping("/purchase/order")
public class PurchaseOrderController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private MessageSourceAccessor messageSourceAccessor;
	@Autowired
	@Qualifier("scPurchase")
	private MessageSourceAccessor scMessageSourceAccessor;

	@Autowired
	private PayPlanetShopService payPlanetShopService;

	@Autowired
	private PurchaseOrderService orderService;
	@Autowired
	private PurchaseOrderPaymentPageService orderPaymentPageService;
	@Autowired
	private PurchaseOrderMakeDataService purchaseOrderMakeDataService;
	@Autowired
	private PurchaseOrderValidationService validationService;
	@Autowired
	private PurchaseOrderPolicyService policyService;

	/**
	 *
	 * <pre>
	 * 구매 요청 처리 V2: 무료구매 경우는 구매완료 처리, 유료구매 경우는 PayPlanet 결제Page 요청을 위한 처리.
	 * </pre>
	 *
	 * @param req
	 *            구매요청 정보
	 * @return 구매요청 처리 결과: 무료구매완료 또는 결제page요청정보
	 */
	@RequestMapping(value = "/create/v2", method = RequestMethod.POST)
	@ResponseBody
	public CreatePurchaseSacRes createPurchaseV2(
			@RequestBody @Validated(GroupCreatePurchaseV2.class) CreatePurchaseSacReq req,
			SacRequestHeader sacRequestHeader, HttpServletRequest request) {
		return this.createPurchase(req, sacRequestHeader, request);
	}

	/**
	 *
	 * <pre>
	 * 구매 요청 처리: 무료구매 경우는 구매완료 처리, 유료구매 경우는 PayPlanet 결제Page 요청을 위한 처리.
	 * </pre>
	 *
	 * @param req
	 *            구매요청 정보
	 * @return 구매요청 처리 결과: 무료구매완료 또는 결제page요청정보
	 */
	@RequestMapping(value = { "/create/v1" }, method = RequestMethod.POST)
	@ResponseBody
	public CreatePurchaseSacRes createPurchase(
			@RequestBody @Validated(GroupCreatePurchase.class) CreatePurchaseSacReq req,
			SacRequestHeader sacRequestHeader, HttpServletRequest request) {
		this.logger.info("PRCHS,ORDER,SAC,CREATE,REQ,{},{}",
				ReflectionToStringBuilder.toString(req, ToStringStyle.SHORT_PREFIX_STYLE), sacRequestHeader);

		this.loggingPurchaseReq(req, sacRequestHeader); // T Log SET

		this.validationService.validatePurchaseRequestParameter(req); // 요청 값 검증

		// 구매진행 정보 세팅
		PurchaseOrderInfo purchaseOrderInfo = this.readyPurchaseOrderInfo(req, sacRequestHeader.getTenantHeader());
		String requestUrl = request.getRequestURL().toString();
		purchaseOrderInfo.setApiVer(Integer.parseInt(requestUrl.substring(requestUrl.indexOf("/v") + 2))); // API 버전

		// 구매 처리: 무료구매완료 처리 || 결제Page 요청 준비작업
		this.orderService.processPurchase(purchaseOrderInfo);

		// 응답 세팅
		CreatePurchaseSacRes res = new CreatePurchaseSacRes();
		res.setPrchsId(purchaseOrderInfo.getPrchsId());
		res.setResultType(purchaseOrderInfo.getResultType());
		res.setPaymentPageUrl(purchaseOrderInfo.getPaymentPageUrl());
		res.setPaymentPageParam(purchaseOrderInfo.getPaymentPageUrlParam());

		this.logger.info("PRCHS,ORDER,SAC,CREATE,RES,{}",
				ReflectionToStringBuilder.toString(res, ToStringStyle.SHORT_PREFIX_STYLE));
		return res;
	}

	/**
	 *
	 * <pre>
	 * 유료상품 무료결제 구매 요청 처리: 일부 외부판매 사이트에 필요하며, 요청한 사이트에 대해 해당 요청 권한이 있는 지 체크하는 부분이 추가된다.
	 * </pre>
	 *
	 * @param req
	 *            구매요청 정보
	 * @return 구매요청 처리 결과: 비과금 구매완료
	 */
	@RequestMapping(value = "/createFreeCharge/v1", method = RequestMethod.POST)
	@ResponseBody
	public CreateFreePurchaseSacRes createFreeChargePurchase(
			@RequestBody @Validated(GroupCreateFreePurchase.class) CreatePurchaseSacReq req,
			SacRequestHeader sacRequestHeader) {
		this.logger.info("PRCHS,ORDER,SAC,CREATEFREE,REQ,{},{}",
				ReflectionToStringBuilder.toString(req, ToStringStyle.SHORT_PREFIX_STYLE), sacRequestHeader);

		this.loggingPurchaseReq(req, sacRequestHeader); // T Log SET

		// 구매진행 정보 세팅
		req.setTotAmt(0.0);
		PurchaseOrderInfo purchaseOrderInfo = this.readyPurchaseOrderInfo(req, sacRequestHeader.getTenantHeader());
		purchaseOrderInfo.setFreeChargeReq(true); // 비과금 요청

		// 비과금 구매완료 처리
		this.orderService.processFreeChargePurchase(purchaseOrderInfo);

		// 응답 세팅
		CreateFreePurchaseSacRes res = new CreateFreePurchaseSacRes();
		res.setPrchsId(purchaseOrderInfo.getPrchsId());
		res.setResultType(purchaseOrderInfo.getResultType());

		this.logger.info("PRCHS,ORDER,SAC,CREATEFREE,RES,{}",
				ReflectionToStringBuilder.toString(res, ToStringStyle.SHORT_PREFIX_STYLE));
		return res;
	}

	/**
	 *
	 * <pre>
	 * Biz 쿠폰 발행 요청 : 대량의 선물 이력 생성 & biz쿠폰 발급 요청.
	 * </pre>
	 *
	 * @param req
	 *            구매요청 정보
	 * @return 구매요청 처리 결과
	 */
	@RequestMapping(value = "/createBiz/v1", method = RequestMethod.POST)
	@ResponseBody
	public CreateBizPurchaseSacRes createBizPurchase(
			@RequestBody @Validated(GroupCreateBizPurchase.class) CreatePurchaseSacReq req,
			SacRequestHeader sacRequestHeader) {
		this.logger.info("PRCHS,ORDER,SAC,CREATEBIZ,REQ,{},{}",
				ReflectionToStringBuilder.toString(req, ToStringStyle.SHORT_PREFIX_STYLE), sacRequestHeader);

		// 구매진행 정보 세팅
		req.setTotAmt(0.0);
		PurchaseOrderInfo purchaseOrderInfo = this.readyPurchaseOrderInfo(req, sacRequestHeader.getTenantHeader());
		purchaseOrderInfo.setFreeChargeReq(true); // 비과금 요청

		// Biz쿠폰 발행 요청
		int count = this.orderService.processBizPurchase(purchaseOrderInfo);

		// 응답 세팅
		CreateBizPurchaseSacRes res = new CreateBizPurchaseSacRes();
		res.setPrchsId(purchaseOrderInfo.getPrchsId());
		res.setPrchsDt(purchaseOrderInfo.getPrchsDt());
		res.setCount(count);

		this.logger.info("PRCHS,ORDER,SAC,CREATEBIZ,RES,{}",
				ReflectionToStringBuilder.toString(res, ToStringStyle.SHORT_PREFIX_STYLE));
		return res;
	}

	/**
	 *
	 * <pre>
	 * IAP 구매/결제 통합 구매이력 생성 요청.
	 * </pre>
	 *
	 * @param req
	 *            구매/결제 통합 구매이력 생성 요청 정보
	 * @return 구매/결제 통합 구매이력 생성 결과
	 */
	@RequestMapping(value = "/createComplete/v1", method = RequestMethod.POST)
	@ResponseBody
	public CreateCompletePurchaseSacRes createCompletePurchase(
			@RequestBody @Validated CreateCompletePurchaseSacReq req, SacRequestHeader sacRequestHeader) {
		this.logger.info("PRCHS,ORDER,SAC,CREATECOMPLETE,REQ,{},{}",
				ReflectionToStringBuilder.toString(req, ToStringStyle.SHORT_PREFIX_STYLE), sacRequestHeader);

		// IAP 구매/결제 통합 구매이력 생성 처리
		String prchsId = this.orderService.completeIapPurchase(req, sacRequestHeader.getTenantHeader().getTenantId());

		// 응답 세팅
		CreateCompletePurchaseSacRes res = new CreateCompletePurchaseSacRes();
		res.setPrchsId(prchsId);

		this.logger.info("PRCHS,ORDER,SAC,CREATECOMPLETE,RES,{}",
				ReflectionToStringBuilder.toString(res, ToStringStyle.SHORT_PREFIX_STYLE));
		return res;
	}

	/**
	 *
	 * <pre>
	 * 결제 진행 전 구매인증 - 구매 유효성 체크 및 결제수단 정보 정의 등 체크.
	 * </pre>
	 *
	 * @param req
	 *            구매인증 요청 VO
	 * @return 구매인증 응답 VO
	 */
	@RequestMapping(value = "/verifyOrder/v1", method = RequestMethod.POST)
	@ResponseBody
	public VerifyOrderSacRes verifyOrder(@RequestBody @Validated VerifyOrderSacReq req,
			SacRequestHeader sacRequestHeader) {
		this.logger.info("PRCHS,ORDER,SAC,VERIFY,REQ,{},{}",
				ReflectionToStringBuilder.toString(req, ToStringStyle.SHORT_PREFIX_STYLE), sacRequestHeader);

		TenantHeader tenantHeader = sacRequestHeader.getTenantHeader();
		String tenantId = null;
		String systemId = null;

		if (tenantHeader != null) {
			tenantId = tenantHeader.getTenantId();
			systemId = tenantHeader.getSystemId();
		}

		final String purchase_id = req.getPrchsId();
		new TLogUtil().set(new ShuttleSetter() {
			@Override
			public void customize(TLogSentinelShuttle shuttle) {
				shuttle.log_id(PurchaseConstants.TLOG_ID_PURCHASE_ORDER_VERIFY).purchase_id(purchase_id);
			}
		});

		// TAKTODO:: SAP 요청값 체크 03.04 일단 생략
		// if (StringUtils.equals(tenantId, "S01") == false) {
		// if (StringUtils.isBlank(req.getMarketDeviceKey()) || StringUtils.isBlank(req.getDeviceKeyAuth())) {
		// throw new StorePlatformException("SAC_PUR_5100", "SAP 정보 누락");
		// }
		// }

		VerifyOrderInfo verifyOrderInfo = new VerifyOrderInfo();
		verifyOrderInfo.setTenantId(tenantId);
		verifyOrderInfo.setSystemId(systemId);
		verifyOrderInfo.setPrchsId(req.getPrchsId());
		verifyOrderInfo.setMctSpareParam(req.getMctSpareParam());
		verifyOrderInfo.setUserKey(req.getUserKey());
		verifyOrderInfo.setMarketDeviceKey(req.getMarketDeviceKey());
		verifyOrderInfo.setDeviceKeyAuth(req.getDeviceKeyAuth());

		VerifyOrderSacRes res = null;

		StorePlatformException checkException = null;
		try {
			res = this.orderService.verifyPurchaseOrder(verifyOrderInfo);

		} catch (StorePlatformException e) {
			checkException = e;
			throw checkException;

		} catch (Exception e) {
			checkException = new StorePlatformException("SAC_PUR_7223", e);
			throw checkException;

		} finally {
			if (checkException == null) {
				this.logger.info("PRCHS,ORDER,SAC,VERIFY,RES,{},{}", req.getPrchsId(),
						ReflectionToStringBuilder.toString(res, ToStringStyle.SHORT_PREFIX_STYLE));
			} else {
				this.logger.info("PRCHS,ORDER,SAC,VERIFY,RES,ERROR,{},{}", req.getPrchsId(), checkException);
			}

			ErrorInfo errorInfo = (checkException != null ? checkException.getErrorInfo() : null);

			String msg = "";
			if (errorInfo != null) {
				if (StringUtils.isNotBlank(errorInfo.getMessage())) {
					msg = errorInfo.getMessage();
				} else {
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
			}

			final String result_code = (errorInfo != null ? errorInfo.getCode() : PurchaseConstants.TLOG_RESULT_CODE_SUCCESS);
			final String result_message = msg;
			final String exception_log = (errorInfo != null ? (errorInfo.getCause() == null ? "" : errorInfo.getCause()
					.toString()) : "");

			new TLogUtil().set(new ShuttleSetter() {
				@Override
				public void customize(TLogSentinelShuttle shuttle) {
					shuttle.result_code(result_code);
					if (StringUtils.equals(result_code, PurchaseConstants.TLOG_RESULT_CODE_SUCCESS) == false) {
						shuttle.result_message(result_message).exception_log(exception_log);
					}
				}
			});

			if (checkException == null) {
				this.loggingVerifyRes(res);
			}
		}

		return res;
	}

	/**
	 *
	 * <pre>
	 * 결제 처리 결과 Noti.
	 * </pre>
	 *
	 * @param notifyPaymentReq
	 *            결제 결과 정보
	 * @return 결제 결과 처리 응답
	 */
	@RequestMapping(value = "/notifyPayment/v1", method = RequestMethod.POST)
	@ResponseBody
	public NotifyPaymentSacRes notifyPayment(@RequestBody @Validated NotifyPaymentSacReq notifyPaymentReq,
			SacRequestHeader sacRequestHeader) {
		this.logger.info("PRCHS,ORDER,SAC,NOTIFYPAY,REQ,{},{}",
				ReflectionToStringBuilder.toString(notifyPaymentReq, ToStringStyle.SHORT_PREFIX_STYLE),
				sacRequestHeader);

		if (Integer.parseInt(notifyPaymentReq.getResultCd()) != 0) { // 결제실패 경우, 아무 처리 안 함
			this.logger.info("PRCHS,ORDER,SAC,NOTIFYPAY,FAIL,{},{}", notifyPaymentReq.getResultCd(),
					notifyPaymentReq.getResultMsg());
			return new NotifyPaymentSacRes(notifyPaymentReq.getPrchsId(), notifyPaymentReq.getPaymentInfoList().size());
		}

		String tenantId = sacRequestHeader.getTenantHeader().getTenantId();

		// 구매완료 TLog 로그ID 초기세팅
		// final String prchsId = notifyPaymentReq.getPrchsId();
		// new TLogUtil().set(new ShuttleSetter() {
		// @Override
		// public void customize(TLogSentinelShuttle shuttle) {
		// shuttle.log_id(PurchaseCDConstants.TLOG_ID_PURCHASE_ORDER_RESULT).purchase_id(prchsId);
		// }
		// });

		// ------------------------------------------------------------------------------
		// 쇼핑상품 쿠폰 발급요청
		// 구매 확정: 구매상세 내역 상태변경 & 구매 내역 저장 & (선물 경우)발송 상세 내역 저장, 결제내역 저장

		List<PrchsDtlMore> prchsDtlMoreList = this.orderService.confirmPurchase(notifyPaymentReq, tenantId);

		// ------------------------------------------------------------------------------
		// 응답 : PP 결제 여부에 따른 차별 세팅

		NotifyPaymentSacRes res = new NotifyPaymentSacRes(notifyPaymentReq.getPrchsId(), notifyPaymentReq
				.getPaymentInfoList().size());

		boolean bPayPlanet = this.payPlanetShopService.startsWithPayPlanetMID(notifyPaymentReq.getPaymentInfoList()
				.get(0).getTid()); // PayPlanet 결제 여부

		PrchsDtlMore prchsDtlMore = prchsDtlMoreList.get(0);

		if (bPayPlanet) { // Pay Planet 결제 경우

			// 쇼핑상품 배송지 입력 URL
			if (StringUtils.startsWith(prchsDtlMore.getTenantProdGrpCd(),
					PurchaseConstants.TENANT_PRODUCT_GROUP_SHOPPING)
					&& prchsDtlMoreList.size() == 1
					&& StringUtils.equals(prchsDtlMore.getPrchsCaseCd(), PurchaseConstants.PRCHS_CASE_PURCHASE_CD)) {
				res.setShippingUrl(prchsDtlMore.getCpnDlvUrl());
			}

		} else { // T store 결제 경우

			// 구매완료 Noti 정보
			if (StringUtils.startsWith(prchsDtlMore.getTenantProdGrpCd(), PurchaseConstants.TENANT_PRODUCT_GROUP_IAP) == false) {

				Map<String, String> reservedDataMap = this.purchaseOrderMakeDataService
						.parseReservedDataByMap(prchsDtlMore.getPrchsResvDesc());

				res.setPrchsDt(prchsDtlMore.getPrchsDt());
				res.setUserKey(prchsDtlMore.getUseInsdUsermbrNo());
				res.setDeviceKey(prchsDtlMore.getUseInsdDeviceId());
				res.setType(PurchaseConstants.TSTORE_NOTI_TYPE_NORMALPAY);
				res.setPublishType(reservedDataMap.get("tstoreNotiPublishType"));
			}
		}

		this.logger.info("PRCHS,ORDER,SAC,NOTIFYPAY,RES,{}",
				ReflectionToStringBuilder.toString(res, ToStringStyle.SHORT_PREFIX_STYLE));
		return res;
	}

	/*
	 * <pre> 구매요청 파라미터와 헤더 정보로 구매처리 진행을 위한 정보 개체 세팅. </pre>
	 * 
	 * @param createPurchaseSacReq 구매요청 VO
	 * 
	 * @param tenantHeader 헤더 테넌트 정보
	 * 
	 * @return 구매처리 진행 정보 VO
	 */
	private PurchaseOrderInfo readyPurchaseOrderInfo(CreatePurchaseSacReq createPurchaseSacReq,
			TenantHeader tenantHeader) {
		PurchaseOrderInfo purchaseOrderInfo = new PurchaseOrderInfo(createPurchaseSacReq);
		purchaseOrderInfo.setTenantId(tenantHeader.getTenantId()); // 구매(선물발신) 테넌트 ID
		purchaseOrderInfo.setSystemId(tenantHeader.getSystemId()); // 구매(선물발신) 시스템 ID
		purchaseOrderInfo.setLangCd(tenantHeader.getLangCd()); // 언어 코드
		purchaseOrderInfo.setUserKey(createPurchaseSacReq.getUserKey()); // 구매(선물발신) 내부 회원 번호
		purchaseOrderInfo.setDeviceKey(createPurchaseSacReq.getDeviceKey()); // 구매(선물발신) 내부 디바이스 ID
		purchaseOrderInfo.setOpmdNo(createPurchaseSacReq.getOpmdNo()); // OPMD 번호
		purchaseOrderInfo.setPrchsReqPathCd(createPurchaseSacReq.getPrchsReqPathCd()); // 구매 요청 경로 코드
		purchaseOrderInfo.setCurrencyCd(createPurchaseSacReq.getCurrencyCd()); // 통화 코드
		purchaseOrderInfo.setSaleAmtProcType(createPurchaseSacReq.getSaleAmtProcType()); // 판매금액 처리 타입
		purchaseOrderInfo.setClientIp(createPurchaseSacReq.getClientIp()); // 클라이언트 IP
		purchaseOrderInfo.setNetworkTypeCd(createPurchaseSacReq.getNetworkTypeCd()); // 네트워크 타입 코드
		purchaseOrderInfo.setPrchsCaseCd(createPurchaseSacReq.getPrchsCaseCd()); // 구매 유형 코드
		purchaseOrderInfo.setTenantProdGrpCd(createPurchaseSacReq.getTenantProdGrpCd()); // 테넌트 상품 분류 코드
		purchaseOrderInfo.setDeviceModelCd(createPurchaseSacReq.getDeviceModelCd()); // 디바이스 모델 코드
		purchaseOrderInfo.setTelecomCd(createPurchaseSacReq.getTelecomCd()); // 통신사
		if (StringUtils.equals(createPurchaseSacReq.getPrchsCaseCd(), PurchaseConstants.PRCHS_CASE_GIFT_CD)) {
			purchaseOrderInfo.setGift(true); // 선물 여부

			List<PurchaseUserDevice> receiveUserList = new ArrayList<PurchaseUserDevice>();
			PurchaseUserDevice purchaseUserDevice = null;

			if (createPurchaseSacReq.getReceiverList() == null) {
				purchaseUserDevice = new PurchaseUserDevice();
				purchaseUserDevice.setTenantId(tenantHeader.getTenantId()); // 선물수신 테넌트 ID
				purchaseUserDevice.setUserKey(createPurchaseSacReq.getRecvUserKey()); // 선물수신 내부 회원 번호
				purchaseUserDevice.setDeviceKey(createPurchaseSacReq.getRecvDeviceKey()); // 선물수신 내부 디바이스 ID
				receiveUserList.add(purchaseUserDevice);

			} else {
				for (PurchaseUserInfo receiver : createPurchaseSacReq.getReceiverList()) {
					purchaseUserDevice = new PurchaseUserDevice();
					purchaseUserDevice.setTenantId(tenantHeader.getTenantId());
					purchaseUserDevice.setUserKey(receiver.getUserKey());
					purchaseUserDevice.setDeviceKey(receiver.getDeviceKey());
					purchaseUserDevice.setDeviceId(receiver.getDeviceId());
					purchaseUserDevice.setGiftMsg(receiver.getGiftMsg());
					receiveUserList.add(purchaseUserDevice);
				}
			}
			purchaseOrderInfo.setReceiveUserList(receiveUserList);
		}
		purchaseOrderInfo.setImei(createPurchaseSacReq.getImei()); // 단말 식별 번호
		purchaseOrderInfo.setUacd(createPurchaseSacReq.getUacd()); // 단말 모델 식별 번호
		purchaseOrderInfo.setSid(StringUtils.defaultIfBlank(createPurchaseSacReq.getSid(),
				createPurchaseSacReq.getSimNo())); // SIM Serial Number TAKTODO:: simNo 제거
		purchaseOrderInfo.setFlag(createPurchaseSacReq.getFlag()); // SMS 인증 필요 여부
		purchaseOrderInfo.setMediaId(createPurchaseSacReq.getMediaId()); // CPS CPID

		purchaseOrderInfo.setOfferingId(createPurchaseSacReq.getOfferingId()); // 오퍼링 ID
		purchaseOrderInfo.setChargeMemberId(createPurchaseSacReq.getChargeMemberId()); // 충전자ID
		purchaseOrderInfo.setChargeMemberNm(createPurchaseSacReq.getChargeMemberNm()); // 충전자명

		// offeringId, resvCol01(암묵적 오퍼링 ID) 처리 : 오퍼링 서비스를 SAC가 수용 시 관련 컬럼 생성 및 예약필드 암묵적 사용 제거
		// Biz쿠폰 경우 productList 없으니, 예외처리
		if (CollectionUtils.isNotEmpty(createPurchaseSacReq.getProductList())
				&& StringUtils.isBlank(createPurchaseSacReq.getProductList().get(0).getResvCol01())) {
			createPurchaseSacReq.getProductList().get(0).setResvCol01(createPurchaseSacReq.getOfferingId());
		}

		purchaseOrderInfo.setTotAmt(createPurchaseSacReq.getTotAmt()); // 총 결제 금액
		purchaseOrderInfo.setRealTotAmt(createPurchaseSacReq.getTotAmt()); // 최종 결제 총 금액

		// 상품 속성

		String tenantProdGrpCd = purchaseOrderInfo.getTenantProdGrpCd();

		if (StringUtils.endsWith(tenantProdGrpCd, PurchaseConstants.TENANT_PRODUCT_GROUP_SUFFIX_FIXRATE)
				|| StringUtils.startsWith(tenantProdGrpCd, PurchaseConstants.TENANT_PRODUCT_GROUP_DTL_GAMECASH_FIXRATE)
				|| StringUtils.startsWith(tenantProdGrpCd, PurchaseConstants.TENANT_PRODUCT_GROUP_DTL_BOOKSCASH_FIXRATE)) {
			purchaseOrderInfo.setFlat(true); // 정액 상품 여부
		}

		if (StringUtils.startsWith(tenantProdGrpCd, PurchaseConstants.TENANT_PRODUCT_GROUP_VOD)) {
			purchaseOrderInfo.setVod(true); // VOD 상품 여부
			if (purchaseOrderInfo.isFlat()) {
				purchaseOrderInfo.setVodFlat(true); // VOD정액 상품 여부
			}
		} else if (StringUtils.startsWith(tenantProdGrpCd, PurchaseConstants.TENANT_PRODUCT_GROUP_EBOOKCOMIC)) {
			purchaseOrderInfo.setEbookcomic(true); // 이북/코믹 상품 여부
			if (purchaseOrderInfo.isFlat()) {
				purchaseOrderInfo.setEbookcomicFlat(true); // 이북/코믹 전권 소장/대여 상품 여부
			}
		} else if (StringUtils.startsWith(tenantProdGrpCd, PurchaseConstants.TENANT_PRODUCT_GROUP_SHOPPING)) {
			purchaseOrderInfo.setShopping(true); // 쇼핑 상품 여부
			purchaseOrderInfo.setPossibleDuplication(true); // 중복 구매 가능 여부
			if (StringUtils.equals(purchaseOrderInfo.getPrchsReqPathCd(), PurchaseConstants.PRCHS_REQ_PATH_BIZ_COUPON)) {
				purchaseOrderInfo.setBizShopping(true); // BIZ 쇼핑 상품 여부
			}
		} else if (StringUtils.startsWith(tenantProdGrpCd, PurchaseConstants.TENANT_PRODUCT_GROUP_IAP)) {
			purchaseOrderInfo.setIap(true); // IAP 상품 여부
			// purchaseOrderInfo.setPossibleDuplication(true); // 중복 구매 가능 여부 : IAP은 기구매체크 제외 2014.06.18.
			// possibleDuplication: 중복 구매 가능 여부 - 상품 정합성 체크 (상품 조회) 시 세팅
			// iapCommercial: IAP 정식판 전환상품 존재 여부 - 상품 정합성 체크 (상품 조회) 시 세팅

		} else if (StringUtils.startsWith(tenantProdGrpCd, PurchaseConstants.TENANT_PRODUCT_GROUP_RINGBELL)) {
			purchaseOrderInfo.setRingbell(true); // 컬러링&벨소리 상품 여부

		} else if (StringUtils.startsWith(tenantProdGrpCd, PurchaseConstants.TENANT_PRODUCT_GROUP_MUSIC)) {
			if (StringUtils.startsWith(tenantProdGrpCd.substring(8), PurchaseConstants.DISPLAY_TOP_MENU_ID_PHONEDECO)) {
				purchaseOrderInfo.setRingbell(true); // 컬러링&벨소리 상품 여부
			}

		} else if (StringUtils.startsWith(tenantProdGrpCd, PurchaseConstants.TENANT_PRODUCT_GROUP_DTL_GAMECASH_FIXRATE)) {
			purchaseOrderInfo.setGamecash(true); // 게임캐쉬 상품 여부
		}

		// 선물 수신자 정보
		purchaseOrderInfo.setNmDelivery(createPurchaseSacReq.getNmDelivery());
		purchaseOrderInfo.setNoMdnDelivery(createPurchaseSacReq.getNoMdnDelivery());

		// CLINK 예외 처리용
		if (StringUtils.equals(createPurchaseSacReq.getPrchsReqPathCd(), PurchaseConstants.PRCHS_REQ_PATH_CLINK)) {
			purchaseOrderInfo.setClink(true);
		}

		return purchaseOrderInfo;
	}

	/*
	 * 
	 * <pre> 구매인입 T Log 작성. </pre>
	 * 
	 * @param req 구매요청 VO
	 * 
	 * @param sacRequestHeader 요청 헤더
	 */
	private void loggingPurchaseReq(CreatePurchaseSacReq req, SacRequestHeader sacRequestHeader) {
		final String insdUsermbrNo = req.getUserKey();
		final String insdDeviceId = req.getDeviceKey();
		final String systemId = sacRequestHeader.getTenantHeader().getSystemId();
		final String clientIp = req.getClientIp();
		final String prchsReqPathCd = req.getPrchsReqPathCd();
		final String prchsCaseCd = req.getPrchsCaseCd();
		final String networkTypeCd = req.getNetworkTypeCd();
		final List<String> prodIdList = new ArrayList<String>();
		final List<Long> prodPriceList = new ArrayList<Long>();
		for (CreatePurchaseSacReqProduct product : req.getProductList()) {
			prodIdList.add(product.getProdId());
			prodPriceList.add(product.getProdAmt().longValue());
		}

		new TLogUtil().set(new ShuttleSetter() {
			@Override
			public void customize(TLogSentinelShuttle shuttle) {
				shuttle.log_id(PurchaseConstants.TLOG_ID_PURCHASE_ORDER_REQUEST).insd_usermbr_no(insdUsermbrNo)
						.insd_device_id(insdDeviceId).system_id(systemId).purchase_channel(prchsReqPathCd)
						.purchase_inflow_channel(prchsCaseCd).device_ip(clientIp).network_type(networkTypeCd)
						.product_id(prodIdList).product_price(prodPriceList);
			}
		});
	}

	/*
	 * 
	 * <pre> 구매인증 응답 T Log 작성. </pre>
	 * 
	 * @param req 구매요청 VO
	 * 
	 * @param sacRequestHeader 요청 헤더
	 */
	private void loggingVerifyRes(VerifyOrderSacRes verifyRes) {
		final String one_id = verifyRes.getOneId();
		final String flg_tele_billing_agree = verifyRes.getFlgTeleBillingAgree();
		final String flg_ocb_use_agree = verifyRes.getFlgOcbUseAgree();
		final String flg_product_status = verifyRes.getFlgProductStatus();
		final String flg_mbr_status = verifyRes.getFlgMbrStatus();
		final String type_danal_content = verifyRes.getTypeDanalContent();
		final String approval_sd = verifyRes.getApprovalSd();
		final String cancel_sd = verifyRes.getCancelSd();
		final String skt_limit_type = verifyRes.getTypeSktLimit();
		final String max_amt_rate_cd = verifyRes.getCdMaxAmtRate();
		final String ocb_save_info = verifyRes.getCdOcbSaveInfo();
		final String ocb_auth_mtd = verifyRes.getOcbAuthMtdCd();
		final String ocb_num = verifyRes.getNoOcbCard();
		final String coupon_list = verifyRes.getCouponList();
		final String cash_point_list = verifyRes.getCashPointList();
		final String test_mdn_type = verifyRes.getTypeTestMdn();
		final String payment_template = verifyRes.getCdPaymentTemplate();
		final String top_menu_id = verifyRes.getTopMenuId();
		final String bonus_cash_point = verifyRes.getBonusCashPoint();
		final String bonus_cash_usable_day = verifyRes.getBonusCashUsableDayCnt();
		final String after_payment_dt = verifyRes.getAfterAutoPayDt();
		final String use_start_dt = verifyRes.getUseStartDt();
		final String use_expr_dt = verifyRes.getUseExprDt();
		final String dwld_available_day_cnt = verifyRes.getDwldAvailableDayCnt();
		final String use_period_cnt = verifyRes.getUsePeriodCnt();
		final String prod_kind = verifyRes.getProdKind();
		VerifyOrderIapInfoSac iapProdInfo = verifyRes.getIapProdInfo();
		if (iapProdInfo == null) {
			iapProdInfo = new VerifyOrderIapInfoSac();
		}
		final String iap_post_back_url = iapProdInfo.getPostbackUrl();
		final String iap_prod_kind = iapProdInfo.getProdKind();
		final String iap_prod_case = iapProdInfo.getProdCase();
		final Integer iap_use_period = iapProdInfo.getUsePeriod();

		new TLogUtil().set(new ShuttleSetter() {
			@Override
			public void customize(TLogSentinelShuttle shuttle) {
				shuttle.log_id(PurchaseConstants.TLOG_ID_PURCHASE_ORDER_VERIFY).one_id(one_id)
						.flg_tele_billing_agree(flg_tele_billing_agree).flg_ocb_use_agree(flg_ocb_use_agree)
						.flg_product_status(flg_product_status).flg_mbr_status(flg_mbr_status)
						.type_danal_content(type_danal_content).approval_sd(approval_sd).cancel_sd(cancel_sd)
						.skt_limit_type(skt_limit_type).max_amt_rate_cd(max_amt_rate_cd).ocb_save_info(ocb_save_info)
						.ocb_auth_mtd(ocb_auth_mtd).ocb_num(ocb_num).coupon_list(coupon_list)
						.cash_point_list(cash_point_list).test_mdn_type(test_mdn_type)
						.payment_template(payment_template).top_menu_id(top_menu_id).bonus_cash_point(bonus_cash_point)
						.bonus_cash_usable_day(bonus_cash_usable_day).after_payment_dt(after_payment_dt)
						.use_start_dt(use_start_dt).use_expr_dt(use_expr_dt)
						.dwld_available_day_cnt(dwld_available_day_cnt).use_period_cnt(use_period_cnt)
						.prod_kind(prod_kind).iap_post_back_url(iap_post_back_url).iap_prod_kind(iap_prod_kind)
						.iap_prod_case(iap_prod_case)
						.iap_use_period(iap_use_period == null ? null : String.valueOf(iap_use_period.intValue()));
			}
		});
	}
}
