package com.skplanet.storeplatform.sac.member.idp.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.external.client.shopping.util.StringUtil;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;
import com.skplanet.storeplatform.member.client.common.vo.KeySearch;
import com.skplanet.storeplatform.member.client.common.vo.LimitTarget;
import com.skplanet.storeplatform.member.client.common.vo.MbrAuth;
import com.skplanet.storeplatform.member.client.common.vo.MbrClauseAgree;
import com.skplanet.storeplatform.member.client.common.vo.MbrLglAgent;
import com.skplanet.storeplatform.member.client.common.vo.MbrOneID;
import com.skplanet.storeplatform.member.client.common.vo.MbrPwd;
import com.skplanet.storeplatform.member.client.common.vo.SearchPolicyRequest;
import com.skplanet.storeplatform.member.client.common.vo.SearchPolicyResponse;
import com.skplanet.storeplatform.member.client.common.vo.UpdateMbrOneIDRequest;
import com.skplanet.storeplatform.member.client.common.vo.UpdateMbrOneIDResponse;
import com.skplanet.storeplatform.member.client.user.sci.DeviceSCI;
import com.skplanet.storeplatform.member.client.user.sci.UserSCI;
import com.skplanet.storeplatform.member.client.user.sci.vo.CreateDeviceRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.CreateUserRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.CreateUserResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.RemoveDeviceRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.RemoveMbrOneIDRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.RemoveUserRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchDeviceListRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchDeviceListResponse;
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
import com.skplanet.storeplatform.member.client.user.sci.vo.UserMbrDevice;
import com.skplanet.storeplatform.member.client.user.sci.vo.UserMbrDeviceDetail;
import com.skplanet.storeplatform.sac.api.util.DateUtil;
import com.skplanet.storeplatform.sac.client.member.vo.user.GameCenterSacReq;
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

	public String SUCCESS_STR = "100"; // SP 프로비져닝 성공
	public String FAIL_STR = "109"; // SP 프로비져닝 실패(FAIL응답 받음)
	public String FAIL_NODATA_STR = "600"; // SP 프로비져닝 실패(데이터 없음)
	public String SC_RETURN = "SC_MEM_";

	@Autowired
	private UserSCI userSCI;

	@Autowired
	private DeviceSCI deviceSCI;

	@Autowired
	private MessageSourceAccessor messageSourceAccessor; // Message Properties

	@Autowired
	private MemberCommonComponent mcc;

	@Autowired
	private DeviceService deviceService;

	/*
	 * 
	 * <pre> 통합회원 전환생성정보를 사이트에 배포 - CMD : RXCreateUserIDP . </pre>
	 * 
	 * @param map Request로 받은 Parameter Map
	 * 
	 * @return HashMap
	 */
	@Override
	public ImResult rXCreateUserIDP(HashMap<String, String> map) {
		// System.out.println("rXCreateUserIDP ------- ");
		/*
		 * map 정보중 리턴값중 이용동의 사이트정보의 old_id 값 null을 판단 신규가입 , 전환가입/변경가입/변경전환 분기처리
		 */

		LOGGER.debug("rXCreateUserIDP ------- Start");

		String isParentApprove = ""; // 법정 대리인 동의 여부

		String tenantID = "";
		String systemID = "";

		String imIntSvcNo = ""; // 통합서비스번호
		String userId = ""; // 사용자 ID
		String ocbJoinCodeYn = ""; // 통합포인트 가입여부

		tenantID = map.get("tenantID").toString();
		systemID = map.get("systemID").toString();
		imIntSvcNo = map.get("im_int_svc_no").toString();
		userId = map.get("user_id").toString();
		ocbJoinCodeYn = map.get("ocb_join_code").toString();
		// LOGGER.debug("JOIN_SST_LIST START");

		String joinSiteTotalList = map.get("join_sst_list").toString(); // 이용동의사이트정보
		// example info list : 41100,null,20130923,212917,tstore000001741|90300,null,20130917,113426,null
		// LOGGER.debug("replace before:" + joinSiteTotalList);
		joinSiteTotalList = joinSiteTotalList.replaceAll(" ", "");
		// LOGGER.debug("replace after:" + joinSiteTotalList);

		// response object create & setting initialized
		ImResult imResult = new ImResult();
		imResult.setCmd("RXCreateUserIDP");
		imResult.setImIntSvcNo(imIntSvcNo);
		imResult.setUserId(userId);
		String[] mbrCaluseAgreeArray = null;
		String[] tempSplit = joinSiteTotalList.split("\\|");
		for (int i = 0; i < tempSplit.length; i++) {
			String[] tmpSplit = tempSplit[i].split(",");

			if (null != tmpSplit && tmpSplit.length >= 1 && null != tmpSplit[0]
					&& MemberConstants.SSO_SST_CD_TSTORE.equals(tmpSplit[0])) {

				if (tmpSplit.length >= 5 && null != tmpSplit[4] && !"".equals(tmpSplit[4])
						&& !"null".equals(tmpSplit[4])) {
					LOGGER.debug("RXCREATEUSERIDP old_id : " + tmpSplit[4]);
					map.put("old_id", tmpSplit[4]);
				} else if (tmpSplit.length >= 5 && null != tmpSplit[4] && !"".equals(tmpSplit[4])
						&& "null".equals(tmpSplit[4])) {
					map.put("old_id", "null");
				}
				if (tmpSplit.length >= 2 && null != tmpSplit[1] && !"".equals(tmpSplit[1])
						&& !"null".equals(tmpSplit[1])) {
					mbrCaluseAgreeArray = tmpSplit[1].split("\\^");
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
			commonRequest.setTenantID(tenantID);
			commonRequest.setSystemID(systemID);

			// 사용자 기본정보
			UserMbr userMbr = new UserMbr();

			userMbr.setTenantID(tenantID); // 테넌트 ID
			userMbr.setSystemID(systemID); // 테넌트의 시스템 ID

			userMbr.setUserKey(""); // 사용자 Key
			userMbr.setImMbrNo(map.get("user_key").toString()); // 외부(OneID/IDP)에서 할당된 사용자 Key IDP 통합서비스 키 USERMBR_NO
			userMbr.setUserType(MemberConstants.USER_TYPE_ONEID); // * 사용자 구분 코드 ONEID회원으로 셋팅
			userMbr.setUserMainStatus(MemberConstants.MAIN_STATUS_NORMAL); // 사용자 메인 상태 코드 가입시 바로 가입됨 정상
			userMbr.setUserSubStatus(MemberConstants.SUB_STATUS_NORMAL); // 사용자 서브 상태 코드 정상
			userMbr.setImSvcNo(imIntSvcNo); // 통합 서비스 관리번호 INTG_SVC_NO : 통합서비스 관리번호
			userMbr.setIsImChanged(map.get("is_im_changed").toString()); // 전환가입코드 * * - 전환가입 : Y, 신규가입 : N, 변경가입 : C,
																		 // 변경전환 : H
			userMbr.setUserID(userId); // 사용자 ID
			userMbr.setUserPhoneCountry(map.get("user_tn_nation_cd").toString()); // 연락처 국가 코드
			userMbr.setUserPhone(map.get("user_tn").toString()); // 사용자 연락처 . --help
			userMbr.setUserEmail(map.get("user_email").toString()); // 사용자 이메일 주소
			userMbr.setUserName(map.get("user_name").toString()); // 사용자 이름
			userMbr.setUserSex(map.get("user_sex").toString()); // 사용자 성별
			userMbr.setUserBirthDay(map.get("user_birthday").toString()); // 사용자 생년월일
			// userMbr.setUserCity(map.get("user_city_name").toString()); // (외국인)도시
			// userMbr.setUserState(map.get("user_area_name").toString()); // (외국인)주
			userMbr.setUserCountry(map.get("user_nation_code").toString()); // 사용자 국가 코드
			userMbr.setImSiteCode(map.get("join_sst_list").toString()); // OneID 이용동의 사이트 정보
			userMbr.setIsRealName(map.get("is_rname_auth").toString()); // 실명인증 여부 (사용자정보는 그대로 전달하는게 맞는지 확인필요)
			userMbr.setIsParent(isParentApprove); // 법정대리인 동의여부(Y/N)
			userMbr.setIsMemberPoint(ocbJoinCodeYn); // 통합 포인트 여부 (Y/N)

			// 실명인증정보
			MbrAuth mbrAuth = new MbrAuth();
			mbrAuth.setIsRealName("N"); // 실명 인증 여부 신규가입인경우 Tstore에서 실명인증을 받아야 구매할수 있으므로 초기값 셋팅
			mbrAuth.setCi(map.get("user_ci").toString()); // CI
			mbrAuth.setDi(map.get("user_di").toString()); // DI
			mbrAuth.setRealNameMethod(map.get("rname_auth_mns_code").toString()); // AUTH_MTD_CD 인증방법코드
			mbrAuth.setBirthDay(map.get("user_birthday").toString()); // BIRTH 생년월일 DB 에 없음
			mbrAuth.setSex(map.get("user_sex").toString()); // SEX 성별
			mbrAuth.setName(map.get("user_name").toString()); // MBR_NM 회원명
			mbrAuth.setTenantID(commonRequest.getTenantID()); // TENANT_ID 테넌트 아이디
			mbrAuth.setRealNameSite(systemID); // systemID 입력으로 변경 20140204

			createUserRequest.setCommonRequest(commonRequest); // 공통요청
			createUserRequest.setUserMbr(userMbr); // 사용자정보

			// 법정대리인 정보 14세미만인 경우 대리인 정보 셋팅
			if (StringUtils.equals(isParentApprove, MemberConstants.USE_Y)) {
				createUserRequest.setMbrLglAgent(this.getMbrLglAgent(map)); // 법정대리인
			}

			createUserRequest.setMbrAuth(mbrAuth); // 실명인증

			// 신규가입인경우만 이용약관이 들어옴.
			List<MbrClauseAgree> mbrClauseAgreeList = new ArrayList<MbrClauseAgree>();

			MbrClauseAgree mca = new MbrClauseAgree();

			mca.setExtraAgreementID(MemberConstants.POLICY_AGREEMENT_CLAUSE_ONEID);
			mca.setIsExtraAgreement("Y");
			mbrClauseAgreeList.add(mca);

			mca = new MbrClauseAgree();
			mca.setExtraAgreementID(MemberConstants.POLICY_AGREEMENT_CLAUSE_INDIVIDUAL_INFO_HANDLE_ONEID);
			mca.setIsExtraAgreement("Y");
			mbrClauseAgreeList.add(mca);

			// 이용약관 배열중 현재 정의가 안된 TAC005는 보류 상태임. 정의 필요
			if (mbrCaluseAgreeArray != null) {

				// TAC001 US010603 Tstore 이용약관동의
				mca = new MbrClauseAgree();
				mca.setExtraAgreementID(MemberConstants.POLICY_AGREEMENT_CLAUSE_TSTORE);
				mca.setIsExtraAgreement("Y");
				mbrClauseAgreeList.add(mca);

				// TAC002 US010609 통신과금서비스 이용약관
				mca = new MbrClauseAgree();
				mca.setExtraAgreementID(MemberConstants.POLICY_AGREEMENT_CLAUSE_COMMUNICATION_CHARGE);
				mca.setIsExtraAgreement("Y");
				mbrClauseAgreeList.add(mca);

				// TAC003 US010604 TSTORE캐쉬이용약관
				mca = new MbrClauseAgree();
				mca.setExtraAgreementID(MemberConstants.POLICY_AGREEMENT_CLAUSE_CASH);
				mca.setIsExtraAgreement("Y");
				mbrClauseAgreeList.add(mca);

				// TAC004 US010605 TSTORE개인정보취급방침
				mca = new MbrClauseAgree();
				mca.setExtraAgreementID(MemberConstants.POLICY_AGREEMENT_CLAUSE_INDIVIDUAL_INFO_HANDLE_TSTORE);
				mca.setIsExtraAgreement("Y");
				mbrClauseAgreeList.add(mca);

				// TAC005 보류 현재 공통코드 정의가 안되있음
				// mca = new MbrClauseAgree();
				// mca.setExtraAgreementID(MemberConstants.POLICY_AGREEMENT_CLAUSE_INDIVIDUAL_INFO_HANDLE_ONEID);
				// mca.setIsExtraAgreement("Y");
				// mbrClauseAgreeList.add(mca);

				if (mbrCaluseAgreeArray.length < 6) { // 6개 이하인 경우는 선택사항 항목인 US010608이 약관항목에 없는경우 N으로 셋팅
					// TAC006 US010608 TSTORE정보광고활용
					mca = new MbrClauseAgree();
					mca.setExtraAgreementID(MemberConstants.POLICY_AGREEMENT_CLAUSE_MARKETING);
					mca.setIsExtraAgreement("N");
					mbrClauseAgreeList.add(mca);
				} else {
					mca = new MbrClauseAgree();
					mca.setExtraAgreementID(MemberConstants.POLICY_AGREEMENT_CLAUSE_MARKETING);
					mca.setIsExtraAgreement("Y");
					mbrClauseAgreeList.add(mca);
				}

			}

			createUserRequest.setMbrClauseAgreeList(mbrClauseAgreeList);
			try {
				create = this.userSCI.create(createUserRequest); // 가입정보 등록
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
			mbrOneID.setUserKey(create.getUserKey()); // 신규가입때 생성된 내부사용자키를 셋팅
			mbrOneID.setUserID(userId); // userID
			mbrOneID.setIsMemberPoint(ocbJoinCodeYn); // 통합포인트 여부
			mbrOneID.setIsRealName(map.get("is_rname_auth").toString()); // 실명인증 여부

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

		} else { // 전환가입/변경전환/변경 가입 oldId != "null" 이 아닌경우 분기
			CommonRequest commonRequest = new CommonRequest();
			commonRequest.setTenantID(tenantID);
			commonRequest.setSystemID(systemID);
			map.put("im_reg_date", DateUtil.getToday()); // 전환가입일을 셋팅
			String updateUserKey = "";
			if (userId.equals(oldId)) { // 전환가입 userId - oldId 비교시 같은경우
				LOGGER.debug("전환가입 정보 입력 시작");
				SearchUserRequest searchUserRequest = new SearchUserRequest();

				KeySearch keySearch = new KeySearch();
				keySearch.setKeyType("MBR_ID");
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

					updateUserResponse = this.userSCI.updateUser(this.getUpdateUserRequest(map, searchUserResponse));
					LOGGER.debug("전환가입 정보 입력 완료");
					// TO DO... 전시,구매,기타에서 사용되는 회원ID, 회원USER_KEY 등을 변경할수 있는 API 호출 추가 로직 대기중...
				} catch (StorePlatformException spe) {
					imResult.setResult(IdpConstants.IM_IDP_RESPONSE_FAIL_CODE);
					imResult.setResultText(IdpConstants.IM_IDP_RESPONSE_FAIL_CODE_TEXT);
					return imResult;
				}

				updateUserKey = updateUserResponse.getUserKey();

			} else if (!userId.equals(oldId)) { // 변경가입, 변경전환
				LOGGER.debug("변경가입,변경전환 정보 입력 시작");
				SearchUserRequest searchUserRequest = new SearchUserRequest();

				KeySearch keySearch = new KeySearch();
				keySearch.setKeyType("MBR_ID");
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

					updateUserResponse = this.userSCI.updateUser(this.getUpdateUserRequest(map, searchUserResponse));
					LOGGER.debug("변경가입,변경전환 정보 입력 완료");

					// TO DO... 전시,구매,기타에서 사용되는 회원ID, 회원USER_KEY 등을 변경할수 있는 API 호출 추가 로직 대기중...
				} catch (StorePlatformException spe) {
					imResult.setResult(IdpConstants.IM_IDP_RESPONSE_FAIL_CODE);
					imResult.setResultText(IdpConstants.IM_IDP_RESPONSE_FAIL_CODE_TEXT);
					return imResult;
				}

				updateUserKey = updateUserResponse.getUserKey();
			}

			LOGGER.debug("ONEID DATA UPDATE START");

			try {
				// ONEID에 데이터 입력
				UpdateMbrOneIDRequest updateMbrOneIDRequest = new UpdateMbrOneIDRequest();
				updateMbrOneIDRequest.setCommonRequest(commonRequest);
				MbrOneID mbrOneID = new MbrOneID();
				mbrOneID.setStopStatusCode(IdpConstants.SUS_STATUS_RELEASE); // 직권중지해제 기본셋팅
				mbrOneID.setIntgSvcNumber(imIntSvcNo);
				mbrOneID.setUserKey(updateUserKey); // 내부사용자키를 셋팅
				mbrOneID.setUserID(userId); // 사용자 ID 셋팅
				mbrOneID.setIsMemberPoint(ocbJoinCodeYn); // 통합포인트 여부
				mbrOneID.setIsRealName(map.get("is_rname_auth").toString()); // 실명인증 여부

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
		}

		imResult.setResult(IdpConstants.IM_IDP_RESPONSE_SUCCESS_CODE);
		imResult.setResultText(IdpConstants.IM_IDP_RESPONSE_SUCCESS_CODE_TEXT);
		LOGGER.debug("rXCreateUserIDP ------- End");
		return imResult;
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
	private UpdateUserRequest getUpdateUserRequest(HashMap<String, String> hashMap,
			SearchUserResponse searchUserResponse) {
		UpdateUserRequest updateUserRequest = new UpdateUserRequest();

		CommonRequest commonRequest = new CommonRequest();

		commonRequest.setTenantID(hashMap.get("tenantID").toString());
		commonRequest.setSystemID(hashMap.get("systemID").toString());

		UserMbr getUserMbr = searchUserResponse.getUserMbr();
		MbrAuth getMbrAuth = searchUserResponse.getMbrAuth();
		getUserMbr.setImRegDate(hashMap.get("im_reg_date").toString());

		getUserMbr.setTenantID(commonRequest.getTenantID()); // 테넌트 ID
		getUserMbr.setSystemID(commonRequest.getSystemID()); // 테넌트의 시스템 ID

		getUserMbr.setImMbrNo(hashMap.get("user_key").toString()); // 외부(OneID/IDP)에서 할당된 사용자 Key . IDP 통합서비스
																   // 키 USERMBR_NO
		getUserMbr.setUserType(MemberConstants.USER_TYPE_ONEID); // 사용자 구분 코드
		getUserMbr.setUserMainStatus(searchUserResponse.getUserMbr().getUserMainStatus()); // 사용자 메인 상태 코드
		getUserMbr.setUserSubStatus(searchUserResponse.getUserMbr().getUserSubStatus()); // 사용자 서브 상태 코드

		getUserMbr.setImSvcNo(hashMap.get("im_int_svc_no").toString()); // 통합 서비스 관리번호 INTG_SVC_NO : 통합서비스 관리번호.
		getUserMbr.setIsImChanged(hashMap.get("is_im_changed").toString()); // 전환가입코드 * * - 전환가입 : Y, 신규가입 : N, 변경가입 :
																			// C, 변경전환 : H
		getUserMbr.setUserID(hashMap.get("user_id").toString()); // 사용자 ID
		getUserMbr.setUserPhoneCountry(hashMap.get("user_tn_nation_cd").toString()); // 연락처 국가 코드
		getUserMbr.setUserPhone(hashMap.get("user_tn").toString()); // 사용자 연락처
		getUserMbr.setUserEmail(hashMap.get("user_email").toString()); // 사용자 이메일 주소
		getUserMbr.setUserName(hashMap.get("user_name").toString()); // 사용자 이름
		getUserMbr.setUserSex(hashMap.get("user_sex").toString()); // 사용자 성별
		getUserMbr.setUserBirthDay(hashMap.get("user_birthday").toString()); // 사용자 생년월일
		// getUserMbr.setUserCity(hashMap.get("user_city_name").toString()); // (외국인)도시
		// getUserMbr.setUserState(hashMap.get("user_area_name").toString()); // (외국인)주
		getUserMbr.setUserCountry(hashMap.get("user_nation_code").toString()); // 사용자 국가 코드
		getUserMbr.setImSiteCode(hashMap.get("join_sst_list").toString()); // OneID 이용동의 사이트 정보
		getUserMbr.setIsParent(hashMap.get("is_parent_approve").toString()); // 법정대리인 동의여부(Y/N)
		getUserMbr.setIsMemberPoint(hashMap.get("ocb_join_code").toString()); // 통합 포인트 여부 (Y/N)

		// 실명인증정보
		getMbrAuth.setIsRealName(searchUserResponse.getUserMbr().getIsRealName()); // 실명 인증 여부

		getMbrAuth.setCi(hashMap.get("user_ci").toString()); // CI
		getMbrAuth.setDi(hashMap.get("user_di").toString()); // DI
		getMbrAuth.setRealNameMethod(hashMap.get("rname_auth_mns_code").toString()); // AUTH_MTD_CD 인증방법코드
		getMbrAuth.setBirthDay(hashMap.get("user_birthday").toString()); // BIRTH 생년월일 DB 에 없음
		getMbrAuth.setSex(hashMap.get("user_sex").toString()); // SEX 성별
		getMbrAuth.setName(hashMap.get("user_name").toString()); // MBR_NM 회원명
		getMbrAuth.setUpdateDate(hashMap.get("modify_req_date").toString() + hashMap.get("modify_req_time").toString()); // UPD_DT
		getMbrAuth.setRealNameSite(hashMap.get("systemID").toString()); // systemID 입력으로 변경 20140204

		getMbrAuth.setTenantID(commonRequest.getTenantID()); // TENANT_ID 테넌트 아이디

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
			mbrLglAgent.setParentRealNameMethod(hashMap.get("parent_rname_auth_type").toString()); // LGL_AGENT_AUTH_MTD_CD
																								   // 법정대리인 인증방법코드
			mbrLglAgent.setParentName(hashMap.get("parent_name").toString()); // LGL_AGENT_FLNM 법정대리인 이름
			mbrLglAgent.setParentType(hashMap.get("parent_type").toString()); // LGL_AGENT_RSHP 법정대리인 관계, API :
			mbrLglAgent.setParentDate(hashMap.get("parent_approve_date").toString()); // LGL_AGENT_AGREE_DT 동의 일시
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
	 * <pre> 회선 변경 정보 Provisioning (번호변경) - CMD : changeMobileNumber </pre>
	 * 
	 * @param map Request로 받은 Parameter Map
	 * 
	 * @return IDP Provisioning 처리 결과
	 */
	@Override
	public String changeMobileNumber(HashMap<String, String> map) {

		String mdn = StringUtil.nvl(map.get("mdn"), "");
		String be_mdn = StringUtil.nvl(map.get("be_mdn"), ""); // 이전 번호
		String user_key = StringUtil.nvl(map.get("user_key"), "");
		String tenantId = StringUtil.nvl(map.get("tenantID"), "");
		String systemId = StringUtil.nvl(map.get("systemID"), "");
		String model_id = StringUtil.nvl(map.get("model_id"), "");

		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID(tenantId);
		commonRequest.setSystemID(systemId);

		try {

			/* 단말 정보 조회 */
			Device device = this.mcc.getPhoneInfo(model_id);

			if (device == null) {

				device.setV4SprtTypeCd("N"); // V4 무조전 해지

			} else if (StringUtil.equals(device.getVerifyDvcYn(), "Y")) {// 타겟 단말인 경우

				/* 테스트 단말여부 확인 */
				String isTestModel = "N";
				List<String> limitPolicyCodeList = new ArrayList<String>();
				limitPolicyCodeList.add(MemberConstants.USER_LIMIT_POLICY_TESTER);
				SearchPolicyRequest policyRequest = new SearchPolicyRequest();
				policyRequest.setCommonRequest(commonRequest);
				policyRequest.setLimitPolicyKey(mdn);
				policyRequest.setLimitPolicyCodeList(limitPolicyCodeList);
				SearchPolicyResponse policyResponse = this.userSCI.searchPolicyList(policyRequest);
				for (LimitTarget limitTarget : policyResponse.getLimitTargetList()) {
					if (limitTarget.getPolicyApplyValue().equals(mdn)) {
						isTestModel = "Y";
						break;
					}
				}

				if (isTestModel.equals("Y")) {

				} else {
					device.setV4SprtTypeCd("N"); // V4 무조전 해지
				}

			}

			/* 휴대기기 수정 요청 */

			/* 게임센터 연동 */
			GameCenterSacReq gameCenterSacReq = new GameCenterSacReq();
			// gameCenterSacReq.setUserKey(schUserRes.getUserKey());
			gameCenterSacReq.setDeviceId(mdn);
			gameCenterSacReq.setPreDeviceId(be_mdn);
			gameCenterSacReq.setSystemId(systemId);
			gameCenterSacReq.setTenantId(tenantId);
			gameCenterSacReq.setWorkCd(MemberConstants.GAMECENTER_WORK_CD_MOBILENUMBER_CHANGE);
			this.deviceService.insertGameCenterIF(gameCenterSacReq);

		} catch (StorePlatformException ex) {
			if (ex.getErrorInfo().getCode().equals(MemberConstants.SC_ERROR_NO_DATA)) {
				return this.FAIL_NODATA_STR;
			} else {
				return this.FAIL_STR;
			}
		}

		return this.SUCCESS_STR;
	}

	/*
	 * 
	 * <pre> 회선 변경 정보 Provisioning (기기변경) - CMD : changeMobileID </pre>
	 * 
	 * @param map Request로 받은 Parameter Map
	 * 
	 * @return IDP Provisioning 처리 결과
	 */
	@Override
	public String changeMobileID(HashMap<String, String> map) {

		String user_key = StringUtil.nvl(map.get("user_key"), "");
		String mdn = StringUtil.nvl(map.get("mdn"), "");
		String model_id = StringUtil.nvl(map.get("model_id"), "");
		String tenantId = StringUtil.nvl(map.get("tenantID"), "");
		String systemId = StringUtil.nvl(map.get("systemID"), "");

		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID(tenantId);
		commonRequest.setSystemID(systemId);

		try {

			/* 회원정보 조회 */
			List<KeySearch> keySearchList = new ArrayList<KeySearch>();
			KeySearch key = new KeySearch();
			key.setKeyType(MemberConstants.KEY_TYPE_USERMBR_NO);
			key.setKeyString(user_key);
			keySearchList.add(key);

			SearchUserRequest schUserReq = new SearchUserRequest();
			schUserReq.setCommonRequest(commonRequest);
			schUserReq.setKeySearchList(keySearchList);
			SearchUserResponse schUserRes = this.userSCI.searchUser(schUserReq);

			/* 단말 정보 조회 */
			Device device = this.mcc.getPhoneInfo(model_id);

			if (device == null) {

				device.setV4SprtTypeCd("N"); // V4 무조전 해지

			} else if (StringUtil.equals(device.getVerifyDvcYn(), "Y")) {// 타겟 단말인 경우

				/* 테스트 단말여부 확인 */
				String isTestModel = "N";
				List<String> limitPolicyCodeList = new ArrayList<String>();
				limitPolicyCodeList.add(MemberConstants.USER_LIMIT_POLICY_TESTER);
				SearchPolicyRequest policyRequest = new SearchPolicyRequest();
				policyRequest.setCommonRequest(commonRequest);
				policyRequest.setLimitPolicyKey(mdn);
				policyRequest.setLimitPolicyCodeList(limitPolicyCodeList);
				SearchPolicyResponse policyResponse = this.userSCI.searchPolicyList(policyRequest);
				for (LimitTarget limitTarget : policyResponse.getLimitTargetList()) {
					if (limitTarget.getPolicyApplyValue().equals(mdn)) {
						isTestModel = "Y";
						break;
					}
				}

				if (isTestModel.equals("Y")) {

				} else {
					device.setV4SprtTypeCd("N"); // V4 무조전 해지
				}

			}

			/* 휴대기기 수정 요청 */
			UserMbrDevice userMbrDevice = new UserMbrDevice();
			userMbrDevice.setDeviceID(mdn);
			userMbrDevice.setDeviceModelNo(model_id);

			List<UserMbrDeviceDetail> userMbrDeviceDetailList = new ArrayList<UserMbrDeviceDetail>();
			UserMbrDeviceDetail userMbrDeviceDetail = new UserMbrDeviceDetail();
			userMbrDeviceDetail.setExtraProfile(MemberConstants.DEVICE_EXTRA_UACD);
			userMbrDeviceDetail.setExtraProfileValue(device.getUaCd());
			userMbrDeviceDetail.setTenantID(tenantId);
			userMbrDeviceDetail.setUserKey(schUserRes.getUserKey());
			userMbrDeviceDetailList.add(userMbrDeviceDetail);

			userMbrDevice.setUserMbrDeviceDetail(userMbrDeviceDetailList);

			CreateDeviceRequest createDeviceReq = new CreateDeviceRequest();
			createDeviceReq.setCommonRequest(commonRequest);
			createDeviceReq.setUserKey(schUserRes.getUserKey());
			createDeviceReq.setIsNew("N");
			createDeviceReq.setUserMbrDevice(userMbrDevice);
			this.deviceSCI.createDevice(createDeviceReq);

			/* 게임센터 연동 */
			GameCenterSacReq gameCenterSacReq = new GameCenterSacReq();
			gameCenterSacReq.setUserKey(schUserRes.getUserKey());
			gameCenterSacReq.setDeviceId(mdn);
			gameCenterSacReq.setSystemId(systemId);
			gameCenterSacReq.setTenantId(tenantId);
			gameCenterSacReq.setWorkCd(MemberConstants.GAMECENTER_WORK_CD_MOBILENUMBER_INSERT);
			this.deviceService.insertGameCenterIF(gameCenterSacReq);

			/* DCD 연동 */
		} catch (StorePlatformException ex) {
			if (ex.getErrorInfo().getCode().equals(MemberConstants.SC_ERROR_NO_DATA)) {
				return this.FAIL_NODATA_STR;
			} else {
				return this.FAIL_STR;
			}
		}

		return this.SUCCESS_STR;
	}

	/*
	 * 
	 * <pre> 회선 변경 정보 Provisioning (번호해지) - CMD : secedeMobileNumber </pre>
	 * 
	 * @param map Request로 받은 Parameter Map
	 * 
	 * @return IDP Provisioning 처리 결과
	 */
	@Override
	public String secedeMobileNumber(HashMap<String, String> map) {

		String svc_mng_num = StringUtil.nvl(map.get("svc_mng_num"), "");
		String mdn = StringUtil.nvl(map.get("mdn"), "");
		String tenantId = StringUtil.nvl(map.get("tenantID"), "");
		String systemId = StringUtil.nvl(map.get("systemID"), "");

		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID(tenantId);
		commonRequest.setSystemID(systemId);

		try {

			/* 휴대기기 정보조회 - 서비스 관리 번호 */

			/* 휴대기기 삭제 요청 */

			/* 게임센터 연동 */
			GameCenterSacReq gameCenterSacReq = new GameCenterSacReq();
			// gameCenterSacReq.setUserKey(schUserRes.getUserKey());
			gameCenterSacReq.setDeviceId(mdn);
			gameCenterSacReq.setSystemId(systemId);
			gameCenterSacReq.setTenantId(tenantId);
			gameCenterSacReq.setWorkCd(MemberConstants.GAMECENTER_WORK_CD_NAME_CHANGE);

			this.deviceService.insertGameCenterIF(gameCenterSacReq);

		} catch (StorePlatformException ex) {
			if (ex.getErrorInfo().getCode().equals(MemberConstants.SC_ERROR_NO_DATA)) {
				return this.FAIL_NODATA_STR;
			} else {
				return this.FAIL_STR;
			}
		}
		return this.SUCCESS_STR;
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
	public ImResult rXInvalidUserTelNoIDP(HashMap map) {
		// Service Method이름은 Provisioning 및 Rx 기능의 'cmd' 값과 동일 해야 함.
		SearchUserRequest searchUserRequest = new SearchUserRequest();
		UpdateUserRequest userVo = new UpdateUserRequest();
		IdpConstants idpConstant = new IdpConstants();

		String idpResult = idpConstant.IM_IDP_RESPONSE_FAIL_CODE;
		String idpResultText = idpConstant.IM_IDP_RESPONSE_FAIL_CODE_TEXT;

		// 회원 정보 조회
		// 공통 헤더
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setSystemID((String) map.get("systemID"));
		commonRequest.setTenantID((String) map.get("tenantID"));
		searchUserRequest.setCommonRequest(commonRequest);

		List<KeySearch> keySearchList = new ArrayList<KeySearch>();
		KeySearch keySearch = new KeySearch();
		keySearch.setKeyType("MBR_ID");
		keySearch.setKeyString((String) map.get("user_id"));

		keySearchList.add(keySearch);
		searchUserRequest.setKeySearchList(keySearchList);
		try {
			SearchUserResponse searchUserRespnse = this.userSCI.searchUser(searchUserRequest);

			// 회원 수정 정보 세팅
			UserMbr getUserMbr = searchUserRespnse.getUserMbr();

			if (getUserMbr != null) {
				getUserMbr.setUserPhone((String) map.get("user_tn"));
				userVo.setUserMbr(getUserMbr);
				userVo.setCommonRequest(commonRequest);

				// 회원 정보 수정 API call
				UpdateUserResponse updateUserResponse = this.userSCI.updateUser(userVo);

				idpResult = idpConstant.IM_IDP_RESPONSE_SUCCESS_CODE;
				idpResultText = idpConstant.IM_IDP_RESPONSE_SUCCESS_CODE_TEXT;
			}
		} catch (StorePlatformException spe) {
			idpResult = idpConstant.IM_IDP_RESPONSE_FAIL_CODE;
			idpResultText = idpConstant.IM_IDP_RESPONSE_FAIL_CODE_TEXT;
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
	public ImResult rXSetLoginConditionIDP(HashMap map) {

		UpdateStatusUserRequest updateUserVo = new UpdateStatusUserRequest();
		MemberConstants memberConstant = new MemberConstants();
		IdpConstants idpConstant = new IdpConstants();

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
		String idpResult = idpConstant.IM_IDP_RESPONSE_FAIL_CODE;
		String idpResultText = idpConstant.IM_IDP_RESPONSE_FAIL_CODE_TEXT;

		try {
			UpdateStatusUserResponse updateStatusResponse = this.userSCI.updateStatus(updateUserVo);
		} catch (StorePlatformException spe) {
			LOGGER.debug("RXSetLoginConditionIDP ------- update state excetion error code = "
					+ spe.getErrorInfo().getCode());
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

			if (updateMbrOneIDResponse.getCommonResponse().getResultCode()
					.equals(this.SC_RETURN + memberConstant.RESULT_SUCCES)) { // SC반환값이
				// 성공이면
				idpResult = idpConstant.IM_IDP_RESPONSE_SUCCESS_CODE;
				idpResultText = idpConstant.IM_IDP_RESPONSE_SUCCESS_CODE_TEXT;
			}
		} catch (StorePlatformException spe) {
			idpResult = idpConstant.IM_IDP_RESPONSE_FAIL_CODE;
			idpResultText = idpConstant.IM_IDP_RESPONSE_FAIL_CODE_TEXT;
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
	public ImResult rXCreateUserIdIDP(HashMap map) {

		LOGGER.debug("rXCreateUserIdIDP ------- Testing Start");

		// Service Method이름은 Provisioning 및 Rx 기능의 'cmd' 값과 동일 해야 함.
		SearchUserRequest searchUserRequest = new SearchUserRequest();
		IdpConstants idpConstant = new IdpConstants();
		MemberConstants memberConstant = new MemberConstants();
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
		keySearch.setKeyType("MBR_ID");
		LOGGER.debug("rXCreateUserIdIDP ------- get user_id");
		LOGGER.debug("rXCreateUserIdIDP ------- user_id = " + map.get("user_id").toString());
		if (null != map.get("user_id")) {
			keySearch.setKeyString((String) map.get("user_id"));
		}
		keySearchList.add(keySearch);
		searchUserRequest.setKeySearchList(keySearchList);

		String idpResult = idpConstant.IM_IDP_RESPONSE_FAIL_CODE;
		String idpResultText = idpConstant.IM_IDP_RESPONSE_FAIL_CODE_TEXT;

		try {
			SearchUserResponse searchUserRespnse = this.userSCI.searchUser(searchUserRequest);
		} catch (StorePlatformException spe) {

			// 회원 존재 여부 확인
			// 회원이 존재 하지 않을때 one id 데이블에 추가
			if (spe.getErrorInfo().getCode().endsWith(memberConstant.RESULT_NOT_FOUND_USER_KEY)) {
				// one id 가입 정보 등록
				UpdateMbrOneIDRequest updateMbrOneIDRequest = new UpdateMbrOneIDRequest();
				updateMbrOneIDRequest.setCommonRequest(commonRequest);
				MbrOneID mbrOneID = new MbrOneID();
				mbrOneID.setIntgSvcNumber(map.get("im_int_svc_no").toString());
				// 통합회원 유형 코드
				mbrOneID.setIntgMbrCaseCode(map.get("im_mem_type_cd").toString());
				// // 가입자 상태코드
				// mbrOneID.setEntryStatusCode(map.get("user_status_code").toString());
				// // 사용자 실명 인증여부
				// mbrOneID.setIsRealName(map.get("is_rname_auth").toString());
				mbrOneID.setUserID(map.get("user_id").toString());
				updateMbrOneIDRequest.setMbrOneID(mbrOneID);

				UpdateMbrOneIDResponse updateMbrOneIDResponse = this.userSCI.createAgreeSite(updateMbrOneIDRequest);

				if (updateMbrOneIDResponse.getCommonResponse().getResultCode()
						.equals(this.SC_RETURN + memberConstant.RESULT_SUCCES)) { // SC반환값이
					idpResult = idpConstant.IM_IDP_RESPONSE_SUCCESS_CODE;
					idpResultText = idpConstant.IM_IDP_RESPONSE_SUCCESS_CODE_TEXT;
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
	public ImResult rXSetSuspendUserIdIDP(HashMap map) {
		UpdateStatusUserRequest updateUserVo = new UpdateStatusUserRequest();
		MemberConstants memberConstant = new MemberConstants();
		IdpConstants idpConstant = new IdpConstants();

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
		String idpResult = idpConstant.IM_IDP_RESPONSE_FAIL_CODE;
		String idpResultText = idpConstant.IM_IDP_RESPONSE_FAIL_CODE_TEXT;

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

			if (updateMbrOneIDResponse.getCommonResponse().getResultCode()
					.equals(this.SC_RETURN + memberConstant.RESULT_SUCCES)) { // SC반환값이
				// 성공이면
				idpResult = idpConstant.IM_IDP_RESPONSE_SUCCESS_CODE;
				idpResultText = idpConstant.IM_IDP_RESPONSE_SUCCESS_CODE_TEXT;
			}
		} catch (StorePlatformException spe) {
			idpResult = idpConstant.IM_IDP_RESPONSE_FAIL_CODE;
			idpResultText = idpConstant.IM_IDP_RESPONSE_FAIL_CODE_TEXT;
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
	public ImResult rXUpdateUserNameIDP(HashMap map) {
		// 실명 인증 정보
		UpdateRealNameRequest updateRealNameRequest = new UpdateRealNameRequest();
		MemberConstants memberConstant = new MemberConstants();
		IdpConstants idpConstant = new IdpConstants();
		String idpResult = idpConstant.IM_IDP_RESPONSE_FAIL_CODE;
		String idpResultText = idpConstant.IM_IDP_RESPONSE_FAIL_CODE_TEXT;

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
				updateRealNameRequest.setIsOwn("OWN");
				// updateRealNameRequest.setIsRealName(map.get("is_rname_auth").toString());
				updateRealNameRequest.setUserKey(searchUserRespnse.getUserMbr().getUserKey());

				MbrAuth mbrAuth = new MbrAuth();
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
				if (map.get("rname_auth_mns_code") != null)
					mbrAuth.setRealNameMethod(map.get("rname_auth_mns_code").toString());

				updateRealNameRequest.setUserMbrAuth(mbrAuth);

				UpdateRealNameResponse updateRealNameResponse = this.userSCI.updateRealName(updateRealNameRequest);
				LOGGER.info("response param : {}", updateRealNameResponse.getUserKey());

				// oneID 테이블 업데이트
				UpdateMbrOneIDRequest updateMbrOneIDRequest = new UpdateMbrOneIDRequest();
				updateMbrOneIDRequest.setCommonRequest(commonRequest);
				MbrOneID mbrOneID = new MbrOneID();
				mbrOneID.setIntgSvcNumber((String) map.get("im_int_svc_no"));
				// 실명 인증여부
				mbrOneID.setIsRealName(map.get("is_rname_auth").toString());
				// CI 존재 여부
				if (map.get("user_ci") == null || map.get("user_ci").toString().length() <= 0) {
					mbrOneID.setIsCi("N");
				} else {
					mbrOneID.setIsCi("Y");
				}
				updateMbrOneIDRequest.setMbrOneID(mbrOneID);

				UpdateMbrOneIDResponse updateMbrOneIDResponse = this.userSCI.createAgreeSite(updateMbrOneIDRequest);

				if (updateRealNameResponse.getCommonResponse().getResultCode()
						.equals(this.SC_RETURN + memberConstant.RESULT_SUCCES)
						&& updateMbrOneIDResponse.getCommonResponse().getResultCode()
								.equals(this.SC_RETURN + memberConstant.RESULT_SUCCES)) {
					idpResult = idpConstant.IM_IDP_RESPONSE_SUCCESS_CODE;
					idpResultText = idpConstant.IM_IDP_RESPONSE_SUCCESS_CODE_TEXT;
				}
			}
		} catch (StorePlatformException spe) {
			idpResult = idpConstant.IM_IDP_RESPONSE_FAIL_CODE;
			idpResultText = idpConstant.IM_IDP_RESPONSE_FAIL_CODE_TEXT;
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
	public ImResult rXUpdateGuardianInfoIDP(HashMap map) {
		// 실명 인증 정보
		UpdateRealNameRequest updateRealNameRequest = new UpdateRealNameRequest();
		MemberConstants memberConstant = new MemberConstants();
		IdpConstants idpConstant = new IdpConstants();
		String idpResult = idpConstant.IM_IDP_RESPONSE_FAIL_CODE;
		String idpResultText = idpConstant.IM_IDP_RESPONSE_FAIL_CODE_TEXT;

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
				updateRealNameRequest.setIsOwn("PARENT");
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
					mbrLglAgent.setParentRealNameDate(map.get("parent_approve_date").toString() + "000000");
				} else if (map.get("parent_approve_date").toString().length() > 8) {
					mbrLglAgent.setParentRealNameDate(map.get("parent_approve_date").toString());
				}
				mbrLglAgent.setParentName(map.get("parent_name").toString());
				mbrLglAgent.setParentType(map.get("parent_type").toString());
				mbrLglAgent.setParentRealNameMethod(map.get("parent_rname_auth_type").toString());
				if (map.get("parent_email") != null)
					mbrLglAgent.setParentEmail(map.get("parent_email").toString());

				updateRealNameRequest.setMbrLglAgent(mbrLglAgent);

				UpdateRealNameResponse updateRealNameResponse = this.userSCI.updateRealName(updateRealNameRequest);
				LOGGER.info("response param : {}", updateRealNameResponse.getUserKey());
				if (updateRealNameResponse.getCommonResponse().getResultCode()
						.equals(this.SC_RETURN + memberConstant.RESULT_SUCCES)) {
					idpResult = idpConstant.IM_IDP_RESPONSE_SUCCESS_CODE;
					idpResultText = idpConstant.IM_IDP_RESPONSE_SUCCESS_CODE_TEXT;
				}
			}
		} catch (StorePlatformException spe) {
			idpResult = idpConstant.IM_IDP_RESPONSE_FAIL_CODE;
			idpResultText = idpConstant.IM_IDP_RESPONSE_FAIL_CODE_TEXT;
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
	public ImResult rXActivateUserIdIDP(HashMap<String, String> map) {
		LOGGER.debug("rXActivateUserIdIDP ------- Start");

		String imIntSvcNo = map.get("im_int_svc_no").toString(); // 통합 서비스 번호

		String resultValue = "";
		String userId = "";
		SearchUserResponse searchUserResponse = null;

		imIntSvcNo = map.get("im_int_svc_no").toString();
		userId = map.get("user_id").toString();

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
		keySearch.setKeyType("MBR_ID");
		keySearch.setKeyString(userId); // 사용자 ID추가
		keySearchList.add(keySearch);

		searchUserRequest.setKeySearchList(keySearchList);

		try {
			searchUserResponse = this.userSCI.searchUser(searchUserRequest);
		} catch (StorePlatformException spe) { // 회원정보 조회시 오류발생시라도 프로비저닝은 성공으로 처리함.
			imResult.setResult(IdpConstants.IM_IDP_RESPONSE_SUCCESS_CODE);
			imResult.setResultText(IdpConstants.IM_IDP_RESPONSE_SUCCESS_CODE_TEXT);
			return imResult;
		}

		try {
			if (searchUserResponse.getUserMbr() != null) { // 통합서비스 번호로 조회한 회원정보가 있을경우만 로직처리

				// 조회되어진 사용자의 상태값이 정상이 아닌경우에 사용자 상태변경 정상 상태로 TB_US_USERMBR 데이터를 수정함
				if (!MemberConstants.MAIN_STATUS_NORMAL.equals(searchUserResponse.getUserMbr().getUserMainStatus())
						|| !MemberConstants.SUB_STATUS_NORMAL
								.equals(searchUserResponse.getUserMbr().getUserSubStatus())) {
					UpdateStatusUserRequest updateStatusUserRequest = new UpdateStatusUserRequest();

					updateStatusUserRequest.setCommonRequest(commonRequest);

					updateStatusUserRequest.setUserMainStatus(MemberConstants.MAIN_STATUS_NORMAL);
					updateStatusUserRequest.setUserSubStatus(MemberConstants.SUB_STATUS_NORMAL);

					KeySearch updateKeySearch = new KeySearch();
					updateKeySearch.setKeyType("INSD_USERMBR_NO");
					updateKeySearch.setKeyString(searchUserResponse.getUserMbr().getUserKey()); // 내부사용자 회원번호

					List<KeySearch> updateKeySearchList = new ArrayList<KeySearch>();
					updateKeySearchList.add(keySearch);

					updateStatusUserRequest.setKeySearchList(updateKeySearchList);

					UpdateStatusUserResponse updateStatusUserResponse = this.userSCI
							.updateStatus(updateStatusUserRequest);
					resultValue = updateStatusUserResponse.getCommonResponse().getResultCode();

					if (resultValue.equals(MemberConstants.RESULT_SUCCES)) {
						LOGGER.debug("ONEID DATA MERGE START");
						// ONEID에 데이터 입력
						UpdateMbrOneIDRequest updateMbrOneIDRequest = new UpdateMbrOneIDRequest();
						updateMbrOneIDRequest.setCommonRequest(commonRequest);
						MbrOneID mbrOneID = new MbrOneID();
						mbrOneID.setStopStatusCode(IdpConstants.SUS_STATUS_RELEASE); // 직권중지해제 기본셋팅
						mbrOneID.setIntgSvcNumber(map.get("im_int_svc_no"));
						mbrOneID.setUserKey(searchUserResponse.getUserMbr().getUserKey()); // 신규가입때 생성된 내부사용자키를 셋팅
						mbrOneID.setUserID(searchUserResponse.getUserMbr().getUserID()); // userID
						updateMbrOneIDRequest.setMbrOneID(mbrOneID);

						this.userSCI.createAgreeSite(updateMbrOneIDRequest);

						LOGGER.debug("ONEID DATA MERGE COMPLETE");
					}
				}

				// TO DO ... 이하 API 호출구현되면 로직 처리
				// 이메일 인증시 모바일 인증여부 Y 업데이트
				// 가입 아이디에 신규 단말 추가시 캐쉬 지급 처리 핸드폰 디바이스 관련
				// 기존 모바일회원과 웹아이디회원 실명인증 정보처리
				// DEVICE관련 & 구매내역이관 모바일 회원 탈퇴처리

			}

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
	 * <pre> 가입 승인 만료 정보 Provisioning (유선, 통합 회원) - CMD : joinComplete. </pre>
	 * 
	 * @param map Request로 받은 Parameter Map
	 * 
	 * @return HashMap
	 */
	@Override
	public String joinComplete(HashMap<String, String> map) {

		String user_key = StringUtil.nvl(map.get("user_key"), "");
		String tenantId = StringUtil.nvl(map.get("tenantID"), "");
		String systemId = StringUtil.nvl(map.get("systemID"), "");

		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID(tenantId);
		commonRequest.setSystemID(systemId);

		List<KeySearch> keySearchList = new ArrayList<KeySearch>();
		KeySearch key = new KeySearch();
		key.setKeyType(MemberConstants.KEY_TYPE_USERMBR_NO);
		key.setKeyString(user_key);
		keySearchList.add(key);

		SearchUserRequest schUserReq = new SearchUserRequest();
		schUserReq.setCommonRequest(commonRequest);
		schUserReq.setKeySearchList(keySearchList);

		try {

			/* 회원 정보 조회 */
			SearchUserResponse schUserRes = this.userSCI.searchUser(schUserReq);

			/* 회원 탈퇴 처리 */
			RemoveUserRequest removeUserReq = new RemoveUserRequest();
			removeUserReq.setCommonRequest(commonRequest);
			removeUserReq.setUserKey(schUserRes.getUserMbr().getUserKey());
			removeUserReq.setSecedeTypeCode(MemberConstants.USER_WITHDRAW_CLASS_JOIN_AGREE_EXPIRED); // 가입승인만료
			removeUserReq.setSecedeReasonCode("US010409"); // 기타
			removeUserReq.setSecedeReasonMessage("가입승인만료");

			this.userSCI.remove(removeUserReq);

			/* 게임센터 연동 */
			GameCenterSacReq gameCenterSacReq = new GameCenterSacReq();
			gameCenterSacReq.setUserKey(user_key);
			gameCenterSacReq.setSystemId(systemId);
			gameCenterSacReq.setTenantId(tenantId);
			gameCenterSacReq.setWorkCd(MemberConstants.GAMECENTER_WORK_CD_USER_SECEDE);
			this.deviceService.insertGameCenterIF(gameCenterSacReq);

		} catch (StorePlatformException ex) {

			if (ex.getErrorInfo().getCode().equals(MemberConstants.SC_ERROR_NO_DATA)) {
				return this.FAIL_NODATA_STR;
			} else {
				return this.FAIL_STR;
			}

		}

		return this.SUCCESS_STR;
	}

	/*
	 * 
	 * <pre> 프로파일 변경 Provisioning (유선, 통합 회원) - CMD : adjustWiredProfile. </pre>
	 * 
	 * @param map Request로 받은 Parameter Map
	 * 
	 * @return HashMap
	 */
	@Override
	public String adjustWiredProfile(HashMap<String, String> map) {

		String im_int_svc_no = StringUtil.nvl(map.get("im_int_svc_no"), "");
		String imMbrNo = StringUtil.nvl(map.get("user_key"), "");

		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID(StringUtil.nvl(map.get("tenantID"), ""));
		commonRequest.setSystemID(StringUtil.nvl(map.get("systemID"), ""));

		/* 회원 정보 조회 */
		List<KeySearch> keySearchList = new ArrayList<KeySearch>();
		KeySearch key = new KeySearch();
		key.setKeyType(MemberConstants.KEY_TYPE_USERMBR_NO);
		key.setKeyString(imMbrNo);
		keySearchList.add(key);
		SearchUserRequest schUserReq = new SearchUserRequest();
		schUserReq.setCommonRequest(commonRequest);
		schUserReq.setKeySearchList(keySearchList);
		SearchUserResponse schUserRes = null;

		try {

			schUserRes = this.userSCI.searchUser(schUserReq);
			String userKey = schUserRes.getUserKey();

			/* 변경정보 셋팅 */
			UserMbr userMbr = new UserMbr();
			userMbr.setUserKey(userKey);

			MbrAuth mbrAuth = new MbrAuth();
			mbrAuth.setMemberKey(userKey);

			String user_phone = StringUtil.nvl(map.get("user_phone"), "");
			String user_id = StringUtil.nvl(map.get("user_id"), "");
			String user_sex = StringUtil.nvl(map.get("user_sex"), "");
			String user_birthday = StringUtil.nvl(map.get("user_birthday"), "");
			String user_email = StringUtil.nvl(map.get("user_email"), "");
			String is_rname_auth = StringUtil.nvl(map.get("is_rname_auth"), "");
			String user_name = StringUtil.nvl(map.get("user_name"), "");
			String user_social_number = StringUtil.nvl(map.get("user_social_number"), "");

			userMbr.setUserID(user_id);
			userMbr.setUserSex(user_sex);
			userMbr.setUserBirthDay(user_birthday);

			/* 실명인증이 추가된 경우만 처리 */
			if (is_rname_auth.equals("Y")) {

				if (!user_name.equals("") && !user_social_number.equals("")) {

					userMbr.setIsRealName(is_rname_auth);
					userMbr.setUserName(user_name);

					/* 실명인증 정보까지 업데이트 해야 하는지 확인필요 */
					mbrAuth.setIsRealName(is_rname_auth);
					mbrAuth.setName(user_name);
					mbrAuth.setBirthDay(user_birthday);
					mbrAuth.setRealNameDate(DateUtil.getDateString(new Date(), "yyyyMMddHHmmss"));

				}
			}

			if (!user_email.equals("")) {
				userMbr.setUserEmail(user_name);
			}

			if (!im_int_svc_no.equals("")) {// 통합회원이 아닌경우
				/* 회원정보 변경 요청 */
				UpdateUserRequest updateUserRequest = new UpdateUserRequest();
				updateUserRequest.setUserMbr(userMbr);
				if (mbrAuth.getIsRealName() != null && mbrAuth.getIsRealName().equals("Y")) {
					updateUserRequest.setMbrAuth(mbrAuth);
				}
				updateUserRequest.setCommonRequest(commonRequest);
				this.userSCI.updateUser(updateUserRequest);
			}

			/* 휴대기기 목록 조회 */
			SearchDeviceListRequest schDeviceListReq = new SearchDeviceListRequest();
			schDeviceListReq.setUserKey(userKey);
			schDeviceListReq.setIsMainDevice("N");
			key.setKeyType(MemberConstants.KEY_TYPE_INSD_USERMBR_NO);
			key.setKeyString(userKey);
			keySearchList.add(key);
			schDeviceListReq.setKeySearchList(keySearchList);
			schDeviceListReq.setCommonRequest(commonRequest);

			/* 사용자 휴대기기 목록 조회 */
			SearchDeviceListResponse schDeviceListRes = this.deviceSCI.searchDeviceList(schDeviceListReq);

			/* 휴대기기 기기 정보 변경 */
			if (!user_phone.equals("")) {

				if (user_phone.indexOf("|") > -1) { // 처리단말이 여러개

					String user_phones[] = user_phone.split("\\|");
					List<String> idpUserPhoneList = new ArrayList<String>();

					for (int i = 0; i < user_phones.length; i++) {

						String phone_info[] = user_phones[i].split(",");

						/* 휴대기기 번호 */
						String deviceId = phone_info[0];
						LOGGER.info("deviceId {}", deviceId);
						idpUserPhoneList.add(i, deviceId);

						/* SKT 통합관리번호 */
						String svcMangNum = phone_info[1];
						LOGGER.info("svcMangNum {}", svcMangNum);

						/* uacd */
						String uaCd = phone_info[2];
						LOGGER.info("uaCd {}", uaCd);

						/* 통신사명 */
						String deviceTelecom = phone_info[3];
						LOGGER.info("deviceTelecom {}", deviceTelecom);

						/* 단말코드 조회 */
						Device device = this.mcc.getPhoneInfoByUacd(uaCd);
						String deviceModelNo = device.getDeviceModelCd();
						LOGGER.info("deviceModelCd {}", deviceModelNo);

						for (UserMbrDevice userMbrDevice : schDeviceListRes.getUserMbrDevice()) {

							/* 동일한 deviceId인 경우 수정처리 */
							if (userMbrDevice.getDeviceID().equals(deviceId)) {

								UserMbrDevice modifyDevice = new UserMbrDevice();
								modifyDevice.setDeviceModelNo(deviceModelNo);
								modifyDevice.setDeviceTelecom(deviceTelecom);
								modifyDevice.setSvcMangNum(svcMangNum);

								List<UserMbrDeviceDetail> modifyDeviceDetailList = new ArrayList<UserMbrDeviceDetail>();

								UserMbrDeviceDetail modifyDeviceDetail = new UserMbrDeviceDetail();
								modifyDeviceDetail.setUserKey(userKey);
								modifyDeviceDetail.setTenantID(map.get("tenantID"));
								modifyDeviceDetail.setExtraProfile(MemberConstants.DEVICE_EXTRA_UACD);
								modifyDeviceDetail.setExtraProfileValue(uaCd);
								modifyDeviceDetailList.add(modifyDeviceDetail);

								modifyDevice.setUserMbrDeviceDetail(modifyDeviceDetailList);

								CreateDeviceRequest createDeviceReq = new CreateDeviceRequest();
								createDeviceReq.setCommonRequest(commonRequest);
								createDeviceReq.setUserKey(userKey);
								createDeviceReq.setIsNew("N");
								createDeviceReq.setUserMbrDevice(userMbrDevice);
								this.deviceSCI.createDevice(createDeviceReq);

							}
						}
					}

					/* user_phone에 존재하지 않은 휴대기기 정보 삭제 처리 */
					List<String> removeKeyList = new ArrayList<String>();
					for (UserMbrDevice userMbrDevice : schDeviceListRes.getUserMbrDevice()) {

						String removeYn = "Y";
						for (String deviceIdStr : idpUserPhoneList) {

							if (userMbrDevice.getDeviceID().equals(deviceIdStr)) {
								removeYn = "N";
							}

						}

						if (removeYn.equals("Y")) {
							removeKeyList.add(userMbrDevice.getDeviceKey());
						}

					}

					if (removeKeyList.size() > 0) {
						RemoveDeviceRequest removeDeviceReq = new RemoveDeviceRequest();
						removeDeviceReq.setUserKey(userKey);
						removeDeviceReq.setDeviceKey(removeKeyList);
					}

				} else { // 처리단말 한개

					String phone_info[] = user_phone.split(",");

					/* 휴대기기 번호 */
					String deviceId = phone_info[0];
					LOGGER.info("deviceId {}", deviceId);

					/* SKT 통합관리번호 */
					String svcMangNum = phone_info[1];
					LOGGER.info("svcMangNum {}", svcMangNum);

					/* uacd */
					String uaCd = phone_info[2];
					LOGGER.info("uaCd {}", uaCd);

					/* 통신사명 */
					String deviceTelecom = phone_info[3];
					LOGGER.info("deviceTelecom {}", deviceTelecom);

					/* 단말코드 조회 */
					Device device = this.mcc.getPhoneInfoByUacd(uaCd);
					String deviceModelNo = device.getDeviceModelCd();
					LOGGER.info("deviceModelCd {}", deviceModelNo);

					for (UserMbrDevice userMbrDevice : schDeviceListRes.getUserMbrDevice()) {

						/* 동일한 deviceId인 경우 수정처리 */
						if (userMbrDevice.getDeviceID().equals(deviceId)) {

							UserMbrDevice modifyDevice = new UserMbrDevice();
							modifyDevice.setDeviceModelNo(deviceModelNo);
							modifyDevice.setDeviceTelecom(deviceTelecom);
							modifyDevice.setSvcMangNum(svcMangNum);

							List<UserMbrDeviceDetail> modifyDeviceDetailList = new ArrayList<UserMbrDeviceDetail>();

							UserMbrDeviceDetail modifyDeviceDetail = new UserMbrDeviceDetail();
							modifyDeviceDetail.setUserKey(userKey);
							modifyDeviceDetail.setTenantID(map.get("tenantID"));
							modifyDeviceDetail.setExtraProfile(MemberConstants.DEVICE_EXTRA_UACD);
							modifyDeviceDetail.setExtraProfileValue(uaCd);
							modifyDeviceDetailList.add(modifyDeviceDetail);

							modifyDevice.setUserMbrDeviceDetail(modifyDeviceDetailList);

							CreateDeviceRequest createDeviceReq = new CreateDeviceRequest();
							createDeviceReq.setCommonRequest(commonRequest);
							createDeviceReq.setUserKey(userKey);
							createDeviceReq.setIsNew("N");
							createDeviceReq.setUserMbrDevice(userMbrDevice);
							this.deviceSCI.createDevice(createDeviceReq);

						}
					}

					/* user_phone에 존재하지 않은 휴대기기 정보 삭제 처리 */
					List<String> removeKeyList = new ArrayList<String>();
					for (UserMbrDevice userMbrDevice : schDeviceListRes.getUserMbrDevice()) {

						String removeYn = "Y";

						if (userMbrDevice.getDeviceID().equals(deviceId)) {
							removeYn = "N";
						}

						if (removeYn.equals("Y")) {
							removeKeyList.add(userMbrDevice.getDeviceKey());
						}

					}

					if (removeKeyList.size() > 0) {
						RemoveDeviceRequest removeDeviceReq = new RemoveDeviceRequest();
						removeDeviceReq.setUserKey(userKey);
						removeDeviceReq.setDeviceKey(removeKeyList);
					}
				}

			}

		} catch (StorePlatformException ex) {

			if (ex.getErrorInfo().getCode().equals(MemberConstants.SC_ERROR_NO_DATA)) {
				return this.FAIL_NODATA_STR;
			} else {
				return this.FAIL_STR;
			}

		}

		return this.SUCCESS_STR;
	}

	/*
	 * 
	 * <pre> 부가서비스 가입 Provisioning - CMD : ecgJoinedTStore. </pre>
	 * 
	 * @param map Request로 받은 Parameter Map
	 * 
	 * @return HashMap
	 */
	@Override
	public String ecgJoinedTStore(HashMap<String, String> map) {
		// TODO Auto-generated method stub

		// Ø 유통망 추천앱 스캐줄 저장
		//
		// - TBL_DIST_RECOM_APP_SCHEDULE
		//
		// - TBL_SEGMENT_NON_MEMBERS

		return this.SUCCESS_STR;
	}

	/*
	 * 
	 * <pre> 부가서비스 해지 Provisioning - CMD : ecgScededTStore. </pre>
	 * 
	 * @param map Request로 받은 Parameter Map
	 * 
	 * @return HashMap
	 */
	@Override
	public String ecgScededTStore(HashMap<String, String> map) {
		// TODO Auto-generated method stub
		return this.SUCCESS_STR;
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
	public ImResult rXDeleteUserIdIDP(HashMap<String, String> map) {
		ImResult imResult = new ImResult();
		String imIntSvcNo = map.get("im_int_svc_no").toString(); // 통합 서비스 번호
		String userId = map.get("user_id").toString(); // 회원 ID

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
		keySearch.setKeyType("MBR_ID");
		keySearch.setKeyString(userId); // 사용자 ID추가
		keySearchList.add(keySearch);

		searchUserRequest.setKeySearchList(keySearchList);
		SearchUserResponse searchUserResponse = null;

		try {
			searchUserResponse = this.userSCI.searchUser(searchUserRequest);
		} catch (StorePlatformException spe) { // 회원정보 조회시 오류발생시라도 프로비저닝은 성공으로 처리함.
			imResult.setResult(IdpConstants.IM_IDP_RESPONSE_SUCCESS_CODE);
			imResult.setResultText(IdpConstants.IM_IDP_RESPONSE_SUCCESS_CODE_TEXT);
			return imResult;
		}

		try {
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

		} catch (StorePlatformException spe) {

			imResult.setResult(IdpConstants.IM_IDP_RESPONSE_FAIL_CODE);
			imResult.setResultText(IdpConstants.IM_IDP_RESPONSE_FAIL_CODE_TEXT);
			return imResult;
		}

		// TO DO... 회원 탈퇴 정보를 전달 하는 TSTORE-TANENT-API가 추가되면 PARAMETER 셋팅해서 호출해야함.

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
	public ImResult rXPreCheckDeleteUserIDP(HashMap map) {
		SearchUserRequest searchUserRequest = new SearchUserRequest();
		IdpConstants idpConstant = new IdpConstants();
		ImResult imResult = new ImResult();
		MemberConstants memberConstant = new MemberConstants();

		// 회원 정보 조회
		// 공통 헤더
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setSystemID((String) map.get("systemID"));
		commonRequest.setTenantID((String) map.get("tenantID"));
		searchUserRequest.setCommonRequest(commonRequest);

		String userID = map.get("user_id").toString();

		List<KeySearch> keySearchList = new ArrayList<KeySearch>();
		KeySearch keySearch = new KeySearch();
		keySearch.setKeyType("MBR_ID");
		keySearch.setKeyString(userID);

		keySearchList.add(keySearch);
		searchUserRequest.setKeySearchList(keySearchList);

		String idpResult = idpConstant.IM_IDP_RESPONSE_FAIL_CODE;
		String idpResultText = idpConstant.IM_IDP_RESPONSE_FAIL_CODE_TEXT;
		String delYN = "N";

		try {
			SearchUserResponse searchUserRespnse = this.userSCI.searchUser(searchUserRequest);

			UserMbr getUserMbr = searchUserRespnse.getUserMbr();

			// 회원 정보 존재
			if (getUserMbr != null) {
				LOGGER.debug("rXPreCheckDeleteUserIDP ------- 회원 정보 존재");
				delYN = "Y";
				idpResult = idpConstant.IM_IDP_RESPONSE_SUCCESS_CODE;
				idpResultText = idpConstant.IM_IDP_RESPONSE_SUCCESS_CODE_TEXT;

				imResult.setCmd("RXPreCheckDeleteUserIDP");
				imResult.setResult(idpResult);
				imResult.setResultText(idpResultText);
				imResult.setImIntSvcNo(map.get("im_int_svc_no").toString());
				imResult.setUserId(userID);
				imResult.setIsCancelAble(delYN);
			}
		} catch (StorePlatformException spe) {

			if (spe.getErrorInfo().getCode().endsWith(memberConstant.RESULT_NOT_FOUND_USER_KEY)) {
				LOGGER.debug("rXPreCheckDeleteUserIDP ------- 회원 정보 없음");
				imResult.setCmd("RXPreCheckDeleteUserIDP");
				imResult.setResult(idpResult);
				imResult.setResultText(idpResultText);
				imResult.setImIntSvcNo(map.get("im_int_svc_no").toString());
				imResult.setUserId(userID);
				imResult.setIsCancelAble(delYN);
				String userPocIp = this.messageSourceAccessor.getMessage("tenantID" + (String) map.get("tenantID"),
						LocaleContextHolder.getLocale());
				String cancelUrl = this.messageSourceAccessor.getMessage("cancelUrl", LocaleContextHolder.getLocale());
				LOGGER.debug("rXPreCheckDeleteUserIDP cancelRetUrl = " + "http://" + userPocIp + cancelUrl);
				imResult.setCancelRetUrl("http://" + userPocIp + cancelUrl);
				imResult.setTermRsnCd(idpConstant.IM_IDP_RESPONSE_FAIL_MEMBERSELECT_CODE);
				imResult.setCancelEtc("(" + userID + ")" + idpConstant.IM_IDP_RESPONSE_FAIL_MEMBERSELECT_CODE_TEXT);

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
	public ImResult rXPreCheckDisagreeUserIDP(HashMap map) {
		SearchUserRequest searchUserRequest = new SearchUserRequest();
		IdpConstants idpConstant = new IdpConstants();
		ImResult imResult = new ImResult();
		MemberConstants memberConstant = new MemberConstants();

		// 회원 정보 조회
		// 공통 헤더
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setSystemID((String) map.get("systemID"));
		commonRequest.setTenantID((String) map.get("tenantID"));
		searchUserRequest.setCommonRequest(commonRequest);

		String userID = map.get("user_id").toString();

		List<KeySearch> keySearchList = new ArrayList<KeySearch>();
		KeySearch keySearch = new KeySearch();
		keySearch.setKeyType("MBR_ID");
		keySearch.setKeyString(userID);

		keySearchList.add(keySearch);
		searchUserRequest.setKeySearchList(keySearchList);

		String idpResult = idpConstant.IM_IDP_RESPONSE_FAIL_CODE;
		String idpResultText = idpConstant.IM_IDP_RESPONSE_FAIL_CODE_TEXT;
		String delYN = "N";

		try {
			SearchUserResponse searchUserRespnse = this.userSCI.searchUser(searchUserRequest);

			UserMbr getUserMbr = searchUserRespnse.getUserMbr();

			// 회원 정보 존재
			if (getUserMbr != null) {
				LOGGER.debug("RXPreCheckDisagreeUserIDP ------- 회원 정보 존재");
				delYN = "Y";
				idpResult = idpConstant.IM_IDP_RESPONSE_SUCCESS_CODE;
				idpResultText = idpConstant.IM_IDP_RESPONSE_SUCCESS_CODE_TEXT;

				imResult.setCmd("RXPreCheckDisagreeUserIDP");
				imResult.setResult(idpResult);
				imResult.setResultText(idpResultText);
				imResult.setImIntSvcNo(map.get("im_int_svc_no").toString());
				imResult.setUserId(userID);
				imResult.setIsCancelAble(delYN);
			}
		} catch (StorePlatformException spe) {

			if (spe.getErrorInfo().getCode().endsWith(memberConstant.RESULT_NOT_FOUND_USER_KEY)) {
				LOGGER.debug("RXPreCheckDisagreeUserIDP ------- 회원 정보 없음");
				imResult.setCmd("RXPreCheckDisagreeUserIDP");
				imResult.setResult(idpResult);
				imResult.setResultText(idpResultText);
				imResult.setImIntSvcNo(map.get("im_int_svc_no").toString());
				imResult.setUserId(userID);
				imResult.setIsCancelAble(delYN);
				String userPocIp = this.messageSourceAccessor.getMessage("tenantID" + (String) map.get("tenantID"),
						LocaleContextHolder.getLocale());
				String cancelUrl = this.messageSourceAccessor.getMessage("cancelUrl", LocaleContextHolder.getLocale());
				LOGGER.debug("RXPreCheckDisagreeUserIDP cancelRetUrl = " + "http://" + userPocIp + cancelUrl);
				imResult.setCancelRetUrl("http://" + userPocIp + cancelUrl);
				imResult.setTermRsnCd(idpConstant.IM_IDP_RESPONSE_FAIL_MEMBERSELECT_CODE);
				imResult.setCancelEtc("(" + userID + ")" + idpConstant.IM_IDP_RESPONSE_FAIL_MEMBERSELECT_CODE_TEXT);
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
	public ImResult rXSetOCBDisagreeIDP(HashMap map) {

		IdpConstants idpConstant = new IdpConstants();
		String idpResult = idpConstant.IM_IDP_RESPONSE_FAIL_CODE;
		String idpResultText = idpConstant.IM_IDP_RESPONSE_FAIL_CODE_TEXT;

		try {
			idpResult = idpConstant.IM_IDP_RESPONSE_SUCCESS_CODE;
			idpResultText = idpConstant.IM_IDP_RESPONSE_SUCCESS_CODE_TEXT;
		} catch (StorePlatformException spe) {
			LOGGER.debug("RXSetOCBDisagreeIDP fail to set succes as reault");
		}
		ImResult imResult = new ImResult();
		imResult.setCmd("RXSetOCBDisagreeIDP");
		imResult.setResult(idpResult);
		imResult.setResultText(idpResultText);
		imResult.setImIntSvcNo(map.get("im_int_svc_no").toString());
		imResult.setImIntSvcNo(map.get("user_id").toString());

		return imResult;
	}

	/*
	 * 
	 * <pre> 이용해지변경 프로파일 배포 - CMD : RXUpdateDisagreeUserIDP . </pre>
	 * 
	 * @param map Request 받은 Parameter Map
	 * 
	 * @return HashMap
	 */
	@Override
	public ImResult rXUpdateDisagreeUserIDP(HashMap<String, String> map) {
		ImResult imResult = new ImResult();
		String imIntSvcNo = map.get("im_int_svc_no").toString(); // 통합 서비스 번호
		String userId = map.get("user_id").toString(); // 회원 ID

		String joinSiteTotalList = "";// StringUtils.equals("", "");

		boolean siteCodeCheck = false;

		imResult.setCmd("RXUpdateDisagreeUserIDP");
		imResult.setImIntSvcNo(imIntSvcNo);
		imResult.setUserId(userId);

		// 이용해지변경 동의 사이트중 41100 Tstore가 있는경우 이용해지 변경 프로파일을 수행함기 위해 boolean변수 셋팅
		if (!"".equals(joinSiteTotalList)) {
			LOGGER.debug("====JSH====" + joinSiteTotalList);
			joinSiteTotalList = joinSiteTotalList.replaceAll(" ", "");
			LOGGER.debug("====JSH====" + joinSiteTotalList);
			// ex : 41100,null,20130923,212917,tstore000001741|90300,null,20130917,113426,null
			String[] tempSplit = joinSiteTotalList.split("\\|");
			for (int i = 0; i < tempSplit.length; i++) {
				String[] tmpSplit = tempSplit[i].split(",");
				LOGGER.debug("====JSH====" + tempSplit[i]);
				if (null != tmpSplit && tmpSplit.length >= 1 && null != tmpSplit[0]
						&& MemberConstants.SSO_SST_CD_TSTORE.equals(tmpSplit[0])) {
					siteCodeCheck = true;
					break;
				}
			}
		}

		// 이용동의 해지인 경우
		if (!siteCodeCheck) {

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
			keySearch.setKeyType("MBR_ID");
			keySearch.setKeyString(userId); // 사용자 ID추가
			keySearchList.add(keySearch);

			searchUserRequest.setKeySearchList(keySearchList);
			SearchUserResponse searchUserResponse = null;

			try {
				searchUserResponse = this.userSCI.searchUser(searchUserRequest);
			} catch (StorePlatformException spe) { // 회원정보 조회시 오류발생시라도 프로비저닝은 성공으로 처리함.
				imResult.setResult(IdpConstants.IM_IDP_RESPONSE_SUCCESS_CODE);
				imResult.setResultText(IdpConstants.IM_IDP_RESPONSE_SUCCESS_CODE_TEXT);
				return imResult;
			}

			try {
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

			} catch (StorePlatformException spe) {

				imResult.setResult(IdpConstants.IM_IDP_RESPONSE_FAIL_CODE);
				imResult.setResultText(IdpConstants.IM_IDP_RESPONSE_FAIL_CODE_TEXT);
				return imResult;
			}

			// TO DO... 회원 탈퇴 정보를 전달 하는 TSTORE-TANENT-API가 추가되면 PARAMETER 셋팅해서 호출해야함.

			imResult.setResult(IdpConstants.IM_IDP_RESPONSE_SUCCESS_CODE);
			imResult.setResultText(IdpConstants.IM_IDP_RESPONSE_SUCCESS_CODE_TEXT);
			return imResult;

		}

		imResult.setResult(IdpConstants.IM_IDP_RESPONSE_FAIL_CODE);
		imResult.setResultText(IdpConstants.IM_IDP_RESPONSE_FAIL_CODE_TEXT);
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
	public ImResult rXTerminateRetryIDP(HashMap map) {
		IdpConstants idpConstant = new IdpConstants();
		String idpResult = idpConstant.IM_IDP_RESPONSE_SUCCESS_CODE;
		String idpResultText = idpConstant.IM_IDP_RESPONSE_SUCCESS_CODE_TEXT;

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
	public ImResult rXUpdateUserPwdIDP(HashMap map) {
		// request의 변경 시간 정보 사용
		String req_date = map.get("modify_req_date").toString();
		String req_time = map.get("modify_req_time").toString();

		UpdatePasswordUserRequest updatePasswordUserRequest = new UpdatePasswordUserRequest();
		UpdatePasswordUserResponse updatePasswordUserResponse = new UpdatePasswordUserResponse();
		IdpConstants idpConstant = new IdpConstants();
		MemberConstants memberConstant = new MemberConstants();

		String idpResult = idpConstant.IM_IDP_RESPONSE_FAIL_CODE;
		String idpResultText = idpConstant.IM_IDP_RESPONSE_FAIL_CODE_TEXT;

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
				updatePasswordUserResponse = this.userSCI.updatePasswordUser(updatePasswordUserRequest);

				if (updatePasswordUserResponse.getCommonResponse().getResultCode()
						.equals(this.SC_RETURN + memberConstant.RESULT_SUCCES)) {
					idpResult = idpConstant.IM_IDP_RESPONSE_SUCCESS_CODE;
					idpResultText = idpConstant.IM_IDP_RESPONSE_SUCCESS_CODE_TEXT;
				}
			} catch (StorePlatformException spe) {
				LOGGER.debug("RXUpdateUserPwdIDP fail to set succes as reault");
			}

		} catch (StorePlatformException spe) {
			LOGGER.debug("RXUpdateUserPwdIDP fail to set succes as reault");
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
	public ImResult rXChangePWDIDP(HashMap map) {

		UpdatePasswordUserRequest updatePasswordUserRequest = new UpdatePasswordUserRequest();
		IdpConstants idpConstant = new IdpConstants();
		MemberConstants memberConstant = new MemberConstants();

		String idpResult = idpConstant.IM_IDP_RESPONSE_FAIL_CODE;
		String idpResultText = idpConstant.IM_IDP_RESPONSE_FAIL_CODE_TEXT;

		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setSystemID((String) map.get("systemID"));
		commonRequest.setTenantID((String) map.get("tenantID"));
		updatePasswordUserRequest.setCommonRequest(commonRequest);

		MbrPwd mbrPwd = new MbrPwd();
		// 현재 시간 세팅
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		LOGGER.debug("RXChangePWDIDP current time = " + dateFormat.format(calendar.getTime()));
		mbrPwd.setPwRegDate(dateFormat.format(calendar.getTime()));
		mbrPwd.setMemberID(map.get("user_id").toString());

		updatePasswordUserRequest.setMbrPwd(mbrPwd);

		try {
			UpdatePasswordUserResponse updatePasswordUserResponse = this.userSCI
					.updatePasswordUser(updatePasswordUserRequest);

			if (updatePasswordUserResponse.getCommonResponse().getResultCode()
					.equals(this.SC_RETURN + memberConstant.RESULT_SUCCES)) {
				idpResult = idpConstant.IM_IDP_RESPONSE_SUCCESS_CODE;
				idpResultText = idpConstant.IM_IDP_RESPONSE_SUCCESS_CODE_TEXT;
			}
		} catch (StorePlatformException spe) {
			idpResult = idpConstant.IM_IDP_RESPONSE_FAIL_CODE;
			idpResultText = idpConstant.IM_IDP_RESPONSE_FAIL_CODE_TEXT;
		}

		ImResult imResult = new ImResult();
		imResult.setCmd("RXChangePWDIDP");
		imResult.setResult(idpResult);
		imResult.setResultText(idpResultText);

		return imResult;
	}

	/*
	 * 
	 * <pre> 변경된 공통프로파일 배포 - CMD : rXUpdateUserInfoIDP . </pre>
	 * 
	 * @param map Request 받은 Parameter Map
	 * 
	 * @return HashMap
	 */
	@Override
	public ImResult rXUpdateUserInfoIDP(HashMap<String, String> map) {
		ImResult imResult = new ImResult();
		String imIntSvcNo = map.get("im_int_svc_no").toString(); // 통합 서비스 번호
		String userId = map.get("user_id").toString(); // 회원 ID
		String tenantID = "";
		String systemID = "";

		tenantID = map.get("tenantID").toString();
		systemID = map.get("systemID").toString();

		imResult.setCmd("rXUpdateUserInfoIDP");
		imResult.setImIntSvcNo(imIntSvcNo);
		imResult.setUserId(userId);

		// 회원정보 조회후 request 넘어온 정보를 셋팅해서 회원정보 수정
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID(tenantID);
		commonRequest.setSystemID(systemID);

		SearchUserRequest searchUserRequest = new SearchUserRequest();

		KeySearch keySearch = new KeySearch();
		keySearch.setKeyType("MBR_ID");
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
				updateUserRequest.setMbrAuth(searchUserResponse.getMbrAuth());
				UserMbr userMbr = searchUserResponse.getUserMbr();
				userMbr.setUserEmail(map.get("user_email"));
				userMbr.setUserPhoneCountry(map.get("user_tn_nation_cd"));
				userMbr.setUserPhone(map.get("user_tn").toString());
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

}
