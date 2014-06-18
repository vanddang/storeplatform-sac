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
	 * @param autoPaymentCancelscReq
	 *            요청정보
	 * @return AutoPaymentCancelRes
	 */
	@Override
	public AutoPaymentCancelScRes updateReservation(AutoPaymentCancelScReq autoPaymentCancelscReq) {

		AutoPaymentCancelScRes autoPaymentCancelRes = this.autoPaymentCancelSCI
				.updateReservation(autoPaymentCancelscReq);
		// 이메일발송을 tenant(Tstore)에서 처리함
		// // 부분유료화자경결재해지가 아닐시에는 메일전송처리를 한다.
		// if (!autoPaymentCancelscReq.getAutoPaymentStatusCd().equals(PurchaseConstants.AUTO_PRCHS_STATUS_IAP_CLOSED))
		// {
		// // ResultYn이 'Y'일 경우에만 이메일 발송
		// if (autoPaymentCancelRes.getResultYn().equals("Y")) {
		// this.logger.debug("@@@@@@자동결재해지예약/예약취소/해지 이메일 발송 처리@@@@@@");
		// // tstore 이메일 발송
		// // try {
		// // TODO TENANT(Tstore 구현X) 주석처리
		// TStoreNotiEcReq tStoreNotiEcReq = new TStoreNotiEcReq();
		// tStoreNotiEcReq.setUserKey(autoPaymentCancelscReq.getUserKey());
		// tStoreNotiEcReq.setDeviceKey(autoPaymentCancelscReq.getDeviceKey());
		// tStoreNotiEcReq.setPrchsId(autoPaymentCancelscReq.getPrchsId());
		// tStoreNotiEcReq.setPrchsDt(autoPaymentCancelRes.getPrchsDt());
		// // type code = 01:일반결제, 02:자동결제, 03:자동결제해지예약, 04:자동결제 해지완료
		// tStoreNotiEcReq.setType(PurchaseConstants.TSTORE_NOTI_TYPE_AUTOPAY_RESERVED);
		// // 2014-03-18 쇼핑의 동기와 비동기 수용을 위해 추가됨
		// // publishType code = (01:동기, 02:비동기, 03:비동기완료)
		// // - 동기 : 쇼핑 상품의 비동기 방식을 제외한 전 상품 구매시 사용, E-Mail/MMS/SMS 발송
		// // - 비동기 : 쇼핑 상품의 비동기 방식을 경우, E-Mail만 발송
		// // - 비동기완료 : 비동기로 응답이 되었을 경우, MMS만 발송
		// tStoreNotiEcReq.setPublishType(PurchaseConstants.TSTORE_NOTI_PUBLISH_TYPE_SYNC);
		// try {
		// TStoreNotiEcRes tStoreNotiEcRes = this.tStoreNotiSCI.postTStoreNoti(tStoreNotiEcReq);
		// this.logger.debug("TStoreNotiEcRes Code    : {}", tStoreNotiEcRes.getCode());
		// this.logger.debug("TStoreNotiEcRes Message : {}", tStoreNotiEcRes.getMessage());
		//
		// } catch (Exception ex) {
		// this.logger.debug("AutoPaymentCancel {}", ex);
		// }
		// }
		// }
		return autoPaymentCancelRes;
	}
}
