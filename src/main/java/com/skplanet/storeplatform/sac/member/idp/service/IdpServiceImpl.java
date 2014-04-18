package com.skplanet.storeplatform.sac.member.idp.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;

import com.skplanet.pdp.sentinel.shuttle.TLogSentinelShuttle;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.util.log.TLogUtil;
import com.skplanet.storeplatform.framework.core.util.log.TLogUtil.ShuttleSetter;
import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;
import com.skplanet.storeplatform.member.client.common.vo.KeySearch;
import com.skplanet.storeplatform.member.client.common.vo.MbrAuth;
import com.skplanet.storeplatform.member.client.common.vo.MbrClauseAgree;
import com.skplanet.storeplatform.member.client.common.vo.MbrLglAgent;
import com.skplanet.storeplatform.member.client.common.vo.MbrOneID;
import com.skplanet.storeplatform.member.client.common.vo.MbrPwd;
import com.skplanet.storeplatform.member.client.common.vo.UpdateMbrOneIDRequest;
import com.skplanet.storeplatform.member.client.common.vo.UpdateMbrOneIDResponse;
import com.skplanet.storeplatform.member.client.user.sci.UserSCI;
import com.skplanet.storeplatform.member.client.user.sci.vo.CreateUserRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.CreateUserResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.RemoveMbrOneIDRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.RemoveUserRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchUserRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchUserResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.UpdatePasswordUserRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.UpdatePasswordUserResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.UpdateRealNameRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.UpdateRealNameResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.UpdateStatusUserRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.UpdateStatusUserResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.UpdateUserRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.UpdateUserResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.UserMbr;
import com.skplanet.storeplatform.sac.api.util.DateUtil;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.sci.ChangeDisplayUserSCI;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.ChangeDisplayUserSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.common.AgreementInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.DeviceExtraInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.DeviceInfo;
import com.skplanet.storeplatform.sac.client.member.vo.user.GameCenterSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.ListDeviceReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.ListDeviceRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.RemoveMemberAmqpSacReq;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.member.common.MemberCommonComponent;
import com.skplanet.storeplatform.sac.member.common.constant.MemberConstants;
import com.skplanet.storeplatform.sac.member.common.vo.Device;
import com.skplanet.storeplatform.sac.member.idp.constant.IdpConstants;
import com.skplanet.storeplatform.sac.member.idp.vo.ImResult;
import com.skplanet.storeplatform.sac.member.user.service.DeviceService;

/**
 * IDP에서 전달되는 Provisioning 및 Rx 처리를 위한 인터페이스
 * 
 * Updated on : 2014. 1. 8. Updated by : 임재호, 인크로스.
 */
@Service
public class IdpServiceImpl implements IdpService {

	private static final Logger LOGGER = LoggerFactory.getLogger(IdpServiceImpl.class);

	public String SC_RETURN = "SC_MEM_";

	@Autowired
	private UserSCI userSCI;

	@Autowired
	private MessageSourceAccessor messageSourceAccessor; // Message Properties

	@Autowired
	private MemberCommonComponent mcc;

	@Autowired
	private DeviceService deviceService;

	@Autowired
	private ChangeDisplayUserSCI changeDisplayUserSCI;

	@Resource(name = "memberRetireAmqpTemplate")
	@Autowired
	private AmqpTemplate memberRetireAmqpTemplate;

	@Value("#{propertiesForSac['member.ogg.internal.method.iscall']}")
	public boolean isCall;

	/*
	 * 
	 * <pre> 통합회원 전환생성정보를 사이트에 배포 - CMD : RXCreateUserIDP . </pre>
	 * 
	 * @param map Request로 받은 Parameter Map
	 * 
	 * @return HashMap
	 */
	@Override
	public ImResult executeRXCreateUserIDP(HashMap<String, String> map) {
		// System.out.println("rXCreateUserIDP ------- ");
		/*
		 * map 정보중 리턴값중 이용동의 사이트정보의 old_id 값 null을 판단 신규가입 , 전환가입/변경가입/변경전환 분기처리
		 */

		LOGGER.debug("executeRXCreateUserIDP ------- Start");

		String isParentApprove = ""; // 법정 대리인 동의 여부

		String tenantId = "";
		String systemId = "";
		String userKey = ""; // 내부사용자키
		String imIntSvcNo = ""; // 통합서비스번호
		String userId = ""; // 사용자 ID
		String ocbJoinCodeYn = "N"; // 통합포인트 가입여부: 초기화 값 'N' join_sst_list에 42100 ocb 서비스사이트코드가 있을경우만 Y로 셋팅
		String joinSstCode = ""; // 가입서비스 사이트 코드
		String joinDate = ""; // 가입일자 (ONEID)
		String joinTime = ""; // 가입시간 (ONEID)
		String isRnameAuth = ""; // 실명인증 여부
		String imMemTypeCd = ""; // 통합회원 유형 코드 100: 국내회원 900: 글로벌회원
		String userType = ""; // 가입자 유형코드 1: 개인, 2:법인사업자, 3: 단체사업자, 4:개인사업자, 5사회공헌단체
		String userStatusCode = ""; // 가입자 상태 코드 10:정상 11:가인증
		String telecomCode = ""; // 통신사 코드
		String emailRecvYn = ""; // 이메일 수신 여부
		String prevMbrNoForgameCenter = ""; // 게임센터 연동을 위한 MbrNo
		String currentMbrNoForgameCenter = ""; // 게임센터 연동을 위한 MbrNo

		tenantId = map.get("tenantID").toString();
		systemId = map.get("systemID").toString();
		imIntSvcNo = map.get("im_int_svc_no").toString();
		userId = map.get("user_id").toString();
		joinSstCode = map.get("join_sst_code").toString();
		joinDate = map.get("join_date").toString();
		joinTime = map.get("join_time").toString();
		isRnameAuth = map.get("is_rname_auth").toString();
		imMemTypeCd = map.get("im_mem_type_cd").toString();
		userType = map.get("user_type").toString();
		userStatusCode = map.get("user_status_code").toString(); // 10:정상 11:가인증 ONEID 테이블의 회원가입상태코드

		if (map.get("user_key") != null)
			currentMbrNoForgameCenter = map.get("user_key").toString(); // 게임센터 연동을 위한 변수mbrNo 셋팅

		if (map.get("TELECOM") != null) { // 통신사유형
			String telecomType = map.get("TELECOM").toString();

			if (telecomType.equals(MemberConstants.NM_DEVICE_TELECOM_SKT)) {
				telecomCode = MemberConstants.DEVICE_TELECOM_SKT;
			} else if (telecomType.equals(MemberConstants.NM_DEVICE_TELECOM_KT)) {
				telecomCode = MemberConstants.DEVICE_TELECOM_KT;
			} else if (telecomType.equals(MemberConstants.NM_DEVICE_TELECOM_LGT)) {
				telecomCode = MemberConstants.DEVICE_TELECOM_LGT;
			} else if (telecomType.equals(MemberConstants.NM_DEVICE_TELECOM_OMD)) {
				telecomCode = MemberConstants.DEVICE_TELECOM_OMD;
			} else if (telecomType.equals(MemberConstants.NM_DEVICE_TELECOM_NSH)) {
				telecomCode = MemberConstants.DEVICE_TELECOM_NSH;
			} else if (telecomType.equals(MemberConstants.NM_DEVICE_TELECOM_NON)) {
				telecomCode = MemberConstants.DEVICE_TELECOM_NON;
			} else if (telecomType.equals(MemberConstants.NM_DEVICE_TELECOM_IOS)) {
				telecomCode = MemberConstants.DEVICE_TELECOM_IOS;
			}
			map.put("telecomCode", telecomCode); // 통신사유형 코드 셋팅
		}

		if (map.get("emailYn") != null) { // 이메일수신여부
			emailRecvYn = map.get("emailYn").toString();
		}

		String joinSiteTotalList = map.get("join_sst_list").toString(); // 이용동의사이트정보
		// example info list : 41100,null,20130923,212917,tstore000001741|90300,null,20130917,113426,null
		// LOGGER.debug("replace before:" + joinSiteTotalList);
		joinSiteTotalList = joinSiteTotalList.replaceAll(" ", "");
		// LOGGER.debug("replace after:" + joinSiteTotalList);

		// response object create & setting initialized

		int checkOcbVal = joinSiteTotalList.indexOf(MemberConstants.SSO_SST_CD_OCB_WEB);
		if (checkOcbVal != -1)
			ocbJoinCodeYn = "Y";

		ImResult imResult = new ImResult();
		imResult.setCmd("RXCreateUserIDP");
		imResult.setImIntSvcNo(imIntSvcNo);
		imResult.setUserId(userId);
		String[] mbrClauseAgreeArray = null;
		String[] tempSplit = joinSiteTotalList.split("\\|");
		for (int i = 0; i < tempSplit.length; i++) {
			String[] tmpSplit = tempSplit[i].split(",");

			if (null != tmpSplit && tmpSplit.length >= 1 && null != tmpSplit[0] && MemberConstants.SSO_SST_CD_TSTORE.equals(tmpSplit[0])) {

				if (tmpSplit.length >= 5 && null != tmpSplit[4] && !"".equals(tmpSplit[4]) && !"null".equals(tmpSplit[4])) {
					LOGGER.debug("RXCREATEUSERIDP old_id : " + tmpSplit[4]);
					map.put("old_id", tmpSplit[4]);
				} else if (tmpSplit.length >= 5 && null != tmpSplit[4] && !"".equals(tmpSplit[4]) && "null".equals(tmpSplit[4])) {
					map.put("old_id", "null");
				}
				if (tmpSplit.length >= 2 && null != tmpSplit[1] && !"".equals(tmpSplit[1]) && !"null".equals(tmpSplit[1])) {
					mbrClauseAgreeArray = tmpSplit[1].split("\\^");
				}
				break;
			}
		}

		String oldId = map.get("old_id").toString();
		isParentApprove = map.get("is_parent_approve").toString();

		if ("null".equals(oldId) || "".equals(oldId)) { // 신규가입경우 기존 Tstore에 없던 회원가입요청시 전환가입 대상자중 Tstore 미가입자로 Tstore에
														// 가입이 안되어있는경우는 신규가입으로 판단
			LOGGER.debug("JOIN NEW DATA INSERT START");
			CreateUserRequest createUserRequest = new CreateUserRequest();
			CreateUserResponse create = null;
			// 공통으로 사용되는 요청정보
			CommonRequest commonRequest = new CommonRequest();
			commonRequest.setTenantID(tenantId);
			commonRequest.setSystemID(systemId);

			// 사용자 기본정보
			UserMbr userMbr = new UserMbr();

			userMbr.setTenantID(tenantId); // 테넌트 ID
			userMbr.setSystemID(systemId); // 테넌트의 시스템 ID

			userMbr.setUserKey(""); // 사용자 Key

			if (map.get("user_key") != null)
				userMbr.setImMbrNo(map.get("user_key").toString()); // 외부(OneID/IDP)에서 할당된 사용자 Key IDP 통합서비스 키USERMBR_NO

			userMbr.setUserType(MemberConstants.USER_TYPE_ONEID); // * 사용자 구분 코드 ONEID회원으로 셋팅
			if (userStatusCode.equals(MemberConstants.JOIN_STATUS_CODE_NORMAL)) { // 10: 정상
				userMbr.setUserMainStatus(MemberConstants.MAIN_STATUS_NORMAL); // 사용자 메인 상태 코드 가입시 바로 가입됨 정상
				userMbr.setUserSubStatus(MemberConstants.SUB_STATUS_NORMAL); // 사용자 서브 상태 코드 정상
			} else { // 11: 가인증
				userMbr.setUserMainStatus(MemberConstants.MAIN_STATUS_WATING); // 가가입
				userMbr.setUserSubStatus(MemberConstants.SUB_STATUS_JOIN_APPLY_WATING); // 가입승인대기
			}
			userMbr.setImSvcNo(imIntSvcNo); // 통합 서비스 관리번호 INTG_SVC_NO : 통합서비스 관리번호
			userMbr.setIsImChanged(map.get("is_im_changed").toString()); // 전환가입코드 * * - 전환가입 : Y, 신규가입 : N, 변경가입 : C,
																			// 변경전환 : H
			userMbr.setUserID(userId); // 사용자 ID

			if (map.get("user_tn_nation_cd") != null)
				userMbr.setUserPhoneCountry(map.get("user_tn_nation_cd").toString()); // 연락처 국가 코드

			if (map.get("user_tn") != null)
				userMbr.setUserPhone(map.get("user_tn").toString()); // 사용자 연락처

			if (map.get("user_email") != null)
				userMbr.setUserEmail(map.get("user_email").toString()); // 사용자 이메일 주소

			if (map.get("user_name") != null)
				userMbr.setUserName(map.get("user_name").toString()); // 사용자 이름

			if (map.get("user_sex") != null)
				userMbr.setUserSex(map.get("user_sex").toString()); // 사용자 성별

			if (map.get("user_birthday") != null)
				userMbr.setUserBirthDay(map.get("user_birthday").toString()); // 사용자 생년월일

			if (map.get("user_nation_code") != null)
				userMbr.setUserCountry(map.get("user_nation_code").toString()); // 사용자 국가 코드

			userMbr.setImSiteCode(map.get("join_sst_list").toString()); // OneID 이용동의 사이트 정보
			// 실명인증 여부 신규인경우 Y로 전달받아도 실명인증정보에서 신규인경우 N으로 수정이됨
			userMbr.setIsRealName(isRnameAuth);
			userMbr.setIsMemberPoint(ocbJoinCodeYn); // 통합 포인트 여부 (Y/N)

			if (map.get("telecomCode") != null)
				userMbr.setUserTelecom(map.get("telecomCode").toString());

			userMbr.setIsRecvEmail(emailRecvYn); // 이메일 수신여부 (Y/N)

			createUserRequest.setCommonRequest(commonRequest); // 공통요청

			if (isRnameAuth.equals(MemberConstants.USE_Y)) {
				MbrAuth mbrAuth = this.getMbrAuthByNew(map, "N");
				createUserRequest.setMbrAuth(mbrAuth); // 실명인증
			}

			createUserRequest.setUserMbr(userMbr); // 사용자정보

			// 법정대리인 정보 14세미만인 경우 대리인 정보 셋팅
			if (StringUtils.equals(isParentApprove, MemberConstants.USE_Y)) {
				createUserRequest.setMbrLglAgent(this.getMbrLglAgent(map)); // 법정대리인
			}

			// JOIN_SST_LIST에 TAC001~TAC006이 있는경우 이용약관이 들어옴.
			List<MbrClauseAgree> mbrClauseAgreeList = this.getMbrClauseAgreeList(tenantId, mbrClauseAgreeArray);

			createUserRequest.setMbrClauseAgreeList(mbrClauseAgreeList);
			try {
				create = this.userSCI.create(createUserRequest); // 가입정보 등록
				userKey = create.getUserKey();
			} catch (StorePlatformException spe) {
				imResult.setResult(IdpConstants.IM_IDP_RESPONSE_FAIL_CODE);
				imResult.setResultText(IdpConstants.IM_IDP_RESPONSE_FAIL_CODE_TEXT);
				return imResult;
			}

			LOGGER.debug("JOIN NEW DATA INSERT COMPLETE");

			LOGGER.debug("JOIN ONEID DATA INSERT START");

			// ONEID에 데이터 입력
			UpdateMbrOneIDRequest updateMbrOneIDRequest = new UpdateMbrOneIDRequest();
			updateMbrOneIDRequest.setCommonRequest(commonRequest);
			MbrOneID mbrOneID = new MbrOneID();
			mbrOneID.setStopStatusCode(IdpConstants.SUS_STATUS_RELEASE); // 직권중지해제 기본셋팅
			mbrOneID.setIntgSvcNumber(imIntSvcNo);
			mbrOneID.setUserKey(userKey); // 신규가입때 생성된 내부사용자키를 셋팅
			mbrOneID.setUserID(userId); // userId
			mbrOneID.setIsMemberPoint(ocbJoinCodeYn); // 통합포인트 여부
			mbrOneID.setIsRealName(isRnameAuth); // 실명인증 여부
			mbrOneID.setIntgSiteCode(joinSstCode); // 가입 서비스 사이트 코드
			mbrOneID.setEntryDate(joinDate + joinTime); // 가입일시
			mbrOneID.setIntgMbrCaseCode(imMemTypeCd); // 통합회원유형코드
			mbrOneID.setEntryStatusCode(userStatusCode); // 가입자 상태코드
			mbrOneID.setMemberCaseCode(userType); // 가입자 유형코드

			if (map.get("user_ci").toString().length() > 0) { // 사용자 CI
				mbrOneID.setIsCi("Y");
			} else {
				mbrOneID.setIsCi("N");
			}

			updateMbrOneIDRequest.setMbrOneID(mbrOneID);

			try {
				this.userSCI.createAgreeSite(updateMbrOneIDRequest);
			} catch (StorePlatformException spe) {
				imResult.setResult(IdpConstants.IM_IDP_RESPONSE_FAIL_CODE);
				imResult.setResultText(IdpConstants.IM_IDP_RESPONSE_FAIL_CODE_TEXT);
				return imResult;
			}
			LOGGER.debug("JOIN ONEID DATA INSERT COMPLETE");

			try {
				/* 게임센터 연동 */
				GameCenterSacReq gameCenterSacReq = new GameCenterSacReq();
				gameCenterSacReq.setUserKey(userKey);
				gameCenterSacReq.setPreUserKey(userKey);
				gameCenterSacReq.setMbrNo(currentMbrNoForgameCenter);
				gameCenterSacReq.setPreMbrNo(prevMbrNoForgameCenter);
				gameCenterSacReq.setSystemId(systemId);
				gameCenterSacReq.setTenantId(tenantId);
				gameCenterSacReq.setWorkCd(MemberConstants.GAMECENTER_WORK_CD_IMUSER_CHANGE);
				this.deviceService.insertGameCenterIF(gameCenterSacReq);
			} catch (StorePlatformException spe) {
				imResult.setResult(IdpConstants.IM_IDP_RESPONSE_FAIL_CODE);
				imResult.setResultText(IdpConstants.IM_IDP_RESPONSE_FAIL_CODE_TEXT);
				return imResult;
			}

		} else { // 전환가입/변경전환/변경 가입 oldId != "null" 이 아닌경우 분기
			CommonRequest commonRequest = new CommonRequest();
			commonRequest.setTenantID(tenantId);
			commonRequest.setSystemID(systemId);
			map.put("im_reg_date", DateUtil.getToday()); // 전환가입일을 셋팅

			if (userId.equals(oldId)) { // 전환가입 userId - oldId 비교시 같은경우
				LOGGER.debug("전환가입 정보 입력 시작");
				SearchUserRequest searchUserRequest = new SearchUserRequest();

				KeySearch keySearch = new KeySearch();
				keySearch.setKeyType(MemberConstants.KEY_TYPE_MBR_ID);

				keySearch.setKeyString(map.get("old_id").toString());
				List<KeySearch> keySearchList = new ArrayList<KeySearch>();
				keySearchList.add(keySearch);
				searchUserRequest.setKeySearchList(keySearchList);
				searchUserRequest.setCommonRequest(commonRequest);
				UpdateUserResponse updateUserResponse = null;

				try {
					SearchUserResponse searchUserResponse = this.userSCI.searchUser(searchUserRequest);

					if (searchUserResponse == null) {
						keySearch.setKeyString(map.get("old_id").toString() + "@nate.com");
						keySearchList = null;
						keySearchList = new ArrayList<KeySearch>();
						keySearchList.add(keySearch);
						searchUserRequest.setKeySearchList(keySearchList);

						searchUserResponse = this.userSCI.searchUser(searchUserRequest);
					}

					if (searchUserResponse != null) {
						prevMbrNoForgameCenter = searchUserResponse.getUserMbr().getImMbrNo(); // 게임센터 연동을 위한 이전 mbrNo셋팅
						UpdateUserRequest updateUserRequest = this.getUpdateUserRequest(map, searchUserResponse);
						// JOIN_SST_LIST에 TAC001~TAC006이 있는경우 이용약관이 들어옴.
						List<MbrClauseAgree> mbrClauseAgreeList = this.getMbrClauseAgreeList(tenantId, mbrClauseAgreeArray);

						updateUserRequest.setMbrClauseAgree(mbrClauseAgreeList);
						updateUserResponse = this.userSCI.updateUser(updateUserRequest);

						userKey = updateUserResponse.getUserKey();

					}
					LOGGER.debug("전환가입 정보 입력 완료");

				} catch (StorePlatformException spe) {
					imResult.setResult(IdpConstants.IM_IDP_RESPONSE_FAIL_CODE);
					imResult.setResultText(IdpConstants.IM_IDP_RESPONSE_FAIL_CODE_TEXT);
					return imResult;
				}

			} else if (!userId.equals(oldId)) { // 변경가입, 변경전환
				LOGGER.debug("변경가입,변경전환 정보 입력 시작");
				SearchUserRequest searchUserRequest = new SearchUserRequest();

				KeySearch keySearch = new KeySearch();
				keySearch.setKeyType(MemberConstants.KEY_TYPE_MBR_ID);

				keySearch.setKeyString(map.get("old_id").toString());
				List<KeySearch> keySearchList = new ArrayList<KeySearch>();
				keySearchList.add(keySearch);
				searchUserRequest.setKeySearchList(keySearchList);
				searchUserRequest.setCommonRequest(commonRequest);
				UpdateUserResponse updateUserResponse = null;

				try {

					SearchUserResponse searchUserResponse = this.userSCI.searchUser(searchUserRequest);

					if (searchUserResponse == null) {
						keySearch.setKeyString(map.get("old_id").toString() + "@nate.com");
						keySearchList = null;
						keySearchList = new ArrayList<KeySearch>();
						keySearchList.add(keySearch);
						searchUserRequest.setKeySearchList(keySearchList);

						searchUserResponse = this.userSCI.searchUser(searchUserRequest);
					}

					if (searchUserResponse != null) {
						prevMbrNoForgameCenter = searchUserResponse.getUserMbr().getImMbrNo(); // 게임센터 연동을 위한 이전 mbrNo셋팅

						UpdateUserRequest updateUserRequest = this.getUpdateUserRequest(map, searchUserResponse);
						// JOIN_SST_LIST에 TAC001~TAC006이 있는경우 이용약관이 들어옴.
						List<MbrClauseAgree> mbrClauseAgreeList = this.getMbrClauseAgreeList(tenantId, mbrClauseAgreeArray);

						updateUserRequest.setMbrClauseAgree(mbrClauseAgreeList);
						updateUserResponse = this.userSCI.updateUser(updateUserRequest);

						userKey = updateUserResponse.getUserKey();

					}
					LOGGER.debug("변경가입,변경전환 정보 입력 완료");

					// 공통_기타 회원ID 변경 시작
					ChangeDisplayUserSacReq changeDisplayUserSacReqByUserID = new ChangeDisplayUserSacReq();
					changeDisplayUserSacReqByUserID.setNewUserId(userId);
					changeDisplayUserSacReqByUserID.setOldUserId(oldId);
					changeDisplayUserSacReqByUserID.setTenantId(tenantId);
					this.changeDisplayUserSCI.changeUserId(changeDisplayUserSacReqByUserID);
					// 공통_기타 회원ID 변경 끝

					/* FDS LOG START */
					final String fdsMbrIdPre = oldId;
					final String fdsMbrId = userId;
					final String fdsUsermbrNoPre = prevMbrNoForgameCenter;
					final String fdsUsermbrNoPost = currentMbrNoForgameCenter;
					final String fdsUserId = userId;
					final String fdsSystemId = systemId;

					new TLogUtil().log(new ShuttleSetter() {
						@Override
						public void customize(TLogSentinelShuttle shuttle) {
							shuttle.log_id("TL00030").mbr_id_pre(fdsMbrIdPre).mbr_id_post(fdsMbrId).usermbr_no_pre(fdsUsermbrNoPre)
									.usermbr_no_post(fdsUsermbrNoPost).result_code("SUCC").mbr_id(fdsUserId).request_system_id(fdsSystemId);
						}
					});
					/* FDS LOG END */

				} catch (StorePlatformException spe) {
					imResult.setResult(IdpConstants.IM_IDP_RESPONSE_FAIL_CODE);
					imResult.setResultText(IdpConstants.IM_IDP_RESPONSE_FAIL_CODE_TEXT);
					final String fdsUserId = userId;
					final String fdsSystemId = systemId;

					new TLogUtil().log(new ShuttleSetter() {
						@Override
						public void customize(TLogSentinelShuttle shuttle) {
							shuttle.log_id("TL00030").result_code(IdpConstants.IM_IDP_RESPONSE_FAIL_CODE)
									.result_message(IdpConstants.IM_IDP_RESPONSE_FAIL_CODE_TEXT).exception_log("").mbr_id(fdsUserId)
									.request_system_id(fdsSystemId);
						}
					});

					return imResult;
				}
			}

			LOGGER.debug("ONEID DATA UPDATE START");

			try {
				// ONEID에 데이터 입력
				UpdateMbrOneIDRequest updateMbrOneIDRequest = new UpdateMbrOneIDRequest();
				updateMbrOneIDRequest.setCommonRequest(commonRequest);
				MbrOneID mbrOneID = new MbrOneID();
				mbrOneID.setStopStatusCode(IdpConstants.SUS_STATUS_RELEASE); // 직권중지해제 기본셋팅
				mbrOneID.setIntgSvcNumber(imIntSvcNo);
				mbrOneID.setUserKey(userKey); // 내부사용자키를 셋팅
				mbrOneID.setUserID(userId); // 사용자 ID 셋팅
				mbrOneID.setIsMemberPoint(ocbJoinCodeYn); // 통합포인트 여부
				mbrOneID.setIsRealName(isRnameAuth); // 실명인증 여부
				mbrOneID.setIntgSiteCode(joinSstCode); // 가입 서비스 사이트 코드
				mbrOneID.setEntryDate(joinDate + joinTime); // 가입일시
				mbrOneID.setIntgMbrCaseCode(imMemTypeCd); // 통합회원유형코드
				mbrOneID.setEntryStatusCode(userStatusCode); // 가입자 상태코드
				mbrOneID.setMemberCaseCode(userType); // 가입자 유형코드

				if (map.get("user_ci").toString().length() > 0) { // 사용자 CI
					mbrOneID.setIsCi("Y");
				} else {
					mbrOneID.setIsCi("N");
				}

				updateMbrOneIDRequest.setMbrOneID(mbrOneID);
				this.userSCI.createAgreeSite(updateMbrOneIDRequest);

			} catch (StorePlatformException spe) {
				imResult.setResult(IdpConstants.IM_IDP_RESPONSE_FAIL_CODE);
				imResult.setResultText(IdpConstants.IM_IDP_RESPONSE_FAIL_CODE_TEXT);
				return imResult;
			}

			LOGGER.debug("ONEID DATA UPDATE COMPLETE");

			if (this.isCall) { // OGG 시에만 사용하고 그 이후에는 불필요 로직 property로 사용함
				try {
					/* 게임센터 연동 */

					GameCenterSacReq gameCenterSacReq = new GameCenterSacReq();
					// Post UserKey를 가져오려했으나 sc쪽에 userKey는 수정후에도 넘겨받는값 userKey updateUserRequest의 userkKey를 그대로 리턴해주고
					// 있어서현재 param으로 넘어온 mbrNo 그대로 셋팅해주고 있음 임시로직이기 때문에 문제 없음.
					gameCenterSacReq.setUserKey(currentMbrNoForgameCenter);
					gameCenterSacReq.setPreUserKey(userKey);
					gameCenterSacReq.setMbrNo(currentMbrNoForgameCenter);
					gameCenterSacReq.setPreMbrNo(prevMbrNoForgameCenter);
					gameCenterSacReq.setSystemId(systemId);
					gameCenterSacReq.setTenantId(tenantId);
					gameCenterSacReq.setWorkCd(MemberConstants.GAMECENTER_WORK_CD_IMUSER_CHANGE);
					this.deviceService.insertGameCenterIF(gameCenterSacReq);
				} catch (StorePlatformException spe) {
					imResult.setResult(IdpConstants.IM_IDP_RESPONSE_FAIL_CODE);
					imResult.setResultText(IdpConstants.IM_IDP_RESPONSE_FAIL_CODE_TEXT);
					return imResult;
				}
			}

			/**
			 * 구매/기타 UserKey 변경.(OGG 시에만 사용하고 그 이후에는 불필요 로직임.) //20140415 현재
			 * 구매쪽에서 null처리 안되있으므로 처리 되면 반영할것
			 */
			if (this.isCall) {
				String newMbrNo = map.get("user_key").toString();
				String prevMbrNo = prevMbrNoForgameCenter;
				this.mcc.excuteInternalMethod(this.isCall, systemId, tenantId, newMbrNo, prevMbrNo, null, null);
			}
		}

		imResult.setResult(IdpConstants.IM_IDP_RESPONSE_SUCCESS_CODE);
		imResult.setResultText(IdpConstants.IM_IDP_RESPONSE_SUCCESS_CODE_TEXT);
		LOGGER.debug("rXCreateUserIDP ------- End");
		return imResult;
	}

