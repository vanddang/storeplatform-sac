/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.purchase.client.interworking.vo;

import java.io.Serializable;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 구매후처리 연동(인터파크,씨네21).
 * 
 * Updated on : 2014. 2. 6. Updated by : 조용진, NTELS.
 */
public class InterworkingScReq extends CommonInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	private String tenantId;
	private String systemId;
	private String prchsId;
	private String insdUsermbrNo;
	private String insdDeviceId;
	private String prchsDt;
	private String prchsCancelDt;
	private String fileMakeYn;
	private String transClasCd;
	private String transClasValue;
	private String prodId;
	private Double prodAmt;
	private String compCid;

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
	 * @return the prchsId
	 */
	public String getPrchsId() {
		return this.prchsId;
	}

	/**
	 * @param prchsId
	 *            the prchsId to set
	 */
	public void setPrchsId(String prchsId) {
		this.prchsId = prchsId;
	}

	/**
	 * @return the insdUsermbrNo
	 */
	public String getInsdUsermbrNo() {
		return this.insdUsermbrNo;
	}

	/**
	 * @param insdUsermbrNo
	 *            the insdUsermbrNo to set
	 */
	public void setInsdUsermbrNo(String insdUsermbrNo) {
		this.insdUsermbrNo = insdUsermbrNo;
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
	 * @return the prchsDt
	 */
	public String getPrchsDt() {
		return this.prchsDt;
	}

	/**
	 * @param prchsDt
	 *            the prchsDt to set
	 */
	public void setPrchsDt(String prchsDt) {
		this.prchsDt = prchsDt;
	}

	/**
	 * @return the prchsCancelDt
	 */
	public String getPrchsCancelDt() {
		return this.prchsCancelDt;
	}

	/**
	 * @param prchsCancelDt
	 *            the prchsCancelDt to set
	 */
	public void setPrchsCancelDt(String prchsCancelDt) {
		this.prchsCancelDt = prchsCancelDt;
	}

	/**
	 * @return the fileMakeYn
	 */
	public String getFileMakeYn() {
		return this.fileMakeYn;
	}

	/**
	 * @param fileMakeYn
	 *            the fileMakeYn to set
	 */
	public void setFileMakeYn(String fileMakeYn) {
		this.fileMakeYn = fileMakeYn;
	}

	/**
	 * @return the transClasCd
	 */
	public String getTransClasCd() {
		return this.transClasCd;
	}

	/**
	 * @param transClasCd
	 *            the transClasCd to set
	 */
	public void setTransClasCd(String transClasCd) {
		this.transClasCd = transClasCd;
	}

	/**
	 * @return the transClasValue
	 */
	public String getTransClasValue() {
		return this.transClasValue;
	}

	/**
	 * @param transClasValue
	 *            the transClasValue to set
	 */
	public void setTransClasValue(String transClasValue) {
		this.transClasValue = transClasValue;
	}

	/**
	 * @return the prodId
	 */
	public String getProdId() {
		return this.prodId;
	}

	/**
	 * @param prodId
	 *            the prodId to set
	 */
	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

	/**
	 * @return the prodAmt
	 */
	public Double getProdAmt() {
		return this.prodAmt;
	}

	/**
	 * @param prodAmt
	 *            the prodAmt to set
	 */
	public void setProdAmt(Double prodAmt) {
		this.prodAmt = prodAmt;
	}

	/**
	 * @return the compCid
	 */
	public String getCompCid() {
		return this.compCid;
	}

	/**
	 * @param compCid
	 *            the compCid to set
	 */
	public void setCompCid(String compCid) {
		this.compCid = compCid;
	}

}
