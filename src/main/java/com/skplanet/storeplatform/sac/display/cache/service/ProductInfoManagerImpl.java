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

import com.skplanet.spring.data.plandasj.PlandasjTemplate;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.display.cache.vo.*;
import com.skplanet.storeplatform.sac.display.common.ContentType;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * ProductInfoManagerImpl
 * </p>
 * Updated on : 2014. 03. 05 Updated by : 정희원, SK 플래닛.
 */
@Service
public class ProductInfoManagerImpl implements ProductInfoManager {

    @Autowired
    @Qualifier("sac")
    private CommonDAO commonDAO;

    @Autowired(required = false)
    PlandasjTemplate<String, String> plandasjTemplate;

    private static final String APP_SVC_GRP_CD = "DP000201";
    private static final String APP_IMG_CD = "DP000101";
    private static final String MUSIC_SVC_GRP_CD = "DP000203";
    private static final String MUSIC_IMG_CD = "DP000162";
    private static final String MUSIC_SVC_TP_CD = "DP001111";
    private static final String SHOPPING_SVC_GRP_CD = "DP000206";
    private static final String FREEPASS_SVC_GRP_CD = "DP000207";

    @Override
    @Cacheable(value = "sac:display:product:app:v2", key = "#param.getCacheKey()", unless = "#result == null")
    public AppMeta getAppMeta(AppMetaParam param) {

        Map<String, Object> reqMap = new HashMap<String, Object>();
        reqMap.put("channelId", param.getChannelId());
        reqMap.put("langCd", param.getLangCd());
        reqMap.put("tenantId", param.getTenantId());
        reqMap.put("imageCd", APP_IMG_CD);
        reqMap.put("svcGrpCd", APP_SVC_GRP_CD);

        return commonDAO.queryForObject("ProductInfo.getAppMeta", reqMap, AppMeta.class);
    }

    @Override
    public List<AppMeta> getAppMetaList(String langCd, String tenantId, List<String> prodIdList, String deviceModelCd) {

        Map<String, Object> reqMap = new HashMap<String, Object>();
        reqMap.put("prodIdList", prodIdList);  // MyBatis에서는 foreach에서 목록이 역순으로 생성됨.
        reqMap.put("langCd", langCd);
        reqMap.put("tenantId", tenantId);
        reqMap.put("deviceModelCd", deviceModelCd);
        reqMap.put("imageCd", APP_IMG_CD);
        reqMap.put("svcGrpCd", APP_SVC_GRP_CD);

        return commonDAO.queryForList("ProductInfo.getAppMetaList", reqMap, AppMeta.class);
    }

    @Override
    @Cacheable(value = "sac:display:product:music:v2", key = "#param.getCacheKey()", unless = "#result == null")
    public MusicMeta getMusicMeta(MusicMetaParam param) {

        Map<String, Object> reqMap = new HashMap<String, Object>();
        reqMap.put("channelId", param.getChannelId());
        reqMap.put("langCd", param.getLangCd());
        reqMap.put("tenantId", param.getTenantId());
        reqMap.put("imageCd", MUSIC_IMG_CD);
        reqMap.put("svcGrpCd", MUSIC_SVC_GRP_CD);
        reqMap.put("svcTypeCd", MUSIC_SVC_TP_CD);

        if(StringUtils.isNotEmpty(param.getChartClsfCd()) && StringUtils.isNotEmpty(param.getRankStartDay())) {
            reqMap.put("chartClsfCd", param.getChartClsfCd());
            reqMap.put("rankStartDay", param.getRankStartDay());
        } else {
            reqMap.put("chartClsfCd", "");
            reqMap.put("rankStartDay", "");
        }

        return commonDAO.queryForObject("ProductInfo.getMusicMeta", reqMap, MusicMeta.class);
    }

    @Override
    public List<MusicMeta> getMusicMetaList(String langCd, String tenantId, String chartClsfCd, String stdDt, List<String> prodIdList) {
        Map<String, Object> reqMap = new HashMap<String, Object>();
        reqMap.put("prodIdList", prodIdList);
        reqMap.put("langCd", langCd);
        reqMap.put("tenantId", tenantId);
        reqMap.put("imageCd", DisplayConstants.DP_MUSIC_REPRESENT_IMAGE_CD);
        reqMap.put("svcGrpCd", MUSIC_SVC_GRP_CD);
        reqMap.put("svcTypeCd", MUSIC_SVC_TP_CD);

        if(StringUtils.isNotEmpty(chartClsfCd) && StringUtils.isNotEmpty(stdDt)) {
            reqMap.put("chartClsfCd", chartClsfCd);
            reqMap.put("rankStartDay", stdDt);
        } else {
            reqMap.put("chartClsfCd", "");
            reqMap.put("rankStartDay", "");
        }

        return commonDAO.queryForList("ProductInfo.getMusicMetaList", reqMap, MusicMeta.class);
    }

    @Override
    @Cacheable(value = "sac:display:product:vod:v2", key = "#param.getCacheKey()", unless = "#result == null")
    public VodMeta getVodMeta(VodMetaParam param) {
        Map<String, Object> reqMap = new HashMap<String, Object>();
        reqMap.put("prodId", param.getProdId());
        reqMap.put("langCd", param.getLangCd());
        reqMap.put("tenantId", param.getTenantId());
        reqMap.put("imageCd", DisplayConstants.DP_VOD_REPRESENT_IMAGE_CD);
        if(param.getContentType() == ContentType.Channel)
            reqMap.put("prodIdType", "CHANNEL");
        else if(param.getContentType() == ContentType.Episode)
            reqMap.put("prodIdType", "EPISODE");
        else
            throw new RuntimeException("prodIdType cannot be null.");

        return commonDAO.queryForObject("ProductInfo.getVodMeta", reqMap, VodMeta.class);
    }

