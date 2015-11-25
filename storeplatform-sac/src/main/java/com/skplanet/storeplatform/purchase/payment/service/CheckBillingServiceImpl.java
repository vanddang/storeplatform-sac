/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.purchase.payment.service;

import com.skplanet.storeplatform.purchase.constant.PurchaseCDConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.purchase.client.payment.vo.CheckBillingForSktCarrierScReq;
import com.skplanet.storeplatform.purchase.client.payment.vo.CheckBillingForSktCarrierScRes;

/**
 * Class 설명
 * 
 * Updated on : 2014. 2. 26. Updated by : nTels_cswoo81, nTels.
 */
@Service
public class CheckBillingServiceImpl implements CheckBillingService {

	@Autowired
	@Qualifier("scPurchase")
	private CommonDAO scPurchaseDAO;

	@Override
	public CheckBillingForSktCarrierScRes checkBillingForSktCarrier(
			CheckBillingForSktCarrierScReq checkBillingForSktCarrierScReq) {

		checkBillingForSktCarrierScReq.setPaymentMtdCd(PurchaseCDConstants.PAYMENT_METHOD_SKT_CARRIER);
		checkBillingForSktCarrierScReq.setPaymentStatusCd(PurchaseCDConstants.PRCHS_STATUS_COMPT);
		checkBillingForSktCarrierScReq.setInAppProdCd(PurchaseCDConstants.TENANT_PRODUCT_GROUP_IAP);

		return this.scPurchaseDAO.queryForObject("Payment.checkBillingForSktCarrier", checkBillingForSktCarrierScReq,
				CheckBillingForSktCarrierScRes.class);

	}
}
