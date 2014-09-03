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

import java.util.Date;

/**
 * <p>
 * DESC
 * </p>
 * Updated on : 2014. 07. 03 Updated by : 정희원, SK 플래닛.
 */
public class UpdateProduct extends CommonInfo{
    private static final long serialVersionUid = 1L;

    private String aid;
    private Integer verMajor;
    private Integer verMinor;
    private String topMenuId;
    private String upMenuId;
    private String menuId;
    private String menuNm;
    private String menuDesc;
    private String partProdId;
    private String prodId;
    private Date lastDeployDt;
    private String prodGrdCd;
    private String prodNm;
    private String apkPkgNm;
    private Integer apkVer;
    private Long fileSize;
    private String filePath;
    private String imagePath;
    private Integer imageSize;
    private Integer prodAmt;
    private String langCd;
    private String topMenuNm;
    private String prodVer;
    private String fakeYn;

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public Integer getVerMajor() {
        return verMajor;
    }

    public void setVerMajor(Integer verMajor) {
        this.verMajor = verMajor;
    }

    public Integer getVerMinor() {
        return verMinor;
    }

    public void setVerMinor(Integer verMinor) {
        this.verMinor = verMinor;
    }

    public String getTopMenuId() {
        return topMenuId;
    }

    public void setTopMenuId(String topMenuId) {
        this.topMenuId = topMenuId;
    }

    public String getUpMenuId() {
        return upMenuId;
    }

    public void setUpMenuId(String upMenuId) {
        this.upMenuId = upMenuId;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getMenuNm() {
        return menuNm;
    }

    public void setMenuNm(String menuNm) {
        this.menuNm = menuNm;
    }

    public String getMenuDesc() {
        return menuDesc;
    }

    public void setMenuDesc(String menuDesc) {
        this.menuDesc = menuDesc;
    }

    public String getPartProdId() {
        return partProdId;
    }

    public void setPartProdId(String partProdId) {
        this.partProdId = partProdId;
    }

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public Date getLastDeployDt() {
        return lastDeployDt;
    }

    public void setLastDeployDt(Date lastDeployDt) {
        this.lastDeployDt = lastDeployDt;
    }

    public String getProdGrdCd() {
        return prodGrdCd;
    }

    public void setProdGrdCd(String prodGrdCd) {
        this.prodGrdCd = prodGrdCd;
    }

    public String getProdNm() {
        return prodNm;
    }

    public void setProdNm(String prodNm) {
        this.prodNm = prodNm;
    }

    public String getApkPkgNm() {
        return apkPkgNm;
    }

    public void setApkPkgNm(String apkPkgNm) {
        this.apkPkgNm = apkPkgNm;
    }

    public Integer getApkVer() {
        return apkVer;
    }

    public void setApkVer(Integer apkVer) {
        this.apkVer = apkVer;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public Integer getImageSize() {
        return imageSize;
    }

    public void setImageSize(Integer imageSize) {
        this.imageSize = imageSize;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }


    public Integer getProdAmt() {
        return prodAmt;
    }

    public void setProdAmt(Integer prodAmt) {
        this.prodAmt = prodAmt;
    }

    public String getLangCd() {
        return langCd;
    }

    public void setLangCd(String langCd) {
        this.langCd = langCd;
    }

    public String getTopMenuNm() {
        return topMenuNm;
    }

    public void setTopMenuNm(String topMenuNm) {
        this.topMenuNm = topMenuNm;
    }

    public String getProdVer() {
        return prodVer;
    }

    public void setProdVer(String prodVer) {
        this.prodVer = prodVer;
    }

    public String getFakeYn() {
        return fakeYn;
    }

    public void setFakeYn(String fakeYn) {
        this.fakeYn = fakeYn;
    }
}
