/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */

package com.skplanet.storeplatform.sac.client.purchase.migration.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

import java.io.Serializable;

/**
 * 구매내역 이관 목록 VO
 * 
 * Updated on : 2016. 01. 14. Updated by : 황민규, SK플래닛.
 */
public class PurchaseMigList extends CommonInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String marketPrchsId; // 이관 대상 구매 ID
	private String prchsDt; // 구매 일시
	private String marketProdId; // 이관대상 상품 ID
	private String migStatusCd; // 이관 처리 상태

	/**
	 * Gets prchs dt.
	 *
	 * @return the prchs dt
	 */
	public String getPrchsDt() {
		return prchsDt;
	}

	/**
	 * Sets prchs dt.
	 *
	 * @param prchsDt
	 *            the prchs dt
	 */
	public void setPrchsDt(String prchsDt) {
		this.prchsDt = prchsDt;
	}

	/**
	 * Gets market prchs id.
	 *
	 * @return the market prchs id
	 */
	public String getMarketPrchsId() {
		return marketPrchsId;
	}

	/**
	 * Sets market prchs id.
	 *
	 * @param marketPrchsId
	 *            the market prchs id
	 */
	public void setMarketPrchsId(String marketPrchsId) {
		this.marketPrchsId = marketPrchsId;
	}

	/**
	 * Gets market prod id.
	 *
	 * @return the market prod id
	 */
	public String getMarketProdId() {
		return marketProdId;
	}

	/**
	 * Sets market prod id.
	 *
	 * @param marketProdId
	 *            the market prod id
	 */
	public void setMarketProdId(String marketProdId) {
		this.marketProdId = marketProdId;
	}

	/**
	 * Gets mig status cd.
	 *
	 * @return the mig status cd
	 */
	public String getMigStatusCd() {
		return migStatusCd;
	}

	/**
	 * Sets mig status cd.
	 *
	 * @param migStatusCd
	 *            the mig status cd
	 */
	public void setMigStatusCd(String migStatusCd) {
		this.migStatusCd = migStatusCd;
	}
}
