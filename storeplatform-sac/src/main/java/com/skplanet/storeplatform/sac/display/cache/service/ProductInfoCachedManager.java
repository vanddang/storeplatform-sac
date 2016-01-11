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

import com.google.common.base.Strings;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.common.util.ServicePropertyManager;
import com.skplanet.storeplatform.sac.display.cache.vo.*;
import com.skplanet.storeplatform.sac.display.common.ContentType;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.meta.service.ProductSubInfoManager;
import com.skplanet.storeplatform.sac.display.meta.vo.CidPrice;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ProductInfoManager for Cache
 *
 * Updated on : 2016. 01. 08 Updated by : 정화수, SK 플래닛.
 */
@Component
public class ProductInfoCachedManager {

	private final Logger logger = LoggerFactory.getLogger( this.getClass() );

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

    @Autowired
    private ProductSubInfoManager subInfoManager;

	private static final String APP_SVC_GRP_CD      = "DP000201";
	private static final String APP_IMG_CD          = "DP000101";
	private static final String MUSIC_SVC_GRP_CD    = "DP000203";
	private static final String MUSIC_IMG_CD        = "DP000162";
	private static final String MUSIC_SVC_TP_CD     = "DP001111";
	private static final String SHOPPING_SVC_GRP_CD = "DP000206";
	private static final String FREEPASS_SVC_GRP_CD = "DP000207";

	@Cacheable(value = "sac:display:product:app:v2", key = "#param.getCacheKey()", unless = "#result == null")
	public AppMeta getAppMetaCached( AppMetaParam param ) {
		return getAppMeta( param );
	}

	public AppMeta getAppMeta( AppMetaParam param ) {

		Map<String, Object> reqMap = new HashMap<String, Object>();

		reqMap.put( "channelId", param.getChannelId() );
		reqMap.put( "langCd",    param.getLangCd()    );
		reqMap.put( "tenantId",  param.getTenantId()  );
		reqMap.put( "imageCd",   APP_IMG_CD           );
		reqMap.put( "svcGrpCd",  APP_SVC_GRP_CD       );

		AppMeta meta = commonDAO.queryForObject( "ProductInfo.getAppMeta", reqMap, AppMeta.class );

		if( meta == null ) {
			this.logger.warn("메타데이터를 읽을 수 없습니다 - App#{}", param.getChannelId() );
		}

		return meta;

	}

	public List<AppMeta> getAppMetaList( List<String> prodIdList, String langCd, String tenantId, String deviceModelCd ) {

		Map<String, Object> reqMap = new HashMap<String, Object>();

		reqMap.put( "prodIdList",    prodIdList     ); // MyBatis에서는 foreach에서 목록이 역순으로 생성됨.
		reqMap.put( "langCd",        langCd         );
		reqMap.put( "tenantId",      tenantId       );
		reqMap.put( "deviceModelCd", deviceModelCd  );
		reqMap.put( "imageCd",       APP_IMG_CD     );
		reqMap.put( "svcGrpCd",      APP_SVC_GRP_CD );

		return commonDAO.queryForList("ProductInfo.getAppMetaList", reqMap, AppMeta.class);
	}

	@Cacheable(value = "sac:display:product:music:v2", key = "#param.getCacheKey()", unless = "#result == null")
	public MusicMeta getMusicMetaCached( MusicMetaParam param ) {
		return getMusicMeta( param );
	}

	public MusicMeta getMusicMeta( MusicMetaParam param ) {

		Map<String, Object> reqMap = new HashMap<String, Object>();

		if (param.getContentType() == ContentType.Channel)
			reqMap.put("channelId", param.getProdId());
		else if (param.getContentType() == ContentType.Episode)
			reqMap.put("episodeId", param.getProdId());
		else
			throw new IllegalArgumentException("contentType cannot be null.");

		reqMap.put( "episodeSvcGrpCd", param.getEpisodeSvcGrpCd() );
		reqMap.put( "langCd",          param.getLangCd()          );
		reqMap.put( "tenantId",        param.getTenantId()        );
		reqMap.put( "imageCd",         MUSIC_IMG_CD               );
		reqMap.put( "svcGrpCd",        MUSIC_SVC_GRP_CD           );
		reqMap.put( "svcTypeCd",       MUSIC_SVC_TP_CD            );

		if (StringUtils.isNotEmpty(param.getChartClsfCd()) && StringUtils.isNotEmpty(param.getRankStartDay())) {
			reqMap.put( "chartClsfCd",  param.getChartClsfCd()  );
			reqMap.put( "rankStartDay", param.getRankStartDay() );
		} else {
			reqMap.put( "chartClsfCd",  "" );
			reqMap.put( "rankStartDay", "" );
		}

		MusicMeta meta = commonDAO.queryForObject( "ProductInfo.getMusicMeta", reqMap, MusicMeta.class );

		if( meta == null ) {
			this.logger.warn( "메타데이터를 읽을 수 없습니다 - Music#{}", param.getProdId() );
		}

		return meta;

	}

