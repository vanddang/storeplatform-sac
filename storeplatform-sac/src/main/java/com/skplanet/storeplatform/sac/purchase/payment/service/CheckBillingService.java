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

import com.skplanet.storeplatform.sac.purchase.payment.vo.CheckBillingForSktCarrierSacParam;
import com.skplanet.storeplatform.sac.purchase.payment.vo.CheckBillingForSktCarrierSacResult;

/**
 * Class 설명
 * 
 * Updated on : 2014. 2. 26. Updated by : nTels_cswoo81, nTels.
 */
public interface CheckBillingService {

	/**
	 * 
	 * <pre>
	 * checkBillingForSktCarrier.
	 * </pre>
	 * 
	 * @param checkBillingForSktCarrierSacParam
	 *            checkBillingForSktCarrierSacParam
	 * @return CheckBillingForSktCarrierSacResult
	 */
	CheckBillingForSktCarrierSacResult checkBillingForSktCarrier(
			CheckBillingForSktCarrierSacParam checkBillingForSktCarrierSacParam);

}
