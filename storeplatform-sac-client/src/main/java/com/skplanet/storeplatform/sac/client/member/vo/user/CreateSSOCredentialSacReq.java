package com.skplanet.storeplatform.sac.client.member.vo.user;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotEmpty;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * [REQUEST] PayPlanet SSOCredential 조회.
 * 
 * Updated on : 2015. 9. 22. Updated by : 반범진.
 */
public class CreateSSOCredentialSacReq extends CommonInfo{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 사용자 키.
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
