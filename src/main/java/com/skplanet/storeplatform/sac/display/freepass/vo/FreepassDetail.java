/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.freepass.vo;

import java.util.List;

/**
 * Freepass 상세조회 Value Object
 * 
 * Updated on : 2014. 2. 14. Updated by : 서영배, GTSOFT.
 */
public class FreepassDetail {
	
	private static final long serialVersionUID = 1L;
	
	private String bannerFilePath;
	private String thumbnailFilePath;
	private String prodId;
	private String topMenuId;
	private String topMenuNm;
	private String metaClsfCd;
	private String prodChrgYn;
	private String sellerMbrNo;
	private String svcGrpCd;
	private String svcTypeCd;
	private String prodGrdCd;
	private String cid;
	private String mallCd;
	private String feeCaseCd;
	private String feeUnitCd;
	private String usePeriodUnitCd;
	private String usePeriod;
	private String drmYn;
	private String drmSetCd;
	private String drmSetValue;
	private String contentsTypeCd;
	private String subMenuId;
	private String cmpxProdClsfCd;
	private String prodIntrDscr;
	private String prodAmt;
	private String prodNm;
	private String prodAlias;
	private String prodBaseDesc;
	private String prodDtlDesc;
	private String prodUseMtd;
	private String autoApprYn;
	private String maxSaleCnt;
	private String salePocCd;
	private String dupPrchsLimtYn;
	private String prodStatusCd;
	private String expoYn;
	private String expoOrd;
	private String applyStartDt;
	private String applyEndDt;
	private String chnlUnlmtAmt;
	private String chnlPeriodAmt;
	private String prodNetAmt;
	private String dcRate;
	private String dcAmt;
	private String dcAftProdAmt;
	private String taxClsf;
	private String langCd;
	private String tenantId;
	/** 상품매핑 정보 */
	private List<FreepassProdMap> freepassProdMapList;
	/** 중보구매 제한 정보*/
	private List<FreepassDupPrchsLimt> freepassDupPrchsLimtList;
	/**
	 * @return the bannerFilePath
	 */
	public String getBannerFilePath() {
		return bannerFilePath;
	}
	/**
	 * @param bannerFilePath the bannerFilePath to set
	 */
	public void setBannerFilePath(String bannerFilePath) {
		this.bannerFilePath = bannerFilePath;
	}
	/**
	 * @return the thumbnailFilePath
	 */
	public String getThumbnailFilePath() {
		return thumbnailFilePath;
	}
	/**
	 * @param thumbnailFilePath the thumbnailFilePath to set
	 */
	public void setThumbnailFilePath(String thumbnailFilePath) {
		this.thumbnailFilePath = thumbnailFilePath;
	}
	/**
	 * @return the prodId
	 */
	public String getProdId() {
		return prodId;
	}
	/**
	 * @param prodId the prodId to set
	 */
	public void setProdId(String prodId) {
		this.prodId = prodId;
	}
	/**
	 * @return the topMenuId
	 */
	public String getTopMenuId() {
		return topMenuId;
	}
	/**
	 * @param topMenuId the topMenuId to set
	 */
	public void setTopMenuId(String topMenuId) {
		this.topMenuId = topMenuId;
	}
	/**
	 * @return the topMenuNm
	 */
	public String getTopMenuNm() {
		return topMenuNm;
	}
	/**
	 * @param topMenuNm the topMenuNm to set
	 */
	public void setTopMenuNm(String topMenuNm) {
		this.topMenuNm = topMenuNm;
	}
	/**
	 * @return the metaClsfCd
	 */
	public String getMetaClsfCd() {
		return metaClsfCd;
	}
	/**
	 * @param metaClsfCd the metaClsfCd to set
	 */
	public void setMetaClsfCd(String metaClsfCd) {
		this.metaClsfCd = metaClsfCd;
	}
	/**
	 * @return the prodChrgYn
	 */
	public String getProdChrgYn() {
		return prodChrgYn;
	}
	/**
	 * @param prodChrgYn the prodChrgYn to set
	 */
	public void setProdChrgYn(String prodChrgYn) {
		this.prodChrgYn = prodChrgYn;
	}
	/**
	 * @return the sellerMbrNo
	 */
	public String getSellerMbrNo() {
		return sellerMbrNo;
	}
	/**
	 * @param sellerMbrNo the sellerMbrNo to set
	 */
	public void setSellerMbrNo(String sellerMbrNo) {
		this.sellerMbrNo = sellerMbrNo;
	}
	/**
	 * @return the svcGrpCd
	 */
	public String getSvcGrpCd() {
		return svcGrpCd;
	}
	/**
	 * @param svcGrpCd the svcGrpCd to set
	 */
	public void setSvcGrpCd(String svcGrpCd) {
		this.svcGrpCd = svcGrpCd;
	}
	/**
	 * @return the svcTypeCd
	 */
	public String getSvcTypeCd() {
		return svcTypeCd;
	}
	/**
	 * @param svcTypeCd the svcTypeCd to set
	 */
	public void setSvcTypeCd(String svcTypeCd) {
		this.svcTypeCd = svcTypeCd;
	}
	/**
	 * @return the prodGrdCd
	 */
	public String getProdGrdCd() {
		return prodGrdCd;
	}
	/**
	 * @param prodGrdCd the prodGrdCd to set
	 */
	public void setProdGrdCd(String prodGrdCd) {
		this.prodGrdCd = prodGrdCd;
	}
	/**
	 * @return the cid
	 */
	public String getCid() {
		return cid;
	}
	/**
	 * @param cid the cid to set
	 */
	public void setCid(String cid) {
		this.cid = cid;
	}
	/**
	 * @return the mallCd
	 */
	public String getMallCd() {
		return mallCd;
	}
	/**
	 * @param mallCd the mallCd to set
	 */
	public void setMallCd(String mallCd) {
		this.mallCd = mallCd;
	}
	/**
	 * @return the feeCaseCd
	 */
	public String getFeeCaseCd() {
		return feeCaseCd;
	}
	/**
	 * @param feeCaseCd the feeCaseCd to set
	 */
	public void setFeeCaseCd(String feeCaseCd) {
		this.feeCaseCd = feeCaseCd;
	}
	/**
	 * @return the feeUnitCd
	 */
	public String getFeeUnitCd() {
		return feeUnitCd;
	}
	/**
	 * @param feeUnitCd the feeUnitCd to set
	 */
	public void setFeeUnitCd(String feeUnitCd) {
		this.feeUnitCd = feeUnitCd;
	}
	/**
	 * @return the usePeriodUnitCd
	 */
	public String getUsePeriodUnitCd() {
		return usePeriodUnitCd;
	}
	/**
	 * @param usePeriodUnitCd the usePeriodUnitCd to set
	 */
	public void setUsePeriodUnitCd(String usePeriodUnitCd) {
		this.usePeriodUnitCd = usePeriodUnitCd;
	}
	/**
	 * @return the usePeriod
	 */
	public String getUsePeriod() {
		return usePeriod;
	}
	/**
	 * @param usePeriod the usePeriod to set
	 */
	public void setUsePeriod(String usePeriod) {
		this.usePeriod = usePeriod;
	}
	/**
	 * @return the drmYn
	 */
	public String getDrmYn() {
		return drmYn;
	}
	/**
	 * @param drmYn the drmYn to set
	 */
	public void setDrmYn(String drmYn) {
		this.drmYn = drmYn;
	}
	/**
	 * @return the drmSetCd
	 */
	public String getDrmSetCd() {
		return drmSetCd;
	}
	/**
	 * @param drmSetCd the drmSetCd to set
	 */
	public void setDrmSetCd(String drmSetCd) {
		this.drmSetCd = drmSetCd;
	}
	/**
	 * @return the drmSetValue
	 */
	public String getDrmSetValue() {
		return drmSetValue;
	}
	/**
	 * @param drmSetValue the drmSetValue to set
	 */
	public void setDrmSetValue(String drmSetValue) {
		this.drmSetValue = drmSetValue;
	}
	/**
	 * @return the contentsTypeCd
	 */
	public String getContentsTypeCd() {
		return contentsTypeCd;
	}
	/**
	 * @param contentsTypeCd the contentsTypeCd to set
	 */
	public void setContentsTypeCd(String contentsTypeCd) {
		this.contentsTypeCd = contentsTypeCd;
	}
	/**
	 * @return the subMenuId
	 */
	public String getSubMenuId() {
		return subMenuId;
	}
	/**
	 * @param subMenuId the subMenuId to set
	 */
	public void setSubMenuId(String subMenuId) {
		this.subMenuId = subMenuId;
	}
	/**
	 * @return the cmpxProdClsfCd
	 */
	public String getCmpxProdClsfCd() {
		return cmpxProdClsfCd;
	}
	/**
	 * @param cmpxProdClsfCd the cmpxProdClsfCd to set
	 */
	public void setCmpxProdClsfCd(String cmpxProdClsfCd) {
		this.cmpxProdClsfCd = cmpxProdClsfCd;
	}
	/**
	 * @return the prodIntrDscr
	 */
	public String getProdIntrDscr() {
		return prodIntrDscr;
	}
	/**
	 * @param prodIntrDscr the prodIntrDscr to set
	 */
	public void setProdIntrDscr(String prodIntrDscr) {
		this.prodIntrDscr = prodIntrDscr;
	}
	/**
	 * @return the prodAmt
	 */
	public String getProdAmt() {
		return prodAmt;
	}
	/**
	 * @param prodAmt the prodAmt to set
	 */
	public void setProdAmt(String prodAmt) {
		this.prodAmt = prodAmt;
	}
	/**
	 * @return the prodNm
	 */
	public String getProdNm() {
		return prodNm;
	}
	/**
	 * @param prodNm the prodNm to set
	 */
	public void setProdNm(String prodNm) {
		this.prodNm = prodNm;
	}
	/**
	 * @return the prodAlias
	 */
	public String getProdAlias() {
		return prodAlias;
	}
	/**
	 * @param prodAlias the prodAlias to set
	 */
	public void setProdAlias(String prodAlias) {
		this.prodAlias = prodAlias;
	}
	/**
	 * @return the prodBaseDesc
	 */
	public String getProdBaseDesc() {
		return prodBaseDesc;
	}
	/**
	 * @param prodBaseDesc the prodBaseDesc to set
	 */
	public void setProdBaseDesc(String prodBaseDesc) {
		this.prodBaseDesc = prodBaseDesc;
	}
	/**
	 * @return the prodDtlDesc
	 */
	public String getProdDtlDesc() {
		return prodDtlDesc;
	}
	/**
	 * @param prodDtlDesc the prodDtlDesc to set
	 */
	public void setProdDtlDesc(String prodDtlDesc) {
		this.prodDtlDesc = prodDtlDesc;
	}
	/**
	 * @return the prodUseMtd
	 */
	public String getProdUseMtd() {
		return prodUseMtd;
	}
	/**
	 * @param prodUseMtd the prodUseMtd to set
	 */
	public void setProdUseMtd(String prodUseMtd) {
		this.prodUseMtd = prodUseMtd;
	}
	/**
	 * @return the autoApprYn
	 */
	public String getAutoApprYn() {
		return autoApprYn;
	}
	/**
	 * @param autoApprYn the autoApprYn to set
	 */
	public void setAutoApprYn(String autoApprYn) {
		this.autoApprYn = autoApprYn;
	}
	/**
	 * @return the maxSaleCnt
	 */
	public String getMaxSaleCnt() {
		return maxSaleCnt;
	}
	/**
	 * @param maxSaleCnt the maxSaleCnt to set
	 */
	public void setMaxSaleCnt(String maxSaleCnt) {
		this.maxSaleCnt = maxSaleCnt;
	}
	/**
	 * @return the salePocCd
	 */
	public String getSalePocCd() {
		return salePocCd;
	}
	/**
	 * @param salePocCd the salePocCd to set
	 */
	public void setSalePocCd(String salePocCd) {
		this.salePocCd = salePocCd;
	}
	/**
	 * @return the dupPrchsLimtYn
	 */
	public String getDupPrchsLimtYn() {
		return dupPrchsLimtYn;
	}
	/**
	 * @param dupPrchsLimtYn the dupPrchsLimtYn to set
	 */
	public void setDupPrchsLimtYn(String dupPrchsLimtYn) {
		this.dupPrchsLimtYn = dupPrchsLimtYn;
	}
	/**
	 * @return the prodStatusCd
	 */
	public String getProdStatusCd() {
		return prodStatusCd;
	}
	/**
	 * @param prodStatusCd the prodStatusCd to set
	 */
	public void setProdStatusCd(String prodStatusCd) {
		this.prodStatusCd = prodStatusCd;
	}
	/**
	 * @return the expoYn
	 */
	public String getExpoYn() {
		return expoYn;
	}
	/**
	 * @param expoYn the expoYn to set
	 */
	public void setExpoYn(String expoYn) {
		this.expoYn = expoYn;
	}
	/**
	 * @return the expoOrd
	 */
	public String getExpoOrd() {
		return expoOrd;
	}
	/**
	 * @param expoOrd the expoOrd to set
	 */
	public void setExpoOrd(String expoOrd) {
		this.expoOrd = expoOrd;
	}
	/**
	 * @return the applyStartDt
	 */
	public String getApplyStartDt() {
		return applyStartDt;
	}
	/**
	 * @param applyStartDt the applyStartDt to set
	 */
	public void setApplyStartDt(String applyStartDt) {
		this.applyStartDt = applyStartDt;
	}
	/**
	 * @return the applyEndDt
	 */
	public String getApplyEndDt() {
		return applyEndDt;
	}
	/**
	 * @param applyEndDt the applyEndDt to set
	 */
	public void setApplyEndDt(String applyEndDt) {
		this.applyEndDt = applyEndDt;
	}
	/**
	 * @return the chnlUnlmtAmt
	 */
	public String getChnlUnlmtAmt() {
		return chnlUnlmtAmt;
	}
	/**
	 * @param chnlUnlmtAmt the chnlUnlmtAmt to set
	 */
	public void setChnlUnlmtAmt(String chnlUnlmtAmt) {
		this.chnlUnlmtAmt = chnlUnlmtAmt;
	}
	/**
	 * @return the chnlPeriodAmt
	 */
	public String getChnlPeriodAmt() {
		return chnlPeriodAmt;
	}
	/**
	 * @param chnlPeriodAmt the chnlPeriodAmt to set
	 */
	public void setChnlPeriodAmt(String chnlPeriodAmt) {
		this.chnlPeriodAmt = chnlPeriodAmt;
	}
	/**
	 * @return the prodNetAmt
	 */
	public String getProdNetAmt() {
		return prodNetAmt;
	}
	/**
	 * @param prodNetAmt the prodNetAmt to set
	 */
	public void setProdNetAmt(String prodNetAmt) {
		this.prodNetAmt = prodNetAmt;
	}
	/**
	 * @return the dcRate
	 */
	public String getDcRate() {
		return dcRate;
	}
	/**
	 * @param dcRate the dcRate to set
	 */
	public void setDcRate(String dcRate) {
		this.dcRate = dcRate;
	}
	/**
	 * @return the dcAmt
	 */
	public String getDcAmt() {
		return dcAmt;
	}
	/**
	 * @param dcAmt the dcAmt to set
	 */
	public void setDcAmt(String dcAmt) {
		this.dcAmt = dcAmt;
	}
	/**
	 * @return the dcAftProdAmt
	 */
	public String getDcAftProdAmt() {
		return dcAftProdAmt;
	}
	/**
	 * @param dcAftProdAmt the dcAftProdAmt to set
	 */
	public void setDcAftProdAmt(String dcAftProdAmt) {
		this.dcAftProdAmt = dcAftProdAmt;
	}
	/**
	 * @return the taxClsf
	 */
	public String getTaxClsf() {
		return taxClsf;
	}
	/**
	 * @param taxClsf the taxClsf to set
	 */
	public void setTaxClsf(String taxClsf) {
		this.taxClsf = taxClsf;
	}
	/**
	 * @return the langCd
	 */
	public String getLangCd() {
		return langCd;
	}
	/**
	 * @param langCd the langCd to set
	 */
	public void setLangCd(String langCd) {
		this.langCd = langCd;
	}
	/**
	 * @return the tenantId
	 */
	public String getTenantId() {
		return tenantId;
	}
	/**
	 * @param tenantId the tenantId to set
	 */
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	/**
	 * @return the freepassProdMapList
	 */
	public List<FreepassProdMap> getFreepassProdMapList() {
		return freepassProdMapList;
	}
	/**
	 * @param freepassProdMapList the freepassProdMapList to set
	 */
	public void setFreepassProdMapList(List<FreepassProdMap> freepassProdMapList) {
		this.freepassProdMapList = freepassProdMapList;
	}
	/**
	 * @return the freepassDupPrchsLimtList
	 */
	public List<FreepassDupPrchsLimt> getFreepassDupPrchsLimtList() {
		return freepassDupPrchsLimtList;
	}
	/**
	 * @param freepassDupPrchsLimtList the freepassDupPrchsLimtList to set
	 */
	public void setFreepassDupPrchsLimtList(
			List<FreepassDupPrchsLimt> freepassDupPrchsLimtList) {
		this.freepassDupPrchsLimtList = freepassDupPrchsLimtList;
	}
	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
