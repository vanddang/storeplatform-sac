package com.skplanet.storeplatform.sac.client.member.vo.common;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 판매자회원 정산 정보 조회 판매자 판매자 정산정보
 * 
 * Updated on : 2014. 1. 7. Updated by : 한서구, 부르칸.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class SellerAccount extends CommonInfo {

	private static final long serialVersionUID = 1L;
	/**
	 * 판매자 Key.
	 */
	private String sellerKey;
	/**
	 * 판매자 법인 업종명.
	 */
	private String sellerBizType;
	/**
	 * 판매자 법인 업종카테고리.
	 */
	private String sellerBizCategory;
	/**
	 * 판매자 법인 등록번호.
	 */
	private String sellerBizCorpNumber;
	/**
	 * CEO 생년월일.
	 */
	private String ceoBirthDay;
	/**
	 * CEO명.
	 */
	private String ceoName;
	/**
	 * 판매자 법인 우편번호.
	 */
	private String sellerBizZip;
	/**
	 * 판매자 법인 주소.
	 */
	private String sellerBizAddress;
	/**
	 * 판매자 법인 상세 주소.
	 */
	private String sellerBizDetailAddress;
	/**
	 * 사업장 전화 국가번호.
	 */
	private String sellerBizPhoneCountry;
	/**
	 * 사업장 전화 번호.
	 */
	private String sellerBizPhone;
	/**
	 * 벤더 코드.
	 */
	private String vendorCode;
	/**
	 * 통신판매업 신고 여부.
	 */
	private String isBizRegistered;
	/**
	 * 통신판매업 신고 번호.
	 */
	private String bizRegNumber;
	/**
	 * 통신판매업 미신고 사유 코드.
	 */
	private String bizUnregReason;
	/**
	 * 간이과세 여부.
	 */
	private String isBizTaxable;
	/**
	 * 등급 코드.
	 */
	private String bizGrade;
	/**
	 * 자동 차감 가능 대상 여부.
	 */
	private String isDeductible;
	/**
	 * 입점 상점 코드.
	 */
	private String marketCode;
	/**
	 * 입점 상태 코드.
	 */
	private String marketStatus;
	/**
	 * 은행 코드.
	 */
	private String bankCode;
	/**
	 * 은행명.
	 */
	private String bankName;
	/**
	 * 은행 지점명.
	 */
	private String bankBranch;
	/**
	 * 은행 지점 코드.
	 */
	private String bankBranchCode;
	/**
	 * 은행 주소.
	 */
	private String bankAddress;
	/**
	 * 은행 위치.
	 */
	private String bankLocation;
	/**
	 * 해외 bsb.
	 */
	private String bankBsb;
	/**
	 * 계좌번호.
	 */
	private String bankAccount;
	/**
	 * 예금자명.
	 */
	private String bankAcctName;
	/**
	 * 공인 인증 여부.
	 */
	private String isOfficialAuth;
	/**
	 * 계좌 인증 여부.
	 */
	private String isAccountReal;
	/**
	 * 계좌인증일시.
	 */
	private String accountRealDate;
	/**
	 * ABA 코드.
	 */
	private String abaCode;
	/**
	 * IBAN 코드.
	 */
	private String ibanCode;
	/**
	 * Swift 코드.
	 */
	private String swiftCode;
	/**
	 * T I-PIN 코드.
	 */
	private String tpinCode;

	/**
	 * @return the abaCode
	 */
	public String getAbaCode() {
		return this.abaCode;
	}

	/**
	 * @param abaCode
	 *            the abaCode to set
	 */
	public void setAbaCode(String abaCode) {
		this.abaCode = abaCode;
	}

	/**
	 * @return the accountRealDate
	 */
	public String getAccountRealDate() {
		return this.accountRealDate;
	}

	/**
	 * @param accountRealDate
	 *            the accountRealDate to set
	 */
	public void setAccountRealDate(String accountRealDate) {
		this.accountRealDate = accountRealDate;
	}

	/**
	 * @return the bankAccount
	 */
	public String getBankAccount() {
		return this.bankAccount;
	}

	/**
	 * @param bankAccount
	 *            the bankAccount to set
	 */
	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	/**
	 * @return the bankAcctName
	 */
	public String getBankAcctName() {
		return this.bankAcctName;
	}

	/**
	 * @param bankAcctName
	 *            the bankAcctName to set
	 */
	public void setBankAcctName(String bankAcctName) {
		this.bankAcctName = bankAcctName;
	}

	/**
	 * @return the bankAddress
	 */
	public String getBankAddress() {
		return this.bankAddress;
	}

	/**
	 * @param bankAddress
	 *            the bankAddress to set
	 */
	public void setBankAddress(String bankAddress) {
		this.bankAddress = bankAddress;
	}

	/**
	 * @return the bankBranch
	 */
	public String getBankBranch() {
		return this.bankBranch;
	}

	/**
	 * @param bankBranch
	 *            the bankBranch to set
	 */
	public void setBankBranch(String bankBranch) {
		this.bankBranch = bankBranch;
	}

	/**
	 * @return the bankBranchCode
	 */
	public String getBankBranchCode() {
		return this.bankBranchCode;
	}

	/**
	 * @param bankBranchCode
	 *            the bankBranchCode to set
	 */
	public void setBankBranchCode(String bankBranchCode) {
		this.bankBranchCode = bankBranchCode;
	}

	/**
	 * @return the bankCode
	 */
	public String getBankCode() {
		return this.bankCode;
	}

	/**
	 * @param bankCode
	 *            the bankCode to set
	 */
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	/**
	 * @return the bankLocation
	 */
	public String getBankLocation() {
		return this.bankLocation;
	}

	/**
	 * @param bankLocation
	 *            the bankLocation to set
	 */
	public void setBankLocation(String bankLocation) {
		this.bankLocation = bankLocation;
	}

	/**
	 * @return the bankName
	 */
	public String getBankName() {
		return this.bankName;
	}

	/**
	 * @param bankName
	 *            the bankName to set
	 */
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	/**
	 * @return the ibanCode
	 */
	public String getIbanCode() {
		return this.ibanCode;
	}

	/**
	 * @param ibanCode
	 *            the ibanCode to set
	 */
	public void setIbanCode(String ibanCode) {
		this.ibanCode = ibanCode;
	}

	/**
	 * @return the swiftCode
	 */
	public String getSwiftCode() {
		return this.swiftCode;
	}

	/**
	 * @param swiftCode
	 *            the swiftCode to set
	 */
	public void setSwiftCode(String swiftCode) {
		this.swiftCode = swiftCode;
	}

	/**
	 * @return the tpinCode
	 */
	public String getTpinCode() {
		return this.tpinCode;
	}

	/**
	 * @param tpinCode
	 *            the tpinCode to set
	 */
	public void setTpinCode(String tpinCode) {
		this.tpinCode = tpinCode;
	}

	/**
	 * @return the sellerBizZip
	 */
	public String getSellerBizZip() {
		return this.sellerBizZip;
	}

	/**
	 * @param sellerBizZip
	 *            the sellerBizZip to set
	 */
	public void setSellerBizZip(String sellerBizZip) {
		this.sellerBizZip = sellerBizZip;
	}

	/**
	 * @return the sellerBizAddress
	 */
	public String getSellerBizAddress() {
		return this.sellerBizAddress;
	}

	/**
	 * @param sellerBizAddress
	 *            the sellerBizAddress to set
	 */
	public void setSellerBizAddress(String sellerBizAddress) {
		this.sellerBizAddress = sellerBizAddress;
	}

	/**
	 * @return the sellerBizDetailAddress
	 */
	public String getSellerBizDetailAddress() {
		return this.sellerBizDetailAddress;
	}

	/**
	 * @param sellerBizDetailAddress
	 *            the sellerBizDetailAddress to set
	 */
	public void setSellerBizDetailAddress(String sellerBizDetailAddress) {
		this.sellerBizDetailAddress = sellerBizDetailAddress;
	}

	/**
	 * @return the sellerBizType
	 */
	public String getSellerBizType() {
		return this.sellerBizType;
	}

	/**
	 * @param sellerBizType
	 *            the sellerBizType to set
	 */
	public void setSellerBizType(String sellerBizType) {
		this.sellerBizType = sellerBizType;
	}

	/**
	 * @return the sellerBizCategory
	 */
	public String getSellerBizCategory() {
		return this.sellerBizCategory;
	}

	/**
	 * @param sellerBizCategory
	 *            the sellerBizCategory to set
	 */
	public void setSellerBizCategory(String sellerBizCategory) {
		this.sellerBizCategory = sellerBizCategory;
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
	 * @return the sellerBizPhoneCountry
	 */
	public String getSellerBizPhoneCountry() {
		return this.sellerBizPhoneCountry;
	}

	/**
	 * @param sellerBizPhoneCountry
	 *            the sellerBizPhoneCountry to set
	 */
	public void setSellerBizPhoneCountry(String sellerBizPhoneCountry) {
		this.sellerBizPhoneCountry = sellerBizPhoneCountry;
	}

	/**
	 * @return the sellerBizPhone
	 */
	public String getSellerBizPhone() {
		return this.sellerBizPhone;
	}

	/**
	 * @param sellerBizPhone
	 *            the sellerBizPhone to set
	 */
	public void setSellerBizPhone(String sellerBizPhone) {
		this.sellerBizPhone = sellerBizPhone;
	}

	/**
	 * @return the vendorCode
	 */
	public String getVendorCode() {
		return this.vendorCode;
	}

	/**
	 * @param vendorCode
	 *            the vendorCode to set
	 */
	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
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

	/**
	 * @return the isBizTaxable
	 */
	public String getIsBizTaxable() {
		return this.isBizTaxable;
	}

	/**
	 * @param isBizTaxable
	 *            the isBizTaxable to set
	 */
	public void setIsBizTaxable(String isBizTaxable) {
		this.isBizTaxable = isBizTaxable;
	}

	/**
	 * @return the bizGrade
	 */
	public String getBizGrade() {
		return this.bizGrade;
	}

	/**
	 * @param bizGrade
	 *            the bizGrade to set
	 */
	public void setBizGrade(String bizGrade) {
		this.bizGrade = bizGrade;
	}

	/**
	 * @return the isDeductible
	 */
	public String getIsDeductible() {
		return this.isDeductible;
	}

	/**
	 * @param isDeductible
	 *            the isDeductible to set
	 */
	public void setIsDeductible(String isDeductible) {
		this.isDeductible = isDeductible;
	}

	/**
	 * @return the marketCode
	 */
	public String getMarketCode() {
		return this.marketCode;
	}

	/**
	 * @param marketCode
	 *            the marketCode to set
	 */
	public void setMarketCode(String marketCode) {
		this.marketCode = marketCode;
	}

	/**
	 * @return the marketStatus
	 */
	public String getMarketStatus() {
		return this.marketStatus;
	}

	/**
	 * @param marketStatus
	 *            the marketStatus to set
	 */
	public void setMarketStatus(String marketStatus) {
		this.marketStatus = marketStatus;
	}

	/**
	 * @return the isAccountReal
	 */
	public String getIsAccountReal() {
		return this.isAccountReal;
	}

	/**
	 * @param isAccountReal
	 *            the isAccountReal to set
	 */
	public void setIsAccountReal(String isAccountReal) {
		this.isAccountReal = isAccountReal;
	}

	/**
	 * @return the isOfficialAuth
	 */
	public String getIsOfficialAuth() {
		return this.isOfficialAuth;
	}

	/**
	 * @param isOfficialAuth
	 *            the isOfficialAuth to set
	 */
	public void setIsOfficialAuth(String isOfficialAuth) {
		this.isOfficialAuth = isOfficialAuth;
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
	 * @return the bankBsb
	 */
	public String getBankBsb() {
		return this.bankBsb;
	}

	/**
	 * @param bankBsb
	 *            the bankBsb to set
	 */
	public void setBankBsb(String bankBsb) {
		this.bankBsb = bankBsb;
	}

	/**
	 * @return the ceoBirthDay
	 */
	public String getCeoBirthDay() {
		return this.ceoBirthDay;
	}

	/**
	 * @param ceoBirthDay
	 *            the ceoBirthDay to set
	 */
	public void setCeoBirthDay(String ceoBirthDay) {
		this.ceoBirthDay = ceoBirthDay;
	}

	/**
	 * @return the ceoName
	 */
	public String getCeoName() {
		return this.ceoName;
	}

	/**
	 * @param ceoName
	 *            the ceoName to set
	 */
	public void setCeoName(String ceoName) {
		this.ceoName = ceoName;
	}
}
