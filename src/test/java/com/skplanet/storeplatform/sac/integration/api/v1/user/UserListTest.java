/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.integration.api.v1.user;

import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import org.apache.http.client.methods.HttpGet;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.skplanet.storeplatform.framework.test.integration.StorePlatformAPIinvokorNew;
import com.skplanet.storeplatform.framework.test.integration.SuccessCallbackForJson;
import com.skplanet.storeplatform.sac.integration.api.constant.TestConstants;
import com.skplanet.storeplatform.sc.client.vo.UserSearchList;

/**
 * 
 * Calss 설명
 * 
 * Updated on : 2013. 11. 28. Updated by : 김현일, 인크로스.
 */
public class UserListTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserListTest.class);

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
	public void shouldObtainUserList() throws Exception {
		StorePlatformAPIinvokorNew.create().url(TestConstants.SAC_URL + "/bypass/user/search/1")
				.method(HttpGet.class).accepts(TestConstants.MEDIA_TYPE_APP_JSON)
				.contentType(TestConstants.MEDIA_TYPE_APP_JSON)
				.addHeader("x-store-auth-info", "authKey=114127c7ef42667669819dad5df8d820c;ist=N")
				.success(UserSearchList.class, new SuccessCallbackForJson() {
					@Override
					public boolean isSuccess(int status) {
						return 200 <= status && status < 300;
					};

					@Override
					public void success(Object result) throws Exception {
						UserSearchList userList = (UserSearchList) result;

						assertThat(userList.getUserSearch().size(), not(0));
					}
				}).run();
		// StorePlatformAPIinvokor.create().url(TestConstants.SAC_URL + "/bypass/user/search/1").method(HttpGet.class)
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
		// UserSearchList userList = UserSearchProto.UserSearchList.newBuilder().mergeFrom(result).build();
		//
		// assertThat(userList.getUserSearchList().size(), not(0));
		//
		// UserListTest.this.LOGGER.info("\r\n - result  : " + StringUtils.toString(result, "UTF-8"));
		// }
		// }).run();
	}
}
