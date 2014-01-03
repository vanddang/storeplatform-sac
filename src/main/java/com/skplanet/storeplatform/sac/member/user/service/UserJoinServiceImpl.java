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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.external.client.uaps.sci.UAPSSCI;
import com.skplanet.storeplatform.sac.client.member.vo.common.AgreementInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.HeaderVo;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateByMdnReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateByMdnRes;

/**
 * 회원 가입 서비스 인터페이스(CoreStoreBusiness) 구현체
 * 
 * Updated on : 2013. 12. 31. Updated by : 심대진, 다모아 솔루션.
 */
@Service
public class UserJoinServiceImpl implements UserJoinService {

	private static final Logger logger = LoggerFactory.getLogger(UserJoinServiceImpl.class);

	@Autowired
	private UAPSSCI uapsSCI;

	@Override
	public CreateByMdnRes createByMdn(HeaderVo headerVo, CreateByMdnReq req) {

		/**
		 * TODO (UAPS 연동) 모번호 조회 - 989로 시작하는 MDN이면 실행
		 */
		// this.uapsSCI.getOpmdInfo("98922223333");

		/**
		 * TODO (SC 연동) 약관 목록 조회 및 동의 여부 체크 / 실패시 에러처리
		 */
		for (AgreementInfo info : req.getAgreementList()) {

			logger.info(info.getExtraAgreementId());
			logger.info(info.getExtraAgreementVersion());
			logger.info(info.getIsExtraAgreement());

		}

		/**
		 * TODO (IDP 연동) 무선회원 가입
		 */

		/**
		 * TODO IDP 회원 기가입 상태인 경우
		 */

		CreateByMdnRes result = new CreateByMdnRes();
		result.setUserKey("12321423543464567457");

		return result;

	}
}
