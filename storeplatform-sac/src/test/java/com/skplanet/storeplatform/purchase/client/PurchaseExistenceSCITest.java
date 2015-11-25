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

import com.skplanet.storeplatform.purchase.client.history.sci.ExistenceSCI;
import com.skplanet.storeplatform.purchase.client.history.vo.ExistenceItemSc;
import com.skplanet.storeplatform.purchase.client.history.vo.ExistenceScReq;
import com.skplanet.storeplatform.purchase.client.history.vo.ExistenceScRes;

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
public class PurchaseExistenceSCITest {

	private static final Logger LOGGER = LoggerFactory.getLogger(PurchaseExistenceSCITest.class);

	@Autowired
	private ExistenceSCI existenceSCI;

	@Autowired
	@Qualifier("scPurchase")
	private MessageSourceAccessor messageSourceAccessor;

	/**
	 * 기구매내역체크.
	 * 
	 * @param
	 * @return
	 */
	// @Rollback(false)
	@Test
	public void shouldOptianPurchaseExistTest() {

		ExistenceScReq existenceScReq = new ExistenceScReq();
		List<ExistenceItemSc> existenceItemListSc = new ArrayList<ExistenceItemSc>();
		ExistenceItemSc existenceItemSc = new ExistenceItemSc();

		existenceScReq.setTenantId("S01");
		// existenceScReq.setUserKey("IW1023795408420101206143202");
		// existenceScReq.setDeviceKey("01040449015");
		// existenceScReq.setPrchsId("M1040449015718184793");
		existenceScReq.setUserKey("US201402040504554230001487");
		existenceScReq.setDeviceKey("01046353524");
		// existenceScReq.setPrchsId("M1040449015718184793");

		List<String> mdnCategoryList = new ArrayList<String>();
		mdnCategoryList.add("DP15");
		mdnCategoryList.add("DP17");
		mdnCategoryList.add("DP18");

		existenceItemSc.setProdId("H900065887");
		existenceItemSc.setTenantProdGrpCd("");
		existenceItemListSc.add(existenceItemSc);
		//
		// existenceItemSc = new ExistenceItemSc();
		// existenceItemSc.setProdId("H000044893");
		// existenceItemSc.setTenantProdGrpCd("");
		// existenceItemListSc.add(existenceItemSc);

		existenceScReq.setProductList(existenceItemListSc);
		List<ExistenceScRes> existenceListScRes = new ArrayList<ExistenceScRes>();
		existenceListScRes = this.existenceSCI.searchExistenceList(existenceScReq);

		LOGGER.debug("testA size : {}", existenceListScRes.size());
		for (int i = 0; i < existenceListScRes.size(); i++) {
			LOGGER.debug("testA : {}", existenceListScRes.get(i).getPrchsId());
			LOGGER.debug("testA : {}", existenceListScRes.get(i).getProdId());
			LOGGER.debug("testA : {}", existenceListScRes.get(i).getStatusCd());
		}
		// assertThat("0000", is(existenceRes.get(0).getResultCd()));
		// assertThat("성공", is(existenceRes.get(0).getResultMsg()));
		// assertThat("0000", is(existenceRes.get(1).getResultCd()));
		// assertThat("성공", is(existenceRes.get(1).getResultMsg()));

	}

}
