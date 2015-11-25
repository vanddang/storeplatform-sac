package com.skplanet.storeplatform.member.client.user.sci.vo;

import java.io.Serializable;
import java.util.Date;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * TB_US_USERMBR_MANG_ITEM_PTCR 테이블 VO
 * 
 * Updated on : 2015. 6. 1. Updated by : incross_cmlee80. I-S Plus.
 */
public class UserMbrMangItemPtcrInfo extends CommonInfo implements Serializable {

	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	private String tenantId;
	private String insdUserMbrNo;
	private String mangItemCd;
	private String regResultValue;
	private Date regDt;
	private String regId;
	private Date updDt;
	private String updId;
	private String dataTranFlag;
	private String userMbrNo;
	private String mbrId;

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
	 * @return the mangItemCd
	 */
	public String getMangItemCd() {
		return this.mangItemCd;
	}

	/**
	 * @param mangItemCd
	 *            the mangItemCd to set
	 */
	public void setMangItemCd(String mangItemCd) {
		this.mangItemCd = mangItemCd;
	}

	/**
	 * @return the regResultValue
	 */
	public String getRegResultValue() {
		return this.regResultValue;
	}

	/**
	 * @param regResultValue
	 *            the regResultValue to set
	 */
	public void setRegResultValue(String regResultValue) {
		this.regResultValue = regResultValue;
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
	 * @return the regId
	 */
	public String getRegId() {
		return this.regId;
	}

	/**
	 * @param regId
	 *            the regId to set
	 */
	public void setRegId(String regId) {
		this.regId = regId;
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
	 * @return the updId
	 */
	public String getUpdId() {
		return this.updId;
	}

	/**
	 * @param updId
	 *            the updId to set
	 */
	public void setUpdId(String updId) {
		this.updId = updId;
	}

	/**
	 * @return the dataTranFlag
	 */
	public String getDataTranFlag() {
		return this.dataTranFlag;
	}

	/**
	 * @param dataTranFlag
	 *            the dataTranFlag to set
	 */
	public void setDataTranFlag(String dataTranFlag) {
		this.dataTranFlag = dataTranFlag;
	}

	/**
	 * @return the userMbrNo
	 */
	public String getUserMbrNo() {
		return this.userMbrNo;
	}

	/**
	 * @param userMbrNo
	 *            the userMbrNo to set
	 */
	public void setUserMbrNo(String userMbrNo) {
		this.userMbrNo = userMbrNo;
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

}
