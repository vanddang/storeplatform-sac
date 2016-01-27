package com.skplanet.storeplatform.member.client.user.sci.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.member.client.common.vo.KeySearch;

import java.util.List;

/**
 * 휴대기기 설정 정보 Value Object.
 * 
 * Updated on : 2016. 1. 27. Updated by : 최진호, 보고지티.
 */
public class UserMbrDeviceSet extends CommonInfo {

	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	/** 시스템 ID. */
	private String systemID; // SYSTEM_ID
	/** 사용자 ID. */
	private String userID; // MBR_ID
	/** 사용자 Key. */
	private String userKey; // INSD_USERMBR_NO
	/** 휴대기기 ID(MDN/UUID/MAC). */
	private String deviceID; // DEVICE_ID
	/** 휴대기기 Key. */
	private String deviceKey; // INSD_DEVICE_ID
	/** PIN 번호. */
	private String pinNo; // PIN_NO
	/** 인증_실패_횟수. */
	private String authFailCnt; // AUTH_FAIL_CNT
	/** PIN 인증_잠금_여부. */
	private String authLockYn; // AUTH_LOCK_YN
	/** 자동_업데이트_유무. */
	private String isAutoUpdate; // AUTO_UPDT_YN
	/** 자동_업데이트_설정_구분(A: 모두/U: 사용자지정). */
	private String autoUpdateSet; // AUTO_UPDT_SET_CLSF
	/** Wi-Fi_자동_업데이트_유무. */
	private String isAutoUpdateWifi; // WI_FI_AUTO_UPDT_YN
	/** 로그인_잠금_유무. */
	private String isLoginLock; // LOGIN_LOCK_YN
	/** 성인_컨텐츠_잠금_유무. */
	private String isAdult; // ADULT_CONTENTS_LOCK_YN
	/** 등록일시. */
	private String regDate; // REG_DT
	/** 등록자. */
	private String regID; // REG_ID
	/** 수정 일시. */
	private String updateDate; // UPD_DT
	/** 수정자. */
	private String updateID; // UPD_ID
	/** PIN 번호 등록 유무. */
	private String isPin;
	/** 성인 컨텐츠 잠금 여부. */
	private String isAdultLock; // ADULT_CONTENTS_LIMT_YN
	/** Wi-Fi에서만 다운로드 여부. */
	private String isDownloadWifiOnly; // WI_FI_AUTO_DWLD_YN
	/** ICAS 인증 여부. */
	private String isIcasAuth; // ICAS_AUTH_YN
	/** 실명인증 일자. */
	private String rnameAuthDate; // RNAME_AUTH_DATE
	/** 실명인증 MDN. */
	private String rnameAuthMdn; // RNAME_AUTH_MDN

	/** 조건 객체. */
	private List<KeySearch> keySearchList;

	/**
	 * @return the systemID
	 */
	public String getSystemID() {
		return this.systemID;
	}

	/**
	 * @param systemID
	 *            the systemID to set
	 */
	public void setSystemID(String systemID) {
		this.systemID = systemID;
	}

	/**
	 * @return the userID
	 */
	public String getUserID() {
		return this.userID;
	}

	/**
	 * @param userID
	 *            the userID to set
	 */
	public void setUserID(String userID) {
		this.userID = userID;
	}

	/**
	 * @return the userKey
	 */
	public String getUserKey() {
		return this.userKey;
	}

	/**
	 * @param userKey
	 *            the userKey to set
	 */
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	/**
	 * @return the deviceID
	 */
	public String getDeviceID() {
		return this.deviceID;
	}

	/**
	 * @param deviceID
	 *            the deviceID to set
	 */
	public void setDeviceID(String deviceID) {
		this.deviceID = deviceID;
	}

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
	 * @return the pinNo
	 */
	public String getPinNo() {
		return this.pinNo;
	}

	/**
	 * @param pinNo
	 *            the pinNo to set
	 */
	public void setPinNo(String pinNo) {
		this.pinNo = pinNo;
	}

	/**
	 * @return the authFailCnt
	 */
	public String getAuthFailCnt() {
		return this.authFailCnt;
	}

	/**
	 * @param authFailCnt
	 *            the authFailCnt to set
	 */
	public void setAuthFailCnt(String authFailCnt) {
		this.authFailCnt = authFailCnt;
	}

	/**
	 * @return the authLockYn
	 */
	public String getAuthLockYn() {
		return this.authLockYn;
	}

	/**
	 * @param authLockYn
	 *            the authLockYn to set
	 */
	public void setAuthLockYn(String authLockYn) {
		this.authLockYn = authLockYn;
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
	 * @return the regDate
	 */
	public String getRegDate() {
		return this.regDate;
	}

	/**
	 * @param regDate
	 *            the regDate to set
	 */
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	/**
	 * @return the regID
	 */
	public String getRegID() {
		return this.regID;
	}

	/**
	 * @param regID
	 *            the regID to set
	 */
	public void setRegID(String regID) {
		this.regID = regID;
	}

	/**
	 * @return the updateDate
	 */
	public String getUpdateDate() {
		return this.updateDate;
	}

	/**
	 * @param updateDate
	 *            the updateDate to set
	 */
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * @return the updateID
	 */
	public String getUpdateID() {
		return this.updateID;
	}

	/**
	 * @param updateID
	 *            the updateID to set
	 */
	public void setUpdateID(String updateID) {
		this.updateID = updateID;
	}

	/**
	 * @return the keySearchList
	 */
	public List<KeySearch> getKeySearchList() {
		return this.keySearchList;
	}

	/**
	 * @param keySearchList
	 *            the keySearchList to set
	 */
	public void setKeySearchList(List<KeySearch> keySearchList) {
		this.keySearchList = keySearchList;
	}

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
	 * @return rnameAuthDate - rnameAuthDate
	 */
	public String getRnameAuthDate() {
		return this.rnameAuthDate;
	}

	/**
	 * 실명인증 일자(을)를 셋팅한다.
	 * 
	 * @param rnameAuthDate
	 *            rnameAuthDate
	 */
	public void setRnameAuthDate(String rnameAuthDate) {
		this.rnameAuthDate = rnameAuthDate;
	}

	/**
	 * 실명인증 MDN(을)를 리턴한다.
	 * 
	 * @return rnameAuthMdn - rnameAuthMdn
	 */
	public String getRnameAuthMdn() {
		return this.rnameAuthMdn;
	}

	/**
	 * 실명인증 MDN(을)를 셋팅한다.
	 * 
	 * @param rnameAuthMdn
	 *            rnameAuthMdn
	 */
	public void setRnameAuthMdn(String rnameAuthMdn) {
		this.rnameAuthMdn = rnameAuthMdn;
	}

}
