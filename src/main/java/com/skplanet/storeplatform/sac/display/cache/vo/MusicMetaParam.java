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
 * MusicMetaParam
 * </p>
 * Updated on : 2014. 04. 09 Updated by : 정희원, SK 플래닛.
 */
public class MusicMetaParam extends CommonInfo {
    private static final long serialVersionUID = 1L;

    private String langCd;
    private String channelId;
    private String tenantId;

    private String chartClsfCd;
    private String rankStartDay;

    public MusicMetaParam() {}

    public MusicMetaParam(String channelId, String langCd, String tenantId) {
        this.channelId = channelId;
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

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getCacheKey() {
        return channelId + "_" + langCd + "_" + tenantId;
    }
}
