package com.skplanet.storeplatform.sac.client.member.vo.user;

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
	private String userKey = "";

	/**
	 * IDP 인증 Key
	 */
	private String userAuthKey = "";

	/**
	 * 기기 ID
	 */
	private String deviceId = "";

	/**
	 * 기기 ID 타입
	 */
	private String deviceIdType = "";

	/**
	 * 사용자 생년월일
	 */
	private String userBirth = "";

	/**
	 * 실명인증 대상
	 */
	private String isOwn = "";

	/**
	 * 이름
	 */
	private String userName = "";

	/**
	 * 성별
	 */
	private String userSex = "";

	/**
	 * 내/외국인 구분
	 */
	private String resident = "";

	/**
	 * 이동 통신사
	 */
	private String deviceTelecom = "";

	/**
	 * 법정대리인 이메일
	 */
	private String parentEmail = "";

	/**
	 * 법정대리인 생년월일
	 */
	private String parentBirthday = "";

	/**
	 * 실명인증 CI
	 */
	private String userCi = "";

	/**
	 * 실명인증 DI
	 */
	private String userDi = "";

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
	 * @return String : deviceId
	 */
	public String getDeviceId() {
		return this.deviceId;
	}

	/**
	 * @param deviceId
	 *            String : the deviceId to set
	 */
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	/**
	 * @return String : deviceIdType
	 */
	public String getDeviceIdType() {
		return this.deviceIdType;
	}

	/**
	 * @param deviceIdType
	 *            String : the deviceIdType to set
	 */
	public void setDeviceIdType(String deviceIdType) {
		this.deviceIdType = deviceIdType;
	}

	/**
	 * @return String : userBirth
	 */
	public String getUserBirth() {
		return this.userBirth;
	}

	/**
	 * @param userBirth
	 *            String : the userBirth to set
	 */
	public void setUserBirth(String userBirth) {
		this.userBirth = userBirth;
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

	/**
	 * @param resident
	 *            String : the resident to set
	 */
	public void setResident(String resident) {
		this.resident = resident;
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
	 * @return String : parentBirthday
	 */
	public String getParentBirthday() {
		return this.parentBirthday;
	}

	/**
	 * @param parentBirthday
	 *            String : the parentBirthday to set
	 */
	public void setParentBirthday(String parentBirthday) {
		this.parentBirthday = parentBirthday;
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

}
