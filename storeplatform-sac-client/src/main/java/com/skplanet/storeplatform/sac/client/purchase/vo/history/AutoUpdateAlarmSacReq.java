/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.purchase.vo.history;

import java.io.Serializable;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 자동업데이트 거부/거부취소 요청.
 * 
 * Updated on : 2014. 2. 25. Updated by : 조용진, NTELS.
 */
public class AutoUpdateAlarmSacReq extends CommonInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotNull
	@NotEmpty
	private String userKey; // 내부사용자번호
	@NotEmpty
	@NotNull
	@Valid
	private List<AutoUpdateAlarmSac> productList; // 알람YN

	/**
	 * @return the userKey
	 */
	public String getUserKey() {
		return this.userKey;
	}

	/**
	 * @param userKey
	 *            the userKey to set
	 */
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	/**
	 * @return the productList
	 */
	public List<AutoUpdateAlarmSac> getProductList() {
		return this.productList;
	}

	/**
	 * @param productList
	 *            the productList to set
	 */
	public void setProductList(List<AutoUpdateAlarmSac> productList) {
		this.productList = productList;
	}

}
