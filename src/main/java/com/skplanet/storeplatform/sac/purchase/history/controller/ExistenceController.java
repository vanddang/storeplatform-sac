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

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.purchase.client.history.vo.ExistenceList;
import com.skplanet.storeplatform.purchase.client.history.vo.ExistenceRequest;
import com.skplanet.storeplatform.purchase.client.history.vo.ExistenceResponse;
import com.skplanet.storeplatform.sac.client.purchase.vo.history.ExistenceListRes;
import com.skplanet.storeplatform.sac.client.purchase.vo.history.ExistenceReq;
import com.skplanet.storeplatform.sac.client.purchase.vo.history.ExistenceRes;
import com.skplanet.storeplatform.sac.purchase.history.service.ExistenceService;

/**
 * 구매 SAC 컨트롤러
 * 
 * Updated on : 2014-01-14 Updated by : 조용진, 엔텔스.
 */
@Controller
@RequestMapping(value = "/purchase")
public class ExistenceController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ExistenceService existenceService;

	/**
	 * 기구매 체크 SAC.
	 * 
	 * @param existenceReq
	 *            기구매 체크 SAC
	 * @return List<ExistenceRes>
	 */
	@RequestMapping(value = "/history/existence/list/v1", method = RequestMethod.POST)
	@ResponseBody
	public ExistenceListRes getExist(@RequestBody ExistenceReq existenceReq) {

		// ExistenceListRes res = new ExistenceListRes();
		List<ExistenceRes> res = new ArrayList<ExistenceRes>();
		ExistenceListRes existenceListRes = new ExistenceListRes();
		// 필수값 체크
		if (StringUtils.isBlank(existenceReq.getTenantId())) {
			this.logger.debug("@@@@@@getTenantId@@@@@@@{}", existenceReq.getTenantId());
			return existenceListRes;
		}
		if (StringUtils.isBlank(existenceReq.getInsdUsermbrNo())) {
			this.logger.debug("@@@@@@getInsdUsermbrNo@@@@@@@{}", existenceReq.getInsdUsermbrNo());
			return existenceListRes;
		}
		ExistenceRequest req = this.reqConvert(existenceReq);
		List<ExistenceResponse> existenceResponse = new ArrayList<ExistenceResponse>();
		existenceResponse = this.existenceService.getExist(req);

		res = this.resConvert(existenceResponse);
		this.logger.debug("@@@@@@@@@@@@@{} ", res.size());

		existenceListRes.setExistenceListRes(res);
		return existenceListRes;
	}

	/**
	 * reqConvert.
	 * 
	 * @param existenceReq
	 *            reqConvert
	 * @return ExistenceRequest
	 */
	private ExistenceRequest reqConvert(ExistenceReq existenceReq) {

		ExistenceRequest req = new ExistenceRequest();
		List<ExistenceList> list = new ArrayList<ExistenceList>();

		req.setTenantId(existenceReq.getTenantId());
		req.setInsdUsermbrNo(existenceReq.getInsdUsermbrNo());
		req.setInsdDeviceId(existenceReq.getInsdDeviceId());
		// req.setPrchsId(existenceReq.getPrchsId());
		int size = existenceReq.getExistenceList().size();
		this.logger.debug("@@@@@@reqConvert@@@@@@@{} ", size);
		for (int i = 0; i < size; i++) {
			ExistenceList existenceList = new ExistenceList();
			existenceList.setProdId(existenceReq.getExistenceList().get(i).getProdId());
			existenceList.setTenantProdGrpCd(existenceReq.getExistenceList().get(i).getTenantProdGrpCd());
			list.add(existenceList);
		}
		req.setExistenceList(list);

		return req;
	}

	/**
	 * resConvert.
	 * 
	 * @param existenceResponseList
	 *            resConvert
	 * @return List<ExistenceRes>
	 */
	private List<ExistenceRes> resConvert(List<ExistenceResponse> existenceResponseList) {
		List<ExistenceRes> res = new ArrayList<ExistenceRes>();
		int size = existenceResponseList.size();
		this.logger.debug("@@@@@@resConvert@@@@@@@{} ", size);
		for (int i = 0; i < size; i++) {
			ExistenceRes existenceRes = new ExistenceRes();
			existenceRes.setPrchsId(existenceResponseList.get(i).getPrchsId());
			existenceRes.setProdId(existenceResponseList.get(i).getProdId());

			res.add(existenceRes);
		}

		return res;
	}

}