	/**
	 * 
	 * <pre>
	 * 실명인증 정보 셋팅 .
	 * </pre>
	 * 
	 * @param hashMap
	 *            Request 받은 Parameter Map
	 * @param isNewYn
	 *            신규여부
	 * @return
	 */
	private MbrAuth getMbrAuthByNew(HashMap<String, String> hashMap, String isNewYn) {
		// 프로비저닝으로 내려온 실명인증은 재 인증을 해야함으로 Garbage method 될수 있음 일단 구현해놓음.
		MbrAuth setMbrAuth = new MbrAuth();

		// 실명 인증 여부 신규가입인경우 Tstore에서 실명인증을 받아야 구매할수 있으므로 N 으로 셋팅이 되서 TB_US_USERMBR의 실명인증여부를 SC에서 N으로 수정함
		if (isNewYn.equals("N")) {
			setMbrAuth.setIsRealName("N");
		} else {
			setMbrAuth.setIsRealName("Y");
		}

		if (hashMap.get("user_ci") != null) { // user_ci 회원테이블에서 필수값이므로 " "공백을 셋팅해줘야 인서트가 됨.
			if (!hashMap.get("user_ci").toString().equals("")) {
				setMbrAuth.setCi(hashMap.get("user_ci").toString()); // CI
			} else {
				setMbrAuth.setCi(" ");
			}
		}

		if (hashMap.get("user_di") != null)
			setMbrAuth.setDi(hashMap.get("user_di").toString()); // DI

		if (hashMap.get("rname_auth_mns_code") != null) { // 실명인증 수단 코드 판단 값
			if (MemberConstants.REAL_NAME_AUTH_TYPE_MOBILE.equals(hashMap.get("rname_auth_mns_code").toString())) { // 휴대폰
				setMbrAuth.setRealNameMethod(MemberConstants.REAL_NAME_AUTH_MOBILE); // AUTH_MTD_CD 인증방법코드
			} else if (MemberConstants.REAL_NAME_AUTH_TYPE_IPIN.equals(hashMap.get("rname_auth_mns_code").toString())) {// IPIN인증
				setMbrAuth.setRealNameMethod(MemberConstants.REAL_NAME_AUTH_IPIN);
			} else { // 기타
				setMbrAuth.setRealNameMethod(""); // AUTH_MTD_CD 인증방법코드
			}
		}

		if (hashMap.get("user_birthday") != null)
			setMbrAuth.setBirthDay(hashMap.get("user_birthday").toString()); // BIRTHDAY

		if (hashMap.get("user_sex") != null)
			setMbrAuth.setSex(hashMap.get("user_sex").toString()); // SEX 성별

		if (hashMap.get("user_name") != null)
			setMbrAuth.setName(hashMap.get("user_name").toString()); // MBR_NM 회원명

		if (hashMap.get("rname_auth_mbr_code") != null) { // 내국인,외국인 설정
			if (hashMap.get("rname_auth_mbr_code").toString().equals("10")) {
				setMbrAuth.setIsDomestic("Y");
			} else {
				setMbrAuth.setIsDomestic("N");
			}
		}

		if (hashMap.get("rname_auth_date") != null) // 실명인증일시 db-datetype 14자리
			setMbrAuth.setRealNameDate(hashMap.get("rname_auth_date").toString());

		if (hashMap.get("telecomCode") != null)
			setMbrAuth.setTelecom(hashMap.get("telecomCode").toString());

		setMbrAuth.setTenantID(hashMap.get("tenantID").toString()); // TENANT_ID 테넌트 아이디
		setMbrAuth.setRealNameSite(hashMap.get("systemID").toString()); // systemID

		return setMbrAuth;
	}

	/**
	 * 
	 * <pre>
	 * method 회원정보를 수정하기 위한 객체 셋팅 .
	 * </pre>
	 * 
	 * @param hashMap
	 * @param searchUserResponse
	 * @return tag UpdateUserRequest
	 */
	private UpdateUserRequest getUpdateUserRequest(HashMap<String, String> hashMap, SearchUserResponse searchUserResponse) {
		UpdateUserRequest updateUserRequest = new UpdateUserRequest();

		CommonRequest commonRequest = new CommonRequest();

		commonRequest.setTenantID(hashMap.get("tenantID").toString());
		commonRequest.setSystemID(hashMap.get("systemID").toString());

		UserMbr getUserMbr = searchUserResponse.getUserMbr();
		MbrAuth getMbrAuth = null;
		getUserMbr.setImRegDate(hashMap.get("im_reg_date").toString());

		getUserMbr.setTenantID(commonRequest.getTenantID()); // 테넌트 ID
		getUserMbr.setSystemID(commonRequest.getSystemID()); // 테넌트의 시스템 ID

		if (hashMap.get("user_key") != null)
			getUserMbr.setImMbrNo(hashMap.get("user_key").toString()); // 외부(OneID/IDP)에서 할당된 사용자 Key . IDP 통합서비스
																		// 키 USERMBR_NO
		getUserMbr.setUserType(MemberConstants.USER_TYPE_ONEID); // 사용자 구분 코드

		if (hashMap.get("user_status_code") != null) {
			if (hashMap.get("user_status_code").toString().equals(MemberConstants.JOIN_STATUS_CODE_NORMAL)) { // 10: 정상
				getUserMbr.setUserMainStatus(MemberConstants.MAIN_STATUS_NORMAL); // 사용자 메인 상태 코드 가입시 바로 가입됨 정상
				getUserMbr.setUserSubStatus(MemberConstants.SUB_STATUS_NORMAL); // 사용자 서브 상태 코드 정상
			} else { // 11: 가인증
				getUserMbr.setUserMainStatus(MemberConstants.MAIN_STATUS_WATING); // 가가입
				getUserMbr.setUserSubStatus(MemberConstants.SUB_STATUS_JOIN_APPLY_WATING); // 가입승인대기
			}
		}

		getUserMbr.setUserMainStatus(searchUserResponse.getUserMbr().getUserMainStatus()); // 사용자 메인 상태 코드
		getUserMbr.setUserSubStatus(searchUserResponse.getUserMbr().getUserSubStatus()); // 사용자 서브 상태 코드

		getUserMbr.setImSvcNo(hashMap.get("im_int_svc_no").toString()); // 통합 서비스 관리번호 INTG_SVC_NO : 통합서비스 관리번호.
		getUserMbr.setIsImChanged(hashMap.get("is_im_changed").toString()); // 전환가입코드 * * - 전환가입 : Y, 신규가입 : N, 변경가입 :
																			// C, 변경전환 : H
		getUserMbr.setUserID(hashMap.get("user_id").toString()); // 사용자 ID

		if (hashMap.get("user_tn_nation_cd") != null)
			getUserMbr.setUserPhoneCountry(hashMap.get("user_tn_nation_cd").toString()); // 연락처 국가 코드
		if (hashMap.get("user_tn") != null)
			getUserMbr.setUserPhone(hashMap.get("user_tn").toString()); // 사용자 연락처
		if (hashMap.get("user_email") != null)
			getUserMbr.setUserEmail(hashMap.get("user_email").toString()); // 사용자 이메일 주소
		if (hashMap.get("user_name") != null) {
			getUserMbr.setUserName(hashMap.get("user_name").toString()); // 사용자 이름
		}

		if (hashMap.get("user_sex") != null)
			getUserMbr.setUserSex(hashMap.get("user_sex").toString()); // 사용자 성별
		if (hashMap.get("user_birthday") != null)
			getUserMbr.setUserBirthDay(hashMap.get("user_birthday").toString()); // 사용자 생년월일
		if (hashMap.get("user_nation_code") != null)
			getUserMbr.setUserCountry(hashMap.get("user_nation_code").toString()); // 사용자 국가 코드
		getUserMbr.setImSiteCode(hashMap.get("join_sst_list").toString()); // OneID 이용동의 사이트 정보
		getUserMbr.setIsMemberPoint(hashMap.get("ocb_join_code").toString()); // 통합 포인트 여부 (Y/N)

		if (hashMap.get("telecomCode") != null)
			getUserMbr.setUserTelecom(hashMap.get("telecomCode").toString());

		if (hashMap.get("emailYn") != null)
			getUserMbr.setIsRecvEmail(hashMap.get("emailYn").toString()); // 이메일 수신여부 (Y/N)

		if (searchUserResponse.getMbrAuth() != null) {
			if (searchUserResponse.getMbrAuth().getIsRealName().equals(MemberConstants.USE_Y)) {
				getMbrAuth = searchUserResponse.getMbrAuth();
			}
		}

		updateUserRequest.setCommonRequest(commonRequest);
		updateUserRequest.setUserMbr(getUserMbr);
		updateUserRequest.setMbrAuth(getMbrAuth);

		// 법정대리인 정보 14세미만인 경우 대리인 정보를 찾아서 입력시켜줘야함.
		if (StringUtils.equals(hashMap.get("is_parent_approve").toString(), MemberConstants.USE_Y)) {
			updateUserRequest.setMbrLglAgent(this.getMbrLglAgent(hashMap));
		}

		return updateUserRequest;
	}

