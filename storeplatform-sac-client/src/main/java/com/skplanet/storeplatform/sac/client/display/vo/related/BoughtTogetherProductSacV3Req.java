/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.display.vo.related;

/**
 * [I03000XXX] 2.5.8. 이 상품과 함께 구매한 상품 조회 V3 Request Client VO Created on : 2014. 10. 24. by 백승현, SP Tek
 */
public class BoughtTogetherProductSacV3Req extends BoughtTogetherProductSacReq {

	private static final long serialVersionUID = 1L;

	private String topMenuId;

	private String prodGradeCd; // 상품 이용 등급 코드 "PD004401":전체이용가, "PD004402":12세이용가, "PD004403":15세이용가,
								// "PD004404":청소년이용불가

	private String[] arrayProdGradeCd; // 상품 이용 등급 배열

	public String getTopMenuId() {
		return this.topMenuId;
	}

	public void setTopMenuId(String topMenuId) {
		this.topMenuId = topMenuId;
	}

	public String getProdGradeCd() {
		return this.prodGradeCd;
	}

	public void setProdGradeCd(String prodGradeCd) {
		this.prodGradeCd = prodGradeCd;
	}

	public String[] getArrayProdGradeCd() {
		return this.arrayProdGradeCd == null ? null : this.arrayProdGradeCd.clone();
	}

	public void setArrayProdGradeCd(String[] arrayProdGradeCd) {
		this.arrayProdGradeCd = arrayProdGradeCd == null ? null : arrayProdGradeCd.clone();
	}

}
