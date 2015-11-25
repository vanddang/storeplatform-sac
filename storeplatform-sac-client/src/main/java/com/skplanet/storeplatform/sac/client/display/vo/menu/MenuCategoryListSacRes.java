/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.display.vo.menu;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.MenuCategoryDetail;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.MenuDetail;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.ArrayList;
import java.util.List;

/**
 * Interface Message Menu List Value Object.
 * 
 * Updated on : 2015. 10. 01. Updated by : 정화수
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class MenuCategoryListSacRes extends CommonInfo {

	private static final long serialVersionUID = 11123123126L;

	private CommonResponse commonResponse = new CommonResponse();
	private List<MenuCategoryDetail> menuCategoryDetailList = new ArrayList<MenuCategoryDetail>();

	/**
	 * 
	 * <pre>
	 * 공통 Response.
	 * </pre>
	 * 
	 * @return CommonResponse
	 */
	public CommonResponse getCommonResponse() {
		return this.commonResponse;
	}

	/**
	 * 
	 * <pre>
	 * 메뉴카테고리 상세 리스트.
	 * </pre>
	 * 
	 * @return List<MenuDetail>
	 */
	public List<MenuCategoryDetail> getMenuCategoryDetailList() {
		return this.menuCategoryDetailList;
	}

	/**
	 * 
	 * <pre>
	 * 메뉴카테고리 상세.
	 * </pre>
	 * 
	 * @param menuCategoryDetail 메뉴카테고리 상세
	 */
	public void addMenuCategoryDetail(MenuCategoryDetail menuCategoryDetail) {
		menuCategoryDetailList.add( menuCategoryDetail );
	}
}
