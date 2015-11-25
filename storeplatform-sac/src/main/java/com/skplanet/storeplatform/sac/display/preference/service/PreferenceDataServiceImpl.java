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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.external.client.isf.sci.ISFSCI;
import com.skplanet.storeplatform.external.client.isf.vo.IsfCategoryV2EcReq;
import com.skplanet.storeplatform.external.client.isf.vo.IsfCategoryV2EcRes;
import com.skplanet.storeplatform.external.client.isf.vo.IsfV2OfferObjectsEcRes;
import com.skplanet.storeplatform.sac.client.display.vo.preference.ListProductReq;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.stat.service.StatMemberItemService;

/**
 * <p>
 * PreferenceDataServiceImpl
 * </p>
 * Updated on : 2014. 11. 28 Updated by : 서대영, SK 플래닛.
 */
@Service
public class PreferenceDataServiceImpl implements PreferenceDataService {
	
	@Autowired
	private ISFSCI isfSCI;
	
	@Autowired
	private StatMemberItemService itemService;
	
	public List<IsfV2OfferObjectsEcRes> getProductList(ListProductReq req) {
		IsfCategoryV2EcReq ecReq = new IsfCategoryV2EcReq();
		ecReq.setUserKey(req.getUserKey());
		ecReq.setAdultYN(req.getAdultYn());
		ecReq.setCateId(req.getMenuId());
		
		IsfCategoryV2EcRes ecRes = isfSCI.getCategoryDetailV2(ecReq);
		if (ecRes == null || ecRes.getData() == null) {
			return Collections.emptyList();
		}
		if (CollectionUtils.isEmpty(ecRes.getData().getOfferObjects())) {
			return Collections.emptyList();
		}
		
		return ecRes.getData().getOfferObjects();
	}

	@Override
	public List<Product> getProductList(List<IsfV2OfferObjectsEcRes> offerList, SacRequestHeader header, int count) {
		if (offerList.size() < count) {
			count = offerList.size();
		}
		
		List<Product> productList = new ArrayList<Product>();
		for (int i = 0; i < count; i++) {
			String itemId = offerList.get(i).getItemId();
			Product prdouct = itemService.findProd(itemId, header);
			productList.add(prdouct);
		}

		return productList;
	}
	
}
