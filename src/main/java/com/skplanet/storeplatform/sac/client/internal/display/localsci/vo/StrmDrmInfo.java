package com.skplanet.storeplatform.sac.client.internal.display.localsci.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 정액제 상품 DRM 메타 정보 조회 VO.
 * 
 * Updated on : 2015. 1. 12. Updated by : 김형식 , 지티소프트
 */
public class StrmDrmInfo extends CommonInfo {
	private static final long serialVersionUID = 1L;

	private String strmDrmYn;
	private String strmDrmUsePeriodUnitCd;
	private String strmDrmUsePeriod;
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