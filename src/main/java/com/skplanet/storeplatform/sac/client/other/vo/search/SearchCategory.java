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

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

@JsonSerialize(include = Inclusion.NON_EMPTY)
public class SearchCategory extends CommonInfo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String categoryCd;
	private String categoryNm;
	private Integer categoryCnt;
	private String categoryDesc;

	public SearchCategory() {
	}

	public SearchCategory(String categoryCd) {
		this.categoryCd = categoryCd;
	}

	public String getCategoryCd() {
		return this.categoryCd;
	}

	public void setCategoryCd(String categoryCd) {
		this.categoryCd = categoryCd;
	}

	public String getCategoryNm() {
		return this.categoryNm;
	}

	public void setCategoryNm(String categoryNm) {
		this.categoryNm = categoryNm;
	}

	public Integer getCategoryCnt() {
		return this.categoryCnt;
	}

	public void setCategoryCnt(Integer categoryCnt) {
		this.categoryCnt = categoryCnt;
	}

	public String getCategoryDesc() {
		return this.categoryDesc;
	}

	public void setCategoryDesc(String categoryDesc) {
		this.categoryDesc = categoryDesc;
	}

}
