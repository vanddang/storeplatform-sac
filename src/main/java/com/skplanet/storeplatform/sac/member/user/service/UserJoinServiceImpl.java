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

import java.util.Hashtable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.external.client.uaps.sci.UAPSSCI;
import com.skplanet.storeplatform.sac.client.member.vo.common.HeaderVo;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateByAgreementReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateByAgreementRes;
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

	// @Autowired
	// private UserSCI userSCI;

	@Override
	public CreateByMdnRes createByMdn(HeaderVo headerVo, CreateByMdnReq req) {

		/**
		 * TODO (UAPS 연동) 모번호 조회 - 989로 시작하는 MDN이면 실행
		 */
		// this.uapsSCI.getOpmdInfo("98922223333");

		/**
		 * TODO (SC 연동) 약관 목록 조회 및 동의 여부 체크 / 실패시 에러처리
		 */
		// SearchManagementListRequest sciReq = new SearchManagementListRequest();
		// sciReq.setUserKey("123142134");
		// this.userSCI.searchManagementList(sciReq);
		// for (AgreementInfo info : req.getAgreementList()) {
		//
		// logger.info(info.getExtraAgreementId());
		// logger.info(info.getExtraAgreementVersion());
		// logger.info(info.getIsExtraAgreement());
		//
		// }

		/**
		 * TODO (IDP 연동) 무선회원 가입
		 */
		Hashtable<String, Object> param = new Hashtable<String, Object>();
		param.put("cmd", "joinForWap");
		param.put("user_mdn", "01011112222");
		param.put("mdn_corp", "SKT");
		param.put("sp_auth_key", "getSpAuthKey()");
		param.put("sp_id", "OMP10000");
		param.put("resp_type", "2");
		param.put("resp_flow", "resp");

		/**
		 * TODO IDP 회원 기가입 상태인 경우 (IDP 연동) 무선회원 해지
		 */

		/**
		 * TODO (SC 연동) 회원 정보 등록
		 */

		/**
		 * TODO (SC 연동) 휴대기기 정보 등록
		 */

		CreateByMdnRes result = new CreateByMdnRes();
		result.setUserKey("12321423543464567457");

		return result;

	}

	@Override
	public CreateByAgreementRes createByAgreement(HeaderVo headerVo, CreateByAgreementReq req) {

		CreateByAgreementRes result = new CreateByAgreementRes();
		result.setUserKey("12321423543464567457");

		return result;
	}

}
