package com.skplanet.storeplatform.member.client.user.sci.vo;

import java.io.Serializable;
import java.util.Date;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * TB_US_USERMBR_LGL_AGENT 테이블 VO
 * 
 * Updated on : 2015. 6. 1. Updated by : incross_cmlee80. I-S Plus.
 */
public class UserMbrLglAgentInfo extends CommonInfo implements Serializable {

	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	private int authSeq;
	private String lglAgentAuthMtdCd;
	private String lglAgentFlNm;
	private String lglAgentRshp;
	private Date lglAgentAgreeDt;
	private String lglAgentEmail;
	private String lglAgentBirth;
	private String mnoCd;
	private String lglAgentHpNo;
	private String ci;
	private Date regDt;
	private String tenantId;
	private String insdUserMbrNo;
	private String systemId;
	private String ictryYn;

	/**
	 * @return the authSeq
	 */
	public int getAuthSeq() {
		return this.authSeq;
	}

	/**
	 * @param authSeq
	 *            the authSeq to set
	 */
	public void setAuthSeq(int authSeq) {
		this.authSeq = authSeq;
	}

	/**
	 * @return the lglAgentAuthMtdCd
	 */
	public String getLglAgentAuthMtdCd() {
		return this.lglAgentAuthMtdCd;
	}

	/**
	 * @param lglAgentAuthMtdCd
	 *            the lglAgentAuthMtdCd to set
	 */
	public void setLglAgentAuthMtdCd(String lglAgentAuthMtdCd) {
		this.lglAgentAuthMtdCd = lglAgentAuthMtdCd;
	}

	/**
	 * @return the lglAgentFlNm
	 */
	public String getLglAgentFlNm() {
		return this.lglAgentFlNm;
	}

	/**
	 * @param lglAgentFlNm
	 *            the lglAgentFlNm to set
	 */
	public void setLglAgentFlNm(String lglAgentFlNm) {
		this.lglAgentFlNm = lglAgentFlNm;
	}

	/**
	 * @return the lglAgentRshp
	 */
	public String getLglAgentRshp() {
		return this.lglAgentRshp;
	}

	/**
	 * @param lglAgentRshp
	 *            the lglAgentRshp to set
	 */
	public void setLglAgentRshp(String lglAgentRshp) {
		this.lglAgentRshp = lglAgentRshp;
	}

	/**
	 * @return the lglAgentAgreeDt
	 */
	public Date getLglAgentAgreeDt() {
		return this.lglAgentAgreeDt;
	}

	/**
	 * @param lglAgentAgreeDt
	 *            the lglAgentAgreeDt to set
	 */
	public void setLglAgentAgreeDt(Date lglAgentAgreeDt) {
		this.lglAgentAgreeDt = lglAgentAgreeDt;
	}

	/**
	 * @return the lglAgentEmail
	 */
	public String getLglAgentEmail() {
		return this.lglAgentEmail;
	}

	/**
	 * @param lglAgentEmail
	 *            the lglAgentEmail to set
	 */
	public void setLglAgentEmail(String lglAgentEmail) {
		this.lglAgentEmail = lglAgentEmail;
	}

	/**
	 * @return the lglAgentBirth
	 */
	public String getLglAgentBirth() {
		return this.lglAgentBirth;
	}

	/**
	 * @param lglAgentBirth
	 *            the lglAgentBirth to set
	 */
	public void setLglAgentBirth(String lglAgentBirth) {
		this.lglAgentBirth = lglAgentBirth;
	}

	/**
	 * @return the mnoCd
	 */
	public String getMnoCd() {
		return this.mnoCd;
	}

	/**
	 * @param mnoCd
	 *            the mnoCd to set
	 */
	public void setMnoCd(String mnoCd) {
		this.mnoCd = mnoCd;
	}

	/**
	 * @return the lglAgentHpNo
	 */
	public String getLglAgentHpNo() {
		return this.lglAgentHpNo;
	}

	/**
	 * @param lglAgentHpNo
	 *            the lglAgentHpNo to set
	 */
	public void setLglAgentHpNo(String lglAgentHpNo) {
		this.lglAgentHpNo = lglAgentHpNo;
	}

	/**
	 * @return the ci
	 */
	public String getCi() {
		return this.ci;
	}

	/**
	 * @param ci
	 *            the ci to set
	 */
	public void setCi(String ci) {
		this.ci = ci;
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
	 * @return the systemId
	 */
	public String getSystemId() {
		return this.systemId;
	}

	/**
	 * @param systemId
	 *            the systemId to set
	 */
	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	/**
	 * @return the ictryYn
	 */
	public String getIctryYn() {
		return this.ictryYn;
	}

	/**
	 * @param ictryYn
	 *            the ictryYn to set
	 */
	public void setIctryYn(String ictryYn) {
		this.ictryYn = ictryYn;
	}

}
