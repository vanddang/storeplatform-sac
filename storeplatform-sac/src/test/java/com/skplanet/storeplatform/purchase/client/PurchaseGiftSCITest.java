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

import com.skplanet.storeplatform.purchase.client.history.sci.GiftSCI;
import com.skplanet.storeplatform.purchase.client.history.vo.GiftConfirmScReq;
import com.skplanet.storeplatform.purchase.client.history.vo.GiftConfirmScRes;
import com.skplanet.storeplatform.purchase.client.history.vo.GiftReceiveScReq;
import com.skplanet.storeplatform.purchase.client.history.vo.GiftReceiveScRes;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * 구매 컴포넌트 인터페이스 선물 테스트 케이스
 * 
 * Updated on : 2014-01-17 Updated by : 조용진, 엔텔스.
 */
@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:purchase/spring-test/context-test.xml" })
@Transactional
@TransactionConfiguration
public class PurchaseGiftSCITest {

	private static final Logger LOGGER = LoggerFactory.getLogger(PurchaseGiftSCITest.class);

	@Autowired
	private GiftSCI giftSCI;

	@Autowired
	@Qualifier("scPurchase")
	private MessageSourceAccessor messageSourceAccessor;

	/**
	 * 선물수신확인 테스트.
	 */
//	 @Rollback(false)
	@Test
	public void shouldOptianpurchaseGiftReceiveTest() {

		GiftReceiveScReq giftReceiveScReq = new GiftReceiveScReq();

		giftReceiveScReq.setTenantId("S01");
		giftReceiveScReq.setUserKey("IF1023541315020111207133720");
		giftReceiveScReq.setDeviceKey("01046129571");
		giftReceiveScReq.setProdId("H900026621");
		giftReceiveScReq.setPrchsId("M1046129571541651515");

		GiftReceiveScRes giftReceiveScRes = new GiftReceiveScRes();
		giftReceiveScRes = this.giftSCI.searchGiftReceive(giftReceiveScReq);
		assertThat(giftReceiveScRes, notNullValue());

	}

	/**
	 * 선물수신 테스트.
	 */
	@Rollback(false)
	@Test
	public void shouldOptianpurchaseGiftReceiveConfirmTest() {

		GiftConfirmScReq giftConfirmScReq = new GiftConfirmScReq();

		giftConfirmScReq.setTenantId("S01");
		giftConfirmScReq.setSystemId("S001");
		giftConfirmScReq.setUserKey("US201506031707480460024079");
		giftConfirmScReq.setDeviceKey("DE2015060317074808615049218");
		giftConfirmScReq.setProdId("H102803101");
		giftConfirmScReq.setRecvDt("20150628082416");
		giftConfirmScReq.setPrchsId("15063016285601018491");

		GiftConfirmScRes giftConfirmRes = new GiftConfirmScRes();
		giftConfirmRes = this.giftSCI.updateGiftConfirm(giftConfirmScReq);
		assertThat(giftConfirmRes, notNullValue());

	}
}
