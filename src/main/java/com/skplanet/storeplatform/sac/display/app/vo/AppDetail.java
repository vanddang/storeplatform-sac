package com.skplanet.storeplatform.sac.display.app.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

import java.util.Date;

/**
 * AppDetail
 * Updated on : 2014. 01. 23 Updated by : 정희원, SK 플래닛.
 */
public class AppDetail extends CommonInfo {
    private static final long serialVersionUID = 1L;

    private String prodId;
    private String subContentsId;
    private String prodNm;
    private Integer fixedAmt;
    private Integer prodAmt;
    private Double avgEvluScore;
    private Integer dwldCnt;
    private Integer paticpersCnt;
    private String aid;
    private String apkPkgNm;
    private String apkVer;
    private String apkVerNm;
    private Integer fileSize;
    private String vmVer;
    private String sellerMbrNo;
    private String prodGbn;
    private String prodGrdCd;
    private String expoSellerEmail;
    private String expoSellerNm;
    private String expoSellerTelno;
    private String prodStatusCd;
    private String prodBaseDesc;
    private String prodDtlDesc;
    private String isDeviceSupp;
    private Date saleStrtDt;
    private String svcGrpCd;

    public String getSvcGrpCd() {
        return svcGrpCd;
    }

    public void setSvcGrpCd(String svcGrpCd) {
        this.svcGrpCd = svcGrpCd;
    }

    public Date getSaleStrtDt() {
        return saleStrtDt;
    }

    public void setSaleStrtDt(Date saleStrtDt) {
        this.saleStrtDt = saleStrtDt;
    }

    public String getIsDeviceSupp() {
        return isDeviceSupp;
    }

    public void setIsDeviceSupp(String isDeviceSupp) {
        this.isDeviceSupp = isDeviceSupp;
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

    public String getProdStatusCd() {
        return prodStatusCd;
    }

    public void setProdStatusCd(String prodStatusCd) {
        this.prodStatusCd = prodStatusCd;
    }

    public Integer getFileSize() {
        return fileSize;
    }

    public void setFileSize(Integer fileSize) {
        this.fileSize = fileSize;
    }

    public String getExpoSellerEmail() {
        return expoSellerEmail;
    }

    public void setExpoSellerEmail(String expoSellerEmail) {
        this.expoSellerEmail = expoSellerEmail;
    }

    public String getExpoSellerNm() {
        return expoSellerNm;
    }

    public void setExpoSellerNm(String expoSellerNm) {
        this.expoSellerNm = expoSellerNm;
    }

    public String getExpoSellerTelno() {
        return expoSellerTelno;
    }

    public void setExpoSellerTelno(String expoSellerTelno) {
        this.expoSellerTelno = expoSellerTelno;
    }

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public String getSubContentsId() {
        return subContentsId;
    }

    public void setSubContentsId(String subContentsId) {
        this.subContentsId = subContentsId;
    }

    public String getProdNm() {
        return prodNm;
    }

    public void setProdNm(String prodNm) {
        this.prodNm = prodNm;
    }

    public Integer getProdAmt() {
        return prodAmt;
    }

    public void setProdAmt(Integer prodAmt) {
        this.prodAmt = prodAmt;
    }

    public Double getAvgEvluScore() {
        return avgEvluScore;
    }

    public void setAvgEvluScore(Double avgEvluScore) {
        this.avgEvluScore = avgEvluScore;
    }

    public Integer getDwldCnt() {
        return dwldCnt;
    }

    public void setDwldCnt(Integer dwldCnt) {
        this.dwldCnt = dwldCnt;
    }

    public Integer getPaticpersCnt() {
        return paticpersCnt;
    }

    public void setPaticpersCnt(Integer paticpersCnt) {
        this.paticpersCnt = paticpersCnt;
    }

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
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

    public String getApkVerNm() {
        return apkVerNm;
    }

    public void setApkVerNm(String apkVerNm) {
        this.apkVerNm = apkVerNm;
    }

    public String getVmVer() {
        return vmVer;
    }

    public void setVmVer(String vmVer) {
        this.vmVer = vmVer;
    }

    public String getSellerMbrNo() {
        return sellerMbrNo;
    }

    public void setSellerMbrNo(String sellerMbrNo) {
        this.sellerMbrNo = sellerMbrNo;
    }

    public String getProdGbn() {
        return prodGbn;
    }

    public void setProdGbn(String prodGbn) {
        this.prodGbn = prodGbn;
    }

    public String getProdGrdCd() {
        return prodGrdCd;
    }

    public void setProdGrdCd(String prodGrdCd) {
        this.prodGrdCd = prodGrdCd;
    }

    public Integer getFixedAmt() {
        return fixedAmt;
    }

    public void setFixedAmt(Integer fixedAmt) {
        this.fixedAmt = fixedAmt;
    }
}
