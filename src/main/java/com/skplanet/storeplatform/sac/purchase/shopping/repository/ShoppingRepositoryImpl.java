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

import com.skplanet.storeplatform.external.client.shopping.sci.ShoppingSCI;
import com.skplanet.storeplatform.external.client.shopping.vo.*;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.purchase.client.cancel.sci.PurchaseCancelSCI;
import com.skplanet.storeplatform.purchase.client.cancel.vo.PurchaseScReq;
import com.skplanet.storeplatform.purchase.client.cancel.vo.PurchaseScRes;
import com.skplanet.storeplatform.purchase.client.common.vo.PrchsDtl;
import com.skplanet.storeplatform.purchase.client.shopping.sci.ShoppingScSCI;
import com.skplanet.storeplatform.purchase.client.shopping.vo.ShoppingScReq;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.sci.PaymentInfoSCI;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.PaymentInfoSacReq;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.PaymentInfoSacRes;
import com.skplanet.storeplatform.sac.purchase.constant.PurchaseConstants;
import com.skplanet.storeplatform.sac.purchase.shopping.vo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 쇼핑쿠폰 Repository Implements.
 * 
 * Updated on : 2014. 2. 3. Updated by : nTels_cswoo81, nTels.
 */
@Component
public class ShoppingRepositoryImpl implements ShoppingRepository {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private PurchaseCancelSCI purchaseCancelSCI;

	@Autowired
	private ShoppingSCI shoppingSCI;

	@Autowired
	private ShoppingScSCI shoppingScSCI;

	@Autowired
	private PaymentInfoSCI paymentInfoSCI;

