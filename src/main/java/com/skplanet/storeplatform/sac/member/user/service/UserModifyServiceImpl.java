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

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.external.client.idp.vo.IDPReceiverM;
import com.skplanet.storeplatform.member.client.user.sci.UserSCI;
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
			 * IDP 연동 정도 setting.
			 */
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("user_auth_key", req.getUserAuthKey());
			param.put("key_type", "2");
			param.put("key", userInfo.getImMbrNo()); // MBR_NO
			param.put("user_name", req.getUserName());
			param.put("user_sex", req.getUserSex());
			param.put("user_birthday", req.getUserBirthDay());
			param.put("user_calendar", req.getUserCalendar());
			param.put("user_zipcode", req.getUserZip());
			param.put("user_address", req.getUserAddress());
			param.put("user_address2", req.getUserDetailAddress());
			param.put("user_tel", req.getUserPhone());

			IDPReceiverM modifyInfo = this.idpService.modifyProfile(param);
			LOGGER.info("## IDP modifyProfile Code : {}", modifyInfo.getResponseHeader().getResult());
			LOGGER.info("## IDP modifyProfile Text  : {}", modifyInfo.getResponseHeader().getResult_text());

			if (StringUtils.equals(modifyInfo.getResponseHeader().getResult(), IDPConstants.IDP_RES_CODE_OK)) { // 정상가입

				LOGGER.info("## IDP 연동 성공 ==============================================");

			}

			/**
			 * TODO SC 사용자 정보 수정
			 */
			// UpdateUserRequest updateUserRequest = new UpdateUserRequest();
			//
			// updateUserRequest.setUserMbr(userMbr);
			//
			// userSCI.updateUser(updateUserRequest);

		}

		/**
		 * SC 회원 수정 요청.
		 */

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

	/**
	 * <pre>
	 * 사용자의 타입을 식별하여 코드를 리턴한다.
	 * </pre>
	 * 
	 * @param userKey
	 *            사용자 고유 Key
	 * @param sacHeader
	 *            공통 헤더
	 * @return 사용자 타입 코드
	 * @throws Exception
	 *             익셉션
	 * 
	 */
	private String checkMemberType(String userKey, SacRequestHeader sacHeader) throws Exception {

		UserInfo userInfo = this.mcc.getUserBaseInfo("userKey", userKey, sacHeader);

		/**
		 * 통합서비스번호 존재 유무로 통합회원인지 기존회원인지 판단한다. (UserType보다 더 신뢰함.)
		 */
		LOGGER.info("## 사용자 타입  : {}", userInfo.getUserType());
		LOGGER.info("## 통합회원번호 : {}", StringUtils.isNotEmpty(userInfo.getImSvcNo()));
		if (StringUtils.isNotEmpty(userInfo.getImSvcNo())) {

			LOGGER.info("## One ID 통합회원 [{}]", userInfo.getUserName());
			return MemberConstants.USER_TYPE_ONEID;

		} else {

			LOGGER.info("## 기존 IDP 회원 [{}]", userInfo.getUserName());
			return MemberConstants.USER_TYPE_IDPID;

		}

	}

}
