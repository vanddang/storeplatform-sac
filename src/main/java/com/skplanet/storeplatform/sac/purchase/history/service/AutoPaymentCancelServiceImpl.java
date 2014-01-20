/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.history.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.purchase.client.history.sci.AutoPaymentCancelSCI;
import com.skplanet.storeplatform.purchase.client.history.vo.AutoPaymentCancelRequest;
import com.skplanet.storeplatform.purchase.client.history.vo.AutoPaymentCancelResponse;

/**
 * 기구매 SAC Service 인터페이스 구현체
 * 
 * Updated on : 2013-09-01 Updated by :조용진, 엔텔스.
 */
@Service
@Transactional
public class AutoPaymentCancelServiceImpl implements AutoPaymentCancelService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AutoPaymentCancelSCI autoPaymentCancelSCI;

	/**
	 * 자동결재해지예약/예약취소/해지 SAC Service.
	 * 
	 * @param autoPaymentCancelRequest
	 *            자동결재해지예약/예약취소/해지 SAC
	 * @return AutoPaymentCancelResponse
	 */
	@Override
	public AutoPaymentCancelResponse modifyReservation(AutoPaymentCancelRequest autoPaymentCancelRequest) {
		AutoPaymentCancelResponse autoPaymentCancelResponse = new AutoPaymentCancelResponse();
		autoPaymentCancelResponse = this.autoPaymentCancelSCI.modifyReservation(autoPaymentCancelRequest);
		if (autoPaymentCancelResponse.getResultYn().equals("Y")) {
			this.logger.debug("@@@@@@자동결재해지예약/예약취소/해지 이메일 발송 처리@@@@@@");
			// 이메일 발송 및 성공실패에 대한 셋팅이 필요함

		}
		return autoPaymentCancelResponse;
	}
}
