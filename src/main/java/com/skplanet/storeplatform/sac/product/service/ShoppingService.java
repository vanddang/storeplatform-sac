package com.skplanet.storeplatform.sac.product.service;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import com.skplanet.storeplatform.sac.client.product.vo.shopping.ShoppingRequest;
import com.skplanet.storeplatform.sac.client.product.vo.shopping.ShoppingResponse;

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
	 * @param ShoppingRequest
	 * @return ShoppingResponse 리스트
	 */
	public ShoppingResponse getFeatureProductList(ShoppingRequest req) throws JsonGenerationException,
			JsonMappingException, IOException, Exception;

	/**
	 * <pre>
	 * 쇼핑 신규 상품 조회 .
	 * </pre>
	 * 
	 * @param ShoppingRequest
	 * @return ShoppingResponse 리스트
	 */
	public ShoppingResponse getNewProductList(ShoppingRequest req) throws JsonGenerationException,
			JsonMappingException, IOException, Exception;

	/**
	 * <pre>
	 * 쇼핑 세부카테고리  상품 조회 .
	 * </pre>
	 * 
	 * @param ShoppingRequest
	 * @return ShoppingResponse 리스트
	 */
	public ShoppingResponse getSubProductList(ShoppingRequest req) throws JsonGenerationException,
			JsonMappingException, IOException, Exception;

	/**
	 * <pre>
	 * 특가 상품 리스트 조회.
	 * </pre>
	 * 
	 * @param ShoppingRequest
	 * @return ShoppingResponse 리스트
	 */
	public ShoppingResponse getSecialPriceProductList(ShoppingRequest req) throws JsonGenerationException,
			JsonMappingException, IOException, Exception;

	/**
	 * <pre>
	 * 기획전  상품  조회.
	 * </pre>
	 * 
	 * @param ShoppingRequest
	 * @return ShoppingResponse 리스트
	 */
	public ShoppingResponse getSpecialSalesList(ShoppingRequest req) throws JsonGenerationException,
			JsonMappingException, IOException, Exception;

	/**
	 * <pre>
	 * 특정 기획전에 대한 상품 리스트 조회.
	 * </pre>
	 * 
	 * @param ShoppingRequest
	 * @return ShoppingResponse 리스트
	 */
	public ShoppingResponse getSpecialSalesProductList(ShoppingRequest req) throws JsonGenerationException,
			JsonMappingException, IOException, Exception;

	/**
	 * <pre>
	 * 브랜드샵 - 메인 리스트 조회.
	 * </pre>
	 * 
	 * @param ShoppingRequest
	 * @return ShoppingResponse 리스트
	 */
	public ShoppingResponse getBrandshopMainList(ShoppingRequest req) throws JsonGenerationException,
			JsonMappingException, IOException, Exception;

	/**
	 * <pre>
	 * 특정 브랜드샵 상품 리스트
	 * </pre>
	 * 
	 * @param ShoppingRequest
	 * @return ShoppingResponse 리스트
	 */
	public ShoppingResponse getBrandshopProductList(ShoppingRequest req) throws JsonGenerationException,
			JsonMappingException, IOException, Exception;

	/**
	 * <pre>
	 * 쇼핑테마 리스트상품 리스트
	 * </pre>
	 * 
	 * @param ShoppingRequest
	 * @return ShoppingResponse 리스트
	 */
	public ShoppingResponse getThemeList(ShoppingRequest req) throws JsonGenerationException, JsonMappingException,
			IOException, Exception;

	/**
	 * <pre>
	 * 특정 테마 리스트상품 리스트
	 * </pre>
	 * 
	 * @param ShoppingRequest
	 * @return ShoppingResponse 리스트
	 */
	public ShoppingResponse getThemeProductList(ShoppingRequest req) throws JsonGenerationException,
			JsonMappingException, IOException, Exception;

	/**
	 * <pre>
	 *  특정 카탈로그에 대한 다른 상품 리스트
	 * </pre>
	 * 
	 * @param ShoppingRequest
	 * @return ShoppingResponse 리스트
	 */
	public ShoppingResponse getCatagoryAnotherProductList(ShoppingRequest req) throws JsonGenerationException,
			JsonMappingException, IOException, Exception;

	/**
	 * <pre>
	 * 특정 브랜드에 대한 다른 상품 리스트
	 * </pre>
	 * 
	 * @param ShoppingRequest
	 * @return ShoppingResponse 리스트
	 */
	public ShoppingResponse getBrandAnotherProductList(ShoppingRequest req) throws JsonGenerationException,
			JsonMappingException, IOException, Exception;

	/**
	 * <pre>
	 * 쇼핑상세
	 * </pre>
	 * 
	 * @param ShoppingRequest
	 * @return ShoppingResponse 리스트
	 */
	public ShoppingResponse getShoppingDetail(ShoppingRequest req) throws JsonGenerationException,
			JsonMappingException, IOException, Exception;
}
