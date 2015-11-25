package com.skplanet.storeplatform.sac.client.member.vo.user;

import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.AgreementInfo;

/**
 * [REQUEST] Store 약관 동의 등록
 * 
 * Updated on : 2014. 1. 6. Updated by : 심대진, 다모아 솔루션.
 */
public class CreateTermsAgreementReq extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/**
	 * 사용자 Key.
	 */
	@NotEmpty
	private String userKey = "";

	/**
	 * 약관 동의 리스트
	 */
	@NotEmpty
	private List<AgreementInfo> agreementList = null;

	/**
	 * @return String : userKey
	 */
	public String getUserKey() {
		return this.userKey;
	}

	/**
	 * @param userKey
	 *            String : the userKey to set
	 */
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	/**
	 * @return List<AgreementInfo> : agreementList
	 */
	public List<AgreementInfo> getAgreementList() {
		return this.agreementList;
	}

	/**
	 * @param agreementList
	 *            List<AgreementInfo> : the agreementList to set
	 */
	public void setAgreementList(List<AgreementInfo> agreementList) {
		this.agreementList = agreementList;
	}

}
