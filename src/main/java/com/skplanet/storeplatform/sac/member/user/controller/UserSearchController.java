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

import com.skplanet.storeplatform.sac.client.member.vo.user.ExistReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.ExistRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.member.common.HeaderInfo;
import com.skplanet.storeplatform.sac.member.common.ParameterExceptionHandling;
import com.skplanet.storeplatform.sac.member.common.idp.service.IDPService;
import com.skplanet.storeplatform.sac.member.user.service.UserSearchService;

/**
 * 회원 조회 서비스 Controller
 * 
 * Updated on : 2014. 1. 7. Updated by : 강신완, 부르칸.
 */
@RequestMapping(value = "/member/user")
@Controller
public class UserSearchController extends ParameterExceptionHandling {

	private static final Logger logger = LoggerFactory.getLogger(UserSearchController.class);

	@Autowired
	private UserSearchService svc;

	@Autowired
	private HeaderInfo headerInfo;

	@Autowired
	private IDPService idpService;

	@RequestMapping(value = "/exist/v1", method = RequestMethod.POST)
	@ResponseBody
	public ExistRes exist(@RequestBody ExistReq req, SacRequestHeader sacHeader) throws Exception {
		logger.info("####################################################");
		logger.info("##### 5.1.6. 회원 가입 여부 조회 (ID/MDN 기반) #####");
		logger.info("####################################################");

		ExistRes res = new ExistRes();

		/**
		 * 회원기본정보 조회 Biz
		 */
		int paramCnt = 0;
		if (!"".equals(req.getUserKey()) && req.getUserKey() != null) {
			paramCnt += 1;
		} else if (!"".equals(req.getUserId()) && req.getUserId() != null) {
			paramCnt += 1;
		} else if (!"".equals(req.getDeviceKey()) && req.getDeviceKey() != null) {
			paramCnt += 1;
		} else if (!"".equals(req.getDeviceId()) && req.getDeviceId() != null) {
			paramCnt += 1;
			// throw new RuntimeException("입력된 [ deviceId ] 파라미터가 없습니다.");
		}

		logger.info("###### ExistReq : {}", req.toString());

		if (paramCnt > 0) {
			res = this.svc.exist(sacHeader, req);
		} else {
			throw new RuntimeException("입력된 파라미터가 없습니다.");
		}
		logger.info("Final Response : {}", res.toString());

		return res;
	}

}
