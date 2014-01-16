package com.skplanet.storeplatform.sac.client.member.vo.seller;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

@SuppressWarnings("serial")
public class CreateReq extends CommonInfo {

	/** 판매자 구분코드. */
	@NotNull
	@NotBlank
	private String sellerClass;
	/** 판매자 분류코드. */
	@NotNull
	@NotBlank
	private String sellerCategory;
	/** 판매자 main 상태코드. */
	@NotNull
	@NotBlank
	private String sellerMainStatus;
	/** 판매자 sub 상태코드. */
	@NotNull
	@NotBlank
	private String sellerSubStatus;
	/** 판매자 ID. */
	private String sellerId;
	/** 판매자 PW. */
	private String sellerPW;
	/** 이동통신사. */
	@NotNull
	@NotBlank
	private String sellerTelecom;
	/** 전화번호 국가코드. */
	private String sellerPhoneCountry;
	/** 전화번호. */
	private String sellerPhone;
	/** SMS 수신 여부. */
	@NotNull
	@NotBlank
	private String isRecvSMS;
	/** 판매자 이메일. */
	private String sellerEmail;
	/** 이메일수신 여부. */
	@NotNull
	@NotBlank
	private String isRecvEmail;
	/** 판매자 이름. */
	private String sellerName;
	/** 노출 이름. */
	private String sellerNickName;
	/** 판매자 성별. */
	@NotNull
	@NotBlank
	private String sellerSex;
	/** 판매자 생년월일. */
	private String sellerBirthDay;
	/** 주민등록번호. */
	private String sellerSSNumber;
	/** 우편번호. */
	private String sellerZip;
	/** 거주지 주소. */
	private String sellerAddress;
	/** 거주지 상세주소. */
	private String sellerDetailAddress;
	/** (외국인)도시. */
	private String sellerCity;
	/** (외국인)주. */
	private String sellerState;
	/** 국가코드. */
	@NotNull
	@NotBlank
	private String sellerCountry;
	/** 언어코드. */
	@NotNull
	@NotBlank
	private String sellerLanguage;
	/** 식별코드. */
	@NotNull
	@NotBlank
	private String isForeign;
	/** 실명인증 여부. */
	private String isRealName;
	/** 실명인증 일시 */
	private String realNameDate;
	/** CI. */
	private String sellerCI;
	/** DI. */
	private String sellerDI;
	/** 실명인증수단 코드. */
	@NotNull
	@NotBlank
	private String realNameMethod;
	/** 실명인증사이트 코드. */
	private String realNameSystemId;
	/** 회사명. */
	private String sellerCompany;
	/** 사업자 등록번호. */
	private String sellerBizNumber;
	/** 고객 응대 전화번호 국가코드. */
	private String customerPhoneCountry;
	/** 고객 전화번호. */
	private String customerPhone;
	/** 고객 응대 이메일. */
	private String customerEmail;
	/** 법인등록번호. */
	private String sellerBizCorpNumber;

	/** 약관 정보들. */
	private List<Agreement> agreementList;
	/** 보안질문 정보들. */
	private List<PwReminde> PWReminderList;

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

	public String getSellerPW() {
		return this.sellerPW;
	}

	public void setSellerPW(String sellerPW) {
		this.sellerPW = sellerPW;
	}

	public String getSellerTelecom() {
		return this.sellerTelecom;
	}

	public void setSellerTelecom(String sellerTelecom) {
		this.sellerTelecom = sellerTelecom;
	}

	public String getSellerPhoneCountry() {
		return this.sellerPhoneCountry;
	}

