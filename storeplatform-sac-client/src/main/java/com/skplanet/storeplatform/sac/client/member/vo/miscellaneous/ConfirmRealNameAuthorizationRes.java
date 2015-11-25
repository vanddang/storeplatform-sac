package com.skplanet.storeplatform.sac.client.member.vo.miscellaneous;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * [RESPONSE] 실명 인증용 휴대폰 인증 코드 확인
 * 
 * Updated on : 2014. 1. 6. Updated by : 김다슬, 인크로스.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class ConfirmRealNameAuthorizationRes extends CommonInfo {
	private static final long serialVersionUID = 1L;

	/**
	 * 연계정보.
	 */
	private String userCI;

	/**
	 * 중복가입 확인정보.
	 */
	private String userDI;

	/**
	 * @return the userCI
	 */
	public String getUserCI() {
		return this.userCI;
	}

	/**
	 * @param userCI
	 *            the userCI to set
	 */
	public void setUserCI(String userCI) {
		this.userCI = userCI;
	}

	/**
	 * @return the userDI
	 */
	public String getUserDI() {
		return this.userDI;
	}

	/**
	 * @param userDI
	 *            the userDI to set
	 */
	public void setUserDI(String userDI) {
		this.userDI = userDI;
	}

}
