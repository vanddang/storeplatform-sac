package com.skplanet.storeplatform.sac.client.member.vo.seller;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 2.2.15. 판매자 회원 전환 신청 [REQUEST]
 * 
 * Updated on : 2014. 2. 10. Updated by : 김경복, 부르칸
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class ConversionClassSacReq extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/** 인증 키. */
	@NotBlank
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
	/** 법인등록번호. */
	private String sellerBizCorpNumber; // CORP_REG_NO 법인등록번호// 추가된것 같음
	/** CEO명. */
	private String ceoName; // CEO_NM
	/** 담당자명. */
	private String charger; // CHRGPERS_NM 담당자 명 >> api 에 포함되지 않아도 되는부분이다. member 에서 가져와도 된다.
	/** 대표 Email. */
	private String repEmail; // CHRGPERS_EMAIL >> member 테이블에 는 없네? member 테이블네 넣을때는 sellerEmail EMAIL 넣으면 될듯
	/** 유선 전화번호. */
	private String cordedTelephone; // CHRGPERS_WIRE_TEL_NO >> member 테이블에 는 없네? member 테이블네 넣을때는 WIRE_TEL_NO 넣으면 될듯
	/** 무선 전화번호. */
	private String sellerPhone; // CHRGPERS_WILS_TEL_NO 담당자 무선전화번호 >> member 테이블에 는 없네? member 테이블네 넣을때는 WILS_TEL_NO 넣으면
	/** SMS 수신여부. */
	private String isRecvSMS; // SMS_RECV_YN 넣으면 될듯
	/** 공인인증여부. */
	private String isOfficialAuth; // PUB_AUTH_YN PUB_AUTH_YN 공인인증여부 >>판매자 테이블에는 매치되는 컬럼 없음
	/** 계좌번호. (필수). */
	@NotBlank
	private String bankAccount; // ACCT_NO 계좌번호
	/** 예금자명. */
	private String bankAcctName; // DEPSTR_NM 예금자명
	/** 은행코드. */
	private String bankCode; // BANK_CD 은행코드
	/** 계좌인증여부. */
	private String isAccountReal; // ACCT_AUTH_YN 계좌인증여부
	/** 회사명. */
	private String sellerCompany; // COMP_NM 회사명 >> api 추가 하지말고 판매자 테이블에서 가져온다.
	/** 사업자 등록번호. */
	private String sellerBizNumber; // BIZ_REG_NO 사업자등록번호 >> api 추가 하지말고 판매자 테이블에서 가져온다.
	/** 업태명. */
	private String sellerBizCategory; // COND_NM 업태명
	/** 업종명. */
	private String sellerBizType; // INDT_NM(서비스,이동통신,영상제작,시스템유지보수) 업종명
	/** 대표전화 국가번호. */
	private String repPhoneArea; // REP_TEL_NATION_NO 대표전화 국가 번호
	/** 대표전화번호. */
	private String repPhone; // REP_TEL_NO 대표전화번호
	/** 대표팩스 국가번호. */
	private String repFaxArea; // FAX_NATION_NO 대표팩스번호 국가코드 => 변경됨 FAX_TEL_NATION_NO
	/** 대표팩스번호. */
	private String repFax; // FAX_NO 대표팩스번호
	/** 우편번호. */
	private String sellerZip; // ZIP 우편번호 => ENPRPL_ZIP 바뀌었나?
	/** 주소. */
	private String sellerAddress; // ADDR 주소 => ENPRPL_ADDR 바뀌었나?
	/** 상세주소. */
	private String sellerDetailAddress; // DTL_ADDR 상세주소 => ENPRPL_ADDR_DTL 바뀌었나?
	/** 통신판매업 신고여부. */
	private String isBizRegistered; // MSALBIZ_DECL_YN 통신판매업 신고 여부 => CMNT_SALBIZ_DECL_YN 바뀌었나?
	/** 통신판매업 신고번호. */
	private String bizRegNumber; // MSALBIZ_DECL_NO 통신판매업 신고 번호 => CMNT_SALBIZ_DECL_NO 바뀌었나?
	/** 통신판매업 미신고 사유코드. */
	private String bizUnregReason; // MSALBIZ_UNDECL_REASON_CD 통신판매업 미신고 사유 코드 =>CMNT_SALBIZ_UNDECL_REASON_CD 바뀌었나?
	/** 통신사 코드. */
	private String sellerTelecom; // MNO_CD 통신사 코드 >> api 추가 하지말고 판매자 테이블에서 가져온다.
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
	private String tpinCode; // FR_TPIN_NO
	/** CEO 생년월일. */
	private String ceoBirthDay; // CEO_BIRTH
	/** 언어 코드. */
	private String sellerLanguage; // LANG_CD
	/** 벤더코드. */
	private String vendorCode; // // VENDOR_CD 벤더코드
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
	/** 계좌인증일시. */
	private String accountRealDate; // ACCT_AUTH_DT 계좌인증일시

	/** 서류 리스트. */
	private List<ExtraDocument> extraDocumentList;

	public List<ExtraDocument> getExtraDocumentList() {
		return this.extraDocumentList;
	}

	public void setExtraDocumentList(List<ExtraDocument> extraDocumentList) {
		this.extraDocumentList = extraDocumentList;
	}

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

	public String getSellerBizCorpNumber() {
		return this.sellerBizCorpNumber;
	}

	public void setSellerBizCorpNumber(String sellerBizCorpNumber) {
		this.sellerBizCorpNumber = sellerBizCorpNumber;
	}

	public String getCeoName() {
		return this.ceoName;
	}

	public void setCeoName(String ceoName) {
		this.ceoName = ceoName;
	}

	public String getCharger() {
		return this.charger;
	}

	public void setCharger(String charger) {
		this.charger = charger;
	}

	public String getRepEmail() {
		return this.repEmail;
	}

	public void setRepEmail(String repEmail) {
		this.repEmail = repEmail;
	}

	public String getCordedTelephone() {
		return this.cordedTelephone;
	}

	public void setCordedTelephone(String cordedTelephone) {
		this.cordedTelephone = cordedTelephone;
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

	public String getIsOfficialAuth() {
		return this.isOfficialAuth;
	}

	public void setIsOfficialAuth(String isOfficialAuth) {
		this.isOfficialAuth = isOfficialAuth;
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

	public String getIsAccountReal() {
		return this.isAccountReal;
	}

	public void setIsAccountReal(String isAccountReal) {
		this.isAccountReal = isAccountReal;
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

	public String getSellerBizCategory() {
		return this.sellerBizCategory;
	}

	public void setSellerBizCategory(String sellerBizCategory) {
		this.sellerBizCategory = sellerBizCategory;
	}

	public String getSellerBizType() {
		return this.sellerBizType;
	}

	public void setSellerBizType(String sellerBizType) {
		this.sellerBizType = sellerBizType;
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

	public String getSellerTelecom() {
		return this.sellerTelecom;
	}

	public void setSellerTelecom(String sellerTelecom) {
		this.sellerTelecom = sellerTelecom;
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

	public String getCeoBirthDay() {
		return this.ceoBirthDay;
	}

	public void setCeoBirthDay(String ceoBirthDay) {
		this.ceoBirthDay = ceoBirthDay;
	}

	public String getSellerLanguage() {
		return this.sellerLanguage;
	}

	public void setSellerLanguage(String sellerLanguage) {
		this.sellerLanguage = sellerLanguage;
	}

	public String getVendorCode() {
		return this.vendorCode;
	}

	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
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

	public String getAccountRealDate() {
		return this.accountRealDate;
	}

	public void setAccountRealDate(String accountRealDate) {
		this.accountRealDate = accountRealDate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
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
