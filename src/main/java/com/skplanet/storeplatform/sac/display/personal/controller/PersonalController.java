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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.sac.client.display.vo.personal.PersonalAutoUpgradeReq;
import com.skplanet.storeplatform.sac.client.display.vo.personal.PersonalAutoUpgradeRes;
import com.skplanet.storeplatform.sac.client.display.vo.personal.PersonalUpgradeProductReq;
import com.skplanet.storeplatform.sac.client.display.vo.personal.PersonalUpgradeProductRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.personal.service.PersonalAutoUpgradeService;
import com.skplanet.storeplatform.sac.display.personal.service.PersonalUpgradeProductService;

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
	private PersonalUpgradeProductService personalUpgradeProductService;

	@Autowired
	private PersonalAutoUpgradeService personalAutoUpgradeService;

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
	@RequestMapping(value = "/update/product/list/v1", method = RequestMethod.GET)
	@ResponseBody
	public PersonalUpgradeProductRes searchUpdateProductList(PersonalUpgradeProductReq req, SacRequestHeader header) {
		return this.personalUpgradeProductService.searchUpdateProductList(req, header);
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
	@RequestMapping(value = "upgrade/autoUpdate/status/list/v1", method = RequestMethod.GET)
	@ResponseBody
	public PersonalAutoUpgradeRes searchAutoUpgradeList(PersonalAutoUpgradeReq req, SacRequestHeader header) {
		return this.personalAutoUpgradeService.searchAutoUpgradeList(req, header);
	}

}
