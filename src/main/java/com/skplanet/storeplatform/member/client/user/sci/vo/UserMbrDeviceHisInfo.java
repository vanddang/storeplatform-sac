/**
 * 
 */
package com.skplanet.storeplatform.member.client.user.sci.vo;

import java.io.Serializable;
import java.util.Date;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * TB_US_USERMBR_DEVICE_HIS 테이블 VO
 * 
 * Updated on : 2015. 6. 1. Updated by : incross_cmlee80. I-S Plus.
 */
public class UserMbrDeviceHisInfo extends CommonInfo implements Serializable {

	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	private int seq;
	private String tenantId;
	private String insdUserMbrNo;
	private String insdDeviceId;
	private Date startDt;
	private Date endDt;
	private String deviceId;
	private String deviceModelCd;
	private String mnoCd;
	private String deviceAlias;
	private String repDeviceYn;
	private String deviceUseYn;
	private String authYn;
	private Date authDt;
	private Date updDt;
	private String smsRecvYn;
	private String deviceNatvId;
	private String deviceAcct;
	private String entryChnlCd;
	private Date insDt;
	private String svcMangNo;
	private Date regDt;
	private String chgCaseCd;
	private String preDeviceId;

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
	 * @return the insdDeviceId
	 */
	public String getInsdDeviceId() {
		return this.insdDeviceId;
	}

	/**
	 * @param insdDeviceId
	 *            the insdDeviceId to set
	 */
	public void setInsdDeviceId(String insdDeviceId) {
		this.insdDeviceId = insdDeviceId;
	}

	/**
	 * @return the startDt
	 */
	public Date getStartDt() {
		return this.startDt;
	}

	/**
	 * @param startDt
	 *            the startDt to set
	 */
	public void setStartDt(Date startDt) {
		this.startDt = startDt;
	}

	/**
	 * @return the endDt
	 */
	public Date getEndDt() {
		return this.endDt;
	}

	/**
	 * @param endDt
	 *            the endDt to set
	 */
	public void setEndDt(Date endDt) {
		this.endDt = endDt;
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
	 * @return the deviceModelCd
	 */
	public String getDeviceModelCd() {
		return this.deviceModelCd;
	}

	/**
	 * @param deviceModelCd
	 *            the deviceModelCd to set
	 */
	public void setDeviceModelCd(String deviceModelCd) {
		this.deviceModelCd = deviceModelCd;
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
	 * @return the deviceAlias
	 */
	public String getDeviceAlias() {
		return this.deviceAlias;
	}

	/**
	 * @param deviceAlias
	 *            the deviceAlias to set
	 */
	public void setDeviceAlias(String deviceAlias) {
		this.deviceAlias = deviceAlias;
	}

	/**
	 * @return the repDeviceYn
	 */
	public String getRepDeviceYn() {
		return this.repDeviceYn;
	}

	/**
	 * @param repDeviceYn
	 *            the repDeviceYn to set
	 */
	public void setRepDeviceYn(String repDeviceYn) {
		this.repDeviceYn = repDeviceYn;
	}

	/**
	 * @return the deviceUseYn
	 */
	public String getDeviceUseYn() {
		return this.deviceUseYn;
	}

	/**
	 * @param deviceUseYn
	 *            the deviceUseYn to set
	 */
	public void setDeviceUseYn(String deviceUseYn) {
		this.deviceUseYn = deviceUseYn;
	}

	/**
	 * @return the authYn
	 */
	public String getAuthYn() {
		return this.authYn;
	}

	/**
	 * @param authYn
	 *            the authYn to set
	 */
	public void setAuthYn(String authYn) {
		this.authYn = authYn;
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
	 * @return the smsRecvYn
	 */
	public String getSmsRecvYn() {
		return this.smsRecvYn;
	}

	/**
	 * @param smsRecvYn
	 *            the smsRecvYn to set
	 */
	public void setSmsRecvYn(String smsRecvYn) {
		this.smsRecvYn = smsRecvYn;
	}

	/**
	 * @return the deviceNatvId
	 */
	public String getDeviceNatvId() {
		return this.deviceNatvId;
	}

	/**
	 * @param deviceNatvId
	 *            the deviceNatvId to set
	 */
	public void setDeviceNatvId(String deviceNatvId) {
		this.deviceNatvId = deviceNatvId;
	}

	/**
	 * @return the deviceAcct
	 */
	public String getDeviceAcct() {
		return this.deviceAcct;
	}

	/**
	 * @param deviceAcct
	 *            the deviceAcct to set
	 */
	public void setDeviceAcct(String deviceAcct) {
		this.deviceAcct = deviceAcct;
	}

	/**
	 * @return the entryChnlCd
	 */
	public String getEntryChnlCd() {
		return this.entryChnlCd;
	}

	/**
	 * @param entryChnlCd
	 *            the entryChnlCd to set
	 */
	public void setEntryChnlCd(String entryChnlCd) {
		this.entryChnlCd = entryChnlCd;
	}

	/**
	 * @return the insDt
	 */
	public Date getInsDt() {
		return this.insDt;
	}

	/**
	 * @param insDt
	 *            the insDt to set
	 */
	public void setInsDt(Date insDt) {
		this.insDt = insDt;
	}

	/**
	 * @return the svcMangNo
	 */
	public String getSvcMangNo() {
		return this.svcMangNo;
	}

	/**
	 * @param svcMangNo
	 *            the svcMangNo to set
	 */
	public void setSvcMangNo(String svcMangNo) {
		this.svcMangNo = svcMangNo;
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
	 * @return the chgCaseCd
	 */
	public String getChgCaseCd() {
		return this.chgCaseCd;
	}

	/**
	 * @param chgCaseCd
	 *            the chgCaseCd to set
	 */
	public void setChgCaseCd(String chgCaseCd) {
		this.chgCaseCd = chgCaseCd;
	}

	/**
	 * @return the preDeviceId
	 */
	public String getPreDeviceId() {
		return this.preDeviceId;
	}

	/**
	 * @param preDeviceId
	 *            the preDeviceId to set
	 */
	public void setPreDeviceId(String preDeviceId) {
		this.preDeviceId = preDeviceId;
	}

}
