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
 * SupportDevice
 * </p>
 * Updated on : 2015. 11. 05 Updated by : 정희원, SK 플래닛.
 */
public class SupportDevice extends CommonInfo {

    private static final long serialVersionUID = 1L;

    private String scId;
    private String osVer;
    private String ver;
    private Integer verCd;

    public String getScId() {
        return scId;
    }

    public void setScId(String scId) {
        this.scId = scId;
    }

    public String getOsVer() {
        return osVer;
    }

    public void setOsVer(String osVer) {
        this.osVer = osVer;
    }

    public String getVer() {
        return ver;
    }

    public void setVer(String ver) {
        this.ver = ver;
    }

    public Integer getVerCd() {
        return verCd;
    }

    public void setVerCd(Integer verCd) {
        this.verCd = verCd;
    }
}
