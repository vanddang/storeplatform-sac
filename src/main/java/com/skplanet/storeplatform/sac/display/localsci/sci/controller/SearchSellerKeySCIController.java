/**
 * 
 */
package com.skplanet.storeplatform.sac.display.localsci.sci.controller;

import com.skplanet.storeplatform.framework.integration.bean.LocalSCI;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.sci.SearchSellerKeySCI;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.util.SacRequestHeaderHolder;
import com.skplanet.storeplatform.sac.display.localsci.sci.service.SearchSellerKeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;

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
	 * 
	 * <pre>
	 * App Id로 Seller Key를 조회한다.
	 * </pre>
	 * 
	 * @param aid
	 *            aid
	 * @return String
	 */
	@Override
	public String searchSellerKeyForAid(@Validated String aid) {
		SacRequestHeader requestHeader = SacRequestHeaderHolder.getValue();
		String tenantId = requestHeader.getTenantHeader().getTenantId();
		return this.searchSellerKeyService.searchSellerKeyForAid(aid, tenantId);
	}

}
