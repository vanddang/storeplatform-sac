package com.skplanet.storeplatform.sac.integration.api.v1.test;

import org.junit.Test;
import org.junit.runner.RunWith;
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
public class ProductCatgoryTest {

	// @Autowired
	// private ProductCategoryService productCategoryService;

	/**
	 * 회원 목록 테스트.
	 */
	@Test
	public void shouldOptianUserCareer() {
		// ProductCategoryRequest productCategoryReqVO = new ProductCategoryRequest();
		// ProductCategoryResponse voRes = this.productCategoryService.searchCategoryProductList(productCategoryReqVO);
		// // System.out.println("++++++" + voRes.getProductCategoryList().size());
		// for (Product vo : voRes.getProductList()) {
		// // System.out.println("++++++" + vo.getProduct().getSupport());
		// System.out.println("++++++" + vo.getIdentifier().getText());
		// System.out.println("++++++" + vo.getIdentifier().getType());
		// }
	}
}