	public void setSellerPhoneCountry(String sellerPhoneCountry) {
		this.sellerPhoneCountry = sellerPhoneCountry;
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

	public String getSellerEmail() {
		return this.sellerEmail;
	}

	public void setSellerEmail(String sellerEmail) {
		this.sellerEmail = sellerEmail;
	}

	public String getIsRecvEmail() {
		return this.isRecvEmail;
	}

	public void setIsRecvEmail(String isRecvEmail) {
		this.isRecvEmail = isRecvEmail;
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

	public String getSellerSex() {
		return this.sellerSex;
	}

	public void setSellerSex(String sellerSex) {
		this.sellerSex = sellerSex;
	}

	public String getSellerBirthDay() {
		return this.sellerBirthDay;
	}

	public void setSellerBirthDay(String sellerBirthDay) {
		this.sellerBirthDay = sellerBirthDay;
	}

	public String getSellerSSNumber() {
		return this.sellerSSNumber;
	}

	public void setSellerSSNumber(String sellerSSNumber) {
		this.sellerSSNumber = sellerSSNumber;
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

	public String getSellerCity() {
		return this.sellerCity;
	}

	public void setSellerCity(String sellerCity) {
		this.sellerCity = sellerCity;
	}

	public String getSellerState() {
		return this.sellerState;
	}

	public void setSellerState(String sellerState) {
		this.sellerState = sellerState;
	}

	public String getSellerCountry() {
		return this.sellerCountry;
	}

	public void setSellerCountry(String sellerCountry) {
		this.sellerCountry = sellerCountry;
	}

	public String getSellerLanguage() {
		return this.sellerLanguage;
	}

	public void setSellerLanguage(String sellerLanguage) {
		this.sellerLanguage = sellerLanguage;
	}

	public String getIsForeign() {
		return this.isForeign;
	}

	public void setIsForeign(String isForeign) {
		this.isForeign = isForeign;
	}

	public String getIsRealName() {
		return this.isRealName;
	}

	public void setIsRealName(String isRealName) {
		this.isRealName = isRealName;
	}

	public String getRealNameDate() {
		return this.realNameDate;
	}

	public void setRealNameDate(String realNameDate) {
		this.realNameDate = realNameDate;
	}

	public String getSellerCI() {
		return this.sellerCI;
	}

	public void setSellerCI(String sellerCI) {
		this.sellerCI = sellerCI;
	}

	public String getSellerDI() {
		return this.sellerDI;
	}

	public void setSellerDI(String sellerDI) {
		this.sellerDI = sellerDI;
	}

	public String getRealNameMethod() {
		return this.realNameMethod;
	}

	public void setRealNameMethod(String realNameMethod) {
		this.realNameMethod = realNameMethod;
	}

	public String getRealNameSystemId() {
		return this.realNameSystemId;
	}

	public void setRealNameSystemId(String realNameSystemId) {
		this.realNameSystemId = realNameSystemId;
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

	public String getCustomerPhoneCountry() {
		return this.customerPhoneCountry;
	}

	public void setCustomerPhoneCountry(String customerPhoneCountry) {
		this.customerPhoneCountry = customerPhoneCountry;
	}

	public String getCustomerPhone() {
		return this.customerPhone;
	}

	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}

	public String getCustomerEmail() {
		return this.customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	public String getSellerBizCorpNumber() {
		return this.sellerBizCorpNumber;
	}

	public void setSellerBizCorpNumber(String sellerBizCorpNumber) {
		this.sellerBizCorpNumber = sellerBizCorpNumber;
	}

	public List<Agreement> getAgreementList() {
		return this.agreementList;
	}

	public void setAgreementList(List<Agreement> agreementList) {
		this.agreementList = agreementList;
	}

	public List<PwReminde> getPWReminderList() {
		return this.PWReminderList;
	}

	public void setPWReminderList(List<PwReminde> pWReminderList) {
		this.PWReminderList = pWReminderList;
	}

	/**
	 * 보안 질문
	 * 
	 * Updated on : 2014. 1. 16. Updated by : 김경복, 부르칸.
	 */
	public class PwReminde {
		/** 보안질문 답변. */
		@NotBlank
		@NotNull
		private String answerString;

		/** 보안질문 ID. */
		@NotBlank
		@NotNull
		private String questionID;

		/** 보안질문 직접입력 값. */
		@NotBlank
		@NotNull
		private String questionMessage;

		public String getAnswerString() {
			return this.answerString;
		}

		public void setAnswerString(String answerString) {
			this.answerString = answerString;
		}

		public String getQuestionID() {
			return this.questionID;
		}

		public void setQuestionID(String questionID) {
			this.questionID = questionID;
		}

		public String getQuestionMessage() {
			return this.questionMessage;
		}

		public void setQuestionMessage(String questionMessage) {
			this.questionMessage = questionMessage;
		}
	}

	/**
	 * 이용약관
	 * 
	 * Updated on : 2014. 1. 16. Updated by : 김경복, 부르칸.
	 */
	public class Agreement {
		/** 이용약관 ID. */
		@NotBlank
		@NotNull
		private String extraAgreementID;

		/** 이용약관 동의 여부. */
		@NotBlank
		@NotNull
		private String isExtraAgreement;

		/** 이용약관 버전. */
		private String extraAgreementVersion;

		public String getExtraAgreementID() {
			return this.extraAgreementID;
		}

		public void setExtraAgreementID(String extraAgreementID) {
			this.extraAgreementID = extraAgreementID;
		}

		public String getIsExtraAgreement() {
			return this.isExtraAgreement;
		}

		public void setIsExtraAgreement(String isExtraAgreement) {
			this.isExtraAgreement = isExtraAgreement;
		}

		public String getExtraAgreementVersion() {
			return this.extraAgreementVersion;
		}

		public void setExtraAgreementVersion(String extraAgreementVersion) {
			this.extraAgreementVersion = extraAgreementVersion;
		}
	}
}
