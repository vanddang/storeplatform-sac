package com.skplanet.storeplatform.sac.display.meta.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.skplanet.storeplatform.sac.display.meta.vo.CidPrice;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.common.header.vo.DeviceHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.display.cache.service.ProductInfoManager;
import com.skplanet.storeplatform.sac.display.cache.vo.AlbumMeta;
import com.skplanet.storeplatform.sac.display.cache.vo.AlbumMetaParam;
import com.skplanet.storeplatform.sac.display.cache.vo.AppMeta;
import com.skplanet.storeplatform.sac.display.cache.vo.AppMetaParam;
import com.skplanet.storeplatform.sac.display.cache.vo.EbookComicMeta;
import com.skplanet.storeplatform.sac.display.cache.vo.EbookComicMetaParam;
import com.skplanet.storeplatform.sac.display.cache.vo.FreepassMeta;
import com.skplanet.storeplatform.sac.display.cache.vo.FreepassMetaParam;
import com.skplanet.storeplatform.sac.display.cache.vo.MenuInfo;
import com.skplanet.storeplatform.sac.display.cache.vo.MenuInfoParam;
import com.skplanet.storeplatform.sac.display.cache.vo.MusicMeta;
import com.skplanet.storeplatform.sac.display.cache.vo.MusicMetaParam;
import com.skplanet.storeplatform.sac.display.cache.vo.ProductStats;
import com.skplanet.storeplatform.sac.display.cache.vo.ProductStatsParam;
import com.skplanet.storeplatform.sac.display.cache.vo.ShoppingMeta;
import com.skplanet.storeplatform.sac.display.cache.vo.ShoppingMetaParam;
import com.skplanet.storeplatform.sac.display.cache.vo.SubContent;
import com.skplanet.storeplatform.sac.display.cache.vo.SubContentParam;
import com.skplanet.storeplatform.sac.display.cache.vo.VodMeta;
import com.skplanet.storeplatform.sac.display.cache.vo.VodMetaParam;
import com.skplanet.storeplatform.sac.display.cache.vo.VoucherMeta;
import com.skplanet.storeplatform.sac.display.cache.vo.VoucherMetaParam;
import com.skplanet.storeplatform.sac.display.cache.vo.WebtoonMeta;
import com.skplanet.storeplatform.sac.display.cache.vo.WebtoonMetaParam;
import com.skplanet.storeplatform.sac.display.common.ContentType;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.common.service.MemberBenefitService;
import com.skplanet.storeplatform.sac.display.meta.util.MetaBeanUtils;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;
import com.skplanet.storeplatform.sac.display.meta.vo.ProductBasicInfo;

/**
 * Meta 정보 조회 Service 구현체
 * 
 * Updated on : 2014. 1. 27. Updated by : 오승민, 인크로스
 */
