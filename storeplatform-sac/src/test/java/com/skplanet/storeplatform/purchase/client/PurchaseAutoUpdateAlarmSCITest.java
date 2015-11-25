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

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

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

import com.skplanet.storeplatform.purchase.client.history.sci.AutoUpdateAlarmSCI;
import com.skplanet.storeplatform.purchase.client.history.vo.AutoUpdateAlarmSc;
import com.skplanet.storeplatform.purchase.client.history.vo.AutoUpdateAlarmScReq;
import com.skplanet.storeplatform.purchase.client.history.vo.AutoUpdateAlarmScRes;

/**
 * 자동업데이트 거부/거부취소 인터페이스 테스트 케이스
 * 
 * Updated on : 2014-01-17 Updated by : 조용진, 엔텔스.
 */
@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:purchase/spring-test/context-test.xml" })
@Transactional
@TransactionConfiguration
public class PurchaseAutoUpdateAlarmSCITest {

	private static final Logger LOGGER = LoggerFactory.getLogger(PurchaseAutoUpdateAlarmSCITest.class);

	@Autowired
	private AutoUpdateAlarmSCI autoUpdateAlarmSCI;

	@Autowired
	@Qualifier("scPurchase")
	private MessageSourceAccessor messageSourceAccessor;

	/**
	 * 자동결재해지예약/예약취소/해지 테스트.
	 * 
	 * @param
	 * @return
	 */
	// @Rollback(false)
	@Test
	public void shouldOptianPurchaseAutoPaymentCancelTest() {

		AutoUpdateAlarmScReq autoUpdateAlarmScReq = new AutoUpdateAlarmScReq();
		List<AutoUpdateAlarmSc> list = new ArrayList<AutoUpdateAlarmSc>();
		AutoUpdateAlarmSc autoUpdateAlarmSc = new AutoUpdateAlarmSc();

		autoUpdateAlarmScReq.setTenantId("S01");
		autoUpdateAlarmScReq.setSystemId("testSystemId");
		autoUpdateAlarmScReq.setUserKey("IF1423218743620101219191957");

		autoUpdateAlarmSc.setProdId("H900067840");
		autoUpdateAlarmSc.setAlarmYn("N");
		list.add(autoUpdateAlarmSc);

		autoUpdateAlarmSc = new AutoUpdateAlarmSc();
		autoUpdateAlarmSc.setProdId("H900067840");
		autoUpdateAlarmSc.setAlarmYn("N");
		list.add(autoUpdateAlarmSc);

		autoUpdateAlarmScReq.setProductList(list);

		AutoUpdateAlarmScRes autoUpdateAlarmScRes = this.autoUpdateAlarmSCI.updateAlarm(autoUpdateAlarmScReq);
		LOGGER.debug("@@@@@@@@@@@@@ getResultYn @@@@@@@@@@@@@@@@ {}", autoUpdateAlarmScRes.getResultYn());

		assertThat("Y", is(autoUpdateAlarmScRes.getResultYn()));

	}
}
