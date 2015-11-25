/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 * 
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
/**
 * 판매자 전환신청 Value Object
 * 
 * Updated on : 2014. 01. 06. Updated by : wisestone_mikepark
 */
package com.skplanet.storeplatform.member.client.seller.sci.vo;

import java.io.Serializable;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.member.client.common.util.Utils;

// TODO: Auto-generated Javadoc
/**
 * 판매자 전환신청정보 Value Object.
 * 
 * Updated on : 2013. 12. 20. Updated by : wisestone_mikepark
 */
public class SellerUpgrade extends CommonInfo implements Serializable {

	/** The Constant serial Version UID. */
	// TB_US_SELLERMBR_CHANGED_APLC
	private static final long serialVersionUID = 1L;

	/** 과세여부. */
	private String isTaxable; // VAT_YN 과세 여부 #####

	/**
	 * 과세여부를 리턴한다.
	 * 
	 * @return isTaxable - 과세여부
	 */
	public String getIsTaxable() {
		return this.isTaxable;
	}

	/**
	 * 과세여부를 설정한다.
	 * 
	 * @param isTaxable
	 *            과세여부
	 */
	public void setIsTaxable(String isTaxable) {
		this.isTaxable = isTaxable;
	}

	/** 법인등록번호. */
	private String sellerBizCorpNumber; // CORP_REG_NO 법인등록번호// 추가된것 같음

	/** CEO명. */
	private String ceoName; // CEO_NM

	/** 판매자 키. */
	private String sellerKey; // INSD_SELLERMBR_NO 내부 사용자코드

	/** 담당자명. */
	private String charger; // CHRGPERS_NM 담당자 명 >> api 에 포함되지 않아도 되는부분이다. member 에서 가져와도 된다.

	/** 대표 Email. */
	private String repEmail; // CHRGPERS_EMAIL >> member 테이블에 는 없네? member 테이블네 넣을때는 sellerEmail EMAIL 넣으면 될듯

	/** 대표 웹사이트. */
	private String repWebsite;

	/** 담당자 유선 전화번호. */
	private String cordedTelephone; // CHRGPERS_WIRE_TEL_NO >> member 테이블에 는 없네? member 테이블네 넣을때는 WIRE_TEL_NO 넣으면 될듯

	/** 담당자 무선 전화번호. */
	private String sellerPhone; // chargerPhone > sellerPhone 바뀜 CHRGPERS_WILS_TEL_NO > WILS_TEL_NO (admin 승인)

	/** SMS 수신여부. */
	private String isRecvSMS; // SMS_RECV_YN 넣으면 될듯

	/** 공인인증여부. */
	private String isOfficialAuth; // PUB_AUTH_YN PUB_AUTH_YN 공인인증여부 >>판매자 테이블에는 매치되는 컬럼 없음

	/** 계좌번호. (필수) */
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

	/** 신청 구분코드. 필수 US010101(개인) ,US010102(개인사업자) ,US010103(법인사업자) */
	private String sellerClassTo; // BIZ_KIND_CD(US000901,US000904) 신청 구분코드(개인/사업자/법인사업자)

	/** 대표전화 국가번호. */
	private String repPhoneArea; // REP_TEL_NATION_NO 대표전화 국가 번호

	/** 대표전화번호(사업장) repPhone > customerPhone 변경 . */
	private String customerPhone; // REP_TEL_NO 대표전화번호 > CUST_CORS_TEL_NO (admin 이 승인 처리)

	/** 사업장 우편번호. */
	private String sellerBizZip; // ZIP 우편번호 => ENPRPL_ZIP 바뀌었나?

	/** 사업장 주소. */
	private String sellerBizAddress; // ADDR 주소 => ENPRPL_ADDR 바뀌었나?

	/** 사업장 상세주소. */
	private String sellerBizDetailAddress; // DTL_ADDR 상세주소 => ENPRPL_ADDR_DTL 바뀌었나?

	/** 통신판매업 신고여부. */
	private String isBizRegistered; // MSALBIZ_DECL_YN 통신판매업 신고 여부 => CMNT_SALBIZ_DECL_YN 바뀌었나?

	/** 통신판매업 신고번호. */
	private String bizRegNumber; // MSALBIZ_DECL_NO 통신판매업 신고 번호 => CMNT_SALBIZ_DECL_NO 바뀌었나?

