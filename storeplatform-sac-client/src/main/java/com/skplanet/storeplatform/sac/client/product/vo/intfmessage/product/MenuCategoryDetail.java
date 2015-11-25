/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * Interface Message Menu Value Object.
 * 
 * Updated on : 2015. 10. 01. Updated by : 정화수.
 */
@JsonSerialize( include = JsonSerialize.Inclusion.NON_NULL )
public class MenuCategoryDetail extends CommonInfo {

	private String tenantId;
	private String menuId;
	private String menuNm;
	private String menuDesc;

	/**
	 * 테넌트ID
	 *
	 * @return 테넌트ID
	 */
	public String getTenantId() {
		return tenantId;
	}

	/**
	 * 테넌트ID
	 * @param tenantId 테넌트ID
	 */
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	/**
	 * 메뉴ID
	 * @return 메뉴ID
	 */
	public String getMenuId() {
		return menuId;
	}

	/**
	 * 메뉴ID
	 * @param menuId 메뉴ID
	 */
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	/**
	 * 메뉴명
	 *
	 * @return 메뉴명
	 */
	public String getMenuNm() {
		return menuNm;
	}

	/**
	 * 메뉴명
	 * @param menuNm 메뉴명
	 */
	public void setMenuNm(String menuNm) {
		this.menuNm = menuNm;
	}

	/**
	 * 메뉴상세설명 (Location)
	 * @return 메뉴상세설명 (Location)
	 */
	public String getMenuDesc() {
		return menuDesc;
	}

	/**
	 * 메뉴상세설명 (Location)
	 * @param menuDesc 메뉴상세설명 (Location)
	 */
	public void setMenuDesc(String menuDesc) {
		this.menuDesc = menuDesc;
	}

}
