
package com.skplanet.storeplatform.sac.client.member.vo.seller;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 2.2.29. 판매자 회원 단순 인증 [RESPONSE]
 * 
 * Updated on : 2014. 2. 14. Updated by : 김다슬, 인크로스
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class AuthorizeSimpleRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/** 로그인 성공 여부. */
	private String isLoginSuccess;

	/**
	 * @return the isLoginSuccess
	 */
	public String getIsLoginSuccess() {
		return this.isLoginSuccess;
	}

	/**
	 * @param isLoginSuccess
	 *            the isLoginSuccess to set
	 */
	public void setIsLoginSuccess(String isLoginSuccess) {
		this.isLoginSuccess = isLoginSuccess;
	}

}
