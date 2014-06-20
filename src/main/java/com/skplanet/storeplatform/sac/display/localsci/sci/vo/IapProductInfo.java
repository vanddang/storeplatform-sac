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

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * <p>
 * IapProductInfo
 * </p>
 * Updated on : 2014. 04. 16 Updated by : 정희원, SK 플래닛.
 */
public class IapProductInfo extends CommonInfo {
    private static final long serialVersionUID = 1L;

    private String parentProdId;
    private String partProdId;
    private String partProductType;
    private String hasFullProdYn;
    private String fullAid;
    private String fullProdId;
    private String menuId;
    private String prodCase;
    private String prodKind;

    public String getProdCase() {
        return prodCase;
    }

    public void setProdCase(String prodCase) {
        this.prodCase = prodCase;
    }

    public String getProdKind() {
        return prodKind;
    }

    public void setProdKind(String prodKind) {
        this.prodKind = prodKind;
    }

    public String getParentProdId() {
        return parentProdId;
    }

    public void setParentProdId(String parentProdId) {
        this.parentProdId = parentProdId;
    }

    public String getPartProdId() {
        return partProdId;
    }

    public void setPartProdId(String partProdId) {
        this.partProdId = partProdId;
    }

    public String getPartProductType() {
        return partProductType;
    }

    public void setPartProductType(String partProductType) {
        this.partProductType = partProductType;
    }

    public String getHasFullProdYn() {
        return hasFullProdYn;
    }

    public void setHasFullProdYn(String hasFullProdYn) {
        this.hasFullProdYn = hasFullProdYn;
    }

    public String getFullAid() {
        return fullAid;
    }

    public void setFullAid(String fullAid) {
        this.fullAid = fullAid;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getFullProdId() {
        return fullProdId;
    }

    public void setFullProdId(String fullProdId) {
        this.fullProdId = fullProdId;
    }
}
