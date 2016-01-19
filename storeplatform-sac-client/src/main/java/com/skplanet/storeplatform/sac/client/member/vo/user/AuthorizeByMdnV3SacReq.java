package com.skplanet.storeplatform.sac.client.member.vo.user;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.DeviceExtraInfo;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

/**
 * [REQUEST] 모바일 전용 회원 인증(MDN 인증) v3.
 * 
 * Updated on : 2016. 1. 13. Updated by : 반범진.
 */
public class AuthorizeByMdnV3SacReq extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/**
	 * 기기 ID.
	 */
	@NotEmpty
	private String deviceId;

	/**
	 * 기기 MDN.
	 */
	@NotEmpty
	private String mdn;

	/**
	 * 이동 통신사.
	 */
	@NotEmpty
	private String deviceTelecom;

	/**
	 * 기기 고유 번호.
	 */
	@NotEmpty
	private String nativeId;

	/**
	 * 가입자 식별 모듈 ID (USIM 일련번호).
	 */
	@NotEmpty
	private String simSerialNo;

	/**
	 * 접속 아이피
	 */
	private String deviceIp;

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

	/**
	 * @return the deviceIp
	 */
	public String getDeviceIp() {
		return this.deviceIp;
	}

	/**
	 * @param deviceIp
	 *            the deviceIp to set
	 */
	public void setDeviceIp(String deviceIp) {
		this.deviceIp = deviceIp;
	}

	/**
	 * @return the simSerialNo
	 */
	public String getSimSerialNo() {
		return simSerialNo;
	}

	/**
	 * @param simSerialNo
	 *            the simSerialNo to set
	 */
	public void setSimSerialNo(String simSerialNo) {
		this.simSerialNo = simSerialNo;
	}

	/**
	 * @return the mdn
	 */
	public String getMdn() {
		return mdn;
	}

	/**
	 * @param mdn
	 *            the mdn to set
	 */
	public void setMdn(String mdn) {
		this.mdn = mdn;
	}
}
