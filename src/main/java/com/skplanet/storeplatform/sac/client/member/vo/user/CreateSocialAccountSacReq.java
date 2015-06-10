package com.skplanet.storeplatform.sac.client.member.vo.user;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 2.1.56. 소셜 계정 등록/수정 [REQUEST].
 * 
 * Updated on : 2015. 6. 3. Updated by : Rejoice, Burkhan
 */
public class CreateSocialAccountSacReq extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/** 사용자 key. */
	@NotBlank
	private String userKey;
	/** 소셜계정타입. */
	@NotBlank
	@Pattern(regexp = "^google|^facebook|^kakao")
	private String socialAcctType;
	/** 소셜계정 아이디. */
	@NotBlank
	private String socialAcctId;
	/** 소셜계정 토큰. */
	@NotBlank
	private String socialAcctToken;

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
	 * @return the socialAcctId
	 */
	public String getSocialAcctId() {
		return this.socialAcctId;
	}

	/**
	 * @param socialAcctId
	 *            the socialAcctId to set
	 */
	public void setSocialAcctId(String socialAcctId) {
		this.socialAcctId = socialAcctId;
	}

	/**
	 * @return the socialAcctToken
	 */
	public String getSocialAcctToken() {
		return this.socialAcctToken;
	}

	/**
	 * @param socialAcctToken
	 *            the socialAcctToken to set
	 */
	public void setSocialAcctToken(String socialAcctToken) {
		this.socialAcctToken = socialAcctToken;
	}

}
