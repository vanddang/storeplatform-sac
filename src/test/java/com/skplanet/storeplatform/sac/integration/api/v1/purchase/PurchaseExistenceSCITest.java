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

import java.util.ArrayList;
import java.util.List;

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

import com.skplanet.storeplatform.sac.client.internal.purchase.sci.ExistenceSacSCI;
import com.skplanet.storeplatform.sac.client.internal.purchase.vo.ExistenceItem;
import com.skplanet.storeplatform.sac.client.internal.purchase.vo.ExistenceListRes;
import com.skplanet.storeplatform.sac.client.internal.purchase.vo.ExistenceReq;

/**
 * 
 * Calss 설명
 * 
 * Updated on : 2013. 11. 28. Updated by : 조용진, 엔텔스.
 */
@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:spring-test/context-test.xml" })
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@WebAppConfiguration
public class PurchaseExistenceSCITest {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ExistenceSacSCI existenceSacSCI;

	/**
	 * 
	 * <pre>
	 * method 설명.
	 * </pre>
	 */
	@Before
	public void before() {
	}

	/**
	 * 
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @throws Exception
	 *             Exception
	 */
	@Test
	public void shouldObtainPurchaseInterparkServiceTest() throws Exception {

		ExistenceReq existenceReq = new ExistenceReq();
		List<ExistenceItem> list = new ArrayList<ExistenceItem>();

		// 예제용 parameter 셋팅
		existenceReq.setTenantId("S01"); // 테넌트ID
		existenceReq.setUserKey("IW1023795408420101206143202"); // UserKey
		existenceReq.setDeviceKey("01040449015"); // DeviceKey
		existenceReq.setPrchsId(""); // 구매ID

		// 예제용 상품 리스트 셋팅
		List<ExistenceItem> exListInput = new ArrayList<ExistenceItem>();
		ExistenceItem exInput = new ExistenceItem();

		// 예제용 사용자가 가지고 있는 상품 리스트
		exInput = new ExistenceItem();
		exInput.setProdId("H900000037"); // 상품 아이디
		exInput.setTenantProdGrpCd("");// 테넌트상품분류코드
		exListInput.add(exInput);
		exInput = new ExistenceItem();
		exInput.setProdId("H000044893"); // 상품 아이디
		exInput.setTenantProdGrpCd("");// 테넌트상품분류코드
		exListInput.add(exInput);

		// 조회 할 상품만큼 셋팅을 하여 줍니다.
		for (int i = 0; i < exListInput.size(); i++) {
			// 조회할 상품 리스트 객체
			ExistenceItem existenceList = new ExistenceItem();
			existenceList.setProdId(exListInput.get(i).getProdId());
			existenceList.setTenantProdGrpCd(exListInput.get(i).getTenantProdGrpCd());
			list.add(existenceList);
		}

		// 조회할 상품리스트 셋팅
		existenceReq.setExistenceItem(list);
		// 응답받을 객체 생성
		ExistenceListRes existenceListRes = new ExistenceListRes();
		// 기구매체크 조회
		existenceListRes = this.existenceSacSCI.searchExistenceList(existenceReq);
		// 조회한 리스트 확인
		for (int i = 0; i < existenceListRes.getExistenceListRes().size(); i++) {
			this.logger.debug("@@@@@@@@@@@@ getPrchsId @@@@@@@@@@@@@@@@@@@ : {}", existenceListRes
					.getExistenceListRes().get(i).getPrchsId());
			this.logger.debug("@@@@@@@@@@@@ getProdId  @@@@@@@@@@@@@@@@@@@ : {}", existenceListRes
					.getExistenceListRes().get(i).getProdId());
		}
		// assertThat(res.getResultStatus(), is("success"));

	}

}
