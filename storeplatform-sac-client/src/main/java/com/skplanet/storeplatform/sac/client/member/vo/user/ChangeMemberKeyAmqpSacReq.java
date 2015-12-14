package com.skplanet.storeplatform.sac.client.member.vo.user;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * Tenant 연동 Amqp Info(회원키 변경)
 * 
 * Updated on : 2015. 12. 22. Updated by : 반범진.
 */
public class ChangeMemberKeyAmqpSacReq extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/* 작업일시 : YYYYMMDDHH24MISS */
	private String workDt;

	/* 회원 내부사용자키 */
	private String userKey;

	/* 회원 이전 내부사용자키 */
	private String oldUserKey;

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
	 * @return the oldUserKey
	 */
	public String getOldUserKey() {
		return this.oldUserKey;
	}

	/**
	 * @param oldUserKey
	 *            the oldUserKey to set
	 */
	public void setOldUserKey(String oldUserKey) {
		this.oldUserKey = oldUserKey;
	}
}
