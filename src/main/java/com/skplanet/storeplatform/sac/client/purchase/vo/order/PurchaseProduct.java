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
 * 구매요청 상품구매정보 VO
 * 
 * Updated on : 2014. 1. 16. Updated by : 이승택, nTels.
 */
public class PurchaseProduct extends CommonInfo {
	private static final long serialVersionUID = 201401031L;

	private String prodId; // 상품 ID
	private Double prodAmt; // 상품 가격
	private Integer prodQty; // 상품 수량
	private String tenantProdGrpCd; // 테넌트 상품그룹 코드

	/**
	 */
	public PurchaseProduct() {
	}

	/**
	 * @param prodId
	 *            prodId
	 * @param prodAmt
	 *            prodAmt
	 * @param prodQty
	 *            prodQty
	 * @param tenantProdGrpCd
	 *            tenantProdGrpCd
	 */
	public PurchaseProduct(String prodId, Double prodAmt, Integer prodQty, String tenantProdGrpCd) {
		this.prodId = prodId;
		this.prodAmt = prodAmt;
		this.prodQty = prodQty;
		this.tenantProdGrpCd = tenantProdGrpCd;
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
	 * @return the prodAmt
	 */
	public Double getProdAmt() {
		return this.prodAmt;
	}

	/**
	 * @param prodAmt
	 *            the prodAmt to set
	 */
	public void setProdAmt(Double prodAmt) {
		this.prodAmt = prodAmt;
	}

	/**
	 * @return the prodQty
	 */
	public Integer getProdQty() {
		return this.prodQty;
	}

	/**
	 * @param prodQty
	 *            the prodQty to set
	 */
	public void setProdQty(Integer prodQty) {
		this.prodQty = prodQty;
	}

	/**
	 * @return the tenantProdGrpCd
	 */
	public String getTenantProdGrpCd() {
		return this.tenantProdGrpCd;
	}

	/**
	 * @param tenantProdGrpCd
	 *            the tenantProdGrpCd to set
	 */
	public void setTenantProdGrpCd(String tenantProdGrpCd) {
		this.tenantProdGrpCd = tenantProdGrpCd;
	}

}
