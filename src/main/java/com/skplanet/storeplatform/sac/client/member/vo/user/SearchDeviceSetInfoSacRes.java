package com.skplanet.storeplatform.sac.client.member.vo.user;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 2.1.48. 휴대기기 설정 정보 조회. [RESPONSE]
 * 
 * Updated on : 2015. 10. 29. Updated by : 최진호, 보고지티.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class SearchDeviceSetInfoSacRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/** 휴대기기 PIN 등록 유무. */
	private String isPin;
	/** 자동 업데이트 유무. */
	private String isAutoUpdate;
	/** 자동 업데이트 설정 구분. */
	private String autoUpdateSet;
	/** Wi-Fi 자동 업데이트 유무. */
	private String isAutoUpdateWifi;
	/** 로그인 잠금 유무. */
	private String isLoginLock;
	/** 결제 PIN 재입력 여부. */
	private String isPinRetry;
	/** 성인 콘테츠 잠금 유무. */
	private String isAdult;
	/** 결제 PIN 잠김 여부. */
	private String isPinClosed;
	/** 인증실패 횟수. */
	private String failCnt;
	/** 성인 컨텐츠 잠금 여부. */
	private String isAdultLock;
	/** Wi-Fi에서만 다운로드 여부. */
	private String isDownloadWifiOnly;
	/** ICAS 인증 여부. */
	private String isIcasAuth;
	/** 실명인증 일자. */
	private String realNameDate;
	/** 실명인증 MDN. */
	private String realNameMdn;

	/**
	 * @return the isPin
	 */
	public String getIsPin() {
		return this.isPin;
	}

	/**
	 * @param isPin
	 *            the isPin to set
	 */
	public void setIsPin(String isPin) {
		this.isPin = isPin;
	}

	/**
	 * @return the isAutoUpdate
	 */
	public String getIsAutoUpdate() {
		return this.isAutoUpdate;
	}

	/**
	 * @param isAutoUpdate
	 *            the isAutoUpdate to set
	 */
	public void setIsAutoUpdate(String isAutoUpdate) {
		this.isAutoUpdate = isAutoUpdate;
	}

	/**
	 * @return the autoUpdateSet
	 */
	public String getAutoUpdateSet() {
		return this.autoUpdateSet;
	}

	/**
	 * @param autoUpdateSet
	 *            the autoUpdateSet to set
	 */
	public void setAutoUpdateSet(String autoUpdateSet) {
		this.autoUpdateSet = autoUpdateSet;
	}

	/**
	 * @return the isAutoUpdateWifi
	 */
	public String getIsAutoUpdateWifi() {
		return this.isAutoUpdateWifi;
	}

	/**
	 * @param isAutoUpdateWifi
	 *            the isAutoUpdateWifi to set
	 */
	public void setIsAutoUpdateWifi(String isAutoUpdateWifi) {
		this.isAutoUpdateWifi = isAutoUpdateWifi;
	}

	/**
	 * @return the isLoginLock
	 */
	public String getIsLoginLock() {
		return this.isLoginLock;
	}

	/**
	 * @param isLoginLock
	 *            the isLoginLock to set
	 */
	public void setIsLoginLock(String isLoginLock) {
		this.isLoginLock = isLoginLock;
	}

	/**
	 * @return the isPinRetry
	 */
	public String getIsPinRetry() {
		return this.isPinRetry;
	}

	/**
	 * @param isPinRetry
	 *            the isPinRetry to set
	 */
	public void setIsPinRetry(String isPinRetry) {
		this.isPinRetry = isPinRetry;
	}

	/**
	 * @return the isAdult
	 */
	public String getIsAdult() {
		return this.isAdult;
	}

	/**
	 * @param isAdult
	 *            the isAdult to set
	 */
	public void setIsAdult(String isAdult) {
		this.isAdult = isAdult;
	}

	/**
	 * @return the isPinClosed
	 */
	public String getIsPinClosed() {
		return this.isPinClosed;
	}

	/**
	 * @param isPinClosed
	 *            the isPinClosed to set
	 */
	public void setIsPinClosed(String isPinClosed) {
		this.isPinClosed = isPinClosed;
	}

	/**
	 * @return the failCnt
	 */
	public String getFailCnt() {
		return this.failCnt;
	}

	/**
	 * @param failCnt
	 *            the failCnt to set
	 */
	public void setFailCnt(String failCnt) {
		this.failCnt = failCnt;
	}

	/**
	 * @return the isAdultLock
	 */
	public String getIsAdultLock() {
		return this.isAdultLock;
	}

	/**
	 * @param isAdultLock
	 *            the isAdultLock to set
	 */
	public void setIsAdultLock(String isAdultLock) {
		this.isAdultLock = isAdultLock;
	}

	/**
	 * @return the isDownloadWifiOnly
	 */
	public String getIsDownloadWifiOnly() {
		return this.isDownloadWifiOnly;
	}

	/**
	 * @param isDownloadWifiOnly
	 *            the isDownloadWifiOnly to set
	 */
	public void setIsDownloadWifiOnly(String isDownloadWifiOnly) {
		this.isDownloadWifiOnly = isDownloadWifiOnly;
	}

	/**
	 * @return the isIcasAuth
	 */
	public String getIsIcasAuth() {
		return this.isIcasAuth;
	}

	/**
	 * @param isIcasAuth
	 *            the isIcasAuth to set
	 */
	public void setIsIcasAuth(String isIcasAuth) {
		this.isIcasAuth = isIcasAuth;
	}

	/**
	 * 실명인증 일자(을)를 리턴한다.
	 * 
	 * @return realNameDate - realNameDate
	 */
	public String getRealNameDate() {
		return this.realNameDate;
	}

	/**
	 * 실명인증 일자(을)를 셋팅한다.
	 * 
	 * @param realNameDate
	 *            realNameDate
	 */
	public void setRealNameDate(String realNameDate) {
		this.realNameDate = realNameDate;
	}

	/**
	 * 실명인증 MDN(을)를 리턴한다.
	 * 
	 * @return realNameMdn - realNameMdn
	 */
	public String getRealNameMdn() {
		return this.realNameMdn;
	}

	/**
	 * 실명인증 MDN(을)를 셋팅한다.
	 * 
	 * @param realNameMdn
	 *            realNameMdn
	 */
	public void setRealNameMdn(String realNameMdn) {
		this.realNameMdn = realNameMdn;
	}

}
