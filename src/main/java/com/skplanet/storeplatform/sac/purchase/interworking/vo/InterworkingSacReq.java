/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.interworking.vo;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 구매후처리 연동(인터파크,씨네21).
 * 
 * Updated on : 2014. 2. 6. Updated by : 조용진, NTELS.
 */
public class InterworkingSacReq extends CommonInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotNull
	@NotEmpty
	private String tenantId;
	@NotNull
	@NotEmpty
	private String systemId;
	@NotNull
	@NotEmpty
	private String prchsId;
	@NotNull
	@NotEmpty
	private String userKey;
	private String deviceKey;
	@NotNull
	@NotEmpty
	private String sellermbrNo;
	@NotNull
	@NotEmpty
	private String prodId;
	@NotNull
	@NotEmpty
	private String compContentsId;
	@NotNull
	@NotEmpty
	private String prchsDt;
	private Integer prodAmt;
	private String prchsCancelDt;
	private String fileMakeYn;

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
	 * @return the userKey
	 */
	public String getUserKey() {
		return this.userKey;
	}

	/**
	 * @param userKey
	 *            the userKey to set
	 */
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	/**
	 * @return the deviceKey
	 */
	public String getDeviceKey() {
		return this.deviceKey;
	}

	/**
	 * @param deviceKey
	 *            the deviceKey to set
	 */
	public void setDeviceKey(String deviceKey) {
		this.deviceKey = deviceKey;
	}

	/**
	 * @return the sellermbrNo
	 */
	public String getSellermbrNo() {
		return this.sellermbrNo;
	}

	/**
	 * @param sellermbrNo
	 *            the sellermbrNo to set
	 */
	public void setSellermbrNo(String sellermbrNo) {
		this.sellermbrNo = sellermbrNo;
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
	 * @return the compContentsId
	 */
	public String getCompContentsId() {
		return this.compContentsId;
	}

	/**
	 * @param compContentsId
	 *            the compContentsId to set
	 */
	public void setCompContentsId(String compContentsId) {
		this.compContentsId = compContentsId;
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
	 * @return the prodAmt
	 */
	public Integer getProdAmt() {
		return this.prodAmt;
	}

	/**
	 * @param prodAmt
	 *            the prodAmt to set
	 */
	public void setProdAmt(Integer prodAmt) {
		this.prodAmt = prodAmt;
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

}
