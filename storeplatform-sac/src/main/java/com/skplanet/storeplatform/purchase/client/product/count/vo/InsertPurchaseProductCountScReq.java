/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.purchase.client.product.count.vo;

import java.util.List;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.purchase.client.common.vo.PrchsProdCnt;

/**
 * 
 * 상품 건수 저장 요청 VO
 * 
 * Updated on : 2014. 3. 25. Updated by : 이승택, nTels.
 */
public class InsertPurchaseProductCountScReq extends CommonInfo {
	private static final long serialVersionUID = 1L;

	private List<PrchsProdCnt> prchsProdCntList;

	/**
	 */
	public InsertPurchaseProductCountScReq() {
	}

	/**
	 * @param prchsProdCntList
	 *            상품 건수 정보 목록
	 */
	public InsertPurchaseProductCountScReq(List<PrchsProdCnt> prchsProdCntList) {
		this.prchsProdCntList = prchsProdCntList;
	}

	/**
	 * @return the prchsProdCntList
	 */
	public List<PrchsProdCnt> getPrchsProdCntList() {
		return this.prchsProdCntList;
	}

	/**
	 * @param prchsProdCntList
	 *            the prchsProdCntList to set
	 */
	public void setPrchsProdCntList(List<PrchsProdCnt> prchsProdCntList) {
		this.prchsProdCntList = prchsProdCntList;
	}

}
