package com.skplanet.storeplatform.sac.client.member.vo.user;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * [REQUEST] 실명 인증 정보 등록
 * 
 * Updated on : 2014. 1. 6. Updated by : 심대진, 다모아 솔루션.
 */
public class CreateRealNameReq extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/**
	 * 사용자 고유 Key
	 */
	@NotEmpty
	private String userKey = "";

	/**
	 * IDP 인증 Key
	 */
	private String userAuthKey = "";

	/**
	 * 실명인증 대상 (OWN/PARENT)
	 */
	@NotEmpty
	@Pattern(regexp = "^OWN|PARENT")
	private String isOwn = "";

	/**
	 * 법정대리인 관계코드 (F/M/O)
	 */
	private String parentType = "";

	/**
	 * 법정대리인 이메일 (isOwn=PARENT 경우 필수)
	 */
	private String parentEmail = "";

	/**
	 * 실명인증 일시
	 */
	private String realNameDate = "";

	/**
	 * CI
	 */
	@NotEmpty
	private String userCi = "";

	/**
	 * DI
	 */
	private String userDi = "";

	/**
	 * 실명인증 수단코드
	 */
	private String realNameMethod = "";

	/**
	 * 실명인증 사이트 코드
	 */
	private String realNameSite = "";

	/**
	 * 이동 통신사
	 */
	private String deviceTelecom = "";

	/**
	 * 사용자 전화번호
	 */
	private String userPhone = "";

	/**
	 * 사용자 이름
	 */
	private String userName = "";

	/**
	 * 사용자/법정대리인 생년월일
	 */
	private String userBirthDay = "";

	/**
	 * 사용자 성별 (M/F)
	 */
	private String userSex = "";

	/**
	 * 실명인증 대상 내•외국인 정보 (local : 내국인, foreign : 외국인)
	 */
	private final String resident = "";

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
	 * @return String : isOwn
	 */
	public String getIsOwn() {
		return this.isOwn;
	}

	/**
	 * @param isOwn
	 *            String : the isOwn to set
	 */
	public void setIsOwn(String isOwn) {
		this.isOwn = isOwn;
	}

	/**
	 * @return String : parentType
	 */
	public String getParentType() {
		return this.parentType;
	}

	/**
	 * @param parentType
	 *            String : the parentType to set
	 */
	public void setParentType(String parentType) {
		this.parentType = parentType;
	}

	/**
	 * @return String : parentEmail
	 */
	public String getParentEmail() {
		return this.parentEmail;
	}

	/**
	 * @param parentEmail
	 *            String : the parentEmail to set
	 */
	public void setParentEmail(String parentEmail) {
		this.parentEmail = parentEmail;
	}

	/**
	 * @return String : realNameDate
	 */
	public String getRealNameDate() {
		return this.realNameDate;
	}

	/**
	 * @param realNameDate
	 *            String : the realNameDate to set
	 */
	public void setRealNameDate(String realNameDate) {
		this.realNameDate = realNameDate;
	}

	/**
	 * @return String : userCi
	 */
	public String getUserCi() {
		return this.userCi;
	}

	/**
	 * @param userCi
	 *            String : the userCi to set
	 */
	public void setUserCi(String userCi) {
		this.userCi = userCi;
	}

	/**
	 * @return String : userDi
	 */
	public String getUserDi() {
		return this.userDi;
	}

	/**
	 * @param userDi
	 *            String : the userDi to set
	 */
	public void setUserDi(String userDi) {
		this.userDi = userDi;
	}

	/**
	 * @return String : realNameMethod
	 */
	public String getRealNameMethod() {
		return this.realNameMethod;
	}

	/**
	 * @param realNameMethod
	 *            String : the realNameMethod to set
	 */
	public void setRealNameMethod(String realNameMethod) {
		this.realNameMethod = realNameMethod;
	}

	/**
	 * @return String : realNameSite
	 */
	public String getRealNameSite() {
		return this.realNameSite;
	}

	/**
	 * @param realNameSite
	 *            String : the realNameSite to set
	 */
	public void setRealNameSite(String realNameSite) {
		this.realNameSite = realNameSite;
	}

	/**
	 * @return String : deviceTelecom
	 */
	public String getDeviceTelecom() {
		return this.deviceTelecom;
	}

	/**
	 * @param deviceTelecom
	 *            String : the deviceTelecom to set
	 */
	public void setDeviceTelecom(String deviceTelecom) {
		this.deviceTelecom = deviceTelecom;
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
	 * @return String : userName
	 */
	public String getUserName() {
		return this.userName;
	}

	/**
	 * @param userName
	 *            String : the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
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
	 * @return String : resident
	 */
	public String getResident() {
		return this.resident;
	}

}
