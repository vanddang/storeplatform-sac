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

import java.util.ArrayList;
import java.util.List;

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

import com.skplanet.storeplatform.purchase.client.history.sci.PaymentListSCI;
import com.skplanet.storeplatform.purchase.client.history.vo.PaymentListScRes;
import com.skplanet.storeplatform.purchase.client.history.vo.PaymentScReq;
import com.skplanet.storeplatform.purchase.client.history.vo.PaymentScRes;

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
public class PurchasePaymentListSCITest {

	private static final Logger logger = LoggerFactory.getLogger(PurchaseAutoPaymentCancelSCITest.class);
	@Autowired
	private PaymentListSCI paymentListSCI;

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
	public void shouldOptianPaymentListTest() {
		PaymentScReq paymentReq = new PaymentScReq();

		paymentReq.setTenantId("S01");
		List<String> prchsIdList = new ArrayList<String>();

		prchsIdList.add("PI100000000000028001");
		prchsIdList.add("PI100000000000028003");
		prchsIdList.add("PI100000000000028008");
		prchsIdList.add("M1045916961763973870");
		paymentReq.setPrchsId(prchsIdList);

		List<PaymentListScRes> list = this.paymentListSCI.searchPaymentList(paymentReq);

		this.logger.debug("@@@@@@@@@@@@@@@ list.size() @@@@@@@@@@@@@@@@@@@ {} ", list.size());
		for (int i = 0; i < list.size(); i++) {
			this.logger.debug("@@@@@@@@@@@@@@@ getPrchsId @@@@@@@@@@@@@@@@@@@ {} ", list.get(i).getPrchsId());
			for (PaymentScRes paymentScRes : list.get(i).getPaymentList()) {
				this.logger.debug("@@@@@@@@@@@@@@@ getPaymentAmt   @@@@@@@@@@@@@@@@@@@ {} ",
						paymentScRes.getPaymentAmt());
				this.logger.debug("@@@@@@@@@@@@@@@ getPaymentMtdCd @@@@@@@@@@@@@@@@@@@ {} ",
						paymentScRes.getPaymentMtdCd());
			}
		}

	}
}
