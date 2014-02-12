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

import com.skplanet.storeplatform.sac.client.member.vo.user.CreateOcbInformationReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateOcbInformationRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.GetOcbInformationReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.GetOcbInformationRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.RemoveOcbInformationReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.RemoveOcbInformationRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.member.common.util.ConvertMapperUtils;
import com.skplanet.storeplatform.sac.member.user.service.UserOcbService;

/**
 * 회원 OCB 정보 Controller
 * 
 * Updated on : 2014. 2. 12. Updated by : 심대진, 다모아 솔루션.
 */
@Controller
public class UserOcbController {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserOcbController.class);

	@Autowired
	private UserOcbService svc;

	/**
	 * <pre>
	 * 회원 OCB 정보 등록/수정.
	 * </pre>
	 * 
	 * @param sacHeader
	 *            공통 헤더
	 * @param req
	 *            Request Value Object
	 * @return Response Value Object
	 */
	@RequestMapping(value = "/member/user/createOcbInformation/v1", method = RequestMethod.POST)
	@ResponseBody
	public CreateOcbInformationRes createOcbInformation(SacRequestHeader sacHeader, @Validated @RequestBody CreateOcbInformationReq req) {

		LOGGER.info("########################################");
		LOGGER.info("##### 2.1.29 회원 OCB 정보 등록/수정 #####");
		LOGGER.info("########################################");

		LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(req));

		/**
		 * Header 정보
		 */
		LOGGER.info("Headers : {}", sacHeader.toString());

		/**
		 * 회원 OCB 정보 등록/수정 Biz
		 */
		CreateOcbInformationRes res = this.svc.createOcbInformation(sacHeader, req);

		LOGGER.info("Response : {}", res.toString());

		return res;

	}

	/**
	 * <pre>
	 * 회원 OCB 정보 삭제.
	 * </pre>
	 * 
	 * @param sacHeader
	 *            공통 헤더
	 * @param req
	 *            Request Value Object
	 * @return Response Value Object
	 */
	@RequestMapping(value = "/member/user/removeOcbInformation/v1", method = RequestMethod.POST)
	@ResponseBody
	public RemoveOcbInformationRes removeOcbInformation(SacRequestHeader sacHeader, @Validated @RequestBody RemoveOcbInformationReq req) {

		LOGGER.info("###################################");
		LOGGER.info("##### 2.1.29 회원 OCB 정보 삭제 #####");
		LOGGER.info("###################################");

		LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(req));

		/**
		 * Header 정보
		 */
		LOGGER.info("Headers : {}", sacHeader.toString());

		/**
		 * 회원 OCB 정보 삭제 Biz
		 */
		RemoveOcbInformationRes res = this.svc.removeOcbInformation(sacHeader, req);

		LOGGER.info("Response : {}", res.toString());

		return res;

	}

	/**
	 * <pre>
	 * 회원 OCB 정보 조회.
	 * </pre>
	 * 
	 * @param sacHeader
	 *            공통 헤더
	 * @param req
	 *            Request Value Object
	 * @return Response Value Object
	 */
	@RequestMapping(value = "/member/user/getOcbInformation/v1", method = RequestMethod.GET)
	@ResponseBody
	public GetOcbInformationRes getOcbInformation(SacRequestHeader sacHeader, @Validated GetOcbInformationReq req) {

		LOGGER.info("###################################");
		LOGGER.info("##### 2.1.29 회원 OCB 정보 조회 #####");
		LOGGER.info("###################################");

		LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(req));

		/**
		 * Header 정보
		 */
		LOGGER.info("Headers : {}", sacHeader.toString());

		/**
		 * 회원 OCB 정보 조회 Biz
		 */
		GetOcbInformationRes res = this.svc.getOcbInformation(sacHeader, req);

		LOGGER.info("Response : {}", res.toString());

		return res;

	}

}
