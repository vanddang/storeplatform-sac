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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.pdp.sentinel.shuttle.TLogSentinelShuttle;
import com.skplanet.storeplatform.external.client.shopping.vo.BizCouponItem;
import com.skplanet.storeplatform.external.client.shopping.vo.BizCouponPublish;
import com.skplanet.storeplatform.external.client.shopping.vo.BizCouponReq;
import com.skplanet.storeplatform.framework.core.util.log.TLogUtil;
import com.skplanet.storeplatform.framework.core.util.log.TLogUtil.ShuttleSetter;
import com.skplanet.storeplatform.purchase.client.shopping.sci.ShoppingAsyncSCI;
import com.skplanet.storeplatform.purchase.client.shopping.vo.ShoppingAsyncItemSc;
import com.skplanet.storeplatform.purchase.client.shopping.vo.ShoppingAsyncListSc;
import com.skplanet.storeplatform.purchase.client.shopping.vo.ShoppingAsyncReq;
import com.skplanet.storeplatform.purchase.client.shopping.vo.ShoppingAsyncRes;
import com.skplanet.storeplatform.sac.purchase.constant.PurchaseConstants;
//import com.skplanet.storeplatform.external.client.shopping.vo.BizCouponItem;
//import com.skplanet.storeplatform.external.client.shopping.vo.BizCouponPublish;
//import com.skplanet.storeplatform.external.client.shopping.vo.BizCouponReq;
//import com.skplanet.storeplatform.external.client.shopping.vo.BizCouponRes;

/**
 * 쇼핑쿠폰 Service Implements.
 * 
 * Updated on : 2014. 2. 3. Updated by : ntels_yjw, nTels.
 */
@Service
public class ShoppingAsyncServiceImpl implements ShoppingAsyncService {

	private static final Logger logger = LoggerFactory.getLogger(ShoppingAsyncServiceImpl.class);

	@Autowired
	private ShoppingAsyncSCI shoppingAsyncSCI;

	@Override
	public int getShoppingAsync(BizCouponReq request) {

		ShoppingAsyncReq reqSc = new ShoppingAsyncReq();
		// ShoppingAsyncRes resSc = new ShoppingAsyncRes();

		ShoppingAsyncListSc listSc = null;
		List<ShoppingAsyncListSc> prchsList = new ArrayList<ShoppingAsyncListSc>();

		ShoppingAsyncItemSc item = null;
		List<ShoppingAsyncItemSc> itemList = null;

		for (BizCouponPublish obj : request.getXmlData().getPublish()) {
			listSc = new ShoppingAsyncListSc();
			itemList = new ArrayList<ShoppingAsyncItemSc>();

			listSc.setPrchsId(obj.getPrchsId());

			logger.info("### Biz coupon :: PrchsId : " + obj.getPrchsId());

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

				/******************************
				 * TLOG Setting Start
				 ******************************/
				final String purchase_id = obj.getPrchsId();
				final String use_start_time = obj.getAvail_startdate() + "000";
				final String use_end_time = obj.getAvail_enddate() + "000";
				final String coupon_publish_code = objItem.getPublishCode();
				final String coupon_code = request.getCouponCode();
				final String coupon_item_code = objItem.getItemCode();

				new TLogUtil().log(new ShuttleSetter() {
					@Override
					public void customize(TLogSentinelShuttle shuttle) {
						shuttle.log_id(PurchaseConstants.INTERFACE_ID_TL_SAC_PUR_0006).purchase_id(purchase_id)
								.use_start_time(use_start_time).use_end_time(use_end_time)
								.download_expired_time(use_end_time).coupon_publish_code(coupon_publish_code)
								.coupon_code(coupon_code).coupon_item_code(coupon_item_code).result_code("SUCC")
								.result_message("");
					}
				});

				/******************************
				 * TLOG Setting End
				 ******************************/

			}
			listSc.setItems(itemList);

			prchsList.add(listSc);

		}

		reqSc.setPublish(prchsList);

		ShoppingAsyncRes res = this.shoppingAsyncSCI.updateShoppingAsync(reqSc);

		return res.getCount();

	}

}
