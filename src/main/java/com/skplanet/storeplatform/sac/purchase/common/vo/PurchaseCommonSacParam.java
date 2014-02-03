/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.common.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.purchase.common.vo.PurchaseCommonSacReq;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * 구매 공통 Parameter VO.
 * 
 * Updated on : 2014. 1. 16. Updated by : nTels_cswoo81, nTels.
 */
public class PurchaseCommonSacParam extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private String tenantId;
	private String systemId;
	private String userKey;
	private String deviceKey;
	private String langCd;

	public PurchaseCommonSacParam() {

	}

	public PurchaseCommonSacParam(SacRequestHeader sacRequestHeader) {
		this.tenantId = sacRequestHeader.getTenantHeader().getTenantId();
		this.systemId = sacRequestHeader.getTenantHeader().getSystemId();
	}

	public PurchaseCommonSacParam(SacRequestHeader sacRequestHeader, PurchaseCommonSacReq purchaseCommonReq) {
		this.tenantId = sacRequestHeader.getTenantHeader().getTenantId();
		this.systemId = sacRequestHeader.getTenantHeader().getSystemId();
		this.userKey = purchaseCommonReq.getUserKey();
		this.deviceKey = purchaseCommonReq.getDeviceKey();
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
	 * @return the langCd
	 */
	public String getLangCd() {
		return this.langCd;
	}

	/**
	 * @param langCd
	 *            the langCd to set
	 */
	public void setLangCd(String langCd) {
		this.langCd = langCd;
	}

}
