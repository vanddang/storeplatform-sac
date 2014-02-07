package com.skplanet.storeplatform.sac.integration.api.v1.test;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.sac.client.display.vo.category.CategoryWebtoonSacReq;
import com.skplanet.storeplatform.sac.display.category.service.CategoryWebtoonService;

@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
@TransactionConfiguration
@Transactional
public class WebtoonTest {

	@Autowired
	private CategoryWebtoonService WebtoonService;

	/**
	 * 회원 목록 테스트.
	 * 
	 * @throws Exception
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 */
	@Test
	public void shouldOptianUserCareer() throws JsonGenerationException, JsonMappingException, IOException, Exception {
		CategoryWebtoonSacReq WebtoonReqVO = new CategoryWebtoonSacReq();
		System.out.println("++++dfsdfsd++");
		// CategoryWebtoonRes voRes = this.WebtoonService.getWebtoonList(WebtoonReqVO);
		// System.out.println("++++++" + voRes.getWebtoonList().size());
		// for (Product vo : voRes.getProductList()) {
		// // System.out.println("++++++" + vo.getProduct().getSupport());
		// System.out.println("++++++" + vo.getIdentifier().getText());
		// System.out.println("++++++" + vo.getIdentifier().getType());
		// }
	}
}
