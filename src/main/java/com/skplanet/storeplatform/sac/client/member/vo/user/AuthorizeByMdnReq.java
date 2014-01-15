package com.skplanet.storeplatform.sac.client.member.vo.user;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * [REQUEST] 모바일 전용 회원 인증(MDN 인증)
 * 
 * Updated on : 2014. 1. 3. Updated by : 반범진. 지티소프트.
 */
public class AuthorizeByMdnReq extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/**
	 * 기기 ID
	 */
	private String deviceId;

	/**
	 * 기기 ID 타입 (msisdn, uuid, macaddress)
	 */
	private String deviceIdType;

	/**
	 * 기기 고유 번호
	 */
	private String nativeId;

	/**
	 * 루팅 여부(Y/N)
	 */
	private String rooting;

	/**
	 * Gmail 주소
	 */
	private String deviceAccount;

	/**
	 * Gmail 주소
	 */
	private String deviceModelNo;

	/**
	 * 자동 업데이트 여부(Y/N)
	 */
	private String isAutoUpdate;

	/**
	 * 이동 통신사
	 */
	private String deviceTelecom;

	/**
	 * 삽클 버전
	 */
	private String scVer;

	/**
	 * 오리지널 OS 버전
	 */
	private String osVer;

	public String getDeviceId() {
		return this.deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getNativeId() {
		return this.nativeId;
	}

	public void setNativeId(String nativeId) {
		this.nativeId = nativeId;
	}

	public String getRooting() {
		return this.rooting;
	}

	public void setRooting(String rooting) {
		this.rooting = rooting;
	}

	public String getDeviceAccount() {
		return this.deviceAccount;
	}

	public void setDeviceAccount(String deviceAccount) {
		this.deviceAccount = deviceAccount;
	}

	public String getDeviceModelNo() {
		return this.deviceModelNo;
	}

	public void setDeviceModelNo(String deviceModelNo) {
		this.deviceModelNo = deviceModelNo;
	}

	public String getIsAutoUpdate() {
		return this.isAutoUpdate;
	}

	public void setIsAutoUpdate(String isAutoUpdate) {
		this.isAutoUpdate = isAutoUpdate;
	}

	public String getDeviceTelecom() {
		return this.deviceTelecom;
	}

	public void setDeviceTelecom(String deviceTelecom) {
		this.deviceTelecom = deviceTelecom;
	}

	public String getScVer() {
		return this.scVer;
	}

	public void setScVer(String scVer) {
		this.scVer = scVer;
	}

	public String getOsVer() {
		return this.osVer;
	}

	public void setOsVer(String osVer) {
		this.osVer = osVer;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getDeviceIdType() {
		return this.deviceIdType;
	}

	public void setDeviceIdType(String deviceIdType) {
		this.deviceIdType = deviceIdType;
	}

}
