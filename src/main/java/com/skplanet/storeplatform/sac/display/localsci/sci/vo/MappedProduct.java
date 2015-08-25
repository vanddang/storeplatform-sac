/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.localsci.sci.vo;

/**
 * <p>
 * MappedProduct
 * </p>
 * Updated on : 2015. 08. 25 Updated by : 정희원, SK 플래닛.
 */
public class MappedProduct {

    private String prodId;
    private String menuId;
    private String topMenuId;

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getTopMenuId() {
        return topMenuId;
    }

    public void setTopMenuId(String topMenuId) {
        this.topMenuId = topMenuId;
    }
}
