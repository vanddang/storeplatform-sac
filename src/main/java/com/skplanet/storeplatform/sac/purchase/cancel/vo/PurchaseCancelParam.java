/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.cancel.vo;

import java.util.ArrayList;
import java.util.List;

import com.skplanet.storeplatform.sac.client.purchase.cancel.vo.PurchaseCancelReq;
import com.skplanet.storeplatform.sac.client.purchase.cancel.vo.PurchaseCancelReqDetail;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.purchase.common.vo.PurchaseCommonSacParam;

/**
 * 구매 취소 Param VO.
 * 
 * Updated on : 2014. 1. 16. Updated by : nTels_cswoo81, nTels.
 */
public class PurchaseCancelParam extends PurchaseCommonSacParam {

	private static final long serialVersionUID = 1L;

	private List<PurchaseCancelParamDetail> prchsCancelParamDetailList;

	public PurchaseCancelParam() {
		super();
	}

	public PurchaseCancelParam(SacRequestHeader sacRequestHeader) {
		super(sacRequestHeader);
	}

	public PurchaseCancelParam(SacRequestHeader sacRequestHeader, PurchaseCancelReq purchaseCancelReq) {
		super(sacRequestHeader, purchaseCancelReq);

		this.prchsCancelParamDetailList = new ArrayList<PurchaseCancelParamDetail>();
		for (PurchaseCancelReqDetail purchaseCancelReqDetail : purchaseCancelReq.getPrchsCancelReqList()) {
			PurchaseCancelParamDetail purchaseCancelParamDetail = new PurchaseCancelParamDetail(purchaseCancelReqDetail);
			this.prchsCancelParamDetailList.add(purchaseCancelParamDetail);
		}
	}

	/**
	 * @return the prchsCancelParamDetailList
	 */
	public List<PurchaseCancelParamDetail> getPrchsCancelParamDetailList() {
		return this.prchsCancelParamDetailList;
	}

	/**
	 * @param prchsCancelParamDetailList
	 *            the prchsCancelParamDetailList to set
	 */
	public void setPrchsCancelParamDetailList(List<PurchaseCancelParamDetail> prchsCancelParamDetailList) {
		this.prchsCancelParamDetailList = prchsCancelParamDetailList;
	}

}
