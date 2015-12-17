package com.skplanet.storeplatform.sac.client.member.vo.user;

import org.hibernate.validator.constraints.NotEmpty;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * [REQUEST] 회원 정보 수정
 * 
 * Updated on : 2015. 12. 15. Updated by : 윤보영, 카레즈.
 */
public class ModifyReq extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/**
	 * 사용자 고유 Key.
	 */
	@NotEmpty
	private String userKey = "";

	/**
	 * 이메일 수신 여부.
	 */
	private String isRecvEmail = "";

	/**
	 * 사용자 성별.
	 */
	private String userSex = "";

	/**
	 * 사용자 생년월일.
	 */
	private String userBirthDay = "";

	/**
	 * 사용자 생일.
	 */
	private String userCalendar = "";

	/** 사용자 업데이트 이메일. */
	private String userUpdEmail;

	/**
	 * @return String : userKey
	 */
	public String getUserKey() {
		return this.userKey;
	}

	/**
	 * @param userKey
	 *            String : the userKey to set
	 */
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	/**
	 * @return String : isRecvEmail
	 */
	public String getIsRecvEmail() {
		return this.isRecvEmail;
	}

	/**
	 * @param isRecvEmail
	 *            String : the isRecvEmail to set
	 */
	public void setIsRecvEmail(String isRecvEmail) {
		this.isRecvEmail = isRecvEmail;
	}

	/**
	 * @return String : userSex
	 */
	public String getUserSex() {
		return this.userSex;
	}

	/**
	 * @param userSex
	 *            String : the userSex to set
	 */
	public void setUserSex(String userSex) {
		this.userSex = userSex;
	}

	/**
	 * @return String : userBirthDay
	 */
	public String getUserBirthDay() {
		return this.userBirthDay;
	}

	/**
	 * @param userBirthDay
	 *            String : the userBirthDay to set
	 */
	public void setUserBirthDay(String userBirthDay) {
		this.userBirthDay = userBirthDay;
	}

	/**
	 * @return String : userCalendar
	 */
	public String getUserCalendar() {
		return this.userCalendar;
	}

	/**
	 * @param userCalendar
	 *            String : the userCalendar to set
	 */
	public void setUserCalendar(String userCalendar) {
		this.userCalendar = userCalendar;
	}

	/**
	 * @return the userUpdEmail
	 */
	public String getUserUpdEmail() {
		return this.userUpdEmail;
	}

	/**
	 * @param userUpdEmail
	 *            the userUpdEmail to set
	 */
	public void setUserUpdEmail(String userUpdEmail) {
		this.userUpdEmail = userUpdEmail;
	}

}
