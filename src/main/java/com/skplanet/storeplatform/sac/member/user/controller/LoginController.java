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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.pdp.sentinel.shuttle.TLogSentinelShuttle;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.util.log.TLogUtil;
import com.skplanet.storeplatform.framework.core.util.log.TLogUtil.ShuttleSetter;
import com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeByIdReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeByIdRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeByMdnReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeByMdnRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeForInAppSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeForInAppSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeForOllehMarketSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeForOllehMarketSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeForUplusStoreSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeForUplusStoreSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeSaveAndSyncByMacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeSaveAndSyncByMacRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeSimpleByMdnReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeSimpleByMdnRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeV2SacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeV2SacRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.CheckVariabilityReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.CheckVariabilityRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.member.common.constant.MemberConstants;
import com.skplanet.storeplatform.sac.member.common.util.ConvertMapperUtils;
import com.skplanet.storeplatform.sac.member.user.service.LoginService;

/**
 * 로그인 Controller.
 * 
 * Updated on : 2014. 1. 6. Updated by : 반범진, 지티소프트.
 */
@Controller
public class LoginController {

	private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

	@Value("#{systemProperties['spring.profiles.active']}")
	private String envServerLevel;

	@Autowired
	private LoginService loginService;

	/**
	 * 모바일 전용 회원 인증 (MDN 인증).
	 * 
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param req
	 *            AuthorizeByMdnReq
	 * @return AuthorizeByMdnRes
	 */
	@RequestMapping(value = "/member/user/authorizeByMdn/v1", method = RequestMethod.POST)
	@ResponseBody
	public AuthorizeByMdnRes authorizeByMdn(SacRequestHeader requestHeader, @Valid @RequestBody AuthorizeByMdnReq req) {

		new TLogUtil().set(new ShuttleSetter() {
			@Override
			public void customize(TLogSentinelShuttle shuttle) {
				shuttle.log_id("TL_SAC_MEM_0006");
			}
		});

		LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(req));

		AuthorizeByMdnRes res = this.loginService.authorizeByMdn(requestHeader, req);

		LOGGER.info("Response : {}", ConvertMapperUtils.convertObjectToJson(res));

