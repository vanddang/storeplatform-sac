/**
 *
 */
package com.skplanet.storeplatform.sac.display.card.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.skplanet.storeplatform.sac.client.display.vo.card.MemberSegmentReq;

/**
 * 회원별 세그먼트 및 선호카테고리 정보 조회 테스트케이스
 *
 * Updated on : 2015. 6. 1.
 * Updated by : 1002159
 */
@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class MemberSegmentControllerTest {

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mvc;

	@Before
	public void before() {
		this.mvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

//	@Test

	public void basic() throws Exception {

		// LocalSci 는.. 접속계정이 달라져야 하는데, 이를 현재 감겨있는 프레임워크에서는 유연하게 변경하지 못하는 것 같네요.
		// 방법을 찾을 때 까지는, LocalSci 테스트는 불가합니다.

		MemberSegmentReq req = new MemberSegmentReq();

		req.setTestMdn( "01031155023" );
		req.setTingYn( "Y" );
		req.setUserKey( "DUMMY" );

		String postBody = new ObjectMapper().writeValueAsString(req);

		System.out.println( postBody );

		this.mvc.perform(
			post( "/display/card/memberSegment/v1" )
				.contentType(MediaType.APPLICATION_JSON)
				.content(postBody)
				.header("x-sac-tenant-id", "S01")
				.header("x-sac-system-id", "S01-01014")
		)
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(content().contentType("application/json;charset=UTF-8"))
		.andExpect(header().string("x-sac-result-code", "SUCC"))
		;
	}

}
