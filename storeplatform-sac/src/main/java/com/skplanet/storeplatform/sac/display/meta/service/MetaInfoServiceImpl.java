package com.skplanet.storeplatform.sac.display.meta.service;

import com.skplanet.storeplatform.sac.display.cache.service.ProductInfoManager;
import com.skplanet.storeplatform.sac.display.cache.vo.AlbumMeta;
import com.skplanet.storeplatform.sac.display.cache.vo.AppMeta;
import com.skplanet.storeplatform.sac.display.cache.vo.EbookComicMeta;
import com.skplanet.storeplatform.sac.display.cache.vo.FreepassMeta;
import com.skplanet.storeplatform.sac.display.cache.vo.MenuInfo;
import com.skplanet.storeplatform.sac.display.cache.vo.MusicMeta;
import com.skplanet.storeplatform.sac.display.cache.vo.ShoppingMeta;
import com.skplanet.storeplatform.sac.display.cache.vo.SubContent;
import com.skplanet.storeplatform.sac.display.cache.vo.VodMeta;
import com.skplanet.storeplatform.sac.display.cache.vo.VoucherMeta;
import com.skplanet.storeplatform.sac.display.cache.vo.WebtoonMeta;
import com.skplanet.storeplatform.sac.display.common.ContentType;
import com.skplanet.storeplatform.sac.display.meta.util.MetaBeanUtils;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;
import com.skplanet.storeplatform.sac.display.meta.vo.ProductBasicInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Meta 정보 조회 Service 구현체
 * 
 * Updated on : 2014. 1. 27. Updated by : 오승민, 인크로스
 */
@Service
public class MetaInfoServiceImpl implements MetaInfoService {

	@Autowired
	private ProductInfoManager productInfoManager;

	@Override
	public String getProdIdByContentsType( ProductBasicInfo productBasicInfo ) {

		switch( ContentType.forCode( productBasicInfo.getContentsTypeCd() ) ) {
			case Channel:
				return productBasicInfo.getProdId();
			case Episode:
				return productBasicInfo.getPartProdId();
		}

		return productBasicInfo.getProdId();

	}

	/*
         * (non-Javadoc)
         *
         * @see com.skplanet.storeplatform.sac.display.meta.service.MetaInfoService#getAppMetaInfo(java.util.Map)
         */
	@Override
	public MetaInfo getAppMetaInfo( String channelProdId ) {

		AppMeta meta = this.productInfoManager.getAppMeta( channelProdId );

		if ( meta == null ) return null;

		SubContent subContent = productInfoManager.getSubContent( channelProdId );
		MenuInfo   menuInfo   = productInfoManager.getMenuInfo( channelProdId );

		MetaInfo me = new MetaInfo();
		MetaBeanUtils.setProperties(menuInfo, me); // MenuInfo
		MetaBeanUtils.setProperties(subContent, me); // SubContent
		MetaBeanUtils.setProperties(meta, me); // App

		me.setRegDt( null );
		if (meta.getPartParentClsfCd() != null) {
			me.setPartParentClsfCd("PD012301".equals(meta.getPartParentClsfCd()) ? "Y" : "N");
		} else {
			me.setPartParentClsfCd("N");
		}
		me.setSubContentsId(null);

		return me;

	}

