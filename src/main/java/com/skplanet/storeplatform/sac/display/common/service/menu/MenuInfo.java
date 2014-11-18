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

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <p>
 * MenuInfo
 * </p>
 * Updated on : 2014. 11. 06 Updated by : 정희원, SK 플래닛.
 */
public class MenuInfo {

    private String menuId;
    private String topMenuId;
    private Map<String, MenuDescription> descriptionMap;

    MenuInfo(String menuId) {
        this.menuId = menuId;
        this.topMenuId = menuId.substring(0, 4);
        this.descriptionMap = new LinkedHashMap<String, MenuDescription>();
    }

    void setDescription(String langCd, String name, String description) {
        this.descriptionMap.put(langCd, new MenuDescription(name, description));
    }

    public String getMenuId() {
        return menuId;
    }

    public String getTopMenuId() {
        return topMenuId;
    }

    public String getName(String langCd) {
        MenuDescription md = getMenuDesc(langCd);
        if(md == null)
            return "";

        return md.name;
    }

    public String getDescription(String langCd) {
        MenuDescription md = getMenuDesc(langCd);
        if(md == null)
            return "";

        return md.description;
    }

    private MenuDescription getMenuDesc(String langCd) {
        MenuDescription md = descriptionMap.get(langCd);
        if(md != null)
            return md;

        // FIXME 기본 언어를 조회한다.
        md = descriptionMap.get("ko");
        return md;
    }

    private class MenuDescription {
        MenuDescription(String name, String description) {
            this.name = name;
            this.description = description;
        }
        private String name;
        private String description;
    }
}
