/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.member.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.skplanet.storeplatform.sac.client.internal.display.localsci.sci.ChangeDisplayUserSCI;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.ChangeDisplayUserSacReq;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.sci.PurchaseUserInfoInternalSCI;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.vo.UserInfoSacInReq;

/**
 * 내부 메서드 호출 기능을 공통 정의해서 사용한다.
 * 
 * Updated on : 2014. 5. 19. Updated by : 반범진, 지티소프트.
 */
@Component
public class MemberCommonInternalComponent {

	private static final Logger LOGGER = LoggerFactory.getLogger(MemberCommonInternalComponent.class);

	@Autowired
	private ChangeDisplayUserSCI changeDisplayUserSCI;

	@Autowired
	private PurchaseUserInfoInternalSCI purchaseUserInfoInternalSCI;

	/**
	 * <pre>
	 * 회원 OGG 관련 구매/기타 내부메서드 호출.
	 * </pre>
	 * 
	 * @param isCall
	 *            내부메서드 연동여부
	 * @param systemId
	 *            Sysetm ID
	 * @param tenantId
	 *            Tenant ID
	 * @param userKey
	 *            사용자 Key
	 * @param previousUserKey
	 *            이전 사용자 Key
	 * @param deviceKey
	 *            휴대기기 Key
	 * @param previousDeviceKey
	 *            이전 휴대기기 Key
	 */
	public void excuteInternalMethod(boolean isCall, String systemId, String tenantId, String userKey, String previousUserKey, String deviceKey,
			String previousDeviceKey) {

		if (isCall) {

			/* 1. 기타/전시 파트 userKey 변경 */
			ChangeDisplayUserSacReq changeDisplayUserSacReq = new ChangeDisplayUserSacReq();
			changeDisplayUserSacReq.setNewUseKey(userKey);
			changeDisplayUserSacReq.setOldUserKey(previousUserKey);
			changeDisplayUserSacReq.setTenantId(tenantId);
			LOGGER.info("changeDisplayUserSCI.changeUserKey request : {}", changeDisplayUserSacReq);
			this.changeDisplayUserSCI.changeUserKey(changeDisplayUserSacReq);

			/* 2. 구매 파트 userKey, deviceKey 변경 */
			UserInfoSacInReq userInfoSacInReq = new UserInfoSacInReq();
			userInfoSacInReq.setSystemId(systemId);
			userInfoSacInReq.setTenantId(tenantId);
			userInfoSacInReq.setNewDeviceKey(deviceKey);
			userInfoSacInReq.setDeviceKey(previousDeviceKey);
			userInfoSacInReq.setUserKey(previousUserKey);
			userInfoSacInReq.setNewUserKey(userKey);
			LOGGER.info("purchaseUserInfoInternalSCI.updateUserDevice request : {}", userInfoSacInReq);
			this.purchaseUserInfoInternalSCI.updateUserDevice(userInfoSacInReq);

		}

	}

	/**
	 * <pre>
	 * 기타/전시 파트 아이디 변경 요청.
	 * </pre>
	 * 
	 * @param req
	 *            ChangeDisplayUserSacReq
	 */
	public void changeUserId(ChangeDisplayUserSacReq req) {
		this.changeDisplayUserSCI.changeUserId(req);
	}

}
