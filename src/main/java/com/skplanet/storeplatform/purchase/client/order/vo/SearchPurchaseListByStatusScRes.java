/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 * 
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.purchase.client.order.vo;

import java.util.List;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * 상태코드 기준의 구매정보 조회 응답 VO
 * 
 * Updated on : 2014. 1. 23. Updated by : 이승택, nTels.
 */
public class SearchPurchaseListByStatusScRes extends CommonInfo {
	private static final long serialVersionUID = 201401231L;

	private List<PrchsDtlMore> prchsDtlMoreList;

	/**
	 */
	public SearchPurchaseListByStatusScRes() {
	}

	/**
	 * @param createPurchaseSc
	 *            구매상세 정보
	 */
	public SearchPurchaseListByStatusScRes(List<PrchsDtlMore> prchsDtlMoreList) {
		this.prchsDtlMoreList = prchsDtlMoreList;
	}

	/**
	 * @return the prchsDtlMoreList
	 */
	public List<PrchsDtlMore> getPrchsDtlMoreList() {
		return this.prchsDtlMoreList;
	}

	/**
	 * @param prchsDtlMoreList
	 *            the prchsDtlMoreList to set
	 */
	public void setPrchsDtlMoreList(List<PrchsDtlMore> prchsDtlMoreList) {
		this.prchsDtlMoreList = prchsDtlMoreList;
	}

}
