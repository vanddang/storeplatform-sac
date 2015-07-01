/**
 *
 */
package com.skplanet.storeplatform.sac.display.card.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.PrintingResultHandler;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.WebApplicationContext;

import com.skplanet.storeplatform.sac.client.display.vo.card.CardListInPanelReq;
import com.skplanet.storeplatform.sac.client.display.vo.card.SegmentReq;

/**
 * 패널 테스트
 *
 * Updated on : 2015. 5. 18.
 * Updated by : 1002159
 */
@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class CardListControllerTest {

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mvc;

	@Before
	public void before() {
		this.mvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Test
	public void basic() throws Exception {

		SegmentReq segmentReq = new SegmentReq();

		segmentReq.setOutsdMbrGrdCd( "1" );
		segmentReq.setInsdMbrGrdCd( "Z6" );
		segmentReq.setSex( "M" );
		segmentReq.setAgeClsfCd( "18" );
		segmentReq.setDeviceChgYn( "N" );
		segmentReq.setNewEntryYn( "N" );
		segmentReq.setMnoClsfCd( "US001201" );
		segmentReq.setCategoryBest( Arrays.asList( "DP30" ) );
		segmentReq.setTestMdnYn( "N" );

		CardListInPanelReq req = new CardListInPanelReq();

		req.setUserKey( "Merong" );
		req.setPanelId( "PN00002"   );
//		req.setUseGrdCd( "PD004401" );
		req.setSegment( segmentReq );


		String postBody = new ObjectMapper().writeValueAsString( req );

		ResponseGreper responseGreper = new ResponseGreper();

		this.mvc.perform(
				post("/display/card/listInPanel/v1")
						.contentType(MediaType.APPLICATION_JSON)
						.content(postBody)
						.header("x-sac-tenant-id", "S01")
						.header("x-sac-system-id", "S01-01014")
		)
		.andDo( responseGreper )
		.andExpect(status().isOk())
		.andExpect(content().contentType("application/json;charset=UTF-8"))
		.andExpect(header().string("x-sac-result-code", "SUCC"))
		;
	}

	/** An {@link PrintingResultHandler} that writes to the "standard" output stream */
	private static class ResponseGreper extends PrintingResultHandler {

		public ResponseGreper() {
			super(new ResultValuePrinter() {

				public void printHeading(String heading) {
					System.out.println();
					System.out.println(String.format("%20s:", heading));
				}

				public void printValue(String label, Object value) {
					if (value != null && value.getClass().isArray()) {
						value = CollectionUtils.arrayToList(value);
					}
					System.out.println(String.format("%20s = %s", label, value));
				}
			});
		}
	}

	@Test
	public void provisionUseGrdCd() throws Exception {

		CardListInPanelReq req = new CardListInPanelReq();

		req.setPanelId( "PN14002"   );
		req.setUseGrdCd( "PD004400" ); // 없는 코드지만... 0건이 나와야 함

		String postBody = new ObjectMapper().writeValueAsString( req );

		this.mvc.perform(
				post( "/display/card/listInPanel/v1" )
				.contentType( MediaType.APPLICATION_JSON )
				.content( postBody )
				.header( "x-sac-tenant-id", "S01" )
				.header( "x-sac-system-id", "S01-01014" )
				)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andExpect(header().string("x-sac-result-code", "SUCC"))
				;
	}

	@Test
	public void provisionTing() throws Exception {

		SegmentReq segmentReq = new SegmentReq();
		segmentReq.setTestMdnYn( "N" );
		segmentReq.setTingYn( "N" );
		segmentReq.setOutsdMbrGrdCd( "[]" );

		CardListInPanelReq req = new CardListInPanelReq();

		req.setPanelId( "PN00002"   );
		req.setUseGrdCd( "PD004401" );
		req.setSegment( segmentReq );


		String postBody = new ObjectMapper().writeValueAsString( req );

		this.mvc.perform(
				post( "/display/card/listInPanel/v1" )
				.contentType( MediaType.APPLICATION_JSON )
				.content( postBody )
				.header( "x-sac-tenant-id", "S01" )
				.header( "x-sac-system-id", "S01-01014" )
		)
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(content().contentType("application/json;charset=UTF-8"))
		.andExpect(header().string("x-sac-result-code", "SUCC"))
		;
	}

}
