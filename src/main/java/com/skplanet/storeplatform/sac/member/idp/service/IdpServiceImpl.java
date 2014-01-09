package com.skplanet.storeplatform.sac.member.idp.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;
import com.skplanet.storeplatform.member.client.common.vo.KeySearch;
import com.skplanet.storeplatform.member.client.user.sci.UserSCI;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchUserRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchUserResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.UpdateStatusUserRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.UpdateUserRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.UpdateUserResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.UserMbr;
import com.skplanet.storeplatform.sac.member.common.idp.vo.ImIDPSenderM;
import com.skplanet.storeplatform.sac.member.idp.constant.IdpConstants;
import com.skplanet.storeplatform.sac.member.idp.vo.ImResult;

/**
 * IDP에서 전달되는 Provisioning 및 Rx 처리를 위한 인터페이스
 * 
 * Updated on : 2014. 1. 8. Updated by : 임재호, 인크로스.
 */
@Service
public class IdpServiceImpl implements IdpService {

	@Autowired
	private UserSCI userSCI;

	/**
	 * 
	 * <pre>
	 * 통합회원전환생성정보를사이트에배포 
	 * - CMD : RXCreateUserIDP
	 * </pre>
	 * 
	 * @param map
	 *            Request로 받은 Parameter Map
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

	/**
	 * 
	 * <pre>
	 * 회선 변경 정보 Provisioning (무선, 통합 회원)
	 * - CMD : changeMobileNumber
	 * </pre>
	 * 
	 * @param map
	 *            Request로 받은 Parameter Map
	 * @return IDP Provisioning 처리 결과
	 */
	@Override
	public String changeMobileNumber(HashMap map) {
		System.out.println("changeMobileNumber ------- ");
		return "0000";
	}

	/**
	 * 
	 * <pre>
	 * 휴대폰소유변경정보배포
	 * - CMD : RXInvalidUserTelNoIDP
	 * </pre>
	 * 
	 * @param map
	 *            Request로 받은 Parameter Map
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
		commonRequest.setSystemID("S001");
		commonRequest.setTenantID("S01");
		searchUserRequest.setCommonRequest(commonRequest);

		List<KeySearch> keySearchList = new ArrayList<KeySearch>();
		KeySearch keySearch = new KeySearch();
		keySearch.setKeyType("MBR_ID");
		if (null != map.get("user_id"))
			keySearch.setKeyString((String) map.get("user_id"));

		keySearchList.add(0, keySearch);
		searchUserRequest.setKeySearchList(keySearchList);
		SearchUserResponse searchUserRespnse = this.userSCI.searchUser(searchUserRequest);

		UserMbr userMbr = new UserMbr();

		// 회원 수정 정보 세팅
		if (null != map.get("user_tn"))
			userMbr.setUserPhone((String) map.get("user_tn"));
		userMbr.setUserKey(searchUserRespnse.getUserKey());
		UserMbr getUserMbr = new UserMbr();
		getUserMbr = searchUserRespnse.getUserMbr();
		if (getUserMbr != null) {
			userMbr.setUserMainStatus(getUserMbr.getUserMainStatus());
			userMbr.setUserSubStatus(getUserMbr.getUserSubStatus());
		}

		userVo.setUserMbr(userMbr);
		userVo.setCommonRequest(commonRequest);
		UpdateUserResponse updateUserResponse = this.userSCI.updateUser(userVo);

		ImResult imResult = new ImResult();

		String idpResult = idpConstant.IM_IDP_RESPONSE_SUCCESS_CODE;
		String idpResultText = idpConstant.IM_IDP_RESPONSE_SUCCESS_CODE_TEXT;

		imResult.setResult(idpResult);
		imResult.setResultText(idpResultText);
		imResult.setImIntSvcNo(map.get("im_int_svc_no").toString());
		imResult.setUserId(map.get("user_id").toString());

		return imResult;
	}

	/**
	 * 
	 * <pre>
	 * 로그인 상태정보배포
	 * - CMD : RXSetLoginConditionIDP
	 * </pre>
	 * 
	 * @param map
	 *            Request로 받은 Parameter Map
	 * @return HashMap
	 */
	@Override
	public ImResult rXSetLoginConditionIDP(HashMap map) {

		ImIDPSenderM imIdpSenderM = new ImIDPSenderM();

		// Service Method이름은 Provisioning 및 Rx 기능의 'cmd' 값과 동일 해야 함.
		if (null != map.get("im_int_svc_no"))
			imIdpSenderM.setIm_int_svc_no((String) map.get("im_int_svc_no"));
		if (null != map.get("login_status_code"))
			imIdpSenderM.setLogin_status_code((String) map.get("login_status_code"));
		if (null != map.get("login_limit_sst_code"))
			imIdpSenderM.setLogin_limit_sst_code((String) map.get("login_limit_sst_code"));
		if (null != map.get("modify_sst_code"))
			imIdpSenderM.setModify_sst_code((String) map.get("modify_sst_code"));
		if (null != map.get("modify_req_date"))
			imIdpSenderM.setModify_req_date((String) map.get("modify_req_date"));
		if (null != map.get("modify_req_time"))
			imIdpSenderM.setModify_req_time((String) map.get("modify_req_time"));

		HashMap resultMap = new HashMap();

		UpdateStatusUserRequest updateUserVo = new UpdateStatusUserRequest();
		// updateUserVo.setUserID(userID)
		this.userSCI.updateStatus(updateUserVo);

		return null;
	}
}
