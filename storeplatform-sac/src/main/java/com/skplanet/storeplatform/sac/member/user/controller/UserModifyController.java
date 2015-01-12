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

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateRealNameReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateRealNameRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateTermsAgreementReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateTermsAgreementRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.ModifyEmailReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.ModifyEmailRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.ModifyPasswordReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.ModifyPasswordRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.ModifyReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.ModifyRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.ModifyTermsAgreementReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.ModifyTermsAgreementRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.member.common.constant.MemberConstants;
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

		LOGGER.debug("#################################");
		LOGGER.debug("##### 2.1.13. 회원 정보 수정 #####");
		LOGGER.debug("#################################");

		LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(req));

		/**
		 * 회원 정보 수정 Biz
		 */
		ModifyRes res = this.svc.mod(sacHeader, req);

		LOGGER.info("Response : {}", ConvertMapperUtils.convertObjectToJson(res));

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
	@RequestMapping(value = "/member/user/modifyPassword/v1", method = RequestMethod.POST)
	@ResponseBody
	public ModifyPasswordRes modifyPassword(SacRequestHeader sacHeader, @Validated @RequestBody ModifyPasswordReq req) {

		LOGGER.debug("################################");
		LOGGER.debug("##### 2.1.14. 비밀번호 수정 #####");
		LOGGER.debug("################################");

		LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(req));

		/**
		 * 비밀번호 수정 Biz
		 */
		ModifyPasswordRes res = this.svc.modPassword(sacHeader, req);

		LOGGER.info("Response : {}", ConvertMapperUtils.convertObjectToJson(res));

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
	@RequestMapping(value = "/member/user/modifyEmail/v1", method = RequestMethod.POST)
	@ResponseBody
	public ModifyEmailRes modifyEmail(SacRequestHeader sacHeader, @Validated @RequestBody ModifyEmailReq req) {

		LOGGER.debug("##################################");
		LOGGER.debug("##### 2.1.15. 이메일 주소 수정 #####");
		LOGGER.debug("##################################");

		LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(req));

		/**
		 * 이메일 주소 Biz
		 */
		ModifyEmailRes res = this.svc.modEmail(sacHeader, req);

		LOGGER.info("Response : {}", ConvertMapperUtils.convertObjectToJson(res));

		return res;

	}

	/**
	 * <pre>
	 * Store 약관 동의 등록.
	 * </pre>
	 * 
	 * @param sacHeader
	 *            공통 헤더
	 * @param req
	 *            Request Value Object
	 * @return Response Value Object
	 */
	@RequestMapping(value = "/member/user/createTermsAgreement/v1", method = RequestMethod.POST)
	@ResponseBody
	public CreateTermsAgreementRes createTermsAgreement(SacRequestHeader sacHeader,
			@Validated @RequestBody CreateTermsAgreementReq req) {

		LOGGER.debug("#####################################");
		LOGGER.debug("##### 2.1.16 Store 약관 동의 등록 #####");
		LOGGER.debug("#####################################");

		LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(req));

		/**
		 * Store 약관 동의 등록 Biz
		 */
		CreateTermsAgreementRes res = this.svc.regTermsAgreement(sacHeader, req);

		LOGGER.info("Response : {}", ConvertMapperUtils.convertObjectToJson(res));

		return res;

	}

	/**
	 * <pre>
	 * Store 약관 동의 수정.
	 * </pre>
	 * 
	 * @param sacHeader
	 *            공통 헤더
	 * @param req
	 *            Request Value Object
	 * @return Response Value Object
	 */
	@RequestMapping(value = "/member/user/modifyTermsAgreement/v1", method = RequestMethod.POST)
	@ResponseBody
	public ModifyTermsAgreementRes modifyTermsAgreement(SacRequestHeader sacHeader,
			@Validated @RequestBody ModifyTermsAgreementReq req) {

		LOGGER.debug("#####################################");
		LOGGER.debug("##### 2.1.17 Store 약관 동의 수정 #####");
		LOGGER.debug("#####################################");

		LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(req));

		/**
		 * Store 약관 동의 수정 Biz
		 */
		ModifyTermsAgreementRes res = this.svc.modTermsAgreement(sacHeader, req);

		LOGGER.info("Response : {}", ConvertMapperUtils.convertObjectToJson(res));

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

		LOGGER.debug("####################################");
		LOGGER.debug("##### 2.1.18 실명 인증 정보 등록 #####");
		LOGGER.debug("####################################");

		LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(req));

		if (StringUtils.equals(req.getIsOwn(), MemberConstants.AUTH_TYPE_OWN)
				|| StringUtils.equals(req.getIsOwn(), MemberConstants.AUTH_TYPE_PARENT)) { // 본인 or 법정대리인

			/**
			 * 필수 파라미터 체크 (userCi).
			 */
			if (StringUtils.equals(req.getUserCi(), "")) {
				throw new StorePlatformException("SAC_MEM_0001", "userCi");
			} else if (StringUtils.equals(req.getUserName(), "")) {
				throw new StorePlatformException("SAC_MEM_0001", "userName");
			}

		}

		/**
		 * 실명인증 등록/수정 Biz
		 */
		CreateRealNameRes res = this.svc.regRealName(sacHeader, req);

		LOGGER.info("Response : {}", ConvertMapperUtils.convertObjectToJson(res));

		return res;

	}
}
