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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.external.client.idp.vo.IDPReceiverM;
import com.skplanet.storeplatform.external.client.idp.vo.ImIDPReceiverM;
import com.skplanet.storeplatform.external.client.uaps.sci.UapsSCI;
import com.skplanet.storeplatform.framework.core.util.StringUtil;
import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;
import com.skplanet.storeplatform.member.client.common.vo.KeySearch;
import com.skplanet.storeplatform.member.client.user.sci.DeviceSCI;
import com.skplanet.storeplatform.member.client.user.sci.UserSCI;
import com.skplanet.storeplatform.member.client.user.sci.vo.RemoveUserRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.RemoveUserResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchUserRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchUserResponse;
import com.skplanet.storeplatform.sac.client.member.vo.user.WithdrawReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.WithdrawRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.member.common.MemberCommonComponent;
import com.skplanet.storeplatform.sac.member.common.MemberConstants;
import com.skplanet.storeplatform.sac.member.common.idp.constants.IDPConstants;
import com.skplanet.storeplatform.sac.member.common.idp.constants.ImIDPConstants;
import com.skplanet.storeplatform.sac.member.common.idp.repository.IDPRepository;
import com.skplanet.storeplatform.sac.member.common.idp.service.IDPService;
import com.skplanet.storeplatform.sac.member.common.idp.service.ImIDPService;

/**
 * 휴대기기 관련 인터페이스 구현체
 * 
 * Updated on : 2014. 1. 6. Updated by : 반범진, 지티소프트.
 */
@Service
@Transactional
public class UserWithdrawServiceImpl implements UserWithdrawService {

	private static final Logger logger = LoggerFactory.getLogger(UserWithdrawServiceImpl.class);

	private static CommonRequest commonRequest;

	static {
		commonRequest = new CommonRequest();
	}

	@Autowired
	MemberCommonComponent commService; // 회원 공통 서비스

	@Autowired
	private UserSCI userSCI; // 회원 콤포넌트 사용자 기능 인터페이스

	@Autowired
	private DeviceSCI deviceSCI; // 회원 콤포넌트 휴대기기 기능 인터페이스

	@Autowired
	private IDPService idpService; // IDP 연동 클래스

	@Autowired
	private ImIDPService imIdpService; // 통합 IDP 연동 클래스

	@Autowired
	private IDPRepository idpRepository;

	@Autowired
	private UapsSCI uapsSCI;

	@Value("#{propertiesForSac['idp.im.request.operation']}")
	public String IDP_OPERATION_MODE;

