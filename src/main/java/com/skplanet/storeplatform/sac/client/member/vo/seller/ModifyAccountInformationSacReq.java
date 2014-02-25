package com.skplanet.storeplatform.sac.client.member.vo.seller;

import java.util.List;

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

	/** 업종명. */
	private String sellerBizType;

	/** 업태명. */
	private String sellerBizCategory;

	/** 법인등록번호. */
	private String sellerBizCorpNumber;

	/** 대표전화 국가번호. */
	private String repPhoneArea;

	/** 대표전화번호. */
	private String repPhone;

	/** 대표팩스 국가번호. */
	private String repFaxArea;

	/** 대표팩스번호. */
	private String repFax;

	/** 대표 Email. */
	private String repEmail;

	/** 우편번호. */
	private String sellerBizZip;

	/** 주소. */
	private String sellerBizAddress;

	/** 상세주소. */
	private String sellerBizDetailAddress;

	/** 벤더코드. */
	private String vendorCode;

	/** 통신판매업 신고여부. */
	private String isBizRegistered;

	/** 통신판매업 신고번호. */
	private String bizRegNumber;

	/** 통신판매업 미신고 사유코드. */
	private String bizUnregReason;

	/** 간이 과세여부. */
	private String isBizTaxable;

	/** 심의 등급코드. */
	private String bizGrade;

	/** 자동 차감 가능 대상여부. */
	private String isDeductible;

	/** 입점 상점코드. */
	private String marketCode;

	/** 입점 회원 상태코드. */
	private String marketStatus;

	/** 계좌 인증여부. */
	private String isAccountReal;

	/** 계좌인증일시. ex (YYYYMMDDHH24MISS) ("20130122162853") 2013-01-22 16:28:53. */
	private String accountRealDate;

	/** 계좌번호. */
	@NotBlank
	private String bankAccount;

	/** 예금자명. */
	private String bankAcctName;

	/** 은행코드. */
	private String bankCode;

	/** 은행명. */
	private String bankName;

	/** 은행지점코드. */
	private String bankBranchCode;

	/** 은행지점명. */
	private String bankBranch;

	/** 은행주소. */
	private String bankAddress;

	/** 은행 위치. */
	private String bankLocation;

	/** Swift 코드. */
	private String swiftCode;

	/** IBAN 코드. */
	private String ibanCode;

	/** ABA 코드. */
	private String abaCode;

	/** TPIN 코드. */
	private String tpinCode;

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

	public String getSellerBizZip() {
		return this.sellerBizZip;
	}

	public void setSellerBizZip(String sellerBizZip) {
		this.sellerBizZip = sellerBizZip;
	}

	public String getSellerBizAddress() {
		return this.sellerBizAddress;
	}

	public void setSellerBizAddress(String sellerBizAddress) {
		this.sellerBizAddress = sellerBizAddress;
	}

	public String getSellerBizDetailAddress() {
		return this.sellerBizDetailAddress;
	}

	public void setSellerBizDetailAddress(String sellerBizDetailAddress) {
		this.sellerBizDetailAddress = sellerBizDetailAddress;
	}

	public String getVendorCode() {
		return this.vendorCode;
	}

	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
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

	public String getBankBranchCode() {
		return this.bankBranchCode;
	}

	public void setBankBranchCode(String bankBranchCode) {
		this.bankBranchCode = bankBranchCode;
	}

	public String getBankBranch() {
		return this.bankBranch;
	}

	public void setBankBranch(String bankBranch) {
		this.bankBranch = bankBranch;
	}

	public String getBankAddress() {
		return this.bankAddress;
	}

	public void setBankAddress(String bankAddress) {
		this.bankAddress = bankAddress;
	}

	public String getBankLocation() {
		return this.bankLocation;
	}

	public void setBankLocation(String bankLocation) {
		this.bankLocation = bankLocation;
	}

	public String getSwiftCode() {
		return this.swiftCode;
	}

	public void setSwiftCode(String swiftCode) {
		this.swiftCode = swiftCode;
	}

	public String getIbanCode() {
		return this.ibanCode;
	}

	public void setIbanCode(String ibanCode) {
		this.ibanCode = ibanCode;
	}

	public String getAbaCode() {
		return this.abaCode;
	}

	public void setAbaCode(String abaCode) {
		this.abaCode = abaCode;
	}

	public String getTpinCode() {
		return this.tpinCode;
	}

	public void setTpinCode(String tpinCode) {
		this.tpinCode = tpinCode;
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
		/** 사용유무. */
		public String isUsed;

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

		public String getIsUsed() {
			return this.isUsed;
		}

		public void setIsUsed(String isUsed) {
			this.isUsed = isUsed;
		}

	}
}
