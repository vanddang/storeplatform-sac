package com.skplanet.storeplatform.sac.client.member.vo.user;

import org.hibernate.validator.constraints.NotEmpty;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * [REQUEST] 비밀번호 수정
 * 
 * Updated on : 2014. 1. 6. Updated by : 심대진, 다모아 솔루션.
 */
public class ModifyPasswordReq extends CommonInfo {

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
	 * 기존 비밀번호.
	 */
	@NotEmpty
	private String oldPassword = "";

	/**
	 * 신규 비밀번호.
	 */
	@NotEmpty
	private String newPassword = "";

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
	 * @return String : oldPassword
	 */
	public String getOldPassword() {
		return this.oldPassword;
	}

	/**
	 * @param oldPassword
	 *            String : the oldPassword to set
	 */
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	/**
	 * @return String : newPassword
	 */
	public String getNewPassword() {
		return this.newPassword;
	}

	/**
	 * @param newPassword
	 *            String : the newPassword to set
	 */
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

}
