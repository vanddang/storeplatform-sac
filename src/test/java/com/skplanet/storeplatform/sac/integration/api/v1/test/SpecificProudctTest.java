package com.skplanet.storeplatform.sac.integration.api.v1.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
@TransactionConfiguration
@Transactional
public class SpecificProudctTest {

	@Autowired
	// private SpecificProductListService specificProductListService;
	/**
	 * 회원 목록 테스트.
	 */
	@Test
	public void shouldOptianUserCareer() {
		String list = "test1+test2";
		String imageSizeCd = "TTTT";

		// try {
		// System.out.println("+++sadfasdfas");
		// // SpecificProductList specificProductList = this.specificProductListService.getSpecificProductList(list,
		// // imageSizeCd);
		// // System.out.println("++++++++++" + specificProductList.getProductList().get(0).getEvent());
		// } catch (JsonGenerationException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// } catch (JsonMappingException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

	}
}
