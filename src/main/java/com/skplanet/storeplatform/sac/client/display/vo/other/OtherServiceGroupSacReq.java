/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.display.vo.other;

import java.util.List;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 기타 카테고리 상품서비스군 상품 조회 Request Value Object.
 * 
 * Updated on : 2014. 1. 28. Updated by : 이승훈, 엔텔스.
 */
public class OtherServiceGroupSacReq extends CommonInfo {
	private static final long serialVersionUID = 1L;

	private String list; // 리스트;
	private List<String> prodIdList;

	public List<String> getProdIdList() {
		return this.prodIdList;
	}

	public void setProdIdList(List<String> prodIdList) {
		this.prodIdList = prodIdList;
	}

	public String getList() {
		return this.list;
	}

	public void setList(String list) {
		this.list = list;
	}

}
