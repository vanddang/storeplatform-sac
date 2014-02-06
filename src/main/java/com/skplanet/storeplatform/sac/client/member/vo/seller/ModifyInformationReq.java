package com.skplanet.storeplatform.sac.client.member.vo.seller;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 5.2.10. 판매자회원 기본정보 수정 [REQUEST]
 * 
 * Updated on : 2014. 1. 24. Updated by : 김경복, 부르칸
 */
public class ModifyInformationReq extends CommonInfo {

	private static final long serialVersionUID = -6272645047957208675L;

	/** 판매자 key. */
	@NotBlank
	private String sellerKey;
	/** 인증키. */
	@NotBlank
	private String sessionKey;
	/** 판매자 구분코드. */
	@NotBlank
	private String sellerClass;
	/** 판매자 분류코드. */
	@NotBlank
	private String sellerCategory;
	/** 판매자 main 상태코드. */
	@NotBlank
	private String sellerMainStatus;
	/** 판매자 sub 상태코드. */
	@NotBlank
	private String sellerSubStatus;
	/** 판매자 ID. */
	@NotBlank
	private String sellerId;
	/** 이동통신사. */
	private String sellerTelecom;
	/** 전화번호 국가코드. */
	private String sellerPhoneCountry;
	/** 전화번호. */
	private String sellerPhone;
	/** SMS 수신 여부. */
	@Pattern(regexp = "^Y|^N")
	private String isRecvSMS;
	/** 판매자 이메일. */
	@Pattern(regexp = "^(?:\\w+\\.?)*\\w+@(?:\\w+\\.)+\\w+$")
	private String sellerEmail;
	/** 이메일수신 여부. */
	@Pattern(regexp = "^Y|^N")
	private String isRecvEmail;
	/** 판매자 이름. */
	private String sellerName;
	/** 판매자 성별. */
	@Pattern(regexp = "^M|^F")
	private String sellerSex;
	/** 판매자 생년월일. */
	@Size(max = 8)
	private String sellerBirthDay;
	/** 우편번호. */
	private String sellerZip;
	/** 거주지 주소. */
	private String sellerAddress;
	/** 거주지 상세주소. */
	private String sellerDetailAddress;
	/** (외국인)도시. */
	private String sellerCity;
	/** (외국인)주. */
	private String sellerState;
	/** 국가코드. */
	@NotBlank
	private String sellerCountry;
	/** 언어코드. */
	@NotBlank
	private String sellerLanguage;
	/** 식별코드. */
	@NotBlank
	@Pattern(regexp = "^Y|^N")
	private String isDomestic;
	/** 법정대리인 동의여부. */
	@NotBlank
	@Pattern(regexp = "^Y|^N")
	private String isParent;
	/** 법정대리인 동의일시. */
	private String parentDate;
	/** 법정대리인 관계코드. */
	@NotBlank
	private String parentType;
	/** 법정대리인 이름. */
	private String parentName;
	/** 법정대리인 생년월일. */
	private String parentBirthDay;
	/** 법정대리인 이메일. */
	@Pattern(regexp = "^(?:\\w+\\.?)*\\w+@(?:\\w+\\.)+\\w+$")
	private String parentEmail;
	/** 법정대리인 휴대폰 번호. */
	private String parentMDN;
	/** 법정대리인 이동통신사. */
	private String parentTelecom;
	/** 법정대리인 실명인증 일시. */
	private String parentRealNameDate;
	/** 법정대리인 CI. */
	private String parentCI;
	/** 법정대리인 실명인증수단 코드. */
	private String parentRealNameMethod;
	/** 법정대리인 실명인증 시스템 id. */
	private String parentRealNameSystemId;
	/** 실명인증 여부. */
	@NotBlank
	@Pattern(regexp = "^Y|^N")
	private String isRealName;
	/** 실명인증 일시. */
	@Size(max = 14)
	private String realNameDate;
	/** CI. */
	private String sellerCI;
	/** DI. */
	private String sellerDI;
	/** 실명인증수단 코드. */
	private String realNameMethod;
	/** 실명인증사이트 코드. */
	private String realNameSystemId;
	/** 회사명. */
	private String sellerCompany;
	/** 사업자 등록번호. */
	private String sellerBizNumber;
	/** 고객 응대 전화번호 국가코드. */
	private String customerPhoneCountry;
	/** 고객 응대 전화번호. */
	private String customerPhone;
	/** 고객 응대 이메일. */
	private String customerEmail;
	/** Flurry 정보. */
	private String flurryInfo;

	public String getSellerKey() {
		return this.sellerKey;
	}

	public void setSellerKey(String sellerKey) {
		this.sellerKey = sellerKey;
	}

