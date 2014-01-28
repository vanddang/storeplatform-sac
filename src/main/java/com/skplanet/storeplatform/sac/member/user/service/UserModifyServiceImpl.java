/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.member.user.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.external.client.idp.vo.IDPReceiverM;
import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;
import com.skplanet.storeplatform.member.client.user.sci.UserSCI;
import com.skplanet.storeplatform.member.client.user.sci.vo.UpdateUserRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.UpdateUserResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.UserMbr;
import com.skplanet.storeplatform.sac.client.member.vo.common.UserInfo;
import com.skplanet.storeplatform.sac.client.member.vo.user.ModifyEmailReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.ModifyEmailRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.ModifyPasswordReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.ModifyPasswordRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.ModifyReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.ModifyRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.member.common.MemberCommonComponent;
import com.skplanet.storeplatform.sac.member.common.constant.MemberConstants;
import com.skplanet.storeplatform.sac.member.common.idp.constants.IDPConstants;
import com.skplanet.storeplatform.sac.member.common.idp.repository.IDPRepository;
import com.skplanet.storeplatform.sac.member.common.idp.service.IDPService;
import com.skplanet.storeplatform.sac.member.common.idp.service.ImIDPService;

/**
 * 회원 정보 수정 서비스 (CoreStoreBusiness) 구현체
 * 
 * Updated on : 2014. 1. 24. Updated by : 심대진, 다모아 솔루션.
 */
