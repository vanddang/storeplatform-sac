package com.skplanet.storeplatform.sac.client.member.vo.seller;

import java.util.List;

import javax.validation.constraints.Pattern;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.FlurryAuth;

/**
 * 2.2.10. 판매자회원 기본정보 수정 [REQUEST]
 * 
 * Updated on : 2014. 1. 24. Updated by : 김경복, 부르칸
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class ModifyInformationSacReq extends CommonInfo {

	private static final long serialVersionUID = -6272645047957208675L;

	/** 판매자 key. */
	@NotBlank
	private String sellerKey;
	/** 인증키. */
	@NotBlank
	private String sessionKey;
	/** 이동통신사. */
	private String sellerTelecom;
	/** 전화번호 국가코드. */
	private String sellerPhoneCountry;
	/** 전화번호. */
	private String sellerPhone;
	/** SMS 수신 여부. */
	private String isRecvSMS;
	/** 이메일수신 여부. */
	private String isRecvEmail;
	/** 판매자 이름. */
	private String sellerName;
	/** 판매자 성별. */
	private String sellerSex;
	/** 판매자 생년월일. */
	// @Size(max = 8)
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
	/** 회사명. */
	private String sellerCompany;
	/** 사업자 등록번호. */
	private String sellerBizNumber;
	/** 담당자 유선 전화번호 국가코드. */
	private String customerPhoneCountry;
	/** 담당자 유선 전화번호. */
	private String customerPhone;
	/** 담당자 이메일. */
	private String customerEmail;
	/** 담당자 명. */
	private String charger;
	/** 담당자 무선 전화번호. */
	private String chargerphone;
	private List<FlurryAuth> flurryAuthList;

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

	public String getCharger() {
		return this.charger;
	}

	public void setCharger(String charger) {
		this.charger = charger;
	}

	public String getChargerphone() {
		return this.chargerphone;
	}

	public void setChargerphone(String chargerphone) {
		this.chargerphone = chargerphone;
	}

	public List<FlurryAuth> getFlurryAuthList() {
		return this.flurryAuthList;
	}

	public void setFlurryAuthList(List<FlurryAuth> flurryAuthList) {
		this.flurryAuthList = flurryAuthList;
	}

}
