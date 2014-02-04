package com.skplanet.storeplatform.sac.member.idp.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;
import com.skplanet.storeplatform.member.client.common.vo.KeySearch;
import com.skplanet.storeplatform.member.client.common.vo.MbrAuth;
import com.skplanet.storeplatform.member.client.common.vo.MbrLglAgent;
import com.skplanet.storeplatform.member.client.common.vo.MbrOneID;
import com.skplanet.storeplatform.member.client.common.vo.UpdateMbrOneIDRequest;
import com.skplanet.storeplatform.member.client.common.vo.UpdateMbrOneIDResponse;
import com.skplanet.storeplatform.member.client.user.sci.UserSCI;
import com.skplanet.storeplatform.member.client.user.sci.vo.CreateUserRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.CreateUserResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.RemoveUserRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchUserRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchUserResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.UpdateRealNameRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.UpdateRealNameResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.UpdateStatusUserRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.UpdateStatusUserResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.UpdateUserRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.UpdateUserResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.UserMbr;
import com.skplanet.storeplatform.sac.api.util.DateUtil;
import com.skplanet.storeplatform.sac.member.common.constant.MemberConstants;
import com.skplanet.storeplatform.sac.member.idp.constant.IdpConstants;
import com.skplanet.storeplatform.sac.member.idp.vo.ImResult;

/**
 * IDP에서 전달되는 Provisioning 및 Rx 처리를 위한 인터페이스
 * 
 * Updated on : 2014. 1. 8. Updated by : 임재호, 인크로스.
 */
@Service
@Transactional
public class IdpServiceImpl implements IdpService {

	private static final Logger LOGGER = LoggerFactory.getLogger(IdpServiceImpl.class);

	public String SUCCESS_STR = "100"; // SP 프로비져닝 성공
	public String FAIL_STR = "109"; // SP 프로비져닝 실패(FAIL응답 받음)
	public String FAIL_NODATA_STR = "600"; // SP 프로비져닝 실패(데이터 없음)

	@Autowired
	private UserSCI userSCI;

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
		String userId = "";

		String responseResult = "";
		String responseResultText = "";
		String responseImIntSvcNo = "";
		String responseUserId = "";

		String tenantID = map.get("tenantID").toString();
		String systemID = map.get("systemID").toString();

		// LOGGER.debug("JOIN_SST_LIST START");

