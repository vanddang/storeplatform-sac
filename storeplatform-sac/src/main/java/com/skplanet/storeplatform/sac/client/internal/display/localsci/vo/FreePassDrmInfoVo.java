package com.skplanet.storeplatform.sac.client.internal.display.localsci.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 정액제 상품 DRM 메타 정보 조회 VO.
 * 
 * Updated on : 2014. 2. 28. Updated by : 김형식 , 지티소프트
 */
public class FreePassDrmInfoVo extends CommonInfo {
	private static final long serialVersionUID = 1L;

	private String prodId;
	private String cmpxProdNm;
	private String episodeProdId;
	private String cmpxProdClsfCd;
	private String topMenuId;
	private String prodStatusCd;
	private DwldDrmInfo dwldDrmInfo;
	private StrmDrmInfo strmDrmInfo;
	private String dwldDrmYn;
	private String dwldDrmUsePeriodUnitCd;
	private String dwldDrmUsePeriod;
	private String strmDrmYn;
	private String strmDrmUsePeriodUnitCd;
	private String strmDrmUsePeriod;	

	/**
	 * @return the prodId
	 */
	public String getProdId() {
		return this.prodId;
	}

	/**
	 * @param prodId
	 *            the prodId to set
	 */
	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

	/**
	 * @return the cmpxProdNm
	 */
	public String getCmpxProdNm() {
		return cmpxProdNm;
	}

	/**
	 * @param cmpxProdNm the cmpxProdNm to set
	 */
	public void setCmpxProdNm(String cmpxProdNm) {
		this.cmpxProdNm = cmpxProdNm;
	}

	/**
	 * @return the episodeProdId
	 */
	public String getEpisodeProdId() {
		return this.episodeProdId;
	}

	/**
	 * @param episodeProdId
	 *            the episodeProdId to set
	 */
	public void setEpisodeProdId(String episodeProdId) {
		this.episodeProdId = episodeProdId;
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
	 * @return the topMenuId
	 */
	public String getTopMenuId() {
		return this.topMenuId;
	}

	/**
	 * @param topMenuId
	 *            the topMenuId to set
	 */
	public void setTopMenuId(String topMenuId) {
		this.topMenuId = topMenuId;
	}

	/**
	 * @return the prodStatusCd
	 */
	public String getProdStatusCd() {
		return this.prodStatusCd;
	}

	/**
	 * @param prodStatusCd
	 *            the prodStatusCd to set
	 */
	public void setProdStatusCd(String prodStatusCd) {
		this.prodStatusCd = prodStatusCd;
	}

	/**
	 * @return the dwldDrmInfo
	 */
	public DwldDrmInfo getDwldDrmInfo() {
		return dwldDrmInfo;
	}

	/**
	 * @param dwldDrmInfo the dwldDrmInfo to set
	 */
	public void setDwldDrmInfo(DwldDrmInfo dwldDrmInfo) {
		this.dwldDrmInfo = dwldDrmInfo;
	}

	/**
	 * @return the strmDrmInfo
	 */
	public StrmDrmInfo getStrmDrmInfo() {
		return strmDrmInfo;
	}

	/**
	 * @param strmDrmInfo the strmDrmInfo to set
	 */
	public void setStrmDrmInfo(StrmDrmInfo strmDrmInfo) {
		this.strmDrmInfo = strmDrmInfo;
	}

	/**
	 * @return the dwldDrmYn
	 */
	public String getDwldDrmYn() {
		return dwldDrmYn;
	}

	/**
	 * @param dwldDrmYn the dwldDrmYn to set
	 */
	public void setDwldDrmYn(String dwldDrmYn) {
		this.dwldDrmYn = dwldDrmYn;
	}

	/**
	 * @return the dwldDrmUsePeriodUnitCd
	 */
	public String getDwldDrmUsePeriodUnitCd() {
		return dwldDrmUsePeriodUnitCd;
	}

	/**
	 * @param dwldDrmUsePeriodUnitCd the dwldDrmUsePeriodUnitCd to set
	 */
	public void setDwldDrmUsePeriodUnitCd(String dwldDrmUsePeriodUnitCd) {
		this.dwldDrmUsePeriodUnitCd = dwldDrmUsePeriodUnitCd;
	}

	/**
	 * @return the dwldDrmUsePeriod
	 */
	public String getDwldDrmUsePeriod() {
		return dwldDrmUsePeriod;
	}

	/**
	 * @param dwldDrmUsePeriod the dwldDrmUsePeriod to set
	 */
	public void setDwldDrmUsePeriod(String dwldDrmUsePeriod) {
		this.dwldDrmUsePeriod = dwldDrmUsePeriod;
	}

	/**
	 * @return the strmDrmYn
	 */
	public String getStrmDrmYn() {
		return strmDrmYn;
	}

	/**
	 * @param strmDrmYn the strmDrmYn to set
	 */
	public void setStrmDrmYn(String strmDrmYn) {
		this.strmDrmYn = strmDrmYn;
	}

	/**
	 * @return the strmDrmUsePeriodUnitCd
	 */
	public String getStrmDrmUsePeriodUnitCd() {
		return strmDrmUsePeriodUnitCd;
	}

	/**
	 * @param strmDrmUsePeriodUnitCd the strmDrmUsePeriodUnitCd to set
	 */
	public void setStrmDrmUsePeriodUnitCd(String strmDrmUsePeriodUnitCd) {
		this.strmDrmUsePeriodUnitCd = strmDrmUsePeriodUnitCd;
	}

	/**
	 * @return the strmDrmUsePeriod
	 */
	public String getStrmDrmUsePeriod() {
		return strmDrmUsePeriod;
	}

	/**
	 * @param strmDrmUsePeriod the strmDrmUsePeriod to set
	 */
	public void setStrmDrmUsePeriod(String strmDrmUsePeriod) {
		this.strmDrmUsePeriod = strmDrmUsePeriod;
	}

}
