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
import com.skplanet.storeplatform.sac.member.user.service.UserExtraInfoService;

/**
 * 회원 부가 정보 등록/수정/삭제/조회 서비스 Controller
 * 
 * Updated on : 2014. 1. 20. Updated by : 강신완, 부르칸.
 */
@RequestMapping(value = "/member/user")
@Controller
public class UserExtraInfoController {

	private static final Logger logger = LoggerFactory.getLogger(UserExtraInfoController.class);

	@Autowired
	private UserExtraInfoService userExtraService;

	@RequestMapping(value = "/modifyAdditionalInformation/v1", method = RequestMethod.POST)
	@ResponseBody
	public UserExtraInfoRes modifyAdditionalInformation(@RequestBody UserExtraInfoReq req, SacRequestHeader sacHeader) {
		logger.debug("####################################################");
		logger.debug("##### 5.1.25. 회원 부가 정보 등록/수정 #####");
		logger.debug("####################################################");

		UserExtraInfoRes res = new UserExtraInfoRes();

		String userKey = StringUtil.nvl(req.getUserKey(), "");
		String extraProfileCode = "";
		String extraProfilValue = "";

		logger.debug("###### 회원부가정보 등록/수정 Req Object : {}", req.getUserKey());
		logger.debug("###### 회원부가정보 등록/수정 Req List : {}", req.getAddInfoList().toString());

		for (UserExtraInfo infoReq : req.getAddInfoList()) {
			extraProfileCode = StringUtil.nvl(infoReq.getExtraProfileCode(), "");
			extraProfilValue = StringUtil.nvl(infoReq.getExtraProfileValue(), "");

			if (extraProfileCode.equals("") || extraProfilValue.equals("")) {
				throw new StorePlatformException("SAC_MEM_0001", infoReq.toString());
			}
		}

		if (userKey.equals("")) {
			throw new StorePlatformException("SAC_MEM_0001", req.getUserKey());
		} else if (req.getAddInfoList() == null) {
			throw new StorePlatformException("SAC_MEM_0001" + req.getAddInfoList().toString());
		}

		res = this.userExtraService.modifyAdditionalInformation(req, sacHeader);

		return res;
	}

	@RequestMapping(value = "/removeAdditionalInformation/v1", method = RequestMethod.POST)
	@ResponseBody
	public UserExtraInfoRes removeAdditionalInformation(@RequestBody UserExtraInfoReq req, SacRequestHeader sacHeader) {
		logger.debug("####################################################");
		logger.debug("##### 5.1.26. 회원 부가 정보 삭제 #####");
		logger.debug("####################################################");

		UserExtraInfoRes res = new UserExtraInfoRes();

		String userKey = StringUtil.nvl(req.getUserKey(), "");
		String extraProfileCode = "";

		logger.debug("###### removeAdditionalInformation Req Object : {}", req.getUserKey());
		logger.debug("###### removeAdditionalInformation Req List : {}", req.getAddInfoList().toString());

		for (UserExtraInfo infoReq : req.getAddInfoList()) {
			extraProfileCode = StringUtil.nvl(infoReq.getExtraProfileCode(), "");

			if (extraProfileCode.equals("")) {
				throw new StorePlatformException("SAC_MEM_0001", extraProfileCode);
			}
		}

		if (userKey.equals("")) {
			throw new StorePlatformException("SAC_MEM_0001", req.getUserKey());
		} else if (req.getAddInfoList() == null) {
			throw new StorePlatformException("SAC_MEM_0001", req.getAddInfoList().toString());
		}

		res = this.userExtraService.removeAdditionalInformation(req, sacHeader);

		return res;
	}

	@RequestMapping(value = "/listAdditionalInformation/v1", method = RequestMethod.GET)
	@ResponseBody
	public UserExtraInfoRes listAdditionalInformation(UserExtraInfoReq req, SacRequestHeader sacHeader) {
		logger.debug("####################################################");
		logger.debug("##### 5.1.27. 회원 부가 정보 조회 #####");
		logger.debug("####################################################");

		UserExtraInfoRes res = new UserExtraInfoRes();

		String userKey = StringUtil.nvl(req.getUserKey(), "");

		if (userKey.equals("")) {
			throw new StorePlatformException("SAC_MEM_0001", req.getUserKey());
		}

		res = this.userExtraService.listAdditionalInformation(req, sacHeader);

		return res;
	}
}
