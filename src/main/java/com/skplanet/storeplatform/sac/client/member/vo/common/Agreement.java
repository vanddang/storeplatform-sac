package com.skplanet.storeplatform.sac.client.member.vo.common;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 약관동의 정보
 * 
 * Updated on : 2014. 1. 7. Updated by : 한서구, 부르칸.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class Agreement extends CommonInfo {

	private static final long serialVersionUID = 1L;
	private String extraAgreementId;
	private String isExtraAgreement;
	private String extraAgreementVersion;

	public String getExtraAgreementId() {
		return this.extraAgreementId;
	}

	public void setExtraAgreementId(String extraAgreementId) {
		this.extraAgreementId = extraAgreementId;
	}

	public String getIsExtraAgreement() {
		return this.isExtraAgreement;
	}

	public void setIsExtraAgreement(String isExtraAgreement) {
		this.isExtraAgreement = isExtraAgreement;
	}

	/**
	 * @return the extraAgreementVersion
	 */
	public String getExtraAgreementVersion() {
		return this.extraAgreementVersion;
	}

	/**
	 * @param extraAgreementVersion
	 *            the extraAgreementVersion to set
	 */
	public void setExtraAgreementVersion(String extraAgreementVersion) {
		this.extraAgreementVersion = extraAgreementVersion;
	}

}
