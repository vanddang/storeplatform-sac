package com.skplanet.storeplatform.sac.client.member.vo.seller;

import java.util.List;

import javax.validation.constraints.Pattern;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 2.2.10. 판매자회원 기본정보 수정 [REQUEST]
 * 
 * Updated on : 2014. 1. 24. Updated by : 김경복, 부르칸
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class ModifyInformationSacReq extends CommonInfo {

	private static final long serialVersionUID = -6272645047957208675L;

	/** 판매자 key. */
	@NotBlank
	private String sellerKey;
	/** 인증키. */
	@NotBlank
	private String sessionKey;
	/** 이동통신사. */
	private String sellerTelecom;
	/** 전화번호 국가코드. */
	private String sellerPhoneCountry;
	/** 전화번호. */
	private String sellerPhone;
	/** SMS 수신 여부. */
	private String isRecvSMS;
	/** 이메일수신 여부. */
	private String isRecvEmail;
	/** 판매자 이름. */
	private String sellerName;
	/** 판매자 성별. */
	private String sellerSex;
	/** 판매자 생년월일. */
	// @Size(max = 8)
	private String sellerBirthDay;
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
	@NotBlank
	private String sellerCountry;
	/** 언어코드. */
	@NotBlank
	private String sellerLanguage;
	/** 식별코드. */
	@NotBlank
	@Pattern(regexp = "^Y|^N")
	private String isDomestic;
	/** 회사명. */
	private String sellerCompany;
	/** 사업자 등록번호. */
	private String sellerBizNumber;
	/** 담당자 이메일. */
	private String customerEmail;
	/** 담당자 명. */
	private String charger;
	/** 유선 국가번호. */
	private String cordedTelephoneCountry;
	/** 유선 전화번호. */
	private String cordedTelephone;
	/** 대표전화 국가번호. */
	private String repPhoneArea;
	/** 대표전화번호. */
	private String repPhone;
	/** 대표 Email. */
	private String repEmail;
	/** 웹사이트. */
	private String website;

	/** 보안질문 정보들. */
	private List<PwReminder> pwReminderList;

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
	 * @return the sellerTelecom
	 */
	public String getSellerTelecom() {
		return this.sellerTelecom;
	}

	/**
	 * @param sellerTelecom
	 *            the sellerTelecom to set
	 */
	public void setSellerTelecom(String sellerTelecom) {
		this.sellerTelecom = sellerTelecom;
	}

	/**
	 * @return the sellerPhoneCountry
	 */
	public String getSellerPhoneCountry() {
		return this.sellerPhoneCountry;
	}

	/**
	 * @param sellerPhoneCountry
	 *            the sellerPhoneCountry to set
	 */
	public void setSellerPhoneCountry(String sellerPhoneCountry) {
		this.sellerPhoneCountry = sellerPhoneCountry;
	}

	/**
	 * @return the sellerPhone
	 */
	public String getSellerPhone() {
		return this.sellerPhone;
	}

	/**
	 * @param sellerPhone
	 *            the sellerPhone to set
	 */
	public void setSellerPhone(String sellerPhone) {
		this.sellerPhone = sellerPhone;
	}

	/**
	 * @return the isRecvSMS
	 */
	public String getIsRecvSMS() {
		return this.isRecvSMS;
	}

	/**
	 * @param isRecvSMS
	 *            the isRecvSMS to set
	 */
	public void setIsRecvSMS(String isRecvSMS) {
		this.isRecvSMS = isRecvSMS;
	}

	/**
	 * @return the isRecvEmail
	 */
	public String getIsRecvEmail() {
		return this.isRecvEmail;
	}

	/**
	 * @param isRecvEmail
	 *            the isRecvEmail to set
	 */
	public void setIsRecvEmail(String isRecvEmail) {
		this.isRecvEmail = isRecvEmail;
	}

	/**
	 * @return the sellerName
	 */
	public String getSellerName() {
		return this.sellerName;
	}

	/**
	 * @param sellerName
	 *            the sellerName to set
	 */
	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	/**
	 * @return the sellerSex
	 */
	public String getSellerSex() {
		return this.sellerSex;
	}

	/**
	 * @param sellerSex
	 *            the sellerSex to set
	 */
	public void setSellerSex(String sellerSex) {
		this.sellerSex = sellerSex;
	}

	/**
	 * @return the sellerBirthDay
	 */
	public String getSellerBirthDay() {
		return this.sellerBirthDay;
	}

	/**
	 * @param sellerBirthDay
	 *            the sellerBirthDay to set
	 */
	public void setSellerBirthDay(String sellerBirthDay) {
		this.sellerBirthDay = sellerBirthDay;
	}

	/**
	 * @return the sellerZip
	 */
	public String getSellerZip() {
		return this.sellerZip;
	}

	/**
	 * @param sellerZip
	 *            the sellerZip to set
	 */
	public void setSellerZip(String sellerZip) {
		this.sellerZip = sellerZip;
	}

	/**
	 * @return the sellerAddress
	 */
	public String getSellerAddress() {
		return this.sellerAddress;
	}

	/**
	 * @param sellerAddress
	 *            the sellerAddress to set
	 */
	public void setSellerAddress(String sellerAddress) {
		this.sellerAddress = sellerAddress;
	}

	/**
	 * @return the sellerDetailAddress
	 */
	public String getSellerDetailAddress() {
		return this.sellerDetailAddress;
	}

	/**
	 * @param sellerDetailAddress
	 *            the sellerDetailAddress to set
	 */
	public void setSellerDetailAddress(String sellerDetailAddress) {
		this.sellerDetailAddress = sellerDetailAddress;
	}

	/**
	 * @return the sellerCity
	 */
	public String getSellerCity() {
		return this.sellerCity;
	}

	/**
	 * @param sellerCity
	 *            the sellerCity to set
	 */
	public void setSellerCity(String sellerCity) {
		this.sellerCity = sellerCity;
	}

	/**
	 * @return the sellerState
	 */
	public String getSellerState() {
		return this.sellerState;
	}

	/**
	 * @param sellerState
	 *            the sellerState to set
	 */
	public void setSellerState(String sellerState) {
		this.sellerState = sellerState;
	}

	/**
	 * @return the sellerCountry
	 */
	public String getSellerCountry() {
		return this.sellerCountry;
	}

	/**
	 * @param sellerCountry
	 *            the sellerCountry to set
	 */
	public void setSellerCountry(String sellerCountry) {
		this.sellerCountry = sellerCountry;
	}

	/**
	 * @return the sellerLanguage
	 */
	public String getSellerLanguage() {
		return this.sellerLanguage;
	}

	/**
	 * @param sellerLanguage
	 *            the sellerLanguage to set
	 */
	public void setSellerLanguage(String sellerLanguage) {
		this.sellerLanguage = sellerLanguage;
	}

	/**
	 * @return the isDomestic
	 */
	public String getIsDomestic() {
		return this.isDomestic;
	}

	/**
	 * @param isDomestic
	 *            the isDomestic to set
	 */
	public void setIsDomestic(String isDomestic) {
		this.isDomestic = isDomestic;
	}

	/**
	 * @return the sellerCompany
	 */
	public String getSellerCompany() {
		return this.sellerCompany;
	}

	/**
	 * @param sellerCompany
	 *            the sellerCompany to set
	 */
	public void setSellerCompany(String sellerCompany) {
		this.sellerCompany = sellerCompany;
	}

	/**
	 * @return the sellerBizNumber
	 */
	public String getSellerBizNumber() {
		return this.sellerBizNumber;
	}

	/**
	 * @param sellerBizNumber
	 *            the sellerBizNumber to set
	 */
	public void setSellerBizNumber(String sellerBizNumber) {
		this.sellerBizNumber = sellerBizNumber;
	}

	/**
	 * @return the customerEmail
	 */
	public String getCustomerEmail() {
		return this.customerEmail;
	}

	/**
	 * @param customerEmail
	 *            the customerEmail to set
	 */
	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	/**
	 * @return the charger
	 */
	public String getCharger() {
		return this.charger;
	}

	/**
	 * @param charger
	 *            the charger to set
	 */
	public void setCharger(String charger) {
		this.charger = charger;
	}

	/**
	 * @return the cordedTelephoneCountry
	 */
	public String getCordedTelephoneCountry() {
		return this.cordedTelephoneCountry;
	}

	/**
	 * @param cordedTelephoneCountry
	 *            the cordedTelephoneCountry to set
	 */
	public void setCordedTelephoneCountry(String cordedTelephoneCountry) {
		this.cordedTelephoneCountry = cordedTelephoneCountry;
	}

	/**
	 * @return the cordedTelephone
	 */
	public String getCordedTelephone() {
		return this.cordedTelephone;
	}

	/**
	 * @param cordedTelephone
	 *            the cordedTelephone to set
	 */
	public void setCordedTelephone(String cordedTelephone) {
		this.cordedTelephone = cordedTelephone;
	}

	/**
	 * @return the repPhoneArea
	 */
	public String getRepPhoneArea() {
		return this.repPhoneArea;
	}

	/**
	 * @param repPhoneArea
	 *            the repPhoneArea to set
	 */
	public void setRepPhoneArea(String repPhoneArea) {
		this.repPhoneArea = repPhoneArea;
	}

	/**
	 * @return the repPhone
	 */
	public String getRepPhone() {
		return this.repPhone;
	}

	/**
	 * @param repPhone
	 *            the repPhone to set
	 */
	public void setRepPhone(String repPhone) {
		this.repPhone = repPhone;
	}

	/**
	 * @return the repEmail
	 */
	public String getRepEmail() {
		return this.repEmail;
	}

	/**
	 * @param repEmail
	 *            the repEmail to set
	 */
	public void setRepEmail(String repEmail) {
		this.repEmail = repEmail;
	}

	/**
	 * @return the website
	 */
	public String getWebsite() {
		return this.website;
	}

	/**
	 * @param website
	 *            the website to set
	 */
	public void setWebsite(String website) {
		this.website = website;
	}

	/**
	 * @return the pwReminderList
	 */
	public List<PwReminder> getPwReminderList() {
		return this.pwReminderList;
	}

	/**
	 * @param pwReminderList
	 *            the pwReminderList to set
	 */
	public void setPwReminderList(List<PwReminder> pwReminderList) {
		this.pwReminderList = pwReminderList;
	}

	/**
	 * Calss 설명
	 * 
	 * Updated on : 2014. 3. 13. Updated by : Rejoice, Burkhan
	 */
	public static class PwReminder {
		/** 보안질문 답변. */
		private String answerString;

		/** 보안질문 ID. */
		private String questionID;

		/** 보안질문 직접입력 값. */
		private String questionMessage;

		/**
		 * @return the answerString
		 */
		public String getAnswerString() {
			return this.answerString;
		}

		/**
		 * @param answerString
		 *            the answerString to set
		 */
		public void setAnswerString(String answerString) {
			this.answerString = answerString;
		}

		/**
		 * @return the questionID
		 */
		public String getQuestionID() {
			return this.questionID;
		}

		/**
		 * @param questionID
		 *            the questionID to set
		 */
		public void setQuestionID(String questionID) {
			this.questionID = questionID;
		}

		/**
		 * @return the questionMessage
		 */
		public String getQuestionMessage() {
			return this.questionMessage;
		}

		/**
		 * @param questionMessage
		 *            the questionMessage to set
		 */
		public void setQuestionMessage(String questionMessage) {
			this.questionMessage = questionMessage;
		}

	}

}
