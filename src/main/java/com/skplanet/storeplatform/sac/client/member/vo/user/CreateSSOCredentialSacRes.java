package com.skplanet.storeplatform.sac.client.member.vo.user;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * [RESPONSE] ayPlanet ssoCredential 조회.
 * 
 * Updated on : 2015. 9. 22. Updated by : 반범진.
 */
public class CreateSSOCredentialSacRes extends CommonInfo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Syrup Pay에서 발급된 ssoCredential.
	 */
	private String ssoCredential;

	/**
	 * @return ssoCredential
	 */
	public String getSsoCredential() {
		return this.ssoCredential;
	}

	/**
	 * @param ssoCredential
	 *            String
	 */
	public void setSsoCredential(String ssoCredential) {
		this.ssoCredential = ssoCredential;
	}

}
