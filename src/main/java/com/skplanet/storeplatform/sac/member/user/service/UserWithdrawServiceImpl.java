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
import com.skplanet.storeplatform.sac.member.common.constant.MemberConstants;
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

	@Autowired
	private MemberCommonComponent mcc;

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

		/* 헤더 정보 셋팅 */
		commonRequest.setSystemID(requestHeader.getTenantHeader().getSystemId());
		commonRequest.setTenantID(requestHeader.getTenantHeader().getTenantId());

		/**
		 * 모번호 조회 (989 일 경우만)
		 */
		req.setDeviceId(this.mcc.getOpmdMdnInfo(req.getDeviceId()));
		logger.info("모번호 조회 getOpmdMdnInfo: {}", this.mcc.getOpmdMdnInfo(req.getDeviceId()));

		/* SC 회원 존재 여부 */
		SearchUserResponse schUserRes = this.searchUser(requestHeader, req);

		/* IMIDP 회원탈퇴 연동 */
		IDPReceiverM idpReceiver = new IDPReceiverM();
		ImIDPReceiverM imIdpReceiver = new ImIDPReceiverM();

		/* 통합회원 연동 */
		if (schUserRes.getUserMbr().getImSvcNo() != null) {
			imIdpReceiver = this.oneIdUser(requestHeader, schUserRes, req);
		}
		/* IDP 모바일 회원 */
		else if (schUserRes.getUserMbr().getImSvcNo() == null
				&& schUserRes.getUserMbr().getUserType().equals(MemberConstants.USER_TYPE_MOBILE)) {
			idpReceiver = this.idpMobileUser(requestHeader, schUserRes, req);
		}
		/* IDP 아이디 회원 */
		else if (schUserRes.getUserMbr().getImSvcNo() == null
				&& schUserRes.getUserMbr().getUserType().equals(MemberConstants.USER_TYPE_IDPID)) {
			idpReceiver = this.idpIdUser(requestHeader, schUserRes, req);
		}

		/* Tenant 연동 (임시 데이터 하드코딩되어 있음) */
		RemoveUserRequest removeUserRequest = this.tenantRemoveUser(requestHeader, schUserRes);

		/* Return Value */
		WithdrawRes withdrawRes = new WithdrawRes();

		/* IDP or IMIDP 연동결과 성공이면 SC회원 탈퇴 */
		if (StringUtil.equals(idpReceiver.getResponseHeader().getResult(), IDPConstants.IDP_RES_CODE_OK)
				|| StringUtil.equals(imIdpReceiver.getResponseHeader().getResult(), ImIDPConstants.IDP_RES_CODE_OK)) {
			logger.info("7. IDP or IMIDP Success Response ", schUserRes.toString());
			withdrawRes = this.sciRemoveUser(removeUserRequest, schUserRes);
		} else {
			throw new RuntimeException("알수없는 오류 IDP 연동 : " + idpReceiver.getResponseHeader().getResult() + " ::: "
					+ idpReceiver.getResponseHeader().getResult_text() + "알수없는 오류 IMIDP 연동 : "
					+ imIdpReceiver.getResponseHeader().getResult() + " ::: "
					+ imIdpReceiver.getResponseHeader().getResult_text());
		}

		return withdrawRes;

	}

	/**
	 * 입력된 Parameter로 회원존재여부 체크 한다. deviceId or userId && userKey
	 */
	@Override
	public SearchUserResponse searchUser(SacRequestHeader requestHeader, WithdrawReq req) throws Exception {
		String userAuthKey = req.getUserAuthKey();
		String userId = req.getUserId();
		String deviceId = req.getDeviceId();

		logger.info("###### 회원정보조회 SearchUser Request : {}", req.toString());

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
			throw new RuntimeException("3. SC Member Search Fail : " + schUserRes.getCommonResponse().getResultCode()
					+ ", " + schUserRes.getCommonResponse().getResultMessage());
		} else if (schUserRes == null) {
			throw new RuntimeException("회원정보 없음.");
		} else {
			logger.info("회원정보조회 SC Member Search Success : {}, {}", schUserRes.getCommonResponse().getResultCode(),
					schUserRes.getCommonResponse().getResultMessage());
			logger.info("회원정보조회 SC Member Search Success Response {}: ", schUserRes.toString());
			logger.info("회원정보조회 SC Member Search Success Response {}: ", schUserRes.getUserMbr().toString());
		}

		if (schUserRes.getUserMbr() == null) {
			throw new RuntimeException("회원정보 없음.");
		} else if (MemberConstants.SUB_STATUS_SECEDE_FINISH.equals(schUserRes.getUserMbr().getUserSubStatus())) {
			throw new RuntimeException("탈퇴완료 회원 : MainStatusCode [" + schUserRes.getUserMbr().getUserMainStatus() + "]"
					+ "SubStatusCode [" + schUserRes.getUserMbr().getUserSubStatus() + "]");
		} else {
			return schUserRes;
		}

	}

	/**
	 * IMIDP 연동(통합회원)
	 */
	@Override
	public ImIDPReceiverM oneIdUser(SacRequestHeader requestHeader, SearchUserResponse schUserRes, WithdrawReq req)
			throws Exception {

		ImIDPReceiverM imIdpReceiver = this.imIdpService.userInfoIdpSearchServer(schUserRes.getUserMbr().getImSvcNo());

		if (!StringUtil.equals(imIdpReceiver.getResponseHeader().getResult(), ImIDPConstants.IDP_RES_CODE_OK)) {
			throw new RuntimeException("[ImIDP JoinCheck 가입여부 체크 : " + imIdpReceiver.getResponseHeader().getResult()
					+ "] " + imIdpReceiver.getResponseHeader().getResult_text());
		} else {
			logger.info("ImIDP 가입여부 체크 userInfoIdpSearchServer Success : ", imIdpReceiver.getResponseHeader()
					.getResult(), "] ", imIdpReceiver.getResponseHeader().getResult_text());
			logger.info("가입여부 체크 ImIDP userInfoIdpSearchServer Success Response : ", imIdpReceiver.getResponseBody()
					.toString());

			// 통합회원 OneId 사용자
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("key", schUserRes.getUserMbr().getUserKey());
			param.put("user_auth_kery", req.getUserAuthKey());
			// param.put("term_reason_cd", "1"); // 1=IM통합서비스번호, 2=IM통합ID

			imIdpReceiver = this.imIdpService.discardUser(param);

			if (!StringUtil.equals(imIdpReceiver.getResponseHeader().getResult(), ImIDPConstants.IDP_RES_CODE_OK)) {
				throw new RuntimeException("[통합회원탈퇴 ImIDP discardUser Fail : "
						+ imIdpReceiver.getResponseHeader().getResult() + "] "
						+ imIdpReceiver.getResponseHeader().getResult_text());
			} else {
				logger.info("[통합회원탈퇴 ImIDP discardUser Success {}, {}: ",
						imIdpReceiver.getResponseHeader().getResult(), imIdpReceiver.getResponseHeader()
								.getResult_text());
				logger.info("[통합회원탈퇴 ImIDP discardUser Success Response {}: ", imIdpReceiver.getResponseBody()
						.toString());
			}
		}

		return imIdpReceiver;

	}

	/**
	 * IDP 모바일 회원(무선)
	 */
	@Override
	public IDPReceiverM idpMobileUser(SacRequestHeader requestHeader, SearchUserResponse schUserRes, WithdrawReq req)
			throws Exception {
		// 모바일 인증
		IDPReceiverM idpReceiver = this.idpService.authForWap(req.getDeviceId());

		logger.info("모바일인증 IDP Mobile authForWap Success : result code : [", idpReceiver.getResponseHeader()
				.getResult(), "] + result message : [", idpReceiver.getResponseHeader().getResult_text() + "]");
		logger.info("모바일인증 IDP Mobile authForWap Success Response :  [", idpReceiver.getResponseBody().toString());

		if (StringUtil.equals(idpReceiver.getResponseHeader().getResult(), IDPConstants.IDP_RES_CODE_OK)) {
			// 인증 OK --> 모바일 해지

			idpReceiver = this.idpService.secedeUser4Wap(req.getDeviceId());

			logger.info("모바일해지 IDP secedeUser4Wap Success {}, {}", idpReceiver.getResponseHeader().getResult(),
					idpReceiver.getResponseHeader().getResult_text());
			logger.info("모바일해지 IDP secedeUser4Wap Success Response {}: " + idpReceiver.getResponseBody().toString());

			if (!StringUtil.equals(idpReceiver.getResponseHeader().getResult(), IDPConstants.IDP_RES_CODE_OK)) {
				throw new RuntimeException("IDP 모바일 회원해지 실패 secedeUser4Wap : [" + req.getDeviceId()
						+ "] result code : [" + idpReceiver.getResponseHeader().getResult() + "] + result message : ["
						+ idpReceiver.getResponseHeader().getResult_text() + "]");
			}
		} else {
			throw new RuntimeException("Not authForWap 무선 가입 상태 아님 : [" + req.getDeviceId() + "] result code : ["
					+ idpReceiver.getResponseHeader().getResult() + "] + result message : ["
					+ idpReceiver.getResponseHeader().getResult_text() + "]");
		}

		return idpReceiver;
	}

	/**
	 * IDP 아이디 회원
	 */
	@Override
	public IDPReceiverM idpIdUser(SacRequestHeader requestHeader, SearchUserResponse schUserRes, WithdrawReq req)
			throws Exception {
		// 모바일 인증
		IDPReceiverM idpReceiver = this.idpService.alredyJoinCheckByEmail(schUserRes.getUserMbr().getUserEmail());

		logger.info("[이메일 가입여부 체크 IDP alredyJoinCheckByEmail Success : ", idpReceiver.getResponseHeader().getResult(),
				"] ", idpReceiver.getResponseHeader().getResult_text());
		logger.info("[이메일 가입여부 체크 IDP alredyJoinCheckByEmail Success Response : ", idpReceiver.getResponseBody()
				.toString());

		// 이메일 가입여부 체크 등록되어 있지 않으면 resultCode : Success
		if (!StringUtil.equals(idpReceiver.getResponseHeader().getResult(), IDPConstants.IDP_RES_CODE_OK)) {
			// IDP 사용자 해지 userAuthKey, keyType(1:userId 2:userKey)
			idpReceiver = this.idpService.secedeUser(req.getUserAuthKey(), "1", schUserRes.getUserMbr().getUserID());
			if (!StringUtil.equals(idpReceiver.getResponseHeader().getResult(), IDPConstants.IDP_RES_CODE_OK)) {
				throw new RuntimeException("[ IDP secedeUser 아이디 회원해지 Fail : "
						+ idpReceiver.getResponseHeader().getResult() + "] "
						+ idpReceiver.getResponseHeader().getResult_text());
			} else {
				logger.info("[회원해지 IDP secedeUser Success : {}, {}", idpReceiver.getResponseHeader().getResult(),
						idpReceiver.getResponseHeader().getResult_text());
				logger.info("[회원해지 IDP secedeUser Success Response : {}", idpReceiver.getResponseBody().toString());
			}
		} else {
			throw new RuntimeException("Not Service Join 서비스 가입 상태 아님 : [" + schUserRes.getUserMbr().getUserEmail()
					+ "] result code : [" + idpReceiver.getResponseHeader().getResult() + "] + result message : ["
					+ idpReceiver.getResponseHeader().getResult_text() + "]");
		}

		return idpReceiver;
	}

	/**
	 * Tenant API 연동
	 */
	@Override
	public RemoveUserRequest tenantRemoveUser(SacRequestHeader requestHeader, SearchUserResponse schUserRes)
			throws Exception {
		RemoveUserRequest removeUserRequest = new RemoveUserRequest();
		removeUserRequest.setCommonRequest(commonRequest);
		removeUserRequest.setSecedeReasonCode("US010411"); // 탈퇴사유 코드 : 임시
		removeUserRequest.setSecedeReasonMessage("돈독이 오름"); // 임시
		removeUserRequest.setSecedeTypeCode("US010705"); // 탈퇴유형 코드 : 임시
		removeUserRequest.setUserKey(schUserRes.getUserMbr().getUserKey()); // 사용자 키

		logger.info("Tenant Request Imsi Setting {}", removeUserRequest.toString());

		return removeUserRequest;
	}

	/**
	 * SC 회원탈퇴 연동
	 */
	@Override
	public WithdrawRes sciRemoveUser(RemoveUserRequest removeUserRequest, SearchUserResponse schUserRes)
			throws Exception {

		WithdrawRes withdrawRes = new WithdrawRes();
		RemoveUserResponse removeUserResponse = this.userSCI.remove(removeUserRequest);

		// SC Component Remove Fail
		if (!StringUtil.equals(removeUserResponse.getCommonResponse().getResultCode(), MemberConstants.RESULT_SUCCES)) {
			throw new RuntimeException("[ IDP -> SC remove Fail : "
					+ removeUserResponse.getCommonResponse().getResultCode() + "] "
					+ removeUserResponse.getCommonResponse().getResultMessage());
		}
		// SC Component Remove Success
		else {
			logger.info("[ IDP -> SC remove Success Code : {}, {}", removeUserResponse.getCommonResponse()
					.getResultCode(), removeUserResponse.getCommonResponse().getResultMessage());
			logger.info("[ IDP -> SC remove Success Response : {}, {}", schUserRes.getUserMbr().getUserKey());
			withdrawRes.setUserKey(schUserRes.getUserMbr().getUserKey());
		}

		return withdrawRes;
	}
}
