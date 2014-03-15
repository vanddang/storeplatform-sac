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
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.external.client.idp.sci.IdpSCI;
import com.skplanet.storeplatform.external.client.idp.vo.JoinForWapEcReq;
import com.skplanet.storeplatform.external.client.idp.vo.SecedeForWapEcReq;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.member.client.user.sci.DeviceSCI;
import com.skplanet.storeplatform.member.client.user.sci.vo.CheckSaveNSyncRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.CheckSaveNSyncResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.ReviveUserRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.ReviveUserResponse;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.member.common.MemberCommonComponent;
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
	private IdpSCI idpSCI;

	@Autowired
	private MemberCommonComponent mcc;

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

		if (StringUtils.equals(isSaveNSync, MemberConstants.USE_Y)) { // 변동성 대상

			LOGGER.info("## >> ★★★  변동성 대상!!!");
			LOGGER.info("## >> ★★★  preDeviceId : {}", preDeviceId);

			/**
			 * 변동성 대상 회원의 상태를 확인한다.
			 */
			if (StringUtils.equals(isActive, MemberConstants.USE_Y)) { // 정상 회원

				/**
				 * 기존 IDP 모바일 회원 탈퇴.
				 */
				this.secedeForWap(preDeviceId);

			}

			/**
			 * IDP 모바일 회원 신규 가입후에 SC 회원 복구 요청.
			 */
			this.reviveUser(sacHeader, userKey, deviceId);

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

			LOGGER.info("## IDP 회원 가입 Error 시에 Skip......");
			LOGGER.info("## errorCode : {}", spe.getErrorInfo().getCode());
			LOGGER.info("## errorMsg  : {}", spe.getErrorInfo().getMessage());

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

		try {

			/**
			 * IDP 모바일 회원 가입 탈퇴 요청.
			 */
			LOGGER.info("## IDP 모바일 회원 가입 탈퇴 요청.");
			SecedeForWapEcReq ecReq = new SecedeForWapEcReq();
			ecReq.setUserMdn(preDeviceId);
			this.idpSCI.secedeForWap(ecReq);

		} catch (StorePlatformException spe) {

			LOGGER.info("## IDP 회원 탈퇴 Error 시에 Skip......");
			LOGGER.info("## errorCode : {}", spe.getErrorInfo().getCode());
			LOGGER.info("## errorMsg  : {}", spe.getErrorInfo().getMessage());

		}

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
	 */
	private void reviveUser(SacRequestHeader sacHeader, String userKey, String deviceId) {

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
		ReviveUserResponse reviveUserResponse = this.deviceSCI.reviveUser(reviveUserRequest);
		LOGGER.info("## >> reviveUserResponse : {}", reviveUserResponse);

	}
}
