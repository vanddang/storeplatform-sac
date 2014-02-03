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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.sac.client.purchase.shopping.coupon.vo.UseStatusDetailSacRes;
import com.skplanet.storeplatform.sac.client.purchase.shopping.coupon.vo.UseStatusSacReq;
import com.skplanet.storeplatform.sac.client.purchase.shopping.coupon.vo.UseStatusSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.purchase.common.util.ConvertVO;
import com.skplanet.storeplatform.sac.purchase.shopping.service.ShoppingCouponService;
import com.skplanet.storeplatform.sac.purchase.shopping.vo.UseStatusDetailSacResult;
import com.skplanet.storeplatform.sac.purchase.shopping.vo.UseStatusSacParam;
import com.skplanet.storeplatform.sac.purchase.shopping.vo.UseStatusSacResult;

/**
 * 쇼핑 쿠폰 Controller.
 * 
 * Updated on : 2014. 2. 3. Updated by : nTels_cswoo81, nTels.
 */
@Controller
@RequestMapping(value = "/purchase/shopping/coupon")
public class ShoppingCouponController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ShoppingCouponService shoppingCouponService;

	/**
	 * 
	 * <pre>
	 * 쇼핑 쿠폰 사용 여부 조회.
	 * </pre>
	 * 
	 * @param sacRequestHeader
	 *            sacRequestHeader
	 * @param useStatusSacReq
	 *            useStatusSacReq
	 * @param bindingResult
	 *            bindingResult
	 * @return UseStatusSacRes
	 */
	@RequestMapping(value = "/getUseStatus/v1", method = RequestMethod.POST)
	@ResponseBody
	public UseStatusSacRes getUseStatus(SacRequestHeader sacRequestHeader,
			@RequestBody @Validated UseStatusSacReq useStatusSacReq, BindingResult bindingResult) {

		// 필수값 체크.
		// TODO : 에러코드 정의.
		if (bindingResult.hasErrors()) {
			if (bindingResult.hasFieldErrors()) {
				throw new StorePlatformException("SAC_PUR_0001", bindingResult.getFieldError().getField());
			}
			throw new StorePlatformException("SAC_PUR_9999");
		}

		UseStatusSacParam useStatusSacParam = this.convertReq(sacRequestHeader, useStatusSacReq);

		UseStatusSacResult useStatusSacResult = this.shoppingCouponService.getUseStatus(useStatusSacParam);

		// TODO : TEST
		useStatusSacResult = new UseStatusSacResult();
		List<UseStatusDetailSacResult> useStatusList = new ArrayList<UseStatusDetailSacResult>();
		UseStatusDetailSacResult useStatusDetailSacResult = new UseStatusDetailSacResult();
		useStatusDetailSacResult.setCpPublishCd("1234");
		useStatusDetailSacResult.setUseStatusCd("0");
		useStatusList.add(useStatusDetailSacResult);

		useStatusSacResult.setUseStatusList(useStatusList);

		UseStatusSacRes useStatusSacRes = this.convertRes(useStatusSacResult);

		return useStatusSacRes;

	}

	private UseStatusSacParam convertReq(SacRequestHeader sacRequestHeader, UseStatusSacReq useStatusSacReq) {

		UseStatusSacParam useStatusSacParam = new UseStatusSacParam();

		ConvertVO.convertPurchaseCommonSacReq(sacRequestHeader, useStatusSacReq, useStatusSacParam);

		useStatusSacParam.setPrchsId(useStatusSacReq.getPrchsId());
		useStatusSacParam.setCpPublishCd(useStatusSacReq.getCpPublishCd());

		return useStatusSacParam;

	}

	private UseStatusSacRes convertRes(UseStatusSacResult useStatusSacResult) {

		UseStatusSacRes useStatusSacRes = new UseStatusSacRes();

		List<UseStatusDetailSacRes> useStatusDetailList = new ArrayList<UseStatusDetailSacRes>();
		for (UseStatusDetailSacResult useStatusDetailSacResult : useStatusSacResult.getUseStatusList()) {
			UseStatusDetailSacRes useStatusDetailSacRes = new UseStatusDetailSacRes();
			useStatusDetailSacRes.setCpPublishCd(useStatusDetailSacResult.getCpPublishCd());
			useStatusDetailSacRes.setUseStatusCd(useStatusDetailSacResult.getUseStatusCd());

			useStatusDetailList.add(useStatusDetailSacRes);
		}

		useStatusSacRes.setUseStatusList(useStatusDetailList);

		return useStatusSacRes;

	}

}
