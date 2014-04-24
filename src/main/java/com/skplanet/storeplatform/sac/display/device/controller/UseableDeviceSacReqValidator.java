/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.device.controller;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.sac.client.display.vo.device.UseableDeviceSacReq;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;

/**
 * <p>
 * UseableDeviceSacReqValidator
 * </p>
 * Updated on : 2014. 04. 24 Updated by : 이석희, 아이에스 플러스.
 */

public class UseableDeviceSacReqValidator implements Validator {

	@Override
	public boolean supports(Class<?> aClass) {
		return UseableDeviceSacReq.class.equals(aClass);
	}

	@Override
	public void validate(Object o, Errors errors) {
		UseableDeviceSacReq vo = (UseableDeviceSacReq) o;

		if (StringUtils.isEmpty(vo.getSvcGrpCd())) {
			errors.rejectValue("svcGrpCd", "NotEmpty", new Object[] { "svcGrpCd" }, "svcGrpCd");
		}

		if (!DisplayConstants.DP_SOCIAL_SHOPPING_PROD_SVC_GRP_CD.equals(vo.getSvcGrpCd())) {
			if (StringUtils.isEmpty(vo.getProductId())) {
				errors.rejectValue("productId", "NotEmpty", new Object[] { "productId" }, "productId");
			}
		}
	}
}
