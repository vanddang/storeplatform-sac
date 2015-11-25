package com.skplanet.storeplatform.member.client.user.sci.vo;

import java.io.Serializable;
import java.util.Date;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * TB_US_USERMBR_OCB 테이블 VO
 * 
 * Updated on : 2015. 6. 1. Updated by : incross_cmlee80. I-S Plus.
 */
public class UserMbrOcbInfo extends CommonInfo implements Serializable {

	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	private String tenantId;
	private String insdUserMbrNo;
	private String ocbAuthMtdCd;
	private String useYn;
	private String ocbNo;
	private Date useStartDt;
	private Date useEndDt;
	private String regId;
	private Date regDt;
	private String updId;
	private Date updDt;

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
	 * @return the ocbAuthMtdCd
	 */
	public String getOcbAuthMtdCd() {
		return this.ocbAuthMtdCd;
	}

	/**
	 * @param ocbAuthMtdCd
	 *            the ocbAuthMtdCd to set
	 */
	public void setOcbAuthMtdCd(String ocbAuthMtdCd) {
		this.ocbAuthMtdCd = ocbAuthMtdCd;
	}

	/**
	 * @return the useYn
	 */
	public String getUseYn() {
		return this.useYn;
	}

	/**
	 * @param useYn
	 *            the useYn to set
	 */
	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}

	/**
	 * @return the ocbNo
	 */
	public String getOcbNo() {
		return this.ocbNo;
	}

	/**
	 * @param ocbNo
	 *            the ocbNo to set
	 */
	public void setOcbNo(String ocbNo) {
		this.ocbNo = ocbNo;
	}

	/**
	 * @return the useStartDt
	 */
	public Date getUseStartDt() {
		return this.useStartDt;
	}

	/**
	 * @param useStartDt
	 *            the useStartDt to set
	 */
	public void setUseStartDt(Date useStartDt) {
		this.useStartDt = useStartDt;
	}

	/**
	 * @return the useEndDt
	 */
	public Date getUseEndDt() {
		return this.useEndDt;
	}

	/**
	 * @param useEndDt
	 *            the useEndDt to set
	 */
	public void setUseEndDt(Date useEndDt) {
		this.useEndDt = useEndDt;
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

}
