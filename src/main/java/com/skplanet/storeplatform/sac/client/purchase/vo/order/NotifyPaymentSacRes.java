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
 * 결제 결과 Noti 응답 VO
 * 
 * Updated on : 2014. 2. 28. Updated by : 이승택, nTels.
 */
public class NotifyPaymentSacRes extends CommonInfo {
	private static final long serialVersionUID = 201402281L;

	private String prchsId; // 구매 ID
	private int count; // 처리된 결제수단 갯수

	/**
	 */
	public NotifyPaymentSacRes() {
	}

	/**
	 * @param prchsId
	 *            구매 ID
	 * @param count
	 *            처리된 결제수단 갯수
	 */
	public NotifyPaymentSacRes(String prchsId, int count) {
		this.prchsId = prchsId;
		this.count = count;
	}

	/**
	 * @return the prchsId
	 */
	public String getPrchsId() {
		return this.prchsId;
	}

	/**
	 * @param prchsId
	 *            the prchsId to set
	 */
	public void setPrchsId(String prchsId) {
		this.prchsId = prchsId;
	}

	/**
	 * @return the count
	 */
	public int getCount() {
		return this.count;
	}

	/**
	 * @param count
	 *            the count to set
	 */
	public void setCount(int count) {
		this.count = count;
	}

}
