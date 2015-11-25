package com.skplanet.storeplatform.sac.display.app.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import org.apache.commons.lang3.StringUtils;

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
    private String aid;
    private String apkPkgNm;
    private String apkVer;
    private String apkVerNm;
    private Long fileSize;
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
    private String drmYn;
    private String descVideoUrl;
    private String partParentClsfCd;
    private String topMenuId;
    private String menuId;
    private String menuNm;
    private String menuDesc;
    private String likeYn;
    private String apkApiLevelCd;
    private Integer sdkMin;
    private Integer sdkMax;
    private String expoSellerWebUrl;
    private String policyInfoUrl;
	private String apkSignedKeyHash;

    public String getPartParentClsfCd() {
        return partParentClsfCd;
    }

    public void setPartParentClsfCd(String partParentClsfCd) {
        this.partParentClsfCd = partParentClsfCd;
    }

    public String getDescVideoUrl() {
        return descVideoUrl;
    }

    public void setDescVideoUrl(String descVideoUrl) {
        this.descVideoUrl = descVideoUrl;
    }

    public String getDrmYn() {
        return drmYn;
    }

    public void setDrmYn(String drmYn) {
        this.drmYn = drmYn;
    }

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
        return StringUtils.defaultString(isDeviceSupp, "N");
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

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
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

    public String getTopMenuId() {
        return topMenuId;
    }

    public void setTopMenuId(String topMenuId) {
        this.topMenuId = topMenuId;
    }

    public String getLikeYn() {
        return likeYn;
    }

    public void setLikeYn(String likeYn) {
        this.likeYn = likeYn;
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

    public String getApkApiLevelCd() {
        return apkApiLevelCd;
    }

    public void setApkApiLevelCd(String apkApiLevelCd) {
        this.apkApiLevelCd = apkApiLevelCd;
    }

    public Integer getSdkMin() {
        return sdkMin != null ? sdkMin : 0;
    }

    public void setSdkMin(Integer sdkMin) {
        this.sdkMin = sdkMin;
    }

    public Integer getSdkMax() {
        return sdkMax != null ? sdkMax : Integer.MAX_VALUE;
    }

    public void setSdkMax(Integer sdkMax) {
        this.sdkMax = sdkMax;
    }

    public String getExpoSellerWebUrl() {
        return expoSellerWebUrl;
    }

    public void setExpoSellerWebUrl(String expoSellerWebUrl) {
        this.expoSellerWebUrl = expoSellerWebUrl;
    }

    public String getPolicyInfoUrl() {
        return policyInfoUrl;
    }

    public void setPolicyInfoUrl(String policyInfoUrl) {
        this.policyInfoUrl = policyInfoUrl;
    }

	public String getApkSignedKeyHash() {
		return apkSignedKeyHash;
	}

	public void setApkSignedKeyHash(String apkSignedKeyHash) {
		this.apkSignedKeyHash = apkSignedKeyHash;
	}
}
