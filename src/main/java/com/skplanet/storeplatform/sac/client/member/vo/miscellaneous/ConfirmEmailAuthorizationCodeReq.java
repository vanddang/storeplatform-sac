package com.skplanet.storeplatform.sac.client.member.vo.miscellaneous;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * [REQUEST] 이메일 인증 코드 확인
 * 
 * Updated on : 2014. 1. 6. Updated by : 김다슬, 인크로스.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class ConfirmEmailAuthorizationCodeReq extends CommonInfo {
	private static final long serialVersionUID = 1L;

	/**
	 * 이메일 인증 코드
	 */
	@NotBlank
	private String emailAuthCode;
	/**
	 * 인증 코드 생존 시간 ( 일 단위 )
	 */
	private String timeToLive;

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

	/**
	 * @return the timeToLive
	 */
	public String getTimeToLive() {
		return this.timeToLive;
	}

	/**
	 * @param timeToLive
	 *            the timeToLive to set
	 */
	public void setTimeToLive(String timeToLive) {
		this.timeToLive = timeToLive;
	}

}
