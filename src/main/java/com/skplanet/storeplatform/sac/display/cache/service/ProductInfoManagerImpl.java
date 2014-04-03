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

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.display.cache.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
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

    @Override
    @Cacheable(value = "sac:display:appmeta", key = "#param.getCacheKey()")
    public AppMeta getAppMeta(AppMetaParam param) {
        final String SVC_GRP_CD = "DP000201";
        final String IMAGE_CD = "DP000101";

        Map<String, Object> reqMap = new HashMap<String, Object>();
        reqMap.put("channelId", param.getChannelId());
        reqMap.put("langCd", param.getLangCd());
        reqMap.put("tenantId", param.getTenantId());
        reqMap.put("imageCd", IMAGE_CD);
        reqMap.put("svcGrpCd", SVC_GRP_CD);

        return commonDAO.queryForObject("ProductInfo.getAppInfo", reqMap, AppMeta.class);
    }

    @Override
    @CacheEvict(value = "sac:display:appmeta", key = "#param.getCacheKey()")
    public void evictAppMeta(AppMetaParam param) {

    }

    @Override
    @Cacheable(value = "sac:display:mmmeta", key = "#param.getCacheKey()")
    public MultimediaMeta getMultimediaMeta(MultimediaMetaParam param) {
        return null;
    }

    @Override
    @Cacheable("sac:display:subcontent")
    public SubContent getSubContent(String prodId, String deviceModelCd) {
        Map<String, String> reqMap = new HashMap<String, String>();
        reqMap.put("prodId", prodId);
        reqMap.put("deviceModelCd", deviceModelCd);

        return commonDAO.queryForObject("ProductInfo.getSubContent", reqMap, SubContent.class);
    }

    @Override
    @Cacheable("sac:display:menuinfo")
    public MenuInfo getMenuInfo(String langCd, String menuId, String prodId) {
        Map<String, String> reqMap = new HashMap<String, String>();
        reqMap.put("prodId", prodId);
        if(menuId != null)
            reqMap.put("menuId", menuId);
        reqMap.put("langCd", langCd);
        // TODO 몇Depth메뉴인지 판단을 해야 함
        return commonDAO.queryForObject("ProductInfo.getMenuInfo", reqMap, MenuInfo.class);
    }
}
