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
import java.util.Hashtable;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.util.StringUtil;
import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;
import com.skplanet.storeplatform.member.client.common.vo.MbrClauseAgree;
import com.skplanet.storeplatform.member.client.user.sci.UserSCI;
import com.skplanet.storeplatform.member.client.user.sci.vo.CreateUserRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.CreateUserResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.UserMbr;
import com.skplanet.storeplatform.sac.client.member.vo.common.AgreementInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.HeaderVo;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateByAgreementReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateByAgreementRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateByMdnReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateByMdnRes;
import com.skplanet.storeplatform.sac.external.idp.service.IdpSacService;
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
	private UserSCI userSCI;

	@Autowired
	private IdpSacService idpService;

	@Override
	public CreateByMdnRes createByMdn(HeaderVo headerVo, CreateByMdnReq req) throws Exception {

		CreateByMdnRes response = new CreateByMdnRes();

		/**
		 * 모번호 조회 (989 일 경우만)
		 */
		String opmdMdn = this.mcc.getOpmdMdnInfo(req.getDeviceId());
		logger.info("### opmdMdn : " + opmdMdn);

		/**
		 * 약관 목록 조회 TODO ((( 테넌트ID 하드코딩을 변경해야 한다. ))) DB 약관목록과 비교하여 상의 할경우 익셉션을 발생시킨다.
		 */
		List<ClauseDTO> clauseDTO = this.mcc.getMandAgreeList("S01");
		StringBuffer dbAgreeInfo = new StringBuffer();
		for (ClauseDTO dto : clauseDTO) {
			dbAgreeInfo.append(dto.getClauseItemCd());
		}

		/**
		 * TODO 리스트 Sort 해야함
		 */
		StringBuffer reqAgreeInfo = new StringBuffer();
		for (AgreementInfo info : req.getAgreementList()) {
			if (StringUtils.equals(info.getIsExtraAgreement(), "Y")) {
				reqAgreeInfo.append(info.getExtraAgreementId());
			}
		}

		logger.info("## dbAgreeInfo  : {}", dbAgreeInfo);
		logger.info("## reqAgreeInfo : {}", reqAgreeInfo);
		if (!StringUtils.equals(dbAgreeInfo.toString(), reqAgreeInfo.toString())) {
			logger.error("## 필수 약관 미동의");
			throw new RuntimeException("필수 약관 미동의");
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
		// IDPReceiverM idpReceiverM = this.idpService.join4Wap(req.getDeviceId());
		// IDPReceiverM idpReceiverM = new IDPReceiverM();
		// idpReceiverM.getResponseHeader().getResult();

		// 정상 가입이면................
		String resultCode = "1000";
		if (StringUtils.equals(resultCode, "1000")) {
			logger.info("## IDP 연동 성공!!!!");

			/**
			 * TODO (SC 연동) 회원 정보 등록
			 */
			// SC 공통정보 setting
			CommonRequest commonRequest = new CommonRequest();
			commonRequest.setSystemID("S001");
			commonRequest.setTenantID("S01");

			// SC 사용자 기본정보 setting
			UserMbr userMbr = new UserMbr();
			userMbr.setImMbrNo("IDP MBRNO2");
			userMbr.setUserType("US011501");// 모바일회원
			userMbr.setUserMainStatus("US010201");// 모바일전용회원
			userMbr.setUserSubStatus("US010301");// 신청
			userMbr.setImRegDate("19001212");
			userMbr.setUserID(req.getDeviceId());// mdn, uuid 받는데로 넣는다. (SC 확인함.)

			// // if() {
			// // SC 법정대리인 정보 setting
			// MbrLglAgent mbrLglAgent = new MbrLglAgent();
			// mbrLglAgent.setParentBirthDay(req.getParentBirth());
			// mbrLglAgent.setParentEmail(req.getParentEmail());
			// mbrLglAgent.setParentMDN(req.getParentMdn());
			// // }

			// SC 이용약관 정보 setting
			List<MbrClauseAgree> mbrClauseAgreeList = new ArrayList<MbrClauseAgree>();

			// SC 사용자 가입요청 setting
			CreateUserRequest createUserRequest = new CreateUserRequest();
			createUserRequest.setCommonRequest(commonRequest);
			createUserRequest.setUserMbr(userMbr);
			CreateUserResponse createUserResponse = this.userSCI.create(createUserRequest);

			logger.info("#### ResponseCode : " + createUserResponse.getCommonResponse().getResultCode());
			logger.info("#### ResponseMsg  : " + createUserResponse.getCommonResponse().getResultMessage());

			logger.info("userKey" + createUserResponse.getUserKey());
			logger.info("UserMainStatus" + createUserResponse.getUserMainStatus());
			logger.info("UserSubStatus" + createUserResponse.getUserSubStatus());

			/**
			 * 폰정보 조회
			 */
			// Device device = this.mcc.getPhoneInfo(req.getDeviceModelNo());
			// logger.info("device : {}", device.getModelNm());
			// logger.info("device : {}", device.getEngModelNm());
			/**
			 * TODO (SC 연동) 휴대기기 정보 등록
			 */

			/**
			 * TODO 구매이력 이관 여부 [기존회원Key, 신규회원 Key]
			 */
			String key = "Y";
			if (StringUtil.equals(key, "Y")) {
				/**
				 * 구매이력 이관 호출
				 */
			}

			response.setUserKey(createUserResponse.getUserKey());

		} else if (StringUtils.equals(resultCode, "2100")) {
			/**
			 * TODO IDP 회원 기가입 상태인 경우 (IDP 연동) 무선회원 해지
			 */
			logger.info("## (기가입 상태) 이미 서비스에 등록한 MDN");
			throw new RuntimeException("IDP 무선회원 가입 실패");
		} else {
			/**
			 * 2000 정의되지 않은 요청 2001 잘못된 요청 데이터 2002 필수 파라메타를 전달 받지 못함 2016 SP IP 검증 실패 2100 이미 서비스에 등록한 MDN 2216 UAPS로부터
			 * MDN의 서비스 관리번호를 얻지 못함. 4200 UAPS 연동 오류 4201 UAPS 연동 간 SKT 사용자 인증 실패 5500 IDP에서 요청수행중 오류 발생(기타오류)
			 */
			logger.info("## IDP 무선회원 가입 연동 실패");
			throw new RuntimeException("IDP 무선회원 가입 실패");
		}

		return response;

	}

	@Override
	public CreateByAgreementRes createByAgreement(HeaderVo headerVo, CreateByAgreementReq req) throws Exception {

		CreateByAgreementRes result = new CreateByAgreementRes();
		result.setUserKey("12321423543464567457");

		return result;
	}

}