	public List<MusicMeta> getMusicMetaList( List<String> prodIdList, String langCd, String tenantId ) {

		Map<String, Object> reqMap = new HashMap<String, Object>();
		reqMap.put( "prodIdList", prodIdList                                   );
		reqMap.put( "langCd",     langCd                                       );
		reqMap.put( "tenantId",   tenantId                                     );
		reqMap.put( "imageCd",    DisplayConstants.DP_MUSIC_REPRESENT_IMAGE_CD );
		reqMap.put( "svcGrpCd",   MUSIC_SVC_GRP_CD                             );
		reqMap.put( "svcTypeCd",  MUSIC_SVC_TP_CD                              );

		return commonDAO.queryForList("ProductInfo.getMusicMetaList", reqMap, MusicMeta.class);
	}

	@Cacheable(value = "sac:display:product:vod:v6", key = "#param.getCacheKey()", unless = "#result == null")
	public VodMeta getVodMetaCached( VodMetaParam param ) {
		return getVodMeta( param );
	}

	public VodMeta getVodMeta( VodMetaParam param ) {

		Map<String, Object> reqMap = new HashMap<String, Object>();
		reqMap.put( "prodId",   param.getProdId()                          );
		reqMap.put( "langCd",   param.getLangCd()                          );
		reqMap.put( "tenantId", param.getTenantId()                        );
		reqMap.put( "imageCd",  DisplayConstants.DP_VOD_REPRESENT_IMAGE_CD );

		if (param.getContentType() == ContentType.Channel)
			reqMap.put("prodIdType", "CHANNEL");
		else if (param.getContentType() == ContentType.Episode)
			reqMap.put("prodIdType", "EPISODE");
		else
			throw new RuntimeException("contentType cannot be null.");

		VodMeta meta = commonDAO.queryForObject( "ProductInfo.getVodMeta", reqMap, VodMeta.class );

		if( meta == null ) {
			this.logger.warn( "메타데이터를 읽을 수 없습니다 - VOD#{}", param.getProdId() );
		}

		return meta;
	}

	@Cacheable(value = "sac:display:product:ebookcomic:v5", key = "#param.getCacheKey()", unless = "#result == null")
	public EbookComicMeta getEbookComicMetaCached( EbookComicMetaParam param ) {
		return getEbookComicMeta( param );
	}

	public EbookComicMeta getEbookComicMeta(EbookComicMetaParam param) {

		Map<String, Object> reqMap = new HashMap<String, Object>();
		reqMap.put( "prodId",   param.getProdId()                                  );
		reqMap.put( "langCd",   param.getLangCd()                                  );
		reqMap.put( "tenantId", param.getTenantId()                                );
		reqMap.put( "imageCd",  DisplayConstants.DP_EBOOK_COMIC_REPRESENT_IMAGE_CD );

		if (param.getContentType() == ContentType.Channel)
			reqMap.put("prodIdType", "CHANNEL");
		else if (param.getContentType() == ContentType.Episode)
			reqMap.put("prodIdType", "EPISODE");
		else
			throw new RuntimeException("contentType cannot be null.");

		EbookComicMeta meta = commonDAO.queryForObject( "ProductInfo.getEbookComicMeta", reqMap, EbookComicMeta.class );

		if( meta == null ) {
			this.logger.warn( "메타데이터를 읽을 수 없습니다 - Ebook/Comic#{}", param.getProdId() );
		}

        if( meta != null && param.getContentType() == ContentType.Episode ) {
            CidPrice cidPrice = subInfoManager.getCidPriceByEpsdId(param.getLangCd(), param.getTenantId(), param.getProdId());
            if (cidPrice != null) {
				meta.setUsePeriodUnitCd(cidPrice.getRentPeriodUnitCd());
				meta.setUsePeriod(cidPrice.getRentPeriod());
				meta.setUsePeriodNm(cidPrice.getRentPeriodUnitNm());
				meta.setEpsdUnlmtAmt(cidPrice.getProdAmt());
				meta.setEpsdPeriodAmt(cidPrice.getRentProdAmt());
            }
        }

        return meta;

    }

	@Cacheable(value = "sac:display:product:album", key = "#param.getCacheKey()", unless = "#result == null")
	public AlbumMeta getAlbumMetaCached( AlbumMetaParam param ) {
		return getAlbumMeta(param);
	}

