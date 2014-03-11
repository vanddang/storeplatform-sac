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

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.skplanet.storeplatform.sac.client.purchase.vo.order.CreatePurchaseSacReq;
import com.skplanet.storeplatform.sac.purchase.order.service.PurchaseOrderPolicyService;
import com.skplanet.storeplatform.sac.purchase.order.service.PurchaseOrderService;
import com.skplanet.storeplatform.sac.purchase.order.vo.PurchaseOrderInfo;
import com.skplanet.storeplatform.sac.purchase.order.vo.SktPaymentPolicyCheckParam;

/**
 * 
 * 테넌트 정책 체크 서비스 테스트
 * 
 * Updated on : 2014. 1. 16. Updated by : 이승택, nTels.
 */
@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class PurchaseOrderPolicyServiceImplTest {
	@Autowired
	private PurchaseOrderService purchaseOrderService;

	@Autowired
	private PurchaseOrderPolicyService purchasePolicyService;

	CreatePurchaseSacReq createPurchaseReq;
	PurchaseOrderInfo purchaseInfo;

	/**
	 * <pre>
	 * 테넌트 정책 체크.
	 * </pre>
	 * 
	 * @throws Exception
	 *             Exception
	 */
	@Test
	public void checkTenantPolicy() {
		SktPaymentPolicyCheckParam policyCheckParam = new SktPaymentPolicyCheckParam();
		policyCheckParam.setTenantId("S01");
		policyCheckParam.setUserKey("MBR01");
		policyCheckParam.setDeviceKey("MBR01_1");
		policyCheckParam.setDeviceId("01046353524");
		policyCheckParam.setTenantProdGrpCd("OR006221");
		policyCheckParam.setPaymentTotAmt(10000.0);

		this.purchasePolicyService.checkSktPaymentPolicy(policyCheckParam);
	}
}
