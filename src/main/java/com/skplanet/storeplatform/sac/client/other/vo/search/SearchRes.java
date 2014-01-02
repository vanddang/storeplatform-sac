/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.other.vo.search;

import java.util.List;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

public class SearchRes extends CommonInfo {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Category> categoryList;
	private String relKeywd;
	private String autoTransKeywd;
	private String orgKeywd;
	private List<Search> productList;

	public List<Category> getCategoryList() {
		return this.categoryList;
	}

	public void setCategoryList(List<Category> categoryList) {
		this.categoryList = categoryList;
	}

	public String getRelKeywd() {
		return this.relKeywd;
	}

	public void setRelKeywd(String relKeywd) {
		this.relKeywd = relKeywd;
	}

	public String getAutoTransKeywd() {
		return this.autoTransKeywd;
	}

	public void setAutoTransKeywd(String autoTransKeywd) {
		this.autoTransKeywd = autoTransKeywd;
	}

	public String getOrgKeywd() {
		return this.orgKeywd;
	}

	public void setOrgKeywd(String orgKeywd) {
		this.orgKeywd = orgKeywd;
	}

	public List<Search> getProductList() {
		return this.productList;
	}

	public void setProductList(List<Search> productList) {
		this.productList = productList;
	}

}
