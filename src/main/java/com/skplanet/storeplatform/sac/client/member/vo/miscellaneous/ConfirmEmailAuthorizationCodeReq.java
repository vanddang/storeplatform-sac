package com.skplanet.storeplatform.sac.client.member.vo.miscellaneous;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * [REQUEST] 이메일 인증 코드 확인
 * 
 * Updated on : 2014. 1. 6. Updated by : 김다슬, 인크로스.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class ConfirmEmailAuthorizationCodeReq extends CommonInfo {
	private static final long serialVersionUID = 1L;

	/**
	 * 이메일 인증 코드
	 */
	private String emailAuthCode;
	/**
	 * 인증 코드 생존 시간 ( 일 단위 )
	 */
	private String tileToLive;

	public String getEmailAuthCode() {
		return this.emailAuthCode;
	}

	public void setEmailAuthCode(String emailAuthCode) {
		this.emailAuthCode = emailAuthCode;
	}

	public String getTileToLive() {
		return this.tileToLive;
	}

	public void setTileToLive(String tileToLive) {
		this.tileToLive = tileToLive;
	}

}
