package com.skplanet.storeplatform.sac.client.member.vo.user;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * [RESPONSE] 모바일 전용 회원 인증(MDN 인증)
 * 
 * Updated on : 2014. 1. 3. Updated by : 반범진. 지티소프트.
 */
public class AuthorizeByMdnRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/**
	 * 사용자 고유 Key
	 */
	private String userKey;

	/**
	 * IDP 인증 Key
	 */
	private String userAuthKey;

	public String getUserKey() {
		return this.userKey;
	}

	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	public String getUserAuthKey() {
		return this.userAuthKey;
	}

	public void setUserAuthKey(String userAuthKey) {
		this.userAuthKey = userAuthKey;
	}

}
