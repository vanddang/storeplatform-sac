package com.skplanet.storeplatform.sac.client.internal.display.localsci.vo;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 이용권 및 에피소드 상품 정보 조회  CmpxProductInfoSacReq VO.
 * 
 * Updated on : 2015. 5. 07. Updated by : 김형식 , 지티소프트
 */
public class CmpxProductInfoSacReq extends CommonInfo {

	private static final long serialVersionUID = 1L;
	@NotNull
	private String tenantId;
	@NotNull
	private String langCd;
	@NotNull
	private String prodId;
	@NotNull
	private String episodeProdId;
	
	private String chapter;

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
	 * @return the chapter
	 */
	public String getChapter() {
		return chapter;
	}

	/**
	 * @param chapter the chapter to set
	 */
	public void setChapter(String chapter) {
		this.chapter = chapter;
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
