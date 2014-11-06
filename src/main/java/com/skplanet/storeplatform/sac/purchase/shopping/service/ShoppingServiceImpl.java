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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.helper.MultiMessageSourceAccessor;
import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.purchase.client.order.sci.PurchaseOrderSearchSCI;
import com.skplanet.storeplatform.purchase.client.order.vo.SearchShoppingSpecialCountScReq;
import com.skplanet.storeplatform.purchase.client.order.vo.SearchShoppingSpecialCountScRes;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.PaymentInfo;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.PaymentInfoSacRes;
import com.skplanet.storeplatform.sac.purchase.shopping.repository.ShoppingRepository;
import com.skplanet.storeplatform.sac.purchase.shopping.vo.CouponPublishAvailableSacParam;
import com.skplanet.storeplatform.sac.purchase.shopping.vo.CouponPublishAvailableSacResult;
import com.skplanet.storeplatform.sac.purchase.shopping.vo.CouponStockSacParam;
import com.skplanet.storeplatform.sac.purchase.shopping.vo.CouponStockSacResult;
import com.skplanet.storeplatform.sac.purchase.shopping.vo.CouponUseStatusSacParam;
import com.skplanet.storeplatform.sac.purchase.shopping.vo.CouponUseStatusSacResult;

/**
 * 쇼핑쿠폰 Service Implements.
 * 
 * Updated on : 2014. 2. 3. Updated by : nTels_cswoo81, nTels.
 */
@Service
public class ShoppingServiceImpl implements ShoppingService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ShoppingRepository shoppingRepository;

	@Autowired
	private PurchaseOrderSearchSCI purchaseOrderSearchSCI;

	@Autowired
	private MultiMessageSourceAccessor multiMessageSourceAccessor;

	@Override
	public CouponUseStatusSacResult getCouponUseStatus(CouponUseStatusSacParam couponUseStatusSacParam) {

		return this.shoppingRepository.getCouponUseStatus(couponUseStatusSacParam);

	}

	@Override
	public CouponPublishAvailableSacResult getCouponPublishAvailable(
			CouponPublishAvailableSacParam couponPublishAvailableSacParam) {

		// 특가상품 조회를 위한 처리
		if (StringUtils.isNotBlank(couponPublishAvailableSacParam.getProdId())) {
			// 상품정보조회
			PaymentInfoSacRes response = this.shoppingRepository.searchPaymentInfo(couponPublishAvailableSacParam);

			if (response != null && response.getPaymentInfoList().size() > 0) {
				PaymentInfo product = response.getPaymentInfoList().get(0);

				// 특가상품 구매가능 건수 체크
				if (StringUtils.isNotBlank(product.getSpecialSaleCouponId())) {
					SearchShoppingSpecialCountScReq specialReq = new SearchShoppingSpecialCountScReq();
					specialReq.setTenantId(couponPublishAvailableSacParam.getTenantId());
					specialReq.setUserKey(couponPublishAvailableSacParam.getUserKey());
					specialReq.setDeviceKey(couponPublishAvailableSacParam.getDeviceKey());
					specialReq.setSpecialCouponId(product.getSpecialSaleCouponId());
					specialReq.setSpecialCouponAmt(product.getProdAmt() - product.getSpecialSaleAmt());

					SearchShoppingSpecialCountScRes specialRes = this.purchaseOrderSearchSCI
							.searchShoppingSpecialCount(specialReq);

					if (specialRes.getDayCount() + couponPublishAvailableSacParam.getItemCount() > product
							.getSpecialSaleDayLimit()) {
						throw new StorePlatformException("SAC_PUR_6104");
					}
					if (specialRes.getDayUserCount() + couponPublishAvailableSacParam.getItemCount() > product
							.getSpecialSaleDayLimitPerson()) {
						throw new StorePlatformException("SAC_PUR_6106");
					}
					if (specialRes.getMonthCount() + couponPublishAvailableSacParam.getItemCount() > product
							.getSpecialSaleMonthLimit()) {
						throw new StorePlatformException("SAC_PUR_6105");
					}
					if (specialRes.getMonthUserCount() + couponPublishAvailableSacParam.getItemCount() > product
							.getSpecialSaleMonthLimitPerson()) {
						throw new StorePlatformException("SAC_PUR_6107");
					}
				}

			} else {
				throw new StorePlatformException("SAC_PUR_5101");
			}
		}

		return this.shoppingRepository.getCouponPublishAvailable(couponPublishAvailableSacParam);

	}

	@Override
	public CouponStockSacResult getCouponStock(CouponStockSacParam couponStockSacParam) {

		CouponStockSacResult couponStockSacResult = new CouponStockSacResult();

		CouponPublishAvailableSacParam couponPublishAvailableSacParam = new CouponPublishAvailableSacParam();

		boolean isSpecialCoupon = false;

		// 특가상품 조회를 위한 처리
		if (StringUtils.isNotBlank(couponStockSacParam.getProdId())) {

			couponPublishAvailableSacParam.setTenantId(couponStockSacParam.getTenantId());
			couponPublishAvailableSacParam.setLangCd(couponStockSacParam.getLangCd());
			couponPublishAvailableSacParam.setModel(couponStockSacParam.getModel());
			couponPublishAvailableSacParam.setProdId(couponStockSacParam.getProdId());

			// 상품정보조회
			PaymentInfoSacRes response = this.shoppingRepository.searchPaymentInfo(couponPublishAvailableSacParam);

			if (response != null && response.getPaymentInfoList().size() > 0) {
				PaymentInfo product = response.getPaymentInfoList().get(0);

				couponStockSacResult = new CouponStockSacResult();
				couponStockSacResult.setMaxMonth(product.getSpecialSaleMonthLimit());
				couponStockSacResult.setMaxMonthMdn(product.getSpecialSaleMonthLimitPerson());
				couponStockSacResult.setMaxDay(product.getSpecialSaleDayLimit());
				couponStockSacResult.setMaxDayMdn(product.getSpecialSaleDayLimitPerson());
				couponStockSacResult.setBuyMaxLimit(0);

				// 특가상품 구매가능 건수 체크
				if (StringUtils.isNotBlank(product.getSpecialSaleCouponId())) {
					isSpecialCoupon = true;
					SearchShoppingSpecialCountScReq specialReq = new SearchShoppingSpecialCountScReq();
					specialReq.setTenantId(couponStockSacParam.getTenantId());
					specialReq.setUserKey(couponStockSacParam.getUserKey());
					specialReq.setDeviceKey(couponStockSacParam.getDeviceKey());
					specialReq.setSpecialCouponId(product.getSpecialSaleCouponId());
					specialReq.setSpecialCouponAmt(product.getProdAmt() - product.getSpecialSaleAmt());
				}
			} else {
				throw new StorePlatformException("SAC_PUR_5101");
			}
		}

		// 쿠폰 재고조회
		if (!isSpecialCoupon) {
			couponStockSacResult = this.shoppingRepository.getCouponStock(couponStockSacParam);
		}

		return couponStockSacResult;

	}

}