    @Override
    @Cacheable(value = "sac:display:product:ebookcomic", key = "#param.getCacheKey()", unless = "#result == null")
    public EbookComicMeta getEbookComicMeta(EbookComicMetaParam param) {
        Map<String, Object> reqMap = new HashMap<String, Object>();
        reqMap.put("prodId", param.getProdId());
        reqMap.put("langCd", param.getLangCd());
        reqMap.put("tenantId", param.getTenantId());
        reqMap.put("imageCd", DisplayConstants.DP_EBOOK_COMIC_REPRESENT_IMAGE_CD);
        if(param.getContentType() == ContentType.Channel)
            reqMap.put("prodIdType", "CHANNEL");
        else if(param.getContentType() == ContentType.Episode)
            reqMap.put("prodIdType", "EPISODE");
        else
            throw new RuntimeException("prodIdType cannot be null.");

        return commonDAO.queryForObject("ProductInfo.getEbookComicMeta", reqMap, EbookComicMeta.class);
    }

    @Override
    @Cacheable(value = "sac:display:product:webtoon", key = "#param.getCacheKey()", unless = "#result == null")
    public WebtoonMeta getWebtoonMeta(WebtoonMetaParam param) {
        // NOTICE EbookComic, Webtoon은 조회방법은 동일하지만 요구되는 응답값이 다르다.
        Map<String, Object> reqMap = new HashMap<String, Object>();
        reqMap.put("prodId", param.getProdId());
        reqMap.put("langCd", param.getLangCd());
        reqMap.put("tenantId", param.getTenantId());
        reqMap.put("imageCd", DisplayConstants.DP_WEBTOON_REPRESENT_IMAGE_CD);
        if(param.getContentType() == ContentType.Channel)
            reqMap.put("prodIdType", "CHANNEL");
        else if(param.getContentType() == ContentType.Episode)
            reqMap.put("prodIdType", "EPISODE");
        else
            throw new RuntimeException("prodIdType cannot be null.");

        return commonDAO.queryForObject("ProductInfo.getWebtoonMeta", reqMap, WebtoonMeta.class);
    }

    @Override
    @Cacheable(value = "sac:display:product:shopping", key = "#param.getCacheKey()", unless = "#result == null")
    public ShoppingMeta getShoppingMeta(ShoppingMetaParam param) {
        Map<String, Object> reqMap = new HashMap<String, Object>();
        reqMap.put("catalogId", param.getCatalogId());
        reqMap.put("langCd", param.getLangCd());
        reqMap.put("tenantId", param.getTenantId());
        reqMap.put("svcGrpCd", SHOPPING_SVC_GRP_CD);
        reqMap.put("imageCd", DisplayConstants.DP_SHOPPING_REPRESENT_IMAGE_CD);

        return commonDAO.queryForObject("ProductInfo.getShoppingMeta", reqMap, ShoppingMeta.class);
    }

    @Override
    @Cacheable(value = "sac:display:product:freepass", key = "#param.getCacheKey()", unless = "#result == null")
    public FreepassMeta getFreepassMeta(FreepassMetaParam param) {
        Map<String, Object> reqMap = new HashMap<String, Object>();
        reqMap.put("prodId", param.getChannelId());
        reqMap.put("langCd", param.getLangCd());
        reqMap.put("tenantId", param.getTenantId());
        reqMap.put("svcGrpCd", FREEPASS_SVC_GRP_CD);
        reqMap.put("thmbImageCd", DisplayConstants.DP_FREEPASS_THUMBNAIL_IMAGE_CD);
        reqMap.put("bannImageCd", DisplayConstants.DP_FREEPASS_BANNER_IMAGE_CD);
        reqMap.put("ebookImageCd", DisplayConstants.DP_FREEPASS_EBOOK_THUMBNAIL_IMAGE_CD);

        return commonDAO.queryForObject("ProductInfo.getFreepassMeta", reqMap, FreepassMeta.class);
    }

    @Override
    @Cacheable(value = "sac:display:subcontent", key = "#param.getCacheKey()", unless = "#result == null")
    public SubContent getSubContent(SubContentParam param) {
        Map<String, String> reqMap = new HashMap<String, String>();
        reqMap.put("prodId", param.getChannelId());
        reqMap.put("deviceModelCd", param.getDeviceModel());

        return commonDAO.queryForObject("ProductInfo.getSubContent", reqMap, SubContent.class);
    }

    @Override
    @Cacheable(value = "sac:display:menuinfo", key = "#param.getCacheKey()", unless = "#result == null")
    public MenuInfo getMenuInfo(MenuInfoParam param) {
        Map<String, String> reqMap = new HashMap<String, String>();
        reqMap.put("prodId", param.getChannelId());
        if(param.getMenuId() != null)
            reqMap.put("menuId", param.getMenuId());
        reqMap.put("langCd", param.getLangCd());
        // TODO 몇Depth메뉴인지 판단을 해야 함
        return commonDAO.queryForObject("ProductInfo.getMenuInfo", reqMap, MenuInfo.class);
    }
}
