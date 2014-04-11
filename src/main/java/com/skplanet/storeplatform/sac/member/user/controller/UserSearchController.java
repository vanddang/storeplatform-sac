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

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.sac.api.util.StringUtil;
import com.skplanet.storeplatform.sac.client.member.vo.user.DetailByDeviceIdSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.DetailByDeviceIdSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.DetailReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.DetailRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.ExistReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.ExistRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.GetProvisioningHistoryReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.GetProvisioningHistoryRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.ListDailyPhoneOsSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.ListTermsAgreementSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.ListTermsAgreementSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.MbrOneidSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.MbrOneidSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.SearchIdSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.SearchIdSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.SearchPasswordSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.SearchPasswordSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.member.common.util.ConvertMapperUtils;
import com.skplanet.storeplatform.sac.member.user.service.UserSearchService;

/**
 * 회원 조회 서비스 Controller
 * 
 * Updated on : 2014. 1. 7. Updated by : 강신완, 부르칸.
 */
@Controller
public class UserSearchController {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserSearchController.class);

	@Autowired
	private UserSearchService svc;

	@RequestMapping(value = "/member/user/exist/v1", method = RequestMethod.POST)
	@ResponseBody
	public ExistRes exist(@RequestBody ExistReq req, SacRequestHeader sacHeader) {
		LOGGER.debug("####################################################");
		LOGGER.debug("##### 5.1.6. 회원 가입 여부 조회 (ID/MDN 기반) #####");
		LOGGER.debug("####################################################");

		if (StringUtil.nvl(req.getDeviceKey(), "").equals("") && StringUtil.nvl(req.getDeviceId(), "").equals("")
				&& StringUtil.nvl(req.getUserId(), "").equals("") && StringUtil.nvl(req.getUserKey(), "").equals("")) {
			throw new StorePlatformException("SAC_MEM_0001", "userId || userKey || deviceId || deviceKey");
		}

		LOGGER.info("회원가입여부조회 Start Request : {}", req);

		ExistRes res = this.svc.exist(sacHeader, req);

		LOGGER.info("회원가입여부조회 Fianl Response : {}", res);

		return res;
	}

	@RequestMapping(value = "/member/user/getProvisioningHistory/v1", method = RequestMethod.POST)
	@ResponseBody
	public GetProvisioningHistoryRes getProvisioningHistory(@RequestBody GetProvisioningHistoryReq req, SacRequestHeader sacHeader) {
		LOGGER.debug("####################################################");
		LOGGER.debug("##### 5.1.12. 회원 프로비저닝 이력 조회 #####");
		LOGGER.debug("####################################################");

		LOGGER.info("============================================ GetProvisioningHistoryReq : {}", req.toString());

		String deviceId = StringUtil.nvl(req.getDeviceId(), "");

		if ("".equals(deviceId)) {
			throw new StorePlatformException("SAC_MEM_0001", "deviceId");
		}

		LOGGER.info("회원 프로비저닝 이력조회 Startl Request : {}", req.toString());
		GetProvisioningHistoryRes res = this.svc.getProvisioningHistory(sacHeader, req);
		LOGGER.info("회원 프로비저닝 이력조회 Final Response : {}", res.toString());

		return res;
	}

	@RequestMapping(value = "/member/user/detail/v1", method = RequestMethod.POST)
	@ResponseBody
	public DetailRes detail(@RequestBody DetailReq req, SacRequestHeader sacHeader) {
		LOGGER.debug("####################################################");
		LOGGER.debug("##### 5.1.9. 회원 정보 조회 #####");
		LOGGER.debug("####################################################");

		if (StringUtil.nvl(req.getDeviceKey(), "").equals("") && StringUtil.nvl(req.getDeviceId(), "").equals("")
				&& StringUtil.nvl(req.getUserId(), "").equals("") && StringUtil.nvl(req.getUserKey(), "").equals("")) {
			throw new StorePlatformException("SAC_MEM_0001", "userId || userKey || deviceId || deviceKey");
		}

		LOGGER.info("회원정보조회 Start Request : {}", req.toString());
		DetailRes res = this.svc.detail(sacHeader, req);
		LOGGER.info("회원정보조회 Final Response : {}", res.getUserKey());

		return res;
	}

	/**
	 * <pre>
	 * DeviceId를 이용하여 회원 정보 조회.
	 * </pre>
	 * 
	 * @param sacHeader
	 *            공통 헤더
	 * @param req
	 *            Request Vaule Object
	 * @return Response Value Object
	 */
	@RequestMapping(value = "/member/user/detailByDeviceId/v1", method = RequestMethod.POST)
	@ResponseBody
	public DetailByDeviceIdSacRes detailByDeviceId(SacRequestHeader sacHeader, @Validated @RequestBody DetailByDeviceIdSacReq req) {

		LOGGER.debug("##################################################");
		LOGGER.debug("##### 2.1.34 DeviceId를 이용하여 회원 정보 조회 #####");
		LOGGER.debug("##################################################");

		LOGGER.info("Request : \n{}", ConvertMapperUtils.convertObjectToJson(req));

		/**
		 * DeviceId를 이용하여 회원 정보 조회 Biz
		 */
		DetailByDeviceIdSacRes res = this.svc.detailByDeviceId(sacHeader, req);

		LOGGER.info("Response : \n{}", ConvertMapperUtils.convertObjectToJson(res));

		return res;

	}

	@RequestMapping(value = "/member/user/searchOneIdInfo/v1", method = RequestMethod.POST)
	@ResponseBody
	public MbrOneidSacRes searchOneIdInfo(SacRequestHeader sacHeader, @RequestBody MbrOneidSacReq req) {
		LOGGER.debug("####################################################");
		LOGGER.debug("##### 2.1.35. OneID 정보조회 #####");
		LOGGER.debug("####################################################");

		String userKey = StringUtil.nvl(req.getUserKey(), "");

		if ("".equals(userKey)) {
			throw new StorePlatformException("SAC_MEM_0001", "userKey");
		}

		req.setUserKey(userKey);

		LOGGER.info("OneID 정보조회 Start Request : {}", req.toString());
		MbrOneidSacRes res = this.svc.searchUserOneId(sacHeader, req);
		LOGGER.info("OneID 정보조회 Final Response : {}", res.toString());

		return res;
	}

	@RequestMapping(value = "/member/user/searchId/v1", method = RequestMethod.POST)
	@ResponseBody
	public SearchIdSacRes searchId(SacRequestHeader sacHeader, @RequestBody SearchIdSacReq req) {
		LOGGER.debug("####################################################");
		LOGGER.debug("##### 2.1.7. ID 찾기 #####");
		LOGGER.debug("####################################################");

		LOGGER.info("============================================ SearchIdSacReq : {}", req.toString());

		String deviceId = StringUtil.nvl(req.getDeviceId(), "");
		String userEmail = StringUtil.nvl(req.getUserEmail(), "");

		if ("".equals(deviceId) && "".equals(userEmail)) {
			throw new StorePlatformException("SAC_MEM_0001", "deviceId or userEmail");
		}

		req.setDeviceId(deviceId);
		req.setUserEmail(userEmail);

		LOGGER.info("ID찾기 Start Response : {}", req.toString());
		SearchIdSacRes res = this.svc.searchId(sacHeader, req);
		LOGGER.info("ID찾기 Final Response : {}", res.toString());

		return res;
	}

	@RequestMapping(value = "/member/user/searchPassword/v1", method = RequestMethod.POST)
	@ResponseBody
	public SearchPasswordSacRes searchPassword(SacRequestHeader sacHeader, @RequestBody SearchPasswordSacReq req) {
		LOGGER.debug("####################################################");
		LOGGER.debug("##### 2.1.8. PASSWORD 찾기 #####");
		LOGGER.debug("####################################################");

		String userId = StringUtil.nvl(req.getUserId(), "");
		String userEmail = StringUtil.nvl(req.getUserEmail(), "");
		String userPhone = StringUtil.nvl(req.getUserPhone(), "");

		if (userId.equals("")) {
			throw new StorePlatformException("SAC_MEM_0001", "userId");
		}

		req.setUserId(userId);
		req.setUserEmail(userEmail);
		req.setUserPhone(userPhone);

		LOGGER.info("패스워드찾기 Start Request : {}", req.toString());
		SearchPasswordSacRes res = this.svc.searchPassword(sacHeader, req);
		LOGGER.info("패스워드찾기 Final Response : {}", res.toString());

		return res;
	}

	@RequestMapping(value = "/member/user/listTermsAgreement/v1", method = RequestMethod.POST)
	@ResponseBody
	public ListTermsAgreementSacRes listTermsAgreement(@RequestBody ListTermsAgreementSacReq req, SacRequestHeader sacHeader) {
		LOGGER.debug("####################################################");
		LOGGER.debug("##### 2.1.10. Store 약관 동의 목록 조회 #####");
		LOGGER.debug("####################################################");

		String userKey = StringUtil.nvl(req.getUserKey(), "");

		if (userKey.equals("")) {
			throw new StorePlatformException("SAC_MEM_0001", "userKey");
		}

		LOGGER.info("Store약관동의목록조회 Start Request : {}", req.toString());
		ListTermsAgreementSacRes res = this.svc.listTermsAgreement(sacHeader, req);
		LOGGER.info("Store약관동의목록조회 Final Request : {}", res.getUserKey());

		return res;
	}

	@RequestMapping(value = "/member/user/listDailyPhoneOs/v1", method = RequestMethod.GET)
	@ResponseBody
	public ListDailyPhoneOsSacRes listDailyPhoneOs(SacRequestHeader sacHeader) {
		LOGGER.debug("####################################################");
		LOGGER.debug("##### 2.1.37. 각 단말의 OS별 누적 가입자 수 조회 #####");
		LOGGER.debug("####################################################");

		LOGGER.info("각 단말의 OS별 누적 가입자수 조회 Start");
		ListDailyPhoneOsSacRes dailyPhoneOsList = this.svc.listDailyPhoneOs(sacHeader);
		LOGGER.info("각 단말의 OS별 누적 가입자수 조회 Final");

		return dailyPhoneOsList;
	}
}