	/** 통신판매업 미신고 사유코드. */
	private String bizUnregReason; // MSALBIZ_UNDECL_REASON_CD 통신판매업 미신고 사유 코드 =>CMNT_SALBIZ_UNDECL_REASON_CD 바뀌었나?

	/** 전환 유형코드. */
	private String changedCd; // // CHANGED_CASE_CD 전환 유형코드 (US001507,US001503,US001501) >> api 에 추가한다.

	/** 유료전환일시. ex(YYYYMMDD) */
	private String chargedDate; // CHRG_CHANGED_DAY 유료전환일 << api 불필요

	/** 승인 요청일시. */
	private String approveReqDate; // APPR_REQ_DAY 승인 요청일 << 등록시 현재시간 넣는다.

	/** 승인 완료일시. */
	private String approveComptDate; // APPR_COMPT_DAY 승인완료일 <<- admin 작업 무시

	/** 승인 거부일시. ex(YYYYMMDD) */
	private String approveDenyDate; // APPR_DENY_DAY 승인 거부일 <<- admin 작업 무시

	/** 처리 상태코드. */
	private String procStatusCd; // PROC_STATUS_CD 처리 상태코드 (US001602, US001603) <<- admin 작업 무시

	/** 통신사 코드. */
	private String sellerTelecom; // MNO_CD 통신사 코드 >> api 추가 하지말고 판매자 테이블에서 가져온다.

	/** 수정일시. */
	private String updateDate; // UPD_ID 수정 id <<- admin 작업 무시

	/** 은행명. */
	private String bankName; // FR_BANK_NM 은행명

	/** 은행BSB . */
	private String bankBsb; // FR_BSB

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

	/** CEO 생년월일. ex(YYYYMMDD) */
	private String ceoBirthDay; // CEO_BIRTH

	/** 언어 코드. */
	private String sellerLanguage; // LANG_CD

	/** 사유. */
	private String reason; // REASON 사유 -> <<- admin 작업 무시

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

	/** 계좌인증일시. ex(YYYYMMDD) */
	private String accountRealDate; // ACCT_AUTH_DT 계좌인증일시

	/**
	 * 법인등록번호를 리턴한다.
	 * 
	 * @return sellerBizCorpNumber - 법인등록번호
	 */
	public String getSellerBizCorpNumber() {
		return this.sellerBizCorpNumber;
	}

	/**
	 * 법인등록번호를 설정한다.
	 * 
	 * @param sellerBizCorpNumber
	 *            법인등록번호
	 */
	public void setSellerBizCorpNumber(String sellerBizCorpNumber) {
		this.sellerBizCorpNumber = sellerBizCorpNumber;
	}

	/**
	 * CEO명을 리턴한다.
	 * 
	 * @return ceoName - CEO명
	 */
	public String getCeoName() {
		return this.ceoName;
	}

	/**
	 * CEO명을 설정한다.
	 * 
	 * @param ceoName
	 *            CEO명
	 */
	public void setCeoName(String ceoName) {
		this.ceoName = ceoName;
	}

	/**
	 * 판매자 키를 리턴한다.
	 * 
	 * @return sellerKey - 판매자 키
	 */
	public String getSellerKey() {
		return this.sellerKey;
	}

	/**
	 * 판매자 키를 설정한다.
	 * 
	 * @param sellerKey
	 *            판매자 키
	 */
	public void setSellerKey(String sellerKey) {
		this.sellerKey = sellerKey;
	}

	/**
	 * 담당자명를 리턴한다.
	 * 
	 * @return charger - 담당자명
	 */
	public String getCharger() {
		return this.charger;
	}

	/**
	 * 담당자명를 설정한다.
	 * 
	 * @param charger
	 *            담당자명
	 */
	public void setCharger(String charger) {
		this.charger = charger;
	}

	/**
	 * 대표 Email를 리턴한다.
	 * 
	 * @return repEmail - 대표 Email
	 */
	public String getRepEmail() {
		return this.repEmail;
	}

	/**
	 * 대표 Email를 설정한다.
	 * 
	 * @param repEmail
	 *            대표 Email
	 */
	public void setRepEmail(String repEmail) {
		this.repEmail = repEmail;
	}

	/**
	 * @return the repWebsite
	 */
	public String getRepWebsite() {
		return this.repWebsite;
	}

