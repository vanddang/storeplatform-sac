/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.shopping.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.sac.client.purchase.shopping.vo.CouponPublishAvailableSacReq;
import com.skplanet.storeplatform.sac.client.purchase.shopping.vo.CouponPublishAvailableSacRes;
import com.skplanet.storeplatform.sac.client.purchase.shopping.vo.CouponPublishAvailableSacV2Req;
import com.skplanet.storeplatform.sac.client.purchase.shopping.vo.CouponPublishAvailableSacV2Res;
import com.skplanet.storeplatform.sac.client.purchase.shopping.vo.CouponStockSacReq;
import com.skplanet.storeplatform.sac.client.purchase.shopping.vo.CouponStockSacRes;
import com.skplanet.storeplatform.sac.client.purchase.shopping.vo.CouponUseStatusDetailSacRes;
import com.skplanet.storeplatform.sac.client.purchase.shopping.vo.CouponUseStatusSacReq;
import com.skplanet.storeplatform.sac.client.purchase.shopping.vo.CouponUseStatusSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.purchase.common.util.ConvertVO;
import com.skplanet.storeplatform.sac.purchase.shopping.service.ShoppingService;
import com.skplanet.storeplatform.sac.purchase.shopping.vo.CouponPublishAvailableSacParam;
import com.skplanet.storeplatform.sac.purchase.shopping.vo.CouponPublishAvailableSacResult;
import com.skplanet.storeplatform.sac.purchase.shopping.vo.CouponPublishAvailableSacV2Param;
import com.skplanet.storeplatform.sac.purchase.shopping.vo.CouponPublishAvailableSacV2Result;
import com.skplanet.storeplatform.sac.purchase.shopping.vo.CouponStockSacParam;
import com.skplanet.storeplatform.sac.purchase.shopping.vo.CouponStockSacResult;
import com.skplanet.storeplatform.sac.purchase.shopping.vo.CouponUseStatusDetailSacResult;
import com.skplanet.storeplatform.sac.purchase.shopping.vo.CouponUseStatusSacParam;
import com.skplanet.storeplatform.sac.purchase.shopping.vo.CouponUseStatusSacResult;

/**
 * 쇼핑 쿠폰 Controller.
 * 
 * Updated on : 2014. 2. 3. Updated by : nTels_cswoo81, nTels.
 */
@Controller
@RequestMapping(value = "/purchase/shopping")
public class ShoppingController {

	@Autowired
	private ShoppingService shoppingService;

	/**
	 * 
	 * <pre>
	 * 쇼핑 쿠폰 사용 여부 조회.
	 * </pre>
	 * 
	 * @param sacRequestHeader
	 *            sacRequestHeader
	 * @param couponUseStatusSacReq
	 *            couponUseStatusSacReq
	 * @param bindingResult
	 *            bindingResult
	 * @return CouponUseStatusSacRes
	 */
	@RequestMapping(value = "/getCouponUseStatus/v1", method = RequestMethod.POST)
	@ResponseBody
	public CouponUseStatusSacRes getCouponUseStatus(SacRequestHeader sacRequestHeader,
			@RequestBody @Validated CouponUseStatusSacReq couponUseStatusSacReq) {

		CouponUseStatusSacParam couponUseStatusSacParam = this.convertReqForGetCouponUseStatus(sacRequestHeader,
				couponUseStatusSacReq);

		CouponUseStatusSacResult couponUseStatusSacResult = this.shoppingService
				.getCouponUseStatus(couponUseStatusSacParam);

		return this.convertResForGetCouponUseStatus(couponUseStatusSacResult);

	}

	/**
	 * 
	 * <pre>
	 * 쇼핑쿠폰 발급가능여부 조회.
	 * </pre>
	 * 
	 * @param sacRequestHeader
	 *            sacRequestHeader
	 * @param couponPublishAvailableSacReq
	 *            couponPublishAvailableSacReq
	 * @param bindingResult
	 *            bindingResult
	 * @return CouponPublishAvailableSacRes
	 */
	@RequestMapping(value = "/getCouponPublishAvailable/v1", method = RequestMethod.POST)
	@ResponseBody
	public CouponPublishAvailableSacRes getCouponPublishAvailable(SacRequestHeader sacRequestHeader,
			@RequestBody @Validated CouponPublishAvailableSacReq couponPublishAvailableSacReq) {

		CouponPublishAvailableSacParam couponPublishAvailableSacParam = this.convertReqForGetCouponPublishAvailable(
				sacRequestHeader, couponPublishAvailableSacReq);

		CouponPublishAvailableSacResult couponUseStatusSacResult = this.shoppingService
				.getCouponPublishAvailable(couponPublishAvailableSacParam);

		return this.convertResForGetCouponPublishAvailable(couponUseStatusSacResult);
	}

