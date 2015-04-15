package com.skplanet.storeplatform.sac.client.member.vo.miscellaneous;

import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 2.3.19. 이메일 인증 URL 확인 [REQUEST].
 * 
 * Updated on : 2015. 4. 14. Updated by : Rejoice, Burkhan
 */
public class ConfirmEmailAuthorizationUrlSacReq extends CommonInfo {

	private static final long serialVersionUID = 1L;
	/** 이메일 인증 코드. */
	@NotBlank
	private String emailAuthCode;
	/** 인증 코드 유효 시간 (시간 단위). */
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