	/**
	 * @param repWebsite
	 *            the repWebsite to set
	 */
	public void setRepWebsite(String repWebsite) {
		this.repWebsite = repWebsite;
	}

	/**
	 * 유선 전화번호를 리턴한다.
	 * 
	 * @return cordedTelephone - 유선 전화번호
	 */
	public String getCordedTelephone() {
		return this.cordedTelephone;
	}

	/**
	 * 유선 전화번호를 설정한다.
	 * 
	 * @param cordedTelephone
	 *            유선 전화번호
	 */
	public void setCordedTelephone(String cordedTelephone) {
		this.cordedTelephone = cordedTelephone;
	}

	/**
	 * SMS 수신여부를 리턴한다.
	 * 
	 * @return isRecvSMS - SMS 수신여부
	 */
	public String getIsRecvSMS() {
		return this.isRecvSMS;
	}

	/**
	 * SMS 수신여부를 설정한다.
	 * 
	 * @param isRecvSMS
	 *            SMS 수신여부
	 */
	public void setIsRecvSMS(String isRecvSMS) {
		this.isRecvSMS = isRecvSMS;
	}

	/**
	 * 공인인증여부를 리턴한다.
	 * 
	 * @return isOfficialAuth - 공인인증여부
	 */
	public String getIsOfficialAuth() {
		return this.isOfficialAuth;
	}

	/**
	 * 공인인증여부를 설정한다.
	 * 
	 * @param isOfficialAuth
	 *            공인인증여부
	 */
	public void setIsOfficialAuth(String isOfficialAuth) {
		this.isOfficialAuth = isOfficialAuth;
	}

	/**
	 * 계좌번호를 리턴한다.
	 * 
	 * @return bankAccount - 계좌번호
	 */
	public String getBankAccount() {
		return this.bankAccount;
	}

	/**
	 * 계좌번호를 설정한다.
	 * 
	 * @param bankAccount
	 *            계좌번호
	 */
	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	/**
	 * 예금자명을 리턴한다.
	 * 
	 * @return bankAcctName - 예금자명
	 */
	public String getBankAcctName() {
		return this.bankAcctName;
	}

	/**
	 * 예금자명을 설정한다.
	 * 
	 * @param bankAcctName
	 *            예금자명
	 */
	public void setBankAcctName(String bankAcctName) {
		this.bankAcctName = bankAcctName;
	}

	/**
	 * 은행코드를 리턴한다.
	 * 
	 * @return bankCode - 은행코드
	 */
	public String getBankCode() {
		return this.bankCode;
	}

	/**
	 * 은행코드를 설정한다.
	 * 
	 * @param bankCode
	 *            은행코드
	 */
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	/**
	 * 계좌 인증여부를 리턴한다.
	 * 
	 * @return isAccountReal - 계좌 인증여부
	 */
	public String getIsAccountReal() {
		return this.isAccountReal;
	}

	/**
	 * 계좌 인증여부를 설정한다.
	 * 
	 * @param isAccountReal
	 *            계좌 인증여부
	 */
	public void setIsAccountReal(String isAccountReal) {
		this.isAccountReal = isAccountReal;
	}

	/**
	 * 회사명을 리턴한다.
	 * 
	 * @return sellerCompany - 회사명
	 */
	public String getSellerCompany() {
		return this.sellerCompany;
	}

	/**
	 * 회사명을 설정한다.
	 * 
	 * @param sellerCompany
	 *            회사명
	 */
	public void setSellerCompany(String sellerCompany) {
		this.sellerCompany = sellerCompany;
	}

	/**
	 * 사업자 등록번호를 리턴한다.
	 * 
	 * @return sellerBizNumber - 사업자 등록번호
	 */
	public String getSellerBizNumber() {
		return this.sellerBizNumber;
	}

	/**
	 * 사업자 등록번호를 설정한다.
	 * 
	 * @param sellerBizNumber
	 *            사업자 등록번호
	 */
	public void setSellerBizNumber(String sellerBizNumber) {
		this.sellerBizNumber = sellerBizNumber;
	}

	/**
	 * 업태명을 리턴한다.
	 * 
	 * @return sellerBizCategory - 업태명
	 */
	public String getSellerBizCategory() {
		return this.sellerBizCategory;
	}

	/**
	 * 업태명을 설정한다.
	 * 
	 * @param sellerBizCategory
	 *            업태명
	 */
	public void setSellerBizCategory(String sellerBizCategory) {
		this.sellerBizCategory = sellerBizCategory;
	}

