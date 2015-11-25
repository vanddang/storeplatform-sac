package com.skplanet.storeplatform.member.client.user.sci.vo;

import java.io.Serializable;
import java.util.Date;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * TB_US_USERMBR_DEVICE_DTL 테이블 VO
 * 
 * Updated on : 2015. 6. 1. Updated by : incross_cmlee80, I-S Plus.
 */
public class UserMbrDeviceDtlInfo extends CommonInfo implements Serializable {

	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	/** 테넌트 ID. */
	private String tenantId;

	/** 내부 사용자 회원번호. */
	private String insdUserMbrNo;

	/** 내부 기기 ID. */
	private String insdDeviceId;

	/** 기기관리항목 코드. */
	private String deviceMangItemCd;

	/** 등록 값. */
	private String regValue;

	/** 등록 일시. */
	private Date regDt;

	/** 등록 ID. */
	private String regId;

	/** 수정 일시. */
	private Date updDt;

	/** 수정 ID. */
	private String updId;

	/** 데이터 전송 플래그. */
	private String dataTranFlag;

	/** 기기 ID. */
	private String deviceId;

	/** 시스템 ID. */
	private String systemId;

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
	 * @return the deviceMangItemCd
	 */
	public String getDeviceMangItemCd() {
		return this.deviceMangItemCd;
	}

	/**
	 * @param deviceMangItemCd
	 *            the deviceMangItemCd to set
	 */
	public void setDeviceMangItemCd(String deviceMangItemCd) {
		this.deviceMangItemCd = deviceMangItemCd;
	}

	/**
	 * @return the regValue
	 */
	public String getRegValue() {
		return this.regValue;
	}

	/**
	 * @param regValue
	 *            the regValue to set
	 */
	public void setRegValue(String regValue) {
		this.regValue = regValue;
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

}
