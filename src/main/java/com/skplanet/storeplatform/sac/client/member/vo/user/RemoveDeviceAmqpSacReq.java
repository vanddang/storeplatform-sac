package com.skplanet.storeplatform.sac.client.member.vo.user;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * Tenant 연동 Mq Info(단말 삭제)
 * 
 * Updated on : 2014. 3. 8. Updated by : 이현, 다모아솔루션.
 */
public class RemoveDeviceAmqpSacReq extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/* 내부기기번호 */
	private String deviceKey;

	/* 기기 아이디 */
	private String deviceId;

	/* 서비스 관리 번호 */
	private String svcMangNo;

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
