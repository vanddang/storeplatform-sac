package com.skplanet.storeplatform.sac.client.member.vo.user;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * Tenant 연동 Amqp Info(회원 탈퇴 정보)
 * 
 * Updated on : 2014. 3. 8. Updated by : 이현, 다모아솔루션.
 */
public class RemoveMemberAmqpSacReq extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/* 작업일시 : YYYYMMDDHH24MISS */
	private String workDt;

	/* 회원번호 */
	private String userKey;

	/* 회원ID */
	private String userId;

	/**
	 * @return the workDt
	 */
	public String getWorkDt() {
		return this.workDt;
	}

	/**
	 * @param workDt
	 *            the workDt to set
	 */
	public void setWorkDt(String workDt) {
		this.workDt = workDt;
	}

	/**
	 * @return the userKey
	 */
	public String getUserKey() {
		return this.userKey;
	}

	/**
	 * @param userKey
	 *            the userKey to set
	 */
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return this.userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
}
