package com.skplanet.storeplatform.member.client.user.sci.vo;

import java.io.Serializable;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * CI Infra 에 제공할 회원 정보.
 * 
 * Updated on : 2014. 10. 2. Updated by : 반범진, SK 플래닛.
 */
public class CIInfraUserInfo extends CommonInfo implements Serializable {

	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	/** 테넌트 ID. */
	private String tenantID;

	/** 테넌트의 시스템 ID. */
	private String systemID;

	/**
	 * IDP/OneId 에서 관리하는 회원 Identity Key.
	 */
	private String imMbrNo;

	/**
	 * 통합 서비스 번호.
	 */
	private String imSvcNo;

	/**
	 * T Store가 관리하는 회원 Identity Key.
	 */
	private String userKey;

	/**
	 * 회원 구분.
	 */
	private String userType;

	/**
	 * 가입자 아이디(아이디 회원).
	 */
	private String userId;

	/**
	 * 가입자의 MDN(모바일 회원).
	 */
	private String mdn;

	/**
	 * 가입자 이름.
	 */
	private String name;

	/**
	 * 가입자 생년월일.
	 */
	private String birth;

	/**
	 * 성별.
	 */
	private String sex;

	/**
	 * 전화번호(연락처).
	 */
	private String phone;

	/**
	 * DI 정보.
	 */
	private String di;

	/**
	 * String CI 정보.
	 */
	private String ci;

	/**
	 * 처리시간(신규 : 가입일, 정보변경 : 수정일, 탈퇴 : 탈퇴일).
	 */
	private String lastTime;

	/**
	 * <pre>
	 * IDP/OneId 에서 관리하는 회원 Identity Key 리턴.
	 * </pre>
	 * 
	 * @return imMbrNo
	 */
	public String getImMbrNo() {
		return this.imMbrNo;
	}

	/**
	 * <pre>
	 * IDP/OneId 에서 관리하는 회원 Identity Key 셋팅.
	 * </pre>
	 * 
	 * @param imMbrNo
	 *            String
	 */
	public void setImMbrNo(String imMbrNo) {
		this.imMbrNo = imMbrNo;
	}

	/**
	 * <pre>
	 * T Store가 관리하는 회원 Identity Key 리턴.
	 * </pre>
	 * 
	 * @return userKey
	 */
	public String getUserKey() {
		return this.userKey;
	}

	/**
	 * <pre>
	 * T Store가 관리하는 회원 Identity Key 셋팅.
	 * </pre>
	 * 
	 * @param userKey
	 *            String
	 */
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	/**
	 * <pre>
	 * 회원 구분 리턴.
	 * </pre>
	 * 
	 * @return userType
	 */
	public String getUserType() {
		return this.userType;
	}

	/**
	 * <pre>
	 * 회원 구분 셋팅.
	 * </pre>
	 * 
	 * @param userType
	 *            String
	 */
	public void setUserType(String userType) {
		this.userType = userType;
	}

	/**
	 * <pre>
	 * 처리시간 리턴.
	 * </pre>
	 * 
	 * @return lastTime
	 */
	public String getLastTime() {
		return this.lastTime;
	}

	/**
	 * <pre>
	 * 처리시간 셋팅.
	 * </pre>
	 * 
	 * @param lastTime
	 *            String
	 */
	public void setLastTime(String lastTime) {
		this.lastTime = lastTime;
	}

	/**
	 * <pre>
	 * 통합서비스관리번호 리턴.
	 * </pre>
	 * 
	 * @return imSvcNo
	 */
	public String getImSvcNo() {
		return this.imSvcNo;
	}

	/**
	 * <pre>
	 * 통합서비스관리번호 셋팅.
	 * </pre>
	 * 
	 * @param imSvcNo
	 *            String
	 */
	public void setImSvcNo(String imSvcNo) {
		this.imSvcNo = imSvcNo;
	}

	/**
	 * <pre>
	 * 가입자 아이디(아이디 회원) 리턴.
	 * </pre>
	 * 
	 * @return userId
	 */
	public String getUserId() {
		return this.userId;
	}

	/**
	 * <pre>
	 * 가입자 아이디(아이디 회원) 셋팅.
	 * </pre>
	 * 
	 * @param userId
	 *            String
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * <pre>
	 * 가입자의 MDN(모바일 회원) 리턴.
	 * </pre>
	 * 
	 * @return mdn
	 */
	public String getMdn() {
		return this.mdn;
	}

	/**
	 * <pre>
	 * 가입자의 MDN(모바일 회원) 셋팅.
	 * </pre>
	 * 
	 * @param mdn
	 *            String
	 */
	public void setMdn(String mdn) {
		this.mdn = mdn;
	}

	/**
	 * <pre>
	 * 가입자 이름 리턴.
	 * </pre>
	 * 
	 * @return name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * <pre>
	 * 가입자 이름 셋팅.
	 * </pre>
	 * 
	 * @param name
	 *            String
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * <pre>
	 * 가입자 생년월일 리턴.
	 * </pre>
	 * 
	 * @return birth
	 */
	public String getBirth() {
		return this.birth;
	}

	/**
	 * <pre>
	 * 가입자 생년월일 셋팅.
	 * </pre>
	 * 
	 * @param birth
	 *            String
	 */
	public void setBirth(String birth) {
		this.birth = birth;
	}

	/**
	 * <pre>
	 * 성별 리턴.
	 * </pre>
	 * 
	 * @return sex
	 */
	public String getSex() {
		return this.sex;
	}

	/**
	 * <pre>
	 * 성별 셋팅.
	 * </pre>
	 * 
	 * @param sex
	 *            String
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}

	/**
	 * <pre>
	 * 전화번호(연락처) 리턴.
	 * </pre>
	 * 
	 * @return phone
	 */
	public String getPhone() {
		return this.phone;
	}

	/**
	 * <pre>
	 * 전화번호(연락처) 셋팅.
	 * </pre>
	 * 
	 * @param phone
	 *            String
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * <pre>
	 * DI 정보 리턴.
	 * </pre>
	 * 
	 * @return di
	 */
	public String getDi() {
		return this.di;
	}

	/**
	 * <pre>
	 * DI 정보 셋팅.
	 * </pre>
	 * 
	 * @param di
	 *            String
	 */
	public void setDi(String di) {
		this.di = di;
	}

	/**
	 * <pre>
	 * CI 정보 리턴.
	 * </pre>
	 * 
	 * @return ci
	 */
	public String getCi() {
		return this.ci;
	}

	/**
	 * <pre>
	 * CI 정보 셋팅.
	 * </pre>
	 * 
	 * @param ci
	 *            String
	 */
	public void setCi(String ci) {
		this.ci = ci;
	}

	/**
	 * 테넌트 ID를 리턴한다.
	 * 
	 * @return tenantID - 테넌트 ID
	 */
	public String getTenantID() {
		return this.tenantID;
	}

	/**
	 * 테넌트 ID를 설정한다.
	 * 
	 * @param tenantID
	 *            테넌트 ID
	 */
	public void setTenantID(String tenantID) {
		this.tenantID = tenantID;
	}

	/**
	 * 테넌트의 시스템 ID를 리턴한다.
	 * 
	 * @return systemID - 시스템 ID
	 */
	public String getSystemID() {
		return this.systemID;
	}

	/**
	 * 테넌트의 시스템 ID를 설정한다.
	 * 
	 * @param systemID
	 *            시스템 ID
	 */
	public void setSystemID(String systemID) {
		this.systemID = systemID;
	}

}
