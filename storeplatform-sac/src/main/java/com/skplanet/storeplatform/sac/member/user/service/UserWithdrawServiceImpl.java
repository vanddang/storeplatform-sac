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

import com.skplanet.storeplatform.external.client.idp.sci.IdpSCI;
import com.skplanet.storeplatform.external.client.idp.sci.ImIdpSCI;
import com.skplanet.storeplatform.external.client.idp.vo.SecedeForWapEcReq;
import com.skplanet.storeplatform.external.client.idp.vo.SecedeUserEcReq;
import com.skplanet.storeplatform.external.client.idp.vo.imidp.DiscardUserEcReq;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.member.client.user.sci.DeviceSCI;
import com.skplanet.storeplatform.member.client.user.sci.UserSCI;
import com.skplanet.storeplatform.member.client.user.sci.vo.RemoveDeviceRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.RemoveUserRequest;
import com.skplanet.storeplatform.sac.api.util.DateUtil;
import com.skplanet.storeplatform.sac.client.member.vo.common.DeviceInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.UserExtraInfo;
import com.skplanet.storeplatform.sac.client.member.vo.user.DetailReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.DetailV2Res;
import com.skplanet.storeplatform.sac.client.member.vo.user.RemoveDeviceAmqpSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.RemoveMemberAmqpSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.SearchExtentReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.WithdrawReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.WithdrawRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.member.common.MemberCommonComponent;
import com.skplanet.storeplatform.sac.member.common.constant.IdpConstants;
import com.skplanet.storeplatform.sac.member.common.constant.MemberConstants;
import com.skplanet.storeplatform.sac.member.common.util.ValidationCheckUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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

		DetailReq detailReq = new DetailReq();

		/** 1. 회원 정보 조회. */
		SearchExtentReq searchExtent = new SearchExtentReq();
		searchExtent.setUserInfoYn(MemberConstants.USE_Y);
		searchExtent.setDeviceInfoYn(MemberConstants.USE_Y);
		detailReq.setDeviceId(req.getDeviceId());
		detailReq.setSearchExtent(searchExtent);

		DetailV2Res detailRes = this.userSearchService.detailV2(requestHeader, detailReq);

		/**
		 * 2. 소셜아이디인 경우 userAuthToken 이 필수, userAuthToken 인증(S2S)후 회원 탈퇴.
		 *   - mdn 회원은 인증 단계 없이 탈퇴
		 *   - 기존 Tstore 아이디는 단말에서 id/pwd 인증 단계 후에 탈퇴 진행되므로 인증 불필요
		 *   - 신규 소셜 계정 아이디는 인증단계가 없으므로 userId/userAuthToken 인증 필요
		 */
		if (StringUtils.equals(detailRes.getUserInfo().getUserType(), MemberConstants.USER_TYPE_FACEBOOK)
				|| StringUtils.equals(detailRes.getUserInfo().getUserType(), MemberConstants.USER_TYPE_GOOGLE)
				|| StringUtils.equals(detailRes.getUserInfo().getUserType(), MemberConstants.USER_TYPE_NAVER)) {
			if (StringUtils.isBlank(req.getUserAuthToken())) {
				throw new StorePlatformException("SAC_MEM_0001", "userAuthToken");
			} else {
				LOGGER.info("소셜 아이디(Facebook, google, naver) > userAuthToken 인증(S2S) 시작");
				boolean isCheckToken = true;
				LOGGER.info("현재는 by pass");
				if (!isCheckToken) {
					throw new StorePlatformException("SAC_MEM_1204");
				}
			}
		}

		/**
		 *  3-1. 요청 파라미터에 따라서 분기 처리한다.
		 *  아이디, 휴대기기 삭제처리, MQ연동 : memberRetireAmqpTemplate
		 *   - userId 탈퇴요청
		 *   - 모바일회원
		 */
		if( StringUtils.isNotBlank(req.getUserId())
				|| StringUtils.equals(detailRes.getUserInfo().getUserType(), MemberConstants.USER_TYPE_MOBILE) ) {

			LOGGER.info("userId요청 혹은 모바일 회원 > 회원 탈퇴");

			/** 3-1-1. 회원 탈퇴. */
			this.rem(requestHeader, detailRes.getUserInfo().getUserKey(),
					detailRes.getUserInfo().getIsDormant(), req.getDeviceId());

			/** 3-1-2. MQ 연동 (회원 탈퇴). */
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
		/**
		 * 3-2. 휴대기기만 invalid 처리
		 * 	 - 휴대기기 invalid, MQ연동 : memberDelDeviceAmqpTemplate
		 * 	 - deviceId 탈퇴요청
		 * 	 - T store 회원, 소셜아이디회원 ( 모바일회원이 아닌경우 )
		 */
		} else {

			LOGGER.info("deviceId 요청 중 모바일회원이 아닌 경우 > 휴대기기 invalid 처리");

			/** 3-2-1. 휴대기기 정보 조회. */
			String keyType = MemberConstants.KEY_TYPE_MDN;
			if ( ValidationCheckUtils.isDeviceId(req.getDeviceId()) ) {
				keyType = MemberConstants.KEY_TYPE_DEVICE_ID;
			}

			DeviceInfo deviceInfo = this.deviceService.srhDevice(requestHeader, keyType,
					req.getDeviceId(), detailRes.getUserInfo().getUserKey());

			/** 3-2-2. 휴대기기 invalid 처리. */
			this.deviceIdInvalid(requestHeader, detailRes.getUserInfo().getUserKey(), req.getDeviceId(),
					detailRes.getUserInfo().getIsDormant());

			/** 3-2-3. MQ 연동 (휴대기기 삭제). */
			RemoveDeviceAmqpSacReq mqInfo = new RemoveDeviceAmqpSacReq();
			try {
				mqInfo.setWorkDt(DateUtil.getToday("yyyyMMddHHmmss"));
				mqInfo.setUserKey(detailRes.getUserInfo().getUserKey());
				mqInfo.setDeviceKey(deviceInfo.getDeviceKey());
				mqInfo.setDeviceId(deviceInfo.getDeviceId());
				mqInfo.setSvcMangNo(deviceInfo.getSvcMangNum());
				mqInfo.setChgCaseCd(MemberConstants.GAMECENTER_WORK_CD_MOBILENUMBER_DELETE);

				this.memberDelDeviceAmqpTemplate.convertAndSend(mqInfo);
			} catch (AmqpException ex) {
				LOGGER.info("MQ process fail {}", mqInfo);

			}
		}

		LOGGER.info("MQ 탈퇴처리, DB 탈퇴처리 모두 완료.");

		/** 결과 세팅. */
		WithdrawRes response = new WithdrawRes();
		response.setUserKey(detailRes.getUserInfo().getUserKey());
		return response;

	}

	/**
	 * <pre>
	 * 휴대기기 invalid 처리
	 * </pre>
	 * 
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param mdn
	 *            String
	 */
	@Override
	public void removeDevice(SacRequestHeader requestHeader, String mdn) {

		/**
		 * deviceId로 회원 정보 조회.
		 */
		DetailReq detailReq = new DetailReq();
		detailReq.setDeviceId(mdn);
		SearchExtentReq searchExtent = new SearchExtentReq();
		searchExtent.setUserInfoYn(MemberConstants.USE_Y);
		searchExtent.setDeviceInfoYn(MemberConstants.USE_Y);
		detailReq.setSearchExtent(searchExtent);

		try {

			DetailV2Res detailRes = this.userSearchService.detailV2(requestHeader, detailReq);

			if (StringUtils.equals(detailRes.getUserInfo().getUserType(), MemberConstants.USER_TYPE_MOBILE)) {

				this.rem(requestHeader, detailRes.getUserInfo().getUserKey(),
						detailRes.getUserInfo().getIsDormant(), mdn);

				/**
				 * MQ 연동(회원 탈퇴).
				 */
				RemoveMemberAmqpSacReq mqInfo = new RemoveMemberAmqpSacReq();

				try {

					mqInfo.setUserId(detailRes.getUserInfo().getUserId());
					mqInfo.setUserKey(detailRes.getUserInfo().getUserKey());
					mqInfo.setWorkDt(DateUtil.getToday("yyyyMMddHHmmss"));
					mqInfo.setDeviceId(mdn);
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

				LOGGER.info("{} 모바일회원 탈퇴처리", mdn);

			} else {

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

				LOGGER.info("{} IDP ID 휴대기기 삭제처리", mdn);
			}

		} catch (StorePlatformException e) {
			// ignore Exception
		}
	}

	@Override
	public void removeUser(SacRequestHeader requestHeader, String userId, String deviceId) {

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
		this.rem(requestHeader, detailRes.getUserInfo().getUserKey(),
				detailRes.getUserInfo().getIsDormant(), deviceId);
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
	 * @param deviceId
	 *            탈퇴 기기 ID (deviceId)
	 */
	public void rem(SacRequestHeader requestHeader, String userKey, String isDormant, String deviceId) {

		LOGGER.info("SC 탈퇴 요청 userKey:{}", userKey);
		RemoveUserRequest scReq = new RemoveUserRequest();
		scReq.setCommonRequest(this.mcc.getSCCommonRequest(requestHeader));
		scReq.setUserKey(userKey);
		scReq.setSecedeReasonMessage("");
		scReq.setIsDormant(isDormant);
		scReq.setBolterDeviceId(deviceId);
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

		/** deviceId mdn여부 확인 */
		String keyType = MemberConstants.KEY_TYPE_MDN;
		if( ValidationCheckUtils.isDeviceId(deviceId)){
			keyType = MemberConstants.KEY_TYPE_DEVICE_ID;
		}

		/**
		 * SC 휴대기기 단건 조회.
		 */
		DeviceInfo deviceInfo = this.deviceService.srhDevice(requestHeader, keyType, deviceId, userKey);

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
