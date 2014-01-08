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
	public ExistRes exist(ExistReq req) throws Exception {
		ExistRes result = new ExistRes();
		SearchUserResponse schRes = new SearchUserResponse();

		/**
		 * TODO 회원기본정보 조회
		 */
		SearchUserRequest schReq = new SearchUserRequest();

		List<KeySearch> keySchList = new ArrayList<KeySearch>();
		KeySearch keySch = new KeySearch();
		if (req.getUserKey() != null) {
			keySch.setKeyType("user_key");
			keySch.setKeyString(req.getUserKey());
		} else if (req.getUserId() != null) {
			keySch.setKeyType("user_id");
			keySch.setKeyString(req.getUserId());
		} else if (req.getDeviceId() != null) {
			keySch.setKeyType("device_id");
			keySch.setKeyString(req.getDeviceId());
		} else if (req.getDeviceKey() != null) {
			keySch.setKeyType("device_key");
			keySch.setKeyString(req.getDeviceKey());
		} else {
			keySch.setKeyType("user_key");
			keySch.setKeyString(req.getUserKey());
		}
		keySchList.add(keySch);

		schReq.setKeySearchList(keySchList);

		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setSystemID("S001");
		commonRequest.setTenantID("T01");
		schReq.setCommonRequest(commonRequest);

		schRes = this.userSCI.searchUser(schReq);

		logger.info("######" + schRes.getCommonResponse().getResultCode());
		logger.info("######" + schRes.getCommonResponse().getResultMessage());

		/**
		 * SC Method Call Success
		 */
		if ("0000".equals(schRes.getCommonResponse().getResultCode())) {

			if (schRes.getUserMbr() != null) {
				result.setTstoreYn("Y");
				result.setUserKey(schRes.getUserMbr().getUserKey());
				result.setUserType(schRes.getUserMbr().getUserType());
				result.setUserId(schRes.getUserMbr().getUserID());
				result.setIsRealName(schRes.getUserMbr().getIsRealName());
				result.setAgencyYn(schRes.getUserMbr().getIsParent());
				result.setUserEmail(schRes.getUserMbr().getUserEmail());
			} else {
				result.setTstoreYn("N");
			}

		}
		/**
		 * SC Method Call Error
		 */
		else {
			throw new RuntimeException("SC 호출에러 Code : " + schRes.getCommonResponse().getResultCode());
		}

		return result;
	}

}
