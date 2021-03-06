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
public class ProductInfoSCITestTest_Ebook {

	@Autowired
	private ProductInfoSCI productInfoSCI;

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Test
	public void testSearchEbookProductList() {
		ProductInfoSacReq req = new ProductInfoSacReq();
		List<String> list = new ArrayList<String>();
		list.add("H001380128");
		list.add("H001390492");
		list.add("H001402272");
		list.add("H001415928");
		list.add("H001474407");
		req.setList(list);
		req.setDeviceModelNo("SHW-M100S");
		req.setTenantId("S01");

		ProductInfoSacRes res = this.productInfoSCI.getProductList(req);
		List<ProductInfo> productList = res.getProductList();
		this.log.debug("##### productInfo cnt : ", productList.size());
		for (ProductInfo productInfo : productList) {
			this.log.debug("##### Ebook productInfo VO : {}",
					ReflectionToStringBuilder.toString(productInfo, ToStringStyle.MULTI_LINE_STYLE));
		}
	}

	@Test
	public void testSearchComicProductList() {
		ProductInfoSacReq req = new ProductInfoSacReq();
		List<String> list = new ArrayList<String>();
		list.add("H900005941");
		list.add("H900006037");
		list.add("H900006041");
		list.add("H900008864");
		list.add("H900006019");
		req.setList(list);
		req.setDeviceModelNo("SHW-M100S");
		req.setTenantId("S01");

		ProductInfoSacRes res = this.productInfoSCI.getProductList(req);
		List<ProductInfo> productList = res.getProductList();
		this.log.debug("##### productInfo cnt : ", productList.size());
		for (ProductInfo productInfo : productList) {
			this.log.debug("##### Comic productInfo VO : {}",
					ReflectionToStringBuilder.toString(productInfo, ToStringStyle.MULTI_LINE_STYLE));
		}
	}

}
