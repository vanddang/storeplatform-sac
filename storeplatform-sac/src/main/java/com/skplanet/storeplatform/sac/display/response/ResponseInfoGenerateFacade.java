/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.response;

import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Coupon;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.display.cache.vo.AlbumMeta;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;

/**
 * Response 객체 Generate Facade
 * 
 * Updated on : 2014. 1. 27. Updated by : 오승민, 인크로스
 */
public interface ResponseInfoGenerateFacade {

	/**
	 * <pre>
	 * App 상품 Product 객체 생성.
	 * </pre>
	 * 
	 * @param metaInfo
	 *            metaInfo
	 * 
	 * @return Product
	 */
	public Product generateAppProduct(MetaInfo metaInfo);

	/**
	 * <pre>
	 * App 상품 Product 객체 생성.
	 * </pre>
	 * 
	 * @param metaInfo
	 *            metaInfo
	 * 
	 * @return Product
	 */
	public Product generateAppProductShort(MetaInfo metaInfo);

	/**
	 * <pre>
	 * Music 상품 Product 생성.
	 * </pre>
	 * 
	 * @param metaInfo
	 *            metaInfo
	 * 
	 * @return Product
	 */
	public Product generateMusicProduct(MetaInfo metaInfo);

	/**
	 * <pre>
	 * Music 상품 Product 생성.
	 * </pre>
	 * 
	 * @param albumMeta
	 *            metaInfo
	 * 
	 * @return Product
	 */
	public Product generateAlbumProduct(AlbumMeta albumMeta);

	/**
	 * <pre>
	 * Music 상품 Product 생성.
	 * </pre>
	 * 
	 * @param albumMeta
	 *            metaInfo
	 * 
	 * @return Product
	 */
	public Product generateAlbumDetailProduct(AlbumMeta albumMeta);

	/**
	 * <pre>
	 * Music 상품 Product 생성.
	 * </pre>
	 * 
	 * @param metaInfo
	 *            metaInfo
	 * 
	 * @return Product
	 */
	public Product generateMusicProductShort(MetaInfo metaInfo);

	/**
	 * <pre>
	 * Movie 상품 Product 생성.
	 * </pre>
	 * 
	 * @param metaInfo
	 *            metaInfo
	 * @return Product
	 */
	public Product generateMovieProduct(MetaInfo metaInfo);

	/**
	 * <pre>
	 * Movie 상품 Product 생성.
	 * </pre>
	 * 
	 * @param metaInfo
	 *            metaInfo
	 * @return Product
	 */
	public Product generateMovieProductShort(MetaInfo metaInfo);

	/**
	 * <pre>
	 * 방송 상품 Product 생성.
	 * </pre>
	 * 
	 * @param metaInfo
	 *            metaInfo
	 * @return Product
	 */
	public Product generateBroadcastProduct(MetaInfo metaInfo);

	/**
	 * <pre>
	 * 방송 상품 Product 생성.
	 * </pre>
	 * 
	 * @param metaInfo
	 *            metaInfo
	 * @return Product
	 */
	public Product generateBroadcastProductShort(MetaInfo metaInfo);

	/**
	 * <pre>
	 * Ebook 상품 Product 생성.
	 * </pre>
	 * 
	 * @param metaInfo
	 *            metaInfo
	 * @return Product
	 */
	public Product generateEbookProduct(MetaInfo metaInfo);

	/**
	 * <pre>
	 * Ebook 상품 Product 생성.
	 * </pre>
	 * 
	 * @param metaInfo
	 *            metaInfo
	 * @return Product
	 */
	public Product generateEbookProductShort(MetaInfo metaInfo);

	/**
	 * <pre>
	 *  Comic 상품 Product 생성.
	 * </pre>
	 * 
	 * @param metaInfo
	 *            metaInfo
	 * @return Product
	 */
	public Product generateComicProduct(MetaInfo metaInfo);

