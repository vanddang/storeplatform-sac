/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 * 
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.member.seller.vo;

import java.io.Serializable;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.member.client.common.util.Utils;

/**
 * 판매자 기본정보 + 비밀번호 Value Object
 * 
 * Updated on : 2014. 01. 15. Updated by : wisestone_dinga
 */
public class SellerMbrRetrievePWD extends CommonInfo implements Serializable {

	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	/** Tenant id. */
	private String tenantID;

	/** 공인인증여부. */
	private String isOfficialAuth;

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

	/** 서브 판매자 키. */
	private String subSellerKey; // INSD_SELLERMBR_NO

	/**
	 * 서브 판매자 키를 리턴한다.
	 * 
	 * @return subSellerKey - 서브판매자 키
	 */
	public String getSubSellerKey() {
		return this.subSellerKey;
	}

	/** 사업장 상세주소. */
	private String sellerBizDetailAddress; // ENPRPL_ADDR_DTL 바뀌었나?

	/** 사업장 우편번호. */
	private String sellerBizZip; // ENPRPL_ZIP 바뀌었나?

	/** 사업장 주소. */
	private String sellerBizAddress; // ENPRPL_ADDR 바뀌었나?

	/** 담당자 무선 전화번호. */
	private String chargerPhone; // CHRGPERS_WILS_TEL_NO

	/**
	 * 서브 판매자 키를 설정한다.
	 * 
	 * @param subSellerKey
	 *            서브판매자 키
	 */
	public void setSubSellerKey(String subSellerKey) {
		this.subSellerKey = subSellerKey;
	}

	/** Start date. */
	private String startDate; // USE_START_DT

	/** End date. */
	private String endDate; // USE_END_DT

	/**
	 * Start date를 리턴한다.
	 * 
	 * @return startDate - Start date
	 */
	public String getStartDate() {
		return this.startDate;
	}

	/**
	 * Start date를 설정한다.
	 * 
	 * @param startDate
	 *            Start date
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	/**
	 * End date를 리턴한다.
	 * 
	 * @return endDate - End date
	 */
	public String getEndDate() {
		return this.endDate;
	}

	/**
	 * End date를 설정한다.
	 * 
	 * @param endDate
	 *            End date
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/** 판매자 키. */
	private String sellerKey; // INSD_SELLERMBR_NO

	/** 판매자 ID. */
	private String sellerID;

	/** 비밀번호. */
	private String sellerPW; // PWD

	/** 상위 판매자 키. */
	private String parentSellerKey; // UP_INSD_SELLERMBR_NO 상위 판매자키

	/**
	 * 판매자 구분 코드.
	 * 
	 * @see com.skplanet.storeplatform.member.common.code.SellerTypeCode
	 */
	private String sellerClass;

	/**
	 * 판매자 분류 코드.
	 * 
	 * @see com.skplanet.storeplatform.member.common.code.SellerClassCode
	 */
	private String sellerCategory;

	/**
	 * 판매자 메인상태 코드.
	 * 
	 * @see com.skplanet.storeplatform.member.common.code.MainStateCode
	 */
	private String sellerMainStatus;

	/**
	 * 판매자 서브상태 코드.
	 * 
	 * @see com.skplanet.storeplatform.member.common.code.SubStateCode
	 */
	private String sellerSubStatus;

	/** 로그인 상태 코드. */
	private String loginStatusCode; // LOGIN_STATUS_CD

	/** 직권중지 상태 코드. */
	private String stopStatusCode; // OFAUTH_STOP_STATUS_CD

	/** 내국인 여부. */
	private String isDomestic; // ICTRY_SELLERMBR_YN 내국인인지 외국인인지

	/** 국가 코드. */
	private String sellerCountry; // NATION_CD

	/** 언어 코드. */
	private String sellerLanguage; // LANG_CD

	/** 등록일시. */
	private String regDate; // ENTRY_DAY 가입일 등록일시 DEFAULT SYSDATE 로 사용하겠음

	/** 탈퇴일시. */
	private String secedeDate; // BOLTER_DAY 탈퇴일

	/** 탈퇴사유 코드. */
	private String secedeReasonCode;

	/** 탈퇴사유 메시지. */
	private String secedeReasonMessage;

	/** 승인일시. */
	private String approveDate; // APPR_DAY 승인일

