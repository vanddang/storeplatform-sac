/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.common.service.menu;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>
 * MenuInfoServiceImpl
 * </p>
 * Updated on : 2014. 11. 06 Updated by : 정희원, SK 플래닛.
 */
@Service
public class MenuInfoServiceImpl implements MenuInfoService {

    @Autowired
    @Qualifier("sac")
    private CommonDAO commonDAO;

    private Map<String, MenuInfo> menuInfoMap;

    @PostConstruct
    public synchronized void load() {
        menuInfoMap = new ConcurrentHashMap<String, MenuInfo>();

        List menuList = commonDAO.queryForList("DisplayCommon.getMenuInfo", null);
        for (Object o : menuList) {
            Map v = (Map) o;
            String menuId = (String)v.get("MENU_ID");
            String nm = (String)v.get("MENU_NM");
            String desc = (String)v.get("MENU_DESC");
            String langCd = (String)v.get("LANG_CD");

            MenuInfo menuInfo = menuInfoMap.get(menuId);
            if(menuInfo == null) {
                menuInfo = new MenuInfo(menuId);
                menuInfoMap.put(menuId, menuInfo);
            }
            menuInfo.setDescription(langCd, nm, desc);
        }
    }

    @Override
    public MenuInfo getMenuInfo(String menuId) {
        return this.menuInfoMap.get(menuId);
    }

    @Override
    public String getMenuName(String menuId, String langCd) {
        MenuInfo menuInfo = this.menuInfoMap.get(menuId);
        if(menuInfo == null)
            return "";

        return menuInfo.getName(langCd);
    }

    @Override
    public String getMenuDesc(String menuId, String langCd) {
        MenuInfo menuInfo = this.menuInfoMap.get(menuId);
        if(menuInfo == null)
            return "";

        return menuInfo.getDescription(langCd);
    }
}
