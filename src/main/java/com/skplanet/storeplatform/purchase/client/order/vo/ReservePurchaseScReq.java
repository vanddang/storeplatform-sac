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
 * 구매 생성/확정 요청 VO
 * 
 * Updated on : 2014. 4. 2. Updated by : 이승택, nTels.
 */
public class ReservePurchaseScReq extends CommonInfo {
	private static final long serialVersionUID = 1L;

	private List<PrchsDtlMore> prchsDtlMoreList;

	/**
	 */
	public ReservePurchaseScReq() {
	}

	/**
	 * @param prchsDtlMoreList
	 *            구매생성 요청 데이터 목록
	 */
	public ReservePurchaseScReq(List<PrchsDtlMore> prchsDtlMoreList) {
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
