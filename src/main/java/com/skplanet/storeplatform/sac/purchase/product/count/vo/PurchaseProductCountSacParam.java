/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.product.count.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * Class 설명
 * 
 * Updated on : 2014. 3. 27. Updated by : nTels_cswoo81, nTels.
 */
public class PurchaseProductCountSacParam extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private String guid;
	private Integer perCount; // 전시에 한번 요청할 카운트.

	/**
	 * @return the perCount
	 */
	public Integer getPerCount() {
		return this.perCount;
	}

	/**
	 * @param perCount
	 *            the perCount to set
	 */
	public void setPerCount(Integer perCount) {
		this.perCount = perCount;
	}

	/**
	 * @return the guid
	 */
	public String getGuid() {
		return this.guid;
	}

	/**
	 * @param guid
	 *            the guid to set
	 */
	public void setGuid(String guid) {
		this.guid = guid;
	}

}
