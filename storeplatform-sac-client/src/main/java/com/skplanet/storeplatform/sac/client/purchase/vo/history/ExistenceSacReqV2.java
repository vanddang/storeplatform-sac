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

import java.util.List;

import javax.validation.Valid;

import org.hibernate.validator.constraints.NotEmpty;

import com.skplanet.storeplatform.sac.client.purchase.common.vo.PurchaseHeaderSacReq;

/**
 * 
 * 기구매체크 요청.
 * 
 * Updated on : 2013. 12. 28. Updated by : 조용진, 엔텔스.
 */
public class ExistenceSacReqV2 extends PurchaseHeaderSacReq {
	private static final long serialVersionUID = 1L;

	@NotEmpty
	@Valid
	private List<ExistenceUserInfoSacV2> userList;
	@NotEmpty
	@Valid
	private List<ExistenceInfoSac> prodList;

	/**
	 * @return the userList
	 */
	public List<ExistenceUserInfoSacV2> getUserList() {
		return this.userList;
	}

	/**
	 * @param userList
	 *            the userList to set
	 */
	public void setUserList(List<ExistenceUserInfoSacV2> userList) {
		this.userList = userList;
	}

	/**
	 * @return the prodList
	 */
	public List<ExistenceInfoSac> getProdList() {
		return this.prodList;
	}

	/**
	 * @param prodList
	 *            the prodList to set
	 */
	public void setProdList(List<ExistenceInfoSac> prodList) {
		this.prodList = prodList;
	}

}
