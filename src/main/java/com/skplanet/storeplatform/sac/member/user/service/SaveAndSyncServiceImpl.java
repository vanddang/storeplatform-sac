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

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.external.client.idp.sci.IdpSCI;
import com.skplanet.storeplatform.external.client.idp.vo.JoinForWapEcReq;
import com.skplanet.storeplatform.external.client.idp.vo.SecedeForWapEcReq;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.member.client.user.sci.DeviceSCI;
import com.skplanet.storeplatform.member.client.user.sci.UserSCI;
import com.skplanet.storeplatform.member.client.user.sci.vo.CheckSaveNSyncRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.CheckSaveNSyncResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.CreateDeviceRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.ReviveUserRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.ReviveUserResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.UpdateUserRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.UserMbr;
import com.skplanet.storeplatform.member.client.user.sci.vo.UserMbrDevice;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.member.common.MemberCommonComponent;
import com.skplanet.storeplatform.sac.member.common.constant.IdpConstants;
import com.skplanet.storeplatform.sac.member.common.constant.MemberConstants;
import com.skplanet.storeplatform.sac.member.common.vo.SaveAndSync;

/**
 * Save & Sync 관련 구현체.
 * 
 * Updated on : 2014. 3. 7. Updated by : 심대진, 다모아 솔루션.
 */
@Service
public class SaveAndSyncServiceImpl implements SaveAndSyncService {

	private static final Logger LOGGER = LoggerFactory.getLogger(SaveAndSyncServiceImpl.class);

	@Autowired
	private DeviceSCI deviceSCI;

	@Autowired
	private UserSCI userSCI;

	@Autowired
	private IdpSCI idpSCI;

	@Autowired
	private MemberCommonComponent mcc;

	@Value("#{propertiesForSac['member.ogg.internal.method.iscall']}")
	public boolean isCall;

	@Override
	public SaveAndSync checkSaveAndSync(SacRequestHeader sacHeader, String deviceId) {

		LOGGER.info("===================================");
		LOGGER.info("===== 변동성 대상 체크 공통 모듈 =====");
		LOGGER.info("===================================");

		/**
		 * SC 변동성 대상 체크. (번호변경 OR 번호이동)
		 */
		CheckSaveNSyncRequest checkSaveNSyncRequest = new CheckSaveNSyncRequest();
		checkSaveNSyncRequest.setCommonRequest(this.mcc.getSCCommonRequest(sacHeader));
		checkSaveNSyncRequest.setDeviceID(deviceId);
		CheckSaveNSyncResponse checkSaveNSyncResponse = this.deviceSCI.checkSaveNSync(checkSaveNSyncRequest);

		String isActive = checkSaveNSyncResponse.getIsActive(); // 정상여부.
		String isSaveNSync = checkSaveNSyncResponse.getIsSaveNSync(); // 변동성대상여부.
		String deviceKey = checkSaveNSyncResponse.getDeviceKey(); // 휴대기기 Key.
		String userKey = checkSaveNSyncResponse.getUserKey(); // 사용자 Key.
		String preDeviceId = checkSaveNSyncResponse.getPreDeviceID(); // 이전 MSISDN.
		String nowDeviceId = checkSaveNSyncResponse.getDeviceID(); // 현재 MSISDN.

		if (StringUtils.equals(isSaveNSync, MemberConstants.USE_Y)) { // 변동성 대상

			LOGGER.info("## >> ★★★  변동성 대상!!!");
			LOGGER.info("## >> ★★★  nowDeviceId : {}", nowDeviceId);
			LOGGER.info("## >> ★★★  preDeviceId  : {}", preDeviceId);

			/**
			 * 변동성 대상 회원의 상태를 확인한다.
			 */
			if (StringUtils.equals(isActive, MemberConstants.USE_Y)) { // 정상 회원

				// 번호 변경만.....

				/**
				 * IDP 무선회원 가입
				 */
				String mbrNo = this.joinForWap(deviceId);

				/**
				 * 기존 IDP 모바일 회원 탈퇴.
				 */
				this.secedeForWap(nowDeviceId);

				/**
				 * 회원 MBR_NO 업데이트
				 */
				this.modifyMbrNo(sacHeader, userKey, deviceKey, deviceId, mbrNo);

			} else {

				// 번호 이동만.....

				/**
				 * IDP 모바일 회원 신규 가입후에 SC 회원 복구 요청.
				 */
				this.reviveUser(sacHeader, userKey, deviceId, deviceKey);

			}

		} else {

			LOGGER.info("## >> ★★★  변동성 대상 아님!!!");

		}

		/**
		 * 결과 setting.
		 */
		SaveAndSync saveAndSync = new SaveAndSync();
		saveAndSync.setIsSaveAndSyncTarget(isSaveNSync); // 변동성 대상 여부 (Y/N)
		saveAndSync.setUserKey(userKey); // 사용자 Key
		saveAndSync.setDeviceKey(deviceKey); // 휴대기기 Key
		LOGGER.info("## >> SaveAndSync : {}", saveAndSync);

		return saveAndSync;

	}

