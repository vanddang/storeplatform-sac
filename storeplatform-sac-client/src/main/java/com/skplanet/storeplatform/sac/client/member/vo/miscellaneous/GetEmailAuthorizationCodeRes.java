package com.skplanet.storeplatform.sac.client.member.vo.miscellaneous;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * [RESPONSE] 이메일 인증 코드 생성
 * 
 * Updated on : 2014. 1. 6. Updated by : 김다슬, 인크로스.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class GetEmailAuthorizationCodeRes extends CommonInfo {
	private static final long serialVersionUID = 1L;

	/**
	 * 이메일 인증 코드.
	 */
	private String emailAuthCode;

	/**
	 * @return the emailAuthCode
	 */
	public String getEmailAuthCode() {
		return this.emailAuthCode;
	}

	/**
	 * @param emailAuthCode
	 *            the emailAuthCode to set
	 */
	public void setEmailAuthCode(String emailAuthCode) {
		this.emailAuthCode = emailAuthCode;
	}

}
