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
import com.skplanet.storeplatform.framework.core.exception.vo.ErrorInfo;
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

		CouponStockSacResult couponStockSacResult = null;
		CouponPublishAvailableSacResult couponPublishAvailableSacResult = new CouponPublishAvailableSacResult();

		boolean isSpecialCoupon = false;

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
						couponPublishAvailableSacResult.setStatusCd("SAC_PUR_6104");
						couponPublishAvailableSacResult.setStatusMsg(this.multiMessageSourceAccessor
								.getMessage("SAC_PUR_6104"));
					}
					if (specialRes.getDayUserCount() + couponPublishAvailableSacParam.getItemCount() > product
							.getSpecialSaleDayLimitPerson()) {

						couponPublishAvailableSacResult.setStatusCd("SAC_PUR_6106");
						couponPublishAvailableSacResult.setStatusMsg(this.multiMessageSourceAccessor
								.getMessage("SAC_PUR_6106"));
					}
					if (specialRes.getMonthCount() + couponPublishAvailableSacParam.getItemCount() > product
							.getSpecialSaleMonthLimit()) {
						couponPublishAvailableSacResult.setStatusCd("SAC_PUR_6105");
						couponPublishAvailableSacResult.setStatusMsg(this.multiMessageSourceAccessor
								.getMessage("SAC_PUR_6105"));
					}
					if (specialRes.getMonthUserCount() + couponPublishAvailableSacParam.getItemCount() > product
							.getSpecialSaleMonthLimitPerson()) {

						couponPublishAvailableSacResult.setStatusCd("SAC_PUR_6107");
						couponPublishAvailableSacResult.setStatusMsg(this.multiMessageSourceAccessor
								.getMessage("SAC_PUR_6107"));
					}

					couponStockSacResult = new CouponStockSacResult();
					couponStockSacResult.setMaxMonth(product.getSpecialSaleMonthLimit());
					couponStockSacResult.setMaxMonthMdn(product.getSpecialSaleMonthLimitPerson());
					couponStockSacResult.setMaxDay(product.getSpecialSaleDayLimit());
					couponStockSacResult.setMaxDayMdn(product.getSpecialSaleDayLimitPerson());
					couponStockSacResult.setBuyMaxLimit(0);
					isSpecialCoupon = true;

				}
			} else {
				couponPublishAvailableSacResult.setStatusCd("SAC_PUR_5101");
				couponPublishAvailableSacResult
						.setStatusMsg(this.multiMessageSourceAccessor.getMessage("SAC_PUR_5101"));
			}
		}

		// 쿠폰 재고조회
		if (!isSpecialCoupon) {
			try {
				CouponStockSacParam couponStockSacParam = new CouponStockSacParam();
				couponStockSacParam.setCouponCode(couponPublishAvailableSacParam.getCouponCode());
				couponStockSacParam.setItemCode(couponPublishAvailableSacParam.getItemCode());
				couponStockSacParam.setMdn(couponPublishAvailableSacParam.getMdn());
				couponStockSacResult = this.shoppingRepository.getCouponStock(couponStockSacParam);
			} catch (Exception e) {

				this.logger.info("## [ShoppingService.getCouponPublishAvailable] getCouponStock Exceotion ");
				couponStockSacResult = new CouponStockSacResult();
			}
		}

		try {

			if (StringUtils.isBlank(couponPublishAvailableSacResult.getStatusCd())) {
				// 쿠폰발급가능여부조회
				couponPublishAvailableSacResult = this.shoppingRepository
						.getCouponPublishAvailable(couponPublishAvailableSacParam);
			}

		} catch (StorePlatformException e) {

			ErrorInfo errorInfo = e.getErrorInfo();
			String errorMsg = "";

			this.logger.info("## [ShoppingService.getCouponPublishAvailable] getCouponPublishAvailable Exceotion : "
					+ errorInfo.getCode());

			if (StringUtils.isBlank(errorInfo.getMessage())) {
				errorMsg = this.multiMessageSourceAccessor.getMessage(errorInfo.getCode(), errorInfo.getArgs());
			} else {
				errorMsg = errorInfo.getMessage();
			}

			couponPublishAvailableSacResult.setStatusCd(errorInfo.getCode());
			couponPublishAvailableSacResult.setStatusMsg(errorMsg);

		} catch (Exception e) {

			this.logger
					.info("## [ShoppingService.getCouponPublishAvailable] getCouponPublishAvailable Exceotion : SAC_PUR_9999");

			couponPublishAvailableSacResult.setStatusCd("SAC_PUR_9999");
			couponPublishAvailableSacResult.setStatusMsg(this.multiMessageSourceAccessor.getMessage("SAC_PUR_9999"));
		}

		couponPublishAvailableSacResult.setSaleDayLimit(couponStockSacResult.getMaxDay());
		couponPublishAvailableSacResult.setSaleDayLimitPerson(couponStockSacResult.getMaxDayMdn());
		couponPublishAvailableSacResult.setSaleMonthLimit(couponStockSacResult.getMaxMonth());
		couponPublishAvailableSacResult.setSaleMonthLimitPerson(couponStockSacResult.getMaxMonthMdn());
		couponPublishAvailableSacResult.setSaleOnceLimit(couponStockSacResult.getBuyMaxLimit());

		return couponPublishAvailableSacResult;

	}
}
