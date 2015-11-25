/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.internal.display.localsci.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * <p>
 * IapProductInfoRes
 * </p>
 * Updated on : 2014. 04. 16 Updated by : 정희원, SK 플래닛.
 */
public class IapProductInfoRes extends CommonInfo {
    private static final long serialVersionUID = 1L;

    private String parentProdId;
    private String partProdId;
    private String hasFullProdYn;
    private String fullAid;
    private String fullProdId;
    private String menuId;
    private String prodCase;
    private String prodKind;
    private String postbackUrl;
    private Integer usePeriod;
    private String s2sMonthlyFreepassYn;

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

    public String getPostbackUrl() {
        return postbackUrl;
    }

    public void setPostbackUrl(String postbackUrl) {
        this.postbackUrl = postbackUrl;
    }

    public Integer getUsePeriod() {
        return usePeriod;
    }

    public void setUsePeriod(Integer usePeriod) {
        this.usePeriod = usePeriod;
    }

    public String getS2sMonthlyFreepassYn() {
        return s2sMonthlyFreepassYn;
    }

    public void setS2sMonthlyFreepassYn(String s2sMonthlyFreepassYn) {
        this.s2sMonthlyFreepassYn = s2sMonthlyFreepassYn;
    }
}
