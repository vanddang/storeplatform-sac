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

import com.skplanet.storeplatform.external.client.tstore.sci.TStoreNotiSCI;
import com.skplanet.storeplatform.external.client.tstore.vo.TStoreNotiV2EcReq;
import com.skplanet.storeplatform.external.client.tstore.vo.TStoreNotiV2EcRes;
import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.purchase.client.history.sci.AutoPaymentCancelSCI;
import com.skplanet.storeplatform.purchase.client.history.vo.AutoPaymentCancelScReq;
import com.skplanet.storeplatform.purchase.client.history.vo.AutoPaymentCancelScRes;
import com.skplanet.storeplatform.sac.purchase.constant.PurchaseConstants;

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
	 * @param autoPaymentCancelscReq
	 *            요청정보
	 * @return AutoPaymentCancelRes
	 */
	@Override
	public AutoPaymentCancelScRes updateReservation(AutoPaymentCancelScReq autoPaymentCancelscReq) {

		// 자동결제해지요청
		AutoPaymentCancelScRes autoPaymentCancelRes = this.autoPaymentCancelSCI
				.updateReservation(autoPaymentCancelscReq);

		// 해지시 SMS 발송을 위한 결제노티 요청
		if (StringUtils.equals(PurchaseConstants.AUTO_PRCHS_STATUS_CLOSED,
				autoPaymentCancelscReq.getAutoPaymentStatusCd())) {

			if (autoPaymentCancelRes != null && StringUtils.equals("Y", autoPaymentCancelRes.getResultYn())) {

				TStoreNotiV2EcReq tStoreNotiV2EcReq = new TStoreNotiV2EcReq();

				tStoreNotiV2EcReq.setUserKey(autoPaymentCancelscReq.getUserKey());
				tStoreNotiV2EcReq.setDeviceKey(autoPaymentCancelscReq.getDeviceKey());
				tStoreNotiV2EcReq.setPrchsId(autoPaymentCancelscReq.getPrchsId());
				tStoreNotiV2EcReq.setPrchsDt(autoPaymentCancelRes.getPrchsDt());
				tStoreNotiV2EcReq.setType(PurchaseConstants.TSTORE_NOTI_TYPE_AUTOPAY_CLOSED); // 04 : 자동결제 해지완료
				tStoreNotiV2EcReq.setReason(PurchaseConstants.TSTORE_NOTI_REASON_AUTOPAY_CLOSED); // 01:VOD 월정액 해지 요청시
				tStoreNotiV2EcReq.setProdId(autoPaymentCancelRes.getProdId());

				tStoreNotiV2EcReq.setPublishType("01");
				tStoreNotiV2EcReq.setGiftYn("N");

				try {
					TStoreNotiV2EcRes tStoreNotiV2EcRes = this.tStoreNotiSCI.postTStoreNotiV2(tStoreNotiV2EcReq);
					this.logger.info("PRCHS,AUTOPAYMENTCANCEL,SAC,NOTI,RES,{}", tStoreNotiV2EcRes);
				} catch (Exception e) {
					this.logger.info("PRCHS,AUTOPAYMENTCANCEL,SAC,ERROR,{},{}", autoPaymentCancelscReq.getPrchsId(),
							e.getMessage());
				}

			}
		}
		return autoPaymentCancelRes;
	}
}
