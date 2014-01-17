package com.skplanet.storeplatform.sac.client.member.vo.miscellaneous;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.hibernate.validator.constraints.NotEmpty;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * [REQUEST] 휴대폰 인증 코드 확인
 * 
 * Updated on : 2014. 1. 6. Updated by : 김다슬, 인크로스.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class ConfirmPhoneAuthorizationCodeReq extends CommonInfo {
	private static final long serialVersionUID = 1L;

	/**
	 * 인증코드 수신 휴대폰 번호.
	 */
	@NotEmpty(message = "필수 파라미터 입니다.")
	private String userPhone;

	/**
	 * 인증코드.
	 */
	@NotEmpty(message = "필수 파라미터 입니다.")
	private String phoneAuthCode;

	/**
	 * 인증코드 Signature.
	 */
	@NotEmpty(message = "필수 파라미터 입니다.")
	private String phoneSign;

	/**
	 * 인증 코드 유효시간 (분).
	 */
	@NotEmpty(message = "필수 파라미터 입니다.")
	private String timeToLive;

	public String getUserPhone() {
		return this.userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public String getPhoneAuthCode() {
		return this.phoneAuthCode;
	}

	public void setPhoneAuthCode(String phoneAuthCode) {
		this.phoneAuthCode = phoneAuthCode;
	}

	public String getPhoneSign() {
		return this.phoneSign;
	}

	public void setPhoneSign(String phoneSign) {
		this.phoneSign = phoneSign;
	}

	public String getTimeToLive() {
		return this.timeToLive;
	}

	public void setTimeToLive(String timeToLive) {
		this.timeToLive = timeToLive;
	}
}
