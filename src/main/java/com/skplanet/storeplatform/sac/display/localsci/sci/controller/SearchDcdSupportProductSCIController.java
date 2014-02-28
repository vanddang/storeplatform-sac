package com.skplanet.storeplatform.sac.display.localsci.sci.controller;

import org.springframework.beans.factory.annotation.Autowired;

import com.skplanet.storeplatform.framework.integration.bean.LocalSCI;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.sci.SearchDcdSupportProductSCI;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.DcdSupportProductRes;
import com.skplanet.storeplatform.sac.display.localsci.sci.service.SearchDcdSupportProductService;

/**
 * SearchDcdSupportProductSCIController 조회 호출 Controller.
 * 
 * Updated on : 2014. 2. 27. Updated by : 이석희, 아이에스플러스.
 */
@LocalSCI
public class SearchDcdSupportProductSCIController implements SearchDcdSupportProductSCI {

	@Autowired
	private SearchDcdSupportProductService searchDcdSupportProductService;

	/**
	 * 
	 * <pre>
	 * DCD 지원 상품 조회.
	 * </pre>
	 * 
	 * @param null null
	 * @return String
	 */
	@Override
	public DcdSupportProductRes searchDcdSupportProduct() {
		return this.searchDcdSupportProductService.getDcdSupportProductList();
	}

}
