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

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateByAgreementReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateByAgreementRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateByMdnReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateByMdnRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateBySimpleReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateBySimpleRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.member.common.util.ConvertMapperUtils;
import com.skplanet.storeplatform.sac.member.user.service.UserJoinService;

/**
 * 회원 가입 서비스 Controller
 * 
 * Updated on : 2014. 1. 2. Updated by : 심대진, 다모아 솔루션.
 */
@Controller
public class UserJoinController {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserJoinController.class);

	@Autowired
	private UserJoinService svc;

	/**
	 * <pre>
	 * 모바일 전용 회원 가입 (MDN 회원 가입).
	 * </pre>
	 * 
	 * @param sacHeader
	 *            공통 헤더
	 * @param req
	 *            Request Value Object
	 * @return Response Value Object
	 */
	@RequestMapping(value = "/member/user/createByMdn/v1", method = RequestMethod.POST)
	@ResponseBody
	public CreateByMdnRes createByMdn(SacRequestHeader sacHeader, @Validated @RequestBody CreateByMdnReq req) {

		LOGGER.info("####################################################");
		LOGGER.info("##### 5.1.1. 모바일 전용 회원 가입 (MDN 회원 가입) #####");
		LOGGER.info("####################################################");

		LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(req));

		/**
		 * Header 정보
		 */
		LOGGER.info("Headers : {}", sacHeader.toString());

		if (StringUtils.equals(sacHeader.getDeviceHeader().getModel(), "")) {
			throw new StorePlatformException("SAC_MEM_0001", "model");
		}

		/**
		 * 모바일 전용회원 Biz
		 */
		CreateByMdnRes res = this.svc.createByMdn(sacHeader, req);

		LOGGER.info("Response : {}", res.toString());

		return res;

	}

	/**
	 * <pre>
	 * ID 회원 약관 동의 가입 (One ID 회원).
	 * </pre>
	 * 
	 * @param sacHeader
	 *            공통 헤더
	 * @param req
	 *            Request Value Object
	 * @return Response Value Object
	 */
	@RequestMapping(value = "/member/user/createByAgreement/v1", method = RequestMethod.POST)
	@ResponseBody
	public CreateByAgreementRes createByAgreement(SacRequestHeader sacHeader, @Validated @RequestBody CreateByAgreementReq req) {

		LOGGER.info("####################################################");
		LOGGER.info("##### 5.1.2. ID 회원 약관 동의 가입 (One ID 회원) #####");
		LOGGER.info("####################################################");

		LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(req));

		/**
		 * Header 정보
		 */
		LOGGER.info("Headers : {}", sacHeader.toString());

		CreateByAgreementRes res = null;
		if (!StringUtils.equals(req.getDeviceId(), "")) {

			/**
			 * [[ 단말정보 포함 ]] Biz
			 */
			if (StringUtils.equals(req.getDeviceId(), "")) {
				throw new StorePlatformException("SAC_MEM_0001", "deviceId");
			} else if (StringUtils.equals(req.getDeviceIdType(), "")) {
				throw new StorePlatformException("SAC_MEM_0001", "deviceType");
			} else if (StringUtils.equals(req.getDeviceTelecom(), "")) {
				throw new StorePlatformException("SAC_MEM_0001", "deviceTelecom");
			} else if (StringUtils.equals(sacHeader.getDeviceHeader().getModel(), "")) {
				throw new StorePlatformException("SAC_MEM_0001", "model");
			}

			LOGGER.info("## 단말정보 존재  ==========================================");
			res = this.svc.createByAgreementDevice(sacHeader, req);

		} else {

			/**
			 * [[ 단말정보 미포함 ]] Biz
			 */
			LOGGER.info("## 단말정보 미존재  ==========================================");
			res = this.svc.createByAgreementId(sacHeader, req);

		}

		LOGGER.info("Response : {}", res.toString());

		return res;

	}

	/**
	 * <pre>
	 * ID 회원 간편 가입 (IDP 회원).
	 * </pre>
	 * 
	 * @param sacHeader
	 *            공통 헤더
	 * @param req
	 *            Request Value Object
	 * @return Response Value Object
	 */
	@RequestMapping(value = "/member/user/createBySimple/v1", method = RequestMethod.POST)
	@ResponseBody
	public CreateBySimpleRes createBySimple(SacRequestHeader sacHeader, @Validated @RequestBody CreateBySimpleReq req) {

		LOGGER.info("#############################################");
		LOGGER.info("##### 5.1.3. ID 회원 간편 가입 (IDP 회원) #####");
		LOGGER.info("#############################################");

		LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(req));

		/**
		 * Header 정보
		 */
		LOGGER.info("Headers : {}", sacHeader.toString());

		CreateBySimpleRes res = null;
		if (!StringUtils.equals(req.getDeviceId(), "")) {

			/**
			 * [[ 단말정보 포함 ]] Biz
			 */
			if (StringUtils.equals(req.getDeviceId(), "")) {
				throw new StorePlatformException("SAC_MEM_0001", "deviceId");
			} else if (StringUtils.equals(req.getDeviceIdType(), "")) {
				throw new StorePlatformException("SAC_MEM_0001", "deviceType");
			} else if (StringUtils.equals(req.getDeviceTelecom(), "")) {
				throw new StorePlatformException("SAC_MEM_0001", "deviceTelecom");
			} else if (StringUtils.equals(sacHeader.getDeviceHeader().getModel(), "")) {
				throw new StorePlatformException("SAC_MEM_0001", "model");
			}

			LOGGER.info("## 단말정보 존재  ==========================================");
			res = this.svc.createBySimpleDevice(sacHeader, req);

		} else {

			/**
			 * [[ 단말정보 미포함 ]] Biz
			 */
			LOGGER.info("## 단말정보 미존재  ==========================================");
			res = this.svc.createBySimpleId(sacHeader, req);

		}

		LOGGER.info("Response : {}", res.toString());

		return res;

	}

	/**
	 * <pre>
	 * ID 회원 간편 가입 (IDP 회원) QA 테스트 페이지용.
	 * </pre>
	 * 
	 * @param userId
	 * @param userPw
	 * @param userEmail
	 * @param model
	 * @param sacHeader
	 *            void
	 */
	@RequestMapping(value = "/member/user/createBySimple/qa", method = RequestMethod.GET)
	@ResponseBody
	public CreateBySimpleRes createBySimpleQaTestPage(SacRequestHeader sacHeader, @Validated @ModelAttribute CreateBySimpleReq req) {

		LOGGER.info("#####################################################");
		LOGGER.info("##### QA 테스트 페이지 용 회원 간편 가입 (IDP 회원) #####");
		LOGGER.info("#####################################################");

		/**
		 * Header 정보
		 */
		LOGGER.info("Headers : {}", sacHeader.toString());

		/**
		 * Request
		 */
		LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(req));

		CreateBySimpleRes res = new CreateBySimpleRes();
		res = this.svc.createBySimpleId(sacHeader, req);

		LOGGER.info("Response : {}", res.toString());

		return res;

	}

}
