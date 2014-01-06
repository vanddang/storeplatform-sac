package com.skplanet.storeplatform.sac.client.member.vo.seller;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

@JsonSerialize(include = Inclusion.NON_NULL)
public class Agreement extends CommonInfo {

	private static final long serialVersionUID = 1L;
	private String extraAgreementId;
	private String isExtraAgreement;

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

}
