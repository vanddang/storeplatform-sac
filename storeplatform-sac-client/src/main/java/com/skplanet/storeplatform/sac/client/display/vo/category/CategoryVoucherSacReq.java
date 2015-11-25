package com.skplanet.storeplatform.sac.client.display.vo.category;
/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * <pre>
 * 특정 상품 이용권 Value Object.
 * </pre>
 * 
 * Created on : 2015-05-19 Created by : 김형식, SK플래닛 
 * Last Updated on : 2015-05-19 Last Updated by : 김형식, SK플래닛
 */

public class CategoryVoucherSacReq extends CommonInfo {

	private static final long serialVersionUID = 1L;
	@NotNull
	@NotBlank
	private String productId; // 상품 ID (카탈로그)
	
	/**
	 * @return the productId
	 */
	public String getProductId() {
		return productId;
	}
	/**
	 * @param productId the productId to set
	 */
	public void setProductId(String productId) {
		this.productId = productId;
	}


}