	public AlbumMeta getAlbumMeta( AlbumMetaParam param ) {
		return this.commonDAO.queryForObject("AlbumDetail.albumDetail", param, AlbumMeta.class);
	}

	@Cacheable(value = "sac:display:product:webtoon", key = "#param.getCacheKey()", unless = "#result == null")
	public WebtoonMeta getWebtoonMetaCached( WebtoonMetaParam param ) {
		return getWebtoonMeta( param );
	}

	public WebtoonMeta getWebtoonMeta(WebtoonMetaParam param) {

		// NOTICE EbookComic, Webtoon은 조회방법은 동일하지만 요구되는 응답값이 다르다.
		Map<String, Object> reqMap = new HashMap<String, Object>();
		reqMap.put( "prodId",   param.getProdId()                              );
		reqMap.put( "langCd",   param.getLangCd()                              );
		reqMap.put( "tenantId", param.getTenantId()                            );
		reqMap.put( "imageCd",  DisplayConstants.DP_WEBTOON_REPRESENT_IMAGE_CD );

		if (param.getContentType() == ContentType.Channel)
			reqMap.put("prodIdType", "CHANNEL");
		else if (param.getContentType() == ContentType.Episode)
			reqMap.put("prodIdType", "EPISODE");
		else
			throw new RuntimeException("contentType cannot be null.");

		WebtoonMeta meta = commonDAO.queryForObject( "ProductInfo.getWebtoonMeta", reqMap, WebtoonMeta.class );

		if( meta == null ) {
			this.logger.warn( "메타데이터를 읽을 수 없습니다 - Webtoon#{}", param.getProdId() );
		}

		return meta;

	}

	@Cacheable(value = "sac:display:product:shopping", key = "#param.getCacheKey()", unless = "#result == null")
	public ShoppingMeta getShoppingMetaCached( ShoppingMetaParam param ) {
		return getShoppingMeta( param );
	}

	public ShoppingMeta getShoppingMeta( ShoppingMetaParam param ) {

		Map<String, Object> reqMap = new HashMap<String, Object>();
		reqMap.put( "catalogId", param.getCatalogId()                            );
		reqMap.put( "langCd",    param.getLangCd()                               );
		reqMap.put( "tenantId",  param.getTenantId()                             );
		reqMap.put( "svcGrpCd",  SHOPPING_SVC_GRP_CD                             );
		reqMap.put( "imageCd",   DisplayConstants.DP_SHOPPING_REPRESENT_IMAGE_CD );

		ShoppingMeta meta = commonDAO.queryForObject( "ProductInfo.getShoppingMeta", reqMap, ShoppingMeta.class );

		if( meta == null ) {
			this.logger.warn( "메타데이터를 읽을 수 없습니다 - Shopping#{}", param.getCatalogId() );
		}

		return meta;
	}

	@Cacheable(value = "sac:display:product:freepass", key = "#param.getCacheKey()", unless = "#result == null")
	public FreepassMeta getFreepassMetaCached( FreepassMetaParam param ) {
		return getFreepassMeta( param );
	}

	public FreepassMeta getFreepassMeta(FreepassMetaParam param) {
		Map<String, Object> reqMap = new HashMap<String, Object>();
		reqMap.put( "prodId",       param.getChannelId()                                  );
		reqMap.put( "langCd",       param.getLangCd()                                     );
		reqMap.put( "tenantId",     param.getTenantId()                                   );
		reqMap.put( "svcGrpCd",     FREEPASS_SVC_GRP_CD                                   );
		reqMap.put( "thmbImageCd",  DisplayConstants.DP_FREEPASS_THUMBNAIL_IMAGE_CD       );
		reqMap.put( "bannImageCd",  DisplayConstants.DP_FREEPASS_BANNER_IMAGE_CD          );
		reqMap.put( "ebookImageCd", DisplayConstants.DP_FREEPASS_EBOOK_THUMBNAIL_IMAGE_CD );

		FreepassMeta meta = commonDAO.queryForObject( "ProductInfo.getFreepassMeta", reqMap, FreepassMeta.class );

		if( meta == null ) {
			this.logger.warn( "메타데이터를 읽을 수 없습니다 - 정액권#{}", param.getChannelId() );
		}

		return meta;
	}

	@Cacheable(value = "sac:display:subcontent", key = "#param.getCacheKey()", unless = "#result == null")
	public SubContent getSubContentCached( SubContentParam param ) {
		return getSubContent( param );
	}

