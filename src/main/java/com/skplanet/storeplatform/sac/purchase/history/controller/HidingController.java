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

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.purchase.client.history.vo.HidingRequest;
import com.skplanet.storeplatform.purchase.client.history.vo.HidingResponse;
import com.skplanet.storeplatform.purchase.client.history.vo.HidingScList;
import com.skplanet.storeplatform.sac.client.purchase.vo.history.HidingReq;
import com.skplanet.storeplatform.sac.client.purchase.vo.history.HidingRes;
import com.skplanet.storeplatform.sac.purchase.history.service.HidingSacService;

/**
 * 구매 SAC 컨트롤러
 * 
 * Updated on : 2014-01-14 Updated by : 조용진, 엔텔스.
 */
@Controller
@RequestMapping(value = "/purchase")
public class HidingController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private HidingSacService hidingSacService;

	/**
	 * 기구매 체크 SAC.
	 * 
	 * @param hidingReq
	 *            기구매 체크 SAC
	 * @return List<HidingRes>
	 */
	@RequestMapping(value = "/history/hiding/modify/v1", method = RequestMethod.POST)
	@ResponseBody
	public List<HidingRes> modifyHiding(@RequestBody HidingReq hidingReq) {

		HidingRequest req = this.reqConvert(hidingReq);
		List<HidingResponse> hidingResponse = new ArrayList<HidingResponse>();
		hidingResponse = this.hidingSacService.modifyHiding(req);

		return this.resConvert(hidingResponse);
	}

	/**
	 * reqConvert.
	 * 
	 * @param hidingReq
	 *            reqConvert
	 * @return HidingRequest
	 */
	private HidingRequest reqConvert(HidingReq hidingReq) {
		HidingRequest req = new HidingRequest();
		List<HidingScList> list = new ArrayList<HidingScList>();

		req.setTenantId(hidingReq.getTenantId());
		req.setInsdUsermbrNo(hidingReq.getInsdUsermbrNo());
		req.setInsdDeviceId(hidingReq.getInsdDeviceId());
		int size = hidingReq.getHidingSacList().size();
		this.logger.debug("@@@@@@reqConvert@@@@@@@" + size);
		for (int i = 0; i < size; i++) {
			HidingScList hidingScList = new HidingScList();

			hidingScList.setPrchsId(hidingReq.getHidingSacList().get(i).getPrchsId());
			hidingScList.setPrchsDtlId(hidingReq.getHidingSacList().get(i).getPrchsDtlId());
			hidingScList.setHidingYn(hidingReq.getHidingSacList().get(i).getHidingYn());

			list.add(hidingScList);
		}
		req.setHidingScList(list);

		return req;
	}

	/**
	 * resConvert.
	 * 
	 * @param hidingResponseList
	 *            resConvert
	 * @return List<HidingRes>
	 */
	private List<HidingRes> resConvert(List<HidingResponse> hidingResponseList) {
		List<HidingRes> res = new ArrayList<HidingRes>();
		int size = hidingResponseList.size();
		this.logger.debug("@@@@@@resConvert@@@@@@@" + size);
		for (int i = 0; i < size; i++) {
			HidingRes hidingRes = new HidingRes();
			hidingRes.setPrchsId(hidingResponseList.get(i).getPrchsId());
			hidingRes.setPrchsDtlId(hidingResponseList.get(i).getPrchsDtlId());
			hidingRes.setResultYn(hidingResponseList.get(i).getResultYn());
			res.add(hidingRes);
		}

		return res;
	}
}
