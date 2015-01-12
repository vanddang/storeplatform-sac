/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.personal.vo;

import com.skplanet.storeplatform.framework.core.util.NumberUtils;
import com.skplanet.storeplatform.framework.core.util.StringUtils;

/**
 * <p>
 * UpdatePkgDetail
 * </p>
 * Updated on : 2014. 07. 07 Updated by : 정희원, SK 플래닛.
 */
public class UpdatePkgDetail {

    private String pkgNm;
    private Integer ver;

    public UpdatePkgDetail(String pkgInfo) {
        String[] arrInfo = StringUtils.split(pkgInfo, "/");
        if(arrInfo.length >= 1)
            this.pkgNm = arrInfo[0];
        if(arrInfo.length >= 2) {
            this.ver = NumberUtils.toInt(arrInfo[1]);
        }

    }

    public UpdatePkgDetail(String pkgNm, Integer ver) {
        this.pkgNm = pkgNm;
        this.ver = ver;
    }

    public String getPkgNm() {
        return pkgNm;
    }

    public void setPkgNm(String pkgNm) {
        this.pkgNm = pkgNm;
    }

    public Integer getVer() {
        return ver;
    }

    public void setVer(Integer ver) {
        this.ver = ver;
    }
}
