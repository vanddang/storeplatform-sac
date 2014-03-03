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

	/** 공인인증여부. */
	private String isOfficialAuth;

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

	/** CEO명. */
	private String ceoName; // CEO_NM

	/** CEO 생년월일. ex(YYYYMMDD) */
	private String ceoBirthDay; // CEO_BIRTH

	/** 서류 관련 리스트. */
	private List<ExtraDocument> extraDocumentList;

	/**
	 * @return the sessionKey
	 */
	public String getSessionKey() {
		return this.sessionKey;
	}

	/**
	 * @param sessionKey
	 *            the sessionKey to set
	 */
	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
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
	 * @return the extraDocumentList
	 */
	public List<ExtraDocument> getExtraDocumentList() {
		return this.extraDocumentList;
	}

	/**
	 * @param extraDocumentList
	 *            the extraDocumentList to set
	 */
	public void setExtraDocumentList(List<ExtraDocument> extraDocumentList) {
		this.extraDocumentList = extraDocumentList;
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

		/**
		 * @return the documentCode
		 */
		public String getDocumentCode() {
			return this.documentCode;
		}

		/**
		 * @param documentCode
		 *            the documentCode to set
		 */
		public void setDocumentCode(String documentCode) {
			this.documentCode = documentCode;
		}

		/**
		 * @return the documentPath
		 */
		public String getDocumentPath() {
			return this.documentPath;
		}

		/**
		 * @param documentPath
		 *            the documentPath to set
		 */
		public void setDocumentPath(String documentPath) {
			this.documentPath = documentPath;
		}

		/**
		 * @return the documentName
		 */
		public String getDocumentName() {
			return this.documentName;
		}

		/**
		 * @param documentName
		 *            the documentName to set
		 */
		public void setDocumentName(String documentName) {
			this.documentName = documentName;
		}

		/**
		 * @return the documentSize
		 */
		public String getDocumentSize() {
			return this.documentSize;
		}

		/**
		 * @param documentSize
		 *            the documentSize to set
		 */
		public void setDocumentSize(String documentSize) {
			this.documentSize = documentSize;
		}

		/**
		 * @return the isUsed
		 */
		public String getIsUsed() {
			return this.isUsed;
		}

		/**
		 * @param isUsed
		 *            the isUsed to set
		 */
		public void setIsUsed(String isUsed) {
			this.isUsed = isUsed;
		}

	}
}
