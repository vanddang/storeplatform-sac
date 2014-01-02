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

public class Category extends CommonInfo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@JsonSerialize(include = Inclusion.NON_EMPTY)
	private String categoryCd;
	@JsonSerialize(include = Inclusion.NON_EMPTY)
	private String categoryNm;
	@JsonSerialize(include = Inclusion.NON_EMPTY)
	private String categoryCnt;
	@JsonSerialize(include = Inclusion.NON_EMPTY)
	private String categoryDesc;

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

	public String getCategoryCnt() {
		return this.categoryCnt;
	}

	public void setCategoryCnt(String categoryCnt) {
		this.categoryCnt = categoryCnt;
	}

	public String getCategoryDesc() {
		return this.categoryDesc;
	}

	public void setCategoryDesc(String categoryDesc) {
		this.categoryDesc = categoryDesc;
	}

}
