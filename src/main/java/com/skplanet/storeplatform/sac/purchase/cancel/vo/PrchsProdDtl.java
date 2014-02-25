/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.cancel.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * Class 설명
 * 
 * Updated on : 2014. 2. 25. Updated by : nTels_cswoo81, nTels.
 */
public class PrchsProdDtl extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private String deviceId;
	private String prodId;
	private String appId;
	private String shoppingProdYn;

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
	 * @return the appId
	 */
	public String getAppId() {
		return this.appId;
	}

	/**
	 * @param appId
	 *            the appId to set
	 */
	public void setAppId(String appId) {
		this.appId = appId;
	}

	/**
	 * @return the shoppingProdYn
	 */
	public String getShoppingProdYn() {
		return this.shoppingProdYn;
	}

	/**
	 * @param shoppingProdYn
	 *            the shoppingProdYn to set
	 */
	public void setShoppingProdYn(String shoppingProdYn) {
		this.shoppingProdYn = shoppingProdYn;
	}

}
