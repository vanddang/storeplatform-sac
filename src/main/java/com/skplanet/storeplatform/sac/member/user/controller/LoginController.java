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

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeByIdReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeByIdRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeByMdnReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeByMdnRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeSaveAndSyncByMacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeSaveAndSyncByMacRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeSimpleByMdnReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeSimpleByMdnRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.CheckVariabilityReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.CheckVariabilityRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.member.user.service.LoginService;

/**
 * 로그인 Controller.
 * 
 * Updated on : 2014. 1. 6. Updated by : 반범진, 지티소프트.
 */
@Controller
public class LoginController {

	private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private LoginService loginService;

	/**
	 * 모바일 전용 회원 인증 (MDN 인증).
	 * 
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param req
	 *            AuthorizeByMdnReq
	 * @return AuthorizeByMdnRes
	 */
	@RequestMapping(value = "/member/user/authorizeByMdn/v1", method = RequestMethod.POST)
	@ResponseBody
	public AuthorizeByMdnRes authorizeByMdn(SacRequestHeader requestHeader, @Valid @RequestBody AuthorizeByMdnReq req) {

		LOGGER.info(":::::::::::::::::::: authorizeByMdn v1 start ::::::::::::::::::::");
		LOGGER.info(req.toString());

		AuthorizeByMdnRes res = this.loginService.executeAuthorizeByMdn(requestHeader, req);

		LOGGER.info(":::::::::::::::::::: authorizeByMdn v1 end ::::::::::::::::::::");
		return res;
	}

	/**
	 * 모바일 전용 회원 인증 v2 (MDN 인증, 변동성 포함).
	 * 
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param req
	 *            AuthorizeByMdnReq
	 * @return AuthorizeByMdnRes
	 */
	@RequestMapping(value = "/member/user/authorizeByMdn/v2", method = RequestMethod.POST)
	@ResponseBody
	public AuthorizeByMdnRes authorizeByMdnV2(SacRequestHeader requestHeader, @Valid @RequestBody AuthorizeByMdnReq req) {

		LOGGER.info(":::::::::::::::::::: authorizeByMdn v2 start ::::::::::::::::::::");
		LOGGER.info(req.toString());

		AuthorizeByMdnRes res = this.loginService.executeAuthorizeByMdnV2(requestHeader, req);

		LOGGER.info(":::::::::::::::::::: authorizeByMdn v2 end ::::::::::::::::::::");

		return res;

	}

	/**
	 * 변동성 회원 체크.
	 * 
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param req
	 *            CheckVariabilityReq
	 * @return CheckVariabilityRes
	 */
	@RequestMapping(value = "/member/user/checkVariability/v1", method = RequestMethod.POST)
	@ResponseBody
	public CheckVariabilityRes checkVariability(SacRequestHeader requestHeader, @Valid @RequestBody CheckVariabilityReq req) {

		LOGGER.info(":::::::::::::::::::: checkVariability v1 start ::::::::::::::::::::");
		LOGGER.info(req.toString());

		CheckVariabilityRes res = this.loginService.executCheckVariability(requestHeader, req);

		LOGGER.info(":::::::::::::::::::: checkVariability v1 start ::::::::::::::::::::");

		return res;

	}

	/**
	 * ID 기반 회원 인증 (One ID, IDP 회원).
	 * 
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param req
	 *            AuthorizeByIdReq
	 * @return AuthorizeByIdRes
	 */
	@RequestMapping(value = "/member/user/authorizeById/v1", method = RequestMethod.POST)
	@ResponseBody
	public AuthorizeByIdRes authorizeById(SacRequestHeader requestHeader, @Valid @RequestBody AuthorizeByIdReq req) {

		LOGGER.info(":::::::::::::::::::: authorizeById v1 start ::::::::::::::::::::");
		LOGGER.info(req.toString());

		AuthorizeByIdRes res = this.loginService.executeAuthorizeById(requestHeader, req);

		LOGGER.info(":::::::::::::::::::: authorizeById v1 end ::::::::::::::::::::");

		return res;

	}

	/**
	 * 심플 인증(간편인증).
	 * 
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param req
	 *            AuthorizeByIdReq
	 * @return AuthorizeByIdRes
	 */
	@RequestMapping(value = "/member/user/authorizeSimpleByMdn/v1", method = RequestMethod.POST)
	@ResponseBody
	public AuthorizeSimpleByMdnRes authorizeSimpleByMdn(SacRequestHeader requestHeader, @Valid @RequestBody AuthorizeSimpleByMdnReq req) {

		LOGGER.info(":::::::::::::::::::: authorizeSimpleByMdn v1 start ::::::::::::::::::::");
		LOGGER.info(req.toString());

		AuthorizeSimpleByMdnRes res = this.loginService.executeAuthorizeSimpleByMdn(requestHeader, req);

		LOGGER.info(":::::::::::::::::::: authorizeSimpleByMdn v1 end ::::::::::::::::::::");

		return res;

	}

	/**
	 * <pre>
	 * Save&Sync 인증.
	 * </pre>
	 * 
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param req
	 *            AuthorizeSaveAndSyncByMacReq
	 * @return AuthorizeSaveAndSyncByMacRes
	 */
	@RequestMapping(value = "/member/user/authorizeSaveAndSyncByMac/v1", method = RequestMethod.POST)
	@ResponseBody
	public AuthorizeSaveAndSyncByMacRes authorizeSaveAndSyncByMac(SacRequestHeader requestHeader, @Valid @RequestBody AuthorizeSaveAndSyncByMacReq req) {

		LOGGER.info(":::::::::::::::::::: authorizeSaveAndSyncByMac v1 start ::::::::::::::::::::");
		LOGGER.info(req.toString());

		AuthorizeSaveAndSyncByMacRes res = this.loginService.executeAuthorizeSaveAndSyncByMac(requestHeader, req);

		LOGGER.info(":::::::::::::::::::: authorizeSaveAndSyncByMac v1 end ::::::::::::::::::::");

		return res;

	}
}
