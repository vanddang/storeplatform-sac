package com.skplanet.storeplatform.sac.member.user.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeByIdReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeByIdRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeByMdnReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeByMdnRes;
import com.skplanet.storeplatform.sac.member.user.service.LoginService;

/**
 * 로그인 Controller
 * 
 * Updated on : 2014. 1. 6. Updated by : 반범진, 지티소프트.
 */
@RequestMapping(value = "/member/user")
@Controller
public class LoginController {

	private static final Logger logger = LoggerFactory
			.getLogger(UserJoinController.class);

	@Autowired
	private LoginService loginService;

	/* @RequestMapping(value = "/authorizeByMdn/v1", method = RequestMethod.GET) */
	@ResponseBody
	public AuthorizeByMdnRes authorizeByMdn(AuthorizeByMdnReq req) {

		AuthorizeByMdnRes res = this.loginService.authorizeByMdn(req);

		return res;
	}

	/* @RequestMapping(value = "/authorizeById/v1", method = RequestMethod.POST) */
	@ResponseBody
	public AuthorizeByIdRes authorizeById(AuthorizeByIdReq req) {

		AuthorizeByIdRes res = this.loginService.authorizeById(req);

		return res;
	}
}
