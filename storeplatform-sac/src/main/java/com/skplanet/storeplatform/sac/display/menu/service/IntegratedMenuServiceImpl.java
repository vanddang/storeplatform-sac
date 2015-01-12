/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.menu.service;

import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.sac.client.product.vo.MenuDetail;
import com.skplanet.storeplatform.sac.display.cache.service.PanelCardInfoManager;
import com.skplanet.storeplatform.sac.display.cache.vo.MenuListCat;
import com.skplanet.storeplatform.sac.display.menu.vo.IntegratedMenuList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * IntegratedMenuServiceImpl
 * </p>
 * Updated on : 2014. 10. 20 Updated by : 정희원, SK 플래닛.
 */
@Service
public class IntegratedMenuServiceImpl implements IntegratedMenuService {

    public static final String MENU_KEY_ROOT = "ROOT";
    public static final String MENU_TP_MENU = "DP01230001";
    public static final String MENU_TP_FEATURED = "DP01230002";

    @Autowired
    private PanelCardInfoManager panelCardInfoManager;

    @Override
    @SuppressWarnings("unchecked")
    public IntegratedMenuList getIntegratedMenuList(String tenantId, String systemId, String langCd, String upMenuKey) {

        List<MenuListCat> listCatList = panelCardInfoManager.getMenuList(tenantId, systemId, langCd,
                                                    StringUtils.isNotEmpty(upMenuKey) ? upMenuKey : MENU_KEY_ROOT);

        List<MenuDetail> featuredMenuList = new ArrayList<MenuDetail>();
        List<MenuDetail> categoryMenuList = new ArrayList<MenuDetail>();
        for (MenuListCat mc : listCatList) {
            MenuDetail md = new MenuDetail();
            md.setTenantId(mc.getTenantId());
            md.setSystemId(mc.getSystemId());
            md.setMenuKey(mc.getMenuKey());
            md.setExpoOrd(mc.getExpoOrd());
            md.setImgPath(mc.getImgPath());
            md.setMenuName(mc.getMenuNm());
            md.setUrlParam(mc.getInjtVar());

            if(MENU_TP_MENU.equals(mc.getKeyType()))
                categoryMenuList.add(md);
            else if(MENU_TP_FEATURED.equals(mc.getKeyType()))
                featuredMenuList.add(md);
        }

        return new IntegratedMenuList(featuredMenuList, categoryMenuList);
    }
}
