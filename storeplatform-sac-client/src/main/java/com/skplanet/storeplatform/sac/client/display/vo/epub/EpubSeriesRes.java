/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.display.vo.epub;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * 이북/코믹 시리즈 조회 Response Value Object.
 *
 * Updated on : 2014. 01. 07. Updated by : 임근대, SK 플래닛.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class EpubSeriesRes extends CommonInfo {

	private static final long serialVersionUID = -7112036496004829217L;

	private CommonResponse commonResponse;
	private Product product;

	public Product getProduct() {
		return this.product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@Override
	public String toString() {
		return "EpubDetailRes [commonResponse=" + this.commonResponse + ", product="
				+ this.product + "]";
	}

}
