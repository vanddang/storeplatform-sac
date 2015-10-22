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
import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.sac.purchase.constant.PurchaseConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * Tstore Cash Param
 * 
 * Updated on : 2015. 10. 22. Updated by : skp1002448, SK플래닛.
 */
public class TStoreCashDetailParam {

	private static final long serialVersionUID = 1L;

	private List<TStoreCashDetailSubParam> tStoreCashDetailSubParamList = new ArrayList<TStoreCashDetailSubParam>();

	public void addTstoreCashChargeEcReq(TStoreCashChargeEcReq tStoreCashChargeEcReq, String identifier) {
		TStoreCashDetailSubParam tStoreCashDetailSubParam = new TStoreCashDetailSubParam(tStoreCashChargeEcReq);
		tStoreCashDetailSubParam.setIdentifier(identifier);
		this.tStoreCashDetailSubParamList.add(tStoreCashDetailSubParam);
	}

	public List<TStoreCashDetailSubParam> getCashDetailParam() {
		return tStoreCashDetailSubParamList;
	}

	public String getCashIdToString() {
		StringBuffer sb = new StringBuffer();
		for (TStoreCashDetailSubParam tStoreCashDetailSubParam : tStoreCashDetailSubParamList) {
			if (StringUtils.equals(tStoreCashDetailSubParam.getCashCls(),
					PurchaseConstants.TSTORE_CASH_CLASS_POINT)) {
				sb.append("CASH=").append(tStoreCashDetailSubParam.getIdentifier()).append(PurchaseConstants.SEPARATOR);
			} else if (StringUtils
					.equals(tStoreCashDetailSubParam.getCashCls(), PurchaseConstants.TSTORE_CASH_CLASS_CASH)) {
				sb.append("POINT=").append(tStoreCashDetailSubParam.getIdentifier()).append(PurchaseConstants.SEPARATOR);
			}
		}
		return sb.toString();
	}
}

