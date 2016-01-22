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

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateByAgreementReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateByAgreementRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateByIdSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateByIdSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateByMdnReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateByMdnRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateByMdnV2SacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateByMdnV2SacRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateBySimpleReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateBySimpleRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateSaveAndSyncReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateSaveAndSyncRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.member.common.MemberCommonComponent;
import com.skplanet.storeplatform.sac.member.common.constant.MemberConstants;
import com.skplanet.storeplatform.sac.member.common.util.ConvertMapperUtils;
import com.skplanet.storeplatform.sac.member.user.service.UserJoinService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
	private MemberCommonComponent commService;

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

		LOGGER.debug("####################################################");
		LOGGER.debug("##### 2.1.1. 모바일 전용 회원 가입 (MDN 회원 가입) #####");
		LOGGER.debug("####################################################");

		LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(req));

        if(!this.commService.isValidDeviceTelecomCode(req.getDeviceTelecom())){
            throw new StorePlatformException("SAC_MEM_1509");
        }

		/**
		 * 모바일 전용회원 Biz
		 */
		 CreateByMdnRes res = this.svc.regByMdn(sacHeader, req);

		LOGGER.info("Response : {}", ConvertMapperUtils.convertObjectToJson(res));

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
	public CreateByAgreementRes createByAgreement(SacRequestHeader sacHeader,
			@Validated @RequestBody CreateByAgreementReq req) {

		LOGGER.debug("####################################################");
		LOGGER.debug("##### 2.1.2. ID 회원 약관 동의 가입 (One ID 회원) #####");
		LOGGER.debug("####################################################");

		LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(req));

		CreateByAgreementRes res = null;
		if (!StringUtils.equals(req.getDeviceId(), "")) {

			/**
			 * [[ 단말정보 존재 ]] Biz
			 */
			if (StringUtils.equals(req.getDeviceId(), "")) {
				throw new StorePlatformException("SAC_MEM_0001", "deviceId");
			} else if (StringUtils.equals(req.getDeviceIdType(), "")) {
				throw new StorePlatformException("SAC_MEM_0001", "deviceIdType");
			} else if (StringUtils.equals(req.getDeviceTelecom(), "")) {
				throw new StorePlatformException("SAC_MEM_0001", "deviceTelecom");
			}
			// 미지원 단말 정보 수정으로 인한 주서처리...
			// else if (StringUtils.isBlank(sacHeader.getDeviceHeader().getModel())) {
			// throw new StorePlatformException("SAC_MEM_0002", "model");
			// }

			res = this.svc.regByAgreementDevice(sacHeader, req);

		} else {

			/**
			 * [[ 단말정보 미존재 ]] Biz
			 */
			res = this.svc.regByAgreementId(sacHeader, req);

		}

		LOGGER.info("Response : {}", ConvertMapperUtils.convertObjectToJson(res));

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

		LOGGER.debug("#############################################");
		LOGGER.debug("##### 2.1.3. ID 회원 간편 가입 (IDP 회원) #####");
		LOGGER.debug("#############################################");

		LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(req));

		CreateBySimpleRes res = null;
		if (!StringUtils.equals(req.getDeviceId(), "")) {

			/**
			 * [[ 단말정보 존재 ]] Biz
			 */
			if (StringUtils.equals(req.getDeviceId(), "")) {
				throw new StorePlatformException("SAC_MEM_0001", "deviceId");
			} else if (StringUtils.equals(req.getDeviceIdType(), "")) {
				throw new StorePlatformException("SAC_MEM_0001", "deviceIdType");
			} else if (StringUtils.equals(req.getDeviceTelecom(), "")) {
				throw new StorePlatformException("SAC_MEM_0001", "deviceTelecom");
			}
			// 미지원 단말 정보 수정으로 인한 주서처리...
			// else if (StringUtils.isBlank(sacHeader.getDeviceHeader().getModel())) {
			// throw new StorePlatformException("SAC_MEM_0002", "model");
			// }

			res = this.svc.regBySimpleDevice(sacHeader, req);

		} else {

			/**
			 * [[ 단말정보 미존재 ]] Biz
			 */
			res = this.svc.regBySimpleId(sacHeader, req);

		}

		LOGGER.info("Response : {}", ConvertMapperUtils.convertObjectToJson(res));

		return res;

	}

	/**
	 * <pre>
	 * Save&Sync 가입.
	 * </pre>
	 * 
	 * @param sacHeader
	 *            공통 헤더
	 * @param req
	 *            Request Value Object
	 * @return Response Value Object
	 */
	@RequestMapping(value = "/member/user/createSaveAndSync/v1", method = RequestMethod.POST)
	@ResponseBody
	public CreateSaveAndSyncRes createSaveAndSync(SacRequestHeader sacHeader,
			@Validated @RequestBody CreateSaveAndSyncReq req) {

		LOGGER.debug("#################################");
		LOGGER.debug("##### 2.1.38. Save&Sync 가입 #####");
		LOGGER.debug("#################################");

		LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(req));

		/**
		 * Save&Sync 가입 Biz
		 */
		CreateSaveAndSyncRes res = this.svc.regSaveAndSync(sacHeader, req);

		LOGGER.info("Response : {}", ConvertMapperUtils.convertObjectToJson(res));

		return res;

	}

	/**
	 * <pre>
	 * ID기반(Social ID) 회원 가입.
	 * </pre>
	 *
	 * @param sacHeader
	 *            공통 헤더
	 * @param req
	 *            Request Value Object
	 * @return Response Value Object
	 */
	@RequestMapping(value = "/member/user/createById/v1", method = RequestMethod.POST)
	@ResponseBody
	public CreateByIdSacRes createById(SacRequestHeader sacHeader, @Validated @RequestBody CreateByIdSacReq req) {

		LOGGER.debug("#################################");
		LOGGER.debug("##### 2.1.67. ID기반(Social ID) 회원 가입 #####");
		LOGGER.debug("#################################");

		LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(req));

		if(!this.commService.isValidDeviceTelecomCode(req.getDeviceTelecom())){
			throw new StorePlatformException("SAC_MEM_1509");
		}

		if(StringUtils.equals(MemberConstants.DEVICE_TELECOM_NON, req.getDeviceTelecom())
				&& StringUtils.isNotBlank(req.getMdn())){
			throw new StorePlatformException("SAC_MEM_1514");
		}

		if(StringUtils.isNotBlank(req.getMdn())){
			if(StringUtils.isBlank(req.getNativeId())){
				throw new StorePlatformException("SAC_MEM_0001", "nativeId");
			}
			if(StringUtils.isBlank(req.getSimSerialNo())){
				throw new StorePlatformException("SAC_MEM_0001", "simSerialNo");
			}
		}

		CreateByIdSacRes res = this.svc.createById(sacHeader, req);

		LOGGER.info("Response : {}", ConvertMapperUtils.convertObjectToJson(res));

		return res;

	}

	/**
	 * <pre>
	 * 모바일 전용 회원 가입 (MDN 회원 가입) v2.
	 * </pre>
	 *
	 * @param sacHeader
	 *            공통 헤더
	 * @param req
	 *            Request Value Object
	 * @return Response Value Object
	 */
	@RequestMapping(value = "/member/user/createByMdn/v2", method = RequestMethod.POST)
	@ResponseBody
	public CreateByMdnV2SacRes createByMdnV2(SacRequestHeader sacHeader, @Validated @RequestBody CreateByMdnV2SacReq req) {

		LOGGER.debug("####################################################");
		LOGGER.debug("##### 2.1.70. 모바일 전용 회원 가입 (MDN 회원 가입) v2 #####");
		LOGGER.debug("####################################################");

		LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(req));

		if(!this.commService.isValidDeviceTelecomCode(req.getDeviceTelecom())){
			throw new StorePlatformException("SAC_MEM_1509");
		}

		// MVNO 통신사 요청시 에러
		if(StringUtils.equals(MemberConstants.DEVICE_TELECOM_SKM, req.getDeviceTelecom())
				|| StringUtils.equals(MemberConstants.DEVICE_TELECOM_KTM, req.getDeviceTelecom())
				|| StringUtils.equals(MemberConstants.DEVICE_TELECOM_LGM, req.getDeviceTelecom())){
			throw new StorePlatformException("SAC_MEM_1515");
		}

		if(StringUtils.equals(MemberConstants.DEVICE_TELECOM_NON, req.getDeviceTelecom())
				&& StringUtils.isNotBlank(req.getMdn())){
			throw new StorePlatformException("SAC_MEM_1514");
		}

		CreateByMdnV2SacRes res = this.svc.regByMdnV2(sacHeader, req);

		LOGGER.info("Response : {}", ConvertMapperUtils.convertObjectToJson(res));

		return res;
	}
}
