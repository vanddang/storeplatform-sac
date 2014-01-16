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
	 *            테넌트ID
	 * @param insdUsermbrNo
	 *            내부 회원 NO
	 * @param insdDeviceId
	 *            내부 디바이스 ID
	 * @return Dummy 회원정보
	 */
	public DummyMember getMemberInfo(String tenantId, String insdUsermbrNo, String insdDeviceId) {
		DummyMember dummy = new DummyMember();
		dummy.setTenantId(tenantId);
		dummy.setInsdUsermbrNo(insdUsermbrNo);
		dummy.setInsdDeviceId(insdDeviceId);
		dummy.setMdn("01046353524");
		dummy.setAge(20);
		dummy.setbAvailableMember(true);
		dummy.setbLogin(true);

		return dummy;
	}

	/**
	 * <pre>
	 * 단말 정보 조회.
	 * </pre>
	 * 
	 * @param tenantId
	 *            테넌트ID
	 * @param insdUsermbrNo
	 *            내부 회원 NO
	 * @param insdDeviceId
	 *            내부 디바이스 ID
	 * @return Dummy 단말정보
	 */
	public DummyMember getDeviceInfo(String tenantId, String insdUsermbrNo, String insdDeviceId) {
		DummyMember dummy = new DummyMember();
		dummy.setTenantId(tenantId);
		dummy.setInsdUsermbrNo(insdUsermbrNo);
		dummy.setInsdDeviceId(insdDeviceId);
		dummy.setMdn("01046353524");
		dummy.setAge(20);
		dummy.setbAvailableMember(true);
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
