package com.skplanet.storeplatform.sac.client.member.vo.common;

import java.util.List;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 사용자 정보
 * 
 * Updated on : 2014. 1. 6. Updated by : 강신완, 부르칸.
 */
public class UserInfo extends CommonInfo {
	/*
	 * 사용자 Key
	 */
	private String userKey;

	/*
	 * 사용자 구분 코드 - US011501 : 기기 사용자 - US011502 : IDP 사용자 - US011503 : OneID 사용자 - null : Tstore 회원 아님
	 */
	private String userType;

	/*
	 * 사용자 메인 상태코드 - US010701 : 정상 - US010702 : 탈퇴 - US010703 : 대기(가가입) - US010704 : 가입 - US010705 : 전환 - US010706 : 탈퇴
	 * - US010707 : 승인대기
	 */
	private String userMainStatus;

	/*
	 * 사용자 서브 상태 코드 - US010201 : 모바일전용회원 - US010202 : 가가입승인대기 - US010203 : 정상 - US010204 : 일시정지 - US010205 : 자의탈퇴 -
	 * US010206 : 이메일변경승인대기 - US010207 : 가입승인 만료 - US010208 : 직권탈퇴
	 */
	private String userSubStatus;

	/*
	 * 통합서비스 관리 번호
	 */
	private String imSvcNo;

	/*
	 * 전환가입 코드
	 */
	private String isImChanged;

	/*
	 * OneID 전환 가입일
	 */
	private String imRegDat;

	/*
	 * 사용자 아이디
	 */
	private String userId;

	/*
	 * 가입된 이동통신사 코드
	 */
	private String userTelecom;

	/*
	 * 연락처 국가코드
	 */
	private String userPhoneCountry;

	/*
	 * 사용자 연락처
	 */
	private String userPhone;

	/*
	 * SMS 수신 여부
	 */
	private String isRecvSMS;

	/*
	 * 사용자 이메일
	 */
	private String userEmail;

	/*
	 * 이메일 수신 여부
	 */
	private String isRecvEmail;

	/*
	 * 사용자 징계여부
	 */
	private String isRestricted;

	/*
	 * 징계 시작일
	 */
	private String restrictStartDate;

	/*
	 * 징계 종료일
	 */
	private String restrictEndDate;

	/*
	 * 징계구분
	 */
	private String restrictId;

	/*
	 * 징계 게시물수
	 */
	private String restrictCount;

	/*
	 * 징계 등록일
	 */
	private String restrictRegisterDate;

	/*
	 * 징계 등록자
	 */
	private String restrictOwner;

	/*
	 * 사용자명
	 */
	private String userName;

	/*
	 * 사용자 성별
	 */
	private String userSex;

	/*
	 * 사용자 생년월일
	 */
	private String userBirthDay;

	/*
	 * 우편번호
	 */
	private String userZip;

	/*
	 * 거주지 주소
	 */
	private String userAddress;

	/*
	 * 거주지 상세주소
	 */
	private String userDetailAddress;

	/*
	 * (외국인) 도시
	 */
	private String userCity;

	/*
	 * (외국인) 주
	 */
	private String userState;

	/*
	 * 국가코드
	 */
	private String userCountry;

	/*
	 * 언어코드
	 */
	private String userLanguage;

	/*
	 * 법정대리인 동의 여부
	 */
	private String isParent;

	/*
	 * 실명 인증 여부
	 */
	private String isRealName;

	/*
	 * 이용동의 사이트 정보
	 */
	private String imSiteCode;

	/*
	 * 사용자 단말 정보
	 */
	private String userDeviceInfo;

	/*
	 * 실명 인증 정보
	 */
	private String realNameInfo;

	/* 사용자 부가정보 */
	private List<UserExtraInfo> userExtraInfo;

