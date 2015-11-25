/**
 * 
 */
package com.skplanet.storeplatform.member.client.user.sci.vo;

import java.io.Serializable;
import java.util.Date;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * TB_US_USERMBR_DEVICE_SET 테이블 VO
 * 
 * Updated on : 2015. 6. 1. Updated by : incross_cmlee80. I-S Plus.
 */
public class UserMbrDeviceSetInfo extends CommonInfo implements Serializable {

	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	private String tenantId;
	private String insdUserMbrNo;
	private String insdDeviceId;
	private String pinNo;
	private int authCnt;
	private String authLockYn;
	private String autoUpdtYn;
	private String autoUpdtSetClsf;
	private String wiFiAutoUpdtYn;
	private String loginLockYn;
	private String apprPinReinsYn;
	private String adultContentsLockYn;
	private Date regDt;
	private String regId;
	private Date updDt;
	private String updId;
	private String systemId;
	private String adultContentsLimtYn;
	private String wiFiAutoDwldYn;

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
	 * @return the pinNo
	 */
	public String getPinNo() {
		return this.pinNo;
	}

	/**
	 * @param pinNo
	 *            the pinNo to set
	 */
	public void setPinNo(String pinNo) {
		this.pinNo = pinNo;
	}

	/**
	 * @return the authCnt
	 */
	public int getAuthCnt() {
		return this.authCnt;
	}

	/**
	 * @param authCnt
	 *            the authCnt to set
	 */
	public void setAuthCnt(int authCnt) {
		this.authCnt = authCnt;
	}

	/**
	 * @return the authLockYn
	 */
	public String getAuthLockYn() {
		return this.authLockYn;
	}

	/**
	 * @param authLockYn
	 *            the authLockYn to set
	 */
	public void setAuthLockYn(String authLockYn) {
		this.authLockYn = authLockYn;
	}

	/**
	 * @return the autoUpdtYn
	 */
	public String getAutoUpdtYn() {
		return this.autoUpdtYn;
	}

	/**
	 * @param autoUpdtYn
	 *            the autoUpdtYn to set
	 */
	public void setAutoUpdtYn(String autoUpdtYn) {
		this.autoUpdtYn = autoUpdtYn;
	}

	/**
	 * @return the autoUpdtSetClsf
	 */
	public String getAutoUpdtSetClsf() {
		return this.autoUpdtSetClsf;
	}

	/**
	 * @param autoUpdtSetClsf
	 *            the autoUpdtSetClsf to set
	 */
	public void setAutoUpdtSetClsf(String autoUpdtSetClsf) {
		this.autoUpdtSetClsf = autoUpdtSetClsf;
	}

	/**
	 * @return the wiFiAutoUpdtYn
	 */
	public String getWiFiAutoUpdtYn() {
		return this.wiFiAutoUpdtYn;
	}

	/**
	 * @param wiFiAutoUpdtYn
	 *            the wiFiAutoUpdtYn to set
	 */
	public void setWiFiAutoUpdtYn(String wiFiAutoUpdtYn) {
		this.wiFiAutoUpdtYn = wiFiAutoUpdtYn;
	}

	/**
	 * @return the loginLockYn
	 */
	public String getLoginLockYn() {
		return this.loginLockYn;
	}

	/**
	 * @param loginLockYn
	 *            the loginLockYn to set
	 */
	public void setLoginLockYn(String loginLockYn) {
		this.loginLockYn = loginLockYn;
	}

	/**
	 * @return the apprPinReinsYn
	 */
	public String getApprPinReinsYn() {
		return this.apprPinReinsYn;
	}

	/**
	 * @param apprPinReinsYn
	 *            the apprPinReinsYn to set
	 */
	public void setApprPinReinsYn(String apprPinReinsYn) {
		this.apprPinReinsYn = apprPinReinsYn;
	}

	/**
	 * @return the adultContentsLockYn
	 */
	public String getAdultContentsLockYn() {
		return this.adultContentsLockYn;
	}

	/**
	 * @param adultContentsLockYn
	 *            the adultContentsLockYn to set
	 */
	public void setAdultContentsLockYn(String adultContentsLockYn) {
		this.adultContentsLockYn = adultContentsLockYn;
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
	 * @return the adultContentsLimtYn
	 */
	public String getAdultContentsLimtYn() {
		return this.adultContentsLimtYn;
	}

	/**
	 * @param adultContentsLimtYn
	 *            the adultContentsLimtYn to set
	 */
	public void setAdultContentsLimtYn(String adultContentsLimtYn) {
		this.adultContentsLimtYn = adultContentsLimtYn;
	}

	/**
	 * @return the wiFiAutoDwldYn
	 */
	public String getWiFiAutoDwldYn() {
		return this.wiFiAutoDwldYn;
	}

	/**
	 * @param wiFiAutoDwldYn
	 *            the wiFiAutoDwldYn to set
	 */
	public void setWiFiAutoDwldYn(String wiFiAutoDwldYn) {
		this.wiFiAutoDwldYn = wiFiAutoDwldYn;
	}

}
