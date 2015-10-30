/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.purchase.client.history.vo;

import java.util.List;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * 기구매체크 요청.
 * 
 * Updated on : 2013. 12. 28. Updated by : 조용진, 엔텔스.
 */
public class ExistenceScV2Req extends CommonInfo {
	private static final long serialVersionUID = 1L;

	private List<ExistenceUserInfoScV2> userList;
	private List<String> prodList;

	/**
	 * @return the userList
	 */
	public List<ExistenceUserInfoScV2> getUserList() {
		return this.userList;
	}

	/**
	 * @param userList
	 *            the userList to set
	 */
	public void setUserList(List<ExistenceUserInfoScV2> userList) {
		this.userList = userList;
	}

	/**
	 * @return the prodList
	 */
	public List<String> getProdList() {
		return this.prodList;
	}

	/**
	 * @param prodList
	 *            the prodList to set
	 */
	public void setProdList(List<String> prodList) {
		this.prodList = prodList;
	}

}
