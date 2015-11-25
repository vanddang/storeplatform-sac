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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.external.client.idp.vo.CommonRes;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.sac.api.util.StringUtil;
import com.skplanet.storeplatform.sac.client.member.vo.common.UserExtraInfo;
import com.skplanet.storeplatform.sac.client.member.vo.user.CheckAdditionalInformationSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.CheckAdditionalInformationSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.MoveUserInfoSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.MoveUserInfoSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.UserExtraInfoReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.UserExtraInfoRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.member.common.constant.MemberConstants;
import com.skplanet.storeplatform.sac.member.common.util.ConvertMapperUtils;
import com.skplanet.storeplatform.sac.member.user.service.UserExtraInfoService;
import com.skplanet.storeplatform.sac.member.user.service.UserService;

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

	@Autowired
	private UserService userService;

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
			if (StringUtils.equals(MemberConstants.USER_EXTRA_SOCIAL_ACCT_TYPE, infoReq.getExtraProfile())
					|| StringUtils.equals(MemberConstants.USER_EXTRA_SOCIAL_ACCT_ID, infoReq.getExtraProfile())
					|| StringUtils.equals(MemberConstants.USER_EXTRA_SOCIAL_ACCT_INT_ID, infoReq.getExtraProfile())
					|| StringUtils.equals(MemberConstants.USER_EXTRA_SOCIAL_EMAIL, infoReq.getExtraProfile())
					|| StringUtils.equals(MemberConstants.USER_EXTRA_SOCIAL_ACCT_TOKEN, infoReq.getExtraProfile())
					|| StringUtils.equals(MemberConstants.USER_EXTRA_SOCIAL_REF_TOKEN, infoReq.getExtraProfile())
					|| StringUtils.equals(MemberConstants.USER_EXTRA_SOCIAL_EXPIRED_TIME, infoReq.getExtraProfile())) {
				throw new StorePlatformException("SAC_MEM_1403");
			}

		}

		UserExtraInfoRes res = this.userExtraService.modAdditionalInformation(req, sacHeader);

		LOGGER.info("Response : {}", res.getUserKey());

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
			if (StringUtils.equals(MemberConstants.USER_EXTRA_SOCIAL_ACCT_TYPE, infoReq.getExtraProfile())
					|| StringUtils.equals(MemberConstants.USER_EXTRA_SOCIAL_ACCT_ID, infoReq.getExtraProfile())
					|| StringUtils.equals(MemberConstants.USER_EXTRA_SOCIAL_ACCT_INT_ID, infoReq.getExtraProfile())
					|| StringUtils.equals(MemberConstants.USER_EXTRA_SOCIAL_EMAIL, infoReq.getExtraProfile())
					|| StringUtils.equals(MemberConstants.USER_EXTRA_SOCIAL_ACCT_TOKEN, infoReq.getExtraProfile())
					|| StringUtils.equals(MemberConstants.USER_EXTRA_SOCIAL_REF_TOKEN, infoReq.getExtraProfile())
					|| StringUtils.equals(MemberConstants.USER_EXTRA_SOCIAL_EXPIRED_TIME, infoReq.getExtraProfile())) {
				throw new StorePlatformException("SAC_MEM_1403");
			}
		}

		LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(req));

		UserExtraInfoRes res = this.userExtraService.remAdditionalInformation(req, sacHeader);

		LOGGER.info("Response : {}", res.getUserKey());

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

		LOGGER.info("Response : {}", res.getUserKey());

		return res;
	}

	/**
	 * <pre>
	 * 회원 부가 정보 중복 확인.
	 * </pre>
	 * 
	 * @param sacHeader
	 *            SacRequestHeader
	 * @param req
	 *            CheckAdditionalInformationSacReq
	 * @return CheckAdditionalInformationSacRes
	 */
	/* @RequestMapping(value = "/checkAdditionalInformation/v1", method = RequestMethod.POST) */
	@ResponseBody
	public CheckAdditionalInformationSacRes checkAdditionalInformation(SacRequestHeader sacHeader,
			@RequestBody CheckAdditionalInformationSacReq req) {

		LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(req));

		CheckAdditionalInformationSacRes res = this.userExtraService.checkAdditionalInformation(sacHeader, req);

		LOGGER.info("Response : {}", res);

		return res;
	}

	@RequestMapping(value = "/checkAdditionalInformation/v1", method = RequestMethod.POST)
	@ResponseBody
	public MoveUserInfoSacRes moveUserInfo(SacRequestHeader sacHeader, @RequestBody MoveUserInfoSacReq req) {

		LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(req));
		MoveUserInfoSacRes sacRes = null;

		String userKey = StringUtil.nvl(req.getUserKey(), "");
		String moveType = StringUtil.nvl(req.getMoveType(), "");
		if (userKey.equals("")) {
			throw new StorePlatformException("SAC_MEM_0001", "userKey");
		}
		if (moveType.equals("")) {
			throw new StorePlatformException("SAC_MEM_0001", "moveType");
		}

		// Constant.USERMBR_MOVE_TYPE_ACTIVATE(정상 처리), Constant.USERMBR_MOVE_TYPE_DORMANT(휴면 처리)
		if ("1".equals(req.getMoveType()))
			req.setMoveType(MemberConstants.USER_MOVE_TYPE_ACTIVATE);
		if ("2".equals(req.getMoveType()))
			req.setMoveType(MemberConstants.USER_MOVE_TYPE_DORMANT);

		try {
			// IDP 휴면계정 연동
			CommonRes res = this.userService.moveUserInfoForIDP(sacHeader, req);

			req.setIdpResultYn(MemberConstants.USE_Y);
			req.setIdpErrCd(res.getResult());

			sacRes = this.userService.moveUserInfo(sacHeader, req);

			// 휴면계정상태해제 계정의 TB_US_USERMBR 테이블의 LAST_LOGIN_DT를 업데이트 한다.
			// 테스트 API에서 복구 처리시 휴면계정고지 배치 대상에 들어가지 않도록 하기 위함.
			if (MemberConstants.USER_MOVE_TYPE_ACTIVATE.equals(req.getMoveType())) {
				this.userService.updateActiveMoveUserLastLoginDt(sacHeader, req);
			}

		} catch (StorePlatformException ex) {
			// IDP result 가 1000X000, 1000이 아닌 경우 StorePlatformException이 발생한다.
			// errorInfo의 code의 prefix가 'EC_IDP_' 인 경우만 체크한다.
			if (ex.getCode() != null && ex.getCode().contains("EC_IDP_")) {
				req.setIdpResultYn(MemberConstants.USE_N);
				req.setIdpErrCd(ex.getCode().replace("EC_IDP_", ""));
				this.userService.moveUserInfo(sacHeader, req);
			}
			throw ex;
		}

		LOGGER.info("Response : {}", sacRes);

		return sacRes;
	}
}
