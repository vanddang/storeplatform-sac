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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.sac.client.member.vo.common.DeviceExtraInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.DeviceInfo;
import com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeByIdRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.AuthorizeByMdnRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateBySimpleRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateDeviceRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateOcbInformationRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateRealNameRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateTermsAgreementRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.ExistRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.GetOcbInformationRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.ListDeviceRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.ModifyEmailRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.ModifyPasswordRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.ModifyRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.ModifyTermsAgreementRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.RemoveOcbInformationRes;
import com.skplanet.storeplatform.sac.member.user.common.HeaderInfo;
import com.skplanet.storeplatform.sac.member.user.controller.UserJoinController;

/**
 * 회원 가입 서비스 Controller
 * 
 * Updated on : 2014. 1. 2. Updated by : 심대진, 다모아 솔루션.
 */
@RequestMapping(value = "/member/user")
@Controller
public class DummyDataController {

	private static final Logger logger = LoggerFactory.getLogger(UserJoinController.class);

	@Autowired
	private HeaderInfo headerInfo;

	// @RequestMapping(value = "/createByMdn/v1", method = RequestMethod.POST)
	// @ResponseBody
	// public CreateByMdnRes createByMdn() {
	//
	// logger.info("####################################################");
	// logger.info("##### 5.1.1. 모바일 전용 회원 가입 (MDN 회원 가입) #####");
	// logger.info("####################################################");
	//
	// CreateByMdnRes res = new CreateByMdnRes();
	// res.setUserKey("IW102158844420091030165015");
	//
	// return res;
	// }
	//
	// @RequestMapping(value = "/createByAgreement/v1", method =
	// RequestMethod.POST)
	// @ResponseBody
	// public CreateByAgreementRes createByAgreement() {
	//
	// logger.info("####################################################");
	// logger.info("##### 5.1.2. ID 회원 약관 동의 가입 (One ID 회원) #####");
	// logger.info("####################################################");
	//
	// CreateByAgreementRes res = new CreateByAgreementRes();
	// res.setUserKey("IW102158844420091030165015");
	//
	// return res;
	// }

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

	@RequestMapping(value = "/authorizeByMdn/v1", method = RequestMethod.GET)
	@ResponseBody
	public AuthorizeByMdnRes authorizeByMdn() {

		logger.info("####################################################");
		logger.info("##### 5.1.4. 모바일 전용 회원 인증 (MDN 인증) #####");
		logger.info("####################################################");

		AuthorizeByMdnRes res = new AuthorizeByMdnRes();
		res.setUserKey("IW102158844420091030165015");
		res.setUserAuthKey("1234567890");

		return res;
	}

