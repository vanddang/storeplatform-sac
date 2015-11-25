package com.skplanet.storeplatform.sac.client.member.vo.miscellaneous;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 2.3.18. 이메일 인증 URL 생성 [RESPONSE].
 * 
 * Updated on : 2015. 4. 14. Updated by : Rejoice, Burkhan
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class GetEmailAuthorizationUrlSacRes extends CommonInfo {

	private static final long serialVersionUID = 1L;
	/** 이메일 인증 Url. */
	private String emailAuthUrl;

	/**
	 * @return the emailAuthUrl
	 */
	public String getEmailAuthUrl() {
		return this.emailAuthUrl;
	}

	/**
	 * @param emailAuthUrl
	 *            the emailAuthUrl to set
	 */
	public void setEmailAuthUrl(String emailAuthUrl) {
		this.emailAuthUrl = emailAuthUrl;
	}

}
