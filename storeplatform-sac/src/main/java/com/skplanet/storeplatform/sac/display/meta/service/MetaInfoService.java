package com.skplanet.storeplatform.sac.display.meta.service;

import com.skplanet.storeplatform.sac.display.cache.vo.AlbumMeta;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;
import com.skplanet.storeplatform.sac.display.meta.vo.ProductBasicInfo;

import java.util.Map;

/**
 * Meta 정보 조회 Service.
 * 
 * Updated on : 2016.01.08 Updated by : 정화수, SK Planet
 */
public interface MetaInfoService {

	/**
	 * 컨텐츠타입(채널/에피소드)에 대응되는 상품ID를 구한다.
	 *
	 * <pre>
	 *    상품메타 refactoring 작업에 사용되는 임시성 로직
	 * </pre>
	 *
	 * (임시로직)
	 *
	 * @param productBasicInfo 상품기본정보
	 * @return 상품ID
	 */
	@Deprecated
	String getProdIdByContentsType( ProductBasicInfo productBasicInfo );

	/**
	 * App 메타정보를 조회한다.
	 *
	 * @param channelProdId 채널상품ID
	 * @return 상품메타정보
	 */
	MetaInfo getAppMetaInfo( String channelProdId );

	/**
	 * App 메타정보를 조회한다.
	 *
	 * <pre>
	 *    상품메타 refactoring 작업에 사용되는 임시성 로직
	 * </pre>
	 *
	 * @param productBasicInfo 상품기본정보
	 * @return 상품메타정보
	 */
	@Deprecated
	MetaInfo getAppMetaInfo( ProductBasicInfo productBasicInfo );

	/**
	 * 음악 메타정보를 조회한다.
	 *
	 * @param prodId 상품ID
	 * @return 상품메타정보
	 */
	MetaInfo getMusicMetaInfo( String prodId );

	/**
	 * 음악 메타정보를 조회한다.
	 *
	 * <pre>
	 *    상품메타 refactoring 작업에 사용되는 임시성 로직
	 * </pre>
	 *
	 * @param productBasicInfo 상품기본정보
	 * @return 상품메타정보
	 */
	@Deprecated
	MetaInfo getMusicMetaInfo( ProductBasicInfo productBasicInfo );

	/**
	 * VOD (VideoOnDemand) 메타정보를 조회한다.
	 *
	 * @param prodId 상품ID
	 * @return 상품메타정보
	 */
	MetaInfo getVODMetaInfo( String prodId );

	/**
	 * VOD (VideoOnDemand) 메타정보를 조회한다.
	 *
	 * <pre>
	 *    상품메타 refactoring 작업에 사용되는 임시성 로직
	 * </pre>
	 *
	 * @param productBasicInfo 상품기본정보
	 * @return 상품메타정보
	 */
	@Deprecated
	MetaInfo getVODMetaInfo( ProductBasicInfo productBasicInfo );

	/**
	 * EBook(이북) / Comic(코믹) 메타정보를 조회한다.
	 *
	 * @param prodId 상품ID
	 * @return 상품메타정보
	 */
	MetaInfo getEbookComicMetaInfo( String prodId );

	/**
	 * EBook(이북) / Comic(코믹) 메타정보를 조회한다.
	 *
	 * <pre>
	 *    상품메타 refactoring 작업에 사용되는 임시성 로직
	 * </pre>
	 *
	 * (임시로직)
	 *
	 * @param productBasicInfo 상품기본정보
	 * @return 상품메타정보
	 */
	@Deprecated
	MetaInfo getEbookComicMetaInfo( ProductBasicInfo productBasicInfo );

	/**
	 * 웹툰 메타정보를 조회한다.
	 *
	 * @param prodId 상품ID
	 * @return 상품메타정보
	 */
	MetaInfo getWebtoonMetaInfo( String prodId );

	/**
	 * 웹툰 메타정보를 조회한다.
	 *
	 * <pre>
	 *    상품메타 refactoring 작업에 사용되는 임시성 로직
	 * </pre>
	 *
	 * @param productBasicInfo 상품기본정보
	 * @return 상품메타정보
	 */
	@Deprecated
	MetaInfo getWebtoonMetaInfo( ProductBasicInfo productBasicInfo );

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
	MetaInfo getShoppingMetaInfo( String catalogId );

	/**
	 * 쇼핑 카탈로그 메타정보를 조회한다.
	 *
	 * <pre>
	 *    상품메타 refactoring 작업에 사용되는 임시성 로직
	 * </pre>
	 *
	 * @param productBasicInfo 상품기본정보
	 * @return 상품메타정보
	 */
	@Deprecated
	MetaInfo getShoppingMetaInfo( ProductBasicInfo productBasicInfo );

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
	MetaInfo getFreepassMetaInfo( String channelProdId );

	/**
	 * 정액권 메타정보를 조회한다.
	 *
	 * <pre>
	 *    상품메타 refactoring 작업에 사용되는 임시성 로직
	 * </pre>
	 *
	 * @param productBasicInfo 상품기본정보
	 * @return 상품메타정보
	 */
	@Deprecated
	MetaInfo getFreepassMetaInfo( ProductBasicInfo productBasicInfo );

	/**
	 * 앨범 메타정보를 조회한다.
	 *
	 * @param prodId 상품ID
	 * @return 상품메타정보
	 */
	AlbumMeta getAlbumMetaInfo( String prodId );

	/**
	 * 앨범 메타정보를 조회한다.
	 *
	 * @param productBasicInfo 상품기본정보
	 * @return 상품메타정보
	 */
	@Deprecated
	AlbumMeta getAlbumMetaInfo( ProductBasicInfo productBasicInfo );

	/**
	 * 이용권 메타정보를 조회한다.
	 *
	 * @param channelProdId 채널상품ID
	 * @return 상품메타정보
	 */
	MetaInfo getVoucherMetaInfo( String channelProdId );

	/**
	 * 이용권 메타정보를 조회한다.
	 *
	 * <pre>
	 *    상품메타 refactoring 작업에 사용되는 임시성 로직
	 * </pre>
	 *
	 * @param productBasicInfo 상품기본정보
	 * @return 상품메타정보
	 */
	@Deprecated
	MetaInfo getVoucherMetaInfo( ProductBasicInfo productBasicInfo );

}
