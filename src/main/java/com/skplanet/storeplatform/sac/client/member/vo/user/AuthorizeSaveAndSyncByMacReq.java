package com.skplanet.storeplatform.sac.client.member.vo.user;

import java.io.Serializable;
import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.DeviceExtraInfo;

/**
 * [REQUEST] Save&Sync 인증.
 * 
 * Updated on : 2014. 3. 5. Updated by : 반범진. 지티소프트.
 */
public class AuthorizeSaveAndSyncByMacReq extends CommonInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 기기 ID.
	 */
	@NotEmpty(message = "파라미터가 존재하지 않습니다.")
	private String deviceId;

	/**
	 * mac 주소.
	 */
	@NotEmpty(message = "파라미터가 존재하지 않습니다.")
	private String macAddress;

	/**
	 * 기기 고유 번호.
	 */
	private String nativeId;

	/**
	 * 기기 계정(Gmail 주소).
	 */
	private String deviceAccount;

	/**
	 * 휴대기기 부가정보 리스트.
	 */
	private List<DeviceExtraInfo> deviceExtraInfoList;

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
	 * @return macAddress
	 */
	public String getMacAddress() {
		return this.macAddress;
	}

	/**
	 * @param macAddress
	 *            String
	 */
	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
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
	 * @return String
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
	 * @return deviceExtraInfoList
	 */
	public List<DeviceExtraInfo> getDeviceExtraInfoList() {
		return this.deviceExtraInfoList;
	}

	/**
	 * @param deviceExtraInfoList
	 *            List<DeviceExtraInfo>
	 */
	public void setDeviceExtraInfoList(List<DeviceExtraInfo> deviceExtraInfoList) {
		this.deviceExtraInfoList = deviceExtraInfoList;
	}

}
