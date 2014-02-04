/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.shopping.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.skplanet.storeplatform.external.client.shopping.sci.ShoppingSCI;
import com.skplanet.storeplatform.external.client.shopping.vo.CouponUseStatusDetailEcRes;
import com.skplanet.storeplatform.external.client.shopping.vo.CouponUseStatusEcReq;
import com.skplanet.storeplatform.external.client.shopping.vo.CouponUseStatusEcRes;
import com.skplanet.storeplatform.sac.purchase.shopping.vo.CouponUseStatusDetailSacResult;
import com.skplanet.storeplatform.sac.purchase.shopping.vo.CouponUseStatusSacParam;
import com.skplanet.storeplatform.sac.purchase.shopping.vo.CouponUseStatusSacResult;

/**
 * 쇼핑쿠폰 Repository Implements.
 * 
 * Updated on : 2014. 2. 3. Updated by : nTels_cswoo81, nTels.
 */
@Component
public class ShoppingRepositoryImpl implements ShoppingRepository {

	@Autowired
	private ShoppingSCI shoppingSCI;

	@Override
	public CouponUseStatusSacResult getCouponUseStatus(CouponUseStatusSacParam couponUseStatusSacParam) {

		CouponUseStatusEcReq couponUseStatusEcReq = this.convertReq(couponUseStatusSacParam);

		CouponUseStatusEcRes couponUseStatusEcRes = this.shoppingSCI.getCouponUseStatus(couponUseStatusEcReq);

		return this.convertRes(couponUseStatusEcRes);

	}

	private CouponUseStatusEcReq convertReq(CouponUseStatusSacParam couponUseStatusSacParam) {

		CouponUseStatusEcReq couponUseStatusEcReq = new CouponUseStatusEcReq();

		couponUseStatusEcReq.setPrchsId(couponUseStatusSacParam.getPrchsId());
		couponUseStatusEcReq.setCouponPublishCode(couponUseStatusSacParam.getCpnPublishCd());

		return couponUseStatusEcReq;

	}

	private CouponUseStatusSacResult convertRes(CouponUseStatusEcRes couponUseStatusEcRes) {

		CouponUseStatusSacResult couponUseStatusSacResult = new CouponUseStatusSacResult();
		List<CouponUseStatusDetailSacResult> cpnUseStatusList = new ArrayList<CouponUseStatusDetailSacResult>();
		for (CouponUseStatusDetailEcRes couponUseStatusDetailEcRes : couponUseStatusEcRes.getCouponUseStatusList()) {
			CouponUseStatusDetailSacResult couponUseStatusDetailSacResult = new CouponUseStatusDetailSacResult();
			couponUseStatusDetailSacResult.setCpnPublishCd(couponUseStatusDetailEcRes.getCouponPublishCode());
			couponUseStatusDetailSacResult.setCpnUseStatusCd(couponUseStatusDetailEcRes.getCouponStatus());

			cpnUseStatusList.add(couponUseStatusDetailSacResult);
		}

		couponUseStatusSacResult.setCpnUseStatusList(cpnUseStatusList);

		return couponUseStatusSacResult;

	}

}
