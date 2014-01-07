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

import javax.validation.Valid;

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
import com.skplanet.storeplatform.sac.client.member.vo.user.ExistReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.ExistRes;
import com.skplanet.storeplatform.sac.member.user.common.HeaderInfo;
import com.skplanet.storeplatform.sac.member.user.common.ParameterExceptionHandling;
import com.skplanet.storeplatform.sac.member.user.service.UserSelectService;

/**
 * 회원 조회 서비스 Controller
 * 
 * Updated on : 2014. 1. 7. Updated by : 강신완, 부르칸.
 */
@RequestMapping(value = "/member/user")
@Controller
public class UserSelectController extends ParameterExceptionHandling {

	private static final Logger logger = LoggerFactory.getLogger(UserSelectController.class);

	@Autowired
	private UserSelectService svc;

	@Autowired
	private HeaderInfo headerInfo;

	@RequestMapping(value = "/exist/v1", method = RequestMethod.POST)
	@ResponseBody
	public ExistRes exist(@Valid @RequestBody ExistReq req, @RequestHeader Map<String, Object> headers) {
		logger.info("####################################################");
		logger.info("##### 5.1.6. 회원 가입 여부 조회 (ID/MDN 기반) #####");
		logger.info("####################################################");

		/**
		 * Header 정보 세팅
		 */
		HeaderVo headerVo = this.headerInfo.getHeader(headers);

		/**
		 * 파라미터 유효성 체크 todo
		 */

		/**
		 * 회원기본정보 조회 Biz
		 */
		ExistRes res = this.svc.exist(headerVo, req);

		logger.info("Response : {}", res.toString());

		return res;
	}

}
