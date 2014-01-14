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

import org.codehaus.jackson.map.ObjectMapper;
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
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateByAgreementReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateByAgreementRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateByMdnReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateByMdnRes;
import com.skplanet.storeplatform.sac.member.common.HeaderInfo;
import com.skplanet.storeplatform.sac.member.user.service.UserJoinService;

/**
 * 회원 가입 서비스 Controller
 * 
 * Updated on : 2014. 1. 2. Updated by : 심대진, 다모아 솔루션.
 */
@RequestMapping(value = "/dev/member/user")
@Controller
public class UserJoinController {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserJoinController.class);

	@Autowired
	private UserJoinService svc;

	@Autowired
	private HeaderInfo headerInfo;

	/**
	 * Data Binding
	 */
	ObjectMapper objMapper = new ObjectMapper();

	/**
	 * <pre>
	 * 모바일 전용 회원 가입 (MDN 회원 가입).
	 * </pre>
	 * 
	 * @param req
	 *            CreateByMdnReq
	 * @param headers
	 *            Request Headers
	 * @return CreateByMdnRes
	 * @throws Exception
	 *             Exception
	 */
	@RequestMapping(value = "/createByMdn/v1", method = RequestMethod.POST)
	@ResponseBody
	public CreateByMdnRes createByMdn(@Valid @RequestBody CreateByMdnReq req, @RequestHeader Map<String, Object> headers) throws Exception {
		LOGGER.info("####################################################");
		LOGGER.info("##### 5.1.1. 모바일 전용 회원 가입 (MDN 회원 가입) #####");
		LOGGER.info("####################################################");

		LOGGER.info("Request : {}", this.objMapper.writeValueAsString(req));

		/**
		 * Header 정보 세팅
		 */
		HeaderVo headerVo = this.headerInfo.getHeader(headers);

		/**
		 * 모바일 전용회원 Biz
		 */
		CreateByMdnRes res = this.svc.createByMdn(headerVo, req);

		LOGGER.info("Response : {}", res.toString());

		return res;
	}

	/**
	 * <pre>
	 * ID 회원 약관 동의 가입 (One ID 회원).
	 * </pre>
	 * 
	 * @param req
	 *            CreateByAgreementReq
	 * @param headers
	 *            Request Headers
	 * @return CreateByAgreementRes
	 * @throws Exception
	 *             Exception
	 */
	@RequestMapping(value = "/createByAgreement/v1", method = RequestMethod.POST)
	@ResponseBody
	public CreateByAgreementRes createByAgreement(@Valid @RequestBody CreateByAgreementReq req, @RequestHeader Map<String, Object> headers) throws Exception {

		LOGGER.info("####################################################");
		LOGGER.info("##### 5.1.2. ID 회원 약관 동의 가입 (One ID 회원) #####");
		LOGGER.info("####################################################");

		LOGGER.info("Request : {}", this.objMapper.writeValueAsString(req));

		/**
		 * Header 정보 세팅
		 */
		HeaderVo headerVo = this.headerInfo.getHeader(headers);

		/**
		 * 모바일 전용회원 Biz
		 */
		CreateByAgreementRes res = this.svc.createByAgreement(headerVo, req);

		LOGGER.info("Response : {}", res.toString());

		return res;
	}

}
