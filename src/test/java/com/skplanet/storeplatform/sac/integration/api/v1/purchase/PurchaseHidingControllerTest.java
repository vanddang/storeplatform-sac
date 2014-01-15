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

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.methods.HttpPost;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.skplanet.storeplatform.framework.test.RequestBodySetter;
import com.skplanet.storeplatform.framework.test.integration.StorePlatformAPIinvokorNew;
import com.skplanet.storeplatform.framework.test.integration.SuccessCallbackForJson;
import com.skplanet.storeplatform.sac.client.purchase.vo.history.HidingListRes;
import com.skplanet.storeplatform.sac.client.purchase.vo.history.HidingReq;
import com.skplanet.storeplatform.sac.client.purchase.vo.history.HidingSacList;
import com.skplanet.storeplatform.sac.integration.api.constant.TestConstants;

/**
 * 
 * Calss 설명
 * 
 * Updated on : 2013. 11. 28. Updated by : 조용진, 엔텔스.
 */

public class PurchaseHidingControllerTest {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Before
	public void before() {
	}

	@Test
	public void existenceControllerTest() throws Exception {

		StorePlatformAPIinvokorNew.create().url("http://localhost:8010/purchase/history/hiding/modify/v1")
				.method(HttpPost.class).accepts(TestConstants.MEDIA_TYPE_APP_JSON)
				.contentType(TestConstants.MEDIA_TYPE_APP_JSON)
				.addHeader("x-store-auth-info", "authKey=114127c7ef42667669819dad5df8d820c;ist=N")
				.requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {
						HidingReq hidingReq = new HidingReq();
						List<HidingSacList> list = new ArrayList<HidingSacList>();
						HidingSacList hidingSacList = new HidingSacList();

						hidingReq.setTenantId("S01");
						hidingReq.setInsdUsermbrNo("IW1023795408420101206143202");
						hidingReq.setInsdDeviceId("01040449015");
						// 숨길 구매내역 셋팅
						hidingSacList.setPrchsId("M1040449015716287379");
						hidingSacList.setPrchsDtlId(1);
						hidingSacList.setHidingYn("Y");
						list.add(hidingSacList);

						hidingSacList = new HidingSacList();
						hidingSacList.setPrchsId("M1040449015716735210");
						hidingSacList.setPrchsDtlId(1);
						hidingSacList.setHidingYn("Y");
						list.add(hidingSacList);

						hidingReq.setHidingSacList(list);
						return hidingReq;
					}
				}).success(HidingListRes.class, new SuccessCallbackForJson() {
					@Override
					public boolean isSuccess(int status) {
						return 200 <= status && status < 300;
					};

					@Override
					public void success(Object result) throws Exception {
						@SuppressWarnings("unchecked")
						HidingListRes hidingListRes = (HidingListRes) result;
						for (int i = 0; i < hidingListRes.getHidingRes().size(); i++) {
							PurchaseHidingControllerTest.this.logger.debug(
									"@@@@@@@@@@@@ getPrchsId @@@@@@@@@@@@@@@@@@@ : {}", hidingListRes.getHidingRes()
											.get(i).getPrchsId());
							PurchaseHidingControllerTest.this.logger.debug(
									"@@@@@@@@@@@@ getProdId  @@@@@@@@@@@@@@@@@@@ : {}", hidingListRes.getHidingRes()
											.get(i).getPrchsDtlId());
							PurchaseHidingControllerTest.this.logger.debug(
									"@@@@@@@@@@@@ getProdId  @@@@@@@@@@@@@@@@@@@ : {}", hidingListRes.getHidingRes()
											.get(i).getResultYn());
						}
						assertThat(hidingListRes, notNullValue());
					}
				}).run();
	}
}
