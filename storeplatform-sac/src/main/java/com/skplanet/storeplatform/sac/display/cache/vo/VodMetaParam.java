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
import com.skplanet.storeplatform.sac.display.common.ContentType;

/**
 * <p>
 * VodMetaParam
 * Vod의 경우 channelId, episodeId 로 요청될 수 있음.
 * </p>
 * Updated on : 2014. 04. 15 Updated by : 정희원, SK 플래닛.
 */
public class VodMetaParam extends CommonInfo {
    private static final long serialVersionUID = 1L;

    private String langCd;
    private String prodId;
    private String tenantId;
    private ContentType contentType;

    public VodMetaParam() {}

    public VodMetaParam(String prodId, String langCd, String tenantId) {
        this.prodId = prodId;
        this.langCd = langCd;
        this.tenantId = tenantId;
    }

    public String getLangCd() {
        return langCd;
    }

    public void setLangCd(String langCd) {
        this.langCd = langCd;
    }

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }

    public String getCacheKey() {
        return prodId + "_" + langCd + "_" + tenantId;
    }
}
