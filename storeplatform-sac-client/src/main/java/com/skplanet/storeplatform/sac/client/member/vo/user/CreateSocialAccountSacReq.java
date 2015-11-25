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
	private String socialAcctId;
	/** 소셜계정 내부 아이디. */
	@NotBlank
	private String socialAcctIntId;
	/** 소셜계정 이메일. */
	private String socialEmail;
	/** 소셜계정 AccessToken. */
	@NotBlank
	private String socialAcctToken;
	/** 소셜계정 RefreshToken. */
	private String socialRefToken;
	/** 소셜계정 만료시간. */
	private String socialExpiredTime;

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
	 * @return the socialEmail
	 */
	public String getSocialEmail() {
		return this.socialEmail;
	}

	/**
	 * @param socialEmail
	 *            the socialEmail to set
	 */
	public void setSocialEmail(String socialEmail) {
		this.socialEmail = socialEmail;
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

	/**
	 * @return the socialRefToken
	 */
	public String getSocialRefToken() {
		return this.socialRefToken;
	}

	/**
	 * @param socialRefToken
	 *            the socialRefToken to set
	 */
	public void setSocialRefToken(String socialRefToken) {
		this.socialRefToken = socialRefToken;
	}

	/**
	 * @return the socialExpiredTime
	 */
	public String getSocialExpiredTime() {
		return this.socialExpiredTime;
	}

	/**
	 * @param socialExpiredTime
	 *            the socialExpiredTime to set
	 */
	public void setSocialExpiredTime(String socialExpiredTime) {
		this.socialExpiredTime = socialExpiredTime;
	}

}
