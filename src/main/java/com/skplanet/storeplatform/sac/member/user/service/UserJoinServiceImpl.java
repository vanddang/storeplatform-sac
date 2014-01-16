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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.external.client.idp.vo.IDPReceiverM;
import com.skplanet.storeplatform.external.client.idp.vo.ImIDPReceiverM;
import com.skplanet.storeplatform.external.client.uaps.vo.UserRes;
import com.skplanet.storeplatform.framework.core.util.StringUtil;
import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;
import com.skplanet.storeplatform.member.client.common.vo.MbrClauseAgree;
import com.skplanet.storeplatform.member.client.common.vo.MbrLglAgent;
import com.skplanet.storeplatform.member.client.user.sci.UserSCI;
import com.skplanet.storeplatform.member.client.user.sci.vo.CreateUserRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.CreateUserResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.UserMbr;
import com.skplanet.storeplatform.sac.api.util.DateUtil;
import com.skplanet.storeplatform.sac.client.member.vo.common.AgreementInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.DeviceInfo;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateByAgreementReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateByAgreementRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateByMdnReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateByMdnRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.member.common.MemberCommonComponent;
import com.skplanet.storeplatform.sac.member.common.MemberConstants;
import com.skplanet.storeplatform.sac.member.common.idp.constants.IDPConstants;
import com.skplanet.storeplatform.sac.member.common.idp.constants.ImIDPConstants;
import com.skplanet.storeplatform.sac.member.common.idp.repository.IDPRepository;
import com.skplanet.storeplatform.sac.member.common.idp.service.IDPService;
import com.skplanet.storeplatform.sac.member.common.idp.service.ImIDPService;
import com.skplanet.storeplatform.sac.member.common.vo.Clause;
import com.skplanet.storeplatform.sac.member.common.vo.Device;

/**
 * 회원 가입 서비스 인터페이스(CoreStoreBusiness) 구현체
 * 
 * Updated on : 2013. 12. 31. Updated by : 심대진, 다모아 솔루션.
 */