	/**
	 * 
	 * <pre>
	 * 쇼핑쿠폰 발급가능여부 조회.
	 * </pre>
	 * 
	 * @param sacRequestHeader
	 *            sacRequestHeader
	 * @param couponPublishAvailableSacReq
	 *            couponPublishAvailableSacReq
	 * @param bindingResult
	 *            bindingResult
	 * @return CouponPublishAvailableSacRes
	 */
	@RequestMapping(value = "/getCouponPublishAvailable/v2", method = RequestMethod.POST)
	@ResponseBody
	public CouponPublishAvailableSacV2Res getCouponPublishAvailableV2(SacRequestHeader sacRequestHeader,
			@RequestBody @Validated CouponPublishAvailableSacV2Req couponPublishAvailableSacReq) {

		CouponPublishAvailableSacV2Param couponPublishAvailableSacParam = this
				.convertReqForGetCouponPublishAvailableV2(sacRequestHeader, couponPublishAvailableSacReq);

		CouponPublishAvailableSacV2Result couponUseStatusSacResult = this.shoppingService
				.getCouponPublishAvailableV2(couponPublishAvailableSacParam);

		return this.convertResForGetCouponPublishAvailableV2(couponUseStatusSacResult);
	}

	/**
	 * 
	 * <pre>
	 * 쇼핑쿠폰 재고 조회.
	 * </pre>
	 * 
	 * @param sacRequestHeader
	 *            sacRequestHeader
	 * @param couponPublishAvailableSacReq
	 *            couponPublishAvailableSacReq
	 * @param bindingResult
	 *            bindingResult
	 * @return CouponPublishAvailableSacRes
	 */
	@RequestMapping(value = "/getCouponStock/v1", method = RequestMethod.POST)
	@ResponseBody
	public CouponStockSacRes getCouponStock(SacRequestHeader sacRequestHeader,
			@RequestBody @Validated CouponStockSacReq couponStockSacReq) {

		// CouponStockSacParam couponStockSacParam = this.convertReqForGetCouponStock(sacRequestHeader,
		// couponStockSacReq);
		// CouponStockSacResult couponUseStatusSacResult = this.shoppingService.getCouponStock(couponStockSacParam);
		// return this.convertResForGetCouponStock(couponUseStatusSacResult);
		return new CouponStockSacRes();

	}

	/**
	 * 
	 * <pre>
	 * convertReqForGetCouponPublishAvailable.
	 * </pre>
	 * 
	 * @param sacRequestHeader
	 *            sacRequestHeader
	 * @param couponPublishAvailableSacReq
	 *            couponPublishAvailableSacReq
	 * @return CouponPublishAvailableSacParam
	 */
	private CouponPublishAvailableSacParam convertReqForGetCouponPublishAvailable(SacRequestHeader sacRequestHeader,
			CouponPublishAvailableSacReq couponPublishAvailableSacReq) {

		CouponPublishAvailableSacParam couponPublishAvailableSacParam = new CouponPublishAvailableSacParam();

		if (!ConvertVO.convertPurchaseCommonSacReq(sacRequestHeader, couponPublishAvailableSacReq,
				couponPublishAvailableSacParam)) {
			throw new StorePlatformException("SAC_PUR_9901");
		}

		couponPublishAvailableSacParam.setCouponCode(couponPublishAvailableSacReq.getCouponCode());
		couponPublishAvailableSacParam.setItemCode(couponPublishAvailableSacReq.getItemCode());
		couponPublishAvailableSacParam.setItemCount(couponPublishAvailableSacReq.getItemCount());
		couponPublishAvailableSacParam.setMdn(couponPublishAvailableSacReq.getMdn());
		couponPublishAvailableSacParam.setProdId(couponPublishAvailableSacReq.getProdId());

		return couponPublishAvailableSacParam;

	}

