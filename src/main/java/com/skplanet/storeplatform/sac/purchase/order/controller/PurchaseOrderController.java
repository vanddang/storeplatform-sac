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

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.purchase.client.common.vo.PrchsDtl;
import com.skplanet.storeplatform.sac.client.purchase.vo.order.CreatePurchaseSacReq;
import com.skplanet.storeplatform.sac.client.purchase.vo.order.CreatePurchaseSacRes;
import com.skplanet.storeplatform.sac.client.purchase.vo.order.NotifyPaymentSacReq;
import com.skplanet.storeplatform.sac.client.purchase.vo.order.NotifyPaymentSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.purchase.constant.PurchaseConstants;
import com.skplanet.storeplatform.sac.purchase.order.service.PurchaseOrderPolicyService;
import com.skplanet.storeplatform.sac.purchase.order.service.PurchaseOrderService;
import com.skplanet.storeplatform.sac.purchase.order.service.PurchaseOrderValidationService;
import com.skplanet.storeplatform.sac.purchase.order.vo.PurchaseOrderInfo;

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
	 * 구매 요청 처리: 무료구매 경우는 구매완료 처리, 유료구매 경우는 PayPlanet 결제Page 요청을 위한 처리
	 * 유료상품 무료결제 구매 요청 처리: 일부 외부판매 사이트에 필요하며, 요청한 사이트에 대해 해당 요청 권한이 있는 지 체크하는 부분이 추가된다.
	 * </pre>
	 * 
	 * @param req
	 *            구매요청 정보
	 * @return 구매요청 처리 결과: 무료구매완료 또는 결제page요청정보
	 */
	@RequestMapping(value = { "/create/v1", "/createFreeCharge/v1" }, method = RequestMethod.POST)
	@ResponseBody
	public CreatePurchaseSacRes createPurchase(@RequestBody @Validated CreatePurchaseSacReq req,
			BindingResult bindingResult, SacRequestHeader sacRequestHeader) {
		TenantHeader tenantHeader = sacRequestHeader.getTenantHeader();
		this.logger.debug("PRCHS,INFO,CREATE,REQ,{},{}", tenantHeader, req);

		if (bindingResult.hasErrors()) {
			this.logger.debug("PRCHS,INFO,CREATE,ERROR,BINDING,{},{},{},{}", bindingResult.getFieldError().getCode(),
					bindingResult.getFieldError().getDefaultMessage(), bindingResult.getFieldError().getField());
			throw new StorePlatformException("SAC_PUR_0001", bindingResult.getFieldError().getField());
		}

		// ------------------------------------------------------------------------------
		// 구매진행 정보 세팅

		PurchaseOrderInfo purchaseOrderInfo = this.setPurchaseOrderInfo(req, tenantHeader);

		// ------------------------------------------------------------------------------
		// 구매전처리: 회원 적합성 / 상품 적합성 / 구매 적합성 및 가능여부 체크 && 제한정책 체크

		this.prePurchaseOrder(purchaseOrderInfo);

		// ------------------------------------------------------------------------------
		// 진행 처리: 무료구매완료 처리 | 결제Page 요청 준비작업

		if (purchaseOrderInfo.getRealTotAmt() > 0) {
			purchaseOrderInfo.setResultType("payment");

			// 구매예약
			this.orderService.createReservedPurchase(purchaseOrderInfo);

			// 결제Page 정보 세팅
			this.orderService.setPaymentPageInfo(purchaseOrderInfo);

		} else {
			purchaseOrderInfo.setResultType("free");
			// 구매생성 (무료)
			this.orderService.createFreePurchase(purchaseOrderInfo);
		}

		// ------------------------------------------------------------------------------
		// 응답 세팅

		CreatePurchaseSacRes res = new CreatePurchaseSacRes();
		res.setResultType(purchaseOrderInfo.getResultType());
		res.setPrchsId(purchaseOrderInfo.getPrchsId());
		res.setPaymentPageUrl(purchaseOrderInfo.getPaymentPageUrl());
		res.setPaymentPageParam(purchaseOrderInfo.getPaymentPageParam());

		this.logger.debug("PRCHS,INFO,CREATE,RES,{}", res);
		return res;
	}

	/**
	 * 
	 * <pre>
	 * Pay Planet 측으로부터 결제 진행 결과 응답 받음.
	 * </pre>
	 * 
	 * @param notifyPaymentReq
	 *            결제 결과 정보
	 * @return 결제 결과 처리 응답
	 */
	@RequestMapping(value = "/notifyPayment/v1", method = RequestMethod.POST)
	@ResponseBody
	public NotifyPaymentSacRes notifyPayment(@RequestBody NotifyPaymentSacReq notifyPaymentReq) {
		this.logger.debug("PRCHS,INFO,NOTI_PAY,REQ,{}", notifyPaymentReq);

		if ("0".equals(notifyPaymentReq.getCode()) == false) {
			return new NotifyPaymentSacRes("0", "SUCCESS");
		}

		// 가맹점용 파라미터 추출
		String mctSpareParam = notifyPaymentReq.getMctSpareParam();
		String[] arSpareParam = null;
		String[] arParamKeyValue = null;
		Map<String, String> spareParamMap = new HashMap<String, String>();

		if (StringUtils.isNotEmpty(mctSpareParam)) {
			arSpareParam = mctSpareParam.split("&");

			for (String param : arSpareParam) {
				arParamKeyValue = param.split(":");
				spareParamMap.put(arParamKeyValue[0], arParamKeyValue[1]);
			}
		}

		String prchsId = notifyPaymentReq.getOrderId();
		String tenantId = spareParamMap.get("tenantId");
		String systemId = spareParamMap.get("systemId");
		String useUserKey = spareParamMap.get("useUserKey");
		String networkTypeCd = spareParamMap.get("networkTypeCd");
		String currencyCd = spareParamMap.get("currencyCd");

		// 구매 예약 건 조회
		PrchsDtl prchsDtl = this.orderService.searchReservedPurchaseDetail(tenantId, prchsId, useUserKey);
		if (prchsDtl == null) {
			return new NotifyPaymentSacRes("1", "FAIL");
		}

		// ------------------------------------------------------------------------------
		// TAKTODO:: 구매 후 처리- 쇼핑상품 쿠폰 발급요청, 씨네21, 인터파크, 이메일 등등
		this.postPurchaseOrder(null);

		// ------------------------------------------------------------------------------
		// 구매 확정 및 결제 내역 저장

		prchsDtl.setResvCol05(systemId); // TAKTODO:: 테이블 및 VO에 system_id 추가 후 사용

		// 구매 확정: 구매상세 내역 상태변경 & 구매 내역 저장 & (선물 경우)발송 상세 내역 저장, 결제내역 저장
		this.orderService.updateConfirmPurchase(prchsDtl, notifyPaymentReq, currencyCd, networkTypeCd);

		// ------------------------------------------------------------------------------
		// 응답

		return new NotifyPaymentSacRes("0", "SUCCESS");
	}

	// 구매요청 파라미터와 헤더 정보로 구매처리 진행을 위한 정보 개체 세팅 (구매요청 VO, 헤더 테넌트 정보 -> 구매처리 진행 정보 VO)
	private PurchaseOrderInfo setPurchaseOrderInfo(CreatePurchaseSacReq createPurchaseSacReq, TenantHeader tenantHeader) {
		PurchaseOrderInfo purchaseOrderInfo = new PurchaseOrderInfo(createPurchaseSacReq);
		purchaseOrderInfo.setTenantId(tenantHeader.getTenantId()); // 구매(선물발신) 테넌트 ID
		purchaseOrderInfo.setSystemId(tenantHeader.getSystemId()); // 구매(선물발신) 시스템 ID
		purchaseOrderInfo.setUserKey(createPurchaseSacReq.getUserKey()); // 구매(선물발신) 내부 회원 번호
		purchaseOrderInfo.setDeviceKey(createPurchaseSacReq.getDeviceKey()); // 구매(선물발신) 내부 디바이스 ID
		purchaseOrderInfo.setPrchsReqPathCd(createPurchaseSacReq.getPrchsReqPathCd()); // 구매 요청 경로 코드
		purchaseOrderInfo.setMid(createPurchaseSacReq.getMid()); // 가맹점 ID
		purchaseOrderInfo.setAuthKey(createPurchaseSacReq.getAuthKey()); // 가맹점 인증키
		purchaseOrderInfo.setResultUrl(createPurchaseSacReq.getResultUrl()); // 결과처리 URL
		purchaseOrderInfo.setCurrencyCd(createPurchaseSacReq.getCurrencyCd()); // 통화 코드
		purchaseOrderInfo.setTotAmt(createPurchaseSacReq.getTotAmt()); // 총 결제 금액
		purchaseOrderInfo.setClientIp(createPurchaseSacReq.getClientIp()); // 클라이언트 IP
		purchaseOrderInfo.setNetworkTypeCd(createPurchaseSacReq.getNetworkTypeCd()); // 네트워크 타입 코드
		purchaseOrderInfo.setPrchsCaseCd(createPurchaseSacReq.getPrchsCaseCd()); // 구매 유형 코드
		if (PurchaseConstants.PRCHS_CASE_GIFT_CD.equals(createPurchaseSacReq.getPrchsCaseCd())) {
			purchaseOrderInfo.setRecvTenantId(tenantHeader.getTenantId()); // 선물수신 테넌트 ID
			purchaseOrderInfo.setRecvUserKey(createPurchaseSacReq.getRecvUserKey()); // 선물수신 내부 회원 번호
			purchaseOrderInfo.setRecvDeviceKey(createPurchaseSacReq.getRecvDeviceKey()); // 선물수신 내부 디바이스 ID
		}

		purchaseOrderInfo.setRealTotAmt(createPurchaseSacReq.getTotAmt()); // 최종 결제 총 금액

		return purchaseOrderInfo;
	}

	// 구매 전처리 (구매진행 정보)
	private void prePurchaseOrder(PurchaseOrderInfo purchaseOrderInfo) {
		// ------------------------------------------------------------------------------
		// 적합성 체크

		// 회원
		this.validationService.validateMember(purchaseOrderInfo);

		// 상품
		this.validationService.validateProduct(purchaseOrderInfo);

		// 구매
		this.validationService.validatePurchase(purchaseOrderInfo);

		// ------------------------------------------------------------------------------
		// 제한정책 체크

		this.policyService.checkTenantPolicy(purchaseOrderInfo);

	}

	// 구매 후처리 단계 (구매진행 정보)
	private void postPurchaseOrder(PurchaseOrderInfo purchaseOrderInfo) {

	}
}
