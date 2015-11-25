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
public class ProductInfoSCITestTest_Music {

	@Autowired
	private ProductInfoSCI productInfoSCI;

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Test
	public void testSearchMusicProductList() {
		ProductInfoSacReq req = new ProductInfoSacReq();
		List<String> list = new ArrayList<String>();
		list.add("H002143433");
		// list.add("H002693934");

		req.setList(list);
		req.setDeviceModelNo("SHW-M100S");
		req.setTenantId("S01");

		ProductInfoSacRes res = this.productInfoSCI.getProductList(req);
		List<ProductInfo> productList = res.getProductList();
		this.log.debug("##### productInfo cnt : ", productList.size());
		for (ProductInfo productInfo : productList) {
			this.log.debug("##### Music productInfo VO : {}",
					ReflectionToStringBuilder.toString(productInfo, ToStringStyle.MULTI_LINE_STYLE));
		}
        for (ProductInfo productInfo : productList) {
            this.log.debug("##### Music productInfo VO : {}",
                    ReflectionToStringBuilder.toString(productInfo, ToStringStyle.MULTI_LINE_STYLE));
        }
	}

	/**
	 * Test method for
	 * {@link com.skplanet.storeplatform.sac.display.localsci.sci.ProductInfoSCITest#searchEbookProductList()}.
	 * 
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 */
	// @Test
	// public void testSearchNormalRingProductList() throws JsonGenerationException, JsonMappingException, IOException {
	// ProductInfoSacReq req = new ProductInfoSacReq();
	// List<String> list = new ArrayList<String>();
	// list.add("H090131936");
	// list.add("H090131932");
	// list.add("H090131924");
	//
	// req.setList(list);
	// req.setDeviceModelNo("SHW-M100S");
	// req.setTenantId("S01");
	//
	// ProductInfoSacRes res = this.productInfoSCI.getProductList(req);
	// List<ProductInfo> productList = res.getProductList();
	// this.log.debug("##### productInfo cnt : ", productList.size());
	// for (ProductInfo productInfo : productList) {
	// this.log.debug("##### Ring productInfo VO : {}",
	// ReflectionToStringBuilder.toString(productInfo, ToStringStyle.MULTI_LINE_STYLE));
	// }
	// ObjectMapper objectMapper = new ObjectMapper();
	// String json = objectMapper.writeValueAsString(productList);
	// this.log.debug("##### Ring productInfo  JSON : {}", json);
	// }
	//
	// /**
	// *
	// * CT31 Test method for
	// * {@link com.skplanet.storeplatform.sac.display.localsci.sci.ProductInfoSCITest#searchEbookProductList()}.
	// *
	// * @throws IOException
	// * @throws JsonMappingException
	// * @throws JsonGenerationException
	// */
	// @Test
	// public void testSearchLongRingProductList() throws JsonGenerationException, JsonMappingException, IOException {
	// ProductInfoSacReq req = new ProductInfoSacReq();
	// List<String> list = new ArrayList<String>();
	// list.add("H090131789");
	// list.add("H090131785");
	// list.add("H090131921");
	//
	// req.setList(list);
	// req.setDeviceModelNo("SHW-M100S");
	// req.setTenantId("S01");
	//
	// ProductInfoSacRes res = this.productInfoSCI.getProductList(req);
	// List<ProductInfo> productList = res.getProductList();
	// this.log.debug("##### Long Ring productInfo cnt : ", productList.size());
	// for (ProductInfo productInfo : productList) {
	// this.log.debug("##### Long Ring productInfo VO : {}",
	// ReflectionToStringBuilder.toString(productInfo, ToStringStyle.MULTI_LINE_STYLE));
	// }
	// }
	//
	// /**
	// * CT32 Test method for
	// * {@link com.skplanet.storeplatform.sac.display.localsci.sci.ProductInfoSCITest#searchEbookProductList()}.
	// *
	// * @throws IOException
	// * @throws JsonMappingException
	// * @throws JsonGenerationException
	// */
	// @Test
	// public void testSearchNormalLiveBellProductList() throws JsonGenerationException, JsonMappingException,
	// IOException {
	// ProductInfoSacReq req = new ProductInfoSacReq();
	// List<String> list = new ArrayList<String>();
	// list.add("H090131938");
	// list.add("H090131934");
	// list.add("H090131926");
	//
	// req.setList(list);
	// req.setDeviceModelNo("SHW-M100S");
	// req.setTenantId("S01");
	//
	// ProductInfoSacRes res = this.productInfoSCI.getProductList(req);
	// List<ProductInfo> productList = res.getProductList();
	// this.log.debug("##### Normal Live Bell productInfo cnt : ", productList.size());
	// for (ProductInfo productInfo : productList) {
	// this.log.debug("##### Normal Live Bell productInfo VO : {}",
	// ReflectionToStringBuilder.toString(productInfo, ToStringStyle.MULTI_LINE_STYLE));
	// }
	// }
	//
	// /**
	// * CT33 Test method for
	// * {@link com.skplanet.storeplatform.sac.display.localsci.sci.ProductInfoSCITest#searchEbookProductList()}.
	// *
	// * @throws IOException
	// * @throws JsonMappingException
	// * @throws JsonGenerationException
	// */
	// @Test
	// public void testSearchHighQualityLiveBellProductList() throws JsonGenerationException, JsonMappingException,
	// IOException {
	// ProductInfoSacReq req = new ProductInfoSacReq();
	// List<String> list = new ArrayList<String>();
	// list.add("H090131787");
	// list.add("H090131791");
	// list.add("H090131923");
	// req.setTenantId("S01");
	//
	// req.setList(list);
	// req.setDeviceModelNo("SHW-M100S");
	//
	// ProductInfoSacRes res = this.productInfoSCI.getProductList(req);
	// List<ProductInfo> productList = res.getProductList();
	// this.log.debug("##### High Quality Live Bell productInfo cnt : ", productList.size());
	// for (ProductInfo productInfo : productList) {
	// this.log.debug("##### High Quality Live Bell productInfo VO : {}",
	// ReflectionToStringBuilder.toString(productInfo, ToStringStyle.MULTI_LINE_STYLE));
	// }
	// }

}
