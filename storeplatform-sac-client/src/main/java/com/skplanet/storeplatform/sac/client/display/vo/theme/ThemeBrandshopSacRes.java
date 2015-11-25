/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 import java.util.List;

import com.skplanet.storeplatform.framework.core.annotation.ProtobufMapping;
lose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.display.vo.theme;

import java.util.List;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Layout;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;

/**
 * 테마존 테마 상품 조회 Input Value Object.
 * 
 * Updated on : 2014. 2. 6. Updated by : 이승훈, 엔텔스.
 */
public class ThemeBrandshopSacRes extends CommonInfo {
	private static final long serialVersionUID = 1L;

	private CommonResponse commonResponse;
	private List<Product> productList;
	private Layout layOut;

	public Layout getLayOut() {
		return this.layOut;
	}

	public void setLayOut(Layout layOut) {
		this.layOut = layOut;
	}

	public CommonResponse getCommonResponse() {
		return this.commonResponse;
	}

	public void setCommonResponse(CommonResponse commonResponse) {
		this.commonResponse = commonResponse;
	}

	public List<Product> getProductList() {
		return this.productList;
	}

	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}
}
