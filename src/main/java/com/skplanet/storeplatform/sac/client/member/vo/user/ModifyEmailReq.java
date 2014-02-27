package com.skplanet.storeplatform.sac.client.member.vo.user;

import org.hibernate.validator.constraints.NotEmpty;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * [REQUEST] 이메일 주소 수정
 * 
 * Updated on : 2014. 1. 6. Updated by : 심대진, 다모아 솔루션.
 */
public class ModifyEmailReq extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/**
	 * 사용자 Key.
	 */
	@NotEmpty
	private String userKey = "";

	/**
	 * IDP 인증 Key.
	 */
	@NotEmpty
	private String userAuthKey = "";

	/**
	 * 신규 이메일.
	 */
	@NotEmpty
	private String newEmail = "";

	/**
	 * 이메일 인증여부.
	 */
	private String isEmailAuth = "";

	/**
	 * @return String : userKey
	 */
	public String getUserKey() {
		return this.userKey;
	}

	/**
	 * @param userKey
	 *            String : the userKey to set
	 */
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	/**
	 * @return String : userAuthKey
	 */
	public String getUserAuthKey() {
		return this.userAuthKey;
	}

	/**
	 * @param userAuthKey
	 *            String : the userAuthKey to set
	 */
	public void setUserAuthKey(String userAuthKey) {
		this.userAuthKey = userAuthKey;
	}

	/**
	 * @return String : newEmail
	 */
	public String getNewEmail() {
		return this.newEmail;
	}

	/**
	 * @param newEmail
	 *            String : the newEmail to set
	 */
	public void setNewEmail(String newEmail) {
		this.newEmail = newEmail;
	}

	/**
	 * @return String : isEmailAuth
	 */
	public String getIsEmailAuth() {
		return this.isEmailAuth;
	}

	/**
	 * @param isEmailAuth
	 *            String : the isEmailAuth to set
	 */
	public void setIsEmailAuth(String isEmailAuth) {
		this.isEmailAuth = isEmailAuth;
	}

}
