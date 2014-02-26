/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.payment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.purchase.client.payment.sci.CheckBillingSCI;
import com.skplanet.storeplatform.purchase.client.payment.vo.CheckBillingForSktCarrierScReq;
import com.skplanet.storeplatform.purchase.client.payment.vo.CheckBillingForSktCarrierScRes;
import com.skplanet.storeplatform.sac.purchase.payment.vo.CheckBillingForSktCarrierSacParam;
import com.skplanet.storeplatform.sac.purchase.payment.vo.CheckBillingForSktCarrierSacResult;

/**
 * Class 설명
 * 
 * Updated on : 2014. 2. 26. Updated by : nTels_cswoo81, nTels.
 */
@Service
public class CheckBillingServiceImpl implements CheckBillingService {

	@Autowired
	private CheckBillingSCI checkBillingSCI;

	@Override
	public CheckBillingForSktCarrierSacResult checkBillingForSktCarrier(
			CheckBillingForSktCarrierSacParam checkBillingForSktCarrierSacParam) {

		CheckBillingForSktCarrierScReq checkBillingForSktCarrierScReq = new CheckBillingForSktCarrierScReq();

		checkBillingForSktCarrierScReq.setTenantId(checkBillingForSktCarrierSacParam.getTenantId());
		checkBillingForSktCarrierScReq.setSystemId(checkBillingForSktCarrierSacParam.getSystemId());
		checkBillingForSktCarrierScReq.setUserKey(checkBillingForSktCarrierSacParam.getUserKey());
		checkBillingForSktCarrierScReq.setDeviceKey(checkBillingForSktCarrierSacParam.getDeviceKey());

		CheckBillingForSktCarrierScRes checkBillingForSktCarrierScRes = this.checkBillingSCI
				.checkBillingForSktCarrier(checkBillingForSktCarrierScReq);

		CheckBillingForSktCarrierSacResult checkBillingForSktCarrierSacResult = new CheckBillingForSktCarrierSacResult();
		checkBillingForSktCarrierSacResult.setMonth(checkBillingForSktCarrierScRes.getMonth());
		checkBillingForSktCarrierSacResult.setTotAmt(checkBillingForSktCarrierScRes.getTotAmt());

		return checkBillingForSktCarrierSacResult;

	}

}
