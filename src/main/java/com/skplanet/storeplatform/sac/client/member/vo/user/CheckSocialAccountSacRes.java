package com.skplanet.storeplatform.sac.client.member.vo.user;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 2.1.58. 소셜 계정 등록 가능 여부 체크 [RESPONSE].
 * 
 * Updated on : 2015. 6. 3. Updated by : Rejoice, Burkhan
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class CheckSocialAccountSacRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/** 사용자 key. */
	private String userKey;
	/** 등록 가능 여부. */
	private String regYn;
	/** 동일 소셜 계정 등록 회원 수. */
	private String socialMemberCnt;
	/** 소셜 계정 등록 횟수. */
	private String socialRegCnt;
	/** 소셜 계정 등록 가능 일자(YYYYMMDD). */
	private String socialRegDate;

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
	 * @return the regYn
	 */
	public String getRegYn() {
		return this.regYn;
	}

	/**
	 * @param regYn
	 *            the regYn to set
	 */
	public void setRegYn(String regYn) {
		this.regYn = regYn;
	}

	/**
	 * @return the socialMemberCnt
	 */
	public String getSocialMemberCnt() {
		return this.socialMemberCnt;
	}

	/**
	 * @param socialMemberCnt
	 *            the socialMemberCnt to set
	 */
	public void setSocialMemberCnt(String socialMemberCnt) {
		this.socialMemberCnt = socialMemberCnt;
	}

	/**
	 * @return the socialRegCnt
	 */
	public String getSocialRegCnt() {
		return this.socialRegCnt;
	}

	/**
	 * @param socialRegCnt
	 *            the socialRegCnt to set
	 */
	public void setSocialRegCnt(String socialRegCnt) {
		this.socialRegCnt = socialRegCnt;
	}

	/**
	 * @return the socialRegDate
	 */
	public String getSocialRegDate() {
		return this.socialRegDate;
	}

	/**
	 * @param socialRegDate
	 *            the socialRegDate to set
	 */
	public void setSocialRegDate(String socialRegDate) {
		this.socialRegDate = socialRegDate;
	}

}
