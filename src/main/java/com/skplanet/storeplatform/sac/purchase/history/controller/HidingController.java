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

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.purchase.client.history.vo.HidingRequest;
import com.skplanet.storeplatform.purchase.client.history.vo.HidingResponse;
import com.skplanet.storeplatform.purchase.client.history.vo.HidingScList;
import com.skplanet.storeplatform.sac.client.purchase.vo.history.HidingListRes;
import com.skplanet.storeplatform.sac.client.purchase.vo.history.HidingReq;
import com.skplanet.storeplatform.sac.client.purchase.vo.history.HidingRes;
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
	 * @param hidingReq
	 *            요청정보
	 * @param requestHeader
	 *            헤더정보
	 * @return List<HidingRes> 응답정보
	 */
	@RequestMapping(value = "/history/hiding/modify/v1", method = RequestMethod.POST)
	@ResponseBody
	public HidingListRes modifyHiding(@RequestBody HidingReq hidingReq, SacRequestHeader requestHeader) {

		TenantHeader header = requestHeader.getTenantHeader();
		// 필수값 체크
		if (header.getTenantId() == null || header.getTenantId() == "") {
			throw new StorePlatformException("SAC_PUR_0001", "TenantId");
		}
		if (header.getSystemId() == null || header.getSystemId() == "") {
			throw new StorePlatformException("SAC_PUR_0001", "TenantId");
		}
		if (hidingReq.getInsdUsermbrNo() == null || hidingReq.getInsdUsermbrNo() == "") {
			throw new StorePlatformException("SAC_PUR_0001", "InsdUsermbrNo");
		}
		if (hidingReq.getInsdDeviceId() == null || hidingReq.getInsdDeviceId() == "") {
			throw new StorePlatformException("SAC_PUR_0001", "insdDeviceId");
		}
		// for (HidingSacList req : hidingReq.getHidingSacList()) {
		// if (req.getPrchsId() == null || req.getPrchsId() == "") {
		// throw new StorePlatformException("SAC_PUR_0001", "prchsId");
		// }
		// if (req.getPrchsDtlId() == null || req.getPrchsDtlId() < 0) {
		// throw new StorePlatformException("SAC_PUR_0001", "prchsDtlId");
		// }
		// if (req.getHidingYn() == null || req.getHidingYn() == "") {
		// throw new StorePlatformException("SAC_PUR_0001", "hidingYn");
		// }
		// }
		HidingRequest req = this.reqConvert(hidingReq, header);
		List<HidingResponse> hidingResponse = new ArrayList<HidingResponse>();
		List<HidingRes> hidingRes = new ArrayList<HidingRes>();

		hidingResponse = this.hidingSacService.modifyHiding(req);
		HidingListRes hidingListRes = new HidingListRes();
		hidingRes = this.resConvert(hidingResponse);
		hidingListRes.setHidingRes(hidingRes);
		return hidingListRes;
	}

	/**
	 * reqConvert.
	 * 
	 * @param hidingReq
	 *            요청정보
	 * @param header
	 *            테넌트 헤더정보
	 * @return HidingRequest
	 */
	private HidingRequest reqConvert(HidingReq hidingReq, TenantHeader header) {
		HidingRequest req = new HidingRequest();
		List<HidingScList> list = new ArrayList<HidingScList>();

		req.setTenantId(header.getTenantId());
		req.setInsdUsermbrNo(hidingReq.getInsdUsermbrNo());
		req.setInsdDeviceId(hidingReq.getInsdDeviceId());
		this.logger.debug("@@@@@@header.getSystemId()@@@@@@@" + header.getSystemId());
		req.setSystemId(header.getSystemId());
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
	 *            요청정보
	 * @param header
	 *            테넌트 헤더정보
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
