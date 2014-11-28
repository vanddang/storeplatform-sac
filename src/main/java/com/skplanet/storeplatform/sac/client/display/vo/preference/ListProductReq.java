/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.display.vo.preference;

import javax.validation.constraints.Pattern;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * <p>
 * ListProductReq
 * </p>
 * Updated on : 2014. 10. 31 Updated by : 서대영, SK 플래닛.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ListProductReq extends CommonInfo {

    private static final long serialVersionUID = 1L;

    @NotBlank
    private String userKey;

    @Pattern(regexp = "Y|N")
    private String adultYn;

    @NotBlank
    private String menuId;
    
    private Integer count;

	public String getUserKey() {
		return userKey;
	}

	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	public String getAdultYn() {
		return adultYn;
	}

	public void setAdultYn(String adultYn) {
		this.adultYn = adultYn;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
	
}
