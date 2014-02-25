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

import com.skplanet.storeplatform.purchase.constant.PurchaseConstants;
import com.skplanet.storeplatform.sac.client.purchase.cancel.vo.PurchaseCancelByUserDetailSacReq;
import com.skplanet.storeplatform.sac.client.purchase.cancel.vo.PurchaseCancelByUserDetailSacRes;
import com.skplanet.storeplatform.sac.client.purchase.cancel.vo.PurchaseCancelByUserSacReq;
import com.skplanet.storeplatform.sac.client.purchase.cancel.vo.PurchaseCancelByUserSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.purchase.cancel.service.PurchaseCancelService;
import com.skplanet.storeplatform.sac.purchase.cancel.vo.PurchaseCancelDetailSacParam;
import com.skplanet.storeplatform.sac.purchase.cancel.vo.PurchaseCancelDetailSacResult;
import com.skplanet.storeplatform.sac.purchase.cancel.vo.PurchaseCancelSacParam;
import com.skplanet.storeplatform.sac.purchase.cancel.vo.PurchaseCancelSacResult;
import com.skplanet.storeplatform.sac.purchase.common.util.ConvertVO;

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
	/*
	 * @RequestMapping(value = "/byAdmin/v1", method = RequestMethod.POST)
	 * 
	 * @ResponseBody public PurchaseCancelRes cancelPurchaseByAdmin(SacRequestHeader sacRequestHeader,
	 * 
	 * @RequestBody PurchaseCancelReq purchaseCancelReq) {
	 * 
	 * PurchaseCancelParam purchaseCancelParam = new PurchaseCancelParam(sacRequestHeader, purchaseCancelReq);
	 * 
	 * PurchaseCancelResult purchaseCancelResult = null; //
	 * this.purchaseCancelService.cancelPurchaseList(purchaseCancelParam);
	 * 
	 * PurchaseCancelRes purchaseCancelRes = this.setResponse(purchaseCancelResult);
	 * 
	 * return purchaseCancelRes;
	 * 
	 * }
	 */

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
		ConvertVO.convertPurchaseCommonSacReq(sacRequestHeader, purchaseCancelByUserSacReq, purchaseCancelSacParam);

		purchaseCancelSacParam.setCancelReqPathCd(purchaseCancelByUserSacReq.getCancelReqPathCd());
		// request user type setting.
		purchaseCancelSacParam.setPrchsCancelByType(PurchaseConstants.PRCHS_CANCEL_BY_USER);

		// parameter setting.
		List<PurchaseCancelDetailSacParam> prchsCancelList = new ArrayList<PurchaseCancelDetailSacParam>();
		for (PurchaseCancelByUserDetailSacReq purchaseCancelByUserDetailSacReq : purchaseCancelByUserSacReq
				.getPrchsCancelList()) {

			PurchaseCancelDetailSacParam purchaseCancelDetailSacParam = new PurchaseCancelDetailSacParam();

			purchaseCancelDetailSacParam.setPrchsId(purchaseCancelByUserDetailSacReq.getPrchsId());

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

		List<PurchaseCancelByUserDetailSacRes> prchsCancelList = new ArrayList<PurchaseCancelByUserDetailSacRes>();
		for (PurchaseCancelDetailSacResult purchaseCancelDetailSacResult : purchaseCancelSacResult.getPrchsCancelList()) {

			PurchaseCancelByUserDetailSacRes purchaseCancelByUserDetailSacRes = new PurchaseCancelByUserDetailSacRes();

			purchaseCancelByUserDetailSacRes.setPrchsId(purchaseCancelDetailSacResult.getPrchsId());
			purchaseCancelByUserDetailSacRes.setResultCd(purchaseCancelDetailSacResult.getResultCd());
			purchaseCancelByUserDetailSacRes.setResultMsg(purchaseCancelDetailSacResult.getResultMsg());

			prchsCancelList.add(purchaseCancelByUserDetailSacRes);

		}

		purchaseCancelByUserSacRes.setPrchsCancelList(prchsCancelList);

		return purchaseCancelByUserSacRes;

	}

	/*
	 * private PurchaseCancelRes setResponse(PurchaseCancelResult purchaseCancelResult) { PurchaseCancelRes
	 * purchaseCancelRes = new PurchaseCancelRes();
	 * 
	 * purchaseCancelRes.setTotCnt(purchaseCancelResult.getTotCnt());
	 * purchaseCancelRes.setSuccessCnt(purchaseCancelResult.getSuccessCnt());
	 * purchaseCancelRes.setFailCnt(purchaseCancelResult.getFailCnt());
	 * 
	 * List<PurchaseCancelResDetail> prchsCancelResultList = new ArrayList<PurchaseCancelResDetail>(); for
	 * (PurchaseCancelResultDetail purchaseCancelResultDetail : purchaseCancelResult.getPrchsCancelResultList()) {
	 * PurchaseCancelResDetail purchaseCancelResDetail = new PurchaseCancelResDetail();
	 * 
	 * purchaseCancelResDetail.setPrchsId(purchaseCancelResultDetail.getPrchsId());
	 * purchaseCancelResDetail.setPrchsCancelResultCd(purchaseCancelResultDetail.getPrchsCancelResultCd());
	 * purchaseCancelResDetail.setPrchsCancelResultMsg(purchaseCancelResultDetail.getPrchsCancelResultMsg());
	 * 
	 * prchsCancelResultList.add(purchaseCancelResDetail); }
	 * 
	 * purchaseCancelRes.setPrchsCancelResultList(prchsCancelResultList);
	 * 
	 * return purchaseCancelRes; }
	 */

}
