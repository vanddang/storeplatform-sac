package com.skplanet.storeplatform.sac.api.v1.member.user;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.skplanet.storeplatform.framework.test.RequestBodySetter;
import com.skplanet.storeplatform.framework.test.SuccessCallback;
import com.skplanet.storeplatform.framework.test.TestCaseTemplate;
import com.skplanet.storeplatform.framework.test.TestCaseTemplate.RunMode;
import com.skplanet.storeplatform.sac.client.member.vo.common.UserExtraInfo;
import com.skplanet.storeplatform.sac.client.member.vo.user.UserExtraInfoReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.UserExtraInfoRes;

@ActiveProfiles(value = "local")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserExtraInfoRemoveTest {
	private static final Logger logger = LoggerFactory.getLogger(UserExtraInfoRemoveTest.class);

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	/**
	 * 
	 * <pre>
	 * method 설명.
	 * </pre>
	 */
	@Before
	public void before() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	/**
	 * <pre>
	 * 회원 부가정보 삭제 테스트
	 * </pre>
	 * 
	 * @throws Exception
	 *             Exception
	 */
	@Test
	public void aRemoveUserExtraInfo() {

		new TestCaseTemplate(this.mockMvc).url("/member/user/removeAdditionalInformation/v1").httpMethod(HttpMethod.POST)
				.requestBody(new RequestBodySetter() {
					@Override
					public Object requestBody() {
						UserExtraInfoReq req = new UserExtraInfoReq();
						req.setUserKey("US201401221721168550000323");

						// 회원 부가 정보 삭제 리스트
						List<UserExtraInfo> userExtraList = new ArrayList<UserExtraInfo>();

						UserExtraInfo user1 = new UserExtraInfo();
						user1.setExtraProfile("US010905");
						// user1.setExtraProfileValue("");

						UserExtraInfo user2 = new UserExtraInfo();
						user2.setExtraProfile("US010906");
						// user2.setExtraProfileValue("");

						// UserExtraInfo user3 = new UserExtraInfo();
						// user3.setExtraProfileCode("US010903");
						// user3.setExtraProfileValue("100008261050");

						userExtraList.add(user1);
						userExtraList.add(user2);
						// userExtraList.add(user3);

						req.setUserExtraInfoList(userExtraList);

						logger.debug("request param : {}", req.toString());
						return req;
					}
				}).success(UserExtraInfoRes.class, new SuccessCallback() {
					@Override
					public void success(Object result, HttpStatus httpStatus, RunMode runMode) {
						UserExtraInfoRes res = (UserExtraInfoRes) result;
						assertThat(res.getUserKey(), notNullValue());
						logger.debug("response param : {}", res.toString());
					}
				}, HttpStatus.OK, HttpStatus.ACCEPTED).run(RunMode.JSON);

	}
}
