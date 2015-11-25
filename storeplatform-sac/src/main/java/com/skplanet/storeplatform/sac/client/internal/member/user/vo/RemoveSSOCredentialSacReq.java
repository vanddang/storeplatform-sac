package com.skplanet.storeplatform.sac.client.internal.member.user.vo;

import org.hibernate.validator.constraints.NotEmpty;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * SAC Internal [REQUEST] SSOCredential 정보 삭제.
 * 
 * Updated on : 2015. 9. 22. Updated by : 반범진.
 */
public class RemoveSSOCredentialSacReq extends CommonInfo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 회원 키.
	 */
	@NotEmpty
	private String userKey;

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

}
