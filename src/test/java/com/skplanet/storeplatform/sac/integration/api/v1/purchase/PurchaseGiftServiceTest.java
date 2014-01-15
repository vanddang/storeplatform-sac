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

import com.skplanet.storeplatform.purchase.client.history.vo.GiftConfirmRequest;
import com.skplanet.storeplatform.purchase.client.history.vo.GiftConfirmResponse;
import com.skplanet.storeplatform.sac.purchase.history.service.GiftService;

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
	private GiftService giftService;

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
	// @Test
	// public void shouldObtainPurchaseGiftReceiveServiceTest() throws Exception {
	//
	// GiftReceiveRequest giftReceiveRequest = new GiftReceiveRequest();
	//
	// giftReceiveRequest.setTenantId("S01");
	// giftReceiveRequest.setSendMbrNo("IF1023541315020111207133720");
	// giftReceiveRequest.setSendDeviceNo("01046129571");
	// giftReceiveRequest.setRecvMbrNo("IF1023541315020111207133720");
	// giftReceiveRequest.setRecvDeviceNo("01033276046");
	// giftReceiveRequest.setProdId("H900026621");
	// giftReceiveRequest.setPrchsId("M1046129571541651515");
	//
	// this.giftService.searchGiftReceive(giftReceiveRequest);
	//
	// }

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

		GiftConfirmRequest giftConfirmRequest = new GiftConfirmRequest();

		giftConfirmRequest.setTenantId("S01");
		giftConfirmRequest.setSendMbrNo("IF1023541315020111207133720");
		giftConfirmRequest.setSendDeviceNo("01046129571");
		giftConfirmRequest.setRecvMbrNo("IF1023541315020111207133720");
		giftConfirmRequest.setRecvDeviceNo("01033276046");
		giftConfirmRequest.setProdId("H900026621");
		giftConfirmRequest.setPrchsId("M1046129571541651515");

		GiftConfirmResponse giftConfirmResponse = new GiftConfirmResponse();
		giftConfirmResponse = this.giftService.modifyGiftConfirm(giftConfirmRequest);

		assertThat("Y", is(giftConfirmResponse.getResultYn()));

	}
}
