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

import com.skplanet.storeplatform.framework.core.annotation.ProtobufMapping;
import com.skplanet.storeplatform.sac.client.product.vo.BestAppProto;

/**
 * 일반/특정 상품 카테고리 리스트 조회 List Value Object.
 * 
 * Updated on : 2013. 11. 28. Updated by : 이태희, SK 플래닛.
 */
@ProtobufMapping(BestAppProto.resBestApp.class)
public class BestAppResponseVO {
	private List<BestAppVO> bestAppList;

	public List<BestAppVO> getBestAppList() {
		return this.bestAppList;
	}

	public void setBestAppList(List<BestAppVO> bestAppList) {
		this.bestAppList = bestAppList;
	}
}
