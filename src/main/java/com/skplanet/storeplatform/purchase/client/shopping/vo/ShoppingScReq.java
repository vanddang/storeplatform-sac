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
public class ShoppingScReq extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private String tenantId;
	private String prchsId;
	private String cpnPublishCd;
	private String cpnUseStatusCd;
	private String systemId;

	/**
	 * @return the prchsId
	 */
	public String getPrchsId() {
		return this.prchsId;
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
	 * @param prchsId
	 *            the prchsId to set
	 */
	public void setPrchsId(String prchsId) {
		this.prchsId = prchsId;
	}

	/**
	 * @return the cpnPublishCd
	 */
	public String getCpnPublishCd() {
		return this.cpnPublishCd;
	}

	/**
	 * @param cpnPublishCd
	 *            the cpnPublishCd to set
	 */
	public void setCpnPublishCd(String cpnPublishCd) {
		this.cpnPublishCd = cpnPublishCd;
	}

	/**
	 * @return the cpnUseStatusCd
	 */
	public String getCpnUseStatusCd() {
		return this.cpnUseStatusCd;
	}

	/**
	 * @param cpnUseStatusCd
	 *            the cpnUseStatusCd to set
	 */
	public void setCpnUseStatusCd(String cpnUseStatusCd) {
		this.cpnUseStatusCd = cpnUseStatusCd;
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
