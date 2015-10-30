package com.skplanet.storeplatform.sac.client.internal.display.localsci.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 정액제 상품 DRM 메타 정보 조회 VO.
 * 
 * Updated on : 2015. 1. 12. Updated by : 김형식 , 지티소프트
 */
public class DwldDrmInfo extends CommonInfo {
	private static final long serialVersionUID = 1L;
	
	private String dwldDrmYn;
	private String dwldDrmUsePeriodUnitCd;
	private String dwldDrmUsePeriod;
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
	
	
	
}