/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.purchase.client.payment.sci;

import org.springframework.validation.annotation.Validated;

import com.skplanet.storeplatform.framework.core.proxy.SCI;
import com.skplanet.storeplatform.purchase.client.payment.vo.CheckBillingForSktCarrierScReq;
import com.skplanet.storeplatform.purchase.client.payment.vo.CheckBillingForSktCarrierScRes;

/**
 * Class 설명
 * 
 * Updated on : 2014. 2. 26. Updated by : nTels_cswoo81, nTels.
 */
@SCI
public interface CheckBillingSCI {

	/**
	 * 
	 * <pre>
	 * 이용자 별 당월 SKT후불 결제 금액 조회 요청.
	 * </pre>
	 * 
	 * @param checkBillingForSktCarrierScReq
	 * @return
	 */
	CheckBillingForSktCarrierScRes checkBillingForSktCarrier(
			@Validated CheckBillingForSktCarrierScReq checkBillingForSktCarrierScReq);

}
