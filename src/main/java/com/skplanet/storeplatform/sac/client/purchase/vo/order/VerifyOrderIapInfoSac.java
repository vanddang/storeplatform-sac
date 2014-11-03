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
 * 구매인증 시, IAP상품 정보
 * 
 * Updated on : 2014. 11. 3. Updated by : 이승택, nTels.
 */
public class VerifyOrderIapInfoSac extends CommonInfo {
	private static final long serialVersionUID = 1L;

	private String postbackUrl; // 모상품 결제결과 전송 URL
	private String prodKind; // 부분유료화 상품 유형
	private String prodCase; // 부분유료화 상품 종류
	private Integer usePeriod; // 이용기간 값

	/**
	 * @return the postbackUrl
	 */
	public String getPostbackUrl() {
		return this.postbackUrl;
	}

	/**
	 * @param postbackUrl
	 *            the postbackUrl to set
	 */
	public void setPostbackUrl(String postbackUrl) {
		this.postbackUrl = postbackUrl;
	}

	/**
	 * @return the prodKind
	 */
	public String getProdKind() {
		return this.prodKind;
	}

	/**
	 * @param prodKind
	 *            the prodKind to set
	 */
	public void setProdKind(String prodKind) {
		this.prodKind = prodKind;
	}

	/**
	 * @return the prodCase
	 */
	public String getProdCase() {
		return this.prodCase;
	}

	/**
	 * @param prodCase
	 *            the prodCase to set
	 */
	public void setProdCase(String prodCase) {
		this.prodCase = prodCase;
	}

	/**
	 * @return the usePeriod
	 */
	public Integer getUsePeriod() {
		return this.usePeriod;
	}

	/**
	 * @param usePeriod
	 *            the usePeriod to set
	 */
	public void setUsePeriod(Integer usePeriod) {
		this.usePeriod = usePeriod;
	}

}
