/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.member.user.dummy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.sac.client.member.vo.user.CreateByAgreementRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateByMdnRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateBySimpleRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateRealNameRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateTermsAgreementRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.ModifyEmailRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.ModifyPasswordRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.ModifyRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.ModifyTermsAgreementRes;
import com.skplanet.storeplatform.sac.member.user.common.HeaderInfo;
import com.skplanet.storeplatform.sac.member.user.controller.UserJoinController;

/**
 * 회원 가입 서비스 Controller
 * 
 * Updated on : 2014. 1. 2. Updated by : 심대진, 다모아 솔루션.
 */
@RequestMapping(value = "/member/user/dummy")
@Controller
public class DummyDataController {

	private static final Logger logger = LoggerFactory.getLogger(UserJoinController.class);

	@Autowired
	private HeaderInfo headerInfo;

	@RequestMapping(value = "/createByMdn/v1", method = RequestMethod.POST)
	@ResponseBody
	public CreateByMdnRes createByMdn() {

		logger.info("####################################################");
		logger.info("##### 5.1.1. 모바일 전용 회원 가입 (MDN 회원 가입) #####");
		logger.info("####################################################");

		CreateByMdnRes res = new CreateByMdnRes();
		res.setUserKey("IW102158844420091030165015");

		return res;
	}

	@RequestMapping(value = "/createByAgreement/v1", method = RequestMethod.POST)
	@ResponseBody
	public CreateByAgreementRes createByAgreement() {

		logger.info("####################################################");
		logger.info("##### 5.1.2. ID 회원 약관 동의 가입 (One ID 회원) #####");
		logger.info("####################################################");

		CreateByAgreementRes res = new CreateByAgreementRes();
		res.setUserKey("IW102158844420091030165015");

		return res;
	}

	@RequestMapping(value = "/createBySimple/v1", method = RequestMethod.POST)
	@ResponseBody
	public CreateBySimpleRes createBySimple() {

		logger.info("#############################################");
		logger.info("##### 5.1.3. ID 회원 간편 가입 (IDP 회원) #####");
		logger.info("#############################################");

		CreateBySimpleRes res = new CreateBySimpleRes();
		res.setUserKey("IW102158844420091030165015");

		return res;
	}

	@RequestMapping(value = "/modify/v1", method = RequestMethod.POST)
	@ResponseBody
	public ModifyRes modify() {

		logger.info("#################################");
		logger.info("##### 5.1.13. 회원 정보 수정 #####");
		logger.info("#################################");

		ModifyRes res = new ModifyRes();
		res.setUserKey("IW102158844420091030165015");

		return res;
	}

	@RequestMapping(value = "/modifyPassword/v1", method = RequestMethod.POST)
	@ResponseBody
	public ModifyPasswordRes modifyPassword() {

		logger.info("################################");
		logger.info("##### 5.1.14. 비밀번호 수정 #####");
		logger.info("################################");

		ModifyPasswordRes res = new ModifyPasswordRes();
		res.setUserKey("IW102158844420091030165015");

		return res;
	}

	@RequestMapping(value = "/modifyEmail/v1", method = RequestMethod.POST)
	@ResponseBody
	public ModifyEmailRes modifyEmail() {

		logger.info("##################################");
		logger.info("##### 5.1.15. 이메일 주소 수정 #####");
		logger.info("##################################");

		ModifyEmailRes res = new ModifyEmailRes();
		res.setUserKey("IW102158844420091030165015");

		return res;
	}

	@RequestMapping(value = "/createTermsAgreement/v1", method = RequestMethod.POST)
	@ResponseBody
	public CreateTermsAgreementRes createTermsAgreement() {

		logger.info("######################################");
		logger.info("##### 5.1.16. Store 약관 동의 등록 #####");
		logger.info("######################################");

		CreateTermsAgreementRes res = new CreateTermsAgreementRes();
		res.setUserKey("IW102158844420091030165015");

		return res;
	}

	@RequestMapping(value = "/modifyTermsAgreement/v1", method = RequestMethod.POST)
	@ResponseBody
	public ModifyTermsAgreementRes modifyTermsAgreement() {

		logger.info("######################################");
		logger.info("##### 5.1.17. Store 약관 동의 수정 #####");
		logger.info("######################################");

		ModifyTermsAgreementRes res = new ModifyTermsAgreementRes();
		res.setUserKey("IW102158844420091030165015");

		return res;
	}

	@RequestMapping(value = "/createRealName/v1", method = RequestMethod.POST)
	@ResponseBody
	public CreateRealNameRes createRealName() {

		logger.info("#####################################");
		logger.info("##### 5.1.18. 실명 인증 정보 등록 #####");
		logger.info("#####################################");

		CreateRealNameRes res = new CreateRealNameRes();
		res.setUserKey("IW102158844420091030165015");

		return res;
	}
}
