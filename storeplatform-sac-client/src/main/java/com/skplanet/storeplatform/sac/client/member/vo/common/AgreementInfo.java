package com.skplanet.storeplatform.sac.client.member.vo.common;

import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 약관동의 정보
 * 
 * Updated on : 2014. 1. 3. Updated by : 심대진, 다모아 솔루션.
 */
public class AgreementInfo extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/**
	 * 약관 동의 코드.
	 */
	@NotBlank
	private String extraAgreementId;

	/**
	 * 약관 버전.
	 */
	private String extraAgreementVersion;

	/**
	 * 약관 동의 여부.
	 */
	@NotBlank
	private String isExtraAgreement;

	/**
	 * 필수 약관 여부.
	 */
	private String mandAgreeYn;

	/**
	 * 약관동의 URL
	 */
	private String extraAgreementURL;

	/**
	 * @return String : extraAgreementId
	 */
	public String getExtraAgreementId() {
		return this.extraAgreementId;
	}

	/**
	 * @param extraAgreementId
	 *            String : the extraAgreementId to set
	 */
	public void setExtraAgreementId(String extraAgreementId) {
		this.extraAgreementId = extraAgreementId;
	}

	/**
	 * @return String : extraAgreementVersion
	 */
	public String getExtraAgreementVersion() {
		return this.extraAgreementVersion;
	}

	/**
	 * @param extraAgreementVersion
	 *            String : the extraAgreementVersion to set
	 */
	public void setExtraAgreementVersion(String extraAgreementVersion) {
		this.extraAgreementVersion = extraAgreementVersion;
	}

	/**
	 * @return String : isExtraAgreement
	 */
	public String getIsExtraAgreement() {
		return this.isExtraAgreement;
	}

	/**
	 * @param isExtraAgreement
	 *            String : the isExtraAgreement to set
	 */
	public void setIsExtraAgreement(String isExtraAgreement) {
		this.isExtraAgreement = isExtraAgreement;
	}

	/**
	 * @return String : mandAgreeYn
	 */
	public String getMandAgreeYn() {
		return this.mandAgreeYn;
	}

	/**
	 * @param mandAgreeYn
	 *            String : the mandAgreeYn to set
	 */
	public void setMandAgreeYn(String mandAgreeYn) {
		this.mandAgreeYn = mandAgreeYn;
	}

	/**
	 * @return extraAgreementURL
	 */
	public String getExtraAgreementURL() {
		return this.extraAgreementURL;
	}

	/**
	 * @param extraAgreementURL
	 *            String
	 */
	public void setExtraAgreementURL(String extraAgreementURL) {
		this.extraAgreementURL = extraAgreementURL;
	}

}
