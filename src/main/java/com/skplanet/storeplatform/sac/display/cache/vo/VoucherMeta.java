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
 * DESC
 * </p>
 * Updated on : 2015. 05. 08 Updated by : 이태균, IS-PLUS
 */
public class VoucherMeta extends CommonInfo {
	private static final long serialVersionUID = 1L;

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
	private String cmpxProdClsfCd;
	private String prodIntrDscr;
	private Integer prodAmt;
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
	private Integer prodNetAmt;
	private Double dcRate;
	private String dcAmt;
	private String dcAftProdAmt;
	private String taxClsf;
	private String langCd;
	private Integer cashAmt;
	private String bnsCashAmtClsfCd;
	private Integer bnsCashAmt;
	private Double bnsCashRatio;
	private String bnsUsePeriodUnitCd;
	private String bnsUsePeriod;
	private String bannerFilePath;
	private String thumbnailFilePath;

	private String cmpxProdGrpCd;
	private String possLendClsfCd;
	private String seriesBookClsfCd;

	public String getProdId() {
		return this.prodId;
	}

	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

	public String getTopMenuId() {
		return this.topMenuId;
	}

	public void setTopMenuId(String topMenuId) {
		this.topMenuId = topMenuId;
	}

	public String getTopMenuNm() {
		return this.topMenuNm;
	}

	public void setTopMenuNm(String topMenuNm) {
		this.topMenuNm = topMenuNm;
	}

	public String getMetaClsfCd() {
		return this.metaClsfCd;
	}

	public void setMetaClsfCd(String metaClsfCd) {
		this.metaClsfCd = metaClsfCd;
	}

	public String getProdChrgYn() {
		return this.prodChrgYn;
	}

	public void setProdChrgYn(String prodChrgYn) {
		this.prodChrgYn = prodChrgYn;
	}

	public String getSellerMbrNo() {
		return this.sellerMbrNo;
	}

	public void setSellerMbrNo(String sellerMbrNo) {
		this.sellerMbrNo = sellerMbrNo;
	}

	public String getSvcGrpCd() {
		return this.svcGrpCd;
	}

	public void setSvcGrpCd(String svcGrpCd) {
		this.svcGrpCd = svcGrpCd;
	}

	public String getSvcTypeCd() {
		return this.svcTypeCd;
	}

	public void setSvcTypeCd(String svcTypeCd) {
		this.svcTypeCd = svcTypeCd;
	}

	public String getProdGrdCd() {
		return this.prodGrdCd;
	}

	public void setProdGrdCd(String prodGrdCd) {
		this.prodGrdCd = prodGrdCd;
	}

