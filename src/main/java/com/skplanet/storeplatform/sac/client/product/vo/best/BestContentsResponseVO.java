/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 import java.util.List;

import com.skplanet.storeplatform.framework.core.annotation.ProtobufMapping;
lose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.product.vo.best;

import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.skplanet.storeplatform.framework.core.annotation.ProtobufMapping;
import com.skplanet.storeplatform.sac.client.product.vo.BestContentsProto;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;

/**
 * 일반/특정 상품 카테고리 리스트 조회 List Value Object.
 * 
 * Updated on : 2013. 11. 28. Updated by : 이태희, SK 플래닛.
 */
@ProtobufMapping(BestContentsProto.resBestContents.class)
public class BestContentsResponseVO {
	private CommonResponse commonResponse;
	private List<BestContentsVO> bestContentsList;

	public CommonResponse getCommonResponse() {
		return this.commonResponse;
	}

	public void setCommonResponse(CommonResponse commonResponse) {
		this.commonResponse = commonResponse;
	}

	public List<BestContentsVO> getBestContentsList() {
		return this.bestContentsList;
	}

	public void setBestContentsList(List<BestContentsVO> BestContentsList) {
		this.bestContentsList = BestContentsList;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
