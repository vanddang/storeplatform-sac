package com.skplanet.storeplatform.sac.client.member.vo.seller;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.member.vo.seller.ModifyAccountInformationSacReq.ExtraDocument;

/**
 * 2.2.15. 판매자 회원 전환 신청 [REQUEST]
 * 
 * Updated on : 2014. 2. 10. Updated by : 김경복, 부르칸
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class ConversionClassResSacReq extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/** 인증 키. */
	private String sessionKey;
	/** 판매자 key. */
	private String sellerKey;
	/** 현재 구분코드. */
	private String sellerClass;
	/** 판매자 분류코드. */
	private String sellerCategory;
	/** 신청 구분코드. */
	private String sellerClassTo;
	/** 판매자 분류코드. */
	private String sellerCategoryTo;
	/** 판매자 main 상태코드. */
	private String sellerMainStatus;
	/** 판매자 sub 상태코드. */
	private String sellerSubStatus;
	/** 판매자 ID. */
	private String sellerId;
	/** 종목. */
	private String sellerBizType;
	/** 업태. */
	private String sellerBizCategory;
	/** 법인등록번호. */
	private String sellerBizCorpNumber;
	/** 대표전화번호 국가코드. */
	private String repPhoneArea;
	/** 대표전화번호. */
	private String repPhone;
	/** 대표팩스번호 국가코드. */
	private String repFaxArea;
	/** 대표팩스번호. */
	private String repFax;
	/** 대표 이메일. */
	private String repEmail;
	/** 사업장 우편번호. */
	private String sellerZip;
	/** 사업장 주소. */
	private String sellerAddress;
	/** 사업장 상세주소. */
	private String sellerDetailAddress;
	/** BP 정산률. */
	private String bp_rate;
	/** 벤더 코드. */
	private String vendor_code;
	/** 통신판매업 신고여부. */
	private String isBizRegistered;
	/** 통신판매업 신고번호. */
	private String bizRegNumber;
	/** 통신판매업 미신고사유. */
	private String bizUnregReason;
	/** 간이과세여부. */
	private String isBizTaxable;
	/** 심의등급코드. */
	private String bizGrade;
	/** 자동차감가능대상여부. */
	private String isDeductible;
	/** 입점상점코드. */
	private String marketCode;
	/** 입점상태코드. */
	private String marketStatus;
	/** 은행코드. */
	private String bankCode;
	/** 은행명. */
	private String bankName;
	/** 계좌인증여부. */
	private String isAccountReal;
	/** 계좌인증일시. */
	private String accountRealDate;
	/** 은행지점명. */
	private String bankBranch;
	/** 은행지점코드. */
	private String bankBranchCode;
	/** Swift 코드. */
	private String swiftCode;
	/** ABA 코드. */
	private String abaCode;
	/** IBAN 코드. */
	private String ibanCode;
	/** TPIN. */
	private String tpinCode;
	/** 계좌번호. */
	private String bankAccount;
	/** 예금자명. */
	private String bankAcctName;
	/** 서류 리스트. */
	private List<ExtraDocument> extraDocumentList;

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

	public String getSellerClassTo() {
		return this.sellerClassTo;
	}

	public void setSellerClassTo(String sellerClassTo) {
		this.sellerClassTo = sellerClassTo;
	}

	public String getSellerCategoryTo() {
		return this.sellerCategoryTo;
	}

	public void setSellerCategoryTo(String sellerCategoryTo) {
		this.sellerCategoryTo = sellerCategoryTo;
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

	public String getSellerBizType() {
		return this.sellerBizType;
	}

	public void setSellerBizType(String sellerBizType) {
		this.sellerBizType = sellerBizType;
	}

	public String getSellerBizCategory() {
		return this.sellerBizCategory;
	}

	public void setSellerBizCategory(String sellerBizCategory) {
		this.sellerBizCategory = sellerBizCategory;
	}

	public String getSellerBizCorpNumber() {
		return this.sellerBizCorpNumber;
	}

	public void setSellerBizCorpNumber(String sellerBizCorpNumber) {
		this.sellerBizCorpNumber = sellerBizCorpNumber;
	}

	public String getRepPhoneArea() {
		return this.repPhoneArea;
	}

	public void setRepPhoneArea(String repPhoneArea) {
		this.repPhoneArea = repPhoneArea;
	}

	public String getRepPhone() {
		return this.repPhone;
	}

	public void setRepPhone(String repPhone) {
		this.repPhone = repPhone;
	}

	public String getRepFaxArea() {
		return this.repFaxArea;
	}

	public void setRepFaxArea(String repFaxArea) {
		this.repFaxArea = repFaxArea;
	}

	public String getRepFax() {
		return this.repFax;
	}

	public void setRepFax(String repFax) {
		this.repFax = repFax;
	}

	public String getRepEmail() {
		return this.repEmail;
	}

	public void setRepEmail(String repEmail) {
		this.repEmail = repEmail;
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

	public String getBp_rate() {
		return this.bp_rate;
	}

	public void setBp_rate(String bp_rate) {
		this.bp_rate = bp_rate;
	}

	public String getVendor_code() {
		return this.vendor_code;
	}

	public void setVendor_code(String vendor_code) {
		this.vendor_code = vendor_code;
	}

	public String getIsBizRegistered() {
		return this.isBizRegistered;
	}

	public void setIsBizRegistered(String isBizRegistered) {
		this.isBizRegistered = isBizRegistered;
	}

	public String getBizRegNumber() {
		return this.bizRegNumber;
	}

	public void setBizRegNumber(String bizRegNumber) {
		this.bizRegNumber = bizRegNumber;
	}

	public String getBizUnregReason() {
		return this.bizUnregReason;
	}

	public void setBizUnregReason(String bizUnregReason) {
		this.bizUnregReason = bizUnregReason;
	}

	public String getIsBizTaxable() {
		return this.isBizTaxable;
	}

	public void setIsBizTaxable(String isBizTaxable) {
		this.isBizTaxable = isBizTaxable;
	}

	public String getBizGrade() {
		return this.bizGrade;
	}

	public void setBizGrade(String bizGrade) {
		this.bizGrade = bizGrade;
	}

	public String getIsDeductible() {
		return this.isDeductible;
	}

	public void setIsDeductible(String isDeductible) {
		this.isDeductible = isDeductible;
	}

	public String getMarketCode() {
		return this.marketCode;
	}

	public void setMarketCode(String marketCode) {
		this.marketCode = marketCode;
	}

	public String getMarketStatus() {
		return this.marketStatus;
	}

	public void setMarketStatus(String marketStatus) {
		this.marketStatus = marketStatus;
	}

	public String getBankCode() {
		return this.bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getBankName() {
		return this.bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getIsAccountReal() {
		return this.isAccountReal;
	}

	public void setIsAccountReal(String isAccountReal) {
		this.isAccountReal = isAccountReal;
	}

	public String getAccountRealDate() {
		return this.accountRealDate;
	}

	public void setAccountRealDate(String accountRealDate) {
		this.accountRealDate = accountRealDate;
	}

	public String getBankBranch() {
		return this.bankBranch;
	}

	public void setBankBranch(String bankBranch) {
		this.bankBranch = bankBranch;
	}

	public String getBankBranchCode() {
		return this.bankBranchCode;
	}

	public void setBankBranchCode(String bankBranchCode) {
		this.bankBranchCode = bankBranchCode;
	}

	public String getSwiftCode() {
		return this.swiftCode;
	}

	public void setSwiftCode(String swiftCode) {
		this.swiftCode = swiftCode;
	}

	public String getAbaCode() {
		return this.abaCode;
	}

	public void setAbaCode(String abaCode) {
		this.abaCode = abaCode;
	}

	public String getIbanCode() {
		return this.ibanCode;
	}

	public void setIbanCode(String ibanCode) {
		this.ibanCode = ibanCode;
	}

	public String getTpinCode() {
		return this.tpinCode;
	}

	public void setTpinCode(String tpinCode) {
		this.tpinCode = tpinCode;
	}

	public String getBankAccount() {
		return this.bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public String getBankAcctName() {
		return this.bankAcctName;
	}

	public void setBankAcctName(String bankAcctName) {
		this.bankAcctName = bankAcctName;
	}

	public List<ExtraDocument> getExtraDocumentList() {
		return this.extraDocumentList;
	}

	public void setExtraDocumentList(List<ExtraDocument> extraDocumentList) {
		this.extraDocumentList = extraDocumentList;
	}
}