@Service
public class MetaInfoServiceImpl implements MetaInfoService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Autowired
	private ProductInfoManager productInfoManager;

	@Autowired
	private MemberBenefitService memberBenefitService;

    @Autowired
    private ProductSubInfoManager subInfoManager;

	private void commonHandler(MetaInfo me, String tenantId, String prodKey) {
		// 집계는 쇼핑의 경우 catalogId, 그 외에는 항상 채널 기준으로 노출하고 있음
		ProductStats productStats = this.productInfoManager.getProductStats(new ProductStatsParam(prodKey));
		me.setPaticpersCnt(productStats.getParticipantCount());
		me.setAvgEvluScore(productStats.getAverageScore());
		me.setPrchsCnt(productStats.getPurchaseCount());

		me.setMileageInfo(this.memberBenefitService.getMileageInfo(tenantId, me.getTopMenuId(), me.getProdId(),
				me.getProdAmt()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.display.meta.service.MetaInfoService#getAppMetaInfo(java.util.Map)
	 */
	@Override
	public MetaInfo getAppMetaInfo(Map<String, Object> paramMap) {

		ProductBasicInfo basicInfo = (ProductBasicInfo) paramMap.get("productBasicInfo");
		String prodId = basicInfo.getProdId();

		MetaInfo me;

		TenantHeader tenantHeader = (TenantHeader) paramMap.get("tenantHeader");

		if (this.isUseCache()) {
			AppMetaParam param = new AppMetaParam();
			DeviceHeader deviceHeader = (DeviceHeader) paramMap.get("deviceHeader");

			param.setChannelId(prodId);
			param.setLangCd(tenantHeader.getLangCd());
			param.setTenantId(tenantHeader.getTenantId());

			AppMeta app = this.productInfoManager.getAppMeta(param);
			if (app == null) {
				this.logger.warn("메타데이터를 읽을 수 없습니다 - App#{}", basicInfo.getProdId());
				return null;
			}

			SubContent subContent = this.productInfoManager.getSubContent(new SubContentParam(basicInfo.getProdId(),
					deviceHeader.getModel()));
			MenuInfo menuInfo = this.productInfoManager.getMenuInfo(new MenuInfoParam(basicInfo.getProdId(), basicInfo
					.getMenuId(), tenantHeader.getLangCd()));

			me = new MetaInfo();
			MetaBeanUtils.setProperties(menuInfo, me); // MenuInfo
			MetaBeanUtils.setProperties(subContent, me); // SubContent
			MetaBeanUtils.setProperties(app, me); // App

			me.setRegDt(null);
			if (app.getPartParentClsfCd() != null) {
				me.setPartParentClsfCd("PD012301".equals(app.getPartParentClsfCd()) ? "Y" : "N");
			} else {
				me.setPartParentClsfCd("N");
			}
			me.setSubContentsId(null);
		} else
			me = this.commonDAO.queryForObject("MetaInfo.getAppMetaInfo", paramMap, MetaInfo.class);

		if (me != null)
			this.commonHandler(me, tenantHeader.getTenantId(), me.getProdId());

		return me;
	}

	@Override
	public List<MetaInfo> getAppMetaInfoList(List<String> prodIdList, String langCd, String tenantId,
			String deviceModelCd) {
		List<AppMeta> appMetaList;

		if (prodIdList.size() > 1000) {
			appMetaList = new ArrayList<AppMeta>();
			List<String> partitionList = new ArrayList<String>();
			for (int i = 1; i <= prodIdList.size(); ++i) {
				partitionList.add(prodIdList.get(i - 1));
				if (i % 1000 == 0) {
					appMetaList.addAll(this.productInfoManager.getAppMetaList(langCd, tenantId, partitionList,
							deviceModelCd));
					partitionList.clear();
				}
			}
		} else {
			appMetaList = this.productInfoManager.getAppMetaList(langCd, tenantId, prodIdList, deviceModelCd);
		}

		ArrayList<MetaInfo> metaList = new ArrayList<MetaInfo>();

		Map<String, AppMeta> appMetaMap = new HashMap<String, AppMeta>();
		for (AppMeta app : appMetaList) {
			appMetaMap.put(app.getProdId(), app);
		}

		for (String prodId : prodIdList) {
			AppMeta app = appMetaMap.get(prodId);
			if (app == null) {
				this.logger.warn("메타데이터를 읽을 수 없습니다 - App#{}", prodId);
			} else {
				MetaInfo me = new MetaInfo();
				MetaBeanUtils.setProperties(app, me);

				if (app.getPartParentClsfCd() != null) {
					me.setPartParentClsfCd("PD012301".equals(app.getPartParentClsfCd()) ? "Y" : "N");
				}
				me.setSubContentsId(null);

				metaList.add(me);
			}
		}

		return metaList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.display.meta.service.MetaInfoService#getMusicMetaInfo(java.util.Map)
	 */
	@Override
	public MetaInfo getMusicMetaInfo(Map<String, Object> paramMap) {
		MetaInfo me;
		TenantHeader tenantHeader = (TenantHeader) paramMap.get("tenantHeader");

		if (this.isUseCache()) {
			MusicMetaParam param = new MusicMetaParam();
			ProductBasicInfo basicInfo = (ProductBasicInfo) paramMap.get("productBasicInfo");

			param.setProdId(basicInfo.getProdId());
			param.setLangCd(tenantHeader.getLangCd());
			param.setTenantId(tenantHeader.getTenantId());

			if (basicInfo.getSvcGrpCd() != null
					&& basicInfo.getContentsTypeCd().equals(DisplayConstants.DP_EPISODE_CONTENT_TYPE_CD))
				param.setEpisodeSvcGrpCd(basicInfo.getSvcGrpCd()); // FOR BELL, COLORING
			else
				param.setEpisodeSvcGrpCd(DisplayConstants.DP_MULTIMEDIA_PROD_SVC_GRP_CD); // DEFAULT

			if (StringUtils.defaultString((String) paramMap.get(DisplayConstants.META_MUSIC_USE_CONTENT_TP), "N")
					.equals("Y")) {
				param.setContentType(ContentType.forCode(basicInfo.getContentsTypeCd()));
			} else {
				param.setContentType(ContentType.Channel);
			}

			if (paramMap.containsKey("chartClsfCd") && paramMap.containsKey("stdDt")) {
				param.setChartClsfCd((String) paramMap.get("chartClsfCd"));
				param.setRankStartDay((String) paramMap.get("stdDt"));
			}

			MusicMeta music = this.productInfoManager.getMusicMeta(param);
			if (music == null) {
				this.logger.warn("메타데이터를 읽을 수 없습니다 - Music#{}", basicInfo.getProdId());
				return null;
			}

			me = new MetaInfo();
			MetaBeanUtils.setProperties(music, me);
		} else
			me = this.commonDAO.queryForObject("MetaInfo.getMusicMetaInfo", paramMap, MetaInfo.class);

		if (me != null)
			this.commonHandler(me, tenantHeader.getTenantId(), me.getProdId());

		return me;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.display.meta.service.MetaInfoService#getVODMetaInfo(java.util.Map)
	 */
	@Override
	public MetaInfo getVODMetaInfo(Map<String, Object> paramMap) {
		MetaInfo me;
		TenantHeader tenantHeader = (TenantHeader) paramMap.get("tenantHeader");

		if (this.isUseCache()) {

			ProductBasicInfo basicInfo = (ProductBasicInfo) paramMap.get("productBasicInfo");

			VodMetaParam param = new VodMetaParam();
			param.setTenantId(tenantHeader.getTenantId());
			param.setLangCd(tenantHeader.getLangCd());
			param.setContentType(ContentType.forCode(basicInfo.getContentsTypeCd()));
			if (param.getContentType() == ContentType.Channel) {
				param.setProdId(basicInfo.getProdId());
			} else if (param.getContentType() == ContentType.Episode) {
				param.setProdId(basicInfo.getPartProdId());
			}

			VodMeta meta = this.productInfoManager.getVodMeta(param);

			if (meta == null) {
				this.logger.warn("메타데이터를 읽을 수 없습니다 - Vod#{}", param.getProdId());
				return null;
			}

			me = new MetaInfo();
			MetaBeanUtils.setProperties(meta, me);
			me.setFileSize(0L);

			me.setContentsTypeCd(param.getContentType().getCode());
			if (param.getContentType() == ContentType.Channel) {
				me.setProdAmt(meta.getChnlProdAmt());
				me.setUnlmtAmt(meta.getChnlUnlmtAmt());
				me.setPeriodAmt(meta.getChnlPeriodAmt());
			} else if (param.getContentType() == ContentType.Episode) {
				me.setProdAmt(meta.getEpsdProdAmt());
				me.setUnlmtAmt(meta.getEpsdUnlmtAmt());
				me.setPeriodAmt(meta.getEpsdPeriodAmt());
			}
		} else
			me = this.commonDAO.queryForObject("MetaInfo.getVODMetaInfo", paramMap, MetaInfo.class);

		if (me != null)
			this.commonHandler(me, tenantHeader.getTenantId(), me.getProdId());

		return me;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.display.meta.service.MetaInfoService#getEbookComicMetaInfo(java.util.Map)
	 */
	@Override
	public MetaInfo getEbookComicMetaInfo(Map<String, Object> paramMap) {

		MetaInfo me;
		TenantHeader tenantHeader = (TenantHeader) paramMap.get("tenantHeader");
        ProductBasicInfo basicInfo = (ProductBasicInfo) paramMap.get("productBasicInfo");
        ContentType contentType = ContentType.forCode(basicInfo.getContentsTypeCd());

        if (this.isUseCache()) {

			EbookComicMetaParam param = new EbookComicMetaParam();
			param.setTenantId(tenantHeader.getTenantId());
			param.setLangCd(tenantHeader.getLangCd());
			param.setContentType(contentType);
			if (param.getContentType() == ContentType.Channel) {
				param.setProdId(basicInfo.getProdId());
			} else if (param.getContentType() == ContentType.Episode) {
				param.setProdId(basicInfo.getPartProdId());
			}

			EbookComicMeta meta = this.productInfoManager.getEbookComicMeta(param);

			if (meta == null) {
				this.logger.warn("메타데이터를 읽을 수 없습니다 - EbookComic#{}", param.getProdId());
				return null;
			}

			me = new MetaInfo();
			MetaBeanUtils.setProperties(meta, me);

			me.setContentsTypeCd(param.getContentType().getCode());
			if (param.getContentType() == ContentType.Channel) {
				me.setProdAmt(meta.getChnlProdAmt());
				me.setProdNetAmt(meta.getChnlProdNetAmt());
				me.setUnlmtAmt(meta.getChnlUnlmtAmt());
				me.setPeriodAmt(meta.getChnlPeriodAmt());
			} else if (param.getContentType() == ContentType.Episode) {
				me.setProdAmt(meta.getEpsdProdAmt());
				me.setProdNetAmt(meta.getEpsdProdNetAmt());
				me.setUnlmtAmt(meta.getEpsdUnlmtAmt());
				me.setPeriodAmt(meta.getEpsdPeriodAmt());
                if(meta.getUsePeriod() != null)
                    me.setUsePeriod(meta.getUsePeriod().toString());
			}
		} else {
            me = this.commonDAO.queryForObject("MetaInfo.getEbookComicMetaInfo", paramMap, MetaInfo.class);
            if (me != null && contentType == ContentType.Episode) {
                CidPrice cidPrice = subInfoManager.getCidPriceByEpsdId(tenantHeader.getLangCd(), tenantHeader.getTenantId(), basicInfo.getPartProdId());
                if (cidPrice != null) {
                    me.setUsePeriodUnitCd(cidPrice.getRentPeriodUnitCd());
                    me.setUsePeriod(cidPrice.getRentPeriod() == null ? null : cidPrice.getRentPeriod().toString());
                    me.setUsePeriodNm(cidPrice.getRentPeriodUnitNm());
                    me.setUnlmtAmt(cidPrice.getProdAmt());
                    me.setPeriodAmt(cidPrice.getRentProdAmt());
                }
            }
        }

		if (me != null)
			this.commonHandler(me, tenantHeader.getTenantId(), me.getProdId());

		return me;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.display.meta.service.MetaInfoService#getWebtoonMetaInfo(java.util.Map)
	 */
	@Override
	public MetaInfo getWebtoonMetaInfo(Map<String, Object> paramMap) {
		if (this.isUseCache()) {
			ProductBasicInfo basicInfo = (ProductBasicInfo) paramMap.get("productBasicInfo");
			TenantHeader tenantHeader = (TenantHeader) paramMap.get("tenantHeader");

			WebtoonMetaParam param = new WebtoonMetaParam();
			param.setTenantId(tenantHeader.getTenantId());
			param.setLangCd(tenantHeader.getLangCd());
			param.setContentType(ContentType.forCode(basicInfo.getContentsTypeCd()));
			if (param.getContentType() == ContentType.Channel) {
				param.setProdId(basicInfo.getProdId());
			} else if (param.getContentType() == ContentType.Episode) {
				param.setProdId(basicInfo.getPartProdId());
			}

			WebtoonMeta meta = this.productInfoManager.getWebtoonMeta(param);

			if (meta == null) {
				this.logger.warn("메타데이터를 읽을 수 없습니다 - Webtoon#{}", param.getProdId());
				return null;
			}

			MetaInfo me = new MetaInfo();
			MetaBeanUtils.setProperties(meta, me);

			me.setContentsTypeCd(param.getContentType().getCode());
			if (param.getContentType() == ContentType.Channel) {
				me.setProdAmt(meta.getChnlProdAmt());
			} else if (param.getContentType() == ContentType.Episode) {
				me.setProdAmt(meta.getEpsdProdAmt());
			}

			return me;
		} else
			return this.commonDAO.queryForObject("MetaInfo.getWebtoonMetaInfo", paramMap, MetaInfo.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.display.meta.service.MetaInfoService#getShoppingMetaInfo(java.util.Map)
	 */
	@Override
	public MetaInfo getShoppingMetaInfo(Map<String, Object> paramMap) {
		MetaInfo me;
		TenantHeader tenantHeader = (TenantHeader) paramMap.get("tenantHeader");

		if (this.isUseCache()) {
			ProductBasicInfo basicInfo = (ProductBasicInfo) paramMap.get("productBasicInfo");

			ShoppingMetaParam param = new ShoppingMetaParam();
			param.setTenantId(tenantHeader.getTenantId());
			param.setLangCd(tenantHeader.getLangCd());
			param.setCatalogId(basicInfo.getCatalogId());

			ShoppingMeta meta = this.productInfoManager.getShoppingMeta(param);
			if (meta == null) {
				this.logger.warn("메타데이터를 읽을 수 없습니다 - Shopping#{}", param.getCatalogId());
				return null;
			}
			me = new MetaInfo();
			MetaBeanUtils.setProperties(meta, me);
			me.setNo("1");
			me.setFileSize(0L);

		} else
			me = this.commonDAO.queryForObject("MetaInfo.getShoppingMetaInfo", paramMap, MetaInfo.class);

		if (me != null)
			this.commonHandler(me, tenantHeader.getTenantId(), me.getCatalogId());

		return me;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.display.meta.service.MetaInfoService#getFreepassMetaInfo(java.util.Map)
	 */
	@Override
	public MetaInfo getFreepassMetaInfo(Map<String, Object> paramMap) {
		if (this.isUseCache()) {
			FreepassMetaParam param = new FreepassMetaParam();
			ProductBasicInfo basicInfo = (ProductBasicInfo) paramMap.get("productBasicInfo");
			TenantHeader tenantHeader = (TenantHeader) paramMap.get("tenantHeader");

			param.setChannelId(basicInfo.getProdId());
			param.setLangCd(tenantHeader.getLangCd());
			param.setTenantId(tenantHeader.getTenantId());

			FreepassMeta ffMeta = this.productInfoManager.getFreepassMeta(param);
			if (ffMeta == null) {
				this.logger.warn("메타데이터를 읽을 수 없습니다 - Freepass#{}", basicInfo.getProdId());
				return null;
			}

			MetaInfo me = new MetaInfo();
			MetaBeanUtils.setProperties(ffMeta, me);

			return me;
		} else
			return this.commonDAO.queryForObject("MetaInfo.getFreepassMetaInfo", paramMap, MetaInfo.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.display.meta.service.MetaInfoService#getAlbumMetaInfo(java.util.Map)
	 */
	@Override
	public AlbumMeta getAlbumMetaInfo(Map<String, Object> paramMap) {
		AlbumMetaParam param = new AlbumMetaParam();
		ProductBasicInfo basicInfo = (ProductBasicInfo) paramMap.get("productBasicInfo");
		TenantHeader tenantHeader = (TenantHeader) paramMap.get("tenantHeader");
		param.setProdId(basicInfo.getProdId());
		param.setLangCd(tenantHeader.getLangCd());
		AlbumMeta albumMeta;
		albumMeta = this.productInfoManager.getAlbumMeta(param, this.isUseCache());
		if (albumMeta == null) {
			this.logger.warn("메타데이터를 읽을 수 없습니다 - Album#{}", basicInfo.getProdId());
		}

		return albumMeta;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.display.meta.service.MetaInfoService#getVoucherMetaInfo(java.util.Map)
	 */
	@Override
	public MetaInfo getVoucherMetaInfo(Map<String, Object> paramMap) {
		if (this.isUseCache()) {
			VoucherMetaParam param = new VoucherMetaParam();
			ProductBasicInfo basicInfo = (ProductBasicInfo) paramMap.get("productBasicInfo");
			TenantHeader tenantHeader = (TenantHeader) paramMap.get("tenantHeader");

			param.setChannelId(basicInfo.getProdId());
			param.setLangCd(tenantHeader.getLangCd());
			param.setTenantId(tenantHeader.getTenantId());

			VoucherMeta ffMeta = this.productInfoManager.getVoucherMeta(param);
			if (ffMeta == null) {
				this.logger.warn("메타데이터를 읽을 수 없습니다 - Voucher#{}", basicInfo.getProdId());
				return null;
			}

			MetaInfo me = new MetaInfo();
			MetaBeanUtils.setProperties(ffMeta, me);

			return me;
		} else
			return this.commonDAO.queryForObject("MetaInfo.getVoucherMetaInfo", paramMap, MetaInfo.class);
	}

	private boolean isUseCache() {
		return (Boolean) RequestContextHolder.currentRequestAttributes().getAttribute("useCache",
				RequestAttributes.SCOPE_REQUEST);
	}

}
