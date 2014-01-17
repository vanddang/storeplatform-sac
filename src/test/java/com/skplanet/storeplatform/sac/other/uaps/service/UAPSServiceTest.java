/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.other.uaps.service;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.external.client.uaps.vo.UserRes;
import com.skplanet.storeplatform.sac.other.uaps.common.UAPSCommon;

/**
 * 
 * UAPS Service Test
 * 
 * Updated on : 2014. 1. 16. Updated by : 김현일, 인크로스.
 */
@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
@TransactionConfiguration
@Transactional
public class UAPSServiceTest {

	@Autowired
	private UAPSService uapsService;

	/**
	 * 
	 * <pre>
	 * UAPS Service 고객정보 조회 기능 테스트.
	 * </pre>
	 */
	@Test
	public void testGetMapping() {
		UserRes userRes = this.uapsService.getMapping(UAPSCommon.DEVICE_ID, UAPSCommon.TYPE);
		assertNotNull(userRes);
	}
}