@Service
@Transactional
public class UserJoinServiceImpl implements UserJoinService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserJoinServiceImpl.class);

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

	/**
	 * IDP 연동 결과.
	 */
	private IDPReceiverM idpReceiverM;

	/**
	 * 통합 IDP 연동 결과.
	 */
	private ImIDPReceiverM imIDPReceiverM;

	/**
	 * TODO 테넌트 아이디/시스템아이디 변경할것 헤더로 들어온다고 하던데....
	 */
	private static final String SYSTEM_ID = "S001";
	private static final String TENANT_ID = "S01";

	@Override
	public CreateByMdnRes createByMdn(SacRequestHeader sacHeader, CreateByMdnReq req) throws Exception {

		CreateByMdnRes response = new CreateByMdnRes();

		/**
		 * 모번호 조회 (989 일 경우만)
		 */
		String msisdn = this.mcc.getOpmdMdnInfo(req.getDeviceId());
		LOGGER.info("### opmdMdn : {}", msisdn);

		/**
		 * 필수 약관 동의여부 체크
		 */
		if (this.checkAgree(req.getAgreementList(), TENANT_ID)) {
			LOGGER.error("## 필수 약관 미동의");
			throw new RuntimeException("회원 가입 실패 - 필수 약관 미동의");
		}

		/**
		 * (IDP 연동) 무선회원 가입
		 */
		this.idpReceiverM = this.idpService.join4Wap(msisdn);
		LOGGER.info("## join4Wap - Result Code : {}", this.idpReceiverM.getResponseHeader().getResult());
		LOGGER.info("## join4Wap - Result Text : {}", this.idpReceiverM.getResponseHeader().getResult_text());

		/**
		 * 무선회원 연동 성공 여부에 따라 분기
		 */
		if (StringUtils.equals(this.idpReceiverM.getResponseHeader().getResult(), IDPConstants.IDP_RES_CODE_OK)) { // 정상가입

			LOGGER.info("## IDP 연동 성공 ==============================================");

			CreateUserRequest createUserRequest = new CreateUserRequest();

			/**
			 * SC 공통정보 setting
			 */
			CommonRequest commonRequest = new CommonRequest();
			commonRequest.setSystemID(SYSTEM_ID);
			commonRequest.setTenantID(TENANT_ID);
			createUserRequest.setCommonRequest(commonRequest);
			LOGGER.info("## SC Request commonRequest : {}", createUserRequest.getCommonRequest().toString());

			/**
			 * SC 사용자 기본정보 setting
			 */
			UserMbr userMbr = new UserMbr();
			userMbr.setImMbrNo(this.idpReceiverM.getResponseBody().getUser_key());
			userMbr.setImSvcNo(this.idpReceiverM.getResponseBody().getSvc_mng_num());
			userMbr.setUserType(MemberConstants.USER_TYPE_MOBILE);
			userMbr.setUserMainStatus(MemberConstants.MAIN_STATUS_NORMAL);
			userMbr.setUserSubStatus(MemberConstants.SUB_STATUS_NORMAL);
			userMbr.setImRegDate(DateUtil.getToday());
			userMbr.setUserID(msisdn);
			userMbr.setUserTelecom(req.getDeviceTelecom()); // mdn, uuid 받는데로 넣는다. (SC 확인함.)
			userMbr.setIsParent(req.getIsParent());
			userMbr.setRegDate(DateUtil.getToday() + DateUtil.getTime());
			createUserRequest.setUserMbr(userMbr);
			LOGGER.info("## SC Request userMbr : {}", createUserRequest.getUserMbr().toString());

			/**
			 * SC 이용약관 정보 setting
			 */
			List<MbrClauseAgree> mbrClauseAgreeList = new ArrayList<MbrClauseAgree>();
			for (AgreementInfo info : req.getAgreementList()) {
				MbrClauseAgree mbrClauseAgree = new MbrClauseAgree();
				mbrClauseAgree.setExtraAgreementID(info.getExtraAgreementId());
				mbrClauseAgree.setExtraAgreementVersion(info.getExtraAgreementVersion());
				mbrClauseAgree.setIsExtraAgreement(info.getIsExtraAgreement());
				mbrClauseAgree.setRegDate(DateUtil.getToday());
				mbrClauseAgreeList.add(mbrClauseAgree);
			}
			createUserRequest.setMbrClauseAgree(mbrClauseAgreeList);
			LOGGER.info("## SC Request mbrClauseAgreeList : {}", createUserRequest.getMbrClauseAgree().toString());

			/**
			 * SC 법정대리인 정보 setting
			 */
			if (StringUtils.equals(req.getIsParent(), MemberConstants.USE_Y)) {

				MbrLglAgent mbrLglAgent = new MbrLglAgent();
				mbrLglAgent.setIsParent(req.getIsParent()); // 법정대리인 동의 여부
				mbrLglAgent.setParentRealNameMethod(req.getParentRealNameMethod()); // 법정대리인 인증방법코드
				mbrLglAgent.setParentName(req.getParentName()); // 법정대리인 이름
				mbrLglAgent.setParentType(req.getParentType()); // 법정대리인 관계
				mbrLglAgent.setParentDate(req.getParentDate()); // 법정대리인 동의일시
				mbrLglAgent.setParentEmail(req.getParentEmail()); // 법정대리인 Email
				mbrLglAgent.setParentBirthDay(req.getParentBirthDay()); // 법정대리인 생년월일
				mbrLglAgent.setParentTelecom(req.getParentTelecom()); // 법정대리인 통신사 코드
				mbrLglAgent.setParentMDN(req.getParentPhone()); // 법정대리인 전화번호
				mbrLglAgent.setParentCI(req.getParentCi()); // 법정대리인 CI
				mbrLglAgent.setParentRealNameDate(req.getParentRealNameDate()); // 법정대리인 인증 일시
				mbrLglAgent.setParentRealNameSite(req.getParentRealNameSite()); // 법정대리인 실명인증사이트 코드
				createUserRequest.setMbrLglAgent(mbrLglAgent);
				LOGGER.info("## SC Request mbrLglAgent : {}", createUserRequest.getMbrLglAgent().toString());

			}

			/**
			 * SC 사용자 가입요청
			 */
			CreateUserResponse createUserResponse = this.userSCI.create(createUserRequest);

			LOGGER.info("## ResponseCode   : {}", createUserResponse.getCommonResponse().getResultCode());
			LOGGER.info("## ResponseMsg    : {}", createUserResponse.getCommonResponse().getResultMessage());
			LOGGER.info("## UserKey        : {}", createUserResponse.getUserKey());
			LOGGER.info("## UserMainStatus : {}", createUserResponse.getUserMainStatus());
			LOGGER.info("## UserSubStatus  : {}", createUserResponse.getUserSubStatus());

			if (!StringUtils.equals(createUserResponse.getCommonResponse().getResultCode(),
					MemberConstants.RESULT_SUCCES)) {

				LOGGER.info("## 사용자 회원 가입 실패 ===========================");
				throw new RuntimeException("사용자 회원 가입 실패");

			}

			DeviceInfo deviceInfo = new DeviceInfo();

			/**
			 * 폰정보 조회 (Phone ModelCode)
			 */
			Device deviceDTO = this.mcc.getPhoneInfo(sacHeader.getDeviceHeader().getModel());
			if (deviceDTO == null) {

				/**
				 * 미지원 단말 setting
				 */
				deviceInfo.setUacd(MemberConstants.NOT_SUPPORT_HP_UACODE); // UA 코드
				deviceInfo.setDeviceTelecom(MemberConstants.DEVICE_TELECOM_NSH); // 이동 통신사
				deviceInfo.setDeviceModelNo(MemberConstants.NOT_SUPPORT_HP_MODEL_CD); // 기기 모델 번호
				deviceInfo.setDeviceNickName(MemberConstants.NOT_SUPPORT_HP_MODEL_NM); // 기기명

			} else {

				// UA코드 setting
				if (StringUtils.equals(req.getDeviceTelecom(), MemberConstants.DEVICE_TELECOM_SKT)) {

					/**
					 * UAPS 연동하여 uacd를 세팅한다.
					 * 
					 * TODO UAPS 방화벽 이슈가 종료 되면 로직 테스트해서 넣을것....
					 */
					String uacd = this.mcc.getMappingInfo(req.getDeviceId(), "mdn").getDeviceModel();
					LOGGER.debug("## UAPS UA 코드 : {}", deviceDTO.getUaCd());
					deviceInfo.setUacd(deviceDTO.getUaCd()); // UA 코드

				} else {

					/**
					 * DB 정보로 uacd를 세팅한다.
					 */
					LOGGER.debug("## DB UA 코드 : {}", deviceDTO.getUaCd());
					deviceInfo.setUacd(deviceDTO.getUaCd()); // UA 코드

				}

				deviceInfo.setDeviceTelecom(req.getDeviceTelecom()); // 이동 통신사
				deviceInfo.setDeviceModelNo(deviceDTO.getDeviceModelCd()); // 기기 모델 번호
				deviceInfo.setDeviceNickName(deviceDTO.getModelNm()); // 기기명

				/**
				 * UUID 일때 이동통신사코드가 IOS가 아니면 로그찍는다. (테넌트에서 잘못 올려준 데이타.)
				 */
				if (StringUtil.equals(req.getDeviceIdType(), MemberConstants.DEVICE_ID_TYPE_UUID)) {
					if (!StringUtil.equals(req.getDeviceTelecom(), MemberConstants.DEVICE_TELECOM_IOS)) {
						LOGGER.warn("###############################################################################");
						LOGGER.warn("##### UUID 일때는 무조건 이동통신사 코드를 IOS로 줘야 한다. AI-IS 로직 반영.... #####");
						LOGGER.warn("###############################################################################");
					}
				}

			}

			/**
			 * 휴대기기 등록정보 setting
			 */
			deviceInfo.setIsPrimary(MemberConstants.USE_Y);
			deviceInfo.setDeviceId(msisdn);
			deviceInfo.setDeviceIdType("msisdn");
			deviceInfo.setJoinId(req.getJoinId());
			deviceInfo.setNativeId(req.getImei());
			deviceInfo.setImMngNum(this.idpReceiverM.getResponseBody().getSvc_mng_num());
			deviceInfo.setDeviceModelNo(this.idpReceiverM.getResponseBody().getModel_id());
			deviceInfo.setIsAuthenticated(MemberConstants.USE_Y);
			deviceInfo.setAuthenticationDate(DateUtil.getToday());
			deviceInfo.setIsUsed(MemberConstants.USE_Y);
			LOGGER.debug("## deviceInfo : {}", deviceInfo.toString());

			/**
			 * 휴대기기 등록 submodule 호출.
			 */
			try {
				this.mcc.insertDeviceInfo(SYSTEM_ID, TENANT_ID, createUserResponse.getUserKey(), deviceInfo);
			} catch (Exception e) {
				throw new RuntimeException("## 휴대기기 등록실패!!!! submodule ERROR");
			}

			/**
			 * 결과 세팅
			 */
			response.setUserKey(createUserResponse.getUserKey());

		} else if (StringUtils.equals(this.idpReceiverM.getResponseHeader().getResult(),
				IDPConstants.IDP_RES_CODE_ALREADY_JOIN)) { // 기가입
			LOGGER.info("## (기가입 상태) 이미 서비스에 등록한 MDN");

			/**
			 * (IDP 연동) 무선회원 해지
			 */
			LOGGER.info("## IDP 무선회원 해지 연동 Start =================");
			this.idpReceiverM = this.idpService.secedeUser4Wap(msisdn);
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
	public CreateByAgreementRes createByAgreementId(SacRequestHeader sacHeader, CreateByAgreementReq req) throws Exception {

		CreateByAgreementRes response = new CreateByAgreementRes();

		/**
		 * 필수 약관 동의여부 체크
		 */
		if (this.checkAgree(req.getAgreementList(), TENANT_ID)) {
			LOGGER.error("## 필수 약관 미동의");
			throw new RuntimeException("회원 가입 실패 - 필수 약관 미동의");
		}

		/**
		 * (OneID 연동) 이용동의 가입
		 */
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("cmd", "TXAgreeUserIDP");
		param.put("key_type", "2"); // 1=IM통합서비스번호, 2=IM통합ID
		param.put("key", req.getUserId());
		param.put("join_sst_list", MemberConstants.SSO_SST_CD_TSTORE + ",TAC001^TAC002^TAC003^TAC004^TAC005," + DateUtil.getToday() + "," + DateUtil.getTime());
		param.put("ocb_join_code", "N"); // 통합포인트 가입 여부 Y=가입, N=미가입

		this.imIDPReceiverM = this.imIdpService.agreeUser(param);
		LOGGER.debug("## Im Result Code   : {}", this.imIDPReceiverM.getResponseHeader().getResult());
		LOGGER.debug("## Im Result Text   : {}", this.imIDPReceiverM.getResponseHeader().getResult_text());

		/**
		 * 이용동의 가입 성공시
		 */
		if (StringUtils.equals(ImIDPConstants.IDP_RES_CODE_OK, "1000X000")) {

			LOGGER.debug("## Im user_key      : {}", this.imIDPReceiverM.getResponseBody().getUser_key());
			LOGGER.debug("## Im im_int_svc_no : {}", this.imIDPReceiverM.getResponseBody().getIm_int_svc_no());
			LOGGER.debug("## Im user_tn       : {}", this.imIDPReceiverM.getResponseBody().getUser_tn());
			LOGGER.debug("## Im user_email    : {}", this.imIDPReceiverM.getResponseBody().getUser_email());

			// /**
			// * 통합 ID 기본 프로파일 조회 (통합ID 회원) 프로파일 조회 - 이름, 생년월일
			// */
			// this.imIDPReceiverM = this.imIdpService.userInfoIdpSearchServer(this.imIDPReceiverM.getResponseBody()
			// .getIm_int_svc_no());
			// LOGGER.debug("## Im Result Code   : {}", this.imIDPReceiverM.getResponseHeader().getResult());
			// LOGGER.debug("## Im Result Text   : {}", this.imIDPReceiverM.getResponseHeader().getResult_text());
			//
			// /**
			// * TODO 조회 성공시 이름과 생년월일을 받아 온다. (등록시 데이타로 넣는다.)
			// */
			// if (StringUtils.equals(ImIDPConstants.IDP_RES_CODE_OK, "1000X000")) {
			//
			// LOGGER.debug("## Im user_name     : {}", this.imIDPReceiverM.getResponseBody().getUser_name());
			// LOGGER.debug("## Im user_birthday : {}", this.imIDPReceiverM.getResponseBody().getUser_birthday());
			//
			// }

		} else {

			LOGGER.error("## 통합 서비스 이용동의 가입 실패!!");
			throw new RuntimeException("통합 서비스 이용동의 가입 실패");

		}

		return response;

	}

	@Override
	public CreateByAgreementRes createByAgreementDevice(SacRequestHeader sacHeader, CreateByAgreementReq req) throws Exception {

		CreateByAgreementRes response = new CreateByAgreementRes();

		/**
		 * 필수 약관 동의여부 체크
		 */
		if (this.checkAgree(req.getAgreementList(), TENANT_ID)) {
			LOGGER.error("## 필수 약관 미동의");
			throw new RuntimeException("회원 가입 실패 - 필수 약관 미동의");
		}

		/**
		 * TODO 기타 파트에서 제공하는..(1월 27일 제공 예정이라함....)
		 * 
		 * TODO 모번호조회 할때 us_cd, IM_INT_SVC_NO(서비스 관리 번호)
		 */
		UserRes userRes = this.mcc.getMappingInfo(req.getDeviceId(), "mdn");

		/**
		 * 통합 IDP 연동을 위한.... Phone 정보 세팅.
		 * 
		 * TODO ua_cd 정보 유무에 따른 파라미터 정보 세팅 분기 로직 처리해야함.
		 */
		StringBuffer sbUserPhone = new StringBuffer();
		sbUserPhone.append(userRes.getMdn());
		sbUserPhone.append(",");
		sbUserPhone.append(userRes.getSvcMngNum());
		sbUserPhone.append(",");
		sbUserPhone.append(userRes.getDeviceModel());
		sbUserPhone.append(",");
		sbUserPhone.append(req.getDeviceTelecom());

		/**
		 * (OneID 연동) 이용동의 가입
		 */
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("cmd", "TXAgreeUserIDP");
		param.put("key_type", "2"); // 1=IM통합서비스번호, 2=IM통합ID
		param.put("key", req.getUserId());
		param.put("user_mdn", sbUserPhone.toString());
		param.put("join_sst_list", MemberConstants.SSO_SST_CD_TSTORE + ",TAC001^TAC002^TAC003^TAC004^TAC005,"
				+ DateUtil.getToday() + "," + DateUtil.getTime());
		param.put("user_mdn_auth_key", this.idpRepository.makePhoneAuthKey(sbUserPhone.toString()));
		param.put("ocb_join_code", "N"); // 통합포인트 가입 여부 Y=가입, N=미가입
		LOGGER.debug("## param : {}", param.entrySet());
		this.imIDPReceiverM = this.imIdpService.agreeUser(param);
		LOGGER.debug("## Im Result Code   : {}", this.imIDPReceiverM.getResponseHeader().getResult());
		LOGGER.debug("## Im Result Text   : {}", this.imIDPReceiverM.getResponseHeader().getResult_text());

		/**
		 * 이용동의 가입 성공시
		 */
		if (StringUtils.equals(ImIDPConstants.IDP_RES_CODE_OK, "1000X000")) {

			LOGGER.debug("## Im user_key      : {}", this.imIDPReceiverM.getResponseBody().getUser_key());
			LOGGER.debug("## Im im_int_svc_no : {}", this.imIDPReceiverM.getResponseBody().getIm_int_svc_no());
			LOGGER.debug("## Im user_tn       : {}", this.imIDPReceiverM.getResponseBody().getUser_tn());
			LOGGER.debug("## Im user_email    : {}", this.imIDPReceiverM.getResponseBody().getUser_email());

			/**
			 * 통합 ID 기본 프로파일 조회 (통합ID 회원) 프로파일 조회 - 이름, 생년월일
			 */
			this.imIDPReceiverM = this.imIdpService.userInfoIdpSearchServer(this.imIDPReceiverM.getResponseBody()
					.getIm_int_svc_no());
			LOGGER.debug("## Im Result Code   : {}", this.imIDPReceiverM.getResponseHeader().getResult());
			LOGGER.debug("## Im Result Text   : {}", this.imIDPReceiverM.getResponseHeader().getResult_text());

			/**
			 * TODO 조회 성공시 이름과 생년월일을 받아 온다. (등록시 데이타로 넣는다.)
			 */
			if (StringUtils.equals(ImIDPConstants.IDP_RES_CODE_OK, "1000X000")) {

				LOGGER.debug("## Im user_name     : {}", this.imIDPReceiverM.getResponseBody().getUser_name());
				LOGGER.debug("## Im user_birthday : {}", this.imIDPReceiverM.getResponseBody().getUser_birthday());

			}

		} else {

			LOGGER.error("## 통합 서비스 이용동의 가입 실패!!");
			throw new RuntimeException("통합 서비스 이용동의 가입 실패");

		}

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
	 * @return boolean
	 * @throws Exception
	 *             Exception
	 */
	private boolean checkAgree(List<AgreementInfo> agreementList, String tenantId) throws Exception {

		/**
		 * DB 약관 목록 조회 sorting
		 */
		List<Clause> dbAgreementList = this.mcc.getMandAgreeList(tenantId);
		if (dbAgreementList.size() == 0) {
			LOGGER.debug("## 체크할 필수 약관이 존재 하지 않습니다.");
			return false;
		}
		Comparator<Clause> dbComparator = new Comparator<Clause>() {
			@Override
			public int compare(Clause value1, Clause value2) {
				return value1.getClauseItemCd().compareTo(value2.getClauseItemCd());
			}
		};
		Collections.sort(dbAgreementList, dbComparator);

		// sorting data setting
		StringBuffer sortDbAgreeInfo = new StringBuffer();
		for (Clause sortInfo : dbAgreementList) {
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

}
