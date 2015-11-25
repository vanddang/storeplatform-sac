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
 * Updated on : 2014. 4. 24. Updated by : nTels_cswoo81, nTels.
 */
public class PurchaseCancelForPaymentErrorSacRes extends PurchaseCommonSacRes {

	private static final long serialVersionUID = 1L;

	private String prchsId;

	public String getPrchsId() {
		return this.prchsId;
	}

	public void setPrchsId(String prchsId) {
		this.prchsId = prchsId;
	}

}
