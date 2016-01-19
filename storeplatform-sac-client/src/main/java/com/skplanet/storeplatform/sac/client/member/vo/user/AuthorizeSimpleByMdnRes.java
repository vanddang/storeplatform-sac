package com.skplanet.storeplatform.sac.client.member.vo.user;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import java.io.Serializable;

/**
 * [RESPONSE] 자동업데이트 회원 인증.
 * 
 * Updated on : 2016. 1. 15. Updated by : 최진호, 보고지티.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class AuthorizeSimpleByMdnRes extends CommonInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 내부 사용자키.
	 */
	private String userKey;

	/**
	 * 내부 휴대기기키.
	 */
	private String deviceKey;

	/**
	 * 로그인 성공여부(Y/N).
	 */
	private String isLoginSuccess;

	/**
	 * @return isLoginSuccess
	 */
	public String getIsLoginSuccess() {
		return this.isLoginSuccess;
	}

	/**
	 * @param isLoginSuccess
	 *            String
	 */
	public void setIsLoginSuccess(String isLoginSuccess) {
		this.isLoginSuccess = isLoginSuccess;
	}

	/**
	 * @return userKey
	 */
	public String getUserKey() {
		return this.userKey;
	}

	/**
	 * @param userKey
	 *            String
	 */
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	/**
	 * @return deviceKey
	 */
	public String getDeviceKey() {
		return this.deviceKey;
	}

	/**
	 * @param deviceKey
	 *            deviceKey
	 */
	public void setDeviceKey(String deviceKey) {
		this.deviceKey = deviceKey;
	}

}
