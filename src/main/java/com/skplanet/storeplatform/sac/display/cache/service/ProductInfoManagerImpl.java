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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Collections;
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

    @Autowired
    PlandasjTemplate<String, String> plandasjTemplate;

    @Override
    @Cacheable(value = "sac:display:product:app", key = "#param.getCacheKey()", unless = "#result == null")
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
    public List<AppMeta> getAppMetaList(String langCd, String tenantId, List<String> prodIdList, String deviceModelCd) {
        final String SVC_GRP_CD = "DP000201";
        final String IMAGE_CD = "DP000101";

        Map<String, Object> reqMap = new HashMap<String, Object>();
        reqMap.put("prodIdList", prodIdList);  // MyBatis에서는 foreach에서 목록이 역순으로 생성됨.
        reqMap.put("langCd", langCd);
        reqMap.put("tenantId", tenantId);
        reqMap.put("deviceModelCd", deviceModelCd);
        reqMap.put("imageCd", IMAGE_CD);
        reqMap.put("svcGrpCd", SVC_GRP_CD);

        return commonDAO.queryForList("ProductInfo.getAppInfoList", reqMap, AppMeta.class);
    }

    @Override
    @CacheEvict(value = "sac:display:product:app", key = "#param.getCacheKey()")
    public void evictAppMeta(AppMetaParam param) {
        // TODO subContent, menuInfo도 함께
    }

    @Override
    @CacheEvict(value = "sac:display:product:app", allEntries = true)
    public void evictAllAppMeta() { }

    @Override
    @Cacheable(value = "sac:display:product:mm", key = "#param.getCacheKey()", unless = "#result == null")
    public MultimediaMeta getMultimediaMeta(MultimediaMetaParam param) {
        return null;
    }

    @Override
    @Cacheable(value = "sac:display:subcontent", unless = "#result == null")
    public SubContent getSubContent(String prodId, String deviceModelCd) {
        Map<String, String> reqMap = new HashMap<String, String>();
        reqMap.put("prodId", prodId);
        reqMap.put("deviceModelCd", deviceModelCd);

        return commonDAO.queryForObject("ProductInfo.getSubContent", reqMap, SubContent.class);
    }

    @Override
    @Cacheable(value = "sac:display:menuinfo", unless = "#result == null")
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
