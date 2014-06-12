/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.purchase.cancel.vo;

import com.skplanet.storeplatform.sac.client.purchase.common.vo.PurchaseCommonSacRes;

/**
 * Class 설명
 * 
 * Updated on : 2014. 6. 11. Updated by : nTels_cswoo81, nTels.
 */
public class PurchaseCancelCommonSacRes extends PurchaseCommonSacRes {

	private static final long serialVersionUID = 1L;

	private String hostName;
	private String instanceName;

	/**
	 * @return the hostName
	 */
	public String getHostName() {
		return this.hostName;
	}

	/**
	 * @param hostName
	 *            the hostName to set
	 */
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	/**
	 * @return the instanceName
	 */
	public String getInstanceName() {
		return this.instanceName;
	}

	/**
	 * @param instanceName
	 *            the instanceName to set
	 */
	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}

}
