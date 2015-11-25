/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.purchase.client.product.count.sci;

import com.skplanet.storeplatform.framework.core.proxy.SCI;
import com.skplanet.storeplatform.purchase.client.product.count.vo.GetPrchsProdCntScReq;
import com.skplanet.storeplatform.purchase.client.product.count.vo.GetPrchsProdCntScRes;
import com.skplanet.storeplatform.purchase.client.product.count.vo.InsertPurchaseProductCountScReq;
import com.skplanet.storeplatform.purchase.client.product.count.vo.InsertPurchaseProductCountScRes;
import com.skplanet.storeplatform.purchase.client.product.count.vo.UpdatePrchsProdCntProcStatusScReq;
import com.skplanet.storeplatform.purchase.client.product.count.vo.UpdatePrchsProdCntProcStatusScRes;

/**
 * 
 * 구매 상품 건수 처리 인터페이스
 * 
 * Updated on : 2014. 3. 25. Updated by : 이승택, nTels.
 */
@SCI
public interface PurchaseCountSCI {

	/**
	 * 
	 * <pre>
	 * 상품 건수 저장.
	 * </pre>
	 * 
	 * @param req
	 *            상품 건수 저장 요청 VO
	 * @return 상품 건수 저장 응답 VO
	 */
	public InsertPurchaseProductCountScRes insertPurchaseProductCount(InsertPurchaseProductCountScReq req);

	/**
	 * 
	 * <pre>
	 * updatePrchsProdCntProcStatus.
	 * </pre>
	 * 
	 * @param updatePrchsProdCntProcStatusScReq
	 *            updatePrchsProdCntProcStatusScReq
	 * @return UpdatePrchsProdCntProcStatusScRes
	 */
	public UpdatePrchsProdCntProcStatusScRes updatePrchsProdCntProcStatus(
			UpdatePrchsProdCntProcStatusScReq updatePrchsProdCntProcStatusScReq);

	/**
	 * 
	 * <pre>
	 * getPrchsProdCnt.
	 * </pre>
	 * 
	 * @param getPrchsProdCntScReq
	 *            getPrchsProdCntScReq
	 * @return GetPrchsProdCntScRes
	 */
	public GetPrchsProdCntScRes getPrchsProdCnt(GetPrchsProdCntScReq getPrchsProdCntScReq);

}
