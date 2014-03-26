package com.skplanet.storeplatform.sac.client.member.vo.common;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 판매자 정보 Value
 * 
 * Updated on : 2014. 1. 7. Updated by : 한서구, 부르칸.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class SellerMbrSac extends CommonInfo {
	private static final long serialVersionUID = 1L;

	/** 판매자 key . */
	private String sellerKey;
	/** 판매자 key . */
	private String sellerId;
	/** 판매자 이름 . */
	private String sellerName;
	/** 판매자 성별 . */
	private String sellerSex;
	/** 판매자 생년월일 . */
	private String sellerBirthDay;
	/** 주민번호. */
	private String sellerSSNumber;
	/** 실명인증 여부 */
	private String isRealName;
	/** 판매자 Email. */
	private String sellerEmail;
	/** 담당자 명 . */
	private String charger;
	/** 담당자 이메일 . */
	private String customerEmail;
	/** 무선전화 국가번호 . */
	private String sellerPhoneCountry;
	/** 무선전화 번호 . */
	private String sellerPhone;
	/** 이동통신사 . */
	private String sellerTelecom;
	/** 유선전화 국가번호 . */
	private String cordedTelephoneCountry;
	/** 유선전화 번호 . */
	private String cordedTelephone;
	/** 대표전화 국가 번호 . */
	private String repPhoneArea;
	/** 대표 전화 번호 . */
	private String repPhone;
	/** 대표 이메일 . */
	private String repEmail;
	/** 판매자 분류 코드. */
	private String sellerCategory;
	/** 판매자 구분 코드. */
	private String sellerClass;
	/** 판매자 메인상태 코드. */
	private String sellerMainStatus;
	/** 판매자 서브상태 코드. */
	private String sellerSubStatus;
	/** 사업자 등록번호 . */
	private String sellerBizNumber;
	/** 회사명 . */
	private String sellerCompany;
	/** 언어코드 . */
	private String sellerLanguage;
	/** 국가코드 . */
	private String sellerCountry;
	/** (외국인)도시 . */
	private String sellerCity;
	/** (외국인)주 . */
	private String sellerState;
	/** 거주지 주소 . */
	private String sellerAddress;
	/** 거주지 상세주소 . */
	private String sellerDetailAddress;
	/** 우편번호 . */
	private String sellerZip;
	/** 쇼핑 노출명. */
	private String sellerNickName;
	/** 내국인여부 . */
	private String isDomestic;
	/** 이메일수신 여부 . */
	private String isRecvEmail;
	/** SMS 수신 여부 . */
	private String isRecvSms;
	/** 웹 사이트 . */
	private String website;
	/** 등록일시. */
	private String regDate;

	// 구매 요청으로 통신판매업 관련 파라미터 3개 추가. 2014.03.26
	/** 통신판매업 신고여부. */
	private String isBizRegistered;
	/** 통신판매업 신고번호. */
	private String bizRegNumber;
	/** 통신판매업 미신고사유 코드. */
	private String bizUnregReason;

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
	 * @return the cordedTelephoneCountry
	 */
	public String getCordedTelephoneCountry() {
		return this.cordedTelephoneCountry;
	}

	/**
	 * @param cordedTelephoneCountry
	 *            the cordedTelephoneCountry to set
	 */
	public void setCordedTelephoneCountry(String cordedTelephoneCountry) {
		this.cordedTelephoneCountry = cordedTelephoneCountry;
	}

	/**
	 * @return the cordedTelephone
	 */
	public String getCordedTelephone() {
		return this.cordedTelephone;
	}

	/**
	 * @param cordedTelephone
	 *            the cordedTelephone to set
	 */
	public void setCordedTelephone(String cordedTelephone) {
		this.cordedTelephone = cordedTelephone;
	}

	/**
	 * @return the isRecvSms
	 */
	public String getIsRecvSms() {
		return this.isRecvSms;
	}

	/**
	 * @param isRecvSms
	 *            the isRecvSms to set
	 */
	public void setIsRecvSms(String isRecvSms) {
		this.isRecvSms = isRecvSms;
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
	 * @return the repPhoneArea
	 */
	public String getRepPhoneArea() {
		return this.repPhoneArea;
	}

	/**
	 * @param repPhoneArea
	 *            the repPhoneArea to set
	 */
	public void setRepPhoneArea(String repPhoneArea) {
		this.repPhoneArea = repPhoneArea;
	}

	/**
	 * @return the repPhone
	 */
	public String getRepPhone() {
		return this.repPhone;
	}

	/**
	 * @param repPhone
	 *            the repPhone to set
	 */
	public void setRepPhone(String repPhone) {
		this.repPhone = repPhone;
	}

	/**
	 * @return the repEmail
	 */
	public String getRepEmail() {
		return this.repEmail;
	}

	/**
	 * @param repEmail
	 *            the repEmail to set
	 */
	public void setRepEmail(String repEmail) {
		this.repEmail = repEmail;
	}

	/**
	 * @return the website
	 */
	public String getWebsite() {
		return this.website;
	}

	/**
	 * @param website
	 *            the website to set
	 */
	public void setWebsite(String website) {
		this.website = website;
	}

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
	 * @return the sellerCategory
	 */
	public String getSellerCategory() {
		return this.sellerCategory;
	}

	/**
	 * @param sellerCategory
	 *            the sellerCategory to set
	 */
	public void setSellerCategory(String sellerCategory) {
		this.sellerCategory = sellerCategory;
	}

	/**
	 * @return the sellerMainStatus
	 */
	public String getSellerMainStatus() {
		return this.sellerMainStatus;
	}

	/**
	 * @param sellerMainStatus
	 *            the sellerMainStatus to set
	 */
	public void setSellerMainStatus(String sellerMainStatus) {
		this.sellerMainStatus = sellerMainStatus;
	}

	/**
	 * @return the sellerSubStatus
	 */
	public String getSellerSubStatus() {
		return this.sellerSubStatus;
	}

	/**
	 * @param sellerSubStatus
	 *            the sellerSubStatus to set
	 */
	public void setSellerSubStatus(String sellerSubStatus) {
		this.sellerSubStatus = sellerSubStatus;
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
	 * @return the isBizRegistered
	 */
	public String getIsBizRegistered() {
		return this.isBizRegistered;
	}

	/**
	 * @param isBizRegistered
	 *            the isBizRegistered to set
	 */
	public void setIsBizRegistered(String isBizRegistered) {
		this.isBizRegistered = isBizRegistered;
	}

	/**
	 * @return the bizRegNumber
	 */
	public String getBizRegNumber() {
		return this.bizRegNumber;
	}

	/**
	 * @param bizRegNumber
	 *            the bizRegNumber to set
	 */
	public void setBizRegNumber(String bizRegNumber) {
		this.bizRegNumber = bizRegNumber;
	}

	/**
	 * @return the bizUnregReason
	 */
	public String getBizUnregReason() {
		return this.bizUnregReason;
	}

	/**
	 * @param bizUnregReason
	 *            the bizUnregReason to set
	 */
	public void setBizUnregReason(String bizUnregReason) {
		this.bizUnregReason = bizUnregReason;
	}

}
