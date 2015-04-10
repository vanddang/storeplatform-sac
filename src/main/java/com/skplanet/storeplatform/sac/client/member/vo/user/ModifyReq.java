package com.skplanet.storeplatform.sac.client.member.vo.user;

import org.hibernate.validator.constraints.NotEmpty;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * [REQUEST] 회원 정보 수정
 * 
 * Updated on : 2014. 1. 24. Updated by : 심대진, 다모아 솔루션.
 */
public class ModifyReq extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/**
	 * 사용자 고유 Key.
	 */
	@NotEmpty
	private String userKey = "";

	/**
	 * IDP 인증 Key.
	 */
	@NotEmpty
	private String userAuthKey = "";

	/**
	 * 사용자 연락처.
	 */
	private String userPhone = "";

	/**
	 * SMS 수신 여부.
	 */
	private String isRecvSms = "";

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
	 * 우편번호.
	 */
	private String userZip = "";

	/**
	 * 거주지 주소.
	 */
	private String userAddress = "";

	/**
	 * 거주지 상세 주소.
	 */
	private String userDetailAddress = "";

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
	 * @return String : userAuthKey
	 */
	public String getUserAuthKey() {
		return this.userAuthKey;
	}

	/**
	 * @param userAuthKey
	 *            String : the userAuthKey to set
	 */
	public void setUserAuthKey(String userAuthKey) {
		this.userAuthKey = userAuthKey;
	}

	/**
	 * @return String : userPhone
	 */
	public String getUserPhone() {
		return this.userPhone;
	}

	/**
	 * @param userPhone
	 *            String : the userPhone to set
	 */
	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	/**
	 * @return String : isRecvSms
	 */
	public String getIsRecvSms() {
		return this.isRecvSms;
	}

	/**
	 * @param isRecvSms
	 *            String : the isRecvSms to set
	 */
	public void setIsRecvSms(String isRecvSms) {
		this.isRecvSms = isRecvSms;
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
	 * @return String : userZip
	 */
	public String getUserZip() {
		return this.userZip;
	}

	/**
	 * @param userZip
	 *            String : the userZip to set
	 */
	public void setUserZip(String userZip) {
		this.userZip = userZip;
	}

	/**
	 * @return String : userAddress
	 */
	public String getUserAddress() {
		return this.userAddress;
	}

	/**
	 * @param userAddress
	 *            String : the userAddress to set
	 */
	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}

	/**
	 * @return String : userDetailAddress
	 */
	public String getUserDetailAddress() {
		return this.userDetailAddress;
	}

	/**
	 * @param userDetailAddress
	 *            String : the userDetailAddress to set
	 */
	public void setUserDetailAddress(String userDetailAddress) {
		this.userDetailAddress = userDetailAddress;
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
