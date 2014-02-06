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
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.sac.client.purchase.shopping.vo.CouponPublishAvailableSacReq;
import com.skplanet.storeplatform.sac.client.purchase.shopping.vo.CouponPublishAvailableSacRes;
import com.skplanet.storeplatform.sac.client.purchase.shopping.vo.CouponUseStatusDetailSacRes;
import com.skplanet.storeplatform.sac.client.purchase.shopping.vo.CouponUseStatusSacReq;
import com.skplanet.storeplatform.sac.client.purchase.shopping.vo.CouponUseStatusSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.purchase.common.util.ConvertVO;
import com.skplanet.storeplatform.sac.purchase.shopping.service.ShoppingService;
import com.skplanet.storeplatform.sac.purchase.shopping.vo.CouponPublishAvailableSacParam;
import com.skplanet.storeplatform.sac.purchase.shopping.vo.CouponPublishAvailableSacResult;
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
			@RequestBody @Validated CouponUseStatusSacReq couponUseStatusSacReq, BindingResult bindingResult) {

		// Request Parameter Validation.
		if (bindingResult.hasErrors()) {
			if (bindingResult.hasFieldErrors()) {
				FieldError fieldError = bindingResult.getFieldError();
				if (StringUtils.equals("NotBlank", fieldError.getCode())) {
					throw new StorePlatformException("SAC_PUR_2000", bindingResult.getFieldError().getField());
				}
			}
			throw new StorePlatformException("SAC_PUR_9999");
		}

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
			@RequestBody @Validated CouponPublishAvailableSacReq couponPublishAvailableSacReq,
			BindingResult bindingResult) {

		// Request Parameter Validation.
		if (bindingResult.hasErrors()) {
			if (bindingResult.hasFieldErrors()) {
				FieldError fieldError = bindingResult.getFieldError();
				if (StringUtils.equals("NotNull", fieldError.getCode())) {
					throw new StorePlatformException("SAC_PUR_2000", bindingResult.getFieldError().getField());
				}
				if (StringUtils.equals("NotBlank", fieldError.getCode())) {
					throw new StorePlatformException("SAC_PUR_2000", bindingResult.getFieldError().getField());
				}
				if (StringUtils.equals("Min", fieldError.getCode())) {
					throw new StorePlatformException("SAC_PUR_2000", bindingResult.getFieldError().getField());
				}
			}
			throw new StorePlatformException("SAC_PUR_9999");
		}

		CouponPublishAvailableSacParam couponPublishAvailableSacParam = this.convertReqForGetCouponPublishAvailable(
				sacRequestHeader, couponPublishAvailableSacReq);

		CouponPublishAvailableSacResult couponUseStatusSacResult = this.shoppingService
				.getCouponPublishAvailable(couponPublishAvailableSacParam);

		return this.convertResForGetCouponPublishAvailable(couponUseStatusSacResult);

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

		ConvertVO.convertPurchaseCommonSacReq(sacRequestHeader, couponPublishAvailableSacReq,
				couponPublishAvailableSacParam);

		couponPublishAvailableSacParam.setCouponCode(couponPublishAvailableSacReq.getCouponCode());
		couponPublishAvailableSacParam.setItemCode(couponPublishAvailableSacReq.getItemCode());
		couponPublishAvailableSacParam.setItemCount(couponPublishAvailableSacReq.getItemCount());
		couponPublishAvailableSacParam.setMdn(couponPublishAvailableSacReq.getMdn());

		return couponPublishAvailableSacParam;

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

		ConvertVO.convertPurchaseCommonSacReq(sacRequestHeader, couponUseStatusSacReq, couponUseStatusSacParam);

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
