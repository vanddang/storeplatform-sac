/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.purchase.client;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.purchase.client.history.sci.AutoPaymentCancelSCI;
import com.skplanet.storeplatform.purchase.client.history.vo.AutoPaymentCancelScReq;
import com.skplanet.storeplatform.purchase.client.history.vo.AutoPaymentCancelScRes;

/**
 * 구매 컴포넌트 인터페이스 테스트 케이스
 * 
 * Updated on : 2014-01-17 Updated by : 조용진, 엔텔스.
 */
@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:purchase/spring-test/context-test.xml" })
@Transactional
@TransactionConfiguration
public class PurchaseAutoPaymentCancelSCITest {

	private static final Logger LOGGER = LoggerFactory.getLogger(PurchaseAutoPaymentCancelSCITest.class);

	@Autowired
	private AutoPaymentCancelSCI autoPaymentCancelSCI;

	@Autowired
	@Qualifier("scPurchase")
	private MessageSourceAccessor messageSourceAccessor;

	/**
	 * 자동결재해지예약/예약취소/해지.
	 * 
	 * @param
	 * @return
	 */
	// @Rollback(false)
	@Test
	public void shouldOptianPurchaseAutoPaymentCancelTest() {

		AutoPaymentCancelScReq autoPaymentCancelReq = new AutoPaymentCancelScReq();

		autoPaymentCancelReq.setTenantId("S01");
		autoPaymentCancelReq.setSystemId("S01-001");
		autoPaymentCancelReq.setUserKey("IF1423218743620101219191957");
		autoPaymentCancelReq.setDeviceKey("01020977940");
		autoPaymentCancelReq.setPrchsId("MI100000000000036091");
		autoPaymentCancelReq.setAutoPaymentStatusCd("OR020102");
		autoPaymentCancelReq.setClosedReasonCd("OR004601");
		autoPaymentCancelReq.setClosedReqPathCd("OR000499");

		AutoPaymentCancelScRes autoPaymentCancelRes = this.autoPaymentCancelSCI.updateReservation(autoPaymentCancelReq);

		LOGGER.debug("@@getPrchsId@@{}", autoPaymentCancelRes.getPrchsId());
		LOGGER.debug("@@getPrchsDt@@{}", autoPaymentCancelRes.getPrchsDt());
		LOGGER.debug("@@getResultYn@@{}", autoPaymentCancelRes.getResultYn());

	}
}