	/**
	 * <pre>
	 *  Comic 상품 Product 생성.
	 * </pre>
	 * 
	 * @param metaInfo
	 *            metaInfo
	 * @return Product
	 */
	public Product generateComicProductShort(MetaInfo metaInfo);

	/**
	 * <pre>
	 *  Webtoon 상품 Product 생성.
	 * </pre>
	 * 
	 * @param metaInfo
	 *            metaInfo
	 * @return Product
	 */
	public Product generateWebtoonProduct(MetaInfo metaInfo);

	/**
	 * <pre>
	 * Shopping 상품 Product 생성.
	 * </pre>
	 * 
	 * @param metaInfo
	 *            metaInfo
	 * @return Product
	 */
	public Product generateShoppingProduct(MetaInfo metaInfo);

	/**
	 * <pre>
	 * Shopping 상품 Product 생성.
	 * </pre>
	 * 
	 * @param metaInfo
	 *            metaInfo
	 * @return Product
	 */
	public Product generateShoppingProductShort(MetaInfo metaInfo);

	/**
	 * <pre>
	 * 특정 상품 조회 API용 App 상품 Product 생성.
	 * </pre>
	 * 
	 * @param metaInfo
	 *            metaInfo
	 * @return Product
	 */
	public Product generateSpecificAppProduct(MetaInfo metaInfo);

	/**
	 * <pre>
	 * 특정 상품 조회 API용 Music 상품 Product 생성.
	 * </pre>
	 * 
	 * @param metaInfo
	 *            metaInfo
	 * @return Product
	 */
	public Product generateSpecificMusicProduct(MetaInfo metaInfo);

	/**
	 * <pre>
	 * 특정 상품 조회 API용 Movie 상품 Product 생성.
	 * </pre>
	 * 
	 * @param metaInfo
	 *            metaInfo
	 * @return Product
	 */
	public Product generateSpecificMovieProduct(MetaInfo metaInfo);

	/**
	 * <pre>
	 * 특정 상품 조회 API용 방송 상품 Product 생성.
	 * </pre>
	 * 
	 * @param metaInfo
	 *            metaInfo
	 * @return Product
	 */
	public Product generateSpecificBroadcastProduct(MetaInfo metaInfo);

	/**
	 * <pre>
	 * 특정 상품 조회 API용 Ebook 상품 Product 생성.
	 * </pre>
	 * 
	 * @param metaInfo
	 *            metaInfo
	 * @return Product
	 */
	public Product generateSpecificEbookProduct(MetaInfo metaInfo);

	/**
	 * <pre>
	 * 특정 상품 조회 API용 Comic 상품 Product 생성.
	 * </pre>
	 * 
	 * @param metaInfo
	 *            metaInfo
	 * @return Product
	 */
	public Product generateSpecificComicProduct(MetaInfo metaInfo);

	/**
	 * <pre>
	 * 특정 상품 조회 API용 Webtoon 상품 Product 생성.
	 * </pre>
	 * 
	 * @param metaInfo
	 *            metaInfo
	 * @return Product
	 */
	public Product generateSpecificWebtoonProduct(MetaInfo metaInfo);

	/**
	 * <pre>
	 * 특정 상품 조회 API용 Shopping 상품 Product 생성.
	 * </pre>
	 * 
	 * @param metaInfo
	 *            metaInfo
	 * @return Product
	 */
	public Product generateSpecificShoppingProduct(MetaInfo metaInfo);

	/**
	 * <pre>
	 * 정액제 상품 Coupon 객체생성.
	 * </pre>
	 * 
	 * @param metaInfo
	 *            metaInfo
	 * @return Coupon
	 */
	public Coupon generateFreepassProduct(MetaInfo metaInfo);

	/**
	 * <pre>
	 * 정액제 상품 Coupon 객체생성.
	 * </pre>
	 * 
	 * @param metaInfo
	 *            metaInfo
	 * @return Coupon
	 */
	public Coupon generateVoucherProduct(MetaInfo metaInfo);
}
