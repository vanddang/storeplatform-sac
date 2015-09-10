package com.skplanet.storeplatform.sac.client.member.vo.user;

import java.io.Serializable;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * [RESPONSE] ayPlanet SSOCredential 조회.
 * 
 * Updated on : 2015. 9. 22. Updated by : 반범진.
 */
public class CreateSSOCredentialSacRes extends CommonInfo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Syrup Pay에서 발급된 SSOCredential.
	 */
	private String SSOCredential;

	/**
	 * @return SSOCredential
	 */
	public String getSSOCredential() {
		return this.SSOCredential;
	}

	/**
	 * @param sSOCredential
	 *            String
	 */
	public void setSSOCredential(String sSOCredential) {
		this.SSOCredential = sSOCredential;
	}

}
