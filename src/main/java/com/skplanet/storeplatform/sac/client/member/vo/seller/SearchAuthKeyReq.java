package com.skplanet.storeplatform.sac.client.member.vo.seller;

import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * [REQIEST] 판매자 회원 인증키 조회.
 * 
 * Updated on : 2014. 1. 21. Updated by : 김경복, 부르칸.
 */
public class SearchAuthKeyReq extends CommonInfo {

	private static final long serialVersionUID = 7071714845900743242L;

	/** 판매자 회원키. */
	@NotBlank
	private String sessionKey;

	@NotBlank
	private String extraDate;

	/**
	 * @return the sessionKey
	 */
	public String getSessionKey() {
		return this.sessionKey;
	}

	/**
	 * @param sessionKey
	 *            the sessionKey to set
	 */
	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}

	/**
	 * @return the extraDate
	 */
	public String getExtraDate() {
		return this.extraDate;
	}

	/**
	 * @param extraDate
	 *            the extraDate to set
	 */
	public void setExtraDate(String extraDate) {
		this.extraDate = extraDate;
	}

}
