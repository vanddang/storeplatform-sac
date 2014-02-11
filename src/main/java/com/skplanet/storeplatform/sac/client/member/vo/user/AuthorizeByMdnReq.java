package com.skplanet.storeplatform.sac.client.member.vo.user;

import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.DeviceExtraInfo;

/**
 * [REQUEST] 모바일 전용 회원 인증(MDN 인증).
 * 
 * Updated on : 2014. 1. 3. Updated by : 반범진. 지티소프트.
 */
public class AuthorizeByMdnReq extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/**
	 * 기기 ID.
	 */
	@NotEmpty(message = "파라미터가 존재하지 않습니다.")
	private String deviceId;

	/**
	 * 기기 ID 타입 (msisdn, uuid, macaddress).
	 */
	@NotEmpty(message = "파라미터가 존재하지 않습니다.")
	private String deviceIdType;

	/**
	 * 이동 통신사.
	 */
	@NotEmpty(message = "파라미터가 존재하지 않습니다.")
	private String deviceTelecom;

	/**
	 * 기기 고유 번호.
	 */
	private String nativeId;

	/**
	 * 기기 고유 번호 비교여부.
	 */
	private String isNativeIdAuth;

	/**
	 * 기기 계정(Gmail 주소).
	 */
	private String deviceAccount;

	/**
	 * 자동 업데이트 여부.
	 */
	private String isAutoUpdate;

	/**
	 * 휴대기기 부가정보 리스트.
	 */
	private List<DeviceExtraInfo> userDeviceExtraInfo;

	/**
	 * @return deviceId
	 */
	public String getDeviceId() {
		return this.deviceId;
	}

	/**
	 * @param deviceId
	 *            String
	 */
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	/**
	 * @return nativeId
	 */
	public String getNativeId() {
		return this.nativeId;
	}

	/**
	 * @param nativeId
	 *            String
	 */
	public void setNativeId(String nativeId) {
		this.nativeId = nativeId;
	}

	/**
	 * @return deviceAccount
	 */
	public String getDeviceAccount() {
		return this.deviceAccount;
	}

	/**
	 * @param deviceAccount
	 *            String
	 */
	public void setDeviceAccount(String deviceAccount) {
		this.deviceAccount = deviceAccount;
	}

	/**
	 * @return deviceTelecom
	 */
	public String getDeviceTelecom() {
		return this.deviceTelecom;
	}

	/**
	 * @param deviceTelecom
	 *            String
	 */
	public void setDeviceTelecom(String deviceTelecom) {
		this.deviceTelecom = deviceTelecom;
	}

	/**
	 * @return deviceIdType
	 */
	public String getDeviceIdType() {
		return this.deviceIdType;
	}

	/**
	 * @param deviceIdType
	 *            String
	 */
	public void setDeviceIdType(String deviceIdType) {
		this.deviceIdType = deviceIdType;
	}

	/**
	 * @return userDeviceExtraInfo
	 */
	public List<DeviceExtraInfo> getUserDeviceExtraInfo() {
		return this.userDeviceExtraInfo;
	}

	/**
	 * @param userDeviceExtraInfo
	 *            List<DeviceExtraInfo>
	 */
	public void setUserDeviceExtraInfo(List<DeviceExtraInfo> userDeviceExtraInfo) {
		this.userDeviceExtraInfo = userDeviceExtraInfo;
	}

	/**
	 * @return isAutoUpdate
	 */
	public String getIsAutoUpdate() {
		return this.isAutoUpdate;
	}

	/**
	 * @param isAutoUpdate
	 *            String
	 */
	public void setIsAutoUpdate(String isAutoUpdate) {
		this.isAutoUpdate = isAutoUpdate;
	}

	/**
	 * @return isNativeIdAuth
	 */
	public String getIsNativeIdAuth() {
		return this.isNativeIdAuth;
	}

	/**
	 * @param isNativeIdAuth
	 *            String
	 */
	public void setIsNativeIdAuth(String isNativeIdAuth) {
		this.isNativeIdAuth = isNativeIdAuth;
	}

}
