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
import java.util.List;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;

/**
 * Interface Message SpecificProductList Value Object.
 * 
 * Updated on : 2013. 12. 17. Updated by : 오승민, Incross.
 */
public class SpecificProductList extends CommonInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	private CommonResponse commonRes;
	private List<Product> productList;

	public CommonResponse getCommonRes() {
		return this.commonRes;
	}

	public void setCommonRes(CommonResponse commonRes) {
		this.commonRes = commonRes;
	}

	public List<Product> getProductList() {
		return this.productList;
	}

	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}
}