	public String getSessionKey() {
		return this.sessionKey;
	}

	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}

	public String getSellerClass() {
		return this.sellerClass;
	}

	public void setSellerClass(String sellerClass) {
		this.sellerClass = sellerClass;
	}

	public String getSellerCategory() {
		return this.sellerCategory;
	}

	public void setSellerCategory(String sellerCategory) {
		this.sellerCategory = sellerCategory;
	}

	public String getSellerMainStatus() {
		return this.sellerMainStatus;
	}

	public void setSellerMainStatus(String sellerMainStatus) {
		this.sellerMainStatus = sellerMainStatus;
	}

	public String getSellerSubStatus() {
		return this.sellerSubStatus;
	}

	public void setSellerSubStatus(String sellerSubStatus) {
		this.sellerSubStatus = sellerSubStatus;
	}

	public String getSellerId() {
		return this.sellerId;
	}

	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}

	public String getSellerTelecom() {
		return this.sellerTelecom;
	}

	public void setSellerTelecom(String sellerTelecom) {
		this.sellerTelecom = sellerTelecom;
	}

	public String getSellerPhoneCountry() {
		return this.sellerPhoneCountry;
	}

	public void setSellerPhoneCountry(String sellerPhoneCountry) {
		this.sellerPhoneCountry = sellerPhoneCountry;
	}

	public String getSellerPhone() {
		return this.sellerPhone;
	}

	public void setSellerPhone(String sellerPhone) {
		this.sellerPhone = sellerPhone;
	}

	public String getIsRecvSMS() {
		return this.isRecvSMS;
	}

	public void setIsRecvSMS(String isRecvSMS) {
		this.isRecvSMS = isRecvSMS;
	}

	public String getSellerEmail() {
		return this.sellerEmail;
	}

	public void setSellerEmail(String sellerEmail) {
		this.sellerEmail = sellerEmail;
	}

	public String getIsRecvEmail() {
		return this.isRecvEmail;
	}

	public void setIsRecvEmail(String isRecvEmail) {
		this.isRecvEmail = isRecvEmail;
	}

	public String getSellerName() {
		return this.sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	public String getSellerSex() {
		return this.sellerSex;
	}

	public void setSellerSex(String sellerSex) {
		this.sellerSex = sellerSex;
	}

	public String getSellerBirthDay() {
		return this.sellerBirthDay;
	}

	public void setSellerBirthDay(String sellerBirthDay) {
		this.sellerBirthDay = sellerBirthDay;
	}

	public String getSellerZip() {
		return this.sellerZip;
	}

	public void setSellerZip(String sellerZip) {
		this.sellerZip = sellerZip;
	}

	public String getSellerAddress() {
		return this.sellerAddress;
	}

	public void setSellerAddress(String sellerAddress) {
		this.sellerAddress = sellerAddress;
	}

	public String getSellerDetailAddress() {
		return this.sellerDetailAddress;
	}

	public void setSellerDetailAddress(String sellerDetailAddress) {
		this.sellerDetailAddress = sellerDetailAddress;
	}

	public String getSellerCity() {
		return this.sellerCity;
	}

	public void setSellerCity(String sellerCity) {
		this.sellerCity = sellerCity;
	}

	public String getSellerState() {
		return this.sellerState;
	}

	public void setSellerState(String sellerState) {
		this.sellerState = sellerState;
	}

	public String getSellerCountry() {
		return this.sellerCountry;
	}

	public void setSellerCountry(String sellerCountry) {
		this.sellerCountry = sellerCountry;
	}

	public String getSellerLanguage() {
		return this.sellerLanguage;
	}

	public void setSellerLanguage(String sellerLanguage) {
		this.sellerLanguage = sellerLanguage;
	}

	public String getIsDomestic() {
		return this.isDomestic;
	}

	public void setIsDomestic(String isDomestic) {
		this.isDomestic = isDomestic;
	}

	public String getIsParent() {
		return this.isParent;
	}

	public void setIsParent(String isParent) {
		this.isParent = isParent;
	}

	public String getParentDate() {
		return this.parentDate;
	}

	public void setParentDate(String parentDate) {
		this.parentDate = parentDate;
	}

	public String getParentType() {
		return this.parentType;
	}

	public void setParentType(String parentType) {
		this.parentType = parentType;
	}

	public String getParentName() {
		return this.parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
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

	public String getParentMDN() {
		return this.parentMDN;
	}

	public void setParentMDN(String parentMDN) {
		this.parentMDN = parentMDN;
	}

	public String getParentTelecom() {
		return this.parentTelecom;
	}

	public void setParentTelecom(String parentTelecom) {
		this.parentTelecom = parentTelecom;
	}

	public String getParentRealNameDate() {
		return this.parentRealNameDate;
	}

	public void setParentRealNameDate(String parentRealNameDate) {
		this.parentRealNameDate = parentRealNameDate;
	}

	public String getParentCI() {
		return this.parentCI;
	}

	public void setParentCI(String parentCI) {
		this.parentCI = parentCI;
	}

	public String getParentRealNameMethod() {
		return this.parentRealNameMethod;
	}

	public void setParentRealNameMethod(String parentRealNameMethod) {
		this.parentRealNameMethod = parentRealNameMethod;
	}

	public String getParentRealNameSystemId() {
		return this.parentRealNameSystemId;
	}

	public void setParentRealNameSystemId(String parentRealNameSystemId) {
		this.parentRealNameSystemId = parentRealNameSystemId;
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

	public String getSellerCompany() {
		return this.sellerCompany;
	}

	public void setSellerCompany(String sellerCompany) {
		this.sellerCompany = sellerCompany;
	}

	public String getSellerBizNumber() {
		return this.sellerBizNumber;
	}

	public void setSellerBizNumber(String sellerBizNumber) {
		this.sellerBizNumber = sellerBizNumber;
	}

	public String getCustomerPhoneCountry() {
		return this.customerPhoneCountry;
	}

	public void setCustomerPhoneCountry(String customerPhoneCountry) {
		this.customerPhoneCountry = customerPhoneCountry;
	}

	public String getCustomerPhone() {
		return this.customerPhone;
	}

	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}

	public String getCustomerEmail() {
		return this.customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	public String getFlurryInfo() {
		return this.flurryInfo;
	}

	public void setFlurryInfo(String flurryInfo) {
		this.flurryInfo = flurryInfo;
	}

}
