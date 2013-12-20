/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 import java.util.List;

import com.skplanet.storeplatform.framework.core.annotation.ProtobufMapping;
lose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.product.vo.menu;

import java.io.Serializable;

import com.skplanet.storeplatform.framework.core.common.vo.CommonVO;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;

/**
 * 카테고리 조회 Value Object.
 * 
 * Updated on : 2013. 12. 20. Updated by : 윤주영, SK 플래닛.
 */
public class CategoryDetailResponseVO extends CommonVO implements Serializable {

	private static final long serialVersionUID = 11123123127L;

	private CommonResponse commonRes;

	private CategoryVO category;

	public CommonResponse getCommonRes() {
		return this.commonRes;
	}

	public void setCommonRes(CommonResponse commonRes) {
		this.commonRes = commonRes;
	}

	public CategoryVO getCategory() {
		return this.category;
	}

	public void setCategory(CategoryVO category) {
		this.category = category;
	}
}
