package com.skplanet.storeplatform.sac.client.member.vo.user;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.AgreementInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.DeviceExtraInfo;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Pattern;
import java.util.List;

/**
 * [REQUEST] ID기반(Social ID) 회원 가입.
 * 
 * Updated on : 2016. 1. 7. Updated by : 반범진.
 */
public class CreateByIdSacReq extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/**
	 * 사용자 아이디.
	 */
	@NotEmpty
	private String userId = "";

	/**
	 * 사용자 구분 코드.
	 */
	@NotEmpty
	@Pattern(regexp = "^US011504|^US011505|^US011506")
	private String userType = "";

	/**
	 * 사용자 인증 토큰.
	 */
	@NotEmpty
	private String userAuthToken = "";

	/**
	 * social아이디 회원 번호.
	 */
	private String socialUserNo = "";

	/**
	 * 기기 ID
	 */
	@NotEmpty
	private String deviceId = "";

	/**
	 * 이동 통신사.
	 */
	@NotEmpty
	private String deviceTelecom = "";

	/**
	 * 사용자 이메일.
	 */
	@NotEmpty
	private String userEmail = "";

	/**
	 * 기기 MDN.
	 */
	private String mdn = "";

	/**
	 * 기기고유 ID (imei).
	 */
	private String nativeId = "";

	/**
	 * 가입자 식별 모듈 ID (USIM 일련번호).
	 */
	private String simSerialNo = "";

	/**
	 * SMS 수신 여부.
	 */
	private String isRecvSms = "";

	/**
	 * 본인의 생년월일.
	 */
	private String ownBirth = "";

	/**
	 * 사용자 단말 부가 정보 리스트.
	 */
	private List<DeviceExtraInfo> deviceExtraInfoList = null;

	/**
	 * 약관동의 정보.
	 */
	@NotEmpty
	private List<AgreementInfo> agreementList = null;

	/**
	 * 법정대리인 동의 여부
	 */
	@NotEmpty
	@Pattern(regexp = "^Y|N")
	private String isParent = "";

	/**
	 * 법정대리인 인증방법코드
	 */
	private String parentRealNameMethod = "";

	/**
	 * 법정대리인 이름
	 */
	private String parentName = "";

	/**
	 * 법정대리인 관계
	 */
	private String parentType = "";

	/**
	 * 법정대리인 동의일시
	 */
	private String parentDate = "";

	/**
	 * 법정대리인 Email
	 */
	private String parentEmail = "";

	/**
	 * 법정대리인 생년월일
	 */
	private String parentBirthDay = "";

	/**
	 * 법정대리인 통신사 코드
	 */
	private String parentTelecom = "";

	/**
	 * 법정대리인 전화번호
	 */
	private String parentPhone = "";

	/**
	 * 법정대리인 CI
	 */
	private String parentCi = "";

	/**
	 * 법정대리인 인증 일시
	 */
	private String parentRealNameDate = "";

	/**
	 * 내국인 여부 Y or N
	 */
	private String parentIsDomestic = "";

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
	 * @return String : nativeId
	 */
	public String getNativeId() {
		return this.nativeId;
	}

	/**
	 * @param nativeId
	 *            String : the nativeId to set
	 */
	public void setNativeId(String nativeId) {
		this.nativeId = nativeId;
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
	 * @return String : ownBirth
	 */
	public String getOwnBirth() {
		return this.ownBirth;
	}

	/**
	 * @param ownBirth
	 *            String : the ownBirth to set
	 */
	public void setOwnBirth(String ownBirth) {
		this.ownBirth = ownBirth;
	}

	/**
	 * @return List<DeviceExtraInfo> : deviceExtraInfoList
	 */
	public List<DeviceExtraInfo> getDeviceExtraInfoList() {
		return this.deviceExtraInfoList;
	}

	/**
	 * @param deviceExtraInfoList
	 *            List<DeviceExtraInfo> : the deviceExtraInfoList to set
	 */
	public void setDeviceExtraInfoList(List<DeviceExtraInfo> deviceExtraInfoList) {
		this.deviceExtraInfoList = deviceExtraInfoList;
	}

	/**
	 * @return List<AgreementInfo> : agreementList
	 */
	public List<AgreementInfo> getAgreementList() {
		return this.agreementList;
	}

	/**
	 * @param agreementList
	 *            List<AgreementInfo> : the agreementList to set
	 */
	public void setAgreementList(List<AgreementInfo> agreementList) {
		this.agreementList = agreementList;
	}

	/**
	 * @return String : isParent
	 */
	public String getIsParent() {
		return this.isParent;
	}

	/**
	 * @param isParent
	 *            String : the isParent to set
	 */
	public void setIsParent(String isParent) {
		this.isParent = isParent;
	}

	/**
	 * @return String : parentRealNameMethod
	 */
	public String getParentRealNameMethod() {
		return this.parentRealNameMethod;
	}

	/**
	 * @param parentRealNameMethod
	 *            String : the parentRealNameMethod to set
	 */
	public void setParentRealNameMethod(String parentRealNameMethod) {
		this.parentRealNameMethod = parentRealNameMethod;
	}

	/**
	 * @return String : parentName
	 */
	public String getParentName() {
		return this.parentName;
	}

	/**
	 * @param parentName
	 *            String : the parentName to set
	 */
	public void setParentName(String parentName) {
		this.parentName = parentName;
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
	 * @return String : parentDate
	 */
	public String getParentDate() {
		return this.parentDate;
	}

	/**
	 * @param parentDate
	 *            String : the parentDate to set
	 */
	public void setParentDate(String parentDate) {
		this.parentDate = parentDate;
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
	 * @return String : parentBirthDay
	 */
	public String getParentBirthDay() {
		return this.parentBirthDay;
	}

	/**
	 * @param parentBirthDay
	 *            String : the parentBirthDay to set
	 */
	public void setParentBirthDay(String parentBirthDay) {
		this.parentBirthDay = parentBirthDay;
	}

	/**
	 * @return String : parentTelecom
	 */
	public String getParentTelecom() {
		return this.parentTelecom;
	}

	/**
	 * @param parentTelecom
	 *            String : the parentTelecom to set
	 */
	public void setParentTelecom(String parentTelecom) {
		this.parentTelecom = parentTelecom;
	}

	/**
	 * @return String : parentPhone
	 */
	public String getParentPhone() {
		return this.parentPhone;
	}

	/**
	 * @param parentPhone
	 *            String : the parentPhone to set
	 */
	public void setParentPhone(String parentPhone) {
		this.parentPhone = parentPhone;
	}

	/**
	 * @return String : parentCi
	 */
	public String getParentCi() {
		return this.parentCi;
	}

	/**
	 * @param parentCi
	 *            String : the parentCi to set
	 */
	public void setParentCi(String parentCi) {
		this.parentCi = parentCi;
	}

	/**
	 * @return String : parentRealNameDate
	 */
	public String getParentRealNameDate() {
		return this.parentRealNameDate;
	}

	/**
	 * @param parentRealNameDate
	 *            String : the parentRealNameDate to set
	 */
	public void setParentRealNameDate(String parentRealNameDate) {
		this.parentRealNameDate = parentRealNameDate;
	}

	/**
	 * @return String : parentIsDomestic
	 */
	public String getParentIsDomestic() {
		return this.parentIsDomestic;
	}

	/**
	 * @param parentIsDomestic
	 *            String : the parentIsDomestic to set
	 */
	public void setParentIsDomestic(String parentIsDomestic) {
		this.parentIsDomestic = parentIsDomestic;
	}

	/**
	 * @return userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId String
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return userType
	 */
	public String getUserType() {
		return userType;
	}

	/**
	 * @param userType String
	 */
	public void setUserType(String userType) {
		this.userType = userType;
	}

	/**
	 * @return userAuthToken
	 */
	public String getUserAuthToken() {
		return userAuthToken;
	}

	/**
	 * @param userAuthToken String
	 */
	public void setUserAuthToken(String userAuthToken) {
		this.userAuthToken = userAuthToken;
	}

	/**
	 * @return userEmail
	 */
	public String getUserEmail() {
		return userEmail;
	}

	/**
	 * @param userEmail String
	 */
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	/**
	 * @return mdn
	 */
	public String getMdn() {
		return mdn;
	}

	/**
	 * @param mdn String
	 */
	public void setMdn(String mdn) {
		this.mdn = mdn;
	}

	/**
	 * @return simSerialNo
	 */
	public String getSimSerialNo() {
		return simSerialNo;
	}

	/**
	 * @param simSerialNo String
	 */
	public void setSimSerialNo(String simSerialNo) {
		this.simSerialNo = simSerialNo;
	}

	/**
	 * @return socialUserNo
	 */
	public String getSocialUserNo() {
		return socialUserNo;
	}

	/**
	 * @param socialUserNo String
	 */
	public void setSocialUserNo(String socialUserNo) {
		this.socialUserNo = socialUserNo;
	}
}
