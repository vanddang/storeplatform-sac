package com.skplanet.storeplatform.sac.client.member.vo.common;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 사용자 정보
 * 
 * Updated on : 2014. 1. 6. Updated by : 강신완, 부르칸.
 */

@JsonSerialize(include = Inclusion.NON_NULL)
public class UserInfo extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/*
	 * 사용자 휴대기기 등록 대수.
	 */
	private String deviceCount;

	/*
	 * 사용자 휴대기기 전체 등록 대수
	 */
	private String totalDeviceCount;

	/*
	 * 외부(IDP)에서 할당된 사용자 Key.
	 */
	private String imMbrNo;

	/*
	 * OneID 전환 가입일.
	 */
	private String imRegDate;

	/*
	 * 통합포인트 사용 여부.
	 */
	private String isMemberPoint;

	/*
	 * 로그인 상태 코드.
	 */
	private String loginStatusCode;

	/*
	 * 등록일시.
	 */
	private String regDate;

	/*
	 * 탈퇴일자.
	 */
	private String secedeDate;

	/*
	 * 탈퇴 사유 코드.
	 */
	private String secedeReasonCode;

	/*
	 * 탈퇴사유 설명
	 */
	private String secedeReasonMessage;

	/*
	 * 직권중지 상태 코드.
	 */
	private String stopStatusCode;

	/*
	 * 사용자 Key
	 */
	private String userKey;

	/*
	 * 사용자 구분 코드
	 */
	private String userType;

	/*
	 * 사용자 메인 상태코드
	 */
	private String userMainStatus;

	/*
	 * 사용자 서브 상태 코드
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

	/**
	 * 회원의 연령대 정보.
	 */
	private String prodExpoLevl;

	/** 회원의 만나이. */
	private String realAge;

	/* 사용자 부가정보 */
	private List<UserExtraInfo> userExtraInfoList;

	/**
	 * @return the deviceCount
	 */
	public String getDeviceCount() {
		return this.deviceCount;
	}

	/**
	 * @param deviceCount
	 *            the deviceCount to set
	 */
	public void setDeviceCount(String deviceCount) {
		this.deviceCount = deviceCount;
	}

	/**
	 * @return the totalDeviceCount
	 */
	public String getTotalDeviceCount() {
		return this.totalDeviceCount;
	}

	/**
	 * @param totalDeviceCount
	 *            the totalDeviceCount to set
	 */
	public void setTotalDeviceCount(String totalDeviceCount) {
		this.totalDeviceCount = totalDeviceCount;
	}

	/**
	 * @return the imMbrNo
	 */
	public String getImMbrNo() {
		return this.imMbrNo;
	}

	/**
	 * @param imMbrNo
	 *            the imMbrNo to set
	 */
	public void setImMbrNo(String imMbrNo) {
		this.imMbrNo = imMbrNo;
	}

	/**
	 * @return the imRegDate
	 */
	public String getImRegDate() {
		return this.imRegDate;
	}

	/**
	 * @param imRegDate
	 *            the imRegDate to set
	 */
	public void setImRegDate(String imRegDate) {
		this.imRegDate = imRegDate;
	}

	/**
	 * @return the isMemberPoint
	 */
	public String getIsMemberPoint() {
		return this.isMemberPoint;
	}

	/**
	 * @param isMemberPoint
	 *            the isMemberPoint to set
	 */
	public void setIsMemberPoint(String isMemberPoint) {
		this.isMemberPoint = isMemberPoint;
	}

	/**
	 * @return the loginStatusCode
	 */
	public String getLoginStatusCode() {
		return this.loginStatusCode;
	}

	/**
	 * @param loginStatusCode
	 *            the loginStatusCode to set
	 */
	public void setLoginStatusCode(String loginStatusCode) {
		this.loginStatusCode = loginStatusCode;
	}

	/**
	 * @return the regDate
	 */
	public String getRegDate() {
		return this.regDate;
	}

	/**
	 * @param regDate
	 *            the regDate to set
	 */
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	/**
	 * @return the secedeDate
	 */
	public String getSecedeDate() {
		return this.secedeDate;
	}

	/**
	 * @param secedeDate
	 *            the secedeDate to set
	 */
	public void setSecedeDate(String secedeDate) {
		this.secedeDate = secedeDate;
	}

	/**
	 * @return the secedeReasonCode
	 */
	public String getSecedeReasonCode() {
		return this.secedeReasonCode;
	}

	/**
	 * @param secedeReasonCode
	 *            the secedeReasonCode to set
	 */
	public void setSecedeReasonCode(String secedeReasonCode) {
		this.secedeReasonCode = secedeReasonCode;
	}

	/**
	 * @return the secedeReasonMessage
	 */
	public String getSecedeReasonMessage() {
		return this.secedeReasonMessage;
	}

	/**
	 * @param secedeReasonMessage
	 *            the secedeReasonMessage to set
	 */
	public void setSecedeReasonMessage(String secedeReasonMessage) {
		this.secedeReasonMessage = secedeReasonMessage;
	}

	/**
	 * @return the stopStatusCode
	 */
	public String getStopStatusCode() {
		return this.stopStatusCode;
	}

	/**
	 * @param stopStatusCode
	 *            the stopStatusCode to set
	 */
	public void setStopStatusCode(String stopStatusCode) {
		this.stopStatusCode = stopStatusCode;
	}

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
	 * @return the userType
	 */
	public String getUserType() {
		return this.userType;
	}

	/**
	 * @param userType
	 *            the userType to set
	 */
	public void setUserType(String userType) {
		this.userType = userType;
	}

	/**
	 * @return the userMainStatus
	 */
	public String getUserMainStatus() {
		return this.userMainStatus;
	}

	/**
	 * @param userMainStatus
	 *            the userMainStatus to set
	 */
	public void setUserMainStatus(String userMainStatus) {
		this.userMainStatus = userMainStatus;
	}

	/**
	 * @return the userSubStatus
	 */
	public String getUserSubStatus() {
		return this.userSubStatus;
	}

	/**
	 * @param userSubStatus
	 *            the userSubStatus to set
	 */
	public void setUserSubStatus(String userSubStatus) {
		this.userSubStatus = userSubStatus;
	}

	/**
	 * @return the imSvcNo
	 */
	public String getImSvcNo() {
		return this.imSvcNo;
	}

	/**
	 * @param imSvcNo
	 *            the imSvcNo to set
	 */
	public void setImSvcNo(String imSvcNo) {
		this.imSvcNo = imSvcNo;
	}

	/**
	 * @return the isImChanged
	 */
	public String getIsImChanged() {
		return this.isImChanged;
	}

	/**
	 * @param isImChanged
	 *            the isImChanged to set
	 */
	public void setIsImChanged(String isImChanged) {
		this.isImChanged = isImChanged;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return this.userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the userTelecom
	 */
	public String getUserTelecom() {
		return this.userTelecom;
	}

	/**
	 * @param userTelecom
	 *            the userTelecom to set
	 */
	public void setUserTelecom(String userTelecom) {
		this.userTelecom = userTelecom;
	}

	/**
	 * @return the userPhoneCountry
	 */
	public String getUserPhoneCountry() {
		return this.userPhoneCountry;
	}

	/**
	 * @param userPhoneCountry
	 *            the userPhoneCountry to set
	 */
	public void setUserPhoneCountry(String userPhoneCountry) {
		this.userPhoneCountry = userPhoneCountry;
	}

	/**
	 * @return the userPhone
	 */
	public String getUserPhone() {
		return this.userPhone;
	}

	/**
	 * @param userPhone
	 *            the userPhone to set
	 */
	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	/**
	 * @return the isRecvSMS
	 */
	public String getIsRecvSMS() {
		return this.isRecvSMS;
	}

	/**
	 * @param isRecvSMS
	 *            the isRecvSMS to set
	 */
	public void setIsRecvSMS(String isRecvSMS) {
		this.isRecvSMS = isRecvSMS;
	}

	/**
	 * @return the userEmail
	 */
	public String getUserEmail() {
		return this.userEmail;
	}

	/**
	 * @param userEmail
	 *            the userEmail to set
	 */
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	/**
	 * @return the isRecvEmail
	 */
	public String getIsRecvEmail() {
		return this.isRecvEmail;
	}

	/**
	 * @param isRecvEmail
	 *            the isRecvEmail to set
	 */
	public void setIsRecvEmail(String isRecvEmail) {
		this.isRecvEmail = isRecvEmail;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return this.userName;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
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
	 * @return the userZip
	 */
	public String getUserZip() {
		return this.userZip;
	}

	/**
	 * @param userZip
	 *            the userZip to set
	 */
	public void setUserZip(String userZip) {
		this.userZip = userZip;
	}

	/**
	 * @return the userAddress
	 */
	public String getUserAddress() {
		return this.userAddress;
	}

	/**
	 * @param userAddress
	 *            the userAddress to set
	 */
	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}

	/**
	 * @return the userDetailAddress
	 */
	public String getUserDetailAddress() {
		return this.userDetailAddress;
	}

	/**
	 * @param userDetailAddress
	 *            the userDetailAddress to set
	 */
	public void setUserDetailAddress(String userDetailAddress) {
		this.userDetailAddress = userDetailAddress;
	}

	/**
	 * @return the userCity
	 */
	public String getUserCity() {
		return this.userCity;
	}

	/**
	 * @param userCity
	 *            the userCity to set
	 */
	public void setUserCity(String userCity) {
		this.userCity = userCity;
	}

	/**
	 * @return the userState
	 */
	public String getUserState() {
		return this.userState;
	}

	/**
	 * @param userState
	 *            the userState to set
	 */
	public void setUserState(String userState) {
		this.userState = userState;
	}

	/**
	 * @return the userCountry
	 */
	public String getUserCountry() {
		return this.userCountry;
	}

	/**
	 * @param userCountry
	 *            the userCountry to set
	 */
	public void setUserCountry(String userCountry) {
		this.userCountry = userCountry;
	}

	/**
	 * @return the userLanguage
	 */
	public String getUserLanguage() {
		return this.userLanguage;
	}

	/**
	 * @param userLanguage
	 *            the userLanguage to set
	 */
	public void setUserLanguage(String userLanguage) {
		this.userLanguage = userLanguage;
	}

	/**
	 * @return the isParent
	 */
	public String getIsParent() {
		return this.isParent;
	}

	/**
	 * @param isParent
	 *            the isParent to set
	 */
	public void setIsParent(String isParent) {
		this.isParent = isParent;
	}

	/**
	 * @return the isRealName
	 */
	public String getIsRealName() {
		return this.isRealName;
	}

	/**
	 * @param isRealName
	 *            the isRealName to set
	 */
	public void setIsRealName(String isRealName) {
		this.isRealName = isRealName;
	}

	/**
	 * @return the imSiteCode
	 */
	public String getImSiteCode() {
		return this.imSiteCode;
	}

	/**
	 * @param imSiteCode
	 *            the imSiteCode to set
	 */
	public void setImSiteCode(String imSiteCode) {
		this.imSiteCode = imSiteCode;
	}

	/**
	 * @return the prodExpoLevl
	 */
	public String getProdExpoLevl() {
		return this.prodExpoLevl;
	}

	/**
	 * @param prodExpoLevl
	 *            the prodExpoLevl to set
	 */
	public void setProdExpoLevl(String prodExpoLevl) {
		this.prodExpoLevl = prodExpoLevl;
	}

	/**
	 * @return the realAge
	 */
	public String getRealAge() {
		return this.realAge;
	}

	/**
	 * @param realAge
	 *            the realAge to set
	 */
	public void setRealAge(String realAge) {
		this.realAge = realAge;
	}

	/**
	 * @return the userExtraInfoList
	 */
	public List<UserExtraInfo> getUserExtraInfoList() {
		return this.userExtraInfoList;
	}

	/**
	 * @param userExtraInfoList
	 *            the userExtraInfoList to set
	 */
	public void setUserExtraInfoList(List<UserExtraInfo> userExtraInfoList) {
		this.userExtraInfoList = userExtraInfoList;
	}

}
