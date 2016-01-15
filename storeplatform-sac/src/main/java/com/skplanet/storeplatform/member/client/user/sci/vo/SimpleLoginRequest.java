/**
 * 
 */
package com.skplanet.storeplatform.member.client.user.sci.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.member.client.common.util.Utils;
import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;

/**
 * 심플 인증(간편 인증) 요청 VO
 * 
 * Updated on : 2016. 1. 15. Updated by : 최진호, 보고지티.
 */
public class SimpleLoginRequest extends CommonInfo {

	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	/** 공통 요청 Value Object. */
	private CommonRequest commonRequest;

	/** 휴대기기 ID (MDN). */
	private String mdn; // MDN

	/** 휴대기기 ID(UUID/MAC). */
	private String deviceID; // DEVICE_ID

	/** 접속_IP. */
	private String connIp;

	/** SC_버전. */
	private String scVersion;

	/** 자동로그인 여부. */
	private String isAutoLogin; // AUTO_LOGIN_YN

	/** 기기_모델_명. */
	private String deviceModelNm;

	/** 기기_OS_명. */
	private String deviceOsNm;

	/** 기기_OS_버전. */
	private String deviceOsVersion;

	/**
	 * 공통 요청 Value Object를 리턴한다.
	 * 
	 * @return commonRequest - 공통 요청 Value Object
	 */
	public CommonRequest getCommonRequest() {
		return this.commonRequest;
	}

	/**
	 * 공통 요청 Value Object를 설정한다.
	 * 
	 * @param commonRequest
	 *            공통 요청 Value Object
	 */
	public void setCommonRequest(CommonRequest commonRequest) {
		this.commonRequest = commonRequest;
	}

	/**
	 * 휴대기기 ID(MDN) 정보를 리턴한다.
	 *
	 * @return mdn - 휴대기기 ID(MDN) 정보
	 */
	public String getMdn() {
		return this.mdn;
	}

	/**
	 * 휴대기기 ID(MDN) 정보를 설정한다.
	 *
	 * @param mdn
	 *            휴대기기 ID(MDN) 정보
	 */
	public void setMdn(String mdn) {
		this.mdn = mdn;
	}

	/**
	 * 휴대기기 ID(UUID/MAC) 정보를 리턴한다.
	 * 
	 * @return deviceID - 휴대기기 ID(UUID/MAC) 정보
	 */
	public String getDeviceID() {
		return this.deviceID;
	}

	/**
	 * 휴대기기 ID(MDN/UUID/MAC) 정보를 설정한다.
	 * 
	 * @param deviceID
	 *            휴대기기 ID(MDN/UUID/MAC) 정보
	 */
	public void setDeviceID(String deviceID) {
		this.deviceID = deviceID;
	}

	/**
	 * Returns the serial version UID.
	 * 
	 * @return serialVersionUID - the serial version UID
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return the connIp
	 */
	public String getConnIp() {
		return this.connIp;
	}

	/**
	 * @param connIp
	 *            the connIp to set
	 */
	public void setConnIp(String connIp) {
		this.connIp = connIp;
	}

	/**
	 * @return the scVersion
	 */
	public String getScVersion() {
		return this.scVersion;
	}

	/**
	 * @param scVersion
	 *            the scVersion to set
	 */
	public void setScVersion(String scVersion) {
		this.scVersion = scVersion;
	}

	/**
	 * @return the isAutoLogin
	 */
	public String getIsAutoLogin() {
		return this.isAutoLogin;
	}

	/**
	 * @param isAutoLogin
	 *            the isAutoLogin to set
	 */
	public void setIsAutoLogin(String isAutoLogin) {
		this.isAutoLogin = isAutoLogin;
	}

	/**
	 * @return the deviceModelNm
	 */
	public String getDeviceModelNm() {
		return this.deviceModelNm;
	}

	/**
	 * @param deviceModelNm
	 *            the deviceModelNm to set
	 */
	public void setDeviceModelNm(String deviceModelNm) {
		this.deviceModelNm = deviceModelNm;
	}

	/**
	 * @return the deviceOsNm
	 */
	public String getDeviceOsNm() {
		return this.deviceOsNm;
	}

	/**
	 * @param deviceOsNm
	 *            the deviceOsNm to set
	 */
	public void setDeviceOsNm(String deviceOsNm) {
		this.deviceOsNm = deviceOsNm;
	}

	/**
	 * @return the deviceOsVersion
	 */
	public String getDeviceOsVersion() {
		return this.deviceOsVersion;
	}

	/**
	 * @param deviceOsVersion
	 *            the deviceOsVersion to set
	 */
	public void setDeviceOsVersion(String deviceOsVersion) {
		this.deviceOsVersion = deviceOsVersion;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return Utils.printKeyValues(this);
	}
}
