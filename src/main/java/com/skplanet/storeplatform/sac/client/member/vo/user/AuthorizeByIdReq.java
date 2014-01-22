package com.skplanet.storeplatform.sac.client.member.vo.user;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotEmpty;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * [REQUEST] ID 기반 회원 인증 (One ID, IDP 회원)
 * 
 * Updated on : 2014. 1. 3. Updated by : 반범진. 지티소프트.
 */
public class AuthorizeByIdReq extends CommonInfo implements Serializable {

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
	 * 사용자 아이디
	 */
	@NotEmpty(message = "필수 파라미터 입니다.")
	private String userId;

	/**
	 * 사용자 패스워드
	 */
	@NotEmpty(message = "필수 파라미터 입니다.")
	private String userPw;

	/**
	 * OS 버전
	 */
	private String osVer;

	/**
	 * 삽클 버전
	 */
	private String scVer;

	/**
	 * Gmail 주소
	 */
	private String deviceAccount;

	/**
	 * 기기 모델 번호
	 */
	private String deviceModelNo;

	/**
	 * 이동 통신사
	 */
	private String deviceTelecom;

	public String getDeviceId() {
		return this.deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserPw() {
		return this.userPw;
	}

	public void setUserPw(String userPw) {
		this.userPw = userPw;
	}

	public String getOsVer() {
		return this.osVer;
	}

	public void setOsVer(String osVer) {
		this.osVer = osVer;
	}

	public String getScVer() {
		return this.scVer;
	}

	public void setScVer(String scVer) {
		this.scVer = scVer;
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

	public String getDeviceTelecom() {
		return this.deviceTelecom;
	}

	public void setDeviceTelecom(String deviceTelecom) {
		this.deviceTelecom = deviceTelecom;
	}

	public String getDeviceIdType() {
		return this.deviceIdType;
	}

	public void setDeviceIdType(String deviceIdType) {
		this.deviceIdType = deviceIdType;
	}

}
