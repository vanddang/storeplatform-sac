package com.skplanet.storeplatform.sac.client.member.vo.user;

import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 2.1.57. 소셜 계정 삭제 [REQUEST].
 * 
 * Updated on : 2015. 6. 3. Updated by : Rejoice, Burkhan
 */
public class RemoveSocialAccountSacReq extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/** 사용자 key. */
	@NotBlank
	private String userKey;

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

}