	/**
	 * 
	 * 회원탈퇴
	 * 
	 * @param
	 * @return
	 */
	@Override
	public WithdrawRes withdraw(SacRequestHeader requestHeader, WithdrawReq req) throws Exception {
		WithdrawRes withdrawRes = new WithdrawRes();

		String userAuthKey = req.getUserAuthKey();
		String userId = req.getUserId();
		String deviceId = req.getDeviceId();

		/* 헤더 정보 셋팅 */
		commonRequest.setSystemID(requestHeader.getTenantHeader().getSystemId());
		commonRequest.setTenantID(requestHeader.getTenantHeader().getTenantId());

		/* SC 회원 정보 조회 Request Setting */
		SearchUserRequest schUserReq = new SearchUserRequest();
		SearchUserResponse schUserRes = new SearchUserResponse();
		schUserReq.setCommonRequest(commonRequest);
		List<KeySearch> keySearchList = new ArrayList<KeySearch>();
		KeySearch key = new KeySearch();

		/* userId & userAuthKey || userId 로 회원정보 조회 */
		if (!userId.equals("") && !userAuthKey.equals("")) {
			key.setKeyType(MemberConstants.KEY_TYPE_MBR_ID);
			key.setKeyString(userId);
			keySearchList.add(key);
			schUserReq.setKeySearchList(keySearchList);
			schUserRes = this.userSCI.searchUser(schUserReq);
		} else if (!deviceId.equals("")) {
			key.setKeyType(MemberConstants.KEY_TYPE_DEVICE_ID);
			key.setKeyString(deviceId);
			keySearchList.add(key);
			schUserReq.setKeySearchList(keySearchList);
			schUserRes = this.userSCI.searchUser(schUserReq);
		} else {
			throw new RuntimeException("파라미터 없음 userId, userAuthKey, deviceId");
		}

		// SC 컴포넌트에서 성공이 아닐때
		if (!StringUtil.equals(schUserRes.getCommonResponse().getResultCode(), MemberConstants.RESULT_SUCCES)) {
			throw new RuntimeException("[ SC Component 회원조회실패 : " + schUserRes.getCommonResponse().getResultCode()
					+ "] " + schUserRes.getCommonResponse().getResultMessage());
		}

		if (schUserRes.getUserMbr() == null) {
			throw new RuntimeException("회원정보 없음.");
		}

		/* IMIDP 회원탈퇴 연동 */
		IDPReceiverM idpReceiver = null;
		ImIDPReceiverM imIdpReceiver = null;

		if (schUserRes.getUserMbr().getImSvcNo() != null) {
			// 가입여부 체크
			// imIdpReceiver = this.imIdpService.checkIdStatusIdpIm(schUserRes.getUserMbr().getUserID());
			// 회원여부 조회
			imIdpReceiver = this.imIdpService.userInfoIdpSearchServer(schUserRes.getUserMbr().getImSvcNo());

			if (!StringUtil.equals(imIdpReceiver.getResponseHeader().getResult(), ImIDPConstants.IDP_RES_CODE_OK)) {
				throw new RuntimeException("[ ImIDP 가입여부 체크 : " + imIdpReceiver.getResponseHeader().getResult() + "] "
						+ imIdpReceiver.getResponseHeader().getResult_text());
			} else {
				// 통합회원 OneId 사용자
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("key", schUserRes.getUserMbr().getUserKey());
				param.put("user_auth_kery", req.getUserAuthKey());
				// param.put("term_reason_cd", "1"); // 1=IM통합서비스번호, 2=IM통합ID

				imIdpReceiver = this.imIdpService.discardUser(param);

				if (!StringUtil.equals(imIdpReceiver.getResponseHeader().getResult(), ImIDPConstants.IDP_RES_CODE_OK)) {
					throw new RuntimeException("[ ImIDP 회원해지 실패 : " + imIdpReceiver.getResponseHeader().getResult()
							+ "] " + imIdpReceiver.getResponseHeader().getResult_text());
				}
			}

		}

		else if (schUserRes.getUserMbr().getImSvcNo() == null
				&& schUserRes.getUserMbr().getUserType().equals(MemberConstants.USER_TYPE_MOBILE)) {
			// 모바일 인증
			idpReceiver = this.idpService.authForWap(deviceId);
			if (StringUtil.equals(idpReceiver.getResponseHeader().getResult(), IDPConstants.IDP_RES_CODE_OK)) {
				// 인증 OK --> 모바일 해지
				idpReceiver = this.idpService.secedeUser4Wap(deviceId);
				if (!StringUtil.equals(idpReceiver.getResponseHeader().getResult(), IDPConstants.IDP_RES_CODE_OK)) {
					throw new RuntimeException("IDP 모바일 회원해지 실패 : [" + deviceId + "] result code : ["
							+ idpReceiver.getResponseHeader().getResult() + "] + result message : ["
							+ idpReceiver.getResponseHeader().getResult_text() + "]");
				}
			} else {
				throw new RuntimeException("무선 가입 상태 아님 : [" + deviceId + "] result code : ["
						+ idpReceiver.getResponseHeader().getResult() + "] + result message : ["
						+ idpReceiver.getResponseHeader().getResult_text() + "]");
			}

		}

		else if (schUserRes.getUserMbr().getImSvcNo() == null
				&& schUserRes.getUserMbr().getUserType().equals(MemberConstants.USER_TYPE_IDPID)) {
			// 서비스 가입여부 체크 (이메일)
			idpReceiver = this.idpService.alredyJoinCheckByEmail(schUserRes.getUserMbr().getUserEmail());

			// 이메일 가입여부 체크 등록되어 있지 않으면 resultCode : Success
			if (!StringUtil.equals(idpReceiver.getResponseHeader().getResult(), IDPConstants.IDP_RES_CODE_OK)) {
				// IDP 사용자 해지 userAuthKey, keyType(1:userId 2:userKey)
				idpReceiver = this.idpService.secedeUser(userAuthKey, "1", schUserRes.getUserMbr().getUserID());
				if (!StringUtil.equals(idpReceiver.getResponseHeader().getResult(), IDPConstants.IDP_RES_CODE_OK)) {
					throw new RuntimeException("[ IDP 아이디 회원해지 실패 : " + idpReceiver.getResponseHeader().getResult()
							+ "] " + idpReceiver.getResponseHeader().getResult_text());
				}
			} else {
				throw new RuntimeException("서비스 가입 상태 아님 : [" + schUserRes.getUserMbr().getUserEmail()
						+ "] result code : [" + idpReceiver.getResponseHeader().getResult() + "] + result message : ["
						+ idpReceiver.getResponseHeader().getResult_text() + "]");
			}

		}

		RemoveUserRequest removeUserRequest = new RemoveUserRequest();
		RemoveUserResponse removeUserResponse = new RemoveUserResponse();

		// TODO : Tenant API ========================== 임시 세팅 ==========================

		removeUserRequest.setCommonRequest(commonRequest);
		removeUserRequest.setSecedeReasonCode("US010411"); // 탈퇴사유 코드 : 임시
		removeUserRequest.setSecedeReasonMessage("돈독이 오름"); // 임시
		removeUserRequest.setSecedeTypeCode("US010705"); // 탈퇴유형 코드 : 임시

		// TODO : Tenant API ========================== 임시 세팅 ==========================

		removeUserRequest.setUserKey(schUserRes.getUserMbr().getUserKey()); // 사용자 키
		/* IDP 연동결과 성공이면 SC회원 탈퇴 */
		if (StringUtil.equals(idpReceiver.getResponseHeader().getResult(), IDPConstants.IDP_RES_CODE_OK)) {

			removeUserResponse = this.userSCI.remove(removeUserRequest);

			// SC Component Remove Fail
			if (!StringUtil
					.equals(removeUserResponse.getCommonResponse().getResultCode(), IDPConstants.IDP_RES_CODE_OK)) {
				throw new RuntimeException("[ SC 회원삭제 실패 : " + idpReceiver.getResponseHeader().getResult() + "] "
						+ idpReceiver.getResponseHeader().getResult_text());
			}
			// SC Component Remove Success
			else {
				withdrawRes.setUserKey(schUserRes.getUserMbr().getUserKey());
			}
		}
		/* ImIDP 연동결과 성공이면 SC회원 탈퇴 */
		else if (StringUtil.equals(imIdpReceiver.getResponseHeader().getResult(), IDPConstants.IDP_RES_CODE_OK)) {
			removeUserResponse = this.userSCI.remove(removeUserRequest);

			// SC Component Remove Fail
			if (!StringUtil
					.equals(removeUserResponse.getCommonResponse().getResultCode(), IDPConstants.IDP_RES_CODE_OK)) {
				throw new RuntimeException("[ SC 회원삭제 실패 : " + idpReceiver.getResponseHeader().getResult() + "] "
						+ idpReceiver.getResponseHeader().getResult_text());
			}
			// SC Component Remove Success
			else {
				withdrawRes.setUserKey(schUserRes.getUserMbr().getUserKey());
			}
		}
		return withdrawRes;
	}
}
