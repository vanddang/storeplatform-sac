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
public class EbookComicThemeReq extends CommonInfo {

	private static final long serialVersionUID = 1L;
	private String listId; // 리스트 Id
	private String filteredBy; // 상품유형 구분
	private String offset; // 시작점 ROW
	private String count; // 페이지당 노출 ROW 수

	/**
	 * 
	 * <pre>
	 * 리스트 Id.
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
	 * 리스트 Id.
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
	 * 상품유형 구분.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getFilteredBy() {
		return this.filteredBy;
	}

	/**
	 * 
	 * <pre>
	 * 상품유형 구분.
	 * </pre>
	 * 
	 * @param filteredBy
	 *            filteredBy
	 */
	public void setFilteredBy(String filteredBy) {
		this.filteredBy = filteredBy;
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
