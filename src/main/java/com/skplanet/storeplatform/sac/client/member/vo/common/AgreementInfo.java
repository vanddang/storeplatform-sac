package com.skplanet.storeplatform.sac.client.member.vo.common;

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
	private String extraAgreementId;

	/**
	 * 약관 버전
	 */
	private String extraAgreementVersion;

	/**
	 * 약관 동의 여부
	 */
	private String isExtraAgreement;

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

}
