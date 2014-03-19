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

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.purchase.client.order.vo.CreatePurchaseSc;
import com.skplanet.storeplatform.sac.client.purchase.vo.order.CreateFreePurchaseSacRes;
import com.skplanet.storeplatform.sac.client.purchase.vo.order.CreatePurchaseSacReq;
import com.skplanet.storeplatform.sac.client.purchase.vo.order.CreatePurchaseSacReq.GroupCreateFreePurchase;
import com.skplanet.storeplatform.sac.client.purchase.vo.order.CreatePurchaseSacReq.GroupCreatePurchase;
import com.skplanet.storeplatform.sac.client.purchase.vo.order.CreatePurchaseSacRes;
import com.skplanet.storeplatform.sac.client.purchase.vo.order.NotifyPaymentSacReq;
import com.skplanet.storeplatform.sac.client.purchase.vo.order.NotifyPaymentSacRes;
import com.skplanet.storeplatform.sac.client.purchase.vo.order.VerifyOrderSacReq;
import com.skplanet.storeplatform.sac.client.purchase.vo.order.VerifyOrderSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.purchase.constant.PurchaseConstants;
import com.skplanet.storeplatform.sac.purchase.order.service.PurchaseOrderPolicyService;
import com.skplanet.storeplatform.sac.purchase.order.service.PurchaseOrderService;
import com.skplanet.storeplatform.sac.purchase.order.service.PurchaseOrderValidationService;
import com.skplanet.storeplatform.sac.purchase.order.vo.PaymentPageParam;
import com.skplanet.storeplatform.sac.purchase.order.vo.PurchaseOrderInfo;
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
	private PurchaseOrderValidationService validationService;
	@Autowired
	private PurchaseOrderPolicyService policyService;

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
		this.logger.info("PRCHS,ORDER,SAC,CREATE,REQ,{},{}", sacRequestHeader, req);

		// ------------------------------------------------------------------------------
		// 요청 값 검증

		this.validationService.validatePurchaseRequestParameter(req);

		// ------------------------------------------------------------------------------
		// 구매진행 정보 세팅

		PurchaseOrderInfo purchaseOrderInfo = this.readyPurchaseOrderInfo(req, sacRequestHeader.getTenantHeader());

		// ------------------------------------------------------------------------------
		// 구매전처리: 회원/상품/구매 정보 세팅 및 적합성 체크, 구매 가능여부 체크, 제한정책 체크

		this.preCheckBeforeProcessOrder(purchaseOrderInfo);

		// ------------------------------------------------------------------------------
		// 진행 처리: 무료구매완료 처리 || 결제Page 요청 준비작업

		if (purchaseOrderInfo.getRealTotAmt() > 0) {
			// 구매예약
			this.orderService.createReservedPurchase(purchaseOrderInfo);

			// 결제Page 정보 세팅
			this.orderService.setPaymentPageInfo(purchaseOrderInfo);

			purchaseOrderInfo.setResultType("payment");

		} else {
			// 구매생성 (무료)
			this.orderService.createFreePurchase(purchaseOrderInfo);

			purchaseOrderInfo.setResultType("free");
		}

		// ------------------------------------------------------------------------------
		// 응답 세팅

		CreatePurchaseSacRes res = new CreatePurchaseSacRes();
		res.setResultType(purchaseOrderInfo.getResultType());
		res.setPrchsId(purchaseOrderInfo.getPrchsId());

		if (StringUtils.equals(purchaseOrderInfo.getResultType(), "payment")) {
			PaymentPageParam paymentPageParam = purchaseOrderInfo.getPaymentPageParam();

			StringBuffer sbParam = new StringBuffer(paymentPageParam.geteData().length()
					+ paymentPageParam.getToken().length() + 25);
			sbParam.append("version=").append(paymentPageParam.getVersion()).append("&token=")
					.append(paymentPageParam.getToken()).append("&eData=").append(paymentPageParam.geteData());

			res.setPaymentPageParam(sbParam.toString());
			res.setPaymentPageUrl(purchaseOrderInfo.getPaymentPageUrl());
		}

		this.logger.info("PRCHS,ORDER,SAC,CREATE,RES,{}", res);
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
		this.logger.info("PRCHS,ORDER,SAC,CREATEFREE,REQ,{},{}", sacRequestHeader, req);

		// ------------------------------------------------------------------------------
		// 비과금 구매요청 권한 체크

		this.validationService.validateFreeChargeAuth(req.getPrchsReqPathCd());

		// ------------------------------------------------------------------------------
		// 구매진행 정보 세팅

		req.setTotAmt(0.0);
		PurchaseOrderInfo purchaseOrderInfo = this.readyPurchaseOrderInfo(req, sacRequestHeader.getTenantHeader());
		purchaseOrderInfo.setFreeChargeReq(true); // 비과금 요청

		// ------------------------------------------------------------------------------
		// 구매전처리: 회원/상품/구매 정보 세팅 및 적합성 체크, 구매 가능여부 체크, 제한정책 체크

		this.preCheckBeforeProcessOrder(purchaseOrderInfo);

		// ------------------------------------------------------------------------------
		// 비과금 구매완료 처리

		this.orderService.createFreePurchase(purchaseOrderInfo);

		purchaseOrderInfo.setResultType("free");

		// ------------------------------------------------------------------------------
		// 응답 세팅

		CreateFreePurchaseSacRes res = new CreateFreePurchaseSacRes();
		res.setResultType(purchaseOrderInfo.getResultType());
		res.setPrchsId(purchaseOrderInfo.getPrchsId());

		this.logger.info("PRCHS,ORDER,SAC,CREATEFREE,RES,{}", res);
		return res;
	}

	// /**
	// *
	// * <pre>
	// * Biz 쿠폰 발행 요청 : 대량의 선물 이력 생성 & biz쿠폰 발급 요청.
	// * </pre>
	// *
	// * @param req
	// * 구매요청 정보
	// * @return 구매요청 처리 결과
	// */
	// @RequestMapping(value = "/createBiz/v1", method = RequestMethod.POST)
	// @ResponseBody
	// public CreateBizPurchaseSacRes createBizPurchase(
	// @RequestBody @Validated(GroupCreateBizPurchase.class) CreatePurchaseSacReq req,
	// SacRequestHeader sacRequestHeader) {
	// this.logger.info("PRCHS,ORDER,SAC,CREATEBIZ,REQ,{},{}", sacRequestHeader, req);
	//
	// // ------------------------------------------------------------------------------
	// // TAKTODOD:: Biz 구매요청 권한 체크
	//
	// // TAKTODOD:: this.validationService.validateFreeChargeAuth(req.getPrchsReqPathCd());
	//
	// // ------------------------------------------------------------------------------
	// // TAKTODOD:: 구매진행 정보 세팅
	//
	// // req.setTotAmt(0.0);
	// // PurchaseOrderInfo purchaseOrderInfo = this.readyPurchaseOrderInfo(req, sacRequestHeader.getTenantHeader());
	// // purchaseOrderInfo.setFreeChargeReq(true); // 비과금 요청
	//
	// // ------------------------------------------------------------------------------
	// // TAKTODOD:: 구매전처리: 회원/상품/구매 정보 세팅 및 적합성 체크, 구매 가능여부 체크, 제한정책 체크
	//
	// // this.preCheckBeforeProcessOrder(purchaseOrderInfo);
	//
	// // ------------------------------------------------------------------------------
	// // TAKTODOD:: 비과금 구매완료 처리
	//
	// // this.orderService.createFreePurchase(purchaseOrderInfo);
	// // purchaseOrderInfo.setResultType("free");
	//
	// // ------------------------------------------------------------------------------
	// // 응답 세팅
	//
	// CreateBizPurchaseSacRes res = new CreateBizPurchaseSacRes();
	// // res.setPrchsId(purchaseOrderInfo.getPrchsId());
	// res.setPrchsId("0");
	// res.setCount(0);
	//
	// this.logger.info("PRCHS,ORDER,SAC,CREATEBIZ,RES,{}", res);
	// return res;
	// }

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
		this.logger.info("PRCHS,ORDER,SAC,VERIFY,REQ,{},{}", req, sacRequestHeader);

		String tenantId = null;
		TenantHeader tenantHeader = sacRequestHeader.getTenantHeader();
		if (tenantHeader != null) {
			tenantId = tenantHeader.getTenantId();
		} else { // P/P -> E/C 통해서 들어온 경우, 가맹점 파라미터 사용
			tenantId = req.getMctSpareParam();
		}

		VerifyOrderInfo verifyOrderInfo = new VerifyOrderInfo();
		verifyOrderInfo.setTenantId(tenantId);
		verifyOrderInfo.setPrchsId(req.getPrchsId());

		VerifyOrderSacRes res = this.orderService.verifyPurchaseOrder(verifyOrderInfo);
		this.logger.info("PRCHS,ORDER,SAC,VERIFY,RES,{},{}", res);
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
		this.logger.info("PRCHS,ORDER,SAC,NOTIFYPAY,REQ,{}", notifyPaymentReq);

		// TAKTODO:: 결제실패 경우 처리 - 구매실패(결제실패) 이력 관리 할건가?
		if (Integer.parseInt(notifyPaymentReq.getResultCd()) != 0) {
			return new NotifyPaymentSacRes(notifyPaymentReq.getPrchsId(), notifyPaymentReq.getPaymentInfoList().size());
		}

		String tenantId = null;
		TenantHeader tenantHeader = sacRequestHeader.getTenantHeader();
		if (tenantHeader != null) {
			tenantId = tenantHeader.getTenantId();
		} else { // P/P -> E/C 통해서 들어온 경우, 가맹점 파라미터 사용
			tenantId = notifyPaymentReq.getMctSpareParam();
		}

		// ------------------------------------------------------------------------------
		// 쇼핑상품 쿠폰 발급요청
		// 구매 확정: 구매상세 내역 상태변경 & 구매 내역 저장 & (선물 경우)발송 상세 내역 저장, 결제내역 저장

		List<CreatePurchaseSc> createPurchaseScList = this.orderService.executeConfirmPurchase(notifyPaymentReq,
				tenantId);

		// ------------------------------------------------------------------------------
		// 구매 후 처리 - 씨네21/인터파크, 구매건수 증가 등등

		this.orderService.postPurchase(createPurchaseScList);

		// ------------------------------------------------------------------------------
		// 응답

		NotifyPaymentSacRes res = new NotifyPaymentSacRes(notifyPaymentReq.getPrchsId(), notifyPaymentReq
				.getPaymentInfoList().size());
		this.logger.info("PRCHS,ORDER,SAC,NOTIFYPAY,RES,{}", res);
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
		purchaseOrderInfo.setMid(createPurchaseSacReq.getMid()); // 가맹점 ID
		purchaseOrderInfo.setAuthKey(createPurchaseSacReq.getAuthKey()); // 가맹점 인증키
		purchaseOrderInfo.setReturnUrl(createPurchaseSacReq.getReturnUrl()); // 결과처리 URL
		purchaseOrderInfo.setCurrencyCd(createPurchaseSacReq.getCurrencyCd()); // 통화 코드
		purchaseOrderInfo.setClientIp(createPurchaseSacReq.getClientIp()); // 클라이언트 IP
		purchaseOrderInfo.setNetworkTypeCd(createPurchaseSacReq.getNetworkTypeCd()); // 네트워크 타입 코드
		purchaseOrderInfo.setPrchsCaseCd(createPurchaseSacReq.getPrchsCaseCd()); // 구매 유형 코드
		purchaseOrderInfo.setTenantProdGrpCd(createPurchaseSacReq.getTenantProdGrpCd()); // 테넌트 상품 분류 코드
		if (StringUtils.equals(createPurchaseSacReq.getPrchsCaseCd(), PurchaseConstants.PRCHS_CASE_GIFT_CD)) {
			purchaseOrderInfo.setRecvTenantId(tenantHeader.getTenantId()); // 선물수신 테넌트 ID
			purchaseOrderInfo.setRecvUserKey(createPurchaseSacReq.getRecvUserKey()); // 선물수신 내부 회원 번호
			purchaseOrderInfo.setRecvDeviceKey(createPurchaseSacReq.getRecvDeviceKey()); // 선물수신 내부 디바이스 ID
		}
		purchaseOrderInfo.setImei(createPurchaseSacReq.getImei()); // 단말 식별 번호
		purchaseOrderInfo.setUacd(createPurchaseSacReq.getUacd()); // 단말 모델 식별 번호
		purchaseOrderInfo.setSimNo(createPurchaseSacReq.getSimNo()); // SIM Serial Number
		purchaseOrderInfo.setSimYn(createPurchaseSacReq.getSimYn()); // SIM 조회 가능 여부

		purchaseOrderInfo.setTotAmt(createPurchaseSacReq.getTotAmt()); // 총 결제 금액
		purchaseOrderInfo.setRealTotAmt(createPurchaseSacReq.getTotAmt()); // 최종 결제 총 금액

		return purchaseOrderInfo;
	}

	/*
	 * <pre> 구매 전처리 - 구매 정합성 체크. </pre>
	 * 
	 * @param purchaseOrderInfo 구매진행 정보
	 */
	private void preCheckBeforeProcessOrder(PurchaseOrderInfo purchaseOrderInfo) {

		// 회원 적합성 체크
		this.validationService.validateMember(purchaseOrderInfo);

		// 상품 적합성 체크
		this.validationService.validateProduct(purchaseOrderInfo);

		// 회원정책 체크 : TestMDN / 구매차단
		this.policyService.checkUserPolicy(purchaseOrderInfo);

		// 구매 적합성(&가능여부) 체크
		this.validationService.validatePurchase(purchaseOrderInfo);
	}

}
