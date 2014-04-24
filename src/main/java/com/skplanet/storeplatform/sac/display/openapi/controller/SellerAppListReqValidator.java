/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.openapi.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.sac.client.display.vo.openapi.SellerAppListReq;

public class SellerAppListReqValidator implements Validator {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	private final Validator validator;

	public SellerAppListReqValidator(Validator validator) {
		this.validator = validator;
	}

	@Override
	public boolean supports(Class<?> aClass) {
		return SellerAppListReq.class.equals(aClass);
	}

	@Override
	public void validate(Object o, Errors errors) {
		this.validator.validate(o, errors);
		SellerAppListReq vo = (SellerAppListReq) o;
		if (("N").equals(vo.getAdmin())) {
			if (StringUtils.isEmpty(vo.getSellerId())) {
				errors.rejectValue("sellerId", "NotEmpty", new Object[] { "sellerId" }, "sellerId");
			} else if (StringUtils.isEmpty(vo.getSellerKey())) {
				errors.rejectValue("sellerKey", "NotEmpty", new Object[] { "sellerKey" }, "sellerKey");
			}
		}
	}
}
