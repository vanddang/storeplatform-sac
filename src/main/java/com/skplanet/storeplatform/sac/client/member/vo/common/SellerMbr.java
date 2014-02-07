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
public class SellerMbr extends CommonInfo {
	private static final long serialVersionUID = 1L;

	/** Tenant id. */
	private String tenantID;

	/** 판매자 키. */
	private String sellerKey; // INSD_SELLERMBR_NO

	/** 판매자 ID. */
	private String sellerId;

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

	/** 국내판매자 여부. */
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
	private String rightProfile; // 서브계정 권한

	/** 서브계정 설명. */
	private String subSellerMemo; // SUB_ACCT_DESC 서브계정에서 사용 아직 db table 컬럼 추가안됨

	public String getRightProfile() {
		return this.rightProfile;
	}

	public void setRightProfile(String rightProfile) {
		this.rightProfile = rightProfile;
	}

	public String getSellerId() {
		return this.sellerId;
	}

	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}

	public String getApproveDate() {
		return this.approveDate;
	}

	public void setApproveDate(String approveDate) {
		this.approveDate = approveDate;
	}

	public String getBizGrade() {
		return this.bizGrade;
	}

	public void setBizGrade(String bizGrade) {
		this.bizGrade = bizGrade;
	}

	public String getBizKindCd() {
		return this.bizKindCd;
	}

	public void setBizKindCd(String bizKindCd) {
		this.bizKindCd = bizKindCd;
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

	public String getCeoBirthDay() {
		return this.ceoBirthDay;
	}

	public void setCeoBirthDay(String ceoBirthDay) {
		this.ceoBirthDay = ceoBirthDay;
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

	public String getCordedTelephone() {
		return this.cordedTelephone;
	}

	public void setCordedTelephone(String cordedTelephone) {
		this.cordedTelephone = cordedTelephone;
	}

	public String getCordedTelephoneCountry() {
		return this.cordedTelephoneCountry;
	}

	public void setCordedTelephoneCountry(String cordedTelephoneCountry) {
		this.cordedTelephoneCountry = cordedTelephoneCountry;
	}

	public String getCustomerEmail() {
		return this.customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	public String getCustomerPhone() {
		return this.customerPhone;
	}

	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}

	public String getCustomerPhoneCountry() {
		return this.customerPhoneCountry;
	}

	public void setCustomerPhoneCountry(String customerPhoneCountry) {
		this.customerPhoneCountry = customerPhoneCountry;
	}

	public String getIsAccountReal() {
		return this.isAccountReal;
	}

	public void setIsAccountReal(String isAccountReal) {
		this.isAccountReal = isAccountReal;
	}

	public String getIsBizRegistered() {
		return this.isBizRegistered;
	}

	public void setIsBizRegistered(String isBizRegistered) {
		this.isBizRegistered = isBizRegistered;
	}

	public String getIsBizTaxable() {
		return this.isBizTaxable;
	}

	public void setIsBizTaxable(String isBizTaxable) {
		this.isBizTaxable = isBizTaxable;
	}

	public String getIsDeductible() {
		return this.isDeductible;
	}

	public void setIsDeductible(String isDeductible) {
		this.isDeductible = isDeductible;
	}

	public String getIsDomestic() {
		return this.isDomestic;
	}

	public void setIsDomestic(String isDomestic) {
		this.isDomestic = isDomestic;
	}

	public String getIsParent() {
		return this.isParent;
	}

	public void setIsParent(String isParent) {
		this.isParent = isParent;
	}

	public String getIsRealName() {
		return this.isRealName;
	}

	public void setIsRealName(String isRealName) {
		this.isRealName = isRealName;
	}

	public String getIsRecvEmail() {
		return this.isRecvEmail;
	}

	public void setIsRecvEmail(String isRecvEmail) {
		this.isRecvEmail = isRecvEmail;
	}

	public String getIsRecvSMS() {
		return this.isRecvSMS;
	}

	public void setIsRecvSMS(String isRecvSMS) {
		this.isRecvSMS = isRecvSMS;
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

	public String getParentSellerKey() {
		return this.parentSellerKey;
	}

	public void setParentSellerKey(String parentSellerKey) {
		this.parentSellerKey = parentSellerKey;
	}

	public String getRegDate() {
		return this.regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public String getRepEmail() {
		return this.repEmail;
	}

	public void setRepEmail(String repEmail) {
		this.repEmail = repEmail;
	}

	public String getRepFax() {
		return this.repFax;
	}

	public void setRepFax(String repFax) {
		this.repFax = repFax;
	}

	public String getRepFaxArea() {
		return this.repFaxArea;
	}

	public void setRepFaxArea(String repFaxArea) {
		this.repFaxArea = repFaxArea;
	}

	public String getRepPhone() {
		return this.repPhone;
	}

	public void setRepPhone(String repPhone) {
		this.repPhone = repPhone;
	}

	public String getRepPhoneArea() {
		return this.repPhoneArea;
	}

	public void setRepPhoneArea(String repPhoneArea) {
		this.repPhoneArea = repPhoneArea;
	}

	public String getSecedeDate() {
		return this.secedeDate;
	}

	public void setSecedeDate(String secedeDate) {
		this.secedeDate = secedeDate;
	}

	public String getSecedePathCd() {
		return this.secedePathCd;
	}

	public void setSecedePathCd(String secedePathCd) {
		this.secedePathCd = secedePathCd;
	}

	public String getSecedeReasonCode() {
		return this.secedeReasonCode;
	}

	public void setSecedeReasonCode(String secedeReasonCode) {
		this.secedeReasonCode = secedeReasonCode;
	}

	public String getSecedeReasonMessage() {
		return this.secedeReasonMessage;
	}

	public void setSecedeReasonMessage(String secedeReasonMessage) {
		this.secedeReasonMessage = secedeReasonMessage;
	}

	public String getSellerAddress() {
		return this.sellerAddress;
	}

	public void setSellerAddress(String sellerAddress) {
		this.sellerAddress = sellerAddress;
	}

	public String getSellerBirthDay() {
		return this.sellerBirthDay;
	}

	public void setSellerBirthDay(String sellerBirthDay) {
		this.sellerBirthDay = sellerBirthDay;
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

	public String getSellerBizNumber() {
		return this.sellerBizNumber;
	}

	public void setSellerBizNumber(String sellerBizNumber) {
		this.sellerBizNumber = sellerBizNumber;
	}

	public String getSellerBizType() {
		return this.sellerBizType;
	}

	public void setSellerBizType(String sellerBizType) {
		this.sellerBizType = sellerBizType;
	}

	public String getSellerCategory() {
		return this.sellerCategory;
	}

	public void setSellerCategory(String sellerCategory) {
		this.sellerCategory = sellerCategory;
	}

	public String getSellerCity() {
		return this.sellerCity;
	}

	public void setSellerCity(String sellerCity) {
		this.sellerCity = sellerCity;
	}

	public String getSellerClass() {
		return this.sellerClass;
	}

	public void setSellerClass(String sellerClass) {
		this.sellerClass = sellerClass;
	}

	public String getSellerCompany() {
		return this.sellerCompany;
	}

	public void setSellerCompany(String sellerCompany) {
		this.sellerCompany = sellerCompany;
	}

	public String getSellerCountry() {
		return this.sellerCountry;
	}

	public void setSellerCountry(String sellerCountry) {
		this.sellerCountry = sellerCountry;
	}

	public String getSellerDetailAddress() {
		return this.sellerDetailAddress;
	}

	public void setSellerDetailAddress(String sellerDetailAddress) {
		this.sellerDetailAddress = sellerDetailAddress;
	}

	public String getSellerEmail() {
		return this.sellerEmail;
	}

	public void setSellerEmail(String sellerEmail) {
		this.sellerEmail = sellerEmail;
	}

	public String getSellerKey() {
		return this.sellerKey;
	}

	public void setSellerKey(String sellerKey) {
		this.sellerKey = sellerKey;
	}

	public String getSellerLanguage() {
		return this.sellerLanguage;
	}

	public void setSellerLanguage(String sellerLanguage) {
		this.sellerLanguage = sellerLanguage;
	}

	public String getSellerMainStatus() {
		return this.sellerMainStatus;
	}

	public void setSellerMainStatus(String sellerMainStatus) {
		this.sellerMainStatus = sellerMainStatus;
	}

	public String getSellerName() {
		return this.sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	public String getSellerNickName() {
		return this.sellerNickName;
	}

	public void setSellerNickName(String sellerNickName) {
		this.sellerNickName = sellerNickName;
	}

	public String getSellerPhone() {
		return this.sellerPhone;
	}

	public void setSellerPhone(String sellerPhone) {
		this.sellerPhone = sellerPhone;
	}

	public String getSellerPhoneCountry() {
		return this.sellerPhoneCountry;
	}

	public void setSellerPhoneCountry(String sellerPhoneCountry) {
		this.sellerPhoneCountry = sellerPhoneCountry;
	}

	public String getSellerSex() {
		return this.sellerSex;
	}

	public void setSellerSex(String sellerSex) {
		this.sellerSex = sellerSex;
	}

	public String getSellerSSNumber() {
		return this.sellerSSNumber;
	}

	public void setSellerSSNumber(String sellerSSNumber) {
		this.sellerSSNumber = sellerSSNumber;
	}

	public String getSellerState() {
		return this.sellerState;
	}

	public void setSellerState(String sellerState) {
		this.sellerState = sellerState;
	}

	public String getSellerSubStatus() {
		return this.sellerSubStatus;
	}

	public void setSellerSubStatus(String sellerSubStatus) {
		this.sellerSubStatus = sellerSubStatus;
	}

	public String getSellerTelecom() {
		return this.sellerTelecom;
	}

	public void setSellerTelecom(String sellerTelecom) {
		this.sellerTelecom = sellerTelecom;
	}

	public String getSellerZip() {
		return this.sellerZip;
	}

	public void setSellerZip(String sellerZip) {
		this.sellerZip = sellerZip;
	}

	public String getTenantID() {
		return this.tenantID;
	}

	public void setTenantID(String tenantID) {
		this.tenantID = tenantID;
	}

	public String getVendorCode() {
		return this.vendorCode;
	}

	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
	}

	public String getLoginStatusCode() {
		return this.loginStatusCode;
	}

	public void setLoginStatusCode(String loginStatusCode) {
		this.loginStatusCode = loginStatusCode;
	}

	public String getStopStatusCode() {
		return this.stopStatusCode;
	}

	public void setStopStatusCode(String stopStatusCode) {
		this.stopStatusCode = stopStatusCode;
	}

	public String getSubSellerMemo() {
		return this.subSellerMemo;
	}

	public void setSubSellerMemo(String subSellerMemo) {
		this.subSellerMemo = subSellerMemo;
	}

}
