/**
 * 
 */
package com.skplanet.storeplatform.sac.member.miscellaneous.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.AuthorizeAccountReq;

/**
 * 결제 계좌 정보 인증 JUnit Test.
 * 
 * Updated on : 2014. 2. 20. Updated by : 김다슬, 인크로스.
 */

@ActiveProfiles(value = "local")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class AuthorizeAccountControllerTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(GetPhoneAuthorizationCodeTest.class);

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	/**
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
	 * 결제 계좌 정보 인증.
	 * - 인증 성공
	 * </pre>
	 * 
	 */
	@Test
	public void testAuthorizteAccount() throws Exception {
		AuthorizeAccountReq req = new AuthorizeAccountReq();
		req.setBankAcctName("반범진");
		req.setBankAccount("02480204080433");
		req.setBankCode("04");

		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(req);
		this.mockMvc
				.perform(
						post("/member/miscellaneous/dev/authorizeAccount/v1").contentType(MediaType.APPLICATION_JSON)
								.content(json)).andDo(print()).andExpect(status().isOk());
	}

	/**
	 * <pre>
	 * 결제 계좌 정보 인증.
	 * - 인증 실패
	 * </pre>
	 * 
	 */
	@Test
	public void testExceptAuthorizteAccount() throws Exception {
		AuthorizeAccountReq req = new AuthorizeAccountReq();
		req.setBankAcctName("반범진");
		req.setBankAccount("02480204080430");
		req.setBankCode("04");

		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(req);
		this.mockMvc
				.perform(
						post("/member/miscellaneous/dev/authorizeAccount/v1").contentType(MediaType.APPLICATION_JSON)
								.content(json)).andDo(print()).andExpect(status().isOk());	}
}

