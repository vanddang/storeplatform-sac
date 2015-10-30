package com.skplanet.storeplatform.member.client.user.sci.vo;

import java.io.Serializable;
import java.util.Date;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * TB_US_MBR_AUTH 테이블 VO
 * 
 * Updated on : 2015. 6. 1. Updated by : incross_cmlee80. I-S Plus.
 */
public class UserMbrAuthInfo extends CommonInfo implements Serializable {

	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	private int authSeq;
	private String ci;
	private String di;
	private String authMtdCd;
	private String mnoCd;
	private String wilsTelNo;
	private String birth;
	private String sex;
	private String mbrNm;
	private String systemId;
	private Date authDt;
	private Date updDt;
	private String mbrClsfCd;
	private String tenantId;
	private String insdUserMbrNo;
	private String insdSellerMbrNo;
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
	 * @return the di
	 */
	public String getDi() {
		return this.di;
	}

	/**
	 * @param di
	 *            the di to set
	 */
	public void setDi(String di) {
		this.di = di;
	}

	/**
	 * @return the authMtdCd
	 */
	public String getAuthMtdCd() {
		return this.authMtdCd;
	}

	/**
	 * @param authMtdCd
	 *            the authMtdCd to set
	 */
	public void setAuthMtdCd(String authMtdCd) {
		this.authMtdCd = authMtdCd;
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
	 * @return the wilsTelNo
	 */
	public String getWilsTelNo() {
		return this.wilsTelNo;
	}

	/**
	 * @param wilsTelNo
	 *            the wilsTelNo to set
	 */
	public void setWilsTelNo(String wilsTelNo) {
		this.wilsTelNo = wilsTelNo;
	}

	/**
	 * @return the birth
	 */
	public String getBirth() {
		return this.birth;
	}

	/**
	 * @param birth
	 *            the birth to set
	 */
	public void setBirth(String birth) {
		this.birth = birth;
	}

	/**
	 * @return the sex
	 */
	public String getSex() {
		return this.sex;
	}

	/**
	 * @param sex
	 *            the sex to set
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}

	/**
	 * @return the mbrNm
	 */
	public String getMbrNm() {
		return this.mbrNm;
	}

	/**
	 * @param mbrNm
	 *            the mbrNm to set
	 */
	public void setMbrNm(String mbrNm) {
		this.mbrNm = mbrNm;
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
	 * @return the authDt
	 */
	public Date getAuthDt() {
		return this.authDt;
	}

	/**
	 * @param authDt
	 *            the authDt to set
	 */
	public void setAuthDt(Date authDt) {
		this.authDt = authDt;
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
	 * @return the mbrClsfCd
	 */
	public String getMbrClsfCd() {
		return this.mbrClsfCd;
	}

	/**
	 * @param mbrClsfCd
	 *            the mbrClsfCd to set
	 */
	public void setMbrClsfCd(String mbrClsfCd) {
		this.mbrClsfCd = mbrClsfCd;
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
	 * @return the insdSellerMbrNo
	 */
	public String getInsdSellerMbrNo() {
		return this.insdSellerMbrNo;
	}

	/**
	 * @param insdSellerMbrNo
	 *            the insdSellerMbrNo to set
	 */
	public void setInsdSellerMbrNo(String insdSellerMbrNo) {
		this.insdSellerMbrNo = insdSellerMbrNo;
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
