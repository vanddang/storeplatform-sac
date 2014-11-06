package com.skplanet.storeplatform.sac.client.member.vo.user;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 2.1.49. 휴대기기 설정 정보 등록/수정. [REQUEST]
 * 
 * Updated on : 2014. 10. 31. Updated by : Rejoice, Burkhan
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class ModifyDeviceSetInfoSacReq extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/** 기기Key. */
	@NotBlank
	private String deviceKey;
	/** 회원 Key. */
	@NotBlank
	private String userKey;
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

}
