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
 * Notict:
 * 클래스 이름이 바뀌거나 필드에 변경이 있는 경우 캐쉬를 모두 Evict시켜야 한다.
 * </p>
 * Updated on : 2014. 03. 05 Updated by : 정희원, SK 플래닛.
 */
public class AppMeta extends CommonInfo {
    private static final long serialVersionUID = 1L;

    private String prodId;
    private String partProdId;
    private String prodChrg;
    private String prodGrdCd;
    private String drmYn;
    private String regDt;
    private String contentsTypeCd;
    private String aid;
    private String prodVer;
    private String partParentClsfCd;
    private String prodNm;
    private String prodBaseDesc;
    private Integer prodAmt;
    private String imagePath;
    private Long imageSize;
    private Integer paticpersCnt;
    private Integer prchsCnt;
    private Double avgEvluScore;
    private String prodDtlDesc;
    private String prodStatusCd;

    private String subContentsId;
    private String apkPkgNm;
    private String apkVer;
    private String menuId;
    private String menuNm;
    private String menuDesc;
    private String topMenuId;
    private String topMenuNm;
    private String topMenuDesc;

    public String getSubContentsId() {
        return subContentsId;
    }

    public void setSubContentsId(String subContentsId) {
        this.subContentsId = subContentsId;
    }

    public String getApkPkgNm() {
        return apkPkgNm;
    }

    public void setApkPkgNm(String apkPkgNm) {
        this.apkPkgNm = apkPkgNm;
    }

    public String getApkVer() {
        return apkVer;
    }

    public void setApkVer(String apkVer) {
        this.apkVer = apkVer;
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

    public String getTopMenuId() {
        return topMenuId;
    }

    public void setTopMenuId(String topMenuId) {
        this.topMenuId = topMenuId;
    }

    public String getTopMenuNm() {
        return topMenuNm;
    }

    public void setTopMenuNm(String topMenuNm) {
        this.topMenuNm = topMenuNm;
    }

    public String getTopMenuDesc() {
        return topMenuDesc;
    }

    public void setTopMenuDesc(String topMenuDesc) {
        this.topMenuDesc = topMenuDesc;
    }

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public String getPartProdId() {
        return partProdId;
    }

    public void setPartProdId(String partProdId) {
        this.partProdId = partProdId;
    }

    public String getProdChrg() {
        return prodChrg;
    }

    public void setProdChrg(String prodChrg) {
        this.prodChrg = prodChrg;
    }

    public String getProdGrdCd() {
        return prodGrdCd;
    }

    public void setProdGrdCd(String prodGrdCd) {
        this.prodGrdCd = prodGrdCd;
    }

    public String getDrmYn() {
        return drmYn;
    }

    public void setDrmYn(String drmYn) {
        this.drmYn = drmYn;
    }

    public String getRegDt() {
        return regDt;
    }

    public void setRegDt(String regDt) {
        this.regDt = regDt;
    }

    public String getContentsTypeCd() {
        return contentsTypeCd;
    }

    public void setContentsTypeCd(String contentsTypeCd) {
        this.contentsTypeCd = contentsTypeCd;
    }

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getProdVer() {
        return prodVer;
    }

    public void setProdVer(String prodVer) {
        this.prodVer = prodVer;
    }

    public String getPartParentClsfCd() {
        return partParentClsfCd;
    }

    public void setPartParentClsfCd(String partParentClsfCd) {
        this.partParentClsfCd = partParentClsfCd;
    }

    public String getProdNm() {
        return prodNm;
    }

    public void setProdNm(String prodNm) {
        this.prodNm = prodNm;
    }

    public String getProdBaseDesc() {
        return prodBaseDesc;
    }

    public void setProdBaseDesc(String prodBaseDesc) {
        this.prodBaseDesc = prodBaseDesc;
    }

    public Integer getProdAmt() {
        return prodAmt;
    }

    public void setProdAmt(Integer prodAmt) {
        this.prodAmt = prodAmt;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Long getImageSize() {
        return imageSize;
    }

    public void setImageSize(Long imageSize) {
        this.imageSize = imageSize;
    }

    public Integer getPaticpersCnt() {
        return paticpersCnt;
    }

    public void setPaticpersCnt(Integer paticpersCnt) {
        this.paticpersCnt = paticpersCnt;
    }

    public Integer getPrchsCnt() {
        return prchsCnt;
    }

    public void setPrchsCnt(Integer prchsCnt) {
        this.prchsCnt = prchsCnt;
    }

    public Double getAvgEvluScore() {
        return avgEvluScore;
    }

    public void setAvgEvluScore(Double avgEvluScore) {
        this.avgEvluScore = avgEvluScore;
    }

    public String getProdDtlDesc() {
        return prodDtlDesc;
    }

    public void setProdDtlDesc(String prodDtlDesc) {
        this.prodDtlDesc = prodDtlDesc;
    }

    public String getProdStatusCd() {
        return prodStatusCd;
    }

    public void setProdStatusCd(String prodStatusCd) {
        this.prodStatusCd = prodStatusCd;
    }
}
