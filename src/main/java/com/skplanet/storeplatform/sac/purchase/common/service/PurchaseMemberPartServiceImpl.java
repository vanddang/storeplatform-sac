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

import com.skplanet.storeplatform.sac.purchase.order.dummy.service.DummyDeviceServiceImpl;
import com.skplanet.storeplatform.sac.purchase.order.dummy.service.DummyMemberServiceImpl;
import com.skplanet.storeplatform.sac.purchase.order.dummy.vo.DummyDevice;
import com.skplanet.storeplatform.sac.purchase.order.dummy.vo.DummyMember;

/**
 * 
 * 회원Part 정보 조회 서비스 구현체
 * 
 * Updated on : 2014. 1. 22. Updated by : 이승택, nTels.
 */
public class PurchaseMemberPartServiceImpl implements PurchaseMemberPartService {

	private final DummyMemberServiceImpl userSearchService = new DummyMemberServiceImpl();
	private final DummyDeviceServiceImpl deviceSearchService = new DummyDeviceServiceImpl();

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
	@Override
	public DummyMember searchDummyUserDetail(String tenantId, String systemId, String userKey, String deviceKey) {
		return this.userSearchService.getUserInfo(tenantId, systemId, userKey, deviceKey);
	}

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
	@Override
	public DummyDevice searchDummyDeviceDetail(String tenantId, String systemId, String userKey, String deviceKey) {
		return this.deviceSearchService.getDeviceInfo(tenantId, systemId, userKey, deviceKey);
	}
}
