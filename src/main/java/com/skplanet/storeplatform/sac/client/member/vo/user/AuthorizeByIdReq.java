package com.skplanet.storeplatform.sac.client.member.vo.user;

import java.io.Serializable;
import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.DeviceExtraInfo;

/**
 * [REQUEST] ID 기반 회원 인증 (One ID, IDP 회원)
 * 
 * Updated on : 2014. 1. 3. Updated by : 반범진. 지티소프트.
 */
public class AuthorizeByIdReq extends CommonInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 사용자 아이디
	 */
	@NotEmpty(message = "파라미터가 존재하지 않습니다.")
	private String userId;

	/**
	 * 사용자 패스워드
	 */
	@NotEmpty(message = "파라미터가 존재하지 않습니다.")
	private String userPw;

	/**
	 * 기기 ID
	 */
	private String deviceId;

	/**
	 * 기기 ID 타입 (msisdn, uuid, macaddress)
	 */
	private String deviceIdType;

	/**
	 * 기기 계정(Gmail)
	 */
	private String deviceAccount;

	/**
	 * 이동 통신사
	 */
	private String deviceTelecom;

	/**
	 * 기기 고유 번호
	 */
	private String nativeId;

	/**
	 * 클라이언트 ip
	 */
	private String ipAddress;

	/**
	 * 계정잠금 해제 요청(Y/N)
	 */
	private String releaseLock;

	/**
	 * 휴대기기 부가정보 리스트
	 */
	private List<DeviceExtraInfo> userDeviceExtraInfo;

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

	public String getDeviceAccount() {
		return this.deviceAccount;
	}

	public void setDeviceAccount(String deviceAccount) {
		this.deviceAccount = deviceAccount;
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

	public List<DeviceExtraInfo> getUserDeviceExtraInfo() {
		return this.userDeviceExtraInfo;
	}

	public void setUserDeviceExtraInfo(List<DeviceExtraInfo> userDeviceExtraInfo) {
		this.userDeviceExtraInfo = userDeviceExtraInfo;
	}

	public String getNativeId() {
		return this.nativeId;
	}

	public void setNativeId(String nativeId) {
		this.nativeId = nativeId;
	}

	public String getIpAddress() {
		return this.ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getReleaseLock() {
		return this.releaseLock;
	}

	public void setReleaseLock(String releaseLock) {
		this.releaseLock = releaseLock;
	}

}
