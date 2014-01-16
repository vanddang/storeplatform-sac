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

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;

/**
 * Interface Message Menu List Value Object.
 * 
 * Updated on : 2013. 12. 19 Updated by : 윤주영, SK 플래닛.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class MenuListRes extends CommonInfo implements Serializable {

	private static final long serialVersionUID = 11123123126L;

	private CommonResponse commonResponse;
	private List<MenuDetail> menuList;

	public CommonResponse getCommonResponse() {
		return this.commonResponse;
	}

	public void setCommonResponse(CommonResponse commonResponse) {
		this.commonResponse = commonResponse;
	}

	public List<MenuDetail> getMenuList() {
		return this.menuList;
	}

	public void setMenuList(List<MenuDetail> menuList) {
		this.menuList = menuList;
	}
}
