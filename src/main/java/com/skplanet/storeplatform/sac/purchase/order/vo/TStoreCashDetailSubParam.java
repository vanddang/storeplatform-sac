/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *  
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */

package com.skplanet.storeplatform.sac.purchase.order.vo;

import com.skplanet.storeplatform.external.client.tstore.vo.TStoreCashChargeEcReq;

/**
 * Class 설명
 *
 * Updated on : 15. 10. 22.
 * Updated by : 황민규, SK 플래닛.
 */
public class TStoreCashDetailSubParam extends TStoreCashChargeEcReq{
	private String identifier;

	public TStoreCashDetailSubParam(TStoreCashChargeEcReq tStoreCashChargeEcReq)
	{
		this.setUserKey(tStoreCashChargeEcReq.getUserKey());
		this.setProductGroup(tStoreCashChargeEcReq.getProductGroup());
		this.setProcType(tStoreCashChargeEcReq.getProcType());
		this.setOrderNo(tStoreCashChargeEcReq.getOrderNo());
		this.setDate(tStoreCashChargeEcReq.getDate());
		this.setCashCls(tStoreCashChargeEcReq.getCashCls());
		this.setAmt(tStoreCashChargeEcReq.getAmt());
		this.setAddCol1(tStoreCashChargeEcReq.getAddCol1());
	}

	/**
	 * Gets identifier.
	 *
	 * @return the identifier
	 */
	public String getIdentifier() {
		return identifier;
	}

	/**
	 * Sets identifier.
	 *
	 * @param identifier
	 *            the identifier
	 */
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
}
