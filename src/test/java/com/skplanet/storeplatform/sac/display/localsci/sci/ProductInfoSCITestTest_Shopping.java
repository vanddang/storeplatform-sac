package com.skplanet.storeplatform.sac.display.localsci.sci;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.sac.client.internal.display.localsci.sci.ProductInfoSCI;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.ProductInfo;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.ProductInfoSacReq;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.ProductInfoSacRes;

/**
 * 
 * FreePassInfo SCI Test
 * 
 * 정액제 상품 DRM 메타 정보 조회
 * 
 * Updated on : 2014. 2. 28. Updated by : 김형식 , 지티소프트
 */
@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
@Transactional
public class ProductInfoSCITestTest_Shopping {

	@Autowired
	private ProductInfoSCI productInfoSCI;

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	/**
	 * Test method for
	 * {@link com.skplanet.storeplatform.sac.display.localsci.sci.ProductInfoSCITest#testSearchFreePassProductList()}.
	 * 
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 */
	@Test
	public void testSearchFreePassProductList() throws JsonGenerationException, JsonMappingException, IOException {
		ProductInfoSacReq req = new ProductInfoSacReq();
		List<String> list = new ArrayList<String>();
		list.add("H900565545");
		req.setList(list);
		req.setDeviceModelNo("SHW-M100S");

		ProductInfoSacRes res = this.productInfoSCI.getProductList(req);
		List<ProductInfo> productList = res.getProductList();
		this.log.debug("##### productInfo cnt : ", productList.size());
		for (ProductInfo productInfo : productList) {
			this.log.debug("##### FreePass productInfo VO : {}",
					ReflectionToStringBuilder.toString(productInfo, ToStringStyle.MULTI_LINE_STYLE));
		}
		ObjectMapper objectMapper = new ObjectMapper();
		String json = objectMapper.writeValueAsString(productList);
		this.log.debug("##### FreePass productInfo  JSON : {}", json);
	}

	/**
	 * Test method for
	 * {@link com.skplanet.storeplatform.sac.display.localsci.sci.ProductInfoSCITest#testSearchShoppingProductList()}.
	 * 
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 */
	// @Test
	public void testSearchShoppingProductList() throws JsonGenerationException, JsonMappingException, IOException {
		ProductInfoSacReq req = new ProductInfoSacReq();
		List<String> list = new ArrayList<String>();
		list.add("H900065860");
		req.setList(list);
		req.setDeviceModelNo("SHW-M100S");

		ProductInfoSacRes res = this.productInfoSCI.getProductList(req);
		List<ProductInfo> productList = res.getProductList();
		this.log.debug("##### productInfo cnt : ", productList.size());
		for (ProductInfo productInfo : productList) {
			this.log.debug("##### Shopping productInfo VO : {}",
					ReflectionToStringBuilder.toString(productInfo, ToStringStyle.MULTI_LINE_STYLE));
		}
		ObjectMapper objectMapper = new ObjectMapper();
		String json = objectMapper.writeValueAsString(productList);
		this.log.debug("##### Shopping productInfo  JSON : {}", json);
	}
}
