/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.common.service;

import com.skplanet.storeplatform.sac.purchase.order.dummy.vo.DummyDevice;
import com.skplanet.storeplatform.sac.purchase.order.dummy.vo.DummyMember;

/**
 * 
 * 회원Part 정보 조회 서비스 인터페이스
 * 
 * Updated on : 2014. 1. 22. Updated by : 이승택, nTels.
 */
public interface PurchaseMemberPartService {

	/**
	 * 
	 * <pre>
	 * Dummy 회원정보 조회.
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
	 * @return 회원정보
	 */
	public DummyMember searchDummyUserDetail(String tenantId, String systemId, String userKey, String deviceKey);

	/**
	 * 
	 * <pre>
	 * Dummy 단말정보 조회.
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
	 * @return 단말정보
	 */
	public DummyDevice searchDummyDeviceDetail(String tenantId, String systemId, String userKey, String deviceKey);
}
