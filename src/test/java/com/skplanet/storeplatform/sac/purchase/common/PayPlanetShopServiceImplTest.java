/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.common;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.skplanet.storeplatform.sac.purchase.common.service.PayPlanetShopService;
import com.skplanet.storeplatform.sac.purchase.common.vo.PayPlanetShop;

/**
 * 
 * 구매Part 테넌트 정책 조회 테스트
 * 
 * Updated on : 2014. 1. 16. Updated by : 이승택, nTels.
 */
@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class PayPlanetShopServiceImplTest {

	@Autowired
	private PayPlanetShopService payPlanetShopService;

	@Test
	public void getShopInfo() {
		String tenantId = "S01";

		PayPlanetShop payPlanetShop = this.payPlanetShopService.getPayPlanetShopInfo(tenantId);
		assertNotNull(payPlanetShop);
	}

}
