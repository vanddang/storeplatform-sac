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
	private String installer;
	private String apkSignedKeyHash;
	private Integer parsedCnt;

    public UpdatePkgDetail(String pkgInfo) {
        String[] arrInfo = StringUtils.split(pkgInfo, "/");
		this.parsedCnt = arrInfo.length;

        if(this.parsedCnt >= 1)
            this.pkgNm = arrInfo[0];
        if(this.parsedCnt >= 2)
            this.ver = NumberUtils.toInt(arrInfo[1]);
		if(this.parsedCnt >= 3)
			this.installer = arrInfo[2];
		if(this.parsedCnt >= 4)
			this.apkSignedKeyHash = arrInfo[3];

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

	public String getInstaller() {
		return installer;
	}

	public void setInstaller(String installer) {
		this.installer = installer;
	}

	public String getApkSignedKeyHash() {
		return apkSignedKeyHash;
	}

	public void setApkSignedKeyHash(String apkSignedKeyHash) {
		this.apkSignedKeyHash = apkSignedKeyHash;
	}

	public Integer getParsedCnt() {
		return parsedCnt;
	}

	public void setParsedCnt(Integer parsedCnt) {
		this.parsedCnt = parsedCnt;
	}
}
