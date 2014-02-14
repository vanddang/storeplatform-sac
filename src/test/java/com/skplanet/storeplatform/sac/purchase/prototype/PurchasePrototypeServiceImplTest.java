/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.prototype;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.skplanet.storeplatform.sac.client.purchase.vo.prototype.CheckPurchaseReq;
import com.skplanet.storeplatform.sac.client.purchase.vo.prototype.MyPagePurchaseHistoryReq;
import com.skplanet.storeplatform.sac.purchase.prototype.service.PurchasePrototypeService;

/**
 * 
 * [Prototype] 구매내역 서비스 테스트
 * 
 * Updated on : 2014. 1. 16. Updated by : 이승택, nTels.
 */
@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class PurchasePrototypeServiceImplTest {

	@Autowired
	private PurchasePrototypeService purchasePrototypeService;

	/**
	 * 구매 목록 조회 테스트.
	 * 
	 * @throws Exception
	 *             Exception
	 */
	@Test
	public void shouldObtainPurchaseList() throws Exception {
		MyPagePurchaseHistoryReq paramVO = new MyPagePurchaseHistoryReq();

		paramVO.setTenantId("S01");
		// paramVO.setMbrNo("IF102815948420090820103525");
		// paramVO.setDeviceNo("01046353524");
		paramVO.setMbrNo("MBR01");
		paramVO.setDeviceNo("MBR01_1");
		paramVO.setProdOwnType("mdn");
		paramVO.setStartDt("20100101");
		paramVO.setEndDt("20141231");
		paramVO.setStartRow(1);
		paramVO.setEndRow(100);

		this.purchasePrototypeService.searchPurchaseList(paramVO);
	}

	/**
	 * 기구매체크 테스트.
	 * 
	 * @throws Exception
	 *             Exception
	 */
	@Test
	public void checkPurchase() throws Exception {
		CheckPurchaseReq paramVO = new CheckPurchaseReq();

		paramVO.setTenantId("S01");
		paramVO.setMbrNo("MBR01");
		paramVO.setDeviceNo("MBR01_1");

		List<String> prodIdList = new ArrayList<String>();
		prodIdList.add("0000027381");
		prodIdList.add("0000122080");
		prodIdList.add("H900725970");
		prodIdList.add("0000413250");
		prodIdList.add("H000044541");
		prodIdList.add("H000040313");

		paramVO.setProdIdList(prodIdList);

		this.purchasePrototypeService.checkPurchase(paramVO);

	}
}
