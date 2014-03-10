package com.skplanet.storeplatform.sac.client.member.vo.user;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * Tenant 연동 Amqp Info(단말 수정)
 * 
 * Updated on : 2014. 3. 8. Updated by : 이현, 다모아솔루션.
 */
public class ModifyDeviceAmqpSacReq extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/* 작업일시 : YYYYMMDDHH24MISS */
	private String workDt;

	/* 회원 내부사용자키 */
	private String userKey;

	/* 회원 이전 내부사용자키 */
	private String oldUserKey;

	/* 내부기기번호 */
	private String deviceKey;

	/* 이전 내부기기번호 */
	private String oldDeviceKey;

	/* 기기 아이디 */
	private String deviceId;

	/* 기기 아이디 */
	private String oldDeviceId;

	/* 통신사코드 */
	private String mnoCd;

	/* 이전 통신사코드 */
	private String oldMnoCd;

	/* 변경유형코드 (번호이동/번호변경등) */
	private String chgCaseCd;

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

	/**
	 * @return the deviceKey
	 */
	public String getDeviceKey() {
		return this.deviceKey;
	}

	/**
	 * @param deviceKey
	 *            the deviceKey to set
	 */
	public void setDeviceKey(String deviceKey) {
		this.deviceKey = deviceKey;
	}

	/**
	 * @return the oldDeviceKey
	 */
	public String getOldDeviceKey() {
		return this.oldDeviceKey;
	}

	/**
	 * @param oldDeviceKey
	 *            the oldDeviceKey to set
	 */
	public void setOldDeviceKey(String oldDeviceKey) {
		this.oldDeviceKey = oldDeviceKey;
	}

	/**
	 * @return the deviceId
	 */
	public String getDeviceId() {
		return this.deviceId;
	}

	/**
	 * @param deviceId
	 *            the deviceId to set
	 */
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	/**
	 * @return the oldDeviceId
	 */
	public String getOldDeviceId() {
		return this.oldDeviceId;
	}

	/**
	 * @param oldDeviceId
	 *            the oldDeviceId to set
	 */
	public void setOldDeviceId(String oldDeviceId) {
		this.oldDeviceId = oldDeviceId;
	}

	/**
	 * @return the mnoCd
	 */
	public String getMnoCd() {
		return this.mnoCd;
	}

	/**
	 * @param mnoCd
	 *            the mnoCd to set
	 */
	public void setMnoCd(String mnoCd) {
		this.mnoCd = mnoCd;
	}

	/**
	 * @return the oldMnoCd
	 */
	public String getOldMnoCd() {
		return this.oldMnoCd;
	}

	/**
	 * @param oldMnoCd
	 *            the oldMnoCd to set
	 */
	public void setOldMnoCd(String oldMnoCd) {
		this.oldMnoCd = oldMnoCd;
	}

	/**
	 * @return the chgCaseCd
	 */
	public String getChgCaseCd() {
		return this.chgCaseCd;
	}

	/**
	 * @param chgCaseCd
	 *            the chgCaseCd to set
	 */
	public void setChgCaseCd(String chgCaseCd) {
		this.chgCaseCd = chgCaseCd;
	}

}
