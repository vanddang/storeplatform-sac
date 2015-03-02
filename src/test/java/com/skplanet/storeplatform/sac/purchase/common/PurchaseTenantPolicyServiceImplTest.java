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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.skplanet.storeplatform.purchase.client.common.vo.TenantSalePolicy;
import com.skplanet.storeplatform.purchase.constant.PurchaseConstants;
import com.skplanet.storeplatform.sac.purchase.common.service.PurchaseTenantPolicyService;

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
public class PurchaseTenantPolicyServiceImplTest {
	@Autowired
	private PurchaseTenantPolicyService policyService;

	/**
	 * 
	 * <pre>
	 * 정책 조회 테스트.
	 * </pre>
	 */
	@Test
	public void searchTest() {
		String tenantId = "S01";

		String tenantProdGrpCd = PurchaseConstants.TENANT_PRODUCT_GROUP_SHOPPING;
		List<TenantSalePolicy> policyList = this.policyService.searchTenantSalePolicyList(tenantId, tenantProdGrpCd);
		assertThat(policyList.size(), not(0));

		String procPatternCd = PurchaseConstants.POLICY_PATTERN_DEVICE_BASED_PRCHSHST;
		policyList = this.policyService.searchTenantSalePolicyList(tenantId, tenantProdGrpCd, procPatternCd, true);
		assertThat(policyList.size(), not(0));

		procPatternCd = PurchaseConstants.POLICY_PATTERN_TMILEAGE_SAVE_PAYMENT_METHOD;
		// policyList = this.policyService.searchTenantSalePolicyList(tenantId, tenantProdGrpCd, procPatternCd,
		// false);
		policyList = this.policyService.searchTenantSalePolicyList(tenantId, null, procPatternCd, false);
		assertThat(policyList.size(), not(0));
		assertThat(policyList.get(0).getApplyValue(), is("11;12;13;14;20;22;23;24"));
	}
}
