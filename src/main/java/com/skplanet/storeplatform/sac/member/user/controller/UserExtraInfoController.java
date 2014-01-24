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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.sac.api.util.StringUtil;
import com.skplanet.storeplatform.sac.client.member.vo.common.UserExtraInfo;
import com.skplanet.storeplatform.sac.client.member.vo.user.UserExtraInfoReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.UserExtraInfoRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.member.common.ParameterExceptionHandling;

/**
 * 회원 부가 정보 등록/수정/삭제/조회 서비스 Controller
 * 
 * Updated on : 2014. 1. 20. Updated by : 강신완, 부르칸.
 */
@RequestMapping(value = "/dev/member/user")
@Controller
public class UserExtraInfoController extends ParameterExceptionHandling {

	private static final Logger logger = LoggerFactory.getLogger(UserExtraInfoController.class);

	// @Autowired
	// private UserExtraInfoService svc;

	@RequestMapping(value = "/modifyAdditionalInformation/v1", method = RequestMethod.POST)
	@ResponseBody
	public UserExtraInfoRes modifyAdditionalInformation(@RequestBody UserExtraInfoReq req, SacRequestHeader sacHeader)
			throws Exception {
		logger.info("####################################################");
		logger.info("##### 5.1.25. 회원 부가 정보 등록/수정 #####");
		logger.info("####################################################");

		UserExtraInfoRes res = new UserExtraInfoRes();

		String userKey = StringUtil.nvl(req.getUserKey(), "");
		String extraProfileCode = "";
		String extraProfilValue = "";

		for (UserExtraInfo infoReq : req.getAddInfoList()) {
			extraProfileCode = StringUtil.nvl(infoReq.getExtraProfileCode(), "");
			extraProfilValue = StringUtil.nvl(infoReq.getExtraProfileValue(), "");

			if (extraProfileCode.equals("") && extraProfilValue.equals("")) {
				throw new RuntimeException("회원 부가 정보 extraProfileCode, extraProfilValue is Null" + "extraProfileCode ["
						+ extraProfileCode + "]" + " [" + extraProfilValue + "]");
			}
		}

		if (userKey.equals("")) {
			throw new Exception("필수요청 파라메터 부족 : userKey" + req.getUserKey());
		} else if (req.getAddInfoList() == null) {
			throw new Exception("필수요청 파라메터 부족 : addInfoList" + req.getAddInfoList().toString());
		}

		// res = this.svc.modifyAdditionalInformation(sacHeader, req);

		return res;
	}
}
