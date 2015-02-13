/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.purchase.vo.order;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * 구매내역 이관 응답 VO
 * 
 * Updated on : 2015. 2. 5. Updated by : 양주원, nTels.
 */
public class PurchaseTransferSac extends CommonInfo {
	private static final long serialVersionUID = 1L;

	private String prchsDt;
	private String marketPrchsId;
	private String marketProdId;

	/**
	 * @return the prchsDt
	 */
	public String getPrchsDt() {
		return this.prchsDt;
	}

	/**
	 * @param prchsDt
	 *            the prchsDt to set
	 */
	public void setPrchsDt(String prchsDt) {
		this.prchsDt = prchsDt;
	}

	/**
	 * @return the marketPrchsId
	 */
	public String getMarketPrchsId() {
		return this.marketPrchsId;
	}

	/**
	 * @param marketPrchsId
	 *            the marketPrchsId to set
	 */
	public void setMarketPrchsId(String marketPrchsId) {
		this.marketPrchsId = marketPrchsId;
	}

	/**
	 * @return the marketProdId
	 */
	public String getMarketProdId() {
		return this.marketProdId;
	}

	/**
	 * @param marketProdId
	 *            the marketProdId to set
	 */
	public void setMarketProdId(String marketProdId) {
		this.marketProdId = marketProdId;
	}

}
