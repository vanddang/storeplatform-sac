/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.common.util;

import com.skplanet.storeplatform.sac.client.purchase.common.vo.PurchaseCommonSacReq;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.purchase.common.vo.PurchaseCommonSacParam;

/**
 * Class 설명
 * 
 * Updated on : 2014. 2. 3. Updated by : nTels_cswoo81, nTels.
 */
public class ConvertVO {

	public static boolean convertPurchaseCommonSacReq(SacRequestHeader sacRequestHeader,
			PurchaseCommonSacReq purchaseCommonSacReq, PurchaseCommonSacParam purchaseCommonSacParam) {

		if (sacRequestHeader == null || purchaseCommonSacReq == null || purchaseCommonSacParam == null) {
			return false;
		}

		purchaseCommonSacParam.setTenantId(sacRequestHeader.getTenantHeader().getTenantId());
		purchaseCommonSacParam.setSystemId(sacRequestHeader.getTenantHeader().getSystemId());
		purchaseCommonSacParam.setLangCd(sacRequestHeader.getTenantHeader().getLangCd());

		purchaseCommonSacParam.setUserKey(purchaseCommonSacReq.getUserKey());
		purchaseCommonSacParam.setDeviceKey(purchaseCommonSacReq.getDeviceKey());

		return true;

	}

}
