package com.skplanet.storeplatform.sac.display.shopping.service;

import com.skplanet.storeplatform.sac.client.display.vo.shopping.ShoppingReq;
import com.skplanet.storeplatform.sac.client.display.vo.shopping.ShoppingRes;
import com.skplanet.storeplatform.sac.client.display.vo.shopping.ShoppingThemeRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * <pre>
 * 쇼핑 서비스 인터페이스
 * </pre>
 * 
 * Created on : 2013-12-23 Created by : 김형식, SK 플래닛. Last Updated on : 2013-12-23 Last Updated by : 김형식, SK 플래닛
 */
public interface ShoppingService {

	/**
	 * <pre>
	 * 쇼핑 추천/인기 상품 리스트 조회.
	 * </pre>
	 */
	public ShoppingRes getFeatureProductList(SacRequestHeader header, ShoppingReq req);

	/**
	 * <pre>
	 * 쇼핑 신규 상품 조회 .
	 * </pre>
	 */
	public ShoppingRes getNewProductList(SacRequestHeader header, ShoppingReq req);

	/**
	 * <pre>
	 * 쇼핑 세부카테고리  상품 조회 .
	 * </pre>
	 */
	public ShoppingRes getSubProductList(SacRequestHeader header, ShoppingReq req);

	/**
	 * <pre>
	 * 특가 상품 리스트 조회.
	 * </pre>
	 */
	public ShoppingRes getSecialPriceProductList(SacRequestHeader header, ShoppingReq req);

	/**
	 * <pre>
	 * 기획전  상품  조회.
	 * </pre>
	 */
	public ShoppingThemeRes getSpecialSalesList(SacRequestHeader header, ShoppingReq req);

	/**
	 * <pre>
	 * 특정 기획전에 대한 상품 리스트 조회.
	 * </pre>
	 */
	public ShoppingThemeRes getSpecialSalesProductList(SacRequestHeader header, ShoppingReq req);

	/**
	 * <pre>
	 * 브랜드샵 - 메인 리스트 조회.
	 * </pre>
	 */
	public ShoppingRes getBrandshopMainList(SacRequestHeader header, ShoppingReq req);

	/**
	 * <pre>
	 * 특정 브랜드샵 상품 리스트
	 * </pre>
	 */
	public ShoppingRes getBrandshopProductList(SacRequestHeader header, ShoppingReq req);

	/**
	 * <pre>
	 * 쇼핑테마 리스트상품 리스트
	 * </pre>
	 */
	public ShoppingRes getThemeList(SacRequestHeader header, ShoppingReq req);

	/**
	 * <pre>
	 * 특정 테마 리스트상품 리스트
	 * </pre>
	 */
	public ShoppingRes getThemeProductList(SacRequestHeader header, ShoppingReq req);

	/**
	 * <pre>
	 *  특정 카탈로그에 대한 다른 상품 리스트
	 * </pre>
	 */
	public ShoppingRes getCatagoryAnotherProductList(SacRequestHeader header, ShoppingReq req);

	/**
	 * <pre>
	 * 특정 브랜드에 대한 다른 상품 리스트
	 * </pre>
	 */
	public ShoppingRes getBrandAnotherProductList(SacRequestHeader header, ShoppingReq req);

	/**
	 * <pre>
	 * 쇼핑상세
	 * </pre>
	 */
	public ShoppingRes getShoppingDetail(SacRequestHeader header, ShoppingReq req);
}
