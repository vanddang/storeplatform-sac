package com.skplanet.storeplatform.sac.client.member.vo.miscellaneous;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * [RESPONSE] 실명 인증용 휴대폰 인증 SMS 발송
 * 
 * Updated on : 2014. 1. 6. Updated by : 김다슬, 인크로스.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class SendSmsForRealNameAuthorizationRes extends CommonInfo {
	private static final long serialVersionUID = 1L;

	/**
	 * 사용자 고유 식별Key.
	 */
	private String kmcSmsAuth;

	/**
	 * 확인용 파라미터1.
	 */
	private String checkParam1;

	/**
	 * 확인용 파라미터2.
	 */
	private String checkParam2;

	/**
	 * 확인용 파라미터3.
	 */
	private String checkParam3;

	/**
	 * @return the kmcSmsAuth
	 */
	public String getKmcSmsAuth() {
		return this.kmcSmsAuth;
	}

	/**
	 * @param kmcSmsAuth
	 *            the kmcSmsAuth to set
	 */
	public void setKmcSmsAuth(String kmcSmsAuth) {
		this.kmcSmsAuth = kmcSmsAuth;
	}

	/**
	 * @return the checkParam1
	 */
	public String getCheckParam1() {
		return this.checkParam1;
	}

	/**
	 * @param checkParam1
	 *            the checkParam1 to set
	 */
	public void setCheckParam1(String checkParam1) {
		this.checkParam1 = checkParam1;
	}

	/**
	 * @return the checkParam2
	 */
	public String getCheckParam2() {
		return this.checkParam2;
	}

	/**
	 * @param checkParam2
	 *            the checkParam2 to set
	 */
	public void setCheckParam2(String checkParam2) {
		this.checkParam2 = checkParam2;
	}

	/**
	 * @return the checkParam3
	 */
	public String getCheckParam3() {
		return this.checkParam3;
	}

	/**
	 * @param checkParam3
	 *            the checkParam3 to set
	 */
	public void setCheckParam3(String checkParam3) {
		this.checkParam3 = checkParam3;
	}

}
