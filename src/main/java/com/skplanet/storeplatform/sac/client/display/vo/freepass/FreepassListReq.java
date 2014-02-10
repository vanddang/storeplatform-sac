/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.display.vo.freepass;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 정액제 리스트 조회 Request Value Object.
 * 
 * Updated on : 2014. 02. 07. Updated by : 서영배, GTSOFT.
 */
public class FreepassListReq extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private String topMenuId; // 최상위메뉴ID
	private String kind; // 자유이용권 종류
	private String productId; // 상품ID
	private String menuId; // 메뉴ID
	private String channelId; // 채널상품ID
	private int offset; // offset
	private int count; // count

	// Dummy Data용
	private String dummy; // 메뉴ID

	public String getTopMenuId() {
		return topMenuId;
	}

	public void setTopMenuId(String topMenuId) {
		this.topMenuId = topMenuId;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getDummy() {
		return dummy;
	}

	public void setDummy(String dummy) {
		this.dummy = dummy;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
