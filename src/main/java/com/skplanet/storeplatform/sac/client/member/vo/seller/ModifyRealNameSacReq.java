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

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 인증 키. */
	@NotBlank
	private String sessionKey;
	/** 판매자 key. */
	@NotBlank
	private String sellerKey;
	/** 법정대리인 관계코드. */
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
	private String sellerCI;
	/** DI. */
	private String sellerDI;
	/** 실명인증수단 코드. */
	private String realNameMethod;
	/** 실명인증사이트 코드. */
	private String realNameSystemId;
	/** 이동통신사. */
	private String sellerTelecom;
	/** 전화번호. */
	private String sellerPhone;
	/** 판매자 이름. */
	private String sellerName;
	/** 판매자 생년월일. */
	private String sellerBirthDay;

	public String getSessionKey() {
		return this.sessionKey;
	}

	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}

	public String getSellerKey() {
		return this.sellerKey;
	}

	public void setSellerKey(String sellerKey) {
		this.sellerKey = sellerKey;
	}

	public String getParentType() {
		return this.parentType;
	}

	public void setParentType(String parentType) {
		this.parentType = parentType;
	}

	public String getIsOwn() {
		return this.isOwn;
	}

	public void setIsOwn(String isOwn) {
		this.isOwn = isOwn;
	}

	public String getParentBirthDay() {
		return this.parentBirthDay;
	}

	public void setParentBirthDay(String parentBirthDay) {
		this.parentBirthDay = parentBirthDay;
	}

	public String getParentEmail() {
		return this.parentEmail;
	}

	public void setParentEmail(String parentEmail) {
		this.parentEmail = parentEmail;
	}

	public String getIsRealName() {
		return this.isRealName;
	}

	public void setIsRealName(String isRealName) {
		this.isRealName = isRealName;
	}

	public String getRealNameDate() {
		return this.realNameDate;
	}

	public void setRealNameDate(String realNameDate) {
		this.realNameDate = realNameDate;
	}

	public String getSellerCI() {
		return this.sellerCI;
	}

	public void setSellerCI(String sellerCI) {
		this.sellerCI = sellerCI;
	}

	public String getSellerDI() {
		return this.sellerDI;
	}

	public void setSellerDI(String sellerDI) {
		this.sellerDI = sellerDI;
	}

	public String getRealNameMethod() {
		return this.realNameMethod;
	}

	public void setRealNameMethod(String realNameMethod) {
		this.realNameMethod = realNameMethod;
	}

	public String getRealNameSystemId() {
		return this.realNameSystemId;
	}

	public void setRealNameSystemId(String realNameSystemId) {
		this.realNameSystemId = realNameSystemId;
	}

	public String getSellerTelecom() {
		return this.sellerTelecom;
	}

	public void setSellerTelecom(String sellerTelecom) {
		this.sellerTelecom = sellerTelecom;
	}

	public String getSellerPhone() {
		return this.sellerPhone;
	}

	public void setSellerPhone(String sellerPhone) {
		this.sellerPhone = sellerPhone;
	}

	public String getSellerName() {
		return this.sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	public String getSellerBirthDay() {
		return this.sellerBirthDay;
	}

	public void setSellerBirthDay(String sellerBirthDay) {
		this.sellerBirthDay = sellerBirthDay;
	}

}
