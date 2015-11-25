package com.skplanet.storeplatform.sac.client.internal.display.localsci.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 정액제 상품 DRM 메타 정보 조회 VO.
 * 
 * Updated on : 2014. 2. 28. Updated by : 김형식 , 지티소프트
 */
public class FreePassInfo extends CommonInfo {
	private static final long serialVersionUID = 1L;

	private String prodId;
	private String cmpxProdNm;
	private String episodeProdId;
	private String drmYn;
	private String usePeriodUnitCd;
	private String usePeriod;
	private String cmpxProdClsfCd;
	private String topMenuId;
	private String prodStatusCd;


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
}
