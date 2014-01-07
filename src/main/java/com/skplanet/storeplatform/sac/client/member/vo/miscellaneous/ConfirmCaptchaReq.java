package com.skplanet.storeplatform.sac.client.member.vo.miscellaneous;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * [REQUEST] Captcha 문자 확인
 * 
 * Updated on : 2014. 1. 6. Updated by : 김다슬, 인크로스.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class ConfirmCaptchaReq extends CommonInfo {
	private static final long serialVersionUID = 1L;

	/**
	 * 인증 코드 (사용자가 입력한 Captcha 문자)
	 */
	private String authCode;

	/**
	 * 인증 코드 확인을 위한 Signature
	 */
	private String imageSign;

}
