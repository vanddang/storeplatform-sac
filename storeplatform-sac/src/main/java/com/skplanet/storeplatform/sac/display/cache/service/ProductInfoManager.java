/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.cache.service;

import com.skplanet.storeplatform.sac.display.cache.vo.AlbumMeta;
import com.skplanet.storeplatform.sac.display.cache.vo.AppMeta;
import com.skplanet.storeplatform.sac.display.cache.vo.EbookComicMeta;
import com.skplanet.storeplatform.sac.display.cache.vo.FreepassMeta;
import com.skplanet.storeplatform.sac.display.cache.vo.MenuInfo;
import com.skplanet.storeplatform.sac.display.cache.vo.MusicMeta;
import com.skplanet.storeplatform.sac.display.cache.vo.ProductBaseInfo;
import com.skplanet.storeplatform.sac.display.cache.vo.ProductStats;
import com.skplanet.storeplatform.sac.display.cache.vo.ShoppingMeta;
import com.skplanet.storeplatform.sac.display.cache.vo.SubContent;
import com.skplanet.storeplatform.sac.display.cache.vo.VodMeta;
import com.skplanet.storeplatform.sac.display.cache.vo.VoucherMeta;
import com.skplanet.storeplatform.sac.display.cache.vo.WebtoonMeta;

/**
 * 상품 메타정보 조회 서비스
 *
 * Updated on : 2016. 01. 08 Updated by : 정화수, SK 플래닛.
 *
 */
public interface ProductInfoManager {

	/**
	 * 상품 기본속성을 가져온다.
	 *
	 * @param prodId 상품ID
	 * @return 상품 기본속성
	 */
	ProductBaseInfo getBaseInformation( String prodId );

	/**
	 * App 메타정보를 조회한다.
	 *
	 * @param channelProdId 채널상품ID
	 * @return 상품메타정보
	 */
	AppMeta getAppMeta( String channelProdId );

	/**
	 * 음악 메타정보를 조회한다.
	 *
	 * @param prodId 상품ID
	 * @return 상품메타정보
	 */
	MusicMeta getMusicMeta( String prodId );

	/**
	 * VOD (VideoOnDemand) 메타정보를 조회한다.
	 *
	 * @param prodId 상품ID
	 * @return 상품메타정보
	 */
	VodMeta getVodMeta( String prodId );

	/**
	 * EBook(이북) / Comic(코믹) 메타정보를 조회한다.
	 *
	 * @param prodId 상품ID
	 * @return 상품메타정보
	 */
	EbookComicMeta getEbookComicMeta( String prodId );

	/**
	 * 앨범 메타정보를 조회한다.
	 *
	 * @param prodId 상품ID
	 * @return 상품메타정보
	 */
	AlbumMeta getAlbumMeta( String prodId );

	/**
	 * 쇼핑 카탈로그 메타정보를 조회한다.
	 *
	 * <pre>
	 *     쇼핑은 카탈로그를 상품 단위로 전시한다.
	 * </pre>
	 *
	 * @param catalogId 쇼핑 카탈로그ID
	 * @return 상품메타정보
	 */
	ShoppingMeta getShoppingMeta( String catalogId );

	/**
	 * 웹툰 메타정보를 조회한다.
	 *
	 * @param prodId 상품ID
	 * @return 상품메타정보
	 */
	WebtoonMeta getWebtoonMeta( String prodId );

	/**
	 * 정액권 메타정보를 조회한다.
	 *
	 * <pre>
	 *     정액권은 이용권으로 변경되었다. (언제인지는 불확실... )
	 * </pre>
	 *
	 * @param channelProdId 채널상품ID
	 * @return 상품메타정보
	 */
	@Deprecated
	FreepassMeta getFreepassMeta( String channelProdId );

	/**
	 * 이용권 메타정보를 조회한다.
	 *
	 * @param channelProdId 채널상품ID
	 * @return 상품메타정보
	 */
	VoucherMeta getVoucherMeta( String channelProdId );

	/**
	 * 서브컨텐트 정보를 조회한다.
	 *
	 * <pre>
	 * ( APP상품에서 이용한다. )
	 * </pre>
	 *
	 * @param channelProdId
	 * @return
	 */
	SubContent getSubContent( String channelProdId );

	/**
	 * 메뉴 카테고리 정보를 조회한다.
	 *
	 * @param prodId 상품ID
	 * @return 메뉴 카테고리 정보
	 */
	MenuInfo getMenuInfo( String prodId );

	/**
	 * 메뉴 카테고리 정보를 조회한다.
	 *
	 * @param prodId 상품ID
	 * @param menuId 메뉴 ID
	 * @return 메뉴 카테고리 정보
	 */
	MenuInfo getMenuInfo( String prodId, String menuId );

	/**
	 * 상품 통계(참여자 수, 구매건수, 평점)을 조회한다.
	 *
	 * @param prodId 상품ID
	 * @return 상품통계정보
	 */
	ProductStats getProductStats( String prodId );



}
