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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * MenuIntegraionServiceImpl
 * </p>
 * Updated on : 2014. 10. 20 Updated by : 정희원, SK 플래닛.
 */
@Service
public class MenuIntegrationServiceImpl implements MenuIntegrationService {

    public static final String MENU_KEY_ROOT = "ROOT";

    @Autowired
    private PanelCardInfoManager panelCardInfoManager;

    @Override
    @SuppressWarnings("unchecked")
    public List<MenuDetail> selectMenuIntegrationList(String tenantId, String systemId, String langCd, String upMenuKey) {

        List<MenuListCat> listCatList = panelCardInfoManager.getMenuList(tenantId, systemId, langCd, StringUtils.isNotEmpty(upMenuKey) ? upMenuKey : MENU_KEY_ROOT);

        List<MenuDetail> menuDetailList = new ArrayList<MenuDetail>();
        for (MenuListCat mc : listCatList) {
            MenuDetail md = new MenuDetail();
            md.setTenantId(mc.getTenantId());
            md.setSystemId(mc.getSystemId());
            md.setMenuKey(mc.getMenuKey());
            md.setExpoOrd(mc.getExpoOrd());
            md.setImgPath(mc.getImgPath());
            md.setMenuName(mc.getMenuNm());
            md.setKeyType(mc.getKeyType());
            md.setUrlParam(mc.getInjtVar());

            menuDetailList.add(md);
        }

        return menuDetailList;
    }
}
