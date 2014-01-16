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

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;

/**
 * Interface Message Menu List Value Object.
 * 
 * Updated on : 2013. 12. 19 Updated by : 윤주영, SK 플래닛.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class MenuDetailRes extends CommonInfo implements Serializable {

	private static final long serialVersionUID = 11123123125L;

	private CommonResponse commonResponse;
	private MenuDetail menu;

	public CommonResponse getCommonResponse() {
		return this.commonResponse;
	}

	public void setCommonRes(CommonResponse commonResponse) {
		this.commonResponse = commonResponse;
	}

	public MenuDetail getMenu() {
		return this.menu;
	}

	public void setMenu(MenuDetail menu) {
		this.menu = menu;
	}
}
