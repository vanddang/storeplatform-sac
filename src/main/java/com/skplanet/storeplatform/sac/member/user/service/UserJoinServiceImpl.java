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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.external.client.idp.vo.IDPReceiverM;
import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;
import com.skplanet.storeplatform.member.client.common.vo.MbrClauseAgree;
import com.skplanet.storeplatform.member.client.common.vo.MbrLglAgent;
import com.skplanet.storeplatform.member.client.user.sci.UserSCI;
import com.skplanet.storeplatform.member.client.user.sci.vo.CreateUserRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.CreateUserResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.UserMbr;
import com.skplanet.storeplatform.sac.api.util.DateUtil;
import com.skplanet.storeplatform.sac.client.member.vo.common.AgreementInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.HeaderVo;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateByAgreementReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateByAgreementRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateByMdnReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateByMdnRes;
import com.skplanet.storeplatform.sac.member.common.MemberCommonComponent;
import com.skplanet.storeplatform.sac.member.common.MemberUserCode;
import com.skplanet.storeplatform.sac.member.common.idp.IDPConstants;
import com.skplanet.storeplatform.sac.member.common.idp.IDPManager;
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
	private IDPManager idpManager;

	private IDPReceiverM idpReceiverM;

	@Override
	public CreateByMdnRes createByMdn(HeaderVo headerVo, CreateByMdnReq req) throws Exception {

		CreateByMdnRes response = new CreateByMdnRes();

		/**
		 * TODO 테넌트 아이디/시스템아이디 변경할것 헤더로 들어온다고 하던데....
		 */
		String systemId = "S001";
		String tenantId = "S01";

		/**
		 * 모번호 조회 (989 일 경우만)
		 */
		String opmdMdn = this.mcc.getOpmdMdnInfo(req.getDeviceId());
		logger.info("### opmdMdn : " + opmdMdn);

		/**
		 * 필수 약관 동의여부 체크 TODO 테넌트 아이디 하드코딩 변경 해야함
		 */
		if (this.checkAgree(req.getAgreementList(), tenantId)) {
			logger.error("## 필수 약관 미동의");
			throw new RuntimeException("필수 약관 미동의");
		}

		/**
		 * (IDP 연동) 무선회원 가입
		 */
		this.idpReceiverM = this.idpManager.join4Wap(req.getDeviceId());
		logger.info("## join4Wap - Result Code : {}", this.idpReceiverM.getResponseHeader().getResult());
		logger.info("## join4Wap - Result Text : {}", this.idpReceiverM.getResponseHeader().getResult_text());

		/**
		 * 무선회원 연동 성공 여부에 따라 분기
		 */
		if (StringUtils.equals(this.idpReceiverM.getResponseHeader().getResult(), IDPConstants.IDP_RES_CODE_OK)) { // 정상가입
			logger.info("## IDP 연동 성공 ==============================================");

			/**
			 * (SC 연동) 회원 정보 등록 TODO (이슈 : sc 컴포넌트 기능이 완료되면 파라미터들 다시한번 확인)
			 */
			// SC 공통정보 setting
			CommonRequest commonRequest = new CommonRequest();
			commonRequest.setSystemID(systemId);
			commonRequest.setTenantID(tenantId);

			// SC 사용자 기본정보 setting
			UserMbr userMbr = new UserMbr();
			userMbr.setImMbrNo(this.idpReceiverM.getResponseBody().getUser_key());
			userMbr.setImSvcNo(this.idpReceiverM.getResponseBody().getSvc_mng_num());
			userMbr.setUserType(MemberUserCode.USER_TYPE_MOBILE.getValue()); // 모바일회원
			userMbr.setUserMainStatus(MemberUserCode.USER_MAIN_STATUS_1.getValue()); // 모바일전용회원
			userMbr.setUserSubStatus(MemberUserCode.USER_SUBS_TATUS_1.getValue()); // 신청
			userMbr.setImRegDate(DateUtil.getToday());
			userMbr.setUserID(req.getDeviceId());// mdn, uuid 받는데로 넣는다. (SC 확인함.)

			// SC 이용약관 정보 setting
			List<MbrClauseAgree> mbrClauseAgreeList = new ArrayList<MbrClauseAgree>();
			for (AgreementInfo info : req.getAgreementList()) {
				MbrClauseAgree mbrClauseAgree = new MbrClauseAgree();
				mbrClauseAgree.setExtraAgreementID(info.getExtraAgreementId());
				mbrClauseAgree.setExtraAgreementVersion(info.getExtraAgreementVersion());
				mbrClauseAgree.setIsExtraAgreement(info.getIsExtraAgreement());
				mbrClauseAgreeList.add(mbrClauseAgree);
			}

			if (StringUtils.isNotEmpty(req.getOwnBirth())) {
				// SC 법정대리인 정보 setting
				MbrLglAgent mbrLglAgent = new MbrLglAgent();
				mbrLglAgent.setParentBirthDay(req.getParentBirth());
				mbrLglAgent.setParentEmail(req.getParentEmail());
				mbrLglAgent.setParentMDN(req.getParentMdn());
			}

			// SC 사용자 가입요청 setting
			CreateUserRequest createUserRequest = new CreateUserRequest();
			createUserRequest.setCommonRequest(commonRequest);
			createUserRequest.setUserMbr(userMbr);
			createUserRequest.setMbrClauseAgree(mbrClauseAgreeList);

			/**
			 * TODO sc 응답결과가 제대로 들어 오지 않음. 확인 요청함!!!
			 */
			logger.info("## SCI Request Info : {}", createUserRequest.toString());
			CreateUserResponse createUserResponse = this.userSCI.create(createUserRequest);

			logger.info("## ResponseCode   : " + createUserResponse.getCommonResponse().getResultCode());
			logger.info("## ResponseMsg    : " + createUserResponse.getCommonResponse().getResultMessage());
			logger.info("## UserKey        : {}", createUserResponse.getUserKey());
			logger.info("## UserMainStatus : {}", createUserResponse.getUserMainStatus());
			logger.info("## UserSubStatus  : {}", createUserResponse.getUserSubStatus());

			/**
			 * TODO (SC 연동) 휴대기기 정보 등록 - 대표폰 여부 정보 포함
			 */
			/**
			 * TODO 폰정보 조회로 필요 데이타 세팅 (휴대기기 정보 등록 공통 모듈 나와봐야할듯....)
			 */
			// Device device = this.mcc.getPhoneInfo(req.getDeviceModelNo());
			// logger.info("device : {}", device.getModelNm());
			// logger.info("device : {}", device.getEngModelNm());
			logger.info("## ModelId : {}", this.idpReceiverM.getResponseBody().getModel_id());

			/**
			 * 결과 세팅
			 */
			response.setUserKey(createUserResponse.getUserKey());

		} else if (StringUtils.equals(this.idpReceiverM.getResponseHeader().getResult(), IDPConstants.IDP_RES_CODE_ALREADY_JOIN)) { // 기가입

			/**
			 * (IDP 연동) 무선회원 해지
			 */
			logger.info("## IDP 무선회원 해지 연동 Start =================");
			this.idpReceiverM = this.idpManager.secedeUser4Wap(req.getDeviceId());
			logger.info("## secedeUser4Wap - Result Code : {}", this.idpReceiverM.getResponseHeader().getResult());
			logger.info("## secedeUser4Wap - Result Text : {}", this.idpReceiverM.getResponseHeader().getResult_text());

			logger.info("## (기가입 상태) 이미 서비스에 등록한 MDN");
			throw new RuntimeException("IDP 무선회원 가입 실패");

		} else { // 기타

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

	/**
	 * <pre>
	 * 필수 약관 동의 정보를 체크 한다.
	 * </pre>
	 * 
	 * @param getAgreementList
	 *            요청 약관 동의 정보
	 * @param tenantId
	 *            테넌트 아이디
	 * @return 하나라도 미동의시 : true
	 * @throws Exception
	 */
	private boolean checkAgree(List<AgreementInfo> agreementList, String tenantId) throws Exception {

		/**
		 * DB 약관 목록 조회 sorting
		 */
		List<ClauseDTO> dbAgreementList = this.mcc.getMandAgreeList(tenantId);
		Comparator<ClauseDTO> dbComparator = new Comparator<ClauseDTO>() {
			@Override
			public int compare(ClauseDTO value1, ClauseDTO value2) {
				return value1.getClauseItemCd().compareTo(value2.getClauseItemCd());
			}
		};
		Collections.sort(dbAgreementList, dbComparator);

		// sorting data setting
		StringBuffer sortDbAgreeInfo = new StringBuffer();
		for (ClauseDTO sortInfo : dbAgreementList) {
			sortDbAgreeInfo.append(sortInfo.getClauseItemCd());
		}
		logger.info("## DB 필수약관목록 : {}", sortDbAgreeInfo);

		/**
		 * 요청 약관 목록 조회 sorting
		 */
		Comparator<AgreementInfo> comparator = new Comparator<AgreementInfo>() {
			@Override
			public int compare(AgreementInfo o1, AgreementInfo o2) {
				return o1.getExtraAgreementId().compareTo(o2.getExtraAgreementId());
			}
		};
		Collections.sort(agreementList, comparator);

		// sorting data setting
		StringBuffer sortAgreeInfo = new StringBuffer();
		for (AgreementInfo info : agreementList) {
			if (StringUtils.equals(info.getIsExtraAgreement(), "Y")) {// 약관 동의한것만 비교대상으로 세팅
				sortAgreeInfo.append(info.getExtraAgreementId());
			}
		}
		logger.info("## DB 요청약관목록 : {}", sortAgreeInfo);

		/**
		 * 정렬된 DB 약관 목록과 요청 약관 목록을 비교한다.
		 */
		if (!StringUtils.equals(sortDbAgreeInfo.toString(), sortAgreeInfo.toString())) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * TODO SC 회원 가입 연동시 데이타 세팅하는 부분 가져와서 Method로 빼자!!
	 * 
	 * <pre>
	 * SC 회원 가입 연동시 데이타 세팅
	 * </pre>
	 * 
	 * @return
	 */
	private CreateUserRequest convertCreateUserRequest() {

		CreateUserRequest createUserRequest = new CreateUserRequest();

		return createUserRequest;
	}
}
