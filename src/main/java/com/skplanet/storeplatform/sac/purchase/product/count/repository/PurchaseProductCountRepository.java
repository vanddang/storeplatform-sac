/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.product.count.repository;

import com.skplanet.storeplatform.purchase.client.product.count.vo.GetPrchsProdCntScReq;
import com.skplanet.storeplatform.purchase.client.product.count.vo.GetPrchsProdCntScRes;
import com.skplanet.storeplatform.purchase.client.product.count.vo.UpdatePrchsProdCntProcStatusScReq;
import com.skplanet.storeplatform.purchase.client.product.count.vo.UpdatePrchsProdCntProcStatusScRes;

/**
 * Class 설명
 * 
 * Updated on : 2014. 3. 27. Updated by : nTels_cswoo81, nTels.
 */
public interface PurchaseProductCountRepository {

	/**
	 * 
	 * <pre>
	 * updatePrchsProdCntProcStatus
	 * </pre>
	 * 
	 * @param updatePrchsProdCntProcStatusScReq
	 *            updatePrchsProdCntProcStatusScReq
	 * @return UpdatePrchsProdCntProcStatusScRes
	 */
	UpdatePrchsProdCntProcStatusScRes updatePrchsProdCntProcStatus(
			UpdatePrchsProdCntProcStatusScReq updatePrchsProdCntProcStatusScReq);

	/**
	 * 
	 * <pre>
	 * getPrchsProdCnt.
	 * </pre>
	 * 
	 * @param getPrchsProdCntScReq
	 *            GetPrchsProdCntScRes
	 * @return GetPrchsProdCntScRes
	 */
	GetPrchsProdCntScRes getPrchsProdCnt(GetPrchsProdCntScReq getPrchsProdCntScReq);

}
