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

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.sac.client.display.vo.openapi.BestDownloadAppSacReq;

/**
 * <p>
 * BestDownloadAppSacReqValidator
 * </p>
 * Updated on : 2014. 04. 24 Updated by : 이석희, 아이에스 플러스.
 */

public class BestDownloadAppSacReqValidator implements Validator {

	@Override
	public boolean supports(Class<?> aClass) {
		return BestDownloadAppSacReq.class.equals(aClass);
	}

	@Override
	public void validate(Object o, Errors errors) {
		BestDownloadAppSacReq vo = (BestDownloadAppSacReq) o;

		if (StringUtils.isEmpty(vo.getListId())) {
			if (StringUtils.isEmpty(vo.getOrderedBy())) {
				errors.rejectValue("orderedBy", "NotEmpty", new Object[] { "orderedBy" }, "orderedBy");
			}
		}
	}
}