	@Override
	public CouponUseStatusSacResult getCouponUseStatus(CouponUseStatusSacParam couponUseStatusSacParam) {

		CouponUseStatusEcReq couponUseStatusEcReq = this.convertReqForGetCouponUseStatus(couponUseStatusSacParam);

		CouponUseStatusEcRes couponUseStatusEcRes = this.shoppingSCI.getCouponUseStatus(couponUseStatusEcReq);

		ShoppingScReq shoppingScReq = new ShoppingScReq();
		shoppingScReq.setTenantId(couponUseStatusSacParam.getTenantId());
		shoppingScReq.setPrchsId(couponUseStatusSacParam.getPrchsId());
		shoppingScReq.setSystemId(couponUseStatusSacParam.getSystemId());

		for (CouponUseStatusDetailEcRes couponUseStatusDetailEcRes : couponUseStatusEcRes.getCouponUseStatusList()) {
			shoppingScReq.setCpnPublishCd(couponUseStatusDetailEcRes.getCouponPublishCode());
			shoppingScReq.setCpnUseStatusCd(couponUseStatusDetailEcRes.getCouponStatus());
			try {
				this.shoppingScSCI.updatePrchsDtl(shoppingScReq);
			} catch (Exception e) {
				this.logger.info("### ShoppingRepositoryImpl.getCouponUseStatus PrchsDtl DB Update Fail");
			}
		}

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

	@Override
	public CouponPublishAvailableSacV2Result getCouponPublishAvailableV2(
			CouponPublishAvailableSacV2Param couponPublishAvailableSacParam) {

		CouponPublishAvailableEcV2Req couponPublishAvailableEcReq = this
				.convertReqForGetCouponPublishAvailableV2(couponPublishAvailableSacParam);

		CouponPublishAvailableEcV2Res couponPublishAvailableEcRes = this.shoppingSCI
				.getCouponPublishAvailableV2(couponPublishAvailableEcReq);

		return this.convertResForGetCouponPublishAvailableV2(couponPublishAvailableEcRes);

	}

	// @Override
	// public CouponStockSacResult getCouponStock(CouponStockSacParam couponStockSacParam) {
	//
	// CouponStockEcReq couponStockEcReq = this.convertReqForGetCouponStock(couponStockSacParam);
	//
	// CouponStockEcRes couponStockEcRes = this.shoppingSCI.getCouponStock(couponStockEcReq);
	//
	// return this.convertResForGetCouponStock(couponStockEcRes);
	//
	// }

	@Override
	public PaymentInfoSacRes searchPaymentInfo(CouponPublishAvailableSacParam couponPublishAvailableSacParam) {

		List<String> prodIdList = new ArrayList<String>();
		prodIdList.add(couponPublishAvailableSacParam.getProdId());

		PaymentInfoSacReq req = new PaymentInfoSacReq();
		req.setTenantId(couponPublishAvailableSacParam.getTenantId());
		req.setLangCd(couponPublishAvailableSacParam.getLangCd());
		req.setDeviceModelCd(couponPublishAvailableSacParam.getModel());
		req.setProdIdList(prodIdList);

		return this.paymentInfoSCI.searchPaymentInfo(req);

	}

	@Override
	public CouponRestoreStatusSacResult restoreCouponStatus(CouponRestoreStatusSacParam couponRestoreStatusSacParam) {

		CouponRestoreStatusEcReq couponRestoreStatusEcReq = this
				.convertReqForRestoreCouponStatus(couponRestoreStatusSacParam);

		CouponRestoreStatusEcRes couponUseStatusEcRes = this.shoppingSCI.restoreCouponStatus(couponRestoreStatusEcReq);

		return this.convertResForGetRestoreCouponStatus(couponUseStatusEcRes);
	}

	private CouponRestoreStatusSacResult convertResForGetRestoreCouponStatus(
			CouponRestoreStatusEcRes couponUseStatusEcRes) {
		CouponRestoreStatusSacResult couponRestoreStatusSacResult = new CouponRestoreStatusSacResult();

		couponRestoreStatusSacResult.setResultCd(couponUseStatusEcRes.getResultCd());
		couponRestoreStatusSacResult.setResultMsg(couponUseStatusEcRes.getResultMsg());

		return couponRestoreStatusSacResult;
	}

	private CouponRestoreStatusEcReq convertReqForRestoreCouponStatus(
			CouponRestoreStatusSacParam couponRestoreStatusSacParam) {
		CouponRestoreStatusEcReq couponRestoreStatusEcReq = new CouponRestoreStatusEcReq();

		couponRestoreStatusEcReq.setPrchsId(couponRestoreStatusSacParam.getPrchsId());

		return couponRestoreStatusEcReq;
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

		// 구매상세 내역을 조회해 온다.
		PurchaseScReq purchaseScReq = new PurchaseScReq();
		purchaseScReq.setTenantId(couponUseStatusSacParam.getTenantId());
		purchaseScReq.setSystemId(couponUseStatusSacParam.getSystemId());
		purchaseScReq.setUserKey(couponUseStatusSacParam.getUserKey());
		purchaseScReq.setDeviceKey(couponUseStatusSacParam.getDeviceKey());
		purchaseScReq.setPrchsId(couponUseStatusSacParam.getPrchsId());
		PurchaseScRes purchaseScRes = this.purchaseCancelSCI.getPurchase(purchaseScReq);

		if (purchaseScRes != null && purchaseScRes.getPrchsDtlList() != null
				&& purchaseScRes.getPrchsDtlList().size() > 0) {
			// AS-IS 쇼핑쿠폰 선물일 경우 구매 ID를 선물구매ID로 변경한다.
			couponUseStatusEcReq.setPrchsId(purchaseScRes.getPrchsDtlList().get(0).getCouponCmsPrchsId());

			if (StringUtils.equals(PurchaseConstants.PRCHS_REQ_PATH_BIZ_COUPON, purchaseScRes.getPrchsDtlList().get(0)
					.getPrchsReqPathCd())
					&& (StringUtils.isNumeric(purchaseScRes.getPrchsDtlList().get(0).getPrchsId()) && purchaseScRes
							.getPrchsDtlList().get(0).getPrchsId().compareTo("140000") > 0)) {
				// BizCoupon이면서 TOBE이면
				// BizCoupon이면 쿠폰번호가 필수!
				if (StringUtils.isBlank(couponUseStatusEcReq.getCouponPublishCode())) {
					throw new StorePlatformException("SAC_PUR_8151");
				}
				// prchsId + prchsDtlId
				Boolean isCpnPublishEquals = false;
				for (PrchsDtl prchsDtl : purchaseScRes.getPrchsDtlList()) {
					if (StringUtils.equals(couponUseStatusEcReq.getCouponPublishCode(), prchsDtl.getCpnPublishCd())) {
						couponUseStatusEcReq.setPrchsId(prchsDtl.getPrchsId()
								+ StringUtils.leftPad(prchsDtl.getPrchsDtlId().toString(), 4, "0"));
						isCpnPublishEquals = true;
					}
				}
				if (!isCpnPublishEquals) {
					throw new StorePlatformException("SAC_PUR_8152");
				}

			}

		}

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
		couponPublishAvailableEcReq.setGiftFlag(couponPublishAvailableSacParam.getGiftFlag());

		return couponPublishAvailableEcReq;
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
	private CouponPublishAvailableEcV2Req convertReqForGetCouponPublishAvailableV2(
			CouponPublishAvailableSacV2Param couponPublishAvailableSacParam) {

		CouponPublishAvailableEcV2Req couponPublishAvailableEcReq = new CouponPublishAvailableEcV2Req();

		couponPublishAvailableEcReq.setCouponCode(couponPublishAvailableSacParam.getCouponCode());
		couponPublishAvailableEcReq.setItemCode(couponPublishAvailableSacParam.getItemCode());
		couponPublishAvailableEcReq.setItemCount(couponPublishAvailableSacParam.getItemCount());
		couponPublishAvailableEcReq.setMdn(couponPublishAvailableSacParam.getMdn());
		couponPublishAvailableEcReq.setGiftFlag(couponPublishAvailableSacParam.getGiftFlag());

		return couponPublishAvailableEcReq;
	}

	/**
	 * 
	 * <pre>
	 * convertReqForGetCouponPublishAvailable.
	 * </pre>
	 * 
	 * @param couponStockSacParam
	 *            couponPublishAvailableSacParam
	 * @return CouponPublishAvailableEcReq
	 */
	private CouponStockEcReq convertReqForGetCouponStock(CouponStockSacParam couponStockSacParam) {

		CouponStockEcReq couponStockEcReq = new CouponStockEcReq();

		couponStockEcReq.setCouponCode(couponStockSacParam.getCouponCode());
		couponStockEcReq.setItemCode(couponStockSacParam.getItemCode());
		couponStockEcReq.setItemCount(couponStockSacParam.getItemCount());
		couponStockEcReq.setMdn(couponStockSacParam.getMdn());
		couponStockEcReq.setGiftFlag(couponStockSacParam.getGiftFlag());

		return couponStockEcReq;
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
	private CouponPublishAvailableSacV2Result convertResForGetCouponPublishAvailableV2(
			CouponPublishAvailableEcV2Res couponPublishAvailableEcRes) {

		CouponPublishAvailableSacV2Result couponPublishAvailableSacResult = new CouponPublishAvailableSacV2Result();

		couponPublishAvailableSacResult.setMaxCount(couponPublishAvailableEcRes.getMaxCount());
		couponPublishAvailableSacResult.setMaxMonth(couponPublishAvailableEcRes.getMaxMonth());
		couponPublishAvailableSacResult.setMaxMonthMdn(couponPublishAvailableEcRes.getMaxMonthMdn());
		couponPublishAvailableSacResult.setMaxDay(couponPublishAvailableEcRes.getMaxDay());
		couponPublishAvailableSacResult.setMaxDayMdn(couponPublishAvailableEcRes.getMaxDayMdn());
		couponPublishAvailableSacResult.setBuyMaxLimit(couponPublishAvailableEcRes.getBuyMaxLimit());
		couponPublishAvailableSacResult.setBuyMaxLimitForGift(couponPublishAvailableEcRes.getBuyMaxLimitForGift());
		couponPublishAvailableSacResult.setCurCount(couponPublishAvailableEcRes.getCurCount());
		couponPublishAvailableSacResult.setCurMonth(couponPublishAvailableEcRes.getCurMonth());
		couponPublishAvailableSacResult.setCurMonthMdn(couponPublishAvailableEcRes.getCurMonthMdn());
		couponPublishAvailableSacResult.setCurDay(couponPublishAvailableEcRes.getCurDay());
		couponPublishAvailableSacResult.setCurDayMdn(couponPublishAvailableEcRes.getCurDayMdn());

		return couponPublishAvailableSacResult;
	}

	/**
	 * 
	 * <pre>
	 * convertResForGetCouponStock.
	 * </pre>
	 * 
	 * @param couponStockEcRes
	 *            couponStockEcRes
	 * @return CouponStockSacResult
	 */
	private CouponStockSacResult convertResForGetCouponStock(CouponStockEcRes couponStockEcRes) {

		CouponStockSacResult couponStockSacResult = new CouponStockSacResult();

		couponStockSacResult.setMaxCount(couponStockEcRes.getMaxCount());
		couponStockSacResult.setMaxMonth(couponStockEcRes.getMaxMonth());
		couponStockSacResult.setMaxMonthMdn(couponStockEcRes.getMaxMonthMdn());
		couponStockSacResult.setMaxDay(couponStockEcRes.getMaxDay());
		couponStockSacResult.setMaxDayMdn(couponStockEcRes.getMaxDayMdn());
		couponStockSacResult.setBuyMaxLimit(couponStockEcRes.getBuyMaxLimit());
		couponStockSacResult.setCurCount(couponStockEcRes.getCurCount());
		couponStockSacResult.setCurMonth(couponStockEcRes.getCurMonth());
		couponStockSacResult.setCurMonthMdn(couponStockEcRes.getCurMonthMdn());
		couponStockSacResult.setCurDay(couponStockEcRes.getCurDay());
		couponStockSacResult.setCurDayMdn(couponStockEcRes.getCurDayMdn());

		return couponStockSacResult;
	}

}
