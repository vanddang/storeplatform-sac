package com.skplanet.storeplatform.sac.client.member.vo.miscellaneous;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

/**
 * 
 * [RESPONSE] 휴대폰 인증 SMS 발송
 * 
 * Updated on : 2014. 1. 6. Updated by : 김다슬, 인크로스.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class GetPhoneAuthorizationCodeRes {
	private static final long serialVersionUID = 1L;

	private String phoneSign;

	public String getPhoneSign() {
		return this.phoneSign;
	}

	public void setPhoneSign(String phoneSign) {
		this.phoneSign = phoneSign;
	}

}
