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

import org.apache.http.client.methods.HttpPost;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.skplanet.storeplatform.framework.test.RequestBodySetter;
import com.skplanet.storeplatform.framework.test.integration.StorePlatformAPIinvokorNew;
import com.skplanet.storeplatform.framework.test.integration.SuccessCallbackForJson;
import com.skplanet.storeplatform.sac.client.purchase.vo.history.AutoPaymentCancelSacReq;
import com.skplanet.storeplatform.sac.client.purchase.vo.history.AutoPaymentCancelSacRes;
import com.skplanet.storeplatform.sac.integration.api.constant.TestConstants;

/**
 * 
 * Calss 설명
 * 
 * Updated on : 2013. 11. 28. Updated by : 조용진, 엔텔스.
 */

public class PurchaseAutoPaymentCancelControllerTest {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Before
	public void before() {
	}

	@Test
	public void giftReceive() throws Exception {

		StorePlatformAPIinvokorNew.create().url("http://localhost:8010/purchase/history/reservation/modify/v1")
				.method(HttpPost.class).accepts(TestConstants.MEDIA_TYPE_APP_JSON)
				.contentType(TestConstants.MEDIA_TYPE_APP_JSON)
				.addHeader("x-store-auth-info", "authKey=114127c7ef42667669819dad5df8d820c;ist=N")
				.requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {
						AutoPaymentCancelSacReq autoPaymentCancelReq = new AutoPaymentCancelSacReq();

						autoPaymentCancelReq.setTenantId("S01");
						autoPaymentCancelReq.setUserKey("IF1423218743620101219191957");
						autoPaymentCancelReq.setDeviceKey("01020977940");
						autoPaymentCancelReq.setPrchsId("MI100000000000036091");
						autoPaymentCancelReq.setClosedCd("OR020101");
						autoPaymentCancelReq.setClosedReasonCd("OR004601");
						autoPaymentCancelReq.setClosedReqPathCd("OR000499");
						return autoPaymentCancelReq;
					}
				}).success(AutoPaymentCancelSacRes.class, new SuccessCallbackForJson() {
					@Override
					public boolean isSuccess(int status) {
						return 200 <= status && status < 300;
					};

					@Override
					public void success(Object result) throws Exception {
						AutoPaymentCancelSacRes autoPaymentCancelRes = (AutoPaymentCancelSacRes) result;
						System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ : " + autoPaymentCancelRes.getPrchsId());
						System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ : " + autoPaymentCancelRes.getResultYn());
						assertThat(autoPaymentCancelRes, notNullValue());
					}
				}).run();
	}

}
