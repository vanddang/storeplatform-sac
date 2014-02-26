/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.display.vo.appguide;

import java.util.List;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;

/**
 * app guide 조회 result Value Object.
 * 
 * Updated on : 2014. 02. 26. Updated by : 윤주영, SK 플래닛.
 */
public class AppguideSacRes extends CommonInfo {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8199886855614732073L;

	private CommonResponse commonResponse;

	private List<Product> productList;

	public CommonResponse getCommonResponse() {
		return this.commonResponse;
	}

	public void setCommonRes(CommonResponse commonResponse) {
		this.commonResponse = commonResponse;
	}

	public List<Product> getProductList() {
		return this.productList;
	}

	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}
}
