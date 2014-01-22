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

/**
 * 카테고리 리스트 조회 List Value Object.
 * 
 * Updated on : 2013. 12. 20. Updated by : 윤주영, SK 플래닛.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class CategoryListRes extends CommonInfo {

	private static final long serialVersionUID = 11123123128L;

	private CommonResponse commonResponse;

	private List<Object> categoryList;

	public CommonResponse getCommonResponse() {
		return this.commonResponse;
	}

	public void setCommonRes(CommonResponse commonResponse) {
		this.commonResponse = commonResponse;
	}

	public List<Object> getCategoryList() {
		return this.categoryList;
	}

	public void setCategoryList(List<Object> categoryList) {
		this.categoryList = categoryList;
	}
}
