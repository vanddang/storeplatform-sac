/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.display.vo.feature.recommend;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 테마추천 상품 Request Value Object.
 * 
 * Updated on : 2014. 02. 05. Updated by : 윤주영, SK 플래닛.
 */
public class ThemeRecommendProdSacReq extends CommonInfo {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4984188877183740905L;

	private String filteredBy; // 서비스 구분 (short|long)

	@NotNull
	@NotBlank
	private String recommendId; // 테마 추천 ID

	private int offset = 1; // 시작점 ROW

	private int count = 20; // 페이지당 노출 ROW 수

	public String getFilteredBy() {
		return this.filteredBy;
	}

	public void setFilteredBy(String filteredBy) {
		this.filteredBy = filteredBy;
	}

	/**
	 * @return the recommendId
	 */
	public String getRecommendId() {
		return this.recommendId;
	}

	/**
	 * @param recommendId
	 *            the recommendId to set
	 */
	public void setRecommendId(String recommendId) {
		this.recommendId = recommendId;
	}

	public int getOffset() {
		return this.offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getCount() {
		return this.count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}
