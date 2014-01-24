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
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.sac.client.member.vo.user.ModifyEmailReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.ModifyEmailRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.ModifyPasswordReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.ModifyPasswordRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.ModifyReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.ModifyRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
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
	 * Data Binding.
	 */
	ObjectMapper objMapper = new ObjectMapper();

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
	 * @throws Exception
	 *             익셉션
	 */
	@RequestMapping(value = "/dev/member/user/modify/v1", method = RequestMethod.POST)
	@ResponseBody
	public ModifyRes modify(SacRequestHeader sacHeader, @Validated @RequestBody ModifyReq req, BindingResult result) throws Exception {

		LOGGER.info("#################################");
		LOGGER.info("##### 5.1.13. 회원 정보 수정 #####");
		LOGGER.info("#################################");

		LOGGER.info("Request : {}", this.objMapper.writeValueAsString(req));

		/**
		 * BindException 처리
		 */
		if (result.hasErrors()) {
			LOGGER.info("## Request Parameter Binding Exception!!! {}", result.getFieldError());
			throw new RuntimeException("Request Parameter Binding Exception!!!");
		}

		/**
		 * Header 정보
		 */
		LOGGER.info("Headers : {}", sacHeader.toString());

		/**
		 * 회원 정보 수정 Biz
		 */
		ModifyRes res = this.svc.modify(sacHeader, req);

		LOGGER.info("Response : {}", res.toString());

		return new ModifyRes();

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
	 * @throws Exception
	 *             익셉션
	 */
	@RequestMapping(value = "/dev/member/user/modifyPassword/v1", method = RequestMethod.POST)
	@ResponseBody
	public ModifyPasswordRes modifyPassword(SacRequestHeader sacHeader, @Validated @RequestBody ModifyPasswordReq req, BindingResult result) throws Exception {

		LOGGER.info("################################");
		LOGGER.info("##### 5.1.14. 비밀번호 수정 #####");
		LOGGER.info("################################");

		LOGGER.info("Request : {}", this.objMapper.writeValueAsString(req));

		/**
		 * BindException 처리
		 */
		if (result.hasErrors()) {
			LOGGER.info("## Request Parameter Binding Exception!!! {}", result.getFieldError());
			throw new RuntimeException("Request Parameter Binding Exception!!!");
		}

		/**
		 * Header 정보
		 */
		LOGGER.info("Headers : {}", sacHeader.toString());

		/**
		 * 비밀번호 수정 Biz
		 */
		ModifyPasswordRes res = this.svc.modifyPassword(sacHeader, req);

		LOGGER.info("Response : {}", res.toString());

		return new ModifyPasswordRes();

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
	 * @throws Exception
	 *             익셉션
	 */
	@RequestMapping(value = "/dev/member/user/modifyEmail/v1", method = RequestMethod.POST)
	@ResponseBody
	public ModifyEmailRes modifyEmail(SacRequestHeader sacHeader, @Validated @RequestBody ModifyEmailReq req, BindingResult result) throws Exception {

		LOGGER.info("##################################");
		LOGGER.info("##### 5.1.15. 이메일 주소 수정 #####");
		LOGGER.info("##################################");

		LOGGER.info("Request : {}", this.objMapper.writeValueAsString(req));

		/**
		 * BindException 처리
		 */
		if (result.hasErrors()) {
			LOGGER.info("## Request Parameter Binding Exception!!! {}", result.getFieldError());
			throw new RuntimeException("Request Parameter Binding Exception!!!");
		}

		/**
		 * Header 정보
		 */
		LOGGER.info("Headers : {}", sacHeader.toString());

		/**
		 * 이메일 주소 Biz
		 */
		ModifyEmailRes res = this.svc.modifyEmail(sacHeader, req);

		LOGGER.info("Response : {}", res.toString());

		return new ModifyEmailRes();

	}

}
