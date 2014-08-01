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
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.skplanet.storeplatform.sac.client.internal.member.miscellaneous.vo.IndividualPolicyInfoSac;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.purchase.order.repository.PurchaseMemberRepository;

/**
 * 
 * 회원Part SAC Inner I/F 테스트
 * 
 * Updated on : 2014. 3. 13. Updated by : 이승택, nTels.
 */
@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class PurchaseMemberRepositoryImplTest {
	@Autowired
	private PurchaseMemberRepository purchaseMemberRepository;

	// @Test
	public void getUserPolicy() {
		TenantHeader tenantHeader = new TenantHeader();
		tenantHeader.setTenantId("S01");
		tenantHeader.setSystemId("S01-01002");
		SacRequestHeader sacRequestHeader = new SacRequestHeader();
		sacRequestHeader.setTenantHeader(tenantHeader);
		RequestContextHolder.getRequestAttributes().setAttribute(SacRequestHeader.class.getName(), sacRequestHeader,
				RequestAttributes.SCOPE_REQUEST);

		String deviceKey = "01046353524";
		List<String> policyCodeList = new ArrayList<String>();
		policyCodeList.add("US011712"); // 비과금 단말
		policyCodeList.add("OR003101"); // Tstore 구매차단
		policyCodeList.add("OR003104"); // IAP 구매차단
		Map<String, IndividualPolicyInfoSac> resMap = this.purchaseMemberRepository.getPurchaseUserPolicy(deviceKey,
				policyCodeList);
		assertThat(resMap.size(), not(0));
	}

	@Test
	public void searchUserGrade() {
		TenantHeader tenantHeader = new TenantHeader();
		tenantHeader.setTenantId("S01");
		tenantHeader.setSystemId("S01-01002");
		SacRequestHeader sacRequestHeader = new SacRequestHeader();
		sacRequestHeader.setTenantHeader(tenantHeader);
		RequestContextHolder.getRequestAttributes().setAttribute(SacRequestHeader.class.getName(), sacRequestHeader,
				RequestAttributes.SCOPE_REQUEST);

		String userKey = "IM142100005724280201303121051";
		String grade = this.purchaseMemberRepository.searchUserGrade(userKey);
		assertThat(grade, is("silver"));
	}
}
