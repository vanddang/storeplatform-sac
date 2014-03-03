/**
 * 
 */
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
 * ProductInfo SCI Test
 * 
 * 구매 내역 조회 시 필요한 상품 메타 정보 조회
 * 
 * Updated on : 2014. 2. 24. Updated by : 오승민, 인크로스
 */
@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
@Transactional
public class ProductInfoSCITestTest_App {

	@Autowired
	private ProductInfoSCI productInfoSCI;

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	/**
	 * Test method for
	 * {@link com.skplanet.storeplatform.sac.display.localsci.sci.ProductInfoSCITest#searchEbookProductList()}.
	 * 
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 */
	@Test
	public void testSearchAppProductList() throws JsonGenerationException, JsonMappingException, IOException {
		ProductInfoSacReq req = new ProductInfoSacReq();
		List<String> list = new ArrayList<String>();
		list.add("0000018125");
		list.add("0000044857");
		list.add("0000048923");
		// list.add("0000632864");
		// list.add("0000633035");
		// list.add("0000633039");
		// list.add("0000633066");
		// list.add("0000633069");
		// list.add("0000633073");
		// list.add("0000633075");
		// list.add("0000633080");
		// list.add("0000633083");
		// list.add("0000633084");
		// list.add("0000633090");
		// list.add("0000633106");
		// list.add("0000633107");
		// list.add("0000633108");
		// list.add("0000633109");
		// list.add("0000633116");
		// list.add("0000633135");
		// list.add("0000633137");
		// list.add("0000633138");
		// list.add("0000633139");
		// list.add("0000633146");
		// list.add("0000633158");
		// list.add("0000633185");
		// list.add("0000633199");
		// list.add("0000633209");
		// list.add("0000633223");
		// list.add("0000633233");
		// list.add("0000633242");
		// list.add("0000633244");
		// list.add("0000633246");
		// list.add("0000633248");
		// list.add("0000633251");
		// list.add("0000633254");
		// list.add("0000633258");
		// list.add("0000633265");
		// list.add("0000633271");
		// list.add("0000633272");
		// list.add("0000633279");
		// list.add("0000633280");
		// list.add("0000633306");
		// list.add("0000633308");
		// list.add("0000633310");
		// list.add("0000633311");
		// list.add("0000633345");
		// list.add("0000633348");
		// list.add("0000633366");
		// list.add("0000633373");
		// list.add("0000633407");
		// list.add("0000633411");
		// list.add("0000633418");
		// list.add("0000633421");
		// list.add("0000633461");
		// list.add("0000633508");
		// list.add("0000633514");
		req.setList(list);
		req.setDeviceModelNo("SHW-M100S");

		ProductInfoSacRes res = this.productInfoSCI.getProductList(req);
		List<ProductInfo> productList = res.getProductList();
		this.log.debug("##### productInfo cnt : ", productList.size());
		for (ProductInfo productInfo : productList) {
			this.log.debug("##### App productInfo VO : {}",
					ReflectionToStringBuilder.toString(productInfo, ToStringStyle.MULTI_LINE_STYLE));
		}
		ObjectMapper objectMapper = new ObjectMapper();
		String json = objectMapper.writeValueAsString(productList);
		this.log.debug("##### App productInfo  JSON : {}", json);
	}

}
