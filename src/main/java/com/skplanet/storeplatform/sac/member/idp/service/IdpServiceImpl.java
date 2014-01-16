package com.skplanet.storeplatform.sac.member.idp.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;
import com.skplanet.storeplatform.member.client.common.vo.KeySearch;
import com.skplanet.storeplatform.member.client.common.vo.MbrOneID;
import com.skplanet.storeplatform.member.client.common.vo.UpdateMbrOneIDRequest;
import com.skplanet.storeplatform.member.client.common.vo.UpdateMbrOneIDResponse;
import com.skplanet.storeplatform.member.client.user.sci.UserSCI;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchUserRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchUserResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.UpdateStatusUserRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.UpdateStatusUserResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.UpdateUserRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.UpdateUserResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.UserMbr;
import com.skplanet.storeplatform.sac.member.common.MemberConstants;
import com.skplanet.storeplatform.sac.member.idp.constant.IdpConstants;
import com.skplanet.storeplatform.sac.member.idp.vo.ImResult;

/**
 * IDP에서 전달되는 Provisioning 및 Rx 처리를 위한 인터페이스
 * 
 * Updated on : 2014. 1. 8. Updated by : 임재호, 인크로스.
 */
@Service
public class IdpServiceImpl implements IdpService {

	private static final Logger LOGGER = LoggerFactory.getLogger(IdpServiceImpl.class);

	@Autowired
	private UserSCI userSCI;

	/*
	 * 
	 * <pre> 통합회원전환생성정보를사이트에배포 - CMD : RXCreateUserIDP </pre>
	 * 
	 * @param map Request로 받은 Parameter Map
	 * 
	 * @return One ID Rx 처리 결과
	 */
	@Override
	public ImResult rXCreateUserIDP(HashMap map) {
		System.out.println("rXCreateUserIDP ------- ");
		ImResult imResult = new ImResult();
		imResult.setResult("setResult");
		imResult.setResultText("setResultText");
		imResult.setImIntSvcNo("setImIntSvcNo");
		imResult.setCancelEtc("setCancelEtc");
		imResult.setCancelRetUrl("setCancelRetUrl");
		imResult.setCmd("setCmd");
		imResult.setIsCancelAble("setIsCancelAble");
		imResult.setTermRsnCd("setTermRsnCd");
		imResult.setUserId("setUserId");
		return imResult;
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
		if (loginStatusCode.equals(idpConstant.LOGIN_STATUS_RELEASE)) { // 로그인 가능
			updateUserVo.setUserMainStatus(memberConstant.MAIN_STATUS_NORMAL);
			updateUserVo.setUserSubStatus(memberConstant.SUB_STATUS_NORMAL);
		} else if (loginStatusCode.equals(idpConstant.LOGIN_STATUS_LOCK)) { // 로그인 제한
			updateUserVo.setUserMainStatus(memberConstant.MAIN_STATUS_PAUSE);
			updateUserVo.setUserSubStatus(memberConstant.SUB_STATUS_LOGIN_PAUSE);
		}

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

		List<KeySearch> keySearchList = new ArrayList<KeySearch>();
		KeySearch keySearch = new KeySearch();
		keySearch.setKeyType("MBR_ID");
		if (null != map.get("user_id")) {
			keySearch.setKeyString((String) map.get("user_id"));
		}
		keySearchList.add(keySearch);
		searchUserRequest.setKeySearchList(keySearchList);
		SearchUserResponse searchUserRespnse = this.userSCI.searchUser(searchUserRequest);

		String idpResult = idpConstant.IM_IDP_RESPONSE_FAIL_CODE;
		String idpResultText = idpConstant.IM_IDP_RESPONSE_FAIL_CODE_TEXT;

		// 회원 존재 여부 확인
		if (null == searchUserRespnse.getUserKey()) {
			// one id 가입 정보 등록
			UpdateMbrOneIDRequest updateMbrOneIDRequest = new UpdateMbrOneIDRequest();
			updateMbrOneIDRequest.setCommonRequest(commonRequest);
			MbrOneID mbrOneID = new MbrOneID();
			mbrOneID.setIntgMbrCaseCode((String) map.get("im_int_svc_no"));
			// 통합회원 유형 코드
			mbrOneID.setIntgSvcNumber((String) map.get("im_mem_type_cd"));
			mbrOneID.setUserID((String) map.get("user_id"));
			updateMbrOneIDRequest.setMbrOneID(mbrOneID);

			UpdateMbrOneIDResponse updateMbrOneIDResponse = this.userSCI.createAgreeSite(updateMbrOneIDRequest);

			if (updateMbrOneIDResponse.getCommonResponse().getResultCode().equals(memberConstant.RESULT_SUCCES)) { // SC반환값이
				idpResult = idpConstant.IM_IDP_RESPONSE_SUCCESS_CODE;
				idpResultText = idpConstant.IM_IDP_RESPONSE_SUCCESS_CODE_TEXT;
			}
		}

		// 회원이 존재 하지 않으면 FAIL값 return
		ImResult imResult = new ImResult();
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
		keySearch.setKeyString((String) map.get("im_int_svc_no"));

		keySearchList.add(keySearch);
		updateUserVo.setKeySearchList(keySearchList);

		String susStatusCode = (String) map.get("sus_status_code");
		if (susStatusCode.equals(idpConstant.SUS_STATUS_RELEASE)) { // 직권중지 해제
			updateUserVo.setUserMainStatus(memberConstant.MAIN_STATUS_NORMAL);
			updateUserVo.setUserSubStatus(memberConstant.SUB_STATUS_NORMAL);
		} else if (susStatusCode.equals(idpConstant.SUS_STATUS_LOCK)) { // 직권중지
			updateUserVo.setUserMainStatus(memberConstant.MAIN_STATUS_PAUSE);
			// updateUserVo.setUserSubStatus(memberConstant.SUB_STATUS_AUTHORITY_PAUSE);
		}

		UpdateStatusUserResponse updateStatusResponse = this.userSCI.updateStatus(updateUserVo);

		// 미동의 회원 정보 수정
		UpdateMbrOneIDRequest updateMbrOneIDRequest = new UpdateMbrOneIDRequest();
		updateMbrOneIDRequest.setCommonRequest(commonRequest);
		MbrOneID mbrOneID = new MbrOneID();
		mbrOneID.setLoginStatusCode(susStatusCode);
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
		imResult.setResult(idpResult);
		imResult.setResultText(idpResultText);
		imResult.setImIntSvcNo(map.get("im_int_svc_no").toString());

		return imResult;
	}

}
