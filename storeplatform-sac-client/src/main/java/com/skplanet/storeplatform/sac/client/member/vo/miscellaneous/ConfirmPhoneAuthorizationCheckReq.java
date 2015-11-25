package com.skplanet.storeplatform.sac.client.member.vo.miscellaneous;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.hibernate.validator.constraints.NotEmpty;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * [REQUEST] 휴대폰 인증 여부 확인
 * 
 * Updated on : 2015. 3. 4. Updated by : 윤보영, 카레즈.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class ConfirmPhoneAuthorizationCheckReq extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/**
	 * 인증 처리 여부 확인 휴대폰 번호.
	 */
	@NotEmpty(message = "파라미터가 존재하지 않습니다.")
	private String userPhone;

	/**
	 * 인증번호 진위여부 확인을 위한 Signature.
	 */
	@NotEmpty(message = "파라미터가 존재하지 않습니다.")
	private String phoneSign;

	/**
	 * @return the userPhone
	 */
	public String getUserPhone() {
		return this.userPhone;
	}

	/**
	 * @return the phoneSign
	 */
	public String getPhoneSign() {
		return this.phoneSign;
	}

	/**
	 * @param userPhone
	 *            the userPhone to set
	 */
	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	/**
	 * @param phoneSign
	 *            the phoneSign to set
	 */
	public void setPhoneSign(String phoneSign) {
		this.phoneSign = phoneSign;
	}
}
