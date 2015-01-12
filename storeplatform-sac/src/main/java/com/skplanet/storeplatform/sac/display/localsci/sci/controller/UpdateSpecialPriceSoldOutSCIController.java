/**
 * 
 */
package com.skplanet.storeplatform.sac.display.localsci.sci.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;

import com.skplanet.storeplatform.framework.integration.bean.LocalSCI;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.sci.UpdateSpecialPriceSoldOutSCI;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.SpecialPriceSoldOutReq;
import com.skplanet.storeplatform.sac.display.localsci.sci.service.UpdateSpecialPriceSoldOutService;

/**
 * 쇼핑 특가 상품에 대한 품절 여부(Sold Out) 등록
 * 
 * Updated on : 2014. 12. 04. Updated by : 김형식
 */
@LocalSCI
public class UpdateSpecialPriceSoldOutSCIController implements UpdateSpecialPriceSoldOutSCI {

	@Autowired
	private UpdateSpecialPriceSoldOutService updateSpecialPriceSoldOutService;

	/**
	 * 
	 * <pre>
	 * 쇼핑 특가 상품에 대한 품절 여부(Sold Out) 등록 한다.
	 * </pre>
	 * 
	 * @param req
	 *            req
	 * @return
	 */
	@Override
	public void updateSpecialPriceSoldOut(@Validated SpecialPriceSoldOutReq req) {
		this.updateSpecialPriceSoldOutService.updateSpecialPriceSoldOut(req);
	}

}
