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

/**
 * <p>
 * TB_DP_MENU_LIST_CATEGORY VO
 * </p>
 * Updated on : 2014. 10. 20 Updated by : 정희원, SK 플래닛.
 */
public class MenuListCat {

    private String tenantId;
    private String systemId;
    private String menuKey;
    private String upMenuKey;
    private String topMenuKey;
    private Integer expoOrd;
    private String expoYn;
    private String imgPath;
    private String menuNm;
    private String injtVar;
    private String keyType;

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getMenuKey() {
        return menuKey;
    }

    public void setMenuKey(String menuKey) {
        this.menuKey = menuKey;
    }

    public String getUpMenuKey() {
        return upMenuKey;
    }

    public void setUpMenuKey(String upMenuKey) {
        this.upMenuKey = upMenuKey;
    }

    public String getTopMenuKey() {
        return topMenuKey;
    }

    public void setTopMenuKey(String topMenuKey) {
        this.topMenuKey = topMenuKey;
    }

    public Integer getExpoOrd() {
        return expoOrd;
    }

    public void setExpoOrd(Integer expoOrd) {
        this.expoOrd = expoOrd;
    }

    public String getExpoYn() {
        return expoYn;
    }

    public void setExpoYn(String expoYn) {
        this.expoYn = expoYn;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getMenuNm() {
        return menuNm;
    }

    public void setMenuNm(String menuNm) {
        this.menuNm = menuNm;
    }

    public String getInjtVar() {
        return injtVar;
    }

    public void setInjtVar(String injtVar) {
        this.injtVar = injtVar;
    }

    public String getKeyType() {
        return keyType;
    }

    public void setKeyType(String keyType) {
        this.keyType = keyType;
    }
}
