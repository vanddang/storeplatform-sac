/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.integration.api.v1.purchase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.skplanet.storeplatform.sac.purchase.interworking.service.InterworkingSacService;
import com.skplanet.storeplatform.sac.purchase.interworking.vo.InterworkingSacReq;

/**
 * 
 * 구매후처리 인터파크 연동 및 씨네21 내역 저장 TEST.
 * 
 * Updated on : 2013. 11. 28. Updated by : 조용진, 엔텔스.
 */
@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:spring-test/context-test.xml" })
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@WebAppConfiguration
public class PurchaseInterworkingServiceTest {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private InterworkingSacService interworkingService;

	/**
	 * 
	 * <pre>
	 * before 설명.
	 * </pre>
	 */
	@Before
	public void before() {
	}

	/**
	 * 
	 * <pre>
	 * 구매후처리 인터파크 연동 및 씨네21 내역 저장.
	 * </pre>
	 * 
	 * @throws Exception
	 *             Exception
	 */
	@Test
	public void shouldObtainPurchaseInterparkServiceTest() throws Exception {

		InterworkingSacReq interworkingReq = new InterworkingSacReq();
		interworkingReq.setTenantId("");
		interworkingReq.setPrchsId("PI100000000000101392"); // 구매아이디
		interworkingReq.setSystemId("S01-S001");
		interworkingReq.setUserKey("testuser");
		interworkingReq.setDeviceKey("testdevice");
		interworkingReq.setSellermbrNo("testseller");
		interworkingReq.setProdId("H000023315"); // 상품아이디
		interworkingReq.setCompContentsId("123456765"); // 인터파크상품아이디
		interworkingReq.setPrchsDt("20110404155020"); // 구매일시
		interworkingReq.setProdAmt(2000); // 상품금액
		interworkingReq.setPrchsCancelDt("");

		this.interworkingService.createInterworking(interworkingReq);
		// assertThat(res.getResultStatus(), is("success"));

	}

}
