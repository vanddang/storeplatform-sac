package com.skplanet.storeplatform.member.client.user.sci.vo;

import java.io.Serializable;
import java.util.Date;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * TB_US_USERMBR_IDMBR_TRFM_CHAS 테이블 VO
 * 
 * Updated on : 2015. 6. 1. Updated by : incross_cmlee80. I-S Plus.
 */
public class UserMbrIdMbrTrfmChasInfo extends CommonInfo implements Serializable {

	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	private int seq;
	private String preTenantId;
	private String preInsdUserMbrNo;
	private String afterTenantId;
	private String afterInsdUserMbrNo;
	private Date regDt;
	private String regId;
	private String deviceId;
	private String preMbrId;
	private String afterMbrId;
	private String encDeviceId;

	/**
	 * @return the seq
	 */
	public int getSeq() {
		return this.seq;
	}

	/**
	 * @param seq
	 *            the seq to set
	 */
	public void setSeq(int seq) {
		this.seq = seq;
	}

	/**
	 * @return the preTenantId
	 */
	public String getPreTenantId() {
		return this.preTenantId;
	}

	/**
	 * @param preTenantId
	 *            the preTenantId to set
	 */
	public void setPreTenantId(String preTenantId) {
		this.preTenantId = preTenantId;
	}

	/**
	 * @return the preInsdUserMbrNo
	 */
	public String getPreInsdUserMbrNo() {
		return this.preInsdUserMbrNo;
	}

	/**
	 * @param preInsdUserMbrNo
	 *            the preInsdUserMbrNo to set
	 */
	public void setPreInsdUserMbrNo(String preInsdUserMbrNo) {
		this.preInsdUserMbrNo = preInsdUserMbrNo;
	}

	/**
	 * @return the afterTenantId
	 */
	public String getAfterTenantId() {
		return this.afterTenantId;
	}

	/**
	 * @param afterTenantId
	 *            the afterTenantId to set
	 */
	public void setAfterTenantId(String afterTenantId) {
		this.afterTenantId = afterTenantId;
	}

	/**
	 * @return the afterInsdUserMbrNo
	 */
	public String getAfterInsdUserMbrNo() {
		return this.afterInsdUserMbrNo;
	}

	/**
	 * @param afterInsdUserMbrNo
	 *            the afterInsdUserMbrNo to set
	 */
	public void setAfterInsdUserMbrNo(String afterInsdUserMbrNo) {
		this.afterInsdUserMbrNo = afterInsdUserMbrNo;
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
	 * @return the deviceId
	 */
	public String getDeviceId() {
		return this.deviceId;
	}

	/**
	 * @param deviceId
	 *            the deviceId to set
	 */
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	/**
	 * @return the preMbrId
	 */
	public String getPreMbrId() {
		return this.preMbrId;
	}

	/**
	 * @param preMbrId
	 *            the preMbrId to set
	 */
	public void setPreMbrId(String preMbrId) {
		this.preMbrId = preMbrId;
	}

	/**
	 * @return the afterMbrId
	 */
	public String getAfterMbrId() {
		return this.afterMbrId;
	}

	/**
	 * @param afterMbrId
	 *            the afterMbrId to set
	 */
	public void setAfterMbrId(String afterMbrId) {
		this.afterMbrId = afterMbrId;
	}

	/**
	 * @return the encDeviceId
	 */
	public String getEncDeviceId() {
		return this.encDeviceId;
	}

	/**
	 * @param encDeviceId
	 *            the encDeviceId to set
	 */
	public void setEncDeviceId(String encDeviceId) {
		this.encDeviceId = encDeviceId;
	}

}
