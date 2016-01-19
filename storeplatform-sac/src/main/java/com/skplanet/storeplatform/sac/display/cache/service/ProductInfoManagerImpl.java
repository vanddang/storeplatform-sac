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

import com.skplanet.storeplatform.sac.common.header.extractor.HeaderExtractor;
import com.skplanet.storeplatform.sac.display.cache.vo.*;
import com.skplanet.storeplatform.sac.display.common.ContentType;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.List;

/**
 * 상품 메타정보 조회 서비스 구현체
 *
 * Updated on : 2016. 01. 08 Updated by : 정화수, SK 플래닛.
 *
 */
@Service
public class ProductInfoManagerImpl implements ProductInfoManager {

	@Autowired
	ProductInfoCacheManager cachedManager;

	@Autowired
	private CachedExtraInfoManager extraInfoManager;
	
	@Autowired
	HeaderExtractor header;
	
	@Override
	public ProductBaseInfo getBaseInformation( String prodId ) {
		return extraInfoManager.getProductBaseInfo( new GetProductBaseInfoParam( prodId ) );
	}

	@Override
	public AppMeta getAppMeta( String channelProdId ) {

		AppMetaParam param = new AppMetaParam();

		param.setChannelId( channelProdId );
		param.setTenantId( header.getTenantId() );
		param.setLangCd( header.getLangCd() );

		return cachedManager.getAppMeta( param, isCacheable() );

	}

	public List<AppMeta> getAppMetaList( List<String> prodIdList ) {

		String tenantId    = header.getTenantId();
		String deviceModel = header.getDeviceModel();
		String langCd      = header.getLangCd();

		return cachedManager.getAppMetaList( prodIdList, langCd, tenantId, deviceModel );

	}

	@Override
	public MusicMeta getMusicMeta( String prodId ) {

		ProductBaseInfo baseInfo = getBaseInformation( prodId );
		if( baseInfo == null ) return null;

		MusicMetaParam param = new MusicMetaParam();

		param.setProdId( prodId );
		param.setLangCd( header.getLangCd() );
		param.setTenantId( header.getTenantId() );

		// BELL, COLORING
		if ( baseInfo.getSvcGrpCd() != null && baseInfo.getContentsTypeCd().equals(DisplayConstants.DP_EPISODE_CONTENT_TYPE_CD)) {
			param.setEpisodeSvcGrpCd( baseInfo.getSvcGrpCd() );

		// DEFAULT
		} else {
			param.setEpisodeSvcGrpCd( DisplayConstants.DP_MULTIMEDIA_PROD_SVC_GRP_CD );
		}

		// 멀티미디어(뮤직) || 폰꾸미기(링,벨)
		if( "DP000203".equals( param.getEpisodeSvcGrpCd() ) || "DP000204".equals( param.getEpisodeSvcGrpCd() )) {
			param.setContentType( ContentType.forCode( baseInfo.getContentsTypeCd() ) );
		} else {
			param.setContentType( ContentType.Channel );
		}

		return cachedManager.getMusicMeta( param, isCacheable() );

	}

	public List<MusicMeta> getMusicMetaList( List<String> prodIdList ) {

		String langCd   = header.getLangCd();
		String tenantId = header.getTenantId();

		return cachedManager.getMusicMetaList( prodIdList, langCd, tenantId );

	}

	@Override
	public VodMeta getVodMeta( String prodId ) {

		ProductBaseInfo baseInfo = getBaseInformation( prodId );
		if( baseInfo == null ) return null;

		VodMetaParam param = new VodMetaParam();

		param.setProdId( prodId );
		param.setLangCd( header.getLangCd() );
		param.setTenantId( header.getTenantId() );
		param.setContentType( ContentType.forCode( baseInfo.getContentsTypeCd() ) );

		return cachedManager.getVodMeta( param, isCacheable() );

	}

