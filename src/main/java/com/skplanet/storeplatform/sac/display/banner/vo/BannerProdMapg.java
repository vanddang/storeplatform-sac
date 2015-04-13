/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */

package com.skplanet.storeplatform.sac.display.banner.vo;

/**
 * <pre>
 * Banner Prod Mapg Value Object
 * <pre>
 *
 * Updated on : 2015-04-13. Updated By : 양해엽, SK Planet.
 */
public class BannerProdMapg {
    private String tenantId;
    private String bnrMenuId;
    private String bnrSeq;
    private String bnrExpoMenuId;
    private String imgSizeCd;

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getBnrMenuId() {
        return bnrMenuId;
    }

    public void setBnrMenuId(String bnrMenuId) {
        this.bnrMenuId = bnrMenuId;
    }

    public String getBnrSeq() {
        return bnrSeq;
    }

    public void setBnrSeq(String bnrSeq) {
        this.bnrSeq = bnrSeq;
    }

    public String getBnrExpoMenuId() {
        return bnrExpoMenuId;
    }

    public void setBnrExpoMenuId(String bnrExpoMenuId) {
        this.bnrExpoMenuId = bnrExpoMenuId;
    }

    public String getImgSizeCd() {
        return imgSizeCd;
    }

    public void setImgSizeCd(String imgSizeCd) {
        this.imgSizeCd = imgSizeCd;
    }
}
