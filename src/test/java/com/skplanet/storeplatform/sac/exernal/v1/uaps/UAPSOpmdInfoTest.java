/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.exernal.v1.uaps;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * UAPS 연동 SCI 설명
 * 
 * Updated on : 2013. 12. 11. Updated by : 김현일, 인크로스.
 */
@ActiveProfiles(value = "local")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
@TransactionConfiguration
@Transactional
public class UAPSOpmdInfoTest {

	// @Autowired
	// private UAPSSCI uapsSCI;

	/**
	 * 
	 * <pre>
	 * 자번호 정보 조회
	 * </pre>
	 * 
	 * @throws Exception
	 */
	@Test
	public void shouldObtainPurchaseList() throws Exception {
		// Map<String, Object> resultMap = this.uapsSCI.getOpmdInfo("983123");
		//
		// assertThat((Integer) resultMap.get("resultCode"), is(0));
	}
}
