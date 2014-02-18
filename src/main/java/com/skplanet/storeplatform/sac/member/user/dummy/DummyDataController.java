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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.sac.client.member.vo.user.ModifyEmailRes;
import com.skplanet.storeplatform.sac.member.user.controller.UserJoinController;

/**
 * 회원 가입 서비스 Controller
 * 
 * Updated on : 2014. 1. 2. Updated by : 심대진, 다모아 솔루션.
 */
// @RequestMapping(value = "/member/user")
@Controller
public class DummyDataController {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserJoinController.class);

	// /**
	// * <pre>
	// * method 설명.
	// * </pre>
	// *
	// * @return CreateByMdnRes
	// */
	// @RequestMapping(value = "/createByMdn/v1", method = RequestMethod.POST)
	// @ResponseBody
	// public CreateByMdnRes createByMdn() {
	//
	// LOGGER.info("####################################################");
	// LOGGER.info("##### 5.1.1. 모바일 전용 회원 가입 (MDN 회원 가입) #####");
	// LOGGER.info("####################################################");
	//
	// CreateByMdnRes res = new CreateByMdnRes();
	// res.setUserKey("IW102158844420091030165015");
	//
	// return res;
	// }

	// /**
	// * <pre>
	// * method 설명.
	// * </pre>
	// *
	// * @return CreateByAgreementRes
	// */
	// @RequestMapping(value = "/member/user/createByAgreement/v1", method = RequestMethod.POST)
	// @ResponseBody
	// public CreateByAgreementRes createByAgreement() {
	//
	// LOGGER.info("####################################################");
	// LOGGER.info("##### 5.1.2. ID 회원 약관 동의 가입 (One ID 회원) #####");
	// LOGGER.info("####################################################");
	//
	// CreateByAgreementRes res = new CreateByAgreementRes();
	// res.setUserKey("IW102158844420091030165015");
	//
	// return res;
	// }

	// /**
	// * <pre>
	// * method 설명.
	// * </pre>
	// *
	// * @return CreateBySimpleRes
	// */
	// @RequestMapping(value = "/member/user/createBySimple/v1", method = RequestMethod.POST)
	// @ResponseBody
	// public CreateBySimpleRes createBySimple() {
	//
	// LOGGER.info("#############################################");
	// LOGGER.info("##### 5.1.3. ID 회원 간편 가입 (IDP 회원) #####");
	// LOGGER.info("#############################################");
	//
	// CreateBySimpleRes res = new CreateBySimpleRes();
	// res.setUserKey("IW102158844420091030165015");
	//
	// return res;
	// }

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @return AuthorizeByMdnRes
	 */
	/*
	 * @RequestMapping(value = "/authorizeByMdn/v1", method = RequestMethod.POST)
	 * 
	 * @ResponseBody public AuthorizeByMdnRes authorizeByMdn() {
	 * 
	 * LOGGER.info("####################################################");
	 * LOGGER.info("##### 5.1.4. 모바일 전용 회원 인증 (MDN 인증) #####");
	 * LOGGER.info("####################################################");
	 * 
	 * AuthorizeByMdnRes res = new AuthorizeByMdnRes(); res.setUserKey("IW102158844420091030165015");
	 * res.setUserType("US011501"); res.setUserMainStatus("US010201"); res.setUserSubStatus("US010301");
	 * 
	 * return res; }
	 */

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @return AuthorizeByIdRes
	 */
	/*
	 * @RequestMapping(value = MemberTestConstant.PREFIX_USER_PATH_REAL + "/authorizeById/v1", method =
	 * RequestMethod.POST)
	 * 
	 * @ResponseBody public AuthorizeByIdRes authorizeById() {
	 * 
	 * LOGGER.info("####################################################");
	 * LOGGER.info("##### 5.1.5. ID 기반 회원 인증 (One ID, IDP 회원) #####");
	 * LOGGER.info("####################################################");
	 * 
	 * AuthorizeByIdRes res = new AuthorizeByIdRes(); res.setUserKey("IW102158844420091030165015");
	 * res.setUserAuthKey("1234567890"); res.setUserType("US011501"); res.setUserMainStatus("US010201");
	 * res.setUserSubStatus("US010301"); return res; }
	 */

	// /**
	// * <pre>
	// * method 설명.
	// * </pre>
	// *
	// * @return ModifyRes
	// */
	// @RequestMapping(value = "/member/user/modify/v1", method = RequestMethod.POST)
	// @ResponseBody
	// public ModifyRes modify() {
	//
	// LOGGER.info("#################################");
	// LOGGER.info("##### 5.1.13. 회원 정보 수정 #####");
	// LOGGER.info("#################################");
	//
	// ModifyRes res = new ModifyRes();
	// res.setUserKey("IW102158844420091030165015");
	//
	// return res;
	// }

	// /**
	// * <pre>
	// * method 설명.
	// * </pre>
	// *
	// * @return ModifyPasswordRes
	// */
	// @RequestMapping(value = "/member/user/modifyPassword/v1", method = RequestMethod.POST)
	// @ResponseBody
	// public ModifyPasswordRes modifyPassword() {
	//
	// LOGGER.info("################################");
	// LOGGER.info("##### 5.1.14. 비밀번호 수정 #####");
	// LOGGER.info("################################");
	//
	// ModifyPasswordRes res = new ModifyPasswordRes();
	// res.setUserKey("IW102158844420091030165015");
	//
	// return res;
	// }

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @return ModifyEmailRes
	 */
	@RequestMapping(value = "/member/user/modifyEmail/v1", method = RequestMethod.POST)
	@ResponseBody
	public ModifyEmailRes modifyEmail() {

		LOGGER.info("##################################");
		LOGGER.info("##### 5.1.15. 이메일 주소 수정 #####");
		LOGGER.info("##################################");

		ModifyEmailRes res = new ModifyEmailRes();
		res.setUserKey("IW102158844420091030165015");

		return res;
	}

	// /**
	// * <pre>
	// * method 설명.
	// * </pre>
	// *
	// * @return CreateTermsAgreementRes
	// */
	// @RequestMapping(value = "/member/user/createTermsAgreement/v1", method = RequestMethod.POST)
	// @ResponseBody
	// public CreateTermsAgreementRes createTermsAgreement() {
	//
	// LOGGER.info("######################################");
	// LOGGER.info("##### 5.1.16. Store 약관 동의 등록 #####");
	// LOGGER.info("######################################");
	//
	// CreateTermsAgreementRes res = new CreateTermsAgreementRes();
	// res.setUserKey("IW102158844420091030165015");
	//
	// return res;
	// }

	// /**
	// * <pre>
	// * method 설명.
	// * </pre>
	// *
	// * @return ModifyTermsAgreementRes
	// */
	// @RequestMapping(value = "/member/user/modifyTermsAgreement/v1", method = RequestMethod.POST)
	// @ResponseBody
	// public ModifyTermsAgreementRes modifyTermsAgreement() {
	//
	// LOGGER.info("######################################");
	// LOGGER.info("##### 5.1.17. Store 약관 동의 수정 #####");
	// LOGGER.info("######################################");
	//
	// ModifyTermsAgreementRes res = new ModifyTermsAgreementRes();
	// res.setUserKey("IW102158844420091030165015");
	//
	// return res;
	// }

	// /**
	// * <pre>
	// * method 설명.
	// * </pre>
	// *
	// * @return CreateRealNameRes
	// */
	// @RequestMapping(value = "/member/user/createRealName/v1", method = RequestMethod.POST)
	// @ResponseBody
	// public CreateRealNameRes createRealName() {
	//
	// LOGGER.info("#####################################");
	// LOGGER.info("##### 5.1.18. 실명 인증 정보 등록 #####");
	// LOGGER.info("#####################################");
	//
	// CreateRealNameRes res = new CreateRealNameRes();
	// res.setUserKey("IW102158844420091030165015");
	//
	// return res;
	// }

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @return ListDeviceRes
	 */
	// @RequestMapping(value = MemberTestConstant.PREFIX_USER_PATH_REAL + "/listDevice/v1", method = RequestMethod.POST)
	// @ResponseBody
	// public ListDeviceRes listDevice() {
	//
	// LOGGER.info("####################################################");
	// LOGGER.info("##### 5.1.17.	휴대기기 목록 조회 #####");
	// LOGGER.info("####################################################");
	//
	// ListDeviceRes res = new ListDeviceRes();
	//
	// try {
	//
	// /*
	// * 휴대기기 부가정보 리스트
	// */
	// List<DeviceExtraInfo> deviceExtraInfoList = new ArrayList<DeviceExtraInfo>();
	// DeviceExtraInfo deviceExtraInfo = new DeviceExtraInfo();
	// deviceExtraInfo.setExtraProfile("US011404");
	// deviceExtraInfo.setExtraProfileValue("LGFL");
	//
	// deviceExtraInfoList.add(deviceExtraInfo);
	//
	// /*
	// * 휴대기기정보 리스트
	// */
	// List<DeviceInfo> deviceInfoList = new ArrayList<DeviceInfo>();
	// DeviceInfo deviceInfo = new DeviceInfo();
	//
	// deviceInfo.setUserDeviceExtraInfo(deviceExtraInfoList);
	// deviceInfo.setDeviceKey("01011112222");
	// deviceInfo.setDeviceId("01011112222");
	// deviceInfo.setDeviceModelNo("LG-SH810");
	// deviceInfo.setDeviceTelecom("SKT");
	// deviceInfo.setImMngNum("");
	// deviceInfo.setDeviceNickName("LG-SH810");
	// deviceInfo.setIsPrimary("Y");
	// deviceInfo.setIsAuthenticated("Y");
	// deviceInfo.setAuthenticationDate("20131226103711");
	// deviceInfo.setIsRecvSms("Y");
	// deviceInfo.setNativeId("358362045580844");
	// deviceInfo.setDeviceAccount("hkd@gmail.com");
	// deviceInfo.setJoinId("US012301");
	// deviceInfo.setOsVer("1.0");
	// deviceInfo.setScVer("1.0");
	// deviceInfo.setRooting("N");
	// deviceInfo.setMakeComp("SAMSUNG");
	//
	// deviceInfoList.add(deviceInfo);
	//
	// res.setUserId("hdk");
	// res.setUserKey("IW102158844420091030165015");
	// res.setDeviceInfoList(deviceInfoList);
	//
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	//
	// return res;
	// }
	/*
	 * @RequestMapping(value = "/listDevice/v1", method = RequestMethod.POST)
	 * 
	 * @ResponseBody public ListDeviceRes listDevice() {
	 * 
	 * LOGGER.info("####################################################");
	 * LOGGER.info("##### 5.1.17.	휴대기기 목록 조회 #####");
	 * LOGGER.info("####################################################");
	 * 
	 * ListDeviceRes res = new ListDeviceRes();
	 * 
	 * try {
	 * 
	 * 
	 * 휴대기기 부가정보 리스트
	 * 
	 * List<DeviceExtraInfo> deviceExtraInfoList = new ArrayList<DeviceExtraInfo>(); DeviceExtraInfo deviceExtraInfo =
	 * new DeviceExtraInfo(); deviceExtraInfo.setExtraProfile("US011404"); deviceExtraInfo.setExtraProfileValue("LGFL");
	 * 
	 * deviceExtraInfoList.add(deviceExtraInfo);
	 * 
	 * 
	 * 휴대기기정보 리스트
	 * 
	 * List<DeviceInfo> deviceInfoList = new ArrayList<DeviceInfo>(); DeviceInfo deviceInfo = new DeviceInfo();
	 * 
	 * deviceInfo.setUserDeviceExtraInfo(deviceExtraInfoList); deviceInfo.setDeviceKey("01011112222");
	 * deviceInfo.setDeviceId("01011112222"); deviceInfo.setDeviceModelNo("LG-SH810");
	 * deviceInfo.setDeviceTelecom("SKT"); deviceInfo.setImMngNum(""); deviceInfo.setDeviceNickName("LG-SH810");
	 * deviceInfo.setIsPrimary("Y"); deviceInfo.setIsAuthenticated("Y");
	 * deviceInfo.setAuthenticationDate("20131226103711"); deviceInfo.setIsRecvSms("Y");
	 * deviceInfo.setNativeId("358362045580844"); deviceInfo.setDeviceAccount("hkd@gmail.com");
	 * deviceInfo.setJoinId("US012301"); deviceInfo.setOsVer("1.0"); deviceInfo.setScVer("1.0");
	 * deviceInfo.setRooting("N"); deviceInfo.setMakeComp("SAMSUNG");
	 * 
	 * deviceInfoList.add(deviceInfo);
	 * 
	 * res.setUserId("hdk"); res.setUserKey("IW102158844420091030165015"); res.setDeviceInfoList(deviceInfoList);
	 * 
	 * } catch (Exception e) { e.printStackTrace(); }
	 * 
	 * return res; }
	 */

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @return CreateDeviceRes
	 */
	/*
	 * @RequestMapping(value = MemberTestConstant.PREFIX_USER_PATH_REAL + "/createDevice/v1", method =
	 * RequestMethod.POST)
	 * 
	 * @RequestMapping(value = "/createDevice/v1", method = RequestMethod.POST)
	 * 
	 * @ResponseBody public CreateDeviceRes createDevice() {
	 * 
	 * LOGGER.info("####################################################"); LOGGER.info("##### 5.1.18.	휴대기기 등록 #####");
	 * LOGGER.info("####################################################");
	 * 
	 * CreateDeviceRes res = new CreateDeviceRes(); res.setDeviceId("01011112222"); res.setDeviceKey("01011112222");
	 * res.setUserKey("IW102158844420091030165015");
	 * 
	 * return res; }
	 */

	// /**
	// * <pre>
	// * method 설명.
	// * </pre>
	// *
	// * @return CreateOcbInformationRes
	// */
	// @RequestMapping(value = "/member/user/createOcbInformation/v1", method = RequestMethod.POST)
	// @ResponseBody
	// public CreateOcbInformationRes createOcbInformation() {
	//
	// LOGGER.info("#########################################");
	// LOGGER.info("##### 5.1.28. 회원 OCB 정보 등록/수정 #####");
	// LOGGER.info("#########################################");
	//
	// CreateOcbInformationRes res = new CreateOcbInformationRes();
	// res.setUserKey("IW102158844420091030165015");
	//
	// return res;
	// }

	// /**
	// * <pre>
	// * method 설명.
	// * </pre>
	// *
	// * @return RemoveOcbInformationRes
	// */
	// @RequestMapping(value = "/member/user/removeOcbInformation/v1", method = RequestMethod.POST)
	// @ResponseBody
	// public RemoveOcbInformationRes removeOcbInformation() {
	//
	// LOGGER.info("####################################");
	// LOGGER.info("##### 5.1.29. 회원 OCB 정보 삭제 #####");
	// LOGGER.info("####################################");
	//
	// RemoveOcbInformationRes res = new RemoveOcbInformationRes();
	// res.setUserKey("IW102158844420091030165015");
	//
	// return res;
	// }

	// /**
	// * <pre>
	// * method 설명.
	// * </pre>
	// *
	// * @return GetOcbInformationRes
	// */
	// @RequestMapping(value = "/member/user/getOcbInformation/v1", method = RequestMethod.GET)
	// @ResponseBody
	// public GetOcbInformationRes getOcbInformation() {
	//
	// LOGGER.info("####################################");
	// LOGGER.info("##### 5.1.30. 회원 OCB 정보 조회 #####");
	// LOGGER.info("####################################");
	//
	// GetOcbInformationRes res = new GetOcbInformationRes();
	// res.setUserKey("IW102158844420091030165015");
	// res.setOcbAuthMtdCd("OR003401");
	// res.setUseYn("Y");
	// res.setOcbNo("1f7df530b17a0ac38d817d87a5f994855565bae540877207012e46c2eb9af4db");
	// res.setUseStartDt("20130106");
	// res.setUseEndDt("20130106");
	//
	// return res;
	// }

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @return ExistRes
	 */
	// @RequestMapping(value = "/exist/v1", method = RequestMethod.POST)
	// @ResponseBody
	// public ExistRes exist() {
	//
	// LOGGER.info("####################################################");
	// LOGGER.info("##### 5.1.6. 회원 가입 여부 조회 (ID/MDN 기반) #####");
	// LOGGER.info("####################################################");
	//
	// ExistRes res = new ExistRes();
	// res.setUserKey("IW102158844420091030165015");
	// res.setUserType("US011501");
	// res.setUserId("hkd");
	// res.setIsRealName("N");
	// res.setAgencyYn("Y");
	// res.setUserEmail("hkd@aaaa.com");
	// res.setUserMainStatus("US010202");
	// res.setUserSubStatus("US010303");
	//
	// return res;
	// }

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @return SearchIdRes
	 */
	// @RequestMapping(value = "/member/user/searchId/v1", method = RequestMethod.POST)
	// @ResponseBody
	// public SearchIdRes searchId() {
	//
	// LOGGER.info("####################################################");
	// LOGGER.info("##### 5.1.7. ID 찾기에 대한 기능을 제공한다. #####");
	// LOGGER.info("####################################################");
	//
	// SearchIdRes res = new SearchIdRes();
	// res.setUserId("hkd");
	//
	// return res;
	// }

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @return SearchPasswordRes
	 */
	// @RequestMapping(value = "/member/user/searchPassword/v1", method = RequestMethod.POST)
	// @ResponseBody
	// public SearchPasswordSacRes searchPassword() {
	//
	// LOGGER.info("####################################################");
	// LOGGER.info("##### 5.1.8. Password 찾기에 대한 기능을 제공한다. #####");
	// LOGGER.info("####################################################");
	//
	// SearchPasswordSacRes res = new SearchPasswordSacRes();
	// res.setUserPw("");
	// res.setSendMean("01");
	// res.setSendInfo("seoguman@nate.com");
	//
	// return res;
	// }

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @return DetailRes
	 */
	// @RequestMapping(value = "/member/user/detail/v1", method = RequestMethod.POST)
	// @ResponseBody
	// public DetailRes detail() {
	//
	// LOGGER.info("####################################################");
	// LOGGER.info("##### 5.1.9. ID/MDN를 기반으로 사용자 회원 정보 조회 기능을 제공한다. #####");
	// LOGGER.info("####################################################");
	//
	// DetailRes res = new DetailRes();
	//
	// /*
	// * 휴대기기 부가정보 리스트
	// */
	// List<DeviceExtraInfo> deviceExtraInfoList = new ArrayList<DeviceExtraInfo>();
	// DeviceExtraInfo deviceExtraInfo = new DeviceExtraInfo();
	// deviceExtraInfo.setExtraProfile("US011404");
	// deviceExtraInfo.setExtraProfileValue("LGFL");
	// deviceExtraInfoList.add(deviceExtraInfo);
	//
	// /*
	// * 휴대기기 정보 리스트
	// */
	// List<DeviceInfo> deviceInfoList = new ArrayList<DeviceInfo>();
	// DeviceInfo deviceInfo = new DeviceInfo();
	//
	// deviceInfo.setUserDeviceExtraInfo(deviceExtraInfoList);
	// deviceInfo.setDeviceKey("");
	// deviceInfo.setDeviceId("01011112222");
	// deviceInfo.setDeviceType("");
	// deviceInfo.setDeviceModelNo("LG-SH810");
	// // deviceInfo.setImMngNum("");
	// deviceInfo.setDeviceTelecom("US001201");
	// deviceInfo.setDeviceNickName("LG-SH810");
	// deviceInfo.setIsPrimary("Y");
	// deviceInfo.setIsAuthenticated("Y");
	// deviceInfo.setAuthenticationDate("20140106");
	// deviceInfo.setIsRecvSms("N");
	// deviceInfo.setNativeId("358362045580844");
	// deviceInfo.setDeviceAccount("hkd@aaaa.com");
	// deviceInfo.setJoinId("US002903");
	//
	// deviceInfoList.add(deviceInfo);
	//
	// /*
	// * 사용자 부가정보 리스트
	// */
	// List<UserExtraInfo> userExtraInfoList = new ArrayList<UserExtraInfo>();
	// UserExtraInfo userExtraInfo = new UserExtraInfo();
	// userExtraInfo.setExtraProfileCode("US010901");
	// userExtraInfo.setExtraProfileValue("100000003899");
	// userExtraInfoList.add(userExtraInfo);
	//
	// /*
	// * 사용자 정보 리스트
	// */
	// UserInfo userInfo = new UserInfo();
	// userInfo.setUserExtraInfo(userExtraInfoList);
	// userInfo.setUserKey("IW102158844420091030165015");
	// userInfo.setUserType("US011501");
	// userInfo.setUserMainStatus("US010701");
	// userInfo.setUserSubStatus("US010201");
	// userInfo.setImSvcNo("100001111241");
	// userInfo.setIsImChanged("Y");
	// userInfo.setImRegDat("20121126");
	// userInfo.setUserId("hkd");
	// userInfo.setUserTelecom("US001201");
	// userInfo.setUserPhoneCountry("82");
	// userInfo.setUserPhone("01011112222");
	// userInfo.setIsRecvSMS("N");
	// userInfo.setUserEmail("hkd@aaaa.com");
	// userInfo.setIsRecvEmail("N");
	// userInfo.setIsRestricted("Y");
	// userInfo.setRestrictStartDate("20130726");
	// userInfo.setRestrictEndDate("20130801");
	// userInfo.setRestrictId("PD00542");
	// userInfo.setRestrictCount("6");
	// userInfo.setRestrictRegisterDate("2013-12-26");
	// userInfo.setRestrictOwner("admin");
	// userInfo.setUserName("홍길동");
	// userInfo.setUserSex("M");
	// userInfo.setUserBirthDay("19700407");
	// userInfo.setUserZip("157030");
	// userInfo.setUserAddress("서울 강서구 등촌동");
	// userInfo.setUserDetailAddress("999-9");
	// userInfo.setUserCity("");
	// userInfo.setUserState("");
	// userInfo.setUserCountry("");
	// userInfo.setUserLanguage("ko");
	// userInfo.setIsParent("N");
	// userInfo.setIsRealName("N");
	// userInfo.setImSiteCode("");
	// userInfo.setUserExtraInfo(userExtraInfoList);
	//
	// /*
	// * 약관동의 리스트
	// */
	// List<Agreement> agreementList = new ArrayList<Agreement>();
	// Agreement agreement = new Agreement();
	// agreement.setExtraAgreementId("US010601");
	// agreement.setExtraAgreementVersion("");
	// agreement.setIsExtraAgreement("Y");
	//
	// agreementList.add(agreement);
	//
	// /*
	// * 인증정보
	// */
	// List<MbrAuth> mbrAuthList = new ArrayList<MbrAuth>();
	// MbrAuth mbrAuth = new MbrAuth();
	// mbrAuth.setBirthDay("");
	// mbrAuth.setCi("");
	// mbrAuth.setDi("");
	// mbrAuth.setIsRealName("");
	// mbrAuth.setMemberCategory("");
	// mbrAuth.setMemberKey("");
	// mbrAuth.setName("");
	// mbrAuth.setPhone("");
	// mbrAuth.setRealNameDate("");
	// mbrAuth.setRealNameMethod("");
	// mbrAuth.setRealNameSite("");
	// mbrAuth.setSequence("");
	// mbrAuth.setSex("");
	// mbrAuth.setTelecom("");
	// mbrAuth.setTenantID("");
	// mbrAuth.setUpdateDate("");
	// mbrAuthList.add(mbrAuth);
	//
	// /*
	// * 법정대리인 정보
	// */
	// List<MbrLglAgent> mbrLglAgentList = new ArrayList<MbrLglAgent>();
	// MbrLglAgent mbrLglAgent = new MbrLglAgent();
	// mbrLglAgent.setIsParent("");
	// mbrLglAgent.setMemberKey("");
	// mbrLglAgent.setParentBirthDay("");
	// mbrLglAgent.setParentCI("");
	// mbrLglAgent.setParentDate("");
	// mbrLglAgent.setParentEmail("");
	// mbrLglAgent.setParentMDN("");
	// mbrLglAgent.setParentName("");
	// mbrLglAgent.setParentRealNameDate("");
	// mbrLglAgent.setParentRealNameMethod("");
	// mbrLglAgent.setParentRealNameSite("");
	// mbrLglAgent.setParentTelecom("");
	// mbrLglAgent.setParentType("");
	// mbrLglAgent.setSequence("");
	// mbrLglAgentList.add(mbrLglAgent);
	//
	// res.setUserInfo(userInfo);
	// res.setDeviceInfoList(deviceInfoList);
	// res.setAgreementList(agreementList);
	// res.setMbrAuthList(mbrAuthList);
	// res.setMbrLglAgentList(mbrLglAgentList);
	//
	// return res;
	//
	// }

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @return ListTermsAgreementRes
	 */
	// @RequestMapping(value = "/member/user/listTermsAgreement/v1", method = RequestMethod.GET)
	// @ResponseBody
	// public ListTermsAgreementSacRes listTermsAgreement() {
	//
	// LOGGER.info("####################################################");
	// LOGGER.info("##### 5.1.10. Store의 약관에 대한 동의 목록 조회하는 기능을 제공한다. #####");
	// LOGGER.info("####################################################");
	//
	// ListTermsAgreementSacRes res = new ListTermsAgreementSacRes();
	// List<Agreement> agreementList = new ArrayList<Agreement>();
	// Agreement agreement = new Agreement();
	// agreement.setExtraAgreementId("US010601");
	// agreement.setExtraAgreementVersion("");
	// agreement.setIsExtraAgreement("Y");
	//
	// agreementList.add(agreement);
	// res.setAgreementList(agreementList);
	//
	// return res;
	// }

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @return GetSupportAomRes
	 */
	// @RequestMapping(value = "/member/user/getSupportAom/v1", method = RequestMethod.GET)
	// @ResponseBody
	// public GetSupportAomRes getSupportAom() {
	//
	// LOGGER.info("####################################################");
	// LOGGER.info("##### 5.3.8. 단말 AOM 지원 여부 확인. #####");
	// LOGGER.info("####################################################");
	//
	// GetSupportAomRes res = new GetSupportAomRes();
	// res.setIsAomSupport("Y");
	//
	// return res;
	// }

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @return GetProvisioningHistoryRes
	 */
	// @RequestMapping(value = "/member/user/getProvisioningHistory/v1", method = RequestMethod.GET)
	// @ResponseBody
	// public GetProvisioningHistoryRes getProvisioningHistory() {
	//
	// LOGGER.info("####################################################");
	// LOGGER.info("##### 5.3.14.	회원 프로비저닝 이력 조회. #####");
	// LOGGER.info("####################################################");
	//
	// GetProvisioningHistoryRes res = new GetProvisioningHistoryRes();
	// res.setWorkdCd("US003206");
	// res.setMbrNo("");
	// res.setOldMbrNo("");
	// res.setRegDate("20131226154512");
	//
	// return res;
	// }

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @return RemoveDeviceRes
	 */
	// @RequestMapping(value = "/member/user/removeDevice/v1", method = RequestMethod.POST)
	// @ResponseBody
	// public RemoveDeviceRes removeDevice() {
	//
	// LOGGER.info("####################################################");
	// LOGGER.info("##### 5.1.19.	휴대기기 삭제. #####");
	// LOGGER.info("####################################################");
	//
	// RemoveDeviceRes res = new RemoveDeviceRes();
	// res.setDeviceKey("01011112222");
	//
	// return res;
	// }

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @return ModifyRepresentationDeviceRes
	 */
	// @RequestMapping(value = "/modifyRepresentationDevice/v1", method = RequestMethod.POST)
	// @ResponseBody
	// public ModifyRepresentationDeviceRes modifyRepresentationDevice() {
	//
	// LOGGER.info("####################################################");
	// LOGGER.info("##### 5.1.20.	대표 단말 설정. #####");
	// LOGGER.info("####################################################");
	//
	// ModifyRepresentationDeviceRes res = new ModifyRepresentationDeviceRes();
	// res.setDeviceKey("01011112222");
	//
	// return res;
	// }

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @return DetailRepresentationDeviceRes
	 */
	// @RequestMapping(value = "/detailRepresentationDevice/v1", method = RequestMethod.POST)
	// @ResponseBody
	// public DetailRepresentationDeviceRes detailRepresentationDevice() {
	//
	// LOGGER.info("####################################################");
	// LOGGER.info("##### 5.1.23.	대표 단말 정보 조회. #####");
	// LOGGER.info("####################################################");
	//
	// DetailRepresentationDeviceRes res = new DetailRepresentationDeviceRes();
	//
	// // 휴대기기 부가정보
	// List<DeviceExtraInfo> deviceExtraInfoList = new ArrayList<DeviceExtraInfo>();
	// DeviceExtraInfo deviceExtraInfo = new DeviceExtraInfo();
	// deviceExtraInfo.setExtraProfile("US011404");
	// deviceExtraInfo.setExtraProfileValue("LGFL");
	//
	// deviceExtraInfoList.add(deviceExtraInfo);
	//
	// // 휴대기기정보
	// List<DeviceInfo> deviceInfoList = new ArrayList<DeviceInfo>();
	// DeviceInfo deviceInfo = new DeviceInfo();
	// deviceInfo.setUserDeviceExtraInfo(deviceExtraInfoList);
	// deviceInfo.setDeviceKey("01011112222");
	// deviceInfo.setDeviceId("01011112222");
	// deviceInfo.setDeviceType("");
	// deviceInfo.setDeviceModelNo("LG-SH810");
	// deviceInfo.setImMngNum("");
	// deviceInfo.setDeviceTelecom("SKT");
	// deviceInfo.setDeviceNickName("LG-SH810(임시)");
	// deviceInfo.setIsPrimary("Y");
	// deviceInfo.setIsAuthenticated("Y");
	// deviceInfo.setAuthenticationDate("2013-12-26");
	// deviceInfo.setIsRecvSms("N");
	// deviceInfo.setNativeId("358362045580844");
	// deviceInfo.setDeviceAccount("hkd@aaaa.com");
	// deviceInfo.setJoinId("US002903");
	//
	// deviceInfoList.add(deviceInfo);
	//
	// res.setUserDeviceInfo(deviceInfoList);
	//
	// return res;
	// }

	// /**
	// * <pre>
	// * method 설명.
	// * </pre>
	// *
	// * @return WithdrawRes
	// */
	// @RequestMapping(value = "/member/user/withdraw/v1", method = RequestMethod.POST)
	// @ResponseBody
	// public WithdrawRes withdraw() {
	//
	// LOGGER.info("####################################################");
	// LOGGER.info("##### 5.1.22.	회원 탈퇴. #####");
	// LOGGER.info("####################################################");
	//
	// WithdrawRes res = new WithdrawRes();
	//
	// res.setUserKey("IW102158844420091030165015");
	//
	// return res;
	// }

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @return CreateAddtionalInformationRes
	 */
	// @RequestMapping(value = "/member/user/modifyAdditionalInformation/v1", method = RequestMethod.POST)
	// @ResponseBody
	// public CreateAddtionalInformationRes createAddtionalInformation() {
	//
	// LOGGER.info("####################################################");
	// LOGGER.info("##### 5.1.25.	회원 부가 정보 등록/수정. #####");
	// LOGGER.info("####################################################");
	//
	// CreateAddtionalInformationRes res = new CreateAddtionalInformationRes();
	//
	// res.setUserKey("IF1023511101420120615164319");
	//
	// return res;
	// }

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @return RemoveAddtionalInformationRes
	 */
	// @RequestMapping(value = "/member/user/removeAdditionalInformation/v1", method = RequestMethod.POST)
	// @ResponseBody
	// public RemoveAddtionalInformationRes removeAddtionalInformation() {
	//
	// LOGGER.info("####################################################");
	// LOGGER.info("##### 5.1.26.	회원 부가 정보 삭제. #####");
	// LOGGER.info("####################################################");
	//
	// RemoveAddtionalInformationRes res = new RemoveAddtionalInformationRes();
	//
	// res.setUserKey("IF1023511101420120615164319");
	//
	// return res;
	// }

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @return ListAddtionalInformationRes
	 */
	// @RequestMapping(value = "/member/user/listAdditionalInformation/v1", method = RequestMethod.GET)
	// @ResponseBody
	// public ListAddtionalInformationRes listAddtionalInformation() {
	//
	// LOGGER.info("####################################################");
	// LOGGER.info("##### 5.1.27.	회원 부가 정보 조회. #####");
	// LOGGER.info("####################################################");
	//
	// ListAddtionalInformationRes res = new ListAddtionalInformationRes();
	//
	// List<UserExtraInfo> userExtraInfoList = new ArrayList<UserExtraInfo>();
	// UserExtraInfo userExtraInfo = new UserExtraInfo();
	// userExtraInfo.setExtraProfileCode("US010903");
	// userExtraInfo.setExtraProfileValue("100000003899");
	//
	// userExtraInfoList.add(userExtraInfo);
	//
	// res.setUserKey("IF1023511101420120615164319");
	// res.setAddInfoList(userExtraInfoList);
	//
	// return res;
	// }

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @return ListAddtionalInformationRes
	 */
	// @RequestMapping(value = "/member/user/listClause/v1", method = RequestMethod.POST)
	// @ResponseBody
	// public DetailClauseSacRes listClause() {
	//
	// LOGGER.info("####################################################");
	// LOGGER.info("##### 5.1.31.	약관목록 조회 #####");
	// LOGGER.info("####################################################");
	//
	// DetailClauseSacRes res = new DetailClauseSacRes();
	// List<ClauseSacRes> clauseResList = new ArrayList<ClauseSacRes>();
	//
	// ClauseSacRes clauseRes1 = new ClauseSacRes();
	// ClauseSacRes clauseRes2 = new ClauseSacRes();
	// ClauseSacRes clauseRes3 = new ClauseSacRes();
	//
	// clauseRes1.setClauseId("1000000063");
	// clauseRes1.setClauseItemCd("US010601");
	// clauseRes1.setStartDay("20130514");
	// clauseRes1.setEndDay("20130526");
	// clauseRes1.setFilePath("/data/img/termsHTML/20130524111753_listNotice.htm");
	// clauseRes1.setFileNm("20130524111753_listNotice.htm");
	// clauseRes1.setClauseVer("1");
	// clauseRes1.setUpClauseId("1000000059");
	// clauseRes1.setDpYn("Y");
	// clauseRes1.setRegId("admin");
	// clauseRes1.setRegDt("20130524");
	// clauseRes1.setUpdId("admin");
	// clauseRes1.setUpdDt("20130528");
	//
	// clauseRes2.setClauseId("1000000026");
	// clauseRes2.setClauseItemCd("US010602");
	// clauseRes2.setStartDay("20121130");
	// clauseRes2.setEndDay("20130430");
	// clauseRes2.setFilePath("/data/img/termsHTML/20121119114503_oneidinformation.html");
	// clauseRes2.setFileNm("20121119114503_oneidinformation.html");
	// clauseRes2.setClauseVer("1");
	// clauseRes2.setUpClauseId("1000000065");
	// clauseRes2.setDpYn("Y");
	// clauseRes2.setRegId("admin");
	// clauseRes2.setRegDt("20121119");
	// clauseRes2.setUpdId("admin");
	// clauseRes2.setUpdDt("20130524");
	//
	// clauseRes3.setClauseId("1000000061");
	// clauseRes3.setClauseItemCd("US010609");
	// clauseRes3.setStartDay("20130502");
	// clauseRes3.setEndDay("20130430");
	// clauseRes3.setFilePath("/data/img/termsHTML/20130524110946_sample02.html");
	// clauseRes3.setFileNm("20130524110946_sample02.html");
	// clauseRes3.setClauseVer("1");
	// clauseRes3.setUpClauseId("1000000056");
	// clauseRes3.setDpYn("Y");
	// clauseRes3.setRegId("admin");
	// clauseRes3.setRegDt("20130524");
	// clauseRes3.setUpdId("admin");
	// clauseRes3.setUpdDt("20130524");
	//
	// clauseResList.add(clauseRes1);
	// clauseResList.add(clauseRes2);
	// clauseResList.add(clauseRes3);
	//
	// res.setListClauseList(clauseResList);
	//
	// return res;
	// }

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @return ListAddtionalInformationRes
	 */
	// @RequestMapping(value = "/member/user/detailClause/v1", method = RequestMethod.POST)
	// @ResponseBody
	// public DetailClauseSacRes detailClause() {
	//
	// LOGGER.info("####################################################");
	// LOGGER.info("##### 5.1.32.	약관목록 상세 조회 #####");
	// LOGGER.info("####################################################");
	//
	// DetailClauseSacRes res = new DetailClauseSacRes();
	// List<ClauseSacRes> clauseResList = new ArrayList<ClauseSacRes>();
	//
	// ClauseSacRes clauseRes1 = new ClauseSacRes();
	// ClauseSacRes clauseRes2 = new ClauseSacRes();
	// ClauseSacRes clauseRes3 = new ClauseSacRes();
	//
	// clauseRes1.setClauseId("1000000059");
	// clauseRes1.setClauseItemCd("US010601");
	// clauseRes1.setStartDay("20130527");
	// clauseRes1.setEndDay("29991231");
	// clauseRes1.setFilePath("/data/img/termsHTML/20130523225047_sample03.html");
	// clauseRes1.setFileNm("20130523225047_sample03.html");
	// clauseRes1.setClauseVer("1");
	// clauseRes1.setUpClauseId("1000000059");
	// clauseRes1.setDpYn("Y");
	// clauseRes1.setRegId("admin");
	// clauseRes1.setRegDt("20130524");
	// clauseRes1.setUpdId("admin");
	// clauseRes1.setUpdDt("20130528");
	//
	// clauseRes2.setClauseId("1000000063");
	// clauseRes2.setClauseItemCd("US010601");
	// clauseRes2.setStartDay("20121130");
	// clauseRes2.setEndDay("20130430");
	// clauseRes2.setFilePath("/data/img/termsHTML/20130524111753_listNotice.htm");
	// clauseRes2.setFileNm("20130524111753_listNotice.htm");
	// clauseRes2.setClauseVer("1");
	// clauseRes2.setUpClauseId("1000000065");
	// clauseRes2.setDpYn("Y");
	// clauseRes2.setRegId("admin");
	// clauseRes2.setRegDt("20121119");
	// clauseRes2.setUpdId("admin");
	// clauseRes2.setUpdDt("20130524");
	//
	// clauseRes3.setClauseId("1000000066");
	// clauseRes3.setClauseItemCd("US010601");
	// clauseRes3.setStartDay("20130502");
	// clauseRes3.setEndDay("20130430");
	// clauseRes3.setFilePath("/data/img/termsHTML/20130526125332_sample04.html");
	// clauseRes3.setFileNm("20130526125332_sample04.html");
	// clauseRes3.setClauseVer("1");
	// clauseRes3.setUpClauseId("1000000056");
	// clauseRes3.setDpYn("Y");
	// clauseRes3.setRegId("admin");
	// clauseRes3.setRegDt("20130524");
	// clauseRes3.setUpdId("admin");
	// clauseRes3.setUpdDt("20130524");
	//
	// clauseResList.add(clauseRes1);
	// clauseResList.add(clauseRes2);
	// clauseResList.add(clauseRes3);
	//
	// res.setDetailClauseList(clauseResList);
	//
	// return res;
	// }

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @return ListAddtionalInformationRes
	 */
	// @RequestMapping(value = "/member/user/searchOneIdInfo/v1", method = RequestMethod.GET)
	// @ResponseBody
	// public OneIdInfoSacRes searchOneIdInfo() {
	//
	// LOGGER.info("####################################################");
	// LOGGER.info("##### 5.1.33.	OneId 정보 조회 #####");
	// LOGGER.info("####################################################");
	//
	// OneIdInfoSacRes res = new OneIdInfoSacRes();
	// res.setIdctYn("Y");
	// res.setOcbYn("Y");
	// res.setCiYn("Y");
	//
	// return res;
	// }

	// /**
	// * <pre>
	// * method 설명.
	// * </pre>
	// *
	// * @return LockAccountSacRes
	// */
	// @RequestMapping(value = "/member/user/lockAccount/v1", method = RequestMethod.POST)
	// @ResponseBody
	// public LockAccountSacRes lockAccount() {
	//
	// LOGGER.info("###############################");
	// LOGGER.info("##### 2.1.36 회원 계정 잠금 #####");
	// LOGGER.info("###############################");
	//
	// LockAccountSacRes res = new LockAccountSacRes();
	// res.setUserId("testId");
	//
	// return res;
	// }

}
