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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.external.client.idp.vo.IDPReceiverM;
import com.skplanet.storeplatform.external.client.idp.vo.ImIDPReceiverM;
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
import com.skplanet.storeplatform.sac.client.member.vo.common.DeviceInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.HeaderVo;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateByAgreementReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateByAgreementRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateByMdnReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateByMdnRes;
import com.skplanet.storeplatform.sac.member.common.MemberCommonComponent;
import com.skplanet.storeplatform.sac.member.common.MemberConstants;
import com.skplanet.storeplatform.sac.member.common.idp.constants.IDPConstants;
import com.skplanet.storeplatform.sac.member.common.idp.repository.IDPRepository;
import com.skplanet.storeplatform.sac.member.common.idp.service.IDPService;
import com.skplanet.storeplatform.sac.member.common.idp.service.ImIDPService;
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

	@Autowired
	private ImIDPService imIdpService;

	@Autowired
	private IDPRepository idpRepository;

	private IDPReceiverM idpReceiverM;

	@Value("#{propertiesForSac['idp.im.request.operation']}")
	public String IDP_OPERATION_MODE;

	/**
	 * TODO 테넌트 아이디/시스템아이디 변경할것 헤더로 들어온다고 하던데....
	 */
	private static final String SYSTEM_ID = "S001";
	private static final String TENANT_ID = "S01";

	@Override
	public CreateByMdnRes createByMdn(HeaderVo headerVo, CreateByMdnReq req) throws Exception {

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

			/**
			 * (SC 연동) 회원 정보 등록 TODO (이슈 : sc 컴포넌트 기능이 완료되면 파라미터들 다시한번 확인)
			 */
			// SC 공통정보 setting
			CommonRequest commonRequest = new CommonRequest();
			commonRequest.setSystemID(SYSTEM_ID);
			commonRequest.setTenantID(TENANT_ID);

			// SC 사용자 기본정보 setting
			UserMbr userMbr = new UserMbr();
			userMbr.setImMbrNo(this.idpReceiverM.getResponseBody().getUser_key());
			userMbr.setImSvcNo(this.idpReceiverM.getResponseBody().getSvc_mng_num());
			userMbr.setUserType(MemberConstants.USER_STATE_MOBILE);
			userMbr.setUserMainStatus(MemberConstants.MAIN_STATUS_NORMAL);
			userMbr.setUserSubStatus(MemberConstants.SUB_STATUS_NORMAL);
			userMbr.setImRegDate(DateUtil.getToday());
			userMbr.setUserID(msisdn);
			userMbr.setUserTelecom(req.getDeviceTelecom()); // mdn, uuid 받는데로 넣는다. (SC 확인함.)

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
			if (StringUtils.equals(req.getIsParent(), MemberConstants.USE_Y)) {
				MbrLglAgent mbrLglAgent = new MbrLglAgent();
				mbrLglAgent.setIsParent(MemberConstants.USE_Y); // 법정대리인 동의 여부
				mbrLglAgent.setParentBirthDay(req.getParentBirthDay()); // 법정대리인 생년월일
				mbrLglAgent.setParentEmail(req.getParentEmail()); // 법정대리인 이메일
				mbrLglAgent.setParentMDN(req.getParentPhone()); // 법정대리인 전화번호
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
			 * 
			 * TODO DeviceInfo 정보 넘겨서 반대리님
			 */
			DeviceInfo deviceInfo = new DeviceInfo();
			this.mcc.insertDeviceInfo(createUserResponse.getUserKey(), deviceInfo);
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
	public CreateByAgreementRes createByAgreement(HeaderVo headerVo, CreateByAgreementReq req) throws Exception {

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
		UserRes userRes = this.mcc.getMappingInfo(req.getDeviceId(), req.getDeviceIdType());
		LOGGER.debug("## userRes : {}" + userRes.toString());

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
		LOGGER.debug("############## this.IDP_OPERATION_MODE : {}", this.IDP_OPERATION_MODE);
		// param.put("operation_mode", this.IDP_OPERATION_MODE);
		param.put("key_type", "2"); // 1=IM통합서비스번호, 2=IM통합ID
		param.put("key", req.getUserId());
		param.put("user_mdn", sbUserPhone.toString());
		param.put("join_sst_list", MemberConstants.SSO_SST_CD_TSTORE + ",TAC001^TAC002^TAC003^TAC004^TAC005," + DateUtil.getToday() + "," + DateUtil.getTime());
		param.put("user_mdn_auth_key", this.idpRepository.makePhoneAuthKey(sbUserPhone.toString()));
		param.put("ocb_join_code", "N"); // 통합포인트 가입 여부 Y=가입, N=미가입
		param.put("modify_req_date", DateUtil.getToday());
		param.put("modify_req_time", DateUtil.getTime());
		LOGGER.debug("## param : {}", param.entrySet());
		ImIDPReceiverM imIDPReceiverM = this.imIdpService.agreeUser(param);
		LOGGER.debug("## Im Result Code   : {}", imIDPReceiverM.getResponseHeader().getResult());
		LOGGER.debug("## Im Result Text   : {}", imIDPReceiverM.getResponseHeader().getResult_text());

		if (StringUtils.equals(imIDPReceiverM.getResponseHeader().getResult(), "00000")) {

			LOGGER.debug("## Im user_key      : {}", imIDPReceiverM.getResponseBody().getUser_key());
			LOGGER.debug("## Im im_int_svc_no : {}", imIDPReceiverM.getResponseBody().getIm_int_svc_no());
			LOGGER.debug("## Im user_tn       : {}", imIDPReceiverM.getResponseBody().getUser_tn());
			LOGGER.debug("## Im user_email    : {}", imIDPReceiverM.getResponseBody().getUser_email());

			/**
			 * 통합 ID 기본 프로파일 조회 (통합ID 회원) 프로파일 조회 - 이름, 생년월일
			 */
			this.imIdpService.userInfoIdpSearchServer("파라미터 넣어야함 - imServiceNo");

			// sMbrNm = mapParse.get("user_name");
			// sBirthDt = mapParse.get("user_birthday");

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

	// /**
	// * <pre>
	// * 기존 As-Is 소스 그대로 로직 옴김......
	// * TODO 기타 파트에서 제공해야될 API 로써 삭제되야될 메서드임..!!!!!
	// * TODO 기존 내부로직 분석하여 임시로 넣어둠.................!!!!!
	// * </pre>
	// *
	// * @param pReqParam
	// * mdn or uuid 값
	// * @param type
	// * 타입
	// * @return UserRes
	// * @throws Exception
	// * Exception
	// */
	// public UserRes getMappingInfo(String pReqParam, String type) throws Exception {
	//
	// LOGGER.debug("## ================================== 고객정보조회");
	//
	// UserRes userRes = new UserRes();
	// /**
	// * 1. 모번호 조회 (989 일 경우만)
	// */
	// String opmdMdn = this.mcc.getOpmdMdnInfo(pReqParam);
	// LOGGER.info("### opmdMdn : {}", opmdMdn);
	//
	// /**
	// * TODO HEADER 정보로 바꿀것....
	// */
	// // logger.debug("## DeviceInfo : {}", headerVo.getxSacDeviceInfo());
	// Device device = this.mcc.getPhoneInfo("LG-SU910");
	// LOGGER.debug("## ua_cd : {}", device.getUaCd());
	//
	// /**
	// * MBR_ID 로 회원 기본 정보 조회
	// */
	// CommonRequest commonRequest = new CommonRequest();
	// commonRequest.setSystemID(SYSTEM_ID);
	// commonRequest.setTenantID(TENANT_ID);
	// List<KeySearch> keySearchList = new ArrayList<KeySearch>();
	// KeySearch keySearch = new KeySearch();
	// keySearch.setKeyString(pReqParam);
	// keySearch.setKeyType(MemberConstants.KEY_TYPE_MBR_ID);
	// keySearchList.add(keySearch);
	//
	// SearchUserRequest searchUserRequest = new SearchUserRequest();
	// searchUserRequest.setCommonRequest(commonRequest);
	// searchUserRequest.setKeySearchList(keySearchList);
	// SearchUserResponse searchUserResponse = this.userSCI.searchUser(searchUserRequest);
	// LOGGER.debug("@@@ Response : {}", searchUserResponse.toString());
	// LOGGER.debug("@@@ Response : {}", searchUserResponse.getUserMbr().getImMbrNo());
	//
	// // if (StringUtils.isNotEmpty(sPreImIntSvcNo)) {
	// // mapUrl.put("cmd", "getMdnInfoIDP");
	// // mapUrl.put("mdn", sMdn);
	// //
	// // } else {
	// // mapUrl.put("cmd", "findProfileForWap");
	// // mapUrl.put("key", sMdn);
	// // mapUrl.put("key_type", "1");
	// // }
	//
	// /**
	// * OneID - getMdnInfoIDP IDP - findProfileForWap
	// */
	//
	// return userRes;
	// }

}
