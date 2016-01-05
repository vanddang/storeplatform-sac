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
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.member.client.user.sci.DeviceSCI;
import com.skplanet.storeplatform.member.client.user.sci.UserSCI;
import com.skplanet.storeplatform.member.client.user.sci.vo.CheckUserAuthTokenRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.CheckUserAuthTokenResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.RemoveDeviceRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.RemoveUserRequest;
import com.skplanet.storeplatform.sac.api.util.DateUtil;
import com.skplanet.storeplatform.sac.client.member.vo.common.DeviceInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.UserExtraInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.UserInfo;
import com.skplanet.storeplatform.sac.client.member.vo.user.*;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.member.common.MemberCommonComponent;
import com.skplanet.storeplatform.sac.member.common.constant.IdpConstants;
import com.skplanet.storeplatform.sac.member.common.constant.MemberConstants;
import com.skplanet.storeplatform.sac.member.common.util.ValidationCheckUtils;

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
	@Resource(name = "memberDelDeviceAmqpTemplate")
	private AmqpTemplate memberDelDeviceAmqpTemplate;

	@Autowired
	private UserService userService;

	@Autowired
	private UserSearchService userSearchService;

	@Override
	public WithdrawRes withdraw(SacRequestHeader requestHeader, WithdrawReq req) {

		/**
		 * 회원 정보 조회 Value Object.
		 */
		UserInfo userInfo = null;

		/**
		 * 요청 파라미터에 따라서 분기 처리한다.
		 *
		 * 1. userId, userAuthToken 둘다 존재 or 모두 존재 Case.
		 *
		 * 2. deviceId만 존재하는 Case.
		 */
		if (StringUtils.isNotBlank(req.getUserId()) && StringUtils.isNotBlank(req.getUserAuthToken())) {

			LOGGER.debug("########################################");
			LOGGER.info("userId, userAuthToken 둘다 존재 or 모두 존재 Case.");
			LOGGER.debug("########################################");

			/**
			 * userId로 회원 정보 조회. 이미 탈퇴되었을 경우 오류처리
			 */
			userInfo = this.mcc.getUserBaseInfo("userId", req.getUserId(), requestHeader);
			if (StringUtils.equals(userInfo.getIsDormant(), MemberConstants.USE_Y)) {
				throw new StorePlatformException("SAC_MEM_0006");
			}

			/** 소셜 아이디인 경우만 userAuthToken 인증 */
			if (StringUtils.equals(userInfo.getUserType(), MemberConstants.USER_TYPE_FACEBOOK)
				|| StringUtils.equals(userInfo.getUserType(), MemberConstants.USER_TYPE_GOOGLE)
				|| StringUtils.equals(userInfo.getUserType(), MemberConstants.USER_TYPE_NAVER)) {
				CheckUserAuthTokenRequest chkUserAuthTkReqeust = new CheckUserAuthTokenRequest();
				chkUserAuthTkReqeust.setCommonRequest(mcc.getSCCommonRequest(requestHeader));
				chkUserAuthTkReqeust.setUserKey(userInfo.getUserKey());
				chkUserAuthTkReqeust.setUserAuthToken(req.getUserAuthToken());
				CheckUserAuthTokenResponse chkUserAuthTkResponse = userSCI.checkUserAuthToken(chkUserAuthTkReqeust);
				if (chkUserAuthTkResponse.getUserKey() == null || chkUserAuthTkResponse.getUserKey().length() <= 0) {
					throw new StorePlatformException("SAC_MEM_1204");
				}
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
			String keyType = "deviceId";
			if(ValidationCheckUtils.isMdn(req.getDeviceId()) ){
				keyType = "mdn";
			}
			userInfo = this.mcc.getUserBaseInfo(keyType, req.getDeviceId(), requestHeader);
			if (StringUtils.equals(userInfo.getIsDormant(), MemberConstants.USE_Y)) {
				throw new StorePlatformException("SAC_MEM_0006");
			}

		}

		/** SC회원 탈퇴 요청*/
		this.rem(requestHeader, userInfo.getUserKey(), userInfo.getIsDormant());

		/** MQ 연동을 위해 userId가 가지고 있는 휴대기기 목록 조회 */
		ListDeviceReq listDeviceReq = new ListDeviceReq();
		listDeviceReq.setUserId(userInfo.getUserId());
		listDeviceReq.setIsMainDevice("N");
		ListDeviceRes listDeviceRes = this.deviceService.listDevice(requestHeader, listDeviceReq);

		/** MQ 연동 (회원 탈퇴) */
		StringBuffer buf = new StringBuffer();
		String mqDeviceStr = "";
		if (listDeviceRes.getDeviceInfoList() != null) {
			for (DeviceInfo deviceInfo : listDeviceRes.getDeviceInfoList()) { // 휴대기기 정보가 여러건인경우 | 로 구분하여 MQ로 모두 전달
				buf.append(deviceInfo.getDeviceId()).append("|");
			}
			mqDeviceStr = buf.toString();
			mqDeviceStr = mqDeviceStr.substring(0, mqDeviceStr.lastIndexOf("|"));
		}
		RemoveMemberAmqpSacReq mqInfo = new RemoveMemberAmqpSacReq();
		mqInfo.setUserId(userInfo.getUserId());
		mqInfo.setUserKey(userInfo.getUserKey());
		mqInfo.setWorkDt(DateUtil.getToday("yyyyMMddHHmmss"));

		List<UserExtraInfo> list = userInfo.getUserExtraInfoList();
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				UserExtraInfo extraInfo = list.get(i);
				if (StringUtils.equals(MemberConstants.USER_EXTRA_PROFILEIMGPATH, extraInfo.getExtraProfile())) {
					mqInfo.setProfileImgPath(extraInfo.getExtraProfileValue());
				}
			}
		}

		if (StringUtils.isNotBlank(mqDeviceStr)) {
			mqInfo.setDeviceId(mqDeviceStr);
		}

		LOGGER.info("{} 탈퇴 MQ device : {}", req.getUserId(), mqDeviceStr);

		try {

			this.memberRetireAmqpTemplate.convertAndSend(mqInfo);

		} catch (AmqpException ex) {
			LOGGER.error("MQ process fail {}", mqInfo);
		}

		LOGGER.info("IDP 탈퇴처리, DB 탈퇴처리 모두 완료.");

		/**
		 * 결과 세팅
		 */
		WithdrawRes response = new WithdrawRes();
		response.setUserKey(userInfo.getUserKey());
		return response;

	}

	/**
	 * <pre>
	 * deviceId 삭제처리
	 * - 모바일 전용회원 인증 시에 SC는 회원이고, IDP는 미회원인 경우 deviceId를 삭제처리 하기 위해 호출한다.
	 * </pre>
	 * 
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param deviceId
	 *            String
	 * @param userAuthKey
	 *            String
	 */
	@Override
	public void removeDevice(SacRequestHeader requestHeader, String deviceId, String userAuthKey) {

		/**
		 * deviceId로 회원 정보 조회.
		 */
		DetailReq detailReq = new DetailReq();
		detailReq.setDeviceId(deviceId);
		SearchExtentReq searchExtent = new SearchExtentReq();
		searchExtent.setUserInfoYn(MemberConstants.USE_Y);
		searchExtent.setDeviceInfoYn(MemberConstants.USE_Y);
		detailReq.setSearchExtent(searchExtent);

		try {

			DetailV2Res detailRes = this.userSearchService.detailV2(requestHeader, detailReq);

			if (StringUtils.equals(detailRes.getUserInfo().getUserType(), MemberConstants.USER_TYPE_MOBILE)) {

				this.rem(requestHeader, detailRes.getUserInfo().getUserKey(), detailRes.getUserInfo().getIsDormant());

				/**
				 * MQ 연동(회원 탈퇴).
				 */
				RemoveMemberAmqpSacReq mqInfo = new RemoveMemberAmqpSacReq();

				try {

					mqInfo.setUserId(detailRes.getUserInfo().getUserId());
					mqInfo.setUserKey(detailRes.getUserInfo().getUserKey());
					mqInfo.setWorkDt(DateUtil.getToday("yyyyMMddHHmmss"));
					mqInfo.setDeviceId(deviceId);
					List<UserExtraInfo> list = detailRes.getUserInfo().getUserExtraInfoList();
					if (list != null) {
						for (int i = 0; i < list.size(); i++) {
							UserExtraInfo extraInfo = list.get(i);
							if (StringUtils.equals(MemberConstants.USER_EXTRA_PROFILEIMGPATH,
									extraInfo.getExtraProfile())) {
								mqInfo.setProfileImgPath(extraInfo.getExtraProfileValue());
							}
						}
					}
					this.memberRetireAmqpTemplate.convertAndSend(mqInfo);

				} catch (AmqpException ex) {
					LOGGER.error("MQ process fail {}", mqInfo);
				}

				LOGGER.info("{} 모바일회원 탈퇴처리", deviceId);

			} else if (StringUtils.equals(detailRes.getUserInfo().getUserType(), MemberConstants.USER_TYPE_IDPID)) {

				this.deviceIdInvalidByDeviceKey(requestHeader, detailRes.getUserInfo().getUserKey(), detailRes
						.getDeviceInfoList().get(0).getDeviceKey(), detailRes.getUserInfo().getIsDormant());

				/** MQ 연동(휴대기기 삭제) */
				RemoveDeviceAmqpSacReq mqInfo = new RemoveDeviceAmqpSacReq();
				try {
					mqInfo.setWorkDt(DateUtil.getToday("yyyyMMddHHmmss"));
					mqInfo.setUserKey(detailRes.getUserInfo().getUserKey());
					mqInfo.setDeviceKey(detailRes.getDeviceInfoList().get(0).getDeviceKey());
					mqInfo.setDeviceId(detailRes.getDeviceInfoList().get(0).getDeviceId());
					mqInfo.setSvcMangNo(detailRes.getDeviceInfoList().get(0).getSvcMangNum());
					mqInfo.setChgCaseCd(MemberConstants.GAMECENTER_WORK_CD_MOBILENUMBER_DELETE);

					this.memberDelDeviceAmqpTemplate.convertAndSend(mqInfo);
				} catch (AmqpException ex) {
					LOGGER.info("MQ process fail {}", mqInfo);
				}

				LOGGER.info("{} IDP ID 휴대기기 삭제처리", deviceId);
			}

		} catch (StorePlatformException e) {
			// ignore Exception
		}
	}

	@Override
	public void removeUser(SacRequestHeader requestHeader, String userId) {

		DetailReq detailReq = new DetailReq();
		detailReq.setUserId(userId);
		SearchExtentReq searchExtent = new SearchExtentReq();
		searchExtent.setUserInfoYn(MemberConstants.USE_Y);
		searchExtent.setDeviceInfoYn(MemberConstants.USE_Y);
		detailReq.setSearchExtent(searchExtent);
		DetailV2Res detailRes = this.userSearchService.detailV2(requestHeader, detailReq);

		/** MQ 연동 (회원 탈퇴) */
		StringBuffer buf = new StringBuffer();
		String mqDeviceStr = "";
		if (detailRes.getDeviceInfoList() != null && detailRes.getDeviceInfoList().size() > 0) {
			for (DeviceInfo deviceInfo : detailRes.getDeviceInfoList()) { // 휴대기기 정보가 여러건인경우 | 로 구분하여 MQ로 모두 전달
				buf.append(deviceInfo.getDeviceId()).append("|");
			}
			mqDeviceStr = buf.toString();
			mqDeviceStr = mqDeviceStr.substring(0, mqDeviceStr.lastIndexOf("|"));
		}
		RemoveMemberAmqpSacReq mqInfo = new RemoveMemberAmqpSacReq();
		mqInfo.setUserId(detailRes.getUserInfo().getUserId());
		mqInfo.setUserKey(detailRes.getUserInfo().getUserKey());
		mqInfo.setWorkDt(DateUtil.getToday("yyyyMMddHHmmss"));

		List<UserExtraInfo> list = detailRes.getUserInfo().getUserExtraInfoList();
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				UserExtraInfo extraInfo = list.get(i);
				if (StringUtils.equals(MemberConstants.USER_EXTRA_PROFILEIMGPATH, extraInfo.getExtraProfile())) {
					mqInfo.setProfileImgPath(extraInfo.getExtraProfileValue());
				}
			}
		}

		if (StringUtils.isNotBlank(mqDeviceStr)) {
			mqInfo.setDeviceId(mqDeviceStr);
		}
		try {

			this.memberRetireAmqpTemplate.convertAndSend(mqInfo);

		} catch (AmqpException ex) {
			LOGGER.error("MQ process fail {}", mqInfo);
		}

		// 회원탈퇴처리
		this.rem(requestHeader, detailRes.getUserInfo().getUserKey(), detailRes.getUserInfo().getIsDormant());
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
		try {
			this.idpSCI.secedeForWap(ecReq);
		} catch (StorePlatformException e) {
			if (!StringUtils.equals(e.getErrorInfo().getCode(), MemberConstants.EC_IDP_ERROR_CODE_TYPE
					+ IdpConstants.IDP_RES_CODE_NOT_EXIST_MDN)) {
				throw e;
			}
		}
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
	 * @param isDormant
	 *            휴면계정 유무
	 */
	public void rem(SacRequestHeader requestHeader, String userKey, String isDormant) {

		LOGGER.info("SC 탈퇴 요청 userKey:{}", userKey);
		RemoveUserRequest scReq = new RemoveUserRequest();
		scReq.setCommonRequest(this.mcc.getSCCommonRequest(requestHeader));
		scReq.setUserKey(userKey);
		scReq.setSecedeReasonCode(MemberConstants.USER_WITHDRAW_CLASS_USER_SELECTED);
		scReq.setSecedeReasonMessage("");
		scReq.setIsDormant(isDormant);
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
	 * @param isDormant
	 *            휴면계정유무
	 */
	public void deviceIdInvalid(SacRequestHeader requestHeader, String userKey, String deviceId, String isDormant) {

		/**
		 * SC 휴대기기 단건 조회.
		 */
		DeviceInfo deviceInfo = this.deviceService.srhDevice(requestHeader, MemberConstants.KEY_TYPE_DEVICE_ID,
				deviceId, userKey);

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
		removeDeviceRequest.setIsDormant(isDormant);
		this.deviceSCI.removeDevice(removeDeviceRequest);

	}

	public void deviceIdInvalidByDeviceKey(SacRequestHeader requestHeader, String userKey, String deviceKey,
			String isDormant) {

		List<String> removeKeyList = new ArrayList<String>();
		removeKeyList.add(deviceKey);

		RemoveDeviceRequest removeDeviceRequest = new RemoveDeviceRequest();
		removeDeviceRequest.setCommonRequest(this.mcc.getSCCommonRequest(requestHeader));
		removeDeviceRequest.setUserKey(userKey);
		removeDeviceRequest.setDeviceKey(removeKeyList);
		removeDeviceRequest.setIsDormant(isDormant);
		this.deviceSCI.removeDevice(removeDeviceRequest);

	}
}
