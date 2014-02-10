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
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.sac.client.member.vo.user.DetailByDeviceIdSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.DetailByDeviceIdSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.DetailReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.DetailRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.ExistReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.ExistRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.GetProvisioningHistoryReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.GetProvisioningHistoryRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.MbrOneidSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.MbrOneidSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.SearchIdSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.SearchIdSacRes;
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
		LOGGER.info("####################################################");
		LOGGER.info("##### 5.1.6. 회원 가입 여부 조회 (ID/MDN 기반) #####");
		LOGGER.info("####################################################");

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
		}

		LOGGER.info("============================================ ExistReq : {}", req.toString());

		if (paramCnt > 0) {
			res = this.svc.exist(sacHeader, req);
		} else {
			throw new StorePlatformException("SAC_MEM_0001", req.toString());
		}
		LOGGER.info("Final Response : {}", res.toString());

		return res;
	}

	@RequestMapping(value = "/member/user/getProvisioningHistory/v1", method = RequestMethod.GET)
	@ResponseBody
	public GetProvisioningHistoryRes getProvisioningHistory(GetProvisioningHistoryReq req, SacRequestHeader sacHeader) {
		LOGGER.info("####################################################");
		LOGGER.info("##### 5.1.12. 회원 프로비저닝 이력 조회 #####");
		LOGGER.info("####################################################");

		GetProvisioningHistoryRes res = new GetProvisioningHistoryRes();

		/**
		 * 회원기본정보 조회 Biz
		 */
		int paramCnt = 0;
		if (!"".equals(req.getDeviceId()) && req.getDeviceId() != null) {
			paramCnt += 1;
		}

		LOGGER.info("============================================ GetProvisioningHistoryReq : {}", req.toString());

		if (paramCnt > 0) {
			res = this.svc.getProvisioningHistory(sacHeader, req);
		} else {
			throw new StorePlatformException("SAC_MEM_0001", req.toString());
		}
		LOGGER.info("Final Response : {}", res.toString());

		return res;
	}

	@RequestMapping(value = "/member/user/detail/v1", method = RequestMethod.POST)
	@ResponseBody
	public DetailRes detail(@RequestBody DetailReq req, SacRequestHeader sacHeader) {
		LOGGER.info("####################################################");
		LOGGER.info("##### 5.1.9. 회원 정보 조회 #####");
		LOGGER.info("####################################################");

		DetailRes res = new DetailRes();

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
		}

		LOGGER.info("============================================ DetailReq : {}", req.toString());

		if (paramCnt > 0) {
			res = this.svc.detail(sacHeader, req);
		} else {
			throw new StorePlatformException("SAC_MEM_0001", req.toString());
		}
		LOGGER.info("Final Response : {}", res.toString());

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
	 * @param result
	 *            BindingResult
	 * @return Response Value Object
	 */
	@RequestMapping(value = "/member/user/detailByDeviceId/v1", method = RequestMethod.POST)
	@ResponseBody
	public DetailByDeviceIdSacRes detailByDeviceId(SacRequestHeader sacHeader, @Validated @RequestBody DetailByDeviceIdSacReq req,
			BindingResult result) {

		LOGGER.info("##################################################");
		LOGGER.info("##### 2.1.34 DeviceId를 이용하여 회원 정보 조회 #####");
		LOGGER.info("##################################################");

		LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(req));

		/**
		 * BindException 처리
		 */
		if (result.hasErrors()) {
			throw new StorePlatformException("SAC_MEM_0001", result.getFieldError());
		}

		/**
		 * Header 정보
		 */
		LOGGER.info("Headers : {}", sacHeader.toString());

		/**
		 * DeviceId를 이용하여 회원 정보 조회 Biz
		 */
		DetailByDeviceIdSacRes res = this.svc.detailByDeviceId(sacHeader, req);

		LOGGER.info("Response : {}", res.toString());

		return res;

	}

	@RequestMapping(value = "/member/user/searchOneIdInfo/v1", method = RequestMethod.GET)
	@ResponseBody
	public MbrOneidSacRes detail(SacRequestHeader sacHeader, MbrOneidSacReq req) {
		LOGGER.info("####################################################");
		LOGGER.info("##### 2.1.35. OneID 정보조회 #####");
		LOGGER.info("####################################################");

		MbrOneidSacRes res = new MbrOneidSacRes();

		LOGGER.info("============================================ MbrOneidSacReq : {}", req.toString());

		if (req.getUserKey() != null && !"".equals(req.getUserKey())) {
			res = this.svc.searchUserOneId(sacHeader, req);
		} else {
			throw new StorePlatformException("SAC_MEM_0001", "getUserKey()");
		}

		LOGGER.info("Final MbrOneidSacRes Response : {}", res.toString());

		return res;
	}

	@RequestMapping(value = "/member/user/searchId/v1", method = RequestMethod.POST)
	@ResponseBody
	public SearchIdSacRes searchId(SacRequestHeader sacHeader, @RequestBody SearchIdSacReq req) {
		LOGGER.info("####################################################");
		LOGGER.info("##### 2.1.7. ID 찾기 #####");
		LOGGER.info("####################################################");

		LOGGER.info("============================================ SearchIdSacReq : {}", req.toString());

		SearchIdSacRes res = new SearchIdSacRes();

		if (req.getDeviceId() != null && !"".equals(req.getDeviceId())) {
			res = this.svc.searchId(sacHeader, req);
		} else {
			throw new StorePlatformException("SAC_MEM_0001", "getDeviceId()");
		}

		LOGGER.info("Final SearchIdSacRes Response : {}", res.toString());

		return res;
	}

}