	@Override
	public MetaInfo getAppMetaInfo( ProductBasicInfo productBasicInfo ) {
		return getAppMetaInfo( productBasicInfo.getProdId() );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.display.meta.service.MetaInfoService#getMusicMetaInfo(java.util.Map)
	 */
	@Override
	public MetaInfo getMusicMetaInfo( String prodId ) {

		MusicMeta meta = productInfoManager.getMusicMeta( prodId );

		if( meta == null ) return null;

		MetaInfo me = new MetaInfo();
		MetaBeanUtils.setProperties(meta, me);

		return me;

	}

	@Override
	public MetaInfo getMusicMetaInfo( ProductBasicInfo productBasicInfo ) {
		return getMusicMetaInfo( productBasicInfo.getProdId() );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.display.meta.service.MetaInfoService#getVODMetaInfo(java.util.Map)
	 */
	@Override
	public MetaInfo getVODMetaInfo( String prodId ) {

		VodMeta meta = productInfoManager.getVodMeta( prodId );

		if( meta == null ) return null;

		MetaInfo me = new MetaInfo();
		MetaBeanUtils.setProperties(meta, me);
		me.setFileSize(0L);

		String contentsTypeCd = productInfoManager.getBaseInformation( prodId ).getContentsTypeCd();

		switch( ContentType.forCode( contentsTypeCd ) ) {

			case Channel:
				me.setProdAmt(meta.getChnlProdAmt());
				me.setUnlmtAmt(meta.getChnlUnlmtAmt());
				me.setPeriodAmt(meta.getChnlPeriodAmt());
				break;

			case Episode:
				me.setProdAmt(meta.getEpsdProdAmt());
				me.setUnlmtAmt(meta.getEpsdUnlmtAmt());
				me.setPeriodAmt(meta.getEpsdPeriodAmt());

				// 에피소드의 drmYn 세팅. Rights를 생성하는 부분에서 소장이냐 대여냐에 따라 생성 로직이 분리되기 때문에 아래와 같이 처리해도 무방
				me.setPlayDrmYn(meta.getEpsdDrmYn());
				me.setStoreDrmYn(meta.getEpsdDrmYn());
				break;
		}

		return me;

	}

	@Override
	public MetaInfo getVODMetaInfo( ProductBasicInfo productBasicInfo ) {
		return getVODMetaInfo( getProdIdByContentsType( productBasicInfo ) );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.display.meta.service.MetaInfoService#getEbookComicMetaInfo(java.util.Map)
	 */
	@Override
	public MetaInfo getEbookComicMetaInfo( String prodId ) {

		EbookComicMeta meta = productInfoManager.getEbookComicMeta( prodId );

		if( meta == null ) return null;

		MetaInfo me = new MetaInfo();
		MetaBeanUtils.setProperties(meta, me);

		String contentsTypeCd = productInfoManager.getBaseInformation( prodId ).getContentsTypeCd();

		switch( ContentType.forCode( contentsTypeCd ) ) {

			case Channel:
				me.setProdAmt(meta.getChnlProdAmt());
				me.setProdNetAmt(meta.getChnlProdNetAmt());
				me.setUnlmtAmt(meta.getChnlUnlmtAmt());
				me.setPeriodAmt(meta.getChnlPeriodAmt());
				break;

			case Episode:
				me.setProdAmt(meta.getEpsdProdAmt());
				me.setProdNetAmt( meta.getEpsdProdNetAmt() );
				me.setUnlmtAmt( meta.getEpsdUnlmtAmt() );
				me.setPeriodAmt( meta.getEpsdPeriodAmt() );
				if (meta.getUsePeriod() != null)
					me.setUsePeriod(meta.getUsePeriod().toString());
				break;
		}

		return me;

	}

	@Override
	public MetaInfo getEbookComicMetaInfo( ProductBasicInfo productBasicInfo ) {
		return getEbookComicMetaInfo( getProdIdByContentsType( productBasicInfo ) );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.display.meta.service.MetaInfoService#getWebtoonMetaInfo(java.util.Map)
	 */
	@Override
	public MetaInfo getWebtoonMetaInfo( String prodId ) {

		WebtoonMeta meta = this.productInfoManager.getWebtoonMeta( prodId );

		if( meta == null ) return null;

		MetaInfo me = new MetaInfo();
		MetaBeanUtils.setProperties(meta, me);

		String contentsTypeCd = productInfoManager.getBaseInformation( prodId ).getContentsTypeCd();

		switch( ContentType.forCode( contentsTypeCd ) ) {

			case Channel:
				me.setProdAmt(meta.getChnlProdAmt());
				break;

			case Episode:
				me.setProdAmt(meta.getEpsdProdAmt());
				break;
		}

		return me;

	}

	@Override
	public MetaInfo getWebtoonMetaInfo( ProductBasicInfo productBasicInfo ) {
		return getWebtoonMetaInfo( getProdIdByContentsType( productBasicInfo ) );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.display.meta.service.MetaInfoService#getShoppingMetaInfo(java.util.Map)
	 */
	@Override
	public MetaInfo getShoppingMetaInfo( String catalogId ) {

		ShoppingMeta meta = this.productInfoManager.getShoppingMeta( catalogId );

		if( meta == null ) return null;

		MetaInfo me = new MetaInfo();
		MetaBeanUtils.setProperties( meta, me );
		me.setNo("1");
		me.setFileSize(0L);

		return me;

	}

	@Override
	public MetaInfo getShoppingMetaInfo( ProductBasicInfo productBasicInfo ) {
		return getShoppingMetaInfo( productBasicInfo.getCatalogId() );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.display.meta.service.MetaInfoService#getFreepassMetaInfo(java.util.Map)
	 */
	@Override
	public MetaInfo getFreepassMetaInfo( String channelProdId ) {

		FreepassMeta meta = productInfoManager.getFreepassMeta( channelProdId );

		if( meta == null ) return null;

		MetaInfo me = new MetaInfo();
		MetaBeanUtils.setProperties( meta, me );

		return me;

	}

	@Override
	public MetaInfo getFreepassMetaInfo( ProductBasicInfo productBasicInfo ) {
		return getFreepassMetaInfo( productBasicInfo.getProdId() );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.display.meta.service.MetaInfoService#getAlbumMetaInfo(java.util.Map)
	 */
	@Override
	public AlbumMeta getAlbumMetaInfo( String prodId ) {

		AlbumMeta meta = productInfoManager.getAlbumMeta( prodId );

		if( meta == null ) return null;

		return meta;

	}

	@Override
	public AlbumMeta getAlbumMetaInfo( ProductBasicInfo productBasicInfo ) {
		return getAlbumMetaInfo( productBasicInfo.getProdId() );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.display.meta.service.MetaInfoService#getVoucherMetaInfo(java.util.Map)
	 */
	@Override
	public MetaInfo getVoucherMetaInfo( String channelProdId ) {

		VoucherMeta meta = this.productInfoManager.getVoucherMeta( channelProdId );

		MetaInfo me = new MetaInfo();
		MetaBeanUtils.setProperties( meta, me );

		return me;

	}

	@Override
	public MetaInfo getVoucherMetaInfo( ProductBasicInfo productBasicInfo ) {
		return getVoucherMetaInfo( productBasicInfo.getProdId() );
	}

}
