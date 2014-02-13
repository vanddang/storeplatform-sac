/**
 * 
 */
package com.skplanet.storeplatform.sac.display.localsci.sci.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;

import com.skplanet.storeplatform.framework.integration.bean.LocalSCI;
import com.skplanet.storeplatform.sac.client.internal.display.sci.SearchSellerKeySCI;
import com.skplanet.storeplatform.sac.display.localsci.sci.service.SearchSellerKeyService;

/**
 * SellerKey 조회 호출 Controller.
 * 
 * Updated on : 2014. 2. 13. Updated by : 이석희, 아이에스플러스.
 */
@LocalSCI
public class SearchSellerKeySCIController implements SearchSellerKeySCI {

	@Autowired
	private SearchSellerKeyService searchSellerKeyService;

	/**
	 * App Id로 Seller Key를 조회한다.
	 */
	@Override
	public String searchSellerKeyForAid(@Validated String aid) {
		return this.searchSellerKeyService.searchSellerKeyForAid(aid);
	}

}
