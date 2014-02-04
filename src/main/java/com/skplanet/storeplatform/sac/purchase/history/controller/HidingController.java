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
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.purchase.client.history.vo.HidingListSc;
import com.skplanet.storeplatform.purchase.client.history.vo.HidingScReq;
import com.skplanet.storeplatform.purchase.client.history.vo.HidingScRes;
import com.skplanet.storeplatform.sac.client.purchase.vo.history.HidingListSacRes;
import com.skplanet.storeplatform.sac.client.purchase.vo.history.HidingSacReq;
import com.skplanet.storeplatform.sac.client.purchase.vo.history.HidingSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
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
		if (bindingResult.hasErrors()) {
			List<FieldError> errors = bindingResult.getFieldErrors();
			for (FieldError error : errors) {
				if (error.getCode().equals("Min")) {
					throw new StorePlatformException("SAC_PUR_0005", error.getField(), error.getRejectedValue(), "1");
				} else {
					throw new StorePlatformException("SAC_PUR_0001", error.getField());
				}
			}
		}

		HidingScReq req = this.reqConvert(hidingSacReq, header);
		List<HidingScRes> hidingScRes = new ArrayList<HidingScRes>();
		List<HidingSacRes> hidingRes = new ArrayList<HidingSacRes>();

		hidingScRes = this.hidingSacService.updateHiding(req);
		HidingListSacRes hidingListSacRes = new HidingListSacRes();
		hidingRes = this.resConvert(hidingScRes);
		hidingListSacRes.setHidingSacRes(hidingRes);
		return hidingListSacRes;
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
		HidingScReq req = new HidingScReq();
		List<HidingListSc> list = new ArrayList<HidingListSc>();

		req.setTenantId(header.getTenantId());
		req.setInsdUsermbrNo(hidingSacReq.getInsdUsermbrNo());
		req.setInsdDeviceId(hidingSacReq.getInsdDeviceId());
		this.logger.debug("@@@@@@header.getSystemId()@@@@@@@" + header.getSystemId());
		req.setSystemId(header.getSystemId());
		int size = hidingSacReq.getHidingListSac().size();
		this.logger.debug("@@@@@@reqConvert@@@@@@@" + size);
		for (int i = 0; i < size; i++) {

			HidingListSc hidingListSc = new HidingListSc();

			hidingListSc.setPrchsId(hidingSacReq.getHidingListSac().get(i).getPrchsId());
			hidingListSc.setPrchsDtlId(hidingSacReq.getHidingListSac().get(i).getPrchsDtlId());
			hidingListSc.setHidingYn(hidingSacReq.getHidingListSac().get(i).getHidingYn());

			list.add(hidingListSc);
		}
		req.setHidingListSc(list);

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