	/**
	 * 
	 * <pre>
	 * convertReqForGetCouponPublishAvailable.
	 * </pre>
	 * 
	 * @param sacRequestHeader
	 *            sacRequestHeader
	 * @param couponPublishAvailableSacReq
	 *            couponPublishAvailableSacReq
	 * @return CouponPublishAvailableSacParam
	 */
	private CouponPublishAvailableSacV2Param convertReqForGetCouponPublishAvailableV2(
			SacRequestHeader sacRequestHeader, CouponPublishAvailableSacV2Req couponPublishAvailableSacReq) {

		CouponPublishAvailableSacV2Param couponPublishAvailableSacParam = new CouponPublishAvailableSacV2Param();

		if (!ConvertVO.convertPurchaseCommonSacReq(sacRequestHeader, couponPublishAvailableSacReq,
				couponPublishAvailableSacParam)) {
			throw new StorePlatformException("SAC_PUR_9901");
		}

		couponPublishAvailableSacParam.setCouponCode(couponPublishAvailableSacReq.getCouponCode());
		couponPublishAvailableSacParam.setItemCode(couponPublishAvailableSacReq.getItemCode());
		couponPublishAvailableSacParam.setItemCount(couponPublishAvailableSacReq.getItemCount());
		couponPublishAvailableSacParam.setMdn(couponPublishAvailableSacReq.getMdn());
		couponPublishAvailableSacParam.setProdId(couponPublishAvailableSacReq.getProdId());
		couponPublishAvailableSacParam.setGiftFlag(couponPublishAvailableSacReq.getGiftFlag());

		return couponPublishAvailableSacParam;

	}

	/**
	 * 
	 * <pre>
	 * convertReqForGetCouponStock.
	 * </pre>
	 * 
	 * @param sacRequestHeader
	 *            sacRequestHeader
	 * @param couponStockSacReq
	 *            couponStockSacReq
	 * @return CouponStockSacParam
	 */
	private CouponStockSacParam convertReqForGetCouponStock(SacRequestHeader sacRequestHeader,
			CouponStockSacReq couponStockSacReq) {

		CouponStockSacParam couponStockSacParam = new CouponStockSacParam();

		if (!ConvertVO.convertPurchaseCommonSacReq(sacRequestHeader, couponStockSacReq, couponStockSacParam)) {
			throw new StorePlatformException("SAC_PUR_9901");
		}

		couponStockSacParam.setCouponCode(couponStockSacReq.getCouponCode());
		couponStockSacParam.setItemCode(couponStockSacReq.getItemCode());
		couponStockSacParam.setItemCount(couponStockSacReq.getItemCount());
		couponStockSacParam.setMdn(couponStockSacReq.getMdn());
		couponStockSacParam.setProdId(couponStockSacReq.getProdId());
		couponStockSacParam.setGiftFlag(couponStockSacReq.getGiftFlag());

		return couponStockSacParam;

	}

	/**
	 * 
	 * <pre>
	 * convertResForGetCouponPublishAvailable.
	 * </pre>
	 * 
	 * @param couponUseStatusSacResult
	 *            couponUseStatusSacResult
	 * @return CouponPublishAvailableSacRes
	 */
	private CouponPublishAvailableSacRes convertResForGetCouponPublishAvailable(
			CouponPublishAvailableSacResult couponUseStatusSacResult) {

		CouponPublishAvailableSacRes couponPublishAvailableSacRes = new CouponPublishAvailableSacRes();
		couponPublishAvailableSacRes.setStatusCd(couponUseStatusSacResult.getStatusCd());
		couponPublishAvailableSacRes.setStatusMsg(couponUseStatusSacResult.getStatusMsg());

		return couponPublishAvailableSacRes;

	}

	/**
	 * 
	 * <pre>
	 * convertResForGetCouponPublishAvailable.
	 * </pre>
	 * 
	 * @param couponUseStatusSacResult
	 *            couponUseStatusSacResult
	 * @return CouponPublishAvailableSacRes
	 */
	private CouponPublishAvailableSacV2Res convertResForGetCouponPublishAvailableV2(
			CouponPublishAvailableSacV2Result couponUseStatusSacResult) {

		CouponPublishAvailableSacV2Res couponPublishAvailableSacRes = new CouponPublishAvailableSacV2Res();

		couponPublishAvailableSacRes.setMaxCount(couponUseStatusSacResult.getMaxCount());
		couponPublishAvailableSacRes.setMaxMonth(couponUseStatusSacResult.getMaxMonth());
		couponPublishAvailableSacRes.setMaxMonthMdn(couponUseStatusSacResult.getMaxMonthMdn());
		couponPublishAvailableSacRes.setMaxDay(couponUseStatusSacResult.getMaxDay());
		couponPublishAvailableSacRes.setMaxDayMdn(couponUseStatusSacResult.getMaxDayMdn());

		couponPublishAvailableSacRes.setCurCount(couponUseStatusSacResult.getCurCount());
		couponPublishAvailableSacRes.setCurMonth(couponUseStatusSacResult.getCurMonth());
		couponPublishAvailableSacRes.setCurMonthMdn(couponUseStatusSacResult.getCurMonthMdn());
		couponPublishAvailableSacRes.setCurDay(couponUseStatusSacResult.getCurDay());
		couponPublishAvailableSacRes.setCurDayMdn(couponUseStatusSacResult.getCurDayMdn());

		couponPublishAvailableSacRes.setBuyMaxLimit(couponUseStatusSacResult.getBuyMaxLimit());
		couponPublishAvailableSacRes.setBuyMaxLimitForGift(couponUseStatusSacResult.getBuyMaxLimitForGift());

		return couponPublishAvailableSacRes;

	}

