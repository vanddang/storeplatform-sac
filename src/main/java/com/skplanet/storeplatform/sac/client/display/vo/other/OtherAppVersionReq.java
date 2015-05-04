/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.display.vo.other;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * <p>
 * OtherAppVersionReq
 * </p>
 * Updated on : 2015. 04. 29 Updated by : 정희원, SK 플래닛.
 */
public class OtherAppVersionReq extends CommonInfo {

    private static final long serialVersionUID = 1L;

    @NotEmpty
    private String apkPkgNm;

    @NotEmpty
    private String deviceModelCd;

    @NotEmpty
    private String osVersion;

    public String getApkPkgNm() {
        return apkPkgNm;
    }

    public void setApkPkgNm(String apkPkgNm) {
        this.apkPkgNm = apkPkgNm;
    }

    public String getDeviceModelCd() {
        return deviceModelCd;
    }

    public void setDeviceModelCd(String deviceModelCd) {
        this.deviceModelCd = deviceModelCd;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }
}
