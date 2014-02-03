/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.display.vo.feature.appCodi;

import java.util.List;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;

/**
 * App codi list 조회 result Value Object.
 * 
 * Updated on : 2014. 01. 28. Updated by : 윤주영, SK 플래닛.
 */
public class AppCodiListRes extends CommonInfo {

	private static final long serialVersionUID = 319359921029557339L;

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
