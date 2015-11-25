/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.display.vo.related;

/**
 * [I03000126] 2.5.6. 이 상품과 함께 구매한 상품 조회 v2 Request Client VO
 * Created on : 2014. 07. 18. by 서대영, SK 플래닛
 */
public class BoughtTogetherProductSacV2Req extends BoughtTogetherProductSacReq {

	private static final long serialVersionUID = 1L;

	private String menuId;

	public String getMenuId() {
		return this.menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

}
