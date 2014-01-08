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
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.member.user.sci.UserSCIController;
import com.skplanet.storeplatform.sac.client.member.vo.common.AgreementInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.HeaderVo;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateByAgreementReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateByAgreementRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateByMdnReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateByMdnRes;
import com.skplanet.storeplatform.sac.member.common.MemberCommonComponent;
import com.skplanet.storeplatform.sac.member.common.vo.ClauseDTO;

/**
 * 회원 가입 서비스 인터페이스(CoreStoreBusiness) 구현체
 * 
 * Updated on : 2013. 12. 31. Updated by : 심대진, 다모아 솔루션.
 */
@Service
public class UserJoinServiceImpl implements UserJoinService {

	private static final Logger logger = LoggerFactory.getLogger(UserJoinServiceImpl.class);

	@Autowired
	private MemberCommonComponent mcc;

	@Autowired
	private UserSCIController userSCI;

	@Override
	public CreateByMdnRes createByMdn(HeaderVo headerVo, CreateByMdnReq req) throws Exception {

		/**
		 * 모번호 조회 (989 일 경우만)
		 */
		String opmdMdn = this.mcc.getOpmdMdnInfo(req.getDeviceId());
		logger.info("### opmdMdn : " + opmdMdn);

		/**
		 * 약관 목록 조회 TODO ((( 테넌트ID 하드코딩을 변경해야 한다. )))
		 * 
		 */
		List<ClauseDTO> clauseDTO = this.mcc.getMandAgreeList("S01");
		StringBuffer dbAgreeInfo = new StringBuffer();
		for (ClauseDTO dto : clauseDTO) {
			logger.info("UseYn : {} ", dto.getUseYn());
			logger.info("getMandAgreeYn : {} ", dto.getMandAgreeYn());
			logger.info("getClauseItemCd : {} ", dto.getClauseItemCd());
			dbAgreeInfo.append(dto.getClauseItemCd());
		}

		StringBuffer reqAgreeInfo = new StringBuffer();
		for (AgreementInfo info : req.getAgreementList()) {
			if (StringUtils.equals(info.getIsExtraAgreement(), "Y")) {
				reqAgreeInfo.append("");
			}
		}

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
	public CreateByAgreementRes createByAgreement(HeaderVo headerVo, CreateByAgreementReq req) throws Exception {

		CreateByAgreementRes result = new CreateByAgreementRes();
		result.setUserKey("12321423543464567457");

		return result;
	}

}
