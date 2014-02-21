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

import com.skplanet.storeplatform.sac.purchase.order.dummy.vo.DummyDevice;

/**
 * 
 * 단말 정보 조회 서비스 구현체
 * 
 * Updated on : 2014. 1. 22. Updated by : 이승택, nTels.
 */
public class DummyDeviceServiceImpl {

	/**
	 * <pre>
	 * 단말 정보 조회.
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
	 * @return Dummy 단말정보
	 */
	public DummyDevice getDeviceInfo(String tenantId, String systemId, String userKey, String deviceKey) {
		DummyDevice dummy = new DummyDevice();
		dummy.setTenantId(tenantId);
		dummy.setSystemId(systemId);
		dummy.setUserKey(userKey);
		dummy.setUserId("testid01");
		dummy.setDeviceKey(deviceKey);
		dummy.setDeviceId("01046353524");
		dummy.setDeviceIdType("msisdn"); // 기기 ID 타입 (msisdn, uuid, macaddress)
		dummy.setDeviceModelNo("SHV-E210S");
		dummy.setDeviceTelecom("1");

		return dummy;
	}
}
