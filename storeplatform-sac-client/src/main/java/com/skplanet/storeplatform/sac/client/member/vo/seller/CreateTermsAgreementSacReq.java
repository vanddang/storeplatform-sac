package com.skplanet.storeplatform.sac.client.member.vo.seller;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.SellerAgreement;

/**
 * 2.2.36. 판매자 약관 동의 등록/수정 [REQUEST].
 * 
 * Updated on : 2015. 3. 4. Updated by : Rejoice, Burkhan
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class CreateTermsAgreementSacReq extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/** 판매자 Key. */
	@NotBlank
	private String sellerKey;

	/** 약관동의 리스트. */
	@NotEmpty
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
