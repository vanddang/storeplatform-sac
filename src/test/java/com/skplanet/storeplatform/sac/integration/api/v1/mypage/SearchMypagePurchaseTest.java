/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.integration.api.v1.mypage;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.HttpPost;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.protobuf.AbstractMessage.Builder;
import com.skplanet.storeplatform.framework.test.integration.ProtobufRequestBodySetter;
import com.skplanet.storeplatform.framework.test.integration.StorePlatformAPIinvokor;
import com.skplanet.storeplatform.framework.test.integration.SuccessCallback;
import com.skplanet.storeplatform.sac.client.user.vo.MyPagePurchaseProto;
import com.skplanet.storeplatform.sac.client.user.vo.MyPagePurchaseProto.MyPagePurchase;
import com.skplanet.storeplatform.sac.integration.api.constant.TestConstants;

/**
 * 
 * Calss 설명
 * 
 * Updated on : 2013. 11. 28. Updated by : 김현일, 인크로스.
 */
public class SearchMypagePurchaseTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(SearchMypagePurchaseTest.class);

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
	public void shouldObtainMypagePruchase() throws Exception {
		StorePlatformAPIinvokor.create().url(TestConstants.SAC_URL + "/entity/user/mypage/purchase/1")
				.method(HttpPost.class).accepts(TestConstants.MEDIA_TYPE_APP_XPROTOBUF)
				.contentType(TestConstants.MEDIA_TYPE_APP_XPROTOBUF)
				.addHeader("x-store-auth-info", "authKey=114127c7ef42667669819dad5df8d820c;ist=N")
				.requestBody(new ProtobufRequestBodySetter() {
					@Override
					public Builder body() {
						return MyPagePurchaseProto.MyPagePurchase.newBuilder().setId(10001).setPid("A123456789");
					}
				}).success(new SuccessCallback() {
					@Override
					public boolean isSuccess(int status) {
						return 200 <= status && status < 300;
					};

					@Override
					public void success(byte[] result) throws Exception {
						MyPagePurchase myPagePurchase = MyPagePurchaseProto.MyPagePurchase.newBuilder()
								.mergeFrom(result).build();

						assertThat(myPagePurchase, notNullValue());

						SearchMypagePurchaseTest.this.LOGGER.info("\r\n - result  : "
								+ StringUtils.toString(result, "UTF-8"));
					}
				}).run();
	}

}
