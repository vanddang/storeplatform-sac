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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.sac.api.util.StringUtil;
import com.skplanet.storeplatform.sac.client.member.vo.common.UserExtraInfo;
import com.skplanet.storeplatform.sac.client.member.vo.user.UserExtraInfoReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.UserExtraInfoRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.member.common.util.ConvertMapperUtils;
import com.skplanet.storeplatform.sac.member.user.service.UserExtraInfoService;

/**
 * 회원 부가 정보 등록/수정/삭제/조회 서비스 Controller
 * 
 * Updated on : 2014. 1. 20. Updated by : 강신완, 부르칸.
 */
@RequestMapping(value = "/member/user")
@Controller
public class UserExtraInfoController {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserExtraInfoController.class);

	@Autowired
	private UserExtraInfoService userExtraService;

	@RequestMapping(value = "/modifyAdditionalInformation/v1", method = RequestMethod.POST)
	@ResponseBody
	public UserExtraInfoRes modifyAdditionalInformation(@RequestBody UserExtraInfoReq req, SacRequestHeader sacHeader) {

		LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(req));

		String userKey = StringUtil.nvl(req.getUserKey(), "");
		String extraProfile = "";
		String extraProfilValue = "";

		if (userKey.equals("")) {
			throw new StorePlatformException("SAC_MEM_0001", "userKey");
		}

		for (UserExtraInfo infoReq : req.getUserExtraInfoList()) {
			extraProfile = StringUtil.nvl(infoReq.getExtraProfile(), "");
			extraProfilValue = StringUtil.nvl(infoReq.getExtraProfileValue(), "");

			if (extraProfile.equals("") || extraProfilValue.equals("")) {
				throw new StorePlatformException("SAC_MEM_0001", "extraProfile, extraProfileValue");
			}

		}

		UserExtraInfoRes res = this.userExtraService.modifyAdditionalInformation(req, sacHeader);

		LOGGER.info("Response : {}", ConvertMapperUtils.convertObjectToJson(res.getUserKey()));

		return res;
	}

	@RequestMapping(value = "/removeAdditionalInformation/v1", method = RequestMethod.POST)
	@ResponseBody
	public UserExtraInfoRes removeAdditionalInformation(@RequestBody UserExtraInfoReq req, SacRequestHeader sacHeader) {

		String userKey = StringUtil.nvl(req.getUserKey(), "");
		String extraProfile = "";

		if (userKey.equals("")) {
			throw new StorePlatformException("SAC_MEM_0001", "userKey");
		}

		for (UserExtraInfo infoReq : req.getUserExtraInfoList()) {
			extraProfile = StringUtil.nvl(infoReq.getExtraProfile(), "");

			if (extraProfile.equals("")) {
				throw new StorePlatformException("SAC_MEM_0001", "extraProfile");
			}
		}

		LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(req));

		UserExtraInfoRes res = this.userExtraService.removeAdditionalInformation(req, sacHeader);

		LOGGER.info("Response : {}", ConvertMapperUtils.convertObjectToJson(res.getUserKey()));

		return res;
	}

	@RequestMapping(value = "/listAdditionalInformation/v1", method = RequestMethod.POST)
	@ResponseBody
	public UserExtraInfoRes listAdditionalInformation(@RequestBody UserExtraInfoReq req, SacRequestHeader sacHeader) {

		String userKey = StringUtil.nvl(req.getUserKey(), "");

		if (userKey.equals("")) {
			throw new StorePlatformException("SAC_MEM_0001", "userKey");
		}

		LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(req));

		UserExtraInfoRes res = this.userExtraService.listAdditionalInformation(req, sacHeader);

		LOGGER.info("Response : {}", ConvertMapperUtils.convertObjectToJson(res.getUserKey()));

		return res;
	}
}
