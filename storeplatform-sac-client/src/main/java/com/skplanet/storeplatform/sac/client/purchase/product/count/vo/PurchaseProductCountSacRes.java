/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.purchase.product.count.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 구매 상품 건수 업데이트 실행 요청 VO.
 * 
 * Updated on : 2014. 3. 27. Updated by : nTels_cswoo81, nTels.
 */
public class PurchaseProductCountSacRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private String updId;

	/**
	 * @return the updId
	 */
	public String getUpdId() {
		return this.updId;
	}

	/**
	 * @param updId
	 *            the updId to set
	 */
	public void setUpdId(String updId) {
		this.updId = updId;
	}

}