	/**
	 * 
	 * <pre>
	 * method 14세 미만의 법정대리인 정보를 셋팅.
	 * </pre>
	 * 
	 * @param hashMap
	 * @return tag MbrLglAgent
	 */
	private MbrLglAgent getMbrLglAgent(HashMap<String, String> hashMap) {
		MbrLglAgent mbrLglAgent = new MbrLglAgent();
		if (StringUtils.equals(hashMap.get("is_parent_approve").toString(), MemberConstants.USE_Y)) {
			mbrLglAgent.setIsParent(hashMap.get("is_parent_approve").toString()); // 법정대리인 동의여부(Y/N)
			mbrLglAgent.setTenantID(hashMap.get("tenantID").toString()); // 테넌트 ID

			// 법정 대리인 실명인증 수단코드
			if (hashMap.get("parent_rname_auth_type").toString().equals("1")) {// 휴대폰 인증
				mbrLglAgent.setParentRealNameMethod(MemberConstants.REAL_NAME_AUTH_MOBILE);
			} else if (hashMap.get("parent_rname_auth_type").toString().equals("3")) {// 아이핀 인증
				mbrLglAgent.setParentRealNameMethod(MemberConstants.REAL_NAME_AUTH_IPIN);
			} else if (hashMap.get("parent_rname_auth_type").toString().equals("6")) {// 이메일
				mbrLglAgent.setParentRealNameMethod(MemberConstants.REAL_NAME_AUTH_EMAIL);
			}

			mbrLglAgent.setParentName(hashMap.get("parent_name").toString()); // LGL_AGENT_FLNM 법정대리인 이름

			// 법정대리인 관계 코드
			if (hashMap.get("parent_type").toString().equals("0")) {// 부
				mbrLglAgent.setParentType(MemberConstants.PARENT_TYPE_FATHER);
			} else if (hashMap.get("parent_type").toString().equals("1")) {// 모
				mbrLglAgent.setParentType(MemberConstants.PARENT_TYPE_MOTHER);
			} else { // 기타 : parent_type == 2
				mbrLglAgent.setParentType(MemberConstants.PARENT_TYPE_ECT);
			}

			// LGL_AGENT_AGREE_DT 동의 일시
			if (hashMap.get("parent_approve_date").toString().length() == 8) {
				mbrLglAgent.setParentDate(hashMap.get("parent_approve_date").toString() + "000000");
			} else if (hashMap.get("parent_approve_date").toString().length() > 8) {
				mbrLglAgent.setParentDate(hashMap.get("parent_approve_date").toString());
			}

			mbrLglAgent.setParentEmail(hashMap.get("parent_email").toString()); // LGL_AGENT_EMAIL
			mbrLglAgent.setParentBirthDay(hashMap.get("parent_birthday").toString()); // LGL_AGENT_BIRTH
			mbrLglAgent.setParentRealNameSite(hashMap.get("systemID").toString()); // AUTH_REQ_CHNL_CD
			if (!hashMap.get("parent_rname_auth_type").toString().equals("6")) {
				mbrLglAgent.setParentCI(hashMap.get("parent_rname_auth_key").toString());
			}
		}
		return mbrLglAgent;
	}

	/*
	 * 
	 * <pre> 휴대폰소유변경정보배포 - CMD : RXInvalidUserTelNoIDP </pre>
	 * 
	 * @param map Request로 받은 Parameter Map
	 * 
	 * @return HashMap
	 */
	@Override
	public ImResult executeRXInvalidUserTelNoIDP(HashMap map) {
		// Service Method 이름은 Provisioning 및 Rx 기능의 'cmd' 값과 동일 해야 함.
		SearchUserRequest searchUserRequest = new SearchUserRequest();
		UpdateUserRequest userVo = new UpdateUserRequest();

		String idpResult = IdpConstants.IM_IDP_RESPONSE_FAIL_CODE;
		String idpResultText = IdpConstants.IM_IDP_RESPONSE_FAIL_CODE_TEXT;

		// 휴대폰 번호가 초기값이면 변경하지 않음
		String getUserTn = map.get("user_tn").toString();
		if (getUserTn.equals("01000000000")) {
			idpResult = IdpConstants.IM_IDP_RESPONSE_SUCCESS_CODE;
			idpResultText = IdpConstants.IM_IDP_RESPONSE_SUCCESS_CODE_TEXT;

			ImResult imResult = new ImResult();

			imResult.setResult(idpResult);
			imResult.setCmd("RXInvalidUserTelNoIDP");
			imResult.setResultText(idpResultText);
			imResult.setImIntSvcNo(map.get("im_int_svc_no").toString());
			imResult.setUserId(map.get("user_id").toString());

			return imResult;
		}

		// 회원 정보 조회
		// 공통 헤더
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setSystemID((String) map.get("systemID"));
		commonRequest.setTenantID((String) map.get("tenantID"));
		searchUserRequest.setCommonRequest(commonRequest);

		List<KeySearch> keySearchList = new ArrayList<KeySearch>();
		KeySearch keySearch = new KeySearch();
		keySearch.setKeyType(MemberConstants.KEY_TYPE_MBR_ID);
		keySearch.setKeyString((String) map.get("user_id"));

		keySearchList.add(keySearch);
		searchUserRequest.setKeySearchList(keySearchList);
		UserMbr getUserMbr = null;
		try {
			SearchUserResponse searchUserRespnse = this.userSCI.searchUser(searchUserRequest);

			// 회원 수정 정보 세팅
			if (searchUserRespnse != null)
				getUserMbr = searchUserRespnse.getUserMbr();

			if (getUserMbr != null) {
				getUserMbr.setUserPhone(getUserTn);
				userVo.setUserMbr(getUserMbr);
				userVo.setCommonRequest(commonRequest);

				// 회원 정보 수정 API call
				this.userSCI.updateUser(userVo);

				idpResult = IdpConstants.IM_IDP_RESPONSE_SUCCESS_CODE;
				idpResultText = IdpConstants.IM_IDP_RESPONSE_SUCCESS_CODE_TEXT;
			}
		} catch (StorePlatformException spe) {
			idpResult = IdpConstants.IM_IDP_RESPONSE_FAIL_CODE;
			idpResultText = IdpConstants.IM_IDP_RESPONSE_FAIL_CODE_TEXT;
		}
		ImResult imResult = new ImResult();

		imResult.setResult(idpResult);
		imResult.setCmd("RXInvalidUserTelNoIDP");
		imResult.setResultText(idpResultText);
		imResult.setImIntSvcNo(map.get("im_int_svc_no").toString());
		imResult.setUserId(map.get("user_id").toString());

		return imResult;
	}

	/*
	 * 
	 * <pre> 로그인 상태정보배포 - CMD : RXSetLoginConditionIDP </pre>
	 * 
	 * @param map Request로 받은 Parameter Map
	 * 
	 * @return HashMap
	 */
	@Override
	public ImResult executeRXSetLoginConditionIDP(HashMap map) {

		UpdateStatusUserRequest updateUserVo = new UpdateStatusUserRequest();

		// 공통 헤더 세팅
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setSystemID((String) map.get("systemID"));
		commonRequest.setTenantID((String) map.get("tenantID"));
		updateUserVo.setCommonRequest(commonRequest);

		List<KeySearch> keySearchList = new ArrayList<KeySearch>();
		KeySearch keySearch = new KeySearch();
		keySearch.setKeyType("INTG_SVC_NO");
		keySearch.setKeyString((String) map.get("im_int_svc_no"));

		keySearchList.add(keySearch);
		updateUserVo.setKeySearchList(keySearchList);

		String loginStatusCode = (String) map.get("login_status_code");
		updateUserVo.setLoginStatusCode(loginStatusCode);

		// 결과값 세팅
		String idpResult = IdpConstants.IM_IDP_RESPONSE_FAIL_CODE;
		String idpResultText = IdpConstants.IM_IDP_RESPONSE_FAIL_CODE_TEXT;

		try {
			UpdateStatusUserResponse updateStatusResponse = this.userSCI.updateStatus(updateUserVo);
		} catch (StorePlatformException spe) {
			LOGGER.debug("RXSetLoginConditionIDP ------- update state excetion error code = " + spe.getErrorInfo().getCode());
		}

		// 미동의 회원 정보 수정
		UpdateMbrOneIDRequest updateMbrOneIDRequest = new UpdateMbrOneIDRequest();
		updateMbrOneIDRequest.setCommonRequest(commonRequest);
		MbrOneID mbrOneID = new MbrOneID();
		mbrOneID.setLoginStatusCode(loginStatusCode);
		mbrOneID.setIntgSvcNumber((String) map.get("im_int_svc_no"));
		updateMbrOneIDRequest.setMbrOneID(mbrOneID);

		try {
			UpdateMbrOneIDResponse updateMbrOneIDResponse = this.userSCI.createAgreeSite(updateMbrOneIDRequest);

			if (updateMbrOneIDResponse.getCommonResponse().getResultCode().equals(this.SC_RETURN + MemberConstants.RESULT_SUCCES)) { // SC반환값이
				// 성공이면
				idpResult = IdpConstants.IM_IDP_RESPONSE_SUCCESS_CODE;
				idpResultText = IdpConstants.IM_IDP_RESPONSE_SUCCESS_CODE_TEXT;
			}
		} catch (StorePlatformException spe) {
			idpResult = IdpConstants.IM_IDP_RESPONSE_FAIL_CODE;
			idpResultText = IdpConstants.IM_IDP_RESPONSE_FAIL_CODE_TEXT;
		}

		ImResult imResult = new ImResult();
		imResult.setCmd("RXSetLoginConditionIDP");
		imResult.setResult(idpResult);
		imResult.setResultText(idpResultText);
		imResult.setImIntSvcNo(map.get("im_int_svc_no").toString());

		return imResult;
	}

	/*
	 * 
	 * <pre> 신규가입정보를 미동의 사이트에 배포 - CMD : RXCreateUserIdIDP </pre>
	 * 
	 * @param map Request로 받은 Parameter Map
	 * 
	 * @return ImResult
	 */
	@Override
	public ImResult executeRXCreateUserIdIDP(HashMap map) {

		LOGGER.debug("rXCreateUserIdIDP ------- Testing Start");

		// Service Method이름은 Provisioning 및 Rx 기능의 'cmd' 값과 동일 해야 함.
		SearchUserRequest searchUserRequest = new SearchUserRequest();
		ImResult imResult = new ImResult();

		// 회원 정보 조회
		// 공통 헤더
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setSystemID((String) map.get("systemID"));
		commonRequest.setTenantID((String) map.get("tenantID"));
		searchUserRequest.setCommonRequest(commonRequest);
		LOGGER.debug("rXCreateUserIdIDP ------- CommonRequest setting");

		List<KeySearch> keySearchList = new ArrayList<KeySearch>();
		KeySearch keySearch = new KeySearch();
		keySearch.setKeyType(MemberConstants.KEY_TYPE_MBR_ID);
		LOGGER.debug("rXCreateUserIdIDP ------- get user_id");
		LOGGER.debug("rXCreateUserIdIDP ------- user_id = " + map.get("user_id").toString());
		if (null != map.get("user_id")) {
			keySearch.setKeyString((String) map.get("user_id"));
		}
		keySearchList.add(keySearch);
		searchUserRequest.setKeySearchList(keySearchList);

		String idpResult = IdpConstants.IM_IDP_RESPONSE_FAIL_CODE;
		String idpResultText = IdpConstants.IM_IDP_RESPONSE_FAIL_CODE_TEXT;

		try {
			this.userSCI.searchUser(searchUserRequest);
		} catch (StorePlatformException spe) {

			// 회원 존재 여부 확인
			// 회원이 존재 하지 않을때 one id 테이블에 추가
			if (spe.getErrorInfo().getCode().endsWith(MemberConstants.RESULT_NOT_FOUND_USER_KEY)) {
				// one id 가입 정보 등록
				UpdateMbrOneIDRequest updateMbrOneIDRequest = new UpdateMbrOneIDRequest();
				updateMbrOneIDRequest.setCommonRequest(commonRequest);
				MbrOneID mbrOneID = new MbrOneID();
				mbrOneID.setIntgSvcNumber(map.get("im_int_svc_no").toString());
				// 통합회원 유형 코드
				mbrOneID.setIntgMbrCaseCode(map.get("im_mem_type_cd").toString());
				// 가입자 상태코드
				if (null != map.get("user_status_code"))
					mbrOneID.setEntryStatusCode(map.get("user_status_code").toString());
				// 사용자 실명 인증여부
				if (null != map.get("is_rname_auth"))
					mbrOneID.setIsRealName(map.get("is_rname_auth").toString());
				mbrOneID.setUserID(map.get("user_id").toString());
				updateMbrOneIDRequest.setMbrOneID(mbrOneID);

				UpdateMbrOneIDResponse updateMbrOneIDResponse = this.userSCI.createAgreeSite(updateMbrOneIDRequest);

				if (updateMbrOneIDResponse != null) {
					if (updateMbrOneIDResponse.getCommonResponse().getResultCode().equals(this.SC_RETURN + MemberConstants.RESULT_SUCCES)) { // SC반환값이
						idpResult = IdpConstants.IM_IDP_RESPONSE_SUCCESS_CODE;
						idpResultText = IdpConstants.IM_IDP_RESPONSE_SUCCESS_CODE_TEXT;
					}
				}
			}

		}
		imResult.setCmd("RXCreateUserIdIDP");
		imResult.setResult(idpResult);
		imResult.setResultText(idpResultText);
		imResult.setImIntSvcNo(map.get("im_int_svc_no").toString());
		LOGGER.debug("rXCreateUserIdIDP ------- return value setting");

		return imResult;
	}

	/*
	 * 
	 * <pre> 직권중지 상태정보 배포 - CMD : RXSetSuspendUserIdIDP </pre>
	 * 
	 * @param map Request로 받은 Parameter Map
	 * 
	 * @return ImResult
	 */
	@Override
	public ImResult executeRXSetSuspendUserIdIDP(HashMap map) {
		UpdateStatusUserRequest updateUserVo = new UpdateStatusUserRequest();

		// 공통 헤더 세팅
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setSystemID((String) map.get("systemID"));
		commonRequest.setTenantID((String) map.get("tenantID"));
		updateUserVo.setCommonRequest(commonRequest);

		List<KeySearch> keySearchList = new ArrayList<KeySearch>();
		KeySearch keySearch = new KeySearch();
		keySearch.setKeyType("INTG_SVC_NO");
		// keySearch.setKeyType("MBR_ID");
		keySearch.setKeyString((String) map.get("im_int_svc_no"));

		keySearchList.add(keySearch);
		updateUserVo.setKeySearchList(keySearchList);

		String susStatusCode = (String) map.get("sus_status_code");
		updateUserVo.setStopStatusCode(susStatusCode);

		// 결과값 세팅
		String idpResult = IdpConstants.IM_IDP_RESPONSE_FAIL_CODE;
		String idpResultText = IdpConstants.IM_IDP_RESPONSE_FAIL_CODE_TEXT;

		try {
			UpdateStatusUserResponse updateStatusResponse = this.userSCI.updateStatus(updateUserVo);
		} catch (StorePlatformException spe) {

		}

		// 미동의 회원 정보 수정
		UpdateMbrOneIDRequest updateMbrOneIDRequest = new UpdateMbrOneIDRequest();
		updateMbrOneIDRequest.setCommonRequest(commonRequest);
		MbrOneID mbrOneID = new MbrOneID();
		mbrOneID.setStopStatusCode(susStatusCode);
		mbrOneID.setIntgSvcNumber((String) map.get("im_int_svc_no"));
		updateMbrOneIDRequest.setMbrOneID(mbrOneID);

		try {
			UpdateMbrOneIDResponse updateMbrOneIDResponse = this.userSCI.createAgreeSite(updateMbrOneIDRequest);
			if (updateMbrOneIDResponse != null) {
				if (updateMbrOneIDResponse.getCommonResponse().getResultCode().equals(this.SC_RETURN + MemberConstants.RESULT_SUCCES)) { // SC반환값이
					// 성공이면
					idpResult = IdpConstants.IM_IDP_RESPONSE_SUCCESS_CODE;
					idpResultText = IdpConstants.IM_IDP_RESPONSE_SUCCESS_CODE_TEXT;
				}
			}
		} catch (StorePlatformException spe) {
			idpResult = IdpConstants.IM_IDP_RESPONSE_FAIL_CODE;
			idpResultText = IdpConstants.IM_IDP_RESPONSE_FAIL_CODE_TEXT;
		}

		ImResult imResult = new ImResult();
		imResult.setCmd("RXSetSuspendUserIdIDP");
		imResult.setResult(idpResult);
		imResult.setResultText(idpResultText);
		imResult.setImIntSvcNo(map.get("im_int_svc_no").toString());

		return imResult;
	}

	/**
	 * 
	 * <pre>
	 * 실명변경 정보 배포.
	 * - CMD : RXUpdateUserNameIDP
	 * </pre>
	 * 
	 * @param map
	 *            Request로 받은 Parameter Map
	 * @return ImResult
	 */

