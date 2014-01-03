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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.sac.client.member.vo.common.HeaderVo;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateByMdnReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateByMdnRes;
import com.skplanet.storeplatform.sac.member.user.common.HeaderInfo;
import com.skplanet.storeplatform.sac.member.user.service.UserJoinService;

/**
 * 회원 가입 서비스 Controller
 * 
 * Updated on : 2014. 1. 2. Updated by : 심대진, 다모아 솔루션.
 */
@RequestMapping(value = "/member/user")
@Controller
public class UserJoinController {

	private static final Logger logger = LoggerFactory.getLogger(UserJoinController.class);

	@Autowired
	private UserJoinService svc;

	@Autowired
	private HeaderInfo headerInfo;

	/**
	 * <pre>
	 * 모바일 전용 회원 가입
	 * </pre>
	 */
	@RequestMapping(value = "/createByMdn/v1", method = RequestMethod.POST)
	@ResponseBody
	public CreateByMdnRes createByMdn(@RequestBody CreateByMdnReq req, @RequestHeader Map<String, Object> headers) {

		logger.info("####################################################");
		logger.info("##### 5.1.1. 모바일 전용 회원 가입 (MDN 회원 가입) #####");
		logger.info("####################################################");

		logger.info("Request : {}", req.toString());

		/**
		 * TODO 필수 파라미터 체크
		 */

		/**
		 * Header 정보 세팅
		 */
		HeaderVo headerVo = this.headerInfo.getHeader(headers);

		/**
		 * 모바일 전용회원 Biz
		 */
		CreateByMdnRes res = this.svc.createByMdn(headerVo, req);

		logger.info("Response : {}", res.toString());

		return res;
	}
}
