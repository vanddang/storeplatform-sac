package com.skplanet.storeplatform.sac.client.member.vo.seller;

import java.util.List;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 2.2.11. 판매자 회원 정산 정보 수정 [REQUEST]
 * 
 * Updated on : 2014. 1. 23. Updated by : 김경복, 부르칸.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class ModifyAccountInformationSacReq extends CommonInfo {

	private static final long serialVersionUID = -4035947141521875296L;

	/** 인증키. */
	@NotBlank
	private String sessionKey;
	/** 판매자 key. */
	@NotBlank
	private String sellerKey;
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
	private String sellerId;
	/** 법인등록번호. */
	private String sellerBizCorpNumber;
	/** 종목. */
	private String sellerBizType;
	/** 업태. */
	private String sellerBizCategory;
	/** 대표전화번호 국가코드. */
	private String repPhoneArea;
	/** 대표전화번호. */
	private String repPhone;
	/** 대표팩스번호 국가코드. */
	private String repFaxArea;
	/** 대표팩스번호. */
	private String repFax;
	/** 대표 이메일. */
	@Pattern(regexp = "^(?:\\w+\\.?)*\\w+@(?:\\w+\\.)+\\w+$")
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
	@NotBlank
	@Pattern(regexp = "^Y|^N")
	private String isBizRegistered;
	/** 통신판매업 신고번호. */
	private String bizRegNumber;
	/** 통신판매업 미신고사유. */
	private String bizUnregReason;
	/** 간이과세여부. */
	@NotBlank
	@Pattern(regexp = "^Y|^N")
	private String isBizTaxable;
	/** 심의등급코드. */
	private String bizGrade;
	/** 자동차감가능대상여부. */
	@NotBlank
	@Pattern(regexp = "^Y|^N")
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
	@NotBlank
	@Pattern(regexp = "^Y|^N")
	private String isAccountReal;
	/** 계좌인증일시. */
	@Size(max = 14)
	private String accountRealDate;
	/** 은행지점명. */
	private String bankBranch;
	/** 은행지점코드. */
	private String bankBranchCode;
	/** Swift 코드. */
	private String swift_code;
	/** ABA 코드. */
	private String aba_code;
	/** IBAN 코드. */
	private String iban_code;
	/** TPIN. */
	private String tpin_code;
	/** 계좌번호. */
	private String bankAccount;
	/** 예금자명. */
	private String bankAcctName;
	/** 서류 관련 리스트. */
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

	public String getSellerBizCorpNumber() {
		return this.sellerBizCorpNumber;
	}

	public void setSellerBizCorpNumber(String sellerBizCorpNumber) {
		this.sellerBizCorpNumber = sellerBizCorpNumber;
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

	public String getSwift_code() {
		return this.swift_code;
	}

	public void setSwift_code(String swift_code) {
		this.swift_code = swift_code;
	}

	public String getAba_code() {
		return this.aba_code;
	}

	public void setAba_code(String aba_code) {
		this.aba_code = aba_code;
	}

	public String getIban_code() {
		return this.iban_code;
	}

	public void setIban_code(String iban_code) {
		this.iban_code = iban_code;
	}

	public String getTpin_code() {
		return this.tpin_code;
	}

	public void setTpin_code(String tpin_code) {
		this.tpin_code = tpin_code;
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

	/**
	 * 서류관련 객체
	 * 
	 * Updated on : 2014. 1. 23. Updated by : 김경복, 부르칸.
	 */
	public static class ExtraDocument {

		/** 서류코드. */
		public String documentCode;
		/** 서류경로. */
		public String documentPath;
		/** 서류명. */
		public String documentName;
		/** 서류사이즈. */
		public String documentSize;

		public String getDocumentCode() {
			return this.documentCode;
		}

		public void setDocumentCode(String documentCode) {
			this.documentCode = documentCode;
		}

		public String getDocumentPath() {
			return this.documentPath;
		}

		public void setDocumentPath(String documentPath) {
			this.documentPath = documentPath;
		}

		public String getDocumentName() {
			return this.documentName;
		}

		public void setDocumentName(String documentName) {
			this.documentName = documentName;
		}

		public String getDocumentSize() {
			return this.documentSize;
		}

		public void setDocumentSize(String documentSize) {
			this.documentSize = documentSize;
		}
	}
}
