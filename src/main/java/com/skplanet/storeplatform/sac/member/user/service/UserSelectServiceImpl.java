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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.sac.client.member.vo.common.HeaderVo;
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

	// @Autowired
	// private SearchUserResponse userSCI;

	@Override
	public ExistRes exist(HeaderVo headerVo, ExistReq req) {

		/**
		 * TODO 회원기본정보 조회
		 */
		// UserMbr test = this.userSCI.getUserMbr();
		// logger.info("################################");
		// logger.info("######## userSCI " + test.toString());
		// logger.info("################################");

		ExistRes result = new ExistRes();
		result.setUserKey("IW102158844420091030165015");
		result.setTstoreYn("Y");
		result.setUserType("US011501");
		result.setUserId("hkd");
		result.setIsRealName("N");
		result.setUnder14(null);
		result.setAgencyYn(null);
		result.setUnder19(null);
		result.setProdAdultYn(null);
		result.setUserEmail("hkd@aaaa.com");

		return result;

	}

}