	@Override
	public ImResult executeRXUpdateUserNameIDP(HashMap map) {
		// 실명 인증 정보
		UpdateRealNameRequest updateRealNameRequest = new UpdateRealNameRequest();
		String idpResult = IdpConstants.IM_IDP_RESPONSE_FAIL_CODE;
		String idpResultText = IdpConstants.IM_IDP_RESPONSE_FAIL_CODE_TEXT;

		// 초기화값 가져오기
		String authType = map.get("rname_auth_type_cd").toString();

		// 공통 헤더 세팅
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setSystemID((String) map.get("systemID"));
		commonRequest.setTenantID((String) map.get("tenantID"));
		updateRealNameRequest.setCommonRequest(commonRequest);

		// 회원 조회
		SearchUserRequest searchUserRequest = new SearchUserRequest();
		List<KeySearch> keySearchList = new ArrayList<KeySearch>();
		KeySearch keySearch = new KeySearch();
		keySearch.setKeyType("INTG_SVC_NO");
		if (null != map.get("im_int_svc_no")) {
			keySearch.setKeyString((String) map.get("im_int_svc_no"));
		}
		keySearchList.add(keySearch);
		searchUserRequest.setKeySearchList(keySearchList);
		searchUserRequest.setCommonRequest(commonRequest);

		try {
			SearchUserResponse searchUserRespnse = this.userSCI.searchUser(searchUserRequest);

			if (searchUserRespnse.getUserMbr() != null) {

				// 실명인증 대상 본인/법정대리인 여부
				// 본인
				updateRealNameRequest.setIsOwn(MemberConstants.AUTH_TYPE_OWN);
				updateRealNameRequest.setCommonRequest(commonRequest);
				if (authType.equals("C")) {
					updateRealNameRequest.setIsRealName("N");
				}
				// updateRealNameRequest.setIsRealName(map.get("is_rname_auth").toString());
				updateRealNameRequest.setUserKey(searchUserRespnse.getUserMbr().getUserKey());

				MbrAuth mbrAuth = new MbrAuth();
				if (!authType.equals("C")) {// 초기화 값이 아닐때
					if (map.get("user_birthday") != null)
						mbrAuth.setBirthDay(map.get("user_birthday").toString());
					// ci는 DB 필수 값임으로 없을 경우 공백 입력
					if (map.get("user_ci") == null || map.get("user_ci").toString().length() <= 0) {
						mbrAuth.setCi(" ");
					} else {
						mbrAuth.setCi(map.get("user_ci").toString());
					}
					if (map.get("user_di") != null)
						mbrAuth.setDi(map.get("user_di").toString());
					// mbrAuth.setIsRealName(map.get("is_rname_auth").toString());
					// 내국인여부
					if (map.get("rname_auth_mbr_code") != null) {
						// 내국인
						if (map.get("rname_auth_mbr_code").toString().equals("10")) {
							mbrAuth.setIsDomestic("Y");
						} else {// 외국인
							mbrAuth.setIsDomestic("N");
						}
					}
					// systemid 입력
					mbrAuth.setRealNameSite((String) map.get("systemID"));
					if (map.get("rname_auth_date") != null)
						mbrAuth.setRealNameDate(map.get("rname_auth_date").toString());
					mbrAuth.setMemberCategory(searchUserRespnse.getMbrAuth().getMemberCategory());
					mbrAuth.setTelecom(searchUserRespnse.getMbrAuth().getTelecom());
					mbrAuth.setPhone(searchUserRespnse.getMbrAuth().getPhone());
					if (map.get("user_sex") != null)
						mbrAuth.setSex(map.get("user_sex").toString());
					if (map.get("user_name") != null)
						mbrAuth.setName(map.get("user_name").toString());
					mbrAuth.setMemberKey(searchUserRespnse.getMbrAuth().getMemberKey());
					if (map.get("rname_auth_mns_code") != null) {
						if (map.get("rname_auth_mns_code").toString().equals("1")) {// 휴대폰 인증{
							mbrAuth.setRealNameMethod(MemberConstants.REAL_NAME_AUTH_MOBILE);
						} else if (map.get("rname_auth_mns_code").toString().equals("2")) {// 아이핀 인증
							mbrAuth.setRealNameMethod(MemberConstants.REAL_NAME_AUTH_IPIN);
						}
					}
				} else {// 실명인증 정보 초기화 요청 시
					// CI 필수값만 세팅
					mbrAuth.setCi(" ");
					mbrAuth.setIsDomestic(" ");
					mbrAuth.setIsRealName("N");
				}

				updateRealNameRequest.setUserMbrAuth(mbrAuth);

				UpdateRealNameResponse updateRealNameResponse = this.userSCI.updateRealName(updateRealNameRequest);
				LOGGER.debug("response param : {}", updateRealNameResponse.getUserKey());

				// oneID 테이블 업데이트
				UpdateMbrOneIDRequest updateMbrOneIDRequest = new UpdateMbrOneIDRequest();
				updateMbrOneIDRequest.setCommonRequest(commonRequest);
				MbrOneID mbrOneID = new MbrOneID();
				mbrOneID.setIntgSvcNumber((String) map.get("im_int_svc_no"));
				// 실명 인증여부
				// CI값 존재 여부
				if (authType.equals("C")) {
					mbrOneID.setIsRealName("N");
					mbrOneID.setIsCi("N");
				} else {
					mbrOneID.setIsRealName(map.get("is_rname_auth").toString());
					if (map.get("user_ci") == null || map.get("user_ci").toString().length() <= 0) {
						mbrOneID.setIsCi("N");
					} else {
						mbrOneID.setIsCi("Y");
					}
				}
				updateMbrOneIDRequest.setMbrOneID(mbrOneID);

				UpdateMbrOneIDResponse updateMbrOneIDResponse = this.userSCI.createAgreeSite(updateMbrOneIDRequest);
				if (updateMbrOneIDResponse != null) {
					if (updateRealNameResponse.getCommonResponse().getResultCode().equals(this.SC_RETURN + MemberConstants.RESULT_SUCCES)
							&& updateMbrOneIDResponse.getCommonResponse().getResultCode().equals(this.SC_RETURN + MemberConstants.RESULT_SUCCES)) {
						idpResult = IdpConstants.IM_IDP_RESPONSE_SUCCESS_CODE;
						idpResultText = IdpConstants.IM_IDP_RESPONSE_SUCCESS_CODE_TEXT;
					}
				}
			}
		} catch (StorePlatformException spe) {
			idpResult = IdpConstants.IM_IDP_RESPONSE_FAIL_CODE;
			idpResultText = IdpConstants.IM_IDP_RESPONSE_FAIL_CODE_TEXT;
		}

		ImResult imResult = new ImResult();
		imResult.setCmd("rXUpdateUserNameIDP");
		imResult.setResult(idpResult);
		imResult.setResultText(idpResultText);
		imResult.setImIntSvcNo(map.get("im_int_svc_no").toString());

		return imResult;
	}

	/**
	 * 
	 * <pre>
	 * 법정대리인 동의정보 변경 배포.
	 * - CMD : RXUpdateGuardianInfoIDP
	 * </pre>
	 * 
	 * @param map
	 *            Request로 받은 Parameter Map
	 * @return ImResult
	 */

	@Override
	public ImResult executeRXUpdateGuardianInfoIDP(HashMap map) {
		// 실명 인증 정보
		UpdateRealNameRequest updateRealNameRequest = new UpdateRealNameRequest();

		String idpResult = IdpConstants.IM_IDP_RESPONSE_FAIL_CODE;
		String idpResultText = IdpConstants.IM_IDP_RESPONSE_FAIL_CODE_TEXT;

		// 공통 헤더 세팅
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setSystemID((String) map.get("systemID"));
		commonRequest.setTenantID((String) map.get("tenantID"));
		updateRealNameRequest.setCommonRequest(commonRequest);

		// 회원 조회
		SearchUserRequest searchUserRequest = new SearchUserRequest();
		List<KeySearch> keySearchList = new ArrayList<KeySearch>();
		KeySearch keySearch = new KeySearch();
		keySearch.setKeyType("INTG_SVC_NO");
		if (null != map.get("im_int_svc_no")) {
			keySearch.setKeyString((String) map.get("im_int_svc_no"));
		}
		keySearchList.add(keySearch);
		searchUserRequest.setKeySearchList(keySearchList);
		searchUserRequest.setCommonRequest(commonRequest);

		try {
			SearchUserResponse searchUserRespnse = this.userSCI.searchUser(searchUserRequest);

			if (searchUserRespnse.getUserMbr() != null) {

				// 실명인증 대상 본인/법정대리인 여부
				// 법정대리인
				updateRealNameRequest.setIsOwn(MemberConstants.AUTH_TYPE_PARENT);
				updateRealNameRequest.setCommonRequest(commonRequest);
				// updateRealNameRequest.setIsRealName(map.get("is_parent_approve").toString());
				updateRealNameRequest.setUserKey(searchUserRespnse.getUserMbr().getUserKey());

				MbrLglAgent mbrLglAgent = new MbrLglAgent();
				mbrLglAgent.setParentBirthDay(map.get("parent_birthday").toString());
				if (map.get("parent_rname_auth_key") != null) {
					mbrLglAgent.setParentCI(map.get("parent_rname_auth_key").toString());
				}
				// mbrLglAgent.setIsParent(map.get("is_parent_approve").toString());
				mbrLglAgent.setParentRealNameSite((String) map.get("systemID"));
				if (map.get("parent_approve_date").toString().length() == 8) {
					mbrLglAgent.setParentDate(map.get("parent_approve_date").toString() + "000000");
				} else if (map.get("parent_approve_date").toString().length() > 8) {
					mbrLglAgent.setParentDate(map.get("parent_approve_date").toString());
				}
				mbrLglAgent.setParentName(map.get("parent_name").toString());

				// 법정대리인 관계 코드
				if (map.get("parent_type").toString().equals("0")) {// 부
					mbrLglAgent.setParentType(MemberConstants.PARENT_TYPE_FATHER);
				} else if (map.get("parent_type").toString().equals("1")) {// 모
					mbrLglAgent.setParentType(MemberConstants.PARENT_TYPE_MOTHER);
				} else { // 기타 : parent_type == 2
					mbrLglAgent.setParentType(MemberConstants.PARENT_TYPE_ECT);
				}

				// 법정 대리인 실명인증 수단코드
				if (map.get("parent_rname_auth_type").toString().equals("1")) {// 휴대폰 인증
					mbrLglAgent.setParentRealNameMethod(MemberConstants.REAL_NAME_AUTH_MOBILE);
				} else if (map.get("parent_rname_auth_type").toString().equals("3")) {// 아이핀 인증
					mbrLglAgent.setParentRealNameMethod(MemberConstants.REAL_NAME_AUTH_IPIN);
				} else if (map.get("parent_rname_auth_type").toString().equals("6")) {// 이메일
					mbrLglAgent.setParentRealNameMethod(MemberConstants.REAL_NAME_AUTH_EMAIL);
				}

				if (map.get("parent_email") != null)
					mbrLglAgent.setParentEmail(map.get("parent_email").toString());

				updateRealNameRequest.setMbrLglAgent(mbrLglAgent);

				UpdateRealNameResponse updateRealNameResponse = this.userSCI.updateRealName(updateRealNameRequest);
				if (updateRealNameResponse != null) {
					LOGGER.debug("response param : {}", updateRealNameResponse.getUserKey());
					if (updateRealNameResponse.getCommonResponse().getResultCode().equals(this.SC_RETURN + MemberConstants.RESULT_SUCCES)) {
						idpResult = IdpConstants.IM_IDP_RESPONSE_SUCCESS_CODE;
						idpResultText = IdpConstants.IM_IDP_RESPONSE_SUCCESS_CODE_TEXT;
					}
				}
			}
		} catch (StorePlatformException spe) {
			idpResult = IdpConstants.IM_IDP_RESPONSE_FAIL_CODE;
			idpResultText = IdpConstants.IM_IDP_RESPONSE_FAIL_CODE_TEXT;
		}

		ImResult imResult = new ImResult();
		imResult.setCmd("RXUpdateGuardianInfoIDP");
		imResult.setResult(idpResult);
		imResult.setResultText(idpResultText);
		imResult.setImIntSvcNo(map.get("im_int_svc_no").toString());

		return imResult;
	}

	/*
	 * 
	 * <pre> 활성화된통합 ID 가입자상태정보배포 - CMD : RXActivateUserIdIDP . </pre>
	 * 
	 * @param map Request로 받은 Parameter Map
	 * 
	 * @return HashMap
	 */
	@Override
	public ImResult executeRXActivateUserIdIDP(HashMap<String, String> map) {
		LOGGER.debug("rXActivateUserIdIDP ------- Start");

		String imIntSvcNo = map.get("im_int_svc_no").toString(); // 통합 서비스 번호

		String userId = "";
		String isEmailAuth = ""; // 이메일 인증여부 Y인경우에 ONEID정보의 회원가입상태코드를
		SearchUserResponse searchUserResponse = null;

		imIntSvcNo = map.get("im_int_svc_no").toString();
		userId = map.get("user_id").toString();
		isEmailAuth = map.get("is_email_auth").toString();

		ImResult imResult = new ImResult();
		imResult.setCmd("RXActivateUserIdIDP");
		imResult.setImIntSvcNo(imIntSvcNo);
		imResult.setUserId(userId);

		SearchUserRequest searchUserRequest = new SearchUserRequest();

		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID(map.get("tenantID").toString());
		commonRequest.setSystemID(map.get("systemID").toString());

		searchUserRequest.setCommonRequest(commonRequest);

		List<KeySearch> keySearchList = new ArrayList<KeySearch>();
		KeySearch keySearch = new KeySearch();
		keySearch.setKeyType("INTG_SVC_NO");
		keySearch.setKeyString(imIntSvcNo); // 통합 서비스 번호
		keySearchList.add(keySearch);
		keySearch = new KeySearch();
		keySearch.setKeyType(MemberConstants.KEY_TYPE_MBR_ID);
		keySearch.setKeyString(userId); // 사용자 ID추가
		keySearchList.add(keySearch);

		searchUserRequest.setKeySearchList(keySearchList);

		try {
			searchUserResponse = this.userSCI.searchUser(searchUserRequest);
		} catch (StorePlatformException spe) { // 회원정보 조회시 오류발생시라도 프로비저닝은 성공으로 처리함.
			imResult.setResult(IdpConstants.IM_IDP_RESPONSE_SUCCESS_CODE);
			imResult.setResultText(IdpConstants.IM_IDP_RESPONSE_SUCCESS_CODE_TEXT);
		}

		try {
			if (searchUserResponse != null) { // 통합서비스 번호로 조회한 회원정보가 있을경우만 로직처리
				// 조회되어진 사용자의 상태값이 정상이 아닌경우에 사용자 상태변경 정상 상태로 TB_US_USERMBR 데이터를 수정함
				if (!MemberConstants.MAIN_STATUS_NORMAL.equals(searchUserResponse.getUserMbr().getUserMainStatus())
						|| !MemberConstants.SUB_STATUS_NORMAL.equals(searchUserResponse.getUserMbr().getUserSubStatus())) {
					UpdateStatusUserRequest updateStatusUserRequest = new UpdateStatusUserRequest();

					updateStatusUserRequest.setCommonRequest(commonRequest);

					if (isEmailAuth.equals(MemberConstants.USE_Y)) { // 이메일 인증이 Y인경우에 정상, 정상 상태로 함. check!!!!
						updateStatusUserRequest.setUserMainStatus(MemberConstants.MAIN_STATUS_NORMAL);
						updateStatusUserRequest.setUserSubStatus(MemberConstants.SUB_STATUS_NORMAL);
					}

					KeySearch updateKeySearch = new KeySearch();
					updateKeySearch.setKeyType("INSD_USERMBR_NO");
					updateKeySearch.setKeyString(searchUserResponse.getUserMbr().getUserKey()); // 내부사용자 회원번호

					List<KeySearch> updateKeySearchList = new ArrayList<KeySearch>();
					updateKeySearchList.add(keySearch);

					updateStatusUserRequest.setKeySearchList(updateKeySearchList);

					this.userSCI.updateStatus(updateStatusUserRequest);

				}
			}
		} catch (StorePlatformException spe) { // 회원정보가 없더라도 성공처리함 OCB,TMAP등에서 RXACTIVATEUSERIDP가 내려오므로
			imResult.setResult(IdpConstants.IM_IDP_RESPONSE_SUCCESS_CODE);
			imResult.setResultText(IdpConstants.IM_IDP_RESPONSE_SUCCESS_CODE_TEXT);
		}

		try {
			LOGGER.debug("ONEID DATA MERGE START");
			// ONEID에 데이터 입력
			UpdateMbrOneIDRequest updateMbrOneIDRequest = new UpdateMbrOneIDRequest();
			updateMbrOneIDRequest.setCommonRequest(commonRequest);
			MbrOneID mbrOneID = new MbrOneID();
			mbrOneID.setStopStatusCode(IdpConstants.SUS_STATUS_RELEASE); // 직권중지해제 기본셋팅
			mbrOneID.setIntgSvcNumber(map.get("im_int_svc_no"));
			if (isEmailAuth.equals(MemberConstants.USE_Y))
				mbrOneID.setEntryStatusCode(MemberConstants.JOIN_STATUS_CODE_NORMAL);// 정상
			updateMbrOneIDRequest.setMbrOneID(mbrOneID);

			this.userSCI.createAgreeSite(updateMbrOneIDRequest);

			LOGGER.debug("ONEID DATA MERGE COMPLETE");
		} catch (StorePlatformException spe) {
			imResult.setResult(IdpConstants.IM_IDP_RESPONSE_FAIL_CODE);
			imResult.setResultText(IdpConstants.IM_IDP_RESPONSE_FAIL_CODE_TEXT);
			return imResult;
		}

		imResult.setResult(IdpConstants.IM_IDP_RESPONSE_SUCCESS_CODE);
		imResult.setResultText(IdpConstants.IM_IDP_RESPONSE_SUCCESS_CODE_TEXT);
		LOGGER.debug("rXActivateUserIdIDP ------- End");
		return imResult;

	}

	/*
	 * 
	 * <pre> 전체서비스사이트에해지배포 - CMD : RXDeleteUserIdIDP . </pre>
	 * 
	 * @param map Request 받은 Parameter Map
	 * 
	 * @return HashMap
	 */
	@Override
	public ImResult executeRXDeleteUserIdIDP(HashMap<String, String> map) {
		ImResult imResult = new ImResult();
		String imIntSvcNo = map.get("im_int_svc_no").toString(); // 통합 서비스 번호
		String userId = map.get("user_id").toString(); // 회원 ID
		String userKey = "";
		String prevMbrNoForgameCenter = ""; // 게임센터 연동을 위한 MbrNo
		String currentMbrNoForgameCenter = ""; // 게임센터 연동을 위한 MbrNo

		if (map.get("user_key") != null)
			currentMbrNoForgameCenter = map.get("user_key").toString(); // 게임센터 연동을 위한 변수mbrNo 셋팅

		imResult.setCmd("RXDeleteUserIdIDP");
		imResult.setImIntSvcNo(imIntSvcNo);
		imResult.setUserId(userId);

		// 통합서비스 번호 & mbr_id로 멤버 조회
		SearchUserRequest searchUserRequest = new SearchUserRequest();

		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID(map.get("tenantID").toString());
		commonRequest.setSystemID(map.get("systemID").toString());

		searchUserRequest.setCommonRequest(commonRequest);

		List<KeySearch> keySearchList = new ArrayList<KeySearch>();
		KeySearch keySearch = new KeySearch();
		keySearch.setKeyType("INTG_SVC_NO");
		keySearch.setKeyString(imIntSvcNo); // 통합 서비스 번호
		keySearchList.add(keySearch);
		keySearch = new KeySearch();
		keySearch.setKeyType(MemberConstants.KEY_TYPE_MBR_ID);
		keySearch.setKeyString(userId); // 사용자 ID추가
		keySearchList.add(keySearch);

		searchUserRequest.setKeySearchList(keySearchList);
		SearchUserResponse searchUserResponse = null;

		try {
			searchUserResponse = this.userSCI.searchUser(searchUserRequest);
			if (searchUserResponse != null) {
				prevMbrNoForgameCenter = searchUserResponse.getUserMbr().getImMbrNo();
				userKey = searchUserResponse.getUserKey();
			}
		} catch (StorePlatformException spe) { // 회원정보 조회시 오류발생시라도 프로비저닝은 성공으로 처리함.
			imResult.setResult(IdpConstants.IM_IDP_RESPONSE_SUCCESS_CODE);
			imResult.setResultText(IdpConstants.IM_IDP_RESPONSE_SUCCESS_CODE_TEXT);
			return imResult;
		}

		try {

			if (searchUserResponse != null) {
				RemoveUserRequest removeUserRequest = new RemoveUserRequest();
				removeUserRequest.setCommonRequest(commonRequest);
				removeUserRequest.setUserKey(searchUserResponse.getUserKey());
				removeUserRequest.setSecedeReasonCode(MemberConstants.WITHDRAW_REASON_OTHER);
				removeUserRequest.setSecedeReasonMessage("프로비저닝"); // DB 탈퇴사유설명 칼럼에 프로비저닝으로 입력처리.
				removeUserRequest.setSecedeTypeCode(MemberConstants.USER_WITHDRAW_CLASS_PROVISIONING);
				this.userSCI.remove(removeUserRequest);

				RemoveMbrOneIDRequest removeMbrOneIDRequest = new RemoveMbrOneIDRequest();
				removeMbrOneIDRequest.setCommonRequest(commonRequest);
				removeMbrOneIDRequest.setImSvcNo(imIntSvcNo);
				this.userSCI.removeMbrOneID(removeMbrOneIDRequest);
			}
		} catch (StorePlatformException spe) {

			imResult.setResult(IdpConstants.IM_IDP_RESPONSE_FAIL_CODE);
			imResult.setResultText(IdpConstants.IM_IDP_RESPONSE_FAIL_CODE_TEXT);
			return imResult;
		}

		try {
			/* 게임센터 연동 */
			GameCenterSacReq gameCenterSacReq = new GameCenterSacReq();
			gameCenterSacReq.setUserKey(userKey);
			gameCenterSacReq.setPreUserKey(userKey);
			gameCenterSacReq.setMbrNo(currentMbrNoForgameCenter);
			gameCenterSacReq.setPreMbrNo(prevMbrNoForgameCenter);
			gameCenterSacReq.setSystemId(map.get("systemID").toString());
			gameCenterSacReq.setTenantId(map.get("tenantID").toString());
			gameCenterSacReq.setWorkCd(MemberConstants.GAMECENTER_WORK_CD_USER_SECEDE);
			this.deviceService.insertGameCenterIF(gameCenterSacReq);

			// 회원 탈퇴 정보를 전달 하는 mq 호출.
			if (searchUserResponse != null) {

				RemoveMemberAmqpSacReq mqInfo = new RemoveMemberAmqpSacReq();
				mqInfo.setUserId(userId);
				mqInfo.setUserKey(searchUserResponse.getUserKey());
				mqInfo.setWorkDt(DateUtil.getToday("yyyyMMddHHmmss"));

				try {
					this.memberRetireAmqpTemplate.convertAndSend(mqInfo);
				} catch (Exception e) {

				}

			}

		} catch (StorePlatformException spe) {
			imResult.setResult(IdpConstants.IM_IDP_RESPONSE_FAIL_CODE);
			imResult.setResultText(IdpConstants.IM_IDP_RESPONSE_FAIL_CODE_TEXT);
			return imResult;
		}

		imResult.setResult(IdpConstants.IM_IDP_RESPONSE_SUCCESS_CODE);
		imResult.setResultText(IdpConstants.IM_IDP_RESPONSE_SUCCESS_CODE_TEXT);
		return imResult;
	}

