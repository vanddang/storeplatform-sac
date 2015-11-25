package com.skplanet.storeplatform.sac.client.member.vo.common;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 판매자 회원 약관 정보.
 * 
 * Updated on : 2015. 3. 6. Updated by : Rejoice, Burkhan
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class SellerAgreement extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/** 약관 동의 코드. */
	private String clauseAgreementId;
	/** 약관 버전. */
	private String clauseAgreementVersion;
	/** 약관 동의 여부. */
	private String isClauseAgreement;
	/** 필수동의 여부. */
	private String isClauseMandatory;

	/**
	 * @return the clauseAgreementId
	 */
	public String getClauseAgreementId() {
		return this.clauseAgreementId;
	}

	/**
	 * @param clauseAgreementId
	 *            the clauseAgreementId to set
	 */
	public void setClauseAgreementId(String clauseAgreementId) {
		this.clauseAgreementId = clauseAgreementId;
	}

	/**
	 * @return the clauseAgreementVersion
	 */
	public String getClauseAgreementVersion() {
		return this.clauseAgreementVersion;
	}

	/**
	 * @param clauseAgreementVersion
	 *            the clauseAgreementVersion to set
	 */
	public void setClauseAgreementVersion(String clauseAgreementVersion) {
		this.clauseAgreementVersion = clauseAgreementVersion;
	}

	/**
	 * @return the isClauseAgreement
	 */
	public String getIsClauseAgreement() {
		return this.isClauseAgreement;
	}

	/**
	 * @param isClauseAgreement
	 *            the isClauseAgreement to set
	 */
	public void setIsClauseAgreement(String isClauseAgreement) {
		this.isClauseAgreement = isClauseAgreement;
	}

	/**
	 * @return the isClauseMandatory
	 */
	public String getIsClauseMandatory() {
		return this.isClauseMandatory;
	}

	/**
	 * @param isClauseMandatory
	 *            the isClauseMandatory to set
	 */
	public void setIsClauseMandatory(String isClauseMandatory) {
		this.isClauseMandatory = isClauseMandatory;
	}

}