	/** 판매자명. */
	private String sellerName; // MBR_NM 회원명

	/** 담당자명. */
	private String charger; // CHRGPERS_NM 담당자 명

	/** 판매자 Email. */
	private String sellerEmail;

	/** Email 수신여부. */
	private String isRecvEmail; // EMAIL_RECV_YN

	/** 유선 국가번호. */
	private String cordedTelephoneCountry; // WIRE_NATION_NO 유선국가번호 sellerPhoneCountry > cordedTelephoneCountry 변경했다

	/** 유선 전화번호. */
	private String cordedTelephone; // WIRE_TEL_NO 유선전화번호

	/** 무선 국가번호. */
	private String sellerPhoneCountry; // WILS_NATION_NO // 무선 국가 번호

	/** 무선 전화번호. */
	private String sellerPhone; // WILS_TEL_NO 무선 전화번호

	/** SMS 수신여부. */
	private String isRecvSMS; // SMS_RECV_YN

	/** 실명인증여부. */
	private String isRealName; // REALNM_AUTH_YN 실명인증여부

	/** 성별. */
	private String sellerSex; // SEX

	/** 우편번호. */
	private String sellerZip; // ZIP 우편번호

	/** 주소. */
	private String sellerAddress; // ADDR 주소

	/** 상세주소. */
	private String sellerDetailAddress; // DTL_ADDR 상세주소

	/** 도시. */
	private String sellerCity; // FR_CITY 외국도시

	/** 지역. */
	private String sellerState; // FR_AREA 외국지역

	/** 벤더코드. */
	private String vendorCode; // // VENDOR_CD 벤더코드

	/** 회사명. */
	private String sellerCompany;

	/** 쇼핑 노출명. */
	private String sellerNickName; // SHPG_EXPO_NM 쇼핑 노출명

	/** 주민번호. */
	private String sellerSSNumber; // CTZ_REG_NO 주민번호

	/** 사업자 등록번호. */
	private String sellerBizNumber;

	/** 사업자 종류 코드. */
	private String bizKindCd; // BIZ_KIND_CD 사업자 종류 코드

	/** 법인등록번호. */
	private String sellerBizCorpNumber; // CORP_REG_NO 법인등록번호

	/** 업태명. */
	private String sellerBizCategory; // COND_NM 업태명 업태 업태

	/** 업종명. */
	private String sellerBizType; // INDT_NM 업종명 종목 종목

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

	/** 탈퇴 경로코드. */
	private String secedePathCd; // ENTRY_BOLTER_PATH_CD 가입 퇄퇴 경로 코드 ##### 전환 쪽에서 사용

	/** 고객 대응 Email. */
	private String customerEmail; // CUST_CORS_EMAIL 고객 대응 이메일

	/** 고객 대응 전화 국가번호. */
	private String customerPhoneCountry; // CUST_CORS_TEL_NATION_NO

	/** 고객 대응 전화번호. */
	private String customerPhone; // CUST_CORS_TEL_NO //고객응대 전화번호

	/** 통신사 코드. */
	private String sellerTelecom; // MNO_CD 통신사 코드

	/** 생년월일. */
	private String sellerBirthDay; // BIRTH 생년월일 DB 에 없음?

	/** 법정대리인 동의여부. */
	private String isParent; // LGL_AGENT_AGREE_YN 법정대리인 동의여무 DB 에 컬럼 추가해야함(처리된듯)

	/** 계좌 인증여부. */
	private String isAccountReal; // ACCT_AUTH_YN 계좌 인증여부 컬럼

	/** CEO명. */
	private String ceoName; // CEO_NM

	/** CEO 생년월일. */
	private String ceoBirthDay; // CEO_BIRTH

	/** 서브계정 권한. */
	private String rightProfileList; // 서브계정 권한

	/** 서브계정 설명. */
	private String subSellerMemo; // SUB_ACCT_DESC 서브계정에서 사용 아직 db table 컬럼 추가안됨

	/** 비밀번호. */
	private String pwd;

	/** 비밀번호encType . */
	private String encType;

	/** 비밀번호 수정일시. */
	private String updateDate;

	/** 비밀번호 실패횟수. */
	private int failCnt;

