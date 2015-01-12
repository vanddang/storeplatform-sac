/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.shopping.sci;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;

import com.skplanet.storeplatform.framework.integration.bean.LocalSCI;
import com.skplanet.storeplatform.sac.client.internal.purchase.shopping.sci.ShoppingInternalSCI;
import com.skplanet.storeplatform.sac.client.internal.purchase.shopping.vo.CouponUseStatusDetailSacInRes;
import com.skplanet.storeplatform.sac.client.internal.purchase.shopping.vo.CouponUseStatusSacInReq;
import com.skplanet.storeplatform.sac.client.internal.purchase.shopping.vo.CouponUseStatusSacInRes;
import com.skplanet.storeplatform.sac.purchase.shopping.service.ShoppingService;
import com.skplanet.storeplatform.sac.purchase.shopping.vo.CouponUseStatusDetailSacResult;
import com.skplanet.storeplatform.sac.purchase.shopping.vo.CouponUseStatusSacParam;
import com.skplanet.storeplatform.sac.purchase.shopping.vo.CouponUseStatusSacResult;

/**
 * Class 설명
 * 
 * Updated on : 2014. 2. 13. Updated by : nTels_cswoo81, nTels.
 */
@LocalSCI
public class ShoppingInternalSCIController implements ShoppingInternalSCI {

	@Autowired
	private ShoppingService shoppingService;

	@Override
	public CouponUseStatusSacInRes getCouponUseStatus(@Validated CouponUseStatusSacInReq couponUseStatusSacInReq) {

		CouponUseStatusSacParam couponUseStatusSacParam = this.convertReqForGetCouponUseStatus(couponUseStatusSacInReq);

		CouponUseStatusSacResult couponUseStatusSacResult = this.shoppingService
				.getCouponUseStatus(couponUseStatusSacParam);

		return this.convertResForGetCouponUseStatus(couponUseStatusSacResult);

	}

	private CouponUseStatusSacParam convertReqForGetCouponUseStatus(CouponUseStatusSacInReq couponUseStatusSacInReq) {

		CouponUseStatusSacParam couponUseStatusSacParam = new CouponUseStatusSacParam();

		couponUseStatusSacParam.setTenantId(couponUseStatusSacInReq.getTenantId());
		couponUseStatusSacParam.setSystemId(couponUseStatusSacInReq.getSystemId());
		couponUseStatusSacParam.setUserKey(couponUseStatusSacInReq.getUserKey());
		couponUseStatusSacParam.setDeviceKey(couponUseStatusSacInReq.getDeviceKey());
		couponUseStatusSacParam.setPrchsId(couponUseStatusSacInReq.getPrchsId());
		couponUseStatusSacParam.setCpnPublishCd(couponUseStatusSacInReq.getCpnPublishCd());

		return couponUseStatusSacParam;

	}

	private CouponUseStatusSacInRes convertResForGetCouponUseStatus(CouponUseStatusSacResult couponUseStatusSacResult) {

		CouponUseStatusSacInRes couponUseStatusSacInRes = new CouponUseStatusSacInRes();

		List<CouponUseStatusDetailSacInRes> couponUseStatusDetailList = new ArrayList<CouponUseStatusDetailSacInRes>();
		for (CouponUseStatusDetailSacResult couponUseStatusDetailSacResult : couponUseStatusSacResult
				.getCpnUseStatusList()) {
			CouponUseStatusDetailSacInRes couponUseStatusDetailSacInRes = new CouponUseStatusDetailSacInRes();
			couponUseStatusDetailSacInRes.setCpnPublishCd(couponUseStatusDetailSacResult.getCpnPublishCd());
			couponUseStatusDetailSacInRes.setCpnUseStatusCd(couponUseStatusDetailSacResult.getCpnUseStatusCd());

			couponUseStatusDetailList.add(couponUseStatusDetailSacInRes);
		}

		couponUseStatusSacInRes.setCpnUseStatusList(couponUseStatusDetailList);

		return couponUseStatusSacInRes;

	}

}