	/*
	 * 
	 * <pre> 탈퇴가 가능한 통합회원 확인요청 - CMD : RXPreCheckDeleteUserIDP . </pre>
	 * 
	 * @param map Request로 받은 Parameter Map
	 * 
	 * @return HashMap
	 */
	@Override
	public ImResult executeRXPreCheckDeleteUserIDP(HashMap map) {
		SearchUserRequest searchUserRequest = new SearchUserRequest();

		ImResult imResult = new ImResult();

		// 회원 정보 조회
		// 공통 헤더
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setSystemID((String) map.get("systemID"));
		commonRequest.setTenantID((String) map.get("tenantID"));
		searchUserRequest.setCommonRequest(commonRequest);

		String userId = map.get("user_id").toString();

		List<KeySearch> keySearchList = new ArrayList<KeySearch>();
		KeySearch keySearch = new KeySearch();
		keySearch.setKeyType(MemberConstants.KEY_TYPE_MBR_ID);
		keySearch.setKeyString(userId);

		keySearchList.add(keySearch);
		searchUserRequest.setKeySearchList(keySearchList);

		String idpResult = IdpConstants.IM_IDP_RESPONSE_FAIL_CODE;
		String idpResultText = IdpConstants.IM_IDP_RESPONSE_FAIL_CODE_TEXT;
		String delYN = "N";
		UserMbr getUserMbr = null;
		try {
			SearchUserResponse searchUserRespnse = this.userSCI.searchUser(searchUserRequest);

			if (searchUserRespnse != null)
				getUserMbr = searchUserRespnse.getUserMbr();

			// 회원 정보 존재
			if (getUserMbr != null) {
				LOGGER.debug("rXPreCheckDeleteUserIDP ------- 회원 정보 존재");
				delYN = "Y";
				idpResult = IdpConstants.IM_IDP_RESPONSE_SUCCESS_CODE;
				idpResultText = IdpConstants.IM_IDP_RESPONSE_SUCCESS_CODE_TEXT;

				imResult.setCmd("RXPreCheckDeleteUserIDP");
				imResult.setResult(idpResult);
				imResult.setResultText(idpResultText);
				imResult.setImIntSvcNo(map.get("im_int_svc_no").toString());
				imResult.setUserId(userId);
				imResult.setIsCancelAble(delYN);
			}
		} catch (StorePlatformException spe) {

			if (spe.getErrorInfo().getCode().endsWith(MemberConstants.RESULT_NOT_FOUND_USER_KEY)) {
				LOGGER.debug("rXPreCheckDeleteUserIDP ------- 회원 정보 없음");
				imResult.setCmd("RXPreCheckDeleteUserIDP");
				imResult.setResult(idpResult);
				imResult.setResultText(idpResultText);
				imResult.setImIntSvcNo(map.get("im_int_svc_no").toString());
				imResult.setUserId(userId);
				imResult.setIsCancelAble(delYN);
				String userPocIp = this.messageSourceAccessor.getMessage("tenantID" + (String) map.get("tenantID"), LocaleContextHolder.getLocale());
				String cancelUrl = this.messageSourceAccessor.getMessage("cancelUrl", LocaleContextHolder.getLocale());
				LOGGER.debug("rXPreCheckDeleteUserIDP cancelRetUrl = " + "http://" + userPocIp + cancelUrl);
				imResult.setCancelRetUrl("http://" + userPocIp + cancelUrl);
				imResult.setTermRsnCd(IdpConstants.IM_IDP_RESPONSE_FAIL_MEMBERSELECT_CODE);
				imResult.setCancelEtc("(" + userId + ")" + IdpConstants.IM_IDP_RESPONSE_FAIL_MEMBERSELECT_CODE_TEXT);

			}

		}

		return imResult;
	}

	/*
	 * 
	 * <pre> 이용해지가 가능한 통합회원 확인요청 - CMD : RXPreCheckDisagreeUserIDP . </pre>
	 * 
	 * @param map Request로 받은 Parameter Map
	 * 
	 * @return HashMap
	 */
	@Override
	public ImResult executeRXPreCheckDisagreeUserIDP(HashMap map) {
		SearchUserRequest searchUserRequest = new SearchUserRequest();
		ImResult imResult = new ImResult();

		// 회원 정보 조회
		// 공통 헤더
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setSystemID((String) map.get("systemID"));
		commonRequest.setTenantID((String) map.get("tenantID"));
		searchUserRequest.setCommonRequest(commonRequest);

		String userId = map.get("user_id").toString();

		List<KeySearch> keySearchList = new ArrayList<KeySearch>();
		KeySearch keySearch = new KeySearch();
		keySearch.setKeyType(MemberConstants.KEY_TYPE_MBR_ID);
		keySearch.setKeyString(userId);

		keySearchList.add(keySearch);
		searchUserRequest.setKeySearchList(keySearchList);

		String idpResult = IdpConstants.IM_IDP_RESPONSE_FAIL_CODE;
		String idpResultText = IdpConstants.IM_IDP_RESPONSE_FAIL_CODE_TEXT;
		String delYN = "N";
		UserMbr getUserMbr = null;
		try {
			SearchUserResponse searchUserRespnse = this.userSCI.searchUser(searchUserRequest);

			if (searchUserRespnse != null)
				getUserMbr = searchUserRespnse.getUserMbr();

			// 회원 정보 존재
			if (getUserMbr != null) {
				LOGGER.debug("RXPreCheckDisagreeUserIDP ------- 회원 정보 존재");
				delYN = "Y";
				idpResult = IdpConstants.IM_IDP_RESPONSE_SUCCESS_CODE;
				idpResultText = IdpConstants.IM_IDP_RESPONSE_SUCCESS_CODE_TEXT;

				imResult.setCmd("RXPreCheckDisagreeUserIDP");
				imResult.setResult(idpResult);
				imResult.setResultText(idpResultText);
				imResult.setImIntSvcNo(map.get("im_int_svc_no").toString());
				imResult.setUserId(userId);
				imResult.setIsCancelAble(delYN);
			}
		} catch (StorePlatformException spe) {

			if (spe.getErrorInfo().getCode().endsWith(MemberConstants.RESULT_NOT_FOUND_USER_KEY)) {
				LOGGER.debug("RXPreCheckDisagreeUserIDP ------- 회원 정보 없음");
				imResult.setCmd("RXPreCheckDisagreeUserIDP");
				imResult.setResult(idpResult);
				imResult.setResultText(idpResultText);
				imResult.setImIntSvcNo(map.get("im_int_svc_no").toString());
				imResult.setUserId(userId);
				imResult.setIsCancelAble(delYN);
				String userPocIp = this.messageSourceAccessor.getMessage("tenantID" + (String) map.get("tenantID"), LocaleContextHolder.getLocale());
				String cancelUrl = this.messageSourceAccessor.getMessage("cancelUrl", LocaleContextHolder.getLocale());
				LOGGER.debug("RXPreCheckDisagreeUserIDP cancelRetUrl = " + "http://" + userPocIp + cancelUrl);
				imResult.setCancelRetUrl("http://" + userPocIp + cancelUrl);
				imResult.setTermRsnCd(IdpConstants.IM_IDP_RESPONSE_FAIL_MEMBERSELECT_CODE);
				imResult.setCancelEtc("(" + userId + ")" + IdpConstants.IM_IDP_RESPONSE_FAIL_MEMBERSELECT_CODE_TEXT);
			}
		}

		return imResult;
	}

	/*
	 * 
	 * <pre> OCB 해지 및 철회 요청 설정 배포 - CMD : RXSetOCBDisagreeIDP . </pre>
	 * 
	 * @param map Request로 받은 Parameter Map
	 * 
	 * @return HashMap
	 */
	@Override
	public ImResult executeRXSetOCBDisagreeIDP(HashMap map) {
		String idpResult = IdpConstants.IM_IDP_RESPONSE_FAIL_CODE;
		String idpResultText = IdpConstants.IM_IDP_RESPONSE_FAIL_CODE_TEXT;
		UpdateUserRequest updateUserRequest = new UpdateUserRequest();
		SearchUserRequest searchUserRequest = new SearchUserRequest();

		String ocbTermReq = map.get("is_ocb_term_req").toString(); // OCB해지 요청 여부
		String imIntSvcNo = map.get("im_int_svc_no").toString();
		String userId = map.get("user_id").toString();

		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setSystemID((String) map.get("systemID"));
		commonRequest.setTenantID((String) map.get("tenantID"));
		updateUserRequest.setCommonRequest(commonRequest);
		searchUserRequest.setCommonRequest(commonRequest);

		List<KeySearch> keySearchList = new ArrayList<KeySearch>();
		KeySearch keySearch = new KeySearch();
		keySearch.setKeyType(MemberConstants.KEY_TYPE_MBR_ID);
		keySearch.setKeyString(userId);

		keySearchList.add(keySearch);
		searchUserRequest.setKeySearchList(keySearchList);
		UserMbr getUserMbr = null;
		try {
			SearchUserResponse searchUserRespnse = this.userSCI.searchUser(searchUserRequest);

			if (searchUserRespnse != null) // 회원 수정 정보 세팅
				getUserMbr = searchUserRespnse.getUserMbr();

			if (getUserMbr != null) {
				UserMbr userMbr = new UserMbr();
				userMbr.setImSvcNo(imIntSvcNo); // 통합서비스 번호 M
				userMbr.setUserID(userId); // mbrID M
				userMbr.setIsMemberPoint(ocbTermReq);
				userMbr.setUserKey(getUserMbr.getUserKey());
				updateUserRequest.setUserMbr(userMbr);
			}

			this.userSCI.updateUser(updateUserRequest);

			// oneID 테이블 업데이트
			UpdateMbrOneIDRequest updateMbrOneIDRequest = new UpdateMbrOneIDRequest();
			updateMbrOneIDRequest.setCommonRequest(commonRequest);
			MbrOneID mbrOneID = new MbrOneID();
			mbrOneID.setIntgSvcNumber(imIntSvcNo);
			mbrOneID.setIsMemberPoint(ocbTermReq);
			updateMbrOneIDRequest.setMbrOneID(mbrOneID);

			this.userSCI.createAgreeSite(updateMbrOneIDRequest);

			// if (updateUserResponse.getCommonResponse().getResultCode()
			// .equals(this.SC_RETURN + memberConstant.RESULT_SUCCES)) {
			idpResult = IdpConstants.IM_IDP_RESPONSE_SUCCESS_CODE;
			idpResultText = IdpConstants.IM_IDP_RESPONSE_SUCCESS_CODE_TEXT;
			// }
		} catch (StorePlatformException spe) {
			LOGGER.debug("RXSetOCBDisagreeIDP fail to set success as result");
		}

		ImResult imResult = new ImResult();
		imResult.setCmd("RXSetOCBDisagreeIDP");
		imResult.setResult(idpResult);
		imResult.setResultText(idpResultText);
		imResult.setImIntSvcNo(imIntSvcNo);
		imResult.setImIntSvcNo(userId);

		return imResult;
	}

	/*
	 * 
	 * <pre> 이용해지변경 프로파일 배포 - CMD : executeRXUpdateDisagreeUserIDP . </pre>
	 * 
	 * @param map Request 받은 Parameter Map
	 * 
	 * @return HashMap
	 */
	@Override
	public ImResult executeRXUpdateDisagreeUserIDP(HashMap<String, String> map) {
		ImResult imResult = new ImResult();
		String imIntSvcNo = map.get("im_int_svc_no").toString(); // 통합 서비스 번호
		String userId = map.get("user_id").toString(); // 회원 ID
		String joinSiteTotalList = map.get("join_sst_list").toString();// StringUtils.equals("", "");
		String modifySstCode = map.get("modify_sst_code").toString();
		SearchUserResponse searchUserResponse = null;
		String tenantId = map.get("tenantID").toString();
		String systemId = map.get("systemID").toString();
		boolean siteCodeCheck = false;

		imResult.setCmd("RXUpdateDisagreeUserIDP");
		imResult.setImIntSvcNo(imIntSvcNo);
		imResult.setUserId(userId);

		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID(tenantId);
		commonRequest.setSystemID(systemId);

		SearchUserRequest searchUserRequest = new SearchUserRequest();
		searchUserRequest.setCommonRequest(commonRequest);

		// 통합서비스 번호 & mbr_id로 멤버 조회
		List<KeySearch> keySearchList = new ArrayList<KeySearch>();
		KeySearch keySearch = new KeySearch();
		keySearch.setKeyType("INTG_SVC_NO");
		keySearch.setKeyString(imIntSvcNo); // 통합 서비스 번호
		keySearchList.add(keySearch);
		keySearch = new KeySearch();
		keySearch.setKeyType(MemberConstants.KEY_TYPE_MBR_ID);
		keySearch.setKeyString(userId); // 사용자 ID추가
		keySearchList.add(keySearch);

		searchUserRequest.setKeySearchList(keySearchList);

		// 이용해지변경 동의 사이트중 41100 Tstore가 있는경우 이용해지상황이 아님 41100이 없는경우가 이용해지 상황임.
		if (!"".equals(joinSiteTotalList)) {
			LOGGER.debug("====JSH====" + joinSiteTotalList);
			joinSiteTotalList = joinSiteTotalList.replaceAll(" ", "");
			LOGGER.debug("====JSH====" + joinSiteTotalList);
			// ex : 41100,null,20130923,212917,tstore000001741|90300,null,20130917,113426,null
			String[] tempSplit = joinSiteTotalList.split("\\|");
			for (int i = 0; i < tempSplit.length; i++) {
				String[] tmpSplit = tempSplit[i].split(",");
				LOGGER.debug("====JSH====" + tempSplit[i]);
				if (null != tmpSplit && tmpSplit.length >= 1 && null != tmpSplit[0] && MemberConstants.SSO_SST_CD_TSTORE.equals(tmpSplit[0])) {
					siteCodeCheck = true;
					break;
				}
			}
		}

		// 이용동의 해지인 경우
		if (!siteCodeCheck) { // tStore 이용동의 해지 join_sst_list 안에 tstore 41100 이 없으면 siteCodeCheck=false로 셋팅되서 tstore이용동의
								// 해지 상태임.
			try {
				searchUserResponse = this.userSCI.searchUser(searchUserRequest);
			} catch (StorePlatformException spe) { // 회원정보 조회시 오류발생시라도 프로비저닝은 성공으로 처리함.
				imResult.setResult(IdpConstants.IM_IDP_RESPONSE_SUCCESS_CODE);
				imResult.setResultText(IdpConstants.IM_IDP_RESPONSE_SUCCESS_CODE_TEXT);
				return imResult;
			}

			try {

				if (searchUserResponse != null) {
					RemoveUserRequest removeUserRequest = new RemoveUserRequest();
					removeUserRequest.setCommonRequest(commonRequest);
					removeUserRequest.setUserKey(searchUserResponse.getUserKey());
					removeUserRequest.setSecedeReasonCode(MemberConstants.WITHDRAW_REASON_OTHER);
					removeUserRequest.setSecedeReasonMessage("프로비저닝"); // DB 탈퇴사유설명 칼럼에 프로비저닝으로 입력처리.
					removeUserRequest.setSecedeTypeCode(MemberConstants.USER_WITHDRAW_CLASS_PROVISIONING);
					this.userSCI.remove(removeUserRequest);

					// as is src 이용동의 해지시 tstore에서 이용동의 해지면서 변경서비스사이트코드가 티스토어가 아닌경우 게임센터 연동을 함
					if (!MemberConstants.SSO_SST_CD_TSTORE.equals(modifySstCode)) {
						GameCenterSacReq gameCenterSacReq = new GameCenterSacReq();
						gameCenterSacReq.setUserKey(searchUserResponse.getUserKey());
						gameCenterSacReq.setPreUserKey(searchUserResponse.getUserKey());
						gameCenterSacReq.setMbrNo(searchUserResponse.getUserMbr().getImMbrNo());
						gameCenterSacReq.setPreMbrNo(searchUserResponse.getUserMbr().getImMbrNo());
						gameCenterSacReq.setSystemId(systemId);
						gameCenterSacReq.setTenantId(tenantId);
						gameCenterSacReq.setWorkCd(MemberConstants.GAMECENTER_WORK_CD_IMUSER_CHANGE);
						this.deviceService.insertGameCenterIF(gameCenterSacReq);
					}

					RemoveMemberAmqpSacReq mqInfo = new RemoveMemberAmqpSacReq();
					mqInfo.setUserId(userId);
					mqInfo.setUserKey(searchUserResponse.getUserKey());
					mqInfo.setWorkDt(DateUtil.getToday("yyyyMMddHHmmss"));
					try {
						this.memberRetireAmqpTemplate.convertAndSend(mqInfo);
					} catch (Exception e) {

					}
				}

			} catch (StorePlatformException spe) {

				imResult.setResult(IdpConstants.IM_IDP_RESPONSE_FAIL_CODE);
				imResult.setResultText(IdpConstants.IM_IDP_RESPONSE_FAIL_CODE_TEXT);
				return imResult;
			}

		} else { // 타사이트 이용동의 해지
			try {
				searchUserResponse = this.userSCI.searchUser(searchUserRequest);

				if (searchUserResponse != null) {
					UpdateUserRequest updateUserRequest = new UpdateUserRequest();
					updateUserRequest.setCommonRequest(commonRequest);
					UserMbr userMbr = searchUserResponse.getUserMbr();

					if (!"".equals(joinSiteTotalList)) {
						userMbr.setImSiteCode(joinSiteTotalList);
					}

					updateUserRequest.setUserMbr(userMbr);
					this.userSCI.updateUser(updateUserRequest);
				}
			} catch (StorePlatformException spe) { // 회원정보 조회시 오류발생시라도 프로비저닝은 성공으로 처리함.
				imResult.setResult(IdpConstants.IM_IDP_RESPONSE_SUCCESS_CODE);
				imResult.setResultText(IdpConstants.IM_IDP_RESPONSE_SUCCESS_CODE_TEXT);
				return imResult;
			}

		}

		imResult.setResult(IdpConstants.IM_IDP_RESPONSE_SUCCESS_CODE);
		imResult.setResultText(IdpConstants.IM_IDP_RESPONSE_SUCCESS_CODE_TEXT);
		return imResult;
	}