		return res;
	}

	/**
	 * 모바일 전용 회원 인증 v2 (MDN 인증, 변동성 포함).
	 * 
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param req
	 *            AuthorizeByMdnReq
	 * @return AuthorizeByMdnRes
	 */
	@RequestMapping(value = "/member/user/authorizeByMdn/v2", method = RequestMethod.POST)
	@ResponseBody
	public AuthorizeByMdnRes authorizeByMdnV2(SacRequestHeader requestHeader, @Valid @RequestBody AuthorizeByMdnReq req) {

		new TLogUtil().set(new ShuttleSetter() {
			@Override
			public void customize(TLogSentinelShuttle shuttle) {
				shuttle.log_id("TL_SAC_MEM_0006");
			}
		});

		LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(req));

		AuthorizeByMdnRes res = this.loginService.authorizeByMdnV2(requestHeader, req);

		LOGGER.info("Response : {}", ConvertMapperUtils.convertObjectToJson(res));

		return res;

	}

	/**
	 * 변동성 회원 체크.
	 * 
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param req
	 *            CheckVariabilityReq
	 * @return CheckVariabilityRes
	 */
	@RequestMapping(value = "/member/user/checkVariability/v1", method = RequestMethod.POST)
	@ResponseBody
	public CheckVariabilityRes checkVariability(SacRequestHeader requestHeader,
			@Valid @RequestBody CheckVariabilityReq req) {

		LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(req));

		CheckVariabilityRes res = this.loginService.checkVariability(requestHeader, req);

		LOGGER.info("Response : {}", ConvertMapperUtils.convertObjectToJson(res));

		return res;

	}

	/**
	 * ID 기반 회원 인증 (One ID, IDP 회원).
	 * 
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param req
	 *            AuthorizeByIdReq
	 * @return AuthorizeByIdRes
	 */
	@RequestMapping(value = "/member/user/authorizeById/v1", method = RequestMethod.POST)
	@ResponseBody
	public AuthorizeByIdRes authorizeById(SacRequestHeader requestHeader, @Valid @RequestBody AuthorizeByIdReq req) {

		LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(req));

		AuthorizeByIdRes res = this.loginService.authorizeById(requestHeader, req);

		LOGGER.info("Response : {}", ConvertMapperUtils.convertObjectToJson(res));

		return res;

	}

	/**
	 * 심플 인증(간편인증).
	 * 
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param req
	 *            AuthorizeSimpleByMdnReq
	 * @return AuthorizeByIdRes
	 */
	@RequestMapping(value = "/member/user/authorizeSimpleByMdn/v1", method = RequestMethod.POST)
	@ResponseBody
	public AuthorizeSimpleByMdnRes authorizeSimpleByMdn(SacRequestHeader requestHeader,
			@Valid @RequestBody AuthorizeSimpleByMdnReq req) {

		LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(req));

		AuthorizeSimpleByMdnRes res = this.loginService.authorizeSimpleByMdn(requestHeader, req);

		LOGGER.info("Response : {}", ConvertMapperUtils.convertObjectToJson(res));

		return res;

	}

	/**
	 * <pre>
	 * Save&Sync.
	 * </pre>
	 * 
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param req
	 *            AuthorizeSaveAndSyncByMacReq
	 * @return AuthorizeSaveAndSyncByMacRes
	 */
	@RequestMapping(value = "/member/user/authorizeSaveAndSyncByMac/v1", method = RequestMethod.POST)
	@ResponseBody
	public AuthorizeSaveAndSyncByMacRes authorizeSaveAndSyncByMac(SacRequestHeader requestHeader,
			@Valid @RequestBody AuthorizeSaveAndSyncByMacReq req) {

		LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(req));

		AuthorizeSaveAndSyncByMacRes res = this.loginService.authorizeSaveAndSyncByMac(requestHeader, req);

		LOGGER.info("Response : {}", ConvertMapperUtils.convertObjectToJson(res));

		return res;

	}

	/**
	 * <pre>
	 * PayPlanet에 InApp용으로 제공되는 T Store 회원인증.
	 * </pre>
	 * 
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param req
	 *            AuthorizeForInAppSacReq
	 * @return AuthorizeForInAppSacRes
	 */
	@RequestMapping(value = "/member/user/authorizeForInApp/v1", method = RequestMethod.POST)
	@ResponseBody
	public AuthorizeForInAppSacRes authorizeForInApp(SacRequestHeader requestHeader,
			@Valid @RequestBody AuthorizeForInAppSacReq req) {

		LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(req));

		if (StringUtils.equals(req.getDeviceId(), "null")) {
			throw new StorePlatformException("SAC_MEM_0001", "deviceId");
		}

		if (!StringUtils.equals(req.getDeviceTelecom(), MemberConstants.DEVICE_TELECOM_SKT)
				&& !StringUtils.equals(req.getDeviceTelecom(), MemberConstants.DEVICE_TELECOM_KT)
				&& !StringUtils.equals(req.getDeviceTelecom(), MemberConstants.DEVICE_TELECOM_LGT)) {
			throw new StorePlatformException("SAC_MEM_1203");
		}

		AuthorizeForInAppSacRes res = this.loginService.authorizeForInApp(requestHeader, req);

		LOGGER.info("Response : {}, {}, {}, {}", res.getTenantId(), res.getDeviceId(), res.getUserInfo().getUserKey(),
				res.getUserStatus());

		return res;

	}

	/**
	 * <pre>
	 * PayPlanet에 InApp용으로 제공되는 3사 통합 회원인증.
	 * </pre>
	 * 
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param req
	 *            AuthorizeForInAppSacReq
	 * @return AuthorizeForInAppSacRes
	 */
	@RequestMapping(value = "/member/user/authorizeForInApp/v2", method = RequestMethod.POST)
	@ResponseBody
	public AuthorizeForInAppSacRes authorizeForInAppV2(SacRequestHeader requestHeader,
			@Valid @RequestBody AuthorizeForInAppSacReq req) {

		LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(req));

		if (StringUtils.equals(req.getDeviceId(), "null")) {
			throw new StorePlatformException("SAC_MEM_0001", "deviceId");
		}

		if (!StringUtils.equals(req.getDeviceTelecom(), MemberConstants.DEVICE_TELECOM_SKT)
				&& !StringUtils.equals(req.getDeviceTelecom(), MemberConstants.DEVICE_TELECOM_KT)
				&& !StringUtils.equals(req.getDeviceTelecom(), MemberConstants.DEVICE_TELECOM_LGT)) {
			throw new StorePlatformException("SAC_MEM_1203");
		}

		AuthorizeForInAppSacRes res = this.loginService.authorizeForInAppV2(requestHeader, req);

		LOGGER.info("Response : {}, {}, {}, {}, {}", res.getTenantId(), res.getDeviceId(), res.getUserInfo()
				.getUserKey(), res.getUserInfo().getImMbrNo(), res.getUserStatus());

		return res;

	}

	/**
	 * <pre>
	 * PayPlanet에 InApp용으로 제공되는 3사 통합 회원인증 V3.
	 * 구매하는 상품의 최근 다운로드 이력정보로 tenantId를 구분한다.
	 * </pre>
	 * 
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param req
	 *            AuthorizeForInAppSacReq
	 * @return AuthorizeForInAppSacRes
	 */
	@RequestMapping(value = "/member/user/authorizeForInApp/v3", method = RequestMethod.POST)
	@ResponseBody
	public AuthorizeForInAppSacRes authorizeForInAppV3(SacRequestHeader requestHeader,
			@Valid @RequestBody AuthorizeForInAppSacReq req) {

		LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(req));

		if (StringUtils.equals(req.getDeviceId(), "null")) {
			throw new StorePlatformException("SAC_MEM_0001", "deviceId");
		}

		if (!StringUtils.equals(req.getDeviceTelecom(), MemberConstants.DEVICE_TELECOM_SKT)
				&& !StringUtils.equals(req.getDeviceTelecom(), MemberConstants.DEVICE_TELECOM_KT)
				&& !StringUtils.equals(req.getDeviceTelecom(), MemberConstants.DEVICE_TELECOM_LGT)) {
			throw new StorePlatformException("SAC_MEM_1203");
		}

		AuthorizeForInAppSacRes res = this.loginService.authorizeForInAppV3(requestHeader, req);

		LOGGER.info("Response : {}, {}, {}, {}, {}", res.getTenantId(), res.getDeviceId(), res.getUserInfo()
				.getUserKey(), res.getUserInfo().getImMbrNo(), res.getUserStatus());

		return res;

	}

	/**
	 * <pre>
	 * PayPlanet에 제공되는 T Store 회원인증.
	 * </pre>
	 * 
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param req
	 *            AuthorizeSacReq
	 * @return AuthorizeSacRes
	 */
	@RequestMapping(value = "/member/user/authorize/v1", method = RequestMethod.POST)
	@ResponseBody
	public AuthorizeSacRes authorize(SacRequestHeader requestHeader, @Valid @RequestBody AuthorizeSacReq req) {

		LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(req));

		if (StringUtils.equals(req.getDeviceId(), "null")) {
			throw new StorePlatformException("SAC_MEM_0001", "deviceId");
		}

		// tenantId, systemId 설정
		TenantHeader tenant = requestHeader.getTenantHeader();
		tenant.setTenantId(MemberConstants.TENANT_ID_TSTORE);
		tenant.setSystemId(MemberConstants.SYSTEM_ID_PAYPLANET_S01);
		requestHeader.setTenantHeader(tenant);

		AuthorizeSacRes res = this.loginService.authorize(requestHeader, req);

		LOGGER.info("Response : {}, {}, {}", res.getDeviceInfo().getDeviceId(), res.getUserInfo().getUserKey(),
				res.getUserMainStatus());

		return res;

	}

	/**
	 * <pre>
	 * Olleh Market 회원의 회원인증.
	 * </pre>
	 * 
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param req
	 *            AuthorizeForOllehMarketSacReq
	 * @return AuthorizeForOllehMarketSacRes
	 */
	@RequestMapping(value = "/member/user/authorizeForOllehMarket/v1", method = RequestMethod.POST)
	@ResponseBody
	public AuthorizeForOllehMarketSacRes authorizeForOllehMarket(SacRequestHeader requestHeader,
			@Valid @RequestBody AuthorizeForOllehMarketSacReq req) {

		new TLogUtil().set(new ShuttleSetter() {
			@Override
			public void customize(TLogSentinelShuttle shuttle) {
				shuttle.log_id("TL_SAC_MEM_0006");
			}
		});

		LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(req));

		AuthorizeForOllehMarketSacRes res = this.loginService.authorizeForOllehMarket(requestHeader, req);

		LOGGER.info("Response : {}, {}, {}", res.getDeviceId(), res.getUserInfo().getUserKey(), res.getUserStatus());

		return res;

	}

	/**
	 * <pre>
	 * Uplus Store 회원의 회원인증.
	 * </pre>
	 * 
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param req
	 *            AuthorizeForUplusStoreSacReq
	 * @return AuthorizeForUplusStoreSacRes
	 */
	@RequestMapping(value = "/member/user/authorizeForUplusStore/v1", method = RequestMethod.POST)
	@ResponseBody
	public AuthorizeForUplusStoreSacRes authorizeForUplusStore(SacRequestHeader requestHeader,
			@Valid @RequestBody AuthorizeForUplusStoreSacReq req) {

		new TLogUtil().set(new ShuttleSetter() {
			@Override
			public void customize(TLogSentinelShuttle shuttle) {
				shuttle.log_id("TL_SAC_MEM_0006");
			}
		});

		LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(req));

		AuthorizeForUplusStoreSacRes res = null;
		if (StringUtils.equals(this.envServerLevel, "real")) {
			res = this.loginService.authorizeForUplusStoreTest(requestHeader, req);
		} else {
			res = this.loginService.authorizeForUplusStore(requestHeader, req);
		}

		LOGGER.info("Response : {}, {}, {}", res.getDeviceId(), res.getUserInfo().getUserKey(), res.getUserStatus());

		return res;

	}

	/**
	 * <pre>
	 * PayPlanet에 제공되는 3사(SKT/KT/U+) 회원인증.
	 * </pre>
	 * 
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param req
	 *            AuthorizeV2SacReq
	 * @return AuthorizeV2SacRes
	 */
	@RequestMapping(value = "/member/user/authorize/v2", method = RequestMethod.POST)
	@ResponseBody
	public AuthorizeV2SacRes authorizeV2(SacRequestHeader requestHeader, @Valid @RequestBody AuthorizeV2SacReq req) {

		LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(req));

		if (StringUtils.equals(req.getDeviceId(), "null")) {
			throw new StorePlatformException("SAC_MEM_0001", "deviceId");
		}

		// tenantId 없는 경우 default S01
		if (StringUtils.isBlank(req.getTenantId())) {
			req.setTenantId(MemberConstants.TENANT_ID_TSTORE);
		}

		// 타사 인증시 필수 파라메터 체크
		if (!StringUtils.equals(req.getTenantId(), MemberConstants.TENANT_ID_TSTORE)) {

			if (StringUtils.isBlank(req.getDeviceTelecom())) {
				throw new StorePlatformException("SAC_MEM_0001", "deviceTelecom");
			}

			if (StringUtils.isBlank(req.getTrxNo())) {
				throw new StorePlatformException("SAC_MEM_0001", "trxNo");
			}

			if (StringUtils.isBlank(req.getNativeId())) {
				throw new StorePlatformException("SAC_MEM_0001", "nativeId");
			}
		}

		// tenantId, systemId 설정
		TenantHeader tenant = requestHeader.getTenantHeader();
		tenant.setTenantId(req.getTenantId());
		if (StringUtils.equals(req.getTenantId(), MemberConstants.TENANT_ID_TSTORE)) {
			tenant.setSystemId(MemberConstants.SYSTEM_ID_PAYPLANET_S01);
		} else if (StringUtils.equals(req.getTenantId(), MemberConstants.TENANT_ID_OLLEH_MARKET)) {
			tenant.setSystemId(MemberConstants.SYSTEM_ID_PAYPLANET_S02);
		} else if (StringUtils.equals(req.getTenantId(), MemberConstants.TENANT_ID_UPLUS_STORE)) {
			tenant.setSystemId(MemberConstants.SYSTEM_ID_PAYPLANET_S03);
		}
		requestHeader.setTenantHeader(tenant);

		AuthorizeV2SacRes res = this.loginService.authorizeV2(requestHeader, req);

		LOGGER.info("Response : {}, {}, {}", res.getDeviceInfo().getDeviceId(), res.getUserInfo().getUserKey(),
				res.getUserMainStatus());

		return res;

	}

}
