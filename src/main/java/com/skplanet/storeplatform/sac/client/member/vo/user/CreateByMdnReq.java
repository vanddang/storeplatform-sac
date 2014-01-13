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
	 * 기기 ID (mdn, uuid)
	 */
	@NotEmpty(message = "필수 파라미터 입니다.")
	private String deviceId;

	/**
	 * 이동 통신사
	 */
	@NotEmpty(message = "필수 파라미터 입니다.")
	private String deviceTelecom;

	/**
	 * 가입 채널 코드
	 */
	@NotEmpty(message = "필수 파라미터 입니다.")
	private String joinId;

	/**
	 * 약관동의 정보
	 */
	@NotEmpty(message = "필수 파라미터 입니다.")
	private List<AgreementInfo> agreementList;

	/**
	 * 본인 생년월일
	 */
	@Pattern(regexp = "^([0-9]{4})([0-9]{2})([0-9]{2})", message = "유효한 생년월일이 아닙니다.")
	private String ownBirth;

	/**
	 * 법정대리인 관계 코드
	 */
	private String parentType;

	/**
	 * 법정대리인 인증타입
	 */
	private String realNameMethod;

	/**
	 * 법정대리인 이름
	 */
	private String parentName;

	/**
	 * 법정대리인 CI
	 */
	private String parentCI;

	/**
	 * 법정대리인 이메일
	 */
	@Email(message = "유효한 Email 주소가 아닙니다.")
	private String parentEmail;

	/**
	 * 법정대리인 통신사 정보
	 */
	private String parentTelecom;

	/**
	 * 법정대리인 휴대폰 번호
	 */
	private String parentMdn;

	/**
	 * 법정대리인 내/외국인 구분
	 */
	private String parentResident;

	/**
	 * 법정대리인 생년월일
	 */
	private String parentBirth;

	public String getDeviceId() {
		return this.deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getDeviceTelecom() {
		return this.deviceTelecom;
	}

	public void setDeviceTelecom(String deviceTelecom) {
		this.deviceTelecom = deviceTelecom;
	}

	public String getJoinId() {
		return this.joinId;
	}

	public void setJoinId(String joinId) {
		this.joinId = joinId;
	}

	public List<AgreementInfo> getAgreementList() {
		return this.agreementList;
	}

	public void setAgreementList(List<AgreementInfo> agreementList) {
		this.agreementList = agreementList;
	}

	public String getOwnBirth() {
		return this.ownBirth;
	}

	public void setOwnBirth(String ownBirth) {
		this.ownBirth = ownBirth;
	}

	public String getParentType() {
		return this.parentType;
	}

	public void setParentType(String parentType) {
		this.parentType = parentType;
	}

	public String getRealNameMethod() {
		return this.realNameMethod;
	}

	public void setRealNameMethod(String realNameMethod) {
		this.realNameMethod = realNameMethod;
	}

	public String getParentName() {
		return this.parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getParentCI() {
		return this.parentCI;
	}

	public void setParentCI(String parentCI) {
		this.parentCI = parentCI;
	}

	public String getParentEmail() {
		return this.parentEmail;
	}

	public void setParentEmail(String parentEmail) {
		this.parentEmail = parentEmail;
	}

	public String getParentTelecom() {
		return this.parentTelecom;
	}

	public void setParentTelecom(String parentTelecom) {
		this.parentTelecom = parentTelecom;
	}

	public String getParentMdn() {
		return this.parentMdn;
	}

	public void setParentMdn(String parentMdn) {
		this.parentMdn = parentMdn;
	}

	public String getParentResident() {
		return this.parentResident;
	}

	public void setParentResident(String parentResident) {
		this.parentResident = parentResident;
	}

	public String getParentBirth() {
		return this.parentBirth;
	}

	public void setParentBirth(String parentBirth) {
		this.parentBirth = parentBirth;
	}

}
