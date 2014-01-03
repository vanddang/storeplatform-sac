/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.api.v1.member.user;

import org.apache.http.client.methods.HttpPost;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.skplanet.storeplatform.framework.test.RequestBodySetter;
import com.skplanet.storeplatform.framework.test.integration.StorePlatformAPIinvokorNew;
import com.skplanet.storeplatform.framework.test.integration.SuccessCallbackForJson;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateByMdnReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateByMdnRes;
import com.skplanet.storeplatform.sac.integration.api.constant.TestConstants;

@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class CreateByMdnTest {

	/**
	 * <pre>
	 * 모바일 전용 회원 가입
	 * </pre>
	 */
	@Test
	public void shouldCreateByMdn() {

		StorePlatformAPIinvokorNew.create().url("http://localhost:8080/storeplatform-sac/internal/member/user/createByMdn/v1")
				.method(HttpPost.class)
				.accepts(TestConstants.MEDIA_TYPE_APP_JSON)
				.contentType(TestConstants.MEDIA_TYPE_APP_JSON)
				.addHeader("x-store-auth-info", "ist=N; tenantId=KOR001")
				.requestBody(new RequestBodySetter() {

					@Override
					public Object requestBody() {
						CreateByMdnReq req = new CreateByMdnReq();
						req.setDeviceId("12123123");
						return req;
					}
				})
				.success(CreateByMdnRes.class, new SuccessCallbackForJson() {

					@Override
					public void success(Object result) throws Exception {
						CreateByMdnRes res = (CreateByMdnRes) result;
					}

					@Override
					public boolean isSuccess(int status) {
						return 200 <= status && status < 300;
					}
				})
				.run();

	}
}
