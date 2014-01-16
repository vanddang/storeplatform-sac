package com.skplanet.storeplatform.sac.purchase.order.dummy.service;

import com.skplanet.storeplatform.sac.purchase.order.dummy.vo.DummyMember;

public class DummyMemberServiceImpl {
	/**
	 * 회원 정보 조회.
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
	 * 단말 정보 조회
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
	 * 구매차단 여부 조회
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
	 * 디바이스 기준 구매차단 조회
	 */
	private boolean isBlockDevice(String tenantId, String insdUsermbrNo, String insdDeviceId) {
		return false;
	}

	/**
	 * 회원 기준 구매차단 조회
	 */
	private boolean isBlockMember(String tenantId, String insdUsermbrNo) {
		return false;
	}
}
