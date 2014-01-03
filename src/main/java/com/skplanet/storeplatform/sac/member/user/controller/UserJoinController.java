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

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.annotation.Headers;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 회원 가입 서비스 Controller
 * 
 * Updated on : 2014. 1. 2. Updated by : 심대진, 다모아 솔루션.
 */
@RequestMapping(value = "/member/user")
@Controller
public class UserJoinController {

	private static final Logger logger = LoggerFactory.getLogger(UserJoinController.class);

	/**
	 * <pre>
	 * 모바일 전용 회원 가입
	 * </pre>
	 */
	@RequestMapping(value = "/createByMdn/{ver}", method = RequestMethod.POST)
	@ResponseBody
	public String createByMdn(@PathVariable("ver") int ver, @Headers Map<String, Object> haders, @RequestBody Object object) {

		logger.info("==================================::{}", ver);

		return "";
	}
}
