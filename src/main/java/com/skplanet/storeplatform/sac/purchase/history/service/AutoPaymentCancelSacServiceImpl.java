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

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.external.client.tstore.sci.TStoreNotiSCI;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.purchase.client.history.sci.AutoPaymentCancelSCI;
import com.skplanet.storeplatform.purchase.client.history.vo.AutoPaymentCancelScReq;
import com.skplanet.storeplatform.purchase.client.history.vo.AutoPaymentCancelScRes;

/**
 * 기구매 SAC Service 인터페이스 구현체
 * 
 * Updated on : 2013-09-01 Updated by :조용진, 엔텔스.
 */
@Service
public class AutoPaymentCancelSacServiceImpl implements AutoPaymentCancelSacService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AutoPaymentCancelSCI autoPaymentCancelSCI;
	@Autowired
	private TStoreNotiSCI tStoreNotiSCI;

	/**
	 * 자동결재해지예약/예약취소/해지 SAC Service.
	 * 
	 * @param autoPaymentCancelReq
	 *            요청정보
	 * @return AutoPaymentCancelRes
	 */
	@Override
	public AutoPaymentCancelScRes updateReservation(AutoPaymentCancelScReq autoPaymentCancelReq) {

		AutoPaymentCancelScRes autoPaymentCancelRes = this.autoPaymentCancelSCI.updateReservation(autoPaymentCancelReq);

		// ResultYn이 'Y'일 경우에만 이메일 발송
		if (autoPaymentCancelRes.getResultYn().equals("Y")) {
			this.logger.debug("@@@@@@자동결재해지예약/예약취소/해지 이메일 발송 처리@@@@@@");
			Map<String, Object> reqMap = new HashMap<String, Object>();

			reqMap.put("userKey", autoPaymentCancelReq.getUserKey());
			reqMap.put("deviceKey", autoPaymentCancelReq.getDeviceKey());
			reqMap.put("prchsId", autoPaymentCancelReq.getPrchsId());
			// 구매일시는 조회하여 가저옴
			reqMap.put("prchsDt", autoPaymentCancelRes.getPrchsDt());
			// type code = 01:일반결제, 02:자동결제, 03:자동결제해지예약, 04:자동결제 해지완료
			reqMap.put("type", "03");
			// tstore 이메일 발송
			try {
				// TODO TENANT(Tstore 구현X) 주석처리
				// this.tStoreNotiSCI.postTStoreNoti(reqMap);
			} catch (Exception ex) {
				// 공통에러코드 정의 필요
				throw new StorePlatformException("", ex);
			}
		}
		return autoPaymentCancelRes;
	}
}
