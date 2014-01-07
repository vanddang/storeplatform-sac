package com.skplanet.storeplatform.sac.client.member.vo.miscellaneous;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * [REQUEST] 이메일 인증 코드 생성
 * 
 * Updated on : 2014. 1. 6. Updated by : 김다슬, 인크로스.
 */

@JsonSerialize(include = Inclusion.NON_NULL)
public class GetEmailAuthorizationCodeReq extends CommonInfo {
	private static final long serialVersionUID = 1L;
	/**
	 * 사용자 고유 Key
	 */
	private String userKey;

	/**
	 * 인증 코드를 발송할 회원 이메일 주소
	 */
	private String userEmail;

	public String getUserKey() {
		return this.userKey;
	}

	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	public String getUserEmail() {
		return this.userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

}
