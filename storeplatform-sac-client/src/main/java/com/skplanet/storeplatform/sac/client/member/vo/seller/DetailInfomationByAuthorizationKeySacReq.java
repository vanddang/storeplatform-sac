package com.skplanet.storeplatform.sac.client.member.vo.seller;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * [REQIEST] 판매자 회원 인증키 조회.
 * 
 * Updated on : 2014. 1. 21. Updated by : 김경복, 부르칸.
 */
public class DetailInfomationByAuthorizationKeySacReq extends CommonInfo {

	private static final long serialVersionUID = 7071714845900743242L;

	/** 판매자 회원키. */
	@NotBlank
	private String sessionKey;

	/** 만료일시. */
	@NotBlank
	@Pattern(regexp = "^\\d*")
	private String expireDate;

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
	 * @return the expireDate
	 */
	public String getExpireDate() {
		return this.expireDate;
	}

	/**
	 * @param expireDate
	 *            the expireDate to set
	 */
	public void setExpireDate(String expireDate) {
		this.expireDate = expireDate;
	}

}
