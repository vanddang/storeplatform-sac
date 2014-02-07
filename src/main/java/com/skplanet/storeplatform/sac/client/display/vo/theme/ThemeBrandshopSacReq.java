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
 * 테마존 테마 상품 조회 Input Value Object.
 * 
 * Updated on : 2014. 2. 6. Updated by : 이승훈, 엔텔스.
 */
public class ThemeBrandshopSacReq extends CommonInfo {

	private String listId; // 리스트 Id
	private String menuId; // 메뉴Id
	private String themezoneId; // 테마존 Id
	private String offset; // 시작점 ROW
	private String count; // 페이지당 노출 ROW 수
	// TODO osm1021 dummy data가 필요없어지면 삭제할것
	private String dummy;

	public String getDummy() {
		return this.dummy;
	}

	public void setDummy(String dummy) {
		this.dummy = dummy;
	}

	/**
	 * 
	 * <pre>
	 * 리스트 ID.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getListId() {
		return this.listId;
	}

	/**
	 * 
	 * <pre>
	 * 리스트 ID.
	 * </pre>
	 * 
	 * @param listId
	 *            listId
	 */
	public void setListId(String listId) {
		this.listId = listId;
	}

	/**
	 * 
	 * <pre>
	 * Menu Id.
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
	 * Menu Id.
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
	 * 테마존 Id.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getThemezoneId() {
		return this.themezoneId;
	}

	/**
	 * 
	 * <pre>
	 * 테마존 Id.
	 * </pre>
	 * 
	 * @param themezoneId
	 *            themezoneId
	 */
	public void setThemezoneId(String themezoneId) {
		this.themezoneId = themezoneId;
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
