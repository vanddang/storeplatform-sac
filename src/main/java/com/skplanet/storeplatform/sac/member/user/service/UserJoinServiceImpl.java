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
import com.skplanet.storeplatform.external.client.uaps.vo.UserRes;
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
import com.skplanet.storeplatform.sac.common.vo.Device;
import com.skplanet.storeplatform.sac.member.common.MemberCommonComponent;
import com.skplanet.storeplatform.sac.member.common.MemberConstants;
import com.skplanet.storeplatform.sac.member.common.idp.constants.IDPConstants;
import com.skplanet.storeplatform.sac.member.common.idp.service.IDPService;
import com.skplanet.storeplatform.sac.member.common.vo.ClauseDTO;

/**
 * 회원 가입 서비스 인터페이스(CoreStoreBusiness) 구현체
 * 
 * Updated on : 2013. 12. 31. Updated by : 심대진, 다모아 솔루션.
 */
@Service
public class UserJoinServiceImpl implements UserJoinService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserJoinServiceImpl.class);

	@Autowired
	private MemberCommonComponent mcc;

	@Autowired
	private UserSCI userSCI;

	@Autowired
	private IDPService idpService;

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
		LOGGER.info("### opmdMdn : {}", opmdMdn);

		/**
		 * 필수 약관 동의여부 체크 TODO 테넌트 아이디 하드코딩 변경 해야함
		 */
		if (this.checkAgree(req.getAgreementList(), tenantId)) {
			LOGGER.error("## 필수 약관 미동의");
			throw new RuntimeException("회원 가입 실패 - 필수 약관 미동의");
		}

		/**
		 * (IDP 연동) 무선회원 가입
		 */
		this.idpReceiverM = this.idpService.join4Wap(req.getDeviceId());
		LOGGER.info("## join4Wap - Result Code : {}", this.idpReceiverM.getResponseHeader().getResult());
		LOGGER.info("## join4Wap - Result Text : {}", this.idpReceiverM.getResponseHeader().getResult_text());

		/**
		 * 무선회원 연동 성공 여부에 따라 분기
		 */
		if (StringUtils.equals(this.idpReceiverM.getResponseHeader().getResult(), IDPConstants.IDP_RES_CODE_OK)) { // 정상가입
			LOGGER.info("## IDP 연동 성공 ==============================================");

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
			userMbr.setUserType(MemberConstants.USER_STATE_MOBILE);
			userMbr.setUserMainStatus(MemberConstants.MAIN_STATUS_NORMAL);
			userMbr.setUserSubStatus(MemberConstants.SUB_STATUS_NORMAL);
			userMbr.setImRegDate(DateUtil.getToday());
			userMbr.setUserID(req.getDeviceId());
			/** mdn, uuid 받는데로 넣는다. (SC 확인함.) */
			userMbr.setUserTelecom(req.getDeviceTelecom());

			// SC 이용약관 정보 setting
			List<MbrClauseAgree> mbrClauseAgreeList = new ArrayList<MbrClauseAgree>();
			for (AgreementInfo info : req.getAgreementList()) {
				MbrClauseAgree mbrClauseAgree = new MbrClauseAgree();
				mbrClauseAgree.setExtraAgreementID(info.getExtraAgreementId());
				mbrClauseAgree.setExtraAgreementVersion(info.getExtraAgreementVersion());
				mbrClauseAgree.setIsExtraAgreement(info.getIsExtraAgreement());
				mbrClauseAgreeList.add(mbrClauseAgree);
			}

			// SC 법정대리인 정보 setting
			if (StringUtils.isNotEmpty(req.getOwnBirth())) {
				MbrLglAgent mbrLglAgent = new MbrLglAgent();
				mbrLglAgent.setIsParent(MemberConstants.USE_Y); // 법정대리인 동의 여부
				mbrLglAgent.setParentBirthDay(req.getParentBirth()); // 법정대리인 생년월일
				mbrLglAgent.setParentEmail(req.getParentEmail()); // 법정대리인 이메일
				mbrLglAgent.setParentMDN(req.getParentMdn()); // 법정대리인 전화번호
				mbrLglAgent.setParentTelecom(req.getParentTelecom()); // 법정대리인 이동통신사
			}

			// SC 사용자 가입요청 setting
			CreateUserRequest createUserRequest = new CreateUserRequest();
			createUserRequest.setCommonRequest(commonRequest);
			createUserRequest.setUserMbr(userMbr);
			createUserRequest.setMbrClauseAgree(mbrClauseAgreeList);

			/**
			 * TODO sc 응답결과가 제대로 들어 오지 않음. userKey 확인 요청함!!!
			 */
			LOGGER.info("## SC Request commonRequest      : {}", createUserRequest.getCommonRequest().toString());
			LOGGER.info("## SC Request userMbr            : {}", createUserRequest.getUserMbr().toString());
			LOGGER.info("## SC Request mbrClauseAgreeList : {}", createUserRequest.getMbrClauseAgree().toString());
			CreateUserResponse createUserResponse = this.userSCI.create(createUserRequest);

			LOGGER.info("## ResponseCode   : {}", createUserResponse.getCommonResponse().getResultCode());
			LOGGER.info("## ResponseMsg    : {}", createUserResponse.getCommonResponse().getResultMessage());
			LOGGER.info("## UserKey        : {}", createUserResponse.getUserKey());
			LOGGER.info("## UserMainStatus : {}", createUserResponse.getUserMainStatus());
			LOGGER.info("## UserSubStatus  : {}", createUserResponse.getUserSubStatus());

			if (!StringUtils.equals(createUserResponse.getCommonResponse().getResultCode(), MemberConstants.RESULT_SUCCES)) {

				LOGGER.info("## 사용자 회원 가입 실패 ===========================");
				throw new RuntimeException("사용자 회원 가입 실패");

			}

			/**
			 * TODO (SC 연동) 휴대기기 정보 등록 - 대표폰 여부 정보 포함
			 */
			/**
			 * TODO 폰정보 조회로 필요 데이타 세팅 (휴대기기 정보 등록 공통 모듈 나와봐야할듯....)
			 */
			// Device device = this.mcc.getPhoneInfo(req.getDeviceModelNo());
			// logger.info("device : {}", device.getModelNm());
			// logger.info("device : {}", device.getEngModelNm());
			LOGGER.info("## ModelId : {}", this.idpReceiverM.getResponseBody().getModel_id());

			/**
			 * 결과 세팅
			 */
			response.setUserKey(createUserResponse.getUserKey());

		} else if (StringUtils.equals(this.idpReceiverM.getResponseHeader().getResult(), IDPConstants.IDP_RES_CODE_ALREADY_JOIN)) { // 기가입
			LOGGER.info("## (기가입 상태) 이미 서비스에 등록한 MDN");

			/**
			 * (IDP 연동) 무선회원 해지
			 */
			LOGGER.info("## IDP 무선회원 해지 연동 Start =================");
			this.idpReceiverM = this.idpService.secedeUser4Wap(req.getDeviceId());
			LOGGER.info("## secedeUser4Wap - Result Code : {}", this.idpReceiverM.getResponseHeader().getResult());
			LOGGER.info("## secedeUser4Wap - Result Text : {}", this.idpReceiverM.getResponseHeader().getResult_text());

			throw new RuntimeException("IDP 무선회원 가입 실패");

		} else { // 기타

			LOGGER.info("## IDP 무선회원 가입 연동 실패");
			throw new RuntimeException("IDP 무선회원 가입 실패");

		}

		return response;

	}

	@Override
	public CreateByAgreementRes createByAgreement(HeaderVo headerVo, CreateByAgreementReq req) throws Exception {

		CreateByAgreementRes response = new CreateByAgreementRes();

		/**
		 * TODO 테넌트 아이디/시스템아이디 변경할것 헤더로 들어온다고 하던데....
		 */
		String systemId = "S001";
		String tenantId = "S01";

		/**
		 * 필수 약관 동의여부 체크 TODO 테넌트 아이디 하드코딩 변경 해야함
		 */
		if (this.checkAgree(req.getAgreementList(), tenantId)) {
			LOGGER.error("## 필수 약관 미동의");
			throw new RuntimeException("회원 가입 실패 - 필수 약관 미동의");
		}

		/**
		 * TODO 기타 파트에서 제공하는..(1월 27일 제공 예정이라함....) 모번호조회 할때 us_cd도 같이 내려 주는 API 호출로 바꿔야함. (1번 2번 3번 항목)
		 * UAPSSCI.getMappingInfo
		 */
		this.getMappingInfo(req.getDeviceId(), "mdn");

		/**
		 * TODO 결과정보 셋팅 해야함.
		 */
		response.setUserKey("12321423543464567457");

		return response;
	}

	/**
	 * <pre>
	 * 필수 약관 동의 정보를 체크 한다.
	 * </pre>
	 * 
	 * @param agreementList
	 *            요청 약관 동의 정보
	 * @param tenantId
	 *            테넌트 아이디
	 * @return 하나라도 미동의시 : true
	 * @throws Exception
	 *             Exception
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
		LOGGER.info("## DB 필수약관목록 : {}", sortDbAgreeInfo);

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
			if (StringUtils.equals(info.getIsExtraAgreement(), MemberConstants.USE_Y)) { // 약관 동의한것만 비교대상으로 세팅
				sortAgreeInfo.append(info.getExtraAgreementId());
			}
		}
		LOGGER.info("## DB 요청약관목록 : {}", sortAgreeInfo);

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
	 * <pre>
	 * TODO 기타 파트에서 제공해야될 API 로써 삭제되야될 메서드임..!!!!!
	 * TODO 기존 내부로직 분석하여 임시로 넣어둠.................!!!!!
	 * </pre>
	 * 
	 * @param pReqParam
	 *            mdn or uuid 값
	 * @param type
	 *            타입
	 * @return UserRes
	 * @throws Exception
	 *             Exception
	 */
	public UserRes getMappingInfo(String pReqParam, String type) throws Exception {

		LOGGER.debug("## ================================== 고객정보조회");

		UserRes userRes = new UserRes();
		/**
		 * 1. 모번호 조회 (989 일 경우만)
		 */
		String opmdMdn = this.mcc.getOpmdMdnInfo(pReqParam);
		LOGGER.info("### opmdMdn : {}", opmdMdn);

		/**
		 * TODO HEADER 정보로 바꿀걸....
		 */
		// logger.debug("## DeviceInfo : {}", headerVo.getxSacDeviceInfo());
		Device device = this.mcc.getPhoneInfo("LG-SU910");
		LOGGER.debug("Device : {}", device.getUaCd());

		/**
		 * OneID - getMdnInfoIDP IDP - findProfileForWap
		 */

		return userRes;
	}

}
