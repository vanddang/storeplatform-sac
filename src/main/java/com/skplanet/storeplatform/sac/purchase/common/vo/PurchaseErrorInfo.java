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

/**
 * Class 설명
 * 
 * Updated on : 2014. 6. 27. Updated by : nTels_cswoo81, nTels.
 */
public class PurchaseErrorInfo extends CommonInfo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String errorMsg;

	/**
	 * @return the errorMsg
	 */
	public String getErrorMsg() {
		return this.errorMsg;
	}

	/**
	 * @param errorMsg
	 *            the errorMsg to set
	 */
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

}
