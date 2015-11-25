/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.shopping;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.sac.purchase.shopping.service.ShoppingService;
import com.skplanet.storeplatform.sac.purchase.shopping.vo.CouponPublishAvailableSacParam;
import com.skplanet.storeplatform.sac.purchase.shopping.vo.CouponPublishAvailableSacResult;

/**
 * 
 * 쇼핑상품 쿠폰CMS 연동 처리 서비스 테스트
 * 
 * Updated on : 2014. 1. 16. Updated by : 이승택, nTels.
 */
@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class ShoppingServiceImplTest {
	@Autowired
	private ShoppingService shoppingService;

	/**
	 * 
	 * <pre>
	 * 쇼핑 발급 가능여부 테스트.
	 * </pre>
	 */
	@Test
	public void getCouponPublishAvailable() {
		CouponPublishAvailableSacParam shoppingReq = new CouponPublishAvailableSacParam();
		shoppingReq.setCouponCode("2000000170");
		shoppingReq.setItemCode("2000000170");
		shoppingReq.setItemCount(1);
		shoppingReq.setMdn("01011112222");

		String statusCd = null;
		String statusMsg = null;
		try {
			CouponPublishAvailableSacResult shoppingRes = this.shoppingService.getCouponPublishAvailable(shoppingReq);
			statusCd = shoppingRes.getStatusCd();
			statusMsg = shoppingRes.getStatusMsg();
		} catch (StorePlatformException e) {
			statusCd = e.getErrorInfo().getCode();
			statusMsg = e.getErrorInfo().getMessage();
		}

		assertThat(statusCd, is("EC_SCPNCMS_3101")); // 해당 쿠폰 상품 미존재

	}

	/**
	 * 
	 * <pre>
	 * 쇼핑 쿠폰 발급 테스트.
	 * </pre>
	 */
	@Test
	public void createCouponPublish() {

	}
}
