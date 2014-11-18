/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.card.vo;

import java.util.List;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * <p>
 * PreferredCategoryInfo
 * </p>
 * Updated on : 2014. 10. 30 Updated by : 서대영, SK 플래닛.
 */
public class PreferredCategory extends CommonInfo {

	private static final long serialVersionUID = 1L;
	
	private String menuId;
	
	private String menuNm;
	
	public PreferredCategory() {
	}
	
	public PreferredCategory(String menuId, String menuNm) {
		this.menuId = menuId;
		this.menuNm = menuNm;
	}

	private List<PreferredCategory> prefer;

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getMenuNm() {
		return menuNm;
	}

	public void setMenuNm(String menuNm) {
		this.menuNm = menuNm;
	}

	public List<PreferredCategory> getPrefer() {
		return prefer;
	}

	public void setPrefer(List<PreferredCategory> prefer) {
		this.prefer = prefer;
	}
	
}
