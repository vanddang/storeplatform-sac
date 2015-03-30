/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.download.vo;

import com.skplanet.storeplatform.sac.client.display.vo.download.DownloadAppSacRes;

/**
 * <p>
 * SearchDownloadAppResult
 * </p>
 * Updated on : 2015. 03. 30 Updated by : 정희원, SK 플래닛.
 */
public class SearchDownloadAppResult {

    private DownloadAppSacRes downloadAppSacRes;
    private String aid;
    private String prodId;
    private boolean hasDl;

    public SearchDownloadAppResult(DownloadAppSacRes downloadAppSacRes, String aid, String prodId, boolean hasDl) {
        this.downloadAppSacRes = downloadAppSacRes;
        this.aid = aid;
        this.prodId = prodId;
        this.hasDl = hasDl;
    }

    public DownloadAppSacRes getDownloadAppSacRes() {
        return downloadAppSacRes;
    }

    public void setDownloadAppSacRes(DownloadAppSacRes downloadAppSacRes) {
        this.downloadAppSacRes = downloadAppSacRes;
    }

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public boolean isHasDl() {
        return hasDl;
    }

    public void setHasDl(boolean hasDl) {
        this.hasDl = hasDl;
    }
}
