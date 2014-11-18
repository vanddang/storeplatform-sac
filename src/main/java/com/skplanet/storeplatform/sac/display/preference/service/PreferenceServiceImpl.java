/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.preference.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.sac.client.display.vo.best.BestAppSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.best.BestAppSacRes;
import com.skplanet.storeplatform.sac.client.display.vo.preference.ListProductReq;
import com.skplanet.storeplatform.sac.client.display.vo.preference.ListProductRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.feature.best.service.BestAppService;

/**
 * <p>
 * PreferenceServiceImpl (더미 데이터)
 * </p>
 * Updated on : 2014. 10. 31 Updated by : 서대영, SK 플래닛.
 */
@Service
public class PreferenceServiceImpl implements PreferenceService {
	
	@Autowired
	private BestAppService bestAppService;

	@Override
	public ListProductRes listProduct(ListProductReq req, SacRequestHeader header) {
		// Hard coding
		BestAppSacReq bestAppReq = new BestAppSacReq();
		bestAppReq.setListId("ADM000000012");
		bestAppReq.setOffset(1);
		bestAppReq.setCount(5);
		BestAppSacRes bestAppRes = bestAppService.searchBestAppList(header, bestAppReq);
		
		CommonResponse commonResponse = bestAppRes.getCommonResponse();
		List<Product> productList = bestAppRes.getProductList();
		
		ListProductRes res = new ListProductRes();
		res.setCommonResponse(commonResponse);
		res.setProductList(productList);
		
		return res;
	}

}
