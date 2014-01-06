/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 import java.util.List;

import com.skplanet.storeplatform.framework.core.annotation.ProtobufMapping;
lose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.display.vo.menu;

import java.io.Serializable;
import java.util.List;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;

/**
 * 카테고리 조회 Value Object. 최대 중-세 카테고리 정보 표현 Updated on : 2013. 12. 20. Updated by : 윤주영, SK 플래닛.
 */
public class CategoryDetailListRes extends CommonInfo implements Serializable {

	private static final long serialVersionUID = 11123123111L;

	private CommonResponse commonRes;

	// private Menu category;

	private List<CategoryDetail> categoryList;

	public CommonResponse getCommonRes() {
		return this.commonRes;
	}

	public void setCommonRes(CommonResponse commonRes) {
		this.commonRes = commonRes;
	}

	/*
	 * public Menu getCategory() { return this.category; }
	 * 
	 * public void setCategory(Menu category) { this.category = category; }
	 */
	public List<CategoryDetail> getCategoryList() {
		return this.categoryList;
	}

	public void setCategoryList(List<CategoryDetail> categoryList) {
		this.categoryList = categoryList;
	}

}
