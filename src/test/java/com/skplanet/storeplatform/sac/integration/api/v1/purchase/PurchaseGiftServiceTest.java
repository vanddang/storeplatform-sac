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

import com.skplanet.storeplatform.purchase.client.history.vo.GiftConfirmScReq;
import com.skplanet.storeplatform.purchase.client.history.vo.GiftConfirmScRes;
import com.skplanet.storeplatform.purchase.client.history.vo.GiftReceiveScReq;
import com.skplanet.storeplatform.purchase.client.history.vo.GiftReceiveScRes;
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

		GiftReceiveScReq giftReceiveScReq = new GiftReceiveScReq();

		giftReceiveScReq.setTenantId("S01");
		giftReceiveScReq.setSendMbrNo("IW1425031723920130227143009");
		giftReceiveScReq.setSendDeviceNo("01020977752");
		giftReceiveScReq.setProdId("H090107203");
		giftReceiveScReq.setPrchsId("MI100000000000031936");

		GiftReceiveScRes giftReceiveScRes = new GiftReceiveScRes();
		giftReceiveScRes = this.giftService.searchGiftReceive(giftReceiveScReq);

		assertThat(giftReceiveScRes, notNullValue());

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

		GiftConfirmScReq giftConfirmScReq = new GiftConfirmScReq();

		giftConfirmScReq.setTenantId("S01");
		giftConfirmScReq.setSystemId("S001");
		giftConfirmScReq.setInsdUsermbrNo("IW1425031723920130227143009");
		giftConfirmScReq.setInsdDeviceId("01020977752");
		giftConfirmScReq.setProdId("H090107203");
		giftConfirmScReq.setRecvDt("20140129225055");
		giftConfirmScReq.setPrchsId("MI100000000000031936");

		GiftConfirmScRes giftConfirmScRes = new GiftConfirmScRes();
		giftConfirmScRes = this.giftService.updateGiftConfirm(giftConfirmScReq);

		assertThat("Y", is(giftConfirmScRes.getResultYn()));

	}
}
