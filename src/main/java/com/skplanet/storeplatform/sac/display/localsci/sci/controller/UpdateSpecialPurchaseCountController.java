/**
 * 
 */
package com.skplanet.storeplatform.sac.display.localsci.sci.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;

import com.skplanet.storeplatform.framework.integration.bean.LocalSCI;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.sci.UpdateSpecialPurchaseCountSCI;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.SpecialPurchaseCountSacReq;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.SpecialPurchaseCountSacRes;
import com.skplanet.storeplatform.sac.display.localsci.sci.service.UpdateSpecialPurchaseCountService;

/**
 * 특가 상품 구매수 업데이트
 * 
 * Updated on : 2015. 03. 26. Updated by : 김형식
 */
@LocalSCI
public class UpdateSpecialPurchaseCountController implements UpdateSpecialPurchaseCountSCI {

	@Autowired
	private UpdateSpecialPurchaseCountService updateSpecialPurchaseCountService;

	/**
	 * 입력받은 상품의 구매수를 업데이트 한다
	 * 
	 * @param req
	 *            req
	 * @return SpecialPurchaseCountSacRes  상품 정보 .            
	 */
	
	@Override
	public SpecialPurchaseCountSacRes updateSpecialPurchaseCount(@Validated SpecialPurchaseCountSacReq req) {
		return this.updateSpecialPurchaseCountService.updateSpecialPurchaseCount(req);
	}	


}
