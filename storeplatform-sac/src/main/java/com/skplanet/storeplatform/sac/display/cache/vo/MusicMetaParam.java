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
 * MusicMetaParam
 * </p>
 * Updated on : 2014. 04. 09 Updated by : 정희원, SK 플래닛.
 */
public class MusicMetaParam extends CommonInfo {
    private static final long serialVersionUID = 1L;

    private String tenantId;
    private String langCd;
    private String prodId;
    private ContentType contentType;
    private String episodeSvcGrpCd;

    private String chartClsfCd;
    private String rankStartDay;

    public MusicMetaParam() {}

    public MusicMetaParam(String prodId, String langCd, String tenantId) {
        this.prodId = prodId;
        this.langCd = langCd;
        this.tenantId = tenantId;
    }

    public String getChartClsfCd() {
        return chartClsfCd;
    }

    public void setChartClsfCd(String chartClsfCd) {
        this.chartClsfCd = chartClsfCd;
    }

    public String getRankStartDay() {
        return rankStartDay;
    }

    public void setRankStartDay(String rankStartDay) {
        this.rankStartDay = rankStartDay;
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

    public String getEpisodeSvcGrpCd() {
		return episodeSvcGrpCd;
	}

	public void setEpisodeSvcGrpCd(String episodeSvcGrpCd) {
		this.episodeSvcGrpCd = episodeSvcGrpCd;
	}

	public String getCacheKey() {
        return prodId + "_" + langCd + "_" + tenantId;
    }
}
