package com.skplanet.storeplatform.sac.client.member.vo.miscellaneous;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * [REQUEST] 실명 인증용 휴대폰 인증 SMS 재발송
 * 
 * Updated on : 2014. 1. 6. Updated by : 김다슬, 인크로스.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class ResendSmsForRealNameAuthorizationReq extends CommonInfo {
	private static final long serialVersionUID = 1L;

	/**
	 * 사용자 고유 식별Key
	 */
	private String kmcSmsAuth;

	/**
	 * 확인용 파라미터1
	 */
	private String checkParam1;

	/**
	 * 확인용 파라미터2
	 */
	private String checkParam2;

	/**
	 * 확인용 파라미터3
	 */
	private String checkParam3;

	public String getKmcSmsAuth() {
		return this.kmcSmsAuth;
	}

	public void setKmcSmsAuth(String kmcSmsAuth) {
		this.kmcSmsAuth = kmcSmsAuth;
	}

	public String getCheckParam1() {
		return this.checkParam1;
	}

	public void setCheckParam1(String checkParam1) {
		this.checkParam1 = checkParam1;
	}

	public String getCheckParam2() {
		return this.checkParam2;
	}

	public void setCheckParam2(String checkParam2) {
		this.checkParam2 = checkParam2;
	}

	public String getCheckParam3() {
		return this.checkParam3;
	}

	public void setCheckParam3(String checkParam3) {
		this.checkParam3 = checkParam3;
	}

}
