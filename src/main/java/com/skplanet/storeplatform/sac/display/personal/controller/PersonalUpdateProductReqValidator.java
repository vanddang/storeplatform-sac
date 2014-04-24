/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.personal.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.sac.client.display.vo.personal.PersonalUpdateProductReq;

public class PersonalUpdateProductReqValidator implements Validator {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	private final Validator validator;

	public PersonalUpdateProductReqValidator(Validator validator) {
		this.validator = validator;
	}

	@Override
	public boolean supports(Class<?> aClass) {
		return PersonalUpdateProductReq.class.equals(aClass);
	}

	@Override
	public void validate(Object o, Errors errors) {
		this.validator.validate(o, errors);
		PersonalUpdateProductReq vo = (PersonalUpdateProductReq) o;

		if ("updatedList".equals(vo.getMemberType())) {
			if (StringUtils.isEmpty(vo.getUserKey())) {
				errors.rejectValue("userKey", "NotEmpty", new Object[] { "userKey" }, "userKey");
			}
			if (StringUtils.isEmpty(vo.getDeviceKey())) {
				errors.rejectValue("deviceKey", "NotEmpty", new Object[] { "deviceKey" }, "deviceKey");
			}
		}
	}
}
