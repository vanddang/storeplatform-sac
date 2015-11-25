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
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.purchase.client.history.sci.HidingSCI;
import com.skplanet.storeplatform.purchase.client.history.vo.HidingListSc;
import com.skplanet.storeplatform.purchase.client.history.vo.HidingScReq;
import com.skplanet.storeplatform.purchase.client.history.vo.HidingScRes;

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
public class PurchaseHidingSCITest {

	private static final Logger LOGGER = LoggerFactory.getLogger(PurchaseHidingSCITest.class);

	@Autowired
	private HidingSCI hidingSCI;

	@Autowired
	private MessageSourceAccessor messageSourceAccessor;

	/**
	 * 구매내역 숨김 service 테스트.
	 */
	// @Rollback(false)
	@Test
	public void shouldOptianPurchaseHidingTest() {

		HidingScReq hidingScReq = new HidingScReq();
		List<HidingListSc> list = new ArrayList<HidingListSc>();
		HidingListSc hidingList = new HidingListSc();

		hidingScReq.setTenantId("S01");
		hidingScReq.setSystemId("S001");
		hidingScReq.setUserKey("IW1023795408420101206143202");
		hidingScReq.setDeviceKey("01040449015");

		hidingList.setPrchsId("M1040449015716287379");
		hidingList.setPrchsDtlId(1);
		hidingList.setHidingYn("N");
		list.add(hidingList);

		hidingList = new HidingListSc();
		hidingList.setPrchsId("M1040449015716735210");
		hidingList.setPrchsDtlId(1);
		hidingList.setHidingYn("Y");
		list.add(hidingList);

		hidingScReq.setHidingList(list);

		List<HidingScRes> hidingResList = new ArrayList<HidingScRes>();
		hidingResList = this.hidingSCI.updateHiding(hidingScReq);
		assertThat(hidingResList, notNullValue());
		assertThat(2, is(hidingResList.size()));

	}
}
