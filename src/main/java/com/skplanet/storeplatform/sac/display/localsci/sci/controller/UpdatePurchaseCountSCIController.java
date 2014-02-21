/**
 * 
 */
package com.skplanet.storeplatform.sac.display.localsci.sci.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;

import com.skplanet.storeplatform.framework.integration.bean.LocalSCI;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.sci.UpdatePurchaseCountSCI;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.UpdatePurchaseCountSacReq;
import com.skplanet.storeplatform.sac.display.localsci.sci.service.UpdatePurchaseCountService;

/**
 * 상품 구매수 업데이트 Controller.
 * 
 * Updated on : 2014. 2. 13. Updated by : 이석희, 아이에스플러스.
 */
@LocalSCI
public class UpdatePurchaseCountSCIController implements UpdatePurchaseCountSCI {

	@Autowired
	private UpdatePurchaseCountService updatePurchaseCountService;

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
	@Override
	public void updatePurchaseCount(@Validated List<UpdatePurchaseCountSacReq> reqList) {
		this.updatePurchaseCountService.updatePurchaseCount(reqList);
	}

}
