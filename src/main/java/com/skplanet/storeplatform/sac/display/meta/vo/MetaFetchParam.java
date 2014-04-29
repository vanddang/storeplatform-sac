/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.meta.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * <p>
 * MetaFetchParam
 * </p>
 * Updated on : 2014. 04. 08 Updated by : 정희원, SK 플래닛.
 */
public class MetaFetchParam extends CommonInfo {
    private static final long serialVersionUID = 1L;

    private String tenantId;
    private String langCd;
    private String deviceModelCd;

    private String chartClsfCd; // [Music]
    private String stdDt;       // [Music]

    public String getChartClsfCd() {
        return chartClsfCd;
    }

    public void setChartClsfCd(String chartClsfCd) {
        this.chartClsfCd = chartClsfCd;
    }

    public String getStdDt() {
        return stdDt;
    }

    public void setStdDt(String stdDt) {
        this.stdDt = stdDt;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getLangCd() {
        return langCd;
    }

    public void setLangCd(String langCd) {
        this.langCd = langCd;
    }

    public String getDeviceModelCd() {
        return deviceModelCd;
    }

    public void setDeviceModelCd(String deviceModelCd) {
        this.deviceModelCd = deviceModelCd;
    }
}