	/*
	 * 
	 * <pre> Retry 완료 알림 배포 - CMD : RXTerminateRetryIDP . </pre>
	 * 
	 * @param map Request로 받은 Parameter Map
	 * 
	 * @return HashMap
	 */
	@Override
	public ImResult executeRXTerminateRetryIDP(HashMap map) {
		String idpResult = IdpConstants.IM_IDP_RESPONSE_SUCCESS_CODE;
		String idpResultText = IdpConstants.IM_IDP_RESPONSE_SUCCESS_CODE_TEXT;

		ImResult imResult = new ImResult();
		imResult.setCmd("RXTerminateRetryIDP");
		imResult.setResult(idpResult);
		imResult.setResultText(idpResultText);

		return imResult;
	}

	/*
	 * 
	 * <pre> 비밀번호 변경 배포 - oneID 회원 - CMD : RXUpdateUserPwdIDP . </pre>
	 * 
	 * @param map Request로 받은 Parameter Map
	 * 
	 * @return HashMap
	 */
	@Override
	public ImResult executeRXUpdateUserPwdIDP(HashMap map) {
		// request의 변경 시간 정보 사용
		String req_date = map.get("modify_req_date").toString();
		String req_time = map.get("modify_req_time").toString();

		UpdatePasswordUserRequest updatePasswordUserRequest = new UpdatePasswordUserRequest();

		String idpResult = IdpConstants.IM_IDP_RESPONSE_FAIL_CODE;
		String idpResultText = IdpConstants.IM_IDP_RESPONSE_FAIL_CODE_TEXT;

		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setSystemID((String) map.get("systemID"));
		commonRequest.setTenantID((String) map.get("tenantID"));
		updatePasswordUserRequest.setCommonRequest(commonRequest);

		SearchUserRequest searchUserRequest = new SearchUserRequest();
		List<KeySearch> keySearchList = new ArrayList<KeySearch>();
		KeySearch keySearch = new KeySearch();
		keySearch.setKeyType("INTG_SVC_NO");
		if (null != map.get("im_int_svc_no")) {
			keySearch.setKeyString((String) map.get("im_int_svc_no"));
		}
		keySearchList.add(keySearch);
		searchUserRequest.setKeySearchList(keySearchList);
		searchUserRequest.setCommonRequest(commonRequest);

		try {
			SearchUserResponse searchUserRespnse = this.userSCI.searchUser(searchUserRequest);
			MbrPwd mbrPwd = new MbrPwd();
			mbrPwd.setPwRegDate(req_date + req_time);
			mbrPwd.setMemberID(searchUserRespnse.getUserMbr().getUserID());

			updatePasswordUserRequest.setMbrPwd(mbrPwd);

			try {
				UpdatePasswordUserResponse updatePasswordUserResponse = this.userSCI.updatePasswordUser(updatePasswordUserRequest);

				if (updatePasswordUserResponse.getCommonResponse().getResultCode().equals(this.SC_RETURN + MemberConstants.RESULT_SUCCES)) {
					idpResult = IdpConstants.IM_IDP_RESPONSE_SUCCESS_CODE;
					idpResultText = IdpConstants.IM_IDP_RESPONSE_SUCCESS_CODE_TEXT;
				}
			} catch (StorePlatformException spe) {
				LOGGER.debug("RXUpdateUserPwdIDP fail to set success as result");
			}

		} catch (StorePlatformException spe) {
			LOGGER.debug("RXUpdateUserPwdIDP error code = " + spe.getErrorInfo().getCode());
		}

		ImResult imResult = new ImResult();
		imResult.setCmd("RXUpdateUserPwdIDP");
		imResult.setResult(idpResult);
		imResult.setResultText(idpResultText);
		imResult.setImIntSvcNo(map.get("im_int_svc_no").toString());

		return imResult;
	}

	/*
	 * 
	 * <pre> 비밀번호 변경 알림 배포 -기존 IDP 회원 - CMD : RXChangePWDIDP . </pre>
	 * 
	 * @param map Request로 받은 Parameter Map
	 * 
	 * @return HashMap
	 */
	@Override
	public ImResult executeRXChangePWDIDP(HashMap map) {

		/*
		 * UpdatePasswordUserRequest updatePasswordUserRequest = new
		 * UpdatePasswordUserRequest(); IdpConstants idpConstant = new
		 * IdpConstants(); MemberConstants memberConstant = new
		 * MemberConstants();
		 * 
		 * String idpResult = idpConstant.IM_IDP_RESPONSE_FAIL_CODE; String
		 * idpResultText = idpConstant.IM_IDP_RESPONSE_FAIL_CODE_TEXT;
		 * 
		 * CommonRequest commonRequest = new CommonRequest();
		 * commonRequest.setSystemID((String) map.get("systemID"));
		 * commonRequest.setTenantID((String) map.get("tenantID"));
		 * updatePasswordUserRequest.setCommonRequest(commonRequest);
		 * 
		 * MbrPwd mbrPwd = new MbrPwd(); // 현재 시간 세팅 Calendar calendar =
		 * Calendar.getInstance(); SimpleDateFormat dateFormat = new
		 * SimpleDateFormat("yyyyMMddHHmmss");
		 * LOGGER.debug("RXChangePWDIDP current time = " +
		 * dateFormat.format(calendar.getTime()));
		 * mbrPwd.setPwRegDate(dateFormat.format(calendar.getTime()));
		 * mbrPwd.setMemberID(map.get("user_id").toString());
		 * 
		 * updatePasswordUserRequest.setMbrPwd(mbrPwd);
		 * 
		 * try { UpdatePasswordUserResponse updatePasswordUserResponse =
		 * this.userSCI .updatePasswordUser(updatePasswordUserRequest);
		 * 
		 * if (updatePasswordUserResponse.getCommonResponse().getResultCode()
		 * .equals(this.SC_RETURN + memberConstant.RESULT_SUCCES)) { idpResult =
		 * idpConstant.IM_IDP_RESPONSE_SUCCESS_CODE; idpResultText =
		 * idpConstant.IM_IDP_RESPONSE_SUCCESS_CODE_TEXT; } } catch
		 * (StorePlatformException spe) { idpResult =
		 * idpConstant.IM_IDP_RESPONSE_FAIL_CODE; idpResultText =
		 * idpConstant.IM_IDP_RESPONSE_FAIL_CODE_TEXT; }
		 * 
		 * ImResult imResult = new ImResult();
		 * imResult.setCmd("RXChangePWDIDP"); imResult.setResult(idpResult);
		 * imResult.setResultText(idpResultText);
		 * 
		 * return imResult;
		 */
		String idpResult = IdpConstants.IM_IDP_RESPONSE_SUCCESS_CODE;
		String idpResultText = IdpConstants.IM_IDP_RESPONSE_SUCCESS_CODE_TEXT;

		ImResult imResult = new ImResult();
		imResult.setCmd("RXChangePWDIDP");
		imResult.setResult(idpResult);
		imResult.setResultText(idpResultText);

		return imResult;
	}

	/*
	 * 
	 * <pre> 변경된 공통프로파일 배포 - CMD : executeRXUpdateUserInfoIDP . </pre>
	 * 
	 * @param map Request 받은 Parameter Map
	 * 
	 * @return HashMap
	 */
	@Override
	public ImResult executeRXUpdateUserInfoIDP(HashMap<String, String> map) {
		ImResult imResult = new ImResult();
		String imIntSvcNo = map.get("im_int_svc_no").toString(); // 통합 서비스 번호
		String userId = map.get("user_id").toString(); // 회원 ID
		String tenantId = "";
		String systemId = "";

		tenantId = map.get("tenantID").toString();
		systemId = map.get("systemID").toString();

		imResult.setCmd("RXUpdateUserInfoIDP");
		imResult.setImIntSvcNo(imIntSvcNo);
		imResult.setUserId(userId);

		// 회원정보 조회후 request 넘어온 정보를 셋팅해서 회원정보 수정
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID(tenantId);
		commonRequest.setSystemID(systemId);

		SearchUserRequest searchUserRequest = new SearchUserRequest();

		KeySearch keySearch = new KeySearch();
		keySearch.setKeyType(MemberConstants.KEY_TYPE_MBR_ID);
		keySearch.setKeyString(userId);

		List<KeySearch> keySearchList = new ArrayList<KeySearch>();
		keySearchList.add(keySearch);
		searchUserRequest.setKeySearchList(keySearchList);
		searchUserRequest.setCommonRequest(commonRequest);

		try {
			SearchUserResponse searchUserResponse = this.userSCI.searchUser(searchUserRequest);

			if (searchUserResponse != null) {
				UpdateUserRequest updateUserRequest = new UpdateUserRequest();
				updateUserRequest.setCommonRequest(commonRequest);
				if (searchUserResponse.getMbrAuth() != null) {// 실명인증 정보가 있어야 셋팅
					if (searchUserResponse.getMbrAuth().getIsRealName().equals(MemberConstants.USE_Y)) {
						updateUserRequest.setMbrAuth(searchUserResponse.getMbrAuth());//
					}
				}
				UserMbr userMbr = searchUserResponse.getUserMbr();
				userMbr.setImSvcNo(imIntSvcNo); // 통합서비스 번호 M
				userMbr.setUserID(userId); // mbrID M
				userMbr.setUserPhone(map.get("user_tn").toString());
				userMbr.setUserEmail(map.get("user_email"));
				userMbr.setUserPhoneCountry(map.get("user_tn_nation_cd"));

				userMbr.setUserKey(searchUserResponse.getUserMbr().getUserKey());
				updateUserRequest.setUserMbr(userMbr);
				if (searchUserResponse.getMbrLglAgent().getIsParent().equals(MemberConstants.USE_Y)) { // 법정대리인 정보가
																										// 있는경우만 셋팅
					updateUserRequest.setMbrLglAgent(searchUserResponse.getMbrLglAgent());
				}

				this.userSCI.updateUser(updateUserRequest);
			}

		} catch (StorePlatformException spe) {
			imResult.setResult(IdpConstants.IM_IDP_RESPONSE_FAIL_CODE);
			imResult.setResultText(IdpConstants.IM_IDP_RESPONSE_FAIL_CODE_TEXT);
			return imResult;
		}

		imResult.setResult(IdpConstants.IM_IDP_RESPONSE_SUCCESS_CODE);
		imResult.setResultText(IdpConstants.IM_IDP_RESPONSE_SUCCESS_CODE_TEXT);
		return imResult;
	}

	/*
	 * 
	 * <pre> 통합 ID 변경 배포 - CMD : rXChangeUserIdIDP . </pre>
	 * 
	 * @param map Request 받은 Parameter Map
	 * 
	 * @return HashMap
	 */
	@Override
	public ImResult executeRXChangeUserIdIDP(HashMap<String, String> map) {
		ImResult imResult = new ImResult();
		imResult.setCmd("RXChangeUserIdIDP");

		String tenantId = "";
		String systemId = "";

		String imIntSvcNo = map.get("im_int_svc_no").toString();
		String newUserId = map.get("new_user_id").toString();
		String userKey = map.get("user_key").toString();
		String oldId = "";
		imResult.setResult(IdpConstants.IM_IDP_RESPONSE_FAIL_CODE);
		imResult.setResultText(IdpConstants.IM_IDP_RESPONSE_FAIL_CODE_TEXT);

		tenantId = map.get("tenantID").toString();
		systemId = map.get("systemID").toString();
		// 1. 통합서비스 번호와 사용자 신규ID 존재 여부 체크
		if (!"".equals(imIntSvcNo) && !"".equals(newUserId)) {
			CommonRequest commonRequest = new CommonRequest();
			commonRequest.setTenantID(tenantId);
			commonRequest.setSystemID(systemId);

			SearchUserRequest searchUserRequest = new SearchUserRequest();

			KeySearch keySearch = new KeySearch();
			keySearch.setKeyType("INTG_SVC_NO");
			keySearch.setKeyString(imIntSvcNo);
			List<KeySearch> keySearchList = new ArrayList<KeySearch>();
			keySearchList.add(keySearch);
			searchUserRequest.setKeySearchList(keySearchList);
			searchUserRequest.setCommonRequest(commonRequest);
			try {
				// 2. 사용자 정보 조회
				SearchUserResponse searchUserResponse = this.userSCI.searchUser(searchUserRequest);

				// 3. 사용자 정보 수정 & ONEID 가입 정보 수정
				if (null != searchUserResponse.getUserMbr()) {
					String mbrNo = searchUserResponse.getUserMbr().getImMbrNo(); // 사용자회원_번호
					if (mbrNo.equals(userKey)) {
						UserMbr userMbr = searchUserResponse.getUserMbr();
						oldId = userMbr.getUserID();
						UpdateUserRequest updateUserRequest = new UpdateUserRequest();
						updateUserRequest.setCommonRequest(commonRequest);

						userMbr.setImMbrNo(userKey); // 사용자회원번호 수정
						userMbr.setUserID(newUserId); // 사용자ID 수정

						updateUserRequest.setUserMbr(userMbr);
						this.userSCI.updateUser(updateUserRequest); // 사용자 정보 수정

						UpdateMbrOneIDRequest updateMbrOneIDRequest = new UpdateMbrOneIDRequest();
						updateMbrOneIDRequest.setCommonRequest(commonRequest);
						MbrOneID mbrOneID = new MbrOneID();
						mbrOneID.setTenantID(tenantId);
						mbrOneID.setIntgSvcNumber(imIntSvcNo);
						mbrOneID.setUserID(newUserId);

						updateMbrOneIDRequest.setMbrOneID(mbrOneID);
						this.userSCI.createAgreeSite(updateMbrOneIDRequest); // ONEID 정보 수정

						imResult.setResult(IdpConstants.IM_IDP_RESPONSE_SUCCESS_CODE);
						imResult.setResultText(IdpConstants.IM_IDP_RESPONSE_SUCCESS_CODE_TEXT);

						if (!newUserId.equals(searchUserResponse.getUserMbr().getUserID())) {
							// 공통_기타 회원ID 변경 시작
							ChangeDisplayUserSacReq changeDisplayUserSacReqByUserID = new ChangeDisplayUserSacReq();
							changeDisplayUserSacReqByUserID.setNewUserId(newUserId);
							changeDisplayUserSacReqByUserID.setOldUserId(searchUserResponse.getUserMbr().getUserID());
							changeDisplayUserSacReqByUserID.setTenantId(tenantId);
							this.changeDisplayUserSCI.changeUserId(changeDisplayUserSacReqByUserID);
							// 공통_기타 회원ID 변경 끝
						}

						/* FDS LOG START */
						final String fdsMbrIdPre = oldId;
						final String fdsMbrId = newUserId;
						final String fdsUsermbrNoPre = mbrNo;
						final String fdsUsermbrNoPost = userKey;
						final String fdsUserId = newUserId;
						final String fdsSystemId = systemId;

						new TLogUtil().log(new ShuttleSetter() {
							@Override
							public void customize(TLogSentinelShuttle shuttle) {
								shuttle.log_id("TL00030").mbr_id_pre(fdsMbrIdPre).mbr_id_post(fdsMbrId).usermbr_no_pre(fdsUsermbrNoPre)
										.usermbr_no_post(fdsUsermbrNoPost).result_code("SUCC").mbr_id(fdsUserId).request_system_id(fdsSystemId);
							}
						});
						/* FDS LOG END */

					}
				}
			} catch (StorePlatformException spe) {
				imResult.setResult(IdpConstants.IM_IDP_RESPONSE_FAIL_CODE);
				imResult.setResultText(IdpConstants.IM_IDP_RESPONSE_FAIL_CODE_TEXT);
				final String fdsUserId = newUserId;
				final String fdsSystemId = systemId;

				new TLogUtil().log(new ShuttleSetter() {
					@Override
					public void customize(TLogSentinelShuttle shuttle) {
						shuttle.log_id("TL00030").result_code(IdpConstants.IM_IDP_RESPONSE_FAIL_CODE)
								.result_message(IdpConstants.IM_IDP_RESPONSE_FAIL_CODE_TEXT).exception_log("").mbr_id(fdsUserId)
								.request_system_id(fdsSystemId);
					}
				});

				return imResult;
			}

		}

		return imResult;
	}

