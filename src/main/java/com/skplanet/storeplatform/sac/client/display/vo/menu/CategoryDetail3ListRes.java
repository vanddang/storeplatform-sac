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

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Menu;

/**
 * 카테고리 조회 Value Object. 최대 중-세 카테고리 정보 표현 Updated on : 2013. 12. 20. Updated by : 윤주영, SK 플래닛.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class CategoryDetail3ListRes extends CommonInfo implements CategoryDetailRes {

	private static final long serialVersionUID = 111231231122L;

	private CommonResponse commonResponse;

	private Menu category;

	private List<CategoryDetail> categoryList;

	@Override
	public CommonResponse getCommonResponse() {
		return this.commonResponse;
	}

	@Override
	public void setCommonResponse(CommonResponse commonResponse) {
		this.commonResponse = commonResponse;
	}

	@Override
	public Menu getCategory() {
		return this.category;
	}

	@Override
	public void setCategory(Menu category) {
		this.category = category;
	}

	@Override
	public List<CategoryDetail> getCategoryList() {
		return this.categoryList;
	}

	@Override
	public void setCategoryList(List categoryList) {
		this.categoryList = categoryList;
	}

}
