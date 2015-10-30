package com.skplanet.storeplatform.sac.client.internal.member.user.vo;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 2.1.9. 회원 segment 정보 조회 [RESPONSE].
 * 
 * Updated on : 2014. 9. 22. Updated by : Rejoice, Burkhan
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class SearchUserSegmentSacRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/**
	 * 회원 키.
	 */
	private String userKey;
	/**
	 * 회원 등급.
	 */
	private String userGradeCd;
	/**
	 * 회원 성별.
	 */
	private String userSex;
	/**
	 * 회원 생년월일.
	 */
	private String userBirthDay;
	/**
	 * 기변 여부.
	 */
	private String isChanged;
	/**
	 * 등록 일자.
	 */
	private String entryDay;

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
	 * @return the userGradeCd
	 */
	public String getUserGradeCd() {
		return this.userGradeCd;
	}

	/**
	 * @param userGradeCd
	 *            the userGradeCd to set
	 */
	public void setUserGradeCd(String userGradeCd) {
		this.userGradeCd = userGradeCd;
	}

	/**
	 * @return the userSex
	 */
	public String getUserSex() {
		return this.userSex;
	}

	/**
	 * @param userSex
	 *            the userSex to set
	 */
	public void setUserSex(String userSex) {
		this.userSex = userSex;
	}

	/**
	 * @return the userBirthDay
	 */
	public String getUserBirthDay() {
		return this.userBirthDay;
	}

	/**
	 * @param userBirthDay
	 *            the userBirthDay to set
	 */
	public void setUserBirthDay(String userBirthDay) {
		this.userBirthDay = userBirthDay;
	}

	/**
	 * @return the isChanged
	 */
	public String getIsChanged() {
		return this.isChanged;
	}

	/**
	 * @param isChanged
	 *            the isChanged to set
	 */
	public void setIsChanged(String isChanged) {
		this.isChanged = isChanged;
	}

	/**
	 * @return the entryDay
	 */
	public String getEntryDay() {
		return this.entryDay;
	}

	/**
	 * @param entryDay
	 *            the entryDay to set
	 */
	public void setEntryDay(String entryDay) {
		this.entryDay = entryDay;
	}

}