	public String getUserKey() {
		return this.userKey;
	}

	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	public String getUserType() {
		return this.userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getUserMainStatus() {
		return this.userMainStatus;
	}

	public void setUserMainStatus(String userMainStatus) {
		this.userMainStatus = userMainStatus;
	}

	public String getUserSubStatus() {
		return this.userSubStatus;
	}

	public void setUserSubStatus(String userSubStatus) {
		this.userSubStatus = userSubStatus;
	}

	public String getImSvcNo() {
		return this.imSvcNo;
	}

	public void setImSvcNo(String imSvcNo) {
		this.imSvcNo = imSvcNo;
	}

	public String getIsImChanged() {
		return this.isImChanged;
	}

	public void setIsImChanged(String isImChanged) {
		this.isImChanged = isImChanged;
	}

	public String getImRegDat() {
		return this.imRegDat;
	}

	public void setImRegDat(String imRegDat) {
		this.imRegDat = imRegDat;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserTelecom() {
		return this.userTelecom;
	}

	public void setUserTelecom(String userTelecom) {
		this.userTelecom = userTelecom;
	}

	public String getUserPhoneCountry() {
		return this.userPhoneCountry;
	}

	public void setUserPhoneCountry(String userPhoneCountry) {
		this.userPhoneCountry = userPhoneCountry;
	}

	public String getUserPhone() {
		return this.userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public String getIsRecvSMS() {
		return this.isRecvSMS;
	}

	public void setIsRecvSMS(String isRecvSMS) {
		this.isRecvSMS = isRecvSMS;
	}

	public String getUserEmail() {
		return this.userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getIsRecvEmail() {
		return this.isRecvEmail;
	}

	public void setIsRecvEmail(String isRecvEmail) {
		this.isRecvEmail = isRecvEmail;
	}

	public String getIsRestricted() {
		return this.isRestricted;
	}

	public void setIsRestricted(String isRestricted) {
		this.isRestricted = isRestricted;
	}

	public String getRestrictStartDate() {
		return this.restrictStartDate;
	}

	public void setRestrictStartDate(String restrictStartDate) {
		this.restrictStartDate = restrictStartDate;
	}

	public String getRestrictEndDate() {
		return this.restrictEndDate;
	}

	public void setRestrictEndDate(String restrictEndDate) {
		this.restrictEndDate = restrictEndDate;
	}

	public String getRestrictId() {
		return this.restrictId;
	}

	public void setRestrictId(String restrictId) {
		this.restrictId = restrictId;
	}

	public String getRestrictCount() {
		return this.restrictCount;
	}

	public void setRestrictCount(String restrictCount) {
		this.restrictCount = restrictCount;
	}

	public String getRestrictRegisterDate() {
		return this.restrictRegisterDate;
	}

	public void setRestrictRegisterDate(String restrictRegisterDate) {
		this.restrictRegisterDate = restrictRegisterDate;
	}

	public String getRestrictOwner() {
		return this.restrictOwner;
	}

	public void setRestrictOwner(String restrictOwner) {
		this.restrictOwner = restrictOwner;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserSex() {
		return this.userSex;
	}

	public void setUserSex(String userSex) {
		this.userSex = userSex;
	}

	public String getUserBirthDay() {
		return this.userBirthDay;
	}

	public void setUserBirthDay(String userBirthDay) {
		this.userBirthDay = userBirthDay;
	}

	public String getUserZip() {
		return this.userZip;
	}

	public void setUserZip(String userZip) {
		this.userZip = userZip;
	}

	public String getUserAddress() {
		return this.userAddress;
	}

	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}

	public String getUserDetailAddress() {
		return this.userDetailAddress;
	}

	public void setUserDetailAddress(String userDetailAddress) {
		this.userDetailAddress = userDetailAddress;
	}

	public String getUserCity() {
		return this.userCity;
	}

	public void setUserCity(String userCity) {
		this.userCity = userCity;
	}

	public String getUserState() {
		return this.userState;
	}

	public void setUserState(String userState) {
		this.userState = userState;
	}

	public String getUserCountry() {
		return this.userCountry;
	}

	public void setUserCountry(String userCountry) {
		this.userCountry = userCountry;
	}

	public String getUserLanguage() {
		return this.userLanguage;
	}

	public void setUserLanguage(String userLanguage) {
		this.userLanguage = userLanguage;
	}

	public String getIsParent() {
		return this.isParent;
	}

	public void setIsParent(String isParent) {
		this.isParent = isParent;
	}

	public String getIsRealName() {
		return this.isRealName;
	}

	public void setIsRealName(String isRealName) {
		this.isRealName = isRealName;
	}

	public String getImSiteCode() {
		return this.imSiteCode;
	}

	public void setImSiteCode(String imSiteCode) {
		this.imSiteCode = imSiteCode;
	}

	public String getUserDeviceInfo() {
		return this.userDeviceInfo;
	}

	public void setUserDeviceInfo(String userDeviceInfo) {
		this.userDeviceInfo = userDeviceInfo;
	}

	public String getRealNameInfo() {
		return this.realNameInfo;
	}

	public void setRealNameInfo(String realNameInfo) {
		this.realNameInfo = realNameInfo;
	}

	public List<UserExtraInfo> getUserExtraInfo() {
		return this.userExtraInfo;
	}

	public void setUserExtraInfo(List<UserExtraInfo> userExtraInfo) {
		this.userExtraInfo = userExtraInfo;
	}

}
