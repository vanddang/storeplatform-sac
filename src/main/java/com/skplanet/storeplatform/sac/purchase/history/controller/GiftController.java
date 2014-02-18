/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.history.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.purchase.client.history.vo.GiftConfirmScReq;
import com.skplanet.storeplatform.purchase.client.history.vo.GiftConfirmScRes;
import com.skplanet.storeplatform.purchase.client.history.vo.GiftReceiveScReq;
import com.skplanet.storeplatform.purchase.client.history.vo.GiftReceiveScRes;
import com.skplanet.storeplatform.sac.client.purchase.vo.history.GiftConfirmSacReq;
import com.skplanet.storeplatform.sac.client.purchase.vo.history.GiftConfirmSacRes;
import com.skplanet.storeplatform.sac.client.purchase.vo.history.GiftReceiveSacReq;
import com.skplanet.storeplatform.sac.client.purchase.vo.history.GiftReceiveSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.purchase.common.util.PurchaseCommonUtils;
import com.skplanet.storeplatform.sac.purchase.history.service.GiftSacService;

/**
 * 구매 SAC 컨트롤러
 * 
 * Updated on : 2014-01-14 Updated by : 조용진, 엔텔스.
 */
@Controller
@RequestMapping(value = "/purchase")
public class GiftController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private GiftSacService giftService;
	@Autowired
	private PurchaseCommonUtils purchaseCommonUtils;

	/**
	 * 선물수신확인 체크 SAC.
	 * 
	 * @param giftReceiveSacReq
	 *            요청정보
	 * @param bindingResult
	 *            Validated Result
	 * @param requestHeader
	 *            헤더정보
	 * @return GiftReceiveSacRes
	 */
	@RequestMapping(value = "/history/gift/search/v1", method = RequestMethod.POST)
	@ResponseBody
	public GiftReceiveSacRes searchGiftReceive(@RequestBody @Validated GiftReceiveSacReq giftReceiveSacReq,
			BindingResult bindingResult, SacRequestHeader requestHeader) {
		TenantHeader header = requestHeader.getTenantHeader();
		// 필수값 체크
		this.purchaseCommonUtils.getBindingValid(bindingResult);

		return this.resConvert(this.giftService.searchGiftReceive(this.reqConvert(giftReceiveSacReq, header)));
	}

	/**
	 * 선물수신 처리.
	 * 
	 * @param giftConfirmSacReq
	 *            요청정보
	 * @param bindingResult
	 *            Validated Result
	 * @param requestHeader
	 *            헤더정보
	 * @return GiftConfirmSacRes
	 */
	@RequestMapping(value = "/history/gift/update/v1", method = RequestMethod.POST)
	@ResponseBody
	public GiftConfirmSacRes modifyGiftConfirm(@RequestBody @Validated GiftConfirmSacReq giftConfirmSacReq,
			BindingResult bindingResult, SacRequestHeader requestHeader) {
		TenantHeader header = requestHeader.getTenantHeader();
		// 필수값 체크
		if (bindingResult.hasErrors()) {
			List<FieldError> errors = bindingResult.getFieldErrors();
			for (FieldError error : errors) {
				if (error.getCode().equals("Pattern")) {
					throw new StorePlatformException("SAC_PUR_0003", error.getField(), error.getRejectedValue(),
							"YYYYMMDDHH24MISS");
				} else {
					throw new StorePlatformException("SAC_PUR_0001", error.getField());
				}

			}
		}
		this.purchaseCommonUtils.getBindingValid(bindingResult);

		return this.resConvert(this.giftService.updateGiftConfirm(this.reqConvert(giftConfirmSacReq, header)));
	}

	/**
	 * 선물수신처리 reqConvert.
	 * 
	 * @param giftConfirmSacReq
	 *            선물수신처리 요청정보
	 * @param header
	 *            테넌트 헤더정보
	 * @return GiftConfirmScReq
	 */
	private GiftConfirmScReq reqConvert(GiftConfirmSacReq giftConfirmSacReq, TenantHeader header) {
		GiftConfirmScReq req = new GiftConfirmScReq();

		req.setTenantId(header.getTenantId());
		req.setSystemId(header.getSystemId());
		req.setUserKey(giftConfirmSacReq.getUserKey());
		req.setDeviceKey(giftConfirmSacReq.getDeviceKey());
		req.setPrchsId(giftConfirmSacReq.getPrchsId());
		req.setRecvDt(giftConfirmSacReq.getRecvDt());
		req.setRecvConfPathCd(giftConfirmSacReq.getRecvConfPathCd());
		req.setProdId(giftConfirmSacReq.getProdId());

		return req;
	}

	/**
	 * 선물수신처리 resConvert.
	 * 
	 * @param giftComfirmScRes
	 *            선물수신처리 응답정보
	 * @return GiftConfirmSacRes
	 */
	private GiftConfirmSacRes resConvert(GiftConfirmScRes giftComfirmScRes) {
		GiftConfirmSacRes res = new GiftConfirmSacRes();
		res.setPrchsId(giftComfirmScRes.getPrchsId());
		res.setProdId(giftComfirmScRes.getProdId());
		res.setResultYn(giftComfirmScRes.getResultYn());

		return res;
	}

	/**
	 * reqConvert.
	 * 
	 * @param giftReceiveSacReq
	 *            선물수신확인 체크 요청정보
	 * @param header
	 *            테넌트 헤더정보
	 * @return giftReceiveSacReq
	 */
	private GiftReceiveScReq reqConvert(GiftReceiveSacReq giftReceiveSacReq, TenantHeader header) {
		GiftReceiveScReq req = new GiftReceiveScReq();

		req.setTenantId(header.getTenantId());
		req.setUserKey(giftReceiveSacReq.getUserKey());
		req.setDeviceKey(giftReceiveSacReq.getDeviceKey());
		req.setPrchsId(giftReceiveSacReq.getPrchsId());
		req.setProdId(giftReceiveSacReq.getProdId());

		return req;
	}

	/**
	 * resConvert.
	 * 
	 * @param giftReceiveScRes
	 *            요청정보
	 * @return GiftReceiveSacRes
	 */
	private GiftReceiveSacRes resConvert(GiftReceiveScRes giftReceiveScRes) {
		GiftReceiveSacRes res = new GiftReceiveSacRes();
		res.setRecvDt(giftReceiveScRes.getRecvDt());

		return res;
	}
}
