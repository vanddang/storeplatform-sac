package com.skplanet.storeplatform.member.client.user.sci.vo;

import java.io.Serializable;
import java.util.Date;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * TB_US_USERMBR_ONEID 테이블 VO
 * 
 * Updated on : 2015. 6. 1. Updated by : incross_cmlee80. I-S Plus.
 */
public class UserMbrOneIdInfo extends CommonInfo implements Serializable {

	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	private String tenantId;
	private String insdUserMbrNo;
	private String intgSvcNo;
	private String intgMbrCaseCd;
	private Date regDt;
	private Date updDt;
	private String mbrEntryStatusCd;
	private String loginStatusCd;
	private String ofauthStopStatusCd;
	private String mbrId;
	private String idctYn;
	private Date entryDt;
	private String mbrCaseCd;
	private String entrySvcSiteCd;
	private String intgPontYn;
	private String ciYn;

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
	 * @return the intgSvcNo
	 */
	public String getIntgSvcNo() {
		return this.intgSvcNo;
	}

	/**
	 * @param intgSvcNo
	 *            the intgSvcNo to set
	 */
	public void setIntgSvcNo(String intgSvcNo) {
		this.intgSvcNo = intgSvcNo;
	}

	/**
	 * @return the intgMbrCaseCd
	 */
	public String getIntgMbrCaseCd() {
		return this.intgMbrCaseCd;
	}

	/**
	 * @param intgMbrCaseCd
	 *            the intgMbrCaseCd to set
	 */
	public void setIntgMbrCaseCd(String intgMbrCaseCd) {
		this.intgMbrCaseCd = intgMbrCaseCd;
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
	 * @return the mbrEntryStatusCd
	 */
	public String getMbrEntryStatusCd() {
		return this.mbrEntryStatusCd;
	}

	/**
	 * @param mbrEntryStatusCd
	 *            the mbrEntryStatusCd to set
	 */
	public void setMbrEntryStatusCd(String mbrEntryStatusCd) {
		this.mbrEntryStatusCd = mbrEntryStatusCd;
	}

	/**
	 * @return the loginStatusCd
	 */
	public String getLoginStatusCd() {
		return this.loginStatusCd;
	}

	/**
	 * @param loginStatusCd
	 *            the loginStatusCd to set
	 */
	public void setLoginStatusCd(String loginStatusCd) {
		this.loginStatusCd = loginStatusCd;
	}

	/**
	 * @return the ofauthStopStatusCd
	 */
	public String getOfauthStopStatusCd() {
		return this.ofauthStopStatusCd;
	}

	/**
	 * @param ofauthStopStatusCd
	 *            the ofauthStopStatusCd to set
	 */
	public void setOfauthStopStatusCd(String ofauthStopStatusCd) {
		this.ofauthStopStatusCd = ofauthStopStatusCd;
	}

	/**
	 * @return the mbrId
	 */
	public String getMbrId() {
		return this.mbrId;
	}

	/**
	 * @param mbrId
	 *            the mbrId to set
	 */
	public void setMbrId(String mbrId) {
		this.mbrId = mbrId;
	}

	/**
	 * @return the idctYn
	 */
	public String getIdctYn() {
		return this.idctYn;
	}

	/**
	 * @param idctYn
	 *            the idctYn to set
	 */
	public void setIdctYn(String idctYn) {
		this.idctYn = idctYn;
	}

	/**
	 * @return the entryDt
	 */
	public Date getEntryDt() {
		return this.entryDt;
	}

	/**
	 * @param entryDt
	 *            the entryDt to set
	 */
	public void setEntryDt(Date entryDt) {
		this.entryDt = entryDt;
	}

	/**
	 * @return the mbrCaseCd
	 */
	public String getMbrCaseCd() {
		return this.mbrCaseCd;
	}

	/**
	 * @param mbrCaseCd
	 *            the mbrCaseCd to set
	 */
	public void setMbrCaseCd(String mbrCaseCd) {
		this.mbrCaseCd = mbrCaseCd;
	}

	/**
	 * @return the entrySvcSiteCd
	 */
	public String getEntrySvcSiteCd() {
		return this.entrySvcSiteCd;
	}

	/**
	 * @param entrySvcSiteCd
	 *            the entrySvcSiteCd to set
	 */
	public void setEntrySvcSiteCd(String entrySvcSiteCd) {
		this.entrySvcSiteCd = entrySvcSiteCd;
	}

	/**
	 * @return the intgPontYn
	 */
	public String getIntgPontYn() {
		return this.intgPontYn;
	}

	/**
	 * @param intgPontYn
	 *            the intgPontYn to set
	 */
	public void setIntgPontYn(String intgPontYn) {
		this.intgPontYn = intgPontYn;
	}

	/**
	 * @return the ciYn
	 */
	public String getCiYn() {
		return this.ciYn;
	}

	/**
	 * @param ciYn
	 *            the ciYn to set
	 */
	public void setCiYn(String ciYn) {
		this.ciYn = ciYn;
	}

}
