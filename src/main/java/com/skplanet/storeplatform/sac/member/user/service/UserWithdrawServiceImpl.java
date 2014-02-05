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

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.external.client.idp.vo.IDPReceiverM;
import com.skplanet.storeplatform.external.client.idp.vo.ImIDPReceiverM;
import com.skplanet.storeplatform.external.client.uaps.sci.UapsSCI;
import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;
import com.skplanet.storeplatform.member.client.common.vo.KeySearch;
import com.skplanet.storeplatform.member.client.user.sci.DeviceSCI;
import com.skplanet.storeplatform.member.client.user.sci.UserSCI;
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
 * 회원탈퇴 관련 인터페이스 구현체
 * 
 * Updated on : 2014. 1. 6. Updated by : 강신완, 부르칸.
 */
@Service
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
	public WithdrawRes withdraw(SacRequestHeader requestHeader, WithdrawReq req) {

		/* 헤더 정보 셋팅 */
		commonRequest.setSystemID(requestHeader.getTenantHeader().getSystemId());
		commonRequest.setTenantID(requestHeader.getTenantHeader().getTenantId());

		/**
		 * 모번호 조회 (989 일 경우만)
		 */
		if (req.getDeviceId() != null) {
			String opmdMdn = this.mcc.getOpmdMdnInfo(req.getDeviceId());
			req.setDeviceId(opmdMdn);
			logger.info("모번호 조회 getOpmdMdnInfo: {}", opmdMdn);
		}

		/* SC 회원 존재 여부 */
		SearchUserResponse schUserRes = this.searchUser(requestHeader, req);

		/* IMIDP 회원탈퇴 연동 */
		IDPReceiverM idpReceiver = new IDPReceiverM();
		ImIDPReceiverM imIdpReceiver = new ImIDPReceiverM();

		/* Return Value */
		WithdrawRes withdrawRes = new WithdrawRes();

		/* 통합회원 연동 */
		if (schUserRes.getUserMbr().getImSvcNo() != null) {
			imIdpReceiver = this.oneIdUser(requestHeader, schUserRes, req);

			logger.info("IMIDP Success Response ", schUserRes.toString());
			withdrawRes.setUserKey(schUserRes.getUserMbr().getUserKey());
		} else {
			if (schUserRes.getUserMbr().getUserType().equals(MemberConstants.USER_TYPE_MOBILE)) {
				idpReceiver = this.idpMobileUser(requestHeader, schUserRes, req);

				logger.info("IDP MDN Success Response ", schUserRes.toString());
				withdrawRes.setUserKey(schUserRes.getUserMbr().getUserKey());
			}
			/* IDP 아이디 회원 */
			else if (schUserRes.getUserMbr().getUserType().equals(MemberConstants.USER_TYPE_IDPID)) {
				idpReceiver = this.idpIdUser(requestHeader, schUserRes, req);

				logger.info("IDP ID Success Response ", schUserRes.toString());
				withdrawRes.setUserKey(schUserRes.getUserMbr().getUserKey());
			}
		}
		/* IDP 모바일 회원 */

		return withdrawRes;

	}

	/**
	 * 입력된 Parameter로 회원존재여부 체크 한다. deviceId or userId && userKey
	 */
	@Override
	public SearchUserResponse searchUser(SacRequestHeader requestHeader, WithdrawReq req) {
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

		} else if (!deviceId.equals("")) {
			key.setKeyType(MemberConstants.KEY_TYPE_DEVICE_ID);
			key.setKeyString(deviceId);
			keySearchList.add(key);
			schUserReq.setKeySearchList(keySearchList);

		} else {
			throw new RuntimeException("파라미터 없음 userId, userAuthKey, deviceId");
		}

		schUserRes = this.userSCI.searchUser(schUserReq);

		logger.info("###### SearchUser.deviceId req : {}", schUserReq.toString());

		// SC 컴포넌트에서 성공이 아닐때
		if (!StringUtils.equals(schUserRes.getCommonResponse().getResultCode(), MemberConstants.RESULT_SUCCES)) {
			throw new RuntimeException("3. SC Member Search Fail : " + schUserRes.getCommonResponse().getResultCode() + ", "
					+ schUserRes.getCommonResponse().getResultMessage());
		} else if (schUserRes.getUserMbr() == null) {
			throw new RuntimeException("회원정보 없음. schUserRes.getUserMbr()");
		} else if (MemberConstants.SUB_STATUS_SECEDE_FINISH.equals(schUserRes.getUserMbr().getUserSubStatus())) {
			throw new RuntimeException("탈퇴완료 회원 : SubStatusCode [" + schUserRes.getUserMbr().getUserSubStatus() + "]");
		} else if (MemberConstants.MAIN_STATUS_SECEDE.equals(schUserRes.getUserMbr().getUserMainStatus())) {
			throw new RuntimeException("탈퇴완료 회원 : MainStatusCode [" + schUserRes.getUserMbr().getUserMainStatus() + "]");
		} else {
			logger.info("회원정보조회 SC Member Search Success : {}, {}", schUserRes.getCommonResponse().getResultCode(), schUserRes.getCommonResponse()
					.getResultMessage());
			logger.info("회원정보조회 SC Member Search Success Response {}: ", schUserRes.toString());
			logger.info("회원정보조회 SC Member Search Success Response {}: ", schUserRes.getUserMbr().toString());

			return schUserRes;
		}

	}

	/**
	 * IMIDP 연동(통합회원)
	 */
	@Override
	public ImIDPReceiverM oneIdUser(SacRequestHeader requestHeader, SearchUserResponse schUserRes, WithdrawReq req) {

		Map<String, Object> param = new HashMap<String, Object>();

		param.put("key", schUserRes.getUserMbr().getImSvcNo());
		param.put("key_type", "1");
		param.put("user_auth_key", req.getUserAuthKey());
		// param.put("term_reason_cd", "1"); // 1=IM통합서비스번호, 2=IM통합ID

		ImIDPReceiverM imIdpReceiver = this.imIdpService.discardUser(param);

		if (!StringUtils.equals(imIdpReceiver.getResponseHeader().getResult(), ImIDPConstants.IDP_RES_CODE_OK)) {
			throw new RuntimeException("[통합회원탈퇴 ImIDP discardUser Fail : " + imIdpReceiver.getResponseHeader().getResult() + "] "
					+ imIdpReceiver.getResponseHeader().getResult_text());
		} else {
			logger.info("[통합회원탈퇴 ImIDP discardUser Success {}, {}: ", imIdpReceiver.getResponseHeader().getResult(), imIdpReceiver
					.getResponseHeader().getResult_text());
			logger.info("[통합회원탈퇴 ImIDP discardUser Success Response {}: ", imIdpReceiver.getResponseBody().toString());
		}

		return imIdpReceiver;

	}

	/**
	 * IDP 모바일 회원(무선)
	 */
	@Override
	public IDPReceiverM idpMobileUser(SacRequestHeader requestHeader, SearchUserResponse schUserRes, WithdrawReq req) {

		IDPReceiverM idpReceiver = this.idpService.secedeUser4Wap(req.getDeviceId());

		logger.info("모바일해지 IDP secedeUser4Wap Success {}, {}", idpReceiver.getResponseHeader().getResult(), idpReceiver.getResponseHeader()
				.getResult_text());
		logger.info("모바일해지 IDP secedeUser4Wap Success Response {}: " + idpReceiver.getResponseBody().toString());

		if (!StringUtils.equals(idpReceiver.getResponseHeader().getResult(), IDPConstants.IDP_RES_CODE_OK)) {
			throw new RuntimeException("IDP 모바일 회원해지 실패 secedeUser4Wap : [" + req.getDeviceId() + "] result code : ["
					+ idpReceiver.getResponseHeader().getResult() + "] + result message : [" + idpReceiver.getResponseHeader().getResult_text() + "]");
		}

		return idpReceiver;
	}

	/**
	 * IDP 아이디 회원
	 */
	@Override
	public IDPReceiverM idpIdUser(SacRequestHeader requestHeader, SearchUserResponse schUserRes, WithdrawReq req) {

		IDPReceiverM idpReceiver = this.idpService.secedeUser(req.getUserAuthKey(), "1", schUserRes.getUserMbr().getUserID());
		if (!StringUtils.equals(idpReceiver.getResponseHeader().getResult(), IDPConstants.IDP_RES_CODE_OK)) {
			throw new RuntimeException("[ IDP secedeUser 아이디 회원해지 Fail : " + idpReceiver.getResponseHeader().getResult() + "] "
					+ idpReceiver.getResponseHeader().getResult_text());
		} else {
			logger.info("[회원해지 IDP secedeUser Success : {}, {}", idpReceiver.getResponseHeader().getResult(), idpReceiver.getResponseHeader()
					.getResult_text());
			logger.info("[회원해지 IDP secedeUser Success Response : {}", idpReceiver.getResponseBody().toString());
		}

		return idpReceiver;
	}

}
