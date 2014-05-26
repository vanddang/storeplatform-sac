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
 * EbookComicMeta
 * </p>
 * Updated on : 2014. 04. 17 Updated by : 정희원, SK 플래닛.
 */
public class EbookComicMeta extends CommonInfo {
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
    private String prodDtlDesc;
    private String prodGrdCd;
    private String artist1Nm;
    private String artist2Nm;
    private String artist3Nm;
    private String issueDay;
    private String chnlCompNm;
    private String bookType;
    private Integer bookCount;
    private String supportStore;
    private String supportPlay;
    private String comptYn;
    private String bookStatus;
    private Integer chnlProdAmt;
    private Integer epsdProdAmt;
    private Integer chnlProdNetAmt;
    private Integer epsdProdNetAmt;
    private Integer paticpersCnt;
    private Integer dwldCnt;
    private Integer prchsCnt;
    private Double avgEvluScore;
    private String prodChrg;
    private String filePath;
    private String prodStatusCd;
    private Integer epsdUnlmtAmt;
    private Integer epsdPeriodAmt;
    private Integer chnlUnlmtAmt;
    private Integer chnlPeriodAmt;

    public Integer getChnlUnlmtAmt() {
        return chnlUnlmtAmt;
    }

    public void setChnlUnlmtAmt(Integer chnlUnlmtAmt) {
        this.chnlUnlmtAmt = chnlUnlmtAmt;
    }

    public Integer getChnlPeriodAmt() {
        return chnlPeriodAmt;
    }

    public void setChnlPeriodAmt(Integer chnlPeriodAmt) {
        this.chnlPeriodAmt = chnlPeriodAmt;
    }

    public Integer getEpsdUnlmtAmt() {
        return epsdUnlmtAmt;
    }

    public void setEpsdUnlmtAmt(Integer epsdUnlmtAmt) {
        this.epsdUnlmtAmt = epsdUnlmtAmt;
    }

    public Integer getEpsdPeriodAmt() {
        return epsdPeriodAmt;
    }

    public void setEpsdPeriodAmt(Integer epsdPeriodAmt) {
        this.epsdPeriodAmt = epsdPeriodAmt;
    }

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

    public String getProdDtlDesc() {
        return prodDtlDesc;
    }

    public void setProdDtlDesc(String prodDtlDesc) {
        this.prodDtlDesc = prodDtlDesc;
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

    public String getBookType() {
        return bookType;
    }

    public void setBookType(String bookType) {
        this.bookType = bookType;
    }

    public Integer getBookCount() {
        return bookCount;
    }

    public void setBookCount(Integer bookCount) {
        this.bookCount = bookCount;
    }

    public String getSupportStore() {
        return supportStore;
    }

    public void setSupportStore(String supportStore) {
        this.supportStore = supportStore;
    }

    public String getSupportPlay() {
        return supportPlay;
    }

    public void setSupportPlay(String supportPlay) {
        this.supportPlay = supportPlay;
    }

    public String getComptYn() {
        return comptYn;
    }

    public void setComptYn(String comptYn) {
        this.comptYn = comptYn;
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

    public Integer getChnlProdNetAmt() {
        return chnlProdNetAmt;
    }

    public void setChnlProdNetAmt(Integer chnlProdNetAmt) {
        this.chnlProdNetAmt = chnlProdNetAmt;
    }

    public Integer getEpsdProdNetAmt() {
        return epsdProdNetAmt;
    }

    public void setEpsdProdNetAmt(Integer epsdProdNetAmt) {
        this.epsdProdNetAmt = epsdProdNetAmt;
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

    public String getProdChrg() {
        return prodChrg;
    }

    public void setProdChrg(String prodChrg) {
        this.prodChrg = prodChrg;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getProdStatusCd() {
        return prodStatusCd;
    }

    public void setProdStatusCd(String prodStatusCd) {
        this.prodStatusCd = prodStatusCd;
    }
}