	/*
	 * 
	 * <pre> 이용동의 변경사이트 목록 배포 - CMD : rXUpdateAgreeUserIDP . </pre>
	 * 
	 * @param map Request 받은 Parameter Map
	 * 
	 * @return HashMap
	 */
	@Override
	public ImResult executeRXUpdateAgreeUserIDP(HashMap<String, String> map) {
		LOGGER.debug("executeRXUpdateAgreeUserIDP ------- Start");

		String isParentApprove = ""; // 법정 대리인 동의 여부
		String tenantId = "";
		String systemId = "";
		String userKey = ""; // 내부사용자키
		String imIntSvcNo = ""; // 통합서비스번호
		String userId = ""; // 사용자 ID
		String oldId = "";
		String ocbJoinCodeYn = "N"; // 통합포인트 가입여부: 초기화 값 'N' join_sst_list에 42100 ocb 서비스사이트코드가 있을경우만 Y로 셋팅
		String joinSstCode = ""; // 가입서비스 사이트 코드
		String joinDate = ""; // 가입일자 (ONEID)
		String joinTime = ""; // 가입시간 (ONEID)
		String isRnameAuth = ""; // 실명인증 여부
		String imMemTypeCd = ""; // 통합회원 유형 코드 100: 국내회원 900: 글로벌회원
		String userType = ""; // 가입자 유형코드 1: 개인, 2:법인사업자, 3: 단체사업자, 4:개인사업자, 5사회공헌단체
		String userStatusCode = ""; // 가입자 상태 코드 10:정상 11:가인증
		String telecomCode = ""; // 통신사 코드
		String emailRecvYn = ""; // 이메일 수신 여부
		String prevMbrNoForgameCenter = ""; // 게임센터 연동을 위한 MbrNo
		String currentMbrNoForgameCenter = ""; // 게임센터 연동을 위한 MbrNo

		tenantId = map.get("tenantID").toString();
		systemId = map.get("systemID").toString();
		imIntSvcNo = map.get("im_int_svc_no").toString();
		userId = map.get("user_id").toString();
		joinSstCode = map.get("join_sst_code").toString();
		joinDate = map.get("join_date").toString();
		joinTime = map.get("join_time").toString();

		if (map.get("is_rname_auth") != null)
			isRnameAuth = map.get("is_rname_auth").toString();

		if (map.get("im_mem_type_cd") != null)
			imMemTypeCd = map.get("im_mem_type_cd").toString();

		if (map.get("user_type") != null)
			userType = map.get("user_type").toString();

		if (map.get("user_status_code") != null)
			userStatusCode = map.get("user_status_code").toString(); // 10:정상 11:가인증 ONEID 테이블의 회원가입상태코드

		if (map.get("user_key") != null)
			currentMbrNoForgameCenter = map.get("user_key").toString(); // 게임센터 연동을 위한 변수mbrNo 셋팅

		if (map.get("TELECOM") != null) { // 통신사유형
			String telecomType = map.get("TELECOM").toString();

			if (telecomType.equals(MemberConstants.NM_DEVICE_TELECOM_SKT)) {
				telecomCode = MemberConstants.DEVICE_TELECOM_SKT;
			} else if (telecomType.equals(MemberConstants.NM_DEVICE_TELECOM_KT)) {
				telecomCode = MemberConstants.DEVICE_TELECOM_KT;
			} else if (telecomType.equals(MemberConstants.NM_DEVICE_TELECOM_LGT)) {
				telecomCode = MemberConstants.DEVICE_TELECOM_LGT;
			} else if (telecomType.equals(MemberConstants.NM_DEVICE_TELECOM_OMD)) {
				telecomCode = MemberConstants.DEVICE_TELECOM_OMD;
			} else if (telecomType.equals(MemberConstants.NM_DEVICE_TELECOM_NSH)) {
				telecomCode = MemberConstants.DEVICE_TELECOM_NSH;
			} else if (telecomType.equals(MemberConstants.NM_DEVICE_TELECOM_NON)) {
				telecomCode = MemberConstants.DEVICE_TELECOM_NON;
			} else if (telecomType.equals(MemberConstants.NM_DEVICE_TELECOM_IOS)) {
				telecomCode = MemberConstants.DEVICE_TELECOM_IOS;
			}
			map.put("telecomCode", telecomCode); // 통신사유형 코드 셋팅
		}

		if (map.get("emailYn") != null) { // 이메일수신여부
			emailRecvYn = map.get("emailYn").toString();
		}

		boolean siteCodeCheck = false; // 이용동의 사이트중 tstore가 있는지 없는지 체크하기 위한 boolean 변수

		// MDN합치기 관련 추가변수 20140410 START
		String mdnJsonStringInfo = ""; // mdnJsonStringInfo : mdn합치기 정보
		ObjectMapper mapper = new ObjectMapper();
		Map<String, String> jsonParsingMap = new HashMap<String, String>();
		// MDN합치기 관련 추가변수 20140410 END

		ImResult imResult = new ImResult();
		imResult.setCmd("RXUpdateAgreeUserIDP");
		imResult.setUserId(userId);
		imResult.setImIntSvcNo(imIntSvcNo);

		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID(tenantId);
		commonRequest.setSystemID(systemId);

		// LOGGER.debug("JOIN_SST_LIST START");

		String joinSiteTotalList = map.get("join_sst_list").toString(); // 이용동의사이트정보
		// example info list : 41100,null,20130923,212917,tstore000001741|90300,null,20130917,113426,null
		// LOGGER.debug("replace before:" + joinSiteTotalList);
		joinSiteTotalList = joinSiteTotalList.replaceAll(" ", "");
		// LOGGER.debug("replace after:" + joinSiteTotalList);

		int checkOcbVal = joinSiteTotalList.indexOf(MemberConstants.SSO_SST_CD_OCB_WEB);
		if (checkOcbVal != -1)
			ocbJoinCodeYn = "Y";

		String[] mbrClauseAgreeArray = null;
		String[] tempSplit = joinSiteTotalList.split("\\|");
		for (int i = 0; i < tempSplit.length; i++) {
			String[] tmpSplit = tempSplit[i].split(",");
			if (null != tmpSplit && tmpSplit.length >= 1 && null != tmpSplit[0] && MemberConstants.SSO_SST_CD_TSTORE.equals(tmpSplit[0])) {
				siteCodeCheck = true; // join_sst_list 문자열에 tstore 41100 이용동의가 있는경우
				if (tmpSplit.length >= 5 && null != tmpSplit[4] && !"".equals(tmpSplit[4]) && !"null".equals(tmpSplit[4])) {
					LOGGER.debug("RXUPDATEAGREEUSERIDP old_id : " + tmpSplit[4]);
					map.put("old_id", tmpSplit[4]);
				} else if (tmpSplit.length >= 5 && null != tmpSplit[4] && !"".equals(tmpSplit[4]) && "null".equals(tmpSplit[4])) {
					map.put("old_id", "null");
				}
				if (tmpSplit.length >= 2 && null != tmpSplit[1] && !"".equals(tmpSplit[1]) && !"null".equals(tmpSplit[1])) {
					mbrClauseAgreeArray = tmpSplit[1].split("\\^");
				}
				break;
			}
		}

		if (map.get("mdnJsonInfo") != null) { // mdnJsonInfo가 null이 아닌경우 전달된 mdnJsonInfo data 셋팅
			mdnJsonStringInfo = map.get("mdnJsonInfo").toString();
		}

		if (null != map.get("old_id")) { // null check
			oldId = map.get("old_id").toString();
		}
		isParentApprove = map.get("is_parent_approve").toString();

		if (siteCodeCheck) { // 0. tstore(41100)에서 이용동의가 올경우만 로직 처리함 20140227
			// 1. 통합서비스 번호가 존재시에만 로직 수행 이용동의
			if (!"".equals(imIntSvcNo)) {
				// 이용동의 사이트 목록 배포 (신규) 이용동의 사이트 목록 배포 합치기
				// 1-1. REAL 신규인경우
				// 1-2. ONEID에 사이트에가서 TSTORE아이디로 로그인한후 합치기메뉴를 통해 다른 ID를 합치는경우 oldId param으로 전달되어짐. 이경우 전환처리(수정) 하면 됨
				if ("null".equals(oldId) || "".equals(oldId)) {
					// 20140221 방어로직 추가 기존에 타사이트에서 ONEID가입시 TSTORE미동의 회원으로 가입을 했으나
					// TSOTE에 아이디가 생긴경우가 발생 이경우 통합서비스번호로 조회하여 TSTORE정보가 존재시 수정함.
					SearchUserRequest searchUserRequest = new SearchUserRequest();
					searchUserRequest.setCommonRequest(commonRequest);

					List<KeySearch> keySearchList = new ArrayList<KeySearch>();
					KeySearch keySearch = new KeySearch();
					keySearch.setKeyType("INTG_SVC_NO");
					keySearch.setKeyString(imIntSvcNo); // 통합 서비스 번호
					keySearchList.add(keySearch);
					searchUserRequest.setKeySearchList(keySearchList);
					SearchUserResponse searchUserResponse = null;
					try {
						searchUserResponse = this.userSCI.searchUser(searchUserRequest);
						userKey = searchUserResponse.getUserKey();
					} catch (StorePlatformException spe) {
						searchUserResponse = null; // 정보가 없을경우 null로 셋팅
					}

					if (searchUserResponse != null) { // 통합서비스 번호로 조회된 회원정보가 있는경우

						map.put("im_reg_date", "");
						UpdateUserRequest updateUserRequest = new UpdateUserRequest();
						updateUserRequest.setCommonRequest(commonRequest);

						// 통합서비스 관리번호 동일 & mbr_no가 다를 경우만 DB에 있는 데이터를 Request있는 데이터로 db내용을 update시킴
						if (!searchUserResponse.getUserMbr().getImMbrNo().equals(map.get("user_key").toString())) {
							UpdateUserResponse updateUserResponse = this.userSCI.updateUser(this.getUpdateUserRequest(map, searchUserResponse));

							userKey = updateUserResponse.getUserKey();

							try {
								// ONEID에 데이터 입력
								UpdateMbrOneIDRequest updateMbrOneIDRequest = new UpdateMbrOneIDRequest();
								updateMbrOneIDRequest.setCommonRequest(commonRequest);
								MbrOneID mbrOneID = new MbrOneID();
								mbrOneID.setStopStatusCode(IdpConstants.SUS_STATUS_RELEASE); // 직권중지해제 기본셋팅
								mbrOneID.setIntgSvcNumber(imIntSvcNo);
								mbrOneID.setUserKey(userKey); // 내부사용자키를 셋팅
								mbrOneID.setUserID(userId); // 사용자 ID 셋팅
								mbrOneID.setIsMemberPoint(ocbJoinCodeYn); // 통합포인트 여부
								mbrOneID.setIsRealName(isRnameAuth); // 실명인증 여부

								if (map.get("user_ci").toString().length() > 0) { // 사용자 CI
									mbrOneID.setIsCi("Y");
								} else {
									mbrOneID.setIsCi("N");
								}

								updateMbrOneIDRequest.setMbrOneID(mbrOneID);
								this.userSCI.createAgreeSite(updateMbrOneIDRequest);

							} catch (StorePlatformException spe) {
								imResult.setResult(IdpConstants.IM_IDP_RESPONSE_FAIL_CODE);
								imResult.setResultText(IdpConstants.IM_IDP_RESPONSE_FAIL_CODE_TEXT);
								return imResult;
							}
						}

					} else { // 통합서비스 번호로 조회된 회원정보가 없는경우
						LOGGER.debug("JOIN NEW DATA INSERT START");
						CreateUserRequest createUserRequest = new CreateUserRequest();
						CreateUserResponse create = null;
						// 공통으로 사용되는 요청정보

						// 사용자 기본정보
						UserMbr userMbr = new UserMbr();

						userMbr.setTenantID(tenantId); // 테넌트 ID
						userMbr.setSystemID(systemId); // 테넌트의 시스템 ID

						userMbr.setUserKey(""); // 사용자 Key
						if (map.get("user_key") != null)
							userMbr.setImMbrNo(map.get("user_key").toString()); // 외부(OneID/IDP)에서 할당된 사용자 Key IDP 통합서비스
																				// 키
																				// USERMBR_NO
						userMbr.setUserType(MemberConstants.USER_TYPE_ONEID); // * 사용자 구분 코드 ONEID회원으로 셋팅

						if (userStatusCode.equals(MemberConstants.JOIN_STATUS_CODE_NORMAL)) { // 10: 정상
							userMbr.setUserMainStatus(MemberConstants.MAIN_STATUS_NORMAL); // 사용자 메인 상태 코드 가입시 바로 가입됨 정상
							userMbr.setUserSubStatus(MemberConstants.SUB_STATUS_NORMAL); // 사용자 서브 상태 코드 정상
						} else if (userStatusCode.equals(MemberConstants.JOIN_STATUS_CODE_HALF_AUTH)) { // 11: 가인증
							userMbr.setUserMainStatus(MemberConstants.MAIN_STATUS_WATING); // 가가입
							userMbr.setUserSubStatus(MemberConstants.SUB_STATUS_JOIN_APPLY_WATING); // 가입승인대기
						}

						userMbr.setUserMainStatus(MemberConstants.MAIN_STATUS_NORMAL); // 사용자 메인 상태 코드 가입시 바로 가입됨 정상
						userMbr.setUserSubStatus(MemberConstants.SUB_STATUS_NORMAL); // 사용자 서브 상태 코드 정상
						userMbr.setImSvcNo(imIntSvcNo); // 통합 서비스 관리번호 INTG_SVC_NO : 통합서비스 관리번호
						userMbr.setIsImChanged(map.get("is_im_changed").toString()); // 전환가입코드 * * - 전환가입 : Y, 신규가입 : N,
																						// 변경가입 :
																						// C,
																						// 변경전환 : H
						userMbr.setUserID(userId); // 사용자 ID

						if (map.get("user_tn_nation_cd") != null)
							userMbr.setUserPhoneCountry(map.get("user_tn_nation_cd").toString()); // 연락처 국가 코드

						if (map.get("user_tn") != null)
							userMbr.setUserPhone(map.get("user_tn").toString()); // 사용자 연락처

						if (map.get("user_email") != null)
							userMbr.setUserEmail(map.get("user_email").toString()); // 사용자 이메일 주소

						if (map.get("user_name") != null)
							userMbr.setUserName(map.get("user_name").toString()); // 사용자 이름

						if (map.get("user_sex") != null)
							userMbr.setUserSex(map.get("user_sex").toString()); // 사용자 성별

						if (map.get("user_birthday") != null)
							userMbr.setUserBirthDay(map.get("user_birthday").toString()); // 사용자 생년월일

						if (map.get("user_nation_code") != null)
							userMbr.setUserCountry(map.get("user_nation_code").toString()); // 사용자 국가 코드

						userMbr.setImSiteCode(map.get("join_sst_list").toString()); // OneID 이용동의 사이트 정보
						// 실명인증 여부 신규인경우 Y로 전달받아도 실명인증정보에서 신규인경우 N으로 수정이됨
						userMbr.setIsRealName(isRnameAuth);
						userMbr.setIsMemberPoint(ocbJoinCodeYn); // 통합 포인트 여부 (Y/N)

						if (map.get("telecomCode") != null)
							userMbr.setUserTelecom(map.get("telecomCode").toString());

						userMbr.setIsRecvEmail(emailRecvYn); // 이메일 수신여부 (Y/N)

						createUserRequest.setCommonRequest(commonRequest); // 공통요청

						if (isRnameAuth.equals(MemberConstants.USE_Y)) {
							MbrAuth mbrAuth = this.getMbrAuthByNew(map, "N");
							createUserRequest.setMbrAuth(mbrAuth); // 실명인증
						}

						createUserRequest.setUserMbr(userMbr); // 사용자정보

						// 법정대리인 정보 14세미만인 경우 대리인 정보 셋팅
						if (StringUtils.equals(isParentApprove, MemberConstants.USE_Y)) {
							createUserRequest.setMbrLglAgent(this.getMbrLglAgent(map)); // 법정대리인
						}

						List<MbrClauseAgree> mbrClauseAgreeList = this.getMbrClauseAgreeList(tenantId, mbrClauseAgreeArray);

						createUserRequest.setMbrClauseAgreeList(mbrClauseAgreeList);
						try {
							create = this.userSCI.create(createUserRequest); // 가입정보 등록
							userKey = create.getUserKey();
						} catch (StorePlatformException spe) {
							imResult.setResult(IdpConstants.IM_IDP_RESPONSE_FAIL_CODE);
							imResult.setResultText(IdpConstants.IM_IDP_RESPONSE_FAIL_CODE_TEXT);
							return imResult;
						}

						LOGGER.debug("JOIN NEW DATA INSERT COMPLETE");

						LOGGER.debug("JOIN ONEID DATA INSERT START");

						// ONEID에 데이터 입력
						UpdateMbrOneIDRequest updateMbrOneIDRequest = new UpdateMbrOneIDRequest();
						updateMbrOneIDRequest.setCommonRequest(commonRequest);
						MbrOneID mbrOneID = new MbrOneID();
						mbrOneID.setStopStatusCode(IdpConstants.SUS_STATUS_RELEASE); // 직권중지해제 기본셋팅
						mbrOneID.setIntgSvcNumber(imIntSvcNo);
						mbrOneID.setUserKey(userKey); // 신규가입때 생성된 내부사용자키를 셋팅
						mbrOneID.setUserID(userId); // userId
						mbrOneID.setIsMemberPoint(ocbJoinCodeYn); // 통합포인트 여부
						if (null != map.get("is_rname_auth"))
							mbrOneID.setIsRealName(map.get("is_rname_auth").toString()); // 실명인증 여부
						mbrOneID.setIntgSiteCode(joinSstCode); // 가입 서비스 사이트 코드
						mbrOneID.setEntryDate(joinDate + joinTime); // 가입일시
						mbrOneID.setIntgMbrCaseCode(imMemTypeCd); // 통합회원유형코드
						mbrOneID.setEntryStatusCode(userStatusCode); // 가입자 상태코드
						mbrOneID.setMemberCaseCode(userType); // 가입자 유형코드

						if (map.get("user_ci").toString().length() > 0) { // 사용자 CI
							mbrOneID.setIsCi("Y");
						} else {
							mbrOneID.setIsCi("N");
						}

						updateMbrOneIDRequest.setMbrOneID(mbrOneID);

						try {
							this.userSCI.createAgreeSite(updateMbrOneIDRequest);
							imResult.setResult(IdpConstants.IM_IDP_RESPONSE_SUCCESS_CODE);
							imResult.setResultText(IdpConstants.IM_IDP_RESPONSE_SUCCESS_CODE_TEXT);

						} catch (StorePlatformException spe) {
							imResult.setResult(IdpConstants.IM_IDP_RESPONSE_FAIL_CODE);
							imResult.setResultText(IdpConstants.IM_IDP_RESPONSE_FAIL_CODE_TEXT);
							return imResult;
						}
						LOGGER.debug("JOIN ONEID DATA INSERT COMPLETE");

						try { /*
							 * 게임센터 연동 : 신규가입인경우 as-is src에서 신규가입 데이터를 입력하고
							 * user_id로 조회하여 회원정보 있으면 게임센터 연동함
							 */

							GameCenterSacReq gameCenterSacReq = new GameCenterSacReq();
							gameCenterSacReq.setUserKey(userKey);
							gameCenterSacReq.setPreUserKey(userKey);
							gameCenterSacReq.setMbrNo(currentMbrNoForgameCenter);
							gameCenterSacReq.setPreMbrNo(prevMbrNoForgameCenter);
							gameCenterSacReq.setSystemId(systemId);
							gameCenterSacReq.setTenantId(tenantId);
							gameCenterSacReq.setWorkCd(MemberConstants.GAMECENTER_WORK_CD_IMUSER_CHANGE);
							this.deviceService.insertGameCenterIF(gameCenterSacReq);

						} catch (StorePlatformException spe) {
							imResult.setResult(IdpConstants.IM_IDP_RESPONSE_FAIL_CODE);
							imResult.setResultText(IdpConstants.IM_IDP_RESPONSE_FAIL_CODE_TEXT);
							return imResult;
						}
					}

					// MDN합치기 기능추가 20140410 START
					if (!mdnJsonStringInfo.equals("")) { // MDN 정보가 있는경우 MDN합치기 수행
						try {
							jsonParsingMap = mapper.readValue(mdnJsonStringInfo, new TypeReference<HashMap<String, String>>() {
							});
						} catch (Exception e) {
							imResult.setResult(IdpConstants.IM_IDP_RESPONSE_FAIL_CODE);
							imResult.setResultText(IdpConstants.IM_IDP_RESPONSE_FAIL_CODE_TEXT);
							return imResult;
						}

						String deviceId = jsonParsingMap.get("mdn").toString(); // mdn
						String telecomTypeByMdnInfo = jsonParsingMap.get("carrier").toString(); // carrier
						String userKeyByMdnInfo = jsonParsingMap.get("user_key").toString(); // user_key
						String svcMngNumByMdnInfo = jsonParsingMap.get("svc_mng_num").toString();// svc_mng_num
						String modelIdByMdnInfo = jsonParsingMap.get("model_id").toString(); // model_id
						String insdUserKeyByMdnInfo = ""; // 내부사용자키
						String telecomValueByMdnInfo = ""; // 통신사코드정보
						String isPrimary = "N"; // 대표단말여부

						// MDN_CARRIER_DEVICE_TELECOM_SKT
						if (telecomTypeByMdnInfo.equals(MemberConstants.MDN_CARRIER_DEVICE_TELECOM_SKT)) {
							telecomValueByMdnInfo = MemberConstants.DEVICE_TELECOM_SKT;
						} else if (telecomTypeByMdnInfo.equals(MemberConstants.MDN_CARRIER_DEVICE_TELECOM_KTF)) {
							telecomValueByMdnInfo = MemberConstants.DEVICE_TELECOM_KT;
						} else if (telecomTypeByMdnInfo.equals(MemberConstants.MDN_CARRIER_DEVICE_TELECOM_LGT)) {
							telecomValueByMdnInfo = MemberConstants.DEVICE_TELECOM_LGT;
						} else if (telecomTypeByMdnInfo.equals(MemberConstants.MDN_CARRIER_DEVICE_TELECOM_NON_SKT)) {
							telecomValueByMdnInfo = MemberConstants.DEVICE_TELECOM_NON;
						}

						// MDN정보 USERMBR_NO로 INSD_USERMBR_NO를 조회하기위해 사용자 목록을 조회함.
						List<KeySearch> keySearchListByMdnInfo = new ArrayList<KeySearch>();
						KeySearch keySearchByMdnInfo = new KeySearch();
						keySearchByMdnInfo.setKeyType(MemberConstants.KEY_TYPE_USERMBR_NO);
						keySearchByMdnInfo.setKeyString(userKeyByMdnInfo); // MBR_NO - USERMBR_NO
						keySearchListByMdnInfo.add(keySearchByMdnInfo);
						SearchUserRequest searchUserRequestByMdnInfo = new SearchUserRequest();
						searchUserRequestByMdnInfo.setCommonRequest(commonRequest);
						searchUserRequestByMdnInfo.setKeySearchList(keySearchListByMdnInfo);
						SearchUserResponse searchUserResponseByMdnInfo = null;

						searchUserResponseByMdnInfo = this.userSCI.searchUser(searchUserRequestByMdnInfo);

						if (searchUserResponseByMdnInfo != null) {
							insdUserKeyByMdnInfo = searchUserResponseByMdnInfo.getUserKey();

							SacRequestHeader requestHeader = new SacRequestHeader();
							TenantHeader tenant = new TenantHeader();
							tenant.setSystemId(systemId);
							tenant.setTenantId(tenantId);
							requestHeader.setTenantHeader(tenant);

							DeviceInfo getDeviceInfo = this.deviceService.searchDevice(requestHeader, MemberConstants.KEY_TYPE_DEVICE_ID, deviceId,
									insdUserKeyByMdnInfo);

							ListDeviceReq req = new ListDeviceReq();

							req.setUserKey(insdUserKeyByMdnInfo);
							req.setIsMainDevice("N");

							if (searchUserResponse != null) {
								ListDeviceRes listDeviceRes = this.deviceService.listDevice(requestHeader, req);

								for (DeviceInfo deviceInfo : listDeviceRes.getDeviceInfoList()) {
									if (StringUtils.equals(deviceInfo.getIsPrimary(), "Y")) {
										isPrimary = "Y";
									} else {
										isPrimary = "N";
									}

									// 게임센터 연동
									GameCenterSacReq gameCenterSacReq = new GameCenterSacReq();
									//deviceService.insertGameCenterIF(gameCenterSacReq)
								}

							} else {
								isPrimary = "Y"; // 이용동의로 신규가입할 경우 대표기기 여부는 Y로 셋팅
							}

							// 휴대기기 등록시 MDN_JSON_DATA 정보중 MDN,CARRIER,SVC_MNG_NUM, MODEL_ID정보는 REQUEST PARAM
							if (getDeviceInfo != null) {

								// 휴대기기등록
								getDeviceInfo.setDeviceId(deviceId);
								getDeviceInfo.setDeviceTelecom(telecomValueByMdnInfo);
								getDeviceInfo.setSvcMangNum(svcMngNumByMdnInfo);
								getDeviceInfo.setIsPrimary(isPrimary);

								if (getDeviceInfo.getDeviceExtraInfoList() != null) { // 부가속성

									for (int i = 0; i < getDeviceInfo.getDeviceExtraInfoList().size(); i++) {
										DeviceExtraInfo checkInfo = getDeviceInfo.getDeviceExtraInfoList().get(i);
										if (checkInfo.getExtraProfile().equals(MemberConstants.DEVICE_EXTRA_UACD)) {
											getDeviceInfo.getDeviceExtraInfoList().get(i).setExtraProfileValue(modelIdByMdnInfo);
										}
									}
								}

								/* 단말코드 조회 */
								Device device = this.mcc.getPhoneInfoByUacd(modelIdByMdnInfo);
								if (device == null) {
									getDeviceInfo.setDeviceModelNo(MemberConstants.NOT_SUPPORT_HP_MODEL_CD);
									getDeviceInfo.setModelNm(MemberConstants.NOT_SUPPORT_HP_MODEL_NM);
								} else {
									getDeviceInfo.setDeviceModelNo(device.getDeviceModelCd());
								}

								String afterDeviceKey = this.deviceService.insertDeviceInfo(systemId, tenantId, userKey, getDeviceInfo);

								// insertDeviceInfo 호출시 deviceKey가 새로 생성되는데 새로 생성된 값을 updateDeviceInfo
								// api호출해서 부가속성을 모두 바꿔줘야함.
								getDeviceInfo.setDeviceKey(afterDeviceKey);
								if (getDeviceInfo.getDeviceExtraInfoList() != null) { // 부가속성
									for (int i = 0; i < getDeviceInfo.getDeviceExtraInfoList().size(); i++) {
										getDeviceInfo.getDeviceExtraInfoList().get(i).setDeviceKey(afterDeviceKey);
									}
								}

								this.deviceService.updateDeviceInfo(requestHeader, getDeviceInfo);

							}
						}
					}
					// MDN합치기 기능추가 20140410 END

				} else { // 신규가입이 아닌경우 전환가입/변경전환/변경 가입 oldId != "null" 이 아닌경우 분기
					map.put("im_reg_date", DateUtil.getToday()); // 전환가입일을 셋팅

					if (userId.equals(oldId)) { // 전환가입 userId - oldId 비교시 같은경우
						LOGGER.debug("전환가입 정보 입력 시작");
						SearchUserRequest searchUserRequest = new SearchUserRequest();

						KeySearch keySearch = new KeySearch();
						keySearch.setKeyType(MemberConstants.KEY_TYPE_MBR_ID);
						keySearch.setKeyString(map.get("old_id").toString());
						List<KeySearch> keySearchList = new ArrayList<KeySearch>();
						keySearchList.add(keySearch);
						searchUserRequest.setKeySearchList(keySearchList);
						searchUserRequest.setCommonRequest(commonRequest);
						UpdateUserResponse updateUserResponse = null;

						try {
							SearchUserResponse searchUserResponse = this.userSCI.searchUser(searchUserRequest);

							if (searchUserResponse == null) {
								keySearch.setKeyString(map.get("old_id").toString() + "@nate.com");
								keySearchList = null;
								keySearchList = new ArrayList<KeySearch>();
								keySearchList.add(keySearch);
								searchUserRequest.setKeySearchList(keySearchList);

								searchUserResponse = this.userSCI.searchUser(searchUserRequest);
							}

							if (searchUserResponse != null) {
								prevMbrNoForgameCenter = searchUserResponse.getUserMbr().getImMbrNo(); // 게임센터연동을위한기존mbrNo셋팅
								UpdateUserRequest updateUserRequest = this.getUpdateUserRequest(map, searchUserResponse);
								// JOIN_SST_LIST에 TAC001~TAC006이 있는경우 이용약관이 들어옴.
								List<MbrClauseAgree> mbrClauseAgreeList = this.getMbrClauseAgreeList(tenantId, mbrClauseAgreeArray);

								updateUserRequest.setMbrClauseAgree(mbrClauseAgreeList);
								updateUserResponse = this.userSCI.updateUser(updateUserRequest);

								LOGGER.debug("전환가입 정보 입력 완료");
								userKey = updateUserResponse.getUserKey();

								// 전환가입시 게임센터 연동을 함
								if (this.isCall) { // OGG 시에만 사용하고 그 이후에는 불필요 로직 property로 사용함
									GameCenterSacReq gameCenterSacReq = new GameCenterSacReq();
									// Post UserKey를 가져오려했으나 sc쪽에 userKey는 수정후에도 넘겨받는값 userKey updateUserRequest의
									// userkKey를 그대로 리턴해주고 있어서현재 param으로 넘어온 mbrNo 그대로 셋팅해주고 있음 임시로직이기 때문에 문제 없음.
									gameCenterSacReq.setUserKey(currentMbrNoForgameCenter);
									gameCenterSacReq.setPreUserKey(userKey);
									gameCenterSacReq.setMbrNo(currentMbrNoForgameCenter);
									gameCenterSacReq.setPreMbrNo(prevMbrNoForgameCenter);
									gameCenterSacReq.setSystemId(systemId);
									gameCenterSacReq.setTenantId(tenantId);
									gameCenterSacReq.setWorkCd(MemberConstants.GAMECENTER_WORK_CD_IMUSER_CHANGE);
									this.deviceService.insertGameCenterIF(gameCenterSacReq);
								}
							}

						} catch (StorePlatformException spe) {
							imResult.setResult(IdpConstants.IM_IDP_RESPONSE_FAIL_CODE);
							imResult.setResultText(IdpConstants.IM_IDP_RESPONSE_FAIL_CODE_TEXT);
							return imResult;
						}

					} else if (!userId.equals(oldId)) { // 변경가입, 변경전환
						LOGGER.debug("변경가입,변경전환 정보 입력 시작");
						SearchUserRequest searchUserRequest = new SearchUserRequest();

						KeySearch keySearch = new KeySearch();
						keySearch.setKeyType(MemberConstants.KEY_TYPE_MBR_ID);
						keySearch.setKeyString(map.get("old_id").toString());
						List<KeySearch> keySearchList = new ArrayList<KeySearch>();
						keySearchList.add(keySearch);
						searchUserRequest.setKeySearchList(keySearchList);
						searchUserRequest.setCommonRequest(commonRequest);
						UpdateUserResponse updateUserResponse = null;

						try {
							SearchUserResponse searchUserResponse = this.userSCI.searchUser(searchUserRequest);

							if (searchUserResponse == null) {
								keySearch.setKeyString(map.get("old_id").toString() + "@nate.com");
								keySearchList = null;
								keySearchList = new ArrayList<KeySearch>();
								keySearchList.add(keySearch);
								searchUserRequest.setKeySearchList(keySearchList);

								searchUserResponse = this.userSCI.searchUser(searchUserRequest);
							}

							if (searchUserResponse != null) {
								prevMbrNoForgameCenter = searchUserResponse.getUserMbr().getImMbrNo(); // 게임센터연동을위한기존mbrNo셋팅

								UpdateUserRequest updateUserRequest = this.getUpdateUserRequest(map, searchUserResponse);
								// JOIN_SST_LIST에 TAC001~TAC006이 있는경우 이용약관이 들어옴.
								List<MbrClauseAgree> mbrClauseAgreeList = this.getMbrClauseAgreeList(tenantId, mbrClauseAgreeArray);

								updateUserRequest.setMbrClauseAgree(mbrClauseAgreeList);
								updateUserResponse = this.userSCI.updateUser(updateUserRequest);

								userKey = updateUserResponse.getUserKey();
								LOGGER.debug("변경가입,변경전환 정보 입력 완료");

								// 공통_기타 회원ID 변경 시작
								ChangeDisplayUserSacReq changeDisplayUserSacReqByUserID = new ChangeDisplayUserSacReq();
								changeDisplayUserSacReqByUserID.setNewUserId(userId);
								changeDisplayUserSacReqByUserID.setOldUserId(oldId);
								changeDisplayUserSacReqByUserID.setTenantId(tenantId);
								this.changeDisplayUserSCI.changeUserId(changeDisplayUserSacReqByUserID);
								// 공통_기타 회원ID 변경 끝
							}
						} catch (StorePlatformException spe) {
							imResult.setResult(IdpConstants.IM_IDP_RESPONSE_FAIL_CODE);
							imResult.setResultText(IdpConstants.IM_IDP_RESPONSE_FAIL_CODE_TEXT);
							return imResult;
						}

					}

					LOGGER.debug("ONEID DATA UPDATE START");

					try {
						// ONEID에 데이터 입력
						UpdateMbrOneIDRequest updateMbrOneIDRequest = new UpdateMbrOneIDRequest();
						updateMbrOneIDRequest.setCommonRequest(commonRequest);
						MbrOneID mbrOneID = new MbrOneID();
						mbrOneID.setStopStatusCode(IdpConstants.SUS_STATUS_RELEASE); // 직권중지해제 기본셋팅
						mbrOneID.setIntgSvcNumber(imIntSvcNo);
						mbrOneID.setUserKey(userKey); // 내부사용자키를 셋팅
						mbrOneID.setUserID(userId); // 사용자 ID 셋팅
						mbrOneID.setIsMemberPoint(ocbJoinCodeYn); // 통합포인트 여부
						mbrOneID.setIsRealName(isRnameAuth); // 실명인증 여부
						mbrOneID.setIntgSiteCode(joinSstCode); // 가입 서비스 사이트 코드
						mbrOneID.setEntryDate(joinDate + joinTime); // 가입일시
						mbrOneID.setIntgMbrCaseCode(imMemTypeCd); // 통합회원유형코드
						mbrOneID.setEntryStatusCode(userStatusCode); // 가입자 상태코드
						mbrOneID.setMemberCaseCode(userType); // 가입자 유형코드

						if (map.get("user_ci").toString().length() > 0) { // 사용자 CI
							mbrOneID.setIsCi("Y");
						} else {
							mbrOneID.setIsCi("N");
						}

						updateMbrOneIDRequest.setMbrOneID(mbrOneID);
						this.userSCI.createAgreeSite(updateMbrOneIDRequest);

					} catch (StorePlatformException spe) {
						imResult.setResult(IdpConstants.IM_IDP_RESPONSE_FAIL_CODE);
						imResult.setResultText(IdpConstants.IM_IDP_RESPONSE_FAIL_CODE_TEXT);
						return imResult;
					}

					LOGGER.debug("ONEID DATA UPDATE COMPLETE");

					/**
					 * 구매/기타 UserKey 변경.(OGG 시에만 사용하고 그 이후에는 불필요 로직임.)
					 * //20140415 현재 구매쪽에서 null처리 안되있으므로 처리 되면 반영할것
					 */
					if (this.isCall) {
						String newMbrNo = map.get("user_key").toString();
						String prevMbrNo = prevMbrNoForgameCenter;
						this.mcc.excuteInternalMethod(this.isCall, systemId, tenantId, newMbrNo, prevMbrNo, null, null);
					}
				}

			}
		}
		imResult.setResult(IdpConstants.IM_IDP_RESPONSE_SUCCESS_CODE);
		imResult.setResultText(IdpConstants.IM_IDP_RESPONSE_SUCCESS_CODE_TEXT);
		LOGGER.debug("rXUpdateAgreeUserIDP ------- End");
		return imResult;
	}

