/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.purchase.cancel.vo;

import java.util.List;

import com.skplanet.storeplatform.sac.client.purchase.common.vo.PurchaseCommonSacReq;

/**
 * 구매 취소 요청 VO.
 * 
 * Updated on : 2014. 1. 16. Updated by : nTels_cswoo81, nTels.
 */
public class PurchaseCancelReq extends PurchaseCommonSacReq {

	private static final long serialVersionUID = 1L;

	private List<PurchaseCancelReqDetail> prchsCancelReqList;

	/**
	 * @return the prchsCancelReqList
	 */
	public List<PurchaseCancelReqDetail> getPrchsCancelReqList() {
		return this.prchsCancelReqList;
	}

	/**
	 * @param prchsCancelReqList
	 *            the prchsCancelReqList to set
	 */
	public void setPrchsCancelReqList(List<PurchaseCancelReqDetail> prchsCancelReqList) {
		this.prchsCancelReqList = prchsCancelReqList;
	}

}
