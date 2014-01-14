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

import com.skplanet.storeplatform.sac.api.util.StringUtil;
import com.skplanet.storeplatform.sac.client.member.vo.common.HeaderVo;
import com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeByIdReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeByIdRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeByMdnReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeByMdnRes;
import com.skplanet.storeplatform.sac.member.common.HeaderInfo;
import com.skplanet.storeplatform.sac.member.user.service.LoginService;

/**
 * 로그인 Controller
 * 
 * Updated on : 2014. 1. 6. Updated by : 반범진, 지티소프트.
 */
@RequestMapping(value = "/dev/member/user")
@Controller
public class LoginController {

	private static final Logger logger = LoggerFactory.getLogger(UserJoinController.class);

	@Autowired
	private HeaderInfo headerInfo;

	@Autowired
	private LoginService loginService;

	/**
	 * 모바일 전용 회원 인증 (MDN 인증)
	 * 
	 * @param headers
	 * @param req
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/authorizeByMdn/v1", method = RequestMethod.POST)
	@ResponseBody
	public AuthorizeByMdnRes authorizeByMdn(@RequestHeader Map<String, Object> headers,	@RequestBody AuthorizeByMdnReq req) throws Exception {
		
		logger.info("######################## LoginController authorizeByMdn start ############################");
		
		/* 필수 파라메터 체크 */
		String deviceId = StringUtil.nvl(req.getDeviceId(), ""); // 기기ID(mdn,uuid)
		String deviceTelecom = StringUtil.nvl(req.getDeviceTelecom(), ""); // 이동통신사
		String deviceModelNo = StringUtil.nvl(req.getDeviceModelNo(), ""); // 기기모델번호

		if (deviceId.equals("")) {
			throw new Exception("필수요청 파라메터 부족(\"devicdId\")");
		} else if (deviceTelecom.equals("")) {
			throw new Exception("필수요청 파라메터 부족(\"deviceTelecom\")");
		} else if (deviceModelNo.equals("")) {
			throw new Exception("필수요청 파라메터 부족(\"deviceModelNo\")");
		}

		/* Header 정보 세팅 */
		HeaderVo headerVo = this.headerInfo.getHeader(headers);

		AuthorizeByMdnRes res = this.loginService.authorizeByMdn(headerVo, req);

		logger.info("######################## LoginController authorizeByMdn end ############################");
		return res;
	}

	/**
	 * ID 기반 회원 인증 (One ID, IDP 회원)
	 * 
	 * @param headers
	 * @param req
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/authorizeById/v1", method = RequestMethod.POST)
	@ResponseBody
	public AuthorizeByIdRes authorizeById(@RequestHeader Map<String, Object> headers, @RequestBody AuthorizeByIdReq req) throws Exception {

		/* 필수 파라메터 체크 */
		String deviceId = StringUtil.nvl(req.getDeviceId(), ""); // 기기ID(mdn,uuid)
		String userId = StringUtil.nvl(req.getUserId(), ""); // 사용자 아이디
		String userPw = StringUtil.nvl(req.getUserPw(), ""); // 사용자 패스워드

		if (deviceId.equals("")) {
			throw new Exception("필수요청 파라메터 부족(\"devicdId\")");
		} else if (userId.equals("")) {
			throw new Exception("필수요청 파라메터 부족(\"userId\")");
		} else if (userPw.equals("")) {
			throw new Exception("필수요청 파라메터 부족(\"userPw\")");
		}

		/* Header 정보 세팅 */
		HeaderVo headerVo = this.headerInfo.getHeader(headers);

		AuthorizeByIdRes res = this.loginService.authorizeById(headerVo, req);

		return res;
	}

}