	/**
	 * <pre>
	 * IDP 모바일 회원 가입.
	 * </pre>
	 * 
	 * @param deviceId
	 *            기기 ID
	 * @return IDP (MBR_NO)
	 */
	private String joinForWap(String deviceId) {

		String mbrNo = "";

		try {

			/**
			 * (IDP 연동) 무선회원 가입 요청 (cmd - joinForWap).
			 */
			LOGGER.info("## IDP 모바일 회원 가입 요청.");
			JoinForWapEcReq joinForWapEcReq = new JoinForWapEcReq();
			joinForWapEcReq.setUserMdn(deviceId);
			joinForWapEcReq.setMdnCorp(MemberConstants.NM_DEVICE_TELECOM_SKT); // 이동 통신사
			mbrNo = this.idpSCI.joinForWap(joinForWapEcReq).getUserKey();

		} catch (StorePlatformException spe) {

			/**
			 * 가가입일 경우 처리. (이경우에 걸리게 되면 테넌트에서 모바일전용회원가입을 시킨다. [** 신규 가입처리 되므로 회원정보가 복구 되지 않는다. 이때 회원이 클레임을 걸경우 수동으로
			 * 처리하기로함.])
			 */
			if (StringUtils.equals(spe.getErrorInfo().getCode(), MemberConstants.EC_IDP_ERROR_CODE_TYPE + IdpConstants.IDP_RES_CODE_ALREADY_JOIN)) {

				throw new StorePlatformException("SAC_MEM_0002", "회원(Save&Sync)");

			} else {

				throw spe;

			}

		}

		return mbrNo;

	}

	/**
	 * <pre>
	 * IDP 모바일 회원 탈퇴.
	 * </pre>
	 * 
	 * @param preDeviceId
	 *            이전 기기 ID
	 */
	private void secedeForWap(String preDeviceId) {

		/**
		 * IDP 모바일 회원 가입 탈퇴 요청.
		 */
		LOGGER.info("## IDP 모바일 회원 가입 탈퇴 요청.");
		SecedeForWapEcReq ecReq = new SecedeForWapEcReq();
		ecReq.setUserMdn(preDeviceId);
		this.idpSCI.secedeForWap(ecReq);

	}

	/**
	 * <pre>
	 * 회원 복구.
	 * </pre>
	 * 
	 * @param sacHeader
	 *            공통 헤더
	 * @param userKey
	 *            사용자 Key
	 * @param deviceId
	 *            기기 ID
	 * @param deviceKey
	 *            기기 Key
	 */
	private void reviveUser(SacRequestHeader sacHeader, String userKey, String deviceId, String deviceKey) {

		/**
		 * IDP 모바일 회원 가입.
		 */
		String newMbrNo = this.joinForWap(deviceId);

		/**
		 * SC 회원 복구 요청.
		 */
		ReviveUserRequest reviveUserRequest = new ReviveUserRequest();
		reviveUserRequest.setCommonRequest(this.mcc.getSCCommonRequest(sacHeader));
		reviveUserRequest.setImMbrNo(newMbrNo);
		reviveUserRequest.setUserKey(userKey);
		ReviveUserResponse reviveUserResponse = this.deviceSCI.reviveUser(reviveUserRequest);
		LOGGER.info("## >> reviveUserResponse : {}", reviveUserResponse);

		/**
		 * 구매/기타 UserKey 변경.(OGG 시에만 사용하고 그 이후에는 불필요 로직임.)
		 */
		this.mcc.excuteInternalMethod(this.isCall, sacHeader.getTenantHeader().getSystemId(), sacHeader.getTenantHeader().getTenantId(), newMbrNo, userKey, deviceKey, deviceKey);

	}

	/**
	 * <pre>
	 * 회원 MbrNo만 업데이트.
	 * </pre>
	 * 
	 * @param sacHeader
	 *            공통 헤더
	 * @param userKey
	 *            사용자 Key
	 * @param deviceKey
	 *            휴대기기 Key
	 * @param mbrNo
	 *            IDP Key
	 */
	private void modifyMbrNo(SacRequestHeader sacHeader, String userKey, String deviceKey, String deviceId, String mbrNo) {

		/**
		 * SC 사용자 회원 기본정보 수정 요청.
		 */
		UpdateUserRequest updateUserRequest = new UpdateUserRequest();
		updateUserRequest.setCommonRequest(this.mcc.getSCCommonRequest(sacHeader));
		UserMbr userMbr = new UserMbr();
		userMbr.setUserKey(userKey);
		userMbr.setImMbrNo(mbrNo); // MBR_NO
		updateUserRequest.setUserMbr(userMbr);
		this.userSCI.updateUser(updateUserRequest);

		/**
		 * 구매/기타 UserKey 변경.(OGG 시에만 사용하고 그 이후에는 불필요 로직임.)
		 */
		this.mcc.excuteInternalMethod(this.isCall, sacHeader.getTenantHeader().getSystemId(), sacHeader.getTenantHeader().getTenantId(), mbrNo, userKey, deviceKey, deviceKey);

		/**
		 * SC 단말 Device 업데이트.
		 */
		CreateDeviceRequest createDeviceRequest = new CreateDeviceRequest();
		createDeviceRequest.setCommonRequest(this.mcc.getSCCommonRequest(sacHeader));
		createDeviceRequest.setIsNew(MemberConstants.USE_N);
		createDeviceRequest.setUserKey(userKey);
		UserMbrDevice userMbrDevice = new UserMbrDevice();
		userMbrDevice.setUserKey(userKey);
		userMbrDevice.setDeviceKey(deviceKey);
		userMbrDevice.setDeviceID(deviceId); // 수정할 DeviceId
		createDeviceRequest.setUserMbrDevice(userMbrDevice);

		this.deviceSCI.createDevice(createDeviceRequest);

	}
}