	/**
	 * 
	 * <pre>
	 * convertResForGetCouponStock.
	 * </pre>
	 * 
	 * @param couponUseStatusSacResult
	 *            couponUseStatusSacResult
	 * @return CouponStockSacRes
	 */
	private CouponStockSacRes convertResForGetCouponStock(CouponStockSacResult couponStockSacResult) {

		CouponStockSacRes couponStockSacRes = new CouponStockSacRes();
		couponStockSacRes.setSaleDayLimit(couponStockSacResult.getMaxDay());
		couponStockSacRes.setSaleDayLimitPerson(couponStockSacResult.getMaxDayMdn());
		couponStockSacRes.setSaleMonthLimit(couponStockSacResult.getMaxMonth());
		couponStockSacRes.setSaleMonthLimitPerson(couponStockSacResult.getMaxMonthMdn());
		couponStockSacRes.setSaleOnceLimit(couponStockSacResult.getBuyMaxLimit());

		return couponStockSacRes;

	}

	/**
	 * 
	 * <pre>
	 * convertReqForGetCouponUseStatus.
	 * </pre>
	 * 
	 * @param sacRequestHeader
	 *            sacRequestHeader
	 * @param couponUseStatusSacReq
	 *            couponUseStatusSacReq
	 * @return CouponUseStatusSacParam
	 */
	private CouponUseStatusSacParam convertReqForGetCouponUseStatus(SacRequestHeader sacRequestHeader,
			CouponUseStatusSacReq couponUseStatusSacReq) {

		CouponUseStatusSacParam couponUseStatusSacParam = new CouponUseStatusSacParam();

		if (!ConvertVO.convertPurchaseCommonSacReq(sacRequestHeader, couponUseStatusSacReq, couponUseStatusSacParam)) {
			throw new StorePlatformException("SAC_PUR_9901");
		}

		couponUseStatusSacParam.setPrchsId(couponUseStatusSacReq.getPrchsId());
		couponUseStatusSacParam.setCpnPublishCd(couponUseStatusSacReq.getCpnPublishCd());

		return couponUseStatusSacParam;

	}

	/**
	 * 
	 * <pre>
	 * convertResForGetCouponUseStatus.
	 * </pre>
	 * 
	 * @param couponUseStatusSacResult
	 *            couponUseStatusSacResult
	 * @return CouponUseStatusSacRes
	 */
	private CouponUseStatusSacRes convertResForGetCouponUseStatus(CouponUseStatusSacResult couponUseStatusSacResult) {

		CouponUseStatusSacRes couponUseStatusSacRes = new CouponUseStatusSacRes();

		List<CouponUseStatusDetailSacRes> couponUseStatusDetailList = new ArrayList<CouponUseStatusDetailSacRes>();
		for (CouponUseStatusDetailSacResult couponUseStatusDetailSacResult : couponUseStatusSacResult
				.getCpnUseStatusList()) {
			CouponUseStatusDetailSacRes couponUseStatusDetailSacRes = new CouponUseStatusDetailSacRes();
			couponUseStatusDetailSacRes.setCpnPublishCd(couponUseStatusDetailSacResult.getCpnPublishCd());
			couponUseStatusDetailSacRes.setCpnUseStatusCd(couponUseStatusDetailSacResult.getCpnUseStatusCd());

			couponUseStatusDetailList.add(couponUseStatusDetailSacRes);
		}

		couponUseStatusSacRes.setCpnUseStatusList(couponUseStatusDetailList);

		return couponUseStatusSacRes;

	}

}
