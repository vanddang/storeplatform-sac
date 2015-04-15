package com.skplanet.storeplatform.sac.client.member.vo.miscellaneous;

import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 2.3.18. 이메일 인증 URL 확인 [REQUEST].
 * 
 * Updated on : 2015. 4. 14. Updated by : Rejoice, Burkhan
 */
public class GetEmailAuthorizationUrlSacReq extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/** 사용자 고유 Key. */
	@NotBlank
	private String userKey;
	/** 변경할 이메일 주소. */
	@NotBlank
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
