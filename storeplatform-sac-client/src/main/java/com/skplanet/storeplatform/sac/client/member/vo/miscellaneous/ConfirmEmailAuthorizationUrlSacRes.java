package com.skplanet.storeplatform.sac.client.member.vo.miscellaneous;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 2.3.19. 이메일 인증 URL 확인 [RESPONSE].
 * 
 * Updated on : 2015. 4. 14. Updated by : Rejoice, Burkhan
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class ConfirmEmailAuthorizationUrlSacRes extends CommonInfo {

	private static final long serialVersionUID = 1L;
	/** 사용자 고유 Key. */
	private String userKey;
	/** 변경된 사용자 Email 주소. */
	private String userEmail;

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

	/**
	 * @return the userEmail
	 */
	public String getUserEmail() {
		return this.userEmail;
	}

	/**
	 * @param userEmail
	 *            the userEmail to set
	 */
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

}
