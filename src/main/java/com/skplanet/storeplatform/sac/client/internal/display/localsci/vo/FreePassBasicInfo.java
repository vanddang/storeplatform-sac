package com.skplanet.storeplatform.sac.client.internal.display.localsci.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * FreePassBasicInfo.
 * 
 * Updated on : 2014. 6. 09. Updated by : 김형식, 지티소프트
 */
public class FreePassBasicInfo extends CommonInfo {
	private static final long serialVersionUID = 1L;

	private String prodId;
	private String prodNm;
	private String planType;

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
	 * @return the prodNm
	 */
	public String getProdNm() {
		return this.prodNm;
	}

	/**
	 * @param prodNm
	 *            the prodNm to set
	 */
	public void setProdNm(String prodNm) {
		this.prodNm = prodNm;
	}

	/**
	 * @return the planType
	 */
	public String getPlanType() {
		return this.planType;
	}

	/**
	 * @param planType
	 *            the planType to set
	 */
	public void setPlanType(String planType) {
		this.planType = planType;
	}

}
