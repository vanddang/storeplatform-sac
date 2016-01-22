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

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.sac.api.util.StringUtil;
import com.skplanet.storeplatform.sac.client.member.vo.common.DeliveryInfo;
import com.skplanet.storeplatform.sac.client.member.vo.user.*;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.member.common.util.ConvertMapperUtils;
import com.skplanet.storeplatform.sac.member.domain.mbr.UserDelivery;
import com.skplanet.storeplatform.sac.member.user.service.DeliveryInfoService;
import com.skplanet.storeplatform.sac.member.user.service.UserSearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

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

    @Autowired
    private DeliveryInfoService deliveryInfoService;

	@RequestMapping(value = "/member/user/exist/v1", method = RequestMethod.POST)
	@ResponseBody
	public ExistRes exist(@RequestBody ExistRemoveDeviceIdReq req, SacRequestHeader sacHeader) {

		if (StringUtil.nvl(req.getDeviceKey(), "").equals("") && StringUtil.nvl(req.getUserId(), "").equals("")
				&& StringUtil.nvl(req.getUserKey(), "").equals("")) {
			throw new StorePlatformException("SAC_MEM_0001", "userId || userKey || deviceKey");
		}

		LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(req));

		// request에서는 deviceId를 사용하지 않으나 내부적으로는 사용하므로 다시 셋팅한다.
		ExistReq existReq = new ExistReq();
		existReq.setUserKey(req.getUserKey());
		existReq.setUserId(req.getUserId());
		existReq.setDeviceKey(req.getDeviceKey());

		ExistRes res = this.svc.exist(sacHeader, existReq);

		LOGGER.info("Response : {}", res.getUserKey());

		return res;
	}

	@RequestMapping(value = "/member/user/detail/v1", method = RequestMethod.POST)
	@ResponseBody
	public DetailRes detail(@RequestBody DetailReq req, SacRequestHeader sacHeader) {

		if (StringUtil.nvl(req.getDeviceKey(), "").equals("") && StringUtil.nvl(req.getDeviceId(), "").equals("")
				&& StringUtil.nvl(req.getUserId(), "").equals("") && StringUtil.nvl(req.getUserKey(), "").equals("")) {
			throw new StorePlatformException("SAC_MEM_0001", "userId || userKey || deviceId || deviceKey");
		}

		LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(req));

		DetailRes res = this.svc.detail(sacHeader, req);

		LOGGER.info("Response : {}", res.getUserKey());

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
	public DetailByDeviceIdSacRes detailByDeviceId(SacRequestHeader sacHeader,
			@Validated @RequestBody DetailByDeviceIdSacReq req) {

		LOGGER.debug("##################################################");
		LOGGER.debug("##### 2.1.33 DeviceId를 이용하여 회원 정보 조회 #####");
		LOGGER.debug("##################################################");

		LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(req));

		/** DeviceId를 이용하여 회원 정보 조회 Biz. */
		DetailByDeviceIdSacRes res = this.svc.detailByDeviceId(sacHeader, req);

		LOGGER.info("Response : deviceId={},userKey={},deviceKey={}", res.getDeviceId(), res.getUserKey(),
				res.getDeviceKey());

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

		LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(req));

		MbrOneidSacRes res = this.svc.srhUserOneId(sacHeader, req);

		LOGGER.info("Response : {}", ConvertMapperUtils.convertObjectToJson(res));

		return res;
	}

	@RequestMapping(value = "/member/user/searchId/v1", method = RequestMethod.POST)
	@ResponseBody
	public SearchIdSacRes searchId(SacRequestHeader sacHeader, @RequestBody SearchIdSacReq req) {
		LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(req));

		String deviceId = StringUtil.nvl(req.getDeviceId(), "");
		String userEmail = StringUtil.nvl(req.getUserEmail(), "");

		if ("".equals(deviceId) && "".equals(userEmail)) {
			throw new StorePlatformException("SAC_MEM_0001", "deviceId or userEmail");
		}

		req.setDeviceId(deviceId);
		req.setUserEmail(userEmail);

		SearchIdSacRes res = this.svc.srhId(sacHeader, req);

		LOGGER.info("Response ID : {}", res.getSearchIdList().get(0).getUserId());

		return res;
	}

	@RequestMapping(value = "/member/user/searchPassword/v1", method = RequestMethod.POST)
	@ResponseBody
	public SearchPasswordSacRes searchPassword(SacRequestHeader sacHeader, @RequestBody SearchPasswordSacReq req) {

		String userId = StringUtil.nvl(req.getUserId(), "");
		String userEmail = StringUtil.nvl(req.getUserEmail(), "");
		String userPhone = StringUtil.nvl(req.getUserPhone(), "");

		if (userId.equals("")) {
			throw new StorePlatformException("SAC_MEM_0001", "userId");
		}

		req.setUserId(userId);
		req.setUserEmail(userEmail);
		req.setUserPhone(userPhone);

		LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(req));

		SearchPasswordSacRes res = this.svc.srhPassword(sacHeader, req);

		LOGGER.info("Response : {}", ConvertMapperUtils.convertObjectToJson(res));

		return res;
	}

	@RequestMapping(value = "/member/user/listDailyPhoneOs/v1", method = RequestMethod.GET)
	@ResponseBody
	public ListDailyPhoneOsSacRes listDailyPhoneOs(SacRequestHeader sacHeader) {
		LOGGER.debug("####################################################");
		LOGGER.debug("##### 2.1.37. 각 단말의 OS별 누적 가입자 수 조회 #####");
		LOGGER.debug("####################################################");

		LOGGER.info("Request : {}");

		ListDailyPhoneOsSacRes res = this.svc.listDailyPhoneOs(sacHeader);

		LOGGER.info("Response Size : {}", res.getDailyPhoneList().size());

		return res;
	}

	/**
	 * <pre>
	 * 2.1.43. 회원 기본정보 조회 V2.
	 * </pre>
	 * 
	 * @param req
	 *            DetailReq
	 * @param sacHeader
	 *            SacRequestHeader
	 * @return DetailV2Res
	 */
	@RequestMapping(value = "/member/user/detail/v2", method = RequestMethod.POST)
	@ResponseBody
	public DetailV2Res detailV2(@RequestBody DetailReq req, SacRequestHeader sacHeader) {

		if (StringUtils.isBlank(req.getDeviceKey()) && StringUtils.isBlank(req.getDeviceId())
				&& StringUtils.isBlank(req.getUserId()) && StringUtils.isBlank(req.getUserKey())) {
			throw new StorePlatformException("SAC_MEM_0001", "userId || userKey || deviceId || deviceKey");
		}

		LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(req));

		DetailV2Res res = this.svc.detailV2(sacHeader, req);

		LOGGER.info("Response : {}", res.getUserKey());

		return res;
	}

	/**
	 * <pre>
	 * 2.1.50. 회원 가입 여부 리스트 조회.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param req
	 *            ExistListSacReq
	 * @return ExistListSacRes
	 */
	@RequestMapping(value = "/member/user/existList/v1", method = RequestMethod.POST)
	@ResponseBody
	public ExistListSacRes existList(SacRequestHeader header, @RequestBody @Validated ExistListSacReq req) {
		LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(req));

		if (req.getDeviceIdList() == null) {
			throw new StorePlatformException("SAC_MEM_0001", "deviceIdList");
		}

		if (req.getDeviceIdList().size() <= 0) {
			throw new StorePlatformException("SAC_MEM_0001", "deviceIdList");
		}

		if (req.getDeviceIdList().size() > 10) {
			throw new StorePlatformException("SAC_MEM_1303", req.getDeviceIdList().size());
		}

		ExistListSacRes res = this.svc.existList(header, req);

		LOGGER.info("Response : {}", ConvertMapperUtils.convertObjectToJson(res));

		return res;
	}

	/**
	 * <pre>
	 * 2.1.56. 가입 테넌트 정보 조회.
	 * </pre>
	 * 
	 * @param requestHeader
	 *            공통 헤더
	 * @param req
	 *            Request Value Object
	 * @return Response Value Object
	 */
	@RequestMapping(value = "/member/user/listTenant/v1", method = RequestMethod.POST)
	@ResponseBody
	public ListTenantRes listTenant(SacRequestHeader requestHeader, @RequestBody ListTenantReq req) {
		LOGGER.debug("####################################");
		LOGGER.debug("##### 2.1.56 가입 테넌트 정보 조회   #####");
		LOGGER.debug("####################################");

		LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(req));

		if (StringUtils.isEmpty(req.getDeviceId())) {
			throw new StorePlatformException("SAC_MEM_0001", "deviceId");
		}

		/**
		 * 가입 테넌트 정보 조회 Biz
		 */
		ListTenantRes res = this.svc.listTenant(requestHeader, req);

		LOGGER.info("Response : {}", ConvertMapperUtils.convertObjectToJson(res));

		return res;
	}

	/**
	 * <pre>
	 * 2.1.58. 소셜 계정 등록 가능 여부 체크.
	 * </pre>
	 * 
	 * @param header
	 *            CheckSocialAccountSacReq
	 * @param req
	 *            CheckSocialAccountSacReq
	 * @return CheckSocialAccountSacRes
	 */
	@RequestMapping(value = "/member/user/checkSocialAccount/v1", method = RequestMethod.POST)
	@ResponseBody
	public CheckSocialAccountSacRes checkSocialAccount(SacRequestHeader header,
			@RequestBody @Validated CheckSocialAccountSacReq req) {
		LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(req));
		CheckSocialAccountSacRes res = this.svc.checkSocialAccount(header, req);
		LOGGER.info("Response : {}", ConvertMapperUtils.convertObjectToJson(res));
		return res;
	}

	/**
	 * <pre>
	 * 2.1.59. 소셜 계정 등록 회원 리스트.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param req
	 *            SearchSocialAccountSacReq
	 * @return SearchSocialAccountSacRes
	 */
	@RequestMapping(value = "/member/user/searchSocialAccount/v1", method = RequestMethod.POST)
	@ResponseBody
	public SearchSocialAccountSacRes searchSocialAccount(SacRequestHeader header,
			@RequestBody @Validated SearchSocialAccountSacReq req) {
		LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(req));
		SearchSocialAccountSacRes res = this.svc.searchSocialAccount(header, req);
		LOGGER.info("Response : {}", ConvertMapperUtils.convertObjectToJson(res));
		return res;
	}

	/**
	 * <pre>
	 * 2.1.61.	PayPlanet SSOCredential 조회.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param req
	 *            CreateSSOCredentialSacReq
	 * @return CreateSSOCredentialSacRes
	 */
	@RequestMapping(value = "/member/user/createSSOCredential/v1", method = RequestMethod.POST)
	@ResponseBody
	public CreateSSOCredentialSacRes createSSOCredential(SacRequestHeader header,
			@RequestBody @Validated CreateSSOCredentialSacReq req) {
		LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(req));
		CreateSSOCredentialSacRes res = this.svc.createSSOCredential(header, req);
		LOGGER.info("Response : {}", ConvertMapperUtils.convertObjectToJson(res));
		return res;
	}

	/**
	 * <pre>
	 * [I01000135] 2.1.64.	배송지 정보 조회.
	 * </pre>
	 * @param req SearchDeliveryInfoSacReq
	 * @return SearchDeliveryInfoSacRes
	 */
	@RequestMapping(value = "/member/user/searchDeliveryInfo/v1", method = RequestMethod.POST)
	@ResponseBody
	public SearchDeliveryInfoSacRes searchDeliveryInfo(@RequestBody @Validated SearchDeliveryInfoSacReq req) {
		LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(req));

        List<UserDelivery> list = deliveryInfoService.find(req.getUserKey(), req.getDeliveryTypeCd());
        List<DeliveryInfo> deliveryInfoList = Lists.transform(list, new Function<UserDelivery, DeliveryInfo>() {
            @Override
            public DeliveryInfo apply(UserDelivery input) {
                return input.convertToDeliveryInfo();
            }
        });

        SearchDeliveryInfoSacRes res = new SearchDeliveryInfoSacRes(req.getUserKey(), deliveryInfoList);
        LOGGER.info("Response : {}", ConvertMapperUtils.convertObjectToJson(res));
		return res;
	}

	/**
	 * <pre>
	 * 2.1.65.	회원 상품권 충전 정보 조회.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param req
	 *            SearchGiftChargeInfoSacReq
	 * @return SearchGiftChargeInfoSacRes
	 */
	@RequestMapping(value = "/member/user/searchGiftChargeInfo/v1", method = RequestMethod.POST)
	@ResponseBody
	public SearchGiftChargeInfoSacRes searchGiftChargeInfo(SacRequestHeader header,
			@RequestBody @Validated SearchGiftChargeInfoSacReq req) {
		LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(req));
		SearchGiftChargeInfoSacRes res = this.svc.searchGiftChargeInfo(header, req);
		LOGGER.info("Response : {}, {}", res.getUserKey(), res.getGiftChargeInfoList().size());
		return res;
	}
}
