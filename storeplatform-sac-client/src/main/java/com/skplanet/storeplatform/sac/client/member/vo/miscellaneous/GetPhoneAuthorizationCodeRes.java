package com.skplanet.storeplatform.sac.client.member.vo.miscellaneous;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * [RESPONSE] 휴대폰 인증 SMS 발송
 * 
 * Updated on : 2014. 1. 6. Updated by : 김다슬, 인크로스.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class GetPhoneAuthorizationCodeRes extends CommonInfo {
	private static final long serialVersionUID = 1L;

	/**
	 * 인증 Signature.
	 */
	private String phoneSign;

	/**
	 * @return the phoneSign
	 */
	public String getPhoneSign() {
		return this.phoneSign;
	}

	/**
	 * @param phoneSign
	 *            the phoneSign to set
	 */
	public void setPhoneSign(String phoneSign) {
		this.phoneSign = phoneSign;
	}

}
