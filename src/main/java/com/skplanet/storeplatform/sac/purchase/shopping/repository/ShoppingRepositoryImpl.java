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
import com.skplanet.storeplatform.external.client.shopping.vo.CouponPublishAvailableEcReq;
import com.skplanet.storeplatform.external.client.shopping.vo.CouponPublishAvailableEcRes;
import com.skplanet.storeplatform.external.client.shopping.vo.CouponUseStatusDetailEcRes;
import com.skplanet.storeplatform.external.client.shopping.vo.CouponUseStatusEcReq;
import com.skplanet.storeplatform.external.client.shopping.vo.CouponUseStatusEcRes;
import com.skplanet.storeplatform.sac.purchase.shopping.vo.CouponPublishAvailableSacParam;
import com.skplanet.storeplatform.sac.purchase.shopping.vo.CouponPublishAvailableSacResult;
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

		CouponUseStatusEcReq couponUseStatusEcReq = this.convertReqForGetCouponUseStatus(couponUseStatusSacParam);

		CouponUseStatusEcRes couponUseStatusEcRes = this.shoppingSCI.getCouponUseStatus(couponUseStatusEcReq);

		return this.convertResForGetCouponUseStatus(couponUseStatusEcRes);

	}

	@Override
	public CouponPublishAvailableSacResult getCouponPublishAvailable(
			CouponPublishAvailableSacParam couponPublishAvailableSacParam) {

		CouponPublishAvailableEcReq couponPublishAvailableEcReq = this
				.convertReqForGetCouponPublishAvailable(couponPublishAvailableSacParam);

		CouponPublishAvailableEcRes couponPublishAvailableEcRes = this.shoppingSCI
				.getCouponPublishAvailable(couponPublishAvailableEcReq);

		return this.convertResForGetCouponPublishAvailable(couponPublishAvailableEcRes);

	}

	/**
	 * 
	 * <pre>
	 * convertReqForGetCouponUseStatus.
	 * </pre>
	 * 
	 * @param couponUseStatusSacParam
	 *            CouponUseStatusEcReq
	 * @return CouponUseStatusEcReq
	 */
	private CouponUseStatusEcReq convertReqForGetCouponUseStatus(CouponUseStatusSacParam couponUseStatusSacParam) {

		CouponUseStatusEcReq couponUseStatusEcReq = new CouponUseStatusEcReq();

		couponUseStatusEcReq.setPrchsId(couponUseStatusSacParam.getPrchsId());
		couponUseStatusEcReq.setCouponPublishCode(couponUseStatusSacParam.getCpnPublishCd());

		return couponUseStatusEcReq;

	}

	/**
	 * 
	 * <pre>
	 * convertResForGetCouponUseStatus.
	 * </pre>
	 * 
	 * @param couponUseStatusEcRes
	 *            couponUseStatusEcRes
	 * @return CouponUseStatusSacResult
	 */
	private CouponUseStatusSacResult convertResForGetCouponUseStatus(CouponUseStatusEcRes couponUseStatusEcRes) {

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

	/**
	 * 
	 * <pre>
	 * convertReqForGetCouponPublishAvailable.
	 * </pre>
	 * 
	 * @param couponPublishAvailableSacParam
	 *            couponPublishAvailableSacParam
	 * @return CouponPublishAvailableEcReq
	 */
	private CouponPublishAvailableEcReq convertReqForGetCouponPublishAvailable(
			CouponPublishAvailableSacParam couponPublishAvailableSacParam) {

		CouponPublishAvailableEcReq couponPublishAvailableEcReq = new CouponPublishAvailableEcReq();

		couponPublishAvailableEcReq.setCouponCode(couponPublishAvailableSacParam.getCouponCode());
		couponPublishAvailableEcReq.setItemCode(couponPublishAvailableSacParam.getItemCode());
		couponPublishAvailableEcReq.setItemCount(couponPublishAvailableSacParam.getItemCount());
		couponPublishAvailableEcReq.setMdn(couponPublishAvailableSacParam.getMdn());

		return couponPublishAvailableEcReq;
	}

	/**
	 * 
	 * <pre>
	 * convertResForGetCouponPublishAvailable.
	 * </pre>
	 * 
	 * @param couponPublishAvailableEcRes
	 *            couponPublishAvailableEcRes
	 * @return CouponPublishAvailableSacResult
	 */
	private CouponPublishAvailableSacResult convertResForGetCouponPublishAvailable(
			CouponPublishAvailableEcRes couponPublishAvailableEcRes) {

		CouponPublishAvailableSacResult couponPublishAvailableSacResult = new CouponPublishAvailableSacResult();

		couponPublishAvailableSacResult.setStatusCd(couponPublishAvailableEcRes.getStatusCd());
		couponPublishAvailableSacResult.setStatusMsg(couponPublishAvailableEcRes.getStatusMsg());

		return couponPublishAvailableSacResult;
	}

}