	@Override
	public EbookComicMeta getEbookComicMeta( String prodId ) {

		ProductBaseInfo baseInfo = getBaseInformation( prodId );
		if( baseInfo == null ) return null;

		EbookComicMetaParam param = new EbookComicMetaParam();

		param.setProdId( prodId );
		param.setLangCd( header.getLangCd() );
		param.setTenantId( header.getTenantId() );
		param.setContentType( ContentType.forCode( baseInfo.getContentsTypeCd() ) );

		return cachedManager.getEbookComicMeta( param, isCacheable() );

    }

	@Override
	public AlbumMeta getAlbumMeta( String prodId ) {

		AlbumMetaParam param = new AlbumMetaParam();

		param.setProdId( prodId );
		param.setLangCd( header.getLangCd() );
		param.setTenantId( header.getTenantId() );

		return cachedManager.getAlbumMeta( param, isCacheable() );

	}

	@Override
	public WebtoonMeta getWebtoonMeta( String prodId ) {

		ProductBaseInfo baseInfo = getBaseInformation( prodId );
		if( baseInfo == null ) return null;

		WebtoonMetaParam param = new WebtoonMetaParam();

		param.setProdId( prodId );
		param.setLangCd( header.getLangCd() );
		param.setTenantId( header.getTenantId() );
		param.setContentType( ContentType.forCode( baseInfo.getContentsTypeCd() ) );

		return cachedManager.getWebtoonMeta( param, isCacheable() );

	}

	@Override
	public ShoppingMeta getShoppingMeta( String catalogId ) {

		ShoppingMetaParam param = new ShoppingMetaParam();

		param.setCatalogId( catalogId );
		param.setLangCd( header.getLangCd() );
		param.setTenantId( header.getTenantId() );

		return cachedManager.getShoppingMeta( param, isCacheable() );

	}

	@Override
	public FreepassMeta getFreepassMeta( String channelProdId ) {

		FreepassMetaParam param = new FreepassMetaParam();

		param.setChannelId( channelProdId );
		param.setLangCd( header.getLangCd() );
		param.setTenantId( header.getTenantId() );

		return cachedManager.getFreepassMeta( param, isCacheable() );

	}

	@Override
	public SubContent getSubContent( String channelProdId ) {

		SubContentParam param = new SubContentParam();

		param.setChannelId( channelProdId );
		param.setDeviceModel( header.getDeviceModel() );

		return cachedManager.getSubContent( param, isCacheable() );

	}

	@Override
	public MenuInfo getMenuInfo( String prodId ) {
		return getMenuInfo( prodId, null );
	}

	@Override
	public MenuInfo getMenuInfo( String prodId, String menuId ) {

		ProductBaseInfo baseInfo = getBaseInformation( prodId );
		if( baseInfo == null ) return null;

		MenuInfoParam param = new MenuInfoParam();

		param.setChannelId( prodId );
		param.setLangCd( header.getLangCd() );
		param.setMenuId( menuId );

		return cachedManager.getMenuInfo( param, isCacheable() );

	}

	@Override
	public ProductStats getProductStats( String prodId ) {

		ProductStatsParam param = new ProductStatsParam();

		param.setProdId( prodId );

		return cachedManager.getProductStats( param, isCacheable() );

	}

	@Override
	public VoucherMeta getVoucherMeta( String channelProdId ) {

		VoucherMetaParam param = new VoucherMetaParam();

		param.setChannelId( channelProdId );
		param.setLangCd( header.getLangCd() );
		param.setTenantId( header.getTenantId() );

		return cachedManager.getVoucherMeta( param, isCacheable() );

	}

	/**
	 * Plandas Cache 사용가능 여부를 확인한다.
	 *
	 * @return Plandas Cache 사용 가능여부
	 */
	private boolean isCacheable() {
		return (Boolean) RequestContextHolder.currentRequestAttributes().getAttribute( "useCache", RequestAttributes.SCOPE_REQUEST );
	}

}
