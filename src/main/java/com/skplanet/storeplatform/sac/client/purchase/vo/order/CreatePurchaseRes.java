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
 * 구매요청 응답 VO
 * 
 * Updated on : 2014. 1. 16. Updated by : 이승택, nTels.
 */
public class CreatePurchaseRes extends CommonInfo {
	private static final long serialVersionUID = 201401031L;

	private String resultType; // 결과 타입: payment-결제Page 요청 진행, free-무료구매 완료
	private String paymentPageUrl; // 결제Page URL
	private String paymentPageParam; // 결제Page 요청 파라미터

	/**
	 * @return the resultType
	 */
	public String getResultType() {
		return this.resultType;
	}

	/**
	 * @param resultType
	 *            the resultType to set
	 */
	public void setResultType(String resultType) {
		this.resultType = resultType;
	}

	/**
	 * @return the paymentPageUrl
	 */
	public String getPaymentPageUrl() {
		return this.paymentPageUrl;
	}

	/**
	 * @param paymentPageUrl
	 *            the paymentPageUrl to set
	 */
	public void setPaymentPageUrl(String paymentPageUrl) {
		this.paymentPageUrl = paymentPageUrl;
	}

	/**
	 * @return the paymentPageParam
	 */
	public String getPaymentPageParam() {
		return this.paymentPageParam;
	}

	/**
	 * @param paymentPageParam
	 *            the paymentPageParam to set
	 */
	public void setPaymentPageParam(String paymentPageParam) {
		this.paymentPageParam = paymentPageParam;
	}

}
