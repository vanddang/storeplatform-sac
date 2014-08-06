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

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
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
import com.skplanet.storeplatform.member.client.user.sci.vo.UpdateUserRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.UserMbr;
import com.skplanet.storeplatform.member.client.user.sci.vo.UserMbrDevice;
import com.skplanet.storeplatform.sac.api.util.DateUtil;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateDeviceAmqpSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.GameCenterSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.ModifyDeviceAmqpSacReq;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.member.common.MemberCommonComponent;
import com.skplanet.storeplatform.sac.member.common.MemberCommonInternalComponent;
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

	@Autowired
	private DeviceService deviceService;

	@Autowired
	private MemberCommonInternalComponent mcic;

	@Autowired
	@Resource(name = "memberModDeviceAmqpTemplate")
	private AmqpTemplate memberModDeviceAmqpTemplate;

	@Autowired
	@Resource(name = "memberAddDeviceAmqpTemplate")
	private AmqpTemplate memberAddDeviceAmqpTemplate;

	@Override
	public SaveAndSync checkSaveAndSync(SacRequestHeader sacHeader, String deviceId) {

		LOGGER.debug("===================================");
		LOGGER.debug("===== 변동성 대상 체크 공통 모듈 =====");
		LOGGER.debug("===================================");

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
		String preMbrNo = checkSaveNSyncResponse.getImMbrNo(); // 사용자 IDP Key
		String preDeviceId = checkSaveNSyncResponse.getPreDeviceID(); // 이전 MSISDN.
		String nowDeviceId = checkSaveNSyncResponse.getDeviceID(); // 현재 MSISDN.

		SaveAndSync saveAndSync = new SaveAndSync();
		String newMbrNo = null;
		String gcWorkCd = null;

		if (StringUtils.equals(isSaveNSync, MemberConstants.USE_Y)) { // 변동성 대상

			LOGGER.info("{} 변동성 대상({})", deviceId, StringUtils.equals(isActive, "Y") ? "번호변경" : "번호이동");
			LOGGER.info("{} CheckSaveNSyncResponse : isActive={},isSaveNSync={},deviceKey={},userKey={},preDeviceId={},nowDeviceId={}", deviceId,
					isActive, isSaveNSync, deviceKey, userKey, preDeviceId, nowDeviceId);

			/**
			 * 변동성 대상 회원의 상태를 확인한다.
			 */
			if (StringUtils.equals(isActive, MemberConstants.USE_Y)) { // 정상 회원

				// 번호 변경만.....

				/**
				 * IDP 무선회원 가입
				 */
				newMbrNo = this.joinForWap(deviceId);

				/**
				 * 기존 IDP 모바일 회원 탈퇴.
				 */
				this.secedeForWap(nowDeviceId);

				/**
				 * 회원 MBR_NO 업데이트
				 */
				this.modMbrNo(sacHeader, userKey, deviceKey, deviceId, newMbrNo);

				/** MQ 연동(번호변경) */
				ModifyDeviceAmqpSacReq mqInfo = new ModifyDeviceAmqpSacReq();
				try {
					mqInfo.setWorkDt(DateUtil.getToday("yyyyMMddHHmmss"));
					mqInfo.setUserKey(userKey);
					mqInfo.setOldUserKey(userKey);
					mqInfo.setDeviceKey(deviceKey);
					mqInfo.setOldDeviceKey(deviceKey);
					mqInfo.setDeviceId(deviceId);
					mqInfo.setOldDeviceId(preDeviceId);
					mqInfo.setMnoCd(MemberConstants.DEVICE_TELECOM_SKT);
					mqInfo.setOldMnoCd(MemberConstants.DEVICE_TELECOM_SKT);
					mqInfo.setChgCaseCd(MemberConstants.GAMECENTER_WORK_CD_MOBILENUMBER_CHANGE);
					LOGGER.debug("{} 번호변경 변동성 회원 MQ 정보 : {}", deviceId, mqInfo);
					this.memberModDeviceAmqpTemplate.convertAndSend(mqInfo);

				} catch (AmqpException ex) {
					LOGGER.info("MQ process fail {}", mqInfo);
				}

				gcWorkCd = MemberConstants.GAMECENTER_WORK_CD_MOBILENUMBER_CHANGE;

			} else {

				// 번호 이동만.....

				/**
				 * IDP 모바일 회원 신규 가입후에 SC 회원 복구 요청.
				 */
				newMbrNo = this.reviveUser(sacHeader, userKey, deviceId, deviceKey);

				/** MQ 연동(MDN 등록) */
				CreateDeviceAmqpSacReq mqInfo = new CreateDeviceAmqpSacReq();
				try {
					mqInfo.setWorkDt(DateUtil.getToday("yyyyMMddHHmmss"));
					mqInfo.setUserKey(userKey);
					mqInfo.setDeviceKey(deviceKey);
					mqInfo.setDeviceId(deviceId);
					mqInfo.setMnoCd(MemberConstants.DEVICE_TELECOM_SKT);
					this.memberAddDeviceAmqpTemplate.convertAndSend(mqInfo);
					LOGGER.debug("{} 번호이동 변동성 회원 MQ 정보 : {}", deviceId, mqInfo);
				} catch (AmqpException ex) {
					LOGGER.info("MQ process fail {}", mqInfo);
				}

				gcWorkCd = MemberConstants.GAMECENTER_WORK_CD_MOBILENUMBER_INSERT;

			}

			if (this.isCall) {
				/**
				 * 게임센터 연동.
				 */
				GameCenterSacReq gameCenterSacReq = new GameCenterSacReq();
				gameCenterSacReq.setUserKey(newMbrNo);
				gameCenterSacReq.setMbrNo(newMbrNo);
				gameCenterSacReq.setDeviceId(deviceId);
				gameCenterSacReq.setSystemId(sacHeader.getTenantHeader().getSystemId());
				gameCenterSacReq.setTenantId(sacHeader.getTenantHeader().getTenantId());
				gameCenterSacReq.setPreDeviceId(preDeviceId);
				gameCenterSacReq.setPreUserKey(userKey);
				gameCenterSacReq.setPreMbrNo(userKey);
				gameCenterSacReq.setWorkCd(gcWorkCd);
				this.deviceService.regGameCenterIF(gameCenterSacReq);
				saveAndSync.setUserKey(newMbrNo); // OGG연동시에는 mbr_no가 userKey가 된다.
			} else {
				/**
				 * 게임센터 연동.
				 */
				GameCenterSacReq gameCenterSacReq = new GameCenterSacReq();
				gameCenterSacReq.setUserKey(userKey);
				gameCenterSacReq.setMbrNo(newMbrNo);
				gameCenterSacReq.setDeviceId(deviceId);
				gameCenterSacReq.setSystemId(sacHeader.getTenantHeader().getSystemId());
				gameCenterSacReq.setTenantId(sacHeader.getTenantHeader().getTenantId());
				gameCenterSacReq.setPreDeviceId(preDeviceId);
				gameCenterSacReq.setPreUserKey(userKey);
				gameCenterSacReq.setPreMbrNo(preMbrNo);
				gameCenterSacReq.setWorkCd(gcWorkCd);
				this.deviceService.regGameCenterIF(gameCenterSacReq);
				saveAndSync.setUserKey(userKey);
			}

			saveAndSync.setDeviceKey(deviceKey); // 휴대기기 Key

		} else {
			LOGGER.info("{} 변동성 대상 아님", deviceId);
		}

		/**
		 * 결과 setting.
		 */
		saveAndSync.setIsSaveAndSyncTarget(isSaveNSync); // 변동성 대상 여부 (Y/N)

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
			LOGGER.info("{} IDP 모바일 회원 가입", deviceId);
			JoinForWapEcReq joinForWapEcReq = new JoinForWapEcReq();
			joinForWapEcReq.setUserMdn(deviceId);
			joinForWapEcReq.setMdnCorp(MemberConstants.NM_DEVICE_TELECOM_SKT); // 이동 통신사
			mbrNo = this.idpSCI.joinForWap(joinForWapEcReq).getUserKey();

		} catch (StorePlatformException spe) {

			/**
			 * 가가입일 경우 처리. (이경우에 걸리게 되면 테넌트에서 모바일전용회원가입을 시킨다. [** 신규 가입처리 되므로
			 * 회원정보가 복구 되지 않는다. 이때 회원이 클레임을 걸경우 수동으로 처리하기로함.])
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

		try {

			/**
			 * IDP 모바일 회원 가입 탈퇴 요청.
			 */
			LOGGER.info("{} IDP 모바일 회원 탈퇴", preDeviceId);
			SecedeForWapEcReq ecReq = new SecedeForWapEcReq();
			ecReq.setUserMdn(preDeviceId);
			this.idpSCI.secedeForWap(ecReq);

		} catch (StorePlatformException spe) {

			LOGGER.info("{} IDP 모바일 회원 탈퇴 오류", preDeviceId);
			LOGGER.info("errorCode : {}", spe.getErrorInfo().getCode());
			LOGGER.info("errorMsg  : {}", spe.getErrorInfo().getMessage());

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
	 * @param deviceKey
	 *            기기 Key
	 * @return newMbrNo 신규가입된 IDP Key
	 */
	private String reviveUser(SacRequestHeader sacHeader, String userKey, String deviceId, String deviceKey) {

		/**
		 * IDP 모바일 회원 가입.
		 */
		String newMbrNo = this.joinForWap(deviceId);

		/**
		 * SC 회원 복구 요청.
		 */
		LOGGER.info("{} 신규가입한 IDP userKey로 회원 복구 처리 newMbrNo={}", deviceId, newMbrNo);
		ReviveUserRequest reviveUserRequest = new ReviveUserRequest();
		reviveUserRequest.setCommonRequest(this.mcc.getSCCommonRequest(sacHeader));
		reviveUserRequest.setImMbrNo(newMbrNo);
		reviveUserRequest.setUserKey(userKey);
		reviveUserRequest.setDeviceID(deviceId);
		reviveUserRequest.setDeviceKey(deviceKey);
		this.deviceSCI.reviveUser(reviveUserRequest);

		/**
		 * 구매/기타 UserKey 변경.(OGG 시에만 사용하고 그 이후에는 불필요 로직임.)
		 */
		this.mcic.excuteInternalMethod(this.isCall, sacHeader.getTenantHeader().getSystemId(), sacHeader.getTenantHeader().getTenantId(), newMbrNo,
				userKey, deviceKey, deviceKey);

		return newMbrNo;

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
	 * @param deviceId
	 *            휴대기기 mdn
	 * @param mbrNo
	 *            IDP Key
	 */
	private void modMbrNo(SacRequestHeader sacHeader, String userKey, String deviceKey, String deviceId, String mbrNo) {

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

		/**
		 * 신규가입한 mbr_no값으로 수정 요청.
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
		this.mcic.excuteInternalMethod(this.isCall, sacHeader.getTenantHeader().getSystemId(), sacHeader.getTenantHeader().getTenantId(), mbrNo,
				userKey, deviceKey, deviceKey);

	}

}
