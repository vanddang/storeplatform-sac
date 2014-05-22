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
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.external.client.idp.sci.IdpSCI;
import com.skplanet.storeplatform.external.client.idp.sci.ImIdpSCI;
import com.skplanet.storeplatform.external.client.idp.vo.SecedeForWapEcReq;
import com.skplanet.storeplatform.external.client.idp.vo.SecedeUserEcReq;
import com.skplanet.storeplatform.external.client.idp.vo.imidp.DiscardUserEcReq;
import com.skplanet.storeplatform.member.client.user.sci.DeviceSCI;
import com.skplanet.storeplatform.member.client.user.sci.UserSCI;
import com.skplanet.storeplatform.member.client.user.sci.vo.RemoveDeviceRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.RemoveUserRequest;
import com.skplanet.storeplatform.sac.api.util.DateUtil;
import com.skplanet.storeplatform.sac.client.member.vo.common.DeviceInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.UserInfo;
import com.skplanet.storeplatform.sac.client.member.vo.user.GameCenterSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.RemoveMemberAmqpSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.WithdrawReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.WithdrawRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.member.common.MemberCommonComponent;
import com.skplanet.storeplatform.sac.member.common.constant.MemberConstants;

/**
 * 회원탈퇴 관련 인터페이스 구현체
 * 
 * Updated on : 2014. 1. 6. Updated by : 강신완, 부르칸.
 */
