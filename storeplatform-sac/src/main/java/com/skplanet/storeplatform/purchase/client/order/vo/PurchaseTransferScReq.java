/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.purchase.client.order.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * 구매내역 이관 요청 VO
 * 
 * Updated on : 2015. 2. 5. Updated by : 양주원, nTels.
 */
public class PurchaseTransferScReq extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private String tenantId;
	private String marketDeviceKey;
	private String systemId;
	private String statusCd;
	private String trcResultCd;

	private String prchsDt;
	private String marketPrchsId;
	private String marketProdId;
	private String cancelDt;
	private int prchsCount;

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
	 * @return the marketDeviceKey
	 */
	public String getMarketDeviceKey() {
		return this.marketDeviceKey;
	}

	/**
	 * @param marketDeviceKey
	 *            the marketDeviceKey to set
	 */
	public void setMarketDeviceKey(String marketDeviceKey) {
		this.marketDeviceKey = marketDeviceKey;
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
	 * @return the statusCd
	 */
	public String getStatusCd() {
		return this.statusCd;
	}

	/**
	 * @param statusCd
	 *            the statusCd to set
	 */
	public void setStatusCd(String statusCd) {
		this.statusCd = statusCd;
	}

	/**
	 * @return the trcResultCd
	 */
	public String getTrcResultCd() {
		return this.trcResultCd;
	}

	/**
	 * @param trcResultCd
	 *            the trcResultCd to set
	 */
	public void setTrcResultCd(String trcResultCd) {
		this.trcResultCd = trcResultCd;
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
	 * @return the marketPrchsId
	 */
	public String getMarketPrchsId() {
		return this.marketPrchsId;
	}

	/**
	 * @param marketPrchsId
	 *            the marketPrchsId to set
	 */
	public void setMarketPrchsId(String marketPrchsId) {
		this.marketPrchsId = marketPrchsId;
	}

	/**
	 * @return the marketProdId
	 */
	public String getMarketProdId() {
		return this.marketProdId;
	}

	/**
	 * @param marketProdId
	 *            the marketProdId to set
	 */
	public void setMarketProdId(String marketProdId) {
		this.marketProdId = marketProdId;
	}

	/**
	 * @return the cancelDt
	 */
	public String getCancelDt() {
		return this.cancelDt;
	}

	/**
	 * @param cancelDt
	 *            the cancelDt to set
	 */
	public void setCancelDt(String cancelDt) {
		this.cancelDt = cancelDt;
	}

	/**
	 * @return the prchsCount
	 */
	public int getPrchsCount() {
		return this.prchsCount;
	}

	/**
	 * @param prchsCount
	 *            the prchsCount to set
	 */
	public void setPrchsCount(int prchsCount) {
		this.prchsCount = prchsCount;
	}

}
