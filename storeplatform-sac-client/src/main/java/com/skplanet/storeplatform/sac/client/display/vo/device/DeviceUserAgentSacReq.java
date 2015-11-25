package com.skplanet.storeplatform.sac.client.display.vo.device;

import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 단말 모델 정보 조회 (by UserAgent) Request Value Object.
 * 
 * Updated on : 2014. 03. 12. Updated by : 이태희.
 */
public class DeviceUserAgentSacReq extends CommonInfo {
	private static final long serialVersionUID = 1L;

	@NotBlank
	private String userAgent;

	/**
	 * @return the userAgent
	 */
	public String getUserAgent() {
		return this.userAgent;
	}

	/**
	 * @param userAgent
	 *            the userAgent to set
	 */
	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}
}
