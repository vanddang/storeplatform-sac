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
import com.skplanet.storeplatform.sac.display.cache.vo.AppMetaInfo;
import com.skplanet.storeplatform.sac.display.cache.vo.AppMetaInfoParam;
import com.skplanet.storeplatform.sac.display.cache.vo.SubContent;
import com.skplanet.storeplatform.sac.display.cache.vo.MenuInfo;
import org.codehaus.jackson.map.annotate.JsonCachable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * DESC
 * </p>
 * TODO VO로 파라메터 들어오는 경우 캐쉬가 어떻게 타는지 확인해요.
 * Updated on : 2014. 03. 05 Updated by : 정희원, SK 플래닛.
 */
@Service
public class ProductInfoManagerImpl implements ProductInfoManager {

    @Autowired
    @Qualifier("sac")
    private CommonDAO commonDAO;

    @Override
    @Cacheable("appMetaInfo")
    public AppMetaInfo getAppMetaInfo(AppMetaInfoParam param) {
        final String SVC_GRP_CD = "DP000201";
        final String PROD_STATUS_CD = "PD000403";
        final String IMAGE_CD = "DP000101";

        Map<String, Object> reqMap = new HashMap<String, Object>();
        reqMap.put("channelId", param.getChannelId());
        reqMap.put("langCd", param.getLangCd());
        reqMap.put("tenantId", param.getTenantId());
        reqMap.put("prodStatusCd", PROD_STATUS_CD);
        reqMap.put("imageCd", IMAGE_CD);
        reqMap.put("svcGrpCd", SVC_GRP_CD);

        return commonDAO.queryForObject("ProductInfo.getAppInfo", reqMap, AppMetaInfo.class);
    }

    @Override
    @Cacheable("subContent")
    public SubContent getSubContent(String prodId, String deviceModelCd) {
        Map<String, String> reqMap = new HashMap<String, String>();
        reqMap.put("prodId", prodId);
        reqMap.put("deviceModelCd", deviceModelCd);

        return commonDAO.queryForObject("ProductInfo.getSubContent", reqMap, SubContent.class);
    }

    @Override
    @Cacheable("menuInfo")
    public MenuInfo getMenuInfo(String langCd, String menuId, String prodId) {
        Map<String, String> reqMap = new HashMap<String, String>();
        reqMap.put("prodId", prodId);
        reqMap.put("menuId", menuId);
        reqMap.put("langCd", langCd);
        // TODO 몇Depth메뉴인지 판단을 해야 함
        return commonDAO.queryForObject("ProductInfo.getMenuInfo", reqMap, MenuInfo.class);
    }
}
