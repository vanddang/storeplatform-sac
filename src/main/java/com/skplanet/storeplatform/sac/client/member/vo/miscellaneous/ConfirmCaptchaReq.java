package com.skplanet.storeplatform.sac.client.member.vo.miscellaneous;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * [REQUEST] Captcha 문자 확인
 * 
 * Updated on : 2014. 1. 6. Updated by : 김다슬, 인크로스.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class ConfirmCaptchaReq extends CommonInfo {
	private static final long serialVersionUID = 1L;

	/**
	 * 인증 코드 (사용자가 입력한 Captcha 문자).
	 */
	@NotBlank
	private String authCode;

	/**
	 * 인증 코드 확인을 위한 Signature.
	 */
	@NotBlank
	private String imageSign;

	/**
	 * 인증 코드 확인을 위한 Signature Data.
	 */
	@NotBlank
	private String signData;

	/**
	 * @return the authCode
	 */
	public String getAuthCode() {
		return this.authCode;
	}

	/**
	 * @param authCode
	 *            the authCode to set
	 */
	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}

	/**
	 * @return the imageSign
	 */
	public String getImageSign() {
		return this.imageSign;
	}

	/**
	 * @param imageSign
	 *            the imageSign to set
	 */
	public void setImageSign(String imageSign) {
		this.imageSign = imageSign;
	}

	/**
	 * @return the signData
	 */
	public String getSignData() {
		return this.signData;
	}

	/**
	 * @param signData
	 *            the signData to set
	 */
	public void setSignData(String signData) {
		this.signData = signData;
	}

}
