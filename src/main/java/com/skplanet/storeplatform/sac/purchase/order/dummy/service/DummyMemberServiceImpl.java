/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.order.dummy.service;

import com.skplanet.storeplatform.sac.purchase.order.dummy.vo.DummyMember;

/**
 * 
 * Dummy 회원 서비스 구현체
 * 
 * Updated on : 2014. 1. 16. Updated by : 이승택, nTels.
 */
public class DummyMemberServiceImpl {
	/**
	 * <pre>
	 * 회원 정보 조회.
	 * </pre>
	 * 
	 * @param tenantId
	 *            테넌트 ID
	 * @param systemId
	 *            시스템 ID
	 * @param userKey
	 *            내부 회원 NO
	 * @param deviceKey
	 *            내부 디바이스 ID
	 * @return Dummy 회원정보
	 */
	public DummyMember getUserInfo(String tenantId, String systemId, String userKey, String deviceKey) {
		DummyMember dummy = new DummyMember();
		dummy.setTenantId(tenantId);
		dummy.setSystemId(systemId);
		dummy.setUserKey(userKey);
		dummy.setUserId("testid01");
		dummy.setDeviceKey(deviceKey);
		dummy.setDeviceId("01046353524");
		dummy.setUserTypeCd("US011501"); // 사용자 구분 코드 - US011501 : 기기 사용자 - US011502 : IDP 사용자 - US011503 : OneID 사용자 -
										 // null : Tstore 회원 아님
		dummy.setUserStatusCd("US010701"); // 회원상태코드: US010701-정상, US010702-탈퇴, US010703-대기(가가입), US010704-가입,
										   // US010705-전환, US010706 : 탈퇴 - US010707-승인대기
		dummy.setAge(20);
		dummy.setbLogin(true);

		return dummy;
	}

	/**
	 * <pre>
	 * 구매차단 여부 조회.
	 * </pre>
	 * 
	 * @param tenantId
	 *            테넌트ID
	 * @param insdUsermbrNo
	 *            내부 회원 NO
	 * @param insdDeviceId
	 *            내부 디바이스 ID
	 * @return 구매차단 여부
	 */
	public boolean isBlock(String tenantId, String insdUsermbrNo, String insdDeviceId) {
		if (this.isBlockDevice(tenantId, insdUsermbrNo, insdDeviceId))
			return true;
		else if (this.isBlockMember(tenantId, insdUsermbrNo))
			return true;

		return false;
	}

	// ==============================================================================================================

	/**
	 * <pre>
	 * 디바이스 기준 구매차단 조회.
	 * </pre>
	 * 
	 * @param tenantId
	 *            테넌트ID
	 * @param insdUsermbrNo
	 *            내부 회원 NO
	 * @param insdDeviceId
	 *            내부 디바이스 ID
	 * @return 구매차단 여부
	 */
	private boolean isBlockDevice(String tenantId, String insdUsermbrNo, String insdDeviceId) {
		return false;
	}

	/**
	 * <pre>
	 * 회원 기준 구매차단 조회.
	 * </pre>
	 * 
	 * @param tenantId
	 *            테넌트ID
	 * @param insdUsermbrNo
	 *            내부 회원 NO
	 * @return 구매차단 여부
	 */
	private boolean isBlockMember(String tenantId, String insdUsermbrNo) {
		return false;
	}
}
