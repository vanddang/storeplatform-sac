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

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.framework.core.util.StringUtil;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateByAgreementReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateByAgreementRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateByMdnReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateByMdnRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.member.common.HeaderInfo;
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

	@Autowired
	private HeaderInfo headerInfo;

	/**
	 * Data Binding.
	 */
	ObjectMapper objMapper = new ObjectMapper();

	/**
	 * <pre>
	 * 모바일 전용 회원 가입 (MDN 회원 가입).
	 * </pre>
	 * 
	 * @param req
	 *            CreateByMdnReq
	 * @param sacHeader
	 *            SacRequestHeader
	 * @return CreateByMdnRes
	 * @throws Exception
	 *             Exception
	 */
	@RequestMapping(value = "/member/user/createByMdn/v1", method = RequestMethod.POST)
	@ResponseBody
	public CreateByMdnRes createByMdn(@Valid @RequestBody CreateByMdnReq req, SacRequestHeader sacHeader) throws Exception {

		LOGGER.info("####################################################");
		LOGGER.info("##### 5.1.1. 모바일 전용 회원 가입 (MDN 회원 가입) #####");
		LOGGER.info("####################################################");

		LOGGER.info("Request : {}", this.objMapper.writeValueAsString(req));

		/**
		 * Header 정보 세팅
		 */
		LOGGER.info("Headers : {}", sacHeader.toString());
		if (StringUtil.equals(sacHeader.getDeviceHeader().getModel(), "")) {
			throw new RuntimeException("[model] 미존재.");
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
	 * @param req
	 *            CreateByAgreementReq
	 * @param sacHeader
	 *            SacRequestHeader
	 * @return CreateByAgreementRes
	 * @throws Exception
	 *             Exception
	 */
	@RequestMapping(value = "/dev/member/user/createByAgreement/v1", method = RequestMethod.POST)
	@ResponseBody
	public CreateByAgreementRes createByAgreement(@Valid @RequestBody CreateByAgreementReq req, SacRequestHeader sacHeader) throws Exception {

		LOGGER.info("####################################################");
		LOGGER.info("##### 5.1.2. ID 회원 약관 동의 가입 (One ID 회원) #####");
		LOGGER.info("####################################################");

		LOGGER.info("Request : {}", this.objMapper.writeValueAsString(req));

		/**
		 * Header 정보 세팅
		 */
		LOGGER.info("Headers : {}", sacHeader.toString());

		CreateByAgreementRes res = new CreateByAgreementRes();

		if (!StringUtil.equals(req.getDeviceId(), "")) {

			/**
			 * ID 회원 약관 동의 가입 (One ID 회원) [[ 단말정보 포함 ]] Biz
			 */
			if (StringUtil.equals(req.getDeviceId(), "")) {
				throw new RuntimeException("[deviceId] 미존재.");
			} else if (StringUtil.equals(req.getDeviceIdType(), "")) {
				throw new RuntimeException("[deviceIdType] 미존재.");
			} else if (StringUtil.equals(req.getDeviceTelecom(), "")) {
				throw new RuntimeException("[deviceTelecom] 미존재.");
			} else if (StringUtils.equals(sacHeader.getDeviceHeader().getModel(), "")) {
				throw new RuntimeException("[model] 미존재.");
			}

			LOGGER.info("## 단말정보 존재  ==========================================");
			res = this.svc.createByAgreementDevice(sacHeader, req);

		} else {

			/**
			 * ID 회원 약관 동의 가입 (One ID 회원) [[ 단말정보 미포함 ]] Biz
			 */
			LOGGER.info("## 단말정보 미존재  ==========================================");
			res = this.svc.createByAgreementId(sacHeader, req);

		}

		LOGGER.info("Response : {}", res.toString());

		return res;

	}

}
