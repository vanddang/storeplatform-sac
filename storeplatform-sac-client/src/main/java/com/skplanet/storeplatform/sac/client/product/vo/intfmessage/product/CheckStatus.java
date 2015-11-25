/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product;

import java.io.Serializable;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * Interface Message CheckStatus Value Object.
 * 
 * Updated on : 2013. 12. 17. Updated by : 오승민, Incross.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class CheckStatus extends CommonInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	private String productId;

	/*
	 * 상품 상태 - 상품 선물 발신 여부 조회인 경우 Y : 가능 N : 불가능 - 상품 추천 가능 여부 조회인 경우 Y : 가능 N : 불가능
	 */
	private String status;

	/**
	 * @return String
	 */
	public String getProductId() {
		return this.productId;
	}

	/**
	 * @param productId
	 *            productId
	 */
	public void setProductId(String productId) {
		this.productId = productId;
	}

	/**
	 * @return String
	 */
	public String getStatus() {
		return this.status;
	}

	/**
	 * @param status
	 *            status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

}