	@RequestMapping(value = "/authorizeById/v1", method = RequestMethod.POST)
	@ResponseBody
	public AuthorizeByIdRes authorizeById() {

		logger.info("####################################################");
		logger.info("##### 5.1.5. ID 기반 회원 인증 (One ID, IDP 회원) #####");
		logger.info("####################################################");

		AuthorizeByIdRes res = new AuthorizeByIdRes();
		res.setUserKey("IW102158844420091030165015");
		res.setUserAuthKey("1234567890");

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

	@RequestMapping(value = "/listDevice/v1", method = RequestMethod.GET)
	@ResponseBody
	public ListDeviceRes listDevice() {

		logger.info("####################################################");
		logger.info("##### 5.1.17.	휴대기기 목록 조회 #####");
		logger.info("####################################################");

		ListDeviceRes res = new ListDeviceRes();

		/*
		 * 휴대기기정보 리스트
		 */
		List<DeviceInfo> deviceInfoList = new ArrayList<DeviceInfo>();

		/*
		 * 휴대기기 부가정보 리스트
		 */
		List<DeviceExtraInfo> deviceExtraInfoList = new ArrayList<DeviceExtraInfo>();
		DeviceExtraInfo deviceExtraInfo = new DeviceExtraInfo();
		deviceExtraInfo.setExtraProfile("US011404");
		deviceExtraInfo.setExtraProfileValue("LGFL");

		deviceExtraInfoList.add(deviceExtraInfo);

		deviceInfoList.get(0).setDeviceExtraInfoList(deviceExtraInfoList);
		deviceInfoList.get(0).setDeviceKey("");
		deviceInfoList.get(0).setDeviceId("01011112222");
		deviceInfoList.get(0).setDeviceType("");
		deviceInfoList.get(0).setDeviceModelNo("LG-SH810");
		deviceInfoList.get(0).setDeviceTelecom("SKT");
		deviceInfoList.get(0).setSvcMgmtNum("");
		deviceInfoList.get(0).setDeviceNickName("LG-SH810");
		deviceInfoList.get(0).setIsPrimary("Y");
		deviceInfoList.get(0).setIsAuthenticated("Y");
		deviceInfoList.get(0).setAuthenticationDate("20140106");
		deviceInfoList.get(0).setOsVer("1.0");
		deviceInfoList.get(0).setMakeComp("SAMSUNG");
		deviceInfoList.get(0).setUserId("hkd");

		res.setDeviceInfoList(deviceInfoList);

		return res;
	}

	@RequestMapping(value = "/createDevice/v1", method = RequestMethod.GET)
	@ResponseBody
	public CreateDeviceRes createDevice() {

		logger.info("####################################################");
		logger.info("##### 5.1.18.	휴대기기 등록 #####");
		logger.info("####################################################");

		CreateDeviceRes res = new CreateDeviceRes();
		res.setDeviceId("01011112222");
		res.setDeviceKey("01011112222");
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

	@RequestMapping(value = "/createOcbInformation/v1", method = RequestMethod.POST)
	@ResponseBody
	public CreateOcbInformationRes createOcbInformation() {

		logger.info("#########################################");
		logger.info("##### 5.1.28. 회원 OCB 정보 등록/수정 #####");
		logger.info("#########################################");

		CreateOcbInformationRes res = new CreateOcbInformationRes();
		res.setUserKey("IW102158844420091030165015");

		return res;
	}

	@RequestMapping(value = "/removeOcbInformation/v1", method = RequestMethod.POST)
	@ResponseBody
	public RemoveOcbInformationRes removeOcbInformation() {

		logger.info("####################################");
		logger.info("##### 5.1.29. 회원 OCB 정보 삭제 #####");
		logger.info("####################################");

		RemoveOcbInformationRes res = new RemoveOcbInformationRes();
		res.setUserKey("IW102158844420091030165015");

		return res;
	}

	@RequestMapping(value = "/getOcbInformation/v1", method = RequestMethod.GET)
	@ResponseBody
	public GetOcbInformationRes getOcbInformation() {

		logger.info("####################################");
		logger.info("##### 5.1.30. 회원 OCB 정보 조회 #####");
		logger.info("####################################");

		GetOcbInformationRes res = new GetOcbInformationRes();
		res.setUserKey("IW102158844420091030165015");
		res.setOcbAuthMtdCd("OR003401");
		res.setUseYn("Y");
		res.setOcbNo("1f7df530b17a0ac38d817d87a5f994855565bae540877207012e46c2eb9af4db");
		res.setUseStartDt("20130106");
		res.setUseEndDt("20130106");

		return res;
	}

	@RequestMapping(value = "/exist/v1", method = RequestMethod.GET)
	@ResponseBody
	public ExistRes exist(@RequestHeader Map<String, Object> headers) {

		logger.info("####################################################");
		logger.info("##### 5.1.6. 회원 가입 여부 조회 (ID/MDN 기반) #####");
		logger.info("####################################################");

		ExistRes res = new ExistRes();
		res.setUserKey("IW102158844420091030165015");
		res.setTstoreYn("Y");
		res.setUserType("US011501");
		res.setUserId("hkd");
		res.setIsRealName("N");
		res.setUnder14(null);
		res.setAgencyYn(null);
		res.setUnder19(null);
		res.setUserEmail("hkd@aaaa.com");

		return res;
	}
}
