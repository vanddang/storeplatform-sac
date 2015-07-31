/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.cancel.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.sac.client.purchase.cancel.vo.PurchaseCancelByAdminSacReq;
import com.skplanet.storeplatform.sac.client.purchase.cancel.vo.PurchaseCancelByAdminSacRes;
import com.skplanet.storeplatform.sac.client.purchase.cancel.vo.PurchaseCancelByUserSacReq;
import com.skplanet.storeplatform.sac.client.purchase.cancel.vo.PurchaseCancelByUserSacRes;
import com.skplanet.storeplatform.sac.client.purchase.cancel.vo.PurchaseCancelDetailSacReq;
import com.skplanet.storeplatform.sac.client.purchase.cancel.vo.PurchaseCancelDetailSacRes;
import com.skplanet.storeplatform.sac.client.purchase.cancel.vo.PurchaseCancelForInAppSacReq;
import com.skplanet.storeplatform.sac.client.purchase.cancel.vo.PurchaseCancelForInAppSacRes;
import com.skplanet.storeplatform.sac.client.purchase.cancel.vo.PurchaseCancelForPaymentErrorSacReq;
import com.skplanet.storeplatform.sac.client.purchase.cancel.vo.PurchaseCancelForPaymentErrorSacRes;
import com.skplanet.storeplatform.sac.client.purchase.cancel.vo.PurchaseCancelForTCashSacReq;
import com.skplanet.storeplatform.sac.client.purchase.cancel.vo.PurchaseCancelForTCashSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.purchase.cancel.service.PurchaseCancelService;
import com.skplanet.storeplatform.sac.purchase.cancel.vo.PurchaseCancelDetailSacParam;
import com.skplanet.storeplatform.sac.purchase.cancel.vo.PurchaseCancelDetailSacResult;
import com.skplanet.storeplatform.sac.purchase.cancel.vo.PurchaseCancelSacParam;
import com.skplanet.storeplatform.sac.purchase.cancel.vo.PurchaseCancelSacResult;
import com.skplanet.storeplatform.sac.purchase.common.util.ConvertVO;
import com.skplanet.storeplatform.sac.purchase.constant.PurchaseConstants;

/**
 * 구매 취소 Controller.
 * 
 * Updated on : 2014. 1. 16. Updated by : nTels_cswoo81, nTels.
 */
