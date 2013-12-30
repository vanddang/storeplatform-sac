/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.product.vo.feature;

import java.util.List;

import com.skplanet.storeplatform.framework.core.annotation.ProtobufMapping;
import com.skplanet.storeplatform.sac.client.product.vo.FeaturedProductProto;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;

/**
 * Feature 상품  리스트 조회 List Value Object.
 * 
 * Updated on : 2013. 12. 20. Updated by : 서영배, GTSOFT.
 */
@ProtobufMapping(FeaturedProductProto.FeaturedProductList.class)
public class RecommendResponseVO {
	private CommonResponse commonResponse;
	private List<RecommendProductVO> featureProductList;

	public List<RecommendProductVO> getFeatureProductList() {
		return this.featureProductList;
	}

	public void setFeatureProductList(List<RecommendProductVO> featureProductList) {
		this.featureProductList = featureProductList;
	}

	public CommonResponse getCommonResponse() {
		return commonResponse;
	}

	public void setCommonResponse(CommonResponse commonResponse) {
		this.commonResponse = commonResponse;
	}
}
