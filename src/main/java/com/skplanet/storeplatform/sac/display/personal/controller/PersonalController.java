/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.personal.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.sac.client.display.vo.personal.PersonalAutoUpdateReq;
import com.skplanet.storeplatform.sac.client.display.vo.personal.PersonalAutoUpdateRes;
import com.skplanet.storeplatform.sac.client.display.vo.personal.PersonalUpdateProductReq;
import com.skplanet.storeplatform.sac.client.display.vo.personal.PersonalUpdateProductRes;
import com.skplanet.storeplatform.sac.client.display.vo.personal.RecommandNewMemberProductReq;
import com.skplanet.storeplatform.sac.client.display.vo.personal.RecommandNewMemberProductRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.personal.service.PersonalAutoUpdateService;
import com.skplanet.storeplatform.sac.display.personal.service.PersonalUpdateAlarmService;
import com.skplanet.storeplatform.sac.display.personal.service.PersonalUpdateProductService;
import com.skplanet.storeplatform.sac.display.personal.service.RecommandNewMemberProductService;

/**
 * 개인화 관련 Controller
 * 
 * Updated on : 2014. 2. 10. Updated by : 오승민, 인크로스
 */
@Controller
@RequestMapping("/display/personal")
public class PersonalController {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private PersonalUpdateProductService personalUpdateProductService;

	@Autowired
	private PersonalAutoUpdateService personalAutoUpdateService;

	@Autowired
	private PersonalUpdateAlarmService personalUpdateAlarmService;

	@Autowired
	private RecommandNewMemberProductService recommandNewMemberProductService;

	/**
	 * <pre>
	 * 업데이트 대상 목록 조회.
	 * </pre>
	 * 
	 * @param req
	 *            req
	 * @param header
	 *            header
	 * @return PersonalUpgradeProductRes
	 */
	@RequestMapping(value = "/update/product/list/v1", method = RequestMethod.POST)
	@ResponseBody
	public PersonalUpdateProductRes searchUpdateProductList(@Validated @RequestBody PersonalUpdateProductReq req,
			SacRequestHeader header) {
		return this.personalUpdateProductService.searchUpdateProductList(req, header);
	}

	/**
	 * <pre>
	 * 자동 Update 목록 조회.
	 * </pre>
	 * 
	 * @param req
	 *            req
	 * @param header
	 *            header
	 * @return DeviceProductProvisioningRes
	 */
	@RequestMapping(value = "/update/autoUpdate/status/list/v1", method = RequestMethod.POST)
	@ResponseBody
	public PersonalAutoUpdateRes updateAutoUpdateList(@Validated @RequestBody PersonalAutoUpdateReq req,
			SacRequestHeader header) {
		return this.personalAutoUpdateService.updateAutoUpdateList(req, header);
	}

	/**
	 * <pre>
	 * 신규 가입 고객 추천 상품 조회.
	 * </pre>
	 * 
	 * @param req
	 *            req
	 * @param header
	 *            header
	 * @return DeviceProductProvisioningRes
	 */
	@RequestMapping(value = "/recommandNewMember/product/List/v1", method = RequestMethod.GET)
	@ResponseBody
	public RecommandNewMemberProductRes recommandNewMemberProductList(SacRequestHeader header,
			RecommandNewMemberProductReq req) {
		return this.recommandNewMemberProductService.recommandNewMemberProductList(header, req);
	}

}
