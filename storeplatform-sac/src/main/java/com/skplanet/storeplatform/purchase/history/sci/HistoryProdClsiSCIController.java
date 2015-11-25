/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 * 
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.purchase.history.sci;

import org.springframework.beans.factory.annotation.Autowired;

import com.skplanet.storeplatform.framework.integration.bean.LocalSCI;
import com.skplanet.storeplatform.purchase.client.history.sci.HistoryProdClsiSCI;
import com.skplanet.storeplatform.purchase.client.history.vo.HistoryProdClsiScReq;
import com.skplanet.storeplatform.purchase.client.history.vo.HistoryProdClsiScRes;
import com.skplanet.storeplatform.purchase.history.service.HistoryProdClsiScService;

/**
 * 상품별 구매내역 Controller
 * 
 * Updated on : 2013. 12. 10. Updated by : wisestone_mikepark
 */
@LocalSCI
public class HistoryProdClsiSCIController implements HistoryProdClsiSCI {

	@Autowired
	private HistoryProdClsiScService service;

	/**
	 * 상품별 구매내역 조회 기능을 제공한다(보유상품).
	 * 
	 * @param request
	 *            구매내역요청
	 * @return HistoryProdClsiScRes
	 */
	@Override
	public HistoryProdClsiScRes searchHistoryProdClsiList(HistoryProdClsiScReq request) {
		return this.service.searchHistoryProdClsiList(request);

	}
}
