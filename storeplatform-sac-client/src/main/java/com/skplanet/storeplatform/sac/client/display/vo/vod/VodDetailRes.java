/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.display.vo.vod;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;

/**
 * Interface Message Vod Detail Value Object.
 *
 * Updated on : 2014. 01. 09 Updated by : 임근대, SK 플래닛.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class VodDetailRes extends CommonInfo {

	private static final long serialVersionUID = 3342908170045743735L;

	private CommonResponse commonResponse;

	private Product product;

	public CommonResponse getCommonResponse() {
		return this.commonResponse;
	}

	public void setCommonResponse(CommonResponse commonResponse) {
		this.commonResponse = commonResponse;
	}

	public Product getProduct() {
		return this.product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@Override
	public String toString() {
		return "VodDetailRes [commonResponse=" + this.commonResponse + ", product="
				+ this.product + "]";
	}

}
