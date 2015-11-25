package com.skplanet.storeplatform.sac.client.member.vo.user;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * Tenant 연동 Mq Info(단말 삭제)
 * 
 * Updated on : 2014. 3. 8. Updated by : 이현, 다모아솔루션.
 */
public class RemoveDeviceAmqpSacReq extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/* 작업일시 : YYYYMMDDHH24MISS */
	private String workDt;

	/* 회원 내부사용자키 */
	private String userKey;

	/* 내부기기번호 */
	private String deviceKey;

	/* 기기 아이디 */
	private String deviceId;

	/* 변경유형코드 (번호이동/번호변경등) */
	private String chgCaseCd;

	/* 서비스 관리 번호 */
	private String svcMangNo;

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

	/**
	 * @return the svcMangNo
	 */
	public String getSvcMangNo() {
		return this.svcMangNo;
	}

	/**
	 * @param svcMangNo
	 *            the svcMangNo to set
	 */
	public void setSvcMangNo(String svcMangNo) {
		this.svcMangNo = svcMangNo;
	}

}
