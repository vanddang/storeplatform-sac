/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.api.v1.common.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.sac.client.common.vo.CmDevice;
import com.skplanet.storeplatform.sac.common.service.CommonService;

/**
 * 공통 서비스 조회 Class Test
 * 
 * Updated on : 2014. 1. 3. Updated by : 김현일, 인크로스
 */
@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
@TransactionConfiguration
@Transactional
public class CommonServiceTest {

	@Autowired
	private CommonService commonService;

	/**
	 * 
	 * <pre>
	 * Device 정보조회.
	 * </pre>
	 */
	@Test
	public void shouldDevice() {
		String deviceModeCd = "SHV-E160L";
		CmDevice device = this.commonService.getCmDevice("SHV-E160L");
		assertEquals(device.getDeviceModelCd(), is(deviceModeCd));
	}
}