@Controller
@RequestMapping(value = "/purchase/cancel")
public class PurchaseCancelController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private PurchaseCancelService purchaseCancelService;

	/**
	 * 
	 * <pre>
	 * 구매 취소(사용자) 요청.
	 * </pre>
	 * 
	 * @param sacRequestHeader
	 * @param purchaseCancelReq
	 * @return
	 */
	@RequestMapping(value = "/user/v1", method = RequestMethod.POST)
	@ResponseBody
	public PurchaseCancelByUserSacRes cancelPurchaseByUser(SacRequestHeader sacRequestHeader,
			@RequestBody @Validated PurchaseCancelByUserSacReq purchaseCancelByUserSacReq) {

		PurchaseCancelSacParam purchaseCancelSacParam = this.convertReqForCancelPurchaseByUser(sacRequestHeader,
				purchaseCancelByUserSacReq);

		PurchaseCancelSacResult purchaseCancelSacResult = this.purchaseCancelService
				.cancelPurchaseList(purchaseCancelSacParam);

		return this.convertResForCancelPurchaseByUser(purchaseCancelSacResult);

	}

	/**
	 * <pre>
	 * 관리자 권한 구매 취소 요청.
	 * </pre>
	 * 
	 * @param sacRequestHeader
	 * @param purchaseCancelReq
	 * @return
	 */
	@RequestMapping(value = "/admin/v1", method = RequestMethod.POST)
	@ResponseBody
	public PurchaseCancelByAdminSacRes cancelPurchaseByAdmin(SacRequestHeader sacRequestHeader,
			@RequestBody @Validated PurchaseCancelByAdminSacReq purchaseCancelByAdminSacReq) {

		PurchaseCancelSacParam purchaseCancelSacParam = this.convertReqForCancelPurchaseByAdmin(sacRequestHeader,
				purchaseCancelByAdminSacReq);

		PurchaseCancelSacResult purchaseCancelSacResult = this.purchaseCancelService
				.cancelPurchaseList(purchaseCancelSacParam);

		return this.convertResForCancelPurchaseByAdmin(purchaseCancelSacResult);

	}

	/**
	 * 
	 * <pre>
	 * 결제 실패 시 구매 취소.
	 * </pre>
	 * 
	 * @param sacRequestHeader
	 *            sacRequestHeader
	 * @param purchaseCancelForPaymentErrorSacReq
	 *            purchaseCancelForPaymentErrorSacReq
	 * @return PurchaseCancelForPaymentErrorSacRes
	 */
	@RequestMapping(value = "/paymentError/v1", method = RequestMethod.POST)
	@ResponseBody
	public PurchaseCancelForPaymentErrorSacRes cancelPurchaseForPaymentError(SacRequestHeader sacRequestHeader,
			@RequestBody @Validated PurchaseCancelForPaymentErrorSacReq purchaseCancelForPaymentErrorSacReq) {

		PurchaseCancelSacParam purchaseCancelSacParam = this.convertReqForCancelPurchaseForPaymentError(
				sacRequestHeader, purchaseCancelForPaymentErrorSacReq);

		PurchaseCancelDetailSacResult purchaseCancelDetailSacResult = this.purchaseCancelService
				.cancelPurchaseForPaymentError(purchaseCancelSacParam,
						purchaseCancelSacParam.getPrchsCancelList().get(0));

		if (!StringUtils.equals("SAC_PUR_0000", purchaseCancelDetailSacResult.getResultCd())) {
			throw new StorePlatformException("SAC_PUR_8999");
		}

		return this.convertResForCancelPurchaseForPaymentError(purchaseCancelDetailSacResult);

	}

	/**
	 * 
	 * <pre>
	 * (구)InApp에서 구매 취소 요청.
	 * </pre>
	 * 
	 * @param sacRequestHeader
	 *            sacRequestHeader
	 * @param purchaseCancelForInAppSacReq
	 *            purchaseCancelForInAppSacReq
	 * @return PurchaseCancelForInAppSacRes
	 */
	@RequestMapping(value = "/inApp/v1", method = RequestMethod.POST)
	@ResponseBody
	public PurchaseCancelForInAppSacRes cancelPurchaseForInApp(SacRequestHeader sacRequestHeader,
			@RequestBody @Validated PurchaseCancelForInAppSacReq purchaseCancelForInAppSacReq) {

		PurchaseCancelSacParam purchaseCancelSacParam = this.convertReqForCancelPurchaseForInApp(sacRequestHeader,
				purchaseCancelForInAppSacReq);

		PurchaseCancelDetailSacResult purchaseCancelDetailSacResult = this.purchaseCancelService
				.cancelPurchaseForInApp(purchaseCancelSacParam, purchaseCancelSacParam.getPrchsCancelList().get(0));

		if (!StringUtils.equals("SAC_PUR_0000", purchaseCancelDetailSacResult.getResultCd())) {
			throw new StorePlatformException("SAC_PUR_8999");
		}

		return this.convertResForCancelPurchaseForInApp(purchaseCancelDetailSacResult);

	}

	/**
	 * <pre>
	 * T Cash 충전 취소.
	 * </pre>
	 * 
	 * @param sacRequestHeader
	 *            sacRequestHeader
	 * @param purchaseCancelForTCashSacRes
	 *            purchaseCancelForTCashSacRes
	 * @return PurchaseCancelForTCashSacRes
	 */
	@RequestMapping(value = "/tcash/v1", method = RequestMethod.POST)
	@ResponseBody
	public PurchaseCancelForTCashSacRes cancelPurchaseForTcash(SacRequestHeader sacRequestHeader,
			@RequestBody @Validated PurchaseCancelForTCashSacReq purchaseCancelForTCashSacRes) {

		PurchaseCancelSacParam purchaseCancelSacParam = this.convertReqForCancelPurchaseForTcash(sacRequestHeader,
				purchaseCancelForTCashSacRes);

		PurchaseCancelSacResult purchaseCancelSacResult = this.purchaseCancelService
				.cancelPurchaseList(purchaseCancelSacParam);

		return this.convertResForCancelPurchaseForTcash(purchaseCancelSacResult);

	}

	/**
	 * 
	 * <pre>
	 * convertReqForCancelPurchaseByUser
	 * </pre>
	 * 
	 * @param sacRequestHeader
	 *            sacRequestHeader
	 * @param purchaseCancelByUserSacReq
	 *            purchaseCancelByUserSacReq
	 * @return PurchaseCancelSacParam
	 */
	private PurchaseCancelSacParam convertReqForCancelPurchaseByUser(SacRequestHeader sacRequestHeader,
			PurchaseCancelByUserSacReq purchaseCancelByUserSacReq) {

		PurchaseCancelSacParam purchaseCancelSacParam = new PurchaseCancelSacParam();

		// common parameter setting.
		if (!ConvertVO
				.convertPurchaseCommonSacReq(sacRequestHeader, purchaseCancelByUserSacReq, purchaseCancelSacParam)) {
			throw new StorePlatformException("SAC_PUR_9901");
		}

		// 2014.12.08 tenantId 받도록 처리함(Default:HeaderValue)
		if (StringUtils.isNotBlank(purchaseCancelByUserSacReq.getTenantId())) {
			purchaseCancelSacParam.setTenantId(purchaseCancelByUserSacReq.getTenantId());
		}

		purchaseCancelSacParam.setReqUserId(purchaseCancelByUserSacReq.getReqUserId());
		purchaseCancelSacParam.setCancelReqPathCd(purchaseCancelByUserSacReq.getCancelReqPathCd());
		purchaseCancelSacParam.setShoppingForceCancelYn("N");
		purchaseCancelSacParam.setSktLimitUserCancelYn("N");

		if (StringUtils.equals(PurchaseConstants.PRCHS_REQ_PATH_ADMIN_REFUND,
				purchaseCancelSacParam.getCancelReqPathCd())
				|| StringUtils.equals(PurchaseConstants.PRCHS_REQ_PATH_ADMIN_FORCE_CANCEL,
						purchaseCancelSacParam.getCancelReqPathCd())
				|| StringUtils.equals(PurchaseConstants.PRCHS_REQ_PATH_ADMIN_PAYMENT_NOT_SYNC,
						purchaseCancelSacParam.getCancelReqPathCd())
				|| StringUtils.equals(PurchaseConstants.PRCHS_REQ_PATH_PAYMENT_ERROR_CANCEL,
						purchaseCancelSacParam.getCancelReqPathCd())) {
			throw new StorePlatformException("SAC_PUR_8102");
		}
		if (StringUtils.equals(PurchaseConstants.PRCHS_REQ_PATH_S2S,
				purchaseCancelSacParam.getCancelReqPathCd()))
			purchaseCancelSacParam.setIgnorePayment(true);
		else
			purchaseCancelSacParam.setIgnorePayment(false);
		// request user type setting.
		purchaseCancelSacParam.setPrchsCancelByType(PurchaseConstants.PRCHS_CANCEL_BY_USER);

		// parameter setting.
		List<PurchaseCancelDetailSacParam> prchsCancelList = new ArrayList<PurchaseCancelDetailSacParam>();
		for (PurchaseCancelDetailSacReq purchaseCancelDetailSacReq : purchaseCancelByUserSacReq.getPrchsCancelList()) {

			PurchaseCancelDetailSacParam purchaseCancelDetailSacParam = new PurchaseCancelDetailSacParam();
			purchaseCancelDetailSacParam.setPrchsId(purchaseCancelDetailSacReq.getPrchsId());

			prchsCancelList.add(purchaseCancelDetailSacParam);

		}

		// systemId에 요청자 정보가 있으면 셋팅하고 없으면 systemId로 셋팅
		purchaseCancelSacParam
				.setSystemId(!StringUtils.isEmpty(purchaseCancelByUserSacReq.getReqUserId()) ? purchaseCancelByUserSacReq
						.getReqUserId() : purchaseCancelSacParam.getSystemId());

		purchaseCancelSacParam.setPrchsCancelList(prchsCancelList);

		return purchaseCancelSacParam;

	}

	/**
	 * 
	 * <pre>
	 * convertResForCancelPurchaseByUser.
	 * </pre>
	 * 
	 * @param purchaseCancelByUserSacResult
	 *            purchaseCancelByUserSacResult
	 * @return PurchaseCancelByUserSacRes
	 */
	private PurchaseCancelByUserSacRes convertResForCancelPurchaseByUser(PurchaseCancelSacResult purchaseCancelSacResult) {

		PurchaseCancelByUserSacRes purchaseCancelByUserSacRes = new PurchaseCancelByUserSacRes();

		// cancel common response setting.
		if (!ConvertVO.convertPurchaseCancelCommonSacRes(purchaseCancelSacResult, purchaseCancelByUserSacRes)) {
			throw new StorePlatformException("SAC_PUR_9902");
		}

		// response setting.
		purchaseCancelByUserSacRes.setTotCnt(purchaseCancelSacResult.getTotCnt());
		purchaseCancelByUserSacRes.setSuccessCnt(purchaseCancelSacResult.getSuccessCnt());
		purchaseCancelByUserSacRes.setFailCnt(purchaseCancelSacResult.getFailCnt());

		List<PurchaseCancelDetailSacRes> prchsCancelList = new ArrayList<PurchaseCancelDetailSacRes>();
		for (PurchaseCancelDetailSacResult purchaseCancelDetailSacResult : purchaseCancelSacResult.getPrchsCancelList()) {

			PurchaseCancelDetailSacRes purchaseCancelDetailSacRes = new PurchaseCancelDetailSacRes();

			purchaseCancelDetailSacRes.setPrchsId(purchaseCancelDetailSacResult.getPrchsId());
			purchaseCancelDetailSacRes.setResultCd(purchaseCancelDetailSacResult.getResultCd());
			purchaseCancelDetailSacRes.setResultMsg(purchaseCancelDetailSacResult.getResultMsg());

			prchsCancelList.add(purchaseCancelDetailSacRes);

		}

		purchaseCancelByUserSacRes.setPrchsCancelList(prchsCancelList);

		return purchaseCancelByUserSacRes;

	}

	/**
	 * 
	 * <pre>
	 * convertReqForCancelPurchaseByAdmin.
	 * </pre>
	 * 
	 * @param sacRequestHeader
	 *            sacRequestHeader
	 * @param purchaseCancelByAdminSacReq
	 *            purchaseCancelByAdminSacReq
	 * @return PurchaseCancelSacParam
	 */
	private PurchaseCancelSacParam convertReqForCancelPurchaseByAdmin(SacRequestHeader sacRequestHeader,
			PurchaseCancelByAdminSacReq purchaseCancelByAdminSacReq) {

		this.logger.info("######## {} ", purchaseCancelByAdminSacReq);

		PurchaseCancelSacParam purchaseCancelSacParam = new PurchaseCancelSacParam();

		// common parameter setting.
		if (!ConvertVO.convertPurchaseCommonSacReq(sacRequestHeader, purchaseCancelByAdminSacReq,
				purchaseCancelSacParam)) {
			throw new StorePlatformException("SAC_PUR_9901");
		}

		// 2014.12.08 tenantId 받도록 처리함(Default:HeaderValue)
		if (StringUtils.isNotBlank(purchaseCancelByAdminSacReq.getTenantId())) {
			purchaseCancelSacParam.setTenantId(purchaseCancelByAdminSacReq.getTenantId());
		}

		purchaseCancelSacParam.setReqUserId(purchaseCancelByAdminSacReq.getReqUserId());
		purchaseCancelSacParam.setCancelReqPathCd(purchaseCancelByAdminSacReq.getCancelReqPathCd());
		purchaseCancelSacParam.setShoppingForceCancelYn(purchaseCancelByAdminSacReq.getShoppingForceCancelYn());
		// 쇼핑쿠폰 cms 취소 결과값 무시 여부.
		if (StringUtils.equals("F", purchaseCancelByAdminSacReq.getShoppingForceCancelYn())) {
			purchaseCancelSacParam.setIgnoreCouponCms(true);
			purchaseCancelSacParam.setShoppingForceCancelYn("Y");
		}
		purchaseCancelSacParam.setSktLimitUserCancelYn(purchaseCancelByAdminSacReq.getSktLimitUserCancelYn());
		if (StringUtils.equals(PurchaseConstants.PRCHS_REQ_PATH_ADMIN_REFUND,
				purchaseCancelSacParam.getCancelReqPathCd())
				|| StringUtils.equals(PurchaseConstants.PRCHS_REQ_PATH_ADMIN_FORCE_CANCEL,
						purchaseCancelSacParam.getCancelReqPathCd())
				|| StringUtils.equals(PurchaseConstants.PRCHS_REQ_PATH_ADMIN_PAYMENT_NOT_SYNC,
						purchaseCancelSacParam.getCancelReqPathCd())
				|| StringUtils.equals(PurchaseConstants.PRCHS_REQ_PATH_PAYMENT_ERROR_CANCEL,
						purchaseCancelSacParam.getCancelReqPathCd())) {
			purchaseCancelSacParam.setIgnorePayment(true); // 결제 취소 진행 안함
		} else {
			purchaseCancelSacParam.setIgnorePayment(false); // 결제 취소 진행함 (OR00400, OR00435)
		}

		// request admin type setting.
		purchaseCancelSacParam.setPrchsCancelByType(PurchaseConstants.PRCHS_CANCEL_BY_ADMIN);

		// 2014.07.29 최상훈c 요건 추가(해당값이 Y이면 PP 결제 오류시 예외처리 하기위해 사용)
		if (StringUtils.equals(PurchaseConstants.USE_Y, purchaseCancelByAdminSacReq.getIgnorePayPlanet())) {
			purchaseCancelSacParam.setIgnorePayPlanet(true);
		}

		// parameter setting.
		List<PurchaseCancelDetailSacParam> prchsCancelList = new ArrayList<PurchaseCancelDetailSacParam>();
		for (PurchaseCancelDetailSacReq purchaseCancelDetailSacReq : purchaseCancelByAdminSacReq.getPrchsCancelList()) {

			PurchaseCancelDetailSacParam purchaseCancelDetailSacParam = new PurchaseCancelDetailSacParam();

			purchaseCancelDetailSacParam.setPrchsId(purchaseCancelDetailSacReq.getPrchsId());
			purchaseCancelDetailSacParam.setCancelMdn(purchaseCancelDetailSacReq.getCancelMdn());

			prchsCancelList.add(purchaseCancelDetailSacParam);

		}

		// systemId에 요청자 정보가 있으면 셋팅하고 없으면 systemId로 셋팅
		purchaseCancelSacParam
				.setSystemId(!StringUtils.isEmpty(purchaseCancelByAdminSacReq.getReqUserId()) ? purchaseCancelByAdminSacReq
						.getReqUserId() : purchaseCancelSacParam.getSystemId());

		purchaseCancelSacParam.setPrchsCancelList(prchsCancelList);

		return purchaseCancelSacParam;

	}

	/**
	 * 
	 * <pre>
	 * convertResForCancelPurchaseByAdmin.
	 * </pre>
	 * 
	 * @param purchaseCancelSacResult
	 *            purchaseCancelSacResult
	 * @return PurchaseCancelByAdminSacRes
	 */
	private PurchaseCancelByAdminSacRes convertResForCancelPurchaseByAdmin(
			PurchaseCancelSacResult purchaseCancelSacResult) {

		PurchaseCancelByAdminSacRes purchaseCancelByAdminSacRes = new PurchaseCancelByAdminSacRes();

		// cancel common response setting.
		if (!ConvertVO.convertPurchaseCancelCommonSacRes(purchaseCancelSacResult, purchaseCancelByAdminSacRes)) {
			throw new StorePlatformException("SAC_PUR_9902");
		}

		// response setting.
		purchaseCancelByAdminSacRes.setTotCnt(purchaseCancelSacResult.getTotCnt());
		purchaseCancelByAdminSacRes.setSuccessCnt(purchaseCancelSacResult.getSuccessCnt());
		purchaseCancelByAdminSacRes.setFailCnt(purchaseCancelSacResult.getFailCnt());

		List<PurchaseCancelDetailSacRes> prchsCancelList = new ArrayList<PurchaseCancelDetailSacRes>();
		for (PurchaseCancelDetailSacResult purchaseCancelDetailSacResult : purchaseCancelSacResult.getPrchsCancelList()) {

			PurchaseCancelDetailSacRes purchaseCancelDetailSacRes = new PurchaseCancelDetailSacRes();

			purchaseCancelDetailSacRes.setPrchsId(purchaseCancelDetailSacResult.getPrchsId());
			purchaseCancelDetailSacRes.setResultCd(purchaseCancelDetailSacResult.getResultCd());
			purchaseCancelDetailSacRes.setResultMsg(purchaseCancelDetailSacResult.getResultMsg());

			prchsCancelList.add(purchaseCancelDetailSacRes);

		}

		purchaseCancelByAdminSacRes.setPrchsCancelList(prchsCancelList);

		return purchaseCancelByAdminSacRes;

	}

	private PurchaseCancelSacParam convertReqForCancelPurchaseForPaymentError(SacRequestHeader sacRequestHeader,
			PurchaseCancelForPaymentErrorSacReq purchaseCancelForPaymentErrorSacReq) {

		PurchaseCancelSacParam purchaseCancelSacParam = new PurchaseCancelSacParam();

		// common parameter setting.
		if (!ConvertVO.convertPurchaseCommonSacReq(sacRequestHeader, purchaseCancelForPaymentErrorSacReq,
				purchaseCancelSacParam)) {
			throw new StorePlatformException("SAC_PUR_9901");
		}

		// 2014.12.08 tenantId 받도록 처리함(Default:HeaderValue)
		if (StringUtils.isNotBlank(purchaseCancelForPaymentErrorSacReq.getTenantId())) {
			purchaseCancelSacParam.setTenantId(purchaseCancelForPaymentErrorSacReq.getTenantId());
		}

		purchaseCancelSacParam.setReqUserId(purchaseCancelForPaymentErrorSacReq.getReqUserId());
		purchaseCancelSacParam.setCancelReqPathCd(purchaseCancelForPaymentErrorSacReq.getCancelReqPathCd());
		purchaseCancelSacParam.setShoppingForceCancelYn("N");
		purchaseCancelSacParam.setSktLimitUserCancelYn("N");
		purchaseCancelSacParam.setIgnorePayment(true);

		// request admin type setting.
		purchaseCancelSacParam.setPrchsCancelByType(PurchaseConstants.PRCHS_CANCEL_BY_ADMIN);

		// systemId에 요청자 정보가 있으면 셋팅하고 없으면 systemId로 셋팅
		purchaseCancelSacParam
				.setSystemId(!StringUtils.isEmpty(purchaseCancelForPaymentErrorSacReq.getReqUserId()) ? purchaseCancelForPaymentErrorSacReq
						.getReqUserId() : purchaseCancelSacParam.getSystemId());

		// parameter setting.
		List<PurchaseCancelDetailSacParam> prchsCancelList = new ArrayList<PurchaseCancelDetailSacParam>();
		PurchaseCancelDetailSacParam purchaseCancelDetailSacParam = new PurchaseCancelDetailSacParam();

		purchaseCancelDetailSacParam.setPrchsId(purchaseCancelForPaymentErrorSacReq.getPrchsId());

		prchsCancelList.add(purchaseCancelDetailSacParam);

		purchaseCancelSacParam.setPrchsCancelList(prchsCancelList);

		return purchaseCancelSacParam;

	}

	/**
	 * 
	 * <pre>
	 * convertResForCancelPurchaseForPaymentError.
	 * </pre>
	 * 
	 * @param purchaseCancelDetailSacResult
	 *            purchaseCancelDetailSacResult
	 * @return PurchaseCancelDetailSacResult
	 */
	private PurchaseCancelForPaymentErrorSacRes convertResForCancelPurchaseForPaymentError(
			PurchaseCancelDetailSacResult purchaseCancelDetailSacResult) {

		PurchaseCancelForPaymentErrorSacRes purchaseCancelForPaymentErrorSacRes = new PurchaseCancelForPaymentErrorSacRes();

		purchaseCancelForPaymentErrorSacRes.setPrchsId(purchaseCancelDetailSacResult.getPrchsId());

		return purchaseCancelForPaymentErrorSacRes;

	}

	private PurchaseCancelSacParam convertReqForCancelPurchaseForInApp(SacRequestHeader sacRequestHeader,
			PurchaseCancelForInAppSacReq purchaseCancelForInAppSacReq) {

		PurchaseCancelSacParam purchaseCancelSacParam = new PurchaseCancelSacParam();

		// common parameter setting.
		if (!ConvertVO.convertPurchaseCommonSacReq(sacRequestHeader, purchaseCancelForInAppSacReq,
				purchaseCancelSacParam)) {
			throw new StorePlatformException("SAC_PUR_9901");
		}

		purchaseCancelSacParam.setReqUserId(purchaseCancelForInAppSacReq.getReqUserId());
		purchaseCancelSacParam.setCancelReqPathCd(purchaseCancelForInAppSacReq.getCancelReqPathCd());
		purchaseCancelSacParam.setShoppingForceCancelYn("N");
		purchaseCancelSacParam.setSktLimitUserCancelYn("N");
		purchaseCancelSacParam.setIgnorePayment(true);

		// request admin type setting.
		purchaseCancelSacParam.setPrchsCancelByType(PurchaseConstants.PRCHS_CANCEL_BY_ADMIN);

		// parameter setting.
		List<PurchaseCancelDetailSacParam> prchsCancelList = new ArrayList<PurchaseCancelDetailSacParam>();
		PurchaseCancelDetailSacParam purchaseCancelDetailSacParam = new PurchaseCancelDetailSacParam();

		purchaseCancelDetailSacParam.setPrchsId(purchaseCancelForInAppSacReq.getPrchsId());

		// systemId에 요청자 정보가 있으면 셋팅하고 없으면 systemId로 셋팅
		purchaseCancelSacParam
				.setSystemId(!StringUtils.isEmpty(purchaseCancelForInAppSacReq.getReqUserId()) ? purchaseCancelForInAppSacReq
						.getReqUserId() : purchaseCancelSacParam.getSystemId());

		prchsCancelList.add(purchaseCancelDetailSacParam);

		purchaseCancelSacParam.setPrchsCancelList(prchsCancelList);

		return purchaseCancelSacParam;

	}

	private PurchaseCancelForInAppSacRes convertResForCancelPurchaseForInApp(
			PurchaseCancelDetailSacResult purchaseCancelDetailSacResult) {

		PurchaseCancelForInAppSacRes purchaseCancelForInAppSacRes = new PurchaseCancelForInAppSacRes();

		purchaseCancelForInAppSacRes.setPrchsId(purchaseCancelDetailSacResult.getPrchsId());

		return purchaseCancelForInAppSacRes;

	}

	/**
	 * 
	 * <pre>
	 * convertReqForCancelPurchaseByUser
	 * </pre>
	 * 
	 * @param sacRequestHeader
	 *            sacRequestHeader
	 * @param purchaseCancelByUserSacReq
	 *            purchaseCancelByUserSacReq
	 * @return PurchaseCancelSacParam
	 */
	private PurchaseCancelSacParam convertReqForCancelPurchaseForTcash(SacRequestHeader sacRequestHeader,
			PurchaseCancelForTCashSacReq purchaseCancelForTCashSacReq) {

		PurchaseCancelSacParam purchaseCancelSacParam = new PurchaseCancelSacParam();

		// common parameter setting.
		if (!ConvertVO.convertPurchaseCommonSacReq(sacRequestHeader, purchaseCancelForTCashSacReq,
				purchaseCancelSacParam)) {
			throw new StorePlatformException("SAC_PUR_9901");
		}

		purchaseCancelSacParam.setReqUserId(purchaseCancelForTCashSacReq.getReqUserId());
		purchaseCancelSacParam.setCancelReqPathCd(purchaseCancelForTCashSacReq.getCancelReqPathCd());
		purchaseCancelSacParam.setShoppingForceCancelYn("N");
		purchaseCancelSacParam.setSktLimitUserCancelYn("N");
		purchaseCancelSacParam.setIgnorePayment(false);

		// request user type setting.
		purchaseCancelSacParam.setPrchsCancelByType(PurchaseConstants.PRCHS_CANCEL_BY_ADMIN);
		purchaseCancelSacParam.setPrchsCancelServiceType(PurchaseConstants.PRCHS_CANCEL_SERVICE_TCASH);

		// parameter setting.
		List<PurchaseCancelDetailSacParam> prchsCancelList = new ArrayList<PurchaseCancelDetailSacParam>();
		for (PurchaseCancelDetailSacReq purchaseCancelDetailSacReq : purchaseCancelForTCashSacReq.getPrchsCancelList()) {

			PurchaseCancelDetailSacParam purchaseCancelDetailSacParam = new PurchaseCancelDetailSacParam();

			purchaseCancelDetailSacParam.setPrchsId(purchaseCancelDetailSacReq.getPrchsId());

			prchsCancelList.add(purchaseCancelDetailSacParam);

		}

		// systemId에 요청자 정보가 있으면 셋팅하고 없으면 systemId로 셋팅
		purchaseCancelSacParam
				.setSystemId(!StringUtils.isEmpty(purchaseCancelForTCashSacReq.getReqUserId()) ? purchaseCancelForTCashSacReq
						.getReqUserId() : purchaseCancelSacParam.getSystemId());

		purchaseCancelSacParam.setPrchsCancelList(prchsCancelList);

		return purchaseCancelSacParam;

	}

	/**
	 * 
	 * <pre>
	 * convertResForCancelPurchaseByUser.
	 * </pre>
	 * 
	 * @param purchaseCancelByUserSacResult
	 *            purchaseCancelByUserSacResult
	 * @return PurchaseCancelByUserSacRes
	 */
	private PurchaseCancelForTCashSacRes convertResForCancelPurchaseForTcash(
			PurchaseCancelSacResult purchaseCancelSacResult) {

		PurchaseCancelForTCashSacRes purchaseCancelForTCashSacRes = new PurchaseCancelForTCashSacRes();

		// cancel common response setting.
		if (!ConvertVO.convertPurchaseCancelCommonSacRes(purchaseCancelSacResult, purchaseCancelForTCashSacRes)) {
			throw new StorePlatformException("SAC_PUR_9902");
		}

		// response setting.
		purchaseCancelForTCashSacRes.setTotCnt(purchaseCancelSacResult.getTotCnt());
		purchaseCancelForTCashSacRes.setSuccessCnt(purchaseCancelSacResult.getSuccessCnt());
		purchaseCancelForTCashSacRes.setFailCnt(purchaseCancelSacResult.getFailCnt());

		List<PurchaseCancelDetailSacRes> prchsCancelList = new ArrayList<PurchaseCancelDetailSacRes>();
		for (PurchaseCancelDetailSacResult purchaseCancelDetailSacResult : purchaseCancelSacResult.getPrchsCancelList()) {

			PurchaseCancelDetailSacRes purchaseCancelDetailSacRes = new PurchaseCancelDetailSacRes();

			purchaseCancelDetailSacRes.setPrchsId(purchaseCancelDetailSacResult.getPrchsId());
			purchaseCancelDetailSacRes.setResultCd(purchaseCancelDetailSacResult.getResultCd());
			purchaseCancelDetailSacRes.setResultMsg(purchaseCancelDetailSacResult.getResultMsg());

			prchsCancelList.add(purchaseCancelDetailSacRes);

		}

		purchaseCancelForTCashSacRes.setPrchsCancelList(prchsCancelList);

		return purchaseCancelForTCashSacRes;

	}

}
