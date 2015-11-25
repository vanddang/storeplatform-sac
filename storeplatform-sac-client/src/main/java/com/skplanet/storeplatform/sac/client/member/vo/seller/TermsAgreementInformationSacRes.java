package com.skplanet.storeplatform.sac.client.member.vo.seller;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.SellerAgreement;

/**
 * 2.2.37. 판매자 약관 동의 조회 [REQUEST].
 * 
 * Updated on : 2015. 3. 4. Updated by : Rejoice, Burkhan
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class TermsAgreementInformationSacRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/** 판매자 회원 키. */
	private String sellerKey;
	/** 약관동의 리스트. */
	private List<SellerAgreement> agreementList;

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
	 * @return the agreementList
	 */
	public List<SellerAgreement> getAgreementList() {
		return this.agreementList;
	}

	/**
	 * @param agreementList
	 *            the agreementList to set
	 */
	public void setAgreementList(List<SellerAgreement> agreementList) {
		this.agreementList = agreementList;
	}

}
