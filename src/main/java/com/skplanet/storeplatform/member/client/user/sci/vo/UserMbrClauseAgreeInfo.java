package com.skplanet.storeplatform.member.client.user.sci.vo;

import java.io.Serializable;
import java.util.Date;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * TB_US_USERMBR_CLAUSE_AGREE 테이블 VO
 * 
 * Updated on : 2015. 6. 1. Updated by : incross_cmlee80. I-S Plus.
 */
public class UserMbrClauseAgreeInfo extends CommonInfo implements Serializable {

	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	private String tenantId;
	private String insdUserMbrNo;
	private String clauseId;
	private String agreeYn;
	private Date regDt;
	private Date updDt;
	private String mandAgreeYn;
	private String clauseVer;

	/**
	 * @return the tenantId
	 */
	public String getTenantId() {
		return this.tenantId;
	}

	/**
	 * @param tenantId
	 *            the tenantId to set
	 */
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	/**
	 * @return the insdUserMbrNo
	 */
	public String getInsdUserMbrNo() {
		return this.insdUserMbrNo;
	}

	/**
	 * @param insdUserMbrNo
	 *            the insdUserMbrNo to set
	 */
	public void setInsdUserMbrNo(String insdUserMbrNo) {
		this.insdUserMbrNo = insdUserMbrNo;
	}

	/**
	 * @return the clauseId
	 */
	public String getClauseId() {
		return this.clauseId;
	}

	/**
	 * @param clauseId
	 *            the clauseId to set
	 */
	public void setClauseId(String clauseId) {
		this.clauseId = clauseId;
	}

	/**
	 * @return the agreeYn
	 */
	public String getAgreeYn() {
		return this.agreeYn;
	}

	/**
	 * @param agreeYn
	 *            the agreeYn to set
	 */
	public void setAgreeYn(String agreeYn) {
		this.agreeYn = agreeYn;
	}

	/**
	 * @return the regDt
	 */
	public Date getRegDt() {
		return this.regDt;
	}

	/**
	 * @param regDt
	 *            the regDt to set
	 */
	public void setRegDt(Date regDt) {
		this.regDt = regDt;
	}

	/**
	 * @return the updDt
	 */
	public Date getUpdDt() {
		return this.updDt;
	}

	/**
	 * @param updDt
	 *            the updDt to set
	 */
	public void setUpdDt(Date updDt) {
		this.updDt = updDt;
	}

	/**
	 * @return the mandAgreeYn
	 */
	public String getMandAgreeYn() {
		return this.mandAgreeYn;
	}

	/**
	 * @param mandAgreeYn
	 *            the mandAgreeYn to set
	 */
	public void setMandAgreeYn(String mandAgreeYn) {
		this.mandAgreeYn = mandAgreeYn;
	}

	/**
	 * @return the clauseVer
	 */
	public String getClauseVer() {
		return this.clauseVer;
	}

	/**
	 * @param clauseVer
	 *            the clauseVer to set
	 */
	public void setClauseVer(String clauseVer) {
		this.clauseVer = clauseVer;
	}

}
