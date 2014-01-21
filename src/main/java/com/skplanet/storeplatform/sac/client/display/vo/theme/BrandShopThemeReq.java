/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.display.vo.theme;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 일반/특정 상품 카테고리 리스트 조회 Input Value Object.
 * 
 * Updated on : 2013. 11. 28. Updated by : 이태희, SK 플래닛.
 */
public class BrandShopThemeReq extends CommonInfo {

	private String menuId; // 메뉴Id
	private String brandshopId; // 테마존 Id
	private String orderedBy; // 이미지 사이즈 코드
	private String offset; // 시작점 ROW
	private String count; // 페이지당 노출 ROW 수

	/**
	 * 
	 * <pre>
	 * menu Id.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getMenuId() {
		return this.menuId;
	}

	/**
	 * 
	 * <pre>
	 * menu Id.
	 * </pre>
	 * 
	 * @param menuId
	 *            menuId
	 */
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	/**
	 * 
	 * <pre>
	 * 브랜드샵 Id.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getBrandshopId() {
		return this.brandshopId;
	}

	/**
	 * 
	 * <pre>
	 * 브랜드샵 Id.
	 * </pre>
	 * 
	 * @param brandshopId
	 *            brandshopId
	 */
	public void setBrandshopId(String brandshopId) {
		this.brandshopId = brandshopId;
	}

	/**
	 * 
	 * <pre>
	 * 정렬방식.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getOrderedBy() {
		return this.orderedBy;
	}

	/**
	 * 
	 * <pre>
	 * 정렬방식.
	 * </pre>
	 * 
	 * @param orderedBy
	 *            orderedBy
	 */
	public void setOrderedBy(String orderedBy) {
		this.orderedBy = orderedBy;
	}

	/**
	 * 
	 * <pre>
	 * 시작점 ROW.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getOffset() {
		return this.offset;
	}

	/**
	 * 
	 * <pre>
	 * 시작점 ROW.
	 * </pre>
	 * 
	 * @param offset
	 *            offset
	 */
	public void setOffset(String offset) {
		this.offset = offset;
	}

	/**
	 * 
	 * <pre>
	 * 페이지당 노출될 ROW 개수.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getCount() {
		return this.count;
	}

	/**
	 * 
	 * <pre>
	 * 페이지당 노출될 ROW 개수.
	 * </pre>
	 * 
	 * @param count
	 *            count
	 */
	public void setCount(String count) {
		this.count = count;
	}

}
