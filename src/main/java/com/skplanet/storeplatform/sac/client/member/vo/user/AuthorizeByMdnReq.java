package com.skplanet.storeplatform.sac.client.member.vo.user;

import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.DeviceExtraInfo;

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
	@NotEmpty(message = "파라미터가 존재하지 않습니다.")
	private String deviceId;

	/**
	 * 기기 ID 타입 (msisdn, uuid, macaddress)
	 */
	@NotEmpty(message = "파라미터가 존재하지 않습니다.")
	private String deviceIdType;

	/**
	 * 이동 통신사
	 */
	@NotEmpty(message = "파라미터가 존재하지 않습니다.")
	private String deviceTelecom;

	/**
	 * 기기 고유 번호
	 */
	private String nativeId;

	/**
	 * 기기 계정(Gmail 주소)
	 */
	private String deviceAccount;

	/**
	 * 자동 업데이트 여부
	 */
	private String isAutoUpdate;

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

	public String getNativeId() {
		return this.nativeId;
	}

	public void setNativeId(String nativeId) {
		this.nativeId = nativeId;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
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

	public String getIsAutoUpdate() {
		return this.isAutoUpdate;
	}

	public void setIsAutoUpdate(String isAutoUpdate) {
		this.isAutoUpdate = isAutoUpdate;
	}

}
