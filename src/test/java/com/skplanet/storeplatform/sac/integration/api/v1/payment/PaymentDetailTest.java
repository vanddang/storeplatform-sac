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
import com.skplanet.storeplatform.sac.integration.api.constant.TestConstants;
import com.skplanet.storeplatform.sc.client.vo.Payment;

/**
 * 
 * Calss 설명
 * 
 * Updated on : 2013. 11. 28. Updated by : 김현일, 인크로스.
 */
public class PaymentDetailTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(PaymentDetailTest.class);

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
	public void shouldObtainPaymentDetail() throws Exception {
		StorePlatformAPIinvokorNew.create().url(TestConstants.SAC_URL + "/bypass/payment/detail/1")
				.method(HttpPost.class).accepts(TestConstants.MEDIA_TYPE_APP_JSON)
				.contentType(TestConstants.MEDIA_TYPE_APP_JSON)
				.addHeader("x-store-auth-info", "authKey=114127c7ef42667669819dad5df8d820c;ist=N")
				.requestBody(new RequestBodySetter() {
					@SuppressWarnings("rawtypes")
					@Override
					public Object requestBody() {
						Payment payment = new Payment();
						payment.setIdentifier("10001");
						return payment;
					}
				}).success(Payment.class, new SuccessCallbackForJson() {
					@Override
					public boolean isSuccess(int status) {
						return 200 <= status && status < 300;
					};

					@Override
					public void success(Object result) throws Exception {
						Payment payment = (Payment) result;

						assertThat(payment, notNullValue());
					}
				}).run();
		// StorePlatformAPIinvokor.create().url(TestConstants.SAC_URL +
		// "/bypass/payment/detail/1").method(HttpPost.class)
		// .accepts(TestConstants.MEDIA_TYPE_APP_XPROTOBUF).contentType(TestConstants.MEDIA_TYPE_APP_XPROTOBUF)
		// .addHeader("x-store-auth-info", "authKey=114127c7ef42667669819dad5df8d820c;ist=N")
		// .requestBody(new ProtobufRequestBodySetter() {
		// @Override
		// public Builder body() {
		// return PaymentProto.Payment.newBuilder().setIdentifier("10001");
		// }
		// }).success(new SuccessCallback() {
		// @Override
		// public boolean isSuccess(int status) {
		// return 200 <= status && status < 300;
		// };
		//
		// @Override
		// public void success(byte[] result) throws Exception {
		// Payment payment = PaymentProto.Payment.newBuilder().mergeFrom(result).build();
		//
		// assertThat(payment, notNullValue());
		//
		// PaymentDetailTest.this.LOGGER.info("\r\n - result  : " + StringUtils.toString(result, "UTF-8"));
		// }
		// }).run();
	}

}
