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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.external.client.idp.sci.IdpSCI;
import com.skplanet.storeplatform.external.client.idp.vo.JoinForWapEcReq;
import com.skplanet.storeplatform.external.client.idp.vo.SecedeForWapEcReq;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
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
	private IdpSCI idpSCI;

	@Autowired
	private MemberCommonComponent mcc;

	@Override
	public SaveAndSync checkSaveAndSync(String deviceId) {

		LOGGER.info("===================================");
		LOGGER.info("===== 변동성 대상 체크 공통 모듈 =====");
		LOGGER.info("===================================");

		/**
		 * 14일 이내 번호이동, 14일 이내 번호변경 체크.
		 * 
		 * TODO SC API 호출 해야함.
		 */
		boolean check = false;

		if (check) {

			/**
			 * TODO IDP 기존회원탈퇴
			 */

			/**
			 * TODO IDP 신규가입
			 */

			/**
			 * TODO SC API 호출...... 회원 복구
			 */

		}

		/**
		 * 결과 setting.
		 */
		SaveAndSync saveAndSync = new SaveAndSync();
		saveAndSync.setIsSaveAndSyncTarget(MemberConstants.USE_Y);
		saveAndSync.setPreUserKey("");
		saveAndSync.setPreDeviceKey("");

		LOGGER.info("## >> SaveAndSync : {}", saveAndSync);

		return saveAndSync;

	}

	/**
	 * <pre>
	 * IDP 모바일 회원 탈퇴.
	 * </pre>
	 * 
	 * @param deviceId
	 */
	private void secedeForWap(String deviceId) {

		try {

			/**
			 * IDP 모바일 회원 가입 탈퇴 요청.
			 */
			SecedeForWapEcReq ecReq = new SecedeForWapEcReq();
			ecReq.setUserMdn(deviceId);
			this.idpSCI.secedeForWap(ecReq);

		} catch (StorePlatformException spe) {

			LOGGER.info("## IDP 회원 탈퇴 Error 시에 Skip......");
			LOGGER.info("## errorCode : {}", spe.getErrorInfo().getCode());
			LOGGER.info("## errorMsg  : {}", spe.getErrorInfo().getMessage());

		}

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
			 * (IDP 연동) 무선회원 가입 (cmd - joinForWap)
			 */
			JoinForWapEcReq joinForWapEcReq = new JoinForWapEcReq();
			joinForWapEcReq.setUserMdn(deviceId);
			joinForWapEcReq.setMdnCorp(MemberConstants.NM_DEVICE_TELECOM_SKT); // SKT 고정 (변동성 회원은 SKT 만...)
			mbrNo = this.idpSCI.joinForWap(joinForWapEcReq).getUserKey();

		} catch (StorePlatformException spe) {

			LOGGER.info("## IDP 회원 가입 Error 시에 Skip......");
			LOGGER.info("## errorCode : {}", spe.getErrorInfo().getCode());
			LOGGER.info("## errorMsg  : {}", spe.getErrorInfo().getMessage());

		}

		return mbrNo;

	}

}
