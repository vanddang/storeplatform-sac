package com.skplanet.storeplatform.sac.client.member.vo.miscellaneous;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * [RESPONSE] Caprcha 문자 발급
 * 
 * Updated on : 2014. 1. 6. Updated by : 김다슬, 인크로스.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class GetCaptchaRes extends CommonInfo {
	private static final long serialVersionUID = 1L;

	/**
	 * Captch 문자 (인증코드)
	 */
	private String imageData;

	/**
	 * 인증 코드 확인을 위한 Signature
	 */
	private String imageSign;

	public String getImageData() {
		return this.imageData;
	}

	public void setImageData(String imageData) {
		this.imageData = imageData;
	}

	public String getImageSign() {
		return this.imageSign;
	}

	public void setImageSign(String imageSign) {
		this.imageSign = imageSign;
	}

}
