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
 * ShoppingMetaParam
 * </p>
 * Updated on : 2014. 04. 11 Updated by : 정희원, SK 플래닛.
 */
public class ShoppingMetaParam extends CommonInfo {
    private static final long serialVersionUID = 1L;

    private String langCd;
    private String catalogId;
    private String tenantId;

    public ShoppingMetaParam() {}

    public ShoppingMetaParam(String catalogId, String langCd, String tenantId) {
        this.catalogId = catalogId;
        this.langCd = langCd;
        this.tenantId = tenantId;
    }

    public String getLangCd() {
        return langCd;
    }

    public void setLangCd(String langCd) {
        this.langCd = langCd;
    }

    public String getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(String catalogId) {
        this.catalogId = catalogId;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getCacheKey() {
        return catalogId + "_" + langCd + "_" + tenantId;
    }
}