	/**
	 * 업종명을 리턴한다.
	 * 
	 * @return sellerBizType - 업종명
	 */
	public String getSellerBizType() {
		return this.sellerBizType;
	}

	/**
	 * 업종명을 설정한다.
	 * 
	 * @param sellerBizType
	 *            업종명
	 */
	public void setSellerBizType(String sellerBizType) {
		this.sellerBizType = sellerBizType;
	}

	/**
	 * 신청 구분코드를 리턴한다.
	 * 
	 * @return sellerClassTo - 신청 구분코드
	 */
	public String getSellerClassTo() {
		return this.sellerClassTo;
	}

	/**
	 * 신청 구분코드를 설정한다.
	 * 
	 * @param sellerClassTo
	 *            신청 구분코드
	 */
	public void setSellerClassTo(String sellerClassTo) {
		this.sellerClassTo = sellerClassTo;
	}

	/**
	 * 대표전화 국가번호를 리턴한다.
	 * 
	 * @return repPhoneArea - 대표전화 국가번호
	 */
	public String getRepPhoneArea() {
		return this.repPhoneArea;
	}

	/**
	 * 대표전화 국가번호를 설정한다.
	 * 
	 * @param repPhoneArea
	 *            대표전화 국가번호
	 */
	public void setRepPhoneArea(String repPhoneArea) {
		this.repPhoneArea = repPhoneArea;
	}

	/**
	 * 대표전화번호를 리턴한다.
	 * 
	 * @return customerPhone - 대표전화번호
	 */
	public String getCustomerPhone() {
		return this.customerPhone;
	}

