/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.order;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.skplanet.storeplatform.sac.purchase.order.repository.PurchaseIapRepository;

/**
 * 
 * IAP 테스트
 * 
 * Updated on : 2014. 7. 22. Updated by : 이승택, nTels.
 */
@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class PurchaseIapRepositoryImplTest {
	@Autowired
	private PurchaseIapRepository purchaseIapRepository;

	@Test
	public void inquiryIapBillingAmt() {
		String mdn = "01046353524";
		String svcMangNo = "7021874265";
		String currMonth = new SimpleDateFormat("yyyyMM").format(new Date());

		int iapBillingAmt = this.purchaseIapRepository.inquiryBillingAmt(mdn, svcMangNo, currMonth);
		assertThat(iapBillingAmt, is(0));
	}
}
