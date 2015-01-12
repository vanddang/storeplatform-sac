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

import static org.junit.Assert.assertNotNull;

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

import com.skplanet.storeplatform.sac.purchase.common.service.CommonCodeService;
import com.skplanet.storeplatform.sac.purchase.common.vo.PurchaseCommonCode;

/**
 * 공통코드 서비스 테스트
 * 
 * Updated on : 2014. 12. 4. Updated by : 이승택, nTels.
 */
@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class CommonCodeServiceImplTest {

	@Autowired
	private CommonCodeService commonCodeService;

	@Test
	public void getShopInfo() {
		String langCd = "ko";
		List<String> cdIdList = new ArrayList<String>();
		cdIdList.add("OR000605");
		cdIdList.add("OR000606");
		cdIdList.add("OR000607");

		Map<String, PurchaseCommonCode> codeMap = this.commonCodeService.searchCommonCodeMap(cdIdList, langCd);
		for (String cdId : cdIdList) {
			assertNotNull(codeMap.get(cdId));
		}
	}

}
