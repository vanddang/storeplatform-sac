/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.shopping.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.external.client.shopping.vo.BizCouponItem;
import com.skplanet.storeplatform.external.client.shopping.vo.BizCouponPublish;
import com.skplanet.storeplatform.external.client.shopping.vo.BizCouponReq;
import com.skplanet.storeplatform.external.client.shopping.vo.BizCouponRes;
import com.skplanet.storeplatform.purchase.client.shopping.sci.ShoppingAsyncSCI;
import com.skplanet.storeplatform.purchase.client.shopping.vo.ShoppingAsyncItemSc;
import com.skplanet.storeplatform.purchase.client.shopping.vo.ShoppingAsyncListSc;
import com.skplanet.storeplatform.purchase.client.shopping.vo.ShoppingAsyncReq;
import com.skplanet.storeplatform.purchase.client.shopping.vo.ShoppingAsyncRes;

/**
 * 쇼핑쿠폰 Service Implements.
 * 
 * Updated on : 2014. 2. 3. Updated by : ntels_yjw, nTels.
 */
@Service
public class ShoppingAsyncServiceImpl implements ShoppingAsyncService {

	@Autowired
	private ShoppingAsyncSCI shoppingAsyncSCI;

	@Override
	public BizCouponRes getShoppingAsync(BizCouponReq request) {

		ShoppingAsyncReq reqSc = new ShoppingAsyncReq();
		ShoppingAsyncRes resSc = new ShoppingAsyncRes();

		ShoppingAsyncListSc listSc;
		List<ShoppingAsyncListSc> prchsList = new ArrayList<ShoppingAsyncListSc>();

		ShoppingAsyncItemSc item;
		List<ShoppingAsyncItemSc> itemList = new ArrayList<ShoppingAsyncItemSc>();

		System.out.println("" + request.getXmlData().getPublish().size());
		System.out.println("" + request.getXmlData().getPublish().get(0).getItems().size());

		for (BizCouponPublish obj : request.getXmlData().getPublish()) {
			listSc = new ShoppingAsyncListSc();
			itemList = new ArrayList<ShoppingAsyncItemSc>();

			listSc.setPrchsId(obj.getPrchsId());
			listSc.setAvail_startdate(obj.getAvail_startdate());
			listSc.setAvail_enddate(obj.getAvail_enddate());
			listSc.setMdn(obj.getMdn());
			listSc.setMdn2(obj.getMdn2());

			for (BizCouponItem objItem : obj.getItems()) {
				item = new ShoppingAsyncItemSc();

				item.setItemCode(objItem.getItemCode());
				item.setPublishCode(objItem.getPublishCode());
				item.setShippingUrl(objItem.getShippingUrl());
				item.setExtraData(objItem.getExtraData());

				itemList.add(item);
			}
			listSc.setItems(itemList);

			prchsList.add(listSc);

		}

		reqSc.setPublish(prchsList);

		System.out.println("###" + reqSc.getPublish().size());
		System.out.println("###" + reqSc.getPublish().get(0).getItems().size());

		resSc = this.shoppingAsyncSCI.updateShoppingAsync(reqSc);

		return new BizCouponRes();

	}

}
