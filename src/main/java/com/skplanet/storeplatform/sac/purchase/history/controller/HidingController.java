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
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.purchase.client.history.vo.HidingListSc;
import com.skplanet.storeplatform.purchase.client.history.vo.HidingScReq;
import com.skplanet.storeplatform.purchase.client.history.vo.HidingScRes;
import com.skplanet.storeplatform.sac.client.purchase.vo.history.HidingListSac;
import com.skplanet.storeplatform.sac.client.purchase.vo.history.HidingListSacRes;
import com.skplanet.storeplatform.sac.client.purchase.vo.history.HidingSacReq;
import com.skplanet.storeplatform.sac.client.purchase.vo.history.HidingSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.purchase.common.util.PurchaseCommonUtils;
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
	@Autowired
	private PurchaseCommonUtils purchaseCommonUtils;

	/**
	 * 구매내역 숨김처리 SAC.
	 * 
	 * @param hidingSacReq
	 *            요청정보
	 * @param bindingResult
	 *            Validated Result
	 * @param requestHeader
	 *            헤더정보
	 * @return HidingListSacRes 응답정보
	 */
	@RequestMapping(value = "/history/hiding/update/v1", method = RequestMethod.POST)
	@ResponseBody
	public HidingListSacRes updateHiding(@RequestBody @Validated HidingSacReq hidingSacReq,
			BindingResult bindingResult, SacRequestHeader requestHeader) {

		TenantHeader header = requestHeader.getTenantHeader();
		// 필수값 체크
		this.purchaseCommonUtils.getBindingValid(bindingResult);

		HidingListSacRes response = new HidingListSacRes();
		response.setResponseList(this.resConvert(this.hidingSacService.updateHiding(this.reqConvert(hidingSacReq,
				header))));
		return response;
	}

	/**
	 * reqConvert.
	 * 
	 * @param hidingSacReq
	 *            요청정보
	 * @param header
	 *            테넌트 헤더정보
	 * @return hidingSacReq
	 */
	private HidingScReq reqConvert(HidingSacReq hidingSacReq, TenantHeader header) {
		this.logger.debug("@@@@@@ Hiding reqConvert @@@@@@@");
		HidingScReq req = new HidingScReq();
		List<HidingListSc> list = new ArrayList<HidingListSc>();

		req.setTenantId(header.getTenantId());
		req.setUserKey(hidingSacReq.getUserKey());
		req.setDeviceKey(hidingSacReq.getDeviceKey());
		req.setSystemId(header.getSystemId());

		for (HidingListSac hidingListSac : hidingSacReq.getHidingList()) {

			HidingListSc hidingListSc = new HidingListSc();

			hidingListSc.setPrchsId(hidingListSac.getPrchsId());
			hidingListSc.setPrchsDtlId(hidingListSac.getPrchsDtlId());
			hidingListSc.setSendYn(hidingListSac.getSendYn());
			hidingListSc.setHidingYn(hidingListSac.getHidingYn());

			list.add(hidingListSc);
		}
		req.setHidingList(list);

		return req;
	}

	/**
	 * resConvert.
	 * 
	 * @param hidingListScRes
	 *            요청정보
	 * @return List<HidingSacRes>
	 */
	private List<HidingSacRes> resConvert(List<HidingScRes> hidingListScRes) {
		this.logger.debug("@@@@@@ Hiding resConvert @@@@@@@");
		List<HidingSacRes> res = new ArrayList<HidingSacRes>();

		for (HidingScRes hidingScRes : hidingListScRes) {
			HidingSacRes hidingRes = new HidingSacRes();
			hidingRes.setPrchsId(hidingScRes.getPrchsId());
			hidingRes.setPrchsDtlId(hidingScRes.getPrchsDtlId());
			hidingRes.setResultYn(hidingScRes.getResultYn());
			res.add(hidingRes);
		}
		return res;
	}
}
