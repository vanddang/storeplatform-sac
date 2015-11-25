package com.skplanet.storeplatform.sac.client.internal.display.localsci.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 정액제 상품 DRM 메타 정보 조회 VO.
 * 
 * Updated on : 2014. 2. 28. Updated by : 김형식 , 지티소프트
 */
public class FreePassDrmInfo extends CommonInfo {
	private static final long serialVersionUID = 1L;

	private String prodId;
	private String cmpxProdNm;
	private String episodeProdId;
	private String cmpxProdClsfCd;
	private String topMenuId;
	private String prodStatusCd;
	private DwldDrmInfo dwldDrmInfo;
	private StrmDrmInfo strmDrmInfo;

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

	
}
