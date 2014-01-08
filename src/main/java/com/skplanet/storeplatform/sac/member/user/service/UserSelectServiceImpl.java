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

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;
import com.skplanet.storeplatform.member.client.common.vo.KeySearch;
import com.skplanet.storeplatform.member.client.user.sci.UserSCI;
import com.skplanet.storeplatform.member.client.user.sci.vo.CheckDuplicationRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.CheckDuplicationResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchUserRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchUserResponse;
import com.skplanet.storeplatform.sac.client.member.vo.user.ExistReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.ExistRes;

/**
 * 회원 조회 서비스 인터페이스(CoreStoreBusiness) 구현체
 * 
 * Updated on : 2014. 1. 7. Updated by : 강신완, 부르칸.
 */
@Service
public class UserSelectServiceImpl implements UserSelectService {

	private static final Logger logger = LoggerFactory.getLogger(UserSelectServiceImpl.class);

	@Autowired
	private UserSCI userSCI;

	@Override
	public ExistRes exist(ExistReq req) {

		/**
		 * TODO 회원기본정보 조회
		 */
		SearchUserRequest schReq = new SearchUserRequest();
		CheckDuplicationRequest chkReq = new CheckDuplicationRequest();

		List<KeySearch> keySchList = new ArrayList<KeySearch>();
		KeySearch keySch = new KeySearch();
		keySch.setKeyType("user_key");
		keySch.setKeyString(req.getUserKey());
		keySchList.add(keySch);

		schReq.setKeySearchList(keySchList);

		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setSystemID("S001");
		commonRequest.setTenantID("T01");
		schReq.setCommonRequest(commonRequest);

		SearchUserResponse schRes = this.userSCI.searchUser(schReq);

		/**
		 * TODO ID 또는 이메일 존재하는지 확인 : Tstore 회원판별용 (협의필요)
		 */
		List<KeySearch> keySchListDupl = new ArrayList<KeySearch>();
		KeySearch keySchDupl = new KeySearch();
		keySchDupl.setKeyType("user_id");
		keySchDupl.setKeyString(schRes.getUserMbr().getUserID());
		keySchListDupl.add(keySchDupl);

		chkReq.setCommonRequest(commonRequest);
		chkReq.setKeySearchList(keySchListDupl);

		CheckDuplicationResponse chkRes = this.userSCI.checkDuplication(chkReq);

		logger.info("######" + schRes.getCommonResponse().getResultCode());
		logger.info("######" + schRes.getCommonResponse().getResultMessage());

		logger.info("###### Tstore ID/EMail 체크 : " + chkRes.getIsRegistered());
		logger.info("###### 유저 아이디 : " + schRes.getUserMbr().getUserID());
		logger.info("###### 유저 키 : " + schRes.getUserMbr().getUserKey());
		logger.info("###### 유저 이름 : " + schRes.getUserMbr().getUserName());
		logger.info("###### 통신사 : " + schRes.getUserMbr().getUserTelecom());
		logger.info("###### 유저 타입 : " + schRes.getUserMbr().getUserType());
		logger.info("###### 유저 이메일 : " + schRes.getUserMbr().getUserEmail());
		logger.info("###### 실명인증여부 : " + schRes.getMbrAuth().getIsRealName());
		logger.info("###### 법정대리인동의여부 : " + schRes.getMbrLglAgent().getIsParent());
		logger.info("###### 14세 미만 여부 : ");
		logger.info("###### 19세 미만 여부 : ");
		logger.info("###### 상품 19금 여부 : ");

		ExistRes result = new ExistRes();
		result.setUserKey(schRes.getUserMbr().getUserKey());
		result.setTstoreYn(chkRes.getIsRegistered());
		result.setUserType(schRes.getUserMbr().getUserType());
		result.setUserId(schRes.getUserMbr().getUserID());
		result.setIsRealName(schRes.getMbrAuth().getIsRealName());
		result.setUnder14(null);
		result.setAgencyYn(schRes.getMbrLglAgent().getIsParent());
		result.setUnder19(null);
		result.setProdAdultYn(null);
		result.setUserEmail(schRes.getUserMbr().getUserEmail());

		return result;

	}

}
