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

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.MenuDetail;

/**
 * Interface Message Menu List Value Object.
 * 
 * Updated on : 2014. 02. 12. Updated by : 유시혁.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class MenuDetailSacRes extends CommonInfo {

	private static final long serialVersionUID = 11123123125L;

	private CommonResponse commonResponse;
	private MenuDetail menuDetail;

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
	 * 공통 Response.
	 * </pre>
	 * 
	 * @param commonResponse
	 *            CommonResponse
	 */
	public void setCommonResponse(CommonResponse commonResponse) {
		this.commonResponse = commonResponse;
	}

	/**
	 * 
	 * <pre>
	 * 메뉴 상세 정보.
	 * </pre>
	 * 
	 * @return MenuDetail
	 */
	public MenuDetail getMenuDetail() {
		return this.menuDetail;
	}

	/**
	 * 
	 * <pre>
	 * 메뉴 상세 정보.
	 * </pre>
	 * 
	 * @param menuDetail
	 *            MenuDetail
	 */
	public void setMenuDetail(MenuDetail menuDetail) {
		this.menuDetail = menuDetail;
	}

}
