package com.skplanet.storeplatform.sac.display.shopping.service;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import com.skplanet.storeplatform.sac.client.display.vo.shopping.ShoppingReq;
import com.skplanet.storeplatform.sac.client.display.vo.shopping.ShoppingRes;

/**
 * 쇼핑 Service 인터페이스(CoreStoreBusiness)
 * 
 * Updated on : 2013. 12. 23. Updated by : 김형식, SK 플래닛.
 */
public interface ShoppingService {

	/**
	 * <pre>
	 * 쇼핑 추천/인기 상품 리스트 조회.
	 * </pre>
	 * 
	 * @param ShoppingReq
	 * @return ShoppingRes 리스트
	 */
	public ShoppingRes getFeatureProductList(ShoppingReq req);

	/**
	 * <pre>
	 * 쇼핑 신규 상품 조회 .
	 * </pre>
	 * 
	 * @param ShoppingReq
	 * @return ShoppingRes 리스트
	 */
	public ShoppingRes getNewProductList(ShoppingReq req) throws JsonGenerationException, JsonMappingException,
			IOException, Exception;

	/**
	 * <pre>
	 * 쇼핑 세부카테고리  상품 조회 .
	 * </pre>
	 * 
	 * @param ShoppingReq
	 * @return ShoppingRes 리스트
	 */
	public ShoppingRes getSubProductList(ShoppingReq req) throws JsonGenerationException, JsonMappingException,
			IOException, Exception;

	/**
	 * <pre>
	 * 특가 상품 리스트 조회.
	 * </pre>
	 * 
	 * @param ShoppingReq
	 * @return ShoppingRes 리스트
	 */
	public ShoppingRes getSecialPriceProductList(ShoppingReq req) throws JsonGenerationException, JsonMappingException,
			IOException, Exception;

	/**
	 * <pre>
	 * 기획전  상품  조회.
	 * </pre>
	 * 
	 * @param ShoppingReq
	 * @return ShoppingRes 리스트
	 */
	public ShoppingRes getSpecialSalesList(ShoppingReq req) throws JsonGenerationException, JsonMappingException,
			IOException, Exception;

	/**
	 * <pre>
	 * 특정 기획전에 대한 상품 리스트 조회.
	 * </pre>
	 * 
	 * @param ShoppingReq
	 * @return ShoppingRes 리스트
	 */
	public ShoppingRes getSpecialSalesProductList(ShoppingReq req) throws JsonGenerationException,
			JsonMappingException, IOException, Exception;

	/**
	 * <pre>
	 * 브랜드샵 - 메인 리스트 조회.
	 * </pre>
	 * 
	 * @param ShoppingReq
	 * @return ShoppingRes 리스트
	 */
	public ShoppingRes getBrandshopMainList(ShoppingReq req) throws JsonGenerationException, JsonMappingException,
			IOException, Exception;

	/**
	 * <pre>
	 * 특정 브랜드샵 상품 리스트
	 * </pre>
	 * 
	 * @param ShoppingReq
	 * @return ShoppingRes 리스트
	 */
	public ShoppingRes getBrandshopProductList(ShoppingReq req) throws JsonGenerationException, JsonMappingException,
			IOException, Exception;

	/**
	 * <pre>
	 * 쇼핑테마 리스트상품 리스트
	 * </pre>
	 * 
	 * @param ShoppingReq
	 * @return ShoppingRes 리스트
	 */
	public ShoppingRes getThemeList(ShoppingReq req) throws JsonGenerationException, JsonMappingException, IOException,
			Exception;

	/**
	 * <pre>
	 * 특정 테마 리스트상품 리스트
	 * </pre>
	 * 
	 * @param ShoppingReq
	 * @return ShoppingRes 리스트
	 */
	public ShoppingRes getThemeProductList(ShoppingReq req) throws JsonGenerationException, JsonMappingException,
			IOException, Exception;

	/**
	 * <pre>
	 *  특정 카탈로그에 대한 다른 상품 리스트
	 * </pre>
	 * 
	 * @param ShoppingReq
	 * @return ShoppingRes 리스트
	 */
	public ShoppingRes getCatagoryAnotherProductList(ShoppingReq req) throws JsonGenerationException,
			JsonMappingException, IOException, Exception;

	/**
	 * <pre>
	 * 특정 브랜드에 대한 다른 상품 리스트
	 * </pre>
	 * 
	 * @param ShoppingReq
	 * @return ShoppingRes 리스트
	 */
	public ShoppingRes getBrandAnotherProductList(ShoppingReq req) throws JsonGenerationException,
			JsonMappingException, IOException, Exception;

	/**
	 * <pre>
	 * 쇼핑상세
	 * </pre>
	 * 
	 * @param ShoppingReq
	 * @return ShoppingRes 리스트
	 */
	public ShoppingRes getShoppingDetail(ShoppingReq req) throws JsonGenerationException, JsonMappingException,
			IOException, Exception;
}
