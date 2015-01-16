/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */

package com.skplanet.storeplatform.sac.display.localsci.sci.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * Created by 1002184 on 2015-01-16.
 */
public class BannerInfo extends CommonInfo {

    private static final long serialVersionUID = 8281783504088927158L;

    private String bnrId;
    private String bnrInfoTypeCd;
    private String bnrInfo;
    private String bnrNm;
    private String bgColorCd;
    private String imgSizeCd;
    private String imgPath;

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

    public String getBnrInfo() {
        return bnrInfo;
    }

    public void setBnrInfo(String bnrInfo) {
        this.bnrInfo = bnrInfo;
    }

    public String getBnrNm() {
        return bnrNm;
    }

    public void setBnrNm(String bnrNm) {
        this.bnrNm = bnrNm;
    }

    public String getBgColorCd() {
        return bgColorCd;
    }

    public void setBgColorCd(String bgColorCd) {
        this.bgColorCd = bgColorCd;
    }

    public String getImgSizeCd() {
        return imgSizeCd;
    }

    public void setImgSizeCd(String imgSizeCd) {
        this.imgSizeCd = imgSizeCd;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }
}
