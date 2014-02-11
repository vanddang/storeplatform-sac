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

import com.skplanet.storeplatform.sac.client.member.vo.user.CreateRealNameReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateRealNameRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.ModifyEmailReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.ModifyEmailRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.ModifyPasswordReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.ModifyPasswordRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.ModifyReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.ModifyRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.member.common.util.ConvertMapperUtils;
import com.skplanet.storeplatform.sac.member.user.service.UserModifyService;

/**
 * 회원 수정 서비스 Controller
 * 
 * Updated on : 2014. 1. 24. Updated by : 심대진, 다모아 솔루션.
 */
@Controller
public class UserModifyController {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserModifyController.class);

	@Autowired
	private UserModifyService svc;

	/**
	 * <pre>
	 * 회원 정보 수정.
	 * </pre>
	 * 
	 * @param sacHeader
	 *            공통 헤더 정보
	 * @param req
	 *            Request Value Object
	 * @return Response Value Object
	 */
	@RequestMapping(value = "/member/user/modify/v1", method = RequestMethod.POST)
	@ResponseBody
	public ModifyRes modify(SacRequestHeader sacHeader, @Validated @RequestBody ModifyReq req) {

		LOGGER.info("#################################");
		LOGGER.info("##### 2.1.13. 회원 정보 수정 #####");
		LOGGER.info("#################################");

		LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(req));

		/**
		 * Header 정보
		 */
		LOGGER.info("Headers : {}", sacHeader.toString());

		/**
		 * 회원 정보 수정 Biz
		 */
		ModifyRes res = this.svc.modify(sacHeader, req);

		LOGGER.info("Response : {}", res.toString());

		return res;

	}

	/**
	 * <pre>
	 * 비밀번호 수정.
	 * </pre>
	 * 
	 * @param sacHeader
	 *            공통 헤더 정보
	 * @param req
	 *            Request Value Object
	 * @return Response Value Object
	 */
	@RequestMapping(value = "/dev/member/user/modifyPassword/v1", method = RequestMethod.POST)
	@ResponseBody
	public ModifyPasswordRes modifyPassword(SacRequestHeader sacHeader, @Validated @RequestBody ModifyPasswordReq req) {

		LOGGER.info("################################");
		LOGGER.info("##### 2.1.14. 비밀번호 수정 #####");
		LOGGER.info("################################");

		LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(req));

		/**
		 * Header 정보
		 */
		LOGGER.info("Headers : {}", sacHeader.toString());

		/**
		 * 비밀번호 수정 Biz
		 */
		ModifyPasswordRes res = this.svc.modifyPassword(sacHeader, req);

		LOGGER.info("Response : {}", res.toString());

		return res;

	}

	/**
	 * <pre>
	 * 이메일 주소 수정.
	 * </pre>
	 * 
	 * @param sacHeader
	 *            공통 헤더 정보
	 * @param req
	 *            Request Value Object
	 * @return Response Value Object
	 */
	@RequestMapping(value = "/dev/member/user/modifyEmail/v1", method = RequestMethod.POST)
	@ResponseBody
	public ModifyEmailRes modifyEmail(SacRequestHeader sacHeader, @Validated @RequestBody ModifyEmailReq req) {

		LOGGER.info("##################################");
		LOGGER.info("##### 2.1.15. 이메일 주소 수정 #####");
		LOGGER.info("##################################");

		LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(req));

		/**
		 * Header 정보
		 */
		LOGGER.info("Headers : {}", sacHeader.toString());

		/**
		 * 이메일 주소 Biz
		 */
		ModifyEmailRes res = this.svc.modifyEmail(sacHeader, req);

		LOGGER.info("Response : {}", res.toString());

		return res;

	}

	/**
	 * <pre>
	 * 실명 인증 정보 등록.
	 * </pre>
	 * 
	 * @param sacHeader
	 *            공통 헤더
	 * @param req
	 *            Request Value Object
	 * @return Response Value Object
	 */
	@RequestMapping(value = "/member/user/createRealName/v1", method = RequestMethod.POST)
	@ResponseBody
	public CreateRealNameRes createRealName(SacRequestHeader sacHeader, @Validated @RequestBody CreateRealNameReq req) {

		LOGGER.info("####################################");
		LOGGER.info("##### 2.1.18 실명 인증 정보 등록 #####");
		LOGGER.info("####################################");

		LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(req));

		/**
		 * Header 정보
		 */
		LOGGER.info("Headers : {}", sacHeader.toString());

		/**
		 * 이메일 주소 Biz
		 */
		CreateRealNameRes res = this.svc.createRealName(sacHeader, req);

		LOGGER.info("Response : {}", res.toString());

		return res;

	}

}
