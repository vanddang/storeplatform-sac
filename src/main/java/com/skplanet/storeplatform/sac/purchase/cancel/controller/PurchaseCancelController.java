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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.sac.client.purchase.cancel.vo.PurchaseCancelByAdminSacReq;
import com.skplanet.storeplatform.sac.client.purchase.cancel.vo.PurchaseCancelByAdminSacRes;
import com.skplanet.storeplatform.sac.client.purchase.cancel.vo.PurchaseCancelByUserSacReq;
import com.skplanet.storeplatform.sac.client.purchase.cancel.vo.PurchaseCancelByUserSacRes;
import com.skplanet.storeplatform.sac.client.purchase.cancel.vo.PurchaseCancelDetailSacReq;
import com.skplanet.storeplatform.sac.client.purchase.cancel.vo.PurchaseCancelDetailSacRes;
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

		purchaseCancelSacParam.setCancelReqPathCd(purchaseCancelByUserSacReq.getCancelReqPathCd());
		purchaseCancelSacParam.setForceCancelYn("N");
		purchaseCancelSacParam.setSktLimitUserCancelYn("N");
		// request user type setting.
		purchaseCancelSacParam.setPrchsCancelByType(PurchaseConstants.PRCHS_CANCEL_BY_USER);

		// parameter setting.
		List<PurchaseCancelDetailSacParam> prchsCancelList = new ArrayList<PurchaseCancelDetailSacParam>();
		for (PurchaseCancelDetailSacReq purchaseCancelDetailSacReq : purchaseCancelByUserSacReq.getPrchsCancelList()) {

			PurchaseCancelDetailSacParam purchaseCancelDetailSacParam = new PurchaseCancelDetailSacParam();

			purchaseCancelDetailSacParam.setPrchsId(purchaseCancelDetailSacReq.getPrchsId());

			prchsCancelList.add(purchaseCancelDetailSacParam);

		}

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

		PurchaseCancelSacParam purchaseCancelSacParam = new PurchaseCancelSacParam();

		// common parameter setting.
		if (!ConvertVO.convertPurchaseCommonSacReq(sacRequestHeader, purchaseCancelByAdminSacReq,
				purchaseCancelSacParam)) {
			throw new StorePlatformException("SAC_PUR_9901");
		}

		purchaseCancelSacParam.setCancelReqPathCd(purchaseCancelByAdminSacReq.getCancelReqPathCd());
		purchaseCancelSacParam.setForceCancelYn(purchaseCancelByAdminSacReq.getForceCancelYn());
		purchaseCancelSacParam.setSktLimitUserCancelYn(purchaseCancelByAdminSacReq.getSktLimitUserCancelYn());

		// request admin type setting.
		purchaseCancelSacParam.setPrchsCancelByType(PurchaseConstants.PRCHS_CANCEL_BY_ADMIN);

		// parameter setting.
		List<PurchaseCancelDetailSacParam> prchsCancelList = new ArrayList<PurchaseCancelDetailSacParam>();
		for (PurchaseCancelDetailSacReq purchaseCancelDetailSacReq : purchaseCancelByAdminSacReq.getPrchsCancelList()) {

			PurchaseCancelDetailSacParam purchaseCancelDetailSacParam = new PurchaseCancelDetailSacParam();

			purchaseCancelDetailSacParam.setPrchsId(purchaseCancelDetailSacReq.getPrchsId());

			prchsCancelList.add(purchaseCancelDetailSacParam);

		}

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

		purchaseCancelSacParam.setCancelReqPathCd(purchaseCancelForTCashSacReq.getCancelReqPathCd());
		purchaseCancelSacParam.setForceCancelYn("N");
		purchaseCancelSacParam.setSktLimitUserCancelYn("N");
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