		String joinSiteTotalList = map.get("join_sst_list").toString(); // 이용동의사이트정보
		// example info list : 41100,null,20130923,212917,tstore000001741|90300,null,20130917,113426,null
		// LOGGER.debug("replace before:" + joinSiteTotalList);
		joinSiteTotalList = joinSiteTotalList.replaceAll(" ", "");
		// LOGGER.debug("replace after:" + joinSiteTotalList);

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
					String[] marketingYnSplit = tmpSplit[1].split("\\^");
					for (int j = 0; j < marketingYnSplit.length; j++) {
						if (null != marketingYnSplit[j] && "TAC006".equals(marketingYnSplit[j])) {
							map.put("marketingYn", "Y");
						} else if (null != marketingYnSplit[j] && "TAC002".equals(marketingYnSplit[j])) {
							map.put("comm_charg_term_yn", "Y");
						}
					}
				}
				break;
			}
		}

		String oldId = map.get("old_id").toString();
		userId = map.get("user_id").toString();
		isParentApprove = map.get("is_parent_approve").toString();

		if ("null".equals(oldId) || "".equals(oldId)) { // 신규가입경우 기존 Tstore에 없던 회원가입요청시 전환가입 대상자중 Tstore 미가입자로 Tstore에
														// 가입이 안되어있는경우는 신규가입으로 판단
			LOGGER.debug("JOIN NEW DATA INSERT START");
			CreateUserRequest createUserRequest = new CreateUserRequest();

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
			userMbr.setUserType(map.get("user_type").toString()); // * 사용자 구분 코드
			userMbr.setUserMainStatus(MemberConstants.MAIN_STATUS_NORMAL); // 사용자 메인 상태 코드 가입시 바로 가입됨 정상
			userMbr.setUserSubStatus(MemberConstants.SUB_STATUS_NORMAL); // 사용자 서브 상태 코드 정상
			userMbr.setImSvcNo(map.get("im_int_svc_no").toString()); // 통합 서비스 관리번호 INTG_SVC_NO : 통합서비스 관리번호
			userMbr.setIsImChanged(map.get("is_im_changed").toString()); // 전환가입코드 * * - 전환가입 : Y, 신규가입 : N, 변경가입 : C,
																		 // 변경전환 : H
			userMbr.setUserID(map.get("user_id").toString()); // 사용자 ID
			userMbr.setUserPhoneCountry(map.get("user_tn_nation_cd").toString()); // 연락처 국가 코드
			userMbr.setUserPhone(map.get("user_tn").toString()); // 사용자 연락처 . --help
			userMbr.setUserEmail(map.get("user_email").toString()); // 사용자 이메일 주소
			userMbr.setUserName(map.get("user_name").toString()); // 사용자 이름
			userMbr.setUserSex(map.get("user_sex").toString()); // 사용자 성별
			userMbr.setUserBirthDay(map.get("user_birthday").toString()); // 사용자 생년월일
			userMbr.setUserCity(map.get("user_city_name").toString()); // (외국인)도시
			userMbr.setUserState(map.get("user_area_name").toString()); // (외국인)주
			userMbr.setUserCountry(map.get("user_nation_code").toString()); // 사용자 국가 코드
			// userMbr.setImSiteCode("Y"); // OneID 이용동의 사이트 정보
			userMbr.setIsRealName(map.get("is_rname_auth").toString()); // 실명인증 여부
			userMbr.setIsParent(isParentApprove); // 법정대리인 동의여부(Y/N)

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
			CreateUserResponse create = this.userSCI.create(createUserRequest); // 가입정보 등록
			LOGGER.debug("JOIN NEW DATA INSERT COMPLETE");

			if (MemberConstants.RESULT_SUCCES.equals(create.getCommonResponse().getResultCode())) {

				LOGGER.debug("JOIN ONEID DATA INSERT START");

				// ONEID에 데이터 입력
				UpdateMbrOneIDRequest updateMbrOneIDRequest = new UpdateMbrOneIDRequest();
				updateMbrOneIDRequest.setCommonRequest(commonRequest);
				MbrOneID mbrOneID = new MbrOneID();
				mbrOneID.setStopStatusCode(IdpConstants.SUS_STATUS_RELEASE); // 직권중지해제 기본셋팅
				mbrOneID.setIntgSvcNumber(map.get("im_int_svc_no"));
				mbrOneID.setUserKey(create.getUserKey()); // 신규가입때 생성된 내부사용자키를 셋팅
				mbrOneID.setUserID(userId); // userID
				updateMbrOneIDRequest.setMbrOneID(mbrOneID);

				UpdateMbrOneIDResponse updateMbrOneIDResponse = this.userSCI.createAgreeSite(updateMbrOneIDRequest);

				responseResult = updateMbrOneIDResponse.getCommonResponse().getResultCode();
				responseResultText = updateMbrOneIDResponse.getCommonResponse().getResultMessage();
				LOGGER.debug("JOIN ONEID DATA INSERT COMPLETE");
			} else { // 신규가입정보 입력시 리턴메시지가 정상이 아닌경우
				responseResult = create.getCommonResponse().getResultCode();
				responseResultText = create.getCommonResponse().getResultMessage();
				LOGGER.debug("Error Result Code :" + create.getCommonResponse().getResultCode());
				LOGGER.debug("Error Result Message :" + create.getCommonResponse().getResultMessage());
			}

			responseImIntSvcNo = map.get("im_int_svc_no").toString();
			responseUserId = map.get("user_id").toString();

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

				SearchUserResponse searchUserResponse = this.userSCI.searchUser(searchUserRequest);

				if (searchUserResponse == null) {
					keySearch.setKeyString(map.get("old_id").toString() + "@nate.com");
					keySearchList = null;
					keySearchList = new ArrayList<KeySearch>();
					keySearchList.add(keySearch);
					searchUserRequest.setKeySearchList(keySearchList);

					searchUserResponse = this.userSCI.searchUser(searchUserRequest);
				}

				UpdateUserResponse updateUserResponse = this.userSCI.updateUser(this.getUpdateUserRequest(map,
						searchUserResponse));
				LOGGER.debug("전환가입 정보 입력 완료");
				// TO DO... 전시,구매,기타에서 사용되는 회원ID, 회원USER_KEY 등을 변경할수 있는 API 호출 추가 로직 대기중...
				responseResult = updateUserResponse.getCommonResponse().getResultCode();
				responseResultText = updateUserResponse.getCommonResponse().getResultMessage();
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

				SearchUserResponse searchUserResponse = this.userSCI.searchUser(searchUserRequest);

				if (searchUserResponse == null) {
					keySearch.setKeyString(map.get("old_id").toString() + "@nate.com");
					keySearchList = null;
					keySearchList = new ArrayList<KeySearch>();
					keySearchList.add(keySearch);
					searchUserRequest.setKeySearchList(keySearchList);

					searchUserResponse = this.userSCI.searchUser(searchUserRequest);
				}

				UpdateUserResponse updateUserResponse = this.userSCI.updateUser(this.getUpdateUserRequest(map,
						searchUserResponse));
				LOGGER.debug("변경가입,변경전환 정보 입력 완료");

				// TO DO... 전시,구매,기타에서 사용되는 회원ID, 회원USER_KEY 등을 변경할수 있는 API 호출 추가 로직 대기중...
				responseResult = updateUserResponse.getCommonResponse().getResultCode();
				responseResultText = updateUserResponse.getCommonResponse().getResultMessage();
				updateUserKey = updateUserResponse.getUserKey();
			}

			responseImIntSvcNo = map.get("im_int_svc_no").toString();
			responseUserId = map.get("user_id").toString();

			LOGGER.debug("ONEID DATA UPDATE START");

			if (responseResult.equals(MemberConstants.RESULT_SUCCES)) {
				// ONEID에 데이터 입력
				UpdateMbrOneIDRequest updateMbrOneIDRequest = new UpdateMbrOneIDRequest();
				updateMbrOneIDRequest.setCommonRequest(commonRequest);
				MbrOneID mbrOneID = new MbrOneID();
				mbrOneID.setStopStatusCode(IdpConstants.SUS_STATUS_RELEASE); // 직권중지해제 기본셋팅
				mbrOneID.setIntgSvcNumber(map.get("im_int_svc_no"));
				mbrOneID.setUserKey(updateUserKey); // 내부사용자키를 셋팅
				mbrOneID.setUserID(userId); // 사용자 ID 셋팅
				updateMbrOneIDRequest.setMbrOneID(mbrOneID);

				UpdateMbrOneIDResponse updateMbrOneIDResponse = this.userSCI.createAgreeSite(updateMbrOneIDRequest);

				responseResult = updateMbrOneIDResponse.getCommonResponse().getResultCode();
				responseResultText = updateMbrOneIDResponse.getCommonResponse().getResultMessage();
			} else {
				LOGGER.debug("Error Result Code :" + responseResult);
				LOGGER.debug("Error Result Message :" + responseResultText);
			}
			LOGGER.debug("ONEID DATA UPDATE COMPLETE");
		}

		ImResult imResult = new ImResult();
		imResult.setCmd("RXCreateUserIDP");
		if (responseResult.equals(MemberConstants.RESULT_SUCCES)) {
			imResult.setResult(IdpConstants.IM_IDP_RESPONSE_SUCCESS_CODE);
			imResult.setResultText(IdpConstants.IM_IDP_RESPONSE_SUCCESS_CODE_TEXT);
			imResult.setImIntSvcNo(responseImIntSvcNo);
			imResult.setUserId(responseUserId);
		} else {
			imResult.setResult(IdpConstants.IM_IDP_RESPONSE_FAIL_CODE);
			imResult.setResultText(IdpConstants.IM_IDP_RESPONSE_FAIL_CODE_TEXT);
			imResult.setImIntSvcNo(responseImIntSvcNo);
			imResult.setUserId(responseUserId);
		}

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
		getUserMbr.setUserType(hashMap.get("user_type").toString()); // 사용자 구분 코드
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
		getUserMbr.setUserCity(hashMap.get("user_city_name").toString()); // (외국인)도시
		getUserMbr.setUserState(hashMap.get("user_area_name").toString()); // (외국인)주
		getUserMbr.setUserCountry(hashMap.get("user_nation_code").toString()); // 사용자 국가 코드
		getUserMbr.setImSiteCode(hashMap.get("join_sst_list").toString()); // OneID 이용동의 사이트 정보
		getUserMbr.setIsParent(hashMap.get("is_parent_approve").toString()); // 법정대리인 동의여부(Y/N)

		// 실명인증정보
		getMbrAuth.setIsRealName(hashMap.get("is_rname_auth").toString()); // 실명 인증 여부

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
	 * <pre> 회선 변경 정보 Provisioning (무선, 통합 회원) - CMD : changeMobileNumber </pre>
	 * 
	 * @param map Request로 받은 Parameter Map
	 * 
	 * @return IDP Provisioning 처리 결과
	 */
	@Override
	public String changeMobileNumber(HashMap map) {
		System.out.println("changeMobileNumber ------- ");
		return "0000";
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
		SearchUserResponse searchUserRespnse = this.userSCI.searchUser(searchUserRequest);

		UserMbr userMbr = new UserMbr();

		// 회원 수정 정보 세팅
		UserMbr getUserMbr = new UserMbr();
		getUserMbr = searchUserRespnse.getUserMbr();

		String idpResult = idpConstant.IM_IDP_RESPONSE_FAIL_CODE;
		String idpResultText = idpConstant.IM_IDP_RESPONSE_FAIL_CODE_TEXT;

		if (getUserMbr != null) {
			getUserMbr.setUserPhone((String) map.get("user_tn"));
			userVo.setUserMbr(getUserMbr);
			userVo.setCommonRequest(commonRequest);

			// 회원 정보 수정 API call
			UpdateUserResponse updateUserResponse = this.userSCI.updateUser(userVo);

			idpResult = idpConstant.IM_IDP_RESPONSE_SUCCESS_CODE;
			idpResultText = idpConstant.IM_IDP_RESPONSE_SUCCESS_CODE_TEXT;
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

		UpdateStatusUserResponse updateStatusResponse = this.userSCI.updateStatus(updateUserVo);

		// 미동의 회원 정보 수정
		UpdateMbrOneIDRequest updateMbrOneIDRequest = new UpdateMbrOneIDRequest();
		updateMbrOneIDRequest.setCommonRequest(commonRequest);
		MbrOneID mbrOneID = new MbrOneID();
		mbrOneID.setLoginStatusCode(loginStatusCode);
		mbrOneID.setIntgSvcNumber((String) map.get("im_int_svc_no"));
		updateMbrOneIDRequest.setMbrOneID(mbrOneID);

		UpdateMbrOneIDResponse updateMbrOneIDResponse = this.userSCI.createAgreeSite(updateMbrOneIDRequest);

		// 결과값 세팅
		String idpResult = idpConstant.IM_IDP_RESPONSE_FAIL_CODE;
		String idpResultText = idpConstant.IM_IDP_RESPONSE_FAIL_CODE_TEXT;

		if (updateStatusResponse.getCommonResponse().getResultCode().equals(memberConstant.RESULT_SUCCES)
				&& updateMbrOneIDResponse.getCommonResponse().getResultCode().equals(memberConstant.RESULT_SUCCES)) { // SC반환값이
																													  // 성공이면
			idpResult = idpConstant.IM_IDP_RESPONSE_SUCCESS_CODE;
			idpResultText = idpConstant.IM_IDP_RESPONSE_SUCCESS_CODE_TEXT;
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
		UpdateUserRequest userVo = new UpdateUserRequest();
		IdpConstants idpConstant = new IdpConstants();
		MemberConstants memberConstant = new MemberConstants();

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
		SearchUserResponse searchUserRespnse = this.userSCI.searchUser(searchUserRequest);

		LOGGER.debug("rXCreateUserIdIDP ------- searchUser");

		String idpResult = idpConstant.IM_IDP_RESPONSE_FAIL_CODE;
		String idpResultText = idpConstant.IM_IDP_RESPONSE_FAIL_CODE_TEXT;

		// 회원 존재 여부 확인
		if (null == searchUserRespnse.getUserKey()) {
			// one id 가입 정보 등록
			UpdateMbrOneIDRequest updateMbrOneIDRequest = new UpdateMbrOneIDRequest();
			updateMbrOneIDRequest.setCommonRequest(commonRequest);
			MbrOneID mbrOneID = new MbrOneID();
			mbrOneID.setIntgSvcNumber((String) map.get("im_int_svc_no"));
			// 통합회원 유형 코드
			mbrOneID.setIntgMbrCaseCode((String) map.get("im_mem_type_cd"));
			mbrOneID.setUserID((String) map.get("user_id"));
			updateMbrOneIDRequest.setMbrOneID(mbrOneID);

			UpdateMbrOneIDResponse updateMbrOneIDResponse = this.userSCI.createAgreeSite(updateMbrOneIDRequest);

			if (updateMbrOneIDResponse.getCommonResponse().getResultCode().equals(memberConstant.RESULT_SUCCES)) { // SC반환값이
				idpResult = idpConstant.IM_IDP_RESPONSE_SUCCESS_CODE;
				idpResultText = idpConstant.IM_IDP_RESPONSE_SUCCESS_CODE_TEXT;
			}
		}

		LOGGER.debug("rXCreateUserIdIDP ------- return value setting");
		// 회원이 존재 하지 않으면 FAIL값 return
		ImResult imResult = new ImResult();
		imResult.setCmd("RXCreateUserIdIDP");
		imResult.setResult(idpResult);
		imResult.setResultText(idpResultText);
		imResult.setImIntSvcNo(map.get("im_int_svc_no").toString());

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

		UpdateStatusUserResponse updateStatusResponse = this.userSCI.updateStatus(updateUserVo);

		// 미동의 회원 정보 수정
		UpdateMbrOneIDRequest updateMbrOneIDRequest = new UpdateMbrOneIDRequest();
		updateMbrOneIDRequest.setCommonRequest(commonRequest);
		MbrOneID mbrOneID = new MbrOneID();
		mbrOneID.setStopStatusCode(susStatusCode);
		mbrOneID.setIntgSvcNumber((String) map.get("im_int_svc_no"));
		updateMbrOneIDRequest.setMbrOneID(mbrOneID);

		UpdateMbrOneIDResponse updateMbrOneIDResponse = this.userSCI.createAgreeSite(updateMbrOneIDRequest);

		// 결과값 세팅
		String idpResult = idpConstant.IM_IDP_RESPONSE_FAIL_CODE;
		String idpResultText = idpConstant.IM_IDP_RESPONSE_FAIL_CODE_TEXT;

		if (updateStatusResponse.getCommonResponse().getResultCode().equals(memberConstant.RESULT_SUCCES)
				&& updateMbrOneIDResponse.getCommonResponse().getResultCode().equals(memberConstant.RESULT_SUCCES)) { // SC반환값이
																													  // 성공이면
			idpResult = idpConstant.IM_IDP_RESPONSE_SUCCESS_CODE;
			idpResultText = idpConstant.IM_IDP_RESPONSE_SUCCESS_CODE_TEXT;
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
		SearchUserResponse searchUserRespnse = this.userSCI.searchUser(searchUserRequest);

		if (searchUserRespnse.getUserMbr() != null) {

			// 실명인증 대상 본인/법정대리인 여부
			// 본인
			updateRealNameRequest.setIsOwn("OWN");
			// updateRealNameRequest.setIsRealName(map.get("is_rname_auth").toString());
			updateRealNameRequest.setUserKey(searchUserRespnse.getUserMbr().getUserKey());

			MbrAuth mbrAuth = new MbrAuth();
			mbrAuth.setBirthDay(map.get("user_birthday").toString());
			mbrAuth.setCi(map.get("user_ci").toString());
			mbrAuth.setDi(map.get("user_di").toString());
			// mbrAuth.setIsRealName(map.get("is_rname_auth").toString());
			mbrAuth.setRealNameSite(map.get("rname_auth_sst_code").toString());
			mbrAuth.setRealNameDate(map.get("rname_auth_date").toString());
			mbrAuth.setMemberCategory(searchUserRespnse.getMbrAuth().getMemberCategory());
			mbrAuth.setTelecom(searchUserRespnse.getMbrAuth().getTelecom());
			mbrAuth.setPhone(searchUserRespnse.getMbrAuth().getPhone());
			mbrAuth.setSex(map.get("user_sex").toString());
			mbrAuth.setName(map.get("user_name").toString());
			mbrAuth.setMemberKey(searchUserRespnse.getMbrAuth().getMemberKey());
			mbrAuth.setRealNameMethod(map.get("rname_auth_mns_code").toString());

			updateRealNameRequest.setUserMbrAuth(mbrAuth);

			UpdateRealNameResponse updateRealNameResponse = this.userSCI.updateRealName(updateRealNameRequest);
			LOGGER.info("response param : {}", updateRealNameResponse.getUserKey());
			if (updateRealNameResponse.getCommonResponse().getResultCode().equals(memberConstant.RESULT_SUCCES)) {
				idpResult = idpConstant.IM_IDP_RESPONSE_SUCCESS_CODE;
				idpResultText = idpConstant.IM_IDP_RESPONSE_SUCCESS_CODE_TEXT;
			}
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
			mbrLglAgent.setParentRealNameSite(map.get("parent_approve_sst_code").toString());
			if (map.get("parent_approve_date").toString().length() == 8) {
				mbrLglAgent.setParentRealNameDate(map.get("parent_approve_date").toString() + "000000");
			} else if (map.get("parent_approve_date").toString().length() > 8) {
				mbrLglAgent.setParentRealNameDate(map.get("parent_approve_date").toString());
			}
			mbrLglAgent.setParentName(map.get("parent_name").toString());
			mbrLglAgent.setParentType(map.get("parent_type").toString());
			mbrLglAgent.setParentRealNameMethod(map.get("parent_rname_auth_type").toString());
			mbrLglAgent.setParentEmail(map.get("parent_email").toString());

			updateRealNameRequest.setMbrLglAgent(mbrLglAgent);

			UpdateRealNameResponse updateRealNameResponse = this.userSCI.updateRealName(updateRealNameRequest);
			LOGGER.info("response param : {}", updateRealNameResponse.getUserKey());
			if (updateRealNameResponse.getCommonResponse().getResultCode().equals(memberConstant.RESULT_SUCCES)) {
				idpResult = idpConstant.IM_IDP_RESPONSE_SUCCESS_CODE;
				idpResultText = idpConstant.IM_IDP_RESPONSE_SUCCESS_CODE_TEXT;
			}
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

		String responseResult = "";
		String responseResultText = "";
		String responseImIntSvcNo = "";
		String responseUserId = "";

		responseImIntSvcNo = map.get("im_int_svc_no").toString();
		responseUserId = map.get("user_id").toString();

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
		keySearch.setKeyString(responseUserId); // 사용자 ID추가
		keySearchList.add(keySearch);

		searchUserRequest.setKeySearchList(keySearchList);

		SearchUserResponse searchUserResponse = this.userSCI.searchUser(searchUserRequest);

		if (searchUserResponse.getUserMbr() != null) { // 통합서비스 번호로 조회한 회원정보가 있을경우만 로직처리

			// 조회되어진 사용자의 상태값이 정상이 아닌경우에 사용자 상태변경 정상 상태로 TB_US_USERMBR 데이터를 수정함
			if (!MemberConstants.MAIN_STATUS_NORMAL.equals(searchUserResponse.getUserMbr().getUserMainStatus())
					|| !MemberConstants.SUB_STATUS_NORMAL.equals(searchUserResponse.getUserMbr().getUserSubStatus())) {
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

				UpdateStatusUserResponse updateStatusUserResponse = this.userSCI.updateStatus(updateStatusUserRequest);
				responseResult = updateStatusUserResponse.getCommonResponse().getResultCode();

				if (responseResult.equals(MemberConstants.RESULT_SUCCES)) {
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

					UpdateMbrOneIDResponse updateMbrOneIDResponse = this.userSCI.createAgreeSite(updateMbrOneIDRequest);

					responseResult = updateMbrOneIDResponse.getCommonResponse().getResultCode();
					responseResultText = updateMbrOneIDResponse.getCommonResponse().getResultMessage();
					LOGGER.debug("ONEID DATA MERGE COMPLETE");
				} else {
					responseResult = MemberConstants.RESULT_FAIL;
				}

				if (responseResult.equals(MemberConstants.RESULT_SUCCES)) {
					responseResult = IdpConstants.IM_IDP_RESPONSE_SUCCESS_CODE;
					responseResultText = IdpConstants.IM_IDP_RESPONSE_SUCCESS_CODE_TEXT;
				} else {
					responseResult = IdpConstants.IM_IDP_RESPONSE_FAIL_CODE;
					responseResultText = IdpConstants.IM_IDP_RESPONSE_FAIL_CODE_TEXT;
				}
			}

			// TO DO ... 이하 API 호출구현되면 로직 처리
			// 이메일 인증시 모바일 인증여부 Y 업데이트
			// 가입 아이디에 신규 단말 추가시 캐쉬 지급 처리 핸드폰 디바이스 관련
			// 기존 모바일회원과 웹아이디회원 실명인증 정보처리
			// DEVICE관련 & 구매내역이관 모바일 회원 탈퇴처리

		} else { // 회원정보가 없어도 성공으로 처리
			responseResult = IdpConstants.IM_IDP_RESPONSE_SUCCESS_CODE;
			responseResultText = IdpConstants.IM_IDP_RESPONSE_SUCCESS_CODE_TEXT;
		}

		ImResult imResult = new ImResult();
		imResult.setCmd("RXActivateUserIdIDP");
		imResult.setResult(responseResult);
		imResult.setResultText(responseResultText);
		imResult.setImIntSvcNo(responseImIntSvcNo);
		imResult.setUserId(responseUserId);
		LOGGER.debug("rXActivateUserIdIDP ------- End");
		return imResult;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.member.idp.service.IdpService#joinComplete (java.util.HashMap)
	 */
	@Override
	public String joinComplete(HashMap map) {

		String mbrNo = map.get("mbrNo").toString();

		UpdateStatusUserRequest updStatusUserReq = new UpdateStatusUserRequest();
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID(map.get("tenantID").toString());
		commonRequest.setSystemID(map.get("systemID").toString());

		List<KeySearch> keySearchList = new ArrayList<KeySearch>();
		KeySearch key = new KeySearch();
		key.setKeyType(MemberConstants.KEY_TYPE_USERMBR_NO);
		key.setKeyString(mbrNo);
		keySearchList.add(key);

		updStatusUserReq.setCommonRequest(commonRequest);
		updStatusUserReq.setKeySearchList(keySearchList);
		updStatusUserReq.setUserMainStatus(MemberConstants.MAIN_STATUS_SECEDE);
		updStatusUserReq.setUserSubStatus(MemberConstants.SUB_STATUS_JOIN_APPLY_ETC);

		UpdateStatusUserResponse updStatusUserRes = this.userSCI.updateStatus(updStatusUserReq);
		if (!StringUtils.equals(updStatusUserRes.getCommonResponse().getResultCode(), MemberConstants.RESULT_SUCCES)) {
			throw new StorePlatformException(updStatusUserRes.getCommonResponse().getResultCode(), updStatusUserRes
					.getCommonResponse().getResultMessage());
		}

		return this.SUCCESS_STR;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.member.idp.service.IdpService# adjustWiredProfile(java.util.HashMap)
	 */
	@Override
	public String adjustWiredProfile(HashMap map) {
		// TODO Auto-generated method stub
		return this.SUCCESS_STR;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.member.idp.service.IdpService#ecgJoinedTStore (java.util.HashMap)
	 */
	@Override
	public String ecgJoinedTStore(HashMap map) {
		// TODO Auto-generated method stub
		return this.SUCCESS_STR;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.member.idp.service.IdpService#ecgScededTStore (java.util.HashMap)
	 */
	@Override
	public String ecgScededTStore(HashMap map) {
		// TODO Auto-generated method stub
		return this.SUCCESS_STR;
	}

	/*
	 * 
	 * <pre> 전체서비스사이트에해지배포 - CMD : RXDeleteUserIdIDP . </pre>
	 * 
	 * @param map Request로 받은 Parameter Map
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
			this.userSCI.remove(removeUserRequest);

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
		UpdateUserRequest userVo = new UpdateUserRequest();
		IdpConstants idpConstant = new IdpConstants();

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
		SearchUserResponse searchUserRespnse = this.userSCI.searchUser(searchUserRequest);

		UserMbr getUserMbr = new UserMbr();
		getUserMbr = searchUserRespnse.getUserMbr();

		String idpResult = idpConstant.IM_IDP_RESPONSE_FAIL_CODE;
		String idpResultText = idpConstant.IM_IDP_RESPONSE_FAIL_CODE_TEXT;
		String delYN = "N";
		ImResult imResult = new ImResult();

		// 회원 정보 존재
		if (getUserMbr != null) {
			delYN = "Y";
			idpResult = idpConstant.IM_IDP_RESPONSE_SUCCESS_CODE;
			idpResultText = idpConstant.IM_IDP_RESPONSE_SUCCESS_CODE_TEXT;

			imResult.setCmd("RXPreCheckDeleteUserIDP");
			imResult.setResult(idpResult);
			imResult.setResultText(idpResultText);
			imResult.setImIntSvcNo(map.get("im_int_svc_no").toString());
			imResult.setUserId(userID);
			imResult.setIsCancelAble(delYN);
		} else {
			imResult.setCmd("RXPreCheckDeleteUserIDP");
			imResult.setResult(idpResult);
			imResult.setResultText(idpResultText);
			imResult.setImIntSvcNo(map.get("im_int_svc_no").toString());
			imResult.setUserId(userID);
			imResult.setIsCancelAble(delYN);
			// imResult.setCancelRetUrl("http://"+Constants.USER_POC_IP +"/userpoc/mypage/goMemberSecessionSSO.omp");
			imResult.setTermRsnCd(idpConstant.IM_IDP_RESPONSE_FAIL_MEMBERSELECT_CODE);
			imResult.setCancelEtc("(" + userID + ")" + idpConstant.IM_IDP_RESPONSE_FAIL_MEMBERSELECT_CODE_TEXT);

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
		UpdateUserRequest userVo = new UpdateUserRequest();
		IdpConstants idpConstant = new IdpConstants();

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
		SearchUserResponse searchUserRespnse = this.userSCI.searchUser(searchUserRequest);

		UserMbr getUserMbr = new UserMbr();
		getUserMbr = searchUserRespnse.getUserMbr();

		String idpResult = idpConstant.IM_IDP_RESPONSE_FAIL_CODE;
		String idpResultText = idpConstant.IM_IDP_RESPONSE_FAIL_CODE_TEXT;
		String delYN = "N";
		ImResult imResult = new ImResult();

		// 회원 정보 존재
		if (getUserMbr != null) {
			delYN = "Y";
			idpResult = idpConstant.IM_IDP_RESPONSE_SUCCESS_CODE;
			idpResultText = idpConstant.IM_IDP_RESPONSE_SUCCESS_CODE_TEXT;

			imResult.setCmd("RXPreCheckDeleteUserIDP");
			imResult.setResult(idpResult);
			imResult.setResultText(idpResultText);
			imResult.setImIntSvcNo(map.get("im_int_svc_no").toString());
			imResult.setUserId(userID);
			imResult.setIsCancelAble(delYN);
		} else {
			imResult.setCmd("RXPreCheckDeleteUserIDP");
			imResult.setResult(idpResult);
			imResult.setResultText(idpResultText);
			imResult.setImIntSvcNo(map.get("im_int_svc_no").toString());
			imResult.setUserId(userID);
			imResult.setIsCancelAble(delYN);
			// imResult.setCancelRetUrl("http://"+Constants.USER_POC_IP +"/userpoc/mypage/goMemberSecessionSSO.omp");
			imResult.setTermRsnCd(idpConstant.IM_IDP_RESPONSE_FAIL_MEMBERSELECT_CODE);
			imResult.setCancelEtc("(" + userID + ")" + idpConstant.IM_IDP_RESPONSE_FAIL_MEMBERSELECT_CODE_TEXT);

		}
		return imResult;
	}
}
