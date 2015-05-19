/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.display.vo.card;

import java.util.List;

import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

public class CardDetailSacReq extends CommonInfo {

	private static final long serialVersionUID = -6674026392593497068L;

	@NotBlank
	private String id;

	private String  userKey;

	private List<PreferredCategoryReq> preferredCategoryList;

	public String getId() {
		return id;
	}

	public CardDetailSacReq setId(String id) {
		this.id = id;
		return this;
	}

	public String getUserKey() {
		return userKey;
	}

	public CardDetailSacReq setUserKey(String userKey) {
		this.userKey = userKey;
		return this;
	}

	public List<PreferredCategoryReq> getPreferredCategoryList() {
		return preferredCategoryList;
	}

	public CardDetailSacReq setPreferredCategoryList(List<PreferredCategoryReq> preferredCategoryList) {
		this.preferredCategoryList = preferredCategoryList;
		return this;
	}

}
