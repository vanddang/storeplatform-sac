/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.cache.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * <p>
 * DESC
 * </p>
 * Updated on : 2014. 03. 05 Updated by : 정희원, SK 플래닛.
 */
public class MenuInfo extends CommonInfo {
    private static final long serialVersionUID = 1L;

    private String topMenuId;
    private String topMenuNm;
    private String topMenuDesc;
    private String upMenuId;
    private String menuId;
    private String menuNm;
    private String menuDesc;

    public String getTopMenuId() {
        return topMenuId;
    }

    public void setTopMenuId(String topMenuId) {
        this.topMenuId = topMenuId;
    }

    public String getTopMenuNm() {
        return topMenuNm;
    }

    public void setTopMenuNm(String topMenuNm) {
        this.topMenuNm = topMenuNm;
    }

    public String getTopMenuDesc() {
        return topMenuDesc;
    }

    public void setTopMenuDesc(String topMenuDesc) {
        this.topMenuDesc = topMenuDesc;
    }

    public String getUpMenuId() {
        return upMenuId;
    }

    public void setUpMenuId(String upMenuId) {
        this.upMenuId = upMenuId;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getMenuNm() {
        return menuNm;
    }

    public void setMenuNm(String menuNm) {
        this.menuNm = menuNm;
    }

    public String getMenuDesc() {
        return menuDesc;
    }

    public void setMenuDesc(String menuDesc) {
        this.menuDesc = menuDesc;
    }
}
