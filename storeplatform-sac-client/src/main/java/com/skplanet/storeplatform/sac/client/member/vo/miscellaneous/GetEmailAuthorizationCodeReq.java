package com.skplanet.storeplatform.sac.client.member.vo.miscellaneous;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * [REQUEST] 이메일 인증 코드 생성
 * 
 * Updated on : 2014. 1. 6. Updated by : 김다슬, 인크로스.
 */

@JsonSerialize(include = Inclusion.NON_NULL)
public class GetEmailAuthorizationCodeReq extends CommonInfo {
	private static final long serialVersionUID = 1L;
	/**
	 * 사용자 고유 Key.
	 */
	@NotBlank
	private String userKey;

	/**
	 * 인증 코드를 발송할 회원 이메일 주소.
	 */
	@Email
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
