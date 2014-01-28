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
import com.skplanet.storeplatform.purchase.client.history.vo.GiftConfirmRequest;
import com.skplanet.storeplatform.purchase.client.history.vo.GiftConfirmResponse;
import com.skplanet.storeplatform.purchase.client.history.vo.GiftReceiveRequest;
import com.skplanet.storeplatform.purchase.client.history.vo.GiftReceiveResponse;
import com.skplanet.storeplatform.sac.client.purchase.vo.history.GiftConfirmReq;
import com.skplanet.storeplatform.sac.client.purchase.vo.history.GiftConfirmRes;
import com.skplanet.storeplatform.sac.client.purchase.vo.history.GiftReceiveReq;
import com.skplanet.storeplatform.sac.client.purchase.vo.history.GiftReceiveRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.purchase.history.service.GiftService;

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
	private GiftService giftService;

	/**
	 * 선물수신확인 체크 SAC.
	 * 
	 * @param giftReceiveReq
	 *            요청정보
	 * @param requestHeader
	 *            헤더정보
	 * @return GiftReceiveRes
	 */
	@RequestMapping(value = "/history/gift/get/v1", method = RequestMethod.POST)
	@ResponseBody
	public GiftReceiveRes searchGiftReceive(@RequestBody @Validated GiftReceiveReq giftReceiveReq,
			BindingResult bindingResult, SacRequestHeader requestHeader) {
		TenantHeader header = requestHeader.getTenantHeader();
		// 필수값 체크
		if (bindingResult.hasErrors()) {
			List<FieldError> errors = bindingResult.getFieldErrors();
			for (FieldError error : errors) {
				throw new StorePlatformException("SAC_PUR_0001", error.getField() + error.getCode());
			}
		}
		GiftReceiveRequest req = this.reqConvert(giftReceiveReq, header);
		GiftReceiveResponse giftReceiveResponse = new GiftReceiveResponse();
		giftReceiveResponse = this.giftService.searchGiftReceive(req);
		GiftReceiveRes res = this.resConvert(giftReceiveResponse);

		return res;
	}

	/**
	 * 선물수신 처리.
	 * 
	 * @param giftConfirmReq
	 *            요청정보
	 * @param header
	 *            테넌트 헤더정보
	 * @return GiftConfirmRes
	 */
	@RequestMapping(value = "/history/gift/modify/v1", method = RequestMethod.POST)
	@ResponseBody
	public GiftConfirmRes modifyGiftConfirm(@RequestBody @Validated GiftConfirmReq giftConfirmReq,
			BindingResult bindingResult, SacRequestHeader requestHeader) {
		TenantHeader header = requestHeader.getTenantHeader();
		// 필수값 체크
		if (bindingResult.hasErrors()) {
			List<FieldError> errors = bindingResult.getFieldErrors();
			for (FieldError error : errors) {
				this.logger.debug("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@{} ", error.getObjectName());
				if (error.getCode().equals("Pattern")) {
					throw new StorePlatformException("SAC_PUR_0003", error.getField(), error.getRejectedValue(),
							"YYYYMMDDHH24MISS");
				} else {
					throw new StorePlatformException("SAC_PUR_0001", error.getField());
				}

			}
		}

		GiftConfirmRequest req = this.reqConvert(giftConfirmReq, header);
		GiftConfirmResponse giftConfirmResponse = new GiftConfirmResponse();
		giftConfirmResponse = this.giftService.modifyGiftConfirm(req);
		GiftConfirmRes res = this.resConvert(giftConfirmResponse);

		return res;
	}

	/**
	 * 선물수신처리 reqConvert.
	 * 
	 * @param giftConfirmReq
	 *            선물수신처리 요청정보
	 * @param header
	 *            테넌트 헤더정보
	 * @return GiftConfirmRequest
	 */
	private GiftConfirmRequest reqConvert(GiftConfirmReq giftConfirmReq, TenantHeader header) {
		GiftConfirmRequest req = new GiftConfirmRequest();

		req.setTenantId(header.getTenantId());
		req.setSystemId(header.getSystemId());
		req.setInsdUsermbrNo(giftConfirmReq.getInsdUsermbrNo());
		req.setInsdDeviceId(giftConfirmReq.getInsdDeviceId());
		req.setPrchsId(giftConfirmReq.getPrchsId());
		req.setRecvDt(giftConfirmReq.getRecvDt());
		req.setProdId(giftConfirmReq.getProdId());

		return req;
	}

	/**
	 * 선물수신처리 resConvert.
	 * 
	 * @param giftComfirmResponse
	 *            선물수신처리 응답정보
	 * @return GiftConfirmRes
	 */
	private GiftConfirmRes resConvert(GiftConfirmResponse giftComfirmResponse) {
		GiftConfirmRes res = new GiftConfirmRes();
		res.setPrchsId(giftComfirmResponse.getPrchsId());
		res.setProdId(giftComfirmResponse.getProdId());
		res.setResultYn(giftComfirmResponse.getResultYn());

		return res;
	}

	/**
	 * reqConvert.
	 * 
	 * @param giftReceiveReq
	 *            요청정보
	 * @param header
	 *            테넌트 헤더정보
	 * @return GiftReceiveRequest
	 */
	private GiftReceiveRequest reqConvert(GiftReceiveReq giftReceiveReq, TenantHeader header) {
		GiftReceiveRequest req = new GiftReceiveRequest();

		req.setTenantId(header.getTenantId());
		req.setSendMbrNo(giftReceiveReq.getSendMbrNo());
		req.setSendDeviceNo(giftReceiveReq.getSendDeviceNo());
		req.setRecvMbrNo(giftReceiveReq.getRecvMbrNo());
		req.setRecvDeviceNo(giftReceiveReq.getRecvDeviceNo());
		req.setPrchsId(giftReceiveReq.getPrchsId());
		req.setProdId(giftReceiveReq.getProdId());

		return req;
	}

	/**
	 * resConvert.
	 * 
	 * @param giftReceiveResponse
	 *            요청정보
	 * @return GiftReceiveRes
	 */
	private GiftReceiveRes resConvert(GiftReceiveResponse giftReceiveResponse) {
		GiftReceiveRes res = new GiftReceiveRes();
		res.setRecvDt(giftReceiveResponse.getRecvDt());

		return res;
	}
}
