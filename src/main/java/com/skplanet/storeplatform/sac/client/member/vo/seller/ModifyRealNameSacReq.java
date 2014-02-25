package com.skplanet.storeplatform.sac.client.member.vo.seller;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 2.2.17. 판매자회원 실명 인증 정보 수정 [REQUEST]
 * 
 * Updated on : 2014. 2. 6. Updated by : 김경복, 부르칸
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class ModifyRealNameSacReq extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/** 인증 키. */
	@NotBlank
	private String sessionKey;
	/** 판매자 key. */
	@NotBlank
	private String sellerKey;
	/** 법정대리인 관계코드. */
	@NotBlank
	private String parentType;
	/** 실명인증 대상. */
	@NotBlank
	private String isOwn;
	/** 법정대리인 생년월일. */
	private String parentBirthDay;
	/** 법정대리인 이메일. */
	private String parentEmail;
	/** 실명인증 여부. */
	@NotBlank
	private String isRealName;
	/** 실명인증 일시. */
	private String realNameDate;
	/** CI. */
	@NotBlank
	private String sellerCI;
	/** DI. */
	private String sellerDI;
	/** 실명인증수단 코드. */
	private String realNameMethod;
	/** 이동통신사. */
	private String sellerTelecom;
	/** 전화번호. */
	private String sellerPhone;
	/** 판매자 이름. */
	private String sellerName;
	/** 판매자 생년월일. */
	private String sellerBirthDay;
	/** 내국인 여부. */
	private String isDomestic;
	/** 성별. */
	private String sex;
	/** 동의일시. */
	private String parentDate;

	/**
	 * @return the sessionKey
	 */
	public String getSessionKey() {
		return this.sessionKey;
	}

	/**
	 * @param sessionKey
	 *            the sessionKey to set
	 */
	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}

	/**
	 * @return the sellerKey
	 */
	public String getSellerKey() {
		return this.sellerKey;
	}

	/**
	 * @param sellerKey
	 *            the sellerKey to set
	 */
	public void setSellerKey(String sellerKey) {
		this.sellerKey = sellerKey;
	}

	/**
	 * @return the parentType
	 */
	public String getParentType() {
		return this.parentType;
	}

	/**
	 * @param parentType
	 *            the parentType to set
	 */
	public void setParentType(String parentType) {
		this.parentType = parentType;
	}

	/**
	 * @return the isOwn
	 */
	public String getIsOwn() {
		return this.isOwn;
	}

	/**
	 * @param isOwn
	 *            the isOwn to set
	 */
	public void setIsOwn(String isOwn) {
		this.isOwn = isOwn;
	}

	/**
	 * @return the parentBirthDay
	 */
	public String getParentBirthDay() {
		return this.parentBirthDay;
	}

	/**
	 * @param parentBirthDay
	 *            the parentBirthDay to set
	 */
	public void setParentBirthDay(String parentBirthDay) {
		this.parentBirthDay = parentBirthDay;
	}

	/**
	 * @return the parentEmail
	 */
	public String getParentEmail() {
		return this.parentEmail;
	}

	/**
	 * @param parentEmail
	 *            the parentEmail to set
	 */
	public void setParentEmail(String parentEmail) {
		this.parentEmail = parentEmail;
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
	 * @return the realNameDate
	 */
	public String getRealNameDate() {
		return this.realNameDate;
	}

	/**
	 * @param realNameDate
	 *            the realNameDate to set
	 */
	public void setRealNameDate(String realNameDate) {
		this.realNameDate = realNameDate;
	}

	/**
	 * @return the sellerCI
	 */
	public String getSellerCI() {
		return this.sellerCI;
	}

	/**
	 * @param sellerCI
	 *            the sellerCI to set
	 */
	public void setSellerCI(String sellerCI) {
		this.sellerCI = sellerCI;
	}

	/**
	 * @return the sellerDI
	 */
	public String getSellerDI() {
		return this.sellerDI;
	}

	/**
	 * @param sellerDI
	 *            the sellerDI to set
	 */
	public void setSellerDI(String sellerDI) {
		this.sellerDI = sellerDI;
	}

	/**
	 * @return the realNameMethod
	 */
	public String getRealNameMethod() {
		return this.realNameMethod;
	}

	/**
	 * @param realNameMethod
	 *            the realNameMethod to set
	 */
	public void setRealNameMethod(String realNameMethod) {
		this.realNameMethod = realNameMethod;
	}

	/**
	 * @return the sellerTelecom
	 */
	public String getSellerTelecom() {
		return this.sellerTelecom;
	}

	/**
	 * @param sellerTelecom
	 *            the sellerTelecom to set
	 */
	public void setSellerTelecom(String sellerTelecom) {
		this.sellerTelecom = sellerTelecom;
	}

	/**
	 * @return the sellerPhone
	 */
	public String getSellerPhone() {
		return this.sellerPhone;
	}

	/**
	 * @param sellerPhone
	 *            the sellerPhone to set
	 */
	public void setSellerPhone(String sellerPhone) {
		this.sellerPhone = sellerPhone;
	}

	/**
	 * @return the sellerName
	 */
	public String getSellerName() {
		return this.sellerName;
	}

	/**
	 * @param sellerName
	 *            the sellerName to set
	 */
	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	/**
	 * @return the sellerBirthDay
	 */
	public String getSellerBirthDay() {
		return this.sellerBirthDay;
	}

	/**
	 * @param sellerBirthDay
	 *            the sellerBirthDay to set
	 */
	public void setSellerBirthDay(String sellerBirthDay) {
		this.sellerBirthDay = sellerBirthDay;
	}

	/**
	 * @return the isDomestic
	 */
	public String getIsDomestic() {
		return this.isDomestic;
	}

	/**
	 * @param isDomestic
	 *            the isDomestic to set
	 */
	public void setIsDomestic(String isDomestic) {
		this.isDomestic = isDomestic;
	}

	/**
	 * @return the sex
	 */
	public String getSex() {
		return this.sex;
	}

	/**
	 * @param sex
	 *            the sex to set
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}

	/**
	 * @return the parentDate
	 */
	public String getParentDate() {
		return this.parentDate;
	}

	/**
	 * @param parentDate
	 *            the parentDate to set
	 */
	public void setParentDate(String parentDate) {
		this.parentDate = parentDate;
	}

}
