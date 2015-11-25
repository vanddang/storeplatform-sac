/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.purchase.payment.sci;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;

import com.skplanet.storeplatform.framework.integration.bean.LocalSCI;
import com.skplanet.storeplatform.purchase.client.payment.sci.CheckBillingSCI;
import com.skplanet.storeplatform.purchase.client.payment.vo.CheckBillingForSktCarrierScReq;
import com.skplanet.storeplatform.purchase.client.payment.vo.CheckBillingForSktCarrierScRes;
import com.skplanet.storeplatform.purchase.payment.service.CheckBillingService;

/**
 * Class 설명
 * 
 * Updated on : 2014. 2. 26. Updated by : nTels_cswoo81, nTels.
 */
@LocalSCI
public class CheckBillingSCIController implements CheckBillingSCI {

	@Autowired
	private CheckBillingService checkBillingService;

	@Override
	public CheckBillingForSktCarrierScRes checkBillingForSktCarrier(
			@Validated CheckBillingForSktCarrierScReq checkBillingForSktCarrierScReq) {

		return this.checkBillingService.checkBillingForSktCarrier(checkBillingForSktCarrierScReq);

	}

}
