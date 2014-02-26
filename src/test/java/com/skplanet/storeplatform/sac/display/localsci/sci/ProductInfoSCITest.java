package com.skplanet.storeplatform.sac.display.localsci.sci;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
public class ProductInfoSCITest {

	@Autowired
	private ProductInfoSCI productInfoSCI;

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	/**
	 * 
	 * <pre>
	 * App 정보 조회.
	 * </pre>
	 * 
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 */
	@Test
	public void searchAppProductList() throws JsonGenerationException, JsonMappingException, IOException {
		ProductInfoSacReq req = new ProductInfoSacReq();
		List<String> list = new ArrayList<String>();
		list.add("0000018125");
		list.add("0000044857");
		list.add("0000048923");
		req.setList(list);
		req.setDeviceModelNo("SHW-M100S");

		ProductInfoSacRes res = this.productInfoSCI.getProductList(req);
		List<ProductInfo> productList = res.getProductList();
		this.log.debug("##### productInfo cnt : ", productList.size());
		ObjectMapper objectMapper = new ObjectMapper();
		String json = objectMapper.writeValueAsString(productList);
		this.log.debug("##### App productInfo : {}", json);

	}

	/**
	 * 
	 * <pre>
	 * 방송 정보 조회.
	 * </pre>
	 * 
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 */
	@Test
	public void searchBroadCastProductList() throws JsonGenerationException, JsonMappingException, IOException {
		ProductInfoSacReq req = new ProductInfoSacReq();
		List<String> list = new ArrayList<String>();
		list.add("H000044277");
		list.add("H000043978");
		list.add("H000043950");
		req.setList(list);
		req.setDeviceModelNo("SHW-M100S");

		ProductInfoSacRes res = this.productInfoSCI.getProductList(req);
		List<ProductInfo> productList = res.getProductList();
		this.log.debug("##### productInfo cnt : ", productList.size());
		ObjectMapper objectMapper = new ObjectMapper();
		String json = objectMapper.writeValueAsString(productList);
		this.log.debug("##### Broadcast productInfo : {}", json);

	}
}