@Service
public class UserWithdrawServiceImpl implements UserWithdrawService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserWithdrawServiceImpl.class);

	@Autowired
	private UserSCI userSCI; // 회원 콤포넌트 사용자 기능 인터페이스

	@Autowired
	private DeviceSCI deviceSCI; // 회원 콤포넌트 휴대기기 기능 인터페이스

	@Autowired
	private DeviceService deviceService;

	@Autowired
	private MemberCommonComponent mcc;

	@Autowired
	private IdpSCI idpSCI;

	@Autowired
	private ImIdpSCI imIdpSCI;

	@Autowired
	@Resource(name = "memberRetireAmqpTemplate")
	private AmqpTemplate memberRetireAmqpTemplate;

	@Autowired
	private UserService userService;

	@Override
	public WithdrawRes executeWithdraw(SacRequestHeader requestHeader, WithdrawReq req) {

		/**
		 * 회원 정보 조회 Value Object.
		 */
		UserInfo userInfo = null;

		/**
		 * 요청 파라미터에 따라서 분기 처리한다.
		 * 
		 * 1. userId, userAuthKey 둘다 존재 or 모두 존재 Case.
		 * 
		 * 2. deviceId만 존재하는 Case.
		 */
		if (StringUtils.isNotBlank(req.getUserId()) && StringUtils.isNotBlank(req.getUserAuthKey())) {

			LOGGER.debug("########################################");
			LOGGER.info("userId, userAuthKey 둘다 존재 or 모두 존재 Case.");
			LOGGER.debug("########################################");

			/**
			 * userId로 회원 정보 조회.
			 */
			userInfo = this.mcc.getUserBaseInfo("userId", req.getUserId(), requestHeader);
			if (StringUtils.isNotBlank(userInfo.getImSvcNo())) {

				/**********************************************
				 * OneId ID 회원 Case.
				 **********************************************/
				LOGGER.info("[OneId ID 회원 Case] id:{}, type:{}", userInfo.getUserId(), userInfo.getUserType());
				this.discardUser(userInfo.getImSvcNo(), req.getUserAuthKey());
				this.remove(requestHeader, userInfo.getUserKey());

			} else {

				/**********************************************
				 * IDP ID 회원 Case.
				 **********************************************/
				LOGGER.info("[IDP ID 회원 Case] id:{}, type:{}", userInfo.getUserId(), userInfo.getUserType());
				this.secedeUser(req.getUserId(), req.getUserAuthKey());
				this.remove(requestHeader, userInfo.getUserKey());

			}

		} else {

			LOGGER.debug("########################################");
			LOGGER.info("deviceId 존재 Case.");
			LOGGER.debug("########################################");

			/**
			 * 모번호 조회 (989 일 경우만)
			 */
			req.setDeviceId(this.mcc.getOpmdMdnInfo(req.getDeviceId()));

			/**
			 * deviceId로 회원 정보 조회.
			 */
			userInfo = this.mcc.getUserBaseInfo("deviceId", req.getDeviceId(), requestHeader);
			if (StringUtils.isNotBlank(userInfo.getImSvcNo())) { // 통합회원, IDP 회원 구분

				/**********************************************
				 * OneId ID 회원 Case.
				 **********************************************/
				LOGGER.info("[OneId ID 회원 Case] deviceId:{}, type:{}", req.getDeviceId(), userInfo.getUserType());
				this.deviceIdInvalid(requestHeader, userInfo.getUserKey(), req.getDeviceId());
				this.userService.updateAdditionalInfoForNonLogin(requestHeader, userInfo.getUserKey(), userInfo.getImSvcNo());

			} else {

				if (StringUtils.equals(userInfo.getUserType(), MemberConstants.USER_TYPE_MOBILE)) {

					/**********************************************
					 * 무선 회원 Case.
					 **********************************************/
					LOGGER.info("[무선회원 Case] deviceId:{}, type:{}", req.getDeviceId(), userInfo.getUserType());
					this.secedeForWap(req.getDeviceId());
					this.remove(requestHeader, userInfo.getUserKey());

				} else if (StringUtils.equals(userInfo.getUserType(), MemberConstants.USER_TYPE_IDPID)) {

					/**********************************************
					 * IDP ID 회원 Case.
					 **********************************************/
					LOGGER.info("[IDP ID 회원 Case] deviceId:{}, type:{}", req.getDeviceId(), userInfo.getUserType());
					this.secedeForWap(req.getDeviceId());
					this.deviceIdInvalid(requestHeader, userInfo.getUserKey(), req.getDeviceId());

				} // 모바일회원, IDP ID회원 분기 END

			} // 통합회원, IDP 회원 구분 END

		} // 요청 파라미터에 따른 분기 END

		LOGGER.info("IDP 탈퇴처리, DB 탈퇴처리 모두 완료.");

		/**
		 * 게임센터 연동.
		 */
		GameCenterSacReq gameCenterSacReq = new GameCenterSacReq();
		gameCenterSacReq.setUserKey(userInfo.getUserKey());
		if (StringUtils.isNotBlank(req.getDeviceId())) {
			gameCenterSacReq.setDeviceId(req.getDeviceId());
		}
		gameCenterSacReq.setSystemId(requestHeader.getTenantHeader().getSystemId());
		gameCenterSacReq.setTenantId(requestHeader.getTenantHeader().getTenantId());
		gameCenterSacReq.setWorkCd(MemberConstants.GAMECENTER_WORK_CD_USER_SECEDE);
		this.deviceService.insertGameCenterIF(gameCenterSacReq);

		/**
		 * MQ 연동.
		 */
		RemoveMemberAmqpSacReq mqInfo = new RemoveMemberAmqpSacReq();

		try {

			mqInfo.setUserId(userInfo.getUserId());
			mqInfo.setUserKey(userInfo.getUserKey());
			mqInfo.setWorkDt(DateUtil.getToday("yyyyMMddHHmmss"));
			this.memberRetireAmqpTemplate.convertAndSend(mqInfo);

		} catch (AmqpException ex) {
			LOGGER.error("MQ process fail {}", mqInfo);
		}

		/**
		 * 결과 세팅
		 */
		WithdrawRes response = new WithdrawRes();
		response.setUserKey(userInfo.getUserKey());
		return response;

	}

	/**
	 * <pre>
	 * 통합회원 해지 연동.
	 * </pre>
	 * 
	 * @param imSvcNo
	 *            통합회원 관리번호
	 * @param userAuthKey
	 *            IDP 인증 Key
	 */
	public void discardUser(String imSvcNo, String userAuthKey) {

		DiscardUserEcReq ecReq = new DiscardUserEcReq();
		ecReq.setKey(imSvcNo);
		ecReq.setKeyType("1");
		ecReq.setUserAuthKey(userAuthKey);
		this.imIdpSCI.discardUser(ecReq);

	}

	/**
	 * <pre>
	 * 모바일 전용회원 탈퇴 연동.
	 * </pre>
	 * 
	 * @param deviceId
	 *            기기 ID (mdn, uuid)
	 */
	public void secedeForWap(String deviceId) {

		SecedeForWapEcReq ecReq = new SecedeForWapEcReq();
		ecReq.setUserMdn(deviceId);
		this.idpSCI.secedeForWap(ecReq);

	}

	/**
	 * <pre>
	 * IDP 회원 해지 연동.
	 * </pre>
	 * 
	 * @param userId
	 *            사용자 아이디
	 * @param userAuthKey
	 *            IDP 인증 Key
	 */
	public void secedeUser(String userId, String userAuthKey) {

		SecedeUserEcReq ecReq = new SecedeUserEcReq();
		ecReq.setKey(userId);
		ecReq.setUserAuthKey(userAuthKey);
		ecReq.setKeyType("1");
		this.idpSCI.secedeUser(ecReq);

	}

	/**
	 * <pre>
	 * SC 회원 탈퇴 요청.
	 * </pre>
	 * 
	 * @param requestHeader
	 *            SAC 공통 헤더
	 * @param userKey
	 *            사용자 Key
	 */
	public void remove(SacRequestHeader requestHeader, String userKey) {

		LOGGER.info("SC 탈퇴 요청 userKey:{}", userKey);
		RemoveUserRequest scReq = new RemoveUserRequest();
		scReq.setCommonRequest(this.mcc.getSCCommonRequest(requestHeader));
		scReq.setUserKey(userKey);
		scReq.setSecedeReasonCode(MemberConstants.USER_WITHDRAW_CLASS_USER_SELECTED);
		scReq.setSecedeReasonMessage("");
		this.userSCI.remove(scReq);

	}

	/**
	 * <pre>
	 * SC DeviceId Invalid 처리 요청.
	 * </pre>
	 * 
	 * @param requestHeader
	 *            SAC 공통 헤더
	 * @param userKey
	 *            사용자 Key
	 * @param deviceId
	 *            기기 ID
	 */
	public void deviceIdInvalid(SacRequestHeader requestHeader, String userKey, String deviceId) {

		/**
		 * SC 휴대기기 단건 조회.
		 */
		DeviceInfo deviceInfo = this.deviceService.searchDevice(requestHeader, MemberConstants.KEY_TYPE_DEVICE_ID, deviceId, userKey);

		/**
		 * SC 휴대기기 삭제요청.
		 */
		LOGGER.info("SC DeviceId Invalid 처리 요청 userKey:{}, deviceKey:{}", userKey, deviceInfo.getDeviceKey());
		List<String> removeKeyList = new ArrayList<String>();
		removeKeyList.add(deviceInfo.getDeviceKey());

		RemoveDeviceRequest removeDeviceRequest = new RemoveDeviceRequest();
		removeDeviceRequest.setCommonRequest(this.mcc.getSCCommonRequest(requestHeader));
		removeDeviceRequest.setUserKey(userKey);
		removeDeviceRequest.setDeviceKey(removeKeyList);

		this.deviceSCI.removeDevice(removeDeviceRequest);

	}

}
