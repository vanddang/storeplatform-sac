package com.skplanet.storeplatform.sac.client.member.vo.miscellaneous;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * [RESPONSE] 휴대폰 인증 여부 확인
 * 
 * Updated on : 2015. 3. 4. Updated by : 윤보영, 카레즈.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class ConfirmPhoneAuthorizationCheckRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/**
	 * 인증 처리 여부 확인 휴대폰 번호.
	 */
	private String userPhone;

	/**
	 * 인증여부.
	 */
	private String comptYn;

	/**
	 * 인증번호 요청일시.
	 */
	private String regDt;

	/**
	 * 인증 확인 일시.
	 */
	private String updDt;

	/**
	 * @return the userPhone
	 */
	public String getUserPhone() {
		return this.userPhone;
	}

	/**
	 * @return the comptYn
	 */
	public String getComptYn() {
		return this.comptYn;
	}

	/**
	 * @return the regDt
	 */
	public String getRegDt() {
		return this.regDt;
	}

	/**
	 * @return the updDt
	 */
	public String getUpdDt() {
		return this.updDt;
	}

	/**
	 * @param userPhone
	 *            the userPhone to set
	 */
	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	/**
	 * @param comptYn
	 *            the comptYn to set
	 */
	public void setComptYn(String comptYn) {
		this.comptYn = comptYn;
	}

	/**
	 * @param regDt
	 *            the regDt to set
	 */
	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}

	/**
	 * @param updDt
	 *            the updDt to set
	 */
	public void setUpdDt(String updDt) {
		this.updDt = updDt;
	}
}
