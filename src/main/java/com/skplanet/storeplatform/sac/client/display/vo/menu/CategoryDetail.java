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
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Menu;

/**
 * 카테고리 조회 Value Object. 최대 중-세 카테고리 정보 표현 Updated on : 2013. 12. 20. Updated by : 윤주영, SK 플래닛.
 */
public class CategoryDetail extends CommonInfo implements Serializable {

	private static final long serialVersionUID = 11123123127L;

	private Menu category;

	private List<Menu> subCategoryList;

	public Menu getCategory() {
		return this.category;
	}

	public void setCategory(Menu category) {
		this.category = category;
	}

	public List<Menu> getSubCategoryList() {
		return this.subCategoryList;
	}

	public void setSubCategoryList(List<Menu> subCategoryList) {
		this.subCategoryList = subCategoryList;
	}

}
