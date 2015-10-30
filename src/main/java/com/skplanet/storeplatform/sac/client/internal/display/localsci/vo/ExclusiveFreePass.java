package com.skplanet.storeplatform.sac.client.internal.display.localsci.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 정액제 제외 상품 정보 조회 VO.
 * 
 * Updated on : 2015. 05. 11. Updated by : 김형식 , 지티소프트
 */
public class ExclusiveFreePass extends CommonInfo {
	private static final long serialVersionUID = 1L;
	
	private String dupPrchsLimtProdId;
	private String dupPrchsLimtTypeCd;
	/**
	 * @return the dupPrchsLimtProdId
	 */
	public String getDupPrchsLimtProdId() {
		return dupPrchsLimtProdId;
	}
	/**
	 * @param dupPrchsLimtProdId the dupPrchsLimtProdId to set
	 */
	public void setDupPrchsLimtProdId(String dupPrchsLimtProdId) {
		this.dupPrchsLimtProdId = dupPrchsLimtProdId;
	}
	/**
	 * @return the dupPrchsLimtTypeCd
	 */
	public String getDupPrchsLimtTypeCd() {
		return dupPrchsLimtTypeCd;
	}
	/**
	 * @param dupPrchsLimtTypeCd the dupPrchsLimtTypeCd to set
	 */
	public void setDupPrchsLimtTypeCd(String dupPrchsLimtTypeCd) {
		this.dupPrchsLimtTypeCd = dupPrchsLimtTypeCd;
	}
	
	
	
}