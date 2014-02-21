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

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * 
 * 구매ID 생성 테스트
 * 
 * Updated on : 2014. 1. 16. Updated by : 이승택, nTels.
 */
@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class PrchsIdGeneratorTest {
	PrchsIdGenerator generator = new PrchsIdGenerator();

	/**
	 * <pre>
	 * 구매ID 생성 테스트.
	 * </pre>
	 */
	@Test
	public void genPrchsId() {
		Set<String> set = new HashSet<String>();

		int testcnt = 1000;
		for (int i = 0; i < testcnt; i++) {
			set.add(this.generator.generatePrchsId());
		}
	}
}
