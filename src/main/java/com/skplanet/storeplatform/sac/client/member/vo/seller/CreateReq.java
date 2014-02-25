package com.skplanet.storeplatform.sac.client.member.vo.seller;

import java.util.List;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.PwReminder;

/**
 * 2.2.1. 판매자 회원 가입 [REQUEST]
 * 
 * Updated on : 2014. 1. 23. Updated by : 김경복, 부르칸.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class CreateReq extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/** 판매자 구분코드. */
	@NotBlank
	private String sellerClass;
	/** 판매자 ID. */
	@NotBlank
	private String sellerId;
	/** 판매자 PW. */
	@NotBlank
	private String sellerPW;
	/** 이동통신사. */
	private String sellerTelecom;
	/** 전화번호 국가코드. */
	private String sellerPhoneCountry;
	/** 전화번호. */
	private String sellerPhone;
	/** SMS 수신 여부. */
	private String isRecvSMS;
	/** 판매자 이메일. */
	@NotBlank
	@Pattern(regexp = "^(?:\\w+\\.?)*\\w+@(?:\\w+\\.)+\\w+$")
	private String sellerEmail;
	/** 이메일수신 여부. */
	private String isRecvEmail;
	/** 판매자 이름. */
	private String sellerName;
	/** 노출 이름. */
	private String sellerNickName;
	/** 판매자 성별. */
	private String sellerSex;
	/** 판매자 생년월일. */
	@Size(max = 8)
	private String sellerBirthDay;
	/** 주민등록번호. */
	private String sellerSSNumber;
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
	/** 내국인 식별. */
	@NotBlank
	@Pattern(regexp = "^Y|^N")
	private String isDomestic;

	/** 실명인증 일시. */
	@Size(max = 14)
	private String realNameDate;
	/** CI. */
	@NotBlank
	private String sellerCI;
	/** DI. */
	private String sellerDI;
	/** 실명인증수단 코드. */
	private String realNameMethod;
	/** 회사명. */
	private String sellerCompany;
	/** 사업자 등록번호. */
	private String sellerBizNumber;
	/** 담당자 유선전화번호 국가코드. */
	private String customerPhoneCountry;
	/** 담당자 유선전화번호. */
	private String customerPhone;
	/** 담당자 이메일. */
	private String customerEmail;
	/** 담당자 명. */
	private String charger;
	/** 담당자 무선 전화 번호. */
	private String chargerPhone;
	/** 법인등록번호. */
	private String sellerBizCorpNumber;

	/** 보안질문 정보들. */
	private List<PwReminder> pwReminderList;

	/**
	 * @return the sellerClass
	 */
	public String getSellerClass() {
		return this.sellerClass;
	}

	/**
	 * @param sellerClass
	 *            the sellerClass to set
	 */
	public void setSellerClass(String sellerClass) {
		this.sellerClass = sellerClass;
	}

	/**
	 * @return the sellerId
	 */
	public String getSellerId() {
		return this.sellerId;
	}

	/**
	 * @param sellerId
	 *            the sellerId to set
	 */
	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}

	/**
	 * @return the sellerPW
	 */
	public String getSellerPW() {
		return this.sellerPW;
	}

	/**
	 * @param sellerPW
	 *            the sellerPW to set
	 */
	public void setSellerPW(String sellerPW) {
		this.sellerPW = sellerPW;
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
	 * @return the sellerPhoneCountry
	 */
	public String getSellerPhoneCountry() {
		return this.sellerPhoneCountry;
	}

	/**
	 * @param sellerPhoneCountry
	 *            the sellerPhoneCountry to set
	 */
	public void setSellerPhoneCountry(String sellerPhoneCountry) {
		this.sellerPhoneCountry = sellerPhoneCountry;
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
	 * @return the sellerEmail
	 */
	public String getSellerEmail() {
		return this.sellerEmail;
	}

	/**
	 * @param sellerEmail
	 *            the sellerEmail to set
	 */
	public void setSellerEmail(String sellerEmail) {
		this.sellerEmail = sellerEmail;
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
	 * @return the sellerNickName
	 */
	public String getSellerNickName() {
		return this.sellerNickName;
	}

	/**
	 * @param sellerNickName
	 *            the sellerNickName to set
	 */
	public void setSellerNickName(String sellerNickName) {
		this.sellerNickName = sellerNickName;
	}

	/**
	 * @return the sellerSex
	 */
	public String getSellerSex() {
		return this.sellerSex;
	}

	/**
	 * @param sellerSex
	 *            the sellerSex to set
	 */
	public void setSellerSex(String sellerSex) {
		this.sellerSex = sellerSex;
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
	 * @return the sellerSSNumber
	 */
	public String getSellerSSNumber() {
		return this.sellerSSNumber;
	}

	/**
	 * @param sellerSSNumber
	 *            the sellerSSNumber to set
	 */
	public void setSellerSSNumber(String sellerSSNumber) {
		this.sellerSSNumber = sellerSSNumber;
	}

	/**
	 * @return the sellerZip
	 */
	public String getSellerZip() {
		return this.sellerZip;
	}

	/**
	 * @param sellerZip
	 *            the sellerZip to set
	 */
	public void setSellerZip(String sellerZip) {
		this.sellerZip = sellerZip;
	}

	/**
	 * @return the sellerAddress
	 */
	public String getSellerAddress() {
		return this.sellerAddress;
	}

	/**
	 * @param sellerAddress
	 *            the sellerAddress to set
	 */
	public void setSellerAddress(String sellerAddress) {
		this.sellerAddress = sellerAddress;
	}

	/**
	 * @return the sellerDetailAddress
	 */
	public String getSellerDetailAddress() {
		return this.sellerDetailAddress;
	}

	/**
	 * @param sellerDetailAddress
	 *            the sellerDetailAddress to set
	 */
	public void setSellerDetailAddress(String sellerDetailAddress) {
		this.sellerDetailAddress = sellerDetailAddress;
	}

	/**
	 * @return the sellerCity
	 */
	public String getSellerCity() {
		return this.sellerCity;
	}

	/**
	 * @param sellerCity
	 *            the sellerCity to set
	 */
	public void setSellerCity(String sellerCity) {
		this.sellerCity = sellerCity;
	}

	/**
	 * @return the sellerState
	 */
	public String getSellerState() {
		return this.sellerState;
	}

	/**
	 * @param sellerState
	 *            the sellerState to set
	 */
	public void setSellerState(String sellerState) {
		this.sellerState = sellerState;
	}

	/**
	 * @return the sellerCountry
	 */
	public String getSellerCountry() {
		return this.sellerCountry;
	}

	/**
	 * @param sellerCountry
	 *            the sellerCountry to set
	 */
	public void setSellerCountry(String sellerCountry) {
		this.sellerCountry = sellerCountry;
	}

	/**
	 * @return the sellerLanguage
	 */
	public String getSellerLanguage() {
		return this.sellerLanguage;
	}

	/**
	 * @param sellerLanguage
	 *            the sellerLanguage to set
	 */
	public void setSellerLanguage(String sellerLanguage) {
		this.sellerLanguage = sellerLanguage;
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
	 * @return the sellerCompany
	 */
	public String getSellerCompany() {
		return this.sellerCompany;
	}

	/**
	 * @param sellerCompany
	 *            the sellerCompany to set
	 */
	public void setSellerCompany(String sellerCompany) {
		this.sellerCompany = sellerCompany;
	}

	/**
	 * @return the sellerBizNumber
	 */
	public String getSellerBizNumber() {
		return this.sellerBizNumber;
	}

	/**
	 * @param sellerBizNumber
	 *            the sellerBizNumber to set
	 */
	public void setSellerBizNumber(String sellerBizNumber) {
		this.sellerBizNumber = sellerBizNumber;
	}

	/**
	 * @return the customerPhoneCountry
	 */
	public String getCustomerPhoneCountry() {
		return this.customerPhoneCountry;
	}

	/**
	 * @param customerPhoneCountry
	 *            the customerPhoneCountry to set
	 */
	public void setCustomerPhoneCountry(String customerPhoneCountry) {
		this.customerPhoneCountry = customerPhoneCountry;
	}

	/**
	 * @return the customerPhone
	 */
	public String getCustomerPhone() {
		return this.customerPhone;
	}

	/**
	 * @param customerPhone
	 *            the customerPhone to set
	 */
	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}

	/**
	 * @return the customerEmail
	 */
	public String getCustomerEmail() {
		return this.customerEmail;
	}

	/**
	 * @param customerEmail
	 *            the customerEmail to set
	 */
	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	/**
	 * @return the charger
	 */
	public String getCharger() {
		return this.charger;
	}

	/**
	 * @param charger
	 *            the charger to set
	 */
	public void setCharger(String charger) {
		this.charger = charger;
	}

	/**
	 * @return the chargerPhone
	 */
	public String getChargerPhone() {
		return this.chargerPhone;
	}

	/**
	 * @param chargerPhone
	 *            the chargerPhone to set
	 */
	public void setChargerPhone(String chargerPhone) {
		this.chargerPhone = chargerPhone;
	}

	/**
	 * @return the sellerBizCorpNumber
	 */
	public String getSellerBizCorpNumber() {
		return this.sellerBizCorpNumber;
	}

	/**
	 * @param sellerBizCorpNumber
	 *            the sellerBizCorpNumber to set
	 */
	public void setSellerBizCorpNumber(String sellerBizCorpNumber) {
		this.sellerBizCorpNumber = sellerBizCorpNumber;
	}

	/**
	 * @return the pwReminderList
	 */
	public List<PwReminder> getPwReminderList() {
		return this.pwReminderList;
	}

	/**
	 * @param pwReminderList
	 *            the pwReminderList to set
	 */
	public void setPwReminderList(List<PwReminder> pwReminderList) {
		this.pwReminderList = pwReminderList;
	}

}
