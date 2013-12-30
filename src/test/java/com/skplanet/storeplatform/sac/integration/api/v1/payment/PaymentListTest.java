/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.integration.api.v1.payment;

import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import org.apache.http.client.methods.HttpPost;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.skplanet.storeplatform.framework.test.integration.StorePlatformAPIinvokorForJson;
import com.skplanet.storeplatform.framework.test.integration.SuccessCallbackForJson;
import com.skplanet.storeplatform.sac.integration.api.constant.TestConstants;
import com.skplanet.storeplatform.sc.client.vo.PaymentList;

/**
 * 
 * Calss 설명
 * 
 * Updated on : 2013. 11. 28. Updated by : 김현일, 인크로스.
 */
public class PaymentListTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(PaymentListTest.class);

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
	public void shouldObtainPaymentList() throws Exception {
		StorePlatformAPIinvokorForJson.create().url(TestConstants.SAC_URL + "/bypass/payment/search/1")
				.method(HttpPost.class).accepts(TestConstants.MEDIA_TYPE_APP_JSON)
				.contentType(TestConstants.MEDIA_TYPE_APP_JSON)
				.addHeader("x-store-auth-info", "authKey=114127c7ef42667669819dad5df8d820c;ist=N")
				.success(PaymentList.class, new SuccessCallbackForJson() {
					@Override
					public boolean isSuccess(int status) {
						return 200 <= status && status < 300;
					};

					@Override
					public void success(Object result) throws Exception {
						PaymentList paymentList = (PaymentList) result;

						assertThat(paymentList.getPayment().size(), not(0));
					}
				}).run();
		// StorePlatformAPIinvokor.create().url(TestConstants.SAC_URL +
		// "/bypass/payment/search/1").method(HttpPost.class)
		// .accepts(TestConstants.MEDIA_TYPE_APP_XPROTOBUF).contentType(TestConstants.MEDIA_TYPE_APP_XPROTOBUF)
		// .addHeader("x-store-auth-info", "authKey=114127c7ef42667669819dad5df8d820c;ist=N")
		// .success(new SuccessCallback() {
		// @Override
		// public boolean isSuccess(int status) {
		// return 200 <= status && status < 300;
		// };
		//
		// @Override
		// public void success(byte[] result) throws Exception {
		// PaymentList paymentList = PaymentProto.PaymentList.newBuilder().mergeFrom(result).build();
		//
		// assertThat(paymentList.getPaymentList().size(), not(0));
		//
		// LOGGER.info("\r\n - result  : " + StringUtils.toString(result, "UTF-8"));
		// }
		// }).run();
	}

}