	public String getCid() {
		return this.cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getMallCd() {
		return this.mallCd;
	}

	public void setMallCd(String mallCd) {
		this.mallCd = mallCd;
	}

	public String getFeeCaseCd() {
		return this.feeCaseCd;
	}

	public void setFeeCaseCd(String feeCaseCd) {
		this.feeCaseCd = feeCaseCd;
	}

	public String getFeeUnitCd() {
		return this.feeUnitCd;
	}

	public void setFeeUnitCd(String feeUnitCd) {
		this.feeUnitCd = feeUnitCd;
	}

	public String getUsePeriodUnitCd() {
		return this.usePeriodUnitCd;
	}

	public void setUsePeriodUnitCd(String usePeriodUnitCd) {
		this.usePeriodUnitCd = usePeriodUnitCd;
	}

	public String getUsePeriod() {
		return this.usePeriod;
	}

	public void setUsePeriod(String usePeriod) {
		this.usePeriod = usePeriod;
	}

	public String getDrmYn() {
		return this.drmYn;
	}

	public void setDrmYn(String drmYn) {
		this.drmYn = drmYn;
	}

	public String getDrmSetCd() {
		return this.drmSetCd;
	}

	public void setDrmSetCd(String drmSetCd) {
		this.drmSetCd = drmSetCd;
	}

	public String getDrmSetValue() {
		return this.drmSetValue;
	}

	public void setDrmSetValue(String drmSetValue) {
		this.drmSetValue = drmSetValue;
	}

	public String getContentsTypeCd() {
		return this.contentsTypeCd;
	}

	public void setContentsTypeCd(String contentsTypeCd) {
		this.contentsTypeCd = contentsTypeCd;
	}

	public String getCmpxProdClsfCd() {
		return this.cmpxProdClsfCd;
	}

	public void setCmpxProdClsfCd(String cmpxProdClsfCd) {
		this.cmpxProdClsfCd = cmpxProdClsfCd;
	}

	public String getProdIntrDscr() {
		return this.prodIntrDscr;
	}

	public void setProdIntrDscr(String prodIntrDscr) {
		this.prodIntrDscr = prodIntrDscr;
	}

	public Integer getProdAmt() {
		return this.prodAmt;
	}

	public void setProdAmt(Integer prodAmt) {
		this.prodAmt = prodAmt;
	}

	public String getProdNm() {
		return this.prodNm;
	}

	public void setProdNm(String prodNm) {
		this.prodNm = prodNm;
	}

	public String getProdAlias() {
		return this.prodAlias;
	}

	public void setProdAlias(String prodAlias) {
		this.prodAlias = prodAlias;
	}

	public String getProdBaseDesc() {
		return this.prodBaseDesc;
	}

	public void setProdBaseDesc(String prodBaseDesc) {
		this.prodBaseDesc = prodBaseDesc;
	}

	public String getProdDtlDesc() {
		return this.prodDtlDesc;
	}

	public void setProdDtlDesc(String prodDtlDesc) {
		this.prodDtlDesc = prodDtlDesc;
	}

	public String getProdUseMtd() {
		return this.prodUseMtd;
	}

	public void setProdUseMtd(String prodUseMtd) {
		this.prodUseMtd = prodUseMtd;
	}

	public String getAutoApprYn() {
		return this.autoApprYn;
	}

	public void setAutoApprYn(String autoApprYn) {
		this.autoApprYn = autoApprYn;
	}

	public String getMaxSaleCnt() {
		return this.maxSaleCnt;
	}

	public void setMaxSaleCnt(String maxSaleCnt) {
		this.maxSaleCnt = maxSaleCnt;
	}

	public String getSalePocCd() {
		return this.salePocCd;
	}

	public void setSalePocCd(String salePocCd) {
		this.salePocCd = salePocCd;
	}

	public String getDupPrchsLimtYn() {
		return this.dupPrchsLimtYn;
	}

	public void setDupPrchsLimtYn(String dupPrchsLimtYn) {
		this.dupPrchsLimtYn = dupPrchsLimtYn;
	}

	public String getProdStatusCd() {
		return this.prodStatusCd;
	}

	public void setProdStatusCd(String prodStatusCd) {
		this.prodStatusCd = prodStatusCd;
	}

	public String getExpoYn() {
		return this.expoYn;
	}

	public void setExpoYn(String expoYn) {
		this.expoYn = expoYn;
	}

	public String getExpoOrd() {
		return this.expoOrd;
	}

	public void setExpoOrd(String expoOrd) {
		this.expoOrd = expoOrd;
	}

	public String getApplyStartDt() {
		return this.applyStartDt;
	}

	public void setApplyStartDt(String applyStartDt) {
		this.applyStartDt = applyStartDt;
	}

	public String getApplyEndDt() {
		return this.applyEndDt;
	}

	public void setApplyEndDt(String applyEndDt) {
		this.applyEndDt = applyEndDt;
	}

	public String getChnlUnlmtAmt() {
		return this.chnlUnlmtAmt;
	}

	public void setChnlUnlmtAmt(String chnlUnlmtAmt) {
		this.chnlUnlmtAmt = chnlUnlmtAmt;
	}

	public String getChnlPeriodAmt() {
		return this.chnlPeriodAmt;
	}

	public void setChnlPeriodAmt(String chnlPeriodAmt) {
		this.chnlPeriodAmt = chnlPeriodAmt;
	}

	public Integer getProdNetAmt() {
		return this.prodNetAmt;
	}

	public void setProdNetAmt(Integer prodNetAmt) {
		this.prodNetAmt = prodNetAmt;
	}

	public Double getDcRate() {
		return this.dcRate;
	}

	public void setDcRate(Double dcRate) {
		this.dcRate = dcRate;
	}

	public String getDcAmt() {
		return this.dcAmt;
	}

	public void setDcAmt(String dcAmt) {
		this.dcAmt = dcAmt;
	}

	public String getDcAftProdAmt() {
		return this.dcAftProdAmt;
	}

	public void setDcAftProdAmt(String dcAftProdAmt) {
		this.dcAftProdAmt = dcAftProdAmt;
	}

	public String getTaxClsf() {
		return this.taxClsf;
	}

	public void setTaxClsf(String taxClsf) {
		this.taxClsf = taxClsf;
	}

	public String getLangCd() {
		return this.langCd;
	}

	public void setLangCd(String langCd) {
		this.langCd = langCd;
	}

	public Integer getCashAmt() {
		return this.cashAmt;
	}

	public void setCashAmt(Integer cashAmt) {
		this.cashAmt = cashAmt;
	}

	public String getBnsCashAmtClsfCd() {
		return this.bnsCashAmtClsfCd;
	}

	public void setBnsCashAmtClsfCd(String bnsCashAmtClsfCd) {
		this.bnsCashAmtClsfCd = bnsCashAmtClsfCd;
	}

	public Integer getBnsCashAmt() {
		return this.bnsCashAmt;
	}

	public void setBnsCashAmt(Integer bnsCashAmt) {
		this.bnsCashAmt = bnsCashAmt;
	}

	public Double getBnsCashRatio() {
		return this.bnsCashRatio;
	}

	public void setBnsCashRatio(Double bnsCashRatio) {
		this.bnsCashRatio = bnsCashRatio;
	}

	public String getBnsUsePeriodUnitCd() {
		return this.bnsUsePeriodUnitCd;
	}

	public void setBnsUsePeriodUnitCd(String bnsUsePeriodUnitCd) {
		this.bnsUsePeriodUnitCd = bnsUsePeriodUnitCd;
	}

	public String getBnsUsePeriod() {
		return this.bnsUsePeriod;
	}

	public void setBnsUsePeriod(String bnsUsePeriod) {
		this.bnsUsePeriod = bnsUsePeriod;
	}

	public String getBannerFilePath() {
		return this.bannerFilePath;
	}

	public void setBannerFilePath(String bannerFilePath) {
		this.bannerFilePath = bannerFilePath;
	}

	public String getThumbnailFilePath() {
		return this.thumbnailFilePath;
	}

	public void setThumbnailFilePath(String thumbnailFilePath) {
		this.thumbnailFilePath = thumbnailFilePath;
	}

	public String getPossLendClsfCd() {
		return this.possLendClsfCd;
	}

	public void setPossLendClsfCd(String possLendClsfCd) {
		this.possLendClsfCd = possLendClsfCd;
	}

	public String getSeriesBookClsfCd() {
		return this.seriesBookClsfCd;
	}

	public void setSeriesBookClsfCd(String seriesBookClsfCd) {
		this.seriesBookClsfCd = seriesBookClsfCd;
	}

	public String getCmpxProdGrpCd() {
		return this.cmpxProdGrpCd;
	}

	public void setCmpxProdGrpCd(String cmpxProdGrpCd) {
		this.cmpxProdGrpCd = cmpxProdGrpCd;
	}

}
