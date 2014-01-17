package com.skplanet.storeplatform.sac.client.member.vo.miscellaneous;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * [REQUEST] 실명 인증용 휴대폰 인증 SMS 발송
 * 
 * Updated on : 2014. 1. 6. Updated by : 김다슬, 인크로스.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class SendSmsForRealNameAuthorizationReq extends CommonInfo {
	private static final long serialVersionUID = 1L;

	/**
	 * 실명인증 대상 이름
	 */
	private String userName;

	/**
	 * 실명인증 대상 생년월일
	 */
	private String userBirthDay;

	/**
	 * 실명인증 대상 통신사 정보
	 */
	private String userPhone;

	/**
	 * 실명인증 대상 성별
	 */
	private String userSex;

	/**
	 * 실명인증 요청 일시
	 */
	private String realNameDate;

	/**
	 * 실명인증 대상 내/외국인 정보
	 */
	private String resident;

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserBirthDay() {
		return this.userBirthDay;
	}

	public void setUserBirthDay(String userBirthDay) {
		this.userBirthDay = userBirthDay;
	}

	public String getUserPhone() {
		return this.userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public String getUserSex() {
		return this.userSex;
	}

	public void setUserSex(String userSex) {
		this.userSex = userSex;
	}

	public String getRealNameDate() {
		return this.realNameDate;
	}

	public void setRealNameDate(String realNameDate) {
		this.realNameDate = realNameDate;
	}

	public String getResident() {
		return this.resident;
	}

	public void setResident(String resident) {
		this.resident = resident;
	}

}
