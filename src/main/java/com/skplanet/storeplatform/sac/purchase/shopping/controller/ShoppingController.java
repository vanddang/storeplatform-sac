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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.skplanet.storeplatform.sac.client.purchase.shopping.vo.CouponUseStatusDetailSacRes;
import com.skplanet.storeplatform.sac.client.purchase.shopping.vo.CouponUseStatusSacReq;
import com.skplanet.storeplatform.sac.client.purchase.shopping.vo.CouponUseStatusSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.purchase.common.util.ConvertVO;
import com.skplanet.storeplatform.sac.purchase.shopping.service.ShoppingService;
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

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

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

		CouponUseStatusSacParam couponUseStatusSacParam = this.convertReq(sacRequestHeader, couponUseStatusSacReq);

		CouponUseStatusSacResult couponUseStatusSacResult = this.shoppingService
				.getCouponUseStatus(couponUseStatusSacParam);

		// TODO : TEST
		couponUseStatusSacResult = new CouponUseStatusSacResult();
		List<CouponUseStatusDetailSacResult> useStatusList = new ArrayList<CouponUseStatusDetailSacResult>();
		CouponUseStatusDetailSacResult useStatusDetailSacResult = new CouponUseStatusDetailSacResult();
		useStatusDetailSacResult.setCpnPublishCd("1234");
		useStatusDetailSacResult.setCpnUseStatusCd("0");
		useStatusList.add(useStatusDetailSacResult);

		couponUseStatusSacResult.setCpnUseStatusList(useStatusList);

		CouponUseStatusSacRes couponUseStatusSacRes = this.convertRes(couponUseStatusSacResult);

		return couponUseStatusSacRes;

	}

	private CouponUseStatusSacParam convertReq(SacRequestHeader sacRequestHeader,
			CouponUseStatusSacReq couponUseStatusSacReq) {

		CouponUseStatusSacParam couponUseStatusSacParam = new CouponUseStatusSacParam();

		ConvertVO.convertPurchaseCommonSacReq(sacRequestHeader, couponUseStatusSacReq, couponUseStatusSacParam);

		couponUseStatusSacParam.setPrchsId(couponUseStatusSacReq.getPrchsId());
		couponUseStatusSacParam.setCpnPublishCd(couponUseStatusSacReq.getCpnPublishCd());

		return couponUseStatusSacParam;

	}

	private CouponUseStatusSacRes convertRes(CouponUseStatusSacResult couponUseStatusSacResult) {

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
