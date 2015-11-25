/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.purchase.client.cancel.vo;

import com.skplanet.storeplatform.purchase.client.common.vo.AutoPrchs;
import com.skplanet.storeplatform.purchase.client.common.vo.PurchaseCommonScRes;

/**
 * 자동결제상태정보 응답 VO.
 * 
 * Updated on : 2014. 5. 15. Updated by : nTels_cswoo81, nTels.
 */
public class AutoPaymentScRes extends PurchaseCommonScRes {

	private static final long serialVersionUID = 1L;

	private AutoPrchs autoPrchs;

	/**
	 * @return the autoPrchs
	 */
	public AutoPrchs getAutoPrchs() {
		return this.autoPrchs;
	}

	/**
	 * @param autoPrchs
	 *            the autoPrchs to set
	 */
	public void setAutoPrchs(AutoPrchs autoPrchs) {
		this.autoPrchs = autoPrchs;
	}

}
