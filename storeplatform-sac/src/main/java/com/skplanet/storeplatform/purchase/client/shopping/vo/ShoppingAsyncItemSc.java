/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.purchase.client.shopping.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * <pre>
 * 쿠폰 발급 비동기 응답
 * </pre>
 * 
 * Created on : 2014-01-02 Created by : nTels_yjw, nTels.
 */
public class ShoppingAsyncItemSc extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private String prchsId;
	private Integer prchsDtlId;
	private String availStartdate;
	private String availEnddate;
	private String mdn;
	private String mdn2;

	private String itemCode;
	private String publishCode;
	private String shippingUrl;;
	private String extraData;

	private String systemId;
	private String tenantId;
	private String prchsStatusCd;
	private String cancelReqPathCd;

	/**
	 * @return the itemCode
	 */
	public String getItemCode() {
		return this.itemCode;
	}

	/**
	 * @param itemCode
	 *            the itemCode to set
	 */
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	/**
	 * @return the publishCode
	 */
	public String getPublishCode() {
		return this.publishCode;
	}

	/**
	 * @param publishCode
	 *            the publishCode to set
	 */
	public void setPublishCode(String publishCode) {
		this.publishCode = publishCode;
	}

	/**
	 * @return the shippingUrl
	 */
	public String getShippingUrl() {
		return this.shippingUrl;
	}

	/**
	 * @param shippingUrl
	 *            the shippingUrl to set
	 */
	public void setShippingUrl(String shippingUrl) {
		this.shippingUrl = shippingUrl;
	}

	/**
	 * @return the extraData
	 */
	public String getExtraData() {
		return this.extraData;
	}

	/**
	 * @param extraData
	 *            the extraData to set
	 */
	public void setExtraData(String extraData) {
		this.extraData = extraData;
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
	 * @return the availStartdate
	 */
	public String getAvailStartdate() {
		return this.availStartdate;
	}

	/**
	 * @param availStartdate
	 *            the availStartdate to set
	 */
	public void setAvailStartdate(String availStartdate) {
		this.availStartdate = availStartdate;
	}

	/**
	 * @return the availEnddate
	 */
	public String getAvailEnddate() {
		return this.availEnddate;
	}

	/**
	 * @param availEnddate
	 *            the availEnddate to set
	 */
	public void setAvailEnddate(String availEnddate) {
		this.availEnddate = availEnddate;
	}

	/**
	 * @return the prchsDtlId
	 */
	public Integer getPrchsDtlId() {
		return this.prchsDtlId;
	}

	/**
	 * @param prchsDtlId
	 *            the prchsDtlId to set
	 */
	public void setPrchsDtlId(Integer prchsDtlId) {
		this.prchsDtlId = prchsDtlId;
	}

	/**
	 * @return the mdn
	 */
	public String getMdn() {
		return this.mdn;
	}

	/**
	 * @param mdn
	 *            the mdn to set
	 */
	public void setMdn(String mdn) {
		this.mdn = mdn;
	}

	/**
	 * @return the mdn2
	 */
	public String getMdn2() {
		return this.mdn2;
	}

	/**
	 * @param mdn2
	 *            the mdn2 to set
	 */
	public void setMdn2(String mdn2) {
		this.mdn2 = mdn2;
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
	 * @return the prchsStatusCd
	 */
	public String getPrchsStatusCd() {
		return this.prchsStatusCd;
	}

	/**
	 * @param prchsStatusCd
	 *            the prchsStatusCd to set
	 */
	public void setPrchsStatusCd(String prchsStatusCd) {
		this.prchsStatusCd = prchsStatusCd;
	}

	/**
	 * @return the cancelReqPathCd
	 */
	public String getCancelReqPathCd() {
		return this.cancelReqPathCd;
	}

	/**
	 * @param cancelReqPathCd
	 *            the cancelReqPathCd to set
	 */
	public void setCancelReqPathCd(String cancelReqPathCd) {
		this.cancelReqPathCd = cancelReqPathCd;
	}

}
