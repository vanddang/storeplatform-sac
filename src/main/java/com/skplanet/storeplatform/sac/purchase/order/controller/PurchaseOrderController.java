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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.pdp.sentinel.shuttle.TLogSentinelShuttle;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.exception.vo.ErrorInfo;
import com.skplanet.storeplatform.framework.core.util.log.TLogUtil;
import com.skplanet.storeplatform.framework.core.util.log.TLogUtil.ShuttleSetter;
import com.skplanet.storeplatform.purchase.client.order.vo.PrchsDtlMore;
import com.skplanet.storeplatform.sac.client.purchase.vo.order.CreateBizPurchaseSacRes;
import com.skplanet.storeplatform.sac.client.purchase.vo.order.CreateCompletePurchaseSacReq;
import com.skplanet.storeplatform.sac.client.purchase.vo.order.CreateCompletePurchaseSacRes;
import com.skplanet.storeplatform.sac.client.purchase.vo.order.CreateFreePurchaseSacRes;
import com.skplanet.storeplatform.sac.client.purchase.vo.order.CreatePurchaseSacReq;
import com.skplanet.storeplatform.sac.client.purchase.vo.order.CreatePurchaseSacReq.GroupCreateBizPurchase;
import com.skplanet.storeplatform.sac.client.purchase.vo.order.CreatePurchaseSacReq.GroupCreateFreePurchase;
import com.skplanet.storeplatform.sac.client.purchase.vo.order.CreatePurchaseSacReq.GroupCreatePurchase;
import com.skplanet.storeplatform.sac.client.purchase.vo.order.CreatePurchaseSacReqProduct;
import com.skplanet.storeplatform.sac.client.purchase.vo.order.CreatePurchaseSacRes;
import com.skplanet.storeplatform.sac.client.purchase.vo.order.NotifyPaymentSacReq;
import com.skplanet.storeplatform.sac.client.purchase.vo.order.NotifyPaymentSacRes;
import com.skplanet.storeplatform.sac.client.purchase.vo.order.PurchaseUserInfo;
import com.skplanet.storeplatform.sac.client.purchase.vo.order.VerifyOrderSacReq;
import com.skplanet.storeplatform.sac.client.purchase.vo.order.VerifyOrderSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.purchase.constant.PurchaseConstants;
import com.skplanet.storeplatform.sac.purchase.order.service.PurchaseOrderMakeDataService;
import com.skplanet.storeplatform.sac.purchase.order.service.PurchaseOrderPaymentPageService;
import com.skplanet.storeplatform.sac.purchase.order.service.PurchaseOrderPolicyService;
import com.skplanet.storeplatform.sac.purchase.order.service.PurchaseOrderPostService;
import com.skplanet.storeplatform.sac.purchase.order.service.PurchaseOrderService;
import com.skplanet.storeplatform.sac.purchase.order.service.PurchaseOrderValidationService;
import com.skplanet.storeplatform.sac.purchase.order.vo.PurchaseOrderInfo;
import com.skplanet.storeplatform.sac.purchase.order.vo.PurchaseProduct;
import com.skplanet.storeplatform.sac.purchase.order.vo.PurchaseUserDevice;
import com.skplanet.storeplatform.sac.purchase.order.vo.VerifyOrderInfo;

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
	private PurchaseOrderService orderService;
	@Autowired
	private PurchaseOrderPaymentPageService orderPaymentPageService;
	@Autowired
	private PurchaseOrderPostService orderPostService;
	@Autowired
	private PurchaseOrderMakeDataService purchaseOrderMakeDataService;
	@Autowired
	private PurchaseOrderValidationService validationService;
	@Autowired
	private PurchaseOrderPolicyService policyService;

	@Autowired
	private MessageSourceAccessor messageSourceAccessor;

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
	@RequestMapping(value = "/create/v1", method = RequestMethod.POST)
	@ResponseBody
	public CreatePurchaseSacRes createPurchase(
			@RequestBody @Validated(GroupCreatePurchase.class) CreatePurchaseSacReq req,
			SacRequestHeader sacRequestHeader) {
		this.logger.info("PRCHS,ORDER,SAC,CREATE,REQ,{},{}",
				ReflectionToStringBuilder.toString(req, ToStringStyle.SHORT_PREFIX_STYLE), sacRequestHeader);

		// T Log SET
		this.loggingPurchaseReq(req, sacRequestHeader);

		// 요청 값 검증
		this.validationService.validatePurchaseRequestParameter(req);

		// 구매진행 정보 세팅
		PurchaseOrderInfo purchaseOrderInfo = this.readyPurchaseOrderInfo(req, sacRequestHeader.getTenantHeader());

		// 구매전처리: 회원/상품/구매 정보 세팅 및 적합성 체크, 구매 가능여부 체크, 제한정책 체크
		this.preCheckBeforeProcessOrder(purchaseOrderInfo);

		// 진행 처리: 무료구매완료 처리 || 결제Page 요청 준비작업
		if (purchaseOrderInfo.getRealTotAmt() > 0) {
			// 구매(결제)차단 여부 체크
			if (this.policyService.isBlockPayment(purchaseOrderInfo.getTenantId(), purchaseOrderInfo.getPurchaseUser()
					.getDeviceId(), purchaseOrderInfo.getTenantProdGrpCd())) {
				throw new StorePlatformException("SAC_PUR_6103");
			}

			this.orderService.reservePurchase(purchaseOrderInfo); // 구매예약
			this.orderPaymentPageService.buildPaymentPageUrlParam(purchaseOrderInfo); // 결제Page 정보 세팅
			purchaseOrderInfo.setResultType(PurchaseConstants.CREATE_PURCHASE_RESULT_PAYMENT);

		} else {
			this.orderService.freePurchase(purchaseOrderInfo); // 구매생성 (무료)
			purchaseOrderInfo.setResultType(PurchaseConstants.CREATE_PURCHASE_RESULT_FREE);
		}

		// 응답 세팅
		CreatePurchaseSacRes res = new CreatePurchaseSacRes();
		res.setResultType(purchaseOrderInfo.getResultType());
		res.setPrchsId(purchaseOrderInfo.getPrchsId());

		if (StringUtils.equals(purchaseOrderInfo.getResultType(), PurchaseConstants.CREATE_PURCHASE_RESULT_PAYMENT)) {
			res.setPaymentPageUrl(purchaseOrderInfo.getPaymentPageUrl());
			res.setPaymentPageParam(purchaseOrderInfo.getPaymentPageUrlParam());

		} else if (purchaseOrderInfo.isClink()) { // CLINK 예외 처리용
			res.setPaymentPageParam(this.makeClinkResProductResult(purchaseOrderInfo.getPurchaseProductList()));
		}

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

		// T Log SET
		this.loggingPurchaseReq(req, sacRequestHeader);

		// 비과금 구매요청 권한 체크
		this.validationService.validateFreeChargeAuth(req.getPrchsReqPathCd());

		// 구매진행 정보 세팅
		req.setTotAmt(0.0);
		PurchaseOrderInfo purchaseOrderInfo = this.readyPurchaseOrderInfo(req, sacRequestHeader.getTenantHeader());
		purchaseOrderInfo.setFreeChargeReq(true); // 비과금 요청

		// 구매전처리: 회원/상품/구매 정보 세팅 및 적합성 체크, 구매 가능여부 체크, 제한정책 체크
		this.preCheckBeforeProcessOrder(purchaseOrderInfo);

		// 비과금 구매완료 처리
		this.orderService.freePurchase(purchaseOrderInfo);

		// 응답 세팅
		CreateFreePurchaseSacRes res = new CreateFreePurchaseSacRes();
		res.setResultType(PurchaseConstants.CREATE_PURCHASE_RESULT_FREE);
		res.setPrchsId(purchaseOrderInfo.getPrchsId());

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

		// T Log
		// this.loggingPurchaseReq(req, sacRequestHeader);

		// Biz 구매요청 권한 체크
		this.validationService.validateBizAuth(req.getPrchsReqPathCd());

		// 구매진행 정보 세팅
		req.setTotAmt(0.0);
		PurchaseOrderInfo purchaseOrderInfo = this.readyPurchaseOrderInfo(req, sacRequestHeader.getTenantHeader());
		purchaseOrderInfo.setFreeChargeReq(true); // 비과금 요청

		// 구매전처리: 회원/상품/구매 정보 세팅 및 적합성 체크, 구매 가능여부 체크, 제한정책 체크
		this.preCheckBeforeProcessOrder(purchaseOrderInfo);

		// 비과금 구매완료 처리
		int count = this.orderService.freePurchase(purchaseOrderInfo);

		// 응답 세팅
		CreateBizPurchaseSacRes res = new CreateBizPurchaseSacRes();
		res.setPrchsId(purchaseOrderInfo.getPrchsId());
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

		// IAP 구매/결제 통합 구매이력 생성 요청 권한 체크
		// this.validationService.validateCompleteAuth(req.getPrchsReqPathCd());

		// IAP 구매/결제 통합 구매이력 생성 처리
		String prchsId = this.orderService.completeIapPurchase(req);

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
	 * @param verifyOrderReq
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

		if (StringUtils.isNotBlank(req.getMctSpareParam())) { // P/P -> E/C 통해서 들어온 경우, 가맹점 파라미터 사용
			tenantId = req.getMctSpareParam();
		} else {
			if (tenantHeader != null) {
				tenantId = tenantHeader.getTenantId();
			}
		}

		if (tenantHeader != null) {
			systemId = tenantHeader.getSystemId();
		}

		VerifyOrderInfo verifyOrderInfo = new VerifyOrderInfo();
		verifyOrderInfo.setTenantId(tenantId);
		verifyOrderInfo.setSystemId(systemId);
		verifyOrderInfo.setPrchsId(req.getPrchsId());

		VerifyOrderSacRes res = this.orderService.verifyPurchaseOrder(verifyOrderInfo);
		this.logger.info("PRCHS,ORDER,SAC,VERIFY,RES,{},{}", req.getPrchsId(),
				ReflectionToStringBuilder.toString(res, ToStringStyle.SHORT_PREFIX_STYLE));
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

		// TAKTODO:: 결제실패 경우 처리 - 구매실패(결제실패) 이력 관리 할건가?
		if (Integer.parseInt(notifyPaymentReq.getResultCd()) != 0) {
			return new NotifyPaymentSacRes(notifyPaymentReq.getPrchsId(), notifyPaymentReq.getPaymentInfoList().size());
		}

		// String tenantId = null;
		// if (StringUtils.isNotBlank(notifyPaymentReq.getMctSpareParam())) { // P/P -> E/C 통해서 들어온 경우, 가맹점 파라미터 사용
		// tenantId = notifyPaymentReq.getMctSpareParam();
		// } else {
		// TenantHeader tenantHeader = sacRequestHeader.getTenantHeader();
		// if (tenantHeader != null) {
		// tenantId = tenantHeader.getTenantId();
		// }
		// }

		String tenantId = sacRequestHeader.getTenantHeader().getTenantId();

		// 구매완료 TLog 로그ID 초기세팅
		// final String prchsId = notifyPaymentReq.getPrchsId();
		// new TLogUtil().set(new ShuttleSetter() {
		// @Override
		// public void customize(TLogSentinelShuttle shuttle) {
		// shuttle.log_id(PurchaseConstants.TLOG_ID_PURCHASE_ORDER_RESULT).purchase_id(prchsId);
		// }
		// });

		// ------------------------------------------------------------------------------
		// 쇼핑상품 쿠폰 발급요청
		// 구매 확정: 구매상세 내역 상태변경 & 구매 내역 저장 & (선물 경우)발송 상세 내역 저장, 결제내역 저장

		List<PrchsDtlMore> prchsDtlMoreList = this.orderService.confirmPurchase(notifyPaymentReq, tenantId);

		// ------------------------------------------------------------------------------
		// 구매 후 처리 - 씨네21/인터파크, 구매건수 증가 등등

		boolean bPayPlanet = false; // PayPlanet 결제 여부
		if (StringUtils.startsWith(notifyPaymentReq.getPaymentInfoList().get(0).getTid(),
				PurchaseConstants.PAYPLANET_TID_PREFIX)) {
			bPayPlanet = true;
		}
		this.orderPostService.postPurchase(prchsDtlMoreList, bPayPlanet);

		// ------------------------------------------------------------------------------
		// 응답

		NotifyPaymentSacRes res = new NotifyPaymentSacRes(notifyPaymentReq.getPrchsId(), notifyPaymentReq
				.getPaymentInfoList().size());

		// 구매완료Noti정보 세팅: IAP 은 skip
		PrchsDtlMore prchsDtlMore = prchsDtlMoreList.get(0);
		if (StringUtils.startsWith(prchsDtlMore.getTenantProdGrpCd(), PurchaseConstants.TENANT_PRODUCT_GROUP_IAP) == false) {
			Map<String, String> reservedDataMap = this.purchaseOrderMakeDataService.parseReservedData(prchsDtlMore
					.getPrchsResvDesc());

			res.setPrchsDt(prchsDtlMore.getPrchsDt());
			res.setUserKey(prchsDtlMore.getUseInsdUsermbrNo());
			res.setDeviceKey(prchsDtlMore.getUseInsdDeviceId());
			res.setType(PurchaseConstants.TSTORE_NOTI_TYPE_NORMALPAY);
			res.setPublishType(reservedDataMap.get("tstoreNotiPublishType"));
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
			if (createPurchaseSacReq.getReceiverList() == null) {
				purchaseOrderInfo.setRecvTenantId(tenantHeader.getTenantId()); // 선물수신 테넌트 ID
				purchaseOrderInfo.setRecvUserKey(createPurchaseSacReq.getRecvUserKey()); // 선물수신 내부 회원 번호
				purchaseOrderInfo.setRecvDeviceKey(createPurchaseSacReq.getRecvDeviceKey()); // 선물수신 내부 디바이스 ID
			} else {
				List<PurchaseUserDevice> receiveUserList = new ArrayList<PurchaseUserDevice>();
				PurchaseUserDevice purchaseUserDevice = null;
				for (PurchaseUserInfo receiver : createPurchaseSacReq.getReceiverList()) {
					purchaseUserDevice = new PurchaseUserDevice();
					purchaseUserDevice.setUserKey(receiver.getUserKey());
					purchaseUserDevice.setDeviceKey(receiver.getDeviceKey());
					purchaseUserDevice.setDeviceId(receiver.getDeviceId());
					receiveUserList.add(purchaseUserDevice);
				}
				purchaseOrderInfo.setReceiveUserList(receiveUserList);
			}
		}
		purchaseOrderInfo.setImei(createPurchaseSacReq.getImei()); // 단말 식별 번호
		purchaseOrderInfo.setUacd(createPurchaseSacReq.getUacd()); // 단말 모델 식별 번호
		purchaseOrderInfo.setSimNo(createPurchaseSacReq.getSimNo()); // SIM Serial Number
		purchaseOrderInfo.setSimYn(createPurchaseSacReq.getSimYn()); // SIM 조회 가능 여부

		purchaseOrderInfo.setTotAmt(createPurchaseSacReq.getTotAmt()); // 총 결제 금액
		purchaseOrderInfo.setRealTotAmt(createPurchaseSacReq.getTotAmt()); // 최종 결제 총 금액

		// 상품 속성

		String tenantProdGrpCd = purchaseOrderInfo.getTenantProdGrpCd();

		if (StringUtils.endsWith(tenantProdGrpCd, PurchaseConstants.TENANT_PRODUCT_GROUP_SUFFIX_FIXRATE)
				|| StringUtils.startsWith(tenantProdGrpCd, PurchaseConstants.TENANT_PRODUCT_GROUP_DTL_GAMECASH_FIXRATE)) {
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
			purchaseOrderInfo.setPossibleDuplication(true); // 중복 구매 가능 여부 : IAP은 기구매체크 제외 2014.06.18.
			// possibleDuplication: 중복 구매 가능 여부 - 상품 정합성 체크 (상품 조회) 시 세팅
			// iapCommercial: IAP 정식판 전환상품 존재 여부 - 상품 정합성 체크 (상품 조회) 시 세팅

		} else if (StringUtils.startsWith(tenantProdGrpCd, PurchaseConstants.TENANT_PRODUCT_GROUP_RINGBELL)) {
			purchaseOrderInfo.setRingbell(true); // 컬러링&벨소리 상품 여부

		} else if (StringUtils.startsWith(tenantProdGrpCd, PurchaseConstants.TENANT_PRODUCT_GROUP_DTL_GAMECASH_FIXRATE)) {
			purchaseOrderInfo.setGamecash(true); // 게임캐쉬 상품 여부
		}

		// CLINK 예외 처리용
		if (StringUtils.equals(createPurchaseSacReq.getPrchsReqPathCd(), PurchaseConstants.PRCHS_REQ_PATH_CLINK)) {
			purchaseOrderInfo.setClink(true);
		}

		return purchaseOrderInfo;
	}

	/*
	 * <pre> 구매 전처리 - 구매 정합성 체크. </pre>
	 * 
	 * @param purchaseOrderInfo 구매진행 정보
	 */
	private void preCheckBeforeProcessOrder(PurchaseOrderInfo purchaseOrderInfo) {

		ErrorInfo errorInfo = null;

		try {
			// 회원 적합성 체크
			this.validationService.validateMember(purchaseOrderInfo);

			// 상품 적합성 체크
			this.validationService.validateProduct(purchaseOrderInfo);

			// 회원정책 체크 : TestMDN
			this.policyService.checkUserPolicy(purchaseOrderInfo);

			// 구매 적합성(&가능여부) 체크
			this.validationService.validatePurchase(purchaseOrderInfo);

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

	/**
	 * 
	 * <pre>
	 * CLINK 상품 별 구매 결과 응답 값 생성.
	 * </pre>
	 * 
	 * @param productList
	 *            구매요청 상품 목록
	 * @return 상품 별 구매 결과
	 */
	private String makeClinkResProductResult(List<PurchaseProduct> productList) {
		StringBuffer sb = new StringBuffer();

		for (PurchaseProduct product : productList) {
			sb.append(product.getProdId()).append(":")
					.append(StringUtils.defaultIfBlank(product.getResultCd(), "0000")).append(",");
		}

		if (sb.length() > 0) {
			sb.setLength(sb.length() - 1);
		}

		return sb.toString();
	}
}
