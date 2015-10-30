/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */

package com.skplanet.storeplatform.sac.client.internal.display.localsci.vo;


import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * Created by 양해엽 on 2015-01-13.
 */
public class Banner extends CommonInfo {
    private static final long serialVersionUID = 1894576113591712636L;

    private String bnrId;
    private String bnrInfoTypeCd;
    private String imgSizeCd;
    private String title;
    private String imagePath;
    private String linkUrl;
    private String backColorCd;

    public String getBnrId() {
        return bnrId;
    }

    public void setBnrId(String bnrId) {
        this.bnrId = bnrId;
    }

    public String getBnrInfoTypeCd() {
        return bnrInfoTypeCd;
    }

    public void setBnrInfoTypeCd(String bnrInfoTypeCd) {
        this.bnrInfoTypeCd = bnrInfoTypeCd;
    }

    public String getImgSizeCd() {
        return imgSizeCd;
    }

    public void setImgSizeCd(String imgSizeCd) {
        this.imgSizeCd = imgSizeCd;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public String getBackColorCd() {
        return backColorCd;
    }

    public void setBackColorCd(String backColorCd) {
        this.backColorCd = backColorCd;
    }
}