	/**
	 * 비밀번호를 반환한다.
	 * 
	 * @return the pwd - 비밀번호
	 */
	public String getEncType() {
		return this.encType;
	}

	/**
	 * 비밀번호encType를 설정한다.
	 * 
	 * @param encType
	 *            비밀번호encType
	 */
	public void setEncType(String encType) {
		this.encType = encType;
	}

	/**
	 * 비밀번호encType를 반환한다.
	 * 
	 * @return the encType - 비밀번호encType
	 */
	public String getPwd() {
		return this.pwd;
	}

	/**
	 * 비밀번호를 설정한다.
	 * 
	 * @param pwd
	 *            비밀번호
	 */
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	/**
	 * 비밀번호 수정일시를 반환한다.
	 * 
	 * @return the update date - 비밀번호 수정일시
	 */
	public String getUpdateDate() {
		return this.updateDate;
	}

	/**
	 * 비밀번호 수정일시를 설정한다.
	 * 
	 * @param updateDate
	 *            비밀번호 수정일시
	 */
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * 담당자 무선 전화번호를 리턴한다.
	 * 
	 * @return chargerPhone - 담당자 무선 전화번호
	 */
	public String getChargerPhone() {
		return this.chargerPhone;
	}

	/**
	 * 담당자 무선 전화번호를 설정한다.
	 * 
	 * @param chargerPhone
	 *            담당자 무선 전화번호
	 */

