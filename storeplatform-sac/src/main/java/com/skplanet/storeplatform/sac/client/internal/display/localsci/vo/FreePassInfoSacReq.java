package com.skplanet.storeplatform.sac.client.internal.display.localsci.vo;

import javax.validation.constraints.NotNull;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 정액제 상품 DRM 메타 FreepassDrmInfoSacReq VO.
 * 
 * Updated on : 2014. 2. 28. Updated by : 김형식 , 지티소프트
 */
public class FreePassInfoSacReq extends CommonInfo {

	private static final long serialVersionUID = 1L;
	@NotNull
	private String tenantId;
	@NotNull
	private String langCd;
	@NotNull
	private String prodId;
	@NotNull
	private String episodeProdId;

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

}
