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
 * WebtoonMeta
 * </p>
 * Updated on : 2014. 04. 22 Updated by : 정희원, SK 플래닛.
 */
public class WebtoonMeta extends CommonInfo {
    private static final long serialVersionUID = 1L;
    private String upMenuId;
    private String topMenuNm;
    private String topMenuId;
    private String menuId;
    private String menuNm;
    private String menuDesc;
    private String metaClsfCd;
    private String prodId;
    private String partProdId;
    private String prodNm;
    private String chapter;
    private String bookClsfCd;
    private String prodBaseDesc;
    private String prodGrdCd;
    private String artist1Nm;
    private String artist2Nm;
    private String artist3Nm;
    private String issueDay;
    private String chnlCompNm;
    private String bookStatus;
    private Integer chnlProdAmt;
    private Integer epsdProdAmt;
    private Integer paticpersCnt;
    private Integer dwldCnt;
    private Double avgEvluScore;
    private String filePath;
    private String iconYn;
    private Date updDt;
    private String prodStatusCd;

    public String getUpMenuId() {
        return upMenuId;
    }

    public void setUpMenuId(String upMenuId) {
        this.upMenuId = upMenuId;
    }

    public String getTopMenuNm() {
        return topMenuNm;
    }

    public void setTopMenuNm(String topMenuNm) {
        this.topMenuNm = topMenuNm;
    }

    public String getTopMenuId() {
        return topMenuId;
    }

    public void setTopMenuId(String topMenuId) {
        this.topMenuId = topMenuId;
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

    public String getMetaClsfCd() {
        return metaClsfCd;
    }

    public void setMetaClsfCd(String metaClsfCd) {
        this.metaClsfCd = metaClsfCd;
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

    public String getProdNm() {
        return prodNm;
    }

    public void setProdNm(String prodNm) {
        this.prodNm = prodNm;
    }

    public String getChapter() {
        return chapter;
    }

    public void setChapter(String chapter) {
        this.chapter = chapter;
    }

    public String getBookClsfCd() {
        return bookClsfCd;
    }

    public void setBookClsfCd(String bookClsfCd) {
        this.bookClsfCd = bookClsfCd;
    }

    public String getProdBaseDesc() {
        return prodBaseDesc;
    }

    public void setProdBaseDesc(String prodBaseDesc) {
        this.prodBaseDesc = prodBaseDesc;
    }

    public String getProdGrdCd() {
        return prodGrdCd;
    }

    public void setProdGrdCd(String prodGrdCd) {
        this.prodGrdCd = prodGrdCd;
    }

    public String getArtist1Nm() {
        return artist1Nm;
    }

    public void setArtist1Nm(String artist1Nm) {
        this.artist1Nm = artist1Nm;
    }

    public String getArtist2Nm() {
        return artist2Nm;
    }

    public void setArtist2Nm(String artist2Nm) {
        this.artist2Nm = artist2Nm;
    }

    public String getArtist3Nm() {
        return artist3Nm;
    }

    public void setArtist3Nm(String artist3Nm) {
        this.artist3Nm = artist3Nm;
    }

    public String getIssueDay() {
        return issueDay;
    }

    public void setIssueDay(String issueDay) {
        this.issueDay = issueDay;
    }

    public String getChnlCompNm() {
        return chnlCompNm;
    }

    public void setChnlCompNm(String chnlCompNm) {
        this.chnlCompNm = chnlCompNm;
    }

    public String getBookStatus() {
        return bookStatus;
    }

    public void setBookStatus(String bookStatus) {
        this.bookStatus = bookStatus;
    }

    public Integer getChnlProdAmt() {
        return chnlProdAmt;
    }

    public void setChnlProdAmt(Integer chnlProdAmt) {
        this.chnlProdAmt = chnlProdAmt;
    }

    public Integer getEpsdProdAmt() {
        return epsdProdAmt;
    }

    public void setEpsdProdAmt(Integer epsdProdAmt) {
        this.epsdProdAmt = epsdProdAmt;
    }

    public Integer getPaticpersCnt() {
        return paticpersCnt;
    }

    public void setPaticpersCnt(Integer paticpersCnt) {
        this.paticpersCnt = paticpersCnt;
    }

    public Integer getDwldCnt() {
        return dwldCnt;
    }

    public void setDwldCnt(Integer dwldCnt) {
        this.dwldCnt = dwldCnt;
    }

    public Double getAvgEvluScore() {
        return avgEvluScore;
    }

    public void setAvgEvluScore(Double avgEvluScore) {
        this.avgEvluScore = avgEvluScore;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getIconYn() {
        return iconYn;
    }

    public void setIconYn(String iconYn) {
        this.iconYn = iconYn;
    }

    public Date getUpdDt() {
        return updDt;
    }

    public void setUpdDt(Date updDt) {
        this.updDt = updDt;
    }

    public String getProdStatusCd() {
        return prodStatusCd;
    }

    public void setProdStatusCd(String prodStatusCd) {
        this.prodStatusCd = prodStatusCd;
    }
}