@Service
@Transactional
public class UserModifyServiceImpl implements UserModifyService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserModifyServiceImpl.class);

	@Autowired
	private MemberCommonComponent mcc;

	@Autowired
	private UserSCI userSCI;

	@Autowired
	private IDPService idpService;

	@Autowired
	private ImIDPService imIdpService;

	@Autowired
	private IDPRepository idpRepository;

	@Override
	public ModifyRes modify(SacRequestHeader sacHeader, ModifyReq req) throws Exception {

		ModifyRes response = new ModifyRes();

		/**
		 * 회원 정보 조회.
		 */
		UserInfo userInfo = this.mcc.getUserBaseInfo("userKey", req.getUserKey(), sacHeader);

		/**
		 * 통합서비스번호 존재 유무로 통합회원인지 기존회원인지 판단한다. (UserType보다 더 신뢰함.) 회원 타입에 따라서 [통합IDP, 기존IDP] 연동처리 한다.
		 */
		LOGGER.info("## 사용자 타입  : {}", userInfo.getUserType());
		LOGGER.info("## 통합회원번호 : {}", StringUtils.isNotEmpty(userInfo.getImSvcNo()));
		if (StringUtils.isNotEmpty(userInfo.getImSvcNo())) {

			LOGGER.info("## ====================================================");
			LOGGER.info("## One ID 통합회원 [{}]", userInfo.getUserName());
			LOGGER.info("## ====================================================");
			/**
			 * TODO 통합 IDP 연동
			 */

			/**
			 * TODO SC 사용자 정보 수정
			 */

		} else {

			LOGGER.info("## ====================================================");
			LOGGER.info("## 기존 IDP 회원 [{}]", userInfo.getUserName());
			LOGGER.info("## ====================================================");

			/**
			 * IDP 연동 정보 setting.
			 */
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("user_auth_key", req.getUserAuthKey());
			param.put("key_type", "2");
			param.put("key", userInfo.getImMbrNo()); // MBR_NO
			param.put("user_sex", req.getUserSex()); // 성별
			param.put("user_birthday", req.getUserBirthDay()); // 생년월일
			param.put("user_calendar", req.getUserCalendar()); // 양력1, 음력2
			param.put("user_zipcode", req.getUserZip()); // 우편번호
			param.put("user_address", req.getUserAddress()); // 주소
			param.put("user_address2", req.getUserDetailAddress()); // 상세주소
			param.put("user_tel", req.getUserPhone()); // 사용자 연락처

			IDPReceiverM modifyInfo = this.idpService.modifyProfile(param);
			LOGGER.info("## IDP modifyProfile Code : {}", modifyInfo.getResponseHeader().getResult());
			LOGGER.info("## IDP modifyProfile Text  : {}", modifyInfo.getResponseHeader().getResult_text());

			if (StringUtils.equals(modifyInfo.getResponseHeader().getResult(), IDPConstants.IDP_RES_CODE_OK)) {

				LOGGER.info("## IDP 연동 성공 ==============================================");

				IDPReceiverM searchUserInfo = this.idpService.searchUserCommonInfo("3", userInfo.getImMbrNo());
				LOGGER.info("## IDP searchUserInfo Code : {}", searchUserInfo.getResponseHeader().getResult());
				LOGGER.info("## IDP searchUserInfo Text  : {}", searchUserInfo.getResponseHeader().getResult_text());
				LOGGER.info("## IDP searchUserInfo Text  : {}", searchUserInfo.getResponseBody().getUser_sex());
				LOGGER.info("## IDP searchUserInfo Text  : {}", searchUserInfo.getResponseBody().getUser_birthday());
				LOGGER.info("## IDP searchUserInfo Text  : {}", searchUserInfo.getResponseBody().getUser_zipcode());
				LOGGER.info("## IDP searchUserInfo Text  : {}", searchUserInfo.getResponseBody().getUser_address());
				LOGGER.info("## IDP searchUserInfo Text  : {}", searchUserInfo.getResponseBody().getUser_address2());
				LOGGER.info("## IDP searchUserInfo Text  : {}", searchUserInfo.getResponseBody().getUser_tel());

				/**
				 * SC 회원 수정.
				 */
				String userKey = this.updateUser(sacHeader, req);
				response.setUserKey(userKey);

			} else {

				this.mcc.errorIdp(modifyInfo.getResponseHeader());

			}

		}

		return response;
	}

	@Override
	public ModifyPasswordRes modifyPassword(SacRequestHeader sacHeader, ModifyPasswordReq req) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ModifyEmailRes modifyEmail(SacRequestHeader sacHeader, ModifyEmailReq req) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	private String updateUser(SacRequestHeader sacHeader, ModifyReq req) throws Exception {

		UpdateUserRequest updateUserRequest = new UpdateUserRequest();

		/**
		 * 공통 정보 setting.
		 */
		updateUserRequest.setCommonRequest(this.getCommonRequest(sacHeader));

		/**
		 * 사용자 기본정보 setting.
		 */
		updateUserRequest.setUserMbr(this.getUserMbr(req));

		/**
		 * SC 사용자 회원 기본정보 수정 요청.
		 */
		UpdateUserResponse updateUserResponse = this.userSCI.updateUser(updateUserRequest);
		LOGGER.info("## ResponseCode : {}", updateUserResponse.getCommonResponse().getResultCode());
		LOGGER.info("## ResponseMsg  : {}", updateUserResponse.getCommonResponse().getResultMessage());
		LOGGER.info("## UserKey      : {}", updateUserResponse.getUserKey());

		if (StringUtils.equals(updateUserResponse.getCommonResponse().getResultCode(), MemberConstants.RESULT_SUCCES)) {

			/**
			 * 결과 세팅
			 */
			return updateUserResponse.getUserKey();

		} else {

			LOGGER.info("## 사용자 기본정보 수정 실패 ===========================");
			throw new RuntimeException("사용자 기본정보 수정 실패");

		}

	}

	/**
	 * <pre>
	 * SC 공통정보 setting.
	 * </pre>
	 * 
	 * @param sacHeader
	 *            SacRequestHeader
	 * @return CommonRequest
	 * @throws Exception
	 *             익셉션
	 */
	private CommonRequest getCommonRequest(SacRequestHeader sacHeader) throws Exception {

		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setSystemID(sacHeader.getTenantHeader().getSystemId());
		commonRequest.setTenantID(sacHeader.getTenantHeader().getTenantId());
		LOGGER.info("## SC Request 공통 정보 : {}", commonRequest.toString());

		return commonRequest;
	}

	/**
	 * <pre>
	 * 사용자 기본정보 setting..
	 * </pre>
	 * 
	 * @param req
	 *            ModifyReq
	 * @return UserMbr
	 * @throws Exception
	 *             익셉션
	 */
	private UserMbr getUserMbr(ModifyReq req) throws Exception {

		UserMbr userMbr = new UserMbr();
		userMbr.setUserKey(req.getUserKey());

		/**
		 * 이동통신사
		 */
		if (!StringUtils.equals(req.getDeviceTelecom(), "")) {
			userMbr.setUserTelecom(req.getDeviceTelecom());
		}

		/**
		 * 휴대폰 번호 국가 코드
		 */
		if (!StringUtils.equals(req.getUserPhoneCountry(), "")) {
			userMbr.setUserPhoneCountry(req.getUserPhoneCountry());
		}

		/**
		 * 사용자 연락처
		 */
		if (!StringUtils.equals(req.getUserPhone(), "")) {
			userMbr.setUserPhone(req.getUserPhone());
		}

		/**
		 * SMS 수신 여부
		 */
		if (!StringUtils.equals(req.getIsRecvSms(), "")) {
			userMbr.setIsRecvSMS(req.getIsRecvSms());
		}

		/**
		 * 이메일 수신여부
		 */
		if (!StringUtils.equals(req.getIsRecvEmail(), "")) {
			userMbr.setIsRecvEmail(req.getIsRecvEmail());
		}

		/**
		 * 사용자 성별
		 */
		if (!StringUtils.equals(req.getUserSex(), "")) {
			userMbr.setUserSex(req.getUserSex());
		}

		/**
		 * 사용자 생년월일
		 */
		if (!StringUtils.equals(req.getUserBirthDay(), "")) {
			userMbr.setUserBirthDay(req.getUserBirthDay());
		}

		/**
		 * 우편번호
		 */
		if (!StringUtils.equals(req.getUserZip(), "")) {
			userMbr.setUserZip(req.getUserZip());
		}

		/**
		 * 거주지 주소
		 */
		if (!StringUtils.equals(req.getUserAddress(), "")) {
			userMbr.setUserAddress(req.getUserAddress());
		}

		/**
		 * 거주지 상세주소
		 */
		if (!StringUtils.equals(req.getUserDetailAddress(), "")) {
			userMbr.setUserDetailAddress(req.getUserDetailAddress());
		}

		/**
		 * (외국인) 도시
		 */
		if (!StringUtils.equals(req.getUserCity(), "")) {
			userMbr.setUserCity(req.getUserCity());
		}

		/**
		 * (외국인) 주
		 */
		if (!StringUtils.equals(req.getUserState(), "")) {
			userMbr.setUserState(req.getUserState());
		}
		LOGGER.info("## SC Request 사용자 기본정보 : {}", userMbr.toString());

		return userMbr;
	}

}
