/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.purchase.history.sci;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.skplanet.storeplatform.framework.integration.bean.LocalSCI;
import com.skplanet.storeplatform.purchase.client.history.sci.AutoPaymentCancelSCI;
import com.skplanet.storeplatform.purchase.client.history.vo.AutoPaymentCancelScReq;
import com.skplanet.storeplatform.purchase.client.history.vo.AutoPaymentCancelScRes;
import com.skplanet.storeplatform.purchase.history.service.AutoPaymentCancelScService;

/**
 * 구매 컴포넌트 인터페이스 컨트롤러
 * 
 * Updated on : 2014-01-16 Updated by : 조용진, 엔텔스.
 */
@LocalSCI
public class AutoPaymentCancelSCIController implements AutoPaymentCancelSCI {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AutoPaymentCancelScService autoPaymentCancelScService;

	/**
	 * 자동결제 해지예약/해지예약취소/해지 요청.
	 * 
	 * @param autoPaymentCancelScReq
	 *            요청정보
	 * @return AutoPaymentCancelScRes
	 */
	@Override
	public AutoPaymentCancelScRes updateReservation(AutoPaymentCancelScReq autoPaymentCancelScReq) {
		this.logger.debug("PRCHS,AutoPaymentCancelSCIController,SC,REQ,{}", autoPaymentCancelScReq);
		return this.autoPaymentCancelScService.updateReservation(autoPaymentCancelScReq);
	}

}