	/**
	 * 대표전화번호를 설정한다.
	 * 
	 * @param customerPhone
	 *            대표전화번호
	 */
	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}

	/**
	 * 통신판매업 신고여부를 리턴한다.
	 * 
	 * @return isBizRegistered - 통신판매업 신고여부
	 */
	public String getIsBizRegistered() {
		return this.isBizRegistered;
	}

	/**
	 * 통신판매업 신고여부를 설정한다.
	 * 
	 * @param isBizRegistered
	 *            통신판매업 신고여부
	 */
	public void setIsBizRegistered(String isBizRegistered) {
		this.isBizRegistered = isBizRegistered;
	}

	/**
	 * 통신판매업 신고번호를 리턴한다.
	 * 
	 * @return bizRegNumber - 통신판매업 신고번호
	 */
	public String getBizRegNumber() {
		return this.bizRegNumber;
	}

	/**
	 * 통신판매업 신고번호를 설정한다.
	 * 
	 * @param bizRegNumber
	 *            통신판매업 신고번호
	 */
	public void setBizRegNumber(String bizRegNumber) {
		this.bizRegNumber = bizRegNumber;
	}

	/**
	 * 통신판매업 미신고 사유코드를 리턴한다.
	 * 
	 * @return bizUnregReason - 통신판매업 미신고 사유코드
	 */
	public String getBizUnregReason() {
		return this.bizUnregReason;
	}

	/**
	 * 통신판매업 미신고 사유코드를 설정한다.
	 * 
	 * @param bizUnregReason
	 *            통신판매업 미신고 사유코드
	 */
	public void setBizUnregReason(String bizUnregReason) {
		this.bizUnregReason = bizUnregReason;
	}

	/**
	 * 전환 유형코드를 리턴한다.
	 * 
	 * @return changedCd - 전환 유형코드
	 */
	public String getChangedCd() {
		return this.changedCd;
	}

	/**
	 * 전환 유형코드를 설정한다.
	 * 
	 * @param changedCd
	 *            전환 유형코드
	 */
	public void setChangedCd(String changedCd) {
		this.changedCd = changedCd;
	}

	/**
	 * 유료전환일시를 리턴한다.
	 * 
	 * @return chargedDate - 유료전환일시
	 */
	public String getChargedDate() {
		return this.chargedDate;
	}

	/**
	 * 유료전환일시를 설정한다.
	 * 
	 * @param chargedDate
	 *            유료전환일시
	 */
	public void setChargedDate(String chargedDate) {
		this.chargedDate = chargedDate;
	}

	/**
	 * 승인 요청일시를 리턴한다.
	 * 
	 * @return approveReqDate - 승인 요청일시
	 */
	public String getApproveReqDate() {
		return this.approveReqDate;
	}

	/**
	 * 승인 요청일시를 설정한다.
	 * 
	 * @param approveReqDate
	 *            승인 요청일시
	 */
	public void setApproveReqDate(String approveReqDate) {
		this.approveReqDate = approveReqDate;
	}

	/**
	 * 승인 완료일시를 리턴한다.
	 * 
	 * @return approveComptDate - 승인 완료일시
	 */
	public String getApproveComptDate() {
		return this.approveComptDate;
	}

	/**
	 * 승인 완료일시를 설정한다.
	 * 
	 * @param approveComptDate
	 *            승인 완료일시
	 */
	public void setApproveComptDate(String approveComptDate) {
		this.approveComptDate = approveComptDate;
	}

	/**
	 * 승인 거부일시를 리턴한다.
	 * 
	 * @return approveDenyDate - 승인 거부일시
	 */
	public String getApproveDenyDate() {
		return this.approveDenyDate;
	}

	/**
	 * 승인 거부일시를 설정한다.
	 * 
	 * @param approveDenyDate
	 *            승인 거부일시
	 */
	public void setApproveDenyDate(String approveDenyDate) {
		this.approveDenyDate = approveDenyDate;
	}

	/**
	 * 처리 상태코드를 리턴한다.
	 * 
	 * @return procStatusCd - 처리 상태코드
	 */
	public String getProcStatusCd() {
		return this.procStatusCd;
	}

	/**
	 * 처리 상태코드를 설정한다.
	 * 
	 * @param procStatusCd
	 *            처리 상태코드
	 */
	public void setProcStatusCd(String procStatusCd) {
		this.procStatusCd = procStatusCd;
	}

	/**
	 * 통신사 코드를 리턴한다.
	 * 
	 * @return sellerTelecom - 통신사 코드
	 */
	public String getSellerTelecom() {
		return this.sellerTelecom;
	}

	/**
	 * 통신사 코드를 설정한다.
	 * 
	 * @param sellerTelecom
	 *            통신사 코드
	 */
	public void setSellerTelecom(String sellerTelecom) {
		this.sellerTelecom = sellerTelecom;
	}

	/**
	 * 수정일시를 리턴한다.
	 * 
	 * @return updateDate - 수정일시
	 */
	public String getUpdateDate() {
		return this.updateDate;
	}

	/**
	 * 수정일시를 설정한다.
	 * 
	 * @param updateDate
	 *            수정일시
	 */
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * 은행BSB 리턴한다.
	 * 
	 * @return bankBsb - 은행명Bsb
	 */
	public String getBankBsb() {
		return this.bankBsb;
	}

	/**
	 * 은행Bsb 설정한다.
	 * 
	 * @param bankBsb
	 *            Bsb
	 */
	public void setBankBsb(String bankBsb) {
		this.bankBsb = bankBsb;
	}

	/**
	 * 은행명을 리턴한다.
	 * 
	 * @return bankName - 은행명
	 */
	public String getBankName() {
		return this.bankName;
	}

	/**
	 * 은행명을 설정한다.
	 * 
	 * @param bankName
	 *            은행명
	 */
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	/**
	 * 은행지점코드를 리턴한다.
	 * 
	 * @return bankBranchCode - 은행지점코드
	 */
	public String getBankBranchCode() {
		return this.bankBranchCode;
	}

	/**
	 * 은행지점코드를 설정한다.
	 * 
	 * @param bankBranchCode
	 *            은행지점코드
	 */
	public void setBankBranchCode(String bankBranchCode) {
		this.bankBranchCode = bankBranchCode;
	}

	/**
	 * 은행지점명을 리턴한다.
	 * 
	 * @return bankBranch - 은행지점명
	 */
	public String getBankBranch() {
		return this.bankBranch;
	}

	/**
	 * 은행지점명을 설정한다.
	 * 
	 * @param bankBranch
	 *            은행지점명
	 */
	public void setBankBranch(String bankBranch) {
		this.bankBranch = bankBranch;
	}

	/**
	 * 은행주소를 리턴한다.
	 * 
	 * @return bankAddress - 은행주소
	 */
	public String getBankAddress() {
		return this.bankAddress;
	}

	/**
	 * 은행주소를 설정한다.
	 * 
	 * @param bankAddress
	 *            은행주소
	 */
	public void setBankAddress(String bankAddress) {
		this.bankAddress = bankAddress;
	}

	/**
	 * 은행 위치를 리턴한다.
	 * 
	 * @return bankLocation - 은행 위치
	 */
	public String getBankLocation() {
		return this.bankLocation;
	}

	/**
	 * 은행 위치를 설정한다.
	 * 
	 * @param bankLocation
	 *            은행 위치
	 */
	public void setBankLocation(String bankLocation) {
		this.bankLocation = bankLocation;
	}

	/**
	 * Swift 코드를 리턴한다.
	 * 
	 * @return swiftCode - Swift 코드
	 */
	public String getSwiftCode() {
		return this.swiftCode;
	}

	/**
	 * Swift 코드를 설정한다.
	 * 
	 * @param swiftCode
	 *            Swift 코드
	 */
	public void setSwiftCode(String swiftCode) {
		this.swiftCode = swiftCode;
	}

	/**
	 * IBAN 코드를 리턴한다.
	 * 
	 * @return ibanCode - IBAN 코드
	 */
	public String getIbanCode() {
		return this.ibanCode;
	}

	/**
	 * IBAN 코드를 설정한다.
	 * 
	 * @param ibanCode
	 *            IBAN 코드
	 */
	public void setIbanCode(String ibanCode) {
		this.ibanCode = ibanCode;
	}

	/**
	 * ABA 코드를 리턴한다.
	 * 
	 * @return abaCode - ABA 코드
	 */
	public String getAbaCode() {
		return this.abaCode;
	}

	/**
	 * ABA 코드를 설정한다.
	 * 
	 * @param abaCode
	 *            ABA 코드
	 */
	public void setAbaCode(String abaCode) {
		this.abaCode = abaCode;
	}

	/**
	 * TPIN 코드를 리턴한다.
	 * 
	 * @return tpinCode - TPIN 코드
	 */
	public String getTpinCode() {
		return this.tpinCode;
	}

	/**
	 * TPIN 코드를 설정한다.
	 * 
	 * @param tpinCode
	 *            TPIN 코드
	 */
	public void setTpinCode(String tpinCode) {
		this.tpinCode = tpinCode;
	}

	/**
	 * CEO 생년월일을 리턴한다.
	 * 
	 * @return ceoBirthDay - CEO 생년월일
	 */
	public String getCeoBirthDay() {
		return this.ceoBirthDay;
	}

	/**
	 * CEO 생년월일을 설정한다.
	 * 
	 * @param ceoBirthDay
	 *            CEO 생년월일
	 */
	public void setCeoBirthDay(String ceoBirthDay) {
		this.ceoBirthDay = ceoBirthDay;
	}

	/**
	 * 언어 코드를 리턴한다.
	 * 
	 * @return sellerLanguage - 언어 코드
	 */
	public String getSellerLanguage() {
		return this.sellerLanguage;
	}

	/**
	 * 언어 코드를 설정한다.
	 * 
	 * @param sellerLanguage
	 *            언어 코드
	 */
	public void setSellerLanguage(String sellerLanguage) {
		this.sellerLanguage = sellerLanguage;
	}

	/**
	 * 사유를 리턴한다.
	 * 
	 * @return reason - 사유
	 */
	public String getReason() {
		return this.reason;
	}

	/**
	 * 사유를 설정한다.
	 * 
	 * @param reason
	 *            사유
	 */
	public void setReason(String reason) {
		this.reason = reason;
	}

	/**
	 * 벤더코드를 리턴한다.
	 * 
	 * @return vendorCode - 벤더코드
	 */
	public String getVendorCode() {
		return this.vendorCode;
	}

	/**
	 * 벤더코드를 설정한다.
	 * 
	 * @param vendorCode
	 *            벤더코드
	 */
	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
	}

	/**
	 * 간이 과세여부를 리턴한다.
	 * 
	 * @return isBizTaxable - 간이 과세여부
	 */
	public String getIsBizTaxable() {
		return this.isBizTaxable;
	}

	/**
	 * 간이 과세여부를 설정한다.
	 * 
	 * @param isBizTaxable
	 *            간이 과세여부
	 */
	public void setIsBizTaxable(String isBizTaxable) {
		this.isBizTaxable = isBizTaxable;
	}

	/**
	 * 심의 등급코드를 리턴한다.
	 * 
	 * @return bizGrade - 심의 등급코드
	 */
	public String getBizGrade() {
		return this.bizGrade;
	}

	/**
	 * 심의 등급코드를 설정한다.
	 * 
	 * @param bizGrade
	 *            심의 등급코드
	 */
	public void setBizGrade(String bizGrade) {
		this.bizGrade = bizGrade;
	}

	/**
	 * 자동 차감 가능 대상여부를 리턴한다.
	 * 
	 * @return isDeductible - 자동 차감 가능 대상여부
	 */
	public String getIsDeductible() {
		return this.isDeductible;
	}

	/**
	 * 자동 차감 가능 대상여부를 설정한다.
	 * 
	 * @param isDeductible
	 *            자동 차감 가능 대상여부
	 */
	public void setIsDeductible(String isDeductible) {
		this.isDeductible = isDeductible;
	}

	/**
	 * 입점 상점코드를 리턴한다.
	 * 
	 * @return marketCode - 입점 상점코드
	 */
	public String getMarketCode() {
		return this.marketCode;
	}

	/**
	 * 입점 상점코드를 설정한다.
	 * 
	 * @param marketCode
	 *            입점 상점코드
	 */
	public void setMarketCode(String marketCode) {
		this.marketCode = marketCode;
	}

	/**
	 * 입점 회원 상태코드를 리턴한다.
	 * 
	 * @return marketStatus - 입점 회원 상태코드
	 */
	public String getMarketStatus() {
		return this.marketStatus;
	}

	/**
	 * 입점 회원 상태코드를 설정한다.
	 * 
	 * @param marketStatus
	 *            입점 회원 상태코드
	 */
	public void setMarketStatus(String marketStatus) {
		this.marketStatus = marketStatus;
	}

	/**
	 * 계좌인증일시를 리턴한다.
	 * 
	 * @return accountRealDate - 계좌인증일시
	 */
	public String getAccountRealDate() {
		return this.accountRealDate;
	}

	/**
	 * 계좌인증일시를 설정한다.
	 * 
	 * @param accountRealDate
	 *            계좌인증일시
	 */
	public void setAccountRealDate(String accountRealDate) {
		this.accountRealDate = accountRealDate;
	}

	/**
	 * 담당자 무선 전화번호를 리턴한다.
	 * 
	 * @return sellerPhone - 담당자 무선 전화번호
	 */
	public String getSellerPhone() {
		return this.sellerPhone;
	}

	/**
	 * 담당자 무선 전화번호를 설정한다.
	 * 
	 * @param sellerPhone
	 *            담당자 무선 전화번호
	 */

	public void setSellerPhone(String sellerPhone) {
		this.sellerPhone = sellerPhone;
	}

	/**
	 * 사업장 우편번호를 리턴한다.
	 * 
	 * @return sellerBizZip - 사업장 우편번호
	 */
	public String getSellerBizZip() {
		return this.sellerBizZip;
	}

	/**
	 * 사업장 우편번호를 설정한다.
	 * 
	 * @param sellerBizZip
	 *            사업장 우편번호
	 */
	public void setSellerBizZip(String sellerBizZip) {
		this.sellerBizZip = sellerBizZip;
	}

	/**
	 * 사업장 주소를 리턴한다.
	 * 
	 * @return sellerBizAddress- 사업장 주소
	 */
	public String getSellerBizAddress() {
		return this.sellerBizAddress;
	}

	/**
	 * 사업장 주소를 설정한다.
	 * 
	 * @param sellerBizAddress
	 *            사업장 주소
	 */
	public void setSellerBizAddress(String sellerBizAddress) {
		this.sellerBizAddress = sellerBizAddress;
	}

	/**
	 * 사업장 상세주소를 리턴한다.
	 * 
	 * @return sellerBizDetailAddress- 사업장 상세주소
	 */
	public String getSellerBizDetailAddress() {
		return this.sellerBizDetailAddress;
	}

	/**
	 * 사업장 상세주소를 설정한다.
	 * 
	 * @param sellerBizDetailAddress
	 *            사업장 상세주소
	 */
	public void setSellerBizDetailAddress(String sellerBizDetailAddress) {
		this.sellerBizDetailAddress = sellerBizDetailAddress;
	}

	/**
	 * Returns the serial version UID.
	 * 
	 * @return serialVersionUID - the serial version UID
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return Utils.printKeyValues(this);
	}
}
