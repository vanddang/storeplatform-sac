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

import com.skplanet.storeplatform.sac.client.product.vo.category.WebtoonRequest;
import com.skplanet.storeplatform.sac.client.product.vo.category.WebtoonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.product.service.WebtoonService;

@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
@TransactionConfiguration
@Transactional
public class WebtoonTest {

	@Autowired
	private WebtoonService WebtoonService;

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
		WebtoonRequest WebtoonReqVO = new WebtoonRequest();
		System.out.println("++++dfsdfsd++");
		WebtoonResponse voRes = this.WebtoonService.getWebtoonList(WebtoonReqVO);
		// System.out.println("++++++" + voRes.getWebtoonList().size());
		for (Product vo : voRes.getProductList()) {
			// System.out.println("++++++" + vo.getProduct().getSupport());
			System.out.println("++++++" + vo.getIdentifier().getText());
			System.out.println("++++++" + vo.getIdentifier().getType());
		}
	}
}
