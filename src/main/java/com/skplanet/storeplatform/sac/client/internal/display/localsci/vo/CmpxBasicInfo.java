package com.skplanet.storeplatform.sac.client.internal.display.localsci.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * CmpxBasicInfo.
 * 
 * Updated on : 2015. 5. 07. Updated by : 김형식, 지티소프트
 */
public class CmpxBasicInfo extends CommonInfo {
	private static final long serialVersionUID = 1L;

	private String cmpxProdId;
	private String cmpxProdNm;
	private String prodGrdCd;
	private String usePeriodUnitCd;
	private String usePeriod;
	private String cmpxProdClsfCd;
	private String possLendClsfCd;
	private String seriesBookClsfCd;

	private String drmYn;

	/**
	 * @return the cmpxProdId
	 */
	public String getCmpxProdId() {
		return this.cmpxProdId;
	}

	/**
	 * @param cmpxProdId
	 *            the cmpxProdId to set
	 */
	public void setCmpxProdId(String cmpxProdId) {
		this.cmpxProdId = cmpxProdId;
	}

	/**
	 * @return the cmpxProdNm
	 */
	public String getCmpxProdNm() {
		return this.cmpxProdNm;
	}

	/**
	 * @param cmpxProdNm
	 *            the cmpxProdNm to set
	 */
	public void setCmpxProdNm(String cmpxProdNm) {
		this.cmpxProdNm = cmpxProdNm;
	}

	/**
	 * @return the prodGrdCd
	 */
	public String getProdGrdCd() {
		return this.prodGrdCd;
	}

	/**
	 * @param prodGrdCd
	 *            the prodGrdCd to set
	 */
	public void setProdGrdCd(String prodGrdCd) {
		this.prodGrdCd = prodGrdCd;
	}

	/**
	 * @return the usePeriodUnitCd
	 */
	public String getUsePeriodUnitCd() {
		return this.usePeriodUnitCd;
	}

	/**
	 * @param usePeriodUnitCd
	 *            the usePeriodUnitCd to set
	 */
	public void setUsePeriodUnitCd(String usePeriodUnitCd) {
		this.usePeriodUnitCd = usePeriodUnitCd;
	}

	/**
	 * @return the usePeriod
	 */
	public String getUsePeriod() {
		return this.usePeriod;
	}

	/**
	 * @param usePeriod
	 *            the usePeriod to set
	 */
	public void setUsePeriod(String usePeriod) {
		this.usePeriod = usePeriod;
	}

	/**
	 * @return the cmpxProdClsfCd
	 */
	public String getCmpxProdClsfCd() {
		return this.cmpxProdClsfCd;
	}

	/**
	 * @param cmpxProdClsfCd
	 *            the cmpxProdClsfCd to set
	 */
	public void setCmpxProdClsfCd(String cmpxProdClsfCd) {
		this.cmpxProdClsfCd = cmpxProdClsfCd;
	}

	/**
	 * @return the possLendClsfCd
	 */
	public String getPossLendClsfCd() {
		return this.possLendClsfCd;
	}

	/**
	 * @param possLendClsfCd
	 *            the possLendClsfCd to set
	 */
	public void setPossLendClsfCd(String possLendClsfCd) {
		this.possLendClsfCd = possLendClsfCd;
	}

	/**
	 * @return the drmYn
	 */
	public String getDrmYn() {
		return this.drmYn;
	}

	/**
	 * @param drmYn
	 *            the drmYn to set
	 */
	public void setDrmYn(String drmYn) {
		this.drmYn = drmYn;
	}

	/**
	 * @return the seriesBookClsfCd
	 */
	public String getSeriesBookClsfCd() {
		return this.seriesBookClsfCd;
	}

	/**
	 * @param seriesBookClsfCd
	 *            the seriesBookClsfCd to set
	 */
	public void setSeriesBookClsfCd(String seriesBookClsfCd) {
		this.seriesBookClsfCd = seriesBookClsfCd;
	}

}
