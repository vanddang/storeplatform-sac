package com.skplanet.storeplatform.sac.client.member.vo.common;

import org.hibernate.validator.constraints.NotEmpty;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 약관동의 정보
 * 
 * Updated on : 2014. 1. 3. Updated by : 심대진, 다모아 솔루션.
 */
public class AgreementInfo extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/**
	 * 약관 동의 코드
	 */
	@NotEmpty(message = "필수 파라미터 입니다.")
	private String extraAgreementId;

	/**
	 * 약관 버전
	 */
	private String extraAgreementVersion;

	/**
	 * 약관 동의 여부
	 */
	@NotEmpty(message = "필수 파라미터 입니다.")
	private String isExtraAgreement;

	public String getExtraAgreementId() {
		return this.extraAgreementId;
	}

	public void setExtraAgreementId(String extraAgreementId) {
		this.extraAgreementId = extraAgreementId;
	}

	public String getExtraAgreementVersion() {
		return this.extraAgreementVersion;
	}

	public void setExtraAgreementVersion(String extraAgreementVersion) {
		this.extraAgreementVersion = extraAgreementVersion;
	}

	public String getIsExtraAgreement() {
		return this.isExtraAgreement;
	}

	public void setIsExtraAgreement(String isExtraAgreement) {
		this.isExtraAgreement = isExtraAgreement;
	}

}
