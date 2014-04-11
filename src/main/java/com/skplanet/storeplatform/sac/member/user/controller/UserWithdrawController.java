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

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.sac.client.member.vo.user.WithdrawReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.WithdrawRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.member.user.service.UserWithdrawService;

/**
 * 회원 탈퇴 서비스 Controller
 * 
 * Updated on : 2014. 1. 7. Updated by : 강신완, 부르칸.
 */
@RequestMapping(value = "/member/user")
@Controller
public class UserWithdrawController {

	private static final Logger logger = LoggerFactory.getLogger(UserWithdrawController.class);

	ObjectMapper objMapper = new ObjectMapper();

	@Autowired
	private UserWithdrawService svc;

	@RequestMapping(value = "/withdraw/v1", method = RequestMethod.POST)
	@ResponseBody
	public WithdrawRes exist(@RequestBody WithdrawReq req, SacRequestHeader sacHeader) {
		logger.debug("####################################################");
		logger.debug("##### 5.1.24. 회원 탈퇴 (ID/MDN 기반) #####");
		logger.debug("####################################################");

		logger.info("");
		logger.info("");
		logger.info("###### 회원탈퇴 Start Request : {}", req.toString());
		logger.info("");
		logger.info("");

		WithdrawRes res = this.svc.executeWithdraw(sacHeader, req);

		logger.info("");
		logger.info("");
		logger.info("####### 회원탈퇴 Final Response : {}", res.toString());
		logger.info("");
		logger.info("");

		return res;
	}

}
