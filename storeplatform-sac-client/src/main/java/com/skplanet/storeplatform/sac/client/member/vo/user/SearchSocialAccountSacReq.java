package com.skplanet.storeplatform.sac.client.member.vo.user;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 2.1.59. 소셜 계정 등록 회원 리스트 [REQUEST].
 * 
 * Updated on : 2015. 6. 3. Updated by : Rejoice, Burkhan
 */
public class SearchSocialAccountSacReq extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/** 사용자 키. */
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