	/**
	 * 
	 * <pre>
	 * 공통 약관정보의 필수약관여부, 약관버전를 정보를 조회함.
	 * </pre>
	 * 
	 * @param tenantID
	 * @param strList
	 * @return
	 */
	private List<MbrClauseAgree> getMbrClauseAgreeList(String tenantID, String[] mbrClauseAgreeArray) {

		boolean isSelectedClauseAgree = false; // 선택사항 TAC006 US010608값의 선택판단 여부 없을경우 false

		List<MbrClauseAgree> setMbrClauseAgreeList = new ArrayList<MbrClauseAgree>(); // return 시켜줄 약관정보 목록
		MbrClauseAgree mca = null; // arrayList에 담을 약관정보 초기화

		// 신규가입인경우만 이용약관이 들어옴.
		List<AgreementInfo> agreementList = new ArrayList<AgreementInfo>(); // 공통약관정보의 필수약관여부, 약관정보를 조회하기 위해 셋팅하는 list

		AgreementInfo setAgreementInfo = null; // 약관동의정보 초기화

		if (mbrClauseAgreeArray != null) {

			// TAC001 Tstore 이용약관동의
			setAgreementInfo = new AgreementInfo();
			setAgreementInfo.setExtraAgreementId(MemberConstants.POLICY_AGREEMENT_CLAUSE_TSTORE);
			setAgreementInfo.setIsExtraAgreement("Y");
			agreementList.add(setAgreementInfo);

			// TAC002 통신과금서비스 이용약관
			setAgreementInfo = new AgreementInfo();
			setAgreementInfo.setExtraAgreementId(MemberConstants.POLICY_AGREEMENT_CLAUSE_COMMUNICATION_CHARGE);
			setAgreementInfo.setIsExtraAgreement("Y");
			agreementList.add(setAgreementInfo);

			// TAC003 TSTORE캐쉬이용약관
			setAgreementInfo = new AgreementInfo();
			setAgreementInfo.setExtraAgreementId(MemberConstants.POLICY_AGREEMENT_CLAUSE_CASH);
			setAgreementInfo.setIsExtraAgreement("Y");
			agreementList.add(setAgreementInfo);

			// TAC004 TSTORE개인정보취급방침
			setAgreementInfo = new AgreementInfo();
			setAgreementInfo.setExtraAgreementId(MemberConstants.POLICY_AGREEMENT_CLAUSE_INDIVIDUAL_INFO_HANDLE_TSTORE);
			setAgreementInfo.setIsExtraAgreement("Y");
			agreementList.add(setAgreementInfo);

			// TAC005 3자정보제공동의 11번
			setAgreementInfo = new AgreementInfo();
			setAgreementInfo.setExtraAgreementId(MemberConstants.POLICY_AGREEMENT_CLAUSE_INDIVIDUAL_INFO_HANDLE_OTHERS);
			setAgreementInfo.setIsExtraAgreement("Y");
			agreementList.add(setAgreementInfo);
			agreementList.add(setAgreementInfo);

			for (String mbrClauseAgree : mbrClauseAgreeArray) {
				if (mbrClauseAgree.equals("TAC006")) {// 선택약관
					isSelectedClauseAgree = true;
					break;
				} else {
					isSelectedClauseAgree = false;
				}
			}

			if (!isSelectedClauseAgree) { // mbrClauseAgreeArray 배열에 선택사항 항목인 TAC006이 약관항목에 없는경우 N으로 셋팅
				// TAC006 TSTORE정보광고활용
				setAgreementInfo = new AgreementInfo();
				setAgreementInfo.setExtraAgreementId(MemberConstants.POLICY_AGREEMENT_CLAUSE_MARKETING);
				setAgreementInfo.setIsExtraAgreement("N");
				agreementList.add(setAgreementInfo);
			} else {
				setAgreementInfo = new AgreementInfo();
				setAgreementInfo.setExtraAgreementId(MemberConstants.POLICY_AGREEMENT_CLAUSE_MARKETING);
				setAgreementInfo.setIsExtraAgreement("Y");
				agreementList.add(setAgreementInfo);
			}
		}

		setAgreementInfo = new AgreementInfo();
		setAgreementInfo.setExtraAgreementId(MemberConstants.POLICY_AGREEMENT_CLAUSE_ONEID);
		setAgreementInfo.setIsExtraAgreement("Y");
		agreementList.add(setAgreementInfo);

		setAgreementInfo = new AgreementInfo();
		setAgreementInfo.setExtraAgreementId(MemberConstants.POLICY_AGREEMENT_CLAUSE_INDIVIDUAL_INFO_HANDLE_ONEID);
		setAgreementInfo.setIsExtraAgreement("Y");
		agreementList.add(setAgreementInfo);

		// 약관 맵핑정보 세팅. 약관매핑정보를 조회하여 약관정보를 셋팅함
		List<AgreementInfo> getAgreementInfoList = this.mcc.getClauseMappingInfo(tenantID, agreementList);

		for (AgreementInfo agreementInfo : getAgreementInfoList) {
			mca = new MbrClauseAgree();
			mca.setExtraAgreementID(agreementInfo.getExtraAgreementId());
			mca.setIsExtraAgreement(agreementInfo.getIsExtraAgreement());
			mca.setIsMandatory(agreementInfo.getMandAgreeYn());
			mca.setExtraAgreementVersion(agreementInfo.getExtraAgreementVersion());
			setMbrClauseAgreeList.add(mca);
		}

		return setMbrClauseAgreeList;
	}
}
