/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.common.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

public class CmCategory extends CommonInfo {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String catCd;
	private String catNm;
	private String catEngNm;
	private String catDesc;

	public CmCategory(String catCd, String catNm, String catEngNm, String catDesc) {
		this.catCd = catCd;
		this.catNm = catNm;
		this.catEngNm = catEngNm;
		this.catDesc = catDesc;
	}

	public String getCatCd() {
		return this.catCd;
	}

	public void setCatCd(String catCd) {
		this.catCd = catCd;
	}

	public String getCatNm() {
		return this.catNm;
	}

	public void setCatNm(String catNm) {
		this.catNm = catNm;
	}

	public String getCatEngNm() {
		return this.catEngNm;
	}

	public void setCatEngNm(String catEngNm) {
		this.catEngNm = catEngNm;
	}

	public String getCatDesc() {
		return this.catDesc;
	}

	public void setCatDesc(String catDesc) {
		this.catDesc = catDesc;
	}

}
