package com.skplanet.storeplatform.sac.display.shopping.service;

import com.skplanet.storeplatform.sac.client.display.vo.shopping.*;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.PaymentInfo;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.PaymentInfoSacReq;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

import java.util.List;

/**
 * <pre>
 * 쇼핑 서비스 인터페이스
 * </pre>
 * 
 * Created on : 2013-12-23 Created by : 김형식, SK 플래닛. Last Updated on : 2013-12-23 Last Updated by : 김형식, SK 플래닛
 */
public interface ShoppingService {
	/**
	 * 쇼핑 추천/인기 상품 리스트 조회.
	 * 
	 * @param header
	 *            header
	 * @param req
	 *            req
	 * @return ShoppingRes
	 */

	public ShoppingRes getFeatureProductList(SacRequestHeader header, ShoppingFeatureReq req);

	/**
	 * 쇼핑 신규 상품 조회.
	 * 
	 * @param header
	 *            header
	 * @param req
	 *            req
	 * @return ShoppingRes
	 */
	public ShoppingRes getNewProductList(SacRequestHeader header, ShoppingReq req);

	/**
	 * 쇼핑 세부카테고리 상품 조회 .
	 * 
	 * @param header
	 *            header
	 * @param req
	 *            req
	 * @return ShoppingRes
	 */
	public ShoppingRes getSubProductList(SacRequestHeader header, ShoppingSubReq req);

	/**
	 * 특가 상품 리스트 조회.
	 * 
	 * @param header
	 *            header
	 * @param req
	 *            req
	 * @return ShoppingRes
	 */
	public ShoppingRes getSpecialPriceProductList(SacRequestHeader header, ShoppingReq req);

	/**
	 * 기획전 상품 조회.
	 * 
	 * @param header
	 *            header
	 * @param req
	 *            req
	 * @return ShoppingSpcialSaleRes
	 */
	public ShoppingSpcialSaleRes getSpecialSalesList(SacRequestHeader header, ShoppingReq req);

	/**
	 * 특정 기획전에 대한 상품 리스트 조회.
	 * 
	 * @param header
	 *            header
	 * @param req
	 *            req
	 * @return ShoppingRes
	 */
	public ShoppingThemeRes getSpecialSalesProductList(SacRequestHeader header, ShoppingPlanReq req);

	/**
	 * 브랜드샵 - 메인 리스트 조회.
	 * 
	 * @param header
	 *            header
	 * @param req
	 *            req
	 * @return ShoppingRes
	 */
	public ShoppingRes getBrandshopMainList(SacRequestHeader header, ShoppingReq req);

	/**
	 * 특정 브랜드샵 상품 리스트.
	 * 
	 * @param header
	 *            header
	 * @param req
	 *            req
	 * @return ShoppingRes
	 */
	public ShoppingBrandRes getBrandshopProductList(SacRequestHeader header, ShoppingBrandReq req);

	/**
	 * 쇼핑테마 리스트상품 리스트.
	 * 
	 * @param header
	 *            header
	 * @param req
	 *            req
	 * @return ShoppingRes
	 */
	public ShoppingRes getThemeList(SacRequestHeader header, ShoppingReq req);

	/**
	 * 특정 테마 리스트상품 리스트.
	 * 
	 * @param header
	 *            header
	 * @param req
	 *            req
	 * @return ShoppingRes
	 */
	public ShoppingBrandRes getThemeProductList(SacRequestHeader header, ShoppingThemeReq req);

	/**
	 * 특정 카탈로그에 대한 다른 상품 리스트.
	 * 
	 * @param header
	 *            header
	 * @param req
	 *            req
	 * @return ShoppingRes
	 */
	public ShoppingRes getCatagoryAnotherProductList(SacRequestHeader header, ShoppingCategoryAnotherReq req);

	/**
	 * 특정 브랜드에 대한 다른 상품 리스트.
	 * 
	 * @param header
	 *            header
	 * @param req
	 *            req
	 * @return ShoppingRes
	 */
	public ShoppingRes getBrandAnotherProductList(SacRequestHeader header, ShoppingBrandAnotherReq req);

	/**
	 * 쇼핑상세.
	 * 
	 * @param header
	 *            header
	 * @param req
	 *            req
	 * @return ShoppingRes
	 */
	public ShoppingDetailRes getShoppingDetail(SacRequestHeader header, ShoppingDetailReq req);

	/**
	 * 구매내역 필요한 정보.
	 * 
	 * @param req
	 *            req
	 * @return PaymentInfoSacReq
	 */
	public List<PaymentInfo> getShoppingforPayment(PaymentInfoSacReq req);
}
