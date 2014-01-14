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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.purchase.client.history.vo.GiftConfirmRequest;
import com.skplanet.storeplatform.purchase.client.history.vo.GiftConfirmResponse;
import com.skplanet.storeplatform.purchase.client.history.vo.GiftReceiveRequest;
import com.skplanet.storeplatform.purchase.client.history.vo.GiftReceiveResponse;
import com.skplanet.storeplatform.sac.client.purchase.vo.history.GiftConfirmReq;
import com.skplanet.storeplatform.sac.client.purchase.vo.history.GiftConfirmRes;
import com.skplanet.storeplatform.sac.client.purchase.vo.history.GiftReceiveReq;
import com.skplanet.storeplatform.sac.client.purchase.vo.history.GiftReceiveRes;
import com.skplanet.storeplatform.sac.purchase.history.service.GiftService;

/**
 * 구매 SAC 컨트롤러
 * 
 * Updated on : 2014-01-14 Updated by : 조용진, 엔텔스.
 */
@Controller
@RequestMapping(value = "/purchase")
public class GiftController {

	@Autowired
	private GiftService giftService;

	/**
	 * 선물수신확인 체크.
	 * 
	 * @param GiftReceiveReq
	 *            선물수신확인 체크
	 * @return GiftReceiveRes
	 */
	@RequestMapping(value = "/history/gift/get/v1", method = RequestMethod.POST)
	@ResponseBody
	public GiftReceiveRes searchGiftReceive(@RequestBody GiftReceiveReq giftReceiveReq) {

		GiftReceiveRequest req = this.reqConvert(giftReceiveReq);
		GiftReceiveResponse giftReceiveResponse = new GiftReceiveResponse();
		giftReceiveResponse = this.giftService.searchGiftReceive(req);
		GiftReceiveRes res = this.resConvert(giftReceiveResponse);

		return res;
	}

	/**
	 * 선물수신.
	 * 
	 * @param GiftConfirmReq
	 *            선물수신
	 * @return GiftConfirmRes
	 */
	@RequestMapping(value = "/history/gift/modify/v1", method = RequestMethod.POST)
	@ResponseBody
	public GiftConfirmRes modifyGiftConfirm(@RequestBody GiftConfirmReq giftConfirmReq) {

		GiftConfirmRequest req = this.reqConvert(giftConfirmReq);
		GiftConfirmResponse giftConfirmResponse = new GiftConfirmResponse();
		giftConfirmResponse = this.giftService.modifyGiftConfirm(req);
		GiftConfirmRes res = this.resConvert(giftConfirmResponse);

		return res;
	}

	private GiftConfirmRequest reqConvert(GiftConfirmReq giftConfirmReq) {
		GiftConfirmRequest req = new GiftConfirmRequest();

		req.setTenantId(giftConfirmReq.getTenantId());
		req.setSendMbrNo(giftConfirmReq.getSendMbrNo());
		req.setSendDeviceNo(giftConfirmReq.getSendDeviceNo());
		req.setRecvMbrNo(giftConfirmReq.getRecvMbrNo());
		req.setRecvDeviceNo(giftConfirmReq.getRecvDeviceNo());
		req.setPrchsId(giftConfirmReq.getPrchsId());
		req.setProdId(giftConfirmReq.getProdId());

		return req;
	}

	private GiftConfirmRes resConvert(GiftConfirmResponse giftComfirmResponse) {
		GiftConfirmRes res = new GiftConfirmRes();
		res.setCount(giftComfirmResponse.getCount());

		return res;
	}

	private GiftReceiveRequest reqConvert(GiftReceiveReq giftReceiveReq) {
		GiftReceiveRequest req = new GiftReceiveRequest();

		req.setTenantId(giftReceiveReq.getTenantId());
		req.setSendMbrNo(giftReceiveReq.getSendMbrNo());
		req.setSendDeviceNo(giftReceiveReq.getSendDeviceNo());
		req.setRecvMbrNo(giftReceiveReq.getRecvMbrNo());
		req.setRecvDeviceNo(giftReceiveReq.getRecvDeviceNo());
		req.setPrchsId(giftReceiveReq.getPrchsId());
		req.setProdId(giftReceiveReq.getProdId());

		return req;
	}

	private GiftReceiveRes resConvert(GiftReceiveResponse giftReceiveResponse) {
		GiftReceiveRes res = new GiftReceiveRes();
		res.setRecvDt(giftReceiveResponse.getRecvDt());

		return res;
	}
}
