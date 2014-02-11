package com.skplanet.storeplatform.sac.client.member.vo.user;

import java.util.List;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.AgreementInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.DeviceExtraInfo;

/**
 * [REQUEST] 모바일 전용 회원 가입 (MDN 회원 가입)
 * 
 * Updated on : 2014. 1. 3. Updated by : 심대진, 다모아 솔루션.
 */
public class CreateByMdnReq extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/**
	 * 기기 ID
	 */
	@NotEmpty
	private String deviceId = "";

	/**
	 * 기기 ID 타입
	 */
	@NotEmpty
	private String deviceIdType = "";

	/**
	 * 이동 통신사.
	 */
	@NotEmpty
	private String deviceTelecom = "";

	/**
	 * 기기고유 ID (imei).
	 */
	private String nativeId = "";

	/**
	 * 기기 계정 (Gmail).
	 */
	private String deviceAccount = "";

	/**
	 * 가입 채널 코드.
	 */
	private String joinId = "";

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
	 * 법정대리인 실명인증사이트 코드
	 */
	private String parentRealNameSite = "";

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
	 * @return String : deviceAccount
	 */
	public String getDeviceAccount() {
		return this.deviceAccount;
	}

	/**
	 * @param deviceAccount
	 *            String : the deviceAccount to set
	 */
	public void setDeviceAccount(String deviceAccount) {
		this.deviceAccount = deviceAccount;
	}

	/**
	 * @return String : joinId
	 */
	public String getJoinId() {
		return this.joinId;
	}

	/**
	 * @param joinId
	 *            String : the joinId to set
	 */
	public void setJoinId(String joinId) {
		this.joinId = joinId;
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
	 * @return String : parentRealNameSite
	 */
	public String getParentRealNameSite() {
		return this.parentRealNameSite;
	}

	/**
	 * @param parentRealNameSite
	 *            String : the parentRealNameSite to set
	 */
	public void setParentRealNameSite(String parentRealNameSite) {
		this.parentRealNameSite = parentRealNameSite;
	}

}
