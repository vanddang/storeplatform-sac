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

	/** 업종명. */
	private String sellerBizType; // INDT_NM 업종명 종목 종목

	/** 업태명. */
	private String sellerBizCategory; // COND_NM 업태명 업태 업태

	/** 법인등록번호. */
	private String sellerBizCorpNumber; // CORP_REG_NO 법인등록번호

	/** 대표전화 국가번호. */
	private String repPhoneArea; // REP_TEL_NATION_NO 대표전화 국가 번호

	/** 대표전화번호. */
	private String repPhone; // REP_TEL_NO 대표전화번호

	/** 대표팩스 국가번호. */
	private String repFaxArea; // FAX_NATION_NO 대표팩스번호 국가코드

	/** 대표팩스번호. */
	private String repFax; // FAX_NO 대표팩스번호

	/** 대표 Email. */
	private String repEmail; // REP_EMAIL 대표이메일

	/** 우편번호. */
	private String sellerZip; // ZIP 우편번호

	/** 주소. */
	private String sellerAddress; // ADDR 주소

	/** 상세주소. */
	private String sellerDetailAddress; // DTL_ADDR 상세주소

	/** 벤더코드. */
	private String vendorCode; // // VENDOR_CD 벤더코드

	/** 통신판매업 신고여부. */
	private String isBizRegistered; // MSALBIZ_DECL_YN 통신판매업 신고 여부

	/** 통신판매업 신고번호. */
	private String bizRegNumber; // MSALBIZ_DECL_NO 통신판매업 신고 번호

	/** 통신판매업 미신고 사유코드. */
	private String bizUnregReason; // MSALBIZ_UNDECL_REASON_CD 통신판매업 미신고 사유 코드

	/** 간이 과세여부. */
	private String isBizTaxable; // EASY_TXN_YN 간이 과세 여부 ##### 전환 쪽에서 사용

	/** 심의 등급코드. */
	private String bizGrade; // DELIB_GRD_CD 심의 등급코드 ##### 전환 쪽에서 사용

	/** 자동 차감 가능 대상여부. */
	private String isDeductible; // AUTO_DED_POSB_TARGET_YN 자동 차감 가능 대상 여부 ##### 전환 쪽에서 사용

	/** 입점 상점코드. */
	private String marketCode; // LNCHG_MALL_CD 입점 상점코드 ##### 전환 쪽에서 사용

	/** 입점 회원 상태코드. */
	private String marketStatus; // LNCHG_MBR_STATUS_CD 입점 회원 상태코드 ##### 전환 쪽에서 사용

	/** 계좌 인증여부. */
	private String isAccountReal; // ACCT_AUTH_YN 계좌 인증여부 컬럼

	/** 계좌인증일시. ex (YYYYMMDDHH24MISS) ("20130122162853") 2013-01-22 16:28:53. */
	private String accountRealDate; // ACCT_AUTH_DT 계좌인증일시

	/** 계좌번호. */
	private String bankAccount; // ACCT_NO 계좌번호

	/** 예금자명. */
	private String bankAcctName; // DEPSTR_NM 예금자명

	/** 은행코드. */
	private String bankCode; // BANK_CD 은행코드

	/** 은행명. */
	private String bankName; // FR_BANK_NM 은행명

	/** 은행지점코드. */
	private String bankBranchCode; // FR_BRCH_CD 은행지점코드

	/** 은행지점명. */
	private String bankBranch; // FR_BRCH_NM 은행지점명

	/** 은행주소. */
	private String bankAddress; // FR_BANK_ADDR 외국은행주소

	/** 은행 위치. */
	private String bankLocation; // FR_BANK_LOC 외국은행 위치

	/** Swift 코드. */
	private String swiftCode; // INTL_SWIFT_CD Swift 코드

	/** IBAN 코드. */
	private String ibanCode; // INTL_IBAN IBAN 코드

	/** ABA 코드. */
	private String abaCode; // INTL_ABA ABA 코드

	/** TPIN 코드. */
	private String tpinCode; // FR_TPIN_NO => FR_TIN_NO 로 바뀌었음
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
