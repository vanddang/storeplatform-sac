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

import org.hibernate.validator.constraints.NotEmpty;

import com.skplanet.storeplatform.sac.client.purchase.common.vo.PurchaseCommonSacReq;

/**
 * 구매 취소(사용자) 요청 VO.
 * 
 * Updated on : 2014. 2. 12. Updated by : nTels_cswoo81, nTels.
 */
public class PurchaseCancelByUserSacReq extends PurchaseCommonSacReq {

	private static final long serialVersionUID = 1L;

	@NotEmpty
	private List<PurchaseCancelByUserDetailSacReq> prchsCancelList;

	/**
	 * @return the prchsCancelList
	 */
	public List<PurchaseCancelByUserDetailSacReq> getPrchsCancelList() {
		return this.prchsCancelList;
	}

	/**
	 * @param prchsCancelList
	 *            the prchsCancelList to set
	 */
	public void setPrchsCancelList(List<PurchaseCancelByUserDetailSacReq> prchsCancelList) {
		this.prchsCancelList = prchsCancelList;
	}

}
