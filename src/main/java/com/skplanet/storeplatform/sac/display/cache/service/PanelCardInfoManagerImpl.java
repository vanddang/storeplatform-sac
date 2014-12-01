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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.cache.LocalCacheable;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.display.cache.vo.CardInfo;
import com.skplanet.storeplatform.sac.display.cache.vo.MenuListCat;
import com.skplanet.storeplatform.sac.display.cache.vo.PanelCardMapping;
import com.skplanet.storeplatform.sac.display.cache.vo.PanelItem;
import com.skplanet.storeplatform.sac.display.card.vo.CardSegment;

/**
 * <p>
 * PanelCardInfoManagerImpl
 * </p>
 * Updated on : 2014. 11. 12 Updated by : 정희원, SK 플래닛.
 */
@Service
public class PanelCardInfoManagerImpl implements PanelCardInfoManager {

    @Autowired
    @Qualifier("sac")
    private CommonDAO commonDAO;

    @Override
    @LocalCacheable(value = "sac:display:getCardInfo", key = "#tenantId + '_' + #cardId")
    public CardInfo getCardInfo(String tenantId, String cardId) {
    	Map<String, Object> req = new HashMap<String, Object>();
        req.put("tenantId", tenantId);
        req.put("cardId", cardId);
        return commonDAO.queryForObject("CardInfo.getCardInfo", req, CardInfo.class);
    }

    @Override
    @LocalCacheable(value = "sac:display:getPanelList", key = "#tenantId + '_' + #panelId")
    public List<PanelItem> getPanelList(String tenantId, String panelId) {
        Map<String, Object> req = new HashMap<String, Object>();
        req.put("tenantId", tenantId);
        req.put("panelId", panelId + "%");
        return commonDAO.queryForList("PanelCardInfo.getPanelList", req, PanelItem.class);
    }

    @Override
    @LocalCacheable(value = "sac:display:getCardListInPanel", key = "#tenantId + '_' + #panelId")
    public List<PanelCardMapping> getCardListInPanel(String tenantId, String panelId) {
        Map<String, Object> req = new HashMap<String, Object>();
        req.put("tenantId", tenantId);
        req.put("panelId", panelId);
        return commonDAO.queryForList("PanelCardInfo.getPanelMappingList", req, PanelCardMapping.class);
    }

    @Override
    @LocalCacheable(value = "sac:display:getCardSegmentInfo", key = "#tenantId + '_' + #cardId")
    public CardSegment getCardSegmentInfo(String tenantId, String cardId) {
        Map<String, Object> req = new HashMap<String, Object>();
        req.put("tenantId", tenantId);
        req.put("cardId", cardId);

        return commonDAO.queryForObject("PanelCardInfo.getCardSegment", req, CardSegment.class);
    }

    @Override
    @LocalCacheable(value = "sac:display:getMenuList", key = "#systemId + '_' + #langCd + '_' + #upMenuKey")
    public List<MenuListCat> getMenuList(String tenantId, String systemId, String langCd, String upMenuKey) {
        Map<String, Object> req = new HashMap<String, Object>();
        req.put("tenantId", tenantId);
        req.put("systemId", systemId);
        req.put("langCd", langCd);
        req.put("upMenuKey", upMenuKey);

        return commonDAO.queryForList("PanelCardInfo.getIntegratedMenuList", req, MenuListCat.class);
    }
}
