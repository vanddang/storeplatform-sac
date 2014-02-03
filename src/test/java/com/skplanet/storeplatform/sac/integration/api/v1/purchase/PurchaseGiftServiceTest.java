/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.integration.api.v1.purchase;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.purchase.client.history.vo.GiftConfirmScRequest;
import com.skplanet.storeplatform.purchase.client.history.vo.GiftConfirmScResponse;
import com.skplanet.storeplatform.purchase.client.history.vo.GiftReceiveScRequest;
import com.skplanet.storeplatform.purchase.client.history.vo.GiftReceiveScResponse;
import com.skplanet.storeplatform.sac.purchase.history.service.GiftSacService;

/**
 * 
 * Calss 설명
 * 
 * Updated on : 2013. 11. 28. Updated by : 조용진, 엔텔스.
 */
@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:spring-test/context-test.xml" })
@Transactional
@TransactionConfiguration
public class PurchaseGiftServiceTest {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private GiftSacService giftService;

	/**
	 * 
	 * <pre>
	 * method 설명.
	 * </pre>
	 */
	@Before
	public void before() {
	}

	/**
	 * 
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @throws Exception
	 *             Exception
	 */
	@Test
	public void shouldObtainPurchaseGiftReceiveServiceTest() throws Exception {

		GiftReceiveScRequest giftReceiveScRequest = new GiftReceiveScRequest();

		giftReceiveScRequest.setTenantId("S01");
		giftReceiveScRequest.setSendMbrNo("IW1425031723920130227143009");
		giftReceiveScRequest.setSendDeviceNo("01020977752");
		giftReceiveScRequest.setProdId("H090107203");
		giftReceiveScRequest.setPrchsId("MI100000000000031936");

		GiftReceiveScResponse giftReceiveScResponse = new GiftReceiveScResponse();
		giftReceiveScResponse = this.giftService.searchGiftReceive(giftReceiveScRequest);

		assertThat(giftReceiveScResponse, notNullValue());

	}

	/**
	 * 
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @throws Exception
	 *             Exception
	 */
	@Test
	public void shouldObtainPurchaseGiftConfirmServiceTest() throws Exception {

		GiftConfirmScRequest giftConfirmScRequest = new GiftConfirmScRequest();

		giftConfirmScRequest.setTenantId("S01");
		giftConfirmScRequest.setSystemId("S001");
		giftConfirmScRequest.setInsdUsermbrNo("IW1425031723920130227143009");
		giftConfirmScRequest.setInsdDeviceId("01020977752");
		giftConfirmScRequest.setProdId("H090107203");
		giftConfirmScRequest.setRecvDt("20140129225055");
		giftConfirmScRequest.setPrchsId("MI100000000000031936");

		GiftConfirmScResponse giftConfirmScResponse = new GiftConfirmScResponse();
		giftConfirmScResponse = this.giftService.updateGiftConfirm(giftConfirmScRequest);

		assertThat("Y", is(giftConfirmScResponse.getResultYn()));

	}
}
