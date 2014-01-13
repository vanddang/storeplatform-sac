package com.skplanet.storeplatform.sac.client.member.vo.user;

import java.util.List;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.AgreementInfo;

/**
 * [REQUEST] 모바일 전용 회원 가입 (MDN 회원 가입)
 * 
 * Updated on : 2014. 1. 3. Updated by : 심대진, 다모아 솔루션.
 */
public class CreateByMdnReq extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/**
	 * 기기 ID (mdn, uuid).
	 */
	@NotEmpty(message = "필수 파라미터 입니다.")
	private String deviceId;

	/**
	 * 이동 통신사.
	 */
	@NotEmpty(message = "필수 파라미터 입니다.")
	private String deviceTelecom;

	/**
	 * 가입 채널 코드.
	 */
	@NotEmpty(message = "필수 파라미터 입니다.")
	private String joinId;

	/**
	 * 약관동의 정보.
	 */
	@NotEmpty(message = "필수 파라미터 입니다.")
	private List<AgreementInfo> agreementList;

	/**
	 * 본인 생년월일.
	 */
	@Pattern(regexp = "^([0-9]{4})([0-9]{2})([0-9]{2})", message = "유효한 생년월일이 아닙니다.")
	private String ownBirth;

	/**
	 * 법정대리인 관계 코드.
	 */
	private String parentType;

	/**
	 * 법정대리인 인증타입.
	 */
	private String realNameMethod;

	/**
	 * 법정대리인 이름.
	 */
	private String parentName;

	/**
	 * 법정대리인 CI.
	 */
	private String parentCI;

	/**
	 * 법정대리인 이메일.
	 */
	@Email(message = "유효한 Email 주소가 아닙니다.")
	private String parentEmail;

	/**
	 * 법정대리인 통신사 정보.
	 */
	private String parentTelecom;

	/**
	 * 법정대리인 휴대폰 번호.
	 */
	private String parentMdn;

	/**
	 * 법정대리인 내/외국인 구분.
	 */
	private String parentResident;

	/**
	 * 법정대리인 생년월일.
	 */
	private String parentBirth;

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
	 * @return String : parentCI
	 */
	public String getParentCI() {
		return this.parentCI;
	}

	/**
	 * @param parentCI
	 *            String : the parentCI to set
	 */
	public void setParentCI(String parentCI) {
		this.parentCI = parentCI;
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
	 * @return String : parentMdn
	 */
	public String getParentMdn() {
		return this.parentMdn;
	}

	/**
	 * @param parentMdn
	 *            String : the parentMdn to set
	 */
	public void setParentMdn(String parentMdn) {
		this.parentMdn = parentMdn;
	}

	/**
	 * @return String : parentResident
	 */
	public String getParentResident() {
		return this.parentResident;
	}

	/**
	 * @param parentResident
	 *            String : the parentResident to set
	 */
	public void setParentResident(String parentResident) {
		this.parentResident = parentResident;
	}

	/**
	 * @return String : parentBirth
	 */
	public String getParentBirth() {
		return this.parentBirth;
	}

	/**
	 * @param parentBirth
	 *            String : the parentBirth to set
	 */
	public void setParentBirth(String parentBirth) {
		this.parentBirth = parentBirth;
	}

}
