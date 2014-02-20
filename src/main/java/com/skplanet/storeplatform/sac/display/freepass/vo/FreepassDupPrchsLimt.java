/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.freepass.vo;

/**
 * 정액제 중보구매 제한 정보 VO
 * 
 * Updated on : 2014. 2. 14. Updated by : 서영배, GTSOFT.
 */
public class FreepassDupPrchsLimt {

	private static final long serialVersionUID = 1L;
	
	private String dupPrchsLimtProdId;

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
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
