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
		this.logger.debug("@@@@@@reqConvert@@@@@@@");
		HidingScReq req = new HidingScReq();
		List<HidingListSc> list = new ArrayList<HidingListSc>();

		req.setTenantId(header.getTenantId());
		req.setUserKey(hidingSacReq.getUserKey());
		req.setDeviceKey(hidingSacReq.getDeviceKey());
		req.setSystemId(header.getSystemId());
		int size = hidingSacReq.getHidingList().size();
		for (int i = 0; i < size; i++) {

			HidingListSc hidingListSc = new HidingListSc();

			hidingListSc.setPrchsId(hidingSacReq.getHidingList().get(i).getPrchsId());
			hidingListSc.setPrchsDtlId(hidingSacReq.getHidingList().get(i).getPrchsDtlId());
			hidingListSc.setHidingYn(hidingSacReq.getHidingList().get(i).getHidingYn());

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
		List<HidingSacRes> res = new ArrayList<HidingSacRes>();
		int size = hidingListScRes.size();
		this.logger.debug("@@@@@@resConvert@@@@@@@" + size);
		for (int i = 0; i < size; i++) {
			HidingSacRes hidingRes = new HidingSacRes();
			hidingRes.setPrchsId(hidingListScRes.get(i).getPrchsId());
			hidingRes.setPrchsDtlId(hidingListScRes.get(i).getPrchsDtlId());
			hidingRes.setResultYn(hidingListScRes.get(i).getResultYn());
			res.add(hidingRes);
		}

		return res;
	}
}
