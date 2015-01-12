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
 * DESC
 * </p>
 * Updated on : 2014. 06. 12 Updated by : 정희원, SK 플래닛.
 */
public class UpdateProductParam {

    private String tenantId;
    private String langCd;
    private String epsdId;
    private String subContentsId;

    public UpdateProductParam(String tenantId, String langCd, String epsdId, String subContentsId) {
        this.tenantId = tenantId;
        this.langCd = langCd;
        this.epsdId = epsdId;
        this.subContentsId = subContentsId;
    }

    public String getLangCd() {
        return langCd;
    }

    public void setLangCd(String langCd) {
        this.langCd = langCd;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getEpsdId() {
        return epsdId;
    }

    public void setEpsdId(String epsdId) {
        this.epsdId = epsdId;
    }

    public String getSubContentsId() {
        return subContentsId;
    }

    public void setSubContentsId(String subContentsId) {
        this.subContentsId = subContentsId;
    }

    public String getCacheKey() {
        return tenantId + "_" + langCd + "_" + epsdId + "_" + subContentsId;
    }
}
