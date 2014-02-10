/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.member.user.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.sac.client.member.vo.user.LockAccountSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.LockAccountSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.member.common.util.ConvertMapperUtils;
import com.skplanet.storeplatform.sac.member.user.service.UserLockService;

/**
 * 회원 계정 잠금 Controller
 * 
 * Updated on : 2014. 2. 10. Updated by : 심대진, 다모아 솔루션.
 */
@Controller
public class UserLockController {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserLockController.class);

	@Autowired
	private UserLockService svc;

	/**
	 * <pre>
	 * 회원 계정 잠금.
	 * </pre>
	 * 
	 * @param sacHeader
	 *            공통 헤더
	 * @param req
	 *            Request Value Object
	 * @return Response Value Object
	 */
	@RequestMapping(value = "/dev/member/user/lockAccount/v1", method = RequestMethod.POST)
	@ResponseBody
	public LockAccountSacRes lockAccount(SacRequestHeader sacHeader, @Validated @RequestBody LockAccountSacReq req) {

		LOGGER.info("################################");
		LOGGER.info("##### 2.1.36. 회원 계정 잠금 #####");
		LOGGER.info("################################");

		LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(req));

		/**
		 * Header 정보
		 */
		LOGGER.info("Headers : {}", sacHeader.toString());

		LockAccountSacRes res = new LockAccountSacRes();

		/**
		 * 회원 계정 잠금 Biz
		 */
		res = this.svc.lockAccount(sacHeader, req);

		LOGGER.info("Response : {}", res.toString());

		return res;

	}

}
