package com.skplanet.storeplatform.sac.display.localsci.sci.service;

import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.UpdatePurchaseCountSacReq;

import java.util.List;

/**
 * UpdatePurchaseCount Service 인터페이스(CoreStoreBusiness)
 * 
 * Updated on : 2014. 2. 14. Updated by : 이석희, 아이에스플러스.
 */

public interface UpdatePurchaseCountService {
	/**
	 * 
	 * <pre>
	 * 상품 구매수 업데이트.
	 * </pre>
	 * 
	 * @param reqList
	 *            reqList
	 * @return
	 */
	public void updatePurchaseCount(List<UpdatePurchaseCountSacReq> reqList);
}
