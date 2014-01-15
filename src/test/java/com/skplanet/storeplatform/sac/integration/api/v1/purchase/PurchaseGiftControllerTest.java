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
import com.skplanet.storeplatform.purchase.client.history.vo.GiftConfirmRequest;
import com.skplanet.storeplatform.sac.client.purchase.vo.history.GiftConfirmRes;
import com.skplanet.storeplatform.sac.client.purchase.vo.history.GiftReceiveReq;
import com.skplanet.storeplatform.sac.client.purchase.vo.history.GiftReceiveRes;
import com.skplanet.storeplatform.sac.integration.api.constant.TestConstants;

/**
 * 
 * Calss 설명
 * 
 * Updated on : 2013. 11. 28. Updated by : 조용진, 엔텔스.
 */

public class PurchaseGiftControllerTest {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Before
	public void before() {
	}

	@Test
	public void giftReceive() throws Exception {

		StorePlatformAPIinvokorNew.create().url("http://localhost:8010/purchase/history/gift/get/v1")
				.method(HttpPost.class).accepts(TestConstants.MEDIA_TYPE_APP_JSON)
				.contentType(TestConstants.MEDIA_TYPE_APP_JSON)
				.addHeader("x-store-auth-info", "authKey=114127c7ef42667669819dad5df8d820c;ist=N")
				.requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {
						GiftReceiveReq giftReceiveReq = new GiftReceiveReq();

						giftReceiveReq.setTenantId("S01");
						giftReceiveReq.setSendMbrNo("IF1023541315020111207133720");
						giftReceiveReq.setSendDeviceNo("01046129571");
						giftReceiveReq.setRecvMbrNo("IF1023541315020111207133720");
						giftReceiveReq.setRecvDeviceNo("01033276046");
						giftReceiveReq.setProdId("H900026621");
						giftReceiveReq.setPrchsId("M1046129571541651515");
						return giftReceiveReq;
					}
				}).success(GiftReceiveRes.class, new SuccessCallbackForJson() {
					@Override
					public boolean isSuccess(int status) {
						return 200 <= status && status < 300;
					};

					@Override
					public void success(Object result) throws Exception {
						GiftReceiveRes giftReceiveRes = (GiftReceiveRes) result;
						System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ : " + giftReceiveRes.getRecvDt());
						System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ : " + giftReceiveRes.getRecvDt());
						System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ : " + giftReceiveRes.getRecvDt());
						System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ : " + giftReceiveRes.getRecvDt());
						System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ : " + giftReceiveRes.getRecvDt());
						System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ : " + giftReceiveRes.getRecvDt());
						assertThat(giftReceiveRes, notNullValue());
					}
				}).run();
	}

	@Test
	public void giftConfirm() throws Exception {

		StorePlatformAPIinvokorNew.create().url("http://localhost:8010/purchase/history/gift/modify/v1")
				.method(HttpPost.class).accepts(TestConstants.MEDIA_TYPE_APP_JSON)
				.contentType(TestConstants.MEDIA_TYPE_APP_JSON)
				.addHeader("x-store-auth-info", "authKey=114127c7ef42667669819dad5df8d820c;ist=N")
				.requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {
						GiftConfirmRequest giftConfirmRequest = new GiftConfirmRequest();

						giftConfirmRequest.setTenantId("S01");
						giftConfirmRequest.setSendMbrNo("IF1023541315020111207133720");
						giftConfirmRequest.setSendDeviceNo("01046129571");
						giftConfirmRequest.setRecvMbrNo("IF1023541315020111207133720");
						giftConfirmRequest.setRecvDeviceNo("01033276046");
						giftConfirmRequest.setProdId("H900026621");
						giftConfirmRequest.setPrchsId("M1046129571541651515");
						return giftConfirmRequest;
					}
				}).success(GiftConfirmRes.class, new SuccessCallbackForJson() {
					@Override
					public boolean isSuccess(int status) {
						return 200 <= status && status < 300;
					};

					@Override
					public void success(Object result) throws Exception {
						GiftConfirmRes giftConfirmRes = (GiftConfirmRes) result;
						System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ : " + giftConfirmRes.getPrchsId());
						System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ : " + giftConfirmRes.getProdId());
						System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ : " + giftConfirmRes.getResultYn());
						assertThat(giftConfirmRes, notNullValue());
					}
				}).run();
	}
}