	public SubContent getSubContent( SubContentParam param ) {

		Map<String, String> reqMap = new HashMap<String, String>();
		reqMap.put( "prodId",        param.getChannelId()   );
		reqMap.put( "deviceModelCd", param.getDeviceModel() );

		SubContent subContent = commonDAO.queryForObject( "ProductInfo.getSubContent", reqMap, SubContent.class );

		if( subContent == null ) {
			this.logger.warn( "서브컨텐츠 데이터를 읽을 수 없습니다 - SubContents#{}", param.getChannelId() );
		}

		return subContent;
	}

	@Cacheable(value = "sac:display:menuinfo", key = "#param.getCacheKey()", unless = "#result == null")
	public MenuInfo getMenuInfoCached( MenuInfoParam param ) {
		return getMenuInfo( param );
	}

	public MenuInfo getMenuInfo( MenuInfoParam param ) {

		Map<String, String> reqMap = new HashMap<String, String>();
		reqMap.put( "prodId", param.getChannelId() );
		reqMap.put( "langCd", param.getLangCd()    );
		if ( param.getMenuId() != null ) {
			reqMap.put( "menuId", param.getMenuId() );
		}

		// TODO 몇Depth메뉴인지 판단을 해야 함
		return commonDAO.queryForObject("ProductInfo.getMenuInfo", reqMap, MenuInfo.class);

	}

	@Cacheable(value = "sac:display:productStats", key = "#param.getCacheKey()", unless = "#result == null")
	public ProductStats getProductStatsCached( ProductStatsParam param ) {
		return getProductStats( param );
	}

	public ProductStats getProductStats(ProductStatsParam param) {

		if ( param == null || Strings.isNullOrEmpty(param.getProdId()) ) throw new IllegalArgumentException();

		Map<String, Object> req = new HashMap<String, Object>();

		req.put("prodId",     param.getProdId()                             );
		req.put("tenantList", ServicePropertyManager.getSupportTenantList() );

		RawProductStats rawProductStats = commonDAO.queryForObject("ProductInfo.getProductStats", req, RawProductStats.class);
		ProductStats    stats           = new ProductStats();

		if ( rawProductStats != null ) {
			stats.setPurchaseCount(rawProductStats.getPrchsCnt());
			if (rawProductStats.getPaticpersCnt() > 0) {
				stats.setParticipantCount(rawProductStats.getPaticpersCnt());
				stats.setAverageScore((double) Math.round((double) rawProductStats.getTotEvluScore() / rawProductStats.getPaticpersCnt() * 10) / 10);
			}
		}

		return stats;

	}

	@Cacheable(value = "sac:display:product:voucher:v1", key = "#param.getCacheKey()", unless = "#result == null")
	public VoucherMeta getVoucherMetaCached( VoucherMetaParam param ) {
		return getVoucherMeta( param );
	}

	public VoucherMeta getVoucherMeta( VoucherMetaParam param ) {

		Map<String, Object> reqMap = new HashMap<String, Object>();

		reqMap.put("prodId",       param.getChannelId()                                  );
		reqMap.put("langCd",       param.getLangCd()                                     );
		reqMap.put("tenantId",     param.getTenantId()                                   );
		reqMap.put("svcGrpCd",     FREEPASS_SVC_GRP_CD                                   );
		reqMap.put("thmbImageCd",  DisplayConstants.DP_FREEPASS_THUMBNAIL_IMAGE_CD       );
		reqMap.put("bannImageCd",  DisplayConstants.DP_FREEPASS_BANNER_IMAGE_CD          );
		reqMap.put("ebookImageCd", DisplayConstants.DP_FREEPASS_EBOOK_THUMBNAIL_IMAGE_CD );

		VoucherMeta meta = this.commonDAO.queryForObject( "ProductInfo.getVoucherMeta", reqMap, VoucherMeta.class );

		if( meta == null ) {
			this.logger.warn( "메타데이터를 읽을 수 없습니다 - 이용권#{}", param.getChannelId() );
		}

		return meta;
	}

	public static class RawProductStats {

		private Integer prchsCnt;
		private Integer totEvluScore;
		private Integer paticpersCnt;

		public Integer getPrchsCnt() {
			return prchsCnt;
		}

		public void setPrchsCnt(Integer prchsCnt) {
			this.prchsCnt = prchsCnt;
		}

		public Integer getTotEvluScore() {
			return totEvluScore;
		}

		public void setTotEvluScore(Integer totEvluScore) {
			this.totEvluScore = totEvluScore;
		}

		public Integer getPaticpersCnt() {
			return paticpersCnt;
		}

		public void setPaticpersCnt(Integer paticpersCnt) {
			this.paticpersCnt = paticpersCnt;
		}
	}

}
