package com.skplanet.storeplatform.member.client.user.sci.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 소셜 계정 이력 Value Object.
 * 
 * Updated on : 2015. 6. 10. Updated by : Rejoice, Burkhan
 */
public class SocialAccount extends CommonInfo {

	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	/** 테넌트 아이디. */
	private String tenantId;
	/** 사용자 키. */
	private String userKey;
	/** 소셜계정 내부 아이디. */
	private String socialAcctIntId;
	/** 소셜계정 타입. */
	private String socialAcctType;
	/** 등록일시. */
	private String insDt;

	/**
	 * @return the tenantId
	 */
	public String getTenantId() {
		return this.tenantId;
	}

	/**
	 * @param tenantId
	 *            the tenantId to set
	 */
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	/**
	 * @return the userKey
	 */
	public String getUserKey() {
		return this.userKey;
	}

	/**
	 * @param userKey
	 *            the userKey to set
	 */
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	/**
	 * @return the socialAcctIntId
	 */
	public String getSocialAcctIntId() {
		return this.socialAcctIntId;
	}

	/**
	 * @param socialAcctIntId
	 *            the socialAcctIntId to set
	 */
	public void setSocialAcctIntId(String socialAcctIntId) {
		this.socialAcctIntId = socialAcctIntId;
	}

	/**
	 * @return the socialAcctType
	 */
	public String getSocialAcctType() {
		return this.socialAcctType;
	}

	/**
	 * @param socialAcctType
	 *            the socialAcctType to set
	 */
	public void setSocialAcctType(String socialAcctType) {
		this.socialAcctType = socialAcctType;
	}

	/**
	 * @return the insDt
	 */
	public String getInsDt() {
		return this.insDt;
	}

	/**
	 * @param insDt
	 *            the insDt to set
	 */
	public void setInsDt(String insDt) {
		this.insDt = insDt;
	}

}