	public void setChargerPhone(String chargerPhone) {
		this.chargerPhone = chargerPhone;
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
	 * 비밀번호 실패횟수를 반환한다.
	 * 
	 * @return the fail cnt - 비밀번호 실패횟수
	 */
	public int getFailCnt() {
		return this.failCnt;
	}

	/**
	 * 비밀번호 실패횟수를 설정한다.
	 * 
	 * @param failCnt
	 *            비밀번호 실패횟수
	 */
	public void setFailCnt(int failCnt) {
		this.failCnt = failCnt;
	}

	/**
	 * Tenant ID를 리턴한다.
	 * 
	 * @return tenantID - Tenant ID
	 */
	public String getTenantID() {
		return this.tenantID;
	}

	/**
	 * Tenant ID를 설정한다.
	 * 
	 * @param tenantID
	 *            Tenant ID
	 */
	public void setTenantID(String tenantID) {
		this.tenantID = tenantID;
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
	 * 판매자 ID를 리턴한다.
	 * 
	 * @return sellerID - 판매자 ID
	 */
	public String getSellerID() {
		return this.sellerID;
	}

	/**
	 * 판매자 ID를 설정한다.
	 * 
	 * @param sellerID
	 *            판매자 ID
	 */
	public void setSellerID(String sellerID) {
		this.sellerID = sellerID;
	}

	/**
	 * 비밀번호를 리턴한다.
	 * 
	 * @return sellerPW - 비밀번호
	 */
	public String getSellerPW() {
		return this.sellerPW;
	}

	/**
	 * 비밀번호를 설정한다.
	 * 
	 * @param sellerPW
	 *            비밀번호
	 */
	public void setSellerPW(String sellerPW) {
		this.sellerPW = sellerPW;
	}

	/**
	 * 상위 판매자 키를 리턴한다.
	 * 
	 * @return parentSellerKey - 상위 판매자 키
	 */
	public String getParentSellerKey() {
		return this.parentSellerKey;
	}

	/**
	 * 상위 판매자 키를 설정한다.
	 * 
	 * @param parentSellerKey
	 *            상위 판매자 키
	 */
	public void setParentSellerKey(String parentSellerKey) {
		this.parentSellerKey = parentSellerKey;
	}

	/**
	 * 판매자 구분 코드를 리턴한다.
	 * 
	 * @return sellerClass - 판매자 구분 코드
	 */
	public String getSellerClass() {
		return this.sellerClass;
	}

	/**
	 * 판매자 구분 코드를 설정한다.
	 * 
	 * @param sellerClass
	 *            판매자 구분 코드
	 */
	public void setSellerClass(String sellerClass) {
		this.sellerClass = sellerClass;
	}

	/**
	 * 판매자 분류 코드.
	 * 
	 * @return sellerCategory - 판매자 분류 코드
	 */
	public String getSellerCategory() {
		return this.sellerCategory;
	}

	/**
	 * 판매자 분류 코드를 설정한다.
	 * 
	 * @param sellerCategory
	 *            판매자 분류 코드
	 */
	public void setSellerCategory(String sellerCategory) {
		this.sellerCategory = sellerCategory;
	}

	/**
	 * 판매자 메인상태 코드를 리턴한다.
	 * 
	 * @return sellerMainStatus - 판매자 메인상태 코드
	 */
	public String getSellerMainStatus() {
		return this.sellerMainStatus;
	}

	/**
	 * 판매자 메인상태 코드를 설정한다.
	 * 
	 * @param sellerMainStatus
	 *            판매자 메인상태 코드
	 */
	public void setSellerMainStatus(String sellerMainStatus) {
		this.sellerMainStatus = sellerMainStatus;
	}

	/**
	 * 판매자 서브상태 코드를 리턴한다.
	 * 
	 * @return sellerSubStatus - 판매자 서브상태 코드
	 */
	public String getSellerSubStatus() {
		return this.sellerSubStatus;
	}

	/**
	 * 판매자 서브상태 코드를 설정한다.
	 * 
	 * @param sellerSubStatus
	 *            판매자 서브상태 코드
	 */
	public void setSellerSubStatus(String sellerSubStatus) {
		this.sellerSubStatus = sellerSubStatus;
	}

	/**
	 * 로그인 상태 코드를 리턴한다.
	 * 
	 * @return loginStatusCode - 로그인 상태 코드
	 */
	public String getLoginStatusCode() {
		return this.loginStatusCode;
	}

	/**
	 * 로그인 상태 코드를 설정한다.
	 * 
	 * @param loginStatusCode
	 *            로그인 상태 코드
	 */
	public void setLoginStatusCode(String loginStatusCode) {
		this.loginStatusCode = loginStatusCode;
	}

	/**
	 * 직권중지 상태 코드를 리턴한다.
	 * 
	 * @return stopStatusCode - 직권중지 상태 코드
	 */
	public String getStopStatusCode() {
		return this.stopStatusCode;
	}

	/**
	 * 직권중지 상태 코드를 설정한다.
	 * 
	 * @param stopStatusCode
	 *            직권중지 상태 코드
	 */
	public void setStopStatusCode(String stopStatusCode) {
		this.stopStatusCode = stopStatusCode;
	}

	/**
	 * 내국인 여부를 리턴한다.
	 * 
	 * @return isDomestic - 내국인 여부
	 */
	public String getIsDomestic() {
		return this.isDomestic;
	}

	/**
	 * 내국인 여부를 설정한다.
	 * 
	 * @param isDomestic
	 *            내국인 여부
	 */
	public void setIsDomestic(String isDomestic) {
		this.isDomestic = isDomestic;
	}

	/**
	 * 국가 코드를 리턴한다.
	 * 
	 * @return sellerCountry - 국가 코드
	 */
	public String getSellerCountry() {
		return this.sellerCountry;
	}

	/**
	 * 국가 코드를 설정한다.
	 * 
	 * @param sellerCountry
	 *            국가 코드
	 */
	public void setSellerCountry(String sellerCountry) {
		this.sellerCountry = sellerCountry;
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
	 * 등록일시를 리턴한다.
	 * 
	 * @return regDate - 등록일시
	 */
	public String getRegDate() {
		return this.regDate;
	}

	/**
	 * 등록일시를 설정한다.
	 * 
	 * @param regDate
	 *            등록일시
	 */
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	/**
	 * 탈퇴일시를 리턴한다.
	 * 
	 * @return secedeDate - 탈퇴일시
	 */
	public String getSecedeDate() {
		return this.secedeDate;
	}

	/**
	 * 탈퇴일시를 설정한다.
	 * 
	 * @param secedeDate
	 *            탈퇴일시
	 */
	public void setSecedeDate(String secedeDate) {
		this.secedeDate = secedeDate;
	}

	/**
	 * 탈퇴사유 코드를 리턴한다.
	 * 
	 * @return secedeReasonCode - 탈퇴사유 코드
	 */
	public String getSecedeReasonCode() {
		return this.secedeReasonCode;
	}

	/**
	 * 탈퇴사유 코드를 설정한다.
	 * 
	 * @param secedeReasonCode
	 *            탈퇴사유 코드
	 */
	public void setSecedeReasonCode(String secedeReasonCode) {
		this.secedeReasonCode = secedeReasonCode;
	}

	/**
	 * 탈퇴사유 메시지를 리턴한다.
	 * 
	 * @return secedeReasonMessage - 탈퇴사유 메시지
	 */
	public String getSecedeReasonMessage() {
		return this.secedeReasonMessage;
	}

	/**
	 * 탈퇴사유 메시지를 설정한다.
	 * 
	 * @param secedeReasonMessage
	 *            탈퇴사유 메시지
	 */
	public void setSecedeReasonMessage(String secedeReasonMessage) {
		this.secedeReasonMessage = secedeReasonMessage;
	}

	/**
	 * 승인일시를 리턴한다.
	 * 
	 * @return approveDate - 승인일시
	 */
	public String getApproveDate() {
		return this.approveDate;
	}

	/**
	 * 승인일시를 설정한다.
	 * 
	 * @param approveDate
	 *            승인일시
	 */
	public void setApproveDate(String approveDate) {
		this.approveDate = approveDate;
	}

	/**
	 * 판매자명을 리턴한다.
	 * 
	 * @return sellerName - 판매자명
	 */
	public String getSellerName() {
		return this.sellerName;
	}

	/**
	 * 판매자명을 설정한다.
	 * 
	 * @param sellerName
	 *            판매자명
	 */
	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
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
	 * 판매자 Email를 리턴한다.
	 * 
	 * @return sellerEmail - 판매자 Email
	 */
	public String getSellerEmail() {
		return this.sellerEmail;
	}

	/**
	 * 판매자 Email를 설정한다.
	 * 
	 * @param sellerEmail
	 *            판매자 Email
	 */
	public void setSellerEmail(String sellerEmail) {
		this.sellerEmail = sellerEmail;
	}

	/**
	 * Email 수신여부를 리턴한다.
	 * 
	 * @return isRecvEmail - Email 수신여부
	 */
	public String getIsRecvEmail() {
		return this.isRecvEmail;
	}

	/**
	 * Email 수신여부를 설정한다.
	 * 
	 * @param isRecvEmail
	 *            Email 수신여부
	 */
	public void setIsRecvEmail(String isRecvEmail) {
		this.isRecvEmail = isRecvEmail;
	}

	/**
	 * 유선 국가번호를 리턴한다.
	 * 
	 * @return cordedTelephoneCountry - 유선 국가번호
	 */
	public String getCordedTelephoneCountry() {
		return this.cordedTelephoneCountry;
	}

	/**
	 * 유선 국가번호를 설정한다.
	 * 
	 * @param cordedTelephoneCountry
	 *            유선 국가번호
	 */
	public void setCordedTelephoneCountry(String cordedTelephoneCountry) {
		this.cordedTelephoneCountry = cordedTelephoneCountry;
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
	 * 무선 국가번호를 리턴한다.
	 * 
	 * @return sellerPhoneCountry - 무선 국가번호
	 */
	public String getSellerPhoneCountry() {
		return this.sellerPhoneCountry;
	}

	/**
	 * 무선 국가번호를 설정한다.
	 * 
	 * @param sellerPhoneCountry
	 *            무선 국가번호
	 */
	public void setSellerPhoneCountry(String sellerPhoneCountry) {
		this.sellerPhoneCountry = sellerPhoneCountry;
	}

	/**
	 * 무선 전화번호를 리턴한다.
	 * 
	 * @return sellerPhone - 무선 전화번호
	 */
	public String getSellerPhone() {
		return this.sellerPhone;
	}

	/**
	 * 무선 전화번호를 설정한다.
	 * 
	 * @param sellerPhone
	 *            무선 전화번호
	 */
	public void setSellerPhone(String sellerPhone) {
		this.sellerPhone = sellerPhone;
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
	 * 실명인증여부를 리턴한다.
	 * 
	 * @return isRealName - 실명인증여부
	 */
	public String getIsRealName() {
		return this.isRealName;
	}

	/**
	 * 실명인증여부를 설정한다.
	 * 
	 * @param isRealName
	 *            실명인증여부
	 */
	public void setIsRealName(String isRealName) {
		this.isRealName = isRealName;
	}

	/**
	 * 성별을 리턴한다.
	 * 
	 * @return sellerSex - 성별
	 */
	public String getSellerSex() {
		return this.sellerSex;
	}

	/**
	 * 성별을 설정한다.
	 * 
	 * @param sellerSex
	 *            성별
	 */
	public void setSellerSex(String sellerSex) {
		this.sellerSex = sellerSex;
	}

	/**
	 * 우편번호를 리턴한다.
	 * 
	 * @return sellerZip - 우편번호
	 */
	public String getSellerZip() {
		return this.sellerZip;
	}

	/**
	 * 우편번호를 설정한다.
	 * 
	 * @param sellerZip
	 *            우편번호
	 */
	public void setSellerZip(String sellerZip) {
		this.sellerZip = sellerZip;
	}

	/**
	 * 주소를 리턴한다.
	 * 
	 * @return sellerAddress - 주소
	 */
	public String getSellerAddress() {
		return this.sellerAddress;
	}

	/**
	 * 주소를 설정한다.
	 * 
	 * @param sellerAddress
	 *            주소
	 */
	public void setSellerAddress(String sellerAddress) {
		this.sellerAddress = sellerAddress;
	}

	/**
	 * 상세주소를 리턴한다.
	 * 
	 * @return sellerDetailAddress - 상세주소
	 */
	public String getSellerDetailAddress() {
		return this.sellerDetailAddress;
	}

	/**
	 * 상세주소를 설정한다.
	 * 
	 * @param sellerDetailAddress
	 *            상세주소
	 */
	public void setSellerDetailAddress(String sellerDetailAddress) {
		this.sellerDetailAddress = sellerDetailAddress;
	}

	/**
	 * 도시를 리턴한다.
	 * 
	 * @return sellerCity - 도시
	 */
	public String getSellerCity() {
		return this.sellerCity;
	}

	/**
	 * 도시를 설정한다.
	 * 
	 * @param sellerCity
	 *            도시
	 */
	public void setSellerCity(String sellerCity) {
		this.sellerCity = sellerCity;
	}

	/**
	 * 지역을 리턴한다.
	 * 
	 * @return sellerState - 지역
	 */
	public String getSellerState() {
		return this.sellerState;
	}

	/**
	 * 지역을 설정한다.
	 * 
	 * @param sellerState
	 *            지역
	 */
	public void setSellerState(String sellerState) {
		this.sellerState = sellerState;
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
	 * 쇼핑 노출명을 리턴한다.
	 * 
	 * @return sellerNickName - 쇼핑 노출명
	 */
	public String getSellerNickName() {
		return this.sellerNickName;
	}

	/**
	 * 쇼핑 노출명을 설정한다.
	 * 
	 * @param sellerNickName
	 *            쇼핑 노출명
	 */
	public void setSellerNickName(String sellerNickName) {
		this.sellerNickName = sellerNickName;
	}

	/**
	 * 주민번호를 리턴한다.
	 * 
	 * @return sellerSSNumber - 주민번호
	 */
	public String getSellerSSNumber() {
		return this.sellerSSNumber;
	}

	/**
	 * 주민번호를 설정한다.
	 * 
	 * @param sellerSSNumber
	 *            주민번호
	 */
	public void setSellerSSNumber(String sellerSSNumber) {
		this.sellerSSNumber = sellerSSNumber;
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
	 * 사업자 종류코드를 리턴한다.
	 * 
	 * @return bizKindCd - 사업자 종류코드
	 */
	public String getBizKindCd() {
		return this.bizKindCd;
	}

	/**
	 * 사업자 종류코드를 설정한다.
	 * 
	 * @param bizKindCd
	 *            사업자 종류코드
	 */
	public void setBizKindCd(String bizKindCd) {
		this.bizKindCd = bizKindCd;
	}

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
	 * @return repPhone - 대표전화번호
	 */
	public String getRepPhone() {
		return this.repPhone;
	}

	/**
	 * 대표전화번호를 설정한다.
	 * 
	 * @param repPhone
	 *            대표전화번호
	 */
	public void setRepPhone(String repPhone) {
		this.repPhone = repPhone;
	}

	/**
	 * 대표팩스 국가번호를 리턴한다.
	 * 
	 * @return repFaxArea - 대표팩스 국가번호
	 */
	public String getRepFaxArea() {
		return this.repFaxArea;
	}

	/**
	 * 대표팩스 국가번호를 설정한다.
	 * 
	 * @param repFaxArea
	 *            대표팩스 국가번호
	 */
	public void setRepFaxArea(String repFaxArea) {
		this.repFaxArea = repFaxArea;
	}

	/**
	 * 대표팩스번호를 리턴한다.
	 * 
	 * @return repFax - 대표팩스번호
	 */
	public String getRepFax() {
		return this.repFax;
	}

	/**
	 * 대표팩스번호를 설정한다.
	 * 
	 * @param repFax
	 *            대표팩스번호
	 */
	public void setRepFax(String repFax) {
		this.repFax = repFax;
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
	 * 탈퇴 경로코드를 리턴한다.
	 * 
	 * @return secedePathCd - 탈퇴 경로코드
	 */
	public String getSecedePathCd() {
		return this.secedePathCd;
	}

	/**
	 * 탈퇴 경로코드를 설정한다.
	 * 
	 * @param secedePathCd
	 *            탈퇴 경로코드
	 */
	public void setSecedePathCd(String secedePathCd) {
		this.secedePathCd = secedePathCd;
	}

	/**
	 * 고객 대응 Email를 리턴한다.
	 * 
	 * @return customerEmail - 고객 대응 Email
	 */
	public String getCustomerEmail() {
		return this.customerEmail;
	}

	/**
	 * 고객 대응 Email를 설정한다.
	 * 
	 * @param customerEmail
	 *            고객 대응 Email
	 */
	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	/**
	 * 고객 대응 전화 국가번호를 리턴한다.
	 * 
	 * @return customerPhoneCountry - 고객 대응 전화 국가번호
	 */
	public String getCustomerPhoneCountry() {
		return this.customerPhoneCountry;
	}

	/**
	 * 고객 대응 전화 국가번호를 설정한다.
	 * 
	 * @param customerPhoneCountry
	 *            고객 대응 전화 국가번호
	 */
	public void setCustomerPhoneCountry(String customerPhoneCountry) {
		this.customerPhoneCountry = customerPhoneCountry;
	}

	/**
	 * 고객 대응 전화번호를 리턴한다.
	 * 
	 * @return customerPhone - 고객 대응 전화번호
	 */
	public String getCustomerPhone() {
		return this.customerPhone;
	}

	/**
	 * 고객 대응 전화번호를 설정한다.
	 * 
	 * @param customerPhone
	 *            고객 대응 전화번호
	 */
	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
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
	 * 생년월일을 리턴한다.
	 * 
	 * @return sellerBirthDay - 생년월일
	 */
	public String getSellerBirthDay() {
		return this.sellerBirthDay;
	}

	/**
	 * 생년월일을 설정한다.
	 * 
	 * @param sellerBirthDay
	 *            생년월일
	 */
	public void setSellerBirthDay(String sellerBirthDay) {
		this.sellerBirthDay = sellerBirthDay;
	}

	/**
	 * 법정대리인 동의여부를 리턴한다.
	 * 
	 * @return isParent - 법정대리인 동의여부
	 */
	public String getIsParent() {
		return this.isParent;
	}

	/**
	 * 법정대리인 동의여부를 설정한다.
	 * 
	 * @param isParent
	 *            법정대리인 동의여부
	 */
	public void setIsParent(String isParent) {
		this.isParent = isParent;
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
	 * 서브계정 설명을 리턴한다.
	 * 
	 * @return subSellerMemo - 서브계정 설명
	 */
	public String getSubSellerMemo() {
		return this.subSellerMemo;
	}

	/**
	 * 서브계정 설명을 설정한다.
	 * 
	 * @param subSellerMemo
	 *            서브계정 설명
	 */
	public void setSubSellerMemo(String subSellerMemo) {
		this.subSellerMemo = subSellerMemo;
	}

	/**
	 * 서브계정 권한을 리턴한다.
	 * 
	 * @return rightProfileList - 서브계정 권한
	 */
	public String getRightProfileList() {
		return this.rightProfileList;
	}

	/**
	 * 서브계정 권한을 설정한다.
	 * 
	 * @param rightProfileList
	 *            서브계정 권한
	 */
	public void setRightProfileList(String rightProfileList) {
		this.rightProfileList = rightProfileList;
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
