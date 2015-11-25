/**
 * 
 */
package com.skplanet.storeplatform.sac.client.internal.member.user.vo;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * DeviceId를 이용한 회원 정보 LocalSCI Response.
 * 
 * Updated on : 2014. 4. 19. Updated by : 김다슬, 인크로스.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class UserInfoSacRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/** 회원키. */
	private String userKey;

	/** 기기 키. */
	private String deviceKey;

	/** 사용자 메인 상태 코드. */
	private String userMainStatus;

	/** 사용자 서브 상태 코드. */
	private String userSubStatus;

	/** 사용자 로그인 상태 코드. 10:로그인 가능, 20:로그인 제한 */
	private String loginStatus;

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
	 * @return the userMainStatus
	 */
	public String getUserMainStatus() {
		return this.userMainStatus;
	}

	/**
	 * @param userMainStatus
	 *            the userMainStatus to set
	 */
	public void setUserMainStatus(String userMainStatus) {
		this.userMainStatus = userMainStatus;
	}

	/**
	 * @return the userSubStatus
	 */
	public String getUserSubStatus() {
		return this.userSubStatus;
	}

	/**
	 * @param userSubStatus
	 *            the userSubStatus to set
	 */
	public void setUserSubStatus(String userSubStatus) {
		this.userSubStatus = userSubStatus;
	}

	/**
	 * @return the loginStatus
	 */
	public String getLoginStatus() {
		return this.loginStatus;
	}

	/**
	 * @param loginStatus
	 *            the loginStatus to set
	 */
	public void setLoginStatus(String loginStatus) {
		this.loginStatus = loginStatus;
	}

}
