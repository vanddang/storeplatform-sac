package com.skplanet.storeplatform.sac.client.internal.display.localsci.vo;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 이용권에 등록된 상품 정보 조회 CmpxInfoSacReq VO.
 * 
 * Updated on : 2015. 5. 07. Updated by : 김형식 , 지티소프트
 */
public class CmpxProductSacReq extends CommonInfo {

	private static final long serialVersionUID = 1L;
	@NotNull
	private String tenantId;
	@NotNull
	private String cmpxProdClsfCd;
	@NotNull
	private String prodId;

	private String chapter;

	private String seriesBookClsfCd;

	@NotNull
	private String possLendClsfCd;
	@NotNull
	private String deviceModelCd;
	@NotNull
	private String langCd;
	
	private List<String> episodeProdStatusCdList;

	/**
	 * @return the tenantId
	 */
	public String getTenantId() {
		return this.tenantId;
	}

	/**
	 * @param tenantId
	 *            the tenantId to set
	 */
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
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
	 * @return the chapter
	 */
	public String getChapter() {
		return this.chapter;
	}

	/**
	 * @param chapter
	 *            the chapter to set
	 */
	public void setChapter(String chapter) {
		this.chapter = chapter;
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
	 * @return the deviceModelCd
	 */
	public String getDeviceModelCd() {
		return this.deviceModelCd;
	}

	/**
	 * @param deviceModelCd
	 *            the deviceModelCd to set
	 */
	public void setDeviceModelCd(String deviceModelCd) {
		this.deviceModelCd = deviceModelCd;
	}

	/**
	 * @return the langCd
	 */
	public String getLangCd() {
		return this.langCd;
	}

	/**
	 * @param langCd
	 *            the langCd to set
	 */
	public void setLangCd(String langCd) {
		this.langCd = langCd;
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

	/**
	 * @return the episodeProdStatusCdList
	 */
	public List<String> getEpisodeProdStatusCdList() {
		return episodeProdStatusCdList;
	}

	/**
	 * @param episodeProdStatusCdList the episodeProdStatusCdList to set
	 */
	public void setEpisodeProdStatusCdList(List<String> episodeProdStatusCdList) {
		this.episodeProdStatusCdList = episodeProdStatusCdList;
	}

}
